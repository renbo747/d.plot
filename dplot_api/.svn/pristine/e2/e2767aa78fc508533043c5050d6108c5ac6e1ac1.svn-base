package com.dplot.admin.controller.cs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.cs.AdminCSRepTemplateService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @discription	: 답변템플릿 Controller
 * @fileName	: RepTemplateController.java
 * @author		: JSK
 * @date		: 2022.03.30
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.30	JSK			최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/cs/reptemp")
public class RepTemplateController {

    @Autowired
    private AdminCSRepTemplateService adminCSRepTemplateService;
    
    /**
     * 답변템플릿 목록조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response repTemplateList(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSRepTemplateService.selectRepTemplateList(params));
    }
    
    /**
     * 답변템플릿 상세조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response repTemplateDetail(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSRepTemplateService.selectRepTemplateDetail(params));
    }
    
    /**
     * 답변템플릿 저장
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response saveRepTemplate(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminCSRepTemplateService.mergeRepTemplate(params));
        return new Response(result);
    }
    
    /**
     * 답변템플릿 삭제
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Response removeRepTemplate(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminCSRepTemplateService.removeRepTemplate(params));
        return new Response(result);
    }
    
    /**
     * 답변템플릿 노출순서 변경
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/sort/update", method = RequestMethod.POST)
    public Response updateRepTemplateSort(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminCSRepTemplateService.updateRepTemplateSort(params));
        return new Response(result);
    }
}
