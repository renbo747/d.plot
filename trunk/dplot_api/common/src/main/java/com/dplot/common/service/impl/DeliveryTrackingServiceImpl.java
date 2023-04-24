package com.dplot.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.ERPService;
import com.dplot.mapper.*;
import com.dplot.util.DateTimeUtil;
import net.sf.json.JSONObject;
import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.DeliveryTrackingService;
import com.dplot.util.HttpConnectionUtil;

@Service
public class DeliveryTrackingServiceImpl implements DeliveryTrackingService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryTrackingServiceImpl.class);

    @Autowired
    private ComOrderGoodsDelivMapper comOrderGoodsDelivMapper;

    @Autowired
    private ComOrderGoodsHisMapper comOrderGoodsHisMapper;
    
    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private ClaimGoodsHistMapper claimGoodsHistMapper;

    @Autowired
    private DealerDelivMapper dealerDelivMapper;

    @Autowired
    private ComOrderMapper comOrderMapper;

    @Autowired
    private CJMessageService cjMessageService;

    @Autowired
    private ERPService erpService;

    @Autowired
    private ExIfLogMapper exIfLogMapper;

    @Resource(name="propertiesFactory")
    private Properties prop;

    @Override
    public SOMap sendTraceRequest(SOMap param, List<SOMap> orderList) {
        Map<String, Object> sendData = new HashMap<>();
        List<Map<String, Object>> resultDetails = new ArrayList<>();

        String goodsFlowMemberCode = prop.getProperty("goods.flow.member.code");
        String goodsFlowSendTraceRequestUrl = prop.getProperty("goods.flow.send.trace.request.url");

        //SOMap Camel Case로 변환
        for(SOMap map : orderList){
            Map<String, Object> data = new HashMap<>();

            for(Map.Entry<String, Object> entry : map.entrySet()){
                data.put(JdbcUtils.convertUnderscoreNameToPropertyName(entry.getKey()), entry.getValue());
            }
            resultDetails.add(data);
        }

        sendData.put("transUniqueCode", param.get("trans_unique_code"));
        sendData.put("fromName", param.get("from_name"));
        sendData.put("toName", param.get("to_name"));
        sendData.put("logisticsCode", param.get("logistics_code"));
        sendData.put("invoiceNo", param.get("invoice_no"));
        sendData.put("dlvretType", param.get("dlvret_type"));
        if(param.containsKey("def_code1")){
            sendData.put("defCode1", param.get("def_code1"));
        }
        sendData.put("requestDetails", resultDetails);

        List<Map<String, Object>> items = new ArrayList<>();
        items.add(sendData);
        Map<String, Object> item = new HashMap<>();
        item.put("items", items);
        Map<String, Object> data = new HashMap<>();
        data.put("data", item);

        String url = String.format("%s/%s", goodsFlowSendTraceRequestUrl, goodsFlowMemberCode);

        Map<String, Object> header = getGoodsFlowHeader("zkm");

        SOMap result = new SOMap();
        Map<String, Object> sendResult = HttpConnectionUtil.httpsRequest(url, "POST", data, header);
        if(sendResult != null && sendResult.containsKey("httpCode") && "200".equals(sendResult.get("httpCode").toString())){
            Map<String, Object> error = (HashMap<String, Object>)sendResult.get("error");
            result.put("status", error.get("status"));
            result.put("message", error.get("message"));
            result.put("details", error.get("details"));
        } else {
            result.putAll(sendResult);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap receiveTraceResult(SOMap param) throws Exception {

        logger.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        logger.info("::::::::::::::::::::::::::::: DELIVERY TRACE START :::::::::::::::::::::::::::::");

        String goodsFlowMemberCode = prop.getProperty("goods.flow.member.code");
        String goodsFlowReceiveTraceResultUrl = prop.getProperty("goods.flow.receive.trace.result.url");
        String url = String.format("%s/%s", goodsFlowReceiveTraceResultUrl, goodsFlowMemberCode);

        Map<String, Object> header = getGoodsFlowHeader("zkm");

        //주석처리하면 안됨. temp 의미없는 데이터가 아님.
        //공통으로 사용하는 httpRequest 메소드에서 POST 요청시 BODY가 없는 경우 임시 처리용도로 데이터를 발송
        SOMap result = new SOMap();
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("temp", "temp");

        List<Map<String, Object>> traceList = new ArrayList<>();
        List<Map<String, Object>> pickUpList = new ArrayList<>(); // 집화리스트
        List<Map<String, Object>> completeList = new ArrayList<>(); // 배달완료리스트
        List<Map<String, Object>> errorList = new ArrayList<>(); // 오류리스트
        
        //ERP에서 가져온 데이터로 배송추적하는 경우 예외 응답처리하기 위해 체크
        List<SOMap> directList = new ArrayList<>();

        Map<String, Object> receiveResult = new HashMap<>();

        Map<String, Object> sendResult = HttpConnectionUtil.httpsRequest(url, "POST", sendData, header);
        if(sendResult != null && sendResult.containsKey("httpCode") && "200".equals(sendResult.get("httpCode").toString())){
            Map<String, Object> error = (Map<String, Object>)sendResult.get("error");
            Map<String, Object> data = (Map<String, Object>)sendResult.get("data");
            if("200".equals(error.get("status").toString()) && data != null){

                JSONObject json = new JSONObject();
                json.putAll(data);

                SOMap exLogParam = new SOMap();
                exLogParam.put("iflog", json.toString());
                exLogParam.put("exlogtype", CMConst.EX_IF_LOG_DELIVERY);
                exIfLogMapper.insertExIfLog(exLogParam);

                traceList = (ArrayList) data.get("items");

                for(Map<String, Object> trace : traceList){
                    if(trace.containsKey("logisticsCode")) {
                        String logistics = trace.get("logisticsCode").toString();
                        String logisType = getLogisTypeCode(logistics);
                        trace.put("logistType", logisType);
                    }

                    if(trace.containsKey("defCode1") && trace.get("defCode1") != null){

                    }

                    trace.put("isERP", (trace.containsKey("defCode1") && trace.get("defCode1") != null) ? "Y" : "N");

                }

                pickUpList = traceList.stream().filter(a -> "30".equals(a.get("dlvStatType").toString()) && !"Y".equals(a.get("isERP"))).collect(Collectors.toList());
                completeList = traceList.stream().filter(a -> "70".equals(a.get("dlvStatType").toString()) && !"Y".equals(a.get("isERP"))).collect(Collectors.toList());
                errorList = traceList.stream().filter(a -> "99".equals(a.get("dlvStatType").toString()) && !"Y".equals(a.get("isERP"))).collect(Collectors.toList());
                directList = traceList.stream().filter(a-> "Y".equals(a.get("isERP"))).map(a-> {
                    SOMap direct = new SOMap();
                    direct.putAll(a);
                    direct.put("transuniquecode", a.get("transUniqueCode"));
                    return direct;
                }).collect(Collectors.toList());
            }
            receiveResult.put("status", error.get("status"));
            receiveResult.put("message", error.get("message"));
            receiveResult.put("details", error.get("details"));
            result.put("receiveResult", receiveResult);
        } else {
            result.putAll(sendResult);
        }

        try {
            List<SOMap> orderDeliveringList = new ArrayList<>();
            List<SOMap> claimDeliveringList = new ArrayList<>();

            //배송중 응답 리스트
            if(pickUpList.size() > 0){
                for(Map<String, Object> pickUp : pickUpList){
                    /*
                     * ODS006(배송준비중) -> ODS007(배송중)
                     * RTS008(반품처리-회수완료) -> RTX009(반품완료-반송처리)
                     * EXS008(교환처리-회수완료) -> EXS009(교환처리-배송진행)
                     * EXS008(교환처리-회수완료) -> EXS011(교환완료-반송처리)
                     */
                    SOMap orderDbParam = new SOMap();
                    orderDbParam.putAll(pickUp);
                    orderDbParam.put("status", CMConst.ORDER_PREPARING_DELIV);
                    List<SOMap> deliveringOrdResultList = comOrderGoodsDelivMapper.selectDeliveringList(orderDbParam);
                    if(deliveringOrdResultList.size() > 0){
                        orderDeliveringList.addAll(deliveringOrdResultList);
                    }

                    //클레임 반품에 대한 반송, 교환에 대한 배송, 교환에 대한 반송 처리 리스트
                    SOMap claimDbParam = new SOMap();
                    claimDbParam.putAll(pickUp);
                    claimDbParam.put("target_return_status", CMConst.RETURN_COMPLETE_DELIVERY);
                    claimDbParam.put("target_exchange_status_arr", new String[]{CMConst.EXCHANGE_RETURN_COMPLETE});
                    List<SOMap> deliveringClmResultList = claimMapper.selectClaimDeliveringList(claimDbParam);
                    if(deliveringClmResultList.size() > 0){
                        for(SOMap check : deliveringClmResultList){
                            if( (!"".equals(check.getStr("logistype"))) && pickUp.get("logistType").toString().equalsIgnoreCase(check.getStr("logistype"))){
                                claimDeliveringList.add(check);
                            }
                        }
                    }

                }
                //실제 배송 상태 변경 처리 ODS006(배송준비중) -> ODS007(배송중)
                if(orderDeliveringList.size() > 0){
                    List<String> orgDelividxArr = orderDeliveringList.stream().map(a -> a.get("orgdelividx").toString()).collect(Collectors.toList());
                    List<String> invoiceNoList = orderDeliveringList.stream().map(a -> a.get("invoiceno").toString()).collect(Collectors.toList());
                    SOMap insertUpdateParam = new SOMap();
                    insertUpdateParam.put("idx", 1);
                    insertUpdateParam.put("ordstatus", CMConst.ORDER_IN_DELIVERY);
                    insertUpdateParam.put("userid", "SYSTEM");
                    insertUpdateParam.put("orgdelividxlist", orgDelividxArr);
                    insertUpdateParam.put("invoice_no_arr", invoiceNoList);

                    comOrderGoodsHisMapper.insertComOrderGoodsHisAll(insertUpdateParam);
                    comOrderGoodsDelivMapper.updateAdminOrderGoodsDeliv(insertUpdateParam);

                    insertUpdateParam.put("siteid", param.get("siteid"));
                    erpService.insertOrderDeliveryStateERPData(insertUpdateParam);

                    sendDeliveryMessage(insertUpdateParam);
                }

                if(claimDeliveringList.size() > 0){
                    Map<String, List<Map<String, Object>>> returnAndChangeTypeEntryMap = claimDeliveringList.stream().collect(Collectors.groupingBy(a -> a.get("type").toString()));
                    List<Map<String, Object>> exDeliveryList = returnAndChangeTypeEntryMap.get("EXCHANGE_DELIVERY");
//                    List<Map<String, Object>> exReturnList = returnAndChangeTypeEntryMap.get("EXCHANGE_RETURN");
//                    List<Map<String, Object>> reReturnList = returnAndChangeTypeEntryMap.get("RETURN_RETURN");

                    // 교환처리 (회수완료) -> 교환처리 (배송진행) 변경
                    if(null != exDeliveryList && exDeliveryList.size() > 0){
                        List<String> exDeliveryClmidxArr = exDeliveryList.stream().map(a -> a.get("clmidx").toString()).collect(Collectors.toList());

                        SOMap updateParam = new SOMap();
                        updateParam.put("type", "EXCHANGE_DELIVERY");
                        updateParam.put("bf_status", CMConst.EXCHANGE_RETURN_COMPLETE);
                        updateParam.put("af_status", CMConst.EXCHANGE_IN_DELIVERY);
                        updateParam.put("clmidxarr", exDeliveryClmidxArr);
                        updateParam.put("excdlvstatname", "배송진행");

                        claimGoodsHistMapper.insertClaimGoodsHist(updateParam);
                        claimMapper.updateClaimDeliveryStatus(updateParam);
                    }
                }
            }

            List<SOMap> orderDeliveryCompleteList = new ArrayList<>();
            List<SOMap> claimDeliveryCompleteList = new ArrayList<>();
            List<SOMap> claimRtnManDeliveryCompleteList = new ArrayList<>();
            List<SOMap> claimExManDeliveryCompleteList = new ArrayList<>();

            if(completeList.size() > 0) {
                for(Map<String, Object> complete : completeList){
                    /*
                     * ODS007(배송중) -> ODS008(배송완료)
                     * EXS009(교환처리-배송진행) -> EXS010(교환처리-배송완료)
                     */
                    SOMap orderDbParam = new SOMap();
                    orderDbParam.putAll(complete);
                    orderDbParam.put("status", CMConst.ORDER_IN_DELIVERY);
                    List<SOMap> deliveryCompleteOrdList = comOrderGoodsDelivMapper.selectDeliveringList(orderDbParam);
                    if(deliveryCompleteOrdList.size() > 0){
                        orderDeliveryCompleteList.addAll(deliveryCompleteOrdList);
                    }

                    //클레임 반품에 대한 반송, 교환/배송, 교환/반송 처리 리스트
                    SOMap claimDbParam = new SOMap();
                    claimDbParam.putAll(complete);
                    claimDbParam.put("target_return_status", CMConst.RETURN_A_RETURN);
                    claimDbParam.put("target_exchange_status_arr", new String[]{CMConst.EXCHANGE_IN_DELIVERY, CMConst.EXCHANGE_A_RETURN});
                    List<SOMap> deliveryCompleteClmList = claimMapper.selectClaimDeliveringList(claimDbParam);
                    if(deliveryCompleteClmList.size() > 0){
                        for(SOMap check : deliveryCompleteClmList){
                            if(complete.get("logistType").toString().equals(check.getStr("logistype"))){
                                claimDeliveryCompleteList.add(check);
                            }
                        }
                    }
                    
                    // 클레임 개별반품에 대한 배송완료 처리 리스트
                    SOMap claimRtnManDbParam = new SOMap();
                    claimRtnManDbParam.putAll(complete);
                    claimRtnManDbParam.put("target_return_status", CMConst.RETURN_IN_DELIVERY);
                    claimRtnManDbParam.put("rtn_type", "DLT004");
                    claimRtnManDbParam.put("mantype", "RTNMANDELIVTYPE");
                    List<SOMap> deliveryCompleteClmRtnManList = claimMapper.selectClaimRtnManDeliveringList(claimRtnManDbParam);
                    if(deliveryCompleteClmRtnManList.size() > 0){
                        for(SOMap check : deliveryCompleteClmRtnManList){
                            if(complete.get("logistType").toString().equals(check.getStr("logistype"))){
                            	claimRtnManDeliveryCompleteList.add(check);
                            }
                        }
                    }
                    
                    // 클레임 개별교환에 대한 배송완료 처리 리스트
                    SOMap claimExManDbParam = new SOMap();
                    claimExManDbParam.putAll(complete);
                    claimExManDbParam.put("target_return_status", CMConst.EXCHANGE_RETURN_DELIVERY);
                    claimExManDbParam.put("rtn_type", "DLT004");
                    claimExManDbParam.put("mantype", "EXMANDELIVTYPE");
                    List<SOMap> deliveryCompleteClmExManList = claimMapper.selectClaimRtnManDeliveringList(claimExManDbParam);
                    if(deliveryCompleteClmExManList.size() > 0){
                    	for(SOMap check : deliveryCompleteClmExManList){
                    		if(complete.get("logistType").toString().equals(check.getStr("logistype"))){
                    			claimExManDeliveryCompleteList.add(check);
                    		}
                    	}
                    }
                }

                if(orderDeliveryCompleteList.size() > 0){
                    List<String> orgDelividxArr = orderDeliveryCompleteList.stream().map(a -> a.get("orgdelividx").toString()).collect(Collectors.toList());
                    List<String> invoiceNoList = orderDeliveryCompleteList.stream().map(a -> a.get("invoiceno").toString()).collect(Collectors.toList());
                    SOMap insertUpdateParam = new SOMap();
                    insertUpdateParam.put("idx", 2);
                    insertUpdateParam.put("ordstatus", CMConst.ORDER_COMPLETE_DELIV);
                    insertUpdateParam.put("userid", "SYSTEM");
                    insertUpdateParam.put("orgdelividxlist", orgDelividxArr);
                    insertUpdateParam.put("invoice_no_arr", invoiceNoList);

                    comOrderGoodsHisMapper.insertComOrderGoodsHisAll(insertUpdateParam);
                    comOrderGoodsDelivMapper.updateAdminOrderGoodsDeliv(insertUpdateParam);

                    insertUpdateParam.put("siteid", param.get("siteid"));
                    erpService.insertOrderDeliveryStateERPData(insertUpdateParam);

                    sendDeliveryMessage(insertUpdateParam);
                }

                if(claimDeliveryCompleteList.size() > 0){
                    Map<String, List<Map<String, Object>>> returnAndChangeTypeEntryMap = claimDeliveryCompleteList.stream().collect(Collectors.groupingBy(a -> a.get("type").toString()));

                    List<Map<String, Object>> exDeliveryList = returnAndChangeTypeEntryMap.get("EXCHANGE_DELIVERY");

                    if(null != exDeliveryList && exDeliveryList.size() > 0){
                        List<String> exDeliveryClmidxArr = exDeliveryList.stream().map(a -> a.get("clmidx").toString()).collect(Collectors.toList());
                        SOMap updateParam = new SOMap();
                        updateParam.put("type", "EXCHANGE_DELIVERY");
                        updateParam.put("bf_status", CMConst.EXCHANGE_IN_DELIVERY);
                        updateParam.put("af_status", CMConst.EXCHANGE_COMPLETE_DELIV);
                        updateParam.put("clmidxarr", exDeliveryClmidxArr);
                        updateParam.put("excdlvstatname", "배송완료");
                        updateParam.put("iscomplete", "T");

                        //교환처리 (배송진행) -> 교환처리 (배송완료)
                        claimGoodsHistMapper.insertClaimGoodsHist(updateParam);
                        claimMapper.updateClaimDeliveryStatus(updateParam);
                    }
                }
                
                if(claimRtnManDeliveryCompleteList.size() > 0){
                	Map<String, List<Map<String, Object>>> returnManTypeEntryMap = claimRtnManDeliveryCompleteList.stream().collect(Collectors.groupingBy(a -> a.get("type").toString()));
                	
                	List<Map<String, Object>> rtnManDeliveryList = returnManTypeEntryMap.get("RTNMANDELIVTYPE");
                	
                	if(null != rtnManDeliveryList && rtnManDeliveryList.size() > 0){
                		List<String> rtnManDeliveryClmidxArr = rtnManDeliveryList.stream().map(a -> a.get("clmidx").toString()).collect(Collectors.toList());
                		SOMap updateParam = new SOMap();
                		updateParam.put("type", "RTNMANDELIVTYPE");
                		updateParam.put("bf_status", CMConst.RETURN_IN_DELIVERY);
                		updateParam.put("af_status", CMConst.RETURN_COMPLETE_DELIVERY);
                		updateParam.put("clmidxarr", rtnManDeliveryClmidxArr);
                		updateParam.put("recstatname", "배송완료");
                		updateParam.put("iscomplete", "T");

                		//반품처리 (회수진행) -> 반품처리 (회수완료)
                		claimGoodsHistMapper.insertClaimGoodsHist(updateParam);
                		claimMapper.updateClaimDeliveryStatus(updateParam);
                	}
                }
                if(claimExManDeliveryCompleteList.size() > 0){
                	Map<String, List<Map<String, Object>>> exManTypeEntryMap = claimExManDeliveryCompleteList.stream().collect(Collectors.groupingBy(a -> a.get("type").toString()));
                	
                	List<Map<String, Object>> exManDeliveryList = exManTypeEntryMap.get("EXMANDELIVTYPE");
                	
                	if(null != exManDeliveryList && exManDeliveryList.size() > 0){
                		List<String> exManDeliveryClmidxArr = exManDeliveryList.stream().map(a -> a.get("clmidx").toString()).collect(Collectors.toList());
                		SOMap updateParam = new SOMap();
                		updateParam.put("type", "EXMANDELIVTYPE");
                		updateParam.put("bf_status", CMConst.EXCHANGE_RETURN_DELIVERY);
                		updateParam.put("af_status", CMConst.EXCHANGE_RETURN_COMPLETE);
                		updateParam.put("clmidxarr", exManDeliveryClmidxArr);
                		updateParam.put("recstatname", "배달완료");
                		updateParam.put("iscomplete", "T");
                		//교환처리 (회수진행) -> 교환처리 (회수완료)
                		claimGoodsHistMapper.insertClaimGoodsHist(updateParam);
                		claimMapper.updateClaimDeliveryStatus(updateParam);
                	}
                }
            }

            List<SOMap> orderErrorList = new ArrayList<>();
            List<SOMap> claimErrorList = new ArrayList<>();

            if(errorList.size() > 0) {
                for(Map<String, Object> error : errorList) {
                    SOMap orderDbParam = new SOMap();
                    orderDbParam.putAll(error);
                    List<SOMap> ordErrorTargetList = comOrderGoodsDelivMapper.selectDeliveringList(orderDbParam);
                    if(ordErrorTargetList.size() > 0) {
                        orderErrorList.addAll(ordErrorTargetList);
                    }

                    SOMap claimDbParam = new SOMap();
                    claimDbParam.putAll(error);
                    List<SOMap> clmErrorTargetList = claimMapper.selectClaimDeliveringListForError(claimDbParam);
                    if(clmErrorTargetList.size() > 0){
                        claimErrorList.addAll(clmErrorTargetList);
                    }
                }
            }

            if(orderErrorList.size() > 0){
                for(SOMap error : orderErrorList){
                    comOrderGoodsDelivMapper.updateDeliveryTrackingErrorMessage(error);
                }
            }

            if(claimErrorList.size() > 0){
                for(SOMap row : claimErrorList){
                    SOMap dbParam = new SOMap();
                    String errorName = row.getStr("errormsg");
                    dbParam.put("siteid", param.get("siteid"));
                    dbParam.put("clmno", row.getStr("clmno"));
                    dbParam.put("errorname", errorName);
                    dbParam.put("type", "TRACE");
                    claimMapper.updateClaimTrackingError(dbParam);
                }
            }

            List<SOMap> responseList = new ArrayList<>();
            responseList.addAll(orderDeliveringList);
            responseList.addAll(claimDeliveringList);
            responseList.addAll(orderDeliveryCompleteList);
            responseList.addAll(claimDeliveryCompleteList);
            responseList.addAll(directList);
            responseList.addAll(orderErrorList);
            responseList.addAll(claimErrorList);

            if(responseList.size() > 0){
                List<Map<String, Object>> items = new ArrayList<>();
                for(SOMap row : responseList){
                    Map<String, Object> map = new HashMap<>();
                    map.put("transUniqueCode", row.get("transuniquecode"));
                    map.put("seq", row.get("seq"));
                    items.add(map);
                }
                Map<String, Object> item = new HashMap<>();
                item.put("items", items);
                Map<String, Object> data = new HashMap<>();
                data.put("data", item);

                String goodsFlowResultResponseUrl = prop.getProperty("goods.flow.send.trace.result.response.url");
                String responseUrl = String.format("%s/%s", goodsFlowResultResponseUrl, goodsFlowMemberCode);

                Map<String, Object> sendResponse = HttpConnectionUtil.httpsRequest(responseUrl, "POST", data, header);
                if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                    Map<String, Object> sendResponseResult = new HashMap<>();
                    Map<String, Object> error = (Map<String, Object>)sendResponse.get("error");
                    sendResponseResult.put("status", error.get("status"));
                    sendResponseResult.put("message", error.get("message"));
                    sendResponseResult.put("detailMessage", error.get("detailMessage"));
                    result.put("sendResponseResult", sendResponseResult);
                } else {
                    result.putAll(sendResponse);
                }
            }
        } catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            receiveResult.put("status", "400");
            receiveResult.put("message", "배송상태 처리 중 에러가 발생 하였습니다.");
            receiveResult.put("details", e.getMessage());
            result.put("receiveResult", receiveResult);
            e.printStackTrace();
        }


        logger.info(":::::::::::::::::::::::::::::: DELIVERY TRACE END ::::::::::::::::::::::::::::::");
        logger.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        return result;
    }

    @Override
    public SOMap traceLastDlvState(List<Map<String, Object>> list) {
        String goodsFlowTraceLastdlvStateUrl = prop.getProperty("goods.flow.trace.last.dlv.state.url");
        String goodsFlowMemberCode = prop.getProperty("goods.flow.member.code");
        String traceLastdlvStateUrl = String.format("%s/%s", goodsFlowTraceLastdlvStateUrl, goodsFlowMemberCode);

        Map<String, Object> item = new HashMap<>();
        item.put("items", list);
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("data", item);

        Map<String, Object> header = getGoodsFlowHeader("zkm");
        SOMap result = new SOMap();

        Map<String, Object> traceLastStateResult = HttpConnectionUtil.httpsRequest(traceLastdlvStateUrl, "POST", sendData, header);
        if(traceLastStateResult != null && traceLastStateResult.containsKey("httpCode") && "200".equals(traceLastStateResult.get("httpCode").toString())){
            logger.info(traceLastStateResult.toString());

            Map<String, Object> error = (Map<String, Object>) traceLastStateResult.get("error");
            result.put("status", error.get("status"));
            result.put("message", error.get("message"));
            result.put("details", error.get("details"));
        } else {
            result.putAll(traceLastStateResult);
        }

        return result;
    }

    @Override
    public SOMap requestReturn(SOMap param, List<SOMap> orderList) {

        Map<String, Object> returnInfo = new HashMap<>();
        //고유번호
        returnInfo.put("transUniqueCd", param.get("trans_unique_cd"));
        //T_DEALER_DELIV 테이블에 존재하는 centerCode
        returnInfo.put("centerCode", param.get("center_code"));
        //택배사 코드 (내부 코드가 아닌 굿스플로 코드)
        String delivCode = param.getStr("deliver_code");

        if("KOREX".equalsIgnoreCase(delivCode)){
            delivCode = "CJGLS";
        } else if("LOGEN".equalsIgnoreCase(delivCode)){
            delivCode = "KGB";
        }
        returnInfo.put("deliverCode", delivCode);

        //보내는사람 이름, 우편번호, 기본주소, 상세주소
        //클레임 테이블 회수자명, 회수연락처, 회수지 우편번호(필요), 회수지주소도로명, 회수지주소상세도로명
        returnInfo.put("sndName", param.get("recname"));
        returnInfo.put("sndTel1", param.get("rectel"));
        returnInfo.put("sndZipCode", param.get("recpost"));
        returnInfo.put("sndAddr1", String.format("%s %s", param.getStr("recaddrroad"), param.getStr("recaddrdetailroad")));


        //받는사람 이름, 우편번호, 기본주소, 상세주소
        returnInfo.put("rcvName", param.get("dealername"));
        returnInfo.put("rcvZipCode", param.get("rfpost"));
        returnInfo.put("rcvAddr1", String.format("%s %s", param.get("rfaddr"), param.get("rfaddrdetail")));
        returnInfo.put("rcvTel1", param.get("rcvtel"));

        //딜러넘버로 생성되는걸로 파악
        returnInfo.put("mallId", param.get("dealerno"));

        // status : 원송장 번호 입력여부 (로젠, 편의점 택배만 가능)
        // sheetNo : 배송시 전달한 운송장번호 (로젠, 편의점 택배만 가능)
        // 로젠택배의 경우 원송장 번호를 전송해야 한다.(James, 2022-12-13)
        if("KGB".equalsIgnoreCase(delivCode)) {
        	// orgDeliverCode : [[원택배사코드]] *반품건의 원배송 택배사코드 (이값도 전송해야 하나 필수가 아니므로 생략)
        	/**
        	 * 원송장번호 가져오기
        	 */
        	SOMap orgInvoiceNo = claimMapper.selectOrgInvoiceNoByClaimNo(param);
        	returnInfo.put("status", "O");
            returnInfo.put("sheetNo", orgInvoiceNo.getDbStr("invoiceno"));
        } else {
        	returnInfo.put("status", "N");
            returnInfo.put("sheetNo", ".");
        }
        //선불 : SH , 착불 : BH
        returnInfo.put("paymentTypeCode", param.get("paymenttypecode"));

        List<Map<String, Object>> orderItems = new ArrayList<>();
        for(SOMap order : orderList){
            Map<String, Object> item = new HashMap<>();
            item.put("uniqueCd", param.get("trans_unique_cd")); //결과보고 단위
            item.put("ordNo", order.get("ordno")); //주문번호
            item.put("itemName", order.get("goodsname")); //상품명
            item.put("itemQty", order.get("clmcnt")); //상품수량
            item.put("ordDate", order.get("orderdate")); //주무일자
            item.put("defCode1", order.get("clmgdidx")); //클레임 IDX
            orderItems.add(item);
        }

        returnInfo.put("orderItems", orderItems);

        List<Map<String, Object>> items = new ArrayList<>();
        items.add(returnInfo);
        Map<String, Object> item = new HashMap<>();
        item.put("items", items);
        Map<String, Object> data = new HashMap<>();
        data.put("data", item);

        String goodsFlowDeliveryReturnUrl = prop.getProperty("goods.flow.delivery.return.url");
        String url = String.format("%s/%s", goodsFlowDeliveryReturnUrl, param.getStr("dealerno"));

        Map<String, Object> header = getGoodsFlowHeader("scm");

        SOMap result = new SOMap();
        Map<String, Object> sendResult = HttpConnectionUtil.httpsRequest(url, "POST", data, header);
        if(sendResult != null && sendResult.containsKey("httpCode") && "200".equals(sendResult.get("httpCode").toString())){
            Map<String, Object> error = (HashMap<String, Object>)sendResult.get("error");
            result.put("status", error.get("status"));
            result.put("message", error.get("message"));
            result.put("details", error.get("details"));
        } else {
            result.putAll(sendResult);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap receiveTransResult(SOMap param) throws Exception {

        logger.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        logger.info("::::::::::::::::::::::::::::: DELIVERY TRANS START :::::::::::::::::::::::::::::");

        String goodsFlowMemberCode = prop.getProperty("goods.flow.member.code");
        String goodsFlowReceiveTraceResultUrl = prop.getProperty("goods.flow.receive.trans.result.url");
        String url = String.format("%s/%s", goodsFlowReceiveTraceResultUrl, goodsFlowMemberCode);

        Map<String, Object> header = getGoodsFlowHeader("zkm");

        //주석처리하면 안됨. temp 의미없는 데이터가 아님.
        //공통으로 사용하는 httpRequest 메소드에서 POST 요청시 BODY가 없는 경우 임시 처리용도로 데이터를 발송
        SOMap result = new SOMap();
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("temp", "temp");

        List<Map<String, Object>> transList;
        List<Map<String, Object>> flatList;
        List<Map<String, Object>> beforePickUpList = new ArrayList<>();
        List<Map<String, Object>> pickUpList = new ArrayList<>();
        List<Map<String, Object>> completeList = new ArrayList<>();
        List<Map<String, Object>> errorList = new ArrayList<>();

        Map<String, Object> receiveResult = new HashMap<>();
        List<Map<String, Object>> completeClaimList = new ArrayList<>();

        Map<String, Object> sendResult = HttpConnectionUtil.httpsRequest(url, "POST", sendData, header);
        if(sendResult != null && sendResult.containsKey("httpCode") && "200".equals(sendResult.get("httpCode").toString())){
            Map<String, Object> error = (Map<String, Object>)sendResult.get("error");
            Map<String, Object> data = (Map<String, Object>)sendResult.get("data");
            if("200".equals(error.get("status").toString()) && data != null){

                JSONObject json = new JSONObject();
                json.putAll(data);

                SOMap exLogParam = new SOMap();
                exLogParam.put("iflog", json.toString());
                exLogParam.put("exlogtype", CMConst.EX_IF_LOG_DELIVERY_RETURN);
                exIfLogMapper.insertExIfLog(exLogParam);

                transList = (ArrayList) data.get("items");
//                //중복제거처리 하기 위해 작업(운송장에 같은 배송상태가 전달되는 것을 방지)
                flatList = transList.stream().map(a-> {
                    Map<String, Object> row = new HashMap<>();
                    String logistics = (a.containsKey("logisticsCode")) ? a.get("logisticsCode").toString() : "";
                    if("CJGLS".equalsIgnoreCase(logistics)){
                        logistics = "KOREX";
                    } else if("KGB".equalsIgnoreCase(logistics)){
                        logistics = "LOGEN";
                    }

                    row.put("itemUniqueCode", a.get("itemUniqueCode"));
                    row.put("seq", a.get("seq"));
                    row.put("logisticscode", logistics);
                    row.put("logisttype", getLogisTypeCode(logistics.toLowerCase()));
                    row.put("recinvoiceno", a.get("invoiceNo"));
                    row.put("dlvStatType", a.get("dlvStatType"));
                    row.put("errorName", a.get("errorName"));
                    row.put("clmgdidx", a.get("defCode1"));

                    return row;
                }).distinct().collect(Collectors.toList());
//
                beforePickUpList = flatList.stream().filter(a -> "27".equals(a.get("dlvStatType").toString())).collect(Collectors.toList());
                pickUpList = flatList.stream().filter(a -> "30".equals(a.get("dlvStatType").toString())).collect(Collectors.toList());
                completeList = flatList.stream().filter(a -> "70".equals(a.get("dlvStatType").toString())).collect(Collectors.toList());
                errorList = flatList.stream().filter(a -> "99".equals(a.get("dlvStatType").toString())).collect(Collectors.toList());
            }
            receiveResult.put("status", error.get("status"));
            receiveResult.put("message", error.get("message"));
            receiveResult.put("details", error.get("details"));
            result.put("receiveResult", receiveResult);
        } else {
            result.putAll(sendResult);
        }

        try {
            //집하예정 처리 (운송장번호 업데이트 처리 함)
            if(beforePickUpList.size() > 0){
                for(Map<String, Object> row : beforePickUpList){
                    SOMap claimParam = new SOMap();
                    claimParam.putAll(row);
                    claimParam.put("siteid", param.get("siteid"));
                    claimParam.put("recstatname", "집하예정");
                    int invoiceUpdateResult = claimMapper.updateClaimDeliveryInvoice(claimParam);
                    logger.info("RETURN AND CHANGE INVOICE UPDATE CLMGDIDX ::: " + claimParam.get("clmgdidx") + ", UPDATE CNT ::: " + invoiceUpdateResult);
                }
                completeClaimList.addAll(beforePickUpList);
            }

            //반품처리(승인완료) -> 반품처리(회수진행)
            //교환처리(승인완료) -> 교환처리(회수진행)
            if(pickUpList.size() > 0){
                String[] clmgdIdxArr = pickUpList.stream().map(a-> a.get("clmgdidx").toString()).toArray(String[]::new);
                SOMap claimParam = new SOMap();
                claimParam.put("siteid", param.get("siteid"));
                claimParam.put("clmgd_idx_arr", clmgdIdxArr);
                claimParam.put("rtnstatus", CMConst.RETURN_APPROVAL_COMPLETE);
                claimParam.put("excstatus", CMConst.EXCHANGE_APPROVAL_COMPLETE);
                claimParam.put("after_rtnstatus", CMConst.RETURN_IN_DELIVERY);
                claimParam.put("after_excstatus", CMConst.EXCHANGE_RETURN_DELIVERY);
                claimParam.put("recstatname", "집하");
                int updateClaimStatusCnt = claimMapper.updateClaimStatusNonCancel(claimParam);
                logger.info("RETURN AND CHANGE STATUS UPDATE PICKUP CNT ::: " + updateClaimStatusCnt);
                int insertClaimGoodsHisCnt = claimGoodsHistMapper.insertClaimGoodsHisForDelivery(claimParam);
                logger.info("RETURN AND CHANGE HIST INSERT PICKUP CNT ::: " + insertClaimGoodsHisCnt);
                completeClaimList.addAll(pickUpList);
            }
            //반품처리(회수진행) -> 반품처리(회수완료)
            //교환처리(회수진행) -> 교환처리(회수완료)
            if(completeList.size() > 0){
                String[] clmgdIdxArr = completeList.stream().map(a-> a.get("clmgdidx").toString()).toArray(String[]::new);
                SOMap claimParam = new SOMap();
                claimParam.put("siteid", param.get("siteid"));
                claimParam.put("clmgd_idx_arr", clmgdIdxArr);
                claimParam.put("rtnstatus", CMConst.RETURN_IN_DELIVERY);
                claimParam.put("excstatus", CMConst.EXCHANGE_RETURN_DELIVERY);
                claimParam.put("after_rtnstatus", CMConst.RETURN_COMPLETE_DELIVERY);
                claimParam.put("after_excstatus", CMConst.EXCHANGE_RETURN_COMPLETE);
                claimParam.put("recstatname", "배송완료");
                int updateClaimStatusCnt = claimMapper.updateClaimStatusNonCancel(claimParam);
                logger.info("RETURN AND CHANGE STATUS UPDATE PICKUP CNT ::: " + updateClaimStatusCnt);
                int insertClaimGoodsHisCnt = claimGoodsHistMapper.insertClaimGoodsHisForDelivery(claimParam);
                logger.info("RETURN AND CHANGE HIST INSERT PICKUP CNT ::: " + insertClaimGoodsHisCnt);
                completeClaimList.addAll(completeList);
            }

            if(errorList.size() > 0){
                for(Map<String, Object> error : errorList) {

                    SOMap claimDbParam = new SOMap();
                    claimDbParam.putAll(error);
                    List<SOMap> clmErrorTargetList = claimMapper.selectClaimReturnListForError(claimDbParam);
                    if(clmErrorTargetList.size() > 0){

                        for(SOMap row : clmErrorTargetList){
                            SOMap dbParam = new SOMap();
                            String errorName = row.getStr("errormsg");
                            dbParam.put("siteid", param.get("siteid"));
                            dbParam.put("clmno", row.getStr("clmno"));
                            dbParam.put("errorname", errorName);
                            dbParam.put("type", "TRANS");
                            claimMapper.updateClaimTrackingError(dbParam);

                        }
//                        completeClaimList.addAll(errorList);
                    }
                }

            }

            if(completeClaimList.size() > 0){
                List<Map<String, Object>> items = new ArrayList<>();
                for(Map<String, Object> row : completeClaimList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("itemUniqueCode", row.get("itemUniqueCode"));
                    map.put("seq", row.get("seq"));
                    items.add(map);
                }
                Map<String, Object> item = new HashMap<>();
                item.put("items", items);
                Map<String, Object> data = new HashMap<>();
                data.put("data", item);
                String goodsFlowResultResponseUrl = prop.getProperty("goods.flow.send.trans.result.response.url");
                String responseUrl = String.format("%s/%s", goodsFlowResultResponseUrl, goodsFlowMemberCode);

                Map<String, Object> sendResponse = HttpConnectionUtil.httpsRequest(responseUrl, "POST", data, header);
                if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                    Map<String, Object> sendResponseResult = new HashMap<>();

                    Map<String, Object> error = (Map<String, Object>)sendResponse.get("error");
                    sendResponseResult.put("status", error.get("status"));
                    sendResponseResult.put("message", error.get("message"));
                    sendResponseResult.put("detailMessage", error.get("detailMessage"));
                    result.put("sendResponseResult", sendResponseResult);
                } else {
                    result.putAll(sendResponse);
                }
            }
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            receiveResult.put("status", "400");
            receiveResult.put("message", "회수(교환/반품)상태 처리 중 에러가 발생 하였습니다.");
            receiveResult.put("details", e.getMessage());
            result.put("receiveResult", receiveResult);
            e.printStackTrace();
        }


        logger.info(":::::::::::::::::::::::::::::: DELIVERY TRANS END ::::::::::::::::::::::::::::::");
        logger.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        return result;
    }

    public Map<String, Object> getGoodsFlowHeader(String type){
        String goodsFlowApiKey = "zkm".equals(type) ? prop.getProperty("goods.flow.api.zkm.key") : prop.getProperty("goods.flow.api.scm.key");

        Map<String, Object> header = new HashMap<>();
        header.put("goodsFLOW-Api-Key", goodsFlowApiKey);
        header.put("Content-type", "application/json;charset=utf-8");
        return header;
    }

	@Override
	public SOMap getDelivTrackingUrl(SOMap params) throws Exception {
		SOMap result = new SOMap();
		String goodsFlowWhereisUrl = prop.getProperty("goods.flow.whereis.url");
		String goodsFlowMemberCode = prop.getProperty("goods.flow.member.code");
		String goodsFlowLogisCode = getGoodsFlowLogisCode(params.getStr("logistype"));
		String url = String.format("%s/%s/%s/%s", goodsFlowWhereisUrl, goodsFlowMemberCode, goodsFlowLogisCode, params.getStr("invoiceno"));
		result.put("url", url);

		return result;
	}

	@Override
	public String getGoodsFlowLogisCode(String logistype){
		SOMap params = new SOMap();
		params.put("cmclass", CMConst.CM_CLASS_LOGISTYPE);
		List<SOMap> logisList = codeMapper.selectCodeDtlList(params);
		SOMap logisMap = logisList.stream().filter(logis -> logis.getStr("cmcode").equals(logistype)).findAny().get();
		String[] logisCodeArr = StringUtil.isEmpty(logisMap.getStr("detail"))? new String[] {logistype} : logisMap.getStr("detail").split(",");
		return logisCodeArr[0];
	}

    @Override
    public SOMap getPartnersOTPCode(SOMap param) throws Exception {
        SOMap result = new SOMap();
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("temp", "temp");

        String otpUrl = prop.getProperty("goods.flow.request.otp.code.url");
        String url = String.format("%s/%s", otpUrl, param.get("userno"));

        Map<String, Object> httpResult = HttpConnectionUtil.httpsRequest(url, "POST", sendData, getGoodsFlowHeader("scm"));
        if(httpResult != null && httpResult.containsKey("httpCode") && "200".equals(httpResult.get("httpCode").toString())){
            String otpCode = httpResult.get("data").toString();
            String serviceApplyUrl = String.format("%s?OTP=%s", prop.getProperty("goods.flow.apply.delivery.service.url"), otpCode);

            Map<String, Object> error = (Map<String, Object>) httpResult.get("error");
            result.put("url", serviceApplyUrl);
            result.put("status", error.get("status"));
            result.put("message", error.get("message"));
            result.put("details", error.get("details"));

            logger.info(httpResult.toString());
        } else {
            result.putAll(httpResult);
        }

        return result;
    }

    @Override
    public SOMap requestApplyServiceUsageResult(SOMap param) throws Exception {
        SOMap result = new SOMap();
        /*
         * 서비스 이용신청 결과를 매일 배치가 돌면서 처리 됨
         * 결과가 나온 서비스에 대해서는 T_DEALER_DELIV 테이블에 저장됨.
         * 해당 데이터를 기반으로 반품택배 신청이 가능함.
         */
        String otpUrl = prop.getProperty("goods.flow.delivery.service.result.url");
        String url = String.format("%s/%s/%s", otpUrl, DateTimeUtil.addDays(DateTimeUtil.getNowDatePartShortStr(), -1, "yyyyMMdd"), DateTimeUtil.getNowDatePartShortStr());

        Map<String, Object> httpResult = HttpConnectionUtil.httpsRequest(url, "GET", null, getGoodsFlowHeader("scm"));
        if(httpResult != null && httpResult.containsKey("httpCode") && "200".equals(httpResult.get("httpCode").toString())){
            Map<String, Object> data = (Map<String, Object>) httpResult.get("data");
            List<Map<String, Object>> resultList = (List<Map<String, Object>>) data.get("items");

            String partnersContractUrl = prop.getProperty("goods.flow.partners.contract.info.url");

            if(resultList.size() > 0){

                List<String> flatList = resultList.stream().map(a-> a.get("partnerCode").toString()).distinct().collect(Collectors.toList());

                for(String partnersKey : flatList){
                    String inUrl = String.format("%s/%s", partnersContractUrl, partnersKey);
                    //파트너사 계약 정보 굿스플로에 조회
                    Map<String, Object> partnersContractInfoRequest = HttpConnectionUtil.httpsRequest(inUrl, "GET", null, getGoodsFlowHeader("scm"));

                    if(partnersContractInfoRequest != null && partnersContractInfoRequest.containsKey("httpCode") && "200".equals(partnersContractInfoRequest.get("httpCode").toString())){
                        Map<String, Object> inData = (Map<String, Object>) partnersContractInfoRequest.get("data");
                        List<Map<String, Object>> inResultList = (List<Map<String, Object>>) inData.get("items");
                        int insertUpdateCnt = 0;
                        for(Map<String, Object> row : inResultList){
                            if("Y".equals(row.get("verifiedResult").toString())){
                                SOMap dbParam = new SOMap();
                                dbParam.put("siteid", param.get("siteid"));
                                dbParam.put("userno", row.get("partnerCode"));
                                dbParam.put("deliverycode", row.get("deliverCode"));
                                dbParam.put("locontcode", row.get("contractNo"));
                                dbParam.put("isvalid", row.get("verifiedResult"));
                                dbParam.put("centercode", row.get("centerCode"));

                                String deliveryCode = (row.containsKey("deliverCode")) ? row.get("deliverCode").toString() : "";
                                //CJ대한통운은 CJGLS, KOREX가 있지만 모두 KOREX로 통일 (코드값때문)
                                if(!"".equals(deliveryCode) && deliveryCode.equals("CJGLS")){
                                    dbParam.put("deliverycode", "KOREX");
                                } else if(!"".equals(deliveryCode) && deliveryCode.equals("KGB")){
                                    dbParam.put("deliverycode", "LOGEN");
                                }

                                SOMap partnersDeliv = dealerDelivMapper.selectPartnersDelivery(dbParam);

                                if(partnersDeliv == null){
                                    insertUpdateCnt += dealerDelivMapper.insertDealerDeliv(dbParam);
                                } else {
                                    insertUpdateCnt += dealerDelivMapper.updateDealerDeliv(dbParam);
                                }
                            }
                        }
                        logger.info("DEALER DELIVERY INSERT AND UPDATE COUNT ::: " + insertUpdateCnt);
                    }
                }
            }

            Map<String, Object> error = (Map<String, Object>) httpResult.get("error");
            result.put("status", error.get("status"));
            result.put("message", error.get("message"));
            result.put("details", error.get("details"));
        } else {
            result.putAll(httpResult);
        }

        return result;
    }

    @Override
    public SOMap getDeliveryTrackingData(SOMap param) {
        SOMap result = new SOMap();
        String goodsFlowDeliveryTrackingUrl = prop.getProperty("goods.flow.delivery.tracking.url");
        String goodsFlowMemberCode = prop.getProperty("goods.flow.member.code");
        String trackingUrl = String.format("%s/%s/%s/%s", goodsFlowDeliveryTrackingUrl, goodsFlowMemberCode, param.getStr("logistype"), param.getStr("invoiceno"));

        Map<String, Object> header = getGoodsFlowHeader("zkm");
        Map<String, Object> trackingResult = HttpConnectionUtil.httpsRequest(trackingUrl, "GET", null, header);

        if(trackingResult != null && trackingResult.containsKey("httpCode") && "200".equals(trackingResult.get("httpCode").toString())){
            try {
                Map<String, Object> data = (Map<String, Object>) trackingResult.get("data");
                result.putAll(data);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }

    //KAKAO 배송관련 메세지 전송
    private void sendDeliveryMessage(SOMap param) {
        int idx = param.getInt("idx");

        if(idx == 1 || idx == 2){
            param.put("target_ord_status", (idx == 1) ? CMConst.ORDER_IN_DELIVERY : CMConst.ORDER_COMPLETE_DELIV);
            List<SOMap> list = comOrderMapper.selectDeliverySendTargetByInvoice(param);
            for(SOMap row : list){
                if(idx == 1){
                    cjMessageService.sendShippingOut(row);
                } else{
                    cjMessageService.sendDeliveryComplete(row);
                }
            }
        }
    }

    private String getLogisTypeCode(String logisticsCode){
        String logisType = "";
//        switch (logisticsCode) {
//            case "epost" : logisType = "LGT002"; break;
//            case "hanjin" : logisType = "LGT003"; break;
//            case "korex" :
//            case "cjgls" :
//                logisType = "LGT001"; break;
//            case "hyundai" :
//            case "lotte" :
//                logisType = "LGT004"; break;
//            case "logen" :
//            case "kgb" :
//                logisType = "LGT005"; break;
//            case "kdexp" : logisType = "LGT006"; break;
//            case "daesin" : logisType = "LGT007"; break;
//        }
        logisType = dealerDelivMapper.selectDeliveryCode(logisticsCode);

        return logisType;
    }
}
