package com.dplot.admin.controller.promotion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.promotion.AdminPromotionService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @discription	: 프로모션관리 Controller
 * @fileName	: AdminPromotionController.java
 * @author		: JSK
 * @date		: 2021.12.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.28	JSK			최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/promotion/promotion")
public class AdminPromotionController extends ExcelDownController{
	
    @Autowired
    AdminPromotionService adminPromotionService;

    /**
     * 프로모션 목록 조회
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response promotionList(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionService.selectPromotionList(params));
    }
    
    /**
     * 프로모션 목록 조회(엑셀용)
     *
     * @param params
     * @return ResponseEntity<ByteArrayResource>
     * @throws Exception
     */
    @RequestMapping(value = "/exceldown", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> promotionListForExcel(@RequestBody SOMap params) throws Exception {
    	List<Map<String, Object>> list = adminPromotionService.selectPromotionListForExcel(params);
    	for (int i=0; i<list.size(); i++) {
    		list.get(i).put("no", i+1);
    	}
    	
        String[] header = {"프로모션명", "유형", "등급", "적용채널", "구분", "할인", "사은품 지급조건", "대상상품", "구매수량조건", "대상사은품"
        				 , "사은품선택", "구매금액제한", "적립금", "사용여부", "시작일자", "종료일자", "등록자", "등록일자"};
        String[] column = {"promoname", "mumembertypename", "mumemlvtypename", "muappchtypename", "promodivtypename", "discountconts", "gifttermtypename", "goodscnt", "giftgoodscnt", "giftcnt"
        				 , "giftselcnt", "orderpricefromto", "reservepoint", "istrashname", "promostdate", "promoeddate", "regusername", "regdate"};
        int[] columnSize = {30*256, 20*256, 20*256, 20*256, 10*256, 15*256, 20*256, 10*256, 10*256, 10*256
        				  , 10*256, 25*256, 15*256, 10*256, 15*256, 15*256, 10*256, 15*256};
    	
    	return makeExcelFile(header, column, columnSize, list, "프로모션내역.xlsx");
    }

    /**
     * 프로모션 상세정보 조회
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response promotionDetail(@RequestBody SOMap params) throws Exception {
        return new Response(adminPromotionService.selectPromotionInfo(params));
    }

    /**
     * 프로모션 저장
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response savePromotion(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminPromotionService.savePromotion(params));
        return new Response(result);
    }

    /**
     * 프로모션 수정
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response updatePromotion(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminPromotionService.updatePromotion(params));
        return new Response(result);
    }
    
    /**
     * 프로모션 사용여부 갱신
     *
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/use/update", method = RequestMethod.POST)
    public Response promotionUseUpdate(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminPromotionService.updatePromotionUse(params));
        return new Response(result);
    }
}
