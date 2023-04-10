<template>
  <main class="dp-mypage-invitation">
    <div class="container">
      <section>
        <div class="mypage-invitation__header">
          <p class="mb-0 member__count">
            나의 초대로 가입한 회원 :
            {{ membercnt }}명
          </p>
        </div>
        <div class="mypage-invitation__body">
          <!--  초대로 가입한 친구가 있는경우 -->
          <div>
            <div class="invitation__wrap">
              <ul class="list-style-none invitation-member__ul">
                <li v-for="(list, index) in rewardList" :key="index">
                  <div>
                    <div class="invitation-member__header">
                      <p class="mb-0 d-flex justify-content-between">
                        <span class="member-name">{{ list.name }}</span>
                        <span class="date">{{ list.regdateformat }}</span>
                      </p>
                    </div>
                    <div class="invitation-member__body">
                      <div v-if="list.type == 'join'">
                        <ul class="list-style-none benefit__ul">
                          <li>
                            <div class="d-flex">
                              <span class="dp-badge square lg secondary"
                                >친구혜택</span
                              >
                              <p
                                class="mb-0 benefit__ul__p"
                                v-if="list.recomtype == 'RCT001'"
                              >
                                <span class="mr-1">적립금</span>
                                <span class="mr-1">{{
                                  $util.maskComma(list.revpoint)
                                }}</span>
                                <span>원</span>
                              </p>

                              <p
                                class="mb-0 benefit__ul__p"
                                v-else-if="list.recomtype == 'RCT002'"
                              >
                                <span class="mr-1">D포인트</span>
                                <span class="mr-1">{{
                                  $util.maskComma(list.revpoint)
                                }}</span>
                                <span>원</span>
                              </p>
                              <p class="mb-0 benefit__ul__p" v-else>
                                <span class="mr-1">{{
                                  list.recjoincpnname
                                }}</span>
                              </p>
                            </div>
                          </li>
                          <li>
                            <div class="d-flex">
                              <span class="dp-badge square lg primary"
                                >내혜택</span
                              >
                              <p
                                class="mb-0 benefit__ul__p"
                                v-if="list.recomtype == 'RCT001'"
                              >
                                <span class="mr-1">적립금</span
                                ><span class="mr-1">{{
                                  $util.maskComma(list.recjoinpoint)
                                }}</span
                                ><span>원</span>
                              </p>
                              <p
                                class="mb-0 benefit__ul__p"
                                v-else-if="list.recomtype == 'RCT002'"
                              >
                                <span class="mr-1">D포인트</span
                                ><span class="mr-1">{{
                                  $util.maskComma(list.recjoinpoint)
                                }}</span
                                ><span>원</span>
                              </p>
                              <p class="mb-0 benefit__ul__p" v-else>
                                <span class="mr-1">{{
                                  list.recjoincpnname
                                }}</span>
                              </p>
                            </div>
                          </li>
                        </ul>
                      </div>
                      <div v-else>
                        <p class="mb-0 empty-benefit__p">
                          첫 구매확정시 혜택을 드립니다.
                        </p>
                      </div>
                    </div>
                  </div>
                  <hr class="hr-h1" />
                </li>
                <infinite-loading
                  :identifier="infiniteId"
                  @infinite="getRewardInfo"
                  spinner="circles"
                >
                  <div slot="no-more"></div>
                  <div slot="no-results"></div>
                </infinite-loading>
              </ul>
              <!--  //초대로 가입한 친구가 있는경우 -->
            </div>
          </div>
           <!-- 초대로 가입한 친구가 없는 경우 -->
          <div>
            <div class="empty__invitation__wrap">
              <div>
                <div class="empty__invitation__header">
                  <p class="mb-0 empty__invitation__header__P">
                    D.PLOT를 친구에게 소개하고, 친구와 함께 <br />
                    혜택을 받아보세요.
                  </p>
                </div>
                <div class="empty__invitation__body">
                  <p class="mb-0 empty__invitation__body__P">
                    최대 {{ reclimitcnt }}명의 친구와 함께 혜택을 받을 수 있어요.
                  </p>
                </div>
                <div class="empty__invitation__footer">
                  <b-button
                    class="dp-btn text-white"
                    variant="gray-800"
                    squared
                    @click="openModal()"
                  >
                    <span>자세히 보기</span>
                  </b-button>
                </div>
              </div>
            </div>
          </div>
          <!-- //초대로 가입한 친구가 없는 경우 -->
        </div>
      </section>
    </div>

    <!-- 친구초대 Modal -->
    <!-- // 친구초대 Modal -->
  </main>
</template>

<script src="./Invitation.js"></script>
