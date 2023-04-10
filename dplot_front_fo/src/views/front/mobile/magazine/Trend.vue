<template>
  <main class="dp-magazine-trend">
    <div class="tab-content__header">
      <div class="magazine__title">
        <h2>매거진</h2>
      </div>
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
    <section class="magazine__trend__contents__section">
      <div class="trend__contents__wrap">
        <div class="tab__area">
          <ul class="list-style-none tab-content-list__ul row">
            <li
              v-for="(list, index) in filterData"
              :key="index"
              class="col-12"
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
  </main>
</template>

<script src="./Trend.js"></script>

<style scoped>
  .tab-content__header { padding:40px 10px 10px; }
  h2 {font-size: 22px; font-weight: 600; margin-bottom: 4px;}
  .dp-magazine-trend .trend-sort__ul__wrap .dp-sort__ul li + li {margin: 0;}
  .dp-magazine-trend .trend-sort__ul__wrap input + .dp-sort__badge { background:transparent; color:#666; font-size: 16px; letter-spacing: -.56px; font-weight:400; border:none; height: 42px; text-align: center; white-space: nowrap;; padding:0 10px;}
  .dp-magazine-trend .trend-sort__ul__wrap input:checked + .dp-sort__badge { background:transparent; color:#111; font-weight: 600; font-size: 16px;}
  .tab-style {border-top:1px solid #000; border-bottom:1px solid #000; padding:0;}
  .tab-style ul {display: flex; align-items: center;}
  .tab-wrapper {padding: 0 0 28px 10px;}
  .dp-magazine-trend .magazine__trend__contents__section .trend__contents__wrap li + li {padding-top: 40px;}
</style>