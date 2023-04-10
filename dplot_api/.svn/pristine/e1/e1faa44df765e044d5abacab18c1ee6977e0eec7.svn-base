package com.dplot.admin.controller.operation;

import com.dplot.admin.service.operation.DisplayBlinkService;
import com.dplot.admin.service.operation.DisplayExhibitService;
import com.dplot.admin.service.operation.DisplayNewGoodsService;
import com.dplot.admin.service.operation.DisplayTimeService;
import com.dplot.common.service.InstagramFeedService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.controller.ExcelDownController;

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

/**
 * @author : LKW
 * @discription : 전시관리 Controller
 * @fileName : DisplayController.java
 * @date : 2021-11-26
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-26	LKW		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/display")
public class DisplayController extends ExcelDownController{

	@Autowired
	DisplayExhibitService displayExhibitService;

	@Autowired
    private InstagramFeedService instagramFeedService;
	
	@Autowired
	private DisplayNewGoodsService displayNewGoodsService;
	
	@Autowired
	private DisplayTimeService displayTimeService;
	
	@Autowired
	private DisplayBlinkService displayBlinkService;

    /**
     * 기획전 리스트 조회
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/exhibit/search", method = RequestMethod.POST)
    public Response exhibitPage(@RequestBody SOMap params) throws Exception {
        return new Response(displayExhibitService.selectExhibitList(params));
    }
    
    /**
     * 기획전 수정
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/exhibit/update", method = RequestMethod.POST)
    public Response exhibitUpdate(@RequestBody SOMap params) throws Exception {
    	Response response;
    	
    	try{
    		displayExhibitService.updateExhibit(params);
    		response = new Response(Status.OK);
    	}catch(Exception e){
    		response = new Response(Status.FAIL);
    	}
    	
        return response;
    }
    
    /**
     * 기획전 메인노출가능 체크
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/exhibit/check", method = RequestMethod.POST)
    public Response exhibitCheck(@RequestBody SOMap params) throws Exception {
        return new Response(displayExhibitService.checkExhibitMain(params));
    }
    
    /**
     * 기획전 엑셀 다운로드
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/exhibit/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> exhibitExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> list = displayExhibitService.selectExhibitExcelList(params);
        
        String[] header = {"No","메인노출","제목","유형","등급","적용채널","대상상품","사용여부","작성자","조회수","진행상태","시작일자","종료일자","등록일자"};
        int[] columnSize = {7*256,10*256,15*256,10*256,10*256,10*256,7*256,7*256,9*256,7*256,7*256,10*256,10*256,10*256};
    	
    	
    	return makeExcelFile(header, null, columnSize, list, "기획전목록.xlsx");
    }
    
    /**
     * 기획전 저장
     * @param params
     * @param fileParams
     * @return Response
     * @throws Exception 
     */
    @RequestMapping(value = "/exhibit/save", method = RequestMethod.POST)
    public Response saveExhibit(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
    	Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
    	return new Response(displayExhibitService.saveExhibit(params, uploadFile));
    }
    
    /**
     * 기획전 수정
     * @param params
     * @param fileParams
     * @return Response
     * @throws Exception 
     */
    @RequestMapping(value = "/exhibit/update/detail", method = RequestMethod.POST)
    public Response updateExhibit(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
    	Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
    	return new Response(displayExhibitService.updateExhibit(params, uploadFile));
    }
    
    /**
     * 기획전 상세 조회
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/exhibit/detail", method = RequestMethod.POST)
    public Response exhibitDetail(@RequestBody SOMap params) throws Exception {
        return new Response(displayExhibitService.selectExhibitDetail(params));
    }

    @RequestMapping(value = "/instargram/feed", method = RequestMethod.POST)
    public Response selectInstargramFeed(@RequestBody SOMap params) throws Exception {
        return new Response(instagramFeedService.selectInstagramFeed(params));
    }

    @RequestMapping(value = "/main/feed", method = RequestMethod.POST)
    public Response selectMainFeed(@RequestBody SOMap params) throws Exception {
        return new Response(instagramFeedService.selectMainFeed(params));
    }

    @RequestMapping(value="/insert/main/feed", method =RequestMethod.POST)
    public Response insertMainFeed(@RequestBody SOMap params) throws Exception {
        return new Response(instagramFeedService.insertMainFeed(params));
    }
    
    /**
     * 신상품 리스트 조회
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/newgoods/search", method = RequestMethod.POST)
    public Response newgoodsPage(@RequestBody SOMap params) throws Exception {
        return new Response(displayNewGoodsService.selectNewGoodsList(params));
    }
    
    /**
     * 신상품 수정
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/newgoods/update", method = RequestMethod.POST)
    public Response newgoodsUpdate(@RequestBody SOMap params) throws Exception {
        return new Response(displayNewGoodsService.updateNewGoodsList(params));
    }
    
    /**
     * 신상품 추가
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/newgoods/insert", method = RequestMethod.POST)
    public Response insertNewgoods(@RequestBody SOMap params) throws Exception {
        return new Response(displayNewGoodsService.insertNewGoodsList(params));
    }
    
    /**
     * 신상품 엑셀 다운로드
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/newgoods/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> newgoodsExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> list = displayNewGoodsService.selectNewGoodsExcelList(params);
        
        String[] header = {"No","메인노출","판매구분","파트너사명","브랜드","상품코드","상품명","담당MD","유형","등급","적용채널","사용여부","등록(지정)자","등록(지정)일자"};
        int[] columnSize = {7*256,10*256,10*256,13*256,10*256,13*256,35*256,10*256,10*256,10*256,10*256,10*256,10*256,15*256};
    	
    	
    	return makeExcelFile(header, null, columnSize, list, "신상품목록.xlsx");
    }
    
    /**
     * 타임특가 목록 조회
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/time/search", method = RequestMethod.POST)
    public Response timePage(@RequestBody SOMap params) throws Exception {
        return new Response(displayTimeService.selectTimeSpcPriceList(params));
    }
    
    /**
     * 타임특가 수정
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/time/update", method = RequestMethod.POST)
    public Response updateTimeSpc(@RequestBody SOMap params) throws Exception {
        return new Response(displayTimeService.updateTimeSpcPrice(params));
    }
    
    /**
     * 타임특가 추가
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/time/save", method = RequestMethod.POST)
    public Response insertTimeSpc(@RequestBody SOMap params) throws Exception {
        return new Response(displayTimeService.insertTimeSpcPrice(params));
    }
    
    /**
     * 타임특가 엑셀 다운로드
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/time/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> timeSpcPriceExcel(@RequestBody SOMap params) throws Exception {
        List<Map<String, Object>> list = displayTimeService.selectTimeSpcPriceExcelList(params);
        
        String[] header = {"No","판매구분","파트너사명","브랜드","상품코드","상품명","담당MD","유형","등급","적용채널","노출시작일자","노출종료일자","노출상태","판매시작일자","판매종료일자","판매상태","할인","사용여부","등록(지정)자","등록(지정)일자"};
        String[] column = {"no","ispbgoodsname","brandname","dealername","goodscode","goodsinfo","mdname","mumembertype","mumemlvtype","muappchtype","expsttime","expedtime","exptype","salesttime","saleedtime","saletype","flatrate","istrash","regusername","regdate"};
        int[] columnSize = {7*256,10*256,13*256,10*256,13*256,35*256,10*256,10*256,10*256,10*256,15*256,15*256,15*256,10*256,15*256,15*256,10*256,10*256,10*256,15*256};
    	
    	
    	return makeExcelFile(header, column, columnSize, list, "타임특가목록.xlsx");
    }
    
    /**
     * 타임특가 상세 조회
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/time/detail", method = RequestMethod.POST)
    public Response timeDetail(@RequestBody SOMap params) throws Exception {
        return new Response(displayTimeService.selectTimeSpcPriceDetail(params));
    }
    
    /**
     * 반짝특가 목록 조회
     *
     * @param params
     * @return	
     * @throws Exception
     */
    @RequestMapping(value = "/blink/search", method = RequestMethod.POST)
    public Response blinkPage(@RequestBody SOMap params) throws Exception {
        return new Response(displayBlinkService.selectBlinkSpcPriceList(params));
    }
}
