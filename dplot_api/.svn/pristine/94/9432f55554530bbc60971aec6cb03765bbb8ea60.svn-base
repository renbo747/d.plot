
package com.dplot.admin.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.exception.CustomException;
import com.dplot.mapper.AdminMapper;
import com.dplot.mapper.AdminMenuMapper;
import com.dplot.mapper.AdminPermissionMapper;
import com.dplot.mapper.ComOrderGoodsMapper;
import com.dplot.mapper.CouponMapper;
import com.dplot.mapper.GiftMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.MemberHistoryMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.util.Util;

/**
 * @discription	: 관리자 공통 ServiceImpl
 * @fileName	: AdminCommonServiceImpl.java
 * @author		: JSK
 * @date		: 2021.11.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.09	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminCommonServiceImpl extends MallBaseService implements AdminCommonService {

	@Autowired
	private AdminMenuMapper adminMenuMapper;
	@Autowired
	private CouponMapper couponMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GiftMapper giftMapper;
	@Autowired
	private ComOrderGoodsMapper comOrderGoodsMapper;
	@Autowired
	private MemberHistoryMapper memberHistoryMapper;
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;


	/**
	 * 상단메뉴 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectAdminMenu(SOMap params) throws Exception{
		params.put("authadminno", cs.getInt("authadminno"));
		params.put("siteid", cs.getStr("siteid"));

		List<SOMap> topMenuList = adminMenuMapper.selectAdminMenu(params);

		return topMenuList.stream().peek(a -> {
			if (a.get("url").equals(params.get("path"))) {
				a.put("isActive", true);
			} else {
				a.put("isActive", false);
			}
		}).collect(Collectors.toList());
	}

	/**
	 * 서브메뉴(2뎁스 이상) 조회
	 * @param params
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public List<SOMap> selectAdminSubMenu(SOMap params) throws Exception{
		params.put("authadminno", cs.getInt("authadminno"));

		// 1.   depth 2 이상의 전체 노출메뉴 목록 조회
		List<SOMap> sideMenuList = adminMenuMapper.selectAdminSubMenu(params);

		// 2.   메뉴 parent-child 구조로 세팅
		// 2-1. depth 2 메뉴만 추출
		List<SOMap> resultList = sideMenuList.stream()
				.filter(parentMenu -> "2".equals(parentMenu.get("depth").toString()))
		        .collect(Collectors.toList());

		// 2-2. depth 2 메뉴에 해당하는 하위메뉴(depth 3) 목록 세팅
		if (resultList.size() > 0) {
			for (SOMap parentMenu : resultList) {
				List<SOMap> childMenuList = sideMenuList.stream()
						.peek(menu-> {
							// 상위코드 세팅
							String code = menu.get("code").toString();
							String upperCode = code.substring(0, code.lastIndexOf("-"));
							String url = (menu.get("url") == null) ? "" : menu.get("url").toString();
							menu.put("uppercode", upperCode);
							if(url.equals(params.get("path"))){
								menu.put("isactive", true);
							} else {
								menu.put("isactive", false);
							}
						})
						.filter(menu -> parentMenu.get("code").equals(menu.get("uppercode")))
						.collect(Collectors.toList());
				parentMenu.put("childmenulist", childMenuList);
			}

			// 하위메뉴 isActive일때 상위메뉴 isActive 적용
			for (SOMap parentMenu : resultList) {
				List<SOMap> childMenuList = (List<SOMap>) parentMenu.get("childmenulist");
				for (SOMap childMenu : childMenuList) {
					if ((boolean) childMenu.get("isactive")) {
						parentMenu.put("isactive", true);
						break;
					}
				}
			}
		}

		return resultList;
	}

	/**
	 * 네비게이터에 노출할 메뉴 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectAdminMenuNav(SOMap params) throws Exception {
		if(params.get("code") == null
				|| "".equals(params.get("code").toString())
				|| "undefined".equals(params.get("code").toString())){

			SOMap map = adminMenuMapper.selectAdminMenuInfoByUrl(params);
			params.put("code", map.get("code"));
			params.put("depth", map.get("depth"));
		}
		String parentCode = params.get("code").toString().substring(0, 1);
		params.put("parentcode", parentCode);

		return adminMenuMapper.selectAdminMenuNav(params);
	}

	/**
	 * 관리자 페이지 읽기/쓰기 권한 데이터 리턴
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectAdminPageAuth(SOMap params) throws Exception {
		params.put("authadminno", cs.getInt("authadminno"));
		SOMap soMap = adminMenuMapper.selectAdminPageAuth(params);

		if(soMap == null){
			soMap = new SOMap();
			soMap.put("isread", "F");
			soMap.put("iswrite", "F");
		}
		if (!Util.isEmpty(params.get("iscomponentauth")) && (boolean) params.get("iscomponentauth")) {
			List<SOMap> componentAtuhList = adminMenuMapper.selectAdminComponentAuth(params);
			soMap.put("componentauth", componentAtuhList);
		}

		return soMap;
	}

	/**
	 * 다다픽 사용자 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectMemberList(SOMap params) throws Exception {
		SOMap result = new SOMap();

        params.put("siteid", cs.getStr("siteid"));
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        List<SOMap> memberList = memberMapper.selectAdminMemberList(params);
        SOMap count = memberMapper.selectAdminMemberListCount(params);
        result.put("list", memberList);
        result.put("count", count);
		return result;
	}

	/**
	 * 상품목록 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectGoodsList(SOMap params) throws Exception {
		SOMap result = new SOMap();

        params.put("siteid", cs.getStr("siteid"));
		params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S); 	//이미지구분_상품_PC이미지_소
        List<SOMap> goodsList = goodsMapper.selectCommonGoodsList(params);

        int listcount = 0;
        if (goodsList != null && goodsList.size() > 0) {
        	listcount = goodsList.get(0).getInt("totalcnt");
        }
        result.put("list", goodsList);
        result.put("listcount", listcount);
		return result;
	}

	/**
	 * 사은품목록 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectGiftList(SOMap params) throws Exception {
		SOMap result = new SOMap();

        List<SOMap> giftList = giftMapper.selectGiftList(params);
        SOMap count = giftMapper.selectGiftState(params);
        result.put("list", giftList);
        result.put("count", count);
		return result;
	}

	/**
	 * 주문내역 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	@Override
	public SOMap selectOrderList(SOMap params) throws Exception {
		SOMap result = new SOMap();

        params.put("siteid", cs.getStr("siteid"));
        List<SOMap> resultList = comOrderGoodsMapper.selectAdminCommonOrderGoodsList(params);
        result.put("list", resultList);
		return result;
	}

	@Override
	public SOMap selectMemberInfoHistoryList(SOMap params) throws Exception {
		SOMap result = new SOMap();
		result.put("list", memberHistoryMapper.selectMemberHistoryList(params));
		return result;
	}

	/**
	 * 사용가능한 쿠폰 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public SOMap selectUseCouponList(SOMap params) throws Exception {
		SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
		result.put("list", couponMapper.selectPromotionCouponList(params));
		return result;
	}

	@Override
	public List<SOMap> selectAdminFavoriteAllMenuList(SOMap params) throws Exception {
		params.put("authadminno", cs.getInt("authadminno"));

		// 1.   전체 보유한 권한 노출메뉴 목록 조회
		List<SOMap> menuList = adminMenuMapper.selectAdminAllMenu(params);

		// 2.   메뉴 parent-child 구조로 세팅
		// 2-1. depth 1 메뉴만 추출
		List<SOMap> resultList = menuList.stream()
				.filter(parentMenu -> "1".equals(parentMenu.get("depth").toString()))
		        .collect(Collectors.toList());

		// 2-2. depth 2 메뉴만 추출
		List<SOMap> depth2List = menuList.stream()
				.filter(parentMenu -> "2".equals(parentMenu.get("depth").toString()))
		        .collect(Collectors.toList());

		// 2-3. depth 3메뉴만 추출
		List<SOMap> depth3List = menuList.stream()
				.filter(parentMenu -> "3".equals(parentMenu.get("depth").toString()))
		        .collect(Collectors.toList());

		// 2-4. depth 2 메뉴에 해당하는 하위메뉴(depth 3) 목록 세팅
		if (depth2List.size() > 0) {
			for (SOMap parentMenu : depth2List) {
				List<SOMap> childMenuList = depth3List.stream()
						.peek(menu-> {
							// 상위코드 세팅
							String code = menu.get("code").toString();
							String upperCode = code.substring(0, code.lastIndexOf("-"));
							menu.put("uppercode", upperCode);
						})
						.filter(menu -> parentMenu.get("code").equals(menu.get("uppercode")))
						.collect(Collectors.toList());
				parentMenu.put("childmenulist", childMenuList);
			}
		}

		// 2-5. child가 있는 depth2는 depth3을 없는 경우 depth2를 depth1 자식으로 넣을 수 있도록 세팅
		List<SOMap> addList = new ArrayList<>();
		if(depth2List.size() > 0) {
			depth2List.forEach(menu -> {
				List<SOMap> childMenuList = menu.getArrayList("childmenulist");
				if(childMenuList.size() > 0) {
					childMenuList.forEach(child -> {
						// 상위코드 세팅
						String code = child.get("code").toString();
						String upperCode = code.substring(0, code.indexOf("-"));
						child.put("uppercode", upperCode);
						addList.add(child);
					});
				} else {
					// 상위코드 세팅
					String code = menu.get("code").toString();
					String upperCode = code.substring(0, code.lastIndexOf("-"));
					menu.put("uppercode", upperCode);
					addList.add(menu);
				}
			});
		}

		// 3. depth 1 메뉴에 하위메뉴가 없는 depth2와 depth3를 child로 세팅
		if(resultList.size() > 0) {
			for(SOMap parentMenu : resultList) {
				List<SOMap> childMenuList = addList.stream()
						.filter(menu -> parentMenu.get("code").equals(menu.get("uppercode")))
						.collect(Collectors.toList());
				parentMenu.put("childmenulist", childMenuList);
			}
		}

		return resultList;
	}

	@Override
	public SOMap saveFavoriteMenu(SOMap params) throws Exception {
		params.put("userno", cs.getStr("authuserno"));
		SOMap result = new SOMap();

		try{
			result.put("resCnt", adminPermissionMapper.updateBookMark(params));
		} catch(Exception e){
			throw new CustomException("즐겨찾기 메뉴 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
		}

		return result;
	}

	@Override
	public List<SOMap> selectAdminFavoriteMenuList(SOMap params) throws Exception {
		params.put("authadminno", cs.getInt("authadminno"));

		List<SOMap> favoriteMenuList = adminMenuMapper.selectAdminFavoriteMenu(params);

		for(SOMap menu : favoriteMenuList) {
			menu.put("childmenulist", new ArrayList<>());
		}

		return favoriteMenuList;
	}

}
