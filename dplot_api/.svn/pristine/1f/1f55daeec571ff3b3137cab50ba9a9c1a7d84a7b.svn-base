package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.SubscribeMapper;

/**
 * @discription	: 구독관리 ServiceImpl
 * @fileName	: OperSubscribeServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.08	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class OperSubscribeServiceImpl extends MallBaseService implements OperSubscribeService {
    private static final Logger logger = LoggerFactory.getLogger(OperSubscribeServiceImpl.class);
    
    @Autowired
    private SubscribeMapper subscribeMapper;
    
	@Override
	public SOMap selectSubscribeList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        

        result.put("list", subscribeMapper.selectSubscribeList(params));
        result.put("state", subscribeMapper.selectSubscribeCnt(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateSubscribe(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("siteid",cs.getStr("siteid"));		
		params.put("moduserid", cs.getStr("authuserid"));

		try {
			subscribeMapper.updateSubscribe(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("구독관리 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectSubscribeExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return subscribeMapper.selectSubscribeExcelList(params);
	}
}
