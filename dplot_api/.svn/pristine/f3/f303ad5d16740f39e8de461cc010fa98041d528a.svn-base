package com.dplot.front.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.InstagramFeedService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.mapper.BannerMapper;
import com.dplot.mapper.BrandLikeMapper;
import com.dplot.mapper.MainPopupMapper;
import com.dplot.mapper.MzKeywordMapper;
import com.dplot.mapper.MzexhibitMapper;
import com.dplot.mapper.MztrendLikeMapper;
import com.dplot.util.Util;

@Service
public class MzServiceImpl extends MallBaseService implements MzService{

	@Autowired
	private MztrendLikeMapper mztrendLikeMapper;
	
	@Autowired
	private BrandLikeMapper brandLikeMapper;
	
	@Autowired
	private BannerMapper bannerMapper;
	
	@Autowired
	private MzexhibitMapper mzexhibitMapper;
	
	@Autowired
	private InstagramFeedService instagramFeedService;
	
	@Autowired
	private MzKeywordMapper mzKeywordMapper;
	
	@Autowired
	private MainPopupMapper mainPopupMapper;
	
	/********************************
	 * 좋아요 목록 조회
	 * @throws Exception 
	 *********************************/
	@Override
	public SOMap selectTrendLikeList(SOMap param) throws Exception {
		SOMap result  = new SOMap();
		/*****************
		 * 페이징 처리 및 param값 셋팅
		 ******************/
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		if(Util.flag2Bool(param.getDbStr("init")) && !cs.getStr("platform").equals("MAC001")) {
			param.put("startpage", 0);
			param.put("endpage", startPage + param.getInt("listcnt"));
		}
		
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		if (Util.flag2Bool(cs.getStr("ismobile"))) {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_TRD_MO);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_TRD_PC);
		}
		List<SOMap> likelist = mztrendLikeMapper.selectMzLikeList(param);
		int listTotal = mztrendLikeMapper.selectMzLikeListCnt(param);
		result.put("likelist", likelist);
		result.put("listtotal", listTotal);
		return result;
	}
	
	/********************************
	 * 브랜드, 트랜드 좋아요 전체 삭제 처리
	 * @throws Exception 
	 *********************************/
	@Override
	public SOMap deleteTrendLikeAll(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인이 필요한 서비스입니다.");
		}
		if (param.getStr("type").equals("all")) {
			brandLikeMapper.deleteBrandLike(param);
			mztrendLikeMapper.deleteMztrendLike(param);
		}else if (param.getStr("type").equals("brand")) {
			brandLikeMapper.deleteBrandLike(param);
		}else if (param.getStr("type").equals("megazine")) {
			mztrendLikeMapper.deleteMztrendLike(param);
		}else {
			throw new BizException("목록 전체 삭제에 실패했습니다.");
		}
		return resultMap;
	}

	/********************************
	 * 브랜드, 트랜드 좋아요 선택 삭제 처리
	 * @throws Exception 
	 *********************************/
	@Override
	public SOMap deleteTrendLike(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인이 필요한 서비스입니다.");
		}
		if (param.getInt("idx") == 0 || param.getStr("type") == "") {
			throw new BizException("삭제에 실패했습니다.");
		}
		if (param.getStr("type").equals("brand")) {
			param.put("bridx", param.getInt("idx"));
			brandLikeMapper.deleteBrandLike(param);
		}else {
			param.put("tridx", param.getInt("idx"));
			mztrendLikeMapper.deleteMztrendLike(param);
		}
		return resultMap;
	}
	
	/********************************
	 * 메인 목록 조회
	 * @throws Exception 
	 *********************************/
	@Override
	public SOMap selectMzList(SOMap param) throws Exception {
		SOMap result = new SOMap();
		
		param.put("siteid", cs.getStr("siteid"));
		param.put("platform", cs.getStr("platform"));
		param.put("muappchtype", cs.getStr("platform"));
		param.put("userno", cs.getInt("authmemberno"));
		
		if(param.getInt("userno") == 0) {
			param.put("mumembertype", "DMT001");
			param.put("mumemlvtype", CMConst.MEMLVTYPE_BRONZE);
		} else {
			param.put("mumembertype", cs.getStr("authmembertype"));
			param.put("mumemlvtype", cs.getStr("authmemlvtype"));
		}
		/**********************
		 * 메인배너 조회(최상단)
		 **********************/
		param.put("bntype", "BNT001");
		if(Util.flag2Bool(cs.getStr("ismobile"))) {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_MAIN_MO);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_MAIN_PC);			
		}
		param.put("limit", null);
		List<SOMap> mainBannerList = bannerMapper.selectFrontMainBanner(param);
		result.put("mainbannerlist", mainBannerList);
		
		/**********************
		 * 전시영역 조회
		 **********************/
		if(Util.flag2Bool(cs.getStr("ismobile"))) {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_EXH_MO);			
			param.put("goodsimgtype", CMConst.IMG_TYPE_GOODS_IMG_MO_M);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_EXH_PC);
			param.put("goodsimgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		}
		
		List<SOMap> mzDataList = mzexhibitMapper.selectFrontExhibitList(param);
		List<SOMap> magazineDataList = new ArrayList<>();
		mzDataList.forEach(m -> {
			SOMap allData = new SOMap();
			SOMap exhibitdata = new SOMap();
			List<SOMap> goodsList = new ArrayList<>();
			SOMap goods1 = new SOMap();
			SOMap goods2 = new SOMap();
			exhibitdata.put("mzexidx", m.getStr("mzexidx"));
			exhibitdata.put("title", m.getStr("title"));
			exhibitdata.put("text", m.getStr("text"));
			exhibitdata.put("label", m.getStr("mzname"));
			exhibitdata.put("pclinkurl", m.getStr("pclinkurl"));
			exhibitdata.put("ispcnwindow", m.getStr("ispcnwindow"));
			exhibitdata.put("molinkurl", m.getStr("molinkurl"));
			exhibitdata.put("ismonwindow", m.getStr("ismonwindow"));
			exhibitdata.put("fullpath", m.getStr("fullpath"));
			exhibitdata.put("tags", "".equals(m.getStr("tags")) ? new ArrayList<>() : m.getStr("tags").split(","));
			
			goods1.put("fullpath", m.getStr("goods1fullpath"));
			goods1.put("goodsno", m.getStr("goods1no"));
			goods1.put("goodscode", m.getStr("goods1code"));			
			goods1.put("text", m.getStr("goods1name"));
			goods1.put("rating", m.getStr("goods1rating"));
			goods1.put("users", m.getStr("goods1users"));
			goods1.put("iswished", m.getStr("goods1iswished"));
			goods1.put("cateidx", m.getStr("goods1cateidx"));
			goods1.put("category", m.getStr("goods1categoryname"));
			goods1.put("brname", m.getStr("goods1brname"));
			goods1.put("brenname", m.getStr("goods1brenname"));
			goods1.put("tags",  "".equals(m.getStr("goods1keyword")) ? new ArrayList<>() : m.getStr("goods1keyword").split(","));
			
			goods2.put("fullpath", m.getStr("goods2fullpath"));
			goods2.put("goodsno", m.getStr("goods2no"));
			goods2.put("goodscode", m.getStr("goods2code"));
			goods2.put("text", m.getStr("goods2name"));
			goods2.put("rating", m.getStr("goods2rating"));
			goods2.put("users", m.getStr("goods2users"));
			goods2.put("iswished", m.getStr("goods2iswished"));
			goods2.put("cateidx", m.getStr("goods2cateidx"));
			goods2.put("category", m.getStr("goods2categoryname"));
			goods2.put("brname", m.getStr("goods2brname"));
			goods2.put("brenname", m.getStr("goods2brenname"));
			goods2.put("tags", "".equals(m.getStr("goods2keyword")) ? new ArrayList<>() : m.getStr("goods2keyword").split(","));
			
			allData.put("exhibitdata", exhibitdata);
			goodsList.add(goods1);
			goodsList.add(goods2);
			allData.put("goodsdatalist", goodsList);
			magazineDataList.add(allData);
		});
		
		result.put("magazinedatalist", magazineDataList);
		
		/**********************
		 * 인스타피드 조회
		 **********************/
		result.put("instagramdata", instagramFeedService.selectMainFeed(param).get("list"));
		
		return result;
	}
	
	/********************************
	 * 키워드 목록 조회
	 * @throws Exception 
	 *********************************/
	@Override
	public SOMap selectMzKeywordList(SOMap param) throws Exception {
		SOMap result = new SOMap();
		
		param.put("siteid", cs.getStr("siteid"));
		param.put("platform", cs.getStr("platform"));
		param.put("muappchtype", cs.getStr("platform"));
		param.put("userno", cs.getInt("authmemberno"));
		
		if(param.getInt("userno") == 0) {
			param.put("mumembertype", "DMT001");
			param.put("mumemlvtype", CMConst.MEMLVTYPE_BRONZE);
		} else {
			param.put("mumembertype", cs.getStr("authmembertype"));
			param.put("mumemlvtype", cs.getStr("authmemlvtype"));
		}
		
		if(Util.flag2Bool(cs.getStr("ismobile"))) {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_KWD_MO);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_KWD_PC);			
		}
		param.put("limit", 1);
		List<SOMap> keywordList = mzKeywordMapper.selectFrontKeywordList(param);
		if(keywordList.size() > 0) {
			result.put("subject", keywordList.get(0).getStr("subject"));
			result.put("fullpath", keywordList.get(0).getStr("fullpath"));
		}
		result.put("keywordlist", keywordList);
		
		return result;
	}
	
	/********************************
	 * 매거진 팝업 목록 조회
	 * @throws Exception 
	 *********************************/
	@Override
	public SOMap selectPopupList(SOMap param) throws Exception {
		SOMap result = new SOMap();
		
		param.put("siteid", cs.getStr("siteid"));
		param.put("platform", cs.getStr("platform"));
		param.put("muappchtype", cs.getStr("platform"));
		param.put("userno", cs.getInt("authmemberno"));
		
		if(param.getInt("userno") == 0) {
			param.put("mumembertype", CMConst.MEMBERTYPE_BASIC);
			param.put("mumemlvtype", CMConst.MEMLVTYPE_BRONZE);
		} else {
			param.put("mumembertype", cs.getStr("authmembertype"));
			param.put("mumemlvtype", cs.getStr("authmemlvtype"));
		}
		
		if(Util.flag2Bool(cs.getStr("ismobile"))) {
			param.put("imgtype", CMConst.IMG_TYPE_MAIN_POPUP_MO);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_MAIN_POPUP_PC);			
		}
		List<SOMap> popupList = mainPopupMapper.selectFrontMainPopupList(param);
		
		result.put("popuplist", popupList);
		
		return result;
	}
}
