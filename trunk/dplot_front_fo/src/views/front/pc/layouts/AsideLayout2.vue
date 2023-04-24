<template>
  <div class="dp-aside">
    <div class="aside__area">
      <div class="container">
        <div class="container-inner d-flex">
          <aside class="side-menu__area">
            <div class="aside__title dp-shop-title" @click="$router.push('/nonemember/dashboard')">{{ asideTitle }}</div>
            <div class="side-menu">
              <nav class="list-style-none dp-p side-menu__ul-box">
                <ul
                  class="list-style-none side-menu__ul"
                  v-for="(list, index) in asideData"
                  :key="index"
                >
                  <li>
                    <router-link :to="list.to">{{ list.menu }}</router-link>
                  </li>
                  <li v-for="(subList, index) in list.subMenu" :key="index">
                    <router-link :to="subList.to" v-if="subList.isView">{{
                      subList.title
                    }}</router-link>
                  </li>
                </ul>
              </nav>
            </div>
          </aside>
          <article class="dp-aside-article">
            <router-view></router-view>
          </article>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    asideTitle: {
      type: String,
      default: "마이페이지",
    },
    asideData: {
      type: Array,
      default: () => [
        {
          id: 1,
          menu: "쇼핑",
          to: "/nonemember",
          subMenu: [
            {
              title: "주문/배송",
              to: "/nonemember/order",
              isView:true
            },
            {
              title: "취소/교환/반품",
              to: "/nonemember/claim",
              isView:true
            },
            {
              title: "A/S전용 문의",
              to: {name:'nonemember-as-apply-list',params:{init:true}},
              isView:true
            },
          ],
        },
      ],
    },
  },
  mounted(){
    
  }
};
</script>
