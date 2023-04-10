package com.dplot.admin.controller.goods;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.admin.service.goods.AdminBrandManageService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;

@RestController
@RequestMapping(value={"/admin/goods/brand", "/partners/goods/brand"})
public class BrandManageController {
	
	@Autowired
	private AdminBrandManageService adminBrandManageService;
	
	/**
	 * 브랜드 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public Response search(@RequestBody SOMap params) throws Exception {
		
		return new Response(adminBrandManageService.selectBrandList(params));
	}
	
	/**
	 * 파트너사 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/search/partners", method=RequestMethod.POST)
	public Response searchPartners(@RequestBody SOMap params) throws Exception {
		
		return new Response(adminBrandManageService.selectPartnersList(params));
	}
	
	/**
	 * 브랜드 추가
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public Response regist(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception {
		Response result;
		
		try{
			Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
			adminBrandManageService.insertBrand(params, uploadFile);
			result = new Response(Status.OK);
		}catch(Exception e){
			result = new Response(Status.FAIL);
		}
		
		return result;
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.POST)
	public Response detail(@RequestBody SOMap params) throws Exception {
		return new Response(adminBrandManageService.selectBrandDetail(params));
	}
	
	/**
	 * 브랜드 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Response update(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		Response result;
		
		try{
			Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
			adminBrandManageService.updateBrand(params, uploadFile);
			result = new Response(Status.OK);
		}catch(Exception e){
			result = new Response(Status.FAIL);
		}
		
		return result;
	}
}
