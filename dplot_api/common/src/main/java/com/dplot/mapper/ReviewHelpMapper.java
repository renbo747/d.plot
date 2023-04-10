package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface ReviewHelpMapper {

	/**
	 * 리뷰 도움 정보 조회
	 * @param param
	 * @return
	 */
	int selectReviewHelpCnt(SOMap param);

	/**
	 * 리뷰 도움 삭제처리
	 * @param param
	 */
	void deleteReviewHelp(SOMap param);

	/**
	 * 리뷰 도움 저장처리
	 * @param param
	 */
	void insertReviewHelp(SOMap param);
}
