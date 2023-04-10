package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 정보고시_템플릿 Mapper (T_NOTIFY_TPL)
 * @fileName	: NotifyTplMapper.java
 * @author		: JSK
 * @date		: 2021.11.23
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.23	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface NotifyTplMapper {
    
    /**
     * 정보고시템플릿목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectNotifyTplList(SOMap param) throws Exception;

    /**
     * 상품정보고시관리 목록 조회
     * @param params
     * @return
     */
	List<SOMap> selectNotifyList(SOMap params);

	/**
	 * 상품정보고시관리 상태별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectNotifyState(SOMap params);

	/**
	 * 상품정보고시 사용여부 수정
	 * @param params
	 * @return
	 */
	int updateNotifyUse(SOMap params);

	/**
	 * 상품정보고시관리 엑셀목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectNotifyExcelList(SOMap params);

	/**
	 * 상품정보고시 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectNotifyTpl(SOMap params);

	/**
	 * 상품정보고시 저장
	 * @param params
	 * @return
	 */
	int saveNotifyTpl(SOMap params);

	/**
	 * 상품정보고시 수정
	 * @param params
	 * @return
	 */
	int modifyNotifyTpl(SOMap params);

    /**
     * 상품 일괄업로드 정보고시템플릿 목록 조회
     *
     * @param params
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectNotifyTplListForExcel(SOMap params);
}
