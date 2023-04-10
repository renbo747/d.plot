package com.dplot.admin.service.goods;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.MallConfigService;
import com.dplot.common.service.PartnershipService;
import com.dplot.common.service.util.FileService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.BrandContentsMapper;
import com.dplot.mapper.BrandMapper;
import com.dplot.mapper.CategoryMapper;
import com.dplot.mapper.ConfigDealerMapper;
import com.dplot.mapper.DealerChargeMapper;
import com.dplot.mapper.DealerMapper;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.util.CryptHash;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminBrandManageServiceImpl implements AdminBrandManageService {

	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private DealerMapper dealerMapper;
	
    @Autowired
    private MallConfigService cs;

    @Autowired
    private CommonService commonService;

	@Autowired
    private FileService fileService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private BrandContentsMapper brandContentsMapper;
	
    @Override
    public SOMap selectBrandList(SOMap param) throws Exception {
        param.put("siteid", cs.getStr("siteid"));

        if(param.containsKey("page")) {
        	int page = param.getInt("page");
        	int pageCount = param.getInt("pagecount");
        	int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        	param.put("startpage", startPage);
        	param.put("endpage", pageCount);        	
        }
        param.put("idx", 0);
        
        SOMap result = new SOMap();
        List<SOMap> brandList = brandMapper.selectBrandList(param);
        
        result.put("list", brandList);
        result.put("listCount", brandMapper.selectBrandListCount(param));
        result.put("categorylist", categoryMapper.selectCategoryList(param));

        return result;
    }
    
    @Override
    public SOMap selectPartnersList(SOMap params) throws Exception {
        params.put("siteid", cs.getStr("siteid"));
    	
        SOMap result = new SOMap();
        result.put("partnerList", userMapper.selectPartnersList(params));
        
    	return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap insertBrand(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception{
    	SOMap resultMap = new SOMap();
        params.put("siteid", cs.getStr("siteid"));
        params.put("authuserid", cs.getStr("authuserid"));
        // 공백인 경우 null로 치환
        try{
        	params.put("frstename", params.getStr("frstename").toUpperCase());
        	params.put("frstname", params.getStr("frstname").toUpperCase());
        	if(params.getInt("cateidx") == 0) {
        		params.put("cateidx", null);
        	}
            brandMapper.insertBrand(params);
            
            // 파일 저장
            if(uploadFile.containsKey("brandimgfile")){
                MultipartFile pcimgFile = uploadFile.get("brandimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("idx"), CMConst.IMG_TYPE_BRAND_LOGO);
                resultMap.put(CMConst.IMG_TYPE_BRAND_LOGO, s3Result);
            }
            if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("idx"), CMConst.IMG_TYPE_BRAND_IMG_PC);
                resultMap.put(CMConst.IMG_TYPE_BRAND_IMG_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("idx"), CMConst.IMG_TYPE_BRAND_IMG_MO);
                resultMap.put(CMConst.IMG_TYPE_BRAND_IMG_MO, s3Result);
            }
            
            if(params.containsKey("contentslist")) {
            	brandContentsMapper.insertBrandContents(params);
            }
        }catch(Exception e){
        	throw new CustomException("브랜드 저장시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
    	return resultMap;
    }
    
    @Override
    public SOMap selectBrandDetail(SOMap params) throws Exception{
    	SOMap map = new SOMap();
    	params.put("siteid", cs.getStr("siteid"));
    	
    	SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_BRAND_LOGO,CMConst.IMG_TYPE_BRAND_IMG_PC,CMConst.IMG_TYPE_BRAND_IMG_MO};
		fileParams.put("orgidx", params.get("idx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if(CMConst.IMG_TYPE_BRAND_LOGO.equals(m.get("imgtype"))){
				files.put("brandimgfile", m);
			}else if(CMConst.IMG_TYPE_BRAND_IMG_PC.equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if(CMConst.IMG_TYPE_BRAND_IMG_MO.equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
    	map.put("files", files);
    	map.put("info", brandMapper.selectBrandDetail(params));
    	map.put("contentslist", brandContentsMapper.selectBrandContentsList(params));
    	return map;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap updateBrand(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
    	SOMap resultMap = new SOMap();
        params.put("moduserid", cs.getStr("authuserid"));
        params.put("authuserid", cs.getStr("authuserid"));
    	try{
            // 파일 삭제
    		List<Integer> deletefile = params.getArrayList("deletefile");
            for (int i = 0; i < deletefile.size(); i++) {
                int idx = deletefile.get(i);
                fileService.delete(idx);
            }
            // 파일 저장
            if(uploadFile.containsKey("brandimgfile")){
                MultipartFile pcimgFile = uploadFile.get("brandimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("idx"), CMConst.IMG_TYPE_BRAND_LOGO);
                resultMap.put(CMConst.IMG_TYPE_BRAND_LOGO, s3Result);
      	  	  params.put("imgpath", s3Result.getStr("fullpath"));
      	  	  params.put("imgfname", s3Result.getStr("imgfname"));
      	  	  params.put("imgforiname", s3Result.getStr("imgforiname"));
            }
            // 파일 저장
            if(uploadFile.containsKey("pcimgfile")){
                MultipartFile pcimgFile = uploadFile.get("pcimgfile");
                SOMap s3Result = fileService.uploadImage(pcimgFile, params.getInt("idx"), CMConst.IMG_TYPE_BRAND_IMG_PC);
                resultMap.put(CMConst.IMG_TYPE_BRAND_IMG_PC, s3Result);
            }
            if(uploadFile.containsKey("mobileimgfile")){
                MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
                SOMap s3Result = fileService.uploadImage(mobileimgFile, params.getInt("idx"), CMConst.IMG_TYPE_BRAND_IMG_MO);
                resultMap.put(CMConst.IMG_TYPE_BRAND_IMG_MO, s3Result);
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

                SOMap s3Result = fileService.uploadImage(multipartFile, params.getInt("idx"), CMConst.IMG_TYPE_BRAND_IMG_MO);
            }

        	params.put("frstename", params.getStr("frstename").toUpperCase());
        	params.put("frstname", params.getStr("frstname").toUpperCase());
        	if(params.getInt("cateidx") == 0) {
        		params.put("cateidx", null);
        	}
            brandMapper.updateBrand(params);
            
            brandContentsMapper.deleteBrandContents(params);
            if(params.containsKey("contentslist")) {
            	brandContentsMapper.insertBrandContents(params);            	
            }
    	}catch(Exception e){
        	throw new CustomException("브랜드 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
        
    	return resultMap;
    }
}