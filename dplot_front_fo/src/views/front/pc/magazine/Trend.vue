<template>
  <main class="dp-magazine-trend-pc">
    <div class="container">
      <div class="container-inner">
        <section class="magazine-trend__header">
          <div class="trend__header__title__area">
            <h1 class="mb-0 trend__header__title atten-new">매거진</h1>
          </div>
          <div class="d-flex pt-pb-20 trend-sort__ul__wrap tab-wrapper">
            <div class="container tab-style">
              <ul class="dp-sort__ul list-style-none no-scrollbar">
                <li v-for="(list, index) in trendTabType" :key="index">
                  <label :for="list.mzcateidx" class="dp-sort__label" @click="handleInquiry(index)">
                    <input
                      type="radio"
                      :id="list.mzcateidx"
                      name="terms"
                      :checked="(list.subject=='전체'?'ALL':list.subject) === $route.query.catename"
                      :ref="list.mzcateidx"
                      hidden
                    />
                    <span class="dp-sort__badge dp-p" >{{
                      list.subject
                    }}</span>
                  </label>
                </li>
              </ul>
            </div>
          </div>
        </section>
        <section class="magazine__trend__contents__section">
          <div class="trend__contents__wrap">
            <div class="tab__area">
              <ul class="list-style-none tab-content-list__ul">
                <li
                  v-for="(list, index) in filterData"
                  :key="index"
                  class="col-6"
                >
                  <div>
                    <router-link :to="'/magazine/trend/detail/'+list.tridx">
                      <magazine-card :option="list" :is-route-link="false" :isbedge="false" />
                    </router-link>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </section>
      </div>
    </div>
  </main>
</template>

<script src="@views.mobile/magazine/Trend.js"></script>

<style scoped>
  .tab-content-list__ul {display: flex; flex-wrap: wrap; margin: 0;}
  .dp-magazine-trend-pc .magazine-trend__header {padding-top: 0;}
  .dp-magazine-trend-pc .magazine-trend__header .trend__header__title__area .trend__header__title {font-size: 2rem; margin-top: 60px;}
  .dp-magazine-trend-pc .magazine-trend__header .trend__header__title__area {padding: 0;}
  .tab-content__header { padding:40px 20px 0 20px; }
  h2 {font-size: 42px; font-weight: 600;}
  .dp-magazine-trend .trend-sort__ul__wrap .dp-sort__ul li + li {margin: 0;}
  input + .dp-sort__badge {border:none; font-size: 20px; height: 50px; padding:0 35px; color: #666; cursor:pointer;}
  input:checked + .dp-sort__badge {font-weight: 600; color: #111;}
  .tab-style {border-top:1px solid #000; border-bottom:1px solid #000; padding:0;}
  .tab-style ul {display: flex; align-items: center;}
  .tab-wrapper {padding: 35px 0 60px 0;}
  .dp-magazine-trend-pc .magazine__trend__contents__section .trend__contents__wrap li + li {margin-top: 84px;}
  .dp-magazine-trend-pc .magazine__trend__contents__section .trend__contents__wrap li:nth-child(2) {margin-top: 0;}
</style>