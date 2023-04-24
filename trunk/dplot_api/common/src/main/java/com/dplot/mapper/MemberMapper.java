package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface MemberMapper.
 */
@MapperInterface
public interface MemberMapper {


	/**
	 * Select member count by email.
	 *
	 * @param params the params
	 * @return the int
	 */
	int selectMemberCntByEmail(SOMap params) throws Exception;
	
	/**
	 * Select member.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectMember(SOMap params) throws Exception;

	/**
	 * Update member.
	 *
	 * @param member the member
	 * @return the int
	 */
	int updateMember(SOMap param) throws Exception;

	/**
	 * Insert member.
	 *
	 * @param member the member
	 * @return the int
	 */
	int insertMember(SOMap param) throws Exception;
	
	/**
	 * Update member auto delete null.
	 *
	 * @param member the member
	 * @return the int
	 */
	int updateMemberAutoDeleteNull(SOMap param) throws Exception;

	/**
	 * 회원상태 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int updateMemberState(SOMap param) throws Exception;
	
	/**
	 * 아이디 찾기
	 * 이름과 이메일로 아이디 조회
	 * @param param
	 * @return
	 */
	SOMap selectIdByNameEmailHp(Map<String, Object> param) throws Exception;

	/**
	 * 관리자 - 다다픽사용자 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	List<SOMap> selectAdminMemberList(SOMap params) throws Exception;

	/**
	 * 관리자 - 다다픽사용자 조회 카운트
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	SOMap selectAdminMemberListCount(SOMap params) throws Exception;

	/**
	 * 관리자 - 회원관리 - 사용자 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectAdminMemberManageList(SOMap params) throws Exception;

	/**
	 * 관리자 - 회원관리 - 사용자 목록 조회 카운트
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectAdminMemberManageListCount(SOMap params) throws Exception;

	/**
	 * 관리자 - 회원관리 - 탈퇴 회원 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectMemberResignList(SOMap params) throws Exception;

	/**
	 * 관리자 - 회원관리 - 탈퇴 회원 목록 조회 카운트
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMemberResignListCount(SOMap params) throws Exception;

	/**
	 * 관리자 - 전체회원 - 사용자 상세화면
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMemberDetailForAdmin(SOMap params) throws Exception;

	/**
	 * 활동중인 회원목록 조회
	 * @param params
	 * @return List<SOMap
	 * @throws Exception
	 */
	List<SOMap> selectActiveMemberList(SOMap params) throws Exception;

	/**
	 * 블랙회원 업데이트 (T/F)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int updateBlackMember(SOMap params) throws Exception;

	/**
	 * 블랙회원 처리 (USERNO ARRAY)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int updateBlackMemberArr(SOMap params) throws Exception;

	/**
	 * 회원 탈퇴 처리 (USER NO ARRAY)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int updateMemberWithdraw(SOMap params) throws Exception;

	/**
	 * 회원 구매일자 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int updateMemberOrderDate(SOMap params) throws Exception;
	
	/**
	 * 회원기타정보(포인트등...)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMemberEtcInfo(SOMap params) throws Exception;

	/**
	 * UserNo로 회원 정보 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMemberInfoByUserNo(SOMap params) throws Exception;

	/**
	 * 회원통계 일자별 신규 유저수
	 */
	List<SOMap> selectNewMemberStatsByDay(SOMap params) throws Exception;

	/**
	 * 회원통계 일자별 신규 유저수 카운트
	 */
	SOMap selectNewMemberStatsByDayCount(SOMap params) throws Exception;

	/**
	 * 회원통계 주별 신규 유저수
	 */
	List<SOMap> selectNewMemberStatsByWeek(SOMap params) throws Exception;

	/**
	 * 회원통계 주별 신규 유저수 카운트
	 */
	SOMap selectNewMemberStatsByWeekCount(SOMap params) throws Exception;

	/**
	 * 회원통계 월별 신규 유저수
	 */
	List<SOMap> selectNewMemberStatsByMonth(SOMap params) throws Exception;

	/**
	 * 회원통계 월별 신규 유저수 카운트
	 */
	SOMap selectNewMemberStatsByMonthCount(SOMap params) throws Exception;

	/**
	 *	광고성 수신 동의 안내 메일 발송 대상자 조회
	 */
	List<SOMap> selectAdAgreeMemberList(SOMap params) throws Exception;

	/**
	 * 로그인 11개월 경과 메일 발송 대상자 조회
	 */
	List<SOMap> selectDormancyMemberList(SOMap param);

	/**
	 *	개인정보 이용내역 안내 메일 발송 대상자 조회
	 */
	List<SOMap> selectPersonalInfoMemberList(SOMap param);

	/**
	 * 회원 ERP 전송데이터 (등록/수정/휴면)
	 */
	List<SOMap> selectMemberERPData(SOMap param);

	/**
	 * 회원 탈퇴 ERP 전송데이터
	 */
	List<SOMap> selectResignMemberERPData(SOMap param);
	
	/**
	 * 바이오정보 수정
	 * @param param
	 * @return
	 */
	int updateMemberEncBio(SOMap param);

	/**
	 * 회원 이메일 & 핸드폰 조회
	 * @param param
	 * @return
	 */
	SOMap memberEmailPhone(SOMap param);

	/**
	 * 회원 대시 보드
	 */
	SOMap selectMemberDashBoard(SOMap param);

	/**
	 * 기 탈퇴회원인지 체크
	 * @param param
	 * @return
	 */
	int selectSleepEmailCheck(SOMap param);

	/**
	 * 평생회원 보상 지급 여부 확인
	 * @param param
	 * @return
	 */
	int selectLifetimeCnt(SOMap param);

	/**
	 * 회원 광고성 수신여부 조회
	 * @param dbParam
	 * @return
	 */
	SOMap selectMemberAdInfo(SOMap dbParam);
	
	/**
	 * Select member 정보조회
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectMemberInfo(String userno) throws Exception;

}

