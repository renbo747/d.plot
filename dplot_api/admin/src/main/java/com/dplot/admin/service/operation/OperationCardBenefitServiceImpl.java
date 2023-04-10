package com.dplot.admin.service.operation;

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
import com.dplot.mapper.CardBenefitMapper;
import com.dplot.mapper.ConfigMapper;

/**
 * @discription	: 카드혜택 ServiceImpl
 * @fileName	: OperationCardBenefitServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.19	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class OperationCardBenefitServiceImpl extends MallBaseService implements OperationCardBenefitService {
    private static final Logger logger = LoggerFactory.getLogger(OperationCardBenefitServiceImpl.class);
	
    @Autowired
    private CardBenefitMapper cardBenefitMapper;
    
    @Autowired
    private ConfigMapper configMapper;
    
	@Override
	public SOMap selectCardBenefit(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();
        
        List<SOMap> list = cardBenefitMapper.selectCardBenefitList(params);
                
        result.put("discountlist", list.stream().filter(m -> "T".equals(m.getStr("isdiscount"))).collect(Collectors.toList()));
        result.put("freelist", list.stream().filter(m -> "F".equals(m.getStr("isdiscount"))).collect(Collectors.toList()));
        result.put("note", configMapper.selectConfigCardBenefit(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveCardBenefit(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("moduserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));
		
		try {
			List<Map<String, Object>> allDiscList = params.getArrayList("discountlist");
			List<Map<String, Object>> allFreeList = params.getArrayList("freelist");
			
			List<Map<String, Object>> insertDiscList = allDiscList.stream().filter(m -> !m.containsKey("tcidx")).collect(Collectors.toList());
			List<Map<String, Object>> updateDiscList = allDiscList.stream().filter(m -> m.containsKey("tcidx")).collect(Collectors.toList());
			List<Map<String, Object>> insertFreeList = allFreeList.stream().filter(m -> !m.containsKey("tcidx")).collect(Collectors.toList());
			List<Map<String, Object>> updateFreeList = allFreeList.stream().filter(m -> m.containsKey("tcidx")).collect(Collectors.toList());
			
			insertDiscList.addAll(insertFreeList);
			updateDiscList.addAll(updateFreeList);
			
			for(Map<String,Object> m: insertDiscList) {
				if("".equals(m.get("flatrate").toString())) {
					m.put("flatrate", null);
				}
				if("".equals(m.get("fixedrate").toString())) {
					m.put("fixedrate", null);
				}
				if("".equals(m.get("month").toString())) {
					m.put("month", null);
				}
				if("".equals(m.get("discnote").toString())) {
					m.put("discnote", null);
				}
			}
			for(Map<String,Object> m: updateDiscList) {
				if("".equals(m.get("flatrate").toString())) {
					m.put("flatrate", null);
				}
				if("".equals(m.get("fixedrate").toString())) {
					m.put("fixedrate", null);
				}
				if("".equals(m.get("month").toString())) {
					m.put("month", null);
				}
				if("".equals(m.get("discnote").toString())) {
					m.put("discnote", null);
				}
			}
			
			if(insertDiscList.size() > 0) {
				params.put("insertlist", insertDiscList);
				cardBenefitMapper.insertCardBenefit(params);
			}
			if(updateDiscList.size() > 0) {
				params.put("updatelist", updateDiscList);
				cardBenefitMapper.updateCardBenefit(params);
			}
			
			if(params.containsKey("discdeletelist")) {
				SOMap temp = new SOMap();
				temp.put("istrash", "T");
				temp.put("idxlist", params.get("discdeletelist"));
				temp.put("siteid", cs.getStr("siteid"));
				
				cardBenefitMapper.updateCardBenefitUse(temp);
			}
			if(params.containsKey("freedeletelist")) {
				SOMap temp = new SOMap();
				temp.put("istrash", "T");
				temp.put("idxlist", params.get("freedeletelist"));
				temp.put("siteid", cs.getStr("siteid"));
				
				cardBenefitMapper.updateCardBenefitUse(temp);
			}
			
			SOMap tempMap = new SOMap();
			tempMap.put("siteid", cs.getStr("siteid"));
			tempMap.put("benenote", params.getStr("benenote"));
			tempMap.put("internote", params.getStr("internote"));
			
			configMapper.updateConfig(tempMap);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("카드혜택 등록 및 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
}
