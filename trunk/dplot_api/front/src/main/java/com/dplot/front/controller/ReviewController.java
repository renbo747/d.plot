package com.dplot.front.controller;

import java.util.Map;

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
import com.dplot.front.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	/**
	 * 리뷰 작성 보상 정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reward", method = RequestMethod.POST)
	public Response selectReviewReward(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.selectReviewReward(param));
	}
	
	/**
	 * 상품상세 리뷰 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Response selectReviewList(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.selectReviewList(param));
	}
	
	/**
	 * 상품상세 포토&동영상 리뷰 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/photo/list", method = RequestMethod.POST)
	public Response selectPhotoReviewList(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.selectReviewList(param));
	}
	
	/**
	 * 나의 리뷰 목록 조회(작성가능, 작성완료)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mylist", method = RequestMethod.POST) 
	public Response selectMyReviewList(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.selectMyReviewList(param));
	}
	
	/**
	 * 나의 리뷰 상세 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST) 
	public Response selectReviewDetail(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.selectReviewDetail(param));
	}
	
	/**
	 * 리뷰 저장 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response saveReview(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		return new Response(reviewService.saveReview(params, uploadFile));
	}
	
	
	/**
	 * 리뷰 삭제 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Response deleteReview(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.deleteReview(param));
	}
	
	/**
	 * 리뷰 신고하기 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savenoti", method = RequestMethod.POST)
	public Response saveReviewNoti(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.saveReviewNoti(param));
	}
	
	/**
	 * 리뷰 좋아요 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/help", method = RequestMethod.POST)
	public Response saveReviewLike(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.saveReviewHelp(param));
	}
	
	/**
	 * 매거진 베스트리뷰 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mzReview", method = RequestMethod.POST)
	public Response selectMzReview(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.selectMzReview(param));
	}
	
	/**
	 * 매거진 라이브 리뷰 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mzLiveReview", method = RequestMethod.POST)
	public Response selectMzLiveReview(@RequestBody SOMap param) throws Exception {
		return new Response(reviewService.selectMzLiveReview(param));
	}
	
	
}
