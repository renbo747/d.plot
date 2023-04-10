package com.dplot.admin.service.adjust;

import com.dplot.common.SOMap;
import com.dplot.common.service.TossService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.CalculateDtlMapper;
import com.dplot.mapper.CalculateMapper;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.DealerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminPartnersAdjustServiceImpl  extends MallBaseService implements AdminPartnersAdjustService{

    @Autowired
    private DealerMapper dealerMapper;

    @Autowired
    private CalculateMapper calculateMapper;

    @Autowired
    private CalculateDtlMapper calculateDtlMapper;

    @Autowired
    private TossService tossService;

    @Autowired
    private ComOrderMapper comOrderMapper;

    @Override
    @Transactional
    public void partnersCalculateProcess(SOMap param) {

        String calcMonth = param.getStr("calcmonth");
        String calcExpDay = param.getStr("calcexpday");

        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.getStr("siteid"));
        dbParam.put("calcmonth", calcMonth);
        dbParam.put("calcexpday", calcExpDay);

        List<SOMap> list = dealerMapper.selectDealerListCalculate(dbParam);

        if(list.size() > 0){
            for(SOMap row : list){
                row.put("calcmonth", calcMonth.substring(0, 6));
                row.put("calcexpday", calcExpDay);
                List<SOMap> calcDtlList = calculateDtlMapper.selectCalculateDtlData(row);
                //정산내역이 없으면 INSERT 하지 않음 이전 월 구매확정 및 취소완료, 반품완료, 교환완료
                if(calcDtlList.size() > 0) {
                    SOMap data = new SOMap();
                    List<SOMap> dataList = calcDtl(calcDtlList);

                    calculateMapper.insertCalculateForBatch(row);

                    data.put("calcidx", row.get("calcidx"));
                    data.put("list", dataList);

                    calculateDtlMapper.insertCalculateDtlForBatch(data);
                }
            }
        }
    }

    public List<SOMap> calcDtl(List<SOMap> list){
        List<SOMap> newList = new ArrayList<>();
        for(SOMap row : list){
            SOMap calcRow = new SOMap();
            //로우별 정산 총금액 계산
            int saleAmt = row.getInt("saleamt"); //판매가
            int supplyAmt = row.getInt("supplyamt");
            int supplyVatAmt = row.getInt("supplyvatamt");
            int cnt = row.getInt("cnt"); //수량
            int promoShareAmt = row.getInt("promoshareamt"); //프로모션 할인 분담비율
            int cpnShareAmt = row.getInt("cpnshareamt"); //상품할인 쿠폰 할인 분담비율
            int ptnDelivAmt = row.getInt("ptndelivamt"); //배송비
            int addRPayTotPrice = row.getInt("addrpaytotprice"); //추가 결제 금액

            int calcAmt = ((saleAmt - supplyAmt - supplyVatAmt) * cnt) + promoShareAmt + cpnShareAmt + ptnDelivAmt + addRPayTotPrice;
            row.put("calcamt", calcAmt);
            calcRow.putAll(row);

            //공백이 들어가는걸 방지하기 위해 엘리먼트 삭제 시켜서 NULL 이 들어갈 수 있도록 처리
            if("".equals(calcRow.getStr("clmgdidx"))){
                calcRow.put("clmgdidx", null);
            }

            if("".equals(calcRow.getStr("ordgdidx"))){
                calcRow.put("ordgdidx", null);
            }

            newList.add(calcRow);
        }
        return newList;
    }

    @Override
    public SOMap selectAdjustList(SOMap param) throws Exception{
        SOMap result = new SOMap();

        int page = Integer.parseInt(param.get("page").toString());
        int pageCount = Integer.parseInt(param.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        param.put("startpage", startPage);
        param.put("endpage", pageCount);
        param.put("siteid", cs.getStr("siteid"));

        String sKey = param.getStr("skey");
        if("bizno".equals(sKey)){
            String sword = param.getStr("sword").replaceAll("-", "");
            param.put("sword", sword);
        }
        result.put("list", calculateMapper.selectCalculateList(param));
        result.put("listcount", calculateMapper.selectCalculateListCount(param));

        return result;
    }

    @Override
    public SOMap updateCalcStatus(SOMap param) throws Exception {
        int resultCnt = calculateMapper.updateCalculate(param);
        SOMap result = new SOMap();
        result.put("count", resultCnt);
        return result;
    }

    @Override
    public SOMap reCalculate(SOMap param) throws Exception {
        SOMap result = new SOMap();
        List<SOMap> calcDtlList = calculateDtlMapper.selectCalculateDtlData(param);

        if(calcDtlList.size() > 0) {
            SOMap data = new SOMap();
            List<SOMap> dataList = calcDtl(calcDtlList);
            data.put("calcidx", param.get("calcidx"));
            data.put("list", dataList);

            calculateDtlMapper.deleteCalculateDtl(data);
            int resultCnt = calculateDtlMapper.insertCalculateDtlForBatch(data);
            result.put("count", resultCnt);

            SOMap updateParam = new SOMap();
            List<String> list = new ArrayList<>();
            list.add(param.getStr("calcidx"));

            updateParam.put("calcstatus", "CAS002");
            updateParam.put("calcidx", list);
            updateCalcStatus(updateParam);
        }

        return result;
    }

    @Override
    public SOMap selectAdjustCsList(SOMap param) throws Exception {
        SOMap result = new SOMap();

        int page = Integer.parseInt(param.get("page").toString());
        int pageCount = Integer.parseInt(param.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        param.put("startpage", startPage);
        param.put("endpage", pageCount);
        param.put("siteid", cs.getStr("siteid"));

        result.put("list", calculateDtlMapper.selectCalculateCsList(param));
        result.put("listcount", calculateDtlMapper.selectCalculateCsListCount(param));

        return result;
    }

    @Override
    public SOMap selectAdjustDetailList(SOMap param) throws Exception {
        SOMap result = new SOMap();

        int page = Integer.parseInt(param.get("page").toString());
        int pageCount = Integer.parseInt(param.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;

        param.put("startpage", startPage);
        param.put("endpage", pageCount);
        param.put("siteid", cs.getStr("siteid"));

        result.put("list", calculateDtlMapper.selectAdjustDetailList(param));
        result.put("listcount", calculateDtlMapper.selectAdjustDetailListCount(param));

        return result;
    }

    @Override
    public SOMap updatePaymentDate(SOMap param) throws Exception {
        int resultCnt = calculateMapper.updatePaymentDate(param);
        SOMap result = new SOMap();
        result.put("count", resultCnt);
        return result;
    }

    @Override
    public SOMap selectPayCompareList(SOMap param) throws Exception {

        SOMap result = new SOMap();

        SOMap compareParam = new SOMap();
        compareParam.put("startdate", String.format("%s%s", param.getStr("startdate").replaceAll("-", ""), "000000"));
        compareParam.put("enddate", String.format("%s%s", param.getStr("enddate").replaceAll("-", ""), "235959"));

        List<SOMap> paymentList = tossService.tossPaymentList(compareParam);
        List<SOMap> orderList = comOrderMapper.selectOrderPaymentList(compareParam);

        List<SOMap> completeList = new ArrayList<>();

        if(paymentList.size() > orderList.size()){
            for(SOMap row : paymentList){
                SOMap convertMap = new SOMap();
                convertMap.put("tosspaydate", row.getStr("transactionat").replace("T", " ").substring(0, row.getStr("transactionat").indexOf("+")));
                convertMap.put("tossmethod", row.getStr("method"));
                convertMap.put("tossamount", row.getStr("amount"));

                for(SOMap ord : orderList){
                    if(row.getStr("paymentkey").equals(ord.getStr("paymentkey"))){
                        if(ord.getStr("type").equals("CLM") && row.getStr("status").equals("CANCELED")){
                            convertMap.put("rpaytotprice", ord.getStr("rpaytotprice"));
                            convertMap.put("paymentdate", ord.getStr("paymentdate"));
                            convertMap.put("ordno", ord.getStr("ordno"));
                        }

                        if(ord.getStr("type").equals("ORD") && row.getStr("status").equals("DONE")){
                            convertMap.put("rpaytotprice", ord.getStr("rpaytotprice"));
                            convertMap.put("paymentdate", ord.getStr("paymentdate"));
                            convertMap.put("ordno", ord.getStr("ordno"));
                        }
                    }
                }
                completeList.add(convertMap);
            }
        } else {
            for(SOMap ord : orderList){
                SOMap convertMap = new SOMap();

                convertMap.put("rpaytotprice", ord.getStr("rpaytotprice"));
                convertMap.put("paymentdate", ord.getStr("paymentdate"));
                convertMap.put("ordno", ord.getStr("ordno"));

                for(SOMap pay : paymentList){
                    if(ord.getStr("paymentkey").equals(pay.getStr("paymentkey"))){
                        if(pay.getStr("type").equals("CLM") && ord.getStr("status").equals("CANCELED")){
                            convertMap.put("tosspaydate", ord.getStr("transactionat").replace("T", " ").substring(0, ord.getStr("transactionat").indexOf("+")));
                            convertMap.put("tossmethod", ord.getStr("method"));
                            convertMap.put("tossamount", ord.getStr("amount"));
                        }

                        if(pay.getStr("type").equals("ORD") && ord.getStr("status").equals("DONE")){
                            convertMap.put("tosspaydate", ord.getStr("transactionat").replace("T", " ").substring(0, ord.getStr("transactionat").indexOf("+")));
                            convertMap.put("tossmethod", ord.getStr("method"));
                            convertMap.put("tossamount", ord.getStr("amount"));
                        }
                    }
                }
                completeList.add(convertMap);
            }
        }

        if(completeList.size() > 0 ) {
            if (param.getStr("issame").equals("T")) {
                completeList = completeList.stream().filter(a -> a.getStr("tossamount").equals(a.getStr("rpaytotprice"))).collect(Collectors.toList());
            } else if (param.getStr("issame").equals("F")) {
                completeList = completeList.stream().filter(a -> !a.getStr("tossamount").equals(a.getStr("rpaytotprice"))).collect(Collectors.toList());
            }

            completeList = completeList.stream().peek(a -> {
                if(a.getStr("tossamount").equals(a.getStr("rpaytotprice"))){
                    a.put("issame", "T");
                } else {
                    a.put("issame", "F");
                }
            }).collect(Collectors.toList());
        }

        result.put("list", completeList);
        return result;
    }

    @Override
    public List<SOMap> calcExcelDownList(SOMap param) throws Exception {
        return calculateMapper.selectCalculateExcelDownList(param);
    }
    
    @Override
    public SOMap updateCalcCfm(SOMap param) throws Exception {
        int resultCnt = calculateMapper.updatePartCfmDate(param);
        SOMap result = new SOMap();
        result.put("count", resultCnt);
        return result;
    }
}
