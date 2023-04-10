package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface BrandLikeMapper {

	/**
	 * 브랜드 좋아요 삭제
	 * @param param
	 * @return
	 */
	int deleteBrandLike(SOMap param);

	/**
	 * 브랜드 좋아요 추가
	 * @param param
	 * @return
	 */
	int insertBrandLike(SOMap param);

	/**
	 * 브랜드 좋아요 수 조회
	 * @param param
	 * @return
	 */
	int selectLikeCnt(SOMap param);

}
