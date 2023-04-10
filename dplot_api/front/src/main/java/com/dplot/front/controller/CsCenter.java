package com.dplot.front.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.exception.BizException;
import com.dplot.front.service.FrontCsService;
import com.dplot.front.service.FrontGoodsService;
/**
 * 
 * @FileName : CategoryController.java
 * @Project : datapick_api
 * @Date : 2021. 11. 12. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 11. 12.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@RestController
@RequestMapping("cscenter")
public class CsCenter {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CsCenter.class);
	
	@Autowired
	private FrontCsService frontCsService;

	/** 자주하는질문 Main 노출
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mainfaq",  method=RequestMethod.POST)
	public Response cscenterMainFaq(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("list", frontCsService.selectMainFaq(param));

        return new Response(result);
	}
	
	
	/** 고객센터 FAQ 검색
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/faq",  method=RequestMethod.POST)
	public Response cscenterFaq(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("list", frontCsService.selectFaq(param));
        result.put("listcount", frontCsService.selectFaqCount(param));

        return new Response(result);
	}
	
	/** 조회수 증가
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/hit",  method=RequestMethod.POST)
	public Response cscenterFaqHit(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        frontCsService.addHit(param);

        return new Response(result);
	}
	
	
	/**
	 * 고객센터 공지사항
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notice",  method=RequestMethod.POST)
	public Response cscenterMainNotice(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("noticeList", frontCsService.selectMainNotice(param));
        result.put("noticeListCount", frontCsService.selectMainNoticeCount(param));
        
		return new Response(result);
	}
	
	/**
	 * 고객센터 공지사항 상세
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/noticedetail",  method=RequestMethod.POST)
	public Response cscenterNoticeDetail(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("list", frontCsService.selectNoticeDetail(param));
        
		return new Response(result);
	}
	
	
	/**
	 * 1:1 문의 메인페이지
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/inquiry",  method=RequestMethod.POST)
	public Response cscenterInquiryMain(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        //result.put("inquirytype", frontCsService.selectGroupInquiryType(param));
        result.put("list", frontCsService.selectInquiry(param));
        result.put("listcount", frontCsService.selectInquiryCount(param));
        
		return new Response(result);
	}
	
	/**
	 * 1:1 문의 상세
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/inquirydtl",  method=RequestMethod.POST)
	public Response cscenterInquiryDetail(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("list", frontCsService.selectInquiryDetail(param));
        
		return new Response(result);
	}
	
	/**
	 * 1:1 문의 내역 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/inquirydelete",  method=RequestMethod.POST)
	public Response cscenterInquiryDelete(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        if(frontCsService.deleteInquiryIdx(param) == 0) {
        	result.put("msg", "답변이 등록된 문의는 수정/삭제할 수 없습니다.");
        	result.put("msgcode", "AL-061");
        }else{
        	result.put("msg", "문의 내용이 삭제되었습니다.");        	
        	result.put("msgcode", "AL-041");
        }
        
		return new Response(result);
	}
	
	/**
	 * 상품 Q&A
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/qna",  method=RequestMethod.POST)
	public Response cscenterInquiryqna(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("list", frontCsService.selectInquiryQna(param));
        result.put("listcount", frontCsService.selectInquiryQnaCount(param));
        
		return new Response(result);
	}
	
	
	/**
	 * 상품 Q&A 상세
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/qnadetail",  method=RequestMethod.POST)
	public Response cscenterInquiryqnaDetail(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("list", frontCsService.selectInquiryQnaDetail(param));
        
		return new Response(result);
	}
	
	/**
	 * 상품별 문의 전체
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/qnagoods",  method=RequestMethod.POST)
	public Response selectQnaGoods(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        result.put("qnalist", frontCsService.selectQnaGoods(param));
        result.put("qnalistcount", frontCsService.selectQnaGoodsCount(param));
        result.put("goodsoption", frontCsService.selectGoodsOption(param));
        
		return new Response(result);
	}	
	
	/**
	 * 상품별 문의 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/regqna",  method=RequestMethod.POST)
	public Response insertGoodsQna(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        int insertResult = frontCsService.insertGoodsQna(param);
        
        if(insertResult > 0){
            result.put("msg", "success");        	
        }else{
        	result.put("msgcode", "fail");        	
        }
        
		return new Response(result);
	}	
	
	
	/**
	 * 상품별 문의 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/qnadelete",  method=RequestMethod.POST)
	public Response deleteMyQna(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        int qna = frontCsService.deleteMyQna(param);
        
        if(qna > 0){
        	result.put("message", "문의 내용이 삭제되었습니다.");
        }else{
        	result.put("message", "문의 내용 삭제에 실패하였습니다.");
        }
        
		return new Response(result);
	}	
	
	/**
	 * 주문상품 선택
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/searchorder",  method=RequestMethod.POST)
	public Response cscentersearchOrder(@RequestBody SOMap param) throws Exception {
        SOMap result = new SOMap();
        List<SOMap> Orderlist = new ArrayList<>();     
        
        //주문번호 검색
        Orderlist = frontCsService.selectComorder(param);
                
        //Ordergoods = frontCsService.selectComorderGoods(param);
        
        result.put("orderlist", Orderlist);
        result.put("orderlistCount", frontCsService.selectComorderCount(param));
        
		return new Response(result);
	}
	
	
	/**
	 * 1:1 문의 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/registerinq",  method=RequestMethod.POST)
	public Response cscenterRegisterInq(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();                
		return new Response(frontCsService.saveInquiry(params, uploadFile));
	}
	
	/**
	 * 1:1 문의 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/reviseinq",  method=RequestMethod.POST)
	public Response cscenterReviseInq(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		SOMap result = new SOMap();
		frontCsService.reviseInquiry(params, uploadFile);
		return new Response(result);
	}
	
	
	/**상품 Q&A 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/reviseqna",  method=RequestMethod.POST)
	public Response cscenterReviseqna(@RequestBody SOMap params) throws Exception {		
        SOMap result = new SOMap();
        int qna = frontCsService.updateMyQna(params);
        
        if(qna > 0){
        	result.put("message", "문의 내용이 수정되었습니다.");
        }else{
        	result.put("message", "문의 내용 수정에 실패하였습니다.");
        }
		
		return new Response(result);
	}
	
	/**상품 Q&A 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/store",  method=RequestMethod.POST)
	public Response cscenterStore(@RequestBody SOMap params) throws Exception {		
        SOMap result = new SOMap();
        
        result = frontCsService.sendPartnersApply(params);
        
		return new Response(result);
	}
	
	/**상품 옵션 호출
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/option",  method=RequestMethod.POST)
	public Response cscenterOption(@RequestBody SOMap params) throws Exception {		
        SOMap result = new SOMap();
        
        List<SOMap> optionList = frontCsService.selectGoodsOption(params);
        result.put("optionlist", optionList);
        
		return new Response(result);
	}
	
	/**상품 옵션 호출
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/optiondtl",  method=RequestMethod.POST)
	public Response cscenterOptionDetail(@RequestBody SOMap params) throws Exception {		
        SOMap result = new SOMap();
        
        List<SOMap> optionDetailList = frontCsService.selectGoodsOptionDetail(params);
        result.put("optiondtl", optionDetailList);
        
		return new Response(result);
	}
	
	/**상품 온션 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/reviseopn",  method=RequestMethod.POST)
	public Response cscenterReviseOption(@RequestBody SOMap params) throws Exception {		
        SOMap result = new SOMap();
        
        result.put("seloption", frontCsService.searchOption(params));
        
		return new Response(result);
	}
	
	/**상품 온션 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/revqna",  method=RequestMethod.POST)
	public Response cscenterReviseQna(@RequestBody SOMap params) throws Exception {		
        SOMap result = new SOMap();
        if (params.getInt("idx") == 0) {
			throw new BizException("QNA 정보가 없습니다.");
		}
        int update = frontCsService.updateQna(params);
        if(update > 0){
        	result.put("msg", "success");
        }else{
        	result.put("msg", "fail");        	
        }
        
		return new Response(result);
	}
}
