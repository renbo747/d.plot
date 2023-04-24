<template>
  <main class="dp-mypage-invitation-pc">
    <section>
      <div class="mypage-invitation__header">
        <p class="mb-0 invitation__title">초대 친구목록</p>
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
              <hr class="hr-h1 mt-0" />
              <li v-for="(list, index) in rewardList" :key="index">
                <div>
                  <div class="invitation-member__body d-flex">
                    <div class="member-name__wrap">
                      <p class="mb-0 member-name">
                        {{ list.name }}
                      </p>
                    </div>
                    <div class="benefit-info__wrap">
                      <div class="d-flex" v-if="list.type == 'join'">
                        <div class="d-flex my-benefit__area">
                          <span class="dp-badge square lg primary">내혜택</span>
                          <p class="mb-0 benefit__p"  v-if="list.recomtype == 'RCT001'">
                            <span class="mr-1">적립금</span
                            ><span>{{ $util.maskComma(list.recjoinpoint) }}</span
                            ><span>원</span>
                          </p>
                          <p class="mb-0 benefit__p" v-else-if="list.recomtype == 'RCT002'">
                            <span class="mr-1">D포인트</span
                            ><span>{{ $util.maskComma(list.recjoinpoint) }}</span
                            ><span>P</span>
                          </p>
                          <p class="mb-0 benefit__p" v-else>
                            <span class="mr-1">{{ list.recjoincpnname }}</span>
                          </p>
                        </div>
                        <div class="d-flex friend-benefit__area">
                          <span class="dp-badge square lg secondary"
                            >친구혜택</span
                          >
                          <p class="mb-0 benefit__p"  v-if="list.recomtype == 'RCT001'">
                            <span class="mr-1">적립금</span
                            ><span class="mr-1">{{
                             $util.maskComma(list.revpoint)
                            }}</span
                            ><span>원</span>
                          </p>
                          <p class="mb-0 benefit__p" v-else-if="list.recomtype == 'RCT002'">
                            <span class="mr-1">D포인트</span>
                            <span class="mr-1">{{ $util.maskComma(list.revpoint) }}</span>
                            <span>P</span>
                          </p>
                          <p class="mb-0 benefit__p" v-else>
                            <span class="mr-1">{{ list.revcpname }}</span>
                          </p>
                        </div>
                      </div>
                      <div class="empty-benefit__wrap" v-else>
                        <p class="mb-0 empty-benefit__p">
                          첫 구매확정시 혜택을 드립니다.
                        </p>
                      </div>
                    </div>
                    <div class="date__wrap">
                      <p class="mb-0 date">{{ list.regdateformat }}</p>
                    </div>
                  </div>
                </div>
                <hr class="hr-h1" />
              </li>
            </ul>
          </div>
          <div>
            <div class="pagination__area d-flex justify-content-center">
              <base-pagination 
              :currentPage="pagingData.currentPage"
              :listTotal="pagingData.listTotal"
              :listCnt="pagingData.listCnt"
              @changePage="changePage"/>
            </div>
          </div>
        </div>
        <!--  //초대로 가입한 친구가 있는경우 -->

        <!-- 초대로 가입한 친구가 없는 경우 -->
        <!-- (todo: (pupl) : pc 디자인이 아직 없음-->
        <div>
          <div class="empty__invitation__wrap">
            <div>
              <div class="empty__invitation__header">
                <p class="mb-0 empty__invitation__header__P">
                  D.PLOT를 친구에게 소개하고, 친구와 함께 혜택을 받아보세요.
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
                  @click="goInvitationEvent"
                >
                  <span class="cursor">자세히 보기</span>
                </b-button>
              </div>
            </div>
          </div>
        </div>
        <!-- //초대로 가입한 친구가 없는 경우 -->
      </div>
    </section>
  </main>
</template>

<script src="./Invitataion.js"></script>
