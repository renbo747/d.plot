<template>
  <b-modal
    id="password-reset"
    modal-class="dp-modal page-layer password-find"
    scrollable
    :hide-footer="true"
  >
    <!-- Password-find Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">비밀번호 재설정</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Password-find Modal Body -->
    <div class="page-layer__body">
      <div class="dp-panel pt-0">
        <p class="password-find__description">
          비밀번호가 기억나지 않으시면 다음과 같은 방법으로 비밀번호를 재설정할
          수 있습니다.
        </p>
        <p class="password-find__title">본인인증으로 찾기</p>
        <b-button
          class="dp-btn text-white"
          variant="gray-800"
          squared
          @click="kmcCertReq()"
        >
          <span>본인인증하기</span>
        </b-button>
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="dp-panel">
        <p class="password-find__title mb-3">회원 정보 확인</p>
        <div class="form-group">
          <base-input
            label="아이디"
            placeholder="아이디를 입력해주세요"
            :is-label="true"
            v-model="userid"
            :invalid="false"
            :disabled="isSended"
          />
        </div>
        <div class="form-group">
          <base-input
            label="이름"
            placeholder="이름을 입력해주세요"
            :is-label="true"
            v-model="name"
            :invalid="false"
            :disabled="isSended"
          />
        </div>
        <div class="password-find__email-phone">
          <p class="password-find__label">이메일 또는 휴대폰번호</p>
          <div class="email-phone-find">
            <base-select
              class="base-select"
              placeholder="선택해주세요."
              :isPlaceholder="false"
              v-model="selectType"
              :options="selectOptions"
              @input="changeType"
              :disabled="isSended"
            />
            <base-input
              class="base-input"
              v-model="emailhp"
              :placeholder="
                selectType == 'MOBILE'
                  ? '- 없이 입력해주세요.'
                  : '이메일을 입력해주세요.'
              "
              :disabled="isSended"
            >
            </base-input>
          </div>
        </div>
        <b-button
          class="dp-btn auth-btn"
          variant="outline-gray-400"
          squared
          @click="sendAuthNum()"
        >
          <span class="dp-btn__gray-800">{{
            isSended ? "인증번호 재전송" : "인증번호 보내기"
          }}</span>
        </b-button>
        <div v-if="isSended">
          <p class="password-find__label password-find__p">
            회원정보에 등록된 이메일 / 휴대폰번호로 발송된 인증번호를
            입력해주세요.
          </p>
          <base-input
            :type="'number'"
            class="auth-number"
            placeholder="인증번호 입력"
            :autocomplete="'one-time-code'"
            v-model="authnum"
            :data-timer="hhmm"
          />
          <!-- <b-button
            class="dp-btn auth-number-button"
            variant="outline-gray-400"
            squared
            >
            <span class="dp-btn__gray-800">확인</span>
            </b-button> -->
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            squared
            @click="confirmAuthNum()"
          >
            <span>확인</span>
          </b-button>
        </div>
        <KmcCertReq ref="kmcCertReq"></KmcCertReq>
      </div>
    </div>
  </b-modal>
</template>
<script src="./PasswordReset.js">
</script>
