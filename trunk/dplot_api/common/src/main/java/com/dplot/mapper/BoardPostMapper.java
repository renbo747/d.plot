package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

/**
 * @author : ywm2004
 * @discription : 게시판 게시물 테이블 Mapper (t_board_post)
 * @fileName : BoardPostMapper.java
 * @date : 2021-11-10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-10	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface BoardPostMapper {

    /**
     * 게시판 등록
     *
     * @param params
     */
    void insertBoardPost(SOMap params);

    /**
     * 게시판 조회수 증가
     *
     * @param boardIdx
     */
    void updateBoardHits(String boardIdx);

    /**
     * 게시판 업데이트
     *
     * @param params
     * @return
     */
    void updateBoardPost(SOMap params);

    /**
     * 게시판 노출순서 업데이트
     *
     * @param params
     */
    void updateBoardSortNum(SOMap params);

    /**
     * 게시판 게시물 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectBoardPostDtl(SOMap params);

    /**
     * 게시판 - 공지사항 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectNoticePagingList(SOMap params);

    /**
     * 게시판 - 공지사항 리스트 갯수
     *
     * @param params
     * @return
     */
    int selectNoticeListCount(SOMap params);

    /**
     * 게시판 - 동의현황 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectConsentPagingList(SOMap params);

    /**
     * 게시판 - 동의현황 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectConsentDetail(SOMap params);

    /**
     * 게시판 - 동의현황 전체, 진행, 중지, 종료 건수
     *
     * @param params
     * @return
     */
    SOMap selectConsentListStateCount(SOMap params);

    /**
     * 게시판 - 자주 묻는 질문 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectFaqPagingList(SOMap params);

    /**
     * 게시판 - 자주 묻는 질문 전체, 사용, 미사용 건수
     *
     * @param params
     * @return
     */
    SOMap selectFaqListStateCount(SOMap params);

    /**
     * 게시판 - 이벤트 당첨자 발표 리스트 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectEventAnnounceList(SOMap params);

    /**
     * 게시판 - 이벤트 당첨자 발표 전체 건수
     *
     * @param params
     * @return
     */
    SOMap selectEventAnnounceListStateCount(SOMap params);

    /**
     * 게시판 - 이벤트 당첨자 발표 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectEventAnnounceDetail(SOMap params);

    /**
     * 게시판 - 이벤트 당첨자 수정 가능 여부 체크
     * 
     * @param params
     * @return
     */
    SOMap isAdminCanSaveCheckEventAnnounce(SOMap params);

    /**
     * 운영관리 공지사항 목록 조회
     * @param params
     * @return
     */
	List<SOMap> selectOperationNoticeList(SOMap params);

	/**
	 * 운영관리 공지사항 사용여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectOperationNoticeUseCnt(SOMap params);

	/**
	 * 운영관리 공지사항 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectOperationNoticeExcelList(SOMap params);

	/**
	 * 운영관리 공지사항 상세조회
	 * @param params
	 * @return
	 */
	SOMap selectOperationNoticeDetail(SOMap params);

	/**
	 * 운영관리 공지사항 삭제
	 * @param params
	 * @return
	 */
	int deleteBoardPost(SOMap params);

	/**
	 * 노출중인 공지사항 수량 조회
	 * @param params
	 * @return
	 */
	int selectUseCount(SOMap params);

	/**
	 * 메인노출중인 faq 공지사항 수량 조회
	 * @param params
	 * @return
	 */
	int selectFaqMainCnt(SOMap params);
	
	/**자주하는 질문 메인 노출
	 * @param params
	 * @return
	 */
	List<SOMap> selectMainFaq(SOMap params);
	
	/**
	 * 고객센터 FAQ 검색
	 * @param params
	 * @return
	 */
	List<SOMap> selectFaq(SOMap params);
	
	/**
	 * 고객센터 FAQ 총 갯수
	 * @param params
	 * @return
	 */
	int selectFaqCount(SOMap params);
	
	/**
	 * 고객센터 메인 공지사항 노출 (상단 고정 최신글 + 최신글)
	 * @param params
	 * @return
	 */
	List<SOMap> selectMainNotice(SOMap params);
	
	
	/**
	 * 고객센터 공지사항 총 갯수
	 * @param params
	 * @return
	 */
	int selectMainNoticeCount(SOMap params);
	
	/**
	 * 공지사항 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectNoticeDetail(SOMap params);
				
	/**
	 * 주문번호로 상품 검색
	 * @param params
	 * @return
	 */
	List<SOMap> selectComorderGoods(SOMap params);
		
		
	/**옵션이 있을 떄 옵션 및 goodsno 로 검색
	 * @param params
	 * @return
	 */
	SOMap selectGoodsName(SOMap params);
	
	/**FRONT 이벤트 당첨 내용
	 * @param params
	 * @return
	 */
	SOMap selectEventFront(SOMap params);

	/**
	 * 대시보드용 공지사항 조회
	 */
	List<SOMap> selectBoardPostDashBoard(SOMap param);

	/**
	 * 파트너사 동의현황 대시보드용 조회
	 */
	List<SOMap> selectAgreeDashBoard(SOMap param);

	/**
	 * 대시보드 팝업용 공지사항 조회
	 */
	List<SOMap> selectBoardPostDashBoardPopup(SOMap param);

	/**
	 * 파트너사 동의현황 대시보드 팝업용 조회
	 */
	List<SOMap> selectAgreeDashBoardPopup(SOMap param);
}

