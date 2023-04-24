package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @author : ywm2004
 * @discription : 시리얼번호 테이블 Mapper
 * @fileName : SerialNoMapper.java
 * @date : 2022-01-10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-01-10	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface SerialNoMapper {

    /**
     * 시이러 번호 저장
     *
     * @param params
     */
    void insertSerialNo(SOMap params);

    /**
     * 시리얼 번호 삭제
     * 
     * @param params
     */
    void deleteSerialNo(SOMap params);

    /**
     * 시리얼 번호 중복 체크
     *
     * @param params
     * @return true : 중복있음, false : 중복없음
     */
    SOMap isCheckDuplicationSerialNo(SOMap params);

    /**
     * 시리얼 번호 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectAdminSerialNoList(SOMap params);

    /**
     * 유저 정보가 포함된 시리얼 번호 조회
     * 
     * @param params
     * @return
     */
    List<SOMap> selectAdminSerialNoUserInfoList(SOMap params);

    /**
     * 사용자 해당 시리얼번호 지급수 체크
     * @param param
     * @return
     */
	int selectFrontUserSerialCnt(SOMap param);

	/**
	 * 사용횟수 증가
	 * @param param
	 * @return
	 */
	int updateUseCnt(SOMap param);
}
