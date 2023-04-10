<template>
  <div class="dp-shipping-list">
    <div class="shipping-list-container">
      <div class="shipping-list__header d-flex justify-content-between">
        <div class="list__header__left d-flex">
          <div class="customer-name">
            {{ address.title }}
          </div>
          <span class="dp-badge" v-if="address.isdefault == 'T'"
            >기본 배송지</span
          >
        </div>
        <div class="review-edit">
          <span @click="modify">수정</span>
          <span class="dp-bar h10" v-if="address.isdefault === 'F'"></span>
          <span v-if="address.isdefault === 'F'" @click="remove">삭제</span>
        </div>
      </div>
      <div class="shipping-list__body">
        <p class="shipping-address mb-0">{{address.addrroad}} {{address.addrdetailroad}} </p>
        <p class="customer-phone mb-0">
          {{$util.maskTel(address.mobile)}}
        </p>
      </div>
      <div class="shipping-list__footer">
        <div class="select-btn-area d-flex justify-content-end">
          <b-button
            class="dp-btn btn-h32 not-hover select-btn"
            variant="outline-gray-800 type02"
            squared
            @click="selected"
          >
            <span>선택</span>
          </b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    address: {
      type: Object,
      default: () => ({
        idx:0,
        title: "",
        addrroad: "",
        addrdetailroad : "",
        mobile: "",
        isdefault: false,
      }),
    },
  },
  methods : {
    selected() {
      this.$emit('selected', this.address);
    },
    modify() {
      this.$emit('modify', this.address.idx);
    },
    remove() {
      this.$emit('remove', this.address.idx);
    }
  }
};
</script>

<style scoped></style>
