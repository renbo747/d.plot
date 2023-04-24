<template>
  <!-- 컨텐츠 영역 -->
  <div class="content partner m-leftmenu">
    <div class="dashboard">
      <div class="condition">
        <div class="list">
          <div class="dash-title">
            <strong>판매현황(최근 1개월)</strong>
            <button type="button" class="dash-refresh" @click="onSearch('ORDER')"></button>
          </div>
          <ul>
            <li>
              <p>입금대기</p>
              <a class="link" @click="goRouterView('ORDER_WAIT')">{{ orderStatInfo.ods001 }}건</a>
            </li>
            <li>
              <p>상품준비중</p>
              <a class="link" @click="goRouterView('ORDER_PREPARING_GOODS')">{{ orderStatInfo.ods004 }}건</a>
            </li>
            <li>
              <p>배송준비중</p>
              <a class="link" @click="goRouterView('ORDER_PREPARING_DELIVERY')">{{ orderStatInfo.ods006 }}건</a>
            </li>
            <li>
              <p>배송중</p>
              <a class="link" @click="goRouterView('ORDER_DELIVERY')">{{ orderStatInfo.ods007 }}건</a>
            </li>
            <li>
              <p>배송완료</p>
              <a class="link" @click="goRouterView('ORDER_DELIVERY_COMPLETE')">{{ orderStatInfo.ods008 }}건</a>
            </li>
            <li>
              <p>구매확정</p>
              <a class="link" @click="goRouterView('ORDER_COMPLETE')">{{ orderStatInfo.ods009 }}건</a>
            </li>
          </ul>
        </div>
        <div class="list">
          <div class="dash-title">
            <strong>클레임현황(최근 1년)</strong>
            <button type="button" class="dash-refresh" @click="onSearch('CLAIM')"></button>
          </div>
          <ul>
            <li>
              <p>취소신청</p>
              <a class="link" @click="goRouterView('CLAIM_CANCEL')">{{ claimStatInfo.clm001 }}건</a>
            </li>
            <li>
              <p>반품신청</p>
              <a class="link" @click="goRouterView('CLAIM_RETURN')">{{ claimStatInfo.clm002 }}건</a>
            </li>
            <li>
              <p>교환신청</p>
              <a class="link" @click="goRouterView('CLAIM_CHANGE')">{{ claimStatInfo.clm003 }}건</a>
            </li>
          </ul>
        </div>
        <div class="list">
          <div class="dash-title">
            <strong>지연현황(최근 1년)</strong>
            <button type="button" class="dash-refresh" @click="onSearch('DELAY')"></button>
          </div>
          <ul>
            <li>
              <p>주문확인지연</p>
              <a class="link" @click="goRouterView('DELAY_ORDER')">{{ delayStatInfo.orddelay }}건</a>
            </li>
            <li>
              <p>발송지연</p>
              <a class="link" @click="goRouterView('DELAY_DELIV')">{{ delayStatInfo.delivdelay }}건</a>
            </li>
            <li>
              <p>취소지연</p>
              <a class="link" @click="goRouterView('DELAY_CANCEL')">{{ delayStatInfo.cncdelay }}건</a>
            </li>
            <li>
              <p>반품지연</p>
              <a class="link" @click="goRouterView('DELAY_RETURN')">{{ delayStatInfo.rtndelay }}건</a>
            </li>
            <li>
              <p>교환지연</p>
              <a class="link" @click="goRouterView('DELAY_CHANGE')">{{ delayStatInfo.excdelay }}건</a>
            </li>
          </ul>
        </div>
        <div class="list">
          <div class="dash-title">
            <strong>상품현황</strong>
            <button type="button" class="dash-refresh" @click="onSearch('DELAY')"></button>
          </div>
          <ul>
            <li>
              <p>승인요청</p>
              <a class="link" @click="goRouterView('GOODS_APPLY')">{{ goodsStatInfo.apply }}건</a>
            </li>
            <li>
              <p>판매중인 상품</p>
              <a class="link" @click="goRouterView('GOODS_SALES')">{{ goodsStatInfo.sales }}건</a>
            </li>
            <li>
              <p>품절상품</p>
              <a class="link" @click="goRouterView('GOODS_SOLD_OUT')">{{ goodsStatInfo.soldout }}건</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="board clearfix">
        <div class="col-left">
          <div class="list">
            <div class="dash-title">
              <strong>정산내역</strong>
              <button type="button" class="dash-more" @click="goRouterView('CALCULATE')">more</button>
            </div>
            <ul>
              <li v-for="(row, index) in calculateList" :key="index">
                <p>{{ row.calcmonth }}</p>
                <a class="link" @click="goDirectRouterView('CALCULATE_DETAIL', row)">{{ row.calcamt }}</a>
              </li>
            </ul>
          </div>
          <div class="list notice-group">
            <div class="dash-title">
              <strong>파트너사공지</strong>
              <button type="button" class="dash-more" @click="goRouterView('NOTICE_PARTNER')">more</button>
            </div>
            <ul>
              <li v-for="(row, index) in partnerNoticeList" :key="index">
                <a class="link" @click="goDirectRouterView('NOTICE_PARTNER_DETAIL', row)">{{ row.subject }}</a>
                <span>{{ row.regdate }}</span>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-center">
          <div class="graph-area">
            <div class="dash-title">
              <strong>매출현황(구매확정 기준)</strong>
              <button type="button" class="dash-refresh" @click="onSearch('ACCOUNT')"></button>
            </div>
            <div class="data clearfix">
              <div class="left">
                <div class="month-total-p">
                  <p class="month1">{{ accountInfo.infostr }} 실 결제금액</p>
                  <div class="total1">
                    <strong>{{ accountInfo.payamt }}</strong>
                    <span>원</span>
                  </div>
                </div>
              </div>
              <div class="right">
                <table cellpadding="0" cellspacing="0" class="gray-tb tiny mt5">
                  <colgroup>
                    <col width="29%">
                    <col width="29%">
                    <col width="25%">
                    <col width="17%">
                  </colgroup>
                  <tbody>
                  <tr>
                    <th>쿠폰 할인금액</th>
                    <td class="right">{{ accountInfo.goodscpnamt }}원</td>
                    <th>결제완료</th>
                    <td class="right">{{ payCountInfo.ordcnt }}건</td>
                  </tr>
                  <tr>
                    <th>프로모션 할인금액</th>
                    <td class="right">{{ accountInfo.salepromoamt }}원</td>
                    <th>구매확정</th>
                    <td class="right">{{ payCountInfo.compcnt }}건</td>
                  </tr>
                  <tr>
                    <th>환불금액</th>
                    <td class="right">{{ accountInfo.rtnamt }}원</td>
                    <th>취소/교환/반품</th>
                    <td class="right">{{ payCountInfo.clmcnt }}건</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <div class="graph-title top-list">
              <strong>최근 2주간 매출현황</strong>
            </div>
            <div class="graph">
              <GChart
                  v-if="weekChartView"
                  type="ColumnChart"
                  :data="weekChartData"
                  :options="chartOptions"
                  ref="yearChart"
              />
            </div>
          </div>
        </div>
        <div class="col-right">
          <div class="list">
            <div class="dash-title">
              <strong>미 답변 문의</strong>
            </div>
            <ul>
              <li>
                <p>1:1문의</p>
                <a class="link" @click="goRouterView('INQUIRY')">{{ inquiryStatInfo.inquiry }}건</a>
              </li>
              <li>
                <p>상품문의</p>
                <a class="link" @click="goRouterView('GOODS_QNA')">{{ inquiryStatInfo.goodsqna }}건</a>
              </li>
              <li>
                <p>AS문의</p>
                <a class="link" @click="goRouterView('AS_INQUIRY')">{{ inquiryStatInfo.asinquiry }}건</a>
              </li>
              <li>
                <p>관리자문의</p>
                <a class="link" @click="goRouterView('MANAGE_INQUIRY')">{{ inquiryManageInquiryInfo.inquiry }}건</a>
              </li>
            </ul>
          </div>
          <div class="list notice-group">
            <div class="dash-title">
              <strong>동의현황</strong>
              <button type="button" class="dash-more" @click="goRouterView('NOTICE_AGREE')">more</button>
            </div>
            <ul>
              <li v-for="(row, index) in partnerAgreeList" :key="index">
                <a class="link" @click="goDirectRouterView('NOTICE_AGREE_DETAIL', row)">{{ row.subject }}</a>
                <span>{{ row.regdate }}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <PartnersNoticePopup v-if="isNoticePopShow" :list="noticePopupList" @closePopup="closePopup"></PartnersNoticePopup>
    <PartnersAgreePopup v-if="isAgreePopShow" :list="agreePopupList" @closePopup="closePopup"></PartnersAgreePopup>
    <PartnersCalculatePopup v-if="isCalculPopShow" :list="calculatePopupList" @closePopup="closePopup"></PartnersCalculatePopup>
  </div>
  <!-- /컨텐츠 영역 -->
</template>

<script>
import PartnersNoticePopup from './popup/PartnersNoticePopup.vue';
import PartnersAgreePopup from './popup/PartnersAgreePopup.vue';
import PartnersCalculatePopup from './popup/PartnersCalculatePopup.vue';
import { GChart } from 'vue-google-charts/legacy';

export default {
  name: "PartnersDashBoard",
  components: {
    PartnersNoticePopup,
    PartnersAgreePopup,
    PartnersCalculatePopup,
    GChart
  },
  data() {
    return {
      user : this.$util.getUser(this.$store.getters.CONSTANTS.MANAGER_SESSION),
      salesType : 'DAY',
      noticeType : 'MEMBER',
      weekChartView : false,
      weekChartData : [],
      weekGraph : [],
      chartOptions: {
        legend: 'none',
        interpolateNulls: true,
        chartArea: {width: '70%', height: '70%'},
        hAxis: {
          showTextEvery: '1', textPosition: 'out',
        },
        vAxis: {
          textPosition: 'out',
        },
      },
      chartColors : ['#b42a1e', '#e9bc55', '#4faddf', '#8db86c', '#b6558c', '#5d5d5d', '#a554c7',  '#1a3e54', 'navy', 'black', '#d66b51', '#279a3d', 'yellow', 'darkblue'],
      orderStatInfo : {},
      claimStatInfo : {},
      delayStatInfo : {},
      goodsStatInfo : {},
      partnerNoticeList : [],
      noticePopupList: [],
      partnerAgreeList : [],
      agreePopupList: [],
      inquiryStatInfo : {},
      inquiryManageInquiryInfo : {},
      calculateList : [],
      calculatePopupList: [],
      accountInfo : {},
      payCountInfo : {},
      ROUTER_TYPE : {},
      DEFAULT_PARAM : {
        type : 'LINK',
        startdate : this.$util.getAddMonth(this.$util.getDate(''), -1, '-'),
        enddate : this.$util.getDate('-'),
        period : 'months_1'
      },
      YEAR_PARAM : {
        type : 'LINK',
        startdate : this.$util.getAddYear(this.$util.getAddDate(this.$util.getDate(''), 1, ''), -1, '-'),
        enddate : this.$util.getDate('-'),
        period : ''
      },
      THREEDAY_PARAM : {
        type : 'LINK',
        startdate : this.$util.getAddDate(this.$util.getDate(''), -3, '-'),
        enddate : this.$util.getDate('-'),
        period : ''
      },
      INFIITE_PARAM : {
        type : 'LINK',
        startdate : '2000-01-01',
        enddate : '9999-12-31',
        period : ''
      },
      WEEK_PARAM : {
        type : 'LINK',
        startdate : this.$util.getAddDate(this.$util.getDate(''), -7, '-'),
        enddate : this.$util.getDate('-'),
        period : '7'
      },
      isNoticePopShow: false,
      isAgreePopShow: false,
      isCalculPopShow: false,
    }
  },
  mounted() {
    this.initData();
    this.onSearch('ALL');
  },
  methods : {
    initData(){

      this.ROUTER_TYPE = {
        //판매현황(최근 1개월)
        ORDER_WAIT: {name: 'partners.order.manage.waitingdeposit', params: this.DEFAULT_PARAM},
        ORDER_PREPARING_GOODS: {name: 'partners.order.manage.preparinggoods', params: this.DEFAULT_PARAM},
        ORDER_PREPARING_DELIVERY: {name: 'partners.order.manage.preparingdelivery', params: this.DEFAULT_PARAM},
        ORDER_DELIVERY: {name: 'partners.order.manage.indelivery', params: this.DEFAULT_PARAM},
        ORDER_DELIVERY_COMPLETE: {name: 'partners.order.manage.completedelivery', params: Object.assign({ordstatus : 'ODS008'}, this.DEFAULT_PARAM)},
        ORDER_COMPLETE: {name: 'partners.order.manage.completedelivery', params: Object.assign({ordstatus : 'ODS009'}, this.DEFAULT_PARAM)},

        //클레임현황
        CLAIM_CANCEL: {name: 'partners.order.claim.cancellist', params: Object.assign({cncstatus : 'CNS001'}, this.YEAR_PARAM)},
        CLAIM_RETURN: {name: 'partners.order.claim.returnlist', params: Object.assign({rtnstatus : ['RTS001', 'RTS003']}, this.YEAR_PARAM)},
        CLAIM_CHANGE: {name: 'partners.order.claim.exchangelist', params: Object.assign({excstatus : ['EXS001', 'EXS003']}, this.YEAR_PARAM)},

        //지연현황
        DELAY_ORDER : {name: 'partners.order.manage.preparinggoods', params: this.THREEDAY_PARAM},
        DELAY_DELIV : {name: 'partners.order.manage.preparingdelivery', params: this.YEAR_PARAM},
        DELAY_CANCEL : {name: 'partners.order.claim.cancellist', params: Object.assign({cncstatus : 'CNS001'}, this.YEAR_PARAM)},
        DELAY_RETURN : {name: 'partners.order.claim.returnlist', params: Object.assign({rtnstatus : ['RTS001', 'RTS003', 'RTS006', 'RTS008']}, this.YEAR_PARAM)},
        DELAY_CHANGE : {name: 'partners.order.claim.exchangelist', params: Object.assign({excstatus : ['EXS001', 'EXS003', 'EXS006', 'EXS008']}, this.YEAR_PARAM)},

        //상품현황
        GOODS_APPLY : {name: 'partners.goods.manage.goodsApprovalManage', params: Object.assign({goodsapprtype : 'GAT002'}, this.INFIITE_PARAM) },
        GOODS_SALES : {name: 'partners.goods.manage.allGoodsManage', params: Object.assign({goodsselltype : ['GST002']}, this.INFIITE_PARAM) },
        GOODS_SOLD_OUT : {name: 'partners.goods.manage.allGoodsManage', params: Object.assign({goodsselltype : ['GST004', 'GST005']}, this.INFIITE_PARAM) },

        //1:1문의
        INQUIRY : {name: 'partners.cs.oneone', params: Object.assign({isreply : 'F'}, this.INFIITE_PARAM) },
        GOODS_QNA : {name: 'partners.cs.product', params: Object.assign({isreply : 'F'}, this.INFIITE_PARAM) },
        AS_INQUIRY : {name: 'partners.cs.aslist', params: Object.assign({asstatus : 'AST001'}, this.INFIITE_PARAM) },
        MANAGE_INQUIRY : {name : 'partners.cs.partners', params: Object.assign({isreply : 'F'}, this.INFIITE_PARAM) },

        //공지
        NOTICE_PARTNER : {name: 'partners.partners.notice'},
        NOTICE_PARTNER_DETAIL : {name: 'partners.partners.notice', params : {type: 'LINK'}},
        NOTICE_AGREE : {name: 'partners.partners.consent'},
        NOTICE_AGREE_DETAIL : {name : 'partners.partners.consent', params : {type: 'LINK'}},

        //정산
        CALCULATE : {name : 'partners.adjust.decision'},
        CALCULATE_DETAIL : {name : 'partners.adjust.decision', params : {type: 'LINK'}},

      };
    },
    onSearch(searchType){
      let param = {
        type : searchType,
        userno : this.user.no,
        saletype : 'MONTH',
        standard : 'BUY'
      }

      // 공지사항 다시보지않기 처리용 쿠키 조회
      let noticeIdxList = this.$cookies.get("notice_popup");

      if(noticeIdxList !== null){
        noticeIdxList = noticeIdxList.split(',');
        param.noticelist = noticeIdxList;
      }

      this.$http.post('/admin/dashboard/search/dealer', param).then(result => {

        if (searchType === 'ORDER') {
          this.orderStatInfo = result.data.order;
        } else if (searchType === 'CLAIM') {
          this.claimStatInfo = result.data.claim;
        } else if (searchType === 'GOODS') {
          this.goodsStatInfo = result.data.goods;
        } else if (searchType === 'DELAY') {
          this.delayStatInfo = result.data.delay;
        } else if(searchType === 'ACCOUNT'){
          this.accountInfo = result.data.account;
          this.payCountInfo = result.data.paycount;
          this.weekGraph = result.data.weekgraph;
          this.onGraphInit();
        } else if(searchType === 'ALL'){
          this.orderStatInfo = result.data.order;
          this.claimStatInfo = result.data.claim;
          this.goodsStatInfo = result.data.goods;
          this.delayStatInfo = result.data.delay;
          this.partnerNoticeList = result.data.notice_partner;
          this.noticePopupList = result.data.notice_popup;
          this.inquiryStatInfo = result.data.inquiry;
          this.inquiryManageInquiryInfo = result.data.manageinquiry;
          this.accountInfo = result.data.account;
          this.payCountInfo = result.data.paycount;
          this.partnerAgreeList = result.data.agree_partner;
          this.agreePopupList = result.data.agree_popup;
          this.calculateList = result.data.calculate;
          this.calculatePopupList = result.data.calculate_popup;
          this.weekGraph = result.data.weekgraph;
          this.openPopup('CAL');

          this.onGraphInit();
        }
      }).catch(error => {
        this.$util.debug(error);
      })

    },
    onChangeSalesType(type){
      this.salesType = type;
    },
    setNoticeType(type){
      this.noticeType = type;
    },
    goRouterView(type){
      this.$router.push(this.ROUTER_TYPE[type])
    },
    goDirectRouterView(type, obj){
      this.ROUTER_TYPE[type].params = Object.assign(this.ROUTER_TYPE[type].params, obj);
      this.$router.push(this.ROUTER_TYPE[type]);
    },
    openPopup(type) {
      if(type === 'CAL') {
        if(this.calculatePopupList.length > 0) {
          this.isCalculPopShow = true;
        } else {
          this.openPopup('NOTI');
        }
      } else if (type === 'NOTI') {
        if(this.noticePopupList.length > 0) {
          this.isNoticePopShow = true;
        } else {
          this.openPopup('AGREE');
        }
      } else if (type === 'AGREE') {
        if(this.agreePopupList.length > 0) {
          this.isAgreePopShow = true;
        }
      }
    },
    closePopup(type) {
      if(type === 'CAL') {
        this.isCalculPopShow = false;
        this.openPopup('NOTI');
      } else if (type === 'NOTI') {
        this.isNoticePopShow = false;
        this.openPopup('AGREE');
      } else if (type === 'AGREE') {
        this.isAgreePopShow = false;
      }
    },
    onGraphInit(){
      this.weekChartData = [];
      this.weekChartData.push(['', '', { role: 'style' }]);
      this.weekGraph.forEach((row, index) => {
        let legend = row.target_day + '(' + this.$util.getDayOfWeek(row.target_date) + ')';
        this.weekChartData.push([legend, row.saleamt, this.chartColors[index]]);
      });

      this.weekChartView = true;
    }
  }
}
</script>

<style scoped>

</style>