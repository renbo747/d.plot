<template>
  <main class="dp-member-info">
    <section class="container">
      <div class="member-info__header">
        <div class="member-info__title">
          <p class="mb-0">
            개인 정보를 안전하게 보호하기 위해 <br />
            비밀번호를 다시 한 번 입력해 주세요.
          </p>
        </div>
      </div>
      <div class="member-info__body">
        <div class="member-info-area">
          <ul class="list-style-none">
            <li class="d-flex">
              <span>아이디</span><span>{{ memberInfoData.userid }}</span>
            </li>
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
                    :invalid="pwFail"
                    class="align-items-center"
                    @isEnter="getLogin"
                  >
                    <!-- <span v-if="pwFail">
                      비밀번호를 입력해주세요
                    </span>
                    <span v-if="pwFail2">
                      비밀번호가 일치하지 않습니다.
                    </span> -->
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
            @click="getLogin()"
          >
            <span>확인</span>
          </b-button>
        </div>
      </div>
    </section>
  </main>
</template>

<script src="./InfoConfirm.js"></script>
