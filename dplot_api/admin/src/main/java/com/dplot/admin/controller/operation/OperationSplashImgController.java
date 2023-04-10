package com.dplot.admin.controller.operation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dplot.admin.service.operation.OperSplashImgService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.controller.ExcelDownController;

/**
 * @author : LKW
 * @discription : 스플래시이미지 Controller
 * @fileName : OperationSplashImgController.java
 * @date : 2022-04-08
 *       ================================================================= DATE
 *       AUTHOR NOTE
 *       -----------------------------------------------------------------
 *       2022-04-08 LKW 최초생성
 *       -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/operation/setting/splashimg")
public class OperationSplashImgController extends ExcelDownController {
	@Autowired
	private OperSplashImgService operSplashImgService;

	/**
	 * 스플래시이미지 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response page(@RequestBody SOMap params) throws Exception {
		return new Response(operSplashImgService.selectSplashImgList(params));
	}

	
	 /**
	 * 스플래시이미지 저장
	 * @param params
	 * @return Response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	 public Response save(@RequestPart("params") SOMap params, MultipartHttpServletRequest fileParams) throws Exception{
		 Map<String, MultipartFile> uploadFile = fileParams.getFileMap();
		 return new Response(operSplashImgService.saveSplashImg(params, uploadFile));
	 }
}
