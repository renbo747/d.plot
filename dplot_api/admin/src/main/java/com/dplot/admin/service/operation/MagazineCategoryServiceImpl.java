package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.admin.service.promotion.AdminCouponServiceImpl;
import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BoardPostMapper;
import com.dplot.mapper.MzCateMapper;
import com.dplot.util.ServletRequestInfoUtil;

/**
 * @discription	: 공지사항 ServiceImpl
 * @fileName	: OperationNoticeServiceImpl.java
 * @author		: LKW
 * @date		: 2022.02.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.02.28	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class MagazineCategoryServiceImpl extends MallBaseService implements MagazineCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(OperationNoticeServiceImpl.class);
	
    @Autowired
    private MzCateMapper mzCateMapper;
    
	@Override
	public SOMap selectMzCategoryList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
        SOMap result = new SOMap();

        result.put("list", mzCateMapper.selectMzCategoryList(params));
        result.put("state", mzCateMapper.selectMzCategoryShowCnt(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateMzCategory(SOMap params) throws Exception {
		SOMap result = new SOMap();
    	params.put("siteid", cs.getStr("siteid"));
		params.put("moduserid", cs.getStr("authuserid"));
		try {
			mzCateMapper.updateMzCategory(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진카테고리 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap checkMzCategory(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap result = new SOMap();
		
		int count = mzCateMapper.checkDisplay(params);
		
		// 1개 이상일 때 출력
		String overmsg = params.getStr("overmsg");
		// 0개일 때 출력
		String zeromsg = params.getStr("zeromsg");
		
		if(count > 0){
			result.put("msg", overmsg);
		} else {
			result.put("msg", zeromsg + " 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
		}
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectMzCategoryExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return mzCateMapper.selectMzCategoryExcelList(params);
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveMzCategory(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("siteid", cs.getStr("siteid"));
		params.put("reguserid", cs.getStr("authuserid"));

		try {
			mzCateMapper.insertMzCategory(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진카테고리 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap selectMzCategoryDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("siteid", cs.getStr("siteid"));
		
		result.put("info", mzCateMapper.selectMzCategoryDetail(params));
			
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap modifyMzCategory(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		try{
	    	params.put("siteid", cs.getStr("siteid"));
			params.put("moduserid",cs.getStr("authuserid"));
			
			mzCateMapper.updateMzCategory(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진카테고리 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
}
