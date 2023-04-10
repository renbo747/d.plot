<template>
  <!-- Todo: 적립금 포인트 한페이지에 있음-->
  <main class="dp-mypage-reward">
    <div class="container">
      <!-- 일반회원일 때-->
      <section class="reward-use__guide__section" v-if="$route.name != 'mypage-employee-reward'">
        <div class="reward-use__guide__header">
          <!-- D포인트 -->
          <div class="reward-use__guide__header__area" v-if="$route.name == 'mypage-epoint'" @click="openModal('epointModal')">
            <p class="mb-0 reward-use__guide__header__title">D포인트 사용안내</p>
            <i class="dp-icon icon-info"></i>
          </div>
          <!-- 적립금 -->
          <div class="reward-use__guide__header__area" v-if="$route.name == 'mypage-reward'" @click="openModal('rewardInfoModal')">
            <p class="mb-0 reward-use__guide__header__title">적립금 사용안내</p>
            <i class="dp-icon icon-info"></i>
          </div>
        </div>
        <div class="reward-use__guide__body">
          <!-- 소멸되는 적립금 또는 D포인트 있을경우-->
          <div class="reward__box-area" v-if="extinctpoint > 0">
            <div class="use-reward__box">
              <div class="box__left">
                <i class="dp-icon icon-money h40"></i>
                <!-- 적립금 -->
                <p class="mb-0 use-reward__p" v-if="$route.name == 'mypage-reward'">사용가능 적립금</p>
                <!-- 적립금 -->
                <p class="mb-0 use-reward__p" v-if="$route.name == 'mypage-employee-reward'">사용가능 임직원 적립금</p>
                <!-- D포인트 -->
                <p class="mb-0 use-reward__p" v-else>사용가능 D포인트</p>
              </div>
              <p class="reward-price__area mb-0 price__area">
                <span class="text-primary atten-new">{{$util.maskComma(userpoint)}}</span>
                <!-- 적립금 -->
                <span class="price-unit" v-if="$route.name == 'mypage-reward'">원</span>
                <!-- D포인트 -->
                <span class="price-unit-p atten-new" v-else>p</span>
              </p>
            </div>
            <hr class="h1" />
            <div class="extinction-reward__box">
              <p class="mb-0 extinction-reward__p">
                <!-- 적립금 -->
                <span v-if="$route.name == 'mypage-reward'">소멸예정 적립금</span>
                <!-- D포인트 -->
                <span v-else>소멸예정 D포인트</span><span>({{extinctday}})</span>
              </p>
              <p class="mb-0 price__area">
                <span class="atten-new">{{$util.maskComma(extinctpoint)}}</span>
                <!-- 적립금 -->
                <span class="price-unit" v-if="$route.name == 'mypage-reward'">원</span>
                <!-- D포인트 -->
                <span class="price-unit-p atten-new" v-else>p</span>
              </p>
            </div>
          </div>
          <!-- 소멸되는 적립금 또는 D포인트 없을경우-->
          <div class="reward__box-area" v-else>
            <div class="use-reward__box">
              <div class="box__left">
                <i class="dp-icon icon-money h40"></i>
                <p class="mb-0 use-reward__p" v-if="$route.name == 'mypage-epoint'">사용가능 D포인트</p>
                <p class="mb-0 use-reward__p" v-else-if="$route.name == 'mypage-reward'">사용가능 적립금</p>
                <p class="mb-0 use-reward__p" v-else>사용가능 임직원적립금</p>
              </div>
              <p class="reward-price__area mb-0 price__area">
                <span class="text-primary atten-new">{{$util.maskComma(userpoint)}}</span>
                <span class="price-unit" v-if="$route.name == 'mypage-reward'">원</span>
                <span class="price-unit-p atten-new" v-else>p</span>
              </p>
            </div>
          </div>
        </div>
      </section>
      <!-- 임직원 회원일 때-->
      <section class="reward-use__guide__section dp-mb-30" v-else>
        <div class="reward-use__guide__body">
          <div class="reward__box-area">
            <div class="use-reward__box pb-0">
              <div class="box__left">
                <i class="dp-icon icon-money h40"></i>
                <p class="mb-0 use-reward__p">사용가능<br />임직원적립금</p>
              </div>
              <p class="reward-price__area mb-0 price__area">
                <span class="text-primary atten-new">{{$util.maskComma(userpoint)}}</span>
                <span class="price-unit">원</span>
              </p>
            </div>
          </div>
        </div>
      </section>
      <section class="promotion-code__section" v-if="$route.name != 'mypage-employee-reward'">
        <div class="promotion-code__header">
          <p class="mb-0 promotion-code__header__title">프로모션 코드 등록</p>
        </div>
        <div class="promotion-code__body">
          <div class="input__area">
            <base-input placeholder="빈칸, 하이픈 없이 입력하세요"  v-model.trim="serialno"/>
            <b-button
              class="dp-btn not-hover"
              variant="outline-gray-800"
              squared
              @click="addPromotion()"
            >
              <span>등록</span>
            </b-button>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />

      <section class="reward-content__section">
        <!-- 일반회원 -->
        <div v-if="$route.name != 'mypage-employee-reward'">
          <ul class="dp-sort__ul type02 scroll list-style-none no-scrollbar">
            <li v-for="(list, index) in pointType" :key="index">
              <div>
                <label :for="list.id" class="dp-sort__label">
                  <input
                    type="radio"
                    :id="list.id"
                    name="reward"
                    v-model="type"
                    :value="list.id"
                    hidden
                  />
                  <span class="dp-sort__badge">{{ list.label }}</span>
                </label>
              </div>
            </li>
          </ul>
        </div>

        <!-- 임직원 회원 -->
        <div v-else>
          <ul class="dp-sort__ul type02 scroll list-style-none no-scrollbar">
            <li v-for="(list, index) in employeePointType" :key="index">
              <div>
                <label :for="list.id" class="dp-sort__label">
                  <input
                    type="radio"
                    :id="list.id"
                    name="empreward"
                    v-model="type"
                    :value="list.id"
                    hidden
                  />
                  <span class="dp-sort__badge">{{ list.label }}</span>
                </label>
              </div>
            </li>
          </ul>
        </div>

        <div class="reward-content__area">
          <!-- 적립금 & D포인트 내역이 있을 때-->
          <ul class="list-style-none" v-if="rewardList.length > 0">
            <li v-for="(list, index) in rewardList" :key="index">
              <div>
                <div class="li__header">
                  <p class="mb-0 date">{{ list.regdatestr }}</p>
                </div>
                <div class="li__body">
                  <div class="mb-0 d-flex justify-content-between align-items-center">
                    <!-- 적립금& 임직원적립금 -->
                    <template v-if="$route.name != 'mypage-epoint'">
                      <p class="li__body__title mb-0">[{{list.typename}}]{{ list.respaytypename }}</p>
                    </template>
                    <!-- D포인트 -->
                    <template v-else>
                      <p class="li__body__title mb-0">[{{list.typename}}]{{ list.epopaytypename }}</p>
                    </template>
                    <div class="d-flex align-items-center">
                      <span class="li__body__price atten-new" :class="{'text-primary':list.sign == '+'}">{{list.sign}}{{ $util.maskComma(list.point) }}</span>
                      <!-- 적립금& 임직원적립금 -->
                      <span class="price-unit" :class="{'text-primary':list.sign == '+'}" v-if="$route.name != 'mypage-epoint'">원</span>
                      <!-- D포인트 -->
                      <span class="price-unit-p " :class="{'text-primary atten-new':list.sign == '+'}" v-else>p</span>
                    </div>
                  </div>
                </div>
                <!-- 임직원 적립금이 아닐경우만 날짜기간 노출 -->
                <template v-if="$route.name != 'mypage-employee-reward'">
                <div class="list__footer d-flex justify-content-end"  v-if="!$util.isNull(list.period)">
                  <p class="mb-0 available__period">~ {{ list.period }} 까지</p>
                </div>
                </template>
              </div>
              <hr class="h1" />
            </li>
          </ul>
          <infinite-loading :identifier="infiniteId" @infinite="getRewardList" spinner="circles">
            <div slot="no-more"></div>
            <div slot="no-results">
              <template v-if="isEnd && rewardList.length <= 0">
                <hr class="h1" />
                <div class="empty__area">
                  <div class="empty__body d-flex flex-column align-items-center">
                    <div class="empty-icon-area d-flex align-items-center justify-content-center">
                      <i class="dp-icon icon-point xl02" v-if="$route.name == 'mypage-epoint'"></i>
                      <i class="dp-icon icon-reward xl02" v-else></i>
                    </div>
                    <div class="empty-text__area">
                      <p class="dp-p-sm text-gray-700 mb-0" v-if="$route.name == 'mypage-epoint'">
                        D포인트 내역이 없어요
                      </p>
                      <p class="dp-p-sm text-gray-700 mb-0" v-else-if="$route.name == 'mypage-reward'">
                        적립금 내역이 없어요
                      </p>
                      <p class="dp-p-sm text-gray-700 mb-0" v-else>
                        임직원적립금 내역이 없어요
                      </p>
                    </div>
                  </div>
                </div>
              </template>
            </div>
          </infinite-loading>
        </div>
      </section>
    </div>

    <!-- 적립금 사용안내 Modal -->
    <!-- // 적립금 사용안내 Modal -->

    <!-- D포인트 사용안내 Modal -->
    <!-- // D포인트 사용안내 Modal -->
  </main>
</template>

<script src="./Reward.js"></script>
