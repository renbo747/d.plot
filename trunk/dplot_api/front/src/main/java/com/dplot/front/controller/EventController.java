package com.dplot.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.front.service.FrontEventService;
import com.dplot.util.Util;

/**
 *
 * @FileName : EventController.java
 * @Project : datapick_api
 * @Date : 2021. 11. 22.
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 11. 22.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private FrontEventService frontEventService;

	/**
	 * 메인페이지 목록 조회
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Response mainIndex(@RequestBody SOMap param) throws Exception {
		param.put("isMobileRequest", Util.bool2Flag(true));
		return new Response(frontEventService.selectEventFrontListSQL(param));
	}

	/**
	 * 게시글 상세
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Response eventDetail(@RequestBody SOMap param) throws Exception {
		param.put("isMobileRequest", Util.bool2Flag(true));
		return new Response(frontEventService.selectEventFrontDetailSQL(param));
	}

	/**
	 * 게시글 덧글 (추가 조회)
	 */
	 @RequestMapping(value = "/comment", method = RequestMethod.POST)
	 public Response eventDetailComment(@RequestBody SOMap param) throws Exception {
//	 	param.put("isMobileRequest", Util.bool2Flag(true));
	 	return new Response(frontEventService.selectEventFrontCommentSQL(param));
	 }

	/**
	 * 스크래치 이벤트 참여횟수
	 */
	 @RequestMapping(value = "/getScratchEventJoinCount", method = RequestMethod.POST)
	 public Response getScratchEventJoinCount(@RequestBody SOMap param) throws Exception {
	 	return new Response(frontEventService.getScratchEventJoinCount(param));
	 }

	/**
	 * 스크래치 이벤트 참여
	 */
	 @RequestMapping(value = "/joinScratchEvent", method = RequestMethod.POST)
	 public Response joinScratchEvent(@RequestBody SOMap param) throws Exception {
	 	return new Response(frontEventService.joinScratchEvent(param));
	 }

	/**
	 * 래플 이벤트 대상건 조회
	 *
	 */
	 @RequestMapping(value = "/raffleSearch", method = RequestMethod.POST)
	 public Response raffleSearch(@RequestBody SOMap param) throws Exception {
	 	return new Response(frontEventService.raffleEventInquire(param));
	 }

	/**
	 * 래플 응모하기
	 *
	 */
	 @RequestMapping(value = "/raffleApply", method = RequestMethod.POST)
	 public Response raffleApply(@RequestBody SOMap param) throws Exception {
	 	return frontEventService.raffleEventApply(param);
	 }

	/**
	 * 래플 결과리스트
	 *
	 */
	 @RequestMapping(value = "/raffleWinList", method = RequestMethod.POST)
	 public Response raffleWinList(@RequestBody SOMap param) throws Exception {
	 	return frontEventService.raffleWinList(param);
	 }

		/**
		 * 래플2 이벤트 대상건 조회
		 *
		 */
		@RequestMapping(value = "/raffle2Search", method = RequestMethod.POST)
		public Response raffle2Search(@RequestBody SOMap param) throws Exception {
			return new Response(frontEventService.raffle2EventInquire(param));
		}

		/**
		 * 래플2 응모하기
		 *
		 */
		@RequestMapping(value = "/raffle2Apply", method = RequestMethod.POST)
		public Response raffle2Apply(@RequestBody SOMap param) throws Exception {
			return frontEventService.raffleEventApply(param);
		}

		/**
		 * 래플2 결과리스트
		 *
		 */
		@RequestMapping(value = "/raffle2WinList", method = RequestMethod.POST)
		public Response raffle2WinList(@RequestBody SOMap param) throws Exception {
			return frontEventService.raffleWinList(param);
		}

		/**
		 * 2022년 12월 홀리데이 이벤트 조회
		 *
		 */
		@RequestMapping(value = "/selectHoliday", method = RequestMethod.POST)
		public Response selectHoliday(@RequestBody SOMap param) throws Exception {
			return frontEventService.selectHoliday(param);
		}

		/**
		 * 2022년 12월 홀리데이 이벤트 스탬프 업데이트
		 *
		 */
		@RequestMapping(value = "/updateHolidayStamp", method = RequestMethod.POST)
		public Response updateHolidayStamp(@RequestBody SOMap param) throws Exception {
			return frontEventService.updateHolidayStamp(param);
		}

}
