<template>
  <div>
    <div class="left-menu">
      <div class="inner">
        <div class="title" v-on:click="goMemberMain">
          <i class="icons icon-member-t"></i>
          <span>회원관리</span>
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
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'MemberManageSide',
  data() {
    return {
      sideMenu: [],
      parentCode: 'F'
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
    goMemberMain: function(){
      // this.$router.push({name : 'admin.member.main'})
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