package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.MainPopupMapper;

/**
 * @discription	: 메인팝업 ServiceImpl
 * @fileName	: OperationMainPopupServiceImpl.java
 * @author		: LKW
 * @date		: 2022.03.03
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.03	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class OperationMainPopupServiceImpl extends MallBaseService implements OperationMainPopupService {
    private static final Logger logger = LoggerFactory.getLogger(OperationMainPopupServiceImpl.class);
    
    @Autowired
    private MainPopupMapper mainPopupMapper;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private FileMapper fileMapper;
    
    @Override
    public SOMap selectMainPopupList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));

		SOMap result = new SOMap();
		params.put("pcimgtype", CMConst.IMG_TYPE_MAIN_POPUP_PC);
		params.put("moimgtype", CMConst.IMG_TYPE_MAIN_POPUP_MO);

        result.put("list", mainPopupMapper.selectMainPopupList(params));
        result.put("state", mainPopupMapper.selectMainPopupListCnt(params));
        
		return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public SOMap updateMainPopup(SOMap params) throws Exception {
    	SOMap result = new SOMap();
		params.put("moduserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));
		
		try {
			if(params.containsKey("numsave")) {
				mainPopupMapper.updateMainPopupSortNum(params);
			} else {
				mainPopupMapper.updateMainPopup(params);												
			}
			mainPopupMapper.updateAllSortNum(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("메인배너 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
    }
	
	@Override
	public List<Map<String, Object>> selectMainPopupExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return mainPopupMapper.selectMainPopupExcelList(params);
	}
    
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveMainPopup(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));
		try {
			mainPopupMapper.insertMainPopup(params);
			// 이미지 타입 변경 105 106으로
			if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("popidx"), CMConst.IMG_TYPE_MAIN_POPUP_PC);
                result.put(CMConst.IMG_TYPE_MAIN_POPUP_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("popidx"), CMConst.IMG_TYPE_MAIN_POPUP_MO);
                result.put(CMConst.IMG_TYPE_MAIN_POPUP_MO, s3Result);
            }
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("메인팝업 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
	
	@Override
	public SOMap selectMainPopupDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("siteid", cs.getStr("siteid"));
		
		// 파일타입 변경 필요 105, 106으로
		SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_MAIN_POPUP_PC,CMConst.IMG_TYPE_MAIN_POPUP_MO};
		fileParams.put("orgidx", params.get("popidx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if(CMConst.IMG_TYPE_MAIN_POPUP_PC.equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if(CMConst.IMG_TYPE_MAIN_POPUP_MO.equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
    	result.put("files", files);
		result.put("info",mainPopupMapper.selectMainPopupDetail(params));
		
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap modifyMainPopup(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap resultMap = new SOMap();
		
		params.put("moduserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));
		
		try {
			// 파일 삭제
    		List<Integer> deletefile = params.getArrayList("deletefile");
            for (int i = 0; i < deletefile.size(); i++) {
                int idx = deletefile.get(i);
                fileService.delete(idx);
            }
            
            // 파일 저장
            if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("popidx"), CMConst.IMG_TYPE_MAIN_POPUP_PC);
                resultMap.put(CMConst.IMG_TYPE_MAIN_POPUP_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("popidx"), CMConst.IMG_TYPE_MAIN_POPUP_MO);
                resultMap.put(CMConst.IMG_TYPE_MAIN_POPUP_MO, s3Result);
            }
            
            // pc이미지 변경없이 mobile이미지만 변경했을 시 copy필요
            if(params.containsKey("copycheck")) {
            	SOMap temp = new SOMap();
            	int idx = params.getInt("copyidx");
            	temp.put("idx", idx);
            	SOMap result = fileMapper.selectFile(temp);
            	if(result == null) {
        			throw new  Exception("파일이 존재하지 않습니다");
        		}

        		byte[] byteImage = fileService.get(idx);
        		
        		if(byteImage == null || byteImage.length == 0) {
        			throw new  Exception("파일이 존재하지 않습니다");
        		}
        		
        		String contentType = new Tika().detect(byteImage);
        		MultipartFile multipartFile = new MockMultipartFile(result.getDbStr("imgfname"), result.getDbStr("imgforiname"), contentType, byteImage);

                SOMap s3Result = fileService.uploadImage(multipartFile, params.getInt("popidx"), CMConst.IMG_TYPE_MAIN_POPUP_MO);
            }
            mainPopupMapper.updateMainPopup(params);
            
            mainPopupMapper.updateAllSortNum(params);
		} catch(Exception e){
        	throw new CustomException("메인배너 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
		
		
		return resultMap;
	}
	
	@Override
	public SOMap checkMainPopup(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		SOMap result = new SOMap();
		
		int count = mainPopupMapper.checkMainPopupCnt(params);
		
		// 1개 이상일 때 출력
		String overmsg = params.getStr("overmsg");
		// 0개일 때 출력
		String zeromsg = params.getStr("zeromsg");
		
		if(count > 0){
			result.put("msg", overmsg);
		} else {
			result.put("msg", zeromsg + " 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
		}
		
		return result;
	}
}
