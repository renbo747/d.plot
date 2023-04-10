package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

@MapperInterface
public interface MemberMemoMapper {
    /**
     * 관리자 - 전체회원 - 메모 INSERT 처리 (블랙, 탈퇴)
     * @param params
     * @return
     * @throws Exception
     */
    int insertMemberMemoArr(SOMap params) throws Exception;

    /**
     * 관리자 - 회원상세 - 메모리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    List<SOMap> selectMemberMemoList(SOMap params) throws Exception;


    /**
     * 관리자 - 전체회원 - 회원상세 - 메모 INSERT 처리 (단순메모)
     * @param params
     * @return
     * @throws Exception
     */
    int insertMemberMemo(SOMap params) throws Exception;

    /**
     * 관리자 - 전체회원 - 회원상세 - 메모 DELETE 처리
     * @param params
     * @return
     * @throws Exception
     */
    int deleteMemberMemo(SOMap params) throws Exception;

    /**
     * 관리자 - 메모 Arr처리
     */
    int adminInsertMemberMemoArr(SOMap params) throws Exception;
}
