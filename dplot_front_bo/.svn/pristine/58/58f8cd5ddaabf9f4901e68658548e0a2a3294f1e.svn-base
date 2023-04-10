<template>
  <div>
    <!-- 탑영역 -->
    <div class="top-menu">
      <div class="top clearfix">
        <div class="logo">
          <img src="../../assets/img/logo.png" alt="D.PLOT">
        </div>
        <ul class="user">
          <li class="name-info">
            <span class="name">홍길동</span>
            님, 안녕하세요.
          </li>
          <li class="today-info">
            <span class="today">today</span>
            <span class="date">2021.08.25</span>
          </li>
          <li class="btns">
            <a href="#" class="btn btn-outline-light">관리자메인</a>
            <a href="#" class="btn btn-outline-light">쇼핑몰메인</a>
            <a href="#" class="btn btn-dark">로그아웃</a>
          </li>
        </ul>
      </div>
      <!-- 탑메뉴 -->
      <ul class="nav clearfix">
        <li>
          <a href="#" class="active">
            <i class="icons icon-member"></i>
            <span>회원관리</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-goods"></i>
            <span>상품관리</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-order"></i>
            <span>주문관리</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-promotion"></i>
            <span>프로모션</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-manage"></i>
            <span>운영관리</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-cs"></i>
            <span>CS관리</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-partner"></i>
            <span>파트너사관리</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-calculate"></i>
            <span>정산관리</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-setting"></i>
            <span>환경설정</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="icons icon-stats"></i>
            <span>통계정보</span>
          </a>
        </li>
      </ul>
      <!-- /탑메뉴 -->
    </div>
    <!-- /탑영역 -->
    <!-- 레프트영역 -->
    <div class="left-menu">
      <div class="inner">
        <div class="title">
          <i class="icons icon-member-t"></i>
          <span>1depth Title</span>
        </div>
        <ul class="menu">
          <li>
            <a href="#">
              <span>2depth menu</span>
              <i class="icon-expand active"></i> <!-- active 클래스로 화살표 모양 변경 -->
            </a>
            <ul class="sub-menu"> <!-- dpn 클래스로 숨김여부 변경 -->
              <li>
                <a href="#" class="active">3depth</a> <!-- active 클래스로 메뉴컬러 변경 -->
              </li>
              <li>
                <a href="#">3depth</a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
    <!-- /레프트영역 -->
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
      <div class="page-info clearfix">
        <div class="title">기본정보</div>
        <ul class="breadcrumb">
          <li>
            <a href="#">홈</a>
          </li>
          <li>
            <a href="#">대메뉴</a>
          </li>
          <li>
            <a href="#" class="active">소메뉴</a>
          </li>
        </ul>
      </div>
      <div class="inner">
        <h1>공통 팝업</h1>
        <div class="boxing search-area">
          <dl>
            <dd>
              <h2>SMS 발송 공통 팝업</h2>
              <button class="btn blue ml3" @click="openCommonSmsPopup">SMS 발송</button>
              <CommonSms v-if="isShowCommonSms" @close="closeCommonSmsPopup"/>

              <h2>Mail 발송 공통 팝업</h2>
              <button class="btn blue ml3" @click="openCommonMailPopup">Mail 발송</button>
              <CommonMail v-if="isShowCommonMail" v-on:close-popup="closeCommonMailPopup"/>
            </dd>
          </dl>
        </div>

        <h1>공통 컴포넌트</h1>
        <div class="boxing search-area">
          <dl>
            <dd>
              <h2>DatePicker From To 컴포넌트</h2>
              <CommonDatePickerFromTo
                  :fromYYYYMMDD="timeInfo.startDate"
                  :fromHH="timeInfo.startHour"
                  :fromMM="timeInfo.startMi"
                  :toYYYYMMDD="timeInfo.endDate"
                  :toHH="timeInfo.endHour"
                  :toMM="timeInfo.endMi"
                  :useFrom="pickerFrom"
                  :useTo="pickerTo"
                  :text="pickerText"
                  @getDate="pickerChangeEvent"
              />
              <h3> retrun </h3>
              <p>{{ pickerReturnText }}</p>
              <button class="btn blue ml3" @click="PickerChange('full')">From TO</button>
              <button class="btn blue ml3" @click="PickerChange('from')">From</button>
              <button class="btn blue ml3" @click="PickerChange('to')">To</button>
              <input type="text" class="ml3" v-model="pickerText">
            </dd>
          </dl>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->

    <!-- 풋터 영역 -->
    <div class="footer m-leftmenu">
      Copyright ⓒ D.PLOT All rights reserved.
    </div>
    <!-- /풋터 영역 -->
  </div>
</template>

<script>
import CommonSms from "@views.admin/common/popup/CommonSms";
import CommonMail from "@views.admin/common/popup/CommonMail";
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";

export default {
  name: "AdminDevReference",
  components: {
    CommonDatePickerFromTo,
    CommonSms,
    CommonMail
  },
  data() {
    return {
      isShowCommonSms: false,
      isShowCommonMail: false,
      pickerFrom: true,
      pickerTo: true,
      pickerText: '텍스트',
      pickerReturnText: {},
      timeInfo: {
        startDate: '',
        startHour: '',
        startMi: '',
        endDate: '',
        endHour: '',
        endMi: '',
      }
    }
  },
  mounted() {
    // 시간 셋팅
    this.timeInfo.startDate = this.$util.getDate("-");
    this.timeInfo.startHour = '10';
    this.timeInfo.startMi = '10';
    this.timeInfo.endDate = this.$util.getDate("-");
    this.timeInfo.endHour = '20';
    this.timeInfo.endMi = '20';
  },
  methods: {
    // Sms 팝업 열기
    openCommonSmsPopup() {
      this.isShowCommonSms = true;
    },
    // Sms 팝업 닫기
    closeCommonSmsPopup() {
      this.isShowCommonSms = false;
    },
    // Mail 팝업 열기
    openCommonMailPopup() {
      this.isShowCommonMail = true;
    },
    // Mail 팝업 닫기
    closeCommonMailPopup() {
      this.isShowCommonMail = false;
    },
    // CommonDatePickerFromTo 클릭 이벤트
    PickerChange(type) {
      if ('full' === type) {
        this.pickerFrom = true;
        this.pickerTo = true;
      } else if ('from' === type) {
        this.pickerFrom = true;
        this.pickerTo = false;
      } else {
        this.pickerFrom = false;
        this.pickerTo = true;
      }
    },
    // CommonDatePickerFromTo 콜백 이벤트
    pickerChangeEvent(data) {
      this.pickerReturnText = data;

      if (this.pickerFrom) {
        this.timeInfo.startDate = data.fromYYYYMMDD;
        this.timeInfo.startHour = data.fromHH;
        this.timeInfo.startMi = data.fromMM;
        this.timeInfo.expsttime = data.fromDate12;
      }

      if (this.pickerTo) {
        this.timeInfo.endDate = data.toYYYYMMDD;
        this.timeInfo.endHour = data.toHH;
        this.timeInfo.endMi = data.toMM;
        this.timeInfo.expedtime = data.toDate12;
      }
    },
  }
}
</script>
