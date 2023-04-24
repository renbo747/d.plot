<template>
  <main class="dp-member-withdraw">
    <div class="container">
      <section class="member-withdraw__title">
        <p class="mb-0 dp-title02">지금 탈퇴하시면 잃게 되는 혜택입니다.</p>
      </section>
      <section class="benefit__text-area">
        <div>
          <ul class="list-style-none">
             <li>
              <div class="d-flex">
                <div class="text-area__key">
                  <span>보유 적립금</span>
                </div>
                <div class="text-area__value">
                  <span class="benefit__value">{{  $util.maskComma(payInfo.respoint) }}</span
                  ><span class="value__unit">원</span>
                </div>
              </div>
            </li>
            <!-- <li>
              <div class="d-flex">
                <div class="text-area__key">
                  <span>D포인트</span>
                </div>
                <div class="text-area__value">
                  <span class="benefit__value">{{ $util.maskComma(payInfo.epoint) }}</span
                  ><span class="value__unit">P</span>
                </div>
              </div>
            </li> -->
            <li v-if="memberInfo.membertype == 'DMT003' || memberInfo.membertype == 'DMT004'">
              <div class="d-flex">
                <div class="text-area__key">
                  <span>임직원 적립금</span>
                </div>
                <div class="text-area__value">
                  <span class="benefit__value">{{ $util.maskComma(payInfo.emppoint) }}</span
                  ><span class="value__unit">원</span>
                </div>
              </div>
            </li>
            <li>
              <div class="d-flex">
                <div class="text-area__key">
                  <span>쿠폰</span>
                </div>
                <div class="text-area__value">
                  <span class="benefit__value">{{ $util.maskComma(payInfo.couponcnt) }}</span
                  ><span class="value__unit">장</span>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </section>
      <section class="withdraw__reason-select">
        <div class="reason-select__area">
          <div>
            <p class="mb-0 reason-select__title dp-p-sm">사유선택 (필수)</p>
          </div>
          <div class="withdraw__reason-select__body">
            <base-select
              :options="commonCodeList"
              placeholder="사유를 선택해주세요."
              v-model="select"
              class="reason-select__box"
            />
            <div v-if="select == 'WDR010'">
              <base-textarea
                v-model.trim="textareaData"
                placeholder="탈퇴사유를 작성해주세요."
              ></base-textarea>
            </div>
            <div>
              <p class="mb-0 caution__text dp-p-sm">
                탈퇴시
                 <span v-if="memberInfo.membertype == 'DMT003' || memberInfo.membertype == 'DMT004'">임직원 적립금,</span> 적립금 및
                쿠폰을 포함한 모든 혜택과 회원등급 정보가 삭제됩니다. <br />회원
                탈퇴시 30일간 재가입이 불가하며, 한번 삭제된 정보는 재가입하여도
                복구되지 않습니다.
              </p>
            </div>
          </div>
          <hr class="dp-hr" />
          <div class="withdraw__reason-select__footer"></div>
        </div>
      </section>
      <section class="agree__area">
        <base-checkbox
          label="위의 안내 사항을 모두 확인하였으며, 이에 동의합니다."
          v-model="isAgree"
          name="agreeCheckbox"
        />
      </section>
      <section class="member-withdraw__btn-area">
        <div class="btn-group d-flex">
          <b-button
            class="dp-btn not-hover"
            variant="outline-gray-800 type02"
            squared
            @click="goToInfoModify"
          >
            <span>취소</span>
          </b-button>
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            squared
            @click="setMemberWithdraw"
          >
            <span>탈퇴하기</span>
          </b-button>
        </div>
      </section>
    </div>
  </main>
</template>

<script src="./MemberWithdraw.js">
</script>
