<template>
  <div class="wrap">
    <the-header :class="{ on: isHeaderFixed || onSubPage(), dark: currentColor }" v-if="onHeader()" :is-black="isBlack" />
    <div :class="{ 'sub-page': onSubPage() && onHeader()}">
      <router-view @load="getMainBannerInfo" :key="$route.fullPath"/>
    </div>
    <main-popup 
      v-if="isMainPopup && showPopup.includes($route.name)" :thumbnailData="thumbnailData"
      @close="isMainPopup = false"
    />
    <the-footer v-if="onHeader()"/>
    <component
      :key="skey"
      :is="modalInfo.comp" 
      :id="modalInfo.id" 
      :param="modalInfo.param"
      :fnConfirm="modalInfo.fnConfirm" 
      :fnCancel="modalInfo.fnCancel" />
    <component
      :key="ckey"
      :is="alertInfo.comp" 
      :title="alertInfo.title" 
      :content="alertInfo.content" 
      :button1="alertInfo.button1" 
      :button2="alertInfo.button2" 
      :fnConfirm="alertInfo.fnConfirm" 
      :fnCancel="alertInfo.fnCancel" />
  </div>
</template>

<style scoped>
.sub-page {
  padding-top: 80px;
  padding-bottom: 200px;
}
</style>
<script>
import TheHeader from "@views.pc/layouts/TheHeader";
import TheFooter from "@views.pc/layouts/TheFooter";
import Alert from "@views.common/components/ui/modal/Alert";
import Confirm from "@views.common/components/ui/modal/Confirm";



export default {
  components: {
    TheHeader,
    TheFooter,
  },
  data() {
    return {
      isBlack: null,
      currentColor: null,
      ckey:0,
      skey:0,
      isHeaderFixed: false,
      // 앱 바를 숨겨야 하는 페이지
      hideAppBars: [],
      // 서브 페이지가 아닌 페이지
      isNotSubPage: ["main"],
      // 메인 팝업이 보여야하는 페이지
      showPopup: ["main", "shop"],
      // 해피톡 비노출페이지
      hideTalks : ["order", "member-login", "search", "goods", "brand", "contens", "none-member-order"],
      // 메인팝업
      isMainPopup: false,
      // 팝업 리스트
      thumbnailData: [],
      modalInfo : {
        comp : null,
        id : null,
        param : null,
        fnConfirm : () => {},
        fnCancel : () => {}
      },
      alertInfo : {
        comp : null,
        title : null,
        content : null,
        button1 : null,
        button2 : null,
        fnConfirm : () => {},
        fnCancel : () => {}
      }
    };
  },
  mounted() {
    this.getMainBannerInfo();
    window.addEventListener("scroll", this.detectScroll);
    
    /****************************
     * 메인 팝업 이벤트 버스처리
     *******************************/
    this.$eventBus.$on('popup', (data, check)=>{
      this.thumbnailData = data;
      this.isMainPopup = check;
    })

    this.$eventBus.$on('scrollTo', (name, callback) => {
      const savedPosition = this.$store.state.savedPosition[name];
      let hasScroll = false;
      if(!this.$util.isBlank(savedPosition)){
        window.scrollTo({
          top: savedPosition.y,
          left:0,
          //behavior: 'smooth'
        });
        hasScroll = true;
      }
      if(callback) {
        callback(hasScroll);
      }
    })

    //Confirm 모달창 Show
    this.$eventBus.$on('confirm', (title, content, confirmFn, cancelFn, button1, button2) => {
        this.$store.commit('isCart', false);
        this.$store.commit('isMypage', false);
        this.ckey = Date.now();
        this.alertInfo.comp = Confirm;

        this.alertInfo.title = title ? title : '확인';
        this.alertInfo.content = content ? content : '확인';
        this.alertInfo.button1 = button1 ? button1 : '확인';
        this.alertInfo.button2 = button2 ? button2 : '닫기';

        if (confirmFn) {
            this.alertInfo.fnConfirm = confirmFn;
        } else {
            this.alertInfo.fnConfirm = () => {}
        }
        if (cancelFn) {
            this.alertInfo.fnCancel = cancelFn;
        } else {
            this.alertInfo.fnCancel = () => {}
        }
        this.$nextTick(function () {
          this.$bvModal.show('confirmModal');
        });
    });

    // Alert 모달창 Show
    this.$eventBus.$on('alert', (title, content, confirmFn, button1) => {
        this.$store.commit('isCart', false);
        this.$store.commit('isMypage', false);
        this.ckey = Date.now();
        this.alertInfo.comp = Alert;

        this.alertInfo.title = title ? title : '확인';
        this.alertInfo.content = content ? content : '확인';
        this.alertInfo.button1 = button1 ? button1 : '확인';

        if (confirmFn) {
            this.alertInfo.fnConfirm = confirmFn;
        } else {
            this.alertInfo.fnConfirm = () => {}
        }
        this.$nextTick(function () {
          this.$bvModal.show('alertModal');
        });
    });

    //b-modal이 아닌 모달
    this.$eventBus.$on("showNoBvModal", (comp, params, confirmFn, cancelFn) => {
      this.$modal.show(
        comp,
        {
          modalComp: comp,
          params: params,
          confirmFn: confirmFn,
          cancelFn:cancelFn
        },
        {
          name: "commonModal",
          draggable: false,
          resizable: false,
          width: "320px",
          height: "auto",
        }
      );
    });
    
    //모달창 열기
    this.$eventBus.$on('showModal', (comp, id, param, confirm, cancel) => {
        this.$store.commit('isCart', false);
        this.$store.commit('isMypage', false);
        
        this.skey = Date.now();
        this.modalInfo.comp = comp;
        this.modalInfo.id = id;
        
        if (!this.$util.isNull(param)) {
          this.modalInfo.param = param;
        } else {
          this.modalInfo.param = {};
        }
        
        this.$util.debug("Modal Param :" + JSON.stringify(this.modalInfo.param));
        if (confirm) {
            this.modalInfo.fnConfirm = confirm;
        } else {
            this.modalInfo.fnConfirm = () => {}
        }
        if (cancel) {
            this.modalInfo.fnCancel = cancel;
        } else {
            this.modalInfo.fnCancel = () => {}
        }
        this.$nextTick(function () {
          this.$bvModal.show(id);
        });
    });

    //모달닫기
    this.$eventBus.$on('hideModal', ()=>{
      this.$bvModal.hide(this.modalInfo.id);
    });

    //세션스토리지 장바구니 저장
    this.$eventBus.$on('saveCart', (callback) => {
        let items = this.$storage.getLocalStorage("CART_ITEMS");
        if(this.$util.isNull(items)) {
          items = [];
        }

        if(items.length > 0) {
          this.$front.saveCart(this, items, ()=>{
            if(callback) {
              callback();
            }
          })
        } else {
          //this.$front.getCartList(this);
          this.$front.getCartGoodsCount(this);
          if(callback) {
            callback();
          }
        }
    });

    this.$eventBus.$on('goPage', (param)=>{
      this.$router.push(param);
    });

    this.$eventBus.$on('replacePage', (param)=>{
      this.$router.replace(param);
    });
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.detectScroll);
  },
  methods: {
    getMainBannerInfo(val) {
      this.currentColor = val === "#ffffff";

      if (val === "#ffffff") {
        this.isBlack = true;
      } else {
        this.isBlack = false;
      }
    },
    // 모바일 서브 페이지 판별
    onSubPage() {
      return !this.isNotSubPage.includes(this.$route.name);
    },
    onHeader() {
      if(this.$route.name.indexOf('kmcCert') > -1 
        || this.$route.name.indexOf('naver') > -1 
        || this.$route.name.indexOf('kakao') > -1 
        || this.$route.name.indexOf('toss') > -1 ) {
        return false;
      } else {
        return true;
      }
    },
    // 모바일 앱바 핸들러
    showAppBar() {
      return !this.hideAppBars.includes(this.$route.name);
    },
    detectScroll() {
      let scrollY = window.scrollY;
      if (scrollY >= 100) {
        this.isHeaderFixed = true;
        this.isBlack = false;
      } else {
        this.isHeaderFixed = false;
        this.isBlack = this.currentColor;
      }
    },
  },
  watch: {
    $route(val) {
      this.$eventBus.$emit('hideModal');
      
      let ishappytalk =false;
      if (
        this.$route.path.indexOf("kmcCert") > -1 ||
        this.$route.path.indexOf("naver") > -1 ||
        this.$route.path.indexOf("kakao") > -1 ||
        this.$route.path.indexOf("toss") > -1 ||
        this.hideTalks.includes(this.$route.name)
      ) {
        ishappytalk = false;
      } else {
        ishappytalk = true;
      }
      this.$nextTick(()=>{
        if(document.getElementById("HappytalkIframe")) {
          if (ishappytalk) {
            document.getElementById("HappytalkIframe").style.display = "block";
          } else {
            document.getElementById("HappytalkIframe").style.display = "none";
          }
        }
      });
    },
  },
};
</script>
<style>
</style>