package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface UserMapper.
 */
@MapperInterface
public interface UserMapper {

	/**
	 * Select user.
	 *
	 * @param params the params
	 * @return the user
	 */
	SOMap selectUser(SOMap params);

	/**
	 * Update user.
	 *
	 * @param params the params
	 * @return the int
	 */
	//int updateUser(SOMap params);

	/**
	 * Select user count.
	 *
	 * @param params the params
	 * @return the int
	 */
	int selectUserCount(SOMap params);

	/**
	 * Insert user.
	 *
	 * @param user the user
	 * @return the int
	 */
	int insertUser(SOMap user);

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the int
	 */
	int updateUser(SOMap user);

	/**
	 * Update user all.
	 *
	 * @param params the params
	 * @return the int
	 */
	int updateUserAll(SOMap user);

	/**
	 * Select user join goods history.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectUserJoinGoodsHistory(SOMap params);

	/**
	 * Select user join user total.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectUserJoinUserTotal(SOMap params);

	/**
	 * Update user user pw.
	 *
	 * @param user the user
	 * @return the int
	 */
	int updateUserUserPw(SOMap user);

	/**
	 * Select user front.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectUserFront(SOMap params);

	/**
	 * Select user join dealer.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectUserJoinDealer(SOMap params);

	/**
	 * Delete user by no.
	 *
	 * @param params the params
	 * @return the int
	 */
	int deleteUserByNo(SOMap user);

	/**
	 * Update user regDate.
	 *
	 * @param params the params
	 * @return the int
	 */
	int updateUserRegDate(SOMap user);

	/**
	 * Update user for secession.
	 *
	 * @param params the params
	 * @return the int
	 */
	int updateUserForSecession(SOMap user);

	/**
	 * Select user join member count.
	 *
	 * @param params the params
	 * @return the int
	 */
	int selectUserJoinMemberCount(SOMap params);

	/**
	 * 전체 파트너사 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectPartnersList(SOMap params);


	/**
	 * 비밀번호 찾기 인증을 위한 유저 인증번호와 만료시간 조회.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	Map<String, Object> selectUserAuthNum(Map<String, Object> params);

	/**
	 * T_USER에 NO로 authnum, aulimitdate 업데이트
	 *
	 * @param params the params
	 * @return the int
	 */
	int updateUserAuthNumAndAuLimitDate(Map<String, Object> params);

	/**
	 * T_USER에서 NO로 패스워드 조회
	 * @param params the params
	 * @return the hash map
	 */
	Map<String, Object> selectUserPwByNo(Map<String, Object> params);

	/**
	 * T_USER에서 NO로 모든 컬럼 조회
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectUserAll(SOMap params);

	/**
	 * userid로 user 정보 조회
	 *
	 * @param params
	 * @return
	 */
	List<SOMap> selectUserByIdList(SOMap params);

	/**
	 * 유저 패스워드 업데이트
	 * @param param
	 * @return
	 */
	int updateUserPasswordByNo(SOMap param);

	/**
	 * 유저 패스워드 갱신일 업데이트
	 * @param param
	 * @return
	 */
	int updatePwChangeDateNowByNo(SOMap param);

	/**
	 * 비밀번호 틀린 횟수 증가
	 * @param param
	 * @return
	 */
	int increasePwFailCnt(SOMap param);

	/**
	 * 비밀번호 틀린 횟수 초기화
	 * @param param
	 * @return
	 */
	int resetPwFailCnt(SOMap param);

	/**
	 * 비밀번호 틀린 횟수 조회
	 * @param param
	 * @return
	 */
	int selectPwFailCnt(SOMap param);

	/**
	 * SNS 회원가입 USER ID SEQ조회
	 * @return
	 */
	String selectUserIdSeq();
	
	/**
	 * 임시 패스워드 저장 및 임시비번여부 TRUE 처리
	 * @param param
	 * @return
	 */
	int updateUserPasswordTemp(SOMap param);

	/**
	 * 마이페이지_회원정보 인증
	 * @param dbParam
	 * @return
	 */
	int selectUserCnt(SOMap dbParam);
	
	/**
	 * SNS, BIO인증 체크
	 * @param dbParam
	 * @return
	 */
	int selectCheckSnsBioInfo(SOMap dbParam);

	/**
	 * 이전 다다픽 회원 비밀번호 변경 필요 확인
	 * @param memberMap
	 * @return
	 */
	int selectMigUserPwCk(Map<String, Object> memberMap);
}
