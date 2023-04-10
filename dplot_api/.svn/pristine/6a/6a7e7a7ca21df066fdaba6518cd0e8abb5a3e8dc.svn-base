package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface DealerMapper.
 */
@MapperInterface
public interface DealerMapper {

	/**
	 * 파트너사 정보 조회
	 * @param params
	 * @return
	 */
	SOMap selectDealer(SOMap params);

	/**
	 * 파트너사 전체 목록
	 * @param param
	 * @return
	 */
	List<SOMap> selectPartnersList(SOMap param);

	/**
	 * 파트너사 목록
	 * @param param
	 * @return
	 */
	List<SOMap> selectPartnsersApplyList(SOMap param);

	/**
	 * 파트너사 입점신청상태 별 카운트
	 * @param param
	 * @return
	 */
	SOMap selectPartnsersApplyListStateCount(SOMap param);

	/**
	 * 파트너사 입점신청상태 수정
	 * @param param
	 * @return
	 */
	int updatePartnersReqDealst(SOMap param);

	/**
	 * 파트너사 입점신청 상세 정보
	 * @param param
	 * @return
	 */
	SOMap selectPartnsersDetail(SOMap param);

	/**
	 * 파트너사 등록
	 * @param param
	 */
	void insertPartnership(SOMap param);

	/**
	 * 파트너사 수정
	 * @param param
	 */
	void updatePartnership(SOMap param);


	/**
	 * 파트너사 첨부파일 수정
	 * @param param
	 */
	void updatePartnershipFile(SOMap param);

	/**
	 * 파트너사 현황 리스트
	 * @param param
	 * @return
	 */
	List<SOMap> selectPartnsersStatusList(SOMap param);

	/**
	 * 파트너사 현황 상태 카운트
	 * @param param
	 * @return
	 */
	SOMap selectPartnsersStatusListStateCount(SOMap param);

	/**
	 * 파트너사 현황 엑셀 다운로드 리스트
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> selectPartnsersExcelList(Map<String, Object> param);

	/**
	 * 파트너사 입점상태 수정
	 * @param param
	 * @return
	 */
	int updatePartnersDealerst(SOMap param);

	/**
	 * 모두 사인 전자계약 진행 아이디 등록 (계약 시작시)
	 * @param param
	 * @return
	 */
	int updateSignStateAndModuId(SOMap param);

	/**
	 * 계약 상태 변경시 업데이트
	 * @param param
	 * @return
	 */
	int updateSignState(SOMap param);

	/**
	 * ERP 전송 파트너사 정보 조회
	 */
	List<SOMap> selectPartnsersERPData(SOMap param);

	/**
	 * 정산용 파트너사 조회
	 */
	List<SOMap> selectDealerListCalculate(SOMap param);

	/**
	 * 지연 현황 KAKAO 발송용 타겟 리스트
	 */
	List<SOMap> selectPartnersDelaySendTargetList(SOMap param);

	/**
	 * 상품번호로 파트너사 입점상태 휴점, 일시중단 카운트
	 */
	SOMap selectPartnersDealerstCountByGoodsno(SOMap param);

	/**
	 * 판매업체 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectDealerList(SOMap param);

	SOMap selectDealerMemberInfo(SOMap dbparam);

	void updateDealerMemberCiDiInfo(SOMap memberInfo);
}
