package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 입점사 동의내역 테이블 Mapper
 * @fileName : BoardAgreeMapper.java
 * @date : 2021-11-10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-10	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface BoardAgreeMapper {

    /**
     * 파트너사 미동의, 동의 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectAgreePagingList(Map<String, Object> params);

    /**
     * 게시판 리스트 갯수
     *
     * @param params
     * @return
     */
    int selectAgreeListCount(Map<String, Object> params);

    /**
     * boardPostIdx로 선택된 파트너사 검색
     *
     * @param params
     * @return
     */
    Map<String, Object> selectCheckList(Map<String, Object> params);

    /**
     * 입점사 동의 내역 테이블 추가
     *
     * @param params
     * @return
     */
    void insertBoardAgree(List<SOMap> params);

    /**
     * 입점사 동의 내역 삭제
     *
     * @param params postidx, deleteNoList[] => (key : deleteNoList)
     * @return
     */
    void deleteBoardAgreeByUserNo(Map<String, Object> params);

    /**
     * 입점사 동의 내역 boardIdx 전부 삭제
     *
     * @param postidx
     * @return
     */
    void deleteBoardAgreeAllByPostIdx(String postidx);

    /**
     * 파트너사 동의/미동의 수정
     * @param params
     * @return
     */
	int updateAgree(SOMap params);

}
