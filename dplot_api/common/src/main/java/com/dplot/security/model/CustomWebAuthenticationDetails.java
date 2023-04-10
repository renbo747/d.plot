package com.dplot.security.model;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dplot.security.provider.MemberAuthenticationProvider;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = -3180033345910675742L;

	private static final Logger logger = LoggerFactory.getLogger(MemberAuthenticationProvider.class);
	
	//private String isSaveId;
	private String mode;
	private String ordno;
	private String ordname;
	private String snstype;
	private String otherid;
	private String isadpush;
	private String isadmailing;
	private String isadsms;
	
	
	@SuppressWarnings("unchecked")
	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		if ("application/json".equalsIgnoreCase(request.getHeader("Content-Type"))) {
            try {
            	Map<String,String> paramMap = (Map<String,String>)request.getAttribute("paramMap");
                //this.isSaveId = paramMap.get("isSaveId");
                this.mode = paramMap.get("mode");
                this.ordno = paramMap.get("ordno");
                this.ordname = paramMap.get("ordname");
                this.snstype = paramMap.get("snstype");
                this.otherid = paramMap.get("otherid");
                this.isadpush = paramMap.get("isadpush");
                this.isadmailing = paramMap.get("isadmailing");
                this.isadsms = paramMap.get("isadsms");
                
            } catch (Exception e) {
            	logger.error("", e);
            }
        } else {
        	//this.isSaveId = request.getParameter("isSaveId");
        	this.mode = request.getParameter("mode");
        	this.ordno = request.getParameter("ordno");
        	this.ordname = request.getParameter("ordname");
        	this.snstype = request.getParameter("snstype");
        	this.otherid = request.getParameter("otherid");
        	this.isadpush = request.getParameter("isadpush");
            this.isadmailing = request.getParameter("isadmailing");
            this.isadsms = request.getParameter("isadsms");
        }
	}
	
	public String getIsadpush() {
		return isadpush;
	}

	public String getIsadmailing() {
		return isadmailing;
	}

	public String getIsadsms() {
		return isadsms;
	}

	public String getMode(){
		return this.mode;
	}
	
	
	public String getOrdno() {
		return ordno;
	}

	public String getOrdname() {
		return ordname;
	}

	public String getSnstype(){
		return this.snstype;
	}
	
	public String getOtherid() {
		return otherid;
	}
}
