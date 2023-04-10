package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface MemberSnsMapper.
 */
@MapperInterface
public interface MemberSnsMapper {

	/**
	 * MEMBERNO로 회원조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SOMap> selectMemberSns(SOMap params) throws Exception;
	
	/**
	 * 이메일, 전화번호, CI 회원조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SOMap> selectMember(SOMap params) throws Exception;
	
	

	/**
	 * 회원 SNS정보저장
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertMemberSns(SOMap params) throws Exception;

	/**
	 * 회원가입한 회원 정보 조회
	 * @param param
	 * @return
	 */
	public SOMap selectMemberInfo(SOMap param) throws Exception;

	/**
	 * SNS 회원 삭제 처리
	 * @param param
	 * @return 
	 */
	public int deleteMemberSns(SOMap param) throws Exception;

	/**
	 * SNS 회원 삭제 처리 ARRAY
	 */
	int deleteMemberSnsArr(SOMap param) throws Exception;

	/**
	 * 회원 ci값으로 조회
	 * @param memberPram
	 * @return
	 */
	public List<SOMap> selectMemberbyCi(SOMap memberPram);

	/**
	 * 회원 이메일 및 휴대폰으로 조회
	 * @param memberParam
	 * @return
	 */
	public List<SOMap> selectMemberbyEmail(SOMap memberParam);
}
