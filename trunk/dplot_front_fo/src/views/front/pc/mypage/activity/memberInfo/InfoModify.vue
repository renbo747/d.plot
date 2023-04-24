<template>
  <main class="dp-member-modify-pc">
    <div class="container">
      <section class="info-modify__title__area">
        <div class="info-modify__title">회원정보 수정</div>
      </section>
      <section class="info-modify__area">
        <div class="info-modify__header">
          <div class="member-info__title d-flex">
            <span class="dp-title02 member-name"
              >{{ memberInfo.name }}님의 정보</span
            >
            <span
              class="dp-badge square lg primary dp-caption"
              v-if="memberInfo.dadamembertype === 'DMT002'"
              >평생회원</span
            >
          </div>
          <div
            class="member-info-area"
            v-if="memberInfo.joinchtype == 'UCT001'"
          >
            <ul class="list-style-none">
              <li
                v-if="
                  memberInfo.dadamembertype === 'DMT003' ||
                  memberInfo.dadamembertype === 'DMT004'
                "
              >
                <span>소속회사</span><span>{{ memberInfo.companytypenm }}</span>
              </li>
              <li>
                <span>아이디</span><span>{{ memberInfo.userid }}</span>
              </li>
            </ul>
          </div>
          <div class="member-info-area" v-else>
            <ul class="list-style-none dp-p-sm">
              <li>
                <span>아이디</span><span>{{ memberInfo.userid }}</span>
              </li>
              <li>
                <p class="mb-0">
                  {{ memberInfo.joinchtypenm }} 계정으로 가입하셨습니다.
                  <br />{{ memberInfo.joinchtypenm }} 비밀번호는
                  {{ memberInfo.joinchtypenm }}를 통해 변경해주세요.
                </p>
              </li>
            </ul>
          </div>
          <div class="info-modify__input-area">
            <div class="form-group" v-if="memberInfo.joinchtype === 'UCT003'">
              <base-input
                label="이름"
                :is-label="true"
                v-model="memberInfo.name"
                placeholder="홍길동"
              />
            </div>
            <!-- SNS 가입시 비밀변경노출 X-->
            <!--<div class="form-group" v-if="memberInfo.joinchtype === 'UCT001' ||memberInfo.joinchtype === 'UCT004'">-->
            <div class="form-group" v-if="memberInfo.joinchtype === 'UCT001'">
              <base-input
                label="비밀번호 변경"
                type="password"
                :is-label="true"
                v-model="memberInfo.userpw"
                @input="pwCheck"
                @blur="pwConfirm"
                placeholder="비밀번호를 입력해주세요."
                :class="{ 'is-error': pwFail }"
                :invalid="pwFail"
              >
                <span v-if="pwFail"
                  >영문/숫자/특수문자 2가지 이상 조합(최소 10자 이상)
                  <span class="text-black">또는</span>
                  <br />영문/숫자/특수문자 3가지 이상 조합(최소 8자 이상)
                </span>
              </base-input>
            </div>
            <!--<div class="form-group" v-if="memberInfo.joinchtype === 'UCT001' || memberInfo.joinchtype === 'UCT004'">-->
            <div class="form-group" v-if="memberInfo.joinchtype === 'UCT001'">
              <base-input
                label="비밀번호 재입력"
                type="password"
                :is-label="true"
                v-model="memberInfo.userpw2"
                @blur="pwConfirm"
                @input="pwConfirm"
                placeholder="비밀번호를 다시한번 입력해주세요."
                :class="{ 'is-error': pwCkFail }"
                :invalid="pwCkFail"
              >
                <span v-if="pwCkFail"> 비밀번호가 일치하지 않습니다. </span>
              </base-input>
            </div>
            <div class="form-group d-flex align-items-end">
              <div class="phone-input__area">
                <base-input
                  label="휴대폰 번호 (필수)"
                  :is-label="true"
                  v-model="memberInfo.mobile"
                  placeholder="-제외"
                  :is-readonly="true"
                />
              </div>
              <div class="modify-btn__area">
                <b-button
                  class="dp-btn not-hover modify-btn"
                  variant="outline-gray-800 type02"
                  @click="changePhone()"
                  squared
                >
                  <span>변경하기</span>
                </b-button>
              </div>
            </div>
            <div class="form-group">
              <base-input
                label="일반전화"
                :is-label="true"
                v-model="memberInfo.tel"
                placeholder="전화번호를 입력해주세요."
              />
            </div>
            <div class="form-group">
              <base-input
                label="이메일주소 (필수)"
                :is-label="true"
                v-model="memberInfo.email"
                @input="emailCheck()"
                placeholder="비밀번호를 다시 입력해주세요."
                :class="{ 'is-error': emailFail || emailFail2 || emailFail3 }"
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
            <div class="d-flex align-items-end form-group">
              <div class="address-input__area">
                <base-input
                  label="주소"
                  :is-label="true"
                  v-model="memberInfo.addrroad"
                  placeholder="주소를 입력해주세요."
                  :is-readonly="true"
                />
              </div>
              <div class="address-search-btn__area">
                <b-button
                  class="dp-btn not-hover"
                  variant="outline-gray-800 type02"
                  squared
                  @click="execDaumPostModal()"
                >
                  <span>주소검색</span>
                </b-button>
              </div>
            </div>
            <base-input
              label=""
              :is-label="false"
              v-model="memberInfo.addrdetail"
              placeholder="상세주소를 입력해주세요."
            />
          </div>
        </div>
        <div class="info-modify__body"></div>
      </section>
      <hr class="dp-hr justify-margin" />
      <!--<section class="easyLogin__area" v-if="memberInfo.joinchtype !== 'UCT001'">-->
      <section class="easyLogin__area">
        <div class="easyLogin__title dp-p">간편로그인</div>
        <ul class="list-style-none easyLogin__ul">
          <li v-if="memberInfo.joinchtype !== 'UCT003'">
            <span>카카오 로그인</span>
            <base-toggle
              :is-checked="$store.state.isKakao"
              @click="kakaoConnect"
              ref="kakao"
            />
          </li>
          <li v-if="memberInfo.joinchtype !== 'UCT002'">
            <span>네이버 로그인</span>
            <base-toggle
              :is-checked="$store.state.isNaver"
              @click="naverConnect"
              ref="naver"
            />
          </li>
          <li v-if="memberInfo.joinchtype !== 'UCT004' && browser=='Safari'">
            <span>애플 로그인</span>
            <base-toggle
              :is-checked="$store.state.isApple"
              @click="appleConnect"
              ref="apple"
            />
            <vue-apple-login
              v-show="false"
              mode="logo-only"
              type="sign in"
              color="black"
              width="0"
              height="0"
              ref="applelogin"
              :onSuccess="callSuccess"
              :onFailure="callFail"
            ></vue-apple-login>
          </li>
        </ul>
      </section>
      <hr class="dp-hr justify-margin" />
      <section class="reception-agree__area">
        <div class="reception-agree__title">
          <p class="mb-0 dp-p">광고정보 수신동의</p>
        </div>
        <div class="reception-agree__checkbox-area">
          <div>
            <ul class="list-style-none d-flex reception-agree__ul">
              <li>
                <base-checkbox
                  label="이메일"
                  id="emailCk"
                  name="modifyEmailCk"
                  v-model="memberInfo.isadmailing"
                  trueValue="T"
                  falseValue="F"
                />
              </li>
              <li>
                <base-checkbox
                  label="문자메시지"
                  v-model="memberInfo.isadsms"
                  id="msgCk"
                  name="modifyEmailCk"
                  trueValue="T"
                  falseValue="F"
                />
              </li>
            </ul>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin"  v-if="memberInfo.dadamembertype === 'DMT001' || memberInfo.dadamembertype === 'DMT002'"/>
      <!-- TODO : 7/14 평생회원 숨김처리 -->
      <!-- <section class="lifetime-member__area" v-if="memberInfo.dadamembertype === 'DMT001' || memberInfo.dadamembertype === 'DMT002'">
        <div class="lifetime-member__header d-flex justify-content-between">
          <div
            class="lifetime-member__title dp-p"
            @click="modalOpen('LifetimeMemberModal')"
          >
            [선택]평생회원으로 가입
          </div>
          <div
            class="benefit-view d-flex"
            @click="modalOpen('LifetimeMemberModal')"
          >
            <span class="dp-p">혜택보기</span
            ><i class="dp-icon icon-more sm"></i>
          </div>
        </div>
        <div class="lifetime-member__body">
          <div class="lifetime-member__radio-area d-flex">
            <base-radio
              type="radio"
              id="lifetime"
              label="평생회원으로 가입"
              name="lifeRadio"
              v-model="memberInfo.islife"
              val="T"
            />
            <base-radio
              type="radio"
              id="lifetimeClear"
              label="평생회원 해제"
              name="lifeRadio"
              v-model="memberInfo.islife"
              val="F"
            />
          </div>
          <p class="mb-0 dp-p-sm lifetime-member__join-text">
            평생회원 혜택은 최초 가입시에만 1회 지급됩니다.
          </p>
        </div>
      </section> -->
      <section class="lifetime-member__footer">
        <div>
          <div class="btn-group d-flex">
            <b-button
              class="dp-btn not-hover"
              variant="outline-gray-800 type02"
              squared
              @click="goToMypage"
            >
              <span>취소</span>
            </b-button>
            <b-button
              class="dp-btn text-white"
              variant="gray-800"
              squared
              @click="setSave()"
            >
              <span>저장하기</span>
            </b-button>
          </div>
          <div class="member-withdraw__area">
            <router-link to="/mypage/member-withdraw"
              ><span>회원탈퇴</span></router-link
            >
          </div>
        </div>
      </section>
      <!--  간편 로그인 영역  -->
      <KmcCertReq ref="kmcCertReq"></KmcCertReq>
    </div>
  </main>
</template>

<script src="@views.mobile/mypage/activity/memberInfo/InfoModify.js">
</script>
