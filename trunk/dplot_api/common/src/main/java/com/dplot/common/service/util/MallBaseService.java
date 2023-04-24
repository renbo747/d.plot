package com.dplot.common.service.util;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.dplot.common.CMConst;
import com.dplot.common.service.CommonTrackerService;
import com.dplot.common.service.MallConfigService;

/**
 * The Class MallBaseService.
 */
public abstract class MallBaseService {
	
	/** The Constant logger. */
	//private static final Logger logger = LoggerFactory.getLogger(MallBaseService.class);
	
	@Autowired
	protected MallConfigService cs;
	
	/** The tracker provider. */
	@Autowired
	protected Provider<CommonTrackerService> trackerProvider;
	
	
	// --- shortcut function
	protected int getAdminNo() {
		int no = 0;
		try {
			no = cs.getInt("authadminno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return no;
	}
	
	protected boolean isDealer() {
		return getDealerNo() > 0;
	}
	
	protected String getDealerId() {
		String id = "";
		try {
			id = cs.getStr("authdealerid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	// --- shortcut function
	protected int getDealerNo() {
		int no = 0;
		try {
			no = cs.getInt("authdealerno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return no;
	}
	
	protected boolean isAdmin() {
		return getAdminNo() > 0;
	}
	
	protected String getAdminId() {
		String id = "";
		try {
			id = cs.getStr("authadminid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
		
	/*
	 * 사이트 ID 정보 
	 */
	protected String getSiteId() {
		String id = "base";
		try {
			id = cs.getStr("siteid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	protected int getMemberNo() {
		int no = 0;
		try {
			no = cs.getInt("authmemberno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return no;
	}
	
	protected boolean isMember() {
		return getMemberNo() > 0;
	}
	
	/*
	 * 로그인 했으면 회원아이디, 아니면 빈값
	 */
	protected String getRealMemberId() {
		String id = "";
		try {
			id = cs.getStr("authmemberid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	/*
	 * 로그인 했으면 회원아이디, 아니면 게스트아이디
	 */
	protected String getMemberId() {
		try {
			return isMember() ? cs.getStr("authmemberid") : CMConst.GUEST_ID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	protected String getMemberName() {
		String name = "";
		try {
			name = cs.getStr("authmembername");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
	/*
	 * 세션아이디
	 */
	protected String getMemberSessId() {
		try {
			return isMember() ? cs.getStr("authmemberid") : cs.getStr("csessid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/*
	 * 비회원 주문IDX
	 */
	protected int getOrderIdx() {
		int no = 0;
		try {
			no = cs.getInt("authguestorderidx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return no;
	}
	
	/*
	 * 비회원 주문번호
	 */
	protected String getOrdno() {
		String ordno = "";
		try {
			ordno = cs.getStr("authguestordno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ordno;
	}
	
}
