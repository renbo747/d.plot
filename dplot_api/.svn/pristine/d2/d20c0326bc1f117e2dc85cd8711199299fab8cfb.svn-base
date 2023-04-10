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
public class OperationBannedServiceImpl extends MallBaseService implements OperationBannedService {
    private static final Logger logger = LoggerFactory.getLogger(OperationBannedServiceImpl.class);
    
    @Autowired
    private BannedMapper bannedMapper;
    
	@Override
	public SOMap selectBannedList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();
        
        List<SOMap> list = bannedMapper.selectBannedList(params);

        result.put("list", list);
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveBanned(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("siteid",cs.getStr("siteid"));		
		params.put("reguserid", cs.getStr("authuserid"));

		try {
			List<SOMap> list = bannedMapper.selectBannedList(params);
			
			if(list.size() > 0) {
				result.put("check", "F");
				result.put("list", list);
			} else {
				bannedMapper.insertBanned(params);
				result.put("check", "T");
			}
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("금칙어 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap removeBanned(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("siteid",cs.getStr("siteid"));		

		try {
			bannedMapper.removeBanned(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("금칙어 삭제시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
}
