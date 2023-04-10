<template>
  <div class="dp-promotion pc-top-padding">
    <div class="container">
      <div class="container-inner">
        <header class="promotion__header">
          <h2 class="promotion__title">PROMOTION</h2>
        </header>
        <div class="promotion-tabs">
          <b-tabs>
            <!-- Tabs 반복문 -->
            <template v-for="(item, index) in tabTitle">
              <b-tab
                :title="item.title"
                :key="index"
                :active="$route.name === item.value"
                @click="changeTap(item.value)"
              >
                <template v-if="mainList.length <= 0 && isEnd">
                  <p class="empty-data">{{item.title}} 내역이 없습니다.</p>
                </template>
                <template v-else>
                  <ul class="row list-style-none promotion-list">
                    <!-- 프로모션 리스트 반복문 -->
                    <li
                      v-for="(list, index) in mainList"
                      :key="index"
                      class="col-6"
                    >
                      <promotion-list
                        :promotionInfo="list"
                        :to="{
                          name: 'promotion-detail',
                          params: { eventidx: list.eventidx , init:true},
                        }"
                        @modal="openModal('promotionStModal', list)"
                      />
                    </li>
                  </ul>
                  <div class="d-flex justify-content-center">
                    <base-pagination
                      :currentPage="pagingData.currentPage"
                      :listTotal="pagingData.listTotal"
                      :listCnt="pagingData.listCnt"
                      @changePage="changePage"
                    />
                  </div>
                </template>
              </b-tab>
            </template>
          </b-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="@views.pc/shop/promotion/Index.js"/>
