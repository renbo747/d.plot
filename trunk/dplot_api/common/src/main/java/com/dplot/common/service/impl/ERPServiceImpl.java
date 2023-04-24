package com.dplot.common.service.impl;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CommonOrderService;
import com.dplot.common.service.DeliveryTrackingService;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.*;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.HttpConnectionUtil;
import com.dplot.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ERPServiceImpl extends MallBaseService implements ERPService {

    private static final Logger logger = LoggerFactory.getLogger(ERPServiceImpl.class);

    @Autowired
    private DealerMapper dealerMapper;

    @Autowired
    private DealerChargeMapper dealerChargeMapper;

    @Autowired
    private DelivTemplateMapper delivTemplateMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsOptionDetailMapper goodsOptionDetailMapper;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private OptionErpMapper optionErpMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GoodsKccertMapper goodsKccertMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberSleepMapper memberSleepMapper;

    @Autowired
    private IFLogMapper ifLogMapper;

    @Autowired
    private ChargemdMapper chargemdMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponInfoMapper couponInfoMapper;

    @Autowired
    private CouponGoodsMapper couponGoodsMapper;

    @Autowired
    private CouponCateMapper couponCateMapper;

    @Autowired
    private CouponOrdGoodsMapper couponOrdGoodsMapper;

    @Autowired
    private CouponOrdCateMapper couponOrdCateMapper;

    @Autowired
    private CouponMemberMapper couponMemberMapper;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private PromoCateMapper promoCateMapper;

    @Autowired
    private PromoGoodsMapper promoGoodsMapper;

    @Autowired
    private PromoGiftMapper promoGiftMapper;

    @Autowired
    private ReservePayMapper reservePayMapper;

    @Autowired
    private ComOrderMapper comOrderMapper;

    @Autowired
    private ComOrderGoodsMapper comOrderGoodsMapper;

    @Autowired
    private TossreceiptMapper tossreceiptMapper;

    @Autowired
    private ComOrderGoodsGiftMapper comOrderGoodsGiftMapper;

    @Autowired
    private ComOrderCouponMapper comOrderCouponMapper;

    @Autowired
    private ComOrderGoodsDelivMapper comOrderGoodsDelivMapper;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private ClaimGoodsMapper claimGoodsMapper;

    @Autowired
    private GiftMapper giftMapper;

    @Autowired
    private GiftErpMapper giftErpMapper;

    @Autowired
    private ClaimGoodsGiftMapper claimGoodsGiftMapper;

    @Autowired
    private ClaimCouponMapper claimCouponMapper;

    @Autowired
    private ExchangeHistMapper exchangeHistMapper;

    @Autowired
    private CommonOrderService commonOrderService;

    @Autowired
    private DeliveryTrackingService deliveryTrackingService;

    @Resource(name="propertiesFactory")
    private Properties prop;

    @Override
    public void sendPartnersERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageVendor");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> header = getERPHeader();
        List<SOMap> partnerList = dealerMapper.selectPartnsersERPData(param);

        if(partnerList.size() > 0){
            List<SOMap> erpList = normalizationList(partnerList, "userno");

            for(SOMap row : erpList){
                row.put("chargelist", dealerChargeMapper.selectDealerChargeListERP(row));
                row.put("delivtemplatelist", delivTemplateMapper.selectDelivTemplateListERP(row));
            }

            map.put("partnerlist", erpList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", map, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, partnerList, map);
            }
        }
    }

    @Override
    public void sendGoodsERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageItem");

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> header = getERPHeader();
        List<SOMap> goodsList = goodsMapper.selectGoodsERPData(param);
        if(goodsList.size() > 0){
            List<SOMap> erpList = normalizationList(goodsList, "goodsno");

            for(SOMap row : erpList){
                List<SOMap> optionDetailList = goodsOptionDetailMapper.selectGoodsOptionDetailListERP(row);
                for(SOMap subRow : optionDetailList){
                    subRow.put("goodserpmaplist", optionErpMapper.selectOptionErpListERP(subRow));
                }
                row.put("goodsoptiondetaillist", optionDetailList);
                row.put("goodscategorylist", goodsCategoryMapper.selectGoodsCategoryListERP(row));
                row.put("goodskccertlist", goodsKccertMapper.selectGoodsKccertListERP(row));
                row.put("goodsimagelist", fileMapper.selectGoodsImageERP(row));
            }
            map.put("goodslist", erpList);

//            JSONObject json = new JSONObject();
//            json.putAll(map);
//            System.out.println(json.toString());

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", map, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, goodsList, map);
            }
        }
    }

    @Override
    public void sendCategoryERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageDisplayCategory");

        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();
        SOMap dbParam = new SOMap();

        dbParam.put("siteid", param.get("siteid"));
        dbParam.put("type", CMConst.ERP_DISPLAY_CATEGORY_TYPE);

        List<SOMap> categoryList = categoryMapper.selectCategoryListERP(dbParam);
        if(categoryList.size() > 0){
            data.put("categorylist", categoryList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, categoryList, data);
            }
        }
    }

    @Override
    public void sendMemberERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageMember");

        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();
        SOMap dbParam = new SOMap();

        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> memberList = memberMapper.selectMemberERPData(dbParam);
        if(memberList.size() > 0) {
            List<SOMap> erpList = normalizationList(memberList, "userno");
            data.put("userlist", erpList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, memberList, data);
            }
        }
    }

    @Override
    public void sendResignMemberERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/withdrawMember");

        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();
        SOMap dbParam = new SOMap();

        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> memberResignList = memberMapper.selectResignMemberERPData(dbParam);
        if(memberResignList.size() > 0) {
            List<SOMap> erpList = normalizationList(memberResignList, "userno");
            data.put("userlist", erpList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, memberResignList, data);
            }
        }
    }

    @Override
    public void sendCouponERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageCoupon");

        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();
        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));
        List<SOMap> couponList = couponMapper.selectCouponERPData(dbParam);
        if(couponList.size() > 0) {
            List<SOMap> erpList = normalizationList(couponList, "cpninfoidx");
            for(SOMap row : erpList){
                row.put("cpninfolist", couponInfoMapper.selectCouponInfoERPData(row));
                row.put("cpngoodslist", couponGoodsMapper.selectCouponGoodsERPData(row));
                row.put("cpncatelist", couponCateMapper.selectCouponCateERPData(row));
                row.put("cpnordgoodslist", couponOrdGoodsMapper.selectCouponOrdGoodERPData(row));
                row.put("cpnordcatelist", couponOrdCateMapper.selectCouponOrdCateERPData(row));
                row.put("cpnuseuserlist", couponMemberMapper.selectCouponMemberERPData(row));
            }
            data.put("cpnlist", erpList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, couponList, data);
            }
        }
    }

    @Override
    public void sendPromotionERPData(SOMap param) {
        String url = getERPUrl("/shop/dplot/managePromotion");

        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();
        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> promotionList = promotionMapper.selectPromotionERPData(dbParam);
        if(promotionList.size() > 0) {
            List<SOMap> erpList = normalizationList(promotionList, "promoidx");
            for(SOMap row : erpList){
                row.put("promocatelist", promoCateMapper.selectPromoCateERPData(row));
                row.put("promogoodslist", promoGoodsMapper.selectPromoGoodsERPData(row));
                row.put("promogiftlist", promoGiftMapper.selectPromoGiftERPData(row));
            }
            data.put("promolist", erpList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, promotionList, data);
            }
        }

    }

    @Override
    public void sendReserveERPData(SOMap param) {
        String url = getERPUrl("/shop/dplot/manageMileage");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();
        SOMap dbParam = new SOMap();

        List<SOMap> reserveList = reservePayMapper.selectReserveEPointERPData(dbParam);
        if(reserveList.size() > 0) {
            for (SOMap row : reserveList) {
                String type = row.getStr("pointtype");
                if ("E".equals(type)) {
                    row.put("uselist", reservePayMapper.selectEPointUseERPData(row));
                } else {
                    row.put("uselist", reservePayMapper.selectReserveUseERPData(row));
                }
            }

            data.put("list", reserveList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, reserveList, data);
            }
        }

    }

    @Override
    public void sendOrderERPData(SOMap param) throws Exception {
        String url =getERPUrl("/shop/dplot/createOrder");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        param.put("type", CMConst.ERP_ORDER_INSERT_TYPE);
        List<SOMap> orderList = ifLogMapper.selectIFLogList(param);

        if(orderList.size() > 0) {
            List<SOMap> orderData = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();

            for(SOMap row : orderList){
                String note = row.getStr("note");
                if(!"".equals(note)) {
                    SOMap orderInfo = mapper.readValue(note, SOMap.class);
                    orderInfo.put("aud_type_cd", row.get("auddiv"));
                    orderData.add(orderInfo);
                }
            }

            data.put("orderlist", orderData);

            JSONObject json = new JSONObject();
            json.putAll(data);
            System.out.println(json);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                if("1".equals(result)){
                    erpSendAfterProcessForOrderAndClaim(orderList);
                }
            }
        }

    }

    @Override
    public void sendOrderDeliveryInfoERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/updateOrder");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();
        SOMap dbParam = new SOMap();

        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> orderInfoList = comOrderMapper.selectOrderDeliveryInfoERPData(dbParam);

        if(orderInfoList.size() > 0) {
            List<SOMap> erpList = normalizationList(orderInfoList, "orderidx");
            data.put("orderlist", erpList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, orderInfoList, data);
            }
        }
    }

    @Override
    public void sendOrderGoodsDeliveryData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/updateShipmentStatus");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        param.put("type", CMConst.ERP_ORDER_DELIVERY_UPDATE_TYPE);
        List<SOMap> orderDeliveryStateList = ifLogMapper.selectIFLogList(param);

        if(orderDeliveryStateList.size() > 0) {
            List<SOMap> orderData = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();

            for(SOMap row : orderDeliveryStateList){
                String note = row.getStr("note");
                if(!"".equals(note)) {
                    SOMap orderInfo = mapper.readValue(note, SOMap.class);
                    orderInfo.put("aud_type_cd", row.get("auddiv"));
                    orderData.add(orderInfo);
                }
            }

            data.put("deliverylist", orderData);

            JSONObject json = new JSONObject();
            json.putAll(data);
            System.out.println(json.toString());

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                if("1".equals(result)){
                    erpSendAfterProcessForOrderAndClaim(orderDeliveryStateList);
                }
            }
        }
    }

    @Override
    public void sendOrderConfirmERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/updateOrderStatus");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> orderConfirmList = comOrderGoodsDelivMapper.selectOrderConfirmERPData(dbParam);
        if(orderConfirmList.size() > 0) {
            List<SOMap> erpList = normalizationList(orderConfirmList, "orgdelividx");
            data.put("orderlist", erpList);

            JSONObject json = new JSONObject();
            json.putAll(data);
            System.out.println(json.toString());

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, orderConfirmList, data);
            }
        }
    }

    @Override
    public void sendOrderCancelERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/createCancellation");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> claimCompleteList = claimMapper.selectOrderCancelERPData(dbParam);

        if(claimCompleteList.size() > 0) {
            List<SOMap> erpList = normalizationList(claimCompleteList, "orderidx");
            List<SOMap> sendData = new ArrayList<>();
            for(SOMap row : erpList){

                SOMap cancelParam = new SOMap();
                cancelParam.put("clmidx", row.getStr("clmidx"));
                cancelParam.put("orderidx", row.getStr("orderidx"));
                cancelParam.put("orgno", row.getStr("orderidx"));
                cancelParam.put("siteid", param.get("siteid"));
                cancelParam.put("status", "ERP");

                SOMap orderInfo = comOrderMapper.selectOrderERPData(cancelParam);

                orderInfo.put("iflogidx", row.get("iflogidx"));
                orderInfo.put("aud_type_cd", "A");
                orderInfo.put("clmidxarr", row.getStr("clmidxarr"));
                orderInfo.put("clmgdidxarr", row.getStr("clmgdidxarr"));
                orderInfo.put("all_cancel", (row.getInt("cnt") > 0) ? "F" : "T");
                //주문데이터가져와서 다시 아래 쿼리로 가져온것 넣어주어야함 재계산된 금액
                SOMap orderInfoCalc = claimMapper.selectOrderClaimApplyInfo(cancelParam);

                //금액
                orderInfo.put("ordtotprice", orderInfoCalc.get("ordtotprice")); //주문총금액
                orderInfo.put("rpaytotprice", orderInfoCalc.get("rpaytotprice")); //실결제 총 금액
                orderInfo.put("paytotprice", orderInfoCalc.get("paytotprice")); //결제 총 금액
                orderInfo.put("dadadelivamt", orderInfoCalc.get("dadadelivamt")); //직매입배송비
                orderInfo.put("ptndelivamt", orderInfoCalc.get("ptndelivamt")); //파트너사배송비
                //쿠폰
                orderInfo.put("totsalepromoamt", orderInfoCalc.get("totsalepromoamt"));
                orderInfo.put("totgoodscpnamt", orderInfoCalc.get("totgoodscpnamt"));
                orderInfo.put("totdelivcpnamt", orderInfoCalc.get("totdelivcpnamt"));
                orderInfo.put("basketcpnamt", orderInfoCalc.get("basketcpnamt"));
                //적립금
                orderInfo.put("reservetotamt", orderInfoCalc.get("reservetotamt"));
                orderInfo.put("epointtotamt", orderInfoCalc.get("epointtotamt"));
                orderInfo.put("empreservetotamt", orderInfoCalc.get("empreservetotamt"));

                //추가배송비, 반환배송비
                orderInfo.put("totadddelivamt", orderInfoCalc.get("totadddelivamt"));
                orderInfo.put("totrtndelivamt", orderInfoCalc.get("totrtndelivamt"));


                //추가 배송비 및 반환배송비 계산해야함 상품(해당 항목들 배송비에서 추가해줘야 하는 것인지 빼줘야 하는것인지)
                List<SOMap> goodsList = claimGoodsMapper.selectOrderCancelGoodsERPData(cancelParam);
                //클레임 제외한 쿠폰 리스트 가져옴
                List<SOMap> couponList = claimCouponMapper.selectClaimCouponERPData(cancelParam);
                //사은품 리스트
                cancelParam.put("imgtype", CMConst.IMG_TYPE_GIFT_IMG_PC_B);
                List<SOMap> giftList = claimGoodsGiftMapper.selectClaimGoodsGiftWithoutClaim(cancelParam);
                //결제 정보
                List<SOMap> receiptInfo = tossreceiptMapper.selectClaimReceiptERPData(cancelParam);

                orderInfo.put("receiptinfo", receiptInfo);
                orderInfo.put("goodslist", goodsList); //주문상품
                orderInfo.put("giftinfo", giftList); // 사은품 리스트
                orderInfo.put("couponinfo", couponList); //쿠폰리스트

                sendData.add(orderInfo);
            }

            data.put("cancellist", sendData);

            JSONObject json = new JSONObject();
            json.putAll(data);
            System.out.println(json);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, claimCompleteList, data);
            }
        }
    }

    @Override
    public void sendGiftGoodsERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageGiftItem");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> giftList = giftMapper.selectGiftERPData(dbParam);
        if(giftList.size() > 0) {
            List<SOMap> erpList = normalizationList(giftList, "giftidx");

            for(SOMap row : erpList){
               row.put("giftorglist", giftErpMapper.selectGiftERPSendData(row));
            }

            data.put("giftlist", erpList);

            Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
            if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
                String result = sendResponse.get("result").toString();
                erpSendAfterProcess(result, giftList, data);
            }
        }
    }

    @Override
    @Transactional
    public void receiveBrandERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageBrand");
//        Map<String, Object> data = getFromToOneHour();
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        data.put("from_ymd", "");
        data.put("from_hms", "");
        data.put("to_ymd", "");
        data.put("to_hms", "");

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            if("1".equals(result)){
                List<Map<String, Object>> brandList = (ArrayList) sendResponse.get("data");
                List<String> brCodeList = brandMapper.selectBrandCodeList(param);
                List<Map<String, Object>> insertBrandList = brandList.stream().filter(a-> {
                            String audCode = a.get("aud_type_cd").toString();
                            return (!brCodeList.contains(a.get("brand_code").toString())) && "A".equals(audCode);
                        }
                ).collect(Collectors.toList());
                List<Map<String, Object>> updateBrandList = brandList.stream().filter(a-> {
                            String audCode = a.get("aud_type_cd").toString();
                            return (!brCodeList.contains(a.get("brand_code").toString())) && "U".equals(audCode);
                        }
                ).collect(Collectors.toList());

                System.out.println(":::::::::::::::::::::::::::::::::::::::::::");
                System.out.println(":::::::::::::::::::::::::::::::::::::::::::");
                insertBrandList.forEach(System.out::println);
                updateBrandList.forEach(System.out::println);
                System.out.println(":::::::::::::::::::::::::::::::::::::::::::");
                System.out.println(":::::::::::::::::::::::::::::::::::::::::::");

                SOMap dbParam = new SOMap();
                dbParam.put("siteid", param.get("siteid"));
                if(insertBrandList.size() > 0) {
                    dbParam.put("list", insertBrandList);
                    brandMapper.insertBrandERPData(dbParam);
                }

                if(updateBrandList.size() > 0) {
                    dbParam.put("updatelist", updateBrandList);
                    for(Map<String, Object> row : updateBrandList){
                        SOMap updateParam = new SOMap();
                        updateParam.putAll(row);
                        brandMapper.updateBrandERPData(updateParam);
                    }
                }

                SOMap responseResult = erpResponse("DDMC_SHOP_BASIC_BRAND", brandList, header);
                logger.info("DDMC_SHOP_BASIC_BRAND - RESPONSE ::: ", responseResult.getStr("result"));

            }
        }
    }

    //사용안함
    @Override
    public void receiveManufacturerERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageManufacturer");
        Map<String, Object> data = getFromToOneHour();
        Map<String, Object> header = getERPHeader();

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            if("1".equals(result)){
                List<Map<String, Object>> manufacturerList = (ArrayList) sendResponse.get("data");
                manufacturerList.forEach(System.out::println);
            }
        }
    }

    //사용 안함
    @Override
    public void receiveCategoryERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageCategory");
        Map<String, Object> data = getFromToOneHour();
        Map<String, Object> header = getERPHeader();

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            if("1".equals(result)){
                List<Map<String, Object>> baseCategoryListList = (ArrayList) sendResponse.get("data");
                baseCategoryListList.forEach(System.out::println);
            }
        }
    }

    @Override
    public void receiveOrderDeliveryInfoERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/updateShipmentStatusToDplot");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        data.put("from_ymd", "");
        data.put("from_hms", "");
        data.put("to_ymd", "");
        data.put("to_hms", "");

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())) {
            String result = sendResponse.get("result").toString();
            if ("1".equals(result)) {
                List<Map<String, Object>> deliveryList = (ArrayList) sendResponse.get("data");

                List<SOMap> convertList = new ArrayList<>();
                for(Map<String, Object> deliv : deliveryList){
                    List<Map<String, Object>> goodsList = (ArrayList) deliv.get("goods_list");
                    goodsList.forEach(goods -> {
                        SOMap convertGoods = new SOMap();
                        convertGoods.putAll(goods);
                        convertGoods.put("invoice_no", deliv.get("invoice_no"));
                        convertGoods.put("logis_type", deliv.get("logis_type"));
                        convertGoods.put("order_status", deliv.get("order_status"));
                        convertGoods.put("box_qty", deliv.get("box_qty"));
                        convertGoods.put("erp_key", deliv.get("erp_key"));
                        convertGoods.put("claim_type", deliv.get("claim_type"));
                        convertList.add(convertGoods);


                    });

                    SOMap traceMap = new SOMap();

                    String orderIdx = goodsList.get(0).get("order_idx").toString();
                    SOMap rcvNameParam = new SOMap();
                    rcvNameParam.put("orderidx", orderIdx);
                    SOMap orderInfo = comOrderMapper.selectComOrderInfo(rcvNameParam);

                    if(!Util.isEmpty(orderInfo)) {

                        String logiscode = deliveryTrackingService.getGoodsFlowLogisCode(deliv.get("logis_type").toString());
                        traceMap.put("trans_unique_code", String.format("%s%s_%s", DateTimeUtil.getNowDatePartShortStr(), deliv.get("invoice_no").toString(), logiscode));
                        traceMap.put("from_name", "DPLOT");
                        traceMap.put("logistics_code", logiscode);
                        traceMap.put("invoice_no", deliv.get("invoice_no").toString());
                        traceMap.put("dlvret_type", "D");
                        traceMap.put("to_name", orderInfo.getStr("rcvname"));

                        int index = 0;
                        List<SOMap> sendGoodsList = new ArrayList<>();
                        for (Map<String, Object> map : goodsList) {
                            SOMap rowData = new SOMap();
                            rowData.put("item_qty", map.get("qty"));
                            rowData.put("item_name", orderInfo.get("goodsname"));
                            rowData.put("order_line", ++index);
                            rowData.put("order_no", orderInfo.getStr("ordno"));
                            rowData.put("def_code1", "ERP_DELIVERY");


                            if (Util.equal(prop.getProperty("Globals.Profile"), "prod")) {
                                rowData.put("order_date", DateTimeUtil.getFormatStr(orderInfo.getStr("orderdate"), DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                                rowData.put("payment_date", DateTimeUtil.getFormatStr(orderInfo.getStr("paymentdate"), DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                            } else {
                                // 로컬, 개발서버 배송추적요청 테스트를 위한 날짜 임의 세팅
                                rowData.put("order_date", DateTimeUtil.getFormatStr("20220401000000", DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                                rowData.put("payment_date", DateTimeUtil.getFormatStr("20220401000000", DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                            }

                            sendGoodsList.add(rowData);
                        }

                        SOMap sendResult = deliveryTrackingService.sendTraceRequest(traceMap, sendGoodsList);
                        System.out.println(sendResult);
                    }
                }

                try {
                    int ordUpdateCnt = commonOrderService.updateOrderStatusByERPData(convertList);
                    int exchangeUpdateCnt = commonOrderService.updateExchangeStatusByERPData(convertList);

                    erpResponse("DDMC_SHOP_SHIPMENT_UPDATE2", deliveryList, header);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void receiveReturnDeliveryInfoERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/updateReturnToDplot");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        data.put("from_ymd", "");
        data.put("from_hms", "");
        data.put("to_ymd", "");
        data.put("to_hms", "");

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())) {
            String result = sendResponse.get("result").toString();
            if ("1".equals(result)) {
                List<Map<String, Object>> deliveryList = (ArrayList) sendResponse.get("data");

                List<SOMap> convertList = new ArrayList<>();
                for(Map<String, Object> deliv : deliveryList){
                    List<Map<String, Object>> goodsList = (ArrayList) deliv.get("goods_list");
                    goodsList.forEach(goods -> {
                        SOMap convertGoods = new SOMap();
                        convertGoods.putAll(goods);
                        convertGoods.put("invoice_no", deliv.get("invoice_no"));
                        convertGoods.put("logis_type", deliv.get("logis_type"));
                        convertGoods.put("order_status", deliv.get("order_status"));
                        convertGoods.put("box_qty", deliv.get("box_qty"));
                        convertGoods.put("erp_key", deliv.get("erp_key"));
                        convertGoods.put("claim_type", deliv.get("claim_type"));
                        convertList.add(convertGoods);


                    });

                    SOMap traceMap = new SOMap();

                    String orderIdx = goodsList.get(0).get("order_idx").toString();
                    SOMap rcvNameParam = new SOMap();
                    rcvNameParam.put("orderidx", orderIdx);
                    SOMap orderInfo = comOrderMapper.selectComOrderInfo(rcvNameParam);

                    if(!Util.isEmpty(orderInfo)) {

                        String logiscode = deliveryTrackingService.getGoodsFlowLogisCode(deliv.get("logis_type").toString());
                        traceMap.put("trans_unique_code", String.format("%s%s_%s", DateTimeUtil.getNowDatePartShortStr(), deliv.get("invoice_no").toString(), logiscode));
                        traceMap.put("from_name", orderInfo.getStr("rcvname"));
                        traceMap.put("logistics_code", logiscode);
                        traceMap.put("invoice_no", deliv.get("invoice_no").toString());
                        traceMap.put("dlvret_type", "D");
                        traceMap.put("to_name", "DPLOT");

                        int index = 0;
                        List<SOMap> sendGoodsList = new ArrayList<>();
                        for (Map<String, Object> map : goodsList) {
                            SOMap rowData = new SOMap();
                            rowData.put("item_qty", map.get("qty"));
                            rowData.put("item_name", orderInfo.get("goodsname"));
                            rowData.put("order_line", ++index);
                            rowData.put("order_no", orderInfo.getStr("ordno"));
                            rowData.put("def_code1", "ERP_DELIVERY");


                            if (Util.equal(prop.getProperty("Globals.Profile"), "prod")) {
                                rowData.put("order_date", DateTimeUtil.getFormatStr(orderInfo.getStr("orderdate"), DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                                rowData.put("payment_date", DateTimeUtil.getFormatStr(orderInfo.getStr("paymentdate"), DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                            } else {
                                // 로컬, 개발서버 배송추적요청 테스트를 위한 날짜 임의 세팅
                                rowData.put("order_date", DateTimeUtil.getFormatStr("20220401000000", DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                                rowData.put("payment_date", DateTimeUtil.getFormatStr("20220401000000", DateTimeUtil.MALL_DATE_FORMAT_FULL_SHORT));
                            }

                            sendGoodsList.add(rowData);
                        }

                        SOMap sendResult = deliveryTrackingService.sendTraceRequest(traceMap, sendGoodsList);
                        System.out.println(sendResult);
                    }
                }

                try {
                    commonOrderService.updateReturnStatusByERPData(convertList);
                    erpResponse("DDMC_SHOP_RETURN_UPDATE2", deliveryList, header);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public SOMap selectReceiveERPOrgGoodsList(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/inquireOrgItem");
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> header = getERPHeader();
        data.put("org_item_code", param.getStr("orgcode"));
        data.put("org_item_name", param.getStr("orgname"));

        SOMap resultMap = new SOMap();

        List<SOMap> resultList = new ArrayList<>();
        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            if("1".equals(result)){
                List<Map<String, Object>> orgGoodsList = (ArrayList) sendResponse.get("data");
                resultList = orgGoodsList.stream().map(a-> {
                    SOMap somap = new SOMap();
                    somap.putAll(a);
                    return somap;
                }).collect(Collectors.toList());
            }
        }

        resultMap.put("list", resultList);

        return resultMap;
    }

    @Override
    public SOMap erpGetGoodsAndGiftStock(SOMap param) {
        String url = getERPUrl("/shop/dplot/inquireStock");
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> header = getERPHeader();

        String itemCode = ("T".equals(param.getStr("isgift"))) ? param.getStr("giftidx") : param.getStr("goodsno");
        String unitCode = ("T".equals(param.getStr("isgift"))) ? "" : param.getStr("optioncode");

        data.put("item_code", itemCode);
        data.put("unit_code", unitCode);
        data.put("is_gift", param.getStr("isgift"));

        SOMap resultMap = new SOMap();
        resultMap.putAll(param);

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            if("1".equals(result)){
                List<Map<String, Object>> goodsStockList = (ArrayList) sendResponse.get("data");
                if(goodsStockList.size() > 0){
                    resultMap.putAll(goodsStockList.get(0));
                    resultMap.put("exist", 'T');
                } else {
                    resultMap.put("exist", 'F');
                    resultMap.put("stock_qty", 0);
                    resultMap.put("avail_qty", 0);
                }
            }
        }

        return resultMap;
    }

    @Override
    public void receiveMDCodeERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/manageMd");
//        Map<String, Object> data = getFromToOneHour();
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        data.put("from_ymd", "");
        data.put("from_hms", "");
        data.put("to_ymd", "");
        data.put("to_hms", "");
        data.put("inquire_type", param.getStr("inquire_type"));

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);

        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            if("1".equals(result)){
                List<Map<String, Object>> mdCodeList = (ArrayList) sendResponse.get("data");
                if(mdCodeList.size() > 0) {
                    for(Map<String, Object> mdRow : mdCodeList){
                        SOMap mdParam = new SOMap();
                        mdParam.putAll(mdRow);

                        int existCnt = chargemdMapper.selectMdCount(mdParam);

                        if(existCnt > 0){
                            chargemdMapper.updateMdInfo(mdParam);
                        } else {
                            chargemdMapper.insertMdInfo(mdParam);
                        }
                    }

                    SOMap responseResult = erpResponse("DDMC_SHOP_BASIC_MD", mdCodeList, header);
                    logger.info("DDMC_SHOP_BASIC_MD - RESPONSE ::: ", responseResult.getStr("result"));
                }
            }
        }
    }

    @Override
    public int insertPartnershipERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("orgno", param.get("userno"));
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("type", CMConst.ERP_PARTNERS_TYPE);
        erpParam.put("aud", param.get("aud"));
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertCategoryERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("orgno", param.get("idx"));
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("type", CMConst.ERP_DISPLAY_CATEGORY_TYPE);
        erpParam.put("aud", param.get("aud"));
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertMemberERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgno", param.get("userno"));
        erpParam.put("type", CMConst.ERP_MEMBER_TYPE);
        erpParam.put("aud", param.get("aud"));
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertResignMemberERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgno", param.get("userno"));
        erpParam.put("type", CMConst.ERP_MEMBER_RESIGN_TYPE);
        erpParam.put("aud", param.get("aud"));
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertOrderCancelERPData(SOMap param){
        SOMap erpParam = new SOMap();
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgno", param.get("clmidx"));
        erpParam.put("type", CMConst.ERP_ORDER_CANCEL_TYPE);
        erpParam.put("aud", CMConst.IF_LOG_ADD);
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertOrderReturnApplyERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgno", param.get("clmidx"));
        erpParam.put("type", CMConst.ERP_RETURN_APPLY_TYPE);
        erpParam.put("aud", CMConst.IF_LOG_ADD);
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertOrderExchangeApplyERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgno", param.get("clmidx"));
        erpParam.put("type", CMConst.ERP_EXCHANGE_APPLY_TYPE);
        erpParam.put("aud", CMConst.IF_LOG_ADD);
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertOrderReturnCompleteERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgno", param.get("clmidx"));
        erpParam.put("type", CMConst.ERP_RETURN_COMPLETE_TYPE);
        erpParam.put("aud", CMConst.IF_LOG_ADD);
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertOrderExchangeCompleteERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgno", param.get("clmidx"));
        erpParam.put("type", CMConst.ERP_EXCHANGE_COMPLETE_TYPE);
        erpParam.put("aud", CMConst.IF_LOG_ADD);
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public void sendReturnApplyERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/createReturn");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));
        dbParam.put("iftype", CMConst.ERP_RETURN_APPLY_TYPE);
        dbParam.put("clmtype", CMConst.CLM_RETURN);
        List<SOMap> list = claimMapper.selectClaimApplyERPData(dbParam);

        for(SOMap exchangeParam : list){
            List<SOMap> giftList = claimGoodsGiftMapper.selectClaimGoodsGiftERPData(exchangeParam);
            exchangeParam.put("giftinfo", giftList);
        }

        data.put("returnlist", list);

        JSONObject json = new JSONObject();
        json.putAll(data);
        System.out.println(json);

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            erpSendAfterProcess(result, list, data);
        }
    }

    @Override
    public void sendExchangeApplyERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/createExchange");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));
        dbParam.put("iftype", CMConst.ERP_EXCHANGE_APPLY_TYPE);
        dbParam.put("clmtype", CMConst.CLM_EXCHANGE);
        List<SOMap> list = claimMapper.selectClaimApplyERPData(dbParam);
        data.put("exchangelist", list);

        JSONObject json = new JSONObject();
        json.putAll(data);
        System.out.println(json);

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            erpSendAfterProcess(result, list, data);
        }
    }

    @Override
    public void sendReturnCompleteERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/updateReturn");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> claimReturnCompleteList = claimMapper.selectReturnCompleteERPData(dbParam);
        for(SOMap row : claimReturnCompleteList){
            List<SOMap> goodsList = claimGoodsMapper.selectReturnGoodsERPData(row);
            List<SOMap> receiptList = new ArrayList<>();
            SOMap tossReceipt = tossreceiptMapper.selectTossreceipt(row);
            if(!Util.isEmpty(tossReceipt)){
                receiptList.add(tossreceiptMapper.selectTossreceipt(row));
            }

            SOMap returnParam = new SOMap();
            returnParam.putAll(row);
            returnParam.put("imgtype", CMConst.IMG_TYPE_GIFT_IMG_PC_B);
            returnParam.put("status", "ERP_RETURN");
//            List<SOMap> giftList = claimGoodsGiftMapper.selectClaimGoodsGiftWithoutClaim(returnParam);
            List<SOMap> giftList = claimGoodsGiftMapper.selectClaimGoodsGiftERPData(returnParam);
            List<SOMap> couponInfo = claimCouponMapper.selectReturnCouponERPData(returnParam);

            row.put("receiptinfo", receiptList);
            row.put("goodslist", goodsList); //주문상품
            row.put("giftinfo", giftList); // 사은품 리스트
            row.put("couponinfo", couponInfo); //쿠폰리스트
        }

        data.put("returnlist", claimReturnCompleteList);

        JSONObject json = new JSONObject();
        json.putAll(data);
        System.out.println(json);

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            erpSendAfterProcess(result, claimReturnCompleteList, data);
        }
    }

    @Override
    public void sendExchangeCompleteERPData(SOMap param) throws Exception {
        String url = getERPUrl("/shop/dplot/updateExchange");
        Map<String, Object> header = getERPHeader();
        Map<String, Object> data = new HashMap<>();

        SOMap dbParam = new SOMap();
        dbParam.put("siteid", param.get("siteid"));

        List<SOMap> claimExchangeCompleteList = claimMapper.selectExchangeCompleteERPData(dbParam);
        data.put("exchangelist", claimExchangeCompleteList);

        JSONObject json = new JSONObject();
        json.putAll(data);
        System.out.println(json);

        Map<String, Object> sendResponse = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(sendResponse != null && sendResponse.containsKey("httpCode") && "200".equals(sendResponse.get("httpCode").toString())){
            String result = sendResponse.get("result").toString();
            erpSendAfterProcess(result, claimExchangeCompleteList, data);
        }

    }

    @Override
    public void sendERPServiceForBatch(SOMap param) {
        String environment = prop.getProperty("Globals.Profile");

        try {
            logger.info(":::::::::::::::::::::::::::::: ERP SCHEDULE START ::::::::::::::::::::::::::::::");
            if("dev".equals(environment)){
                receiveBrandERPData(param);
                receiveMDCodeERPData(param);

                sendCategoryERPData(param); //전시 카테고리  등록/수정
                sendPartnersERPData(param); //파트너사 등록/수정
                sendMemberERPData(param); //회원 등록/수정/휴면
                sendResignMemberERPData(param); //회원탈퇴
                sendGoodsERPData(param); //상품 등록/수정
                sendCouponERPData(param); //쿠폰 등록/수정
                sendPromotionERPData(param); //프로모션 등록/수정
                sendReserveERPData(param); //적립금 지급/사용/소멸
                sendGiftGoodsERPData(param); //사은품 등록/수정/삭제 전달

                //주문관련
                sendOrderERPData(param); //주문생성
                sendOrderDeliveryInfoERPData(param); //주문정보변경
                sendOrderCancelERPData(param); //주문취소
                sendOrderGoodsDeliveryData(param); //주문상태변경
                sendExchangeApplyERPData(param); //교환신청접수
                sendReturnApplyERPData(param); //반품신청접수
                sendExchangeCompleteERPData(param); //교환완료
                sendReturnCompleteERPData(param); //반품완료
                sendOrderConfirmERPData(param);  //구매확정전달

                receiveOrderDeliveryInfoERPData(param); //배송상태변경(gTRUST - DPLOT)
                receiveReturnDeliveryInfoERPData(param); //반품상태수정(gTRUST - DPLOT)
            }

            if("prod".equals(environment)){
                receiveBrandERPData(param);
                receiveMDCodeERPData(param);

                sendCategoryERPData(param); //전시 카테고리  등록/수정
                sendPartnersERPData(param); //파트너사 등록/수정
                sendMemberERPData(param); //회원 등록/수정/휴면
                sendResignMemberERPData(param); //회원탈퇴
                sendGoodsERPData(param); //상품 등록/수정
                sendCouponERPData(param); //쿠폰 등록/수정
                sendPromotionERPData(param); //프로모션 등록/수정
                sendReserveERPData(param); //적립금 지급/사용/소멸
                sendGiftGoodsERPData(param); //사은품 등록/수정/삭제 전달

                //주문관련
                sendOrderERPData(param); //주문생성
                sendOrderDeliveryInfoERPData(param); //주문정보변경
                sendOrderCancelERPData(param); //주문취소
                sendOrderGoodsDeliveryData(param); //주문상태변경
                sendExchangeApplyERPData(param);
                sendReturnApplyERPData(param);
                sendExchangeCompleteERPData(param);
                sendReturnCompleteERPData(param);
                sendOrderConfirmERPData(param);  //구매확정전달

//                receiveOrderDeliveryInfoERPData(param); //배송상태변경(gTRUST - DPLOT)
//                receiveReturnDeliveryInfoERPData(param); //반품상태수정(gTRUST - DPLOT)
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int insertOrderERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgno", param.get("orgno"));
        erpParam.put("type", CMConst.ERP_ORDER_INSERT_TYPE);
        erpParam.put("aud", CMConst.IF_LOG_ADD);

        SOMap comOrder = comOrderMapper.selectOrderERPData(erpParam);

        comOrder.put("goodslist", comOrderGoodsMapper.selectOrderGoodsERPData(erpParam)); //주문상품
        comOrder.put("receiptinfo", tossreceiptMapper.selectOrderReceiptERPData(erpParam)); //결제정보
        //상품 ORDGDIDX 리스트 전달
        comOrder.put("giftinfo", comOrderGoodsGiftMapper.selectOrderGiftERPData(erpParam)); // 사은품 리스트
        comOrder.put("couponinfo", comOrderCouponMapper.selectOrderCouponERPData(erpParam));

        JSONObject json = new JSONObject();
        json.putAll(comOrder);
        String note = json.toString();
        erpParam.put("note", note);
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    @Override
    public int insertOrderDeliveryStateERPData(SOMap param) {
        int resultCnt = 0;

        if(param.containsKey("orgdelividxlist")){
            List<String> list = (List<String>) param.get("orgdelividxlist");
            //하나씩 처리해야함
            if(list.size() > 0) {
                List<SOMap> dataList = comOrderGoodsDelivMapper.selectOrderDelivERPData(param);
                for(SOMap row : dataList){
                    String orderGoodsIdx = row.getStr("ordgdidx");
                    List<String> orderGoodsIdxList = Arrays.asList(orderGoodsIdx.split(","));
                    if(orderGoodsIdxList.size() > 0){
                        SOMap dbParam = new SOMap();
                        dbParam.put("list", orderGoodsIdxList);
                        row.put("goodslist", comOrderGoodsMapper.selectComOrderGoodsReverseERPData(dbParam));
                        row.put("aud_type_cd", CMConst.IF_LOG_ADD);

                        JSONObject json = new JSONObject();
                        json.putAll(row);
                        String note = json.toString();

                        SOMap logParam = new SOMap();
                        logParam.put("siteid", param.get("siteid"));
                        logParam.put("type", CMConst.ERP_ORDER_DELIVERY_UPDATE_TYPE);
                        logParam.put("orgno", 0);
                        logParam.put("aud", CMConst.IF_LOG_ADD);
                        logParam.put("note", note);

                        resultCnt += ifLogMapper.insertIfLogERPData(logParam);
                    }
                }
            }
        }
        return resultCnt;
    }

    @Override
    public int insertOrderConfirmERPData(SOMap param) {

        SOMap erpParam = new SOMap();
        if("".equals(param.getStr("siteid"))){
            param.put("siteid", "base");
        }
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("orgnolist", param.get("orgdelividxlist"));
        erpParam.put("type", CMConst.ERP_ORDER_CONFIRM_TYPE);
        erpParam.put("aud", CMConst.IF_LOG_ADD);
        return ifLogMapper.insertIfLogERPData(erpParam);
    }
    
    @Override
    public int insertGiftERPData(SOMap param) {
        SOMap erpParam = new SOMap();
        erpParam.put("orgno", param.get("idx"));
        erpParam.put("siteid", param.get("siteid"));
        erpParam.put("type", CMConst.ERP_GIFT_TYPE);
        erpParam.put("aud", param.get("aud"));
        return ifLogMapper.insertIfLogERPData(erpParam);
    }

    public Map<String, Object> getERPHeader(){
        Map<String, Object> result = new HashMap<>();
        result.put("Content-Type", "application/json;charset=utf-8");
        result.put("Accept-type", "application/json");
        return result;
    }

    public String getERPUrl(String path){
        return String.format("%s%s", prop.getProperty("cj.erp.domain"), path);
    }

    /**
     * AUD 중 D가 없는 처리 과정에서 A와 U가 모두 존재할 경우 A만 처리함
     * 첫번째가 A인 경우만 처리 중간에 다른 데이터가 들어 오는 것들은 고려할 수 없음.
     * (이는 리스트를 가져올때 T_IFLOG 테이블의 등록일자를 고려함)
     */
    public List<SOMap> normalizationList(List<SOMap> list, String key){
        List<SOMap> convertList = new ArrayList<>(list);
        Map<String, List<SOMap>> group = convertList.stream().collect(Collectors.groupingBy(a-> a.get(key).toString()));

        for(Map.Entry<String, List<SOMap>> map : group.entrySet()){
            String rowKey = map.getKey();
            List<SOMap> rowList = map.getValue();
            //A로 시작하는 그룹핑 데이터의 경우 다른 데이터를 모두 제거 (D가 없다는 가정)
            if (rowList.size() > 0 && "A".equals(rowList.get(0).getStr("aud_type_cd"))) {
                convertList.removeIf(a -> (rowKey.equals(a.getStr(key))) && !"A".equals(a.getStr("aud_type_cd")));
            }
        }

        return convertList;
    }

    public Map<String, Object> getFromToOneHour(){
        Map<String, Object> map = new HashMap<>();

        LocalDateTime fDate = LocalDateTime.now().minusHours(1);
        LocalDateTime tDate = LocalDateTime.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatHour = DateTimeFormatter.ofPattern("HHmmss");

        String fromDate = fDate.format(formatDate);
        String fromHour = fDate.format(formatHour);
        String toDate = tDate.format(formatDate);
        String toHour = tDate.format(formatHour);

        map.put("from_ymd", fromDate);
        map.put("from_hms", fromHour);
        map.put("to_ymd", toDate);
        map.put("to_hms", toHour);

        return map;
    }

    public void erpSendAfterProcess(String completeCode, List<SOMap> list, Map<String, Object> data) {
//        SOMap erpParam = new SOMap();
        String ifLogIdxStr = list.stream().map(a-> a.getStr("iflogidx")).collect(Collectors.joining(","));

        String key = data.entrySet().stream().findFirst().map(Map.Entry::getKey).orElse("");
        List<Map<String, Object>> logList = new ArrayList<>();
        if(!"".equals(key)){
            logList = (List<Map<String, Object>>) data.get(key);
        }

        String[] logArr = ifLogIdxStr.split(",");
        for(String idx : logArr){
            SOMap erpParam = new SOMap();
            erpParam.put("idx", idx);
            if("1".equals(completeCode)) {
                erpParam.put("complete", "T");
            } else {
                if(null != logList && logList.size() > 0){
                    List<Map<String, Object>> rowDataList = logList.stream().filter(a-> {
                        boolean result = false;
                        if(a.containsKey("iflogidx")){
                            String[] logIdxArr = a.get("iflogidx").toString().split(",");
                            for(String str : logIdxArr){
                                if(idx.equals(str)){
                                    result =  true;
                                    break;
                                }
                            }
                        }
                        return result;
                    }).collect(Collectors.toList());
                    if(rowDataList.size() > 0){
                        JSONObject rowJson = new JSONObject();
                        rowJson.putAll(rowDataList.get(0));
                        erpParam.put("data", rowJson.toString());
                    }
                }
                erpParam.put("complete", "F");
            }
            ifLogMapper.updateIFLog(erpParam);
        }
    }

    public void erpSendAfterProcessForOrderAndClaim(List<SOMap> list){
        //주문 관련 전송은 일반 과 다름
        SOMap erpParam = new SOMap();
        String ifLogIdxStr = list.stream().map(a-> a.getStr("iflogidx")).collect(Collectors.joining(","));
        erpParam.put("idx", Arrays.asList(ifLogIdxStr.split(",")));
        ifLogMapper.updateIFLogDate(erpParam);
    }

    public SOMap erpResponse(String type, List<Map<String, Object>> list, Map<String, Object> header){
        SOMap result = new SOMap();
        String url = getERPUrl("/shop/dplot/confirmReceive");
        Map<String, Object> data = new HashMap<>();
        data.put("if_id", type);
        data.put("result_list", list);
        Map<String, Object> response = HttpConnectionUtil.httpRequest(url, "POST", data, header);
        if(response != null && response.containsKey("httpCode") && "200".equals(response.get("httpCode").toString())){
            result.putAll(response);
        } else {
            result.put("result", "0");
            result.put("error_msg", "ERP 수신확인 처리 중 에러가 발생 하였습니다.");
        }
        return result;
    }
}
