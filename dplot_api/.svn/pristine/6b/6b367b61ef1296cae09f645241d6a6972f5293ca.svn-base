package com.dplot.admin.service.promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.EPointMapper;
import com.dplot.mapper.IFLogMapper;
import com.dplot.mapper.PromoCateMapper;
import com.dplot.mapper.PromoGiftMapper;
import com.dplot.mapper.PromoGoodsMapper;
import com.dplot.mapper.PromotionMapper;
import com.dplot.util.Util;

/**
 * @discription	: 프로모션관리 ServiceImpl
 * @fileName	: AdminPromotionServiceImpl.java
 * @author		: JSK
 * @date		: 2021.12.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.28	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminPromotionServiceImpl extends MallBaseService implements AdminPromotionService {
    private static final Logger logger = LoggerFactory.getLogger(AdminPromotionServiceImpl.class);

    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private PromoCateMapper promoCateMapper;
    @Autowired
    private PromoGoodsMapper promoGoodsMapper;
    @Autowired
    private PromoGiftMapper promoGiftMapper;
    @Autowired
    private EPointMapper ePointMapper;
	@Autowired
	private IFLogMapper ifLogMapper;
    
    /**
     * 프로모션 목록 조회
     *
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectPromotionList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        params.put("siteid", cs.getStr("siteid"));
        List<SOMap> resultList = promotionMapper.selectPromotionList(params);
        SOMap count = promotionMapper.selectPromotionListCount(params);
        
        result.put("list", resultList);
        result.put("count", count);
        return result;
    }
    
    /**
     * 프로모션 목록 조회(엑셀용)
     *
     * @param params
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectPromotionListForExcel(SOMap params) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        params.put("siteid", cs.getStr("siteid"));
		resultList = promotionMapper.selectPromotionListForExcel(params);
		return resultList;
    }

    /**
     * 프로모션 상세정보 조회
     *
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectPromotionInfo(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        
        // 프로모션 정보조회
        SOMap promotionMap = promotionMapper.selectPromotionDetail(params);
        result.put("info", promotionMap);
        
        // 프로모션 카테고리, 상품, 사은품 목록 조회
        result.put("promocatelist", promoCateMapper.selectPromoCateList(params));
        params.put("filetype", CMConst.FILE_TYPE_IMG);			// 파일타입_이미지 
        params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S);	// 이미지구분_상품_PC이미지_소 
        result.put("promogoodslist", promoGoodsMapper.selectPromoGoodsList(params));
        params.put("imgtype", CMConst.IMG_TYPE_GIFT_IMG_PC_S);	// 이미지구분_사은품_PC이미지_소 
        result.put("promogiftlist", promoGiftMapper.selectPromoGiftList(params));
        return result;
    }

    /**
     * 프로모션 저장
     *  
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {CustomException.class, Exception.class})
    public int savePromotion(SOMap params) throws CustomException {
    	int resultCnt = 0;
    	
        try {
			// 파라미터 세팅
        	params.put("siteid", cs.getStr("siteid"));
			params.put("authuserid", cs.getStr("authuserid"));
        	
			// 이포인트인 경우 날짜 중복 체크
			if (Util.equal(params.getStr("promodivtype"), CMConst.PROMO_DIV_RESERVE) && Util.equal(params.getStr("isreserve"), "F")) {
				params.put("epopayday", params.getStr("promostday"));
				params.put("epovalidday", params.getStr("promoedday"));
				int duplCnt = ePointMapper.checkDuplEpoint(params).getInt("check");
				if (duplCnt > 0) {
		            throw new CustomException("기존에 지급된 D포인트의 유효기간이 만료되지 않아 신규 수동지급이 불가합니다.");
				}
			}
			
			// 프로모션 저장
			resultCnt = promotionMapper.insertPromotion(params);
        	
        	// 특정카테고리인경우 프로모션 카테고리 저장
			if (Util.equal(params.getStr("istotcate"), "F")) {
				List<Map<String, Object>> promocatelist = params.getArrayList("promocatelist");
				if (promocatelist != null && promocatelist.size() > 0) {
					promoCateMapper.insertPromoCate(params);
				}
			}
        	
        	// 특정상품 추가제외인 경우 프로모션 상품 저장
			if (!Util.equal(params.getStr("goodsrangetype"), CMConst.GOODS_RANGE_ALL)) {
				List<Map<String, Object>> promogoodslist = params.getArrayList("promogoodslist");
				if (promogoodslist != null && promogoodslist.size() > 0) {
					promoGoodsMapper.insertPromoGoods(params);
				}
			}
        	
        	// 프로모션 사은품 저장
			if (Util.equal(params.getStr("promodivtype"), CMConst.PROMO_DIV_GIFT)) {
				List<Map<String, Object>> promogiftlist = params.getArrayList("promogiftlist");
				if (promogiftlist != null && promogiftlist.size() > 0) {
					promoGiftMapper.insertPromoGift(params);
				}
			}
			
	        // ERP LOG INSERT
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_PROMOTION_TYPE);
			ifLogMap.put("aud", CMConst.IF_LOG_ADD);
			ifLogMap.put("orgno", params.get("promoidx"));
			ifLogMapper.insertIfLogERPData(ifLogMap);
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("프로모션 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }

    /**
     * 프로모션 수정
     *  
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {CustomException.class, Exception.class})
    public int updatePromotion(SOMap params) throws CustomException {
    	int resultCnt = 0;
    	
        try {
			// 파라미터 세팅
        	params.put("siteid", cs.getStr("siteid"));
			params.put("authuserid", cs.getStr("authuserid"));
        	
			// 이포인트인 경우 날짜 중복 체크
			if (Util.equal(params.getStr("promodivtype"), CMConst.PROMO_DIV_RESERVE) && Util.equal(params.getStr("isreserve"), "F")) {
				params.put("epopayday", params.getStr("promostday"));
				params.put("epovalidday", params.getStr("promoedday"));
				int duplCnt = ePointMapper.checkDuplEpoint(params).getInt("check");
				if (duplCnt > 0) {
		            throw new CustomException("기존에 지급된 D포인트의 유효기간이 만료되지 않아 지급이 불가합니다.");
				}
			}
			
			// 프로모션 수정
			resultCnt = promotionMapper.updatePromotion(params);

        	// 프로모션 카테고리 저장
			promoCateMapper.deletePromoCate(params);
			if (Util.equal(params.getStr("istotcate"), "F")) {
				List<Map<String, Object>> promocatelist = params.getArrayList("promocatelist");
				if (promocatelist != null && promocatelist.size() > 0) {
					promoCateMapper.insertPromoCate(params);
				}
			}
			
        	// 프로모션 상품 저장
			promoGoodsMapper.deletePromoGoods(params);
			if (!Util.equal(params.getStr("goodsrangetype"), CMConst.GOODS_RANGE_ALL)) {
				List<Map<String, Object>> promogoodslist = params.getArrayList("promogoodslist");
				if (promogoodslist != null && promogoodslist.size() > 0) {
					promoGoodsMapper.insertPromoGoods(params);
				}
			}
			
        	// 프로모션 사은품 저장
			promoGiftMapper.deletePromoGift(params);
			if (Util.equal(params.getStr("promodivtype"), CMConst.PROMO_DIV_GIFT)) {
				List<Map<String, Object>> promogiftlist = params.getArrayList("promogiftlist");
				if (promogiftlist != null && promogiftlist.size() > 0) {
					promoGiftMapper.insertPromoGift(params);
				}
			}
			
	        // ERP LOG INSERT
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_PROMOTION_TYPE);
			ifLogMap.put("aud", CMConst.IF_LOG_UPDATE);
			ifLogMap.put("orgno", params.get("promoidx"));
			ifLogMapper.insertIfLogERPData(ifLogMap);
        } catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("프로모션 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
    
    /**
     * 프로모션 사용여부 갱신
     *  
     * @param params
     * @return int
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int updatePromotionUse(SOMap params) throws Exception {
    	int resultCnt = 0;
		
        try {
			// 1. 파라미터 세팅
        	params.put("siteid", cs.getStr("siteid"));
			params.put("authuserid", cs.getStr("authuserid"));
			
			// 2. 프로모션 사용여부 저장
			resultCnt = promotionMapper.updatePromotionUse(params);
			
	        // ERP LOG INSERT
			SOMap ifLogMap = new SOMap();
			ifLogMap.put("siteid", params.get("siteid"));
			ifLogMap.put("type", CMConst.ERP_PROMOTION_TYPE);
			ifLogMap.put("aud", CMConst.IF_LOG_UPDATE);
			ifLogMap.put("orgnolist", params.getArrayList("promoidxlist"));
			ifLogMapper.insertIfLogERPData(ifLogMap);
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("프로모션 상태 전환시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
        return resultCnt;
    }
}
