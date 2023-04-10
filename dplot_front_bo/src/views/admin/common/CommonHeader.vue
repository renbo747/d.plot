<template>
    <!-- 탑영역 -->
    <div class="top-menu">
        <div class="top clearfix">
            <div class="logo link">
                <img src="../../../assets/img/logo.png" v-on:click='goMain' alt="D.PLOT">
            </div>
            <ul class="user">
                <li class="name-info">
                    <span class="name">{{user.name}}</span>
                    님, 안녕하세요.
                </li>
                <li class="today-info">
                    <span class="today">Today : </span>
                    <span class="date">{{ toDay }}</span>
                </li>
                <li class="btns">
                    <a href="javascript:void(0);" class="btn btn-outline-yellow" @click="goFavoriteMenu">즐겨찾기</a>
                    <a href="javascript:void(0);" class="btn btn-outline-light" @click="goMain">{{ goMainText }}</a>
                    <a href="javascript:void(0);" class="btn btn-outline-light" @click="goFrontMain">쇼핑몰메인</a>
                    <a href="javascript:void(0);" class="btn btn-dark" @click="logOut">로그아웃</a>
                </li>
            </ul>
        </div>
        <!-- 탑메뉴 -->
        <ul class="nav clearfix">
            <li v-for="menu in topMenu" v-bind:key="menu.code">
              <router-link :to="{ name: menu.description, params: {code: menu.code}}" v-bind:class="{ active: menu.isactive }" v-on:click.native="onActive(menu)">
                <i class="icons " :class="[
                    { 'icon-setting': menu.code === 'A' },
                    { 'icon-goods': menu.code === 'C' },
                    { 'icon-order': menu.code === 'D' },
                    { 'icon-manage': menu.code === 'E' },
                    { 'icon-member': menu.code === 'F' },
                    { 'icon-stats': menu.code === 'G' },
                    { 'icon-partner': menu.code === 'H' },
                    { 'icon-calculate': menu.code === 'I' },
                    { 'icon-promotion': menu.code === 'K' },
                    { 'icon-cs': menu.code === 'L' }
                    ]">
                </i>
                <span>{{ menu.name }}</span>
              </router-link>
            </li>
        </ul>
        <!-- /탑메뉴 -->
    </div>
</template>

<script>
import CommonFavoriteMenuPopup from '@/views/admin/common/popup/CommonFavoriteMenuPopup.vue'
import storage from "@/js/storage";
import store from "@/js/store";

export default {
  name: 'CommonHeader',
  data() {
    return {
      goMainText : '',
      topMenu: [],
      toDay: this.$util.getDate('-'),
      user : this.$util.getUser(this.$store.getters.CONSTANTS.MANAGER_SESSION)
    }
  },
  mounted() {
    if(typeof this.user !== 'undefined' && this.user.usertype === this.$store.getters.CONSTANTS.ADMIN.USER_TYPE_PARTNER){
      this.goMainText = '파트너사메인';
    } else {
      this.goMainText = '관리자메인';
    }

    let param = { path : this.$route.name, isloading : false }
    this.$http.post('/admin/common/top-menu', param)
        .then(result => {
          this.topMenu = result.data.topmenu;

          let pathDepth = this.$route.name.split(".");
          let thisPath = pathDepth[0] + '.' + pathDepth[1];

          this.topMenu.forEach(function(obj){
            let topPathDepth = obj.url.split(".");
            let topPath = topPathDepth[0] + '.' + topPathDepth[1];

            if(obj.url !== '' && thisPath === topPath){
              obj.isactive = true;
            }
          });
        }).catch(error => {
          this.$util.debug(error);
        });
  },
  methods: {
    goMain: function () {
      this.topMenu.forEach((obj) => {
        obj.isactive = false;
      });

      if (typeof this.user !== 'undefined' && this.user.usertype === this.$store.getters.CONSTANTS.ADMIN.USER_TYPE_PARTNER) {
        this.$router.push({name: 'partners.main'});
      } else {
        this.$router.push({name: 'admin.main.dashboard'});
      }
    },
    goFrontMain: function () {
      window.open(process.env.VUE_APP_PC_DOMAIN, "_blank");
    },
    onActive: function (menu) {
      this.topMenu.forEach((obj) => {
        obj.isactive = obj.url === menu.url;
      })
    },
    goFavoriteMenu: function () {
      this.$eventBus.$emit('modalShow', CommonFavoriteMenuPopup);
    },
    logOut: function () {
      let param = {};
      this.$http.post('/admin/logout', param)
          .then(data => {
            this.$util.debug(data);

            if (this.$store.getters.CONSTANTS.ADMIN.USER_TYPE_PARTNER === this.user.usertype) {
              this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.PARTNER_USER);
              this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION);
              this.$router.push({name: 'partners.login'});
            } else {
              this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.ADMIN_USER);
              this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION);
              this.$router.push({name: 'admin.login'});
            }

          }).catch(error => {
        this.$util.debug(error);
      });
    },
  },
  watch: {
    $route(to) {
      let routeName = to.name;
      let pathDepth = routeName.split(".");
      let thisPath = pathDepth[0] + '.' + pathDepth[1];

      this.topMenu.forEach(function(obj){
        let topPathDepth = obj.url.split(".");
        let topPath = topPathDepth[0] + '.' + topPathDepth[1];
        
        obj.isactive = false;
        if(obj.url !== '' && thisPath === topPath){
          obj.isactive = true;
        }
      });
    }
  }
}
</script>
