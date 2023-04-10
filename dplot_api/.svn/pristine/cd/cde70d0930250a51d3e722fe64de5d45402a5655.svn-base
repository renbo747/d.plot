package com.dplot.admin.service.operation;

import java.util.ArrayList;
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
import com.dplot.mapper.ShopExNameMapper;

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
public class ShopExNameServiceImpl extends MallBaseService implements ShopExNameService {
    private static final Logger logger = LoggerFactory.getLogger(ShopExNameServiceImpl.class);
	
    @Autowired
    private ShopExNameMapper shopExNameMapper;
    
	@Override
	public SOMap selectShopExNameList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();

        List<SOMap> list = shopExNameMapper.selectShopExNameList(params);
        
        List<SOMap> allList = new ArrayList<>();
        
        String[] typeArr = {"EXN001", "EXN002", "EXN003", "EXN004", "EXN005", "EXN006", "EXN007"};
        
        for(String type: typeArr) {
        	boolean check = false;
        	for(SOMap m: list) {
        		if(type.equals(m.getStr("extype"))) {
        			allList.add(m);
        			check = true;
        			break;
        		}
        	}
        	if(!check) {
        		SOMap temp = new SOMap();
        		temp.put("extype", type);
        		temp.put("exhibitname", "");
        		temp.put("isintro", "");
        		temp.put("intro", "");
        		allList.add(temp);
        	}
        }
        
        result.put("list", allList);
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveShopExName(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("moduserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));

		try {
			List<Map<String, Object>> allList = params.getArrayList("list");
			
			List<Map<String, Object>> addList = allList.stream().filter(m -> !m.containsKey("exnidx")).collect(Collectors.toList());
			List<Map<String, Object>> updateList = allList.stream().filter(m -> m.containsKey("exnidx")).collect(Collectors.toList());
			
			if(addList.size() > 0) {
				params.put("list", addList);
				shopExNameMapper.insertExName(params);				
			}
			if(updateList.size() > 0) {
				params.put("list", updateList);
				shopExNameMapper.updateExName(params);
			}
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("쇼핑메인전시영역명칭 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
}
