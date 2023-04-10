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
import com.dplot.mapper.NotifyTplItemMapper;
import com.dplot.mapper.NotifyTplMapper;

@Service
public class AdminNotifyManageServiceImpl extends MallBaseService implements AdminNotifyManageService {
	private static final Logger logger = LoggerFactory.getLogger(AdminCodeManageServiceImpl.class);

	@Autowired
	private NotifyTplMapper notifyTplMapper;
	
	@Autowired
	private NotifyTplItemMapper notifyTplItemMapper;
	
	@Override
	public SOMap selectNotifyList(SOMap params) throws Exception {
		SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        result.put("list", notifyTplMapper.selectNotifyList(params));
        result.put("statelist", notifyTplMapper.selectNotifyState(params));
        
        return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap updateNotify(SOMap params) throws Exception {
		params.put("authuserid", cs.getStr("authuserid"));
    	SOMap result = new SOMap();
		 
    	try {
    		result.put("resCnt", notifyTplMapper.updateNotifyUse(params));
    	} catch(Exception e){
    		logger.error(e.getMessage());
    		throw new CustomException("상품정보고시관리 사용여부 변경시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectNotifyExcelList(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
    	return notifyTplMapper.selectNotifyExcelList(params);
	}
	
	@Override
	public SOMap selectNotifyTpl(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		SOMap result = new SOMap();
		
		params.put("notifytplidx", params.get("idx"));
		
		result.put("info", notifyTplMapper.selectNotifyTpl(params));
		result.put("itemlist", notifyTplItemMapper.selectNotifyTplItemList(params));
		
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap saveNotifyTpl(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		params.put("authuserid", cs.getStr("authuserid"));
		SOMap result = new SOMap();
		 
    	try {
    		notifyTplMapper.saveNotifyTpl(params);
    		notifyTplItemMapper.saveNotifyTplItem(params);
    	} catch(Exception e){
    		logger.error(e.getMessage());
    		throw new CustomException("상품정보고시 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
		
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap modifyNotifyTpl(SOMap params) throws Exception {
		SOMap result = new SOMap();
		params.put("authuserid", cs.getStr("authuserid"));
		 
    	try {
    		notifyTplMapper.modifyNotifyTpl(params);
    		
    		if(params.containsKey("removeitemlist")) {
    			notifyTplItemMapper.removeNotifyTplItem(params);    			
    		}
    		
    		List<Map<String, Object>> list = params.getArrayList("itemlist");
    		List<Map<String, Object>> addItem = list.stream().filter(item -> item.get("notifyitemidx") == null).collect(Collectors.toList());
    		List<Map<String, Object>> updateItem = list.stream().filter(item -> item.get("notifyitemidx") != null).collect(Collectors.toList());
    		
    		if(addItem.size() > 0) {
    			params.put("itemlist",addItem);
    			notifyTplItemMapper.saveNotifyTplItem(params);    			
    		}
    		if(updateItem.size() > 0) {
    			params.put("itemlist", updateItem);
    			notifyTplItemMapper.modifyNotifyTplItem(params);    			
    		}
    	} catch(Exception e){
    		logger.error(e.getMessage());
    		throw new CustomException("상품정보고시 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
		
		return result;
	}
}
