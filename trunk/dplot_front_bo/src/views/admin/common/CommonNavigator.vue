<template>
  <div class="page-info clearfix">
    <div class="title">{{ pageName }}</div>
    <i class="icon-help" @click="toggleHelpLayer(!helpLayerShow)" v-if="!$util.isNull(description)"></i>
    <div class="help-layer dpb" v-if="helpLayerShow"><!-- 도움말 아이콘 클릭 시 노출 : dpb / 한번 더 클릭 시 숨김 : dpn -->
      <ul v-html="description" v-click-outside="toggleHelpLayer"></ul>
    </div>
    <ul class="breadcrumb">
      <li v-for="nav in navData" v-bind:key="nav.code">
        <router-link :to="{name: nav.url, params: { code : nav.code }}" v-bind:class="{ active: nav.isActive }">
          {{nav.name}}
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import vClickOutside from 'v-click-outside';
export default {
  name: 'AdminCommonNavigator',
  directives: {
    clickOutside: vClickOutside.directive
  },
  data() {
    return {
      navData: [],
      pageName: '',
      description : '',
      helpLayerShow : false
    }
  },
  mounted() {
    let params = { code : '', path: this.$route.name, isloading: false }
    this.$http.post('/admin/common/menu-nav', params)
        .then(result => {
          let data = result.data;

          let thisPath = this.$route.name
          this.pageName = data.pagename;
          this.description = data.description;
          this.navData.push({code : 'HOME', name : '홈', url : 'admin.main.dashboard', isActive : false });
          this.navData.push({code : data.depth1code, name : data.depth1name, url : data.depth1url, isActive : (thisPath === data.depth1url)});
          
          if(data.depth2code != null ){
            this.navData.push({code : data.depth2code, name : data.depth2name, url : data.depth2url, isActive : (thisPath === data.depth2url)});
          }
          if(data.depth3code != null ){
            this.navData.push({code : data.depth3code, name : data.depth3name, url : data.depth3url, isActive : (thisPath === data.depth3url)});
          }
        }).catch(error => {
          this.$util.debug(error);
        });
  },
  methods : {
    toggleHelpLayer(value){
      if (typeof(value) === 'boolean') {
        this.helpLayerShow = value;
        return;
      }
      this.helpLayerShow = false;
    }
  }
}
</script>