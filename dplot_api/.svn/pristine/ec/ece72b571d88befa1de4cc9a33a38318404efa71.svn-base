package com.dplot.security.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dplot.security.domain.Role;

/**
 * 	T_USER(사용자) TABLE.
 */
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = -4486236625960060845L;
	/** The no. */
	private Integer no; 								// no INTEGER(22) 번호
	
	/** The siteid. */
	private String siteid; 							// siteid NVARCHAR2(60) 사이트ID
	
	/** The userkind. */
	private Integer userkind; 							// userkind INTEGER(22) 유형
	
	/** The userid. */
	private String userid; 							// userid NVARCHAR2(300) 아이디
	
	/** The userpw. */
	private String userpw; 							// userpw NVARCHAR2(300) 비밀번호
	
	/** The regdate. */
	private Date regdate; 							// regdate DATE(7) 등록일자
	
	/** The name. */
	private String name;
	
	private List<Integer> nos;  // nos for UPDATE IN ~
	
	private String USER_KIND_MANAGER;  // USER_KIND_MANAGER for UPDATE
	
	/** 스프링 시큐리티용 */
	private List<Role> authorities; 
	private boolean accountNonExpired = true; 
	private boolean accountNonLocked = true; 
	private boolean credentialsNonExpired = true; 
	private boolean enabled = true;
	
	/** 중복 로그인 체크용*/
	private String securecode;

	/** 추가로 필요한 사용자정보 */
	private String usertype;	// 사용자타입
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the no.
	 *
	 * @return the no
	 */
	public Integer getNo() {
		return no;
	}
	
	/**
	 * Sets the no.
	 *
	 * @param no the new no
	 */
	public void setNo(Integer no) {
		this.no = no;
	}
	
	/**
	 * Gets the siteid.
	 *
	 * @return the siteid
	 */
	public String getSiteid() {
		return siteid;
	}
	
	/**
	 * Sets the siteid.
	 *
	 * @param siteid the new siteid
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	
	/**
	 * Gets the userkind.
	 *
	 * @return the userkind
	 */
	public Integer getUserkind() {
		return userkind;
	}
	
	/**
	 * Sets the userkind.
	 *
	 * @param userkind the new userkind
	 */
	public void setUserkind(Integer userkind) {
		this.userkind = userkind;
	}
	
	/**
	 * Gets the userid.
	 *
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	
	/**
	 * Sets the userid.
	 *
	 * @param userid the new userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	/**
	 * Gets the userpw.
	 *
	 * @return the userpw
	 */
	public String getUserpw() {
		return userpw;
	}
	
	/**
	 * Sets the userpw.
	 *
	 * @param userpw the new userpw
	 */
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	
	/**
	 * Gets the regdate.
	 *
	 * @return the regdate
	 */
	public Date getRegdate() {
		return regdate;
	}
	
	/**
	 * Sets the regdate.
	 *
	 * @param regdate the new regdate
	 */
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public List<Integer> getNos() {
		return nos;
	}

	public void setNos(List<Integer> nos) {
		this.nos = nos;
	}

	public String getUSER_KIND_MANAGER() {
		return USER_KIND_MANAGER;
	}

	public void setUSER_KIND_MANAGER(String uSER_KIND_MANAGER) {
		USER_KIND_MANAGER = uSER_KIND_MANAGER;
	}

	@Override
	public String toString() {
		return "User [no=" + no + ", siteid=" + siteid + ", userkind=" + userkind  + ", userid=" + userid + ", userpw="
				+ userpw + ", regdate=" + regdate + ", name=" + name + ", nos=" + nos + ", USER_KIND_MANAGER="
				+ USER_KIND_MANAGER + "]";
	}

	//스프링 시큐리티 추가
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.userpw;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userid;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}

	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSecurecode() {
		return securecode;
	}

	public void setSecurecode(String securecode) {
		this.securecode = securecode;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
}
