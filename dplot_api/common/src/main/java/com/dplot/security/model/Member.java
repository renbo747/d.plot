package com.dplot.security.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.dplot.security.domain.Role;

/**
 * 	T_MEMBER(회원) TABLE.
 */
public class Member implements Serializable, UserDetails {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 사용자번호 */
	private Integer userno;
	
	/** 이름 */
	private String name;

	/** 비밀번호 */
	private String userpw;
	
	/** 회원아이디 */
	private String userid;

	/** 회원유형. */
	private String membertype;
	
	/** 회원등급 */
	private String memlvtype;
	
	/** 회원등급명 */
	private String memlvtypenm;
	
	/** 비밀번호 수정일 */
	private String pwmoddate;
	
	/** 자동로그인 여부*/
	private boolean isAutoLogin;
	
	/** 휴면계정 여부 */
	private boolean isSleep;
	
	/** 바이오인증 여부 */
	private boolean isBio;
	
	/** PUSH 마케팅정보 PUSH 여부 */
	private boolean isPush;
	
	/** PUSH 마케팅정보 PUSH 여부 */
	private boolean isSms;
	
	/** PUSH 마케팅정보 PUSH 여부 */
	private boolean isMail;
	
	/** 스프링 시큐리티용 */
	private List<Role> authorities; 
	private boolean accountNonExpired = true; 
	private boolean accountNonLocked = true; 
	private boolean credentialsNonExpired = true; 
	private boolean enabled = true;
	
	/** 중복 로그인 체크용*/
	private String securecode;
	
	private String joinchtype;
	
	public boolean isSms() {
		return isSms;
	}

	public void setSms(boolean isSms) {
		this.isSms = isSms;
	}

	public boolean isMail() {
		return isMail;
	}

	public void setMail(boolean isMail) {
		this.isMail = isMail;
	}
	
	/**
	 * @return the userno
	 */
	public Integer getUserno() {
		return userno;
	}

	/**
	 * @param userno the userno to set
	 */
	public void setUserno(Integer userno) {
		this.userno = userno;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the membertype
	 */
	public String getMembertype() {
		return membertype;
	}

	/**
	 * @param membertype the membertype to set
	 */
	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}

	/**
	 * @return the userpw
	 */
	public String getUserpw() {
		return userpw;
	}

	/**
	 * @param userpw the userpw to set
	 */
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the memlvtype
	 */
	public String getMemlvtype() {
		return memlvtype;
	}

	/**
	 * @param memlvtype the memlvtype to set
	 */
	public void setMemlvtype(String memlvtype) {
		this.memlvtype = memlvtype;
	}

	/**
	 * @return the memlvtypenm
	 */
	public String getMemlvtypenm() {
		return memlvtypenm;
	}

	/**
	 * @param memlvtypenm the memlvtypenm to set
	 */
	public void setMemlvtypenm(String memlvtypenm) {
		this.memlvtypenm = memlvtypenm;
	}

	/**
	 * @return the pwmoddate
	 */
	public String getPwmoddate() {
		return pwmoddate;
	}

	/**
	 * @param pwmoddate the pwmoddate to set
	 */
	public void setPwmoddate(String pwmoddate) {
		this.pwmoddate = pwmoddate;
	}

	/**
	 * @return the isAutoLogin
	 */
	public boolean isAutoLogin() {
		return isAutoLogin;
	}

	/**
	 * @param isAutoLogin the isAutoLogin to set
	 */
	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}
	
	
	/**
	 * @return the isSleep
	 */
	public boolean isSleep() {
		return isSleep;
	}

	/**
	 * @param isSleep the isSleep to set
	 */
	public void setSleep(boolean isSleep) {
		this.isSleep = isSleep;
	}

	/**
	 * @return the isBio
	 */
	public boolean isBio() {
		return isBio;
	}

	/**
	 * @param isBio the isBio to set
	 */
	public void setBio(boolean isBio) {
		this.isBio = isBio;
	}

	/**
	 * @return the isPush
	 */
	public boolean isPush() {
		return isPush;
	}

	/**
	 * @param isPush the isPush to set
	 */
	public void setPush(boolean isPush) {
		this.isPush = isPush;
	}

	/**
	 * @return the accountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the authorities
	 */
	public List<Role> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return the securecode
	 */
	public String getSecurecode() {
		return securecode;
	}

	/**
	 * @param securecode the securecode to set
	 */
	public void setSecurecode(String securecode) {
		this.securecode = securecode;
	}

	@Override
	public String getPassword() {
		return this.userpw;
	}

	@Override
	public String getUsername() {
		return this.userid;
	}

	public String getJoinchtype() {
		return joinchtype;
	}

	public void setJoinchtype(String joinchtype) {
		this.joinchtype = joinchtype;
	}
	
}
