<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="boxing search-area">
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
                    <dt>조회기간</dt>
                    <dd>
                        <select v-model="searchData.dtkey">
                            <option value="regdate">문의일자</option>
                            <option value="repregdate">답변일자</option>
                        </select>
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
                </dl>
                <dl v-if="searchData.isAdmin">
                    <dt>판매구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="ispbgoods" id="ispbgoodsAll" v-model="searchData.ispbgoods" value="" @click="searchData.dealerno=''"/>
                            <label for="ispbgoodsAll">전체</label>
                            <input type="radio" name="ispbgoods" id="ispbgoodsT" v-model="searchData.ispbgoods" value="T" @click="searchData.dealerno=''"/>
                            <label for="ispbgoodsT">직매입</label>
                            <input type="radio" name="ispbgoods" id="ispbgoodsF" v-model="searchData.ispbgoods" value="F"/>
                            <label for="ispbgoodsF">위탁</label>
                        </div>
                        <select :disabled="searchData.ispbgoods!='F'" v-model="searchData.dealerno">
                            <option value="">파트너사 선택</option>
                            <option v-for="item in searchData.partnerList" :key="item.no" :value="item.no">{{ item.name }} </option>
                        </select>
                    </dd>
                </dl>
                <dl v-if="searchData.isAdmin">
                    <dt>상태</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllAsstatus" v-model="searchData.isallasstatus" true-value="T" false-value="F" @change="checkAllAsstatus">
                            <label for="chkAllAsstatus">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.asstatus" :key="item.cmcode">
                            <input type="checkbox" :id="'asstatus_'+item.cmcode" v-model="searchData.asstatusArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'asstatus_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="!searchData.isAdmin">
                    <dt>상태</dt>
                    <dd>
                        <div class="radio_wrap">
                        <input type="radio" name="status" id="isreply" v-model="searchData.isreply" value="" checked/><label for="isreply">전체</label>
                        <input type="radio" name="status" id="isreplyF" v-model="searchData.isreply" value="F"/><label for="isreplyF">대기</label>
                        <input type="radio" name="status" id="isreplyT" v-model="searchData.isreply" value="T"/><label for="isreplyT">완료</label>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ count.totalcnt }}</strong>건</span>
                    <span v-if="searchData.isAdmin">접수완료 <strong>{{ count.receiptcnt }}</strong>건</span>
                    <span v-if="searchData.isAdmin">신청취소 <strong>{{ count.cancelcnt }}</strong>건</span>
                    <span v-if="searchData.isAdmin">A/S완료 <strong>{{ count.compcnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn red-line" @click="cancelApply" v-if="searchData.isAdmin">신청취소</button>
                    <button type="button" class="btn blue-line" @click="sendPartners" v-if="searchData.isAdmin">파트너사 전송</button>
                    <select v-model="pagingData.pageCount">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>        
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>A/S 문의 목록</caption>
                <colgroup v-if="searchData.isAdmin">
                    <col width="1%" /><!-- checkbox -->
                    <col width="2%" /><!-- No -->
                    <col width="3.5%" /><!-- 판매구분 -->
                    <col width="4.5%" /><!-- 파트너사명 -->
                    <col width="4.5%" /><!-- 아이디 -->
                    <col width="3.5%" /><!-- 고객명 -->
                    <col width="5%" /><!-- 휴대폰번호 -->
                    <col width="5.5%" /><!-- 이메일 -->
                    <col width="4.5%" /><!-- 주문번호 -->
                    <col width="4%" /><!-- 송장번호 -->
                    <col width="9%" /><!-- 상품명 -->
                    <col width="3%" /><!-- A/S신청수량 -->
                    <col width="4%" /><!-- 접수자 -->
                    <col width="5%" /><!-- 연락처 -->
                    <col width="7%" /><!-- 주소 -->
                    <col width="" /><!-- A/S접수내용 -->
                    <col width="5.6%" /><!-- 접수일시 -->
                    <col width="3.5%" /><!-- 답변자 -->
                    <col width="5.6%" /><!-- 답변일시 -->
                    <col width="4.5%" /><!-- 첨부이미지/동영상 -->
                    <col width="4%" /><!-- 전송여부 -->
                    <col width="4%" /><!-- A/S상태 -->
                </colgroup>
                <colgroup v-else>
                    <col width="2%" /><!-- No -->
                    <col width="9%" /><!-- 주문번호 -->
                    <col width="7%" /><!-- 송장번호 -->
                    <col width="15%" /><!-- 상품명 -->
                    <col width="" /><!-- A/S접수내용 -->
                    <col width="10%" /><!-- 문의일시 -->
                    <col width="7%" /><!-- 답변자 -->
                    <col width="10%" /><!-- 답변일시 -->
                    <col width="4.5%" /><!-- 첨부이미지/동영상 -->
                    <col width="5%" /><!-- 답변상태 -->
                </colgroup>
                <thead>
                    <tr>
                        <th v-if="searchData.isAdmin"><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                        <th>No</th>
                        <th v-if="searchData.isAdmin">판매구분
                            <button type="button" class="sort" :value="sortData.ispbgoods"
                                :class="[{up : sortData.ispbgoods === 'ispbgoods_asc'}, {down : sortData.ispbgoods === 'ispbgoods_desc'}]"
                                @click="sortToggle(sortData.ispbgoods)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">파트너사명
                            <button type="button" class="sort" :value="sortData.dealername"
                                :class="[{up : sortData.dealername === 'dealername_asc'}, {down : sortData.dealername === 'dealername_desc'}]"
                                @click="sortToggle(sortData.dealername)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">아이디
                            <button type="button" class="sort" :value="sortData.userid"
                                :class="[{up : sortData.userid === 'userid_asc'}, {down : sortData.userid === 'userid_desc'}]"
                                @click="sortToggle(sortData.userid)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">고객명
                            <button type="button" class="sort" :value="sortData.username"
                                :class="[{up : sortData.username === 'username_asc'}, {down : sortData.username === 'username_desc'}]"
                                @click="sortToggle(sortData.username)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">휴대폰번호
                            <button type="button" class="sort" :value="sortData.usermobile"
                                :class="[{up : sortData.usermobile === 'usermobile_asc'}, {down : sortData.usermobile === 'usermobile_desc'}]"
                                @click="sortToggle(sortData.usermobile)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">이메일
                            <button type="button" class="sort" :value="sortData.useremail"
                                :class="[{up : sortData.useremail === 'useremail_asc'}, {down : sortData.useremail === 'useremail_desc'}]"
                                @click="sortToggle(sortData.useremail)"></button>
                        </th>
                        <th>주문번호
                            <button type="button" class="sort" :value="sortData.ordno"
                                :class="[{up : sortData.ordno === 'ordno_asc'}, {down : sortData.ordno === 'ordno_desc'}]"
                                @click="sortToggle(sortData.ordno)"></button>
                        </th>
                        <th>송장번호
                            <button type="button" class="sort" :value="sortData.invoiceno"
                                :class="[{up : sortData.invoiceno === 'invoiceno_asc'}, {down : sortData.invoiceno === 'invoiceno_desc'}]"
                                @click="sortToggle(sortData.invoiceno)"></button>
                        </th>
                        <th>상품명
                            <button type="button" class="sort" :value="sortData.goodsname"
                                :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                                @click="sortToggle(sortData.goodsname)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">A/S신청수량</th>
                        <th v-if="searchData.isAdmin">접수자
                            <button type="button" class="sort" :value="sortData.writer"
                                :class="[{up : sortData.writer === 'writer_asc'}, {down : sortData.writer === 'writer_desc'}]"
                                @click="sortToggle(sortData.writer)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">연락처
                            <button type="button" class="sort" :value="sortData.tel"
                                :class="[{up : sortData.tel === 'tel_asc'}, {down : sortData.tel === 'tel_desc'}]"
                                @click="sortToggle(sortData.tel)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">주소</th>
                        <th>A/S접수내용
                            <button type="button" class="sort" :value="sortData.content"
                                :class="[{up : sortData.content === 'content_asc'}, {down : sortData.content === 'content_desc'}]"
                                @click="sortToggle(sortData.content)"></button>
                        </th>
                        <th>접수일시
                            <button type="button" class="sort" :value="sortData.regdate"
                                :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                @click="sortToggle(sortData.regdate)"></button>
                        </th>
                        <th>답변자
                            <button type="button" class="sort" :value="sortData.repname"
                                :class="[{up : sortData.repname === 'repname_asc'}, {down : sortData.repname === 'repname_desc'}]"
                                @click="sortToggle(sortData.repname)"></button>
                        </th>
                        <th>답변일시
                            <button type="button" class="sort" :value="sortData.repregdate"
                                :class="[{up : sortData.repregdate === 'repregdate_asc'}, {down : sortData.repregdate === 'repregdate_desc'}]"
                                @click="sortToggle(sortData.repregdate)"></button>
                        </th>
                        <th>첨부이미지<br>/동영상</th>
                        <th v-if="searchData.isAdmin">전송여부
                            <button type="button" class="sort" :value="sortData.ispass"
                                :class="[{up : sortData.ispass === 'ispass_asc'}, {down : sortData.ispass === 'ispass_desc'}]"
                                @click="sortToggle(sortData.ispass)"></button>
                        </th>
                        <th v-if="searchData.isAdmin">A/S상태
                            <button type="button" class="sort" :value="sortData.asstatus"
                                :class="[{up : sortData.asstatus === 'asstatus_asc'}, {down : sortData.asstatus === 'asstatus_desc'}]"
                                @click="sortToggle(sortData.asstatus)"></button>
                        </th>
                        <th v-if="!searchData.isAdmin">답변상태
                            <button type="button" class="sort" :value="sortData.isreply"
                                :class="[{up : sortData.isreply === 'isreply_asc'}, {down : sortData.isreply === 'isreply_desc'}]"
                                @click="sortToggle(sortData.isreply)"></button>
                        </th>
                    </tr>
                </thead>
                <tbody v-if="list.length > 0">
                    <tr v-for="(item, index) in list" :key="item.asidx" :class="{'bg gray': item.asstatus===$store.getters['ADMIN'].AS_COMP}"><!-- 완료건 bg 컬러 변경 -->
                        <td v-if="searchData.isAdmin"><input type="checkbox" :id="item.asidx" v-model="checkedList" :value="item.asidx" 
                            @change="checkList($event.target.checked)" :disabled="item.asstatus===$store.getters['ADMIN'].AS_COMP"/></td>
                        <td>{{ index+1 }}</td>
                        <td v-if="searchData.isAdmin">{{ item.ispbgoodsname }}</td>
                        <td v-if="searchData.isAdmin">{{ item.dealername }}</td>
                        <td v-if="searchData.isAdmin">{{ item.userid }}</td>
                        <td v-if="searchData.isAdmin"><a href="javascript:void(0);" class="link" @click="goMemDetail(item.userno)">{{ item.username }}</a></td>
                        <td v-if="searchData.isAdmin">
                            <a href="javascript:void(0);" class="link" @click="goCommonSms(item.userno)">{{ $util.maskTel(item.usermobile) }}</a>
                            <!-- <a @click="goCommonSms(item.userno)"><i class="icon-sms"></i></a> -->
                        </td>
                        <td v-if="searchData.isAdmin">
                            <a href="javascript:void(0);" class="link" @click="goCommonMail(item.userno)">{{ item.useremail }}</a>
                            <!-- <a @click="goCommonMail(item.userno)"><i class="icon-mail"></i></a> -->
                        </td>
                        <td><a href="javascript:void(0);" class="link" @click="goOrderDetail(item.ordno)">{{ item.ordno }}</a></td>
                        <td>{{ item.invoiceno }}</td>
                        <td class="left">
                            <a href="javascript:void(0);" class="link" @click="goFrontGoodsDetail(item.goodscode)">
                                {{ item.goodsname }}<br>옵션:{{ item.optionconts }}
                            </a>
                        </td>
                        <td v-if="searchData.isAdmin">{{ item.ascnt }}</td>
                        <td v-if="searchData.isAdmin">{{ item.writer }}</td>
                        <td v-if="searchData.isAdmin">{{ $util.maskTel(item.tel) }}</td>
                        <td v-if="searchData.isAdmin">{{ item.writeraddr }}</td>
                        <td class="left"><a href="javascript:void(0);" class="link" @click="goAsDetail(item.asidx)">{{ item.listcontent }}</a></td>
                        <td>{{ item.regdate }}</td>
                        <td>{{ item.repname }}</td>
                        <td>{{ item.repregdate }}</td>
                        <td>{{ item.attachcnt }}</td>
                        <td v-if="searchData.isAdmin">{{ item.ispassname }}</td>
                        <td v-if="searchData.isAdmin && item.asstatus===$store.getters['ADMIN'].AS_COMP" @mouseover="showRepLayout(index, true)" @mouseout="showRepLayout(index, false)">
                            <a href="javascript:void(0);" class="link">{{ item.asstatusname }}</a>
                            <!-- 완료에 마우스오버 했을 경우 레이어 팝업 노출 (클래스 dpn -> dpb) -->
                            <div class="a-cell" :class="[ isHovering[index]? 'dpb' : 'dpn']">
                                <div v-html="$util.showImageResize(item.repcontent)"></div>
                            </div>
                            <!-- // 레이어 팝업 -->
                        </td>
                        <td v-if="searchData.isAdmin && item.asstatus!==$store.getters['ADMIN'].AS_COMP">{{ item.asstatusname }}</td>
                        <td v-if="!searchData.isAdmin && item.isreply==='T'" @mouseover="showRepLayout(index, true)" @mouseout="showRepLayout(index, false)">
                            <a href="javascript:void(0);" class="link">{{ item.isreplyname }}</a>
                            <!-- 완료에 마우스오버 했을 경우 레이어 팝업 노출 (클래스 dpn -> dpb) -->
                            <div class="a-cell" :class="[ isHovering[index]? 'dpb' : 'dpn']">
                                <div v-html="$util.showImageResize(item.repcontent)"></div>
                            </div>
                            <!-- // 레이어 팝업 -->
                        </td>
                        <td v-if="!searchData.isAdmin && item.isreply==='F'">{{ item.isreplyname }}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td :colspan="searchData.isAdmin? '22' : '10'">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>         
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
            </div>
        </div>
        <OrderDetail v-if="isShowOrderDetail" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetail" />
        <AdminMemberInfo v-if="isShowMemDetail" :activeUserNo="activeUserNo" @closeDetail="closeMemDetail" />
        <AsDetail v-if="isShowAsDetail" :activeAsIdx="activeAsIdx" @closeDetail="closeAsDetail" />
        <CommonSms v-if="isShowCommonSms" :user-no="activeUserNo" @close="closeCommonSms" />
        <CommonMail v-if="isShowCommonMail" :user-no="activeUserNo" @close-popup="closeCommonMail" />
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator.vue";
import CommonSms from '@/views/admin/common/popup/CommonSms.vue';
import CommonMail from '@/views/admin/common/popup/CommonMail.vue';
import AdminMemberInfo from '@views.admin/member/info/AdminMemberInfo.vue';
import OrderDetail from '@views.admin/order/info/OrderDetail.vue';
import AsDetail from '@/views/admin/cs/as/AsDetail.vue';

export default {
    name: 'admin.cs.aslist',
    components: {
        CommonNavigator,
        CommonPageNavigator,
        CommonDatePicker,
        CommonSms,
        CommonMail,
        AdminMemberInfo,
        OrderDetail,
        AsDetail
    },
    mounted() {
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
            searchData: {
                isAdmin: this.$util.isAuthorized('ADMIN_USER'),
                skeyArr: [
                    {key: '', name: '전체', isShowPartner: true, isShowAdmin: true},
                    {key: 'userid', name: '아이디', isShowPartner: false, isShowAdmin: true},
                    {key: 'username', name: '고객명', isShowPartner: false, isShowAdmin: true},
                    {key: 'ordno', name: '주문번호', isShowPartner: true, isShowAdmin: true},
                    {key: 'invoiceno', name: '송장번호', isShowPartner: true, isShowAdmin: true},
                    {key: 'goodsname', name: '상품명', isShowPartner: true, isShowAdmin: true},
                    {key: 'writer', name: '접수자', isShowPartner: false, isShowAdmin: true},
                    {key: 'content', name: 'A/S접수내용', isShowPartner: true, isShowAdmin: true},
                    {key: 'subject', name: '제목', isShowPartner: false, isShowAdmin: false}
                ],
                skey: '',               // 직접검색KEY
                sword: '',              // 직접검색어
                dtkey: '',              // 날짜검색KEY
                period: '',             // 조회일자기간
                startdate: '',          // 조회시작일자
                enddate: '',            // 조회종료일자
                ispbgoods: '',          // 직매입여부
                dealerno: '',           // 입점업체번호
                isallasstatus: 'T',     // AS상태전체체크여부
                asstatusArr: [],        // AS상태배열
                isreply: '',            // 답변상태
                partnerList: [],        // 파트너사 목록
                psort: 'regdate_desc'   // 정렬조건 (default: 등록일자 최신순)
            },
            sortData: {
                ispbgoods: 'ispbgoods_asc',     // 판매구분
                dealername: 'dealername_asc',   // 파트너사명
                userid: 'userid_asc',           // 아이디
                username: 'username_asc',       // 고객명
                usermobile: 'usermobile_asc',   // 휴대폰번호
                useremail: 'useremail_asc',     // 이메일
                ordno: 'ordno_asc',             // 주문번호
                invoiceno: 'invoiceno_asc',     // 송장번호
                goodsname: 'goodsname_asc',     // 상품명
                writer: 'writer_asc',           // 접수자
                tel: 'tel_asc',                 // 연락처
                content: 'content_asc',         // A/S접수내용
                subject: 'subject_asc',         // 질문제목
                regdate: 'regdate_desc',        // 접수일시
                repname: 'repname_asc',         // 답변자
                repregdate: 'repregdate_asc',   // 답변일시
                ispass: 'ispass_asc',           // 전송여부
                asstatus: 'asstatus_asc',       // A/S상태
                isreply: 'isreply_asc'          // 답변상태
            },
            commonCode: {
                asstatus: []        // AS상태
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            isallchk: false,        // 목록 전체체크여부
            list: [],               // 조회 데이터
            count: {                // 데이터 건수
                totalcnt: 0,        // 전체 건수
                receiptcnt: 0,      // 접수완료 건수
                cancelcnt: 0,       // 신청취소 건수
                compcnt: 0          // AS완료 건수
            },
            checkedList: [],        // 선택된 목록
            isHovering: [],         // 호버된 배열
            isRead : false,
            isWrite : false,
            activeUserNo: '',
            activeOrderCode: '',
            activeAsIdx: '',
            isShowMemDetail: false,     // 회원상세 팝업 노출여부
            isShowOrderDetail: false,   // 주문상세 팝업 노출여부
            isShowAsDetail: false,      // A/S상세 팝업 노출여부
            isShowCommonSms: false,     // 공통SMS 팝업 노출여부
            isShowCommonMail: false,     // 공통Mail 팝업 노출여부
            isLink : false, //대시보드에서 링크를 타고왔는지 체크
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
              this.isLink = true;
            }

            this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
            // 관리자인 경우 조회조건 파트너사목록 필요
            if (this.searchData.isAdmin) {
                for (let i=this.searchData.skeyArr.length-1; i>0; i--) {
                    if (!this.searchData.skeyArr[i].isShowAdmin) {
                        this.searchData.skeyArr.splice(i, 1);
                    }
                }
                // 파트너목록 조회
                this.getPartnerList();
            }
            // 파트너사인 경우 로그인한 파트너사 정보 세팅
            else {
                this.searchData.ispbgoods = 'F';
                this.searchData.dealerno = this.user.no;
                for (let i=this.searchData.skeyArr.length-1; i>0; i--) {
                    if (!this.searchData.skeyArr[i].isShowPartner) {
                        this.searchData.skeyArr.splice(i, 1);
                    }
                }
            }
            // 공통코드 조회
            this.getCommonCodeList();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['ASSTATUS'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 검색조건 초기화
                    this.initSearchData();
                    this.$util.componentSetSearchParam(this);

                    if(this.isLink){
                      let linkParam = this.$route.params;
                      this.searchData.period = linkParam.period
                      this.searchData.startdate = linkParam.startdate;
                      this.searchData.enddate = linkParam.enddate;

                      if(typeof linkParam.asstatus !== 'undefined'){
                        this.searchData.isallasstatus = 'F';
                        this.searchData.asstatusArr = [linkParam.asstatus];
                      }
                    }
                    // 목록 조회
                    this.searchList();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 파트너사 목록 조회
        getPartnerList: function() {
            let params = this.searchData;
            params.isloading = false;
            this.$http.post('/admin/goods/manage/partner/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.searchData.partnerList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey ='';
            this.searchData.sword = '';
            this.searchData.dtkey = 'regdate';
            this.searchData.period = 'months_3';
            this.searchData.ispbgoods = this.searchData.isAdmin? '' : 'F';
            this.searchData.dealerno = this.searchData.isAdmin? '' : this.user.no;
            this.searchData.isallasstatus = 'T';

            this.checkAllAsstatus();
        },
        // 목록 조회
        searchList: function(page) {
            // 목록조회
            let params = Object.assign(this.searchData, this.pagingData);
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.isloading = true;
            this.$http.post('/admin/cs/as/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.list = data.list;
                    this.count = data.count;
                    this.pagingData.listCount = data.count.totalcnt;
                    this.$util.dataSetSearchParam(this);
                    this.isallchk = false;
                    this.checkedList = [];

                    for (let i=0; i<this.list.length; i++) {
                        this.isHovering.push(false);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 조회조건 - AS상태 전체 체크
        checkAllAsstatus: function() {
            let isAllCheck = this.searchData.isallasstatus;
            this.searchData.asstatusArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.asstatus){
                    this.searchData.asstatusArr.push(type.cmcode);
                }
            }
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    if (item.asstatus != this.$store.getters['ADMIN'].AS_COMP) {
                        this.checkedList.push(item.asidx);
                    }
                });
            }
        },
        // 목록 개별체크
        checkList: function() {
            let listCnt = this.list.filter(item=>{
                return item.asstatus != this.$store.getters['ADMIN'].AS_COMP;
            }).length;
            if (listCnt > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate: function(value) {
            this.searchData.startdate = value;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate: function(value) {
            this.searchData.enddate = value;
        },
        // 페이징 콜백
        setPagingData: function(param){
            this.pagingData = param;
            this.searchList();
        },
        // 정렬
        sortToggle: function(key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        showRepLayout: function(index, value) {
            this.$set(this.isHovering, index, value);
        },
        // 신청취소
        cancelApply: function() {
            if(this.checkedList.length === 0) {
                alert('취소할 게시물을 선택해주세요.');
                return;
            }
            for (let i=0; i<this.checkedList.length; i++) {
                let asidx = this.checkedList[i];
                for (let j=0; j<this.list.length; j++) {
                    let item = this.list[j];
                    if (asidx === item.asidx) {
                        if (item.asstatus != this.$store.getters['ADMIN'].AS_RECEIPT) {
                            alert('\'접수완료\'상태의 게시물만 취소가 가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                    }
                }
            }
            if(confirm('선택한 게시물을 취소하시겠습니까?')) {
                this.$http.post('/admin/cs/as/cancel', { asidxlist: this.checkedList })
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("취소가 완료되었습니다.");
                            this.searchList();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 파트너사 전송
        sendPartners: function() {
            if(this.checkedList.length === 0) {
                alert('파트너사에 전송할 게시물을 선택해주세요.');
                return;
            }
            for (let i=0; i<this.checkedList.length; i++) {
                let asidx = this.checkedList[i];
                for (let j=0; j<this.list.length; j++) {
                    let item = this.list[j];
                    if (asidx === item.asidx) {
                        if (item.asstatus != this.$store.getters['ADMIN'].AS_RECEIPT) {
                            alert('\'접수완료\'상태의 게시물만 전송이 가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                        if (item.ispbgoods === 'T') {
                            alert('직매입상품이 존재합니다. 확인후 진행해주세요.');
                            return;
                        }
                        if (this.$util.isNull(item.dealerno)) {
                            alert('파트너사 정보가 존재하지 않습니다. 확인후 진행해주세요.');
                            return;
                        }
                        if (item.ispass === 'T') {
                            alert('이미 전송된 게시물이 존재합니다. 확인후 진행해주세요.');
                            return;
                        }
                    }
                }
            }
            if(confirm('선택한 게시물을 파트너사에 전송하시겠습니까?')) {
                this.$http.post('/admin/cs/as/send', { asidxlist: this.checkedList })
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("전송이 완료되었습니다.");
                            this.searchList();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
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
        // 주문상세 팝업 오픈
        goOrderDetail: function(value) {
            this.isShowOrderDetail = true;
            this.activeOrderCode = value;
        },
        // 주문상세 팝업 닫기
        closeOrderDetail: function() {
            this.isShowOrderDetail = false;
        },
        // A/S문의 답변 팝업 오픈
        goAsDetail: function(value) {
            this.isShowAsDetail = true;
            this.activeAsIdx = value;
        },
        // A/S문의 답변 팝업 닫기
        closeAsDetail: function(isreroad) {
            this.isShowAsDetail = false;
            if (isreroad) {
                this.searchList();
            }
        },
        // SMS 공통 팝업 오픈
        goCommonSms(value) {
            this.isShowCommonSms = true;
            this.activeUserNo = value;
        },
        // SMS 공통 팝업 닫기
        closeCommonSms() {
            this.isShowCommonSms = false;
        },
        // EMAIL 공통 팝업 오픈
        goCommonMail(value) {
            this.isShowCommonMail = true;
            this.activeUserNo = value;
        },
        // EMAIL 공통 팝업 닫기
        closeCommonMail() {
            this.isShowCommonMail = false;
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        },
    },
    watch: {
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
        // AS상태
        'searchData.asstatusArr': function(value) {
            if (value.length < this.commonCode.asstatus.length) {
                this.searchData.isallasstatus = 'F';
            } else {
                this.searchData.isallasstatus = 'T';
            }
        },
    }
}
</script>
