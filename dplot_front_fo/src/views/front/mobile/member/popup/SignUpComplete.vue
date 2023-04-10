<template>
  <b-modal
    id="SignupSuccessModal"
    modal-class="dp-modal page-layer signup-success"
    scrollable
    centered
    :hide-footer="true"
  >
    <!-- Signup Success Modal Header -->
    <template #modal-header="{  cancel}">
      <h5 class="modal-title">회원가입 완료</h5>
      <i class="modal-close" @click="cancel(changePage('member-login') )">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Signup Success Modal Body -->
    <div class="page-layer__body">
      <div class="dp-panel pt-0">
        <p class="signup-success__desc">
          D.PLOT에 오신 것을 환영합니다. <br />
          즐거운 쇼핑되세요!
        </p>
        <p class="signup-success__title">신규가입을 축하드립니다!</p>

        <p class="signup-success__p">
          <!-- {{list.cpnuseeddaystr}}까지 사용가능한
          <span class="coupon-name"> {{list.cpnname}}</span>이 <br />
          발급되었습니다. -->
          디플롯은 스토리가 있는 브랜드 셀렉트숍입니다.<br />
          디플롯에서 가치 있는 브랜드, 취향과 라이프스타일을 발견해보세요.
        </p>
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="dp-panel" v-if="!$util.isNull(rewardinfo)">
        <p class="signup-success__title">추천인 혜택을 받으셨습니다.</p>
        <p class="signup-success__p" v-if="rewardinfo.type == '쿠폰'">
          {{rewardinfo.enddate}}까지 사용가능한
          <span class="coupon-name">{{rewardinfo.paycontent}}</span>이 <br />
          발급되었습니다.
        </p>
        <p class="signup-success__p" v-if="rewardinfo.type == '포인트'">
          {{rewardinfo.enddate}}까지 사용가능한
          <span class="coupon-name">{{$util.maskComma(rewardinfo.paycontent)}}P</span><br />
          가 지급되었습니다.
        </p>
        <p class="signup-success__p" v-if="rewardinfo.type == '적립금'">
          적립금
          <span class="coupon-name">{{$util.maskComma(rewardinfo.paycontent)}}원</span><br />
          가 지급되었습니다.
        </p>
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="dp-panel">
        <p class="signup-success__title mb-3">
          가입하신 정보는 아래와 같습니다.
        </p>
        <div v-if="param.isSns == 'T'" class="signup-success__info">
          <div class="success-info">
            <p>이메일</p>
            <p class="success-info__email">{{memberinfo.email}}</p>
          </div>
          <div class="success-info">
            <p>이름</p>
            <p class="success-info__name">{{memberinfo.name}}</p>
          </div>
          <div class="success-info mb-3">
            <p>휴대폰번호</p>
            <p class="success-info__number">{{$util.maskTel(memberinfo.mobile)}}</p>
          </div>
          <form v-if="isreward">
            <div class="recommend-id__container">
              <p class="recommend-id__p">추천인 아이디 (선택)</p>
              <div class="recommend-id__form">
                <base-input
                  class="btn-mr10 base-input"
                  placeholder="아이디를 입력해주세요."
                  v-model="recommendid"
                />
                <b-button class="dp-btn" variant="outline-gray-800" squared @click="recommendSave()">
                  <span>확인</span>
                </b-button>
              </div>
            </div>
          </form>
        </div>
        <div v-else class="signup-success__info">
          <div class="success-info">
            <p>아이디</p>
            <p class="success-info__id">{{memberinfo.userid}}</p>
          </div>
          <div class="success-info" v-if="memberinfo.dadamembertype == 'DMT003' || memberinfo.dadamembertype == 'DMT004'">
            <p>소속회사</p>
            <p class="success-info__id">{{memberinfo.companytypenm}}</p>
          </div>
          <div class="success-info">
            <p>이메일</p>
            <p class="success-info__email">{{memberinfo.email}}</p>
          </div>
          <div class="success-info">
            <p>이름</p>
            <p class="success-info__name">{{memberinfo.name}}</p>
          </div>
          <div class="success-info">
            <p>휴대폰번호</p>
            <p class="success-info__number">{{$util.maskTel(memberinfo.mobile)}}</p>
          </div>
        </div>
        <div class="signup-success__buttons">
          <b-button
            class="dp-btn btn-mr10 to-main-btn"
            variant="outline-gray-800"
            squared
            @click="changePage('shop')"
          >
            <span>메인으로</span>
          </b-button>
          <b-button class="dp-btn text-white" variant="gray-800" squared @click="changePage('member-login')">
            <span>로그인</span>
          </b-button>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  props: {
    param: { type: Object }
  },
  data() {
    return {
      recommendid: "",
      isreward : false,
      memberinfo: {
        userno: "",            //회원번호
        name:"",               //이름
        mobile:"",             //핸드폰번호
        dadamembertype:"",     //회원유형
        userid:"",             //회원아이디
        email:"",              //이메일
      },
      couponList: [],
      rewardinfo: {
        type:"",               //리워드보상종류
        paycontent:"",         //보상내용
        enddate:""             //종료일
      }
    };
  },
  methods: {
    /**************************
     * 회원가입완료 정보 조회
     **************************/
    getSignupInfo() {
      this.$http.post("/member/signup/memberInfo", this.param).then((result) => {
        if (result.statusCode == 200) {
          this.$util.debug(result);
          this.memberinfo = result.data.membersinfo; //가입정보
          this.rewardinfo = result.data.rewardinfo; //추천인보상정보
          this.couponList = result.data.couponinfo; //가입쿠폰정보
          if (this.$util.isNull(result.data.reward)) {
            this.isreward = false;
          }else {
            this.isreward = true;
          }
        } 
      });
    },
    /*************************
     * 페이지 이동
     *************************/
    changePage(name) {
      this.$bvModal.hide('SignupSuccessModal');
      this.$storage.setSessionStorage('redirectPath', {name : 'mypage'});
      this.$router.push({name:name});
    },
    /*************************
     * 추천인 작성
     *************************/
    recommendSave(){
      if (this.$util.isNull(this.recommendid)) {
        this.$eventBus.$emit('alert', '알림', "추천인 아이디를 작성해주세요.");
        return;
      }
      const params = {
        name: this.param.name,
        mobile: this.param.mobile,
        recommendid: this.recommendid,
        userno : this.memberinfo.userno
      }
      
      this.$http.post("/member/recommend", params).then((result) => {
        if (result.statusCode == 200) {
          this.$eventBus.$emit('alert', '알림',"추천인 등록에 성공했습니다.",()=>{this.changePage('main')});
        }else {
          this.$eventBus.$emit('alert', '알림', result.message);
        }
      })
    }
  },
  mounted() {
    this.getSignupInfo();
    dataLayer.push({
        'event': 'sign_up'
    });
  },
};
</script>

<style></style>