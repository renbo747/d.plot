package com.dplot.front.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.MzCateMapper;
import com.dplot.mapper.MzTrendMapper;
import com.dplot.mapper.MztrendLikeMapper;
import com.dplot.util.Util;

@Service
public class TrendServiceImpl extends MallBaseService implements TrendService{
	
	@Autowired
	private MzCateMapper mzCateMapper;
	
	@Autowired
	private MzTrendMapper mzTrendMapper;
	
	@Autowired
	private MztrendLikeMapper mzTrendLikeMapper;
	/**
	 * 매거진 트렌드 목록 조회
	 */
	@Override
	public SOMap selectTrendList(SOMap param) throws Exception{
		SOMap resultMap = new SOMap();
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
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_TRD_MO);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_TRD_PC);			
		}
		param.put("limit", 7);
		/**************************************
		 * 매거진 카테고리 조회
		 **************************************/
		List<SOMap> cateList = mzCateMapper.selectAllMzCateList(param);
		SOMap allCate = new SOMap();
		allCate.put("mzcateidx", "0");
		allCate.put("subject", "전체");
		allCate.put("checked", true);
		cateList.add(0, allCate);
		
		resultMap.put("trendcategory", cateList);
		/**************************************
		 * 매거진 트렌드 조회
		 **************************************/
		param.put("mztrend", "T");
		List<SOMap> trendList = mzTrendMapper.selectFrontMzTrendList(param);
		resultMap.put("trendlist", trendList);
		
		return resultMap;
	}
	
	@Override
	public SOMap selectTrendDetail(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
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
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_TRD_MO);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_MAGAZINE_TRD_PC);			
		}
		
		resultMap.put("info", mzTrendMapper.selectFrontTrendDetail(param));
		
		return resultMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public SOMap changeWish(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		param.put("platform", cs.getStr("platform"));
		param.put("muappchtype", cs.getStr("platform"));
		param.put("userno", cs.getInt("authmemberno"));
		
		if("T".equals(param.getStr("iswished"))) {
			mzTrendLikeMapper.deleteMztrendLike(param);
		} else {
			mzTrendLikeMapper.insertMzTrendLike(param);
		}
		
		resultMap.put("wishcnt", mzTrendLikeMapper.selectMzTrendLikeCnt(param));
		
		return resultMap;
	}
}
