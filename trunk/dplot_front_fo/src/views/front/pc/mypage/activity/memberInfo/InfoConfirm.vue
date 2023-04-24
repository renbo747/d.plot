<template>
  <main class="dp-member-info-pc">
    <section class="container">
      <div class="member-info__header">
        <div class="member-info__title">
          <p class="mb-0">회원정보 확인</p>
        </div>
      </div>
      <div class="member-info__body">
        <div class="member-info__txt">
          <p class="mb-0">
            개인 정보를 안전하게 보호하기 위해 비밀번호를 다시 한 번 입력해
            주세요.
          </p>
        </div>
        <div class="member-info-area">
          <ul class="list-style-none">
            <li class="d-flex"><span>아이디</span><span>{{memberInfoData.userid}}</span></li>
            <li>
              <form>
                <div>
                  <base-input-horizon
                    label="비밀번호"
                    type="password"
                    :is-label="true"
                    v-model="password"
                    placeholder=""
                    :is-require="false"
                    :invalid="false"
                    class="align-items-center"
                     @isEnter="getLogin"
                  >
                  </base-input-horizon>
                </div>
              </form>
            </li>
          </ul>
        </div>
      </div>
      <div class="member-info__footer">
        <div class="btn-group d-flex">
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            squared
            @click="getLogin"
          >
            <span>확인</span>
          </b-button>
        </div>
      </div>
    </section>
  </main>
</template>

<script src="@views.mobile/mypage/activity/memberInfo/InfoConfirm.js"></script>
