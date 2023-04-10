package com.dplot.common;

import com.dplot.admin.service.adjust.AdminPartnersAdjustService;
import com.dplot.common.service.*;
import com.dplot.mapper.BatchMapper;
import com.dplot.mapper.ConfigMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.HttpConnectionUtil;
import com.dplot.util.Util;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;

@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private BatchMapper batchMapper;

    @Autowired
    private DeliveryTrackingService deliveryTrackingService;

    @Autowired
    private CJMessageService cjMessageService;

    @Autowired
    private CommonOrderService commonOrderService;

    @Autowired
    private AdminPartnersAdjustService adminPartnersAdjustService;

    @Autowired
    private ERPService erpService;

    @Autowired
    private CommonService commonService;

    @Resource(name="propertiesFactory")
    private Properties prop;

    /**
     * 배송 상태 실시간 처리 15분 마다
     */
//    @Scheduled(cron = "0 */15 * * * *")
//    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "callReceiveTraceResult")
    public void callReceiveTraceResult() {
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");
        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);

                deliveryTrackingService.receiveTraceResult(param);
                deliveryTrackingService.receiveTransResult(param);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 매분마다 배치 수행
     * 쿠폰상태변경
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "updateCouponStateProcess")
    public void updateCouponState() {
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                //프로시저 호출 SP_MEMBER_POINT_EXTINCT
                batchMapper.spCouponStateUpdate(param);
                logger.debug(param.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 매분마다 배치 수행
     * 적립금/D포인트 소멸처리
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "memberPointExtinctProcess")
    public void memberPointExtinctProcess() {
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                //프로시저 호출 SP_MEMBER_POINT_EXTINCT
                batchMapper.spMemberPointExtinct(param);
                logger.debug(param.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 매분마다 배치 수행
     * 적립금 예약 포인트 지급
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "memberReservePayProcess")
    public void memberReservePayProcess() {
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                //프로시저 호출 SP_MEMBER_POINT_EXTINCT
                batchMapper.spInsertReservePayPoint(param);
                logger.debug(param.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 매분마다 배치 수행
     * e포인트 예약 포인트 지급
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "memberEPointPayProcess")
    public void memberEPointPayProcess() {
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                //프로시저 호출 SP_MEMBER_POINT_EXTINCT
                batchMapper.spInsertEPointPayPoint(param);
                logger.debug(param.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 매시간마다 배치 수행
     * 전시기간에 따른 상품 판매 상태 업데이트 처리
     * T_GOODS, GOODSSELLTYPE
     * GST001 : 판매대기, GST002 : 판매중, GST003 : 판매종료
     */
//    @Scheduled(cron = "0 0 0/1 * * *")
    @SchedulerLock(name = "UpdateGoodsStateUpdateByDisplay")
    public void updateGoodsStateUpdateByDisplay() {
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                //프로시저 호출 SP_UPDATE_GOODS_STATE_BY_DISPLAY
                batchMapper.spUpdateGoodsStateByDisplay(param);
                logger.debug(String.format("전시기간에 따른 상품 판매 상태 업데이트 %s건 처리", param.get("update_cnt")));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  매분마다 수행 품절 처리
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "updateGoodsStateUpdateByStock")
    public void updateGoodsStateUpdateByStock() {
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                //프로시저 호출 SP_UPDATE_GOODS_STATE_BY_DISPLAY
                batchMapper.spUpdateGoodsStateByStock(param);
                logger.debug(String.format("재고상태에 따른 상품 상태 업데이트 %s건 처리", param.get("update_cnt")));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //매일 00시 30분
    //생일회원 적립금 지급
//    @Scheduled(cron = "0 30 0 * * *")
    @SchedulerLock(name = "memberBirthdayPayPoint")
    public void memberBirthdayPayPoint(){
        SOMap configParam = new SOMap();
        try {
            String siteArrStr = prop.getProperty("schedule.site.id");
            String[] siteArr = siteArrStr.split(",");

            for(String siteId : siteArr){
                configParam.put("siteid", siteId);
                batchMapper.spMemberBirthdayPayPoint(configParam);
                logger.debug(String.format("회원 생일 적립금 지급 %s건 처리", configParam.get("insert_cnt")));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //매일 00시 30분
    //파트너사 3개월 미접속 휴점처리
//    @Scheduled(cron = "0 30 0 * * *")
    @SchedulerLock(name = "partnersStateUpdateAfterThreeMonthOfLogin")
    public void partnersStateUpdateAfterThreeMonthOfLogin(){
        SOMap configParam = new SOMap();
        try {
            String siteArrStr = prop.getProperty("schedule.site.id");
            String[] siteArr = siteArrStr.split(",");

            for(String siteId : siteArr){
                configParam.put("siteid", siteId);
                batchMapper.spUpdatePartnersStateByLastlogin(configParam);
                logger.debug(String.format("3개월 미접속 파트너사 휴점 %s건 처리", configParam.get("update_cnt")));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //매일 00시 30분
    //회원 11개월 미접속 휴면처리 메일 발송
//    @Scheduled(cron = "0 0 10 * * *")
//    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "memberDormancyNoticeEmailPush")
    public void memberDormancyNoticeEmailPush(){
        SOMap configParam = new SOMap();
        try {
            String siteArrStr = prop.getProperty("schedule.site.id");
            String[] siteArr = siteArrStr.split(",");

            for(String siteId : siteArr){
                configParam.put("siteid", siteId);

                SOMap result = cjMessageService.sendDormantAccount(configParam);
                logger.debug(result.toString());
                logger.debug(String.format("11개월 미접속 회원 휴면 메일발송 :::\n%s", result));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //매일 00시 30분
    //회원 1년 미접속 휴면처리
//    @Scheduled(cron = "0 30 0 * * *")
    @SchedulerLock(name = "memberDormancyUpdateAfterOneYearOfLogin")
    public void memberDormancyUpdateAfterOneYearOfLogin(){
        SOMap configParam = new SOMap();
        try {
            String siteArrStr = prop.getProperty("schedule.site.id");
            String[] siteArr = siteArrStr.split(",");

            for(String siteId : siteArr){
                configParam.put("siteid", siteId);
                batchMapper.spUpdateMemberStateByLastLogin(configParam);
                logger.debug(String.format("1년 미접속 회원 휴면 %s건 처리", configParam.get("update_cnt")));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //매일 00시 30분
    //휴면된지 2년이 지난 회원 탈퇴처리
//    @Scheduled(cron = "0 40 0 * * *")
    @SchedulerLock(name = "memberResignUpdateOfDormancy")
    public void memberResignUpdateOfDormancy(){
        SOMap configParam = new SOMap();
        try {
            String siteArrStr = prop.getProperty("schedule.site.id");
            String[] siteArr = siteArrStr.split(",");

            for(String siteId : siteArr){
                configParam.put("siteid", siteId);
                batchMapper.spDeleteMemberThatDormancy(configParam);
                logger.debug(String.format("휴면 회원 탈퇴 %s건 처리", configParam.get("update_cnt")));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //월 1회 인스타그램 인증 토큰 갱신
    //매월 1일 1시
//    @Scheduled(cron = "0 0 1 1 * *")
    @SchedulerLock(name = "monthlyInstaTokenUpdate")
    public void monthlyInstaTokenUpdate(){
        try{
            String instaFeedURL = prop.getProperty("instagram.feed.url");
            String siteArrStr = prop.getProperty("schedule.site.id");
            String[] siteArr = siteArrStr.split(",");

            for(String siteId : siteArr){
                SOMap soMap = batchMapper.selectConfigBySiteId(siteId);
                String instaToken = (soMap.containsKey("instatoken")) ? soMap.get("instatoken").toString() : "";
                String returnSiteId = (soMap.containsKey("siteid")) ? soMap.get("siteid").toString() : "";

                String url = String.format("%s/refresh_access_token?grant_type=ig_refresh_token&access_token=%s", instaFeedURL, instaToken);
                Map<String, Object> httpResult = HttpConnectionUtil.httpsRequest(url, "GET", null, null);
                if(httpResult != null && Status.OK.getKey() == Integer.parseInt(httpResult.get("httpCode").toString())){
                    SOMap param = new SOMap();
                    param.put("siteid", returnSiteId);
                    param.put("instatoken", httpResult.get("access_token"));
                    int instaUpdateCnt = configMapper.updateConfig(param);
                    logger.debug(String.format("INSTAGRAM AUTH TOKEN UPDATE CNT ::: %s", instaUpdateCnt));
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 굿스플로 서비스 이용 신청 결과를 일배치로 처리
     */
//    @Scheduled(cron = "0 0/1 * * * *")
//    @Scheduled(cron = "0 30 0 * * *")
    @SchedulerLock(name = "requestGoodsFlowApplyServiceUsageResult")
    public void requestGoodsFlowApplyServiceUsageResult(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                deliveryTrackingService.requestApplyServiceUsageResult(param);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 주문상태 업데이트
     */
//    @Scheduled(cron = "0 0/1 * * * *")
//    @Scheduled(cron = "0 0 9 * * *")
    @SchedulerLock(name = "orderStatusChange")
    public void orderStatusChange(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                batchMapper.spOrderStatusUpdate(param);
                commonService.batchOrderCompleteAndCancel(param);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 파트너사 동의현황 게시상태 업데이트
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "partnersAgreeStateUpdate")
    public void partnersAgreeStateUpdate(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                batchMapper.spUpdatePartnersAgreeState(param);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 2년마다(짝수년도) 12월 1일 1시 광고성 정보 수신내역 안내
     */
//    @Scheduled(cron = "0 0 1 1 12 *")
    @SchedulerLock(name = "adAgreeEmailSend")
    public void adAgreeEmailSend(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {

            int year = DateTimeUtil.getNowYear()%2;
            if(year != 1) {
                for (String siteId : siteArr) {
                    SOMap param = new SOMap();
                    param.put("siteid", siteId);
                    SOMap result = cjMessageService.sendADAgreeInfo(param);
                    logger.debug(result.toString());
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 매년 12월 1일 1시 개인정보이용내역 안내
     */
//    @Scheduled(cron = "0 0 1 1 12 *")
    @SchedulerLock(name = "personalInfoEmailSend")
    public void personalInfoEmailSend(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                SOMap result = cjMessageService.sendPersonalInfo(param);
                logger.debug(result.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 매월 1일 1시 소멸예정 적립금 안내
     */
//    @Scheduled(cron = "0 0 1 1 * *")
    @SchedulerLock(name = "extinctPoint")
    public void extinctPoint(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                SOMap result = cjMessageService.sendReserveExtinct(param);
                logger.debug(result.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 매주 토요일 0시 30분
     */
//    @Scheduled(cron = "0 30 0 * * 6")
    @SchedulerLock(name = "bestGoodsInsert")
    public void bestGoodsInsert(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                batchMapper.spBestGoodsInsert(param);
                logger.info(param.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 파트너 정산
     */
//    @Scheduled(cron = "0 30 0 1 * *")
    @SchedulerLock(name = "monthPartnersCalculate")
    public void monthPartnersCalculate(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                LocalDateTime date = LocalDateTime.now();
                LocalDateTime beforeMonth = LocalDateTime.now().minusMonths(1);
                DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyyMMdd");
                DateTimeFormatter nowFomatDate = DateTimeFormatter.ofPattern("yyyyMM");

                String calcMonth = beforeMonth.format(formatDate);
                String calcExpDay = date.format(nowFomatDate) + Integer.toString(DateTimeUtil.getLastMonthDay(date.format(formatDate)));

                param.put("siteid", siteId);
                param.put("calcmonth", calcMonth);
                param.put("calcexpday", calcExpDay);

                adminPartnersAdjustService.partnersCalculateProcess(param);
                logger.info(param.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

//    @Scheduled(cron = "0 0/1 * * * *")
//    @Scheduled(cron = "0 0 0/1 * * *")
    @SchedulerLock(name = "sendERPData")
    public void sendERPData(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                erpService.sendERPServiceForBatch(param);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  파트너사 지연 정보 발송
     */
//    @Scheduled(cron = "0 0/1 * * * MON-FRI")
//    @Scheduled(cron = "0 0 10 * * MON-FRI")
    @SchedulerLock(name = "sendPartnersDealyInfo")
    public void sendPartnersDealyInfo(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                cjMessageService.sendPartnersDelayInfo(param);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 재입고 알림
     */
//    @Scheduled(cron = "0 0/1 * * * *")
//    @Scheduled(cron = "0 0 0/1 * * *")
    @SchedulerLock(name = "sendRestockInfo")
    public void sendRestockInfo(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                cjMessageService.sendRestockInfo(param);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

//    @Scheduled(cron = "0 0/1 * * * *")
//    @Scheduled(cron = "0 0/10 * * * *")
    @SchedulerLock(name = "updateGoodsStock")
    public void updateGoodsStock(){
        String siteArrStr = prop.getProperty("schedule.site.id");
        String[] siteArr = siteArrStr.split(",");

        try {
            for (String siteId : siteArr) {
                SOMap param = new SOMap();
                param.put("siteid", siteId);
                if (!Util.equal(prop.getProperty("Globals.Profile"), "local")) {
                    commonService.updateGoodsAndGiftStock(param);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


	//매일 00시 00분
	//추천인리워드 첫구매확정시 리워드 지급
	@Scheduled(cron = "0 0 0 * * *")
	@SchedulerLock(name = "saveRewardConfirmOrder")
	public void saveRewardConfirmOrder(){
		SOMap configParam = new SOMap();
		try {
			String siteArrStr = prop.getProperty("schedule.site.id");
			String[] siteArr = siteArrStr.split(",");

			for(String siteId : siteArr) {
				configParam.put("siteid", siteId);
				configParam.put("type", "Reward");
				commonOrderService.saveRewardConfirmOrder(configParam);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//매일 08시 00분 ~ 20시 00분 10분 간격으로 배송시작 알림톡 발송
	@Scheduled(cron = "0 0/10 8-20 * * *")
	@SchedulerLock(name = "sendDeliveryKakaoMsg")
	public void sendDeliveryKakaoMsg(){
		try {
			String siteArrStr = prop.getProperty("schedule.site.id");
			String[] siteArr = siteArrStr.split(",");
			for(String siteId : siteArr) {
				SOMap param = new SOMap();
				param.put("siteid", siteId);
				param.put("type", "batch");
				cjMessageService.sendShippingOut(param);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 30 15 * * *")
	@SchedulerLock(name = "sendCpnExtinctionMsg")
	public void sendCpnExtinctionMsg(){
		try {
			String siteArrStr = prop.getProperty("schedule.site.id");
			String[] siteArr = siteArrStr.split(",");
			for(String siteId : siteArr) {
				SOMap param = new SOMap();
				param.put("siteid", siteId);
				param.put("type", "batch");
				cjMessageService.sendCpnExtinctionMsg(param);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
