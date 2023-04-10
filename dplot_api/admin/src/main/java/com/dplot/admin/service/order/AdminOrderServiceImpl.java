package com.dplot.admin.service.order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.CommonOrderService;
import com.dplot.common.service.DeliveryTrackingService;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.ClaimCouponMapper;
import com.dplot.mapper.ClaimGoodsGiftMapper;
import com.dplot.mapper.ClaimGoodsMapper;
import com.dplot.mapper.ClaimMapper;
import com.dplot.mapper.ComOrderGoodsDelivMapper;
import com.dplot.mapper.ComOrderGoodsHisMapper;
import com.dplot.mapper.ComOrderGoodsMapper;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.ComOrderMemoMapper;
import com.dplot.mapper.InquiryMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @discription	: 주문관리 ServiceImpl
 * @fileName	: AdminOrderServiceImpl.java
 * @author		: JSK
 * @date		: 2022.02.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.02.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
/**
 * @discription	: 
 * @fileName	: AdminOrderServiceImpl.java
 * @author		: JSK
 * @date		: 2022.05.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.05.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminOrderServiceImpl extends MallBaseService implements AdminOrderService {
    private static final Logger logger = LoggerFactory.getLogger(AdminOrderServiceImpl.class);

    @Autowired
    private ComOrderMapper comOrderMapper;
    @Autowired
    private ComOrderGoodsMapper comOrderGoodsMapper;
    @Autowired
    private ComOrderMemoMapper comOrderMemoMapper;
    @Autowired
    private ClaimGoodsGiftMapper claimGoodsGiftMapper;
    @Autowired
    private ComOrderGoodsDelivMapper comOrderGoodsDelivMapper;
    @Autowired
    private ComOrderGoodsHisMapper comOrderGoodsHisMapper;
    @Autowired
    private ClaimMapper claimMapper;
	@Autowired
	private ClaimGoodsMapper claimGoodsMapper;
    @Autowired
    private ClaimCouponMapper claimCouponMapper;
    @Autowired
    private InquiryMapper inquiryMapper;
    @Autowired
    private DeliveryTrackingService deliveryTrackingService;
    @Autowired
    private CJMessageService cjMessageService;
    @Autowired
    private ERPService erpService;
    @Autowired
    private CommonOrderService commonOrderService;
    
    @Resource(name="propertiesFactory")
    private Properties prop;
    
    /**
     * 전체 주문, 클레임 목록 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectOrderClaimList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        params.put("siteid", cs.getStr("siteid"));
        List<SOMap> resultList = comOrderMapper.selectAdminOrderClaimList(params);
        SOMap count = comOrderMapper.selectAdminOrderClaimListCount(params);
        
        result.put("list", resultList);
        result.put("count", count);
        return result;
    }
    
    /**
     * 전체 주문, 클레임 목록 조회(엑셀용)
     * @param params
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectOrderClaimListForExcel(SOMap params) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        params.put("siteid", cs.getStr("siteid"));
		resultList = comOrderMapper.selectAdminOrderClaimListForExcel(params);
		return resultList;
    }
    
    /**
     * 전체 주문, 클레임 목록 조회(엑셀용)
     * @param params
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectOrderClaimListForExcelNew(SOMap params) throws Exception {
    	List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    	params.put("siteid", cs.getStr("siteid"));
    	resultList = comOrderMapper.selectAdminOrderClaimListForExcelNew(params);
    	return resultList;
    }
    
    /**
     * 상태별 주문목록 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectOrderList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        params.put("siteid", cs.getStr("siteid"));
        List<SOMap> resultList = comOrderMapper.selectAdminOrderList(params);
        SOMap count = comOrderMapper.selectAdminOrderListCount(params);
        result.put("list", resultList);
        result.put("count", count);
        return result;
    }
    
    /**
     * 주문 목록 조회(엑셀용)
     * @param params
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectOrderListForExcel(SOMap params) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        params.put("siteid", cs.getStr("siteid"));
		resultList = comOrderMapper.selectAdminOrderListForExcel(params);
		return resultList;
    }
    
    /**
     * 주문상태변경이력 목록 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectOrderStatusHistList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        List<SOMap> resultList = comOrderGoodsHisMapper.selectOrderStatusHistList(params);
        result.put("list", resultList);
        return result;
    }
    
    /**
     * 주문배송정보 목록조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectOrderInvoiceList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        List<SOMap> resultList = comOrderGoodsDelivMapper.selectOrderInvoiceList(params);
        result.put("list", resultList);
        return result;
    }
    
    /**
     * 주문상태 갱신
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int updateOrderStatus(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
        	if (Util.isEmpty(params.get("dealerno"))) {
        		params.put("userid", getAdminId());
        	} else if (isDealer()) {
        		params.put("userid", getDealerId());
        	}
			// 주문상태 갱신
			List<SOMap> orderDelivList = Util.convertToListSOMap(params.getArrayList("orderdelivlist"));
			if (orderDelivList != null && orderDelivList.size() > 0) {
				for (SOMap orderDelivMap : orderDelivList) {
					// 구매확정인 경우 공통 서비스 호출
					if(Util.equal(params.getStr("ordstatus"), CMConst.ORDER_PURCH_CONFIRM)) {
						int inDeilvCnt = comOrderGoodsDelivMapper.selectInDelivCntByOrdgdidx(orderDelivMap);
						if (inDeilvCnt > 0) {
							throw new CustomException("추가된 송장중 배송중인 상품이 존재하여 구매확정 처리가 불가합니다.");
						}
						orderDelivMap.put("ordcfmtype", CMConst.ORD_CFM_ADMIN);
						commonOrderService.confirmOrder(orderDelivMap);
					} 
					// 배송완료
					else if (Util.equal(params.getStr("ordstatus"), CMConst.ORDER_COMPLETE_DELIV)) {
						orderDelivMap.put("userid", params.get("userid"));
						orderDelivMap.put("ordstatus", params.get("ordstatus"));
						List<SOMap> sameInvoiceMap = comOrderGoodsDelivMapper.selectSameInvoiceInfo(orderDelivMap);
						List<String> orgdelividxlist = sameInvoiceMap.stream().map(m->m.getStr("orgdelividx")).distinct().collect(Collectors.toList());
						orderDelivMap.put("orgdelividxlist", orgdelividxlist);
						
						// 주문상태 변경이력 추가
						comOrderGoodsHisMapper.insertComOrderGoodsHisAll(orderDelivMap);
						resultCnt = comOrderGoodsDelivMapper.updateAdminOrderGoodsDeliv(orderDelivMap);

						// 직배송 배송완료 Message
						SOMap msgParam = new SOMap();
						msgParam.put("ordstatus", params.getStr("ordstatus"));
						if (Util.equal(orderDelivMap.getStr("delivtype"), CMConst.DELIV_DIRECT)) {
							msgParam.put("orgdelividx", orderDelivMap.get("orgdelividx"));
				            List<SOMap> msgTargetList = comOrderMapper.selectCompDelivMsgTargetByInvoice(msgParam);
							for (SOMap msgTarget : msgTargetList) {
			                    cjMessageService.sendDeliveryComplete(msgTarget);
							}
						}
					}
					// 이외
					else {
						orderDelivMap.put("userid", params.get("userid"));
						orderDelivMap.put("isrollback", params.get("isrollback"));
						orderDelivMap.put("ordstatus", params.get("ordstatus"));
						
			        	// 배송중비중->상품준비중으로 변경시 추가송장 등록여부 체크
						if (Util.equal(orderDelivMap.getStr("ordstatus"), CMConst.ORDER_PREPARING_GOODS)) {
							int invoicecnt = comOrderGoodsDelivMapper.selectOrderInvoiceCount(orderDelivMap);
							if (invoicecnt > 1) {
								throw new CustomException("추가등록된 송장이 존재합니다. 삭제후 진행해주시기 바랍니다.");
							}
						}
						// 주문상태 변경이력 추가
						comOrderGoodsHisMapper.insertComOrderGoodsHisAll(orderDelivMap);
						// 주문상태 변경
						if (Util.equal(orderDelivMap.getStr("ordstatus"), CMConst.ORDER_PREPARING_GOODS)
								|| Util.equal(orderDelivMap.getStr("ordstatus"), CMConst.ORDER_PREPARING_DELIV)) {
							orderDelivMap.put("isupdateordcnt", "T");
						}
						resultCnt = comOrderGoodsDelivMapper.updateAdminOrderGoodsDeliv(orderDelivMap);
					}
				}

	            // 직배송상품 배송준비중으로 변경시 KAKAO 메세지 전송
				if (Util.equal(params.getStr("ordstatus"), CMConst.ORDER_IN_DELIVERY)) {
					Map<String, List<SOMap>> orderGoodList = orderDelivList.stream().collect(Collectors.groupingBy(item -> item.get("ordgdidx").toString()));
					for (String ordgdidx : orderGoodList.keySet()) {
						List<SOMap> goodsList = orderGoodList.get(ordgdidx);
						SOMap orderInfo = goodsList.get(0);
						if(Util.equal(orderInfo.getStr("delivtype"), CMConst.DELIV_DIRECT)) {
				            SOMap msgParams = new SOMap();
				            String frontOrderDetailUrl = String.format("%s/%s/%s", prop.getProperty("front.mobile.domain"), "mypage/order/detail", orderInfo.get("ordno"));
				            msgParams.put("number", orderInfo.getStr("ordtel").replaceAll("-", ""));
				            msgParams.put("ordno", orderInfo.getStr("ordno"));
				            msgParams.put("orderdate", orderInfo.getStr("orderday").replaceAll("-", "."));
				            msgParams.put("goodsname", orderInfo.getStr("goodsname"));
				            msgParams.put("ordcnt", goodsList.size()-1);
				            msgParams.put("addr", String.format("%s", orderInfo.getStr("rcvaddrroad")));
//				            msgParams.put("addr", String.format("%s %s", orderInfo.getStr("rcvaddr"), orderInfo.getStr("rcvaddrdetail")));
				            msgParams.put("url", frontOrderDetailUrl);
				            msgParams.put("userno", orderInfo.getStr("orduserno"));
				            cjMessageService.sendDirectDelivery(msgParams);
						}
					}
				}

				// 택배배송 배송완료 Message
				if (Util.equal(params.getStr("ordstatus"), CMConst.ORDER_COMPLETE_DELIV)) {
					Map<String, List<SOMap>> invoiceGroupingMap = orderDelivList.stream().collect(Collectors.groupingBy(item -> item.getStr("invoiceno")));
					for (String key : invoiceGroupingMap.keySet()) {
						SOMap msgParam = new SOMap();
						msgParam.put("ordstatus", params.getStr("ordstatus"));
						if (!Util.isEmpty(key)) {
							msgParam.put("invoiceno", key);
				            List<SOMap> msgTargetList = comOrderMapper.selectCompDelivMsgTargetByInvoice(msgParam);
							for (SOMap msgTarget : msgTargetList) {
			                    cjMessageService.sendDeliveryComplete(msgTarget);
							}
						}
					}
				}
				
				// 배송중, 배송완료 변경시 ERP LOG INSERT Param Setting
				if (Util.equal(params.getStr("ordstatus"), CMConst.ORDER_IN_DELIVERY) || Util.equal(params.getStr("ordstatus"), CMConst.ORDER_COMPLETE_DELIV)) {
					SOMap ifLogMap = new SOMap();
					List<String> ifLogOrgdelividxList = new ArrayList<String>();
					ifLogOrgdelividxList = orderDelivList.stream().map(m -> m.getStr("orgdelividx")).collect(Collectors.toList());
					ifLogMap.put("siteid", cs.getStr("siteid"));
					ifLogMap.put("orgdelividxlist", ifLogOrgdelividxList);
					erpService.insertOrderDeliveryStateERPData(ifLogMap);
				}
			}
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("주문상태 변경시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
    
    /**
     * 일괄출고 (배송추적요청)
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int saveOrderInvoiceAll(SOMap params) throws Exception {
    	List<SOMap> resultList = new ArrayList<>();
		
        try {
            params.put("siteid", cs.getStr("siteid"));
            
        	ObjectMapper mapper = new ObjectMapper();
			List<SOMap> orderDelivList = mapper.convertValue(params.getArrayList("orderdelivlist"), new TypeReference<List<SOMap>>(){});

			// 주문상품별 Grouping처리
			List<SOMap> orderGoodsList = this._groupGoodsOption(orderDelivList);

			// 배송수량 체크
        	for (SOMap orderGoods : orderGoodsList) {
				List<SOMap> delivList = orderGoods.getArrayList("delivlist");
				int frstordcnt = orderGoods.getInt("frstordcnt");
				int totdelivcnt = 0;
				for (SOMap delivMap : delivList) {
            		int delivcnt = delivMap.getInt("delivcnt");
					if (frstordcnt < delivcnt) {
                    	throw new CustomException("배송수량이 주문수량 이상입니다. 확인후 진행해주세요.");
                    }
					totdelivcnt += delivcnt;
            		SOMap dbParams = new SOMap();
            		dbParams.putAll(delivMap);
            		dbParams.put("istracking", "T");
            		dbParams.put("ordcnt", delivcnt);
            		comOrderGoodsDelivMapper.updateAdminOrderGoodsDeliv(dbParams);
				}
                if (frstordcnt < totdelivcnt) {
                	throw new CustomException("총 배송수량이 주문수량 이상입니다. 확인후 진행해주세요.");
                }
                if (frstordcnt != totdelivcnt) {
                	throw new CustomException("총 배송수량이 주문수량과 다릅니다. 확인후 진행해주세요.");
                }
			}
                
            List<SOMap> sendList = new ArrayList<SOMap>();
            // 택배사별 그룹핑
            Map<String, List<SOMap>> logisGroupingMap = orderDelivList.stream().collect(Collectors.groupingBy(item -> item.get("logistype").toString()));
    		for(String key : logisGroupingMap.keySet()){
    			// 송장번호별 그룹핑
    			Map<String, List<SOMap>> invoiceGroupingMap = logisGroupingMap.get(key).stream().collect(Collectors.groupingBy(item -> item.getStr("invoiceno")));
    			for (String key2 : invoiceGroupingMap.keySet()) {
        			SOMap tempMap = new SOMap();
        			tempMap.put("logistype", key);
        			tempMap.put("invoiceno", key2);
        			tempMap.put("orderlist", invoiceGroupingMap.get(key2));
        			sendList.add(tempMap);
    			}
    		}
    		
    		// 배송추적요청
        	SOMap result = new SOMap();
            for (SOMap sendMap : sendList) {
            	List<SOMap> orderList = sendMap.getArrayList("orderlist");
            	if (orderList != null && orderList.size() > 0) {
            		for (int i=0; i<orderList.size()-1; i++) {
            			SOMap preOrdMap = orderList.get(i);
            			for (int j=i+1; j<orderList.size(); j++) {
            				SOMap aftOrdMap = orderList.get(j);
                			if (!Util.equal(preOrdMap.getStr("rcvname"), aftOrdMap.getStr("rcvname"))) {
                	            throw new CustomException("같은 송장정보에 수령인이 다른 주문내역이 존재합니다.(송장번호: "+ sendMap.getStr("invoiceno") +")");
                			}
                			if (!Util.equal(preOrdMap.getStr("rcvaddr"), aftOrdMap.getStr("rcvaddr"))) {
                	            throw new CustomException("같은 송장정보에 수령인주소가 다른 주문내역이 존재합니다.(송장번호: "+ sendMap.getStr("invoiceno") +")");
                			}
                		}
            		}
            		int[] idx = { 0 };
            		List<SOMap> sendOrderList = new ArrayList<SOMap>();
            		for(SOMap orderMap : orderList) {
            			SOMap dbparam = new SOMap();
            			dbparam.put("order_no", orderMap.get("ordno"));
            			dbparam.put("order_line", ++idx[0]);
            			dbparam.put("item_name", orderMap.get("goodsname"));
            			dbparam.put("item_qty", orderMap.get("delivcnt"));
            	        if (Util.equal(prop.getProperty("Globals.Profile"), "prod")) {
                			dbparam.put("order_date", DateTimeUtil.getFormatStr(orderMap.get("orderday").toString(), DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                			dbparam.put("payment_date", DateTimeUtil.getFormatStr(orderMap.get("paymentday").toString(), DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
            	        } else {
                			// 로컬, 개발서버 배송추적요청 테스트를 위한 날짜 임의 세팅
	            			dbparam.put("order_date", DateTimeUtil.getFormatStr("20220401000000", DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
	            			dbparam.put("payment_date", DateTimeUtil.getFormatStr("20220401000000", DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
            	        }
            			sendOrderList.add(dbparam);
            		}

                	SOMap sendParams = new SOMap();
                	String logiscode = deliveryTrackingService.getGoodsFlowLogisCode(sendMap.getStr("logistype"));
                	sendParams.put("trans_unique_code", String.format("%s%s_%s", DateTimeUtil.getNowDatePartShortStr(), sendMap.getStr("invoiceno"), logiscode));
                	sendParams.put("from_name", cs.getStr("cfgsitename"));
                	sendParams.put("logistics_code", logiscode);
                	sendParams.put("invoice_no", sendMap.getStr("invoiceno"));
                	sendParams.put("dlvret_type", "D");
                	sendParams.put("to_name", orderList.get(0).get("rcvname"));
                	
                	result = deliveryTrackingService.sendTraceRequest(sendParams, sendOrderList);
        			if (!"200".equals(result.getStr("status"))) {
        				String errorMsg = String.format("%s(%s)", "배송추적요청중 오류가 발생하였습니다. ", result.getStr("message"));
        				logger.error("배송추척요청중 오류 상세 : " + result.getStr("details"));
        				throw new CustomException(errorMsg);
        			} else {
                    	resultList.add(result);
        			}
            	}
            }
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("송장정보 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultList.size();
    }
    
    /**
     * 송장정보 일괄등록(엑셀)
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int saveOrderInvoiceByExcel(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
        	if (Util.isEmpty(params.get("dealerno"))) {
        		params.put("userid", getAdminId());
        	} else if (isDealer()) {
        		params.put("userid", getDealerId());
        	}
        	
        	ObjectMapper mapper = new ObjectMapper();
			List<SOMap> orderDelivList = mapper.convertValue(params.getArrayList("orderdelivlist"), new TypeReference<List<SOMap>>(){});

			// 주문상품별 Grouping처리
			List<SOMap> orderGoodsList = this._groupGoodsOption(orderDelivList);
			
			// 저장
			if (orderGoodsList != null && orderGoodsList.size() > 0) {
				for (SOMap orderGoods : orderGoodsList) {
					orderGoods.put("siteid", cs.getStr("siteid"));
					
					// 공통주문상품 IDX 조회
					SOMap ordgdInfoMap = comOrderGoodsMapper.selectComOrderGoodsInfo(orderGoods);
					if (ordgdInfoMap == null || ordgdInfoMap.get("ordgdidx") == null || ordgdInfoMap.get("ordgdidx") == "") {
						StringBuffer sb = new StringBuffer();
						sb.append("올바르지 않은 주문정보가 존재합니다.\n");
						sb.append("- 주문번호: " + orderGoods.getStr("ordno") + "\n");
						sb.append("- 상품코드: " + orderGoods.getStr("goodscode") + "\n");
						sb.append("- 단품코드: " + orderGoods.getStr("optioncode") + "\n");
						sb.append("- 상품순번: " + orderGoods.getStr("goodsturn"));
						throw new CustomException(sb.toString());
					} else if (Util.equal(ordgdInfoMap.getStr("delivtype"), CMConst.DELIV_DIRECT)) {
						StringBuffer sb = new StringBuffer();
						sb.append("배송유형이 직배송인 주문정보가 존재합니다.\n");
						sb.append("- 주문번호: " + orderGoods.getStr("ordno") + "\n");
						sb.append("- 상품코드: " + orderGoods.getStr("goodscode") + "\n");
						sb.append("- 단품코드: " + orderGoods.getStr("optioncode") + "\n");
						sb.append("- 상품순번: " + orderGoods.getStr("goodsturn"));
						throw new CustomException(sb.toString());
					} else if (Util.equal(ordgdInfoMap.getStr("ispbgoods"), "T")) {
						StringBuffer sb = new StringBuffer();
						sb.append("자사상품 주문정보가 존재합니다.\n");
						sb.append("- 주문번호: " + orderGoods.getStr("ordno") + "\n");
						sb.append("- 상품코드: " + orderGoods.getStr("goodscode") + "\n");
						sb.append("- 단품코드: " + orderGoods.getStr("optioncode") + "\n");
						sb.append("- 상품순번: " + orderGoods.getStr("goodsturn"));
						throw new CustomException(sb.toString());
					} else if (ordgdInfoMap.getInt("trckcnt") > 0) {
						StringBuffer sb = new StringBuffer();
						sb.append("이미 배송추적이 요청된 주문정보가 존재합니다.\n");
						sb.append("- 주문번호: " + orderGoods.getStr("ordno") + "\n");
						sb.append("- 상품코드: " + orderGoods.getStr("goodscode") + "\n");
						sb.append("- 단품코드: " + orderGoods.getStr("optioncode") + "\n");
						sb.append("- 상품순번: " + orderGoods.getStr("goodsturn"));
						throw new CustomException(sb.toString());
					} else if (ordgdInfoMap.getInt("cncproccnt") > 0) {
						StringBuffer sb = new StringBuffer();
						sb.append("취소처리가 진행중인 상품이 존재합니다.\n");
						sb.append("- 주문번호: " + orderGoods.getStr("ordno") + "\n");
						sb.append("- 상품코드: " + orderGoods.getStr("goodscode") + "\n");
						sb.append("- 단품코드: " + orderGoods.getStr("optioncode") + "\n");
						sb.append("- 상품순번: " + orderGoods.getStr("goodsturn"));
						throw new CustomException(sb.toString());
					}

					// 이전 주문상품배송정보 삭제
					orderGoods.put("istrash", "T");
					orderGoods.put("ordgdidx", ordgdInfoMap.get("ordgdidx"));
					orderGoods.put("isupdateordcnt", "T");
					comOrderGoodsDelivMapper.updateComOrderGoodsDelivUse(orderGoods);
					
					List<SOMap> delivList = orderGoods.getArrayList("delivlist");
					int frstordcnt = ordgdInfoMap.getInt("frstordcnt");
					int totdelivcnt = 0;
					for (SOMap delivMap : delivList) {
						// 총 배송수량 계산
	            		int delivcnt = delivMap.getInt("delivcnt");
						if (frstordcnt < delivcnt) {
							StringBuffer sb = new StringBuffer();
							sb.append("배송수량이 주문수량 이상인 주문정보가 존재합니다.\n");
							sb.append("- 주문번호: " + orderGoods.getStr("ordno") + "\n");
							sb.append("- 상품코드: " + orderGoods.getStr("goodscode") + "\n");
							sb.append("- 단품코드: " + orderGoods.getStr("optioncode") + "\n");
							sb.append("- 상품순번: " + orderGoods.getStr("goodsturn"));
	                    	throw new CustomException(sb.toString());
	                    }
						totdelivcnt += delivcnt;
						// 주문상품배송정보 추가
						delivMap.put("ordgdidx", orderGoods.get("ordgdidx"));
						delivMap.put("userid", params.get("userid"));
						delivMap.put("ordcnt", delivcnt);
						delivMap.put("aftval", CMConst.ORDER_PREPARING_DELIV);
						delivMap.put("ordstatus", CMConst.ORDER_PREPARING_DELIV);
						resultCnt += comOrderGoodsDelivMapper.insertComOrderGoodsDeliv(delivMap);
						// 주문상태 변경이력 추가
						comOrderGoodsHisMapper.insertComOrderGoodsHis(delivMap);
					}
	                if (frstordcnt < totdelivcnt) {
						StringBuffer sb = new StringBuffer();
						sb.append("총 배송수량이 주문수량 이상인 주문정보가 존재합니다.\n");
						sb.append("- 주문번호: " + orderGoods.getStr("ordno") + "\n");
						sb.append("- 상품코드: " + orderGoods.getStr("goodscode") + "\n");
						sb.append("- 단품코드: " + orderGoods.getStr("optioncode") + "\n");
						sb.append("- 상품순번: " + orderGoods.getStr("goodsturn"));
	                	throw new CustomException(sb.toString());
	                }
	                if (frstordcnt != totdelivcnt) {
						StringBuffer sb = new StringBuffer();
						sb.append("총 배송수량이 주문수량과 다른 주문정보가 존재합니다.\n");
						sb.append("- 주문번호: " + orderGoods.getStr("ordno") + "\n");
						sb.append("- 상품코드: " + orderGoods.getStr("goodscode") + "\n");
						sb.append("- 단품코드: " + orderGoods.getStr("optioncode") + "\n");
						sb.append("- 상품순번: " + orderGoods.getStr("goodsturn"));
	                	throw new CustomException(sb.toString());
	                }
				}
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append("등록할 데이터가 존재하지 않습니다.");
            	throw new CustomException(sb.toString());
			}
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("송장정보 일괄등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
    
    /**
     * 주문상품배송정보 추가 (송장등록)
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int insertComOrderGoodsDeliv(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
        	if (Util.isEmpty(params.get("dealerno"))) {
        		params.put("userid", getAdminId());
        	} else if (isDealer()) {
        		params.put("userid", getDealerId());
        	}
        	
			List<Map<String, Object>> delOrgdelivIdxList = params.getArrayList("delorgdelividxlist");
			if (delOrgdelivIdxList != null && delOrgdelivIdxList.size() > 0) {
				params.put("istrash", "T");
				resultCnt = comOrderGoodsDelivMapper.updateComOrderGoodsDelivUse(params);
			}
			List<Map<String, Object>> orderDelivList = params.getArrayList("orderdelivlist");
			if (orderDelivList != null && orderDelivList.size() > 0) {
				for (Map<String, Object> orderDelivMap : orderDelivList) {
					SOMap dbParam = new SOMap();
					dbParam.putAll(orderDelivMap);
					dbParam.put("userid", params.get("userid"));
					dbParam.put("ordstatus", CMConst.ORDER_PREPARING_DELIV);

					if (orderDelivMap.get("orgdelividx") != null && orderDelivMap.get("orgdelividx") != "") {
						// 주문상품배송정보 수정
						dbParam.put("isupdateordcnt", "T");
						resultCnt = comOrderGoodsDelivMapper.updateAdminOrderGoodsDeliv(dbParam);
					} else {
						dbParam.put("aftval", CMConst.ORDER_PREPARING_DELIV);
						// 주문상품배송정보 추가
						resultCnt = comOrderGoodsDelivMapper.insertComOrderGoodsDeliv(dbParam);
						// 주문상태 변경이력 추가
						comOrderGoodsHisMapper.insertComOrderGoodsHis(dbParam);
					}
				}
			}
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("송장 추가시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
    
    /**
     * 주문상품배송정보 사용여부 갱신 (송장삭제)
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int updateComOrderGoodsDelivUse(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
			// 최소 1개 이상 송장정보 존재해야함
			int invoicecnt = comOrderGoodsDelivMapper.selectOrderInvoiceCount(params);
			if (invoicecnt == 1) {
				throw new CustomException("한 개 이상의 송장정보가 필수이므로 삭제가 불가합니다.");
			}

			// 삭제여부 T로 업데이트
			params.put("userid", cs.getStr("authuserid"));
			params.put("istrash", "T");
			resultCnt = comOrderGoodsDelivMapper.updateComOrderGoodsDelivUse(params);
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("송장 삭제시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
    
    /**
     * 주문상품목록 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectOrderGoodsList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        params.put("separator", "\n");
        List<SOMap> resultList = comOrderGoodsMapper.selectAdminOrderGoodsList(params);
        result.put("list", resultList);
        return result;
    }
    
    /**
     * 주문기본정보 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectOrderInfo(SOMap params) throws Exception {
    	SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        // 주문기본정보 조회
        SOMap orderInfo = comOrderMapper.selectAdminOrderInfo(params);
        // 클레임 건수 조회
        SOMap orderCount = claimMapper.selectClaimClmtypeCnt(params);

        result.put("info", orderInfo);
        result.put("count", orderCount);
        return result;
    }
    
    /**
     * 주문상세정보 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectOrderDetail(SOMap params) throws Exception {
    	SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        
        // 주문정보 상세조회
        SOMap orderDetail = comOrderMapper.selectAdminOrderDetail(params);
        int totsaleamt = orderDetail.getInt("totsalepromoamt") + orderDetail.getInt("totgoodscpnamt") + orderDetail.getInt("totdelivcpnamt")  + orderDetail.getInt("basketcpnamt");
        int totpointamt = orderDetail.getInt("empreservetotamt") + orderDetail.getInt("reservetotamt") + orderDetail.getInt("epointtotamt");
        orderDetail.put("totsaleamt", totsaleamt);
        orderDetail.put("totpointamt", totpointamt);
        params.put("orderidx", orderDetail.get("orderidx"));
        
        // 주문상품목록 조회
        List<SOMap> orderList = comOrderGoodsMapper.selectAdminOrderClaimGoodsList(params);
        // 교환 회수이후 목록 조회
        List<SOMap> excAfRecList = claimGoodsMapper.selectExcAfterRecList(params);
        orderList.addAll(excAfRecList);
        
        List<SOMap> waitingDepositList = orderList.stream()		// 입금대기목록
				.filter(order -> Util.equal(order.getStr("ordstatus"), CMConst.ORDER_WAITING_DEPOSIT)
							  || Util.equal(order.getStr("ordstatus"), CMConst.ORDER_CANCEL_BF_DEPOSIT))
		        .collect(Collectors.toList());
        List<SOMap> prepGoodsList = orderList.stream()			// 상품준비중목록
				.filter(order -> Util.equal(order.getStr("ordstatus"), CMConst.ORDER_COMPLEATE_PAYMENT)
						  	  || Util.equal(order.getStr("ordstatus"), CMConst.ORDER_PREPARING_GOODS))
		        .collect(Collectors.toList());
        List<SOMap> prepDelivList = orderList.stream()			// 배송준비중목록
				.filter(order -> Util.equal(order.getStr("ordstatus"), CMConst.ORDER_PREPARING_DELIV))
		        .collect(Collectors.toList());
        List<SOMap> inDelivList = orderList.stream()			// 배송중목록
				.filter(order -> Util.equal(order.getStr("ordstatus"), CMConst.ORDER_IN_DELIVERY))
		        .collect(Collectors.toList());
        List<SOMap> completeDelivList = orderList.stream()		// 배송완료목록
				.filter(order -> Util.equal(order.getStr("ordstatus"), CMConst.ORDER_COMPLETE_DELIV)
							  || Util.equal(order.getStr("ordstatus"), CMConst.ORDER_PURCH_CONFIRM))
				.sorted(Comparator.comparing(o -> o.getStr("goodsno")+o.getStr("ordgdidx")+o.getStr("type")))
		        .collect(Collectors.toList());
        
        // 사은품목록 조회
        params.put("status", "COMPLETE");
        params.put("imgtype", CMConst.IMG_TYPE_GIFT_IMG_PC_B);
        List<SOMap> orderGiftList = claimGoodsGiftMapper.selectClaimGoodsGiftWithoutClaim(params);
        
        // 할인내역목록 조회
        List<SOMap> orderDiscountList = new ArrayList<SOMap>();
        List<SOMap> orderClaimList = claimGoodsMapper.selectOrderGoodsWithoutClaimList(params);
    	if (orderClaimList != null && orderClaimList.size() > 0) {
    		int promoRow = 0;
    		int goodsRow = 0;
    		for (SOMap orderMap : orderClaimList) {
    			if (orderMap.getInt("salepromoamt") > 0 && orderMap.getInt("ordcnt") > 0) {
    				SOMap discountMap = new SOMap();
    				discountMap.put("type", "promo");
    				discountMap.put("typename", "프로모션");
    				discountMap.put("rowno", ++promoRow);
    				discountMap.put("goodscode", orderMap.get("goodscode"));
    				discountMap.put("optioncode", orderMap.get("optioncode"));
    				discountMap.put("cpnname", orderMap.get("promoname"));
    				discountMap.put("discountamt", orderMap.get("salepromoamt"));
    				orderDiscountList.add(discountMap);
    			}
    			if (orderMap.getInt("goodscpnamt") > 0 && orderMap.getInt("ordcnt") > 0) {
    				SOMap discountMap = new SOMap();
    				discountMap.put("type", "goods");
    				discountMap.put("typename", "상품쿠폰");
    				discountMap.put("rowno", ++goodsRow);
    				discountMap.put("goodscode", orderMap.get("goodscode"));
    				discountMap.put("optioncode", orderMap.get("optioncode"));
    				discountMap.put("cpnname", orderMap.get("cpnname"));
    				discountMap.put("discountamt", orderMap.get("goodscpnamt"));
    				orderDiscountList.add(discountMap);
    			}
    		}
    		
    	}
    	List<SOMap> orderCouponList = claimCouponMapper.selectOrderCouponListWithoutClaim(params);
    	if (orderCouponList != null && orderCouponList.size() > 0) {
    		int basketRow = 0;
    		int delivRow = 0;
    		for (SOMap couponMap : orderCouponList) {
				SOMap discountMap = new SOMap();
    			if (Util.equal(couponMap.getStr("comcpntype"), CMConst.COM_CPN_CART) && orderDetail.getInt("basketcpnamt") > 0) {
    				discountMap.put("type", "cart");
    				discountMap.put("typename", "장바구니쿠폰");
    				discountMap.put("rowno", ++basketRow);
    				discountMap.put("goodscode", couponMap.get("goodscode"));
    				discountMap.put("optioncode", couponMap.get("optioncode"));
    				discountMap.put("cpnname", couponMap.get("cpnname"));
    				discountMap.put("discountamt", orderDetail.get("basketcpnamt"));
    				orderDiscountList.add(discountMap);
    			} else if (Util.equal(couponMap.getStr("comcpntype"), CMConst.COM_CPN_DELIV) && orderDetail.getInt("totdelivcpnamt") > 0) {
    				discountMap.put("type", "deliv");
    				discountMap.put("typename", "배송비쿠폰");
    				discountMap.put("rowno", ++delivRow);
    				discountMap.put("goodscode", couponMap.get("goodscode"));
    				discountMap.put("optioncode", couponMap.get("optioncode"));
    				discountMap.put("cpnname", couponMap.get("cpnname"));
    				discountMap.put("discountamt", orderDetail.get("totdelivcpnamt"));
    				orderDiscountList.add(discountMap);
    			}
    		}
    	}
    	if (orderDiscountList != null && orderDiscountList.size() > 0) {
    		orderDiscountList = orderDiscountList.stream().sorted(Comparator.comparing(m -> m.getStr("type"), Comparator.reverseOrder())).collect(Collectors.toList());
        	int promoCnt = (int) orderDiscountList.stream().filter(m -> Util.equal(m.getStr("type"), "promo")).count();
        	int goodscpnCnt = (int) orderDiscountList.stream().filter(m -> Util.equal(m.getStr("type"), "goods")).count();
        	int cartcpnCnt = (int) orderDiscountList.stream().filter(m -> Util.equal(m.getStr("type"), "cart")).count();
        	int delivcpnCnt = (int) orderDiscountList.stream().filter(m -> Util.equal(m.getStr("type"), "deliv")).count();
    		
        	for (SOMap discountMap : orderDiscountList) {
        		if (Util.equal(discountMap.getStr("type"), "promo")) {
        			discountMap.put("rowspan", promoCnt);
        		} else if (Util.equal(discountMap.getStr("type"), "goods")) {
        			discountMap.put("rowspan", goodscpnCnt);
        		} else if (Util.equal(discountMap.getStr("type"), "cart")) {
        			discountMap.put("rowspan", cartcpnCnt);
        		} else if (Util.equal(discountMap.getStr("type"), "deliv")) {
        			discountMap.put("rowspan", delivcpnCnt);
        		}
        	}
    	}
    	
        // 관리자메모목록 조회
        List<SOMap> orderMemoList = comOrderMemoMapper.selectOrderMemoList(params);
        
        result.put("info", orderDetail);
        result.put("waitingDepositList", waitingDepositList);
        result.put("prepGoodsList", prepGoodsList);
        result.put("prepDelivList", prepDelivList);
        result.put("inDelivList", inDelivList);
        result.put("completeDelivList", completeDelivList);
        result.put("orderGiftList", orderGiftList);
        result.put("orderDiscountList", orderDiscountList);
        result.put("orderMemoList", orderMemoList);
        
        return result;
    }
    
    /**
     * 주문상세 클레임내역 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectClaimInfo(SOMap params) throws Exception {
    	SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
    	
        // 클레임 목록 조회
        List<SOMap> claimList = claimMapper.selectAdminClaimListForPrev(params);
    	if (!Util.isEmpty(params.getStr("dealerno"))) {
            claimList = claimList.stream()
            					 .filter(m -> m.getStr("dealernoarr").indexOf(params.getStr("dealerno")) > -1 && Util.equal(m.getStr("clmtype"), params.getStr("clmtype")))
            					 .collect(Collectors.toList());
    	} else {
            claimList = claimList.stream()
					 .filter(m -> Util.equal(m.getStr("clmtype"), params.getStr("clmtype")))
					 .collect(Collectors.toList());
    	}
    	
        // 클레임 목록 조회
        if (claimList != null && claimList.size() > 0) {
	      	for (SOMap claimMap : claimList) {
	      		claimMap.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S);
	    		List<SOMap> claimGoodsList = claimGoodsMapper.selectOrderClaimGoodsList(claimMap);
	    		// 클레임별 상품목록 조회
	            if (claimGoodsList != null && claimGoodsList.size() > 0) {
	            	int rtndadadelivamt = 0;
	            	int rtnptndelivamt = 0;
	    	      	for (SOMap clmGoodsMap : claimGoodsList) {
	    	      		// 자사/파트너사 배송비 세팅
	    	      		if (Util.equal(clmGoodsMap.getStr("ispbgoods"), "T")) {
	    	      			rtndadadelivamt = rtndadadelivamt + clmGoodsMap.getInt("rtndelivamt");
	    	      			claimMap.put("adddadadelivamt", clmGoodsMap.get("adddelivamt"));
	    	      			claimMap.put("addptndelivamt", 0);
	    	      		} else {
	    	      			rtnptndelivamt = rtnptndelivamt + clmGoodsMap.getInt("rtnptndelivamt");
	    	      			claimMap.put("adddadadelivamt", 0);
	    	      			claimMap.put("addptndelivamt", clmGoodsMap.get("adddelivamt"));
	    	      		}
	    	      	}
	    	      	claimMap.put("rtndadadelivamt", rtndadadelivamt);
	    	      	claimMap.put("rtnptndelivamt", rtnptndelivamt);
	            }
	    		claimMap.put("claimGoodsList", claimGoodsList);
	      	}
        }
        result.put("claimList", claimList);
        		
        // 관리자메모목록 조회
        List<SOMap> orderMemoList = comOrderMemoMapper.selectOrderMemoList(params);
        result.put("orderMemoList", orderMemoList);
        
        return result;
    }
    
    /**
     * 주문CS 처리내역 조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectCsInfo(SOMap params) throws Exception {
    	SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        // CS처리목록 조회
        List<SOMap> csList = inquiryMapper.selecrOrderInquiryList(params);
        // 관리자메모목록 조회
        List<SOMap> orderMemoList = comOrderMemoMapper.selectOrderMemoList(params);
        
        result.put("cslist", csList);
        result.put("orderMemoList", orderMemoList);
        
        return result;
    }
    
    /**
     * 관리자메모 추가
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int insertOrderMemo(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
			params.put("userid", cs.getStr("authuserid"));
			resultCnt = comOrderMemoMapper.insertOrderMemo(params);
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("관리자메모 추가시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
    
    /**
     * 관리자메모 사용여부 갱신 (관리자메모 삭제)
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int updateOrderMemoUse(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
			// 삭제여부 T로 업데이트
			params.put("istrash", "T");
			resultCnt = comOrderMemoMapper.updateOrderMemoUse(params);
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("관리자메모 삭제시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
    
    /**
     * 주문 상품옵션별 그룹핑
     * @param orderDelivList
     * @return List<SOMap>
     */
    public List<SOMap> _groupGoodsOption(List<SOMap> orderDelivList) {
    	Map<String, List<SOMap>> groupingMap = orderDelivList.stream().collect(Collectors.groupingBy(item -> String.format("%s%s%s%s", item.getStr("ordno"), item.getStr("goodscode"), item.getStr("optioncode"), item.getStr("goodsturn"))));
		List<SOMap> orderGoodsList = new ArrayList<SOMap>();
		for(String key : groupingMap.keySet()){
			for (SOMap orderDeliv : orderDelivList) {
				String orderDelivKey = String.format("%s%s%s%s", orderDeliv.getStr("ordno"), orderDeliv.getStr("goodscode"), orderDeliv.getStr("optioncode"), orderDeliv.getStr("goodsturn"));
				if (Util.equal(key, orderDelivKey)) {
					SOMap deilvMap = new SOMap();
					deilvMap.put("key", orderDelivKey);
					deilvMap.put("ordno", orderDeliv.get("ordno"));
					deilvMap.put("goodscode", orderDeliv.get("goodscode"));
					deilvMap.put("optioncode", orderDeliv.get("optioncode"));
					deilvMap.put("goodsturn", orderDeliv.get("goodsturn"));
					deilvMap.put("frstordcnt", orderDeliv.get("frstordcnt"));
					deilvMap.put("delivlist", new ArrayList<SOMap>());
					orderGoodsList.add(deilvMap);
					break;
				}
			}
		}
		// 주문상품별 송장정보
		for (SOMap orderDeliv : orderDelivList) {
			String orderDelivKey = String.format("%s%s%s%s", orderDeliv.getStr("ordno"), orderDeliv.getStr("goodscode"), orderDeliv.getStr("optioncode"), orderDeliv.getStr("goodsturn"));
			for (SOMap orderGoods : orderGoodsList) {
				if(Util.equal(orderGoods.getStr("key"), orderDelivKey)) {
					orderGoods.getArrayList("delivlist").add(orderDeliv);
					break;
				}
			}
		}
    	return orderGoodsList;
    }
    
	/**
	 * 직배송 안내문구 저장
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int updateDirDelivMsg(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
			resultCnt = comOrderGoodsDelivMapper.updateDirDelivMsg(params);
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("직배송 안내문구 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
    
	/**
	 * 배송중인 같은 운송장 건수 조회
	 * @param params
	 * @return int
	 * @throws Exception
	 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int selectSameInvoiceCount(SOMap params) throws Exception {
    	int resultCnt = 0;
    	List<SOMap> orderDelivList = Util.convertToListSOMap(params.getArrayList("orderdelivlist"));
    	Map<String, List<SOMap>> logisGroupingMap = orderDelivList.stream().collect(Collectors.groupingBy(m -> m.getStr("logistype")));
    	for (String key : logisGroupingMap.keySet()) {
    		Map<String, List<SOMap>> invoiceGroupingMap = logisGroupingMap.get(key).stream().collect(Collectors.groupingBy(m -> m.getStr("invoiceno")));
    		for (String key2 : invoiceGroupingMap.keySet()) {
    			List<SOMap> delivlist = invoiceGroupingMap.get(key2);
    			List<String> orgdelividxlist = delivlist.stream().map(m -> m.getStr("orgdelividx")).distinct().collect(Collectors.toList());
    			SOMap delivInfo = new SOMap();
    			delivInfo.put("logistype", key);
    			delivInfo.put("invoiceno", key2);
    			delivInfo.put("orgdelividxlist", orgdelividxlist);
    			List<SOMap> sameInvoiceMap = comOrderGoodsDelivMapper.selectSameInvoiceInfo(delivInfo);
        		resultCnt += sameInvoiceMap.size();
			}
    	}
        return resultCnt;
    }
}
