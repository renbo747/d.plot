<template>
  <!-- 회원정보 입력-->
  <main class="dp-signup member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__title font-weight-700 text-black">
          {{ isEmployee == "T" ? "임직원 회원가입" : "회원가입" }}
        </h1>
        <p class="dp-signup__description font-weight-400 dp-title02 mb-0">
          서비스 이용을 위한 회원정보를 입력해주세요.
        </p>
      </div>

      <!-- input박스 영역   -->
      <div class="signup__form">
        <form>
          <div class="form-group">
            <base-input
              label="이름"
              :is-label="true"
              :is-remove="false"
              v-model="signupData.name"
              placeholder="이름을 입력해주세요."
              :is-readonly="true"
            />
          </div>
          <div class="form-group">
            <base-input
              label="휴대폰 번호"
              :is-label="true"
              :is-remove="false"
              v-model="signupData.mobile"
              placeholder="휴대폰번호(-제외)"
              :is-readonly="true"
            />
          </div>
          <div class="form-group">
            <base-input
              label="아이디"
              :is-label="true"
              v-model.trim="signupData.userid"
              @blur="idCheck"
              @input="idCheck"
              ref="idfocus"
              placeholder="아이디를 입력해주세요."
              :class="{ 'is-error': idFail || idFail2 }"
              :valid="idSuccess"
              :invalid="!idSuccess"
              :max-length="12"
            >
              <span v-if="idFail"
                >영문 소문자 또는 영문 소문자와 숫자 조합 6~12자</span
              >
              <span v-if="idFail2"
                >동일한 아이디로 가입된 계정이 있습니다.</span
              >
            </base-input>
          </div>
          <div class="form-group">
            <base-input
              label="비밀번호"
              ref="pwfocus"
              type="password"
              :is-label="true"
              v-model.trim="signupData.userpw"
              @input="pwCheck"
              @blur="pwConfirm"
              placeholder="비밀번호를 입력해주세요."
              :class="{ 'is-error': pwFail }"
              :valid="pwSuccess || pwSuccess2"
              :invalid="pwFail"
            >
              <span v-if="pwFail"
                >영문/숫자/특수문자 2가지 이상 조합(최소 10자 이상)
                <span class="text-black">또는</span>
                <br />영문/숫자/특수문자 3가지 이상 조합(최소 8자 이상)
              </span>
            </base-input>
          </div>
          <div class="form-group">
            <base-input
              label="비밀번호 재입력"
              ref="pwckfocus"
              type="password"
              :is-label="true"
              v-model.trim="signupData.userpw2"
              @blur="pwConfirm"
              @input="pwConfirm"
              placeholder="비밀번호를 다시한번 입력해주세요."
              :class="{ 'is-error': pwConfirmFail }"
              :valid="pwConfirmSuccess"
              :invalid="pwConfirmFail"
            >
              <span v-if="pwConfirmFail"> 비밀번호가 일치하지 않습니다. </span>
            </base-input>
          </div>
          <div class="form-group" v-if="isEmployee === 'F'">
            <base-input
              label="이메일주소"
              ref="emailfocus"
              :is-label="true"
              v-model.trim="signupData.email"
              @input="emailCheck"
              placeholder="이메일 주소를 입력해주세요."
              :class="{ 'is-error': emailFail || emailFail2 || emailFail3 }"
              :valid="
                !(
                  emailFail ||
                  emailFail2 ||
                  emailFail3 ||
                  $util.isNull(signupData.email)
                )
              "
              :invalid="emailFail || emailFail2 || emailFail3"
            >
              <span v-if="emailFail"> 이메일 형식이 올바르지 않습니다. </span>
              <span v-if="emailFail2">
                동일한 이메일로 가입한 회원정보가 있습니다.
              </span>
              <span v-if="emailFail3">
                탈퇴 후 30일이 경과되지 않은 회원의 이메일주소입니다.
              </span>
            </base-input>
          </div>
          <!--임직원 이메일 인증 영역 -->
          <template v-if="isEmployee === 'T'">
            <div class="form-group">
              <div class="email__executive">
                <div class="signup-executive__email">
                  <p class="email__label dp-p-sm">이메일</p>
                  <div class="email-phone-find">
                    <base-input
                      class="email-input"
                      v-model.trim="email1"
                      ref="empemailfocus"
                      :readonly="isAuth == 'T'"
                      @input="emailCheck"
                      placeholder="이메일 입력"
                    />
                    <div class="at-sign">@</div>
                    <base-select
                      class="domain-select"
                      placeholder="도메인 선택"
                      :isDisabled="isAuth=='T'"
                      v-model="email2"
                      :options="emaildomain"
                      @input="emailCheck"
                    />
                  </div>
                  <span v-if="emailFail">
                    이메일 형식이 올바르지 않습니다.
                  </span>
                  <span v-if="emailFail2">
                    동일한 이메일로 가입한 회원정보가 있습니다.
                  </span>
                  <span v-if="emailFail3">
                    탈퇴 후 30일이 경과되지 않은 회원의 이메일주소입니다.
                  </span>
                </div>
              </div>
            </div>
            <b-button
              class="dp-btn send-btn"
              variant="outline-gray-400"
              squared
              v-if="isAuth == 'F'"
              @click.stop="sendAuthNum"
            >
              <span class="dp-btn__gray-800 text-gray-800">이메일로 인증번호 보내기</span>
            </b-button>
            <p class="email-number__label dp-p-sm" v-if="isSend == 'T' && isAuth == 'F'">
              이메일로 발송된 인증번호를 입력해주세요.
            </p>
            <!-- 없으면 퍼블 틀어짐..... --> 
            <p class="email-number__label dp-p-sm" v-else>
            </p>
            <base-input
              v-if="isSend == 'T' && isAuth == 'F'"
              ref="authnum"
              class="number-input pb-0"
              :valid="true"
              v-model.trim="authnum"
              placeholder="인증번호 입력"
              :data-timer="limitTime"
            />
            <!-- todo: 인증번호 완료 시 노출 필요 -->
            <div v-if="isAuth == 'T'" class="confirm-alert">
              <img src="~@/assets/common/icon/icon-check-green-20px.svg" alt="check icon"/>
              <p class="success-text dp-caption text-success">
                인증번호 확인이 완료되었습니다.
              </p>
            </div>
            <b-button v-if="isAuth == 'F' && isSend=='T'" class="dp-btn confirm-btn" variant="outline-gray-400" squared @click="confirmAuthNum">
              <span class="dp-btn__gray-800 text-gray-800">확인</span>
            </b-button>
          </template>
          <!--임직원 이메일 인증 영역 -->
          <!-- todo: (수정) 성별 추가 -->
          <div class="form-group">
            <label class="input-label">성별</label>
            <div class="d-flex align-items-center">
              <base-radio
                type="radio"
                label="남"
                id="gender01"
                name="sampleGender"
                v-model="signupData.gender"
                val="M"
                :disabled="true"
              />
              <base-radio
                type="radio"
                label="여"
                id="gender02"
                class="ml-2"
                name="sampleGender"
                v-model="signupData.gender"
                val="F"
               :disabled="true"
              />
            </div>
          </div>
          <div class="form-group">
            <base-input
              label="생년월일"
              :is-label="true"
              v-model="signupData.birthdate"
              laceholder="생년월일을 입력해주세요."
              :is-readonly="true"
            />
          </div>
          <!-- 일반회원 추천인 등록 영역-->
          <!-- TODO: 7/13  친구초대 이벤트 미설정시 노출 안되도록 수정 -->
          <div class="form-group">
            <base-input
              v-if="isEmployee == 'F' && !$util.isNull(isreward)"
              label="추천인 아이디 (선택)"
              :is-label="true"
              v-model.trim="signupData.recommendId"
              placeholder="추천인 아이디를 입력해주세요."
            />
          </div>
          <!-- 일반회원 추천인 등록 영역-->
          <!-- 이용약관 동의 부분  -->
          <div class="dp-signup__agree-service">
            <p class="agree-service__description dp-p">
              서비스 약관에 동의해주세요.
            </p>
            <ul class="agree-service__box list-style-none">
              <li>
                <base-checkbox
                  label="모두 동의합니다."
                  v-model="isAllAgree"
                  ref="ck"
                  id="agreeChk0"
                  name="Checkbox"
                  :is-checked="isAllAgree"
                  @change="allAgreeChange"
                />
              </li>
              <!-- 2022-07-25 선택은  마켓팅정보에서 -> 광고성으로 문구 수정, >는 기존 그대로 선택은 비노출 -->
              <li v-for="(list, index) in termList" :key="index">
                <base-checkbox
                  :label="list.label"
                  v-model="list.checked"
                  :id="list.id"
                  name="Checkbox"
                  :is-checked="list.checked"
                  @change="agreeChange(list)"
                />
                <i @click="showModal('termsModal', { termstype: list.termstype })" v-if="list.prefix == '[필수]'">
                  <img
                    class="more-icon"
                    src="@/assets/common/icon/icon-arrow-right-gray-16px.svg"
                    alt="더보기 아이콘"
                  />
                </i>
              </li>
            </ul>
            <!-- TODO : 7/14 평생회원 숨김처리 -->
            <!-- <div class="signup-last" v-if="isEmployee != 'T'">
              <base-checkbox
                class="signup-last-checkbox"
                label="[선택]평생회원으로 가입"
                v-model="islife"
                id="agreeChk9"
                name="Checkbox"
              />
              <i @click="showModal('LifetimeMemberModal')">
                <span class="dp-p-sm font-weight-400">혜택보기</span>
                <img
                  class="more-icon"
                  src="@/assets/common/icon/icon-more-black-16px.svg"
                  alt="더보기 아이콘"
                />
              </i>
            </div> -->
          </div>
          <div class="dp-signup__btn-box">
            <!--
            <b-button
              class="dp-btn text-white signup-btn"
              variant="gray-800"
              squared
              @click="handleSignUp"
            >
            -->
             <b-button
              class="dp-btn text-white signup-btn"
              variant="gray-800"
              squared
              @click="handleSignUp"
            >
              <span>{{
                isEmployee === "T" ? "임직원 회원가입하기" : "회원가입하기"
              }}</span>
            </b-button>
          </div>
        </form>
      </div>
    </div>
  </main>
  <!-- // 회원정보 입력-->
</template>

<script src="@views.mobile/member/SignUp.js"></script>
