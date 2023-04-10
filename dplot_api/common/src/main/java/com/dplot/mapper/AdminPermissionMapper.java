package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 관리자권한  Mapper (T_ADMIN_PERMISSION)
 * @fileName	: AdminMapper.java
 * @author		: LKW
 * @date		: 2022.01.18
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.18	LKW			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface AdminPermissionMapper {

	/**
	 * 관리자권한 추가
	 * @param params
	 * @return
	 */
	int insertPermission(SOMap params);

	/**
	 * 관리자권한 추가 or 업데이트 (없을 경우 insert 있을 경우 update)
	 * @param params
	 * @return
	 */
	int insertOrUpdatePermission(SOMap params);

	/**
	 * 즐겨찾기 수정
	 * @param params
	 * @return
	 */
	int updateBookMark(SOMap params);
}
