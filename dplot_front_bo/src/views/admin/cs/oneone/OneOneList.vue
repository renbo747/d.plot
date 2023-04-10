<template>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
    <!-- 상세 -->
    <OneOneDetail
        ref="refDetail"
        v-if="isDetailShow"
        :idx="isDetailIdx"
    />

    <!-- SMS 보내기   -->
    <CommonSms
        @close="goCloseSms"
        v-if="isCommonSmsShow"
        :user-no="smsUserNo"
    />

    <!-- 메일 보내기   -->
    <CommonMail
        v-on:close-popup="goCloseMail"
        v-if="isCommonMailShow"
        :user-no="mailUserNo"
    />

    <!-- 유저 상세 정보 팝업 -->
    <AdminMemberInfo
        v-if="isMemberDetailShow"
        v-bind:activeUserNo="memberDetailUserNo"
        v-on:closeDetail="closeMemberInfoPopup"
        style="z-index: 1005;"
    />

    <!-- 상품 상세 -->
    <OrderDetail
        v-if="isOrderDetailShow"
        :activeOrderCode="activeOrderCode"
        @closeDetail="closeOrderDetailPopup"
    />

    <AdminCommonNavigator/>
    <div class="inner">
      <div class="boxing search-area">
        <dl>
          <dt>직접검색</dt>
          <dd>
            <select v-model="searchData.skey">
              <option value="">전체</option>
              <option v-if="searchData.isAdmin" value="memberid">아이디</option>
              <option v-if="searchData.isAdmin" value="name">고객명</option>
              <option v-if="searchData.isAdmin" value="ordno">주문번호</option>
              <option value="invoiceno">송장번호</option>
              <option value="goodsname">상품명</option>
              <option value="content">문의내용</option>
            </select>
            <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
          </dd>
        </dl>
        <dl>
          <dt>조회기간</dt>
          <dd>
            <select v-model="searchData.sdate">
              <option value="start">문의일자</option>
              <option value="end">답변일자</option>
            </select>
            <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
            <span>-</span>
            <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/>
            <div class="radio_wrap">
              <input type="radio" v-model="searchData.period" id="rd1" value='-1'/><label
                for="rd1">어제</label>
              <input type="radio" v-model="searchData.period" id="rd2" value='0'/><label
                for="rd2">오늘</label>
              <input type="radio" v-model="searchData.period" id="rd3" value='7'/><label
                for="rd3">일주일</label>
              <input type="radio" v-model="searchData.period" id="rd4" value='1'/><label
                for="rd4">1개월</label>
              <input type="radio" v-model="searchData.period" id="rd5" value='3'/><label
                for="rd5">3개월</label>
              <input type="radio" v-model="searchData.period" id="rd6" value='6'/><label
                for="rd6">6개월</label>
            </div>
          </dd>
        </dl>
        <dl v-if="searchData.isAdmin">
          <dt>판매구분</dt>
          <dd>
            <div class="radio_wrap">
              <input type="radio" name="rad" id="rd11" v-model="searchData.ispbgoods" value="" checked @click="searchData.dealerno=''"/><label
                for="rd11">전체</label>
              <input type="radio" name="rad" id="rd12" v-model="searchData.ispbgoods" value="T" @click="searchData.dealerno=''"/><label
                for="rd12">직매입</label>
              <input type="radio" name="rad" id="rd13" v-model="searchData.ispbgoods" value="F"/><label
                for="rd13">파트너사</label>
            </div>
            <select v-model="searchData.dealerno" :disabled="searchData.ispbgoods !== 'F'">
              <option value="">파트너사 선택</option>
              <option v-for="(row, i) in this.partnersList" :key="i" :value="row.no">{{ row.name }}</option>
            </select>
          </dd>
        </dl>
        <dl v-if="searchData.isAdmin">
          <dt>문의유형</dt>
          <dd>
            <select v-model="searchData.inquirytype">
              <option value="">전체</option>
              <option v-for="(row, i) in inquiryCodeList" :key="i" :value="row.cmcode">{{ row.codename }}</option>
            </select>
          </dd>
        </dl>
        <dl>
          <dt>상태</dt>
          <dd>
            <div class="radio_wrap">
              <input type="radio" name="status" id="rd01" v-model="searchData.isreply" value="" checked/><label
                for="rd01">전체</label>
              <input type="radio" name="status" id="rd02" v-model="searchData.isreply" value="F"/><label
                for="rd02">대기</label>
              <input type="radio" name="status" id="rd03" v-model="searchData.isreply" value="T"/><label
                for="rd03">완료</label>
            </div>
          </dd>
        </dl>
      </div>
      <div class="btn-group" v-if="isRead">
        <button type="button" class="btn big blue" @click="onSearch(1)">검색</button>
        <button type="button" class="btn big gray" @click="onSearchDataRest">초기화</button>
      </div>
      <div class="caption-group mt10 clearfix">
        <div class="total-group fl">
          <span class="total">전체 <strong>{{ stateData.total }}</strong>건</span>
          <span v-if="searchData.isAdmin">대기 <strong>{{ stateData.stay }}</strong>건</span>
          <span v-if="searchData.isAdmin">완료 <strong>{{ stateData.comp }}</strong>건</span>
        </div>
        <div class="btn-group fr">
          <button type="button" v-if="isWrite && searchData.isAdmin" class="btn blue-line" @click="sendPartners()">파트너사전송</button>
          <select v-model="pagingData.pageCount" v-if="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <table cellpadding="0" cellspacing="0" class="data-tb align-c">
        <caption>1:1 문의 목록</caption>
        <colgroup>
          <col v-if="searchData.isAdmin" width="2%"/><!-- checkbox -->
          <col width="2%"/><!-- No -->
          <col v-if="searchData.isAdmin" width="4%"/><!-- 판매구분 -->
          <col v-if="searchData.isAdmin" width="4%"/><!-- 파트너사명 -->
          <col width="5%"/><!-- 문의유형 -->
          <col v-if="searchData.isAdmin" width="5%"/><!-- 아이디 -->
          <col v-if="searchData.isAdmin" width="6%"/><!-- 고객명 -->
          <col v-if="searchData.isAdmin" width="8%"/><!-- 휴대폰번호 -->
          <col v-if="searchData.isAdmin" width="7%"/><!-- 이메일 -->
          <col width="5%"/><!-- 주문번호 -->
          <col width="7%"/><!-- 송장번호 -->
          <col width=""/><!-- 상품명 -->
          <col width=""/><!-- 문의내용 -->
          <col :width="searchData.isAdmin ? '6%' : '10%'"/><!-- 문의일시 -->
          <col :width="searchData.isAdmin ? '4%' : '6%'"/><!-- 답변자 -->
          <col :width="searchData.isAdmin ? '6%' : '10%'"/><!-- 답변일시 -->
          <col width="3%"/><!-- 첨부이미지 -->
          <col v-if="searchData.isAdmin" width="4%"/><!-- 전송여부 -->
          <col width="4%"/><!-- 답변상태 -->
        </colgroup>
        <thead>
        <tr>
          <th v-if="searchData.isAdmin"><input type="checkbox" id="chkall" v-model="isChecked" @change="checkAllList($event.target.checked)"/></th>
          <th>No</th>
          <th v-if="searchData.isAdmin">판매구분
            <button type="button" v-if="isRead"
                    :value="sortData.ispbgoods"
                    class="sort"
                    :class="[{up : sortData.ispbgoods === 'ispbgoods_asc'}, {down : sortData.ispbgoods === 'ispbgoods_desc'}]"
                    @click="sortToggle(sortData.ispbgoods)">
            </button>
          </th>
          <th v-if="searchData.isAdmin">파트너사명
            <button type="button" v-if="isRead"
                    :value="sortData.dealername"
                    class="sort"
                    :class="[{up : sortData.dealername === 'dealername_asc'}, {down : sortData.dealername === 'dealername_desc'}]"
                    @click="sortToggle(sortData.dealername)">
            </button>
          </th>
          <th>문의유형
            <button type="button" v-if="isRead"
                    :value="sortData.inquirytype"
                    class="sort"
                    :class="[{up : sortData.inquirytype === 'inquirytype_asc'}, {down : sortData.inquirytype === 'inquirytype_desc'}]"
                    @click="sortToggle(sortData.inquirytype)">
            </button>
          </th>
          <th v-if="searchData.isAdmin">아이디
            <button type="button" v-if="isRead"
                    :value="sortData.userid"
                    class="sort"
                    :class="[{up : sortData.userid === 'userid_asc'}, {down : sortData.userid === 'userid_desc'}]"
                    @click="sortToggle(sortData.userid)">
            </button>
          </th>
          <th v-if="searchData.isAdmin">고객명
            <button type="button" v-if="isRead"
                    :value="sortData.username"
                    class="sort"
                    :class="[{up : sortData.username === 'username_asc'}, {down : sortData.username === 'username_desc'}]"
                    @click="sortToggle(sortData.username)">
            </button>
          </th>
          <th v-if="searchData.isAdmin">휴대폰번호
            <button type="button" v-if="isRead"
                    :value="sortData.mobile"
                    class="sort"
                    :class="[{up : sortData.mobile === 'mobile_asc'}, {down : sortData.dealername === 'mobile_desc'}]"
                    @click="sortToggle(sortData.mobile)">
            </button>
          </th>
          <th v-if="searchData.isAdmin">이메일
            <button type="button" v-if="isRead"
                    :value="sortData.email"
                    class="sort"
                    :class="[{up : sortData.email === 'email_asc'}, {down : sortData.email === 'email_desc'}]"
                    @click="sortToggle(sortData.email)">
            </button>
          </th>
          <th>주문번호
            <button type="button" v-if="isRead"
                    :value="sortData.ordno"
                    class="sort"
                    :class="[{up : sortData.ordno === 'ordno_asc'}, {down : sortData.ordno === 'ordno_desc'}]"
                    @click="sortToggle(sortData.ordno)">
            </button>
          </th>
          <th>송장번호 
            <button type="button" v-if="isRead"
                    :value="sortData.invoiceno"
                    class="sort"
                    :class="[{up : sortData.invoiceno === 'invoiceno_asc'}, {down : sortData.invoiceno === 'invoiceno_desc'}]"
                    @click="sortToggle(sortData.invoiceno)">
            </button>
          </th>
          <th>상품명
            <button type="button" v-if="isRead"
                    :value="sortData.goodsname"
                    class="sort"
                    :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                    @click="sortToggle(sortData.goodsname)">
            </button>
          </th>
          <th>문의내용
            <button type="button" v-if="isRead"
                    :value="sortData.content"
                    class="sort"
                    :class="[{up : sortData.content === 'content_asc'}, {down : sortData.content === 'content_desc'}]"
                    @click="sortToggle(sortData.content)">
            </button>
          </th>
          <th>문의일시
            <button type="button" v-if="isRead"
                    :value="sortData.regdate"
                    class="sort"
                    :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                    @click="sortToggle(sortData.regdate)">
            </button>
          </th>
          <th>답변자
            <button type="button" v-if="isRead"
                    :value="sortData.repname"
                    class="sort"
                    :class="[{up : sortData.repname === 'repname_asc'}, {down : sortData.repname === 'repname_desc'}]"
                    @click="sortToggle(sortData.repname)">
            </button>
          </th>
          <th>답변일시
            <button type="button" v-if="isRead"
                    :value="sortData.repregdate"
                    class="sort"
                    :class="[{up : sortData.repregdate === 'repregdate_asc'}, {down : sortData.repregdate === 'repregdate_desc'}]"
                    @click="sortToggle(sortData.repregdate)">
            </button>
          </th>
          <th>첨부이미지</th>
          <th v-if="searchData.isAdmin">전송여부
            <button type="button" v-if="isRead"
                    :value="sortData.ispass"
                    class="sort"
                    :class="[{up : sortData.ispass === 'ispass_asc'}, {down : sortData.ispass === 'ispass_desc'}]"
                    @click="sortToggle(sortData.ispass)">
            </button>
          </th>
          <th>답변상태
            <button type="button" v-if="isRead"
                    :value="sortData.isreply"
                    class="sort"
                    :class="[{up : sortData.isreply === 'isreply_asc'}, {down : sortData.isreply === 'isreply_desc'}]"
                    @click="sortToggle(sortData.isreply)">
            </button>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr v-for="(row, i) in listData" :key="i">
          <td v-if="searchData.isAdmin"><input type="checkbox" :id="'chk_'+i" v-model="checkedList" :value="row" :disabled="row.ispass === 'T' || row.ispbgoods === '직매입' || row.isreply === '완료' || $util.isNull(row.goodsno)"></td>
          <td>{{ row.rn }}</td>
          <td v-if="searchData.isAdmin">{{ $util.isNull(row.goodsno) ? '' : row.ispbgoods }}</td>
          <td v-if="searchData.isAdmin">{{ row.dealername }}</td>
          <td>{{ row.inquirytype }}</td>
          <td v-if="searchData.isAdmin">{{ row.userid }}</td>
          <td v-if="searchData.isAdmin">
            <a @click="goUserDtl(row.no)" class="link">{{ row.name }}</a>
            <a @click="goSms(row.no)"><i class="icon-sms"></i></a>
            <a @click="goMail(row.no)"><i class="icon-mail"></i></a>
          </td>
          <td v-if="searchData.isAdmin">{{ $util.maskTel(row.mobile) }}</td>
          <td v-if="searchData.isAdmin">{{ row.email }}</td>
          <td><a @click="goOrderDetail(row.ordno)" class="link">{{ row.ordno }}</a></td>
          <td>{{ row.invoiceno }}</td>
          <td class="left"><a @click="onGoodsFront(row.goodscode)" class="link">{{ row.goodsname }}<br>{{ !$util.isNull(row.optionname) ? '옵션 : '+row.optionname : ''}}</a></td>
          <td class="left"><a class="link" @click="goDetail(row.idx)">{{ row.content }}</a></td>
          <td>{{ row.regdate }}</td>
          <td>{{ row.repname }}</td>
          <td>{{ row.repregdate }}</td>
          <td>{{ row.filecount }}</td>
          <td v-if="searchData.isAdmin">{{ row.ispbgoods === '직매입' ? '-' : row.ispass === 'T' ? '전송완료' : '미 전송' }}</td>
          <td v-if="row.isreply === '완료'" @mouseover="mouseOver(i)" @mouseout="mouseOut(i)">
            <a class="link">{{ row.isreply }}</a>
            <!-- 완료에 마우스오버 했을 경우 레이어 팝업 노출 (클래스 dpn -> dpb) -->
            <div :class="[ isHovering[i]? 'a-cell dpb' : 'a-cell dpn']">
              <div v-html="$util.showImageResize(row.repcontent)"></div>
            </div>
            <!-- // 레이어 팝업 -->
          </td>
          <td v-else>
            {{ row.isreply }}
          </td>
        </tr>
        </tbody>
        <tbody v-else>
        <td :colspan="searchData.isAdmin ? 19 : 11">조회 결과가 존재하지 않습니다.</td>
        </tbody>
      </table>
      <div class="bottom-group">
        <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
      </div>
    </div>
  </div>
  <!-- /컨텐츠 영역 -->

</template>

<script>
import OneOneDetail from "@views.admin/cs/oneone/OneOneDetail";
import AdminCommonNavigator from "@views.admin/common/CommonNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CommonSms from "@views.admin/common/popup/CommonSms";
import CommonMail from "@views.admin/common/popup/CommonMail";
import AdminMemberInfo from "@views.admin/member/info/AdminMemberInfo";
import OrderDetail from "@views.admin/order/info/OrderDetail";

export default {
  name: "admin.cs.oneone",
  components: {
    OrderDetail,
    AdminMemberInfo,
    CommonMail,
    CommonSms,
    CommonPageNavigator,
    CommonDatePicker,
    AdminCommonNavigator,
    OneOneDetail
  },
  data() {
    return {
      isOrderDetailShow: false,   // 주문상세 노출여부
      activeOrderCode: '',       // 주문번호

      isCommonSmsShow: false,   // Sms 보내기 팝업
      smsUserNo: '',            // Sms 유저 no 정보

      isCommonMailShow: false,  // Mail 보내기 팝업
      mailUserNo: '',           // Mail 유저 no 정보

      isDetailShow: false,      // 상세 팝업
      isDetailIdx: '',          // 상세 idx 정보

      inquiryCodeList: [],    // 분류 코드
      isHovering: [],         // 호버된 배열
      listData: [],           // 리스트
      partnersList: [],
      searchData: {
        isAdmin: false,
        sdate: 'start',       // 조회기간 (문의일자, 답변일자)
        startDate: '',        // 시작 연도
        endDate: '',          // 끝 연도
        skey: '',             // 검색 조건 (제목 작성자 내용)
        sword: '',            // 검색 단어
        period: '3',          // 날짜 검색 옵션 (어제,오늘,일주일...)
        inquirytype: '',      // 구분
        isreply: '',          // 대기 상태
        ispbgoods: '',
        dealerno: '',
        nowdealerno: '',
      },
      pagingData: {
        pageCount: 20,        // 페이징 옵션(최대수)
        page: 1,              // 현재 페이지
        listCount: 0          // 전체 수
      },
      sortData: {
        ispbgoods: 'ispbgoods_desc',
        dealername: 'dealername_desc',
        inquirytype: 'inquirytype_desc',
        userid: 'userid_desc',
        username: 'username_desc',
        mobile: 'mobile_desc',
        email: 'email_desc',
        ordno: 'ordno_desc',
        invoiceno: 'invoiceno_desc',
        goodsname: 'goodsname_desc',
        content: 'content_desc',
        regdate: 'regdate_desc',
        repname: 'repname_desc',
        repregdate: 'repregdate_desc',
        ispass: 'ispass_desc',
        isreply: 'isreply_desc',
      },
      stateData: {
        comp: '',
        stay: '',
        total: ''
      },
      isChecked: false,
      checkedList: [],
      isRead: false,
      isWrite: false,

      isMemberDetailShow: false,        // 유저 상세 팝업
      memberDetailUserNo: '',           // 유저 번호
      isLink : false, //대시보드에서 링크를 타고왔는지 체크
    }
  },
  methods: {
    ///////////////////////// 내부 사용 메서드 /////////////////////////
    onInit() {
      if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
        this.isLink = true;
      }

      this.onCommonCode();
    },

    // 리스트 조회
    onSearch(page) {
      let param = this.searchData;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post("/admin/cs/oneone/search", param)
          .then(result => {
            let data = result.data;
            this.listData = data.list;
            this.pagingData.listCount = data.statelist.total;
            this.stateData = data.statelist;
            this.partnersList = data.partnerslist;

            let size = this.listData.length;
            for (let i = 0; i < size; i++) {
              this.isHovering.push(false);
            }
            this.isChecked = false;
            this.checkedList = [];
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    onCommonCode() {
      let cmclassArr = ['INQUIRYTYPE'];
      this.$http.post('/common/code/map/list', {cmclass: cmclassArr, isloading: false})
          .then(result => {
            let data = result.data;
            this.inquiryCodeList = data.inquirytype;

            if(this.isLink){
              let linkParam = this.$route.params;
              this.searchData.isreply = linkParam.isreply;
              this.searchData.period = linkParam.period;
              this.searchData.startDate = linkParam.startdate;
              this.searchData.endDate = linkParam.enddate;
            }

            this.onSearch();
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    mouseOver(index) {
      this.$set(this.isHovering, index, true);
    },

    mouseOut(index) {
      this.$set(this.isHovering, index, false);
    },

    // 초기화
    onSearchDataRest() {
      this.searchData.sdate = 'start';
      this.searchData.isreply = '';
      this.searchData.skey = '';
      this.searchData.sword = '';
      this.searchData.inquirytype = '';
      this.searchData.period = '3';
      this.searchData.ispbgoods = '';
      this.searchData.dealerno = '';
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
    },
    //목록 전체체크
    checkAllList: function(value) {
        this.checkedList = [];
        if (value) {
            this.listData.forEach(item => {
                if(item.ispass !== 'T' && item.ispbgoods !== '직매입' && item.isreply !== '완료' && !this.$util.isNull(item.goodsno)) {
                  this.checkedList.push(item);
                }
            });
        }
    },
    // 목록 개별체크
    checkList: function() {
        if (this.list.length > this.checkedList.length){
            this.isallchk = false;
        } else {
            this.isallchk = true;
        }
    },

    // 테이블 소트
    sortToggle(key) {
      let arr = key.split("_");
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData = this.$options.data().sortData;

      this.sortData[sortKey] = sortName;
      this.searchData.psort = sortName;

      this.onSearch();
    },

    sendPartners() {
      if(this.checkedList.length === 0) {
        alert("선택된 항목이 없습니다.");
        return ;
      }

      let param = {
        list : this.checkedList
      }

      if(confirm("선택 한 게시물을 파트너사에 전송 하시겠습니까?")) {
        this.$http.post("/admin/cs/oneone/send", param)
        .then(result => {
          if(result.statusCode === 200) {
            alert("전송이 완료되었습니다.");
            this.onSearch();
          }
        }).catch(error => {
          this.$util.debug(error);
        });
      }
    },
    /////////////////////////////////////////////////////////////////

    //////////////////////// 외부, 콜백 메서드 /////////////////////////
    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.endDate = val;
    },

    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startDate = val;
    },

    // 페이징 콜백 함수
    setPagingData(param) {
      this.pagingData = param;
      this.onSearch();
    },
    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    // 상세 팝업 열기
    goDetail(idx) {
      // this.$refs.refDetail.onOpen(idx);
      this.isDetailShow = true;
      this.isDetailIdx = idx;
    },

    // 상세 팝업 닫기
    goCloseDetailPopup(isreload) {
      this.isDetailShow = false;
      if (isreload) {
        this.onSearch();
      }
    },

    // 고객정보상세 팝업 열기
    goUserDtl(userNo) {
      this.isMemberDetailShow = true;
      this.memberDetailUserNo = userNo;
    },

    closeMemberInfoPopup() {
      this.isMemberDetailShow = false;
      this.memberDetailUserNo = '';
    },

    goOrderDetail(ordercode) {
      this.isOrderDetailShow = true;
      this.activeOrderCode = ordercode;
    },
    // 주문상세 팝업 닫기
    closeOrderDetailPopup() {
      this.isOrderDetailShow = false;
    },

    goSms(userNo) {
      this.isCommonSmsShow = true;
      this.smsUserNo = userNo;
    },

    goCloseSms() {
      this.isCommonSmsShow = false;
    },

    goMail(userNo) {
      this.isCommonMailShow = true;
      this.mailUserNo = userNo;
    },

    goCloseMail() {
      this.isCommonMailShow = false;
    },

    onGoodsFront(goodsCode) {
      window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + goodsCode, "_blank");
    },
    /////////////////////////////////////////////////////////////////

  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
      if (this.isRead) {
        this.searchData.isAdmin = this.$util.isAuthorized('ADMIN_USER');
        if(!this.searchData.isAdmin) {
          this.searchData.ispbgoods = 'F';
          this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].PARTNER_USER);
          this.searchData.nowdealerno = this.user.no;
        }
        this.$util.componentSetSearchParam(this);
        this.onInit();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },

  watch: {
    'searchData.period': function (val) {
      let dayType = ["-1", "0", "7"];
      let monthType = ["1", "3", "6"];
      let valueToInt = parseInt(val);

      if (dayType.includes(val)) {
        if (valueToInt >= 0) {
          this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), (valueToInt * -1), '-');
          this.searchData.endDate = this.$util.getDate('-');
        } else {
          this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
          this.searchData.endDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
        }
      } else if (monthType.includes(val)) {
        this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), (valueToInt * -1), '-');
        this.searchData.endDate = this.$util.getDate('-');
      }
    }
  }
}
</script>
