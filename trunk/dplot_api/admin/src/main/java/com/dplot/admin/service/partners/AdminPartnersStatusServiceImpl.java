package com.dplot.admin.service.partners;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.MallConfigService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.DealerMemoMapper;
import com.dplot.mapper.GoodsMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminPartnersStatusServiceImpl implements AdminPartnersStatusService {

    @Autowired
    private MallConfigService cs;

    @Autowired
    private DealerMapper dealerMapper;

    @Autowired
    private DealerMemoMapper dealerMemoMapper;
    
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public SOMap selectPartnersList(SOMap param) throws Exception {
        param.put("siteid", cs.getStr("siteid"));

        int page = param.getInt("page");
        int pageCount = param.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        param.put("startpage", startPage);
        param.put("endpage", pageCount);

        SOMap result = new SOMap();
        List<SOMap> partnersList = dealerMapper.selectPartnsersStatusList(param);
        SOMap stateCount = dealerMapper.selectPartnsersStatusListStateCount(param);

        result.put("list", partnersList);
        result.put("listCount", (stateCount != null) ? stateCount.get("total_cnt") : 0);
        result.put("totalCnt", (stateCount != null) ? stateCount.get("total_cnt") : 0);
        result.put("operationCnt", (stateCount != null) ? stateCount.get("operation_cnt") : 0);
        result.put("suspendCnt", (stateCount != null) ? stateCount.get("suspend_cnt") : 0);
        result.put("closedCnt", (stateCount != null) ? stateCount.get("closed_cnt") : 0);

        return result;
    }
    
    @Override
    public List<Map<String, Object>> selectPartnersExcelList(SOMap param) throws Exception {
    	param.put("siteid", cs.getStr("siteid"));

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> partnersList = dealerMapper.selectPartnsersExcelList(param);
        /*List<Map<String, Object>> sortList = new ArrayList<>();
        partnersList.forEach(m->{
        	Map<String, Object> tempObj = new LinkedHashMap<>();
        	tempObj.put("아이디", m.get("아이디"));
        	tempObj.put("업체명", m.get("업체명"));
        	tempObj.put("사업자등록번호", m.get("사업자등록번호"));
        	tempObj.put("담당자", m.get("담당자"));
        	tempObj.put("전화번호", m.get("전화번호"));
        	tempObj.put("휴대폰번호", m.get("휴대폰번호"));
        	tempObj.put("입점일", m.get("입점일"));
        	tempObj.put("전시상품", m.get("전시상품"));
        	tempObj.put("상태", m.get("상태"));
        	tempObj.put("메모", m.get("메모"));
        	sortList.add(tempObj);
        });
        
        result.put("list", sortList);*/

        return partnersList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int updatePartnersState(SOMap param) throws Exception{
    	int result = 0;
    	try {
    		param.put("siteid", cs.getStr("siteid"));
    		result = dealerMapper.updatePartnersDealerst(param);

            if (CMConst.PARTNSER_DEALST_STOP.equals(param.getStr("status")) || CMConst.PARTNSER_DEALST_EXIT.equals(param.getStr("status"))) {
    			param.put("isdisplay", "F");
    			result = goodsMapper.updateGoodsDisplay(param);
    		}
    	} catch (Exception e) {
    		throw new CustomException("파트너사 상태 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
    	
    	
        return result;
    }

    @Override
    public SOMap selectPartnsersMemo(SOMap param) {
    	int page = param.getInt("page");
        int pageCount = param.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        param.put("startpage", startPage);
        param.put("endpage", pageCount);

        SOMap result = new SOMap();
        List<SOMap> memoList = dealerMemoMapper.selectPartnsersMemo(param);
        int listCount = dealerMemoMapper.selectPartnersMemoCount(param);
        result.put("list", memoList);
        result.put("listCount", listCount);
        
        return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int insertOrUpdatePartnsersMemo(SOMap params) throws Exception{
    	int idx = Integer.parseInt(params.get("idx").toString());
    	params.put("reguserid", cs.getStr("authuserid"));
    	
    	try{
    		if(idx == 0){
        		return dealerMemoMapper.insertPartnersMemo(params);
        	}else{
        		return dealerMemoMapper.updatePartnersMemo(params);
        	}
    	} catch (Exception e){
			throw new CustomException("파트너사 메모 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
    	
    }
}
