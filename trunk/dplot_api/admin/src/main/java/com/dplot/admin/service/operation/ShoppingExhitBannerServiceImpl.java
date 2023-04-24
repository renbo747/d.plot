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
import com.dplot.mapper.BannerMapper;
import com.dplot.mapper.FileMapper;

/**
 * @discription	: 전시영역배너 ServiceImpl
 * @fileName	: ShoppingExhitBannerServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.05
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.05	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class ShoppingExhitBannerServiceImpl extends MallBaseService implements ShoppingExhitBannerService {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingExhitBannerServiceImpl.class);
    
    @Autowired
    private BannerMapper bannerMapper;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private FileMapper fileMapper;
    
    @Override
    public SOMap selectExhitBannerList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));

		SOMap result = new SOMap();
		params.put("bntype", "BNT004");
		params.put("pcimgtype", CMConst.IMG_TYPE_SHOPPING_EXBN_PC);
		params.put("moimgtype", CMConst.IMG_TYPE_SHOPPING_EXBN_MO);
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        result.put("list", bannerMapper.selectMainBannerList(params));
        result.put("state", bannerMapper.selectMainBannerListCnt(params));
        
		return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
    public SOMap updateExhitBanner(SOMap params) throws Exception {
    	SOMap result = new SOMap();
		params.put("moduserid", cs.getStr("authuserid"));
		
		try {
            params.put("bntype", "BNT004");
			bannerMapper.updateMainBanner(params);	
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("메인배너 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
    }
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveExhitBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap result = new SOMap();
		params.put("reguserid", cs.getStr("authuserid"));
		params.put("siteid", cs.getStr("siteid"));
        params.put("bntype", "BNT004");

		try {
			bannerMapper.insertMainBanner(params);
			// 이미지 타입 변경
			if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("banidx"), CMConst.IMG_TYPE_SHOPPING_EXBN_PC);
                result.put(CMConst.IMG_TYPE_SHOPPING_EXBN_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("banidx"), CMConst.IMG_TYPE_SHOPPING_EXBN_MO);
                result.put(CMConst.IMG_TYPE_SHOPPING_EXBN_MO, s3Result);
            }
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("메인배너 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}	
	
	@Override
	public SOMap selectExhitBannerDetail(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		params.put("siteid", cs.getStr("siteid"));
		
		// 파일타입 변경 필요 105, 106으로
		SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_SHOPPING_EXBN_PC,CMConst.IMG_TYPE_SHOPPING_EXBN_MO};
		fileParams.put("orgidx", params.get("banidx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if(CMConst.IMG_TYPE_SHOPPING_EXBN_PC.equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if(CMConst.IMG_TYPE_SHOPPING_EXBN_MO.equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
    	result.put("files", files);
		result.put("info",bannerMapper. selectMainBannerDetail(params));
		
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap modifyExhitBanner(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap resultMap = new SOMap();
		
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
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("banidx"), CMConst.IMG_TYPE_SHOPPING_EXBN_PC);
                resultMap.put(CMConst.IMG_TYPE_SHOPPING_EXBN_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("banidx"), CMConst.IMG_TYPE_SHOPPING_EXBN_MO);
                resultMap.put(CMConst.IMG_TYPE_SHOPPING_EXBN_MO, s3Result);
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

                SOMap s3Result = fileService.uploadImage(multipartFile, params.getInt("banidx"), CMConst.IMG_TYPE_SHOPPING_EXBN_MO);
            }
            bannerMapper.updateMainBanner(params);
		} catch(Exception e){
        	throw new CustomException("메인배너 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
		
		
		return resultMap;
	}
	
	@Override
	public SOMap checkExhitBanner(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
		params.put("bntype", "BNT004");
		SOMap result = new SOMap();
		
		result.put("msg", params.getStr("overmsg"));
		if("T".equals(params.getStr("isdisplay"))) {
			if(params.containsKey("idxlist")) {
				int check = bannerMapper.checkBannerCntList(params);
				if(check > 1) {
					result.put("msg", "같은 위치 및 기간동안 노출중인 게시물이 1건 이상 존재합니다. 진행 하시겠습니까?");
				}
			} else {
				params.put("maxcheck", "Y");
				int check =  bannerMapper.checkBannerCnt(params);
				if(check >= 1) {
					result.put("msg", "같은 위치 및 기간동안 노출중인 게시물이 1건 이상 존재합니다. 진행 하시겠습니까?");
				}
			}
		} else {
			int check =  bannerMapper.checkBannerCnt(params);
			if(check == 0) {
				result.put("msg", params.getStr("zeromsg")+" 처리 시, 게시 가능한 게시물이 없으므로 영역이 숨김처리 됩니다. 진행 하시겠습니까?");
			}
		}
		
		return result;
	}
}
