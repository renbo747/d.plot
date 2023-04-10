package com.dplot.front.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.CommonClaimService;
import com.dplot.common.service.CommonOrderService;
import com.dplot.common.service.CommonService;
import com.dplot.common.service.DeliveryTrackingService;
import com.dplot.common.service.util.FileService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.BizException;
import com.dplot.mapper.AsInquiryMapper;
import com.dplot.mapper.BannedMapper;
import com.dplot.mapper.ClaimGoodsGiftMapper;
import com.dplot.mapper.ClaimGoodsMapper;
import com.dplot.mapper.ClaimMapper;
import com.dplot.mapper.ComCartMapper;
import com.dplot.mapper.ComOrderGoodsDelivMapper;
import com.dplot.mapper.ComOrderGoodsMapper;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.CommonCodeMapper;
import com.dplot.mapper.CouponMapper;
import com.dplot.mapper.CouponMemissueMapper;
import com.dplot.mapper.EpointPayMapper;
import com.dplot.mapper.FileMapper;
import com.dplot.mapper.GoodsMapper;
import com.dplot.mapper.GoodsReviewMapper;
import com.dplot.mapper.MemberAddressMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.MemberMsgMapper;
import com.dplot.mapper.PrevComorderMapper;
import com.dplot.mapper.RecomRewardLogMapper;
import com.dplot.mapper.RecomRewardMapper;
import com.dplot.mapper.ReserveConfigMapper;
import com.dplot.mapper.ReserveMapper;
import com.dplot.mapper.SerialMemberMapper;
import com.dplot.mapper.SerialNoMapper;
import com.dplot.mapper.SerialPromoMapper;
import com.dplot.mapper.TossreceiptMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.mapper.WishMapper;
import com.dplot.util.CryptHash;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;

/**
 * 
 * @FileName : MypageServiceImpl.java
 * @Project : datapick_api
 * @Date : 2021. 12. 30. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 12. 30.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@Service
public class MypageServiceImpl extends MallBaseService implements MypageService{
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private MemberAddressMapper memberAddressMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private WishMapper wishMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private ComCartMapper comCartMapper;
	
	@Autowired
	private ReserveMapper reserveMapper;
	
	@Autowired
	private ComOrderMapper comOrderMapper;
	
	@Autowired
	private ComOrderGoodsMapper comOrderGoodsMapper;
	
	@Autowired
	private ComOrderGoodsDelivMapper comOrderGoodsDelivMapper;
	
	@Autowired
	private GoodsReviewMapper goodsReviewMapper; 
	
	@Autowired
	private MemberMsgMapper memberMsgMapper;
	
	@Autowired
	private SerialPromoMapper serialPromoMapper;
	
	@Autowired
	private SerialNoMapper serialNoMapper;
	
	@Autowired
	private AsInquiryMapper asInquiryMapper;
	
	@Autowired
	private CouponMemissueMapper couponMemissueMapper;
	
	@Autowired
	private CouponMapper couponMapper;
	
	@Autowired
	private SerialMemberMapper serialMemberMapper;
	
	@Autowired
	private EpointPayMapper epointPayMapper;
	
	@Autowired
	private TossreceiptMapper tossreceiptMapper;
	
	@Autowired
	private CommonCodeMapper commonCodeMapper;
	
	@Autowired
	private RecomRewardMapper recomRewardMapper;
	
	@Autowired
	private RecomRewardLogMapper recomRewardLogMapper;
	
	@Autowired
	private ClaimMapper claimMapper;
	
	@Autowired
	private ClaimGoodsMapper claimGoodsMapper;
	
	@Autowired
	private ClaimGoodsGiftMapper claimGoodsGiftMapper;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private DeliveryTrackingService deliveryTrackingService;
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private CommonOrderService commonOrderService;
	
	@Autowired
	private CommonClaimService commonClaimService;
	
	@Autowired
	private ReserveConfigMapper reserveConfigMapper;
	
	
	@Autowired
	private PrevComorderMapper prevComorderMapper;
	
	@Autowired
	private BannedMapper bannedMapper;
	
    @Resource(name="propertiesFactory")
    private Properties prop;

	/**
	 * 마이페이지 대쉬보드 조회
	 */
	@Override
	public Response selectMypageInfo(SOMap param) throws Exception {
		Response result = new Response();
		result.setErrorShow(false);
		SOMap resultMap = new SOMap();
		SOMap dbParam = new SOMap();
		
		dbParam.put("siteid", cs.getStr("siteid"));
		dbParam.put("userno", cs.getInt("authmemberno"));
		dbParam.put("memlvtypenm", cs.getStr("authmemberlevel"));
		dbParam.put("membertype", cs.getStr("authmembertype"));
		dbParam.put("memlvtype", cs.getStr("authmemlvtype"));
		if (dbParam.getInt("userno")== 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("로그인 후 가능합니다.");
			return result;
		}
		/*******************************************************
		 * 사용자 6개월간 구매 금액 및 건수 조회
		 *******************************************************/
		SOMap orderInfo = comOrderMapper.selectUserOrderInfo(dbParam);
		resultMap.put("orderinfo", orderInfo);
		/*******************************************************
		 * 주문상태별 갯수 조회
		 *******************************************************/
		SOMap orderCntList = comOrderGoodsMapper.selectOrderGoodsCnt(dbParam);
		orderCntList.putAll(claimGoodsMapper.selectClaimGoodsCnt(dbParam));
		resultMap.put("ordercntinfo", orderCntList);
		
		/*******************************************************
		 * 나의 작성가능한 리뷰 조회
		 *******************************************************/
		SOMap reviewRewardInfo= reserveConfigMapper.selectReserveConfigInfoByMemberType(dbParam);
		if (reviewRewardInfo == null) {
			resultMap.put("reviewmax",0);
		}else {
			resultMap.put("reviewmax", reviewRewardInfo.getInt("reviewmax"));
		}
		
		dbParam.put("isreview", "F");
		if (CMConst.CM_PLATFORM_PC.equals(cs.getStr("platform"))) {
			dbParam.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		} else {
			dbParam.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_MO_M);
		}
		dbParam.put("limit", 5);
		List<SOMap> reviewList = goodsReviewMapper.selectMyReviewList(dbParam);
		resultMap.put("reviewlist", reviewList);
		return new Response(resultMap);
	}
	
	
	
	/****************************
	 * 마이페이지_적립금 조회
	 *****************************/
	@Override
	public Response selectReserveList(SOMap param) throws Exception {
		Response result = new Response();
		SOMap resultMap = new SOMap();
		
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno")== 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("로그인 후 가능합니다.");
			return result;
		}
		
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		SOMap extinctinfo = reserveMapper.selectExtinctReserveInfo(param);
		resultMap.putAll(extinctinfo);
		/*********************************************
		 * 적립금 내역 조회
		 ********************************************/
		List<SOMap> reserveList = reserveMapper.selectFrontReserveList(param);
		resultMap.put("rewardlist", reserveList);
		return new Response(resultMap);
	}
	
	/****************************
	 * 마이페이지_e-포인트 조회
	 *****************************/
	@Override
	public Response selectEpointList(SOMap param) throws Exception {
		Response result = new Response();
		SOMap resultMap = new SOMap();
		
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno")== 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("로그인 후 가능합니다.");
			return result;
		}
		
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		SOMap extinctinfo = epointPayMapper.selectEpointInfo(param);
		resultMap.putAll(extinctinfo);
		/*********************************************
		 * e-포인트 내역 조회
		 ********************************************/
		List<SOMap> epointList = epointPayMapper.selectFrontEpointList(param);
		resultMap.put("rewardlist", epointList);
		return new Response(resultMap);
	}
	
	/**
	 * 마이페이지_찜한상품_전체 삭제
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Response deleteMyWishAllDel(SOMap param) throws Exception{
		Response result = new Response();
		result.setErrorShow(false);
		SOMap dbParam = new SOMap();
		int resultCnt = 0;
		dbParam.put("userno", cs.getInt("authmemberno"));
		dbParam.put("siteid", cs.getStr("siteid"));
		
		if (dbParam.getInt("userno")== 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("로그인 후 가능합니다.");
			return result;
		}
	    resultCnt = wishMapper.updateWishDel(dbParam);
	    
	    if (resultCnt <= 0) {
	    	throw new Exception("전체 삭제에 실패했습니다.");
		}
		
		return result;
	}
	
	/**
	 * 마이페이지_찜한상품 카트에 담기
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Response insertWishAddCart(SOMap param) throws Exception {
		Response result = new Response();
		result.setErrorShow(false);
		
		SOMap dbParam = new SOMap();
		String sessid = getMemberSessId();
		int resultCnt =0;
		
		dbParam.put("siteid", cs.getStr("siteid"));
		dbParam.put("sessid", sessid);
		dbParam.put("userno", cs.getInt("authmemberno"));
		dbParam.put("cateidx", param.getInt("cateidx"));
		dbParam.put("goodsno", param.getInt("goodsno"));
		dbParam.put("ea", 1); //찜한 상품은 수량 1개
		
		if (dbParam.getInt("userno") == 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("로그인 후 가능합니다.");
			return result;
		}
		//상품이 존재하는지 체크
		SOMap contentsmap = goodsMapper.selectGoodsJoinGoodsContent(dbParam);
		if (Util.isEmpty(contentsmap)) {
			result.setStatusCode(Status.FAIL.getKey());
			result.setMessage("상품정보가 존재하지 않습니다.");
			return result;
		}
		
//		List<SOMap> optionList = goodsOptionDetailMapper.selectGoodsOptionDetailList(dbParam);
//		//옵션상품
//		if (optionList.size() <= 0) {
//			throw new Exception("상품정보가 존재하지 않습니다.");
//		}
//		if (optionList.size() > 1) { //옵션 상품=> 상품상세페이지로 이동
//			resultMap.put("isoptions", "T");
//			return new Response(resultMap);
//		}
//		dbParam.putAll(optionList.get(0));
//		
		comCartMapper.insertCart(dbParam);
		//resultCnt = cartOptionMapper.insertCartOption(dbParam);
		if (resultCnt  != 1) {
			throw new Exception("장바구니 등록에 실패했습니다.");
		}
		
		return result;
	}
	
	/**
	 * 마이페이지_배송지 정보 조회
	 */
	@Override
	public Response selectAddressList(SOMap param) throws Exception {
		Response result = new Response();
		
		param.put("userno", cs.getInt("authmemberno"));
		
		if (param.getInt("userno") == 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("배송지 정보 조회는 로그인 후 가능합니다.");
			return result;
		}
		
		SOMap resultMap = new SOMap();
		List<SOMap> addressList = memberAddressMapper.selectAddressList(param);
		resultMap.put("addresslist", addressList);
		result.setData(resultMap);
		
		return result;
	}
	
	/**
	 * 마이페이지_배송지 정보 조회
	 */
	@Override
	public Response selectAddress(SOMap param) throws Exception {
		Response result = new Response();
		
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno") == 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("배송지 정보 조회는 로그인 후 가능합니다.");
			return result;
		}
		
		SOMap resultMap = new SOMap();
		SOMap address = memberAddressMapper.selectAddress(param);
		resultMap.put("address", address);
		
		result.setData(resultMap);
		
		return result;
	}
	
	/**
	 * 마이페이지_배송지 정보 저장
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Response saveAddressInfo(SOMap param) throws Exception {
		Response result = new Response();
		result.setErrorShow(false);
		
		SOMap dbParam = new SOMap();
		int resultCnt = 0;
		dbParam.put("userno", cs.getInt("authmemberno"));
		
		List<SOMap> list = memberAddressMapper.selectAddressList(dbParam);
		if(list.size() == 0) {
			dbParam.put("isdefault", "T");
		}
		
		dbParam.putAll(param);
		/******************
		 * Valid Check
		 ******************/
		if (dbParam.getInt("userno") == 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("배송지  저장은 로그인 후 가능합니다.");
			return result;
		}else if (dbParam.getStr("title") == "") {
			result.setStatusCode(Status.BAD_REQUEST.getKey());
			result.setMessage("받는 사람을 작성해 주세요.");
			return result;
		}else if (dbParam.getStr("mobile") == "") {
			result.setStatusCode(Status.BAD_REQUEST.getKey());
			result.setMessage("연락처를 작성해 주세요.");
			return result;
		}else if (dbParam.getStr("post") == "") {
			result.setStatusCode(Status.BAD_REQUEST.getKey());
			result.setMessage("우편번호를 작성해 주세요.");
			return result;
		}else if (dbParam.getStr("addrroad") == "") {
			result.setStatusCode(Status.BAD_REQUEST.getKey());
			result.setMessage("주소를 작성해 주세요.");
			return result;
		}else{
			//기본배송지 등록시 이전 기본 배송지가 있을 경우  이전 기본 배송지 취소 처리
			if (dbParam.getStr("isdefault").equals("T")) {
				SOMap tempMap = memberAddressMapper.selectDefaultAddress(dbParam);
				if (tempMap != null && tempMap.getInt("idx") > 0) {
					SOMap tempParam = new SOMap();
					tempParam.put("idx", tempMap.getInt("idx"));
					tempParam.put("userno", cs.getInt("authmemberno"));
					memberAddressMapper.updateisdefaultCancel(tempParam);
				}
			}
			//등록, 수정 처리
			if(dbParam.getDbInt("idx") == 0) {
				resultCnt = memberAddressMapper.insertAddress(dbParam);
			} else {
				resultCnt = memberAddressMapper.updateAddress(dbParam);
			}
			//회원정보에 주소가 없을경우 회원정보 주소 수정
			SOMap member = memberMapper.selectMember(dbParam);
			dbParam.remove("tel");
			dbParam.remove("mobile");
			if(Util.isEmpty(member.getDbStr("post"))){
				memberMapper.updateMember(dbParam);
			}
		}
		if (resultCnt <= 0) {
			throw new Exception("배송지 저장에 실패하였습니다.");
		}
		
		SOMap resultMap = new SOMap();
		resultMap.put("address", memberAddressMapper.selectAddress(dbParam));
		result.setData(resultMap);
		return result;
	}

	/**
	 * 마이페이지_배송지 정보 삭제
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Response delAddressInfo(SOMap param) throws Exception{
		Response result = new Response();
		result.setErrorShow(false);
		int resultCnt = 0;
		
		param.put("userno", getMemberNo());
		if (param.getInt("userno") == 0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("배송지  삭제는 로그인 후 가능합니다.");
			return result;
		}else if (param.getInt("idx") == 0 ) {
			throw new Exception("배송지 삭제에 실패하였습니다.");
		}else {
			SOMap tempMap = memberAddressMapper.selectAddress(param);
			if (tempMap != null && Util.flag2Bool(tempMap.getDbStr("isdefault"))) {
				throw new Exception("기본배송지는 삭제할수 없습니다.");
			}
		}
		//배송지 삭제
		resultCnt = memberAddressMapper.delAddressInfo(param);
		if (resultCnt <= 0) {
			throw new Exception("배송지 삭제에 실패하였습니다.");
		}
		
		return result;
	}

	/**
	 * 파일 매핑 -> 개별 항목에 대한 전체 이미지
	*/
	List<SOMap> getFiles(Object orgidx, String[] types) throws Exception{
		SOMap fparam = new SOMap();
		fparam.put("imgtypes", types);
		fparam.put("orgidx", orgidx);
		return fileMapper.selectFileList(fparam);
	}
	
	/**
	 * 회원정보 체크
	 */
	@Override
	public Response selectUserCheck(SOMap param) throws Exception{
		Response result = new Response();
		result.setErrorShow(false);
		SOMap dbParam = new SOMap();
		//비밀번호 입력 체크
		if (param.getStr("pw") == "") {
			result.setStatusCode(Status.FAIL.getKey());
			result.setMessage("새 비밀번호를 입력해주세요.");
			return result;
		}
		String dbpw = param.getDbDateStr("pw");
		String pw = CryptHash.hash(dbpw);
		dbParam.put("userid", param.get("id"));
		dbParam.put("userpw", pw);
		dbParam.put("siteid", cs.getStr("siteid"));
		
		int resultCnt = userMapper.selectUserCnt(dbParam);
		
		if (resultCnt != 1) {
			result.setStatusCode(Status.FAIL.getKey()); // 비밀번호가 유효하지 않을때,
			result.setMessage("비밀번호 인증에 실패했습니다.");
		}
		return result;
	}

	/**
	 * 회원정보 조회
	 */
	@Override
	public Response selectUserInfo(SOMap param) throws Exception{
		Response result = new Response();
		SOMap resultMap = new SOMap();
		SOMap dbParam = new SOMap();
		
		dbParam.put("userno", cs.getInt("authmemberno"));
		if (dbParam.getInt("userno")==0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("로그인 후 가능합니다.");
			return result;
		}
		resultMap.put("userinfo", memberMapper.selectMember(dbParam));

		return new Response(resultMap);
	}

	/**
	 * 회원정보 수정
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Response saveUserInfo(SOMap param)  throws Exception{
		Response result = new Response();
		result.setErrorShow(false);
		SOMap dbParam = new SOMap();
		int resultCnt =0;
		
		dbParam.putAll(param);
		dbParam.put("userno", cs.getInt("authmemberno"));
		
		String nowpw = dbParam.getStr("nowpw");
		String newpw = dbParam.getStr("newpw");
		String newpw2 = dbParam.getStr("newpw2");
		
		//VALID CHECK
		if (dbParam.getInt("userno")==0) {
			result.setStatusCode(Status.UNAUTHORIZED.getKey());
			result.setMessage("로그인 후 가능합니다.");
			return result;
		}else if (dbParam.getStr("mobile") == "") {
			throw new Exception("연락처1을 입력해 주세요.");
		}else if (dbParam.getStr("email") == "") {
			throw new Exception("이메일을 입력해 주세요.");
		}else {
			//현재 비밀번호가 있을 경우 현재비밀번호 체크
			if (param.getStr("ispwchange").equals("T")) {
				//비밀번호 valid check
				if (Util.isEmpty(nowpw)) {
					result.setStatusCode(Status.FAIL.getKey());
					result.setMessage("현재 비밀번호를 입력해주세요.");
					return result;
				}else if(Util.isEmpty(newpw)) {
					result.setStatusCode(Status.FAIL.getKey());
					result.setMessage("새 비밀번호를 입력해주세요.");
					return result;
				}else if(Util.isEmpty(newpw2)) {
					result.setStatusCode(Status.FAIL.getKey());
					result.setMessage("확인 비밀번호를 입력해주세요.");
					return result;
				}else if (!Util.isPassword(newpw)) {
					result.setStatusCode(Status.FAIL.getKey()); // 비밀번호가 유효하지 않을때,
					result.setMessage("최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 또는 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합으로 입력해야합니다.");
					return result;
				}else if (!newpw.equals(newpw2)) { //변경 비밀번호와 변경 비밀번호 확인 번호가 일치하는지 체크
					result.setStatusCode(Status.FAIL.getKey()); // 비밀번호가 유효하지 않을때,
					result.setMessage("변경 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
					return result; 
				}else{
					String encNowpw = CryptHash.hash(nowpw);//현재 비밀번호 암호화
					String encNewpw = CryptHash.hash(newpw);//신규 비밀번호 암호화

					//기존 비밀번호 조회
					dbParam.put("no", cs.getStr("authmemberno"));
					SOMap oldPwMap = userMapper.selectUserAll(dbParam);
					
					if (oldPwMap == null) {
						result.setStatusCode(Status.FAIL.getKey());
						result.setMessage("처리오류. 현재 비밀번호 정보가 없습니다.");
						return result;
					}
					if (oldPwMap != null && Util.isEmpty(oldPwMap.get("userpw"))) {
						result.setStatusCode(Status.FAIL.getKey());
						result.setMessage("처리오류. 현재 비밀번호 정보가 없습니다.");
						return result;
					}
					String oldPw = oldPwMap.getDbStr("userpw");//기존 db 비밀번호
					
					//front 현재비밀번호와 db비밀번호가 일치하는지 체크
					if (!oldPw.equals(encNowpw)) {
						result.setStatusCode(4221); // 현재비밀번호가 일치하지 않을때,
						result.setMessage("현재 비밀번호가 일치하지 않습니다.");
						return result;
					}
					
					//신규비밀번호와 기존 비밀번호가 일치하는지 체크
					if (oldPw.equals(encNewpw)) {
						result.setStatusCode(Status.FAIL.getKey());
						result.setMessage("신규비밀번호가 이전 비밀번호와 같습니다. 다른 비밀번호를 입력해주세요.");
						return result;
					}
					
					SOMap userParams = new SOMap();
					userParams.put("no", cs.getInt("authmemberno"));
					userParams.put("userpw", encNewpw);
					resultCnt = userMapper.updateUser(userParams);
					
					if (resultCnt != 1) {
						result.setStatusCode(Status.FAIL.getKey());
						result.setMessage("비밀번호 변경에 실패했습니다.");
					}
				}
			}
		}
		//회원타입이 일반회원 또는 평생회원일경우
		if (dbParam.getStr("dadamembertype").equals("DMT001") || dbParam.getStr("dadamembertype").equals("DMT002")) {
			if (dbParam.getStr("islifemember").equals("T")) {
				if (!dbParam.getStr("isadmailing").equals("T") || !dbParam.getStr("isadsms").equals("T")) {
					result.setStatusCode(Status.FAIL.getKey());
					result.setMessage("평생회원 가입은 광고 정보 수신 동의를 모두 동의해야 합니다.");
					return result;
				}
				dbParam.put("dadamembertype", "DMT002");
			}else {
				dbParam.put("dadamembertype", "DMT001");
			}
		}
		//회원정보 수정
		resultCnt = memberMapper.updateMember(dbParam);
		if (resultCnt != 1) {
			result.setStatusCode(Status.FAIL.getKey());
			result.setMessage("회원정보 수정에 실패했습니다.");
		}
		
		return result;
	}

	/**
	 * 나의등급 조회
	 */
	@Override
	public SOMap selectMyGradeInfo(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));
		/*******************************************************
		 * 사용자 6개월간 구매 금액 및 건수 조회
		 *******************************************************/
		SOMap orderInfo = comOrderMapper.selectUserOrderInfo(param);
		resultMap.put("orderinfo", orderInfo);
		return resultMap;
	}

	/**
	 * 메시지 목록 조회
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap selectMyMsgList(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));
		/*************************
		 * 페이징 처리
		 *************************/
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		/****************************
		 * 메시지 목록 조회
		 ****************************/
		List<SOMap> msgList = memberMsgMapper.selectMyMsgList(param);
		int listTotal = memberMsgMapper.selectMyMsgListCnt(param);
		resultMap.put("messagelist", msgList);
		resultMap.put("listtotal", listTotal);
		/****************************
		 * 메시지 읽음 처리
		 ****************************/
		List<Integer> msgidxList = new ArrayList<Integer>(); 
		for(SOMap msg : msgList) {
			if (msg.getStr("isread").equals("F")) {
				msgidxList.add(msg.getInt("msgidx"));
			}
		}
		param.put("msgidxlist", msgidxList);
		memberMsgMapper.updateMyMsgRead(param);
		
		return resultMap;
	}

	/**
	 * 메시지 목록 삭제
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap deleteMyMsg(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		int resultCnt = 0;
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 이용해주세요.");
		}
	    resultCnt = memberMsgMapper.updateMyMsgDel(param);
	    
	    if (resultCnt <= 0) {
	    	throw new BizException("삭제에 실패했습니다.");
		}
		
		return resultMap;
	}

	@Override
	public SOMap applyAs(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("userno",cs.getInt("authmemberno"));
		param.put("siteid", cs.getStr("siteid"));
		param.put("imgtype",Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		SOMap orderGoods = comOrderGoodsMapper.selectComOrderGoods(param);
		param.put("orderidx", orderGoods.get("orderidx"));
		param.put("ordno", orderGoods.get("ordno"));
		
		//로그인 권한 체크 ( URL 직접 접근 제한 )
		if(param.getInt("userno") == 0){
			if(!param.getStr("orderidx").equals(cs.getStr("authguestorderidx"))){
				result.put("auth", "fail");
				return result;
			}
		}else{
			if(param.getInt("userno") != comOrderMapper.selectComOrderInfo(param).getInt("userno")){
				result.put("auth", "fail");
				return result;				
			}
		}
		
		result.put("auth", "success");
		result.put("list", comOrderGoodsMapper.selectComOrderGoods(param));
		return result;
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})	
	public SOMap applySave(SOMap param, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap result = new SOMap();
		
		param.put("userno",cs.getInt("authmemberno"));
		param.put("siteid", cs.getStr("siteid"));
		
		param.put("membertype",cs.getStr("authmembertype"));
		param.put("memlvtype",cs.getStr("authmemlvtype"));
		param.put("ordno", comOrderMapper.selectComOrderInfo(param).get("ordno"));
		param.put("dealerno", goodsMapper.selectGoodsJoinGoodsContent(param).get("dealerno"));

		Util util = new Util();
		String badWordList = bannedMapper.badWordList();
		
		String filterText = util.filterText(badWordList, param.getStr("content"));
		if(!filterText.equals("")){
			throw new BizException("금칙어가 포함되어 있습니다. " + filterText);
		}

		String content = HtmlUtils.htmlEscape(param.getStr("content"));
		content = content.replaceAll("\n", "<br/>");
		param.put("content", content);
		
		int save = asInquiryMapper.insertAsApplySave(param);
		int asidx = 0;
		if(save > 0){
			result.put("msg", "success");
			asidx = param.getInt("asidx");
			result.put("asidx", asidx);
			result.put("ordno", param.get("ordno"));
			result.put("userno", param.get("userno"));
		}else{
			result.put("msg", "fail");			
		}
		
		if(asidx != 0){
			//이미지 처리
			/*********************************
			 * 파일 업로드 처리
			 *********************************/
			for (String key : uploadFile.keySet()) {
				MultipartFile file = uploadFile.get(key);
				if (key.contains("filelist")) {
					if (file.getContentType().contains("image")) {
						fileService.uploadImage(file, asidx, CMConst.IMG_TYPE_AS_INQUIRY);
					}else if (file.getContentType().contains("video")) {
						fileService.uploadMov(file, asidx, CMConst.IMG_TYPE_AS_INQUIRY);
					}else{
						throw new Exception("지원하지 않는 파일 형식입니다.");
					}
				}
			}
			/************************************
			 * 파일  삭제 처리
			 ***********************************/
			List<Integer> deleteFiles = param.getArrayList("deletefilelist");
			for(int fileIdx : deleteFiles) {
				fileService.delete(fileIdx);
			}					
		}
	
		return result;
	}

	@Override
	public SOMap applyList(SOMap param) throws Exception {
		SOMap result = new SOMap();
		
		param.put("userno",cs.getInt("authmemberno"));
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 이용해주세요.");
		}
		param.put("siteid", cs.getStr("siteid"));
		param.put("membertype",cs.getStr("authmembertype"));
		param.put("memlvtype",cs.getStr("authmemlvtype"));
		param.put("imgtype",Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		
		/*************************
		 * 페이징 처리
		 *************************/
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		if(Util.flag2Bool(param.getDbStr("init")) && !cs.getStr("platform").equals("MAC001")) {
			param.put("startpage", 0);
			param.put("endpage", startPage + param.getInt("listcnt"));
		}
		
		int listtotal = 0;
		List<SOMap> orderList = asInquiryMapper.selectAsApplyList(param);
		if(orderList.size() > 0) {
			listtotal = orderList.get(0).getDbInt("totcnt");
		}
		
		result.put("list", orderList);
		result.put("listcnt", listtotal);
		
		return result;
	}

	@Override
	public int applyListCount(SOMap param) throws Exception {
		return asInquiryMapper.selectAsApplyListCount(param);
	}

	@Override
	public SOMap applyDetail(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 이용해주세요.");
		}
		param.put("imgtype",Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		
		result.put("list",asInquiryMapper.selectAsApplyDetail(param));
		
		String[] imgtypes = {CMConst.IMG_TYPE_AS_INQUIRY};
		SOMap dbParam = new SOMap();
		dbParam.put("imgtypes", imgtypes);
		 
		dbParam.put("orgidx", param.get("asidx"));
		List<SOMap> uploadedfileList = fileMapper.selectFileList(dbParam);

		
		result.put("files", uploadedfileList);
		
		return result;
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})		
	public SOMap applyDelete(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("userno",cs.getInt("authmemberno"));
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 이용해주세요.");
		}

		param.put("asstatus", CMConst.AS_CANCEL);
		int delete = asInquiryMapper.deleteAsApply(param);
		
		if(delete > 0){
			result.put("msg", "success");
		}else{
			result.put("msg", "fail");
		}
		return result;
	}
	
	/****************
	 * 프로모션 코드 등록 처리
	 *****************/
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap addPromotionCode(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("userid", cs.getStr("authmemberid"));
		param.put("membertype",cs.getStr("authmembertype"));
		param.put("memlvtype",cs.getStr("authmemlvtype"));
		
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 이용해주세요.");
		}
		if (param.getStr("serialno") == "") {
			throw new BizException("프로모션 코드를 입력해주세요.");
		}
		
		
		/***********************************
		 * 프로모션 코드 유효성 체크
		 *************************************/
		SOMap serialinfo = serialPromoMapper.selectFrontSerialInfo(param);
		if (serialinfo == null) {
			throw new BizException("입력하신 코드는 잘못된 코드입니다. 다시 입력해주세요.");
		}
		String srptypenm = "";
		if (param.getStr("srptype").equals(CMConst.SRPTYPE_SRT_SAVE)) {
			srptypenm = "적립금";
		}else if (param.getStr("srptype").equals(CMConst.SRPTYPE_SRT_EPOINT)) {
			srptypenm = "D포인트";
		}else if (param.getStr("srptype").equals(CMConst.SRPTYPE_SRT_COUPON)) {
			srptypenm = "쿠폰";
		}else {
			throw new BizException("시리얼 프로모션 혜택구분정보가 없습니다.");
		}
		if (!serialinfo.getStr("srptype").equals(param.getStr("srptype"))) {
			throw new BizException(srptypenm + "프로모션 코드가 아닙니다.");
		}
		if (serialinfo.getStr("bptype").equals("종료")) {
			throw new BizException("입력하신 코드는  기한이 만료된 코드입니다.");
		}
		/***********************************
		 *발급가능 횟수 체크 및 발급 여부 확인
		 *************************************/
		int memeberCnt = 0;
		if (serialinfo.getStr("isequalserial").equals("F")) {
			//하나의 시리얼프로모션에 여러개의 코드가 발급되고  각 한명의 사용자가 사용=> 모든 사용자 중 사용했는지 체크
			if (serialinfo.getInt("usecnt") >= serialinfo.getInt("issuecnt")) {
				throw new BizException("프로모션 발급 수량이 모두 소진되었습니다.");
			}
			SOMap dbpram = new SOMap();
			dbpram.put("serialno", param.getStr("serialno"));
			memeberCnt = serialNoMapper.selectFrontUserSerialCnt(dbpram);
		}else {
			//하나의 시리얼프로모션 하나의 코드가 발급되고  여러사용자가 사용=> 내가 사용했는지 체크
			if (serialinfo.getInt("usecnt") >= serialinfo.getInt("dupcnt")) {
				throw new BizException("프로모션 발급 수량이 모두 소진되었습니다.");
			}
			memeberCnt = serialNoMapper.selectFrontUserSerialCnt(param);
		}
		if (memeberCnt > 0) {
			throw new BizException("입력하신 코드는 이미 등록된 코드입니다.");
		}
		/*************************************
		 *시리얼  프로모션  보상 지급 
		 **************************************/
		SOMap serialParam = new SOMap();
		String startDay = "";
		String endDay = "";
		/***********
		 * 쿠폰 지급 처리
		 ************/
		if (serialinfo.getStr("srptype").equals(CMConst.SRPTYPE_SRT_COUPON)) {
			serialParam.put("comcpnidx",  serialinfo.getInt("couponidx"));
			serialParam.put("siteid", cs.getStr("siteid"));
			SOMap couponInfo = couponMapper.selectCouponByComcpnidx(serialParam);
			//발급중인것만 
			if (!couponInfo.getStr("cpnissuest").equals(CMConst.CPN_ISSUE_ST_ISSUE)) {
				throw new BizException("발급중인 쿠폰이 아닙니다.");
			}
			//쿠폰사용기간종류에 
			if (couponInfo.getStr("cpnusetype").equals(CMConst.CPN_USE_PERIOD)) { // 사용기간
				startDay = couponInfo.getStr("cpnusestday");
				endDay = couponInfo.getStr("cpnuseedday");
			}else if (couponInfo.getStr("cpnusetype").equals(CMConst.CPN_USE_LAST_DAY)) {
				startDay = DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
				endDay = DateTimeUtil.getMonthEndDate(startDay);
			}else if (couponInfo.getStr("cpnusetype").equals(CMConst.CPN_USE_DAY_CNT)) {
				startDay = DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
				endDay = DateTimeUtil.addDays(startDay, couponInfo.getInt("issuedaycnt"), DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
			}else {
				throw new BizException("쿠폰 사용기간 설정 정보가 없습니다.");
			}
			
			serialParam.put("cpninfoidx", couponInfo.getInt("cpninfoidx"));   // 쿠폰발급정보idx
			serialParam.put("userno", param.getInt("userno"));                // 회원번호
			serialParam.put("cpnusestday", startDay); // 쿠폰사용시작일
			serialParam.put("cpnuseedday", endDay);   // 쿠폰사용종료일
			serialParam.put("issuemembertype", param.getStr("membertype"));   // 발급시회원유형
			serialParam.put("issuememlvtype", param.getStr("memlvtype"));     // 발급시회원등급
			serialParam.put("isdownload", couponInfo.getStr("isautopay").equals("T")?"T":"F");                               // 다운로드여부
			serialParam.put("issuedate", startDay+"00");
			serialParam.put("userid", param.getStr("userid"));
			couponMemissueMapper.insertCouponMemissue(serialParam);
		}
		/***********
		 * 적립금 지급 처리
		 ************/
		else if (serialinfo.getStr("srptype").equals(CMConst.SRPTYPE_SRT_SAVE)) {
			startDay = DateTimeUtil.getNowFormatStr(DateTimeUtil.MALL_DATE_FORMAT_VARCHAR12);
			endDay = DateTimeUtil.getPointEndDate(startDay);
			serialParam.put("siteid", cs.getStr("siteid"));                   
			serialParam.put("srpidx", serialinfo.getInt("srpidx"));           //시리얼프로모션IDX
			serialParam.put("userno", param.getInt("userno"));                //회원번호
			serialParam.put("isempreserve", "F");                             //임직원적립금 여부
			serialParam.put("resstday", startDay);                            //적립금유효시작일
			serialParam.put("resedday", endDay);                              //적립금유효종료일
			serialParam.put("respaytype", CMConst.RES_PAY_TYPE_SERIAL_PROMO); //적립금지급구분
			serialParam.put("paymembertype", param.getStr("membertype"));     //지급시회원유형
			serialParam.put("paymemlvtype", param.getStr("memlvtype"));       //지급시회원등급
			serialParam.put("paypoint", serialinfo.getStr("srppoint"));       //지급포인트
			serialParam.put("authuserid", param.getStr("userid"));            //등록자ID
			//reservePayMapper.insertReservePayAll(serialParam);
			commonService.paymentReserve(serialParam);
		} 
		/***********
		 * 이포인트 지급 처리
		 ************/
		else if (serialinfo.getStr("srptype").equals(CMConst.SRPTYPE_SRT_EPOINT)) {
			//시리얼 프로모션 이포인트 지급은 시리얼프로모션 기간과 같게 지급하라고 amin에 나와있음
			serialParam.put("siteid", cs.getStr("siteid"));
			serialParam.put("srpidx", serialinfo.getInt("srpidx"));           //시리얼프로모션IDX
			serialParam.put("userno", param.getInt("userno"));                //회원번호
			serialParam.put("epostday", serialinfo.getStr("srpstday"));            //이포인트유효시작일
			serialParam.put("epoedday", serialinfo.getStr("srpedday"));            //이포인트유효종료일
			serialParam.put("epopaytype", CMConst.EPO_PAY_SERIAL_PROMO);      //이포인트지급구분
			serialParam.put("paymembertype", param.getStr("membertype"));     //지급시회원유형
			serialParam.put("paymemlvtype", param.getStr("memlvtype"));       //지급시회원등급
			serialParam.put("paypoint", serialinfo.getStr("srppoint"));       //지급포인트
			serialParam.put("authuserid", param.getStr("userid"));            //등록자ID
			serialParam.put("isepointdup", serialinfo.getStr("isepointdup")); //등록자ID
			//epointpaymapper.insertEpointPay(serialParam);
			commonService.paymentEpotint(serialParam);
		}else {
			throw new BizException("지급구분정보가 없습니다.");
		}
		serialNoMapper.updateUseCnt(param);
		serialMemberMapper.insertSerialMember(param);
		
		
		
		return resultMap;
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})	
	public SOMap applyRevise(SOMap param, Map<String, MultipartFile> uploadFile) throws Exception {
		SOMap result = new SOMap();
		
		param.put("userno",cs.getInt("authmemberno"));
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 이용해주세요.");
		}
				
		/************************************
		 * 파일  삭제 처리
		 ***********************************/
		List<Integer> deleteFiles = param.getArrayList("deletefilelist");
		for(int fileIdx : deleteFiles) {
			fileService.delete(fileIdx);
		}
		//이미지 처리
		/*********************************
		 * 파일 업로드 처리
		 *********************************/
		for (String key : uploadFile.keySet()) {
			MultipartFile file = uploadFile.get(key);
			if (key.contains("filelist")) {
				if (file.getContentType().contains("image")) {
					fileService.uploadImage(file, param.getDbInt("asidx"), CMConst.IMG_TYPE_AS_INQUIRY);
				}else if (file.getContentType().contains("video")) {
					fileService.uploadMov(file, param.getDbInt("asidx"), CMConst.IMG_TYPE_AS_INQUIRY);
				}else{
					throw new Exception("지원하지 않는 파일 형식입니다.");
				}
			}
		}
		
		Util util = new Util();
		String badWordList = bannedMapper.badWordList();
		
		String filterText = util.filterText(badWordList, param.getStr("content"));
		if(!filterText.equals("")){
			throw new BizException("금칙어가 포함되어 있습니다. " + filterText);
		}

		String content = HtmlUtils.htmlEscape(param.getStr("content"));
		content = content.replaceAll("\n", "<br/>");
		param.put("content", content);
		
		int revise = asInquiryMapper.updateAsApply(param);
		
		if(revise > 0){
			result.put("msg", "success");
		}else{
			result.put("msg", "fail");
		}
		
		return result;
	}

	@Override
	public List<SOMap> applyMyOrder(SOMap params) throws Exception {
		params.put("userno",cs.getInt("authmemberno"));
		if (params.getInt("userno")== 0) {
			throw new BizException("로그인 후 이용해주세요.");
		}		
		params.put("asinquiry", "T");
		params.put("state", CMConst.ORDER_PURCH_CONFIRM);
		params.put("imgtype",Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GOODS_IMG_MO_M : CMConst.IMG_TYPE_GOODS_IMG_PC_M);		
		
		return comOrderMapper.selectMyOrderList(params);
	}
	
	
	/**
	 * 친구초대 보상정보 조회
	 */
	@Override
	public SOMap selectRewardInfo(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		SOMap rewardInfo = recomRewardLogMapper.selectSignupRewardLog(param);
		resultMap.put("rewardinfo", rewardInfo);
		return resultMap;
	}

	/**
	 * 회원 적립금, 포인트, 쿠폰 지급내역 조회
	 */
	@Override
	public SOMap selectMyPayInfo(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));
		param.put("siteid", cs.getStr("siteid"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인이 필요합니다.");
		}
		SOMap payInfo = memberMapper.selectMemberEtcInfo(param); 
		resultMap.put("payinfo", payInfo);
		return resultMap;
	}
	
	/**
	 * 주문배송조회
	 */
	@Override
	public SOMap selectDeliveryTracking(SOMap param) throws Exception{
		SOMap resultMap = new SOMap();
		SOMap orderInfo = comOrderMapper.selectComOrderInfo(param);
		List<SOMap> delivList = comOrderGoodsDelivMapper.selectOrderInvoiceList(param);
		
		param.put("invoiceno", delivList.get(0).getDbStr("invoiceno"));
		param.put("logistype", delivList.get(0).getDbStr("logiscode"));
		
		SOMap tracking = deliveryTrackingService.getDeliveryTrackingData(param); 
		
		resultMap.put("orderinfo", orderInfo);
		resultMap.put("list", delivList);
		resultMap.put("tracking", tracking);
		
		return resultMap;
	}
	
	/**
	 * 마이페이지_주문목록 조회
	 */
	@Override
	public SOMap selectOrderList(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		if (Util.flag2Bool(cs.getStr("ismobile"))) {
			param.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_MO_M);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		}
		
		//로그인 체크
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 가능합니다.");
		}
		
		/*************************
		 * 페이징 처리
		 *************************/
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		if(Util.flag2Bool(param.getDbStr("init"))) {
			param.put("startpage", 0);
			param.put("endpage", startPage + param.getInt("listcnt"));
		}
		int listtotal = 0;
		List<SOMap> orderList = comOrderMapper.selectMyOrderList(param);
		if(orderList.size() > 0) {
			listtotal = orderList.get(0).getDbInt("totcnt");
		}
		resultMap.put("list", orderList);
		resultMap.put("listtotal", listtotal);
		return resultMap;
	}
	
	/**
	 * 마이페이지_주문상세 내역 조회
	 */
	@Override
	public SOMap selectOrderDetail(SOMap param) throws Exception {
		
		SOMap resultMap = new SOMap();
		
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		
		
		if (Util.flag2Bool(cs.getStr("ismobile"))) {
			param.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_MO_M);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		}
		
		//로그인 체크
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 가능합니다.");
		}
		
		//주문정보
		param.put("isuser", "T");
		SOMap orderInfo = comOrderMapper.selectComOrderInfo(param);
		if(orderInfo == null) {
			throw new BizException("주문내역이 없습니다.");
		}
		
		param.put("membertype", orderInfo.getDbStr("dadamembertype"));
		param.put("memlvtype", orderInfo.getDbStr("memlvtype"));
		param.put("orderidx", orderInfo.getDbInt("orderidx"));
		param.put("status", "COMPLETE");
		
		//주문상품 목록
		List<SOMap> orderGoods = comOrderGoodsMapper.selectComOrderGoodsList(param);
		//클레임정보 목록
		List<SOMap> claimList = claimMapper.selectClaimListForPrev(param);
		//클레임상품 목록
		List<SOMap> claimGoods = claimGoodsMapper.selectMyClaimGoodsList(param);
		//사은품
		param.put("imgtype", Util.flag2Bool(cs.getStr("ismobile")) ? CMConst.IMG_TYPE_GIFT_IMG_MO_B : CMConst.IMG_TYPE_GIFT_IMG_PC_B);
		List<SOMap> giftList = claimGoodsGiftMapper.selectClaimGoodsGiftWithoutClaim(param);
		
		//결제정보
		SOMap payInfo = tossreceiptMapper.selectTossreceipt(param);
		if(payInfo == null) {
			payInfo = new SOMap();
		}
		resultMap.put("order", orderInfo);
		resultMap.put("ordergoods", orderGoods);
		resultMap.put("claim", claimList);
		resultMap.put("claimgoods", claimGoods);
		resultMap.put("giftlist", giftList);
		resultMap.put("payinfo", payInfo);
		return resultMap;
	}
	
	/**
	 * 마이페이지_클레임목록 조회
	 */
	@Override
	public SOMap selectClaimList(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		if (Util.flag2Bool(cs.getStr("ismobile"))) {
			param.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_MO_M);
		} else {
			param.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		}
		
		//로그인 체크
		if (param.getInt("userno")== 0) {
			throw new BizException("로그인 후 가능합니다.");
		}
		
		/*************************
		 * 페이징 처리
		 *************************/
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		if(Util.flag2Bool(param.getDbStr("init")) && !cs.getStr("platform").equals("MAC001")) {
			param.put("startpage", 0);
			param.put("endpage", startPage + param.getInt("listcnt"));
		}
		int listtotal = 0;
		List<SOMap> orderList = claimMapper.selectMyClaimList(param);
		if(orderList.size() > 0) {
			listtotal = orderList.get(0).getDbInt("totcnt");
		}
		resultMap.put("list", orderList);
		resultMap.put("listtotal", listtotal);
		return resultMap;
	}
	
	@Override
	public SOMap selectClaimApply(SOMap params) throws Exception{
		if(CMConst.CLM_CANCEL.equals(params.getDbStr("clmtype"))) {
			params.put("cmclass", "CNCTYPE");
		} else if(CMConst.CLM_RETURN.equals(params.getDbStr("clmtype"))) {
			params.put("cmclass", "RTNTYPE");
		} else {
			params.put("cmclass", "EXCTYPE");
		}
		
		//클레임 계산
		SOMap result = commonClaimService.selectClaimApply(params);
				
				
		//취소/반품/교환사유
		params.put("istrash", "F");
		List<SOMap> claimReason = commonCodeMapper.selectCodeList(params);
		result.put("rslist", claimReason);
				
		
		if(CMConst.PAYWAY_TYPE_VIRACCOUNT.equals(result.getSOMap("bforder").getDbStr("paywaytype"))) {
			//환불은행
			params.put("cmclass", "TBANKTYPE");
			List<SOMap> reqlist = commonCodeMapper.selectCodeList(params);
			result.put("banklist", reqlist);
		} else {
			result.put("banklist", new ArrayList<SOMap>());
		}
		
		if(CMConst.CLM_RETURN.equals(params.getDbStr("clmtype")) || CMConst.CLM_EXCHANGE.equals(params.getDbStr("clmtype"))) {
			//배송요청사항
			params.put("cmclass", "RCVREQTYPE");
			List<SOMap> reqlist = commonCodeMapper.selectCodeList(params);
			result.put("reqlist", reqlist);
		} else {
			result.put("reqlist", new ArrayList<SOMap>());
		}
		
		//결제수단
		params.put("cmclass", "PAYWAYTYPE");
		List<SOMap> paywaylist = commonCodeMapper.selectCodeList(params);
		/**
		 * 적립금은 결제수단에 나오면 안되므로 삭제(하드코딩)
		 * (James, 2022-11-09)
		 */
		int index = 0;
		for(SOMap dto : paywaylist) {
			if(dto.get("codename").equals("적립금")) {
				paywaylist.remove(index);
				break;
			}
			index++;
		}
		result.put("paywaylist", paywaylist);
		
		//카드종류
		params.put("cmclass", "CARDCOMPANY");
		List<SOMap> cardlist = commonCodeMapper.selectCodeList(params);
		result.put("cardlist", cardlist);
		
		return result;
	}
	
	@Override
	public SOMap calClaimApply(SOMap params) throws Exception{
		return commonClaimService.selectClaimApply(params);
	}

	@Override
	public SOMap saveClaimApply(Map<String, MultipartFile> files, SOMap params) throws Exception{
		return commonClaimService.saveClaimApply(files, params);
	}
	
	@Override
	public SOMap detailClaim(SOMap params) throws Exception{
		SOMap resultMap = new SOMap();
		
		params.put("siteid", cs.getStr("siteid"));
		params.put("userno", cs.getInt("authmemberno"));
		params.put("isuser", "T");
		
		//클레임정보
		SOMap claim = claimMapper.selectClaimInfo(params);
		if(claim == null) {
			throw new BizException("클레임 내역이 존재하지 않습니다.");
		}
		
		//클레임 상품정보
		params.put("clmidx", claim.getDbInt("clmidx"));
		if (Util.flag2Bool(cs.getStr("ismobile"))) {
			params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_MO_M);
		} else {
			params.put("imgtype", CMConst.IMG_TYPE_GOODS_IMG_PC_M);
		}
		
		List<SOMap> claimGoodsList = claimGoodsMapper.selectMyClaimGoodsList(params);
		
		SOMap dbParam = new SOMap();
		dbParam.put("orgidx",  claim.getDbInt("clmidx"));
		dbParam.put("imgType",  CMConst.IMG_TYPE_CLAIM);
		List<SOMap> uploadedfileList = fileMapper.selectFileList(dbParam);
		List<SOMap> imgList = new ArrayList<SOMap>();
		List<SOMap> movList = new ArrayList<SOMap>();
		List<SOMap> movImgList = new ArrayList<SOMap>();
		
		for(SOMap files : uploadedfileList) {
			if (CMConst.IMG_TYPE_CLAIM.equals(files.getStr("imgtype"))) {
				imgList.add(files);
			}else {
				if (CMConst.FILE_TYPE_IMG.equals(files.getStr("filetype"))) {
					movImgList.add(files);
				}else {
					movImgList.add(files);
				}
			}
		}
		dbParam.put("files", uploadedfileList);
		dbParam.put("imglist", imgList);
		dbParam.put("movlist", movList);
		dbParam.put("movimglist", movImgList);
	
		//주문정보
		params.put("orderidx", claim.getDbInt("orderidx"));
		SOMap order = comOrderMapper.selectComOrderInfo(params);
		
		if(claim.getDbInt("addrpaytotprice") > 0) {
			//결제수단
			params.put("istrash", "F");
			params.put("cmclass", "PAYWAYTYPE");
			List<SOMap> paywaylist = commonCodeMapper.selectCodeList(params);
			/**
			 * 적립금은 결제수단에 나오면 안되므로 삭제(하드코딩)
			 * (James, 2022-11-09)
			 */
			int index = 0;
			for(SOMap dto : paywaylist) {
				if(dto.get("codename").equals("적립금")) {
					paywaylist.remove(index);
					break;
				}
				index++;
			}
			
			//카드종류
			params.put("cmclass", "CARDCOMPANY");
			List<SOMap> cardlist = commonCodeMapper.selectCodeList(params);
			resultMap.put("paywaylist", paywaylist);
			resultMap.put("cardlist", cardlist);
		} else {
			resultMap.put("paywaylist", new ArrayList<SOMap>());
			resultMap.put("cardlist", new ArrayList<SOMap>());
		}
		
		List<SOMap> giftList = claimGoodsGiftMapper.selectClaimGoodsGift(params);
		
		resultMap.put("order", order);
		resultMap.put("claim", claim);
		resultMap.put("list", claimGoodsList);
		resultMap.put("giftlist", giftList);
		resultMap.put("item", dbParam);
		
		return resultMap;
	}
	
	/**
	 * 주문결재정보 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public SOMap saveClaimPayment(SOMap param) throws Exception{
		
		param.put("clmprctype", "CPR002");
		
		return commonClaimService.paymentClaim(param);
	}
	
	/**
	 * 마이페이지_클레임철회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public SOMap cancelClaim(SOMap param) throws Exception {
		return commonClaimService.saveClaimStatus(param);
	}
	
	@Override
	public SOMap updateOrderRcvInfo(SOMap params) throws Exception{
		
		SOMap result = new SOMap();
		commonOrderService.updateOrderRcvInfo(params);
		
		return result;
	}



	/**
	 * 리워즈 지급애역 조회
	 */
	@Override
	public SOMap rewardpayList(SOMap param) throws Exception {
		SOMap resultMap  = new SOMap();
		
		param.put("userno", cs.getInt("authmemberno"));
		param.put("siteid", cs.getString("siteid"));
		
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		SOMap inviteinfo = recomRewardMapper.selectInviteInfo(param);
		if (inviteinfo != null) {
			resultMap.put("membercnt", inviteinfo.get("membercnt"));
			resultMap.put("reclimitcnt", inviteinfo.get("reclimitcnt"));
		}
		List<SOMap> rewardpayList  = recomRewardMapper.selectUserRewardPayList(param);
		int totcnt = recomRewardMapper.selectUserRewardPayListCnt(param);
		
		
		
		resultMap.put("rewardpaylist", rewardpayList);
		resultMap.put("totcnt", totcnt);
		return resultMap;
	}


	/**
	 * 이전주문내역조회
	 */
	@Override
	public SOMap selectPreOrder(SOMap param) throws Exception {
		SOMap resultMap = new SOMap();
		param.put("userno", cs.getInt("authmemberno"));
		param.put("siteid", cs.getStr("siteid"));
		if (param.getInt("userno") == 0) {
			throw new BizException("로그인후 이용해주세요.");
		}
		int page = param.getInt("currentpage");
		int startPage = (page > 1) ? ((page -1 ) * param.getInt("listcnt")) : 0;
		param.put("startpage", startPage);
		param.put("endpage", param.getInt("listcnt"));
		
		List<SOMap> preOrderList = prevComorderMapper.selectPrevComorder(param);
		int listtotal = 0;
		if(preOrderList.size() > 0) {
			listtotal = preOrderList.get(0).getDbInt("totcnt");
		}
		resultMap.put("preorderlist", preOrderList);
		resultMap.put("listtotal", listtotal);
		return resultMap;
	}
	
	/**
	 * 클레임 정보조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public SOMap selectClaimInfo(SOMap param) throws Exception{
		param.put("siteid", cs.getStr("siteid"));
		param.put("userno", cs.getInt("authmemberno"));
		param.put("isuser", "T");
		
		SOMap claimInfo = claimMapper.selectClaimInfo(param);
		
		if(claimInfo == null) {
			throw new BizException("클레임 내역이 존재하지 않습니다.");
		}
		
		return claimInfo;
	}
}
