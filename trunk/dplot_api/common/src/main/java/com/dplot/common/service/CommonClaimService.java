package com.dplot.common.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

public interface CommonClaimService {

	public SOMap selectClaimApply(SOMap param) throws Exception;
	
	public SOMap selectClaimDetail(SOMap param) throws Exception;
	
	public SOMap selectCompClaimDetail(SOMap param) throws Exception;
	
	public SOMap saveClaimApply(Map<String, MultipartFile> files, SOMap param) throws Exception;
	
	public SOMap updateClaimInfo(SOMap param) throws Exception;
	
	public SOMap saveClaimStatus(SOMap param) throws Exception;
	
	public SOMap paymentClaim(SOMap param) throws Exception;
}
