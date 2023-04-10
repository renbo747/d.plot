package com.dplot.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dplot.common.service.CommonTrackerService;
import com.dplot.util.Util;

/**
 * 요청을 추적하기 위한 클래스
 */
@Service
@Scope("request")
public class CommonTrackerServiceImpl implements CommonTrackerService {
	
	private long m_ctime = 0;
	private int m_admin_no = 0;
	private int m_member_no = 0;
	private String m_request = "-";
	private String m_sess = "-";
	private String m_flatform = "-";
	private String m_ip = "-";
	private List<Entry> m_history = new ArrayList<Entry>();
	
	/**
	 * The Class Entry.
	 */
	class Entry {
		
		/** The m_ctime. */
		public long m_ctime = 0;
		
		/** The m_key. */
		public String m_key = "";
		
		/** The m_value. */
		public String m_value = "";
		
		/**
		 * Instantiates a new entry.
		 *
		 * @param k the k
		 * @param v the v
		 */
		public Entry(String k, String v) {
			m_ctime = System.currentTimeMillis();
			m_key = k;
			m_value = v;
		}
	}
	
	/**
	 * Instantiates a new common tracker service impl.
	 *
	 * @throws Exception the exception
	 */
	public CommonTrackerServiceImpl() throws Exception {
		m_ctime = System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see apps.svc.common.CommonTrackerService#getCTime()
	 */
	@Override
	public long getCTime() throws Exception {
		return m_ctime;
	}

	/* (non-Javadoc)
	 * @see apps.svc.common.CommonTrackerService#setRequest(java.lang.String)
	 */
	@Override
	public void setRequest(String request) throws Exception {
		m_request = request;
	}

	/* (non-Javadoc)
	 * @see apps.svc.common.CommonTrackerService#setAdminNo(int)
	 */
	@Override
	public void setAdminNo(int no) throws Exception {
		m_admin_no = no;
	}

	/* (non-Javadoc)
	 * @see apps.svc.common.CommonTrackerService#setMemberNo(int)
	 */
	@Override
	public void setMemberNo(int no) throws Exception {
		m_member_no = no;
	}

	/* (non-Javadoc)
	 * @see apps.svc.common.CommonTrackerService#setIp(java.lang.String)
	 */
	@Override
	public void setIp(String ip) throws Exception {
		m_ip = ip;
	}
	
	/* (non-Javadoc)
	 * @see apps.svc.common.CommonTrackerService#add(java.lang.String, java.lang.String)
	 */
	@Override
	public void add(String k, String v) throws Exception {
		Entry entry = new Entry(k, v);
		m_history.add(entry);
	}
	
	@Override
	public void add(String v) throws Exception {
		Entry entry = new Entry("", v);
		m_history.add(entry);
	}

	/* (non-Javadoc)
	 * @see apps.svc.common.CommonTrackerService#toStr()
	 */
	@Override
	public String toStr() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s %d %d %s %s %s", m_sess, m_admin_no, m_member_no, m_ip, m_request, m_flatform));

		long start = this.m_ctime;

		for (Entry entry : m_history) {
			long diff = entry.m_ctime - start;
			
			if (Util.hasText(entry.m_key)) {
				sb.append(String.format(" [%d:%s:%s]", diff, entry.m_key, entry.m_value));
			} else {
				sb.append(String.format(" [%d:%s]", diff, entry.m_value));
			}
			
			start = entry.m_ctime;
		}

		return sb.toString();
	}
	
	@Override
	public void setSess(String sessPrefix) {
		m_sess = sessPrefix;
	}
	
	@Override
	public void setFlatform(String flatform) throws Exception {
		m_flatform = flatform;
	}

}