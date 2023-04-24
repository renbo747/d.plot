<template>
  <main class="login">
    <div class="container">
      <!--  로그인 페이지 헤더  -->
      <header class="login__header">
        <div class="login__logo">
          <logo />
        </div>
        <!-- <p class="login__caption dp-caption font-weight-400">
          D.PLOT 꿈꾸던 생활에 로그인하세요.
        </p> -->
      </header>
      <!--  // 로그인 페이지 헤더  -->

      <!--  로그인 폼 영역  -->
      <section class="login__form">
        <!--   로그인 인풋 영역   -->
        <div class="login__input">
          <form>
            <div class="form-group">
              <base-input
                class="login__input-id"
                v-model="id"
                placeholder="아이디를 입력해주세요."
              />
            </div>
            <div class="form-group">
              <base-input
                :type="'password'"
                class="login__input-password"
                v-model="pw"
                placeholder="비밀번호를 입력해주세요"
              />
            </div>
          </form>
        </div>
        <!--   로그인 보안문자 영역   -->
        <div class="login__security" v-if="isNeedCaptcha">
          <div class="security-description">
            <p class="description__text dp-p-sm font-weight-400">
              안전한 정보보호를 위해 보안문자를 입력해주세요.
            </p>
          </div>
          <div class="security-box">
            <div class="security__img" v-bind:style="{ backgroundImage: 'url(' + captchaImgSrc + ')','background-size': '200px 92px' }">
              <div v-if="!captchaImgSrc" style="display: table-cell; vertical-align: middle; ">{{captchaLoadText}}</div>
            </div>
            <ul class="security__btn list-style-none">
              <li>
                <b-button
                  class="dp-btn-security dp-btn dp-btn-icon btn-h40"
                  variant="outline-gray-400"
                  @click="setCaptcha('image')"
                  squared
                >
                  <i class="dp-icon icon-reset"></i>
                  <span class="font-weight-400">새로고침</span>
                </b-button>
              </li>

              <li>
                <b-button
                  class="dp-btn-security dp-btn dp-btn-icon btn-h40"
                  variant="outline-gray-400"
                  @click="setCaptcha('sound')"
                  squared
                >
                  <i class="dp-icon icon-play"></i>
                  <span class="font-weight-400">음성듣기</span>
                </b-button>
              </li>
            </ul>
          </div>
          <div class="security__input">
            <form>
              <div>
                <base-input
                  class="security"
                  v-model="captchaValue"
                  @enter="login()"
                  placeholder="위에 표시된 보안문자를 입력해주세요."
                />
              </div>
            </form>
          </div>
        </div>

        <!--   로그인 체크박스 영역   -->
        <div class="login__checkbox">
          <div>
            <base-checkbox
              label="아이디 저장"
              v-model="isSaveId"
              :id="'isSaveId'"
              name="isSaveId"
              :is-checked="isSaveId"
            />
          </div>
          <div>
            <base-checkbox
              label="자동 로그인"
              v-model="rememberMe"
              :id="'rememberMe'"
              name="rememberMe"
              :is-checked="rememberMe"
            />
          </div>
        </div>

        <!--   로그인 버튼 영역   -->
        <div class="login__btn">
          <div class="btn-signin">
            <b-button class="dp-btn text-white" variant="gray-800" squared @click="login()"
              >로그인</b-button
            >
          </div>

          <ul class="btn__signin-login list-style-none">
            <!--  아이디 찾기 모달  -->
            <li>
              <b-button
                class="dp-btn signin__list"
                variant="gray-800"
                @click="showModal('id-find')"
                >아이디 찾기</b-button
              >
            </li>
            <span class="dp-bar"></span>
            <li>
              <!-- 비밀번호 찾기 모달 버튼 -->
              <b-button
                class="dp-btn signin__list"
                variant="gray-800"
                @click="showModal('password-reset')"
                >비밀번호 찾기</b-button
              >
            </li>
            <span class="dp-bar"></span>
            <!--  회원가입  -->
            <li>
              <span class="signin__list" 
                @click="signup()"
                role="button"
                >회원가입</span
              >
            </li>
          </ul>
        </div>
        <div class="non-member__button">
          <router-link to="/etc/none-member-order" class="font-weight-500">
            비회원 주문조회
          </router-link>
        </div>
      </section>
      <!--  // 로그인 폼 영역  -->
      <!--  간편 로그인 영역  -->
      <section class="sns-login">
        <!--  간편 로그인 헤더  -->
        <header class="sns-login__header">
          <p class="sns-title font-weight-400 dp-p-sm">간편 로그인/회원가입</p>
        </header>

        <!--  카카오톡&네이버 로그인  -->
        <ul v-if="browser !== 'Safari'" class="sns-login__list list-style-none" >
          <li v-if="platform !='MAC003'">
            <b-button
              class="dp-btn dp-btn-icon sns-login__kakao"
              squared
              @click="kakaoLogin()"
            >
              <i class="dp-icon icon-kakao"></i>
              <span class="login__kakao dp-p-sm"
                >3초만에 카카오로 시작하기</span
              >
            </b-button>
          </li>
          <li v-if="platform !='MAC003'">
            <b-button
              class="dp-btn dp-btn-icon sns-login__naver"
              squared
              @click="naverLogin()"
            >
              <i class="dp-icon icon-naver"></i>
              <span class="login__naver dp-p-sm">네이버로 시작하기</span>
            </b-button>
          </li>
        </ul>
        <!--   sns 로그인     -->
        <ul
          v-if="platform === 'MAC003' || browser==='Safari'"
          class="sns-login__circle list-style-none"
        >
          <li class="circle-list">
            <a href="#" class="sns-circle-list circle__kakao" @click.prevent="kakaoLogin()"></a>
            <span class="circle-list__text">카카오</span>
          </li>
          <li class="circle-list">
            <a href="#" class="sns-circle-list circle__naver" @click.prevent="naverLogin()"></a>
            <span class="circle-list__text">네이버</span>
          </li>
          <!-- <li v-if="browser=='Safari'" class="circle-list">
            <a href="#" class="sns-circle-list circle__apple"></a>
            <span class="circle-list__text">Apple</span>
          </li> -->
          <li v-if="browser=='Safari'" class="circle-list">
            <vue-apple-login
            mode="logo-only"
            type="sign in"
            color="black"
            class="circle sns-circle-list circle__apple"
            :onSuccess="callSuccess"
            :onFailure="callFail"
            ></vue-apple-login>
            <span class="circle-list__text">Apple</span>
          </li>
          <li v-if="platform === 'MAC003'" class="circle-list">
            <a href="#" class="sns-circle-list circle__faceid" @click.prevent="bioLogin"></a>
            <span class="circle-list__text">생체로그인</span>
          </li>
          <!-- <li v-if="platform === 'MAC003'" class="circle-list">
            <a href="#" class="sns-circle-list circle__faceid" @click.prevent="$router.push('/test')"></a>
            <span class="circle-list__text">테스트</span>
          </li> -->
        </ul>
      </section>
    </div>
  </main>
</template>

<script src="./Login.js">
</script>

<style scoped>
.circle {
  border-radius: 50%;
  overflow: hidden;
  width: 56px;
}
</style>