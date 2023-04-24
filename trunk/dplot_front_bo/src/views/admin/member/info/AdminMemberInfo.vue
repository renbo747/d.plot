<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1600px;">
      <div class="pop-header">
        <h2>회원정보 상세</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="gray-box mg0 clearfix">
          <div class="fl">[{{ info.dadamembertypename }}] [{{  info.memlvtypename }}] {{info.userid}} {{ info.name }}</div>
          <div class="fr txt-gray">
            <span>최근접속일시 : {{ info.lastlogindate }}</span>
            <span class="left-bar">가입일 : {{ info.regdate }}</span>
          </div>
        </div>
        <div class="tab-group mt20">
          <ul><!-- 활성화탭 li에 class="active" 추가 -->
            <li :class="{ active: item.isActive }" v-for="item in tabs" :key="item.tab" @click="toggleTab(item)"><a href="javascript:void(0)">{{ item.name }}</a></li>
          </ul>
        </div>
        <AdminMemberDetail v-if="tabArea === 'BASIC' && !isMemberEdit" v-bind:activeUserNo="activeUserNo" v-on:goMemberEdit="goMemberEdit" v-on:goDirectPage="goDirectPage"></AdminMemberDetail>
        <AdminMemberEdit   v-if="isMemberEdit"                         v-bind:activeUserNo="activeUserNo" v-on:goMemberEdit="goMemberEdit"> </AdminMemberEdit>
        <AdminMemberMemoList v-if="tabArea === 'MEMO'" v-bind:activeUserNo="activeUserNo"></AdminMemberMemoList>
        <AdminMemberQuestion v-if="tabArea === 'QUESTION'" v-bind:activeUserNo="activeUserNo"></AdminMemberQuestion>
        <AdminMemberCMoney v-if="tabArea === 'CMONEY'" v-bind:activeUserNo="activeUserNo"></AdminMemberCMoney>
        <AdminMemberOrder v-if="tabArea === 'ORDER'" v-bind:activeUserNo="activeUserNo"></AdminMemberOrder>
        <AdminMemberEPoint v-if="tabArea === 'EPOINT'" v-bind:activeUserNo="activeUserNo"></AdminMemberEPoint>
        <AdminMemberEmpCMoney v-if="tabArea === 'EMPCMONEY'" v-bind:activeUserNo="activeUserNo"></AdminMemberEmpCMoney>
        <AdminMemberCoupon v-if="tabArea === 'COUPON'" v-bind:activeUserNo="activeUserNo"></AdminMemberCoupon>
        <AdminMemberCart v-if="tabArea === 'CART'" v-bind:activeUserNo="activeUserNo"></AdminMemberCart>
        <AdminMemberWish v-if="tabArea === 'WISH'" v-bind:activeUserNo="activeUserNo"></AdminMemberWish>
        <AdminMemberReview v-if="tabArea === 'REVIEW'" v-bind:activeUserNo="activeUserNo"></AdminMemberReview>


        <div class="btn-group" v-if="!isMemberEdit">
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AdminMemberDetail from "@views.admin/member/info/AdminMemberDetail.vue";
import AdminMemberEdit from "@views.admin/member/info/AdminMemberEdit.vue";
import AdminMemberMemoList from "@views.admin/member/info/AdminMemberMemoList.vue";
import AdminMemberQuestion from "@views.admin/member/info/AdminMemberQuestion.vue";
import AdminMemberCMoney from "@views.admin/member/info/AdminMemberCMoney.vue";
import AdminMemberEPoint from "@views.admin/member/info/AdminMemberEPoint.vue";
import AdminMemberEmpCMoney from "@views.admin/member/info/AdminMemberEmpCMoney.vue";
import AdminMemberCoupon from "@views.admin/member/info/AdminMemberCoupon.vue";
import AdminMemberCart from "@views.admin/member/info/AdminMemberCart.vue";
import AdminMemberWish from "@views.admin/member/info/AdminMemberWish.vue";
import AdminMemberReview from "@views.admin/member/info/AdminMemberReview.vue";
import AdminMemberOrder from "@views.admin/member/info/AdminMemberOrder.vue";


export default {
  name: "AdminMemberInfo",
  components:
      {
        AdminMemberDetail, AdminMemberEdit, AdminMemberMemoList, AdminMemberQuestion, AdminMemberCMoney, AdminMemberEPoint,
        AdminMemberEmpCMoney, AdminMemberCoupon, AdminMemberCart, AdminMemberWish, AdminMemberReview, AdminMemberOrder
      },
  props: ['activeUserNo'],
  data() {
    return {
      tabs: [
        {name: '기본정보', tab: 'BASIC', isActive: true},
        {name: '주문내역', tab: 'ORDER', isActive: false},
        {name: '적립금', tab: 'CMONEY', isActive: false},
        {name: 'D포인트', tab: 'EPOINT', isActive: false},
        {name: '임직원적립금', tab: 'EMPCMONEY', isActive: false},
        {name: '쿠폰발급', tab: 'COUPON', isActive: false},
        {name: '장바구니', tab: 'CART', isActive: false},
        {name: '찜한상품', tab: 'WISH', isActive: false},
        {name: '1:1문의', tab: 'QUESTION', isActive: false},
        {name: '리뷰', tab: 'REVIEW', isActive: false},
        {name: '메모', tab: 'MEMO', isActive: false}
      ],
      info: {},
      tabArea: 'BASIC',
      isMemberEdit : false,
    }
  },
  mounted() {
    this.$http.post('/admin/member/info', {userno : this.activeUserNo, isloading : false}).then(result => {
      this.info = result.data.info;
      this.$util.debug(result);
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods : {
    onClose(){
      this.info = this.$options.data().info
      this.$emit('closeDetail');
    },
    toggleTab(obj){

      this.tabArea = obj.tab;
      this.isMemberEdit = false;

      this.tabs.forEach(function(row){
        row.isActive = obj.name === row.name;
      });
    },
    goMemberEdit(isEdit){
      this.isMemberEdit = isEdit;
    },
    goDirectPage(type){
      this.tabArea = type;
      this.isMemberEdit = false;

      this.tabs.forEach(function(row){
        row.isActive = type === row.tab;
      });
    }
  }
}
</script>

<style scoped />