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
public class OperationNoticeServiceImpl extends MallBaseService implements OperationNoticeService {
    private static final Logger logger = LoggerFactory.getLogger(OperationNoticeServiceImpl.class);
	
    @Autowired
    private BoardPostMapper boardPostMapper;
    
	@Override
	public SOMap selectNoticeList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
        SOMap result = new SOMap();

        result.put("list", boardPostMapper.selectOperationNoticeList(params));
        result.put("count", boardPostMapper.selectOperationNoticeUseCnt(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateNotice(SOMap params) throws Exception {
		SOMap result = new SOMap();
    	params.put("siteid", cs.getStr("siteid"));
		params.put("moduserid", cs.getStr("authuserid"));
		try {
			boardPostMapper.updateBoardPost(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("운영관리 공지사항 사용여부 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap checkNotice(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap result = new SOMap();
		
		int count = boardPostMapper.selectUseCount(params);
		
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
	public List<Map<String, Object>> selectNoticeExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return boardPostMapper.selectOperationNoticeExcelList(params);
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveNotice(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("userno", cs.getStr("authuserno"));
    	params.put("siteid", cs.getStr("siteid"));
		try {
			// BoardType
			params.put("boardtype", CMConst.BOARD_TYPE_OPER_NOTICE);
			
			// ip
			params.put("ip", ServletRequestInfoUtil.getRequestIp());
			
			params.put("hits", 0);
			params.put("isessnotice", "F");
			params.put("ispapopnotice", "F");
			
			if("F".equals(params.getStr("istopfix"))) {
				params.put("topfixsttime", null);
				params.put("topedtime", null);
			} else {
				params.put("topfixsttime", params.getStr("poststtime"));
			}
			
			boardPostMapper.insertBoardPost(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("운영관리 공지사항 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap deleteNotice(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("userno", cs.getStr("authuserno"));
    	params.put("siteid", cs.getStr("siteid"));
		try {
			boardPostMapper.deleteBoardPost(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("운영관리 공지사항 삭제시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap selectNoticeDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();
    	params.put("siteid", cs.getStr("siteid"));
		
//		boardPostMapper.updateBoardHits(params.getStr("idx"));
		
		result.put("info", boardPostMapper.selectOperationNoticeDetail(params));
			
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap modifyNotice(SOMap params) throws Exception {
		SOMap result = new SOMap();

    	params.put("siteid", cs.getStr("siteid"));
		try{
			params.put("moduserid",cs.getStr("authuserid"));
			
			if("F".equals(params.getStr("istopfix"))) {
				params.put("topfixsttime", null);
				params.put("topedtime", null);
			} else {
				params.put("topfixsttime", params.getStr("poststtime"));
			}
			
			boardPostMapper.updateBoardPost(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("운영관리 공지사항 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
}
