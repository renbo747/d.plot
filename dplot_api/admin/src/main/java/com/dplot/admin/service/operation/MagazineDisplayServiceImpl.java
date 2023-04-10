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
import com.dplot.mapper.MzexhibitMapper;
import com.dplot.mapper.MzexhibitwordMapper;

/**
 * @discription	: 전시관리 ServiceImpl
 * @fileName	: OperationNoticeServiceImpl.java
 * @author		: LKW
 * @date		: 2022.02.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.02.28	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class MagazineDisplayServiceImpl extends MallBaseService implements MagazineDisplayService {
    private static final Logger logger = LoggerFactory.getLogger(MagazineDisplayServiceImpl.class);
	
    @Autowired
    private MzexhibitMapper mzexhibitMapper;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private FileMapper fileMapper;
    
    @Autowired
    private MzexhibitwordMapper mzexhibitwordMapper;
    
    @Autowired
    private GoodsMapper goodsMapper;
    
	@Override
	public SOMap selectDisplayList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();

        result.put("list", mzexhibitMapper.selectMzexhibitList(params));
        result.put("state", mzexhibitMapper.selectMzexhibitUseCnt(params));
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap updateDisplay(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("moduserid", cs.getStr("authuserid"));

		try {
			if(params.containsKey("numsave")) {
				mzexhibitMapper.updateMzexhibitSortNum(params);
			} else {
				mzexhibitMapper.updateMzexhibit(params);												
			}
            params.put("siteid",cs.getStr("siteid"));
            mzexhibitMapper.updateAllSortNum(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진전시 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
	
	@Override
	public SOMap checkDisplay(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		
		SOMap result = new SOMap();
		
		int count = mzexhibitMapper.selectUseCount(params);
		
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
	
	@Override
	public List<Map<String, Object>> selectDisplayExcelList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		return mzexhibitMapper.selectMzexhibitExcelList(params);
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveDisplay(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));

		try {
			mzexhibitMapper.insertMzexhibit(params);
			mzexhibitwordMapper.insertMzexhibitword(params);
			if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("mzexidx"), CMConst.IMG_TYPE_MAGAZINE_EXH_PC);
                result.put(CMConst.IMG_TYPE_MAGAZINE_EXH_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("mzexidx"), CMConst.IMG_TYPE_MAGAZINE_EXH_MO);
                result.put(CMConst.IMG_TYPE_MAGAZINE_EXH_MO, s3Result);
            }
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("매거진전시 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap selectDisplayDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();

		params.put("siteid", cs.getStr("siteid"));
		
		SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_MAGAZINE_EXH_PC,CMConst.IMG_TYPE_MAGAZINE_EXH_MO};
		fileParams.put("orgidx", params.get("mzexidx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if(CMConst.IMG_TYPE_MAGAZINE_EXH_PC.equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if(CMConst.IMG_TYPE_MAGAZINE_EXH_MO.equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
		
		SOMap info = mzexhibitMapper.selectMzexhibitDetail(params);
		SOMap tempParams = new SOMap();
		List<String> goodsList = new ArrayList<>();
		goodsList.add(info.getStr("goodsno1"));
		goodsList.add(info.getStr("goodsno2"));
		tempParams.put("goodsnolist", goodsList);
		tempParams.put("siteid", cs.getStr("siteid"));
		tempParams.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_S); 	//이미지구분_상품_PC이미지_소
		
		List<SOMap> goodsInfoList = goodsMapper.selectCommonGoodsList(tempParams);
		
		goodsInfoList.forEach(m -> {
			if(info.getStr("goodsno1").equals(m.getStr("goodsno"))) {
				result.put("goods1", m);
			} else {
				result.put("goods2", m);
			}
		});
		
    	result.put("files", files);
		result.put("info", info);
		result.put("keywordlist", mzexhibitwordMapper.selectMzexhibitwordList(params));
			
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap modifyDisplay(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
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
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("mzexidx"), CMConst.IMG_TYPE_MAGAZINE_EXH_PC);
                resultMap.put(CMConst.IMG_TYPE_MAGAZINE_EXH_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("mzexidx"), CMConst.IMG_TYPE_MAGAZINE_EXH_MO);
                resultMap.put(CMConst.IMG_TYPE_MAGAZINE_EXH_MO, s3Result);
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

                SOMap s3Result = fileService.uploadImage(multipartFile, params.getInt("mzexidx"), CMConst.IMG_TYPE_MAGAZINE_EXH_MO);
            }
            
            if(params.containsKey("addkeywordlist")) {
            	SOMap tempMap = new SOMap();
            	tempMap.put("reguserid", cs.getStr("authuserid"));
        		tempMap.put("mzexidx", params.get("mzexidx"));
        		tempMap.put("keywordlist", params.get("addkeywordlist"));
        		
            	mzexhibitwordMapper.insertMzexhibitword(tempMap);
            }
            
            if(params.containsKey("deletekeywordlist")) {
            	mzexhibitwordMapper.deleteMzexhibitword(params);
            }
			
            mzexhibitMapper.updateMzexhibit(params);
            params.put("siteid",cs.getStr("siteid"));
            mzexhibitMapper.updateAllSortNum(params);
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("전시관리 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return resultMap;
	}
}
