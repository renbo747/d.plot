<template>
  <main class="signup-complete member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          회원가입
        </h1>
        <p class="signup-success__desc mb-0 dp-title02 font-weight-400">
          D.PLOT에 오신 것을 환영합니다. 즐거운 쇼핑되세요!
        </p>
      </div>

      <div class="dp-panel complete-coupon">
        <p
          class="complete-coupon-title text-black dp-title02 font-weight-700 mb-0"
        >
          신규가입을 축하드립니다!
        </p>
        <p class="signup-success__p mb-0 dp-title02 font-weight-400">
          <!-- {{list.cpnuseeddaystr}}까지 사용가능한<span class="coupon-name">{{list.cpnname}}</span>이
          발급되었습니다. -->
          디플롯은 스토리가 있는 브랜드 셀렉트숍입니다.<br />
          디플롯에서 가치 있는 브랜드, 취향과 라이프스타일을 발견해보세요.
        </p>
      </div>
      <hr class="dp-hr" />
      <div class="dp-panel" v-if="!$util.isNull(rewardinfo)">
        <p
          class="complete-coupon-title text-black dp-title02 font-weight-700 mb-0"
        >
          추천인 혜택을 받으셨습니다.
        </p>
        <p
          v-if="rewardinfo.type == '쿠폰'"
          class="signup-success__p mb-0 dp-title02 font-weight-400"
        >
          {{rewardinfo.enddate}}까지 사용가능한<span class="coupon-name">{{rewardinfo.paycontent}}</span>이
          발급되었습니다.
        </p>
        <p
          v-if="rewardinfo.type == '적립금'"
          class="signup-success__p mb-0 dp-title02 font-weight-400"
        >
          적립금 {{$util.maskComma(rewardinfo.paycontent)}}원이 지급되었습니다.
        </p>
        <p
           v-if="rewardinfo.type == '포인트'"
          class="signup-success__p mb-0 dp-title02 font-weight-400"
        >
           {{rewardinfo.enddate}}까지 사용가능한 D포인트 {{$util.maskComma(rewardinfo.paycontent)}}P가 지급되었습니다.
        </p>
      </div>
      <hr class="dp-hr" />
      <div class="dp-panel">
        <p
          class="complete-information text-black dp-title02 font-weight-700 mb-0"
        >
          가입하신 정보는 아래와 같습니다.
        </p>
        <div v-if="isSns == 'T'" class="signup-success__info">
          <div class="success-info-pc">
            <p
              class="success__info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              이메일
            </p>
            <p
              class="success-info__email dp-p font-weight-400 mb-0 text-gray-700"
            >
              {{memberinfo.email}}
            </p>
          </div>
          <div class="success-info-pc">
            <p
              class="success__info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              이름
            </p>
            <p
              class="success-info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              {{memberinfo.name}}
            </p>
          </div>
          <div class="success-info-pc mb-0">
            <p
              class="success__info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              휴대폰번호
            </p>
            <p
              class="success-info__number dp-p font-weight-400 mb-0 text-gray-700"
            >
             {{$util.maskTel(memberinfo.mobile)}}
            </p>
          </div>
          <form v-if="isreward">
            <div class="success-info-recommend">
              <p class="info-recommend-id__p mb-10">추천인 아이디 (선택)</p>
              <div class="info-recommend-id__form d-flex">
                <base-input
                  class="info-recommend-id__form__check btn-h48 base-input"
                  placeholder="아이디를 입력해주세요."
                  v-model="recommendid"
                />
                <b-button
                  class="dp-btn btn-h48 recommend-id-btn"
                  variant="outline-gray-800"
                  squared
                  @click="recommendSave()"
                >
                  <span class="dp-p-sm font-weight-400 text-gray-800"
                    >확인</span
                  >
                </b-button>
              </div>
            </div>
          </form>
        </div>
        <div v-else class="signup-success__info">
          <div class="success-info-pc">
            <p
              class="success__info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              아이디
            </p>
            <p class="success-info__id dp-p font-weight-400 mb-0 text-gray-700">
              {{memberinfo.userid}}
            </p>
          </div>
          <div class="success-info-pc" v-if="memberinfo.dadamembertype == 'DMT003' || memberinfo.dadamembertype == 'DMT004'">
            <p
              class="success__info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              소속회사
            </p>
            <p class="success-info__id dp-p font-weight-400 mb-0 text-gray-700">
              {{memberinfo.companytypenm}}
            </p>
          </div>
          <div class="success-info-pc">
            <p
              class="success__info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              이메일
            </p>
            <p
              class="success-info__email dp-p font-weight-400 mb-0 text-gray-700"
            >
              {{memberinfo.email}}
            </p>
          </div>
          <div class="success-info-pc">
            <p
              class="success__info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              이름
            </p>
            <p
              class="success-info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              {{memberinfo.name}}
            </p>
          </div>
          <div class="success-info-pc mb-0">
            <p
              class="success__info__name dp-p font-weight-400 mb-0 text-gray-700"
            >
              휴대폰번호
            </p>
            <p
              class="success-info__number dp-p font-weight-400 mb-0 text-gray-700"
            >
              {{$util.maskTel(memberinfo.mobile)}}
            </p>
          </div>
        </div>
      </div>
      <div class="signup-success__buttons d-flex pt-40">
        <b-button
          class="dp-btn to-main-btn btn-to-main btn-h48"
          variant="outline-gray-400"
          squared
          @click="changePage('shop')"
        >
          <span class="dp-p text-gray-800">메인으로</span>
        </b-button>
        <b-button
          class="dp-btn text-white btn-to-login btn-h48"
          variant="gray-800"
          squared
          @click="changePage('member-login')"
        >
          <span class="dp-p">로그인</span>
        </b-button>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      recommendid: "",
      isreward : false,
      isSns:true,
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
      let param = {
        userno: this.$route.query.userno,
        isSns: this.$route.query.isSns
      }
      this.$http.post("/member/signup/memberInfo", param).then((result) => {
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
        userno : this.memberinfo.userno,
        dadamembertype: this.memberinfo.dadamembertype,
        memlvtype: this.memberinfo.memlvtype
      }
      
      this.$util.debug("추천인::" + JSON.stringify(params));
      this.$http.post("/member/recommend", params).then((result) => {
        if (result.statusCode == 200) {
          this.$eventBus.$emit('alert', '알림', "추천인 리워드가 지급되었습니다.", ()=>{this.$router.push({name:'shop'})});
        }
      })
    }
  },
  mounted() {
    if (this.$util.isEmpty(this.$route.query)) {
      this.$nextTick(() => {
        this.$eventBus.$emit("alert", "알림", "잘못된 접근입니다.", () => {
          this.$router.replace({ name: "member-login" });
        });
      });
    }
    this.getSignupInfo();
    dataLayer.push({
        'event': 'sign_up'
    });
  },
};
</script>
