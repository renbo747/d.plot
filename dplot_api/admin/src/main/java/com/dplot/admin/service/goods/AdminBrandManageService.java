package com.dplot.admin.service.goods;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface AdminBrandManageService {
	SOMap selectBrandList(SOMap param) throws Exception;

	SOMap selectPartnersList(SOMap params) throws Exception;

	SOMap insertBrand(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	SOMap updateBrand(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	SOMap selectBrandDetail(SOMap params) throws Exception;
}
