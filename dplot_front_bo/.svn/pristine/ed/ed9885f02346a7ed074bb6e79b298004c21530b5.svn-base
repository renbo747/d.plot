<template>
  <div class="content m-leftmenu">
    <common-navigator></common-navigator>
    <div class="inner">
      <div class="insta-layout">
        <div class="left">
          <div class="clearfix">
            <div class="bar-title fl">인스타피드</div>
            <div class="btn-group fr">
              <button type="button" v-if="showSelectBtn" class="btn big black-line" @click="instaFeedSearch">최신 피드 불러오기(25개씩) </button>
              <button type="button" class="btn big blue-line" @click="addMainFeed">메인화면 노출지정 ></button>
            </div>
          </div>
          <div class="scroll-y" style="max-height: calc(100vh - 400px);">
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
              <colgroup>
                <col width="4%" /><!-- 선택 -->
                <col width="122px" /><!-- 썸네일 -->
                <col width="8%" /><!-- 미디어 -->
                <col width="" /><!-- 내용 -->
                <col width="35%" /><!-- 게시물주소 -->
              </colgroup>
              <thead>
              <tr>
                <th>선택</th>
                <th>썸네일</th>
                <th>미디어</th>
                <th>내용</th>
                <th>게시물 주소</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="feed in feedList" v-bind:key="feed.id">
                <td><input type="checkbox" v-model="feed.isChecked" /></td>
                <td>
                  <div class="img-thumb size120" :class="feed.media_url === '' ? 'no-image': ''">
                    <img :src="feed.media_url">
                  </div>
                </td>
                <td>{{ feed.media_type }}</td>
                <td class="left">{{ feed.caption }}</td>
                <td class="left">{{ feed.permalink }}</td>
              </tr>
              </tbody>
            </table>
          </div>
          * 인증 만료시 인스타그램과 연동된 페이스북 계정으로 로그인을 해야 합니다.
        </div>
        <div class="right">
          <div class="clearfix">
            <div class="btn-group fr">
              <button type="button" class="btn big red-line" @click="removeMainFeed">메인화면에서 삭제</button>
            </div>
          </div>
          <div class="scroll-y" style="max-height: calc(100vh - 400px);">
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
              <colgroup>
                <col width="4%" /><!-- 선택 -->
                <col width="7%" /><!-- 노출순서 -->
                <col width="80px" /><!-- 순서변경 -->
                <col width="122px" /><!-- 썸네일 -->
                <col width="8%" /><!-- 미디어 -->
                <col width="" /><!-- 내용 -->
                <col width="30%" /><!-- 게시물주소 -->
              </colgroup>
              <thead>
              <tr>
                <th>선택</th>
                <th>노출순서</th>
                <th>순서변경</th>
                <th>썸네일</th>
                <th>미디어</th>
                <th>내용</th>
                <th>게시물 주소</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(mainFeed, index) in mainFeedList" v-bind:key="mainFeed.id">
                <td><input type="checkbox" v-model="mainFeed.isChecked"/></td>
                <td>{{index+1}}</td>
                <td>
                  <button type="button" class="btn black-line square" @click="sortChange(true, index)"><i class="arr-up"></i></button>
                  <button type="button" class="btn black-line square" @click="sortChange(false, index)"><i class="arr-down"></i></button>
                </td>
                <td>
                  <div class="img-thumb size120" :class="mainFeed.media_url === '' ? 'no-image': ''">
                    <img :src="mainFeed.media_url">
                  </div>
                </td>
                <td>{{ mainFeed.media_type }}</td>
                <td class="left">{{ mainFeed.caption }}</td>
                <td class="left">{{ mainFeed.permalink }}</td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="btn-group fr">
            <button type="button" class="btn big blue" @click="saveMainFeed">저장</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CommonNavigator from "@views.admin/common/CommonNavigator";

export default {
  name: 'admin.operation.display.instagramfeed',
  components: {CommonNavigator},
  data() {
    return {
      showSelectBtn : false,
      feedList: [],
      mainFeedList: [],
      removeList:[],
      paging: {},
      userid: '',
    }
  },
  mounted() {
    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
        this.isRead = (result.data.isread === 'T');
        this.isWrite = (result.data.iswrite === 'T');

        let userInfo = this.$storage.getLocalStorage("ADMIN_USER");
        this.userid = userInfo.id;
        if(this.isRead){
          this.$http.post('/admin/operation/display/instargram/feed', {}).then(result => {
            if(typeof result.data.code !== 'undefined' && result.data.code === 200){
              this.feedList = result.data.feedlist
              this.paging = result.data.paging
              this.showSelectBtn = (typeof result.data.paging.next !== 'undefined');

            } else if(typeof result.data.code !== 'undefined' && result.data.code !== 200){
              let msg = '인스타그램 인증 코드가 만료되었습니다\n인스타그램 인증처리를 진행 하시겠습니까?';
              if(confirm(msg)){
                let thisObj = this;
                let win = window.open(result.data.openurl);
                let timer = setInterval(function() {
                  if(win.closed) {
                    thisObj.instaFeedSearch();
                    clearInterval(timer);
                  }
                }, 1000);
              }
            }

            this.$util.debug(result);
          }).catch(error => {
            this.$util.debug(error);
          });

          this.mainFeedSearch();
        }

        if(!this.isWrite){
            let buttons = this.$el.getElementsByTagName('button');

            for(let button of buttons){
                if(button.className !== 'pop-close') {
                    button.style.display = 'none';
                    button.disabled = true;
                }
            }
        }
    }).catch(error => {
        this.$util.debug(error);
    });
  },
  methods: {
    instaFeedSearch() {
      let param = {};
      if(typeof this.paging.next !== 'undefined'){
        param.next = this.paging.next;
      }

      this.$http.post('/admin/operation/display/instargram/feed', param).then(result => {
        if(typeof result.data.code !== 'undefined' && result.data.code === 200) {
          this.paging = result.data.paging;
          this.showSelectBtn = (typeof result.data.paging.next !== 'undefined');

          let thisObj = this;
          result.data.feedlist.forEach(function (obj) {
            thisObj.feedList.push(obj);
          });
        } else {
          alert("처리 중 에러가 발생 하였습니다.");
        }
      }).catch(error =>{
        this.$util.debug(error);
      });
    },
    mainFeedSearch(){
      this.$http.post('/admin/operation/display/main/feed', {}).then(result => {
        this.mainFeedList = result.data.list;
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    addMainFeed() {
      let thisObj = this;

      let checkFeedList = this.feedList.filter(a => a.isChecked)
      let mainFeedCount = checkFeedList.length + this.mainFeedList.length;

      if(mainFeedCount > 9){
        alert('인스타피드는 9건만 지정할 수 있습니다');
        return false;
      }

      this.feedList.forEach(function(obj){
        if(typeof obj.isChecked !== 'undefined' && obj.isChecked){
          let isExist = thisObj.mainFeedList.find(element => element.id === obj.id);
          if(typeof isExist === 'undefined'){
            let jsonStr = JSON.stringify(obj);
            let json = JSON.parse(jsonStr);
            json.isChecked = false;
            thisObj.mainFeedList.push(json);
            obj.isChecked = false;
          } else {
            alert("노출중인 포스트가 포함되어 있습니다");
          }
        }
      });
    },

    sortChange(isUp, index){
      if( !(index === 0 && isUp) && !(index+1 >= this.mainFeedList.length && !isUp)){
        let targetIndex = (isUp) ? index - 1 : index + 1;
        let targetObj = this.mainFeedList[targetIndex];
        let moveObj = this.mainFeedList[index];

        this.mainFeedList.splice(index, 1, targetObj);
        this.mainFeedList.splice(targetIndex, 1, moveObj);
      }
    },
    saveMainFeed() {
      let msg = "";
      if(this.mainFeedList.length === 0) {
        msg = "메인화면에 노출되는 인스타피드가 없을 시 해당 영역이 숨김처리됩니다. 계속 진행하시겠습니까?";
      } else {
        msg = "저장 하시겠습니까?";
      }

      this.mainFeedList.forEach(function(item,index){
        item.sort = index;
      });

      let param = {
        data : this.mainFeedList,
        removeData: this.removeList,
        userid: this.userid
      }

      if(confirm(msg)){
        this.$http.post('/admin/operation/display/insert/main/feed', param).then(result => {
          this.mainFeedSearch();
        }).catch(error => {
          this.$util.debug(error);
        })
      }
    },
    removeMainFeed(){
      for(let i=0; i<this.mainFeedList.length; i++){
        let item = this.mainFeedList[i];
        if(typeof item.isChecked !== 'undefined' && item.isChecked){
          if(!Object.prototype.hasOwnProperty.call(item,"instaidx")){
            this.mainFeedList.splice(i, 1);
          }else{
            let jsonStr = JSON.stringify(item);
            let json = JSON.parse(jsonStr);
            this.removeList.push(json);
            this.mainFeedList.splice(i, 1);
          }
          i--;
        }
      }
    }
  }
}
</script>

<style></style>