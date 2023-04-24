<template>
  <div class="shop-list">
    <!-- <b-dropdown id="dropdown-1" :text="cateTitle" class="cate_title">
      <b-dropdown-item v-for="(siblingcate, idx) in siblingcates" :key="idx" @click="$router.push(`/shop/list/${siblingcate.cateidx}`)">{{siblingcate.catename}}</b-dropdown-item>
    </b-dropdown> -->
    <div class="selet-wrap">
      <select class="cate_title" name="" v-model="activeCate" :onchange="cateChagne()">
        <option v-for="(siblingcate, idx) in siblingcates" :key="idx" :value="siblingcate.catename">{{siblingcate.catename}}</option>
      </select>
      <span>{{cateTitle}}</span>
    </div>
    <div class="shop-list-ctg-box no-scrollbar">
      <ul class="shop-list-ctg list-style-none d-flex">
        <li
          v-for="(item, i) in cateList"
          :key="i"
          class="ctg-list"
          :class="{ active: item.cateidx == $route.params.idx }"
          @click="changeCate(item.cateidx )"
        >
          <span class="list-default">{{ item.catename }}</span>
        </li>
      </ul>
    </div>
    <section>
      <header class="shop-list__header">
        <div class="header__top">
          <div>
            <p class="dp-p-sm text-black font-weight-500 mb-0">
              총 {{ pagingData.listTotal }}개
            </p>
          </div>
          <div class="header-right d-flex">
            <!-- <div class="list-icon-box">
              <i class="dp-icon icon-sort"></i>
            </div> -->
            <div class="list-select-box">
              <base-select
                class="select-list"
                v-model="sortActive"
                :options="sortList"
                :isPlaceholder="false"
                @change="setSortActive()"
              />
              <span>{{sortList.find(el => el.value == sortActive).label}}</span>
            </div>
            <div class="list-filter-box">
              <i
                class="dp-icon icon-filter"
                v-if="isFilter"
                @click="openModal('productFilterModal')"
              ></i>
            </div>
            
            <!-- 상품 필터-->
            <!-- // 상품 필터 Modal -->
          </div>
        </div>

        <!-- 필터-->
        <div class="header__bottom" v-if="setFilterList.length > 0">
          <ul class="header-filter__ul list-style-none no-scrollbar">
            <li v-for="(items, index) in setFilterList" :key="index">
              <span class="dp-filter" @click="delFilter(items)">
                {{ items.value }}
                <i class="dp-icon icon-close"></i>
              </span>
            </li>
          </ul>
          <div class="bottom__icon" @click="getFilterInit()">
            <i class="dp-icon icon-reset"></i>
          </div>
        </div>
      </header>
      <div class="shop-list__body">
        <div class="container">
          <ul class="row product-list" v-if="goodsList.length > 0">
            <li class="col-6" v-for="(item, index) in goodsList" :key="index">
              <product :product-info="item" :height="155" :isBedge="true"/>
            </li>
          </ul>
        </div>
        <infinite-loading 
            :identifier="infiniteId"
            @infinite="infiniteHandler"
            spinner="circles"
          >
            <div slot="no-more"></div>
            <div slot="no-results">
              <div class="row list__empty-items">
                <div class="list__icon">
                  <i class="empty-list-icon"></i>
                </div>
                <p class="empty-list__text">
                  검색조건에 맞는 상품이 없습니다.<br />
                  필터를 초기화 해주세요.
                </p>
                <!-- <b-button
                  class="dp-btn dp-btn-icon not-hover shop-list__empty__btn"
                  variant="outline-gray-800"
                  @click="getFilterInit()"
                  squared
                >
                  <span class="empyt__btn__text">초기화</span>
                  <i class="dp-icon icon-reset sm"></i>
                </b-button> -->
              </div>
            </div>
          </infinite-loading>
      </div>
    </section>
  </div>
</template>
<script src="./List.js"></script>

<style scoped>
  .shop-list .form-select.select-list {padding: 0;}
  .shop-list .list-select-box {margin-right: 15px;}
  .selet-wrap {position: relative;}
  .cate_title {
    background: transparent;
    font-size:22px;
    font-weight: 600;
    border:none;
    vertical-align: middle;
    -webkit-appearance: none;
    width:100%;
    position: absolute;
    top: 0;
    left: 0;
  }
  .cate_title + span {
    font-size: 22px;
    padding:40px 10px 10px;
    font-weight: 600;
    vertical-align: middle;
    display:inline-flex;
    align-items: center;
  }
  .cate_title + span::after{
    content:'';
    position: relative;
    width: 14px;
    height: 14px;
    top: 2px;
    margin-left: 5px;
    display: inline-block;
    background: url(~@/assets/common/icon/new_down_arrow.svg) no-repeat center center;
  }
  .list-select-box {position: relative;}
  .list-select-box .select-list {
    font-size:14px;
    background: transparent;
    border:none;
    vertical-align: middle;
    -webkit-appearance: none;
    width:100%;
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
  }
  .list-select-box .select-list + span {
    vertical-align: middle;
    display:inline-flex;
    align-items: center;
    font-size:14px;
    height:44px;
  }
  .list-select-box .select-list + span::after{
    content:'';
    position: relative;
    width: 10px;
    height: 10px;
    top: 2px;
    margin-left: 5px;
    display: inline-block;
    background: url(~@/assets/common/icon/new_down_arrow.svg) no-repeat center center;
  }
</style>