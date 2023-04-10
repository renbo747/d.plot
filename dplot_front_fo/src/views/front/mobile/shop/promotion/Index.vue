<template>
  <div class="dp-promotion">
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
            <template v-if="mainList.length <= 0">
              <p class="empty-data">{{item.title}} 내역이 없습니다.</p>
            </template>
            <template v-else>
              <ul class="row list-style-none promotion-list">
                <!-- 프로모션 리스트 반복문 -->
                <li
                  v-for="(list, index) in mainList"
                  :key="index"
                  class="col-12"
                >
                  <promotion-list
                    :promotionInfo="list"
                    :to="{
                      name: 'promotion-detail',
                      params: { eventidx: list.eventidx },
                    }"
                    @modal="openModal('promotionStModal', list)"
                  />
                </li>
              </ul>

              <div class="container">
                <b-button
                   v-if="pagingData.listTotal > mainList.length"
                  class="dp-btn dp-btn-icon dp-mb-40"
                  variant="outline-gray-800 not-hover type02"
                  @click="morePage()"
                  squared
                >
                  <span>더 보기</span>
                  <i class="dp-icon icon-arrow-down"></i>
                </b-button>
              </div>
            </template>
          </b-tab>
        </template>
      </b-tabs>

      <!-- promotion 당첨자 발표 모달 -->
    </div>
  </div>
</template>

<script src="@views.mobile/shop/promotion/Index.js"/>