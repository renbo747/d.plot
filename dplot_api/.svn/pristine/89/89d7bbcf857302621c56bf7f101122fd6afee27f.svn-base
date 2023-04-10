package com.dplot.admin.controller.partners;

import com.dplot.admin.service.common.ExternalService;
import com.dplot.admin.service.partners.AdminPartnersStatusService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin/partners/status")
public class PartnersStatusController extends ExcelDownController {
    private static final Logger logger = LoggerFactory.getLogger(PartnersStatusController.class);

    @Autowired
    private AdminPartnersStatusService adminPartnersStatusService;

    @Autowired
    private ExternalService externalService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response partnersStatusList(@RequestBody SOMap params) throws Exception {
        return new Response(adminPartnersStatusService.selectPartnersList(params));
    }

    @RequestMapping(value="/change", method = RequestMethod.POST)
    public Response partnersChangeStatus(@RequestBody SOMap params){
        Map<String, Object> result = new HashMap<>();
        
        params.put("user_no_arr", params.get("user_no_arr_str"));
        int resultCnt = 0;

        try {
            resultCnt = adminPartnersStatusService.updatePartnersState(params);
            result.put("msg", "success");
        } catch(Exception e){
            logger.error(e.getMessage());
            result.put("msg", "fail");
        }

        result.put("resultCnt", resultCnt);
        return new Response(result);
    }

    @RequestMapping(value="/memo", method = RequestMethod.POST)
    public Response partnersMemo(@RequestBody SOMap params) throws Exception {
        return new Response(adminPartnersStatusService.selectPartnsersMemo(params));
    }
    
    @RequestMapping(value="/memo/save", method = RequestMethod.POST)
    public Response saveMemo(@RequestBody SOMap params) throws Exception {
    	Response result = new Response();
    	
    	int res = adminPartnersStatusService.insertOrUpdatePartnsersMemo(params);
    	
    	if(res > 0){
    		result.setMessage("success");
    	}else{
    		result.setMessage("fail");
    	}
    	
        return result;
    }
    
    @RequestMapping(value="/excel", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> excelDownload(@RequestBody SOMap params) throws Exception {
   	
    	List<Map<String, Object>> list = adminPartnersStatusService.selectPartnersExcelList(params); 

    	// 헤더 이름
    	String[] header = {"아이디","업체명","사업자등록번호","제휴 담당자","담당자 연락처","대표자 휴대폰","입점일","전시상품","상태","메모", "계약요청"};
//    	String[] column = {"DEALER_ID","NAME","BIZNO","CHARGENAME","TEL","MOBILE","REGDATEYYYYMMDD","GOODSCNT","DEALERST","MEMOCNT"};
    	// 컬럼 너비 지정
    	int[] columnSize = {14*256,14*256,15*256,10*256,15*256,15*256,12*256,7*256,9*256,7*256,7*256};
    	
    	return makeExcelFile(header, null, columnSize, list, "파트너사현황목록.xlsx");
    }
    
    @RequestMapping(value="/contract", method = RequestMethod.POST)
    public Response contract(@RequestBody SOMap params) throws Exception {
        return new Response(externalService.partnersSignRequest(params));
    }
    
    @RequestMapping(value="/contract/download", method = RequestMethod.POST)
    public Response contractDown(@RequestBody SOMap params) throws Exception {
    	Map<String, Object> result = new HashMap<>();
        result.put("contract_url", externalService.getContractUrl(params.get("moduid").toString()));
        return new Response(result);
    }
}
