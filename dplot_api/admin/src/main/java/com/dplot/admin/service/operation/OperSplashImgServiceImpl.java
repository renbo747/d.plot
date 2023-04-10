package com.dplot.admin.service.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.SplashImgMapper;
import com.dplot.mapper.SubscribeMapper;

/**
 * @discription	: 스플래시이미지 ServiceImpl
 * @fileName	: OperSplashImgServiceImpl.java
 * @author		: LKW
 * @date		: 2022.04.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.08	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class OperSplashImgServiceImpl extends MallBaseService implements OperSplashImgService {
    private static final Logger logger = LoggerFactory.getLogger(OperSplashImgServiceImpl.class);
    
    @Autowired
    private SplashImgMapper splashImgMapper;
    
    @Autowired
    private FileService fileService;
    
	@Override
	public SOMap selectSplashImgList(SOMap params) throws Exception {
		params.put("siteid", cs.getStr("siteid"));
        
        SOMap result = new SOMap();
        
        List<SOMap> list = splashImgMapper.selectSplashImgList(params);

		SOMap fileParams = new SOMap();
		String[] imgtypes = {CMConst.IMG_TYPE_OPER_SPLASH};
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		List<Map<String, Object>> files = new ArrayList<>();
		
		list.forEach(m -> {
			String idx = m.getStr("splidx");
			fileList.forEach(file -> {
				if(idx.equals(file.get("orgidx").toString())) {
					files.add(file);
				}
			});
		});
		
        result.put("list", list);
        result.put("filelist", files);
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, CustomException.class})
	public SOMap saveSplashImg(SOMap params, Map<String, MultipartFile> files) throws Exception {
		SOMap result = new SOMap();
		params.put("siteid",cs.getStr("siteid"));		
		params.put("reguserid", cs.getStr("authuserid"));

		try {
			List<Map<String, Object>> allList = params.getArrayList("list");
			List<Map<String, Object>> addList = allList.stream().filter(m -> !m.containsKey("splidx")).collect(Collectors.toList());
			List<Map<String, Object>> updateList = allList.stream().filter(m -> m.containsKey("splidx")).collect(Collectors.toList());
			
			if(addList.size() > 0) {
				Collections.reverse(addList);
				params.put("addlist", addList);
				splashImgMapper.insertSplashImg(params);
			}
			
			if(updateList.size() > 0) {
				params.put("updatelist", updateList);
				splashImgMapper.updateSplashImg(params);
			}
			
			// 삭제할 스플래시 이미지가 있을 시 삭제
			if(params.containsKey("removelist")) {
				splashImgMapper.deleteSplashImg(params);
			}
			
			// 파일 삭제
    		List<Integer> deletefile = params.getArrayList("removefilelist");
            for (int i = 0; i < deletefile.size(); i++) {
                int idx = deletefile.get(i);
                fileService.delete(idx);
            }
            
            // 파일 저장
            List<SOMap> list = splashImgMapper.selectSplashImgList(params);
            for(int i=0; i<list.size(); i++) {
            	if(files.containsKey("file"+i)){
            		int idx = list.get(i).getInt("splidx");
            		MultipartFile pcimgFile = files.get("file"+i);
            		SOMap s3Result = fileService.uploadImage(pcimgFile, idx, CMConst.IMG_TYPE_OPER_SPLASH);
            	}
            }
		} catch(Exception e){
	        logger.error(e.getMessage());
	        throw new CustomException("스플래시이미지 수정시 오류가 발생하였습니다. 관리자에게 문의해주세요.");
	    }
		
		return result;
	}
}
