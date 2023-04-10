package com.dplot.admin.service.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.ConfigDealerMapper;
import com.dplot.mapper.ConfigMapper;

@Service
public class AdminGoodsModifyAuthServiceImpl extends MallBaseService implements AdminGoodsModifyAuthService {
	private static final Logger logger = LoggerFactory.getLogger(AdminCodeManageServiceImpl.class);

	@Autowired
	private ConfigDealerMapper configDealerMapper;
	
	@Override
	public SOMap selectAuthType(SOMap params) throws Exception {
		SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        SOMap config = configDealerMapper.selectConfigMuAddAuth(params);
        
		result.put("muaddauthtype", config.getStr("muaddauthtype"));
		if("T".equals(config.getStr("isfirst"))){
			result.put("first", true);
		}
		
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap updateAuthType(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
		SOMap result = new SOMap();
    	
   	 	try {
   	 		result.put("resCnt", configDealerMapper.updateConfigDealer(params));
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("상품수정권한 변경시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
   	
   	 	return result;
	}
}
