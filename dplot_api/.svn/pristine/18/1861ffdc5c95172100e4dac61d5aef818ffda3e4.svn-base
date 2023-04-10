package com.dplot.admin.service.cs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
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
import com.dplot.mapper.ReqTemplateMapper;

/**
 * @discription	: 답변템플릿 ServiceImpl
 * @fileName	: AdminCSRepTemplateServiceImpl.java
 * @author		: JSK
 * @date		: 2022.03.30
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.30	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminCSRepTemplateServiceImpl extends MallBaseService implements AdminCSRepTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(AdminCSRepTemplateServiceImpl.class);

    @Autowired
    ReqTemplateMapper reqTemplateMapper;
    
    /**
     * 답변템플릿 목록조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectRepTemplateList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        params.put("siteid", cs.getStr("siteid"));
        List<SOMap> resultList = reqTemplateMapper.selectRepTemplateList(params);
        SOMap count = reqTemplateMapper.selectRepTemplateListCount(params);
        
        result.put("list", resultList);
        result.put("count", count);
        return result;
    }
    
    /**
     * 답변템플릿 상세조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectRepTemplateDetail(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
        // A/S문의 상세내역 조회
        params.put("siteid", cs.getStr("siteid"));
        result.put("info", reqTemplateMapper.selectRepTemplateDetail(params));
        
        return result;
    }
    
    /**
     * 답변템플릿 저장
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int mergeRepTemplate(SOMap params) throws Exception {
    	int resultCnt = 0;

    	try {
            params.put("siteid", cs.getStr("siteid"));
            params.put("authuserid", cs.getStr("authuserid"));
    		resultCnt = reqTemplateMapper.mergeRepTemplate(params);
    	} catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("답변템플릿 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    	
        return resultCnt;
    }
    
    /**
     * 답변템플릿 삭제
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int removeRepTemplate(SOMap params) throws Exception {
    	int resultCnt = 0;

    	try {
            params.put("siteid", cs.getStr("siteid"));
            params.put("authuserid", cs.getStr("authuserid"));
            // 삭제여부 변경(논리삭제)
            params.put("istrash", "T");
    		resultCnt = reqTemplateMapper.updateRepTemplateUse(params);
    	} catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("답변템플릿 삭제시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    	
        return resultCnt;
    }
    
    /**
     * 답변템플릿 노출순서 변경
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int updateRepTemplateSort(SOMap params) throws Exception {
    	int resultCnt = 0;

    	try {
            params.put("siteid", cs.getStr("siteid"));
            params.put("authuserid", cs.getStr("authuserid"));
    		resultCnt = reqTemplateMapper.updateRepTemplateSort(params);
    	} catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("답변템플릿 노출순서 변경시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    	
        return resultCnt;
    }
}
