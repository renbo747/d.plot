package com.dplot.front.service;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.MallConfigService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.mapper.CouponMemissueMapper;
import com.dplot.mapper.DadaEventEnterMapper;
import com.dplot.mapper.DadaEventGoodsMapper;
import com.dplot.mapper.DadaEventMapper;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.ReservePayMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;

/**
 *
 * @FileName : FrontEventServiceImpl.java
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
@Service
public class FrontEventServiceImpl extends MallBaseService implements FrontEventService {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(FrontEventServiceImpl.class);

	@Autowired
	protected MallConfigService cs;

	/** The BannerMapper. */
	@Autowired
	private DadaEventMapper dadaEventMapper;

	@Autowired
	DadaEventEnterMapper dadaEventEnterMapper;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private ReservePayMapper reservePayMapper;

	/** The MemberMapper. */
	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private CouponMemissueMapper couponMemissueMapper;

	@Autowired
	private DadaEventGoodsMapper dadaEventGoodsMapper;

	/**
	 * 이벤트 리스트 전체조회
	 */
	@Override
	public SOMap selectEventFrontListSQL(SOMap param) throws Exception{
		SOMap resultMap = new SOMap();
		List<SOMap> eventList = dadaEventMapper.selectEventFrontListSQL(param);

		for(SOMap comment : eventList){
			comment.put("file", getFile(comment.get("eventidx"), new String[]{"IGT062"}));
		}
		resultMap.put("eventList", eventList);
		return resultMap;
	}


	/**
	 * 이벤트 상세조회
	 */
	@Override
	public SOMap selectEventFrontDetailSQL(SOMap param) throws Exception{
		SOMap resultMap = new SOMap();
		List<SOMap> eventDetails = dadaEventMapper.selectEventFrontDetailSQL(param);

		if(eventDetails.isEmpty()){// 항목이 존재하는지 여부
			resultMap.put("eventItem", new SOMap());
		}else{
			SOMap eventDetail = eventDetails.get(0);// 첫번째 항목
			eventDetail.put("file", getFile(eventDetail.get("eventidx"), new String[]{"IGT062"}));
			resultMap.put("eventItem",eventDetail);
		}
		return resultMap;
	}

	/**
	 * 이벤트 리스트 덧글 목록 조회
	 */
	@Override
	public SOMap selectEventFrontCommentSQL(SOMap param) throws Exception{
		SOMap resultMap = new SOMap();
		List<SOMap> eventCommantList = dadaEventMapper.selectEventFrontDetailCommetSQL(param);
		List<SOMap> eventState = dadaEventMapper.selectEventFrontDetailCommetLenthSQL(param);
		List<SOMap> eventPage = dadaEventMapper.selectEventFrontDetailCommetPage(param);
		resultMap.put("length",eventState.get(0).get("length"));
		resultMap.put("page",eventPage.get(0).get("page"));

		for(SOMap comment : eventCommantList){
			SOMap query = new SOMap();
			query.put("idx", comment.get("commentidx"));
			comment.put("file", getFile(comment.get("commentidx"), new String[]{"IGT023", "IGT024"}));

			List<SOMap> eventSubCommantList = dadaEventMapper.selectEventFrontDetailCommentSubSQL(query);
			comment.put("subcomment", eventSubCommantList);
		}

		resultMap.put("comments", eventCommantList);
		return resultMap;
	}

	SOMap getFile(Object orgidx, String[] types) throws Exception{
		SOMap fparam = new SOMap();
		fparam.put("imgtypes", types);
		fparam.put("orgidx", orgidx);
		List<SOMap> files = fileMapper.selectFileList(fparam);
		if(files.isEmpty()){
			return new SOMap();
		}else{
			return files.get(0);
		}
	}

	/**
	 * 그랜드 오픈 스크래치 이벤트 응모 횟수 가져오기
	 * @param param
	 * @return
	 */
	public SOMap getScratchEventJoinCount(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();

		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));

		//로그인 체크
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 가능합니다.");
		}

		/**
		 * 금일 응모한 횟수 가져오기
		 */
		SOMap countInfo = dadaEventMapper.selectPromotionJoinCount(param);

		int todayJoinCount = countInfo.getInt("today");

		resultMap.put("todayJoinCount", todayJoinCount);
		resultMap.put("rewardSuccessYn", "N");
		resultMap.put("rewardAmount", 0);

		if(todayJoinCount >= 5) {
			resultMap.put("todayTryYn", "N");
		} else {
			resultMap.put("todayTryYn", "Y");
		}

		return resultMap;
	}

	/**
	 * 그랜드 오픈 스크래치 이벤트 응모
	 * @param param
	 * @return
	 */
	public SOMap joinScratchEvent(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();

		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));

		//로그인 체크
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 응모 가능합니다.");
		}

		/**
		 * 금일 응모한 횟수 가져오기
		 */
		SOMap countInfo = dadaEventMapper.selectPromotionJoinCount(param);

		int todayJoinCount = countInfo.getInt("today") + 1;	// 1을 더한 이유는 아직 데이터를 저장하지 않아서 그럼

		resultMap.put("todayJoinCount", todayJoinCount);
		resultMap.put("rewardSuccessYn", "N");
		resultMap.put("rewardAmount", 0);

		if(todayJoinCount > 5) {
			resultMap.put("todayTryYn", "N");
			resultMap.put("rewardSuccessYn", "N");
			resultMap.put("rewardAmount", 0);
		} else {
			resultMap.put("todayTryYn", "Y");

			/**
			 * 이벤트 응모 테이블(T_PROMOTION_JOIN) 에 데이터 저장
			 */
			dadaEventMapper.insertPromotionJoin(param);

			/**
			 * 당첨 여부 확인(T_PROMOTION_WINNING_CONDITION)
			 * 금일 지원횟수 : todayJoinCount
			 * 금일 지원가능여부 : todayTryYn
			 * 당첨 여부 : rewardSuccessYn
			 * 당첨 금액 : rewardAmount
			 * 회신
			 */
			SOMap rewardResult = dadaEventMapper.selectPromotionWinningCondition(param);

			if(rewardResult != null) {
				/**
				 * T_PROMOTION_WINNING_CONDITION에 update
				 * 한번에 몰릴수 있으므로 update에서 성공한 유저만 준다.
				 */
				rewardResult.put("userno", param.get("userno"));

				param.put("joinidx", rewardResult.get("rewardorder"));

				int result = dadaEventMapper.updatePromotionWinningCondition(param);

				if(result > 0) {

					resultMap.put("rewardSuccessYn", "Y");
					resultMap.put("rewardAmount", rewardResult.getInt("paypoint"));

					/**
					 * 적립금 부여 (T_RESERVE_PAY)
					 * 프로모션 번호는 테이블에서 가져오기
					 */
					rewardResult.put("respaytype", "RPT008");		// 프르모션에 의한 적립금 부여
					//rewardResult.put("paypoint", confirmInfo.getDbInt("paypoint"));
					String startDay = DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
					String endDay = DateTimeUtil.getPointEndDate(startDay);
					rewardResult.put("resstday", startDay);
					rewardResult.put("resedday", endDay);

					SOMap userInfo = memberMapper.selectMemberInfoByUserNo(param);

					rewardResult.put("paymembertype", userInfo.getStr("dadamembertype"));
					rewardResult.put("paymemlvtype", userInfo.getStr("memlvtype"));
					rewardResult.put("authuserid", "system");
					rewardResult.put("isempreserve", "F");

					reservePayMapper.insertReservePayAll(rewardResult);
				} else {
					resultMap.put("rewardSuccessYn", "N");
					resultMap.put("rewardAmount", 0);
				}
			}
		}

		return resultMap;
	}

	/**
	 * 그랜드 오픈 래플 이벤트
	 * @param param
	 * @return
	 */
	public SOMap raffleEventInquire(SOMap param) throws Exception {
		SOMap raffleContent = new SOMap();
		List<SOMap> raffleSearch = dadaEventMapper.selectRaffleEvent(param);

		List startDate = null;
		List endDate = null;
		List pubDate = null;
		int currentRaffle = 0;
		int eventidx = 0;
		int notieventidx = 0;

		int i=0;
		for(SOMap raffleUnit : raffleSearch){
			startDate = new ArrayList();
			startDate.add(raffleUnit.get("startyy").toString());
			startDate.add(raffleUnit.get("startmm").toString());
			startDate.add(raffleUnit.get("startdd").toString());
			startDate.add(raffleUnit.get("starthh").toString());
			startDate.add(raffleUnit.get("startmi").toString());
			startDate.add("00");
			raffleUnit.put("startDate", startDate);

			endDate = new ArrayList();
			endDate.add(raffleUnit.get("endyy").toString());
			endDate.add(raffleUnit.get("endmm").toString());
			endDate.add(raffleUnit.get("enddd").toString());
			endDate.add(raffleUnit.get("endhh").toString());
			endDate.add(raffleUnit.get("endmi").toString());
			endDate.add("00");
			raffleUnit.put("endDate", endDate);

			pubDate = new ArrayList();
			pubDate.add(raffleUnit.get("pubyy").toString());
			pubDate.add(raffleUnit.get("pubmm").toString());
			pubDate.add(raffleUnit.get("pubdd").toString());
			pubDate.add(raffleUnit.get("pubhh").toString());
			pubDate.add(raffleUnit.get("pubmi").toString());
			pubDate.add("00");
			raffleUnit.put("pubDate", pubDate);


			SOMap item = dadaEventMapper.selectRaffleEventItem((int) raffleUnit.get("eventidx"));
			raffleUnit.put("item", item);

			if("T".equals(raffleUnit.get("status").toString())) {
				currentRaffle = i;
				notieventidx = (int) raffleUnit.get("eventidx");
			}
			if("T".equals(raffleUnit.get("statusreal").toString())) {
				eventidx = (int) raffleUnit.get("eventidx");
			}
			i++;
//			logger.info("aaaaaaaaaaaaaaaaaaaaaaaa i :"+i+",eventidx:"+eventidx);
		}
		if(notieventidx == 0) {
			currentRaffle = i;
		}
		raffleContent.put("raffleContent", raffleSearch);
		raffleContent.put("currentRaffle", currentRaffle);
		raffleContent.put("eventidx", eventidx);

		return raffleContent;
	}

	/**
	 * 그랜드 오픈 래플 이벤트 응모하기
	 * @param param
	 * @return
	 */
	public Response raffleEventApply(SOMap param) throws Exception {
		Response res = new Response();
		if(!isMember()) {
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("로그인 후 가능합니다.");
			return res;
		}
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("issucc", "F");
		// 임직원1인지 조회
		List<SOMap> isDMT003 = dadaEventEnterMapper.selectAdminDadaEventDMT003(param);
		if(isDMT003 != null && isDMT003.size() > 0) {
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("임직원은 응모 불가합니다.");
			return res;
		}

		// 이벤트에 응모했는지 조회
		List<SOMap> isExist = dadaEventEnterMapper.selectAdminDadaEventEnterListPopup(param);
		if(isExist != null && isExist.size() > 0) {
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("이미 응모되었습니다.");
			return res;
		}

		dadaEventEnterMapper.insertAdminEventEnter(param);

		return res;
	}

	/**
	 * 그랜드 오픈 래플응모 결과
	 * @param param
	 * @return
	 */
	public Response raffleWinList(SOMap param) throws Exception {
		Response res = new Response();
		SOMap resultMap = new SOMap();
//		if(!isMember()) {
//			res.setStatusCode(Status.FAIL.getKey());
//			res.setMessage("'로그인 후 이벤트 참여해주세요.");
//			return res;
//		}
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("issucc", "F");

		// 이벤트에 응모했는지 조회

		SOMap resultWinning = dadaEventMapper.selectResultWinning(param);
		if("F".equals(resultWinning.get("isselectcomplete"))) {
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("아직 당첨자 추천전입니다.");
			return res;
		}

		List<SOMap> winnerlist = dadaEventMapper.selectraffleWinList(param);

		if(winnerlist == null || winnerlist.size() ==0) {
			logger.info("=================== is null ===========");
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("아직 당첨자 추천전입니다.");
			return res;
		}
		resultMap.put("winnerlist", winnerlist);
		res = new Response(resultMap);
		return res;
	}

	/**
	 * 그랜드 오픈 래플2 이벤트
	 *
	 * @param param
	 * @return
	 */
	public SOMap raffle2EventInquire(SOMap param) throws Exception {
		SOMap raffleContent = new SOMap();
		List<SOMap> raffleSearch = dadaEventMapper.selectRaffle2Event(param);

		List startDate = null;
		List endDate = null;
		List pubDate = null;
		int currentRaffle = 0;
		int eventidx = 0;
		int notieventidx = 0;

		int i = 0;
		for (SOMap raffleUnit : raffleSearch) {
			startDate = new ArrayList();
			startDate.add(raffleUnit.get("startyy").toString());
			startDate.add(raffleUnit.get("startmm").toString());
			startDate.add(raffleUnit.get("startdd").toString());
			startDate.add(raffleUnit.get("starthh").toString());
			startDate.add(raffleUnit.get("startmi").toString());
			startDate.add("00");
			raffleUnit.put("startDate", startDate);

			endDate = new ArrayList();
			endDate.add(raffleUnit.get("endyy").toString());
			endDate.add(raffleUnit.get("endmm").toString());
			endDate.add(raffleUnit.get("enddd").toString());
			endDate.add(raffleUnit.get("endhh").toString());
			endDate.add(raffleUnit.get("endmi").toString());
			endDate.add("00");
			raffleUnit.put("endDate", endDate);

			pubDate = new ArrayList();
			pubDate.add(raffleUnit.get("pubyy").toString());
			pubDate.add(raffleUnit.get("pubmm").toString());
			pubDate.add(raffleUnit.get("pubdd").toString());
			pubDate.add(raffleUnit.get("pubhh").toString());
			pubDate.add(raffleUnit.get("pubmi").toString());
			pubDate.add("00");
			raffleUnit.put("pubDate", pubDate);

			SOMap item = dadaEventMapper.selectRaffle2EventItem((int) raffleUnit.get("eventidx"));
			raffleUnit.put("item", item);

			if ("T".equals(raffleUnit.get("status").toString())) {
				currentRaffle = i;
				notieventidx = (int) raffleUnit.get("eventidx");
			}
			if ("T".equals(raffleUnit.get("statusreal").toString())) {
				eventidx = (int) raffleUnit.get("eventidx");
			}
			i++;
//			logger.info("aaaaaaaaaaaaaaaaaaaaaaaa i :"+i+",eventidx:"+eventidx);
		}
		if (notieventidx == 0) {
			currentRaffle = i;
		}
		raffleContent.put("raffleContent", raffleSearch);
		raffleContent.put("currentRaffle", currentRaffle);
		raffleContent.put("eventidx", eventidx);

		return raffleContent;
	}

	/**
	 * 그랜드 오픈 래플2 이벤트 응모하기
	 *
	 * @param param
	 * @return
	 */
	public Response raffle2EventApply(SOMap param) throws Exception {
		Response res = new Response();
		if (!isMember()) {
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("로그인 후 가능합니다.");
			return res;
		}
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("issucc", "F");
		// 임직원1인지 조회
		List<SOMap> isDMT003 = dadaEventEnterMapper.selectAdminDadaEventDMT003(param);
		if (isDMT003 != null && isDMT003.size() > 0) {
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("임직원은 응모 불가합니다.");
			return res;
		}

		// 이벤트에 응모했는지 조회
		List<SOMap> isExist = dadaEventEnterMapper.selectAdminDadaEventEnterListPopup(param);
		if (isExist != null && isExist.size() > 0) {
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("이미 응모되었습니다.");
			return res;
		}

		dadaEventEnterMapper.insertAdminEventEnter(param);

		return res;
	}

	/**
	 * 그랜드 오픈 래플2응모 결과
	 *
	 * @param param
	 * @return
	 */
	public Response raffle2WinList(SOMap param) throws Exception {
		Response res = new Response();
		SOMap resultMap = new SOMap();
//		if(!isMember()) {
//			res.setStatusCode(Status.FAIL.getKey());
//			res.setMessage("'로그인 후 이벤트 참여해주세요.");
//			return res;
//		}
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("issucc", "F");

		// 이벤트에 응모했는지 조회

		SOMap resultWinning = dadaEventMapper.selectResultWinning(param);
		if ("F".equals(resultWinning.get("isselectcomplete"))) {
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("아직 당첨자 추천전입니다.");
			return res;
		}

		List<SOMap> winnerlist = dadaEventMapper.selectraffleWinList(param);

		if (winnerlist == null || winnerlist.size() == 0) {
			logger.info("=================== is null ===========");
			res.setStatusCode(Status.CREATED.getKey());
			res.setMessage("아직 당첨자 추천전입니다.");
			return res;
		}
		resultMap.put("winnerlist", winnerlist);
		res = new Response(resultMap);
		return res;
	}

	/**
	 * holiday이벤트 조회
	 *
	 * @param param
	 * @return
	 */
	public Response selectHoliday(SOMap params) throws Exception {
		Response res = new Response();
		SOMap resultMap = new SOMap();
		params.put("siteid", cs.getStr("siteid"));
		params.put("userno", cs.getInt("authmemberno"));

		List<SOMap> holidaylist = dadaEventMapper.selectHoliday(params);
		resultMap.put("holidaylist", holidaylist);
		
		/**
		 * Holiday 이벤트 상품 리스트 추가
		 */
		params.put("platform", cs.getStr("platform"));
		params.put("membertype", cs.getInt("authmemberno") == 0 ? CMConst.MEMBERTYPE_BASIC : cs.getStr("authmembertype"));
		params.put("memlvtype", cs.getInt("authmemberno") == 0 ? CMConst.MEMLVTYPE_BRONZE : cs.getStr("authmemlvtype"));
		params.put("imgtype",Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_B : CMConst.IMG_TYPE_GOODS_IMG_PC_B);
		List<SOMap> goodslist = dadaEventGoodsMapper.selectEventDtlGoodsList_tuning_view(params);

		resultMap.put("goodslist", goodslist);
		res = new Response(resultMap);
		return res;
	}

	/**
	 * holiday이벤트 스탬프 업데이트
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Response updateHolidayStamp(SOMap params) throws Exception {
		Response res = new Response();
		params.put("userno", cs.getInt("authmemberno"));
		params.put("mumembertype", cs.getStr("authmembertype"));
		params.put("mumemlvtype", cs.getStr("authmemlvtype"));
		logger.info("==============>:" + params.getInt("userno"));

		if (params.getInt("userno") == 0) {
			throw new BizException("로그인 후 가능합니다.!!!");
		}
		//적립금
		if(params.getInt("idx") == 1 || params.getInt("idx") == 3
				|| params.getInt("idx") == 4 || params.getInt("idx") == 6) {

			dadaEventMapper.insertHolidayReservePay(params);

		}
		//할인쿠폰
		else if(params.getInt("idx") == 2 || params.getInt("idx") == 5) {

			SOMap holidayCoupon = dadaEventMapper.selectHolidayCoupon(params);
			if(holidayCoupon != null) {
				holidayCoupon.put("issuemembertype", params.getDbStr("mumembertype"));
				holidayCoupon.put("issuememlvtype", params.getDbStr("mumemlvtype"));
				couponMemissueMapper.insertCouponMemissue(holidayCoupon);

//				Map<String, Object> holidayCouponMember = new SOMap();
//				holidayCouponMember.put("userno", holidayCoupon.get("userno"));
//
//				List<Map<String, Object>> issueTargetMemberList = new ArrayList<Map<String, Object>>();
//				issueTargetMemberList.add(holidayCouponMember);
//				params.put("issueTargetMemberList", issueTargetMemberList);
//
//				params.put("comcpnidx", holidayCoupon.get("comcpnidx"));
//				params.put("authuserid", "SYSTEM");
//
//				couponMemberMapper.insertCouponMember(params);
			}

		}
		//경품응모
		else if(params.getInt("idx") == 7) {

			// 이벤트에 응모했는지 조회
			SOMap dadaEventMap = dadaEventMapper.selectHolidayDadaEvent(params);

			List<SOMap> isExist = dadaEventEnterMapper.selectAdminDadaEventEnterListPopup(dadaEventMap);
			if (isExist != null && isExist.size() > 0) {
				// res.setStatusCode(Status.CREATED.getKey());
				// res.setMessage("이미 응모되었습니다.");
				return res;
			}
			dadaEventMap.put("userno", params.get("userno"));
			dadaEventMap.put("issucc", "F");

			dadaEventEnterMapper.insertAdminEventEnter(dadaEventMap);
		}

		return res;
	}

}
