package com.dplot.admin.controller.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.dplot.admin.service.goods.GoodsManageService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;
import com.dplot.util.Util;

/**
 * @discription	: 상품관리 Controller
 * @fileName	: GoodsManageController.java
 * @author		: JSK
 * @date		: 2021.12.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.08	JSK			최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = {"/admin/goods/manage", "/partners/goods/manage"})
public class GoodsManageController extends ExcelDownController{
    private static final Logger logger = LoggerFactory.getLogger(GoodsManageController.class);

    @Autowired
    private GoodsManageService goodsManageService;
    
    @Resource(name="propertiesFactory")
    private Properties prop;

    /**
     * 조회조건 - 파트너사 목록 조회
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/partner/list", method = RequestMethod.POST)
    public Response partnerList(@RequestBody SOMap params) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", goodsManageService.selectPartnerList(params));
        return new Response(result);
    }

    /**
     * 전체상품목록 조회
	 * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response goodsList(@RequestBody SOMap params) throws Exception {
        return new Response(goodsManageService.selectAllGoodsList(params));
    }

    /**
     * 전체상품목록 엑셀다운로드
	 * @param params
     * @return ResponseEntity<ByteArrayResource>
     * @throws Exception
     */
    @RequestMapping(value = "/exceldown", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> goodsListForExcel(@RequestBody SOMap params) throws Exception {
    	List<Map<String, Object>> list = goodsManageService.selectAllGoodsListForExcel(params);

        if (params.get("ispartner") != null && Util.equal(params.getStr("ispartner"), "true")) {
        	String[] header = {"판매상태","상품코드","상품명","정상가","판매가","수수료율(%)","재고","조회수"
        					  ,"전시상태","MD","등록일","수정일"};
        	String[] column = {"goodsselltypename","goodscode","goodsname","marketprice","price","commrate","stockcnt","hits"
        					  ,"isdisplayname","mdname","regdate","moddate"};
        	int[] columnSize = {15*256,15*256,30*256,20*256,20*256,10*256,10*256,10*256
        					   ,10*256,15*256,15*256,15*256};
        	return makeExcelFile(header, column, columnSize, list, "상품목록.xlsx");
        } else {
            String[] header = {"판매구분","파트너사명","상품구분","브랜드코드","브랜드명","상품코드","카테고리","상품명","정상가","판매가"
            				  ,"수수료율(%)","재고","조회수","판매상태","전시상태","MD", "등록일", "수정일"};
            String[] column = {"ispbgoodsname","dealername","isdealname","brcode","brandname","goodscode","fullcategoryname","goodsname","marketprice","price"
            		 		  ,"commrate","stockcnt","hits","goodsselltypename","isdisplayname","mdname","regdate","moddate"};
            int[] columnSize = {10*256,20*256,10*256,15*256,30*256,15*256,30*256,30*256,20*256,20*256
            				   ,10*256,10*256,10*256,15*256,10*256,15*256,15*256,15*256};
        	return makeExcelFile(header, column, columnSize, list, "상품목록.xlsx");
        }
    }

    /**
     * 상품 전시상태 변경
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/display/update", method = RequestMethod.POST)
    public Response goodsDisplayUpdate(@RequestBody SOMap params) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCnt", goodsManageService.updateGoodsDisplay(params));
        return new Response(result);
    }

    /**
     * 상품 판매상태 변경
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/goodsselltype/update", method = RequestMethod.POST)
    public Response goodsSellTypeUpdate(@RequestBody SOMap params) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCnt", goodsManageService.updateGoodsSellType(params));
        return new Response(result);
    }

    /**
     * 상품삭제
     * - 판매상태 영구종료 변경
     * - 상품 미전시로 변경
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public Response goodsDelete(@RequestBody SOMap params) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCnt", goodsManageService.updateGoodsTerminate(params));
        return new Response(result);
    }

    /**
     * 상품 승인상태 변경
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/apprtype/update", method = RequestMethod.POST)
    public Response goodsApprTypeUpdate(@RequestBody SOMap params) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCnt", goodsManageService.updateGoodsApprType(params));
        return new Response(result);
    }

    /**
     * 상품 처리(승인)이력 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/apprhist/list", method = RequestMethod.POST)
    public Response goodsApprHistList(@RequestBody SOMap params) throws Exception{
        return new Response(goodsManageService.selectGoodsApprHistList(params));
    }

    /**
     * 상품이력 조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/goodshist/list", method = RequestMethod.POST)
    public Response goodsHistList(@RequestBody SOMap params) throws Exception{
        return new Response(goodsManageService.selectGoodsHistList(params));
    }

    /**
     * 상품정보 일괄수정
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public Response goodsInfoUpdate(@RequestBody SOMap params) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
		Map<String, MultipartFile> uploadFile = new HashMap<String, MultipartFile>();
        result.put("resultCnt", goodsManageService.updateGoodsAll(params, uploadFile));
        return new Response(result);
    }

    /**
     * 상품정보 일괄수정
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value="/update/withfile", method = RequestMethod.POST)
    public Response goodsInfoUpdate(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
        result.put("resultCnt", goodsManageService.updateGoodsAll(params, uploadFile));
        return new Response(result);
    }
}
