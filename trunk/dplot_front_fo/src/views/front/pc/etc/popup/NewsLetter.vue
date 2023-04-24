<template>
  <div>
    <b-modal
      id="newsLetterModal"
      modal-class="dp-modal pop-over"
      hide-footer
      centered
    >
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">구독신청</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@/assets/common/icon/icon-close-modal-30px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <div class="news-letter-body">
        <div class="dp-subscribe">
          <header class="dp-subscribe-header">
            <h1 class="dp-subscribe-title mb-0">
              새로운 매거진 콘텐츠와 신제품,<br />
              이벤트 정보를 빠르고 편하게 받아보세요.
            </h1>
          </header>
          <section class="dp-panel dp-pt-30 pb-0">
            <div class="dp-subscribe__email">
              <form>
                <div class="form-group">
                  <p class="dp-subscribe__email__label">이메일주소</p>
                  <base-input
                    placeholder="이메일을 입력해주세요"
                   v-model.trim="email"
                  />
                </div>
                <div class="dp-subscribe__checkbox d-flex">
                  <base-checkbox
                    class="mr-1"
                    label="[필수] 개인정보 수집 및 이용 동의"
                    v-model="isAgree"
                    id="newslett"
                    name="newslett"
                  />
                  <i
                    class="dp-icon sm02 icon-question"
                    @click="showModal()"
                  ></i>
                </div>
              </form>
            </div>
          </section>
          <div class="dp-subscribe__button dp-panel pb-0">
            <div class="subscribe__button__wrap">
              <b-button class="dp-btn text-white" variant="gray-800" squared @click="subscribe()">
                <span class="subscribe__button__text">구독하기</span>
              </b-button>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
    <!-- // 문의 등록 Modal -->
    <component
      :key="skey + 'm'"
      :is="modalInfo.comp"
      :id="modalInfo.id"
      :param="modalInfo.param"
      :fnConfirm="modalInfo.fnConfirm"
      :fnCancel="modalInfo.fnCancel"
    />
  </div>
</template>

<script>
import NewsLetterPrivacy from '@views.pc/etc/popup/NewsLetterPrivacy.vue';
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "구독신청",
      searchIcon: false,
      cartIcon: false,
      homeIcon: false,
    });
  },
  data() {
    return {
      email: "",
      isAgree:false,
      modalInfo: {
        comp: null,
        param: null,
        fnConfirm: () => {},
        fnCancel: () => {},
      },
      memberEmail:{},
      skey:Date.now()
    };
  },
  methods: {
    /*************************
     * 구독하기 개인정보 수집 안내
     *************************/
    showModal(type, idx) {
      this.skey = Date.now();
      this.modalInfo.comp = NewsLetterPrivacy;
      this.$nextTick(function () {
        this.$bvModal.show("newsLetterPrivacyModal");
      });
    },
    /****************************
     * 구독하기
     ***************************/
    subscribe(){
        if (this.$util.isNull(this.email)) {
            this.$eventBus.$emit('alert', '알림', "이메일을 입력해주세요.");
            return false;
        }
        if (!this.$util.isEmail(this.email)) {
            this.$eventBus.$emit('alert', '알림', "이메일 형식이 올바르지 않습니다.");
            return false;
        }
        if (!this.isAgree) {
            this.$eventBus.$emit('alert', '알림', "개인정보 수집 및 이용에 동의해주세요.");
            return false;
        }
        this.$http.post('/etc/subscribe', {'email':this.email}).then(result => {
            if (result.statusCode == 200) {
                this.$util.debug(result);
                let message = '뉴스레터 구독 신청이 완료되었습니다.'
                this.$eventBus.$emit("alert", "알림", message, () => {
                    this.$bvModal.hide('newsLetterModal');
                });
            }
        })
    }
  },
   mounted(){
        this.$http.post('/member/memberEmailPhone',{}).then(result => {
            if (result.statusCode == 200) {
                this.memberEmail = result.data.memberemail;
                if (!this.$util.isNull(this.memberEmail)) {
                    this.email = this.memberEmail.email;
                }
            }
        })
    }
};
</script>

<style>
</style>