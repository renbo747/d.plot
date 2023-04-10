<template>
  <!-- 재입고 알림 신청 팝업 모달 -->
  <b-modal
    id="restockAlarmModal"
    modal-class="dp-modal pop-over restock-modal"
    centered
  >
    <!-- Popover Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">재입고 알림 신청</h5>
      <i class="modal-close" @click="cancel()">
        <img src="@/assets/common/icon/icon-close-modal-30px.svg" alt="닫기" />
      </i>
    </template>

    <!-- Popover Body -->
    <div class="popover-layer__body">
      <div class="restock-modal__area">
        <div class="product-info-area d-flex">
          <!-- 상품 정보 -->
          <product-thumbnail :thumbnailInfo = "{fullpath:goodsInfo.fullpath2}"/>
          <div class="productInfo-area">
            <p class="productInfo-ctg atten-new dp-caption mb-0">
              {{ goodsInfo.brandname }}
            </p>
            <div class="productInfo-option">
              <ul class="list-style-none">
                <li class="productInfo-name dp-p-sm">
                  {{ goodsInfo.goodsname }}
                </li>
                <li class="dp-caption" v-html="optionHtml">
                  <!-- <span>옵션 : 무선 램프</span>
                  <span class="dp-bar h10"></span>
                  <span>Mustard</span> -->
                </li>
              </ul>
            </div>
          </div>
        </div>
        <!-- 휴대폰 인증 (회원일 때)-->
        <template v-if="isMember">
          <div class="phone-number__input">
            <form>
              <div class="form-group">
                <base-input
                  label="휴대폰 번호"
                  :is-label="true"
                  :is-readonly="true"
                  v-model="mobile"
                />
              </div>
            </form>
          </div>
        </template>
        <!-- 수신 동의 -->
        <div class="reception-agree">
          <base-checkbox
            label="SMS 수신에 동의합니다. (필수)"
            v-model="isAgree"
          />
          <ul
            class="list-style-none reception-agree__description dp-p-sm ul-dot"
          >
            <li>재입고 시 카카오 알림톡(SMS)을 발송해드립니다.</li>
            <li>
              입고 알림을 받았더라도 구매 시점에 따라 품절이 발생할 수 있습니다.
            </li>
            <li>
              휴대폰번호 수정을 원하시면 <a @click="goMyIndoModify()">마이페이지 > 회원 정보수정</a> 메뉴에서
              휴대폰 번호 수정 후 신청 해주세요.
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- Popover Modal Footer -->
    <template #modal-footer="{cancel }">
      <div class="btn-group">
        <b-button
          class="dp-btn not-hover squared"
          variant="white"
          @click="cancel()"
          >취소</b-button
        >
        <b-button
          class="dp-btn text-white apply-btn"
          variant="gray-800 btn-h44"
          @click="alarmApply()"
          >알림신청</b-button
        >
      </div>
    </template>
  </b-modal>
  <!-- // 재입고 알림 신청 팝업 모달 -->
</template>

<script>
export default {
  props: {
    id:{type:String},
    param: { type: Object },
    fnConfirm: { type: Function },
    fnCancel: { type: Function },
  },
  data() {
    return {
      goodsInfo: {},
      optionHtml : '',
      isAgree: false,
      isMember: false,
      mobile: "",
      memberEmailPhone: {},
    };
  },
  mounted() {
    this.goodsInfo = this.param;
    let optionnames = this.goodsInfo.optionname.split(',');
    if(optionnames.length > 0) {
      if(this.goodsInfo.isaddgoods == 'T') {
        optionnames[0] = '추가상품:' + optionnames[0]
      } else {
        optionnames[0] = '옵션 : ' + optionnames[0]
      }
      for(let i = 0 ; i < optionnames.length ; i++) {
        optionnames[i] = '<span>' + optionnames[i] + '</span>';
      } 
      this.optionHtml = optionnames.join('<span class="dp-bar h10"></span>');  
    }

    this.$http.post("/member/memberEmailPhone", {}).then((result) => {
      if (result.statusCode == 200) {
        this.memberEmailPhone = result.data.memberemail;
        if (!this.$util.isNull(this.memberEmailPhone)) {
          this.mobile = this.memberEmailPhone.mobile;
          this.isMember = true;
        } else {
          this.isMember = false;
        }
      }
    });
  },
  methods: {
    cancel() {
      this.$bvModal.hide("optionSelectModal");
    },
    change() {
      this.fnConfirm();
      this.$bvModal.hide("optionSelectModal");
    },
    show() {
      this.product = this.param.productInfo;
      this.selectedItem = this.param.items;
      this.getOptionList(this.product.goodsno, "F");
      this.getAddItemsList();
      this.initSelectedOption();
    },
    goMyIndoModify(){
      this.$router.push('/mypage/info-confirm');
      this.$bvModal.hide("restockAlarmModal");
    },
    alarmApply() {
      if (this.$util.isNull(this.mobile)) {
        this.$eventBus.$emit("alert", "알림", "휴대폰 번호를 입력해주세요.");
        return;
      }
      if (!this.isAgree) {
        this.$eventBus.$emit(
          "alert",
          "알림",
          "마케팅 SMS 수신에 동의해주세요."
        );
        return;
      }
      let param = {
        mobile:this.mobile,
        goodsno:this.goodsInfo.goodsno,
        optioncode:this.goodsInfo.optioncode
      }
      this.$http.post("/goods/restock/apply", param).then((result) => {
        if (result.statusCode == 200) {
          this.$toast.clear();
          this.$toast.open("재입고알림 신청이 완료되었습니다.");
          this.$bvModal.hide("restockAlarmModal");
        }else {
          this.$eventBus.$emit("alert", "알림", result.message);
        }
      });
    },
  },
  watch: {
    isAgree(val) {
    },
  },
};
</script>

<style>
</style>