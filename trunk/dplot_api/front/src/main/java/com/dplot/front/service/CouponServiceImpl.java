/**
 *
 */
package com.dplot.front.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.CouponMapper;
import com.dplot.mapper.CouponMemissueMapper;
import com.dplot.util.Util;

/**
 * @FileName : CouponServiceImpl.java
 * @Project : datapick_api
 * @Date : 2021. 12. 8.
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 8.         KTH                 최초작성
 * ------------------------------------------------------------
 **/
@Service
public class CouponServiceImpl extends MallBaseService implements CouponService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

	@Autowired
	CouponMemissueMapper couponMemissueMapper;

	@Autowired
	CouponMapper couponMapper;

	@Resource(name="propertiesFactory")
	private Properties prop;

	/**
	 * 주문결제 다운로드 쿠폰 조회
	 * @param param
	 * @return
	 */
	public SOMap selectOrderCouponList(SOMap param) throws Exception{
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("platform", cs.getStr("platform"));
		param.put("memlvtype", cs.getStr("authmemlvtype"));
		param.put("membertype", cs.getStr("authmembertype"));

		param.put("isdownload", "F");
		List<SOMap> couponList = couponMemissueMapper.selectCouponListByGoods(param);

		SOMap result = new SOMap();
		result.put("list", couponList);
		return result;
	}

	/**
	 * 마이페이지 쿠폰목록 조회
	 * @param param
	 * @return
	 */
	public SOMap selectMypageCouponList(SOMap param) throws Exception{
		SOMap result = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("platform", cs.getStr("platform"));

		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));

		//쿠폰 사용임박, 사용가능 수 조회
		SOMap myCouponCntInfo = couponMemissueMapper.selectCouponCntInfo(param);
		result.put("deadlinecnt", myCouponCntInfo.getInt("deadlinecnt"));
		result.put("downcnt", myCouponCntInfo.getInt("downcnt"));
		result.put("usecnt", myCouponCntInfo.getInt("usecnt"));

		List<SOMap> couponList = couponMemissueMapper.selectCouponListByUser(param);
		result.put("couponlist", couponList);

		return result;
	}

	/**
	 * 쿠폰다운로드
	 * @param param, request, response
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public Response downloadCoupon(SOMap param) throws Exception{
		Response res = new Response();
		if(!isMember()) {
			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("로그인 후 가능합니다.");
			return res;
		}

		SOMap result = new SOMap();
		if(Util.isEmpty(param.getDbStr("cpnmisidx"))) {
			result.put("isdownload", "T");

			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("다운로드할 쿠폰이 존재하지 않습니다.");
			res.setData(result);

			return res;
		}

		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("userid", getMemberId());

		SOMap coupon = couponMemissueMapper.selectCouponChkInfo(param);

		// 쿠폰수량제한여부 체크
		if(Util.flag2Bool(coupon.getDbStr("iscntlimt")) && coupon.getDbInt("cpnlimtcnt") <= coupon.getDbInt("downcnt")) {
			result.put("isdownload", "T");

			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("쿠폰다운로드 수량을 초과하였습니다.");
			res.setData(result);

			return res;
		}

		//동일인 재발급여부 체크
		if(Util.flag2Bool(coupon.getDbStr("isdupperson")) && coupon.getDbInt("dupcnt") <= coupon.getDbInt("userdowncnt")) {
			result.put("isdownload", "T");

			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("쿠폰다운로드 수량을 초과하였습니다.");
			res.setData(result);

			return res;
		}


		couponMemissueMapper.updateCouponDownload(param);
		result.put("isdownload", "T");
		result.put("cpnmisidx", "0");

		//쿠폰다운로드 후 체크
		coupon = couponMemissueMapper.selectCouponChkInfo(param);

		//수량제한쿠폰이나 동일인 재발급쿠폰이 남아있을경우 쿠폰등록
		if ((Util.flag2Bool(coupon.getDbStr("iscntlimt"))
				&& coupon.getDbInt("cpnlimtcnt") > coupon.getDbInt("downcnt"))
				|| (Util.flag2Bool(coupon.getDbStr("isdupperson"))
						&& coupon.getDbInt("dupcnt") > coupon.getDbInt("userdowncnt"))) {
			coupon.put("userno", cs.getInt("authmemberno"));
			coupon.put("userid", getMemberId());
			coupon.put("isdownload", "F");
			couponMemissueMapper.insertCouponMemissue(coupon);

			result.put("cpnmisidx", coupon.getDbInt("cpnmisidx"));
			result.put("isdownload", "F");
		}

		//다운로드 후 쿠폰목록
		param.put("isdownload", "T");
		param.put("platform", cs.getStr("platform"));
		param.put("memlvtype", cs.getStr("authmemlvtype"));
		param.put("membertype", cs.getStr("authmembertype"));

		if(!param.getArrayList("items").isEmpty()) {
			List<SOMap> couponList = couponMemissueMapper.selectCouponListByGoods(param);
			result.put("list", couponList);
		}


		res.setData(result);
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public Response downloadAllCoupon(SOMap param) throws Exception{
		Response res = new Response();
		if(!isMember()) {
			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("로그인 후 가능합니다.");
			return res;
		}

		SOMap result = new SOMap();
		List<Map<String,Object>> downloadList = param.getArrayList("list");
		SOMap dbparams = new SOMap();

		dbparams.put("siteid", cs.getStr("siteid"));
		dbparams.put("userno", cs.getInt("authmemberno"));
		dbparams.put("userid", getMemberId());

		for (Map<String, Object> map : downloadList) {
			if("T".equals(map.get("isdownload"))) continue;

			dbparams.putAll(map);
			SOMap coupon = couponMemissueMapper.selectCouponChkInfo(dbparams);
			//쿠폰수량제한여부 체크
			if(Util.flag2Bool(coupon.getDbStr("iscntlimt")) && coupon.getDbInt("cpnlimtcnt") <= coupon.getDbInt("downcnt")) {
				continue;
			}

			//동일인 재발급여부 체크
			if(Util.flag2Bool(coupon.getDbStr("isdupperson")) && coupon.getDbInt("dupcnt") <= coupon.getDbInt("userdowncnt")) {
				continue;
			}

			couponMemissueMapper.updateCouponDownload(dbparams);

			// 쿠폰다운로드 후 체크
			coupon = couponMemissueMapper.selectCouponChkInfo(dbparams);

			//수량제한쿠폰이나 동일인 재발급쿠폰이 남아있을경우 쿠폰등록
			if ((Util.flag2Bool(coupon.getDbStr("iscntlimt"))
					&& coupon.getDbInt("cpnlimtcnt") > coupon.getDbInt("downcnt"))
					|| (Util.flag2Bool(coupon.getDbStr("isdupperson"))
							&& coupon.getDbInt("dupcnt") > coupon.getDbInt("userdowncnt"))) {
				coupon.put("userno", cs.getInt("authmemberno"));
				coupon.put("userid", getMemberId());
				coupon.put("isdownload", "F");
				couponMemissueMapper.insertCouponMemissue(coupon);
			}
		}

		//다운로드 후 쿠폰목록
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("isdownload", "T");
		param.put("platform", cs.getStr("platform"));
		param.put("memlvtype", cs.getStr("authmemlvtype"));
		param.put("membertype", cs.getStr("authmembertype"));

		List<SOMap> couponList = couponMemissueMapper.selectCouponListByGoods(param);
		result.put("list", couponList);
		res.setData(result);
		return res;
	}

	/**
	 * 쿠폰전체다운로드(페이징으로인하여 전체 목록 조회후  다운로드처리)
	 */
	@Override
	public SOMap pageDownloadAllCoupon(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("userid", cs.getStr("authmemberid"));
		param.put("platform", cs.getStr("platform"));
		param.put("isdownload", "F");
		param.put("isnopaging", "T");

		List<SOMap> downloadList = couponMemissueMapper.selectCouponListByUser(param);
		for (SOMap map : downloadList) {
			param.putAll(map);
			SOMap coupon = couponMemissueMapper.selectCouponChkInfo(param);
			//쿠폰수량제한여부 체크
			if(Util.flag2Bool(coupon.getDbStr("iscntlimt")) && coupon.getDbInt("cpnlimtcnt") <= coupon.getDbInt("downcnt")) {
				continue;
			}

			//동일인 재발급여부 체크
			if(Util.flag2Bool(coupon.getDbStr("isdupperson")) && coupon.getDbInt("dupcnt") <= coupon.getDbInt("userdowncnt")) {
				continue;
			}
			couponMemissueMapper.updateCouponDownload(param);

			// 쿠폰다운로드 후 체크
			coupon = couponMemissueMapper.selectCouponChkInfo(param);

			//수량제한쿠폰이나 동일인 재발급쿠폰이 남아있을경우 쿠폰등록
			if ((Util.flag2Bool(coupon.getDbStr("iscntlimt"))
					&& coupon.getDbInt("cpnlimtcnt") > coupon.getDbInt("downcnt"))
					|| (Util.flag2Bool(coupon.getDbStr("isdupperson"))
							&& coupon.getDbInt("dupcnt") > coupon.getDbInt("userdowncnt"))) {
				coupon.put("userno", cs.getInt("authmemberno"));
				coupon.put("userid", getMemberId());
				coupon.put("isdownload", "F");
				couponMemissueMapper.insertCouponMemissue(coupon);
			}
		}
		return resultMap;
	}

	/**
	 * 평생회원 쿠폰 조회
	 */
	@Override
	public SOMap lifetimeCoupon(SOMap params) throws Exception {
		SOMap resultMap = new SOMap();
		//평생회원 쿠폰은 관리자 계정으로 등록후 properties에서 관리하기로 함
		params.put("comcpnno", prop.getProperty("life.member.comcpnno"));
		List<SOMap> lifecoupon = couponMapper.selectLifeCoupon(params);
		resultMap.put("lifecoupon", lifecoupon);
		return resultMap;
	}


	@Override
	public Response downCoupon(SOMap param) throws Exception {
		Response res = new Response();
		if(!isMember()) {
			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("로그인 후 가능합니다.");
			return res;
		}

		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));

		SOMap coupon = couponMemissueMapper.selectCouponInfo(param);
		if(!Util.flag2Bool(coupon.getDbStr("isperiod"))) {
			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("다운로드 기간이 만료되었습니다.");
			return res;
		}
		if(Util.flag2Bool(coupon.getDbStr("isdownload"))) {
			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("이미 다운로드한 쿠폰입니다.");
			return res;
		}
		//쿠폰수량제한여부 체크
		if(Util.flag2Bool(coupon.getDbStr("iscntlimt")) && coupon.getDbInt("cpnlimtcnt") < coupon.getDbInt("downcnt")) {
			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("쿠폰다운로드 수량을 초과하였습니다.");
			return res;
		}

		//동일인 재발급여부 체크
		if(Util.flag2Bool(coupon.getDbStr("isdupperson")) && coupon.getDbInt("dupcnt") < coupon.getDbInt("userdowncnt")) {
			res.setStatusCode(Status.FAIL.getKey());
			res.setMessage("쿠폰다운로드 수량을 초과하였습니다.");
			return res;
		}

		param.put("cpnmisidx", coupon.getDbInt("cpnmisidx"));

		couponMemissueMapper.updateCouponDownload(param);
		return res;
	}
}
