<template>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
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
              <p>반품반려승인요청</p>
              <a class="link" @click="goRouterView('CLAIM_RETURN_REJECT')">{{ claimStatInfo.rtncnt }}건</a>
            </li>
            <li>
              <p>교환신청</p>
              <a class="link" @click="goRouterView('CLAIM_CHANGE')">{{ claimStatInfo.clm003 }}건</a>
            </li>
            <li>
              <p>교환반려승인요청</p>
              <a class="link" @click="goRouterView('CLAIM_CHANGE_REJECT')">{{ claimStatInfo.exccnt }}건</a>
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
          <div class="member-condition">
            <div class="dash-title">
              <strong>회원현황</strong>
              <button type="button" class="dash-more" @click="goRouterView('MEMBER_TOTAL')">more</button>
            </div>
            <div class="total">
              <div class="total-member">
                <p>전체 회원 수</p>
                <strong>{{ memberStatInfo.total }}</strong>
              </div>
              <ul class="clearfix">
                <li>
                  <p>PC</p>
                  <a class="link" @click="goRouterView('MEMBER_PC')">{{ memberStatInfo.pc }}</a>
                </li>
                <li>
                  <p>Mobile</p>
                  <a class="link" @click="goRouterView('MEMBER_MOBILE')">{{ memberStatInfo.mobile }}</a>
                </li>
                <li>
                  <p>Mobile App</p>
                  <a class="link" @click="goRouterView('MEMBER_MOBILE_APP')">{{ memberStatInfo.mobileapp }}</a>
                </li>
              </ul>
            </div>
            <div class="recent">
              <p>최근 7일간 {{ memberStatInfo.period }}</p>
              <ul class="clearfix">
                <li>
                  <p>신규가입</p>
                  <a class="link" @click="goRouterView('MEMBER_REG')">{{ memberStatInfo.reg }}</a>
                </li>
                <li>
                  <p>휴면</p>
                  <a class="link" @click="goRouterView('MEMBER_DORMANCY')">{{ memberStatInfo.dormancy }}</a>
                </li>
                <li>
                  <p>탈퇴</p>
                  <a class="link" @click="goRouterView('MEMBER_RESIGN')">{{ memberStatInfo.resign }}</a>
                </li>
              </ul>
            </div>
          </div>
          <div class="notice-group">
            <div class="tab-group mt0">
              <ul><!-- 활성화탭 li에 class="active" 추가 -->
                <li :class="{ active: noticeType === 'MEMBER' }">><a class="link" @click="setNoticeType('MEMBER')">고객공지</a></li>
                <li :class="{ active: noticeType === 'PARTNER' }"><a class="link" @click="setNoticeType('PARTNER')">파트너사공지</a></li>
              </ul>
              <button type="button" class="dash-more" v-if="noticeType === 'MEMBER'" @click="goRouterView('NOTICE_MEMBER')">more</button>
              <button type="button" class="dash-more" v-if="noticeType === 'PARTNER'" @click="goRouterView('NOTICE_PARTNER')">more</button>
            </div>
            <div class="tab-area" v-if="noticeType === 'MEMBER'">
              <ul v-for="(row, index) in noticeList" :key="index">
                <li>
                  <a class="link" @click="goDirectRouterView('NOTICE_MEMBER_DETAIL', row)">{{ row.subject }}</a>
                  <span>{{ row.regdate }}</span>
                </li>
              </ul>
            </div>
            <div class="tab-area" v-if="noticeType === 'PARTNER'">
              <ul v-for="(row, index) in partnerNoticeList" :key="index">
                <li>
                  <a class="link" @click="goDirectRouterView('NOTICE_PARTNER_DETAIL', row)">{{ row.subject }}</a>
                  <span>{{ row.regdate }}</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-center">
          <div class="tab-group mt0">
            <ul><!-- 활성화탭 li에 class="active" 추가 -->
              <li :class="{ active: salesType === 'DAY' }"><a @click="onChangeSalesType('DAY')">일별 매출</a></li>
              <li :class="{ active: salesType === 'WEEK' }"><a @click="onChangeSalesType('WEEK')">주별 매출</a></li>
              <li :class="{ active: salesType === 'MONTH' }"><a @click="onChangeSalesType('MONTH')">월별 매출</a></li>
            </ul>
            <button type="button" class="dash-refresh" @click="onSearch('ACCOUNT')"></button>
          </div>
          <div class="tab-area">
            <div v-if="salesType === 'WEEK' || salesType === 'DAY'">
              <!-- 일별 매출인 경우 -->
              <div class="tab-title">
                <strong v-if="salesType === 'DAY'">금일 매출 현황</strong>
                <strong v-if="salesType === 'WEEK'">금주 매출 현황</strong>
                <span>{{ accountInfo.infostr }}</span>
                <div class="basic-date">
                  <span class="dpib">실 결제금액 기준일</span>
                  <div class="radio_wrap dpib">
                    <input type="radio" name="group00" id="dayAndWeek01" v-model="status" value="PAY"><label for="dayAndWeek01">결제완료</label>
                    <input type="radio" name="group00" id="dayAndWeek02" v-model="status" value="BUY"><label for="dayAndWeek02">구매확정</label>
                  </div>
                </div>
              </div>
              <div class="data clearfix">
                <div class="left">
                  <div class="total">
                    <span>매출(일반기준)</span>
                    <strong>{{ accountInfo.saleamt }}</strong>
                    <span>원</span>
                  </div>
                  <div class="total">
                    <span>매출(회계기준)</span>
                    <strong>{{ accountInfo.accountamt }}</strong>
                    <span>원</span>
                  </div>
                  <div class="total">
                    <span>실 결제금액</span>
                    <strong>{{ accountInfo.payamt }}</strong>
                    <span>원</span>
                  </div>
                  <table cellpadding="0" cellspacing="0" class="gray-tb tiny mt20 mb0">
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
                <div class="right">
                  <div class="graph">
                    <GChart v-if="chartView"
                        type="BarChart"
                        :data="chartData"
                        :options="chartOptions"
                        ref="chart"
                    />
                  </div>
                </div>
              </div>
              <div class="top-list clearfix">
                <div class="top-tb">
                  <div class="tb-title">
                    <strong v-if="salesType === 'DAY'">금일 판매량 순위 Top 10</strong>
                    <strong v-if="salesType === 'WEEK'">금주 판매량 순위 Top 10</strong>
                    <button type="button" class="dash-more" @click="goRouterView('STAT_SALE')">more</button>
                  </div>
                  <div class="scroll-y" style="max-height: 120px;">
                    <table cellpadding="0" cellspacing="0" class="gray-tb align-c line tiny">
                      <colgroup>
                        <col width="10%">
                        <col width="70%">
                        <col width="20%">
                      </colgroup>
                      <thead>
                      <tr>
                        <th>No</th>
                        <th>상품명</th>
                        <th>판매수량</th>
                      </tr>
                      </thead>
                      <tbody v-if="saleList.length > 0">
                      <tr v-for="(row, index) in saleList" v-bind:key="index">
                        <td>{{ index + 1 }}</td>
                        <td class="left">{{ row.goodsname }}</td>
                        <td>{{ row.recnt }}</td>
                      </tr>
                      </tbody>
                      <tbody v-else>
                      <tr><td colspan="3">조회된 결과가 없습니다.</td></tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div class="top-tb">
                  <div class="tb-title">
                    <strong v-if="salesType === 'DAY'">금일 분류 별 판매량 순위 Top 10</strong>
                    <strong v-if="salesType === 'WEEK'">금주 분류 별 판매량 순위 Top 10</strong>
                    <button type="button" class="dash-more" @click="goRouterView('STAT_CATE')">more</button>
                  </div>
                  <div class="scroll-y" style="max-height: 120px;">
                    <table cellpadding="0" cellspacing="0" class="gray-tb align-c line tiny">
                      <colgroup>
                        <col width="10%">
                        <col width="70%">
                        <col width="20%">
                      </colgroup>
                      <thead>
                      <tr>
                        <th>No</th>
                        <th>분류명</th>
                        <th>판매수량</th>
                      </tr>
                      </thead>
                      <tbody v-if="cateList.length > 0">
                      <tr v-for="(row, index) in cateList" v-bind:key="index">
                        <td>{{ index + 1 }}</td>
                        <td class="left">{{ row.name1 }}</td>
                        <td>{{ row.recnt }}</td>
                      </tr>
                      </tbody>
                      <tbody v-else>
                      <tr><td colspan="3">조회된 결과가 없습니다.</td></tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div class="top-tb">
                  <div class="tb-title">
                    <strong v-if="salesType === 'DAY'">금일 장바구니에 담긴 순위 Top 10</strong>
                    <strong v-if="salesType === 'WEEK'">금주 장바구니에 담긴 순위 Top 10</strong>
                    <button type="button" class="dash-more" @click="goRouterView('STAT_CART')">more</button>
                  </div>
                  <div class="scroll-y" style="max-height: 120px;">
                    <table cellpadding="0" cellspacing="0" class="gray-tb align-c line tiny">
                      <colgroup>
                        <col width="10%">
                        <col width="70%">
                        <col width="20%">
                      </colgroup>
                      <thead>
                      <tr>
                        <th>No</th>
                        <th>상품명</th>
                        <th>판매수량</th>
                      </tr>
                      </thead>
                      <tbody v-if="cartList.length > 0">
                      <tr v-for="(row, index) in cartList" v-bind:key="index">
                        <td>{{ index + 1 }}</td>
                        <td class="left">{{ row.goodsname }} ({{row.optionname}})</td>
                        <td>{{ row.ordcnt }}</td>
                      </tr>
                      </tbody>
                      <tbody v-else>
                      <tr><td colspan="3">조회된 결과가 없습니다.</td></tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div class="top-tb">
                  <div class="tb-title">
                    <strong v-if="salesType === 'DAY'">금일 환불 순위 Top 10</strong>
                    <strong v-if="salesType === 'WEEK'">금주 환불 순위 Top 10</strong>
                    <button type="button" class="dash-more" @click="goRouterView('STAT_CLAIM')">more</button>
                  </div>
                  <div class="scroll-y" style="max-height: 120px;">
                    <table cellpadding="0" cellspacing="0" class="gray-tb align-c line tiny">
                      <colgroup>
                        <col width="10%">
                        <col width="70%">
                        <col width="20%">
                      </colgroup>
                      <thead>
                      <tr>
                        <th>No</th>
                        <th>상품명</th>
                        <th>환불수량</th>
                      </tr>
                      </thead>
                      <tbody v-if="claimList.length > 0">
                      <tr v-for="(row, index) in claimList" v-bind:key="index">
                        <td>{{ index + 1 }}</td>
                        <td class="left">{{ row.goodsname }}</td>
                        <td>{{ row.clmcnt }}</td>
                      </tr>
                      </tbody>
                      <tbody v-else>
                      <tr><td colspan="3">조회된 결과가 없습니다.</td></tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            <!-- // 일별/주별 매출인 경우 -->
            <div v-if="salesType === 'MONTH' ">
            <!-- 월별 매출인 경우 -->
              <div class="tab-title">
                <strong>당월 매출 현황</strong>
                <div class="basic-date">
                  <span class="dpib">실 결제금액 기준일</span>
                  <div class="radio_wrap dpib">
                    <input type="radio" name="group00" id="group01" v-model="status" value="PAY"><label for="group01">결제완료</label>
                    <input type="radio" name="group00" id="group02" v-model="status" value="BUY"><label for="group02">구매확정</label>
                  </div>
                </div>
              </div>
              <div class="data clearfix">
                <div class="left">
                  <div class="month-total clearfix">
                    <p class="month">{{ accountInfo.infostr }}</p>
                    <div class="total mt0">
                      <span>매출(일반기준)</span>
                      <strong>{{ accountInfo.saleamt }}</strong>
                      <span>원</span>
                    </div>
                    <div class="total">
                      <span>매출(회계기준)</span>
                      <strong>{{ accountInfo.accountamt }}</strong>
                      <span>원</span>
                    </div>
                    <div class="total">
                      <span>실 결제금액</span>
                      <strong>{{ accountInfo.payamt }}</strong>
                      <span>원</span>
                    </div>
                  </div>
                </div>
                <div class="right">
                  <table cellpadding="0" cellspacing="0" class="gray-tb tiny mt10">
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
              <div class="wide-graph">
                <select class="mb10" v-model="calcYear" @change="onSearch('ACCOUNT')">
                  <option v-for="(value,i) in yearList" :key="i" :value="value" selected>{{ value }}년</option>
                </select>
                <div class="graph">
                  <GChart
                      v-if="yearChartView"
                      type="ColumnChart"
                      :data="yearChartData"
                      :options="chartOptions"
                      ref="yearChart"
                  />
                </div>
                <div class="total">
                  <span>누적 매출액</span>
                  <strong>{{ $util.maskComma(totAmt) }}</strong>
                  <span>원</span>
                </div>
              </div>
            <!-- // 월별 매출인 경우 -->
            </div>
          </div>
        </div>
        <div class="col-right">
          <div class="list">
            <div class="dash-title">
              <strong>쿠폰</strong>
              <button type="button" class="dash-more" @click="goRouterView('COUPON')">more</button>
            </div>
            <ul>
              <li>
                <p>발급 전</p>
                <a class="link" @click="goRouterView('COUPON_BEFORE')">{{ couponStatInfo.beforeissue }}건</a>
              </li>
              <li>
                <p>발급 중</p>
                <a class="link" @click="goRouterView('COUPON_ISSUE')">{{ couponStatInfo.issue }}건</a>
              </li>
              <li>
                <p>기간종료(7일전) 쿠폰</p>
                <a class="link" @click="goRouterView('COUPON_PERIOD')">{{ couponStatInfo.expireissue }}건</a>
              </li>
            </ul>
          </div>
          <div class="list">
            <div class="dash-title">
              <strong>프로모션</strong>
              <button type="button" class="dash-more" @click="goRouterView('PROMOTION')">more</button>
            </div>
            <ul>
              <li>
                <p>할인 프로모션</p>
                <a class="link" @click="goRouterView('PROMOTION_SALE')">{{ promoStatInfo.sale }}건</a>
              </li>
              <li>
                <p>사은품 프로모션</p>
                <a class="link" @click="goRouterView('PROMOTION_GIFT')">{{ promoStatInfo.gift }}건</a>
              </li>
              <li>
                <p>적립금 프로모션</p>
                <a class="link" @click="goRouterView('PROMOTION_RESERVE')">{{ promoStatInfo.reserve }}건</a>
              </li>
              <li>
                <p>종료예정(7일전) 프로모션</p>
                <a class="link" @click="goRouterView('PROMOTION_EXPIRE')">{{ promoStatInfo.expire }}건</a>
              </li>
            </ul>
          </div>
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
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- /컨텐츠 영역 -->
</template>

<script>
import { GChart } from 'vue-google-charts/legacy';

export default {
  name: "AdminDashBoard",
  components : {GChart},
  data() {
    return {
      salesType : 'DAY',
      status : 'PAY',
      noticeType : 'MEMBER',
      calcYear : this.$util.getYear(),
      yearList : [],
      chartColors : ['#b42a1e', '#e9bc55', '#4faddf', '#8db86c', '#b6558c', '#5d5d5d', '#a554c7',  '#1a3e54', 'navy', 'black', '#d66b51', '#279a3d'],
      yearChartView : false,
      chartView : false,
      chartData: [],
      totAmt : 0,
      chartOptions: {
        legend: 'none',
        interpolateNulls: true,
        chartArea: {width: '70%', height: '70%'},
        hAxis: {
          showTextEvery: '1', textPosition: 'out',
          minValue : 1000000,
        },
        vAxis: {
          textPosition: 'out',
          minValue : 1000000,
        },
      },
      yearChartData : [],
      orderStatInfo : {},
      claimStatInfo : {},
      delayStatInfo : {},
      goodsStatInfo : {},
      memberStatInfo : {},
      couponStatInfo : {},
      noticeList : [],
      partnerNoticeList : [],
      promoStatInfo : {},
      inquiryStatInfo : {},
      accountInfo : {},
      payCountInfo : {},
      graphInfo : {},
      yearGraph : [],
      cartList : [],
      claimList : [],
      saleList : [],
      cateList : [],
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
      }
    }
  },
  mounted() {
    let year = parseInt(this.calcYear);
    this.yearList.push(year);
    for(let count = 1; count <= 10; count++){
      this.yearList.push(year - count);
    }

    this.initData();
    this.onSearch('ALL');

  },
  methods : {
    initData(){

      this.ROUTER_TYPE = {
        //판매현황(최근 1개월)
        ORDER_WAIT: {name: 'admin.order.manage.waitingdeposit', params: this.DEFAULT_PARAM},
        ORDER_PREPARING_GOODS: {name: 'admin.order.manage.preparinggoods', params: this.DEFAULT_PARAM},
        ORDER_PREPARING_DELIVERY: {name: 'admin.order.manage.preparingdelivery', params: this.DEFAULT_PARAM},
        ORDER_DELIVERY: {name: 'admin.order.manage.indelivery', params: this.DEFAULT_PARAM},
        ORDER_DELIVERY_COMPLETE: {name: 'admin.order.manage.completedelivery', params: Object.assign({ordstatus : 'ODS008'}, this.DEFAULT_PARAM)},
        ORDER_COMPLETE: {name: 'admin.order.manage.completedelivery', params: Object.assign({ordstatus : 'ODS009'}, this.DEFAULT_PARAM)},

        //클레임현황
        CLAIM_CANCEL: {name: 'admin.order.claim.cancellist', params: Object.assign({cncstatus : 'CNS001'}, this.YEAR_PARAM)},
        CLAIM_RETURN: {name: 'admin.order.claim.returnlist', params: Object.assign({rtnstatus : ['RTS001', 'RTS003']}, this.YEAR_PARAM)},
        CLAIM_RETURN_REJECT: {name: 'admin.order.claim.returnlist', params: Object.assign({rtnstatus : ['RTS011']}, this.YEAR_PARAM)},
        CLAIM_CHANGE: {name: 'admin.order.claim.exchangelist', params: Object.assign({excstatus : ['EXS001', 'EXS003']}, this.YEAR_PARAM)},
        CLAIM_CHANGE_REJECT: {name: 'admin.order.claim.exchangelist', params: Object.assign({excstatus : ['EXS013']}, this.YEAR_PARAM)},

        //지연현황
        DELAY_ORDER : {name: 'admin.order.manage.preparinggoods', params: this.THREEDAY_PARAM},
        DELAY_DELIV : {name: 'admin.order.manage.preparingdelivery', params: this.YEAR_PARAM},
        DELAY_CANCEL : {name: 'admin.order.claim.cancellist', params: Object.assign({cncstatus : 'CNS001'}, this.YEAR_PARAM)},
        DELAY_RETURN : {name: 'admin.order.claim.returnlist', params: Object.assign({rtnstatus : ['RTS001', 'RTS003', 'RTS006', 'RTS008']}, this.YEAR_PARAM)},
        DELAY_CHANGE : {name: 'admin.order.claim.exchangelist', params: Object.assign({excstatus : ['EXS001', 'EXS003', 'EXS006', 'EXS008']}, this.YEAR_PARAM)},

        //상품현황
        GOODS_APPLY : {name: 'admin.goods.manage.goodsApprovalManage', params: Object.assign({goodsapprtype : 'GAT002'}, this.INFIITE_PARAM) },
        GOODS_SALES : {name: 'admin.goods.manage.allGoodsManage', params: Object.assign({goodsselltype : ['GST002']}, this.INFIITE_PARAM) },
        GOODS_SOLD_OUT : {name: 'admin.goods.manage.allGoodsManage', params: Object.assign({goodsselltype : ['GST004', 'GST005']}, this.INFIITE_PARAM) },

        //회원현황
        MEMBER_TOTAL : {name: 'admin.member.total.list'},
        MEMBER_PC : {name: 'admin.member.total.list', params: Object.assign({jointype : 'UJT001'}, this.INFIITE_PARAM) },
        MEMBER_MOBILE : {name: 'admin.member.total.list', params: Object.assign({jointype : 'UJT002'}, this.INFIITE_PARAM) },
        MEMBER_MOBILE_APP : {name: 'admin.member.total.list', params: Object.assign({jointype : 'UJT003'}, this.INFIITE_PARAM) },

        MEMBER_REG : {name: 'admin.member.total.list', params: this.WEEK_PARAM },
        MEMBER_DORMANCY : {name: 'admin.member.dormancy.list', params: this.WEEK_PARAM},
        MEMBER_RESIGN : {name: 'admin.member.resign.list', params: this.WEEK_PARAM},

        //쿠폰
        COUPON : {name: 'admin.promotion.coupon.couponlist'},
        COUPON_BEFORE : {name: 'admin.promotion.coupon.couponlist', params: Object.assign({cpnissuest : 'CIS001'}, this.INFIITE_PARAM) },
        COUPON_ISSUE : {name: 'admin.promotion.coupon.couponlist', params: Object.assign({cpnissuest : 'CIS002'}, this.INFIITE_PARAM) },
        COUPON_PERIOD : {name: 'admin.promotion.coupon.couponlist', params: Object.assign({cpnissuest : 'CIS002'}, this.WEEK_PARAM) },

        //프로모션
        PROMOTION : {name: 'admin.promotion.promotion.promotionlist'},
        PROMOTION_SALE : {name: 'admin.promotion.promotion.promotionlist', params: Object.assign({promodivtype : 'PDT001'}, this.INFIITE_PARAM) },
        PROMOTION_GIFT : {name: 'admin.promotion.promotion.promotionlist', params: Object.assign({promodivtype : 'PDT002'}, this.INFIITE_PARAM) },
        PROMOTION_RESERVE : {name: 'admin.promotion.promotion.promotionlist', params: Object.assign({promodivtype : 'PDT003'}, this.INFIITE_PARAM) },
        PROMOTION_EPOINT : {name: 'admin.promotion.promotion.promotionlist', params: Object.assign({promodivtype : 'PDT003'}, this.INFIITE_PARAM) },
        PROMOTION_EXPIRE : {name: 'admin.promotion.promotion.promotionlist', params: { startdate : this.$util.getDate('-'), enddate : this.$util.getAddDate(this.$util.getDate(''), 7, '-'), period : '', sdate : 'end' } },

        //1:1문의
        INQUIRY : {name: 'admin.cs.oneone', params: Object.assign({isreply : 'F'}, this.INFIITE_PARAM) },
        GOODS_QNA : {name: 'admin.cs.product', params: Object.assign({isreply : 'F'}, this.INFIITE_PARAM) },
        AS_INQUIRY : {name: 'admin.cs.aslist', params: Object.assign({asstatus : 'AST001'}, this.INFIITE_PARAM) },

        //공지
        NOTICE_MEMBER : {name: 'admin.operation.noticelist'},
        NOTICE_MEMBER_DETAIL : {name: 'admin.operation.noticelist', params : {type: 'LINK'}},
        NOTICE_PARTNER : {name: 'admin.partners.notice'},
        NOTICE_PARTNER_DETAIL : {name: 'admin.partners.notice', params : {type: 'LINK'}},

        STAT_CART : {name : 'admin.stats.cart'},
        STAT_CLAIM : {name : 'admin.stats.claim'},
        STAT_SALE : {name : 'admin.stats.salerank'},
        STAT_CATE : {name : 'admin.stats.salecaterank'},
      };
    },
    onSearch(searchType){
      let param = {
        type : searchType,
        saletype : this.salesType,
        standard : this.status,
        year : this.calcYear
      }

      this.$http.post('/admin/dashboard/search', param).then(result => {

        if(searchType === 'ORDER'){
          this.orderStatInfo = result.data.order;
        } else if(searchType === 'CLAIM'){
          this.claimStatInfo = result.data.claim;
        } else if(searchType === 'GOODS'){
          this.goodsStatInfo = result.data.goods;
        } else if(searchType === 'DELAY'){
          this.delayStatInfo = result.data.delay;
        } else if(searchType === 'ACCOUNT') {
          this.accountInfo = result.data.account;
          this.payCountInfo = result.data.paycount;
          this.cartList = result.data.cartlist;
          this.graphInfo = result.data.graph;
          this.yearGraph = result.data.yeargraph;
          this.totAmt = result.data.totamt;
          this.onGraphInit();

          this.claimList = result.data.claimlist;
          this.saleList = result.data.salelist;
          this.cateList = result.data.catelist;

        } else if(searchType === 'ALL'){
          this.orderStatInfo = result.data.order;
          this.claimStatInfo = result.data.claim;
          this.goodsStatInfo = result.data.goods;
          this.delayStatInfo = result.data.delay;
          this.memberStatInfo = result.data.member;
          this.couponStatInfo = result.data.coupon;
          this.noticeList = result.data.notice;
          this.partnerNoticeList = result.data.notice_partner;
          this.promoStatInfo = result.data.promotion;
          this.inquiryStatInfo = result.data.inquiry;
          this.accountInfo = result.data.account;
          this.payCountInfo = result.data.paycount;
          this.cartList = result.data.cartlist;
          this.graphInfo = result.data.graph;
          this.yearGraph = result.data.yeargraph;
          this.totAmt = result.data.totamt;
          this.onGraphInit();

          this.claimList = result.data.claimlist;
          this.saleList = result.data.salelist;
          this.cateList = result.data.catelist;
        }
      }).catch(error => {
        this.$util.debug(error);
      })

    },
    onChangeSalesType(typeStr){
      this.salesType = typeStr;
      this.onSearch('ACCOUNT');
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
    onGraphInit(){

      if(this.salesType === 'MONTH'){
        this.yearChartData = [];
        this.yearChartData.push(['', '', { role: 'style' }]);
        this.yearGraph.forEach((row, index) => {
          this.yearChartData.push([row.month, row.saleamt, this.chartColors[index]]);
        });

        this.chartView = false;
        this.yearChartView = true;
      } else if(this.salesType === 'WEEK'){
        this.chartData = [];
        this.chartData.push(['', '', { role: 'style' }]);
        this.chartData.push(['최근4주\n(평균)', this.graphInfo.calcamt, this.chartColors[0]]);
        this.chartData.push(['저번주', this.graphInfo.next, this.chartColors[1]]);
        this.chartData.push(['이번주', this.graphInfo.recent, this.chartColors[2]]);

        this.chartView = true;
        this.yearChartView = false;
      } else {
        this.chartData = [];
        this.chartData.push(['', '', { role: 'style' }]);
        this.chartData.push(['최근7일\n(평균)', this.graphInfo.calcamt, this.chartColors[0]]);
        this.chartData.push(['어제', this.graphInfo.next, this.chartColors[1]]);
        this.chartData.push(['오늘', this.graphInfo.recent, this.chartColors[2]]);

        this.chartView = true;
        this.yearChartView = false;
      }
    }
  }
}
</script>

<style scoped>

</style>