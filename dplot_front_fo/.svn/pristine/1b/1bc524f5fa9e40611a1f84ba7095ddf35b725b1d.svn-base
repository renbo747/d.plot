<template>
  <!-- Kakao-signup Success Modal -->
  <b-modal
    id="kakao-signup-success"
    :modal-class="{
      'dp-modal pop-over kakao-signup__success-pc':
        $store.state.platform == 'MAC001',
      'dp-modal page-layer kakao-signup__success':
        $store.state.platform != 'MAC001',
    }"
    scrollable
    centered
    :hide-footer="true"
  >
    <!-- Kakao-signup Success Modal Header -->
    <template #modal-header="{ cancel }">
      <h5
        class="modal-title"
        :class="{ 'dp-title01': $store.state.platform == 'MAC001' }"
      >
        카카오로 가입
      </h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Kakao-signup Success Modal Body -->
    <!-- pc일경우 -->
    <template v-if="$store.state.platform == 'MAC001'">
      <div class="page-layer__body">
        <p
          class="
            kakao-signup__success__desc-pc
            mb-0
            dp-title02
            font-weight-400
            text-black
          "
        >
          카카오로 회원가입이 완료되었습니다.
        </p>
        <!-- todo: (개발) 0421 감싸는 태그 수정 -->
        <div class="name-box">
          <div class="now-name-pc">
            <p class="mb-0 dp-p">
              현재 설정된 이름 :
              <span class="text-black">{{ param.name }}</span>
            </p>
          </div>
        </div>
        <!-- // (개발) 0421 감싸는 태그 수정 -->
        <div class="name-enter-box">
          <form>
            <base-input
              label="원활한 서비스 이용을 위해 회원님의 실명을 입력해주세요."
              placeholder="이름을 입력해주세요"
              :is-label="true"
              v-model="nameData"
            />
            <div class="change-name-btn">
              <b-button
                class="dp-btn text-white btn-h48"
                variant="gray-800"
                squared
                @click="saveName()"
              >
                <span class="dp-p font-weight-400">이름 변경하기</span>
              </b-button>
            </div>
          </form>
        </div>

        <!-- todo: (개발) 0421 디자인에 맞춰서 수정 -->
        <ul class="ul-dot">
          <li>
            D.PLOT에서 사용하시는 이름만 변경되며, 카카오 계정에서 사용하시는
            닉네임은 변경되지 않습니다.
          </li>
        </ul>
        <!-- // (개발) 0421 디자인에 맞춰서 수정 -->
      </div>
    </template>
    <!-- 모바일,app일경우 -->
    <template v-else>
      <div class="page-layer__body">
        <p class="kakao-signup__success__desc">
          카카오로 회원가입이 <br />
          완료되었습니다.
        </p>
        <p class="now-name">
          현재 설정된 이름 : <b>{{ param.name }}</b>
        </p>
        <form>
          <base-input
            label="원활한 서비스 이용을 위해 회원님의 실명을
        입력해주세요."
            placeholder="이름을 입력해주세요"
            :is-label="true"
            v-model="nameData"
          />
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            squared
            @click="saveName()"
          >
            <span>이름 변경하기</span>
          </b-button>
        </form>
        <p class="kakao-signup__info">
          D.PLOT에서 사용하시는 이름만 변경되며, 카카오 계정에서 사용하시는
          닉네임은 변경되지 않습니다.
        </p>
      </div>
    </template>
  </b-modal>
  <!-- //Kakao-signup Success Modal -->
</template>

<script>
import SignUpComplete from "@views.mobile/member/popup/SignUpComplete.vue";
export default {
  props: {
    param: { type: Object },
  },
  data() {
    return {
      nameData: "",
    };
  },
  methods: {
    /**********************
     * 카카오 계정 이름 변경
     **********************/
    saveName() {
      this.$eventBus.$emit("confirm","카카오 계정 이름 변경","이름을 변경하시겠습니까?",
        () => {
          let param = {name: this.nameData,userno: this.param.userno};
          this.$http.post("/member/saveKakaoName", param).then((result) => {
            if (result.statusCode == 200) {
              this.$bvModal.hide("SignupSuccessModal");
              this.$eventBus.$emit("showModal",SignUpComplete,"SignupSuccessModal",
                  {
                    name: this.nameData,
                    mobile: this.param.mobile,
                    userno: this.param.userno,
                    isSns: 'T',
                  }
                );
            }
          });
        }
      );
    },
  },
};
</script>
