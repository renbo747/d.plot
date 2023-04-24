package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface ReviewNotiMapper.
 */
@MapperInterface
public interface ReviewNotiMapper {
	/**
	 * 리뷰신고 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectReviewNotiList(SOMap params);

	/**
	 * 신고한 리뷰인지 체큰
	 * @param param
	 * @return
	 */
	int selectReviewNotiCnt(SOMap param);

	/**
	 * 리뷰 신고하기 처리
	 * @param param
	 */
	void insertReviewNoti(SOMap param);
}
