package com.dplot.admin.service.promotion;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.CouponMapper;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.GiftErpMapper;
import com.dplot.mapper.GiftMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminGiftServiceImpl extends MallBaseService implements AdminGiftService {

    @Autowired
    private GiftMapper giftMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;
    
    @Autowired
    private GiftErpMapper giftErpMapper;
    
    @Autowired
    private ERPService erpService;
    /**
     * 사은품 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public SOMap selectGiftList(SOMap params) throws Exception {
        SOMap result = new SOMap();
        params.put("siteid", cs.getStr("siteid"));

        // 페이징 처리
        int page = Integer.parseInt(params.get("page").toString());
        int pageCount = Integer.parseInt(params.get("pagecount").toString());
        int startPage = (page > 1) ? ((page - 1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);

        result.put("list", giftMapper.selectGiftList(params));
        result.put("statelist", giftMapper.selectGiftState(params));
        return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap updateGiftUse(SOMap params) throws Exception{
    	SOMap result = new SOMap();
    	
    	try {
    		int res = giftMapper.updateGiftUse(params);
    		
    		List<Map<String, Object>> list = params.getArrayList("reallist");
    		SOMap logParam = new SOMap();
    		logParam.put("siteid", cs.getStr("siteid"));
    		logParam.put("aud", "U");
    		for(Map map: list) {
    			logParam.put("idx", map.get("giftidx").toString());
    			erpService.insertGiftERPData(logParam);
    		}
    	} catch(Exception e) {
        	throw new CustomException("사은품 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
    	
    	return result;
    }
    
    @Override
    public List<Map<String, Object>> selectGiftExcelList(SOMap params) throws Exception{
    	params.put("siteid", cs.getStr("siteid"));
    	return giftMapper.selectGiftExcelList(params);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap saveGift(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception{
    	SOMap resultMap = new SOMap();
		params.put("siteid", cs.getStr("siteid"));
		params.put("reguserid", cs.getStr("authuserid"));
		try {
			giftMapper.insertGift(params);
			
			int giftidx = params.getInt("giftidx"); 
			
			if(uploadFile.containsKey("pcimgfile")){
				MultipartFile pcimgFile = uploadFile.get("pcimgfile");
				List<SOMap> s3Result = fileService.uploadGiftImage(pcimgFile, giftidx, CMConst.IMG_TYPE_GIFT_IMG_PC_B);
				resultMap.put(CMConst.IMG_TYPE_GIFT_IMG_PC_B, s3Result);
			}
			if(uploadFile.containsKey("mobileimgfile")){
				MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
				List<SOMap> s3Result = fileService.uploadGiftImage(mobileimgFile, giftidx, CMConst.IMG_TYPE_GIFT_IMG_MO_B);
				resultMap.put(CMConst.IMG_TYPE_GIFT_IMG_MO_B, s3Result);
			}
			
			giftErpMapper.insertGiftERP(params);
			
			SOMap logParam = new SOMap();
    		logParam.put("siteid", cs.getStr("siteid"));
    		logParam.put("aud", "A");
    		logParam.put("idx", params.getStr("giftidx"));
    		erpService.insertGiftERPData(logParam);
    		
		} catch(Exception e) {
        	throw new CustomException("사은품 등록시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
		
    	return resultMap;
    }
    
    @Override
    public SOMap selectGiftDetail(SOMap params) throws Exception {
    	SOMap map = new SOMap();
    	
    	SOMap detail = giftMapper.selectGiftDetail(params);
    	List<SOMap> erpGiftList = giftErpMapper.selectGiftERP(params);
    	map.put("erpgiftlist", erpGiftList);
    	map.put("info", detail);
    	
    	SOMap fileParams = new SOMap();
		String[] imgtypes = {"IGT012","IGT015"};
		fileParams.put("orgidx", params.get("giftidx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if("IGT012".equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if("IGT015".equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
		
		map.put("files", files);
		
    	return map;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SOMap modifyGift(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception{
    	SOMap resultMap = new SOMap();
    	int giftidx = params.getInt("giftidx");
		params.put("moduserid", cs.getStr("authuserid"));
    	
    	try {
    		List<Integer> removefile = params.getArrayList("removefile");
    		for (int idx : removefile) {
    			fileService.delete(idx);
    		}
    		
    		if(uploadFile.containsKey("pcimgfile")){
    			MultipartFile pcimgFile = uploadFile.get("pcimgfile");
    			List<SOMap> s3Result = fileService.uploadGiftImage(pcimgFile, giftidx, CMConst.IMG_TYPE_GIFT_IMG_PC_B);
    			resultMap.put(CMConst.IMG_TYPE_GIFT_IMG_PC_B, s3Result);
    		}
    		if(uploadFile.containsKey("mobileimgfile")){
    			MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
    			List<SOMap> s3Result = fileService.uploadGiftImage(mobileimgFile, giftidx, CMConst.IMG_TYPE_GIFT_IMG_MO_B);
    			resultMap.put(CMConst.IMG_TYPE_GIFT_IMG_MO_B, s3Result);
    		}
    		
    		// pc이미지 변경없이 mobile이미지만 변경했을 시 copy필요
    		if(params.containsKey("copyimgcheck")){
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
    			
    			List<SOMap> s3Result = fileService.uploadGiftImage(multipartFile, giftidx, CMConst.IMG_TYPE_GIFT_IMG_MO_B);
    			resultMap.put(CMConst.IMG_TYPE_GIFT_IMG_MO_B, s3Result);
    		}
    		
    		giftMapper.modifyGift(params);
    		
    		params.put("reguserid", cs.getStr("authuserid"));
    		giftErpMapper.deleteGiftERP(params);
    		giftErpMapper.insertGiftERP(params);
    		
    		SOMap logParam = new SOMap();
    		logParam.put("siteid", cs.getStr("siteid"));
    		logParam.put("aud", "U");
    		logParam.put("idx", params.getStr("giftidx"));
    		erpService.insertGiftERPData(logParam);
    	} catch(Exception e) {
        	throw new CustomException("사은품 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    	}
    	
    	return resultMap;
    }
}
