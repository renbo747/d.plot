<template>
  <main class="dormant-account">
    <div class="container">
      <div class="dp-panel">
        <p class="dp-p-sm">
          고객님의 계정은 장기간 로그인을 하지 않아<br />
          휴면 처리되었습니다.
        </p>
        <p class="dp-p-sm">
          고객님의 개인정보보호를 위해
          <span class="text-primary"
            >1년간 로그인 하지 않으실<br />
            경우</span
          >
          고객님의 계정을 휴면상태로 전환합니다.
        </p>
        <p class="dp-p-sm">
          서비스를 다시 이용하고 싶으시면 하단의
          <span class="text-primary"
            >휴면 해제하기<br />
            버튼</span
          >을 눌러 계정을 복구해주세요.
        </p>
      </div>

      <div class="member__footer-group">
        <b-button variant="gray-800 w-100" squared @click="signup()">휴면 해제하기</b-button>
      </div>
    </div>
  </main>
</template>

<script src="@views.mobile/member/DormantAccount.js">
</script>