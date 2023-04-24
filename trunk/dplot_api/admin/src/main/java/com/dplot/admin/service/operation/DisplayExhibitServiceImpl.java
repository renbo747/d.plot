package com.dplot.admin.service.operation;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.ExhibitionMapper;

/**
 * @discription	: 기획전 ServiceImpl
 * @fileName	: DisplayExhibitServiceImpl.java
 * @author		: LKW
 * @date		: 2021.11.26
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.26	LKW			최초생성
 * -----------------------------------------------------------------
 */
@Service
public class DisplayExhibitServiceImpl extends MallBaseService implements DisplayExhibitService {
	@Autowired
	ExhibitionMapper exhibitionMapper;
	
	@Autowired
    private FileService fileService;
	
	/**
	 * 기본정보 - 기획전 전체 조회
	 * @return List<SOMap>
	 * @throws Exception
	 */
	@Override
	public SOMap selectExhibitList(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		int page = params.getInt("page");
        int pageCount = params.getInt("pagecount");
        int startPage = (page > 1) ? ((page-1) * pageCount) : 0;
        params.put("startpage", startPage);
        params.put("endpage", pageCount);
        
        SOMap result = new SOMap();
        List<SOMap> list = exhibitionMapper.selectExhibitList(params);
        SOMap useMap = exhibitionMapper.selectExhibitUseCount(params);

        result.put("list", list);
        result.put("listCount", (useMap != null) ? useMap.getInt("total_cnt") : 0);
        result.put("useCnt", (useMap != null) ? useMap.getInt("use_cnt") : 0);
        result.put("trashCnt", (useMap != null) ? useMap.getInt("trash_cnt") : 0);
        
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public int updateExhibit(SOMap params) throws Exception {
		int res = 0;
        if("istrash".equals(params.getStr("type"))){
        	res = exhibitionMapper.updateExhibitUse(params);
        }else{
        	
        }
        
		return res;
	}
	
	@Override
	public SOMap checkExhibitMain(SOMap params) throws Exception {
		SOMap result = new SOMap();
		
		int check = exhibitionMapper.checkExhibitMain(params);
		
		if(check > 0){
			result.put("ckeck", false);
		}else{
			result.put("check", true);
		}
		
		return result;
	}
	
	@Override
	public List<Map<String, Object>> selectExhibitExcelList(SOMap params) throws Exception{
		params.put("siteid", cs.getStr("siteid"));
		List<Map<String, Object>> exhibitList = exhibitionMapper.selectExhibitExcelList(params);
		return exhibitList;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap saveExhibit(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap resultMap = new SOMap();
		params.put("siteid", cs.getStr("siteid"));
		exhibitionMapper.insertExhibit(params);
		int exhibitidx = params.getInt("exhibitidx"); 
		
		if(uploadFile.containsKey("pcimgFile")){
            MultipartFile pcimgFile = uploadFile.get("pcimgFile");
            SOMap s3Result = fileService.uploadImage(pcimgFile, exhibitidx, CMConst.IMG_TYPE_EXP_PC);
            resultMap.put(CMConst.IMG_TYPE_EXP_PC, s3Result);
        }
        if(uploadFile.containsKey("mobileimgFile")){
            MultipartFile mobileimgFile = uploadFile.get("mobileimgFile");
            SOMap s3Result = fileService.uploadImage(mobileimgFile, exhibitidx, CMConst.IMG_TYPE_EXP_MO);
            resultMap.put(CMConst.IMG_TYPE_EXP_MO, s3Result);
        }
		
		List<Map<String, Object>> themeList = params.getArrayList("themelist");
		themeList.forEach(info -> {
			info.put("reguserid", params.get("reguserid"));
			info.put("istrash", params.get("istrash"));
			info.put("exhibitidx", exhibitidx);
			exhibitionMapper.insertExhibitTheme(info);
			info.put("exhthemeidx", info.get("exhthemeidx"));
			List<Map<String,Object>> temp = (List<Map<String, Object>>) info.get("goodsList");
			if(temp.size() != 0){
				exhibitionMapper.insertExhThemeGoods(info);
			}
		});
		return resultMap;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap updateExhibit(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception{
		SOMap resultMap = new SOMap();
		int exhibitidx = params.getInt("exhibitidx"); 
		
		// 파일 삭제
        List<Integer> deletefile = params.getArrayList("deletefile");
        for (int i = 0; i < deletefile.size(); i++) {
            int idx = deletefile.get(i);
            fileService.delete(idx);
        }
        
        // 파일 저장
        if(uploadFile.containsKey("pcimgfile")){
            MultipartFile pcimgFile = uploadFile.get("pcimgfile");
            SOMap s3Result = fileService.uploadImage(pcimgFile, exhibitidx, CMConst.IMG_TYPE_EXP_PC);
            resultMap.put(CMConst.IMG_TYPE_EXP_PC, s3Result);
        }
        if(uploadFile.containsKey("mobileimgfile")){
            MultipartFile mobileimgFile = uploadFile.get("mobileimgfile");
            SOMap s3Result = fileService.uploadImage(mobileimgFile, exhibitidx, CMConst.IMG_TYPE_EXP_MO);
            resultMap.put(CMConst.IMG_TYPE_EXP_MO, s3Result);
        }
        
        exhibitionMapper.updateExhibit(params);
        
        List<Map<String, Object>> themeList = params.getArrayList("themelist");
        for(int i=0; i<themeList.size(); i++){
        	Map<String, Object> info = themeList.get(i);
        	info.put("reguserid", params.get("reguserid"));
			info.put("exhibitidx", exhibitidx);
			if("".equals(info.get("exhthemeidx").toString())){
				info.put("reguserid", params.get("moduserid"));
				exhibitionMapper.insertExhibitTheme(info);				
			}else{
				exhibitionMapper.updateExhibitTheme(info);		
			}
			List<Map<String,Object>> temp = (List<Map<String, Object>>) info.get("goodslist");
			if(temp.size() > 0){
				List<Map<String,Object>> addGoods = temp.stream().filter(a -> "".equals(a.get("exhgoodsidx").toString())).collect(Collectors.toList());
				List<Map<String,Object>> removeGoods = temp.stream().filter(a -> "T".equals(a.get("istrash").toString())).collect(Collectors.toList());
				List<Map<String,Object>> mainGoods = temp.stream().filter(a -> "T".equals(a.get("ismaindisp").toString())).collect(Collectors.toList());
				if(addGoods.size() != 0){
					info.put("reguserid", params.get("moduserid"));
					info.put("goodsList", addGoods);
					exhibitionMapper.insertExhThemeGoods(info);
				}
				if(removeGoods.size() != 0){
					SOMap tempMap = new SOMap();
					tempMap.put("removegoods", removeGoods);
					exhibitionMapper.removeExhThemeGoods(tempMap);
				}
				if(mainGoods.size() != 0){
					SOMap tempMap = new SOMap();
					tempMap.put("exhthemeidx", info.get("exhthemeidx"));
					tempMap.put("maingoods", mainGoods);
					exhibitionMapper.updateExhThemeGoods(tempMap);
				}
			}
        }
		
		return resultMap;
	}
	
	@Override
	public SOMap selectExhibitDetail(SOMap params) throws Exception {
		SOMap map = new SOMap();
		exhibitionMapper.updateExhibitReadCnt(params);
		SOMap detail = exhibitionMapper.selectExhibitDetail(params);
		List<SOMap> theme = exhibitionMapper.selectExhibitTheme(params);
		List<SOMap> goods = exhibitionMapper.selectExhibitGoods(params);
		Map<String, List<Map<String, Object>>> goodsMapByidx = goods.stream().collect(Collectors.groupingBy(a -> a.get("exhthemeidx").toString()));
		theme.forEach(m -> {
			m.put("isallchkgoods", false);
			m.put("num", "1");
			m.put("dir", "up");
			if(goodsMapByidx.get(m.getStr("exhthemeidx")) != null){
				m.put("goodslist", goodsMapByidx.get(m.getStr("exhthemeidx")));
			}else{
				m.put("goodslist", new ArrayList<>());
			}
		});
		detail.put("themelist", theme);
		
		
		detail.put("muappchtypearr", detail.getStr("muappchtype").split(","));
		detail.put("mumembertypearr", detail.getStr("mumembertype").split(","));
		detail.put("mumemlvtypearr", detail.getStr("mumemlvtype").split(","));
		map.put("info", detail);
		
		SOMap fileParams = new SOMap();
		String[] imgtypes = {"IGT063","IGT064"};
		fileParams.put("orgidx", params.get("exhibitidx"));
		fileParams.put("imgtypes", imgtypes);
		SOMap fileMap = fileService.selectFileList(fileParams);
		
		List<Map<String, Object>> fileList = fileMap.getArrayList("uploadedfile");
		SOMap files = new SOMap();
		fileList.forEach(m -> {
			if("IGT063".equals(m.get("imgtype"))){
				files.put("pcimgfile", m);
			}else if("IGT064".equals(m.get("imgtype"))){
				files.put("mobileimgfile", m);
			}
		});
		
		map.put("files", files);
		
		return map;
	}
}
