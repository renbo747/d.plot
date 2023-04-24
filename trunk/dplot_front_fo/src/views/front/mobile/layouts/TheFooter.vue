<template>
  <footer class="the-footer">
    <div class="footer-top">
      <ul>
        <li>
          <router-link to="/cs/main">고객센터</router-link>
        </li>
        <li>
          <router-link to="/service/intro">정품보증</router-link>
        </li>
        <li>
          <a href="#" @click.prevent="openStoreInquiryModal()">입점/제휴문의</a>
        </li>
        <li class="atten-new">
          <router-link to="/service">About D.PLOT</router-link>
        </li>
      </ul>
    </div>
    <div class="footer-bottom">
      <!-- 푸터 하단 정보 -->
      <details class="footer-detail">
        <summary class="footer-summary">
          <span>(주) 다다엠앤씨 사업자정보</span>
          <img src="@/assets/common/icon/icon-arrow-down-black-20px.svg" />
        </summary>

        <div class="detail-box">
          <p class="detail-p">
            대표이사 : 서승원<br />
            사업자등록번호 : 451-81-02355
            <a
              style="color: black"
              href="http://www.ftc.go.kr/bizCommPop.do?wrkr_no=4518102355"
              >사업자확인 ></a
            ><br />
            주소 : (04377) 서울특별시 용산구 한강대로 23길 55,<br />
            <span>아이파크몰 6층</span><br />
            통신판매업신고번호 : 2021-서울용산-0161<br />
            개인정보관리책임자 : 박성진(james.park@cj.net)<br />
            고객센터 : 1666-3642<br />
            이메일 : cs@dplot.co.kr
          </p>

        </div>
      </details>
      <div class="default_infor">
        <ul>
          <li><a href="https://dadamnc.net/" target="_blank">회사소개</a></li>
          <li><a href="#" @click.prevent="openPersonalModal()">개인정보처리방침</a></li>
          <li><a href="#" @click.prevent="openTermsModal()">이용약관</a></li>
          <li v-if="!$store.state.isLogin">
            <a href="#" @click.prevent="empSignUp">임직원 회원가입</a>
          </li>
        </ul>
      </div>
      <div class="sns-icon-group">
        <a
          href="https://dplotpop.com"
          target="_blank"
          class="sns-icon"
        >
          <img
            src="@/assets/common/icon/pop.svg"
            alt="pop"
          />
        </a>
        <a
          href="https://www.instagram.com/dplot.official/"
          target="_blank"
          class="sns-icon"
        >
          <img
            src="@/assets/common/icon/insta.svg"
            alt="인스타그램"
          />
        </a>
        <a
          href="https://www.youtube.com/channel/UCCV8jAXzoH_xanagR-f4amQ"
          target="_blank"
          class="sns-icon"
        >
          <img
            src="@/assets/common/icon/youtube.svg"
            alt="유튜브"
          />
        </a>
        <!--
        <a
          href="https://www.instagram.com/dplot.official/"
          target="_blank"
          class="sns-icon"
        >
          <img
            src="@/assets/common/icon/icon-instagram-black-30px.svg"
            alt="인스타그램"
          />
        </a> -->
                <!-- <a
          href="https://www.facebook.com/profile.php?id=100075912891379"
          target="_blank"
          class="sns-icon"
        >
          <img
            src="@/assets/common/icon/icon-facebook-black-30px.svg"
            alt="페이스북"
          />
        </a> -->
        <!-- <a href="#" @click.prevent="shareKakao()" class="sns-icon">
          <img
            src="@/assets/common/icon/icon-kakaotalk-black-30px.svg"
            alt="카카오톡"
          />
        </a> -->
      </div>
      <span class="detail-copy atten-new"
        >COPYRIGHT © DADA M&C.&nbsp;&nbsp;ALL RIGHTS RESERVED.</span
      >      
      <!-- <div class="footer-top">
        <figure class="footer-logo">
          <img src="@/assets/common/icon/logo-footer-white.svg" alt="하단 로고" />
        </figure>

        <button class="btn-footer" @click.stop="$router.push('/etc/subscribe')">
          <span>
            <img src="@/assets/common/icon/icon-news-letter-mail.svg" />
            <span>뉴스레터 구독</span>
          </span>

          <img src="@/assets/common/icon/icon-news-letter-arrow.svg" />
        </button>
      </div> -->

      <!-- 푸터 유틸리티 메뉴 -->
      <!-- <div class="footer-middle">
        <ul class="footer-ul list-style-none footer-li-style">
          <li v-if="!$store.state.isLogin">
            <router-link :to="{ name: 'member-login' }">로그인</router-link>
          </li>
          <li v-if="$store.state.isLogin">
            <a href="#" @click.prevent="logout()">로그아웃</a>
          </li>
          <li>
            <a href="https://dadamnc.net/" target="_blank">회사소개</a>
          </li>
          <li>
            <router-link to="/cs/main">고객센터</router-link>
          </li>
          <li>
            <a href="#" @click.prevent="openTermsModal()">이용약관</a>
          </li>
          <li>
            <a href="#" @click.prevent="openStoreInquiryModal()">입점/제휴문의</a>
          </li>
          <li>
            <a href="#" @click.prevent="openPersonalModal()">개인정보처리방침</a>
          </li>
          <li v-if="ismobile">
            <router-link to="#">앱 다운로드</router-link>
          </li>
          <li v-if="!$store.state.isLogin">
            <a href="#" @click.prevent="empSignUp">임직원 회원가입</a>
          </li>
        </ul>
      </div> -->
      <!-- 푸터 바로가기 버튼 -->
      <!-- <div class="footer-btn-area">
        <div
          v-show="isShortcut"
          class="shortcut-backdrop"
          @click="isShortcut = false"
        ></div>
        <div class="footer-shortcut-menu" v-show="isShortcut">
          <div
            v-for="(list, index) in shortcutMenu"
            class="shortcut-menu-list"
            :key="index"
          >
            <p class="shortcut-ctg">{{ list.ctg }}</p>
            <ul
              class="shortcut-ul list-style-none"
              style="list-style: none; padding-left: 10px"
            >
              <li
                v-for="(menu, index) in list.menu"
                :key="index"
                @click="redirect(menu.to)"
              >
                {{ menu.label }}
              </li>
            </ul>
          </div>
        </div>
        <div
          class="footer-shortcut"
          :class="{ open: isShortcut }"
          @click="isShortcut = !isShortcut"
        >
          <span>CJ그룹계열사 바로가기</span>
          <img src="@/assets/common/icon/icon-footer-arrow-down.svg" />
        </div>
      </div> -->

      <!-- 푸터 SNS 버튼 -->

    </div>
  </footer>
</template>

<script>
import Terms from "@views/front/common/components/ui/modal/Terms.vue";
import StoreInquiryModal from "@views.mobile/cs/popup/StoreInquiryModal.vue";
import Personal from "@views.mobile/etc/popup/Personal.vue";
import KmcCert from "@views.mobile/member/KmcCert.js";

export default {
  mixins: [KmcCert],
  data() {
    return {
      isShortcut: false,
      shortcutMenu: [
        {
          ctg: "CJ그룹",
          menu: [
            {
              label: "CJ주식회사",
              to: "http://www.cj.net/",
            },
          ],
        },
        {
          ctg: "식품 & 식품 서비스",
          menu: [
            {
              label: "CJ제일제당",
              to: "http://www.cj.co.kr",
            },
            {
              label: "CJ푸드빌",
              to: "https://www.cjfoodville.co.kr/",
            },
            {
              label: "CJ프레시웨이",
              to: "http://www.cjfreshway.com",
            },
          ],
        },
        {
          ctg: "생명공학",
          menu: [
            {
              label: "CJ제일제당 BIO사업부문",
              to: "https://www.cj.co.kr/kr/about/business/bio",
            },
            {
              label: "CJ Feed&Care",
              to: "https://www.cj.co.kr/kr/about/business/bio",
            },
          ],
        },
        {
          ctg: "물류 & 신유통",
          menu: [
            {
              label: "CJ대한통운",
              to: "http://www.cjlogistics.com",
            },
            {
              label: "CJ대한통운 건설부문",
              to: "http://www.cjenc.co.kr/kr/Default.asp",
            },
            {
              label: "CJ올리브영",
              to: "https://www.oliveyoung.co.kr",
            },
            {
              label: "CJ올리브네트웍스",
              to: "https://www.cjolivenetworks.co.kr/",
            },
            {
              label: "CJ ENM 커머스부문",
              to: "https://www.cjoshopping.com:9002/index.asp",
            },
          ],
        },
        {
          ctg: "엔터테인먼트 & 미디어",
          menu: [
            {
              label: "CJ ENM 엔터테인먼트부문",
              to: "https://www.cjem.net:433/main/?locale=ko",
            },
            {
              label: "CJ CGV",
              to: "http://corp.cgv.co.kr/",
            },
            /*{
              label: "CJ파워캐스트",
              to: "http://www.cjpowercast.com",
            },*/
          ],
        },
      ],
      ismobile: false,
    };
  },
  methods: {
    redirect(url) {
      window.open(url, "_blank");
    },
    /******************
     * 입점문의 모달 오픈
     *********************/
    openStoreInquiryModal() {
      this.$util.debug("openModal ...");
      this.$eventBus.$emit(
        "showModal",
        StoreInquiryModal,
        "StoreInquiryModal",
        {},
        (param) => {}
      );
    },
    /******************
     * 개인정보처리방침 모달 오픈
     *********************/
    openPersonalModal() {
      this.$util.debug("openModal ...");
      this.$eventBus.$emit(
        "showModal",
        Personal,
        "personalModal",
        {},
        (param) => {}
      );
    },
    /******************
     * 이용약관 모달 오픈
     *********************/
    openTermsModal() {
      this.$util.debug("openModal ...");
      this.$eventBus.$emit(
        "showModal",
        Terms,
        "Terms",
        { list: ["TRT001", "TRT002", "TRT003", "TRT004"], termstype: "TRT001" },
        (param) => {}
      );
    },
    /********************************
     * 로그아웃
     ********************************/
    logout() {
      this.$http.post("/member/logout", {}).then((result) => {
        if (result.statusCode == 200) {
          // 리다이렉트 패스 삭제
          this.$storage.removeSessionStorage("redirectPath");
          this.$storage.removeSessionStorage("name");
          // 카트삭제
          this.$store.commit("cartList", []);
          // 메인페이지 이동
          this.$router.replace({ name: "shop", params: { init: true } });
        } else {
          this.$eventBus.$emit("alert", "알림", result.message);
        }
      });
    },
  },
  mounted() {
    if (!Kakao.isInitialized()) {
      Kakao.init(process.env.VUE_APP_KAKAO_SCRIPT_KEY);
    }
    if (window.sessionStorage.getItem("platform") == "MAC002") {
      this.ismobile = true;
    } else {
      this.ismobile = false;
    }
  },
};
</script>
<style>
.the-footer .footer-ul li {
  margin-bottom: 0 !important;
  margin-top: 20px;
}
.the-footer .footer-ul li:nth-child(1),
.the-footer .footer-ul li:nth-child(2) {
  margin-top: 0;
}
</style>