package com.dplot.common.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.CommonClaimService;
import com.dplot.common.service.CommonOrderService;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.NaverService;
import com.dplot.common.service.TossService;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.mapper.ClaimCouponMapper;
import com.dplot.mapper.ClaimGoodsGiftMapper;
import com.dplot.mapper.ClaimGoodsHistMapper;
import com.dplot.mapper.ClaimGoodsMapper;
import com.dplot.mapper.ClaimMapper;
import com.dplot.mapper.ComCartMapper;
import com.dplot.mapper.ComOrderGoodsDelivMapper;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.CouponMemissueMapper;
import com.dplot.mapper.ExchangeHistMapper;
import com.dplot.mapper.GiftMapper;
import com.dplot.mapper.GoodsOptionDetailMapper;
import com.dplot.mapper.TossreceiptMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;

@Service
public class CommonClaimServiceImpl extends MallBaseService implements CommonClaimService {

	private static final Logger logger = LoggerFactory.getLogger(CommonClaimServiceImpl.class);
	
	@Autowired
	private ClaimMapper claimMapper;
	
	@Autowired
	private ClaimGoodsMapper claimGoodsMapper;
	
	@Autowired
	private ClaimGoodsGiftMapper claimGoodsGiftMapper;
	
	@Autowired
	private ClaimCouponMapper claimCouponMapper;
	
	@Autowired
	private ClaimGoodsHistMapper claimGoodsHistMapper;
	
	@Autowired
	private ComOrderGoodsDelivMapper comOrderGoodsDelivMapper;
		
	@Autowired
	private ExchangeHistMapper exchangeHistMapper;
	
	@Autowired
	private CouponMemissueMapper couponMemissueMapper;
	
	@Autowired
	private TossreceiptMapper tossreceiptMapper;
	
    @Autowired
    private GoodsOptionDetailMapper goodsOptionDetailMapper;
	
    @Autowired
    private ComCartMapper comCartMapper;
    
    @Autowired
    private ComOrderMapper comOrderMapper;
    
    @Autowired
    private GiftMapper giftMapper;
    
	@Autowired
	private FileService fileService;
	
	@Autowired
	private TossService tossService;
	
	@Autowired
	private NaverService naverService;
	
	@Autowired
	private CommonOrderService commonOrderService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ERPService erpService;
	
    @Autowired
    private CJMessageService cjMessageService;
	
    @Resource(name="propertiesFactory")
    private Properties prop;
    
	@Override
	/**
	 * 클레임 신청
	 * orderidx : 주문IDX
	 * claim : Map
	 * 	- orggdidx : 주문상품IDX
	 *  - clmcnt : 크레임수량 
	 */
	public SOMap selectClaimApply(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("imgtype", Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_S);
		param.put("status", "APPLY");
		
		SOMap result = this.calClaim(param);
		
		return result;
	}
	
	@Override
	/**
	 * 클레임 상세
	 * orderidx : 주문IDX
	 * clmidx : 클레임IDX
	 */
	public SOMap selectClaimDetail(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("imgtype", Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_S);
		param.put("status", "DETAIL");
		List<SOMap> claimGoods = claimGoodsMapper.selectClaimGoodsList(param);
		param.put("claim", claimGoods);
		
		SOMap result = this.calClaim(param);
		
		return result;
	}
	
	@Override
	/**
	 * 클레임 완료상세
	 * orderidx : 주문IDX
	 * clmidx : 클레임IDX
	 * claim : 재계산시 필요
	 */
	public SOMap selectCompClaimDetail(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("imgtype", Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_S);
		param.put("status", "COMPLETE");
		List<SOMap> claimGoods = claimGoodsMapper.selectClaimGoodsList(param);
		param.put("claim", claimGoods);
		
		SOMap result = this.calClaim(param);
		
		return result;
	}
	
	/**
	 * 클레임 계산
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private SOMap calClaim(SOMap param) throws Exception {
		SOMap result = new SOMap();
		
		/**
		 * 이전클레임 정보조회(이전클레임 포함)
		 */
		SOMap bforder = claimMapper.selectOrderClaimApplyInfo(param);
		
		/**
		 * 클레임 신청상품목록조회
		 */
		SOMap dbparam = new SOMap();
		dbparam.putAll(bforder);
		dbparam.putAll(param);
		dbparam.put("isolatetype", bforder.getDbStr("isolatetype"));
		dbparam.put("imgtype", Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_S);
		List<SOMap> bfCalimList = claimGoodsMapper.selectOrderClaimGoodsApplyList(dbparam);
		dbparam.put("items", bfCalimList);
		
		//이전 배송비 계산(묶음배송/조건부무료)
		List<SOMap> delivList = new ArrayList<SOMap>();
		SOMap delivMap = new SOMap();
		for (SOMap item : bfCalimList) {
			if(Util.flag2Bool(item.getDbStr("iscombdeliv"))) {
				int idx = this.containIdx(delivList, "delividx", item.getDbInt("delividx")); 
				
				if(idx == -1) {
					SOMap deliv = new SOMap();
					deliv.put("delividx", item.getDbInt("delividx"));
					deliv.put("delivamt", item.getDbInt("delivamt"));
					delivList.add(deliv);
				} else {
					delivList.get(idx).put("delivamt", delivList.get(idx).getDbInt("delivamt") + item.getDbInt("delivamt"));
				}
			} else if(!Util.flag2Bool(item.getDbStr("iscombdeliv")) && "DCT003".equals(item.getDbStr("delivfaretype"))){
				if(delivMap.containsKey(item.getDbStr("goodsno"))) {
					delivMap.put(item.getDbStr("goodsno"), delivMap.getDbInt(item.getDbStr("goodsno")) + item.getDbInt("delivamt"));
				}else {
					delivMap.put(item.getDbStr("goodsno"), item.getDbInt("delivamt"));
				}
			} else if(!Util.flag2Bool(item.getDbStr("iscombdeliv")) && "DCT005".equals(item.getDbStr("delivfaretype"))){
				int delivfare = item.getDbInt("delivfare");
				int clmcnt = item.getDbInt("clmcnt");
				if(delivMap.containsKey(item.getDbStr("goodsno"))) {
					delivMap.put(item.getDbStr("goodsno"), delivMap.getDbInt(item.getDbStr("goodsno")) + (delivfare * clmcnt));
				}else {
					delivMap.put(item.getDbStr("goodsno"), (delivfare * clmcnt));
				}
			}
		}
		
		/**
		 * 이전 쿠폰 및 프로모션
		 * 계산전이전 쿠폰세팅
		 */
		//이전 장바구니쿠폰
		param.put("comcpntype", CMConst.COM_CPN_CART);
		List<SOMap> cartcouponList = claimCouponMapper.selectOrderCouponListWithoutClaim(param);
		if(cartcouponList != null && cartcouponList.size() > 0) {
			SOMap cartcoupon = cartcouponList.get(0);
			cartcoupon.put("disamount", bforder.getDbInt("basketcpnamt"));
			result.put("bfbasketcoupon", cartcoupon);
			
			dbparam.put("cartcoupon", cartcoupon);
		}
		
		//이전 배송비할인쿠폰
		param.put("comcpntype", CMConst.COM_CPN_DELIV);
		List<SOMap> delivcouponList = claimCouponMapper.selectOrderCouponListWithoutClaim(param);
		if(delivcouponList != null && delivcouponList.size() > 0) {
			SOMap delivcoupon = delivcouponList.get(0);
			delivcoupon.put("disamount", bforder.getDbInt("totdelivcpnamt"));
			result.put("bfdelivcoupon", delivcoupon);
			
			dbparam.put("delivcoupon", delivcoupon);
		}
		
		//이전 쿠폰/프로모션
		List<SOMap> bfcouponList = new ArrayList<SOMap>();
		List<SOMap> bfpromoList = new ArrayList<SOMap>();
		for (SOMap items : bfCalimList) {
			if(items.getDbInt("goodscpnamt") > 0) {
				SOMap goodscoupon = new SOMap();
				goodscoupon.put("cpnname", items.getDbStr("cpnname"));
				goodscoupon.put("disamount", items.getDbInt("goodscpnamt"));
				goodscoupon.put("idx", items.getDbInt("goodscpnidx"));
				goodscoupon.put("cpntype", CMConst.COM_CPN_GOODS);
				bfcouponList.add(goodscoupon);
			}
			if(items.getDbInt("salepromoamt") > 0) {
				SOMap promotion = new SOMap();
				promotion.put("promoname", items.getDbStr("promoname"));
				promotion.put("disamount", items.getDbInt("salepromoamt"));
				promotion.put("idx", items.getDbInt("salepromoidx"));
				bfpromoList.add(promotion);
			}
		}
		result.put("bfgoodscoupon", this.mergeSum(bfcouponList, "idx", "disamount"));
		result.put("bfpromotion", this.mergeSum(bfpromoList, "idx", "disamount"));
		
		
		/**
		 * 클레임신청 상품을 제외하고 재계산
		 */
		SOMap afCalOrder = commonOrderService.calOrderAmt(dbparam);
		
		SOMap aforder = afCalOrder.getSOMap("totamt");
		List<SOMap> afClaimList = afCalOrder.getArrayList("items");
		
		result.put("bfitems", bfCalimList);
		result.put("afitems", afClaimList);
		
		/**
		 * 이후 쿠폰 및 프로모션
		 */
		//이후 장바구니쿠폰
		result.put("afbasketcoupon", afCalOrder.getSOMap("cartcoupon"));
		//이후 장바구니쿠폰
		result.put("afdelivcoupon", afCalOrder.getSOMap("delivcoupon"));
		
		//이후 쿠폰/프로모션
		List<SOMap> afcouponList = new ArrayList<SOMap>();
		List<SOMap> afpromoList = new ArrayList<SOMap>();
		for (SOMap items : afClaimList) {
			if(items.getDbInt("goodscpnamt") > 0) {
				SOMap goodscoupon = new SOMap();
				goodscoupon.put("cpnname", items.getDbStr("cpnname"));
				goodscoupon.put("disamount", items.getDbInt("goodscpnamt"));
				goodscoupon.put("idx", items.getDbInt("goodscpnidx"));
				goodscoupon.put("cpntype", CMConst.COM_CPN_GOODS);
				afcouponList.add(goodscoupon);
			}
			if(items.getDbInt("salepromoamt") > 0) {
				SOMap promotion = new SOMap();
				promotion.put("promoname", items.getDbStr("promoname"));
				promotion.put("disamount", items.getDbInt("salepromoamt"));
				promotion.put("idx", items.getDbInt("salepromoidx"));
				afpromoList.add(promotion);
			}
		}
		result.put("afgoodscoupon", this.mergeSum(afcouponList, "idx", "disamount"));
		result.put("afpromotion", this.mergeSum(afpromoList, "idx", "disamount"));
		
		/**
		 * 클레임상품
		 */
		List<SOMap> dispitems = new ArrayList<SOMap>(); //화면목록 노출용
		List<SOMap> claimitems = new ArrayList<SOMap>(); //클레임상품용
		
		int ptndelivamt = 0;
		int dadadelivamt = 0;

		int rtnptndelivamt = 0;
		int rtndadadelivamt = 0;
		int totrtndelivamt = 0;
		
		/**
		 * 파트너/자사 총배송비 및 반환배송비
		 */
		for (SOMap bfItem : bfCalimList) {
			if(!Util.flag2Bool(bfItem.getDbStr("isclmoption"))) continue;
			
			dispitems.add(bfItem);
			for(SOMap afItem : afClaimList) {
				if(bfItem.getDbInt("ordgdidx") == afItem.getDbInt("ordgdidx")) {
					SOMap item = new SOMap();
					item.putAll(afItem);
					item.put("salepromoamt", bfItem.getDbInt("salepromoamt") - afItem.getDbInt("salepromoamt"));
					item.put("goodscpnamt", bfItem.getDbInt("goodscpnamt") - afItem.getDbInt("goodscpnamt"));
					item.put("delivcpnamt", bfItem.getDbInt("delivcpnamt") - afItem.getDbInt("delivcpnamt"));
					item.put("realgoodsamt", bfItem.getDbInt("realgoodsamt") - afItem.getDbInt("realgoodsamt"));
					item.put("reserveamt", 0);
					item.put("epointamt", 0);
					item.put("empreserveamt", 0);
					item.put("delivamt", 0);
					item.put("adddelivamt", 0);
					item.put("rtndelivamt", 0);
					
					// 클레임 배송비 설정
					// 계산후 배송비는 배송비+추가배송비(제주/도서산간)
					if(Util.flag2Bool(item.getDbStr("iscombdeliv"))) {
						// 묶음배송일경우 이전템플릿 배송비와 계산후 템플릿 배송비를 비교
						int afdelivamt = 0;
						int bfdelivamt = 0;
						
						//이전 묶음배송비
						int idx = this.containIdx(delivList, "delividx", item.getDbInt("delividx"));
						if(idx > -1) {
							bfdelivamt = delivList.get(idx).getDbInt("delivamt");
							
							for(SOMap tempItem : afClaimList) {
								if(item.getDbInt("delividx") == tempItem.getDbInt("delividx") && Util.flag2Bool(tempItem.getDbStr("iscombdeliv"))) {
									afdelivamt = afdelivamt + (tempItem.getDbInt("delivamt") + tempItem.getDbInt("adddelivamt"));
								}
							}
							if(bfdelivamt < afdelivamt) {
								item.put("rtndelivamt", afdelivamt - bfdelivamt);
							} else if(bfdelivamt > afdelivamt) {
								item.put("delivamt", bfdelivamt);
							}
							delivList.remove(idx);
						}
					} else if(!Util.flag2Bool(item.getDbStr("iscombdeliv")) && "DCT003".equals(item.getDbStr("delivfaretype"))){
						// 묶음배송이 아니고 조건부 무료일경우
						int afdelivamt = 0;
						if(delivMap.containsKey(item.getDbStr("goodsno"))) {
							int bfdelivamt = delivMap.getDbInt(item.getDbStr("goodsno"));
							for(SOMap tempItem : afClaimList) {
								if(item.getDbStr("goodsno").equals(tempItem.getDbStr("goodsno"))) {
									afdelivamt = afdelivamt + (tempItem.getDbInt("delivamt") + tempItem.getDbInt("adddelivamt"));
								}
							}
							if(bfdelivamt < afdelivamt) {
								item.put("rtndelivamt", afdelivamt - bfdelivamt);
							} else if(bfdelivamt > afdelivamt) {
								item.put("delivamt", bfdelivamt);
							}
							delivMap.remove(item.getDbStr("goodsno"));
						}
					} else if(!Util.flag2Bool(item.getDbStr("iscombdeliv")) && "DCT005".equals(item.getDbStr("delivfaretype"))){
						int afdelivamt = 0;
						if(delivMap.containsKey(item.getDbStr("goodsno"))) {
							int bfdelivamt = delivMap.getDbInt(item.getDbStr("goodsno"));
							for(SOMap tempItem : afClaimList) {
								if(item.getDbStr("goodsno").equals(tempItem.getDbStr("goodsno"))) {
									afdelivamt = afdelivamt + (tempItem.getDbInt("delivfare") * tempItem.getDbInt("clmcnt"));
								}
							}
							item.put("delivamt", afdelivamt);
							item.put("rtndelivamt", afdelivamt);
							delivMap.remove(item.getDbStr("goodsno"));
						}
					} else {
						// 개별배송일 경우
						if(bfItem.getDbInt("delivamt") < (afItem.getDbInt("delivamt") + afItem.getDbInt("adddelivamt"))) {
							item.put("rtndelivamt", (afItem.getDbInt("delivamt") + afItem.getDbInt("adddelivamt")) - bfItem.getDbInt("delivamt"));
						} else if(bfItem.getDbInt("delivamt") > (afItem.getDbInt("delivamt") + afItem.getDbInt("adddelivamt"))) {
							item.put("delivamt", bfItem.getDbInt("delivamt"));
						}
					}
					if(item.getDbInt("dealerno") > 0) {
						ptndelivamt = ptndelivamt + item.getDbInt("delivamt");
						rtnptndelivamt = rtnptndelivamt + item.getDbInt("rtndelivamt");
					} else {
						dadadelivamt = dadadelivamt + item.getDbInt("delivamt");
						rtndadadelivamt = rtndadadelivamt + item.getDbInt("rtndelivamt");
					}
					totrtndelivamt = totrtndelivamt + item.getDbInt("rtndelivamt");
					claimitems.add(item);
				}
			}
		}
		result.put("dispitems", dispitems);
		result.put("items", claimitems);
		
		
		SOMap calinfo = new SOMap();
		calinfo.put("orderidx", param.getDbInt("orderidx"));
		calinfo.put("ordno",  bforder.getDbStr("ordno"));
		calinfo.put("paywaytype", param.getDbStr("paywaytype"));
		calinfo.put("isfrstorder", bforder.getDbStr("isfrstorder"));
		
		calinfo.put("ptndelivamt", ptndelivamt);
		calinfo.put("dadadelivamt", dadadelivamt);
		calinfo.put("rtnptndelivamt", rtnptndelivamt);
		calinfo.put("rtndadadelivamt", rtndadadelivamt);
		calinfo.put("totrtndelivamt", calinfo.getDbInt("rtnptndelivamt") + calinfo.getDbInt("rtndadadelivamt"));
		
		/**
		 * 클레임사유별 추가배송비
		 */
		int adddelivamt = 0;
		String isolatetype = Util.isEmpty(param.getDbStr("isolatetype")) ? bforder.getDbStr("isolatetype") : param.getDbStr("isolatetype");
		
		if(CMConst.CLM_RETURN.equals(param.getDbStr("clmtype"))) {
			if(!Util.isEmpty(param.getDbStr("rstype")) && "RTT001".equals(param.getDbStr("rstype"))) { //단순상품불만족
				SOMap item = claimitems.get(0);
				String delivfaretype = item.getDbStr("delivfaretype");
				if(!"DCT001".equals(delivfaretype)) {
					adddelivamt = item.getDbInt("rfowfare");
				} else {
					adddelivamt = item.getDbInt("rfrtnfare");
				}
				if("J".equals(isolatetype)) {
					adddelivamt = adddelivamt + item.getDbInt("chejufare");
				} else if("I".equals(isolatetype)) {
					adddelivamt = adddelivamt + item.getDbInt("isolfare");
				}
			}
		} else if(CMConst.CLM_EXCHANGE.equals(param.getDbStr("clmtype"))) { //교환
			if(!Util.isEmpty(param.getDbStr("rstype")) && "EXT001".equals(param.getDbStr("rstype"))) { //색상 및 옵션변경
				SOMap item = claimitems.get(0);
				adddelivamt = item.getDbInt("exrtnfare");
				
				if("J".equals(isolatetype)) {
					adddelivamt = adddelivamt + item.getDbInt("chejufare");
				} else if("I".equals(isolatetype)) {
					adddelivamt = adddelivamt + item.getDbInt("isolfare");
				}
			}
		} 
		//관리자에서 입력값이 있을경우
		if(!Util.isEmpty(param.getDbStr("adddelivamt"))) {
			adddelivamt = param.getDbInt("adddelivamt");
		}
		
		calinfo.put("adddelivamt", adddelivamt);
		claimitems.get(0).put("adddelivamt", adddelivamt);
		
		/**
		 * 이전주문금액에서 재계산 주문을 금액을 뺀 차액계산
		 */
		//주문총금액
		calinfo.put("ordtotprice", bforder.getDbInt("ordtotprice") - aforder.getDbInt("ordtotprice")); 
        
		//총프로모션할인금액
		calinfo.put("totsalepromoamt", bforder.getDbInt("totsalepromoamt") - aforder.getDbInt("totsalepromoamt")); 
        //총상품쿠폰할인금액
		calinfo.put("totgoodscpnamt", bforder.getDbInt("totgoodscpnamt") - aforder.getDbInt("totgoodscpnamt"));
    	//총배송비할인금액
		calinfo.put("totdelivcpnamt", bforder.getDbInt("totdelivcpnamt") - aforder.getDbInt("totdelivcpnamt"));
		//장바구니 할인금액
		calinfo.put("basketcpnamt", bforder.getDbInt("basketcpnamt") - aforder.getDbInt("basketcpnamt"));
		//총차감금액
		calinfo.put("minusamt", calinfo.getDbInt("totsalepromoamt") + calinfo.getDbInt("totgoodscpnamt") + calinfo.getDbInt("totdelivcpnamt")
		 		+ calinfo.getDbInt("basketcpnamt") + calinfo.getDbInt("totrtndelivamt"));
		
		//배송비할인쿠폰 클레임여부
		if(bforder.getDbInt("totdelivcpnamt") > 0 && aforder.getDbInt("totdelivcpnamt") == 0) {
			calinfo.put("claimdelivcpn", "T");
		}else {
			calinfo.put("claimdelivcpn", "F");
		}
				
		//장바구니쿠폰 크레임여부
		if(bforder.getDbInt("basketcpnamt") > 0 && aforder.getDbInt("basketcpnamt") == 0) {
			calinfo.put("claimbasketcpn", "T");
		}else {
			calinfo.put("claimbasketcpn", "F");
		}
		
		/**
		 * 환불금 및 추가결재금액
		 */
		//이전결제금액
		int bfpaytotprice = bforder.getDbInt("paytotprice");
		//이전실결제금액
		int bfrpaytotprice = bforder.getDbInt("rpaytotprice");
		
		//이후결제금액
		int afpaytotprice = aforder.getDbInt("paytotprice");
		
		//환불예상금액
		int rtnamt = 0;
		//추가결제금액
		int addpaytotprice = 0;
		
		if(afpaytotprice < bfpaytotprice) {
			//이후 결제금액이 이전 결제금액보다 작을경우(이전 : 20000 , 이후 : 15000)
			//환불금액 5,000
			rtnamt = bfpaytotprice - afpaytotprice;
		} else {
			//이전 결제금액이 이후 결제금액보다 작을경우(이전 : 15000, 이후 : 20000)
			//환불금액 0, 추가결제금액 5,000
			rtnamt = 0;
			addpaytotprice = afpaytotprice - bfpaytotprice;
		}
		
		calinfo.put("rtnamt", rtnamt);
		calinfo.put("addpaytotprice", addpaytotprice);
		calinfo.put("addrpaytotprice", addpaytotprice + adddelivamt); //실추가 결제금액은 추가결제금액 + 추가배송비
		
		
		
		//조정금액이 있을경우
		if(Util.isEmpty(param.getDbStr("finadjustamt"))) {
			calinfo.put("finadjustamt", rtnamt);
		} else {
			calinfo.put("finadjustamt", param.getDbInt("finadjustamt"));
		}
		
		rtnamt = calinfo.getDbInt("finadjustamt");
		
		/**
		 * 결제취소금액 계산
		 */
		//결제취소금액
		int rtnpayamt = 0;
		//이전결제금보다 환급금액이 클경우 포인트 반환
		if(rtnamt > bfrpaytotprice) {
			rtnpayamt = bfrpaytotprice;
		} else {
			rtnpayamt = rtnamt;
		}
		
		//환불금액서 결제취소금액을 뺀 나머지 차액계산(포인트반환)
		int tempamt = rtnamt - rtnpayamt;
				
		int empreservetotamt = bforder.getDbInt("empreservetotamt"); //임직원적립금
		int reservetotamt = bforder.getDbInt("reservetotamt"); //적립금
		int epointtotamt = bforder.getDbInt("epointtotamt"); //이포인트
		
		int rtnempresamt = 0; //임직원적립금 반환
		int rtnresamt = 0; //적립금 반환
		int rtnepoamt = 0; //이포인트 반환
		
		//이전결제금보다 환급금액이 클경우 포인트 반환
		if(empreservetotamt > 0) {
			//임직원포인트 반환
			if(empreservetotamt >= tempamt) {
				rtnempresamt = tempamt;
				tempamt = 0;
			} else {
				rtnempresamt = empreservetotamt;
				tempamt = tempamt - empreservetotamt;
			}
		}
		if(reservetotamt > 0) {
			//적립금 반환
			if(reservetotamt >= tempamt) {
				rtnresamt = tempamt;
				tempamt = 0;
			} else {
				rtnresamt = reservetotamt;
				tempamt = tempamt - reservetotamt;
			}
		}
		
		if(epointtotamt > 0) {
			//이포인트 반환
			if(epointtotamt >= tempamt) {
				rtnepoamt = tempamt;
				tempamt = 0;
			} else {
				rtnepoamt = epointtotamt;
				tempamt = tempamt - epointtotamt;
			}
		}
		
		calinfo.put("rtnpayamt", rtnpayamt);
		calinfo.put("rtnempresamt", rtnempresamt);
		calinfo.put("rtnresamt", rtnresamt);
		calinfo.put("rtnepoamt", rtnepoamt);
		
		
		// 교환시 교환출고상품목록 세팅
		/**
		 * 교환 :
		 *   - 교환출고상품목록 세팅
		 * 취소,반품 :
		 *   - 클레임 사은품 재설정
		 *   - 클레임 적립금 분배
		 */
		List<SOMap> excGoodsList = new ArrayList<SOMap>();
		List<SOMap> resultGiftList = new ArrayList<SOMap>();
		if(CMConst.CLM_EXCHANGE.equals(param.getDbStr("clmtype"))) {
    		List<SOMap> items = Util.convertToListSOMap(result.getArrayList("items"));
    		SOMap excParam = items.get(0);
    		excParam.put("siteid", cs.getStr("siteid"));
    		excGoodsList = goodsOptionDetailMapper.selectExcGoodsList(excParam);
    		//장바구니쿠폰 할인금 분배
    		int basketcpnamt = aforder.getDbInt("basketcpnamt");
    		int idx = 1;
			BigDecimal bTotPrice = new BigDecimal(aforder.getDbInt("ordtotprice"));
			for (SOMap item : afClaimList) {
				if (item.getDbInt("ordcnt") == 0) {
					continue;
				}
				
				//적립금 배율계산
				int price = item.getDbInt("price") * item.getDbInt("ordcnt");
				BigDecimal bPrice = new BigDecimal(price);

				double rate = bPrice.divide(bTotPrice, 10, RoundingMode.HALF_UP).doubleValue();
				
				if(idx == afClaimList.size()) {
					item.put("basketdivamt", basketcpnamt);
				} else {
					item.put("basketdivamt", Math.round(aforder.getDbInt("basketcpnamt") * rate));
					basketcpnamt = basketcpnamt - item.getDbInt("basketdivamt");
				}
				idx++;
			}
		} else {
			SOMap giftParam = new SOMap();
			giftParam.put("platform", bforder.getDbStr("ordpathtype"));
			giftParam.put("memlvtype", bforder.getDbStr("memlvtype"));
			giftParam.put("membertype", bforder.getDbStr("dadamembertype"));
			giftParam.put("promodate", DateTimeUtil.getFormatStr(bforder.getDbStr("orderdate"), DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12));
			giftParam.put("imgtype", Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GIFT_IMG_MO_B : CMConst.IMG_TYPE_GIFT_IMG_PC_B);
			giftParam.put("items", afClaimList);
			giftParam.put("ordtotprice", aforder.getDbInt("ordtotprice"));
			giftParam.put("rpaytotprice", aforder.getDbInt("rpaytotprice"));
			giftParam.put("isoption", "T");
			
			//재계산 후 사은품목록
			List<SOMap> giftList = giftMapper.selectGiftListByGoods(giftParam); 
			//이전 사은품목록
			List<SOMap> claimGiftList = claimGoodsGiftMapper.selectClaimGoodsGiftWithoutClaim(param);
			//재계산사은품 사은품 추가/제외
			for (SOMap gift : giftList) {
				boolean isExist = false;
				for (SOMap claimGift : claimGiftList) {
					if(gift.getDbInt("giftpromoidx") == claimGift.getDbInt("giftpromoidx")){
						if(gift.getDbInt("giftidx") == claimGift.getDbInt("giftidx")
								&& gift.getDbInt("rcvamt") != claimGift.getDbInt("rcvamt")){
							//기존사은품이 있고 수량이 다들경우(사은품 취소수량계산(기존사은품수량 - (기존사은품수량 - 재계산사은품수량)
							gift.put("rcvamt", claimGift.getDbInt("rcvamt") - (claimGift.getDbInt("rcvamt") - gift.getDbInt("rcvamt")));
							gift.put("isadd", "F");
							resultGiftList.add(gift);
						}
						isExist = true;
					}				
				}
				if(!isExist) { 
					//기존사은품이 없을경우 추가사은품
					gift.put("isadd", "T");
					resultGiftList.add(gift);
				}
			}
			//기존사은품 프로모션 제외
			for (SOMap claimGift : claimGiftList) {
				boolean isExist = false;
				for (SOMap gift : giftList) {
					if(gift.getDbInt("giftpromoidx") == claimGift.getDbInt("giftpromoidx")){
						isExist = true;
					}
				}
				if(!isExist) { 
					//재계산사은품에 없을경우 제외사은품
					claimGift.put("isadd", "F");
					resultGiftList.add(claimGift);
				}
			}
			//주문상품 적립금 분배
			if(aforder.getDbInt("ordtotprice") > 0) {
				empreservetotamt = empreservetotamt - rtnempresamt;
				reservetotamt = reservetotamt - rtnresamt;
				epointtotamt = epointtotamt - rtnepoamt;
				
				int empreservetotamt1 = empreservetotamt;
				int reservetotamt1 = reservetotamt;
				int epointtotamt1 = epointtotamt;
				int basketcpnamt = aforder.getDbInt("basketcpnamt");
				int idx = 1;
				BigDecimal bTotPrice = new BigDecimal(aforder.getDbInt("reservecalamt"));
				BigDecimal basketTotPrice = new BigDecimal(aforder.getDbInt("ordtotprice"));
				for (SOMap item : afClaimList) {
					//적립금 배율계산
					int price = item.getDbInt("price") * item.getDbInt("ordcnt");
					int saleAmt = price + item.getDbInt("delivamt") - item.getDbInt("goodscpnamt") - item.getDbInt("delivcpnamt");
//					BigDecimal bPrice = new BigDecimal(saleAmt);
//					double rate = bPrice.divide(bTotPrice, 10, RoundingMode.HALF_UP).doubleValue();
					BigDecimal basketPrice = new BigDecimal(price);
					double basketRate = basketPrice.divide(basketTotPrice, 10, RoundingMode.HALF_UP).doubleValue();
					
					if(idx == afClaimList.size()) {
						item.put("reserveamt", reservetotamt1);
						item.put("epointamt", epointtotamt1);
						item.put("empreserveamt", empreservetotamt1);
						item.put("basketdivamt", basketcpnamt);
					} else {
						item.put("basketdivamt", Math.round(aforder.getDbInt("basketcpnamt") * basketRate));
						BigDecimal bPrice = new BigDecimal(saleAmt-item.getDbInt("basketdivamt"));
						double rate = bPrice.divide(bTotPrice, 10, RoundingMode.HALF_UP).doubleValue();
						
						item.put("reserveamt", Math.round(reservetotamt * rate));
						item.put("epointamt", Math.round(epointtotamt * rate));
						item.put("empreserveamt", Math.round(empreservetotamt * rate));
						
						reservetotamt1 = reservetotamt1 - item.getDbInt("reserveamt");
						epointtotamt1 = epointtotamt1 - item.getDbInt("epointamt");
						empreservetotamt1 = empreservetotamt1 - item.getDbInt("empreserveamt");
						basketcpnamt = basketcpnamt - item.getDbInt("basketdivamt");
					}
					idx++;
				}
			}
			
			//클레임 반환적립금/장바구니쿠폰할인금액 분배
			if(calinfo.getDbInt("ordtotprice") > 0) {
				int idx = 1;
				BigDecimal bTotPrice = new BigDecimal(calinfo.getDbInt("ordtotprice"));
				for (SOMap item : claimitems) {
					//적립금 배율계산
					int price = item.getDbInt("price") * item.getDbInt("clmcnt");
					int saleAmt = price + item.getDbInt("delivamt") - item.getDbInt("goodscpnamt") - item.getDbInt("delivcpnamt");
					BigDecimal bPrice = new BigDecimal(saleAmt);
//					double rate = bPrice.divide(bTotPrice, 2, BigDecimal.ROUND_CEILING).doubleValue();
					double rate = bPrice.divide(bTotPrice, 10, RoundingMode.HALF_UP).doubleValue();
					if(idx == claimitems.size()) {
						item.put("reserveamt", rtnresamt);
						item.put("epointamt", rtnepoamt);
						item.put("empreserveamt", rtnempresamt);
					} else {
						item.put("reserveamt", Math.round(rtnresamt * rate));
						item.put("epointamt", Math.round(epointtotamt * rate));
						item.put("empreserveamt", Math.round(empreservetotamt * rate));
						rtnresamt = rtnresamt - item.getDbInt("reserveamt");
						rtnepoamt = rtnepoamt - item.getDbInt("epointamt");
						rtnempresamt = rtnempresamt - item.getDbInt("empreserveamt");
					}
					idx++;
				}
			}
			
		}
		
		result.put("bforder", bforder);
		result.put("aforder", aforder);
		result.put("calinfo", calinfo);
		result.put("giftlist", resultGiftList);
		result.put("excgoodslist", excGoodsList);
		
		return result;
	}
	
	/**
	 * 클레임 체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private SOMap checkClaim(SOMap param) throws Exception {
		SOMap dbparam = new SOMap();
		
		dbparam.put("siteid", cs.getStr("siteid"));
		dbparam.put("imgtype", Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_S);
		dbparam.put("status", "APPLY");
		dbparam.put("clmtype", param.getDateStr("clmtype"));
		dbparam.put("orderidx", param.getDbInt("orderidx"));
		dbparam.put("claim", Util.convertToListSOMap(param.getArrayList("claimitems")));
		dbparam.put("rstype", param.getDbStr("rstype"));
		
		return this.calClaim(dbparam).getSOMap("calinfo");
	}
	
	/**
	 * 클레임신청 저장처리
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap saveClaimApply(Map<String, MultipartFile> files, SOMap param) throws Exception {
		//클레임 금액 검증
		SOMap checkClaim = this.checkClaim(param);
		
		if(checkClaim.getDbInt("rtnpayamt") != param.getDbInt("rtnpayamt")) {
			throw new BizException("환불금액이 일치하지 않습니다.");
		}
		
		if(CMConst.CLM_CANCEL.equals(param.getDateStr("clmtype"))){
			if(Util.isEmpty(param.getDbStr("cnctype"))) {
				throw new Exception("취소사유를 선택해주세요.");
			}
		} else if(CMConst.CLM_RETURN.equals(param.getDateStr("clmtype"))) {
			if(Util.isEmpty(param.getDbStr("rtntype"))) {
				throw new Exception("반품사유를 선택해주세요.");
			}
		} else {
			if(Util.isEmpty(param.getDbStr("exctype"))) {
				throw new Exception("교환사유를 선택해주세요.");
			}
			if(Util.isEmpty(param.getDbStr("excoption"))) {
				throw new Exception("교환옵션를 선택해주세요.");
			}
		}
		
		SOMap result = new SOMap();
		
		param.put("siteid", cs.getStr("siteid"));
		if(isAdmin()) {
			param.put("userid", getAdminId());
		} else if(isDealer()) {
			param.put("userid", getDealerId());
		} else {
			param.put("userid", getMemberId());
		}
		
		int bfclmidx = param.getDbInt("clmidx");
		/**
		 * 클레임 저장
		 */
		//클레임번호
		param.put("clmno", claimMapper.selectClaimNo(param));
		if("local".equals(prop.getProperty("Globals.Profile"))) {
			param.put("clmno", param.getDbStr("clmno").replace("C", "L"));
		}
		
		claimMapper.insertClaim(param);
		
		/**
		 * 반품,교환이력 저장처리
		 */
		if(!CMConst.CLM_CANCEL.equals(param.getDbStr("CLMTYPE"))) {
			param.put("bfclmidx", bfclmidx);
			param.put("afclmidx", param.getDbInt("clmidx"));
			exchangeHistMapper.insertExchangeHist(param);
		}
		
		/**
		 * 상품 저장
		 */
		List<SOMap> items = Util.convertToListSOMap(param.getArrayList("items"));
		for (SOMap item : items) {
			item.put("clmidx", param.getDbInt("clmidx"));
			item.put("clmcnt", 0);
			item.put("isclmoption", "F");
			item.put("adddelivamt", 0);
			item.put("rtndelivamt", 0);
			item.put("userid", param.getDbStr("userid"));
			claimGoodsMapper.insertClaimGoods(item);
		}
	
		/**
		 * 클레임상품저장
		 */
		List<SOMap> claimitems = Util.convertToListSOMap(param.getArrayList("claimitems"));
		for (SOMap item : claimitems) {
			//클레임상품 저장
			item.put("clmidx", param.getDbInt("clmidx"));
			item.put("isclmoption", "T");
			item.put("userid", param.getDbStr("userid"));

			
			if(CMConst.CLM_EXCHANGE.equals(param.getDbStr("clmtype"))) {
				item.put("optioncode", param.getDbStr("excoption"));
			}
			
			claimGoodsMapper.insertClaimGoods(item);
			
			
			//클레임상품 쿠폰 저장
			//즉시할인쿠폰 클레임이후 상품수량이 0일경우 클레임등록
			//그외쿠폰 클레임이후 잔량남은상품수량
			SOMap dbparam = new SOMap();
			dbparam.put("orderidx", item.getDbInt("orderidx"));
			dbparam.put("ordgdidx", item.getDbInt("ordgdidx"));
			dbparam.put("comcpntype", CMConst.COM_CPN_GOODS);
			dbparam.put("status", "APPLY");
			List<SOMap> couponList = claimCouponMapper.selectOrderCouponListWithoutClaim(dbparam);
			
			if(couponList.size() > 0) {
				if(Util.flag2Bool(item.getDbStr("isdirect"))) {
					if(item.getDbInt("ordcnt") == 0) {
						item.put("comcpntype", couponList.get(0).getDbStr("comcpntype"));
						item.put("cpnmisidx", couponList.get(0).getDbInt("cpnmisidx"));
						claimCouponMapper.insertClaimCoupon(item);
					}
				} else {
					// 취소되어야 하는 쿠폰 수량 =  남은 쿠폰 수량 - 재주문 수량
					int cancelcouponcnt =  item.getDbInt("remainusecnt") - item.getDbInt("usecnt");
					item.put("comcpntype", couponList.get(0).getDbStr("comcpntype"));
					for(int i = 0 ; i < cancelcouponcnt; i++) {
						item.put("cpnmisidx", couponList.get(i).getDbInt("cpnmisidx"));
						claimCouponMapper.insertClaimCoupon(item);
					}
				}
			}
			
			/**
			 * 장바구니처리(Front 체크)
			 */
			if(Util.flag2Bool(param.getDbStr("savecart")) && cs.getInt("authmemberno") > 0) {
				item.put("siteid", cs.getStr("siteid"));
				item.put("sessid", "");
				item.put("userno", cs.getInt("authmemberno"));
				item.put("ordcnt", 1);
				item.put("ordpathtype", cs.getStr("platform"));
				
				int cnt = comCartMapper.selectCartByGoods(item);
				if(cnt == 0) {
					comCartMapper.insertCart(item);
				}
			}
		}
		
		/**
		 * 클레임 장바구니/할인쿠폰
		 */
		if(Util.flag2Bool(param.getDbStr("claimdelivcpn"))) {
			SOMap dbparam = new SOMap();
			dbparam.put("status", "APPLY");
			dbparam.put("orderidx", param.getDbInt("orderidx"));
			dbparam.put("comcpntype", CMConst.COM_CPN_DELIV);
			List<SOMap> cartcouponList = claimCouponMapper.selectOrderCouponListWithoutClaim(dbparam);
			if(cartcouponList != null && cartcouponList.size() > 0) {
				SOMap coupon = cartcouponList.get(0);
				coupon.put("clmidx", param.get("clmidx"));
				coupon.put("userid", param.get("userid"));
				claimCouponMapper.insertClaimCoupon(coupon);
			}
		}
		
		if(Util.flag2Bool(param.getDbStr("claimbasketcpn"))) {
			SOMap dbparam = new SOMap();
			dbparam.put("status", "APPLY");
			dbparam.put("orderidx", param.getDbInt("orderidx"));
			dbparam.put("comcpntype", CMConst.COM_CPN_CART);
			List<SOMap> cartcouponList = claimCouponMapper.selectOrderCouponListWithoutClaim(dbparam);
			if(cartcouponList != null && cartcouponList.size() > 0) {
				SOMap coupon = cartcouponList.get(0);
				coupon.put("clmidx", param.get("clmidx"));
				coupon.put("userid", param.get("userid"));
				claimCouponMapper.insertClaimCoupon(coupon);
			}
		}
		
		/**
		 * 사은품처리
		 */
		List<Map<String, Object>> giftList = param.getArrayList("giftlist");
		if(giftList.size() > 0) {
			SOMap giftMap = new SOMap();
			giftMap.putAll(param);
			
			for (Map<String, Object> gift : giftList) {
				giftMap.putAll(gift);
				claimGoodsGiftMapper.insertClaimGoodsGift(giftMap);
			}
		}
				
		/**
		 * 첨부파일저장
		 */
		for (String key : files.keySet()) {
			MultipartFile file = files.get(key);
			
			if (key.contains("filelist")) {
				if (file.getContentType().contains("image")) {
					fileService.uploadImage(file, param.getInt("clmidx"), CMConst.IMG_TYPE_CLAIM);
				}else if (file.getContentType().contains("video")) {
					fileService.uploadMov(file, param.getInt("clmidx"), CMConst.IMG_TYPE_CLAIM);
				}else{
					throw new Exception("지원하지 않는 파일 형식입니다.");
				}
			}
		}
		
		//클레임 상태변경
		this.saveClaimStatus(param);
		
		result.put("clmno", param.getDbStr("clmno"));

		SOMap sendParam = new SOMap();
		SOMap erpParam = new SOMap();
		erpParam.put("siteid", cs.getStr("siteid"));
		erpParam.put("clmidx", param.getDbStr("clmidx"));
		if(CMConst.CLM_RETURN.equals(param.getDbStr("clmtype"))) {
			erpService.insertOrderReturnApplyERPData(erpParam);
			sendParam.put("claimtypename", "반품");
		} else if(CMConst.CLM_EXCHANGE.equals(param.getDbStr("clmtype"))){
			erpService.insertOrderExchangeApplyERPData(erpParam);
			sendParam.put("claimtypename", "교환");
		}
		
		// 교환/반품 신청시 Message
		if (!CMConst.CLM_CANCEL.equals(param.getDbStr("clmtype")) && !param.getDbStr("excstatus").equals("EXS002") && !param.getDbStr("rtnstatus").equals("RTS002")) {
			SOMap orderInfo = comOrderMapper.selectComOrderInfo(param);
			SOMap claimInfo = claimitems.get(0);
			sendParam.put("number", orderInfo.get("ordtel"));
			sendParam.put("ordcnt", 0);
			sendParam.put("goodsname", claimInfo.get("goodsname"));
			sendParam.put("clmno", param.get("clmno"));
			sendParam.put("userno", orderInfo.get("userno"));
			cjMessageService.sendInquiryrReception(sendParam);
		}
		return result;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap updateClaimInfo(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		param.put("status", "COMPLETE");
		
		SOMap calClaim = this.calClaim(param);
		SOMap calInfo = calClaim.getSOMap("calinfo");
		SOMap afBasketCoupon = calClaim.getSOMap("afbasketcoupon");
		SOMap bfBasketCoupon = calClaim.getSOMap("bfbasketcoupon");

		// 이후 장바구니 쿠폰이 있다가 사라지면 이전 쿠폰 idx 세팅
		int basketcpnidx = 0;
		if (bfBasketCoupon != null && !Util.isEmptyMap(bfBasketCoupon) && (afBasketCoupon == null || Util.isEmptyMap(afBasketCoupon))) {
			basketcpnidx = bfBasketCoupon.getInt("cpnmisidx");
		}
		calInfo.put("basketcpnidx", basketcpnidx);
		calInfo.put("clmidx", param.get("clmidx"));
		claimMapper.updateClaim(calInfo);
		
		//클레임 쿠폰 삭제처리
		calInfo.put("istrash", "T");
		claimCouponMapper.updateClaimCouponTrash(calInfo);
		
		/**
		 * 클레임상품저장
		 */
		List<SOMap> claimitems = Util.convertToListSOMap(calClaim.getArrayList("items"));
		for (SOMap item : claimitems) {
			//클레임상품 저장
			item.put("clmidx", calInfo.getDbInt("clmidx"));
			item.put("isclmoption", "T");
			item.put("userid", getAdminId());
			
			if (!Util.equal(param.getStr("clmtype"), CMConst.CLM_CANCEL)) {
				item.put("adddelivamt", param.get("adddelivamt"));
			}
			claimGoodsMapper.updateClaimGoods(item);
			
			
			//클레임상품 쿠폰 저장
			//즉시할인쿠폰 클레임이후 상품수량이 0일경우 클레임등록
			//그외쿠폰 클레임이후 잔량남은상품수량
			SOMap dbparam = new SOMap();
			dbparam.put("clmidx", item.getDbInt("clmidx"));
			dbparam.put("orderidx", item.getDbInt("orderidx"));
			dbparam.put("ordgdidx", item.getDbInt("ordgdidx"));
			dbparam.put("comcpntype", CMConst.COM_CPN_GOODS);
			dbparam.put("status", "COMPLETE");
			List<SOMap> couponList = claimCouponMapper.selectOrderCouponListWithoutClaim(dbparam);
			
			item.put("istrash", "F");
			if(couponList.size() > 0) {
				if(Util.flag2Bool(item.getDbStr("isdirect"))) {
					if(item.getDbInt("ordcnt") == 0) {
						item.put("comcpntype", couponList.get(0).getDbStr("comcpntype"));
						item.put("cpnmisidx", couponList.get(0).getDbInt("cpnmisidx"));
						if(claimCouponMapper.selectClaimCouponCheck(item) > 0) {
							claimCouponMapper.updateClaimCouponTrash(item);
						} else {
							claimCouponMapper.insertClaimCoupon(item);
						}
					}
				} else {
					item.put("comcpntype", couponList.get(0).getDbStr("comcpntype"));
					// 취소되어야 하는 쿠폰 수량 =  남은 쿠폰 수량 - 재주문 수량
					int cancelcouponcnt =  item.getDbInt("remainusecnt") - item.getDbInt("usecnt");
					for(int i = 0 ; i < cancelcouponcnt ; i++) {
						item.put("cpnmisidx", couponList.get(i).getDbInt("cpnmisidx"));
						if(claimCouponMapper.selectClaimCouponCheck(item) > 0) {
							claimCouponMapper.updateClaimCouponTrash(item);
						} else {
							claimCouponMapper.insertClaimCoupon(item);
						}
					}
				}
			}
			
			/**
			 * 배송비 할인쿠폰
			 */
			if(Util.flag2Bool(calInfo.getDbStr("claimdelivcpn"))) {
				dbparam.put("comcpntype", CMConst.COM_CPN_DELIV);
				List<SOMap> cartcouponList = claimCouponMapper.selectOrderCouponListWithoutClaim(dbparam);
				if(cartcouponList != null && cartcouponList.size() > 0) {
					SOMap coupon = cartcouponList.get(0);
					coupon.put("clmidx", item.get("clmidx"));
					coupon.put("userid", item.get("userid"));
					coupon.put("istrash", "F");
					
					if(claimCouponMapper.selectClaimCouponCheck(coupon) > 0) {
						claimCouponMapper.updateClaimCouponTrash(coupon);
					} else {
						claimCouponMapper.insertClaimCoupon(coupon);
					}
				}
			}
			
			/**
			 * 장바구니 할인쿠폰
			 */
			if(Util.flag2Bool(calInfo.getDbStr("claimbasketcpn"))) {
				dbparam.put("comcpntype", CMConst.COM_CPN_CART);
				List<SOMap> cartcouponList = claimCouponMapper.selectOrderCouponListWithoutClaim(dbparam);
				if(cartcouponList != null && cartcouponList.size() > 0) {
					SOMap coupon = cartcouponList.get(0);
					coupon.put("clmidx", param.get("clmidx"));
					coupon.put("userid", param.get("userid"));
					coupon.put("istrash", "F");
					
					if(claimCouponMapper.selectClaimCouponCheck(coupon) > 0) {
						claimCouponMapper.updateClaimCouponTrash(coupon);
					} else {
						claimCouponMapper.insertClaimCoupon(coupon);
					}
				}
			}
			
			/**
			 * 사은품처리
			 * TO-DO
			 */
		}

		//클레임 상태변경
		this.saveClaimStatus(param);
		
		return result;
	}
	
	/**
	 * 클레임상태 변경 (신청 이후)
	 * 	필수 param : clmtype, cncstatus/rtnstatus/excstatus(변경할 상태)
	 * 	조건부필수 param : 
	 *  - 교환/반품 클레임 상태변경시 clmgdidx 또는 clmidx 필수, 취소 클레임상품 상태변경시 clmgdidx 필수
	 * 	  철회-철회사유, 반려-반려사유, 반려승인요청-반려승인사유, 반려거부-반려거부사유 필수
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap saveClaimStatus(SOMap param) throws Exception {
		SOMap result = new SOMap();
    	//클레임처리구분 세팅 (관리자, 파트너사, 고객)
    	if (isAdmin()) {
    		param.put("userid", getAdminId());
    		param.put("clmprctype", CMConst.CLM_PRC_ADMIN);
    	} else if (isDealer()) {
    		param.put("userid", getDealerId());
    		param.put("clmprctype", CMConst.CLM_PRC_PARTNER);
    	} else {
    		param.put("userid", getMemberId());
    		param.put("clmprctype", CMConst.CLM_PRC_MEMBER);
    	}
		
    	if (Util.equal(param.getStr("clmtype"), CMConst.CLM_CANCEL)) {
        	if (!param.containsKey("ischangestatus")
        			|| (!Util.isEmpty(param.getStr("ischangestatus")) && Util.equal(param.getStr("ischangestatus"), "T"))) {
				//클레임상품 상태이력 저장
		    	param.put("aftval", param.getDbStr("cncstatus"));
				claimGoodsHistMapper.insertClaimGoodsHistAll(param);
        	}
	    	//클레임 취소상태 및 사유 업데이트
	    	claimGoodsMapper.updateGoodsCncStatus(param);
	    	
	    	//취소시 클레임마스터 상태변경
	    	if (CMConst.CNC_WAITING_APPRV.equals(param.getDbStr("cncstatus"))
	    			|| CMConst.CNC_WAITING_PAYMENT.equals(param.getDbStr("cncstatus"))
	    			|| CMConst.CNC_PRCCOMP.equals(param.getDbStr("cncstatus"))
	    			|| (CMConst.CNC_WITHDRAW.equals(param.getDbStr("cncstatus")) && Util.isEmpty(param.getDbStr("clmgdidx"))) ) {
	    		claimMapper.updateClaimStatus(param);
	    	}
    	} else {
			//클레임상품 상태이력 저장
			if (Util.equal(param.getStr("clmtype"), CMConst.CLM_RETURN)) {
		    	param.put("aftval", param.getDbStr("rtnstatus"));
			} else {
		    	param.put("aftval", param.getDbStr("excstatus"));
			}
			claimGoodsHistMapper.insertClaimGoodsHistAll(param);
			//클레임 교환/반품상태 및 사유 업데이트
	    	claimMapper.updateClaimStatus(param);
		}

    	//클레임 완료일 수정
    	String clmstatus = "";
    	if (Util.equal(param.getStr("clmtype"), CMConst.CLM_CANCEL)) {
    		clmstatus = param.getStr("cncstatus");
    	} else if (Util.equal(param.getStr("clmtype"), CMConst.CLM_RETURN)) {
    		clmstatus = param.getStr("rtnstatus");
    	} else if (Util.equal(param.getStr("clmtype"), CMConst.CLM_EXCHANGE)) {
    		clmstatus = param.getStr("excstatus");
    	}
    	String[] compStatusArr = {
    			CMConst.CNC_PRCCOMP,				// 취소완료 (처리완료)
    			CMConst.CNC_WITHDRAW,				// 취소완료 (취소철회)
    			CMConst.RETURN_COMPLETE,			// 반품완료 (처리완료)
    			CMConst.RETURN_APPROVAL_REFUSE,		// 반품처리 (반품반려)
    			CMConst.RETURN_CANCEL,				// 반품처리 (반품철회)
    			CMConst.RETURN_A_RETURN,			// 반품완료 (반송처리)
    			CMConst.EXCHANGE_COMPLETE,			// 교환완료 (처리완료)
    			CMConst.EXCHANGE_REJECT,			// 교환처리 (교환반려)
    			CMConst.EXCHANGE_CANCEL,			// 교환처리 (교환철회)
    			CMConst.EXCHANGE_A_RETURN,			// 교환완료 (반송처리)
    	};
    	if (ArrayUtils.indexOf(compStatusArr, clmstatus) > -1) {
			claimMapper.updateClaimCompdate(param);
    	}
		//클레임 완료처리
		if("CNS005".equals(param.getDbStr("cncstatus")) 
				|| "RTS010".equals(param.getDbStr("rtnstatus")) 
				|| "EXS012".equals(param.getDbStr("excstatus"))) {
			
			//취소완료시 배송수량 업데이트
			if("CNS005".equals(param.getDbStr("cncstatus"))) {
				comOrderGoodsDelivMapper.updateDelivCnt(param);
			}
			
			SOMap claimInfo = claimMapper.selectClaimInfo(param);
			SOMap orderInfo = comOrderMapper.selectComOrderInfo(param);
			List<SOMap> claimItems = claimGoodsMapper.selectClaimGoodsList(param);
			
			//적립금 처리
			SOMap reserveParam = new SOMap();
			reserveParam.put("isuse", "F");
			reserveParam.put("rtnresamt", claimInfo.getDbInt("rtnresamt"));
			reserveParam.put("rtnepoamt", claimInfo.getDbInt("rtnepoamt"));
			reserveParam.put("rtnempresamt", claimInfo.getDbInt("rtnempresamt"));
			reserveParam.put("usememlvtype", orderInfo.getDbInt("memlvtype"));
			reserveParam.put("usemembertype", orderInfo.getDbInt("dadamembertype"));
			reserveParam.put("userno", orderInfo.getDbStr("userno"));
			reserveParam.put("userid", orderInfo.getDbStr("userid"));
			reserveParam.put("ordercode", orderInfo.getDbStr("ordno"));
			
			commonService.procUseReserveEPoint(reserveParam);
			
			//환불 처리
			if(claimInfo.getDbInt("rtnpayamt") > 0 && !Util.isEmpty(orderInfo.getDbStr("paymentkey"))) {
				param.put("orderidx", claimInfo.getDbInt("orderidx"));
				
				SOMap paymentParam = new SOMap();
				paymentParam.put("clmno", claimInfo.getDbStr("clmno"));
				paymentParam.put("paywaytype", orderInfo.getDbStr("paywaytype"));
				paymentParam.put("paymentkey", orderInfo.getDbStr("paymentkey"));
				paymentParam.put("amount", claimInfo.getDbInt("rtnpayamt"));
				paymentParam.put("bank", claimInfo.getDbStr("refbank"));
				paymentParam.put("holderName", claimInfo.getDbStr("refcusname"));
				paymentParam.put("accountNumber", claimInfo.getDbStr("refaccnumber"));
				paymentParam.put("requester", isAdmin() || isDealer() ? "2" : "1");
				
				logger.debug("###################################");
				logger.debug(":::::::::::::::requester:::::::::::");
				logger.debug(paymentParam.getStr("requester"));
				logger.debug("###################################");
				
				if(!Util.isEmpty(claimInfo.getDbStr("cnctypenm"))) {
					paymentParam.put("reason", claimInfo.getDbStr("cnctypenm"));
				} else if(!Util.isEmpty(claimInfo.getDbStr("rtntypenm"))) {
					paymentParam.put("reason", claimInfo.getDbStr("rtntypenm"));
				} else {
					paymentParam.put("reason", claimInfo.getDbStr("exctypenm"));
				}
				
				SOMap payInfo = null;
				if(CMConst.PAYWAY_TYPE_NAVER.equals(orderInfo.getDbStr("paywaytype"))) {
					payInfo = naverService.naverPaymentCancel(paymentParam);
				} else {
					payInfo = tossService.tossPaymentCancel(paymentParam);
				}
				
				//현금영수증 발급취소
				if(!Util.isEmpty(orderInfo.getDbStr("cashreceiptkey"))) {
					paymentParam = new SOMap();
					paymentParam.put("clmno", claimInfo.getDbStr("clmno"));
					paymentParam.put("cashreceiptkey", orderInfo.getDbStr("cashreceiptkey"));
					
					int amount = claimInfo.getDbInt("rtnempresamt");
					if(CMConst.PAYWAY_TYPE_ACCOUNTTRANS.equals(orderInfo.getDbStr("paywaytype"))
							|| CMConst.PAYWAY_TYPE_VIRACCOUNT.equals(orderInfo.getDbStr("paywaytype"))) {
						//가상계좌/계좌이체일경우 현금영수증 취소
						amount = amount + claimInfo.getDbInt("rtnpayamt");
					} else if(CMConst.PAYWAY_TYPE_NAVER.equals(orderInfo.getDbStr("paywaytype"))) {
						//네이버페이일경우
						//주결제수단이 계좌이체일경우
						if("BANK".equals(payInfo.getDbStr("primarypaymeans"))) {
							amount = amount + payInfo.getDbInt("primarypaycancelamount");
						}
						//네이버페이 포인트 취소
						if(payInfo.getDbInt("npointcancelamount") > 0) {
							amount = amount + payInfo.getDbInt("npointcancelamount");
						}
						//키프트카드 취소
						if(payInfo.getDbInt("giftcardcancelamount") > 0) {
							amount = amount + payInfo.getDbInt("giftcardcancelamount");
						}
					}
					if(amount > 0) {
						paymentParam.put("amount", amount);
						tossService.tossCashReceiptsCancel(paymentParam);
					}
				}
				
				// 반품 환불 완료시 Message
				if(CMConst.CLM_RETURN.equals(param.getDbStr("clmtype"))) {
					SOMap refundMsgParam = new SOMap();
					refundMsgParam.put("number", orderInfo.get("ordtel"));
					refundMsgParam.put("ordno", orderInfo.get("ordno"));
					refundMsgParam.put("orderdate", DateTimeUtil.getDateKrFormat(orderInfo.getStr("orderdate")));
					refundMsgParam.put("goodsname", claimItems.get(0).get("goodsname"));
					refundMsgParam.put("clmno", claimInfo.get("clmno"));
					refundMsgParam.put("clmcnt", claimItems.size()-1);
					refundMsgParam.put("userno", orderInfo.get("userno"));
					refundMsgParam.put("refundamt", NumberFormat.getInstance().format(claimInfo.get("rtnpayamt")));
					refundMsgParam.put("rtnresamt", NumberFormat.getInstance().format(claimInfo.get("rtnresamt")));
					refundMsgParam.put("rtnempresamt", NumberFormat.getInstance().format(claimInfo.get("rtnempresamt")));
					refundMsgParam.put("refundwaytype", orderInfo.get("paywaytypename"));
					cjMessageService.sendRefundComplete(refundMsgParam);
				}
			}
			
			/**
			 * 쿠폰사용취소
			 */
			List<SOMap> couponList = claimCouponMapper.selectOrderCouponList(param);
			for (SOMap coupon : couponList) {
				couponMemissueMapper.updateCouponUsedCancel(coupon);
			}
			
			/**
			 * 파트너사 상품 재고복원
			 */
			SOMap dbparam = new SOMap();
			dbparam.put("clmidx", claimInfo.getDbInt("clmidx"));
			List<SOMap> claimGoodsList = claimGoodsMapper.selectMyClaimGoodsList(dbparam);
			for (SOMap claimGoods : claimGoodsList) {
				if(!Util.flag2Bool(claimGoods.getDbStr("ispbgoods")) 
						&& (CMConst.CNC_PRCCOMP.equals(claimGoods.getDbStr("clmstatus")) || CMConst.RETURN_COMPLETE.equals(claimGoods.getDbStr("clmstatus")))) {
					SOMap optionDetail = goodsOptionDetailMapper.selectDetailOptionCode(claimGoods);
					optionDetail.put("stockcnt", optionDetail.getDbInt("stockcnt") + claimGoods.getDbInt("clmcnt"));
					goodsOptionDetailMapper.updateGoodsOptionDetailStockCnt(optionDetail);
				}
			}
			
			SOMap erpParam = new SOMap();
			erpParam.put("siteid", cs.getStr("siteid"));
			erpParam.put("clmidx", claimInfo.getDbInt("clmidx"));
			if(CMConst.CLM_CANCEL.equals(param.getDbStr("clmtype"))) {
				erpService.insertOrderCancelERPData(erpParam);
				// 취소처리 완료시 Message
				SOMap cncCompParam = new SOMap();
				cncCompParam.put("number", orderInfo.get("ordtel"));
				cncCompParam.put("userno", orderInfo.get("userno"));
				cncCompParam.put("ordno", orderInfo.get("ordno"));
				cncCompParam.put("clmno", claimInfo.get("clmno"));
				cncCompParam.put("orderdate", DateTimeUtil.getDateKrFormat(orderInfo.getStr("orderdate")));
				cncCompParam.put("goodsname", claimItems.get(0).get("goodsname"));
				cncCompParam.put("ordcnt", claimItems.size()-1);
				cncCompParam.put("cancelamt", NumberFormat.getInstance().format(claimInfo.get("rtnamt")));
				cncCompParam.put("rtnpayamt", NumberFormat.getInstance().format(claimInfo.get("rtnpayamt")));
				cncCompParam.put("cancelwaytype", orderInfo.get("paywaytypename"));
				cncCompParam.put("rtnresamt", NumberFormat.getInstance().format(claimInfo.get("rtnresamt")));
				cncCompParam.put("rtnempresamt", NumberFormat.getInstance().format(claimInfo.get("rtnempresamt")));
				if (Util.equal(param.getStr("clmprctype"), CMConst.CLM_PRC_MEMBER)) {
					cjMessageService.sendOrderCancelBeforeDelivery(cncCompParam);
				} else {
					cjMessageService.sendManagerOrderCancel(cncCompParam);
				}
			} else if(CMConst.CLM_RETURN.equals(param.getDbStr("clmtype"))) {
				erpService.insertOrderReturnCompleteERPData(erpParam);
			} else {
				erpService.insertOrderExchangeCompleteERPData(erpParam);
			}
			
		} else if("CNS004".equals(param.getDbStr("cncstatus")) //취소반려
				|| "RTS004".equals(param.getDbStr("rtnstatus")) //반품반려
				|| "RTS005".equals(param.getDbStr("rtnstatus")) //반품철회
				|| "EXS004".equals(param.getDbStr("excstatus")) //교환반려
				|| "EXS005".equals(param.getDbStr("excstatus")) //교환철회
				) {
			SOMap claimInfo = claimMapper.selectClaimInfo(param);
			if(!Util.isEmpty(claimInfo.getDbStr("paymentkey"))) {
				SOMap paymentParam = new SOMap();
				paymentParam.put("paymentkey", claimInfo.getDbStr("paymentkey"));
				paymentParam.put("amount", claimInfo.getDbInt("addrpaytotprice"));
				paymentParam.put("requester", isAdmin() || isDealer() ? "2" : "1");
				
				if(!Util.isEmpty(claimInfo.getDbStr("rejreason"))) {
					paymentParam.put("reason", claimInfo.getDbStr("rejreason"));
				} else if(!Util.isEmpty(claimInfo.getDbStr("dropreason"))) {
					paymentParam.put("reason", claimInfo.getDbStr("dropreason"));
				} else {
					paymentParam.put("reason", claimInfo.getDbStr("clmtypenm") + " 철회/반려");
				}
				
				if(CMConst.PAYWAY_TYPE_NAVER.equals(claimInfo.getDbStr("addpaywaytype"))) {
					naverService.naverPaymentCancel(paymentParam);
				} else {
					tossService.tossPaymentCancel(paymentParam);
				}
			}

//			SOMap erpParam = new SOMap();
//			erpParam.put("siteid", cs.getStr("siteid"));
//			erpParam.put("clmidx", claimInfo.getDbInt("clmidx"));
//			if(CMConst.CLM_RETURN.equals(param.getDbStr("clmtype"))) {
//				erpService.insertOrderReturnCompleteERPData(erpParam);
//			} else if(CMConst.CLM_EXCHANGE.equals(param.getDbStr("clmtype"))){
//				erpService.insertOrderExchangeCompleteERPData(erpParam);
//			}
		} else if("RTS009".equals(param.getDbStr("rtnstatus")) || "EXS011".equals(param.getDbStr("excstatus"))){
			SOMap claimInfo = claimMapper.selectClaimInfo(param);

			SOMap erpParam = new SOMap();
			erpParam.put("siteid", cs.getStr("siteid"));
			erpParam.put("clmidx", claimInfo.getDbInt("clmidx"));
			if(CMConst.CLM_RETURN.equals(param.getDbStr("clmtype"))) {
				erpService.insertOrderReturnCompleteERPData(erpParam);
			} else if(CMConst.CLM_EXCHANGE.equals(param.getDbStr("clmtype"))){
				erpService.insertOrderExchangeCompleteERPData(erpParam);
			}
		} else if("RTS006".equals(param.getDbStr("rtnstatus")) || "EXS006".equals(param.getDbStr("excstatus"))){
			SOMap claimInfo = claimMapper.selectClaimInfo(param);

			SOMap erpParam = new SOMap();
			erpParam.put("siteid", cs.getStr("siteid"));
			erpParam.put("clmidx", claimInfo.getDbInt("clmidx"));
			if(CMConst.CLM_RETURN.equals(param.getDbStr("clmtype"))) {
				erpService.insertOrderReturnApplyERPData(erpParam);
			} else if(CMConst.CLM_EXCHANGE.equals(param.getDbStr("clmtype"))){
				erpService.insertOrderExchangeApplyERPData(erpParam);
			}
		} else if("RTS003".equals(param.getDbStr("rtnstatus")) || "EXS003".equals(param.getDbStr("excstatus"))){
			SOMap claimInfo = claimMapper.selectClaimInfo(param);
			param.put("orderidx", claimInfo.get("orderidx"));
			SOMap orderInfo = comOrderMapper.selectComOrderInfo(param);
			List<SOMap> claimItems = claimGoodsMapper.selectClaimGoodsList(param);
			SOMap sendParam = new SOMap();

			if(CMConst.CLM_RETURN.equals(param.getDbStr("clmtype"))) {
				sendParam.put("claimtypename", "반품");
			} else if(CMConst.CLM_EXCHANGE.equals(param.getDbStr("clmtype"))){
				sendParam.put("claimtypename", "교환");
			}
			sendParam.put("number", orderInfo.get("ordtel"));
			sendParam.put("ordcnt", 0);
			sendParam.put("goodsname", claimItems.get(0).get("goodsname"));
			sendParam.put("clmno", claimInfo.get("clmno"));
			sendParam.put("userno", orderInfo.get("userno"));
			cjMessageService.sendInquiryrReception(sendParam);
		}
		
		return result;
	}
	
	/**
	 * 클레임 결제처리
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap paymentClaim(SOMap param) throws Exception {
		param.put("siteid", cs.getStr("siteid"));
		param.put("userid", getMemberId());
		
		if(param.getDbStr("clmno").contains("_")) {
			param.put("clmno", param.getDbStr("clmno").split("_")[0]);
		}
		SOMap claimInfo = claimMapper.selectClaimInfo(param);
		if(param.getDbInt("amount") != claimInfo.getDbInt("addrpaytotprice")) {
			throw new BizException("실결제금액과 결제금액이 일치하지 않습니다.");
		}
		SOMap payInfo = null;
		if(Util.flag2Bool(param.getDbStr("isnaverpay"))) {
			payInfo = naverService.naverPayment(param);
		} else {
			payInfo = tossService.tossPayment(param);
		}
				
		payInfo.put("clmidx", claimInfo.getDbInt("clmidx"));
		payInfo.put("userid", getMemberId());
		
		try {
			//결제정보 저장
			tossreceiptMapper.insertTossreceipt(payInfo);
			
			//클레임 결제정보 수정
			claimMapper.updateClaimPayment(payInfo);
			
			param.put("clmidx", claimInfo.getDbInt("clmidx"));
			param.put("clmtype", claimInfo.getDbStr("clmtype"));
			
			if(CMConst.CLM_CANCEL.equals(claimInfo.getDbStr("clmtype"))) {
				param.put("cncstatus", "CNS005");
			} else if(CMConst.CLM_RETURN.equals(claimInfo.getDbStr("clmtype"))) {
				param.put("rtnstatus", "RTS003");
			} else if(CMConst.CLM_EXCHANGE.equals(claimInfo.getDbStr("clmtype"))) {
				param.put("excstatus", "EXS003");
			}
			
			this.saveClaimStatus(param);
		} catch (Exception e) {
			param.put("reason", "결제처리중 오류발생");
			param.put("requester", isMember() ? "1" : "2");
			if(Util.flag2Bool(param.getDbStr("isnaverpay"))) {
				naverService.naverPaymentCancel(param);
			} else {
				tossService.tossPaymentCancel(param);
			}
			throw e;
		}
		SOMap result = new SOMap();
		result.put("clmno", param.getDbStr("clmno"));
		result.put("clmtype", claimInfo.getDbStr("clmtype"));
		
		return result;
	}
	
	
	/**
	 * List Map key를 이용한 중복합계
	 * @param list
	 * @param key
	 * @return
	 */
	private List<SOMap> mergeSum(List<SOMap> list, String key, String sumkey) {
		List<SOMap> result = new ArrayList<SOMap>();
		for (SOMap source : list) {
			int idx = -1;
			for (int i = 0 ; i < result.size() ; i++) {
				if(source.getDbStr(key).equals(result.get(i).getDbStr(key))) {
					idx = i;
					break;
				}
			}
			if(idx == -1) {
				result.add(source);
			} else {
				result.get(idx).put(sumkey, result.get(idx).getDbInt(sumkey) + source.getDbInt(sumkey));
			}
		}
		
		return result;
	}
	
	/**
	 * List Map에 Key값의 Index return;
	 * @param list
	 * @param key
	 * @param value
	 * @return
	 */
	private int containIdx(List<SOMap> list, String key, int value) {
		int idx = -1;
		for(int i = 0 ; i < list.size() ; i++) {
			if(list.get(i).getDbInt(key) == value) {
				idx = i;
				break;
			}
		}
		
		return idx;
	}
}
