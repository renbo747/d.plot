package com.dplot.admin.service.operation;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.ExhibitionMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.NewGoodsMapper;

/**
 * @discription	: 기획전 ServiceImpl
 * @fileName	: DisplayExhibitServiceImpl.java
 * @author		: LKW
 * @date		: 2021.11.26
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.26	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class DisplayNewGoodsServiceImpl extends MallBaseService implements DisplayNewGoodsService {
	
	@Autowired
	NewGoodsMapper newGoodsMapper;
	
	@Autowired
	GoodsMapper goodsMapper;
	
	/**
	 * 기본정보 - 기획전 전체 조회
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public SOMap selectNewGoodsList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        SOMap result = new SOMap();
        List<SOMap> list = newGoodsMapper.selectNewGoodsList(params);
        SOMap useMap = newGoodsMapper.selectNewGoodsUseCount(params);
        
        result.put("list", list);
        result.put("totalcnt", (useMap != null) ? useMap.getInt("total_cnt") : 0);
        result.put("usecnt", (useMap != null) ? useMap.getInt("use_cnt") : 0);
        result.put("trashcnt", (useMap != null) ? useMap.getInt("trash_cnt") : 0);
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap updateNewGoodsList(SOMap params) throws Exception{
		SOMap result = new SOMap();
		if(params.containsKey("istrash")){
			// 상태변경
			newGoodsMapper.updateNewGoodsUse(params);
		}else{
			// 순서변경
			newGoodsMapper.updateNewGoodsSort(params);
		}
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap insertNewGoodsList(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		SOMap result = new SOMap();
		
		List<SOMap> duplData = newGoodsMapper.selectDulpNewGoods(params);
		List<Map<String,Object>> allData = params.getArrayList("data");
		if(duplData.size() > 0){
			List<Map<String,Object>> addData = new ArrayList<>();
			addData.addAll(allData);
			allData.stream().filter(m1 -> duplData.stream().anyMatch(m2 -> m2.getStr("goodsno").equals(m1.get("goodsno").toString()))).forEach(addData::remove);
			params.put("data", addData);
			result.put("msg", "중복된 상품을 제외하고 저장되었습니다.");
		}else{
			result.put("msg", "저장되었습니다.");
		}
		
		if(allData.size() > 0){
			newGoodsMapper.insertNewGoods(params);			
		}
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectNewGoodsExcelList(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		return newGoodsMapper.selectNewGoodsExcelList(params);
	}
}
