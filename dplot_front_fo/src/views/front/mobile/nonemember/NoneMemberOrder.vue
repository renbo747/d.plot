<template>
  <div class="none-member-order-view">
    <div class="container">
      <div class="order-view__header">
        <div class="icon-container">
          <i class="bag-icon"></i>
        </div>
        <div class="info-container">
          <p class="info-text">
            비회원으로 주문하신 내역을 확인하실 수 있습니다.
          </p>
        </div>
      </div>
      <div class="order-view__body">
        <form class="order-form">
          <base-input placeholder="주문자명을 입력해주세요" v-model="ordname"/>
          <base-input placeholder="주문번호를 입력해주세요" v-model="ordno"/>
          <b-button class="dp-btn text-white dp-mt-20" variant="gray-800" squared @click="searchOrder">
            <span>비회원 주문조회</span>
          </b-button>
        </form>
      </div>
      <div class="order-view__footer">
        <ul class="ul-dot">
          <li>주문결제 시 발송된 알림톡을 확인하시면 주문번호를 확인하실 수 
            있습니다.
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script src="@views.mobile/nonemember/NoneMemberOrder.js">
</script>