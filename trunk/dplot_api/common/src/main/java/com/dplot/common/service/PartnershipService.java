package com.dplot.common.service;

import com.dplot.common.SOMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PartnershipService {

    //중복아이디 체크 중복 : true, 중복아님 : false
    boolean checkOverlapUserId(String userId) throws Exception;

    //사업자번호 공공데이터 포털 인증 확인
    boolean bizNumberCheck(SOMap param) throws Exception;

    //파트너사 등록 화면 필요 데이터 조회
    SOMap selectPartnershipJoinInitData(SOMap param) throws Exception;

    //파트너사 등록 일괄 처리
    boolean insertPartnership(SOMap param, Map<String, MultipartFile> files);

    //파트너사 첨부파일 업데이트(사업자등록증, 통장사본, 통신판매업신고증)
    SOMap updatePartnershipFile(SOMap param, Map<String, MultipartFile> files);

    //파트너사 첨부파일 삭제(사업자등록증, 통장사본, 통신판매업신고증)
    SOMap deletePartnershipFile(SOMap param);

    //파트너사 업데이트 일괄 처리
    boolean updatePartnership(SOMap param);

    //파트너사 판매상품유형 조회 (카테고리 1 DEPTH 리스트)
    List<SOMap> prodTypeList() throws Exception;

    SOMap passwordUpdate(SOMap param) throws Exception ;

    SOMap findId(SOMap param) throws Exception;

    SOMap selectPartnersTerms(SOMap param) throws Exception;

}
