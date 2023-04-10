package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface MztrendLikeMapper {

	/**
	 * 매거진트렌드 좋아요 목록조회(브랜드 좋아요 포함)
	 * @param param
	 * @return
	 */
	List<SOMap> selectMzLikeList(SOMap param);

	/**
	 * 매거진트랜드 좋아요 전체 목록수 
	 * @param param
	 * @return
	 */
	int selectMzLikeListCnt(SOMap param);

	/**
	 * 트랜드 좋아요 전체 삭제
	 * @param param
	 * @return
	 */
	int deleteMztrendLike(SOMap param);

	/**
	 * 트렌드 좋아요 추가
	 * @param param
	 * @return
	 */
	int insertMzTrendLike(SOMap param);

	/**
	 * 트렌드 좋아요 수량 조회
	 * @param param
	 * @return
	 */
	int selectMzTrendLikeCnt(SOMap param);
}
