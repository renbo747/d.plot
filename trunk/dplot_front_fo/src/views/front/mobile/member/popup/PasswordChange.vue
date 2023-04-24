<template>
  <b-modal
    id="password-change"
    modal-class="dp-modal page-layer password-find__new-password"
    scrollable
    :hide-footer="true"
    @cancel="close"
  >
    <!-- Password-find New-password Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">비밀번호 변경안내</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>
    <!-- Password-find New-password Modal Body -->
    <div class="page-layer__body">
      <template v-if="!isTempPassword">
        <p class="dp-p-sm">
          회원님의 소중한 정보를 안전하게 보호하고 개인정보<br />
          도용으로 인한 피해를 막기 위하여 비밀번호 변경을<br />
          안내드립니다.<br />
          <br />
          최근 90일 동안 비밀번호를 변경하지 않으셨습니다.<br />
          비밀번호를 변경해주세요.
        </p>
      </template>
      <template v-else>
        <p class="dp-p-sm">
          임시 비밀번호로 로그인하셨습니다.<br />
          비밀번호를 변경해주세요.
        </p>
      </template>
      <form>
        <div class="form-group">
          <base-input
            :type="'password'"
            label="새 비밀번호"
            placeholder="비밀번호를 입력해 주세요."
            :is-label="true"
            v-model="newpw"
          />
        </div>
        <div class="form-group new-password-repeat">
          <base-input
            :type="'password'"
            label="새 비밀번호 재입력"
            placeholder="비밀번호를 다시 입력해주세요."
            :is-label="true"
            v-model="confirmpw"
          />
        </div>
        <div class="form-group">
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            style="margin-bottom: 10px"
            squared
            @click="changeNewPw()"
          >
            <span>비밀번호 변경하기</span>
          </b-button>
        </div>
        <b-button
          v-if="!isTempPassword"
          class="dp-btn not-hover"
          variant="outline-gray-800 type02"
          squared
          @click="renewPwChangeDate()"
        >
          <span>90일 뒤 변경</span>
        </b-button>
      </form>
    </div>
  </b-modal>
</template>

<script>
export default {
  data() {
    return {
      newpw: "", // 새로운 비밀번호
      confirmpw: "", // 새로운 비밀번호 확인 (새로운 비밀번호랑 일치여부)
      userno:"",
      isTempPassword:""
    };
  },
  props: {
    param: { type: Object },
    fnConfirm: { type: Function },
  },
  mounted() {
    this.isTempPassword = this.param.isTempPassword;
    this.userno = this.param.param.memberinfo.userno;
  },
  methods: {
    changeNewPw() {
      if (this.newpw == "") {
        this.$eventBus.$emit("alert", "알림", "새 비밀번호를 입력해주세요.");
        return false;
      }
      if (this.confirmpw == "") {
        this.$eventBus.$emit(
          "alert",
          "알림",
          "비밀번호 확인을 입력해주세요."
        );
        return false;
      }

      if (this.newpw != this.confirmpw) {
        this.$eventBus.$emit(
          "alert",
          "알림",
          "비밀번호 확인이 일치하지 않습니다."
        );
        return false;
      }

      if (!this.$util.isPassword(this.newpw)) {
        this.$eventBus.$emit(
          "alert",
          "알림",
          "최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 또는 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합으로 입력해야합니다."
        );
        return false;
      }
      const param = {
        userno: this.userno,
        newpw: this.newpw,
        confirmpw: this.confirmpw,
      };

      this.$http.post("/member/pw/change", param).then((result) => {
        if (result.statusCode == 200) {
          this.$eventBus.$emit(
            "alert",
            "알림",
            "비밀번호 변경이 처리되었습니다.",
            () => {
              this.close();
            }
          );
        }
      });
    },
    // 90일 후 다시 알리기
    renewPwChangeDate() {
      const param = {
        userno: this.userno,
      };

      this.$http.post("/member/pw/changeDateRenew", param).then((result) => {
        if (result.statusCode == 200) {
          this.close();
        }
      });
    },
    close() {
      this.$front.redirectPage(this);
    },
  },
};
</script>