package com.dplot.admin.service.configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.ConfigTermHistoryMapper;
import com.dplot.mapper.ConfigTermMapper;
import com.dplot.mapper.NotifyTplItemMapper;
import com.dplot.mapper.NotifyTplMapper;

@Service
public class AdminTermsManageServiceImpl extends MallBaseService implements AdminTermsManageService {
	private static final Logger logger = LoggerFactory.getLogger(AdminCodeManageServiceImpl.class);

	@Autowired
	private ConfigTermMapper configTermMapper;
	
	@Autowired
	private ConfigTermHistoryMapper configTermHistoryMapper;
	
	@Override
	public SOMap selectTermsList(SOMap params) throws Exception {
		SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        result.put("list", configTermMapper.selectTermsList(params));
        result.put("totalcnt", configTermMapper.selectConfigTermCnt(params).get("total_cnt"));
        
        return result;
	}
	
	@Override
	public SOMap selectTerms(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		SOMap result = new SOMap();
		
		result.put("info", configTermMapper.selectTerms(params));
		
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap updateTerms(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		params.put("authuserid", cs.getStr("authuserid"));
		SOMap result = new SOMap();
		 
    	try {
    		result.put("uptHistoryCnt", configTermHistoryMapper.updateTermsHistory(params));
    		if("T".equals(params.getStr("versionup"))) {
    			result.put("saveCnt", configTermHistoryMapper.insertTermsHistory(params));    			
    		}
    		result.put("uptCnt", configTermMapper.updateTerms(params));
    	} catch(Exception e){
    		logger.error(e.getMessage());
    		throw new CustomException("이용약관 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
		
		return result;
	}
}
