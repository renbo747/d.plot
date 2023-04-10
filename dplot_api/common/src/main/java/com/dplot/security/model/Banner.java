package com.dplot.security.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 	T_BANNER(배너) TABLE.
 */
public class Banner implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The idx. */
	private Integer idx; 								// idx IntegerEGER(22) 일련번호

	/** The siteid. */
	private String siteid; 							// siteid NVARCHAR2(60) 사이트ID

	/** The areacode. */
	private String areacode; 						// areacode NVARCHAR2(60) 영역_코드

	/** The mainidx. */
	private Integer mainidx; 							// mainidx IntegerEGER(22) 메인_일련번호

	/** The childno. */
	private Integer childno; 							// childno IntegerEGER(22) 하위번호

	/** The title. */
	private String title; 							// title VARCHAR2(100) 배너명

	/** The imgbanner. */
	private String imgbanner; 						// imgbanner NVARCHAR2(900) 배너파일

	/** The imgover. */
	private String imgover; 						// imgover NVARCHAR2(900) 오버이미지

	/** The linkurl. */
	private String linkurl; 						// linkurl NVARCHAR2(900) 링크주소

	/** The linktarget. */
	private String linktarget; 						// linktarget NVARCHAR2(60) 링크Target

	/** The isflash. */
	private String isflash; 						// isflash NCHAR(3) 플래시여부

	/** The width. */
	private Integer width; 								// width IntegerEGER(22) 폭

	/** The height. */
	private Integer height; 							// height IntegerEGER(22) 높이

	/** The popfeatures. */
	private String popfeatures; 					// popfeatures NVARCHAR2(1500) 팝업_속성

	/** The sort. */
	private Integer sort; 								// sort IntegerEGER(22) 정렬

	/** The istrash. */
	private String istrash; 						// istrash NCHAR(3) 삭제여부

	/** The regdate. */
	private Date regdate; 							// regdate DATE(7) 등록일자

	/** The html. */
	private String html; 							// html
	
	private String tmpid; 							// tmpid
	
	private String ispreviewimg;
	private String ispreviewimgover;

	/**
	 * Gets the idx.
	 *
	 * @return the idx
	 */
	public Integer getIdx() {
		return idx;
	}

	/**
	 * Sets the idx.
	 *
	 * @param idx the new idx
	 */
	public void setIdx(Integer idx) {
		this.idx = idx;
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
	 * Gets the areacode.
	 *
	 * @return the areacode
	 */
	public String getAreacode() {
		return areacode;
	}

	/**
	 * Sets the areacode.
	 *
	 * @param areacode the new areacode
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	/**
	 * Gets the mainidx.
	 *
	 * @return the mainidx
	 */
	public Integer getMainidx() {
		return mainidx;
	}

	/**
	 * Sets the mainidx.
	 *
	 * @param mainidx the new mainidx
	 */
	public void setMainidx(Integer mainidx) {
		this.mainidx = mainidx;
	}

	/**
	 * Gets the childno.
	 *
	 * @return the childno
	 */
	public Integer getChildno() {
		return childno;
	}

	/**
	 * Sets the childno.
	 *
	 * @param childno the new childno
	 */
	public void setChildno(Integer childno) {
		this.childno = childno;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the imgbanner.
	 *
	 * @return the imgbanner
	 */
	public String getImgbanner() {
		return imgbanner;
	}

	/**
	 * Sets the imgbanner.
	 *
	 * @param imgbanner the new imgbanner
	 */
	public void setImgbanner(String imgbanner) {
		this.imgbanner = imgbanner;
	}

	/**
	 * Gets the imgover.
	 *
	 * @return the imgover
	 */
	public String getImgover() {
		return imgover;
	}

	/**
	 * Sets the imgover.
	 *
	 * @param imgover the new imgover
	 */
	public void setImgover(String imgover) {
		this.imgover = imgover;
	}

	/**
	 * Gets the linkurl.
	 *
	 * @return the linkurl
	 */
	public String getLinkurl() {
		return linkurl;
	}

	/**
	 * Sets the linkurl.
	 *
	 * @param linkurl the new linkurl
	 */
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	/**
	 * Gets the linktarget.
	 *
	 * @return the linktarget
	 */
	public String getLinktarget() {
		return linktarget;
	}

	/**
	 * Sets the linktarget.
	 *
	 * @param linktarget the new linktarget
	 */
	public void setLinktarget(String linktarget) {
		this.linktarget = linktarget;
	}

	/**
	 * Gets the isflash.
	 *
	 * @return the isflash
	 */
	public String getIsflash() {
		return isflash;
	}

	/**
	 * Sets the isflash.
	 *
	 * @param isflash the new isflash
	 */
	public void setIsflash(String isflash) {
		this.isflash = isflash;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * Gets the popfeatures.
	 *
	 * @return the popfeatures
	 */
	public String getPopfeatures() {
		return popfeatures;
	}

	/**
	 * Sets the popfeatures.
	 *
	 * @param popfeatures the new popfeatures
	 */
	public void setPopfeatures(String popfeatures) {
		this.popfeatures = popfeatures;
	}

	/**
	 * Gets the sort.
	 *
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * Sets the sort.
	 *
	 * @param sort the new sort
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * Gets the istrash.
	 *
	 * @return the istrash
	 */
	public String getIstrash() {
		return istrash;
	}

	/**
	 * Sets the istrash.
	 *
	 * @param istrash the new istrash
	 */
	public void setIstrash(String istrash) {
		this.istrash = istrash;
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

	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	public String getTmpid() {
		return tmpid;
	}

	public void setTmpid(String tmpid) {
		this.tmpid = tmpid;
	}

	public String getIspreviewimg() {
		return ispreviewimg;
	}

	public void setIspreviewimg(String ispreviewimg) {
		this.ispreviewimg = ispreviewimg;
	}

	public String getIspreviewimgover() {
		return ispreviewimgover;
	}

	public void setIspreviewimgover(String ispreviewimgover) {
		this.ispreviewimgover = ispreviewimgover;
	}

}