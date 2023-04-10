package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;


/**
 * @author : sjryn
 * @discription : 이벤트 테이블 Mapper (t_dadaevent)
 * @fileName : DadaEventMapper.java
 * @date : 2021-11-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-22	   sjryn		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface DadaEventMapper {

    /**
     * 이벤트 수정
     *
     * @param params
     */
    void updateEvent(SOMap params);

    /**
     * 이벤트 등록
     *
     * @param params
     */
    void insertEvent(SOMap params);

    /**
     * 이벤트 조회수 증가
     *
     * @param eventIdx
     */
    void updateReadCnt(String eventIdx);

    /**
     * 이벤트관리, 출첵이벤트 저장 가능 여부
     *
     * @param params
     * @return
     */
    SOMap isAdminCanSaveCheckEventAndCheckEvent(SOMap params);

    /**
     * 메인_BEST 상품 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectEventFrontListSQL(SOMap params);

    /**
     * 메인_BEST 상품 상세
     *
     * @param params
     * @return
     */
    List<SOMap> selectEventFrontDetailSQL(SOMap params);

    /**
     * 메인_BEST 상품 덧글
     *
     * @param params
     * @return
     */
    List<SOMap> selectEventFrontDetailCommetSQL(SOMap params);


    /**
     * 메인_BEST 상품 덧글 개수
     *
     * @param params
     * @return
     */
    List<SOMap> selectEventFrontDetailCommetLenthSQL(SOMap params);

    /**
     * @param params
     * @return
     */
    List<SOMap> selectEventFrontDetailCommetPage(SOMap params);

    /**
     * @param params
     * @return
     */
    List<SOMap> selectEventFrontDetailCommentSubSQL(SOMap params);

    /**
     * 이벤트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminEventList(SOMap params);

    /**
     * 종료 이벤트 조회
     *
     * @param
     * @return
     */
    List<SOMap> selectAdminEndEventList(SOMap params);

    /**
     * 이벤트 건별 조회 (전체, 사용, 미사용)
     *
     * @param params
     * @return
     */
    SOMap selectAdminEventStateList(SOMap params);

    /**
     * 이벤트 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectAdminEventDtl(SOMap params);

    /**
     * 프로모션 수량 체크
     * @param params
     * @return
     */
	int checkDisplayCnt(SOMap params);

	/**FRONT 프로모션 화면 노출 리스트
	 * @param params
	 * @return
	 */
	List<SOMap> selectEventList(SOMap params);

	/**FRONT 프로모션 화면 노출 리스트 갯수
	 * @param params
	 * @return
	 */
	int selectEventListCount(SOMap params);

	/**FRONT 프로모션 상세
	 * @param params
	 * @return
	 */
	SOMap selectEventDetail(SOMap params);

	SOMap selectEventSimpleDetail(SOMap params);

	/**
	 * 금일 응모한 count 가져오기 (T_PROMOTION_JOIN)
	 * @param params
	 * @return
	 */
	SOMap selectPromotionJoinCount(SOMap params);

	/**
	 * 응모 데이터 저장 (T_PROMOTION_JOIN)
	 * @param params
	 */
	void insertPromotionJoin(SOMap params);

	/**
	 * 당첨여부 조회 (T_PROMOTION_WINNING_CONDITION)
	 * @param params
	 * @return
	 */
	SOMap selectPromotionWinningCondition(SOMap params);

	/**
	 * 당첨 되었다면 T_PROMOTION_WINNING_CONDITION에 update 한다.
	 * @param params
	 */
	int updatePromotionWinningCondition(SOMap params);

	/**
	 * 추첨을 통해 당첨자를 조회한다.
	 * @param params
	 */
	List selectLotteryWinner(SOMap params);


	/**
	 * 래플이벤트를 조회한다.
	 * @param params
	 */
	List<SOMap> selectRaffleEvent(SOMap param);

	/**
	 * 래플이벤트를 상품을 조회한다
	 * @param params
	 */
	SOMap selectRaffleEventItem(int eventidx);

	/**
	 * 래플이벤트 추첨 여부를 확인한다.
	 * @param params
	 */
	SOMap selectResultWinning(SOMap param);

	/**
	 * 래플이벤트 당첨자를 조회한다.
	 * @param params
	 */
	List<SOMap> selectraffleWinList(SOMap param);

	/**
	 * 래플2이벤트를 조회한다.
	 *
	 * @param params
	 */
	List<SOMap> selectRaffle2Event(SOMap param);


	/**
	 * 래플이벤트를 상품을 조회한다
	 *
	 * @param params
	 */
	SOMap selectRaffle2EventItem(int eventidx);

	/**
	 * 래플응모자 리스트 조회
	 *
	 * @param params
	 */
	List<SOMap> raffleEntryList(SOMap param);

	/**
	 * 래플당첨조건 추가
	 *
	 * @param conddata
	 */
	void insertEventCond(Map conddata);

	/**
	 * 이벤트 응모조건 삭제처리
	 *
	 * @param conddata
	 */
	void updateEventCondResultTrash(SOMap params);


	/**
	 * 이벤트 응모조건 조회
	 *
	 * @param conddata
	 */
	List<SOMap> inquireEventCond(SOMap params);

	/**
	 * 이벤트 응모조건 결과 저장
	 *
	 * @param conddata
	 */
	void insertEventCondResultA(SOMap params);

	void insertEventCondResultB(SOMap params);

	void insertEventCondResultC(SOMap params);

	void insertEventCondResultD(SOMap params);

	/**
	 * 이벤트 당첨자 1명종회
	 *
	 * @param params
	 */
	Integer selectLotteryWinnerOne(SOMap params);

	/**
	 * 이벤트 응모조건 삭제
	 *
	 * @param conddata
	 */
	void updateEventCondTrash(SOMap params);

	/**
	 * 12월 홀리데이 이벤트 조회
	 * @param params
	 */
	List<SOMap> selectHoliday(SOMap params);

	/**
	 * 12월 홀리데이 해당쿠폰조회
	 * @param params
	 */
	SOMap selectHolidayCoupon(SOMap params);

	/**
	 * 12월 홀리데이 추첨응모 조회
	 * @param params
	 */
	SOMap selectHolidayDadaEvent(SOMap params);

	/**
	 * 12월 홀리데이 적립금 지급
	 * @param params
	 */
	void insertHolidayReservePay(SOMap params);

}
