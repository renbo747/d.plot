<template>
  <div>
    <!-- 레프트영역 -->
    <div class="left-menu">
        <div class="inner">
            <div class="title" v-on:click="goPartnersMain">
                <i class="icons icon-partner-t"></i>
                <span>파트너사관리</span>
            </div>
            <ul class="menu">
                <li v-for="menu in sideMenu" v-bind:key="menu.code">
                  <router-link :to="{name: menu.url}" v-bind:class="{ active: menu.isactive }" @click.native="onActive(menu.url)">
                    <span>{{ menu.name }}</span>
                    <i class="icon-expand" v-bind:class="{ active: menu.isactive }"></i>
                  </router-link>
                </li>
            </ul>
        </div>
    </div>
    <!-- /레프트영역 -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'PartnersManageSide',
  data() {
    return {
      sideMenu: [],
      parentCode: 'H'
    }
  },
  mounted() {
    let param = { pageCd : this.parentCode, path : this.$route.name, isloading : false }
    this.$http.post('/admin/common/side-menu', param)
        .then(result => {
          this.sideMenu = result.data.sidemenu;
        }).catch(error => {
          this.$util.debug(error);
        });
  },
  methods: {
    onActive: function(path){
      this.sideMenu.forEach( (obj) => {
        obj.isactive = (obj.url === path);
      })
    },
    goPartnersMain: function(){
        // this.$router.push({name : 'admin.partners.main'})
    },
  },
  watch: {
    $route(to) {
      let menu = null;
      this.sideMenu.forEach((thisMenu) => {
        if(thisMenu.url === to.name) {
          menu = thisMenu;
        }

        thisMenu.childmenulist.forEach((subMenu) => {
          if(subMenu.url === to.name) {
            menu = subMenu;
          }
        });
      })

      this.onActive(menu);
    }
  }
}
</script>