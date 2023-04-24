package com.dplot.admin.service.operation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BannedMapper;
import com.dplot.mapper.ConfigMapper;

/**
 * @discription	: 금칙어 ServiceImpl
 * @fileName	: OperationBannedServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.12
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.12	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class OperationSearchLinkServiceImpl extends MallBaseService implements OperationSearchLinkService {
    private static final Logger logger = LoggerFactory.getLogger(OperationSearchLinkServiceImpl.class);
    
    @Autowired
    private ConfigMapper configMapper;
    
	@Override
	public SOMap selectSearchLink(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();
        SOMap info = new SOMap();
        SOMap config = configMapper.selectConfigForSearchLink(params);
        
        info.put("issearchlink", config.getStr("issearchlink"));
        info.put("searchtext", config.getStr("searchtext"));
        info.put("searchlinkpc", config.getStr("searchlinkpc"));
        info.put("searchlinkmo", config.getStr("searchlinkmo"));
        info.put("isnewlinkpc", config.getStr("isnewlinkpc"));
        info.put("isnewlinkmo", config.getStr("isnewlinkmo"));

        result.put("info", info);
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveSearchLink(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("siteid",cs.getStr("siteid"));		

		try {
			configMapper.updateConfig(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("검색창링크 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
}
