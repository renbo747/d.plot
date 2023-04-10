<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">전체주문</div>
            </div>
            <div class="boxing search-area pd0">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option v-for="item in searchData.skeyArr" :key="item.key" :value="item.key">{{ item.name }}</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>주문일자</dt>
                    <dd>
                        <CommonDatePicker :value="searchData.startdate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.enddate" @change="onChangeEndDate"/>
                        <div class="radio_wrap">
                            <input type="radio" v-model="searchData.period" id="period_aday_1" value='aday_1'/><label for="period_aday_1">어제</label>
                            <input type="radio" v-model="searchData.period" id="period_aday_0" value='aday_0'/><label for="period_aday_0">오늘</label>
                            <input type="radio" v-model="searchData.period" id="period_days_7" value='days_7'/><label for="period_days_7">일주일</label>
                            <input type="radio" v-model="searchData.period" id="period_months_1" value='months_1'/><label for="period_months_1">1개월</label>
                            <input type="radio" v-model="searchData.period" id="period_months_3" value='months_3'/><label for="period_months_3">3개월</label>
                            <input type="radio" v-model="searchData.period" id="period_months_6" value='months_6'/><label for="period_months_6">6개월</label>
                        </div>
                    </dd>
                    <dd style="width: 104px;" v-if="!searchData.isshowdetailarea && !isPartner">
                        <!-- 펼쳤을 경우 i class에 close -> open -->
                        <button type="button" class="btn black-line" @click="searchData.isshowdetailarea = !searchData.isshowdetailarea">
                            <i class="icon-arr-detail close"></i>상세검색
                        </button>
                    </dd>
                </dl>
                <!-- 상세검색 펼침 영역(공통) -->
                <dl v-if="searchData.isshowdetailarea">
                    <dt>주문경로</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllChannel" v-model="searchData.isallchannel" true-value="T" false-value="F" @change="checkAllChannel">
                            <label for="chkAllChannel">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                            <input type="checkbox" :id="'muappchtype_'+item.cmcode" v-model="searchData.muappchtypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'muappchtype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>금액조건</dt>
                    <dd>
                        <select v-model="searchData.pricekey">
                            <!-- <option value="goods">상품구매금액</option> -->
                            <option value="payment">결제금액</option>
                        </select>
                        <input type="text" class="short" v-model="searchData.minprice" maxlength="11"/>
                        <span>~</span>
                        <input type="text" class="short" v-model="searchData.maxprice" maxlength="11" />
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>회원구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="isnonmember" id="isnonmemberAll" v-model="searchData.isnonmember" value="" />
                            <label for="isnonmemberAll">전체</label>
                            <input type="radio" name="isnonmember" id="isnonmemberF" v-model="searchData.isnonmember" value="F" />
                            <label for="isnonmemberF">회원</label>
                            <input type="radio" name="isnonmember" id="isnonmemberT" v-model="searchData.isnonmember" value="T"/>
                            <label for="isnonmemberT">비회원</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>주문구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="isfrstorder" id="isfrstorderAll" v-model="searchData.isfrstorder" value="" />
                            <label for="isfrstorderAll">전체</label>
                            <input type="radio" name="isfrstorder" id="isfrstorderT" v-model="searchData.isfrstorder" value="T" />
                            <label for="isfrstorderT">첫 주문</label>
                            <input type="radio" name="isfrstorder" id="isfrstorderF" v-model="searchData.isfrstorder" value="F"/>
                            <label for="isfrstorderF">재 주문</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>회원유형</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllMember" v-model="searchData.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
                            <label for="chkAllMember">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                            <input type="checkbox" :id="'dadamembertype_'+item.cmcode" v-model="searchData.mumembertypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'dadamembertype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>회원등급</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllMemlv" v-model="searchData.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
                            <label for="chkAllMemlv">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.memlvtype" :key="item.cmcode">
                            <input type="checkbox" :id="'mumemlvtype_'+item.cmcode" v-model="searchData.mumemlvtypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'mumemlvtype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>결제수단</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllPayway" v-model="searchData.isallpayway" true-value="T" false-value="F" @change="checkAllPaywaytype">
                            <label for="chkAllPayway">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.paywaytype" :key="item.cmcode">
                            <input type="checkbox" :id="'paywaytype_'+item.cmcode" v-model="searchData.paywaytypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'paywaytype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                    <dd style="width: 104px;" v-if="searchData.isshowdetailarea && !isPartner">
                        <!-- 펼쳤을 경우 i class에 close -> open -->
                        <button type="button" class="btn black-line" @click="searchData.isshowdetailarea = !searchData.isshowdetailarea">
                            <i class="icon-arr-detail open"></i>상세검색
                        </button>
                    </dd>
                </dl>
                <!-- // 상세검색 펼침 영역(공통) -->
            </div>
            <div class="btn-group" v-if="isRead">
                <button type="button" class="btn big blue" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ count.totalcnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" v-if="isRead" class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <div :class="{'scroll-x': !isPartner}">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="!isPartner? 'width: 2200px;':''">
                    <caption>주문 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="2%" /><!-- No -->
                        <col width="4.5%" /><!-- 주문경로 -->
                        <col width="5%" /><!-- 주문일 -->
                        <col width="5%" /><!-- 결제일 -->
                        <col width="6.5%" /><!-- 주문번호 -->
                        <col width="3.5%" /><!-- 첫 구매 -->
                        <col width="3.5%" /><!-- 주문자 -->
                        <col width="4.5%" /><!-- 아이디 -->
                        <col width="5.5%" /><!-- 연락처 -->
                        <col width="3.5%" /><!-- 회원구분 -->
                        <col width="3.5%" /><!-- 유형 -->
                        <col width="3.5%" /><!-- 등급 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="5%" /><!-- 실결제금액 -->
                        <col width="4.5%" /><!-- 결제수단 -->
                        <col width="4%" /><!-- 미배송 -->
                        <col width="4%" /><!-- 배송중 -->
                        <col width="4%" /><!-- 배송완료 -->
                        <col width="4%" /><!-- 구매확정 -->
                        <col width="3.5%" /><!-- 취소 -->
                        <col width="3.5%" /><!-- 반품 -->
                        <col width="3.5%" /><!-- 교환 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="2%" /><!-- No -->
                        <col width="6%" /><!-- 주문일 -->
                        <col width="6%" /><!-- 결제일 -->
                        <col width="9%" /><!-- 주문번호 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="8%" /><!-- 실결제금액 -->
                        <col width="6%" /><!-- 결제수단 -->
                        <col width="5%" /><!-- 미배송 -->
                        <col width="5%" /><!-- 배송중 -->
                        <col width="5%" /><!-- 배송완료 -->
                        <col width="5%" /><!-- 구매확정 -->
                        <col width="4.5%" /><!-- 취소 -->
                        <col width="4.5%" /><!-- 반품 -->
                        <col width="4.5%" /><!-- 교환 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th v-if="!isPartner">주문경로
                                <button type="button" class="sort" :value="sortData.ordpathtype"
                                    :class="[{up : sortData.ordpathtype === 'ordpathtype_asc'}, {down : sortData.ordpathtype === 'ordpathtype_desc'}]"
                                    @click="sortToggle(sortData.ordpathtype)"></button>
                            </th>
                            <th>주문일
                                <button type="button" class="sort" :value="sortData.orderdate"
                                    :class="[{up : sortData.orderdate === 'orderdate_asc'}, {down : sortData.orderdate === 'orderdate_desc'}]"
                                    @click="sortToggle(sortData.orderdate)"></button>
                            </th>
                            <th>결제일
                                <button type="button" class="sort" :value="sortData.paymentdate"
                                    :class="[{up : sortData.paymentdate === 'paymentdate_asc'}, {down : sortData.paymentdate === 'paymentdate_desc'}]"
                                    @click="sortToggle(sortData.paymentdate)"></button>
                            </th>
                            <th>주문번호
                                <button type="button" class="sort" :value="sortData.ordno"
                                    :class="[{up : sortData.ordno === 'ordno_asc'}, {down : sortData.ordno === 'ordno_desc'}]"
                                    @click="sortToggle(sortData.ordno)"></button>
                            </th>
                            <th v-if="!isPartner">첫 구매
                                <button type="button" class="sort" :value="sortData.isfrstorder"
                                    :class="[{up : sortData.isfrstorder === 'isfrstorder_asc'}, {down : sortData.isfrstorder === 'isfrstorder_desc'}]"
                                    @click="sortToggle(sortData.isfrstorder)"></button>
                            </th>
                            <th v-if="!isPartner">주문자
                                <button type="button" class="sort" :value="sortData.ordname"
                                    :class="[{up : sortData.ordname === 'ordname_asc'}, {down : sortData.ordname === 'ordname_desc'}]"
                                    @click="sortToggle(sortData.ordname)"></button>
                            </th>
                            <th v-if="!isPartner">아이디
                                <button type="button" class="sort" :value="sortData.ordid"
                                    :class="[{up : sortData.ordid === 'ordid_asc'}, {down : sortData.ordid === 'ordid_desc'}]"
                                    @click="sortToggle(sortData.ordid)"></button>
                            </th>
                            <th v-if="!isPartner">연락처
                                <button type="button" class="sort" :value="sortData.ordtel"
                                    :class="[{up : sortData.ordtel === 'ordtel_asc'}, {down : sortData.ordtel === 'ordtel_desc'}]"
                                    @click="sortToggle(sortData.ordtel)"></button>
                            </th>
                            <th v-if="!isPartner">회원구분
                                <button type="button" class="sort" :value="sortData.isnonmember"
                                    :class="[{up : sortData.isnonmember === 'isnonmember_asc'}, {down : sortData.isnonmember === 'isnonmember_desc'}]"
                                    @click="sortToggle(sortData.isnonmember)"></button>
                            </th>
                            <th v-if="!isPartner">유형
                                <button type="button" class="sort" :value="sortData.membertype"
                                    :class="[{up : sortData.membertype === 'membertype_asc'}, {down : sortData.membertype === 'membertype_desc'}]"
                                    @click="sortToggle(sortData.membertype)"></button>
                            </th>
                            <th v-if="!isPartner">등급
                                <button type="button" class="sort" :value="sortData.memlvtype"
                                    :class="[{up : sortData.memlvtype === 'memlvtype_asc'}, {down : sortData.memlvtype === 'memlvtype_desc'}]"
                                    @click="sortToggle(sortData.memlvtype)"></button>
                            </th>
                            <th colspan="2">상품명
                                <button type="button" class="sort" :value="sortData.goodsname"
                                    :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                                    @click="sortToggle(sortData.goodsname)"></button>
                            </th>
                            <th>실결제금액
                                <button type="button" class="sort" :value="sortData.rpaytotprice"
                                    :class="[{up : sortData.rpaytotprice === 'rpaytotprice_asc'}, {down : sortData.rpaytotprice === 'rpaytotprice_desc'}]"
                                    @click="sortToggle(sortData.rpaytotprice)"></button>
                            </th>
                            <th>결제수단
                                <button type="button" class="sort" :value="sortData.paywaytype"
                                    :class="[{up : sortData.paywaytype === 'paywaytype_asc'}, {down : sortData.paywaytype === 'paywaytype_desc'}]"
                                    @click="sortToggle(sortData.paywaytype)"></button>
                            </th>
                            <th>미배송
                                <button type="button" class="sort" :value="sortData.undelivcnt"
                                    :class="[{up : sortData.undelivcnt === 'undelivcnt_asc'}, {down : sortData.undelivcnt === 'undelivcnt_desc'}]"
                                    @click="sortToggle(sortData.undelivcnt)"></button>
                            </th>
                            <th>배송중
                                <button type="button" class="sort" :value="sortData.indelivcnt"
                                    :class="[{up : sortData.indelivcnt === 'indelivcnt_asc'}, {down : sortData.indelivcnt === 'indelivcnt_desc'}]"
                                    @click="sortToggle(sortData.indelivcnt)"></button>
                            </th>
                            <th>배송완료
                                <button type="button" class="sort" :value="sortData.compdelivcnt"
                                    :class="[{up : sortData.compdelivcnt === 'compdelivcnt_asc'}, {down : sortData.compdelivcnt === 'compdelivcnt_desc'}]"
                                    @click="sortToggle(sortData.compdelivcnt)"></button>
                            </th>
                            <th>구매확정
                                <button type="button" class="sort" :value="sortData.cfmcnt"
                                    :class="[{up : sortData.cfmcnt === 'cfmcnt_asc'}, {down : sortData.cfmcnt === 'cfmcnt_desc'}]"
                                    @click="sortToggle(sortData.cfmcnt)"></button>
                            </th>
                            <th>취소
                                <button type="button" class="sort" :value="sortData.canclecnt"
                                    :class="[{up : sortData.canclecnt === 'canclecnt_asc'}, {down : sortData.canclecnt === 'canclecnt_desc'}]"
                                    @click="sortToggle(sortData.canclecnt)"></button>
                            </th>
                            <th>반품
                                <button type="button" class="sort" :value="sortData.returncnt"
                                    :class="[{up : sortData.returncnt === 'returncnt_asc'}, {down : sortData.returncnt === 'returncnt_desc'}]"
                                    @click="sortToggle(sortData.returncnt)"></button>
                            </th>
                            <th>교환
                                <button type="button" class="sort" :value="sortData.exchangecnt"
                                    :class="[{up : sortData.exchangecnt === 'exchangecnt_asc'}, {down : sortData.exchangecnt === 'exchangecnt_desc'}]"
                                    @click="sortToggle(sortData.exchangecnt)"></button>
                            </th>
                        </tr>
                    </thead>
                    <tbody v-if="list.length > 0">
                        <tr v-for="(item, index) in list" :key="item.orgdelividx">
                            <td>{{ loopNumberForPaging(index) }}</td>
                            <td v-if="!isPartner">{{ item.ordpathtypename }}</td>
                            <td>{{ item.orderdate }}<br>{{ item.ordertime }}</td>
                            <td>{{ item.paymentdate }}<br>{{ item.paymenttime }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderDetail(item.ordno)">{{ item.ordno }}</a></td>
                            <td v-if="!isPartner">{{ item.isfrstorder }}</td>
                            <td v-if="!isPartner && item.isnonmember==='F'"><a href="javascript:void(0);" class="link" @click="goMemDetail(item.orduserno)">{{ item.ordname }}</a></td>
                            <td v-if="!isPartner && item.isnonmember==='T'">{{ item.ordname }}</td>
                            <td v-if="!isPartner">{{ item.ordid }}</td>
                            <td v-if="!isPartner">{{ $util.maskTel(item.ordtel) }}</td>
                            <td v-if="!isPartner">{{ item.isnonmembername }}</td>
                            <td v-if="!isPartner">{{ item.membertypename }}</td>
                            <td v-if="!isPartner">{{ item.memlvtypename }}</td>
                            <td>
                                <div class="img-thumb size60"  :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <a href="javascript:void(0);" class="link" @click="goOrderGoods(item)">{{ item.goodsname }}</a>
                            </td>
                            <td class="right">{{ $util.maskComma(item.rpaytotprice) }}</td>
                            <td>{{ item.paywaytypename }}</td>
                            <td>{{ item.undelivcnt }}</td>
                            <td>{{ item.indelivcnt }}</td>
                            <td>{{ item.compdelivcnt }}</td>
                            <td>{{ item.cfmcnt }}</td>
                            <td>{{ item.canclecnt }}</td>
                            <td>{{ item.returncnt }}</td>
                            <td>{{ item.exchangecnt }}</td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr><td :colspan="isPartner? 15:23">조회 결과가 존재하지 않습니다.</td></tr>
                    </tbody>
                </table>
            </div>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
            </div>
        </div>
        <AdminMemberInfo v-if="isShowMemDetail" :activeUserNo="activeUserNo" @closeDetail="closeMemDetail"></AdminMemberInfo>
        <OrderDetail v-if="isShowOrderDetail" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetail"></OrderDetail>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator';
import OrderDetail from '@views.admin/order/info/OrderDetail.vue';
import AdminMemberInfo from '@views.admin/member/info/AdminMemberInfo.vue';
import OrderGoodsPopup from '@views.admin/order/popup/OrderGoodsPopup.vue';

export default {
    name: 'admin.order.manage.allordermanage',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        OrderDetail,
        AdminMemberInfo,
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 초기화
                    this.onInit();
                }
            }).catch(error => {
                this.$util.debug(error);
            });
    },
    data() {
        return {
            isPartner: false,
            user: {},
            searchData: {
                isshowdetailarea: false,    // 상세 검색조건영역 펼침여부
                skeyArr: [
                    {key: '', name: '전체', isShowPartner: true},
                    {key: 'ordno', name: '주문번호', isShowPartner: true},
                    {key: 'ordname', name: '주문자명', isShowPartner: false},
                    {key: 'ordid', name: '주문자ID', isShowPartner: false},
                    {key: 'ordtel', name: '주문자연락처', isShowPartner: false},
                    {key: 'vircusname', name: '입금자명', isShowPartner: true},
                    {key: 'rcvname', name: '수취인명', isShowPartner: true},
                    {key: 'addr', name: '주소', isShowPartner: true}
                ],
                skey: '',               // 직접검색KEY
                sword: '',              // 직접검색어
                dtkey: 'order',         // 조회일자KEY (default: 주문일자)
                period: '',             // 조회일자기간
                startdate: '',          // 조회시작일자
                enddate: '',            // 조회종료일자
                isallchannel: '',       // 전체채널여부
                muappchtypeArr: [],     // 다중적용채널Array
                pricekey: '',           // 금액조건KEY
                minprice: '',           // 최소금액
                maxprice: '',           // 최대금액
                isnonmember: '',        // 비회원주문여부
                isfrstorder: '',        // 첫구매여부
                isallmember: '',        // 다중대상회원유형전체여부
                mumembertypeArr: [],    // 다중대상회원유형Array
                isallmemlv: '',         // 다중대상회원등급전체여부
                mumemlvtypeArr: [],     // 다중대상회원등급Array
                isallpayway: '',        // 결제수단전체여부
                paywaytypeArr: [],      // 결제수단Array
                dealerno: '',           // 입점업체번호
                psort: 'orderday_desc'  // 정렬조건 (default: 최근주문일)
            },
            commonCode: {
                muappchtype: [],        // 적용채널
                dadamembertype: [],     // 다다픽회원유형
                memlvtype: [],          // 회원등급
                paywaytype: []          // 결제수단
            },
            sortData: {
                ordpathtype: 'ordpathtype_asc',     // 주문경로
                orderdate: 'orderdate_asc',         // 주문일
                paymentdate: 'paymentdate_asc',     // 결제일
                ordno: 'ordno_asc',                 // 주문번호
                isfrstorder: 'isfrstorder_asc',     // 첫구매여부
                ordname: 'ordname_asc',             // 주문자명
                ordid: 'ordid_asc',                 // 주문자id
                ordtel: 'ordtel_asc',               // 주문자연락처
                isnonmember: 'isnonmember_asc',     // 회원구분
                membertype: 'membertype_asc',       // 회원유형
                memlvtype: 'memlvtype_asc',         // 회원등급
                ispbgoods: 'ispbgoods_asc',         // 판매구분
                dealername: 'dealername_asc',       // 판매사명
                goodscode: 'goodscode_asc',         // 상품코드
                goodsname: 'goodsname_asc',         // 상품명
                rpaytotprice: 'rpaytotprice_asc',   // 실결제금액
                paywaytype: 'paywaytype_asc',       // 결제수단
                undelivcnt: 'undelivcnt_asc',       // 미배송건수
                indelivcnt: 'indelivcnt_asc',       // 배송중건수
                compdelivcnt: 'compdelivcnt_asc',   // 배송완료건수
                cfmcnt: 'cfmcnt_asc',               // 구매확정건수
                canclecnt: 'canclecnt_asc',         // 취소건수
                returncnt: 'returncnt_asc',         // 반품건수
                exchangecnt: 'exchangecnt_asc'      // 교환건수
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            list: [],               // 조회 데이터
            count: {                // 데이터 건수
                totalcnt: 0,        // 전체 건수
            },
            isRead : false,
            isWrite : false,
            activeOrderCode: '',
            activeUserNo: '',
            isShowMemDetail: false,     // 회원상세 팝업 노출여부
            isShowOrderDetail: false,   // 주문상세 팝업 노출여부
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            // 공통코드 조회
            this.getCommonCodeList();
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey ='';
            this.searchData.sword = '';
            this.searchData.period = 'aday_0';
            this.searchData.startdate = this.$util.getDate('-');
            this.searchData.enddate = this.$util.getDate('-');
            if (!this.isPartner) {
                this.searchData.isallchannel = 'T';
                this.searchData.pricekey = 'payment';
                this.searchData.minprice = '';
                this.searchData.maxprice = '';
                this.searchData.isnonmember = '';
                this.searchData.isfrstorder = '';
                this.searchData.isallmember = 'T';
                this.searchData.isallmemlv = 'T';
                this.searchData.isallpayway = 'T';

                this.checkAllChannel();
                this.checkAllMembertype();
                this.checkAllMemlvtype();
                this.checkAllPaywaytype();
            } else {
                this.searchData.dealerno = this.user.no;
                for (let i=this.searchData.skeyArr.length-1; i>0; i--) {
                    if (!this.searchData.skeyArr[i].isShowPartner) {
                        this.searchData.skeyArr.splice(i, 1);
                    }
                }
            }
        },
        // 목록 조회
        searchList: function(page) {
            // 조회일자 1년 이내 체크
            if (this.$util.isNull(this.searchData.startdate) || this.$util.isNull(this.searchData.enddate)){
                alert('조회일자는 필수 조회조건 입니다.');
                return;
            } else {
                let startDate = this.searchData.startdate.replaceAll('-', '');
                let endDate = this.searchData.enddate.replaceAll('-', '');
                let checkMaxDate = this.$util.getAddDate(this.$util.getAddYear(startDate, 1), -1);
                if (endDate > checkMaxDate) {
                    alert('조회일자는 최대 1년까지만 조회 가능합니다.');
                    return;
                }
            }

            // 목록조회
            this.pagingData.page = this.$util.isNull(page)? this.pagingData.page : 1;
            let params = Object.assign(this.searchData, this.pagingData);
            params.isPartner = this.isPartner;
            this.$http.post('/admin/order/manage/listall', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.list = data.list;
                    this.count = data.count;
                    this.pagingData.listCount = data.count.totalcnt;
                    this.$util.dataSetSearchParam(this);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['MUAPPCHTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE', 'PAYWAYTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 검색조건 초기화
                    this.initSearchData();
                    this.$util.componentSetSearchParam(this);
                    // 목록 조회
                    this.searchList();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 조회조건 - 적용채널 전체 체크
        checkAllChannel: function() {
            let isAllCheck = this.searchData.isallchannel;
            this.searchData.muappchtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.searchData.muappchtypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 대상회원유형 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.searchData.isallmember;
            this.searchData.mumembertypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.dadamembertype){
                    this.searchData.mumembertypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 대상회원등급 전체체크
        checkAllMemlvtype: function() {
            let isAllCheck = this.searchData.isallmemlv;
            this.searchData.mumemlvtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.memlvtype){
                    this.searchData.mumemlvtypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 결제수단 전체체크
        checkAllPaywaytype: function() {
            let isAllCheck = this.searchData.isallpayway;
            this.searchData.paywaytypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.paywaytype){
                    this.searchData.paywaytypeArr.push(type.cmcode);
                }
            }
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startdate = value;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.enddate = value;
        },
        // 페이징 콜백
        setPagingData(param){
            this.pagingData = param;
            this.searchList();
        },
        loopNumberForPaging(index) {
            if(this.pagingData.page > 1){
                let page = this.pagingData.page - 1;
                return (index+1) + (page * this.pagingData.pageCount);
            } else {
                return (index+1);
            }
        },
        // 정렬
        sortToggle(key){
            if (!this.isRead) return;
            
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;
            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        // 엑셀다운로드
        fnExcelDownload: function() {
            if (this.list.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let config = { responseType: 'arraybuffer' };
            let params = this.searchData;
            params.isPartner = this.isPartner;
            this.$http.post('/admin/order/manage/all/exceldown', params, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 주문상세 팝업 오픈
        goOrderDetail: function(value) {
            this.isShowOrderDetail = true;
            this.activeOrderCode = value;
        },
        // 주문상세 팝업 닫기
        closeOrderDetail: function(isreload) {
            this.isShowOrderDetail = false;
            if (isreload) {
                this.searchList();
            }
        },
        // 회원상세 팝업 오픈
        goMemDetail: function(value) {
            this.isShowMemDetail = true;
            this.activeUserNo = value;
        },
        // 회원상세 팝업 닫기
        closeMemDetail: function() {
            this.isShowMemDetail = false;
        },
        // 주문상품목록 팝업 열기
        goOrderGoods: function(obj) {
            let param = { 
                ordno: obj.ordno, 
                dealerno: this.isPartner? this.searchData.dealerno:'', 
                isPartner: this.isPartner
            };
            this.$eventBus.$emit('modalShow', OrderGoodsPopup, param, null);
        }
    },
    watch: {
        // 조회기간
        'searchData.period': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]) * -1;

            if (type == 'aday') {
                this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getDate('-');
            } else if (type == 'months') {
                this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getDate('-');
            }
        },
        // 주문경로
        'searchData.muappchtypeArr': function(value) {
            if (value.length < this.commonCode.muappchtype.length) {
                this.searchData.isallchannel = 'F';
            } else {
                this.searchData.isallchannel = 'T';
            }
        },
        // 대상회원유형
        'searchData.mumembertypeArr': function(value) {
            if (value.length < this.commonCode.dadamembertype.length) {
                this.searchData.isallmember = 'F';
            } else {
                this.searchData.isallmember = 'T';
            }
        },
        // 대상회원등급
        'searchData.mumemlvtypeArr': function(value) {
            if (value.length < this.commonCode.memlvtype.length) {
                this.searchData.isallmemlv = 'F';
            } else {
                this.searchData.isallmemlv = 'T';
            }
        },
        // 결제수단
        'searchData.paywaytypeArr': function(value) {
            if (value.length < this.commonCode.paywaytype.length) {
                this.searchData.isallpayway = 'F';
            } else {
                this.searchData.isallpayway = 'T';
            }
        },
        // 숫자만입력
        'searchData.minprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.searchData.minprice = value.replace(/(^0[\d]|[^\d])/gi, '');
        },
        'searchData.maxprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.searchData.maxprice = value.replace(/(^0[\d]|[^\d])/gi, '');
        }
    }
}
</script>