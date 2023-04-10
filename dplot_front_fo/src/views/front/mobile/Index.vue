<template>
  <div class="wrap">
    <template v-if="!onSubPage()">
      <!-- Todo: (개발) 0418 헤더 로고, 아이콘 색상 상태값 추가 -->
      <template v-if="$route.name !== 'shop'">
        <the-header
          v-if="showHeader()"
          :class="{
            show: isAppBarShow,
            hide: isAppBarShow === false,
          }"
          :is-black="isBlack"
        />
      </template>
      <template v-else>
        <the-header
          v-if="showHeader()"
          :class="{
            show: isAppBarShow || $route.name === 'shop',
            hide: isAppBarShow === false,
          }"
          :is-black="false"
        />
      </template>
      <!-- // (개발) 0418 헤더 로고, 아이콘 색상 상태값 추가 -->
    </template>
    <template v-else>
      <template
        v-if="
          $route.name !== 'search' &&
          !($route.path.indexOf('/search/result') > -1)
        "
      >
        <the-sub-header
          v-if="showSubHeader()"
          @prev="historyBack"
          :option="this.$store.state.subHeaderOption"
        />
      </template>
      <template v-else>
        <header class="the-header search-header on">
          <div class="the-header__inner">
            <i class="the-header__prev" @click="historyBack">
              <img
                src="@assets.mobile/img/icon/icon-prev-white-20px.svg"
                alt="이전으로"
              />
            </i>
            <div class="search-container">
              <search-relation
                v-model="searchInput"
                placeholder="검색어를 입력해주세요"
                @input="getData"
                @select="setSearch"
                type="search"
                ref="search"
              />
            </div>
          </div>
        </header>
      </template>
    </template>
    <div :class="{ 'sub-page': onSubPage() }">
      <!-- Todo: (개발) 0418 슬라이드 상태값 바인딩 -->
      <router-view @load="getMainBannerInfo" :key="$route.fullPath" />
      <!-- // (개발) 0418 슬라이드 상태값 바인딩 -->
    </div>
    <the-app-bar
      v-if="showAppBar()"
      :class="{
        show: isAppBarShow || hideAppBar(),
        hide: isAppBarShow === false && hideAppBar(),
      }"
      :is-sub-page="onSubPage()"
      @prev="historyBack"
    />
    <main-popup
      v-if="isMainPopup && showPopup.includes($route.name)"
      :thumbnailData="thumbnailData"
      @close="isMainPopup = false"
    />
    <!-- 앱 유도 모달 -->
    <div
      class="mobile-app-modal"
      v-if="isMobileApp"
    >
      <div class="mobile-app__contents">
        <figure class="mobile-app__figure">
          <img src="@assets.mobile/img/app-icon.png" alt="앱 아이콘" />
        </figure>
        <p class="dp-p text-gray-888 mobile-app__info">
          D.PLOT 앱에서 편리하게 쇼핑하기
        </p>

        <div class="">
          <b-button
            class="dp-btn text-white btn-h44"
            variant="gray-800"
            squared
            @click="openApp()"
          >
            <span>D.PLOT 앱으로 보기</span>
          </b-button>
          <span
            class="dp-p-sm text-gray-700 mobile-app__text"
            @click="closeAppModal()"
            >괜찮아요. 모바일 웹으로 볼게요</span
          >
        </div>
      </div>
    </div>
    <the-footer v-if="showFooter()" />
    <component
      :key="skey + 'm'"
      :is="modalInfo.comp"
      :id="modalInfo.id"
      :param="modalInfo.param"
      :fnConfirm="modalInfo.fnConfirm"
      :fnCancel="modalInfo.fnCancel"
    />
    <component
      :key="ckey + 'm'"
      :is="alertInfo.comp"
      :title="alertInfo.title"
      :content="alertInfo.content"
      :button1="alertInfo.button1"
      :button2="alertInfo.button2"
      :fnConfirm="alertInfo.fnConfirm"
      :fnCancel="alertInfo.fnCancel"
    />
    <component v-bind:is="modalComp" />
  </div>
</template>


<style scoped lang="scss">
.sub-page {
  padding-top: 50px;
}

.mobile-app-modal {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 4000;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
  &:before {
    position: absolute;
    top: 0;
    left: 0;
    content: "";
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.7);
  }
}
.mobile-app__contents {
  position: relative;
  width: 100%;
  max-width: 360px;
  padding: 30px 20px;
  background: #fff;
  text-align: center;
  .mobile-app__figure {
    width: 64px;
    margin: 0 auto 30px auto;
    img {
      width: 100%;
    }
  }
  .mobile-app__info {
    margin-bottom: 30px;
  }
  .mobile-app__text {
    display: inline-block;
    font-size: 14px;
    letter-spacing: -0.48px;
    text-decoration: underline;
    margin-top: 16px;
  }
}
</style>

<script>
import TheHeader from "@views.mobile/layouts/TheHeader";
import TheSubHeader from "@views.mobile/layouts/TheSubHeader";
import TheFooter from "@views.mobile/layouts/TheFooter";
import TheAppBar from "@views.mobile/layouts/TheAppBar";
import Alert from "@views.common/components/ui/modal/Alert";
import Confirm from "@views.common/components/ui/modal/Confirm";

export default {
  data() {
    return {
      platform: window.sessionStorage.getItem("platform"),
      isMobileApp: false,
      browser: "",
      tempValue: "",
      ckey: 0,
      skey: 0,
      modalComp: null,
      // 메인 배경색에 따라 헤더 아이콘 색상 변경
      isBlack: true,
      // 메인팝업
      isMainPopup: false,
      isMobileOpen: false,
      lastScrollTop: 0,
      isAppBarShow: null,
      // 메인팝업 리스트
      thumbnailData: [],
      // 앱 바를 숨겨야 하는 페이지
      hideAppBars: ["shop-detail", "cart", "order"],
      // 헤더를 숨겨야 하는 페이지
      hideHeader: ["app-access", "etc-news-letter", "etc-error"],
      // 푸터를 숨겨야 하는 페이지
      hideFooter: [
        "app-access",
        "app-setting",
        "etc-subscribe",
        "etc-news-letter",
        "etc-error",
        "order",
      ],
      // 서브 페이지가 아닌 페이지
      isNotSubPage: [
        "main",
        "shop",
        "app-access",
        "etc-news-letter",
        "etc-error",
      ],
      searchInput: "",
      // 메인 팝업이 보여야하는 페이지
      showPopup: ["main", "shop"],
      modalInfo: {
        comp: null,
        id: null,
        param: null,
        fnConfirm: () => {},
        fnCancel: () => {},
      },
      alertInfo: {
        comp: null,
        title: null,
        content: null,
        button1: null,
        button2: null,
        fnConfirm: () => {},
        fnCancel: () => {},
      },
    };
  },
  components: {
    TheHeader,
    TheSubHeader,
    TheAppBar,
    TheFooter,
  },
  beforeCreate() {
    // this.$http.post('/mz/popup', {})
    // .then(result => {
    //     if(result.statusCode === 200) {
    //         let data = result.data;
    //         this.thumbnailData = data.popuplist;
    //         if(this.thumbnailData.length === 0) {
    //           this.isMainPopup = false;
    //         } else {
    //           this.isMainPopup = true;
    //         }
    //     }
    // })
  },
  mounted() {
    this.browser = this.$util.browser();
    /**********************
     * 앱으로 보기 유도 팝업 SESSION 관리
     ********************/
    if (window.sessionStorage.getItem("platform") == "MAC002" && this.$util.isNull(this.$cookies.get("APP_POP_CLOSE_ONEDAY"))) {
      //this.isMobileApp = true;
    } else {
      this.isMobileApp = false;
    }

    window.addEventListener("scroll", this.detectScroll);
    //this.$toast.open('테스트입니다.');
    //Native 메시지 수신
    // if (window.ReactNativeWebView) {
    //   window.document.addEventListener('message', this.receiveMessage, false);
    // }
    if (window.ReactNativeWebView) {
      if (this.$util.browser() == "Safari") {
        window.addEventListener("message", this.receiveMessage, false);
      } else {
        document.addEventListener("message", this.receiveMessage, false);
      }
    }

    this.$eventBus.$on("scrollTo", (name) => {
      const savedPosition = this.$store.state.savedPosition[name];
      if (!this.$util.isEmpty(savedPosition)) {
        window.scrollTo({
          top: savedPosition.y,
          left: 0,
          //behavior: 'smooth'
        });
      }
    });

    /****************************
     * 메인 팝업 이벤트 버스처리
     *******************************/
    this.$eventBus.$on("popup", (data, check) => {
      this.thumbnailData = data;
      this.isMainPopup = check;
    });

    //Confirm 모달창 Show
    this.$eventBus.$on(
      "confirm",
      (title, content, confirmFn, cancelFn, button1, button2) => {
        this.ckey = Date.now();
        this.alertInfo.comp = Confirm;

        this.alertInfo.title = title ? title : "확인";
        this.alertInfo.content = content ? content : "확인";
        this.alertInfo.button1 = button1 ? button1 : "확인";
        this.alertInfo.button2 = button2 ? button2 : "닫기";

        if (confirmFn) {
          this.alertInfo.fnConfirm = confirmFn;
        } else {
          this.alertInfo.fnConfirm = () => {};
        }
        if (cancelFn) {
          this.alertInfo.fnCancel = cancelFn;
        } else {
          this.alertInfo.fnCancel = () => {};
        }
        this.$nextTick(function () {
          this.$bvModal.show("confirmModal");
        });
      }
    );

    // Alert 모달창 Show
    this.$eventBus.$on("alert", (title, content, confirmFn, button1) => {
      this.ckey = Date.now();
      this.alertInfo.comp = Alert;

      this.alertInfo.title = title ? title : "확인";
      this.alertInfo.content = content ? content : "확인";
      this.alertInfo.button1 = button1 ? button1 : "확인";

      if (confirmFn) {
        this.alertInfo.fnConfirm = confirmFn;
      } else {
        this.alertInfo.fnConfirm = () => {};
      }
      this.$nextTick(function () {
        this.$bvModal.show("alertModal");
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
          cancelFn: cancelFn,
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
    this.$eventBus.$on("showModal", (comp, id, param, confirm, cancel) => {
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
        this.modalInfo.fnConfirm = () => {};
      }
      if (cancel) {
        this.modalInfo.fnCancel = cancel;
      } else {
        this.modalInfo.fnCancel = () => {};
      }
      this.$nextTick(function () {
        this.$bvModal.show(id);
      });
    });

    //모달닫기
    this.$eventBus.$on("hideModal", () => {
      this.$bvModal.hide(this.modalInfo.id);
    });

    //세션스토리지 장바구니 저장
    this.$eventBus.$on("saveCart", (callback) => {
      let items = this.$storage.getLocalStorage("CART_ITEMS");
      if (this.$util.isNull(items)) {
        items = [];
      }

      if (items.length > 0) {
        this.$front.saveCart(this, items, () => {
          if (callback) {
            callback();
          }
        });
      } else {
        //this.$front.getCartList(this);
        this.$front.getCartGoodsCount(this);
        if (callback) {
          callback();
        }
      }
    });

    var evt = document.createEvent("HTMLEvents");
    evt.initEvent("resize", true, false);
    window.dispatchEvent(evt);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.detectScroll);
    if (window.ReactNativeWebView) {
      if (this.$util.browser() == "Safari") {
        window.removeEventListner("message");
      } else {
        document.removeEventListner("message");
      }
    }
    document.body.style.overflow = "auto";
  },
  methods: {
    getMainBannerInfo(val) {
      this.tempValue = val;
      if (val === "#ffffff") {
        this.isBlack = true;
      } else {
        this.isBlack = false;
      }
    },
    historyBack() {
      this.$router.go(-1);
    },
    // 모바일 서브 페이지 판별
    onSubPage() {
      return !this.isNotSubPage.includes(this.$route.name);
    },
    // 모바일 헤더 페이지 판별
    showHeader() {
      let onHeader = false;
      if (
        this.$route.name.indexOf("kmcCert") > -1 ||
        this.$route.name.indexOf("naver") > -1 ||
        this.$route.name.indexOf("kakao") > -1 ||
        this.$route.name.indexOf("toss") > -1
      ) {
        onHeader = false;
      } else {
        onHeader = true;
      }
      return !this.hideHeader.includes(this.$route.name) && onHeader;
    },
    // 모바일 서브헤더 페이지 판별
    showSubHeader() {
      let onSubHeader = false;
      if (
        this.$route.name.indexOf("kmcCert") > -1 ||
        this.$route.name.indexOf("naver") > -1 ||
        this.$route.name.indexOf("kakao") > -1 ||
        this.$route.name.indexOf("toss") > -1
      ) {
        onSubHeader = false;
      } else {
        onSubHeader = true;
      }
      return !this.hideHeader.includes(this.$route.name) && onSubHeader;
    },
    // 모바일 앱바 핸들러
    showAppBar() {
      let onAppBar = false;
      if (
        this.$route.name.indexOf("kmcCert") > -1 ||
        this.$route.name.indexOf("naver") > -1 ||
        this.$route.name.indexOf("kakao") > -1 ||
        this.$route.name.indexOf("toss") > -1
      ) {
        onAppBar = false;
      } else {
        onAppBar = true;
      }
      //return !this.hideAppBars.includes(this.$route.name) && onAppBar;
      return onAppBar;
    },
    hideAppBar() {
      return !this.hideAppBars.includes(this.$route.name);
    },
    //모바일 푸터 페이지 판별
    showFooter() {
      let onFooter = false;
      if (
        this.$route.name.indexOf("kmcCert") > -1 ||
        this.$route.name.indexOf("naver") > -1 ||
        this.$route.name.indexOf("kakao") > -1 ||
        this.$route.name.indexOf("toss") > -1 ||
        this.$route.name == "search"
      ) {
        onFooter = false;
      } else {
        onFooter = true;
      }
      return !this.hideFooter.includes(this.$route.name) && onFooter;
    },
    //메인팝업보이는 페이지
    // showMainPopup() {
    //   this.$http.post('/mz/popup', {})
    //   .then(result => {
    //       if(result.statusCode === 200) {
    //           let data = result.data;
    //           this.thumbnailData = data.popuplist;
    //           if(this.thumbnailData.length === 0) {
    //             this.isMainPopup = false;
    //           } else {
    //             this.isMainPopup = true;
    //             return this.showPopup.includes(this.$route.name);
    //           }
    //       }
    //   })
    // },
    detectScroll() {
      let scrollY = window.scrollY;
      if (scrollY === 0) {
        // 최상단 도착할 경우 AppBar 비활성화
        this.isAppBarShow = null;
        if (this.tempValue === "#ffffff") {
          this.isBlack = true;
        } else {
          this.isBlack = false;
        }
      } else if (scrollY > this.lastScrollTop) {
        // 스크롤 내릴 때 AppBar 활성화
        this.isAppBarShow = false;
        if (this.tempValue === "#ffffff") {
          this.isBlack = true;
        } else {
          this.isBlack = false;
        }
      } else {
        // 스크롤 올릴 때 AppBar 활성화
        this.isAppBarShow = true;
        this.isBlack = false;
      }
      this.lastScrollTop = scrollY;
    },
    getData(val) {},
    //Native 메시지 수신
    receiveMessage(event) {
      this.$util.debug("message: " + event.data); // 보낸 메시지
      const { data, type } = JSON.parse(event.data);
      switch (type) {
        case "BACK":
          //this.$router.go(-1);
          break;
        case "LOGIN": //Login 페이지이동
          this.$router.push("/member/login");
          break;
        case "LOGOUT": //Logout 처리
          this.$http.post("/member/logout", {}).then((result) => {
            if (result.statusCode == 200) {
              this.$router.replace({ name: "shop" });
            }
          });
          break;
        case "PREF_GET": //Preference 조회
          this.$http.post("/member/app/info", {}).then((result) => {
            this.$front.sendNativeMessage({
              type: "PREF_GET",
              data: {
                isBio: result.data.members.isbio,
                isLogin: result.data.members.islogin,
                isAlarm: result.data.members.isalarm,
                appVerion: result.data.members.appverion,
                name: result.data.members.name,
                userid: result.data.members.userid,
                cartCnt: this.$store.state.cartList.length,
              },
            });
          });
          break;
        case "PUSH_RECEIVE": //PUSH수신
          break;
        case "BIO_REQ": //BIO설정 ON
          if (this.$route.name == "mypage-info-confirm") {
            if (!this.$util.isNull(data.bio)) {
              this.$http.post("/member/getEncBio", {}).then((result) => {
                let bio = result.statusCode == 200 ? result.data.encbio : "";
                if (data.bio === bio) {
                  this.$router.replace("/mypage/info-modify");
                } else {
                  this.$eventBus.$emit(
                    "alert",
                    "알림",
                    "지문 정보가 일치하지 않습니다."
                  );
                }
              });
            } else {
              this.$http
                .post("/member/bio", { encbio: null })
                .then((result) => {
                  if (result.statusCode == 200) {
                    this.$util.debug("바이오 정보 초기화 성공.");
                  } else {
                    this.$util.debug("바이오 정보 초기화 실패.");
                  }
                });
            }
          } else if (this.$route.name == "mypage-info-modify") {
            if (this.$util.isNull(data.bio)) {
              this.$store.commit("isBio", false);
            } else {
              this.$store.commit("isBio", true);
            }
          } else {
            if (this.$util.isNull(data.bio)) {
              this.$eventBus.$emit(
                "alert",
                "알림",
                "생체정보가 등록되어있지 않습니다. 로그인 이후 생체정보를 등록해주세요."
              );
            } else {
              this.$front.otherLogin(this, data.userid, "bio", data.bio, "");
            }
          }
          break;
        case "BIO_ON": //BIO설정 ON
          this.$http.post("/member/getEncBio", {}).then((result) => {
            this.$front.sendNativeMessage({
              type: "BIO_ON",
              data: {
                result: result.statusCode == 200 ? true : false,
                bio: result.statusCode == 200 ? result.data.encbio : "",
                message: result.message,
              },
            });
          });
          break;
        case "BIO_COMPLETE": //BIO설정 완료
          this.$http
            .post("/member/bio", { encbio: data.bio })
            .then((result) => {
              this.$front.sendNativeMessage({
                type: "BIO_COMPLETE",
                data: {
                  result: result.statusCode == 200 ? true : false,
                  bio: result.statusCode == 200 ? result.data.encbio : "",
                  message: result.message,
                },
              });
              this.$store.commit("isBio", true);
            });
          break;
        case "BIO_OFF": //BIO설정 OFF
          this.$http.post("/member/bio", { encbio: null }).then((result) => {
            //alert native에서 노출하기로 결정(2022-08-18)
            if (result.statusCode == 200) {
              //this.$eventBus.$emit("alert","알림","생체정보 로그인이 해제되었습니다.",()=>{this.$store.commit("isBio", false)});
              this.$store.commit("isBio", false);
            } else {
              //this.$eventBus.$emit("alert","알림","생체정보 로그인 해제에 실패했습니다.",()=>{this.$store.commit("isBio", true)});
              this.$store.commit("isBio", true);
            }
          });
          break;
        case "PUSH_ON": //PUSH 설정ON
          this.$http.post("/member/push", { isadpush: "T" }).then((result) => {
            if (result.statusCode == 200) {
              this.$toast.clear();
              this.$toast.open(
                "광고알림 수신동의 처리되었습니다.</br>다다앰엔씨 " +
                  this.$util.getDate(".")
              );
            }
            this.$front.sendNativeMessage({
              type: "PUSH_ON",
              data: {
                result: result.statusCode == 200 ? true : false,
                message: result.message,
              },
            });
          });
          break;
        case "PUSH_OFF": //PUSH 설정OFF
          this.$http.post("/member/push", { isadpush: "F" }).then((result) => {
            if (result.statusCode == 200) {
              this.$toast.clear();
              this.$toast.open(
                "광고알림 수신거부 처리되었습니다.</br>다다앰엔씨 " +
                  this.$util.getDate(".")
              );
            }
            this.$front.sendNativeMessage({
              type: "PUSH_OFF",
              data: {
                result: result.statusCode == 200 ? true : false,
                message: result.message,
              },
            });
          });
          break;
        case "CART": //카트이동
          this.$router.push("/cart");
          break;
        case "SEARCH": //검색페이지이동
          this.$router.push("/search");
          break;
        default:
          break;
      }
    },
    /**************************
     * 검색화면일 경우 검색처리
     **************************/
    setSearch(content) {
      this.searchInput = content;
      this.$refs.search.searchStart(content);
    },
    /**************************
     * 검색어 삭제처리
     **************************/
    delSearch() {
      this.$refs.search.clearValue();
    },
    /**************************
     * 앱으로 보기 유도 팝업 닫기 처리
     **************************/
    closeAppModal() {
      let now = new Date();
      now.setHours(24, 0, 0);
      this.$cookies.set("APP_POP_CLOSE_ONEDAY", 1, now.toUTCString());
      this.isMobileApp = false;
    },
    /**************************
     * 앱으로 보기 버튼 이벤트
     **************************/
    openApp(){
      if (this.browser == "Safari") {
        //alert("ios");
        this.$util.debug("ios");
      }else{
        //alert("android");
         this.$util.debug("android");
      }
    }
  },
  watch: {
    $route(val) {
      this.$eventBus.$emit("hideModal");
      document.body.style.overflow = "auto";
    },
    isMainPopup(value) {
      if (value) {
        document.body.style.overflow = "hidden";
      } else {
        document.body.style.overflow = "auto";
      }
    },
  },
};
</script>
