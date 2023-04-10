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
import com.dplot.mapper.TimeSpcPriceMapper;

/**
 * @discription	: 타임특가 ServiceImpl
 * @fileName	: DisplayTimeServiceImpl.java
 * @author		: LKW
 * @date		: 2021.12.09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.09	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class DisplayTimeServiceImpl extends MallBaseService implements DisplayTimeService {
	@Autowired
	private TimeSpcPriceMapper timeSpcPriceMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	/**
	 * 기본정보 - 타임특가 전체 조회
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public SOMap selectTimeSpcPriceList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
        SOMap result = new SOMap();
        List<SOMap> list = timeSpcPriceMapper.selectTimeSpcPriceList(params);
        SOMap useMap = timeSpcPriceMapper.selectTimeSpcPriceUseCnt(params);

        result.put("list", list);
        result.put("listCount", (useMap != null) ? useMap.getInt("total_cnt") : 0);
        result.put("useCnt", (useMap != null) ? useMap.getInt("use_cnt") : 0);
        result.put("trashCnt", (useMap != null) ? useMap.getInt("trash_cnt") : 0);
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap insertTimeSpcPrice(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("siteid", cs.getStr("siteid"));
		
		int res = timeSpcPriceMapper.insertTimeSpcPrice(params);
		result.put("rescnt", res);
		
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap updateTimeSpcPrice(SOMap params) throws Exception {
		SOMap result = new SOMap();
		int res = 0;
		if(params.containsKey("list")){
			res = timeSpcPriceMapper.updateTimeSpcPriceUse(params);
		}else{
			res = timeSpcPriceMapper.updateTimeSpcPrice(params);
		}
		
		result.put("rescnt", res);			
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectTimeSpcPriceExcelList(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		return timeSpcPriceMapper.selectTimeSpcPriceExcelList(params);
	}
	
	@Override
	public SOMap selectTimeSpcPriceDetail(SOMap params) throws Exception{
		SOMap result = new SOMap();
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap info = timeSpcPriceMapper.selectTimeSpcPriceDetail(params);
		if(!"".equals(info.getStr("goodsno"))){
			info.put("goodsinfo", goodsMapper.selectAdminGoodsDetailAll(info));
		}
		result.put("info",info);
		return result;
	}
}
