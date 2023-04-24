package com.dplot.admin.service.cs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.dplot.mapper.AsInquiryMapper;
import com.dplot.mapper.ReqTemplateMapper;

/**
 * @discription	: A/S문의 ServiceImpl
 * @fileName	: AdminCSAsServiceImpl.java
 * @author		: JSK
 * @date		: 2022.03.25
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.25	JSK			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class AdminCSAsServiceImpl extends MallBaseService implements AdminCSAsService {
    private static final Logger logger = LoggerFactory.getLogger(AdminCSAsServiceImpl.class);

    @Autowired
    AsInquiryMapper asInquiryMapper;
    @Autowired
    ReqTemplateMapper reqTemplateMapper;
    @Autowired
    FileService fileService;
    @Autowired
    CJMessageService cjMessageService;
    
    @Resource(name="propertiesFactory")
    private Properties prop;
    
    /**
     * A/S문의 목록조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectAsInquiryList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        params.put("siteid", cs.getStr("siteid"));
        List<SOMap> resultList = asInquiryMapper.selectAsInquiryList(params);
        SOMap count = asInquiryMapper.selectAsInquiryListCount(params);
        
        result.put("list", resultList);
        result.put("count", count);
        return result;
    }
    
    /**
     * A/S문의 신청취소
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int updateAsStatusCancel(SOMap params) throws Exception {
    	int resultCnt = 0;
    	
    	try {
    		// 접수완료가 아닌 경우 신청취소 불가능
            params.put("siteid", cs.getStr("siteid"));
    		int nonReceiptCnt = asInquiryMapper.selectNonReceiptAsCount(params);
    		if (nonReceiptCnt > 0) {
    			throw new CustomException("'접수완료'상태의 게시물만 취소가 가능합니다. 확인후 진행해주세요.");
    		}
    		// A/S문의 신청취소
    		params.put("asstatus", CMConst.AS_CANCEL);
    		resultCnt = asInquiryMapper.updateAsStatus(params);
    		
    	} catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("A/S문의 신청취소시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        return resultCnt;
    }
    
    /**
     * A/S문의 파트너사 전송
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public int sendPartnerAsInquiry(SOMap params) throws Exception {
    	int resultCnt = 0;

    	try {
			// 접수완료가 아닌 경우 파트너사 전송 불가능
            params.put("siteid", cs.getStr("siteid"));
			int nonReceiptCnt = asInquiryMapper.selectNonReceiptAsCount(params);
			if (nonReceiptCnt > 0) {
				throw new CustomException("'접수완료'상태의 게시물만 전송이 가능합니다. 확인후 진행해주세요.");
			}
			// 파트너사 전송
    		params.put("ispass", "T");
    		resultCnt = asInquiryMapper.updateAsInquiry(params);
			
    	} catch(CustomException e){
            logger.error(e.getMessage());
            throw e;
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("A/S문의 파트너사 전송시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    	
        return resultCnt;
    }
    
    /**
     * A/S문의 상세조회
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    public SOMap selectAsInquiryDetail(SOMap params) throws Exception {
        SOMap result = new SOMap();
        
        // A/S문의 상세내역 조회
        params.put("siteid", cs.getStr("siteid"));
        SOMap asDetail = asInquiryMapper.selectAsInquiryDetail(params);
        
        // 답변템플릿 목록조회
        List<SOMap> repTemplateList = reqTemplateMapper.selectAllUseTemplateList(params);
        
        // 첨부파일 조회
        SOMap fileParams = new SOMap();
        fileParams.put("orgidx", params.get("asidx"));
        fileParams.put("imgtype", CMConst.IMG_TYPE_AS_INQUIRY);
        
        SOMap fileMap = fileService.selectFileList(fileParams);
        List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
        List<Map<String, Object>> imgFileList = new ArrayList<>();
        List<Map<String, Object>> videoFileList = new ArrayList<>();
        
        fileList.forEach(file -> {
        	if(CMConst.FILE_TYPE_IMG.equals(file.get("filetype"))) {
        		imgFileList.add(file);
        	} else{
        		videoFileList.add(file);
        	}
        });
        
        result.put("info", asDetail);
        result.put("reptemplatelist", repTemplateList);
        result.put("imgfilelist", imgFileList);
        result.put("videofilelist", videoFileList);
        
        return result;
    }
    
    /**
     * A/S문의 답변
     * @param params
     * @return SOMap
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int saveAsInquiry(SOMap params) throws Exception {
    	int resultCnt = 0;

    	try {
            params.put("siteid", cs.getStr("siteid"));
            params.put("asstatus", CMConst.AS_COMP);
            params.put("isreply", "T");
            
			// A/S문의 상세내용 수정
            resultCnt = asInquiryMapper.updateAsInquiry(params);
            
            // 답변완료시 KAKAO 메세지 전송 TODO: FRONT AS 도메인 확인 후 수정
            SOMap msgParams = new SOMap();
//            String frontAsUrl = String.format("%s/%s/%s", prop.getProperty("front.mobile.domain"), "mypage/as/detail", params.get("asidx"));
            msgParams.put("asidx", params.get("asidx"));
//            msgParams.put("url", frontAsUrl);
            msgParams.put("userno", params.get("userno"));
            msgParams.put("number", params.getStr("tel").replaceAll("-", ""));
            cjMessageService.sendAsComplete(msgParams);
            
    	} catch(Exception e){
            logger.error(e.getMessage());
            throw new CustomException("A/S문의 상세 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    	
        return resultCnt;
    }
}
