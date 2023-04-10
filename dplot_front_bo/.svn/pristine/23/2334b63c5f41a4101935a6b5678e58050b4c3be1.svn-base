<template>
  <div>
    <!-- 레프트영역 -->
    <div class="left-menu">
      <div class="inner">
        <div class="title" v-on:click="goOperationMain">
          <i class="icons icon-promotion-t"></i>
          <span>프로모션</span>
        </div>
        <ul class="menu">
          <li v-for="menu in sideMenu" :key="menu.code">
            <router-link :to="menu.childmenulist.length > 0? '':{name: menu.url}" :class="{ active: menu.isactive }" @click.native="onActive(menu)">
              <span>{{ menu.name }}</span>
              <i class="icon-expand" :class="{ active: menu.childmenulist.length > 0 && menu.isactive }"></i>
            </router-link>
            <ul class="sub-menu" :class="{ dpn: !menu.isactive }">
              <li v-for="childMenu in menu.childmenulist" :key="childMenu.code">
                <router-link :to="{name: childMenu.url}" :class="{ active: childMenu.isactive }" @click.native="onActive(childMenu)">
                  {{childMenu.name}}
                </router-link>
              </li>
            </ul>
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
  name: 'PromotionManageSide',
  data() {
    return {
      sideMenu: [],
      parentCode: 'K'
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
    onActive: function(thisMenuObj){
      // 하위 메뉴가 존재하는 경우
      if (thisMenuObj.childmenulist != null && thisMenuObj.childmenulist.length > 0) {
        if(thisMenuObj.isactive) {
          thisMenuObj.isactive = false;
          return;
        }
        this.sideMenu.forEach( (menu) => {
          menu.isactive = false;
        })
        thisMenuObj.isactive = true;
      }
      // 하위메뉴가 존재하지 않는 경우
      else {
        this.sideMenu.forEach( (parentMenu) => {
          if (parentMenu.code === thisMenuObj.uppercode) {
            parentMenu.isactive = true;
          } else {
            parentMenu.isactive = false;
          }
          parentMenu.childmenulist.forEach( (childMenu) =>{
            childMenu.isactive = false;
          })
        })
        thisMenuObj.isactive = true;
      }
    },
    goOperationMain: function(){
      // this.sideMenu.forEach( (parentMenu) => {
      //   parentMenu.isactive = false;
      //   parentMenu.childMenuList.forEach( (childMenu) =>{
      //     childMenu.isactive = false;
      //   })
      // })
      // this.$router.push({name : 'admin.promotion.main'})
    }
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
