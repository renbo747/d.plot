package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 관리자 메뉴 Mapper (T_ADMIN_MENU)
 * @fileName	: AdminMenuMapper.java
 * @author		: JSK
 * @date		: 2021.11.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.09	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface AdminMenuMapper {

	/**
	 * 상단메뉴 조회
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminMenu(SOMap params);

	/**
	 * 서브메뉴 조회
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminSubMenu(SOMap params);

	/**
	 * 메뉴 네이게이션 조회
	 * @return SOMap
	 */
	SOMap selectAdminMenuNav(SOMap params);

	/**
	 * url로 메뉴정보 조회
	 * @return SOMap
	 */
	SOMap selectAdminMenuInfoByUrl(SOMap params);

	/**
	 * url로 메뉴정보 조회
	 * @return Map<String, Object>
	 */
	SOMap selectAdminPageAuth(SOMap params);
	
	/**
	 * url로 컴포넌트정보 조회
	 * @return Map<String, Object>
	 */
	List<SOMap> selectAdminComponentAuth(SOMap params);


	/**
	 * 서브메뉴 조회(파트너사)
	 * @return List<SOMap>
	 */
	List<SOMap> selectPartnersSubMenu(SOMap params);

	/**
	 * 즐겨찾기용 모든메뉴 조회(권한 미보유 및 hide 제외)
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminAllMenu(SOMap params);

	/**
	 * 사용자별 메뉴 권한 
	 * @param params
	 * @return
	 */
	List<SOMap> selectAdminMenuAuth(SOMap params);

	/**
	 * 즐겨찾기 메뉴조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectAdminFavoriteMenu(SOMap params);
}
