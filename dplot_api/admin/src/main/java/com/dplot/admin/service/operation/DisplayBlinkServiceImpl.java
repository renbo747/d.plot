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
import com.dplot.mapper.BlinkSpcPriceMapper;
import com.dplot.mapper.ExhibitionMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.TimeSpcPriceMapper;

/**
 * @discription	: 반짝특가 ServiceImpl
 * @fileName	: DisplayBlinkServiceImpl.java
 * @author		: LKW
 * @date		: 2021.12.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.10	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class DisplayBlinkServiceImpl extends MallBaseService implements DisplayBlinkService {
	@Autowired
	private BlinkSpcPriceMapper blinkSpcPriceMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	/**
	 * 기본정보 - 타임특가 전체 조회
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public SOMap selectBlinkSpcPriceList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
        SOMap result = new SOMap();
        List<SOMap> list = blinkSpcPriceMapper.selectBlinkSpcPriceList(params);
        SOMap useMap = blinkSpcPriceMapper.selectBlinkSpcPriceUseCnt(params);

        result.put("list", list);
        result.put("listCount", (useMap != null) ? useMap.getInt("total_cnt") : 0);
        result.put("useCnt", (useMap != null) ? useMap.getInt("use_cnt") : 0);
        result.put("trashCnt", (useMap != null) ? useMap.getInt("trash_cnt") : 0);
        
		return result;
	}
}
