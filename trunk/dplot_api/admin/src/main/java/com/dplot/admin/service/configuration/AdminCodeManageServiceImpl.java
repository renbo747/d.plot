package com.dplot.admin.service.configuration;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.CommonClassMapper;
import com.dplot.mapper.CommonCodeMapper;
import com.dplot.util.Util;

@Service
public class AdminCodeManageServiceImpl extends MallBaseService implements AdminCodeManageService {
	private static final Logger logger = LoggerFactory.getLogger(AdminCodeManageServiceImpl.class);

	@Autowired
	private CommonClassMapper commonClassMapper;
	
	@Autowired
	private CommonCodeMapper commonCodeMapper;
	
	@Override
    public SOMap selectCodeClassList(SOMap params) throws Exception {
    	SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        result.put("list", commonClassMapper.selectCodeClassList(params));
        result.put("total_cnt", commonClassMapper.selectCodeClassCnt(params));
        
        return result;
    }
    
    @Override
    public SOMap selectCodeList(SOMap params) throws Exception {
    	SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        
        result.put("list", commonCodeMapper.selectCodeList(params));
        return result;
    }
	
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap updateCodeUse(SOMap params) throws CustomException {
    	SOMap result = new SOMap();
    	
    	 try {
    		// 공통코드그룹 사용여부 변경
 			if("G".equals(params.getStr("type"))) {
 				result.put("resCnt", commonClassMapper.updateCodeClassUse(params));
 			} else {
 				result.put("resCnt", commonCodeMapper.updateCodeUse(params));
 			}
         } catch(Exception e){
             logger.error(e.getMessage());
             throw new CustomException("코드관리 사용여부 변경시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
         }
    	
    	return result;
    }
    
    @Override
    public List<Map<String, Object>> selectCodeClassExcelList(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
    	return commonClassMapper.selectCodeClassExcelList(params);
    }
    
    @Override
    public List<Map<String, Object>> selectCodeExcelList(SOMap params) throws Exception {
    	params.put("siteid", cs.getStr("siteid"));
    	return commonCodeMapper.selectCodeExcelList(params);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap saveCode(SOMap params) throws CustomException {
    	SOMap result = new SOMap();
    	
    	 try {
    		// 공통코드그룹 저장
 			if("G".equals(params.getStr("type"))) {
 				result.put("resCnt", commonClassMapper.saveCodeClass(params));
 			} else {
 			// 공통코드 저장
 				result.put("resCnt", commonCodeMapper.saveCode(params));
 			}
         } catch(Exception e){
             logger.error(e.getMessage());
             throw new CustomException("공통코드 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
         }
    	
    	return result;
    }
    
    @Override
    public SOMap selectCodeDetail(SOMap params) throws Exception {
    	SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        // 공통코드그룹 상세조회
		if("G".equals(params.getStr("type"))) {
	        result.put("info", commonClassMapper.selectCodeClassDetail(params));
		} else {
	    // 공통코드 상세조회
	        result.put("info", commonCodeMapper.selectCodeDetail(params));
		}
        return result;
    }
	
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap modifyCode(SOMap params) throws CustomException {
    	SOMap result = new SOMap();
    	
    	 try {
    		// 공통코드그룹 수정
 			if("G".equals(params.getStr("type"))) {
 				result.put("resCnt", commonClassMapper.modifyCodeClass(params));
 			} else {
 			// 공통코드 수정
 				result.put("resCnt", commonCodeMapper.modifyCode(params));
 			}
         } catch(Exception e){
             logger.error(e.getMessage());
             throw new CustomException("공통코드 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
         }
    	
    	return result;
    }
}
