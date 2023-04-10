package com.dplot.front.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * 사용자 회원 service
 */
public interface FrontMemberService {
	
	/**
	 * 사용자->회원 아이디 찾기 처리
	 *
	 * @param p the p
	 * @param rmap the rmap
	 * @return 마스킹 된 아이디, 이메일, 핸드폰번호
	 * @throws Exception the exception
	 */
	SOMap findId(SOMap param) throws Exception;
	
	/**
	 * 사용자 아이디 전송
	 *
	 * @param type = email, mobile
	 * @param id = 전송할 아이디
	 * @throws Exception the exception
	 */
	void sendId(SOMap param) throws Exception;
	
	/**
	 * 비밀번호 찾기 인증번호 발송
	 * @param param
	 * @return 인증시간(초), 인증만료시간
	 * @throws Exception
	 */
	SOMap sendAuthNum(SOMap param) throws Exception;
	
	/**
	 * 비밀번호 찾기 인증번호 확인
	 * @param param authnum, no
	 * @throws Exception
	 */
	SOMap confirmAuthNum(SOMap param) throws Exception;
	
	/**
	 * 비밀번호 변경
	 * @param param no, oldpw, newpw, confirmpw
	 * @throws Exception
	 */
	SOMap changePw(SOMap param) throws Exception;
	
	/**
	 * 비밀번호 수정일자 오늘날짜로 변경
	 * @param param no
	 * @throws Exception
	 */
	void updatePwChangeDateNowByNo(SOMap param) throws Exception;
	
	/**
	 * 회원가입 이용약관 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<SOMap> selectSignUpTerm(SOMap param) throws Exception;
	
	/**
	 * SNSLogin
	 * @param param
	 * @throws Exception
	 */
	public SOMap snsLogin(SOMap param) throws Exception;
	
	/**
	 * SNSConnect
	 * @param param
	 * @throws Exception
	 */
	public SOMap snsConnect(SOMap param) throws Exception;
	
	/**
	 * SNSDisconnect
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap snsDisconnect(SOMap param) throws Exception;
	
	/**
	 * 회원가입처리
	 * @param param
	 * @throws Exception
	 */
	public Response saveSignup(SOMap param) throws Exception;
	
	/**
	 * 회원탈퇴처리
	 * @param param
	 * @throws Exception
	 */
	public void withdraw(SOMap param) throws Exception;
	
	/**
	 * 회원가입 아이디 중복체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserId(SOMap param) throws Exception;
	
	/**
	 * 회원가입 이메일 중복체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean checkEmail(SOMap param) throws Exception;
	
	/**
	 * 회원가입 임직원 인증번호 이메일발송
	 * @param param
	 * @throws Exception
	 */
	public SOMap sendAuthEmail(SOMap param) throws Exception;
	
	/**
	 * 회원가입 임직원 인증번호 이메일확인
	 * @param param
	 * @throws Exception
	 */
	public void confirmAuthEmail(SOMap param) throws Exception;
	
	/**
	 * 휴면회원 복원
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void cancelSleepMember(SOMap param) throws Exception;

	/**
	 * 회원가입한 회원정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectMemberInfo(SOMap param) throws Exception;

	/**
	 * sns 추천인 보상 지급
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void saveRecommend(SOMap param) throws Exception;

	
	/**
	 * 카카오 이름 변경
	 * @param param
	 */
	public void saveKakaoName(SOMap param) throws Exception;

	/**
	 * 로그인한 회원정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectMember(SOMap param) throws Exception;

	/**
	 * 회원정보 확인페이지 비밀번호 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void selectConfirmCheck(SOMap param) throws Exception;

	/**
	 * 회원정보 수정처리
	 * @param param
	 * @throws Exception 
	 */
	public void saveInfoModify(SOMap param) throws Exception;
	
	/**
	 * 광고성Push여부 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void saveIsAdPush(SOMap param) throws Exception;
	
	/**
	 * 바이오Token조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap getEncBio(SOMap param) throws Exception;
	
	/**
	 * 바이오Token저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void saveEncBio(SOMap param) throws Exception;

	/**
	 * 회원이메일 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap memberEmailPhone(SOMap param) throws Exception;

	/**
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap getMemberInfo(SOMap param) throws Exception;

	/**
	 * 기탈퇴 회원email인지 체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	boolean checkSleepMemberEmail(SOMap param) throws Exception;

	/**
	 * APPLE SNS 연결 처리
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap snsAppleConnect(SOMap param) throws Exception;
}
