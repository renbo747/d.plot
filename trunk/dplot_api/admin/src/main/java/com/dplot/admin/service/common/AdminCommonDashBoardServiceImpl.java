package com.dplot.admin.service.common;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.*;
import com.dplot.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminCommonDashBoardServiceImpl extends MallBaseService implements AdminCommonDashBoardService {

    @Autowired
    private ComOrderMapper comOrderMapper;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private BoardPostMapper boardPostMapper;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private InquiryMapper inquiryMapper;

    @Autowired
    private PartnerInquiryMapper partnerInquiryMapper;

    @Autowired
    private CalculateMapper calculateMapper;

    @Autowired
    private ComCartMapper comCartMapper;

    @Autowired
    private ClaimGoodsMapper claimGoodsMapper;

    @Override
    public SOMap selectDashBoardData(SOMap param) throws Exception {
        SOMap result = new SOMap();
        param.put("siteid", cs.getStr("siteid"));
        String type = param.getStr("type");
        switch(type){
            case "ORDER": result.put("ORDER", comOrderMapper.selectOrderDashBoard(param));
                break;
            case "CLAIM" : result.put("CLAIM", claimMapper.selectClaimDashBoard(param));
                break;
            case "GOODS" : result.put("GOODS", goodsMapper.selectGoodsDashBoard(param));
                break;
            case "DELAY" : result.put("DELAY", comOrderMapper.selectOrderClaimDelayDashBoard(param));
                break;
            case "ACCOUNT" :
                if("BUY".equals(param.getStr("standard"))){
                    param.put("status", Collections.singletonList(CMConst.ORDER_PURCH_CONFIRM));
                } else {
                    param.put("status", Arrays.asList(CMConst.ORDER_PREPARING_GOODS, CMConst.ORDER_PREPARING_DELIV ,CMConst.ORDER_IN_DELIVERY, CMConst.ORDER_COMPLETE_DELIV, CMConst.ORDER_PURCH_CONFIRM));
                }

                SOMap acAccount = getAccountData(param);
                result.put("GRAPH", acAccount.get("graph"));
                result.put("ACCOUNT", acAccount.get("account"));
                result.put("TOTAMT", acAccount.get("totamt"));
                result.put("PAYCOUNT", comOrderMapper.selectDashBoardOrderClaimCount(param));

                if(acAccount.containsKey("yeargraph")){
                    result.put("YEARGRAPH", acAccount.get("yeargraph"));
                }
                result.put("CARTLIST", getDashBoardList(param, "CART"));
                result.put("CLAIMLIST", getDashBoardList(param, "CLAIM"));
                result.put("SALELIST", getDashBoardList(param, "GOODS_SALE"));
                result.put("CATELIST", getDashBoardList(param, "GOODS_CATE"));
                break;
            case "ALL" :

                result.put("ORDER", comOrderMapper.selectOrderDashBoard(param));
                result.put("CLAIM", claimMapper.selectClaimDashBoard(param));
                result.put("GOODS", goodsMapper.selectGoodsDashBoard(param));
                result.put("DELAY", comOrderMapper.selectOrderClaimDelayDashBoard(param));
                result.put("MEMBER", memberMapper.selectMemberDashBoard(param));
                result.put("COUPON", couponMapper.selectCouponDashBoard(param));
                param.put("boardtype", "NTT006");
                result.put("NOTICE", boardPostMapper.selectBoardPostDashBoard(param));
                param.put("boardtype", "NTT001");
                result.put("NOTICE_PARTNER", boardPostMapper.selectBoardPostDashBoard(param));
                result.put("PROMOTION", promotionMapper.selectPromotionDashBoard(param));
                result.put("INQUIRY", inquiryMapper.selectInquiryDashBoard(param));

                if("BUY".equals(param.getStr("standard"))){
                    param.put("status", Collections.singletonList(CMConst.ORDER_PURCH_CONFIRM));
                } else {
                    param.put("status", Arrays.asList(CMConst.ORDER_PREPARING_GOODS, CMConst.ORDER_PREPARING_DELIV ,CMConst.ORDER_IN_DELIVERY, CMConst.ORDER_COMPLETE_DELIV, CMConst.ORDER_PURCH_CONFIRM));
                }

                SOMap allAccount = getAccountData(param);
                result.put("GRAPH", allAccount.get("graph"));
                result.put("ACCOUNT", allAccount.get("account"));
                result.put("TOTAMT", allAccount.get("totamt"));
                result.put("PAYCOUNT", comOrderMapper.selectDashBoardOrderClaimCount(param));

                if(allAccount.containsKey("yeargraph")){
                    result.put("YEARGRAPH", allAccount.get("yeargraph"));
                }

                result.put("CARTLIST", getDashBoardList(param, "CART"));
                result.put("CLAIMLIST", getDashBoardList(param, "CLAIM"));
                result.put("SALELIST", getDashBoardList(param, "GOODS_SALE"));
                result.put("CATELIST", getDashBoardList(param, "GOODS_CATE"));

                break;
        }
        return result;
    }

    @Override
    public SOMap selectPartnerDashBoardData(SOMap param) throws Exception {
        SOMap result = new SOMap();
        param.put("siteid", cs.getStr("siteid"));
        String type = param.getStr("type");
        switch(type){
            case "ORDER": result.put("ORDER", comOrderMapper.selectOrderDashBoard(param));
                break;
            case "CLAIM" : result.put("CLAIM", claimMapper.selectClaimDashBoard(param));
                break;
            case "GOODS" : result.put("GOODS", goodsMapper.selectGoodsDashBoard(param));
                break;
            case "DELAY" : result.put("DELAY", comOrderMapper.selectOrderClaimDelayDashBoard(param));
                break;
            case "ACCOUNT" :
                param.put("status", Collections.singletonList(CMConst.ORDER_PURCH_CONFIRM));

                result.put("ACCOUNT", comOrderMapper.selectOrderClaimAccountDashBoard(param));
                result.put("PAYCOUNT", comOrderMapper.selectDashBoardOrderClaimCount(param));
                param.put("istwoweek", true);
                result.put("WEEKGRAPH", comOrderMapper.selectTwoWeekPartnersDashBoard(param));

                break;
            case "ALL" :

                result.put("ORDER", comOrderMapper.selectOrderDashBoard(param));
                result.put("CLAIM", claimMapper.selectClaimDashBoard(param));
                result.put("GOODS", goodsMapper.selectGoodsDashBoard(param));
                result.put("DELAY", comOrderMapper.selectOrderClaimDelayDashBoard(param));
                param.put("boardtype", "NTT001");
                result.put("NOTICE_PARTNER", boardPostMapper.selectBoardPostDashBoard(param));
                result.put("NOTICE_POPUP", boardPostMapper.selectBoardPostDashBoardPopup(param));
                param.put("boardtype", "NTT002");
                result.put("AGREE_PARTNER", boardPostMapper.selectAgreeDashBoard(param));
                result.put("AGREE_POPUP", boardPostMapper.selectAgreeDashBoardPopup(param));
                result.put("INQUIRY", inquiryMapper.selectInquiryDashBoard(param));
                result.put("MANAGEINQUIRY", partnerInquiryMapper.selectPartnerInquiryDashBoard(param));
                result.put("CALCULATE", calculateMapper.selectCalculateDashBoard(param));
                result.put("CALCULATE_POPUP", calculateMapper.selectCalculateDashBoardPopup(param));

                param.put("status", Collections.singletonList(CMConst.ORDER_PURCH_CONFIRM));
                result.put("ACCOUNT", comOrderMapper.selectOrderClaimAccountDashBoard(param));
                result.put("PAYCOUNT", comOrderMapper.selectDashBoardOrderClaimCount(param));
                param.put("istwoweek", true);
                result.put("WEEKGRAPH", comOrderMapper.selectTwoWeekPartnersDashBoard(param));
                break;
        }
        return result;
    }

    public List<SOMap> getDashBoardList(SOMap param, String type) throws Exception{
        List<SOMap> list = new ArrayList<>();
        if(!"MONTH".equals(param.getStr("saletype"))){
            //리스트 뿌려야됨 주간별 일별 매출리스트
            String startDate = "";
            String endDate = "";

            if("DAY".equals(param.getStr("saletype"))){
                startDate = DateTimeUtil.getNowFormatStr("yyyy-MM-dd");
                endDate = DateTimeUtil.getNowFormatStr("yyyy-MM-dd");
            } else if("WEEK".equals(param.getStr("saletype"))){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date =  sdf.parse(DateTimeUtil.getNowFormatStr("yyyy-MM-dd"));

                Calendar cal = Calendar.getInstance(Locale.KOREA);
                cal.setTime(date);
                cal.add(Calendar.DATE, 1 - cal.get(Calendar.DAY_OF_WEEK));
                startDate = sdf.format(cal.getTime());
                cal.add(Calendar.DATE, 7 - cal.get(Calendar.DAY_OF_WEEK));
                endDate = sdf.format(cal.getTime());
            }

            SOMap dtParam = new SOMap();
            dtParam.put("siteid", param.getStr("siteid"));
            dtParam.put("startdate", startDate);
            dtParam.put("enddate", endDate);
            dtParam.put("startpage", 0);
            dtParam.put("endpage", 10);

            if("CART".equals(type)){
                list = comCartMapper.selectCartStatsList(dtParam);
            } else if("CLAIM".equals(type)){
                dtParam.put("gubun", "GOODS");
                list = claimGoodsMapper.selectStatsClaimRankByGoodsList(dtParam);
            } else if("GOODS_SALE".equals(type)){
                dtParam.put("gubun", "GOODS");
                dtParam.put("standard", param.getStr("standard"));
                list = comOrderMapper.selectStatsProductRankByList(dtParam);
            } else if("GOODS_CATE".equals(type)){
                dtParam.put("gubun", "depth1");
                dtParam.put("standard", param.getStr("standard"));
                list = comOrderMapper.selectStatsCategoryRankByList(dtParam);
            }
        }
        return list;
    }

    //여기선 일별, 주별의 그래프 데이터를 다룸
    public SOMap getAccountData(SOMap param){
        SOMap result = new SOMap();

        SOMap account = comOrderMapper.selectOrderClaimAccountDashBoard(param);
        result.put("account", account);
        
        SOMap graphMap = new SOMap();
        graphMap.put("recent", Integer.parseInt(account.getStr("saleamt").replaceAll(",", "")));
        if(!"MONTH".equals(param.getStr("saletype"))){
            param.put("next", true);
            SOMap nextAccount = comOrderMapper.selectOrderClaimAccountDashBoard(param);
            graphMap.put("next", Integer.parseInt(nextAccount.getStr("saleamt").replaceAll(",", "")));

            SOMap calcParam = new SOMap();
            calcParam.putAll(param);

            int addday = ("WEEK".equals(calcParam.getStr("saletype"))) ? -27 : -6;
            int divide = ("WEEK".equals(calcParam.getStr("saletype"))) ? 4 : 7;

            calcParam.put("addday", addday);
            calcParam.put("divide", divide);

            calcParam.put("saletype", "CALC");
            SOMap calcAccount = comOrderMapper.selectOrderClaimAccountCalcDashBoard(calcParam);
            graphMap.put("calcamt", Integer.parseInt(calcAccount.getStr("calcamt").replaceAll(",", "")));
        } else {
            graphMap.put("calcamt", 0);
            param.put("isyear", true);
            List<SOMap> yearCalcList  = comOrderMapper.selectYearCalcListDashBoard(param);
            int totAmt = yearCalcList.stream().mapToInt(a-> a.getInt("saleamt")).sum();
            result.put("yeargraph", yearCalcList);
            result.put("totamt", totAmt);
        }

        result.put("graph", graphMap);

        return result;
    }
}
