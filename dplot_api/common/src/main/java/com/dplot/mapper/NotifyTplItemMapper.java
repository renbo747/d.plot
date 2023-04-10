package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 정보고시_템플릿_항목 Mapper (T_NOTIFY_TPL_ITEM)
 * @fileName	: NotifyTplItemMapper.java
 * @author		: JSK
 * @date		: 2021.11.23
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.23	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface NotifyTplItemMapper {
    
    /**
     * 정보고시템플릿항목 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectNotifyTplItemList(SOMap param) throws Exception;

    /**
     * 상품정보고시항목 저장
     * @param params
     * @return
     */
	int saveNotifyTplItem(SOMap params);

	/**
	 * 상품정보고시항목 삭제
	 * @param params
	 * @return
	 */
	int removeNotifyTplItem(SOMap params);

	/**
	 * 상품정보고시항목 수정
	 * @param params
	 * @return
	 */
	int modifyNotifyTplItem(SOMap params);
}
