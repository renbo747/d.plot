<template>
  <div class="dp-aside">
    <div class="aside__area">
      <div class="container">
        <div class="container-inner d-flex">
          <aside class="side-menu__area">
            <div
              v-if="$route.path.indexOf('mypage') != -1"
              class="aside__title dp-shop-title"
              @click="$router.push('/mypage/dashboard')"
              style="cursor: pointer;"
            >
              {{ asideTitle }}
            </div>
            <div
              v-else
              class="aside__title dp-shop-title"
              @click="$router.push('/cs/main')"
              style="cursor: pointer;"
            >
              {{ asideTitle }}
            </div>
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
                  <template v-for="(subList, index) in list.subMenu">
                  <li :key="index" v-if="subList.isView">
                    <router-link :to="subList.to" >{{
                      subList.title
                    }}</router-link>
                  </li>
                  </template>
                </ul>
                <!-- TODO: YIY -->
                <div class="withdraw__btn">
                  <router-link :to="$store.state.joinchtype == 'UCT001'?'/mypage/info-confirm?type=withdraw':'/mypage/member-withdraw'"
                    >회원탈퇴</router-link
                  >
                </div>
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
          menu: "LIKE",
          to: {name:'mypage-like',params:{init:true}},
          subMenu: [],
        },
        {
          id: 2,
          menu: "나의활동",
          to: "/mypage",
          subMenu: [
            {
              title: "회원정보",
              to: "/mypage/info-confirm",
              isView: true,
            },
            {
              title: "나의 문의",
              to: {name:'mypage-inquiry', params:{init:true}},
              isView: true,
            },
            {
              title: "나의 리뷰",
              to: {name:'before', params:{init:true}},
              isView: true,
            },
            {
              title: "Message",
              to: {name:'mypage-message', params:{init:true}},
              isView: true,
            },
            {
              title: "재입고알림",
              to: {name:'mypage-restock', params:{init:true}},
              isView: true,
            },
          ],
        },
        {
          id: 3,
          menu: "쇼핑",
          to: {name:'mypage-order',params:{init:true}},
          subMenu: [
            {
              title: "주문/배송",
              to: {name:'mypage-order',params:{init:true}},
              isView: true,
            },
            {
              title: "취소/교환/반품",
              to: {name: 'mypage-claim', params: {init:true}},
              isView: true,
            },
            {
              title: "A/S전용 문의",
              to: {name:'mypage-as-apply-list',params:{init:true}},
              isView: true,
            },
            {
              title: "이전주문내역(다다픽)",
              to: "/mypage/previous-order",
              isView: true,
            },
          ],
        },
        {
          id: 4,
          menu: "혜택",
          to: "/mypage/coupon",
          subMenu: [
            {
              title: "쿠폰",
              to: "/mypage/coupon",
              isView: true,
            },
            {
              title: "D포인트",
              to: "/mypage/epoint",
              isView: false,
            },
            {
              title: "적립금",
              to: "/mypage/reward",
              isView: true,
            },
            {
              title: "임직원 적립금",
              to: "/mypage/employee-reward",
              isView: true,
            },
            {
              title: "친구초대",
              to: "/mypage/invitation",
              isView: true,
            },
          ],
        },
        // {
        //   id: 5,
        //   menu: "회원탈퇴",
        //   to: "/mypage/info-confirm?type=withdraw",
        //   subMenu: [],
        // },
      ],
    },
  },
  mounted() {
    if (this.$route.path.indexOf("mypage") != -1) {
      if (
        this.$store.state.membertype == "DMT003" ||
        this.$store.state.membertype == "DMT004"
      ) {
        this.asideData[3].subMenu[3].isView = true;
      } else {
        this.asideData[3].subMenu[3].isView = false;
      }
      if (this.$store.state.joinchtype == "UCT001") {
        this.asideData[1].subMenu[0].to = "/mypage/info-confirm";
      }else {
        this.asideData[1].subMenu[0].to = "/mypage/info-modify";
      }
      // if (this.$store.state.joinchtype == "UCT001") {
      //   this.asideData[4].to = "/mypage/info-confirm?type=withdraw";
      // }else {
      //   this.asideData[4].to = "/mypage/member-withdraw";
      // }

      this.$http.post('/etc/reward', {isloading: false})
      .then(result =>{
        if(result.statusCode === 200) {
          if(this.$util.isNull(result.data.rewardinfo)) {
            this.asideData[3].subMenu[4].isView = false;
          }
        }
      })
    }
  },
};
</script>
