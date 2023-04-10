package com.dplot.admin.service.cs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CJMessageService;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.CodeMapper;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.InquiryMapper;
import com.dplot.mapper.ReqTemplateMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.util.DateTime;

/**
 * @author : ywm2004
 * @discription : CS관리 1:1 문의 ServiceImpl
 * @fileName : AdminCSOneOneServiceImpl.java
 * @date : 2021-11-19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-19	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminCSOneOneServiceImpl extends MallBaseService implements AdminCSOneOneService {

    @Autowired
    InquiryMapper inquiryMapper;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    FileService fileService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DealerMapper dealerMapper;

    @Autowired
    ReqTemplateMapper reqTemplateMapper;
    
    @Autowired
    CJMessageService cjMessageService;

    @Resource(name="propertiesFactory")
    private Properties prop;
    
    @Override
    public SOMap selectInquiryList(SOMap params) throws Exception {
        SOMap result = new SOMap();

        // String 날짜 변환
        Date startDate = DateTime._stringToDate(params.get("startdate").toString());
        Date endDate = DateTime._stringToDate(params.get("enddate").toString());

        // 끝 날짜가 시작 날짜보다 작다면 서로 교체
        if (endDate.before(startDate)) {
            params.put("startdate", DateTime._getDateString(endDate));
            params.put("enddate", DateTime._getDateString(startDate));
        }

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        // 사이트 아이디
        params.put("siteid", cs.getStr("siteid"));

        result.put("list", inquiryMapper.selectInquiryList(params));
        result.put("statelist", inquiryMapper.selectInquiryState(params));
        result.put("partnerslist", dealerMapper.selectPartnersList(params));

        return result;
    }

    @Override
    public SOMap selectInquiryDtl(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
        SOMap result = new SOMap();

        SOMap fileParams = new SOMap();
        fileParams.put("orgidx", params.get("idx"));
        fileParams.put("imgtype", CMConst.IMG_TYPE_INQUIRY);

        SOMap fileMap = fileService.selectFileList(fileParams);
        
        List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
        List<Map<String, Object>> imgfile = new ArrayList<>();
        List<Map<String, Object>> videofile = new ArrayList<>();
        
        fileList.forEach(m -> {
        	if(CMConst.FILE_TYPE_IMG.equals(m.get("filetype"))) {
        		imgfile.add(m);
        	} else{
        		videofile.add(m);
        	}
        });
        // 파일
        result.put("filelist", imgfile);
        result.put("videofile", videofile);

        result.put("list", inquiryMapper.selectInquiryDtl(params));
        result.put("template", reqTemplateMapper.selectAllUseTemplateList(params));

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public void updateInquiry(SOMap params) throws Exception {
        try {
        	// 답변 상태 완료
            params.put("isreply", "T");
            params.put("repuserno", cs.getStr("authuserno"));

            inquiryMapper.updateInquiry(params);

            String domain = prop.getProperty("front.mobile.domain");
            SOMap tempMap = new SOMap();
            tempMap.put("typename", "1:1문의");
//            tempMap.put("url", String.format("%s%s", domain, "/cs/inquiry/index/inquiry"));
            tempMap.put("idx", params.getStr("idx"));
            tempMap.put("number", params.getStr("mobile"));
            tempMap.put("userno", params.getStr("userno"));
            
            cjMessageService.sendCustomerQuestionsAndAnswer(tempMap);
            
		} catch(Exception e){
	        throw new CustomException("1:1문의 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap sendToPartnerInquiry(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
    	try {
            inquiryMapper.sendPartnerInquiry(params);
		} catch(Exception e){
	        throw new CustomException("파트너사 전송시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
        
        return result;
    }
}
