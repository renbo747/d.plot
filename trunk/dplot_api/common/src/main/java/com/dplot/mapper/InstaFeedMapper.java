package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

@MapperInterface
public interface InstaFeedMapper {
    List<SOMap> selectInstaFeed(SOMap params);

    /**
     * 인스타피드 수정
     * @param map
     * @return
     */
	int updateInstaFeed(Map<String, Object> map);

	/**
	 * 인스타피드 추가
	 * @param map
	 * @return
	 */
	int insertInstaFeed(Map<String, Object> map);

	/**
	 * 인스타피드 삭제
	 * @param param
	 * @return
	 */
	int removeInstaFeed(SOMap param);
	
	/**
	 * Front 인스타 목록 조회 상위(9개)
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontInstaFeedList(SOMap param);
}
