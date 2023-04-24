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
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.MzCateMapper;
import com.dplot.mapper.MzTrendKeywordMapper;
import com.dplot.mapper.MzTrendMapper;
import com.dplot.mapper.MzexhibitMapper;
import com.dplot.mapper.MzexhibitwordMapper;

/**
 * @discription	: 매거진트렌드 ServiceImpl
 * @fileName	: MagazineTrendServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.01
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.01	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class MagazineTrendServiceImpl extends MallBaseService implements MagazineTrendService {
    private static final Logger logger = LoggerFactory.getLogger(MagazineTrendServiceImpl.class);
	
    @Autowired
    private MzTrendMapper mzTrendMapper;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private FileMapper fileMapper;
    
    @Autowired
    private MzCateMapper mzCateMapper;
    
    @Autowired
    private MzTrendKeywordMapper mzTrendKeywordMapper;
    
	@Override
	public SOMap selectTrendList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();

        result.put("list", mzTrendMapper.selectTrendList(params));
        result.put("state", mzTrendMapper.selectTrendShowCnt(params));
        
		return result;
	}
	
	@Override
	public SOMap selectAllMzCategoryList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();

        result.put("list", mzCateMapper.selectAllMzCateList(params));
        
		return result;
	}
	
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateTrend(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("moduserid", cs.getStr("authuserid"));

		try {
			if(params.containsKey("numsave")) {
				mzTrendMapper.updateTrendSortNum(params);
			} else {
				mzTrendMapper.updateTrend(params);												
			}
            params.put("siteid",cs.getStr("siteid"));
            mzTrendMapper.updateAllSortNum(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진트렌드 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap checkTrend(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap result = new SOMap();
		
		result.put("msg", params.getStr("overmsg"));

		// 0개일 때 출력
		String zeromsg = params.getStr("zeromsg");
		
		if("T".equals(params.getStr("isdisplay"))) {
			if(params.containsKey("idxlist")) {
				int check = mzTrendMapper.selectDispShopCntList(params);
				if(check > 7) {
					result.put("msg", "동일 기간에 노출중인 영역이 7건 이상 존재합니다. 진행 하시겠습니까?");
				}
			} else {
				if("T".equals(params.getStr("isdispshop"))) {
					int check = mzTrendMapper.selectDispShopCnt(params);
					if(check >= 7) {
						result.put("msg", "동일 기간에 노출중인 영역이 7건 이상 존재합니다. 진행 하시겠습니까?");
					}
				} else {
					int check = mzTrendMapper.checkCondition(params).getInt("maincnt");
					if(check == 0) {
						result.put("msg", "수정 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김 처리 됩니다. 진행 하시겠습니까?");
					}
				}
			}
		} else {
			int check = mzTrendMapper.checkCondition(params).getInt("count");
			int check2 = mzTrendMapper.checkCondition(params).getInt("maincnt");
			if(check == 0) {
				result.put("msg", "수정 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김 처리 됩니다. 진행 하시겠습니까?");
			} else if(check2 == 0){
				result.put("msg", "수정 처리 시, 게시 가능한 쇼핑메인 게시물이 없으므로 영역이 숨김 처리 됩니다. 진행 하시겠습니까?");
			}
		}
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectTrendExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return mzTrendMapper.selectTrendExcelList(params);
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveTrend(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));

		try {
			mzTrendMapper.insertTrend(params);
			if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_PC);
                result.put(CMConst.IMG_TYPE_MAGAZINE_TRD_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_MO);
                result.put(CMConst.IMG_TYPE_MAGAZINE_TRD_MO, s3Result);
            }
            if(uploadFile.containsKey("spcimgfile")){
            	MultipartFile pcimgFile = uploadFile.get("spcimgfile");
            	SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_PC);
            	result.put(CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_PC, s3Result);
            }
            if(uploadFile.containsKey("smobileimgfile")){
            	MultipartFile mobileimgFile = uploadFile.get("smobileimgfile");
            	SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_MO);
            	result.put(CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_MO, s3Result);
            }
            
            if(params.containsKey("keywordlist")) {
            	mzTrendKeywordMapper.insertTrendKeyword(params);            	
            }
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진트렌드 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap selectTrendDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();

		params.put("siteid", cs.getStr("siteid"));
		
		SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_MAGAZINE_TRD_PC,CMConst.IMG_TYPE_MAGAZINE_TRD_MO, CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_PC, CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_MO};
		fileParams.put("orgidx", params.get("tridx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if(CMConst.IMG_TYPE_MAGAZINE_TRD_PC.equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if(CMConst.IMG_TYPE_MAGAZINE_TRD_MO.equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}else if(CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_PC.equals(m.get("imgtype"))){
				files.put("spcimgfile", m);
			}else if(CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_MO.equals(m.get("imgtype"))){
				files.put("smobileimgfile", m);
			}
		});
		
		SOMap info = mzTrendMapper.selectTrendDetail(params);
		
    	result.put("files", files);
		result.put("info", info);
		result.put("categorylist",  mzCateMapper.selectAllMzCateList(params));
		result.put("keywordlist", mzTrendKeywordMapper.selectTrendKeywordList(params));
			
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap modifyTrend(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap resultMap = new SOMap();
		
		params.put("moduserid", cs.getStr("authuserid"));
		
		try{
			// 파일 삭제
    		List<Integer> deletefile = params.getArrayList("deletefile");
            for (int i = 0; i < deletefile.size(); i++) {
                int idx = deletefile.get(i);
                fileService.delete(idx);
            }
            
            // 파일 저장
            if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_PC);
                resultMap.put(CMConst.IMG_TYPE_MAGAZINE_TRD_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_MO);
                resultMap.put(CMConst.IMG_TYPE_MAGAZINE_TRD_MO, s3Result);
            }
            if(uploadFile.containsKey("spcimgfile")){
            	MultipartFile pcimgFile = uploadFile.get("spcimgfile");
            	SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_PC);
            	resultMap.put(CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_PC, s3Result);
            }
            if(uploadFile.containsKey("smobileimgfile")){
            	MultipartFile mobileimgFile = uploadFile.get("smobileimgfile");
            	SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_MO);
            	resultMap.put(CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_MO, s3Result);
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

                SOMap s3Result = fileService.uploadImage(multipartFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_MO);
            }
            if(params.containsKey("scopycheck")) {
            	SOMap temp = new SOMap();
            	int idx = params.getInt("scopyidx");
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

                SOMap s3Result = fileService.uploadImage(multipartFile, params.getInt("tridx"), CMConst.IMG_TYPE_MAGAZINE_TRD_SHOP_MO);
            }
			
            mzTrendMapper.updateTrend(params);
            params.put("siteid",cs.getStr("siteid"));
            mzTrendMapper.updateAllSortNum(params);
            
            if(params.containsKey("deletekeywordlist")) {
            	mzTrendKeywordMapper.deleteTrendKeyword(params);
            }
            if(params.containsKey("addkeywordlist")) {
            	params.put("keywordlist", params.get("addkeywordlist"));
            	mzTrendKeywordMapper.insertTrendKeyword(params);
            }
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진트렌드 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return resultMap;
	}
}
