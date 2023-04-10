<template>
  <div>
    <!-- 레프트영역 -->
    <div class="left-menu-p"> <!-- 파트너사 관리자는 left-menu-p 클래스 사용 -->
      <div class="inner">
        <ul class="menu">
          <li v-for="menu in sideMenu" v-bind:key="menu.code">
            <router-link :to="menu.childmenulist.length > 0? '':{name: menu.url}"
                         v-bind:class="{ active: menu.isactive }" @click.native="onActive(menu)">
              <span>{{ menu.name }}</span>
              <i v-if="menu.childmenulist.length > 0" class="icon-expand" v-bind:class="{ active: menu.childmenulist.length > 0 && menu.isactive }"></i>
            </router-link>
            <ul class="sub-menu" v-bind:class="{ dpn: !menu.isactive }">
              <li v-for="childMenu in menu.childmenulist" v-bind:key="childMenu.code">

                <router-link :to="{name: childMenu.url}" v-bind:class="{ active: childMenu.isactive }"
                             @click.native="onActive(childMenu)">
                  {{ childMenu.name }}
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
  name: "PartnersSide",
  data() {
    return {
      sideMenu: [],
    }
  },
  mounted() {
    this.$http.post('/partners/common/side-menu', {path: this.$route.name, isloading : false}).then(result => {
      this.sideMenu = result.data.sidemenu;
    }).catch(error => {
      this.$util.debug(error);
    });
  },
  methods : {
    onActive: function (thisMenuObj) {
      // 하위 메뉴가 존재하는 경우
      if (thisMenuObj.childmenulist != null && thisMenuObj.childmenulist.length > 0) {
        if (thisMenuObj.isactive) {
          thisMenuObj.isactive = false;
          return;
        }
        this.sideMenu.forEach((menu) => {
          menu.isactive = false;
        })
        thisMenuObj.isactive = true;
      }
      // 하위메뉴가 존재하지 않는 경우
      else {
        this.sideMenu.forEach((parentMenu) => {
          parentMenu.isactive = parentMenu.code === thisMenuObj.uppercode;
          parentMenu.childmenulist.forEach((childMenu) => {
            childMenu.isactive = false;
          })
        })

        thisMenuObj.isactive = true;
      }
    },
  },
  watch: {
    $route(to) {
      if(to.name === 'partners.main.dashboard'){
        this.sideMenu.forEach((menu) => {
          menu.isactive = false;

          menu.childmenulist.forEach((subMenu) => {
            subMenu.isactive = false
          });
        })
      } else {
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
}
</script>

<style scoped>
.content {
  padding : 52px 30px 0 30px !important;;
}
</style>