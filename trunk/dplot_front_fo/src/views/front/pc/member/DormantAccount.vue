<template>
  <main class="dormant-account-pc member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          휴면계정 안내
        </h1>
      </div>
      <div class="dp-panel pt-0">
        <p class="dp-title02 text-black font-weight-400">
          고객님의 계정은 장기간 로그인을 하지 않아 휴면 처리되었습니다.
        </p>
        <p class="dp-title02 text-black font-weight-400">
          고객님의 개인정보보호를 위해
          <span class="text-primary">1년간 로그인 하지 않으실 경우</span>
          고객님의 계정을 휴면상태로 전환합니다.
        </p>
        <p class="dp-title02 text-black font-weight-400 mb-0">
          서비스를 다시 이용하고 싶으시면 하단의
          <span class="text-primary">휴면 해제하기 버튼</span>을 눌러 계정을
          복구해주세요.
        </p>
      </div>

      <div class="member__footer-group">
        <b-button variant="gray-800 w-100 btn-h48 dp-p font-weight-400" squared @click="signup()"
          >휴면계정 해제</b-button
        >
      </div>
    </div>
  </main>
</template>
<script src="@views.mobile/member/DormantAccount.js">
</script>