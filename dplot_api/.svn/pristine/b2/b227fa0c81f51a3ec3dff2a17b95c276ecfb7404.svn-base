package com.dplot.admin.controller.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.admin.service.goods.GoodsRegistService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @discription	: 상품등록 Controller
 * @fileName	: GoodsRegistController.java
 * @author		: JSK
 * @date		: 2021.11.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.09	JSK			최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value={"/admin/goods/regist", "/partners/goods/regist"})
public class GoodsRegistController extends ExcelDownController{
	
    @Autowired
    private GoodsRegistService goodsRegistService;

    /**
     * 기본정보 - 파트너사 목록 조회
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/partner/list", method = RequestMethod.POST)
    public Response partnerList() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectPartnerList());
        return new Response(result);
    }

    /**
     * 기본정보 - 카테고리명 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/catename/list", method = RequestMethod.POST)
    public Response categorynameList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectCategorynameList(params));
        return new Response(result);
    }
    
    /**
     * 기본정보 - 카테고리 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/cate/list", method = RequestMethod.POST)
    public Response categoryList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectCategoryList(params));
        return new Response(result);
    }
    
    /**
     * 기본정보 - 카테고리 템플릿 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/catetemp/list", method = RequestMethod.POST)
    public Response categoryTemplateList() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectCateTemplateList());
        return new Response(result);
    }
    
    /**
     * 기본정보 - 카테고리 템플릿 저장
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/catetemp/save", method = RequestMethod.POST)
    public Response saveCategoryTemplate(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", goodsRegistService.saveCategoryTemplate(params));
        return new Response(result);
    }
    
    /**
     * 기본정보 - 담당MD 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/chargemd/list", method = RequestMethod.POST)
    public Response chargemdList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectChargemdList(params));
        return new Response(result);
    }
    
    /**
     * 기본정보 - 브랜드 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/brand/list", method = RequestMethod.POST)
    public Response brandList(@RequestBody SOMap params) throws Exception {
        return new Response(goodsRegistService.selectBrandList(params));
    }
    
    /**
     * 판매정보 - 회원정보 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/userinfo", method = RequestMethod.POST)
    public Response userInfoList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectUserInfoList(params));
        return new Response(result);
    }

    /**
     * 옵션정보 - 연결상품목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/linked/list", method = RequestMethod.POST)
    public Response linkedGoodsList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectOptionErpList(params));
        return new Response(result);
    }

    /**
     * 배송정보 - 배송템플릿목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/delivtemp/list", method = RequestMethod.POST)
    public Response deilivTempList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectDelivTemplateList(params));
        return new Response(result);
    }

    /**
     * 배송정보 - 배송템플릿상세 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/delivtemp/detail", method = RequestMethod.POST)
    public Response deilivTempDetail(@RequestBody SOMap params) throws Exception {
        return new Response(goodsRegistService.selectDelivTemplateDetail(params));
    }

    /**
     * 배송정보 - 배송템플릿 저장
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/delivtemp/save", method = RequestMethod.POST)
    public Response saveDeilivTemp(@RequestBody SOMap params) throws Exception {
    	Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", goodsRegistService.saveDelivTemplate(params));
        return new Response(result);
    }
    
    /**
     * 배송정보 - 반품택배사 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/rtnlogis/list", method = RequestMethod.POST)
    public Response rtnLogisList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectRtnLogisList(params));
        return new Response(result);
    }
	
	/**
	 * 배송정보 - 개별반품택배사 조회
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	@RequestMapping(value = "/rtnManlogis/list", method = RequestMethod.POST)
	public Response rtnManLogisList(@RequestBody SOMap params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", goodsRegistService.selectRtnManLogisList(params));
		return new Response(result);
	}

    /**
     * 배송정보 - 제주/도서산간지역 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/isolation/list", method = RequestMethod.POST)
    public Response delivIsolList(@RequestBody SOMap params) throws Exception {
        return new Response(goodsRegistService.selectIsolationList(params));
    }
    
    /**
     * 배송정보 - 사업자주소 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/bizaddr", method = RequestMethod.POST)
    public Response bizAddr(@RequestBody SOMap params) throws Exception {
        return new Response(goodsRegistService.selectBizAddr(params));
    }
    
    /**
     * 상품정보고시 - 정보고시템플릿목록 조회
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/notifytpl/list", method = RequestMethod.POST)
    public Response notifyTplItemList() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectNotifyTplList());
        return new Response(result);
    }
    
    /**
     * 상품정보고시 - 정보고시템플릿항목 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/notifytplitem/list", method = RequestMethod.POST)
    public Response notifyTplItemList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectNotifyTplItemList(params));
        return new Response(result);
    }
    
    /**
     * 상품정보 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/goods/detail", method = RequestMethod.POST)
    public Response goodsDetail(@RequestBody SOMap params) throws Exception {
        return new Response(goodsRegistService.selectGoodsDetail(params));
    }
    
    /**
     * 상품 임시저장, 저장
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    public Response saveGoods(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
        return new Response(goodsRegistService.saveGoods(params, uploadFile));
    }
    
    /**
     * KC인증
     * @param params
     * @return kcCertification
     */
    @RequestMapping(value = "/kc/certification", method = RequestMethod.POST)
    public Response kcCertification(@RequestBody SOMap params){
        return new Response(goodsRegistService.goodsKcCertification(params));
    }

    /**
     * 딜상품등록 - 구성상품팝업 목록 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/constgoods/list", method = RequestMethod.POST)
    public Response constGoodsList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsRegistService.selectConstGoodsList(params));
        return new Response(result);
    }
    
    /**
     * 딜상품등록 - 구성상품목록 엑셀다운로드
     *
     * @param params
     * @return ResponseEntity<ByteArrayResource>
     * @throws Exception
     */
    @RequestMapping(value = "/constgoods/exceldown", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> constgoodsExcelDownload(@RequestBody SOMap params) throws Exception {
    	List<Map<String, Object>> list = goodsRegistService.selectGoodsConstGoodsListForExcel(params);
        
        String[] header = {"노출순위","대표상품","노출여부","판매구분","파트너사","상품코드","상품명","상품유형"
        				  ,"판매가","판매상태","전시시작일","전시종료일","배송정보명", "배송비", "배송유형"};
        String[] column = {"sortnum","ismaingoodsname","isdisplayname","ispbgoodsname","dealername","goodscode","goodsname","goodsdivtypename"
        				  ,"price","goodsselltypename","disstdate","diseddate","delivbname","delivfaretext","iscombdelivname"};
        int[] columnSize = {9*256,10*256,10*256,10*256,20*256,15*256,30*256,10*256
        				   ,15*256,10*256,20*256,20*256,15*256,20*256,10*256};
    	
    	return makeExcelFile(header, column, columnSize, list, "구성상품목록.xlsx");
    }
    
    /**
     * 딜상품정보 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/dealgoods/detail", method = RequestMethod.POST)
    public Response dealGoodsDetail(@RequestBody SOMap params) throws Exception {
        return new Response(goodsRegistService.selectDealGoodsDetail(params));
    }
    
    /**
     * 딜상품 임시저장, 저장
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/deal/save", method = RequestMethod.POST)
    public Response saveDealGoods(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
        return new Response(goodsRegistService.saveDealGoods(params, uploadFile));
    }
}
