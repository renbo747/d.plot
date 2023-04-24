package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface FileMapper {

	/**
	 * 통합파일 목록
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectFileList(SOMap params) throws Exception;
	
	/**
	 * 통합파일 조회
	 * @param params
	 * @return List<SOMap>
	 */
	SOMap selectFile(SOMap params) throws Exception;

	/**
	 * 통합파일 등록
	 * @param params
	 * @return SOMap
	 */
	int insertFile(SOMap params) throws Exception;
	
	/**
	 * 통합파일 등록
	 * @param params
	 * @return SOMap
	 */
	int updateFile(SOMap params) throws Exception;
	
	/**
	 * 통합파일 삭제
	 * @param params
	 * @return SOMap
	 */
	int deleteFile(SOMap params) throws Exception;

	/**
	 * ERP 전송 상품이미지 리스트 조회
	 */
	List<SOMap> selectGoodsImageERP(SOMap params) throws Exception;

}
