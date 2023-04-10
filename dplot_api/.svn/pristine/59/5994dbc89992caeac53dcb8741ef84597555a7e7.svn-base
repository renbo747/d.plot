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
import com.dplot.mapper.CateSubsetMapper;

/**
 * @discription	: 배너대용문구 ServiceImpl
 * @fileName	: ShoppingSubsetServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.04	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class ShoppingSubsetServiceImpl extends MallBaseService implements ShoppingSubsetService {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingSubsetServiceImpl.class);
	
    @Autowired
    private CateSubsetMapper cateSubsetMapper;
    
	@Override
	public SOMap selectSubsetList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();

        result.put("list", cateSubsetMapper.selectSubsetList(params));
        result.put("state", cateSubsetMapper.selectSubsetShowCnt(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateSubset(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("moduserid", cs.getStr("authuserid"));

		try {
			cateSubsetMapper.updateSubset(params);		
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("배너대용문구 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap checkSubset(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap result = new SOMap();
		
		result.put("msg", params.getStr("overmsg"));
		if("T".equals(params.getStr("isdisplay"))) {
			if(params.containsKey("idxlist")) {
				if(params.getArrayList("idxlist").size() > 1) {
					result.put("msg", "노출중인 배너문구가 1건 이상 존재합니다. 진행 하시겠습니까?");					
				} else {
					int check =  cateSubsetMapper.selectDisplayCheckCnt(params);
					if(check > 1) {
						result.put("msg", "노출중인 배너문구가 1건 이상 존재합니다. 진행 하시겠습니까?");
					}
				}
			} else {
				int check =  cateSubsetMapper.selectDisplayCheckCnt(params);
				if(check >= 1) {
					result.put("msg", "노출중인 배너문구가 1건 이상 존재합니다. 진행 하시겠습니까?");
				}
			}
		} else {
			int check =  cateSubsetMapper.selectDisplayCheckCnt(params);
			if(check == 0) {
				result.put("msg", params.getStr("zeromsg")+" 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
			}
		}
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectSubsetExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return cateSubsetMapper.selectSubsetExcelList(params);
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveSubset(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));

		try {
			cateSubsetMapper.insertSubset(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("배너대용문구 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap selectSubsetDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();

		params.put("siteid", cs.getStr("siteid"));
		
		SOMap info = cateSubsetMapper.selectSubsetDetail(params);
		
		result.put("info", info);
			
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap modifySubset(SOMap params) throws Exception {
		SOMap resultMap = new SOMap();
		
		params.put("moduserid", cs.getStr("authuserid"));
		
		try{
			cateSubsetMapper.updateSubset(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("배너대용문구 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return resultMap;
	}
}
