package com.dplot.admin.service.operation;

import java.util.ArrayList;
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

import com.dplot.admin.service.promotion.AdminCouponServiceImpl;
import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BoardPostMapper;
import com.dplot.mapper.ComOrderGoodsMapper;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.GoodsReviewMapper;
import com.dplot.mapper.MzKeywordListMapper;
import com.dplot.mapper.MzKeywordMapper;
import com.dplot.mapper.ReviewNotiMapper;
import com.dplot.util.ServletRequestInfoUtil;

/**
 * @discription	: 매거진키워드 ServiceImpl
 * @fileName	: MagazineKeywordServiceImpl.java
 * @author		: LKW
 * @date		: 2022.03.31
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.31	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class MagazineKeywordServiceImpl extends MallBaseService implements MagazineKeywordService {
    private static final Logger logger = LoggerFactory.getLogger(MagazineKeywordServiceImpl.class);

    @Autowired
    private FileService fileService;
    
    @Autowired
    private MzKeywordMapper mzKeywordMapper;
    
    @Autowired
    private MzKeywordListMapper mzKeywordListMapper;
    
    @Autowired
    private FileMapper fileMapper;
    
	@Override
	public SOMap selectMzKeywordList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
        SOMap result = new SOMap();

        result.put("list", mzKeywordMapper.selectMzKeywordList(params));
        result.put("state", mzKeywordMapper.selectMzKeywordCnt(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveMzKeyword(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));

		try {
			mzKeywordMapper.insertMzKeyword(params);
			// 이미지 타입 변경 
			if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("mzwdidx"), CMConst.IMG_TYPE_MAGAZINE_KWD_PC);
                result.put(CMConst.IMG_TYPE_MAGAZINE_KWD_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("mzwdidx"), CMConst.IMG_TYPE_MAGAZINE_KWD_MO);
                result.put(CMConst.IMG_TYPE_MAGAZINE_KWD_MO, s3Result);
            }
            
            // 키워드 리스트 추가
            if(params.containsKey("keywordlist")) {
            	mzKeywordListMapper.insertMzKeyword(params);            	
            }
            
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진키워드 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectMzKeywordExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return mzKeywordMapper.selectMzKeywordExcelList(params);
	}
	
	@Override
	public SOMap selectMzKeywordDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap reviewInfo = mzKeywordMapper.selectMzKeywordDetail(params);

		// 이미지타입 변경 필요
    	SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_MAGAZINE_KWD_PC,CMConst.IMG_TYPE_MAGAZINE_KWD_MO};
		fileParams.put("orgidx", params.get("mzwdidx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if(CMConst.IMG_TYPE_MAGAZINE_KWD_PC.equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if(CMConst.IMG_TYPE_MAGAZINE_KWD_MO.equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
		
		// 키워드 리스트 조회
		result.put("keywordlist", mzKeywordListMapper.selectMzKeywordList(params));
		
    	result.put("files", files);
		result.put("info", reviewInfo);
			
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap modifyMzKeyword(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap resultMap = new SOMap();
    	params.put("siteid", cs.getStr("siteid"));
		params.put("moduserid", cs.getStr("authuserid"));
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
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("mzwdidx"), CMConst.IMG_TYPE_MAGAZINE_KWD_PC);
                resultMap.put(CMConst.IMG_TYPE_MAGAZINE_KWD_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("mzwdidx"), CMConst.IMG_TYPE_MAGAZINE_KWD_MO);
                resultMap.put(CMConst.IMG_TYPE_MAGAZINE_KWD_MO, s3Result);
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

                SOMap s3Result = fileService.uploadImage(multipartFile, params.getInt("mzwdidx"), CMConst.IMG_TYPE_MAGAZINE_KWD_MO);
            }
			mzKeywordMapper.updateMzKeyword(params);
			
			// 키워드 리스트 수정
			
			mzKeywordListMapper.deleteMzKeyword(params);
			if(params.containsKey("keywordlist")) {
				mzKeywordListMapper.insertMzKeyword(params);				
			}
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진키워드 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return resultMap;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateMzKeyword(SOMap params) throws Exception {
		SOMap result = new SOMap();
    	params.put("siteid", cs.getStr("siteid"));
		params.put("moduserid", cs.getStr("authuserid"));
		
		try {
			mzKeywordMapper.updateMzKeyword(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진키워드 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap selectMzKeywordCheck(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap result = new SOMap();
		
		result.put("msg", params.getStr("overmsg"));
		if("T".equals(params.getStr("isdisplay"))) {
			if(params.containsKey("idxlist")) {
				int check = mzKeywordMapper.selectDisplayCheckCntList(params);
				if(check > 1) {
					result.put("msg", "동일 기간에 노출중인 키워드영역이 1건 이상 존재합니다. 진행 하시겠습니까?");
				}
			} else {
				params.put("maxcheck", "Y");
				int check =  mzKeywordMapper.selectDisplayCheckCnt(params);
				if(check >= 1) {
					result.put("msg", "동일 기간에 노출중인 키워드영역이 1건 이상 존재합니다. 진행 하시겠습니까?");
				}
			}
		} else {
			int check =  mzKeywordMapper.selectDisplayCheckCnt(params);
			if(check == 0) {
				result.put("msg", params.getStr("zeromsg")+" 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
			}
		}
		
		return result;
	}
}
