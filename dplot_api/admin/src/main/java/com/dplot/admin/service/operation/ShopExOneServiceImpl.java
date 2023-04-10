package com.dplot.admin.service.operation;

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
import com.dplot.mapper.ShopEx12Mapper;

/**
 * @discription	: 쇼핑전시영역1 ServiceImpl
 * @fileName	: ShopExOneServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.04	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class ShopExOneServiceImpl extends MallBaseService implements ShopExOneService {
    private static final Logger logger = LoggerFactory.getLogger(ShopExOneServiceImpl.class);
	
    @Autowired
    private ShopEx12Mapper shopEx12Mapper;
    
	@Override
	public SOMap selectShopExOneList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN001");
		params.put("filetype", CMConst.IMG_TYPE_GOODS_IMG_PC_S);
        
        SOMap result = new SOMap();

        result.put("list", shopEx12Mapper.selectShopExList(params));
        result.put("state", shopEx12Mapper.selectShopExShowCnt(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateShopExOne(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("moduserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN001");

		try {
			if(params.containsKey("numsave")) {
				shopEx12Mapper.updateShopExSortNum(params);
			} else {
				shopEx12Mapper.updateShopEx(params);						
			}
			shopEx12Mapper.updateAllSortNum(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("쇼핑전시영역1 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap checkShopExOne(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN001");
		
		SOMap result = new SOMap();
		
		result.put("msg", params.getStr("overmsg"));
		int check =  shopEx12Mapper.selectDisplayCheckCnt(params);
		if(params.containsKey("isdisplay")) {
			if("T".equals(params.getStr("isdisplay"))) {
				if(check > 8) {
					result.put("msg", "노출중인 쇼핑메인 게시물이 8건 이상 존재합니다. 진행 하시겠습니까?");
				}
			} else {
				if(check == 0) {
					result.put("msg", params.getStr("zeromsg")+" 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
				}
			}
		}
		
		if(params.containsKey("isshopmain")) {
			if("T".equals(params.getStr("isshopmain"))) {
				if(check > 8) {
					result.put("msg", "노출중인 쇼핑메인 게시물이 8건 이상 존재합니다. 진행 하시겠습니까?");
				}
			} 
		}
		
		if(params.containsKey("istrash")) {
			if(check == 0) {
				result.put("msg", params.getStr("zeromsg")+" 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
			}
		}
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectShopExOneExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN001");
		return shopEx12Mapper.selectShopExExcelList(params);
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveShopExOne(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));
		params.put("extype", "EXN001");

		try {
			shopEx12Mapper.insertShopEx(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("쇼핑전시영역1 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
}
