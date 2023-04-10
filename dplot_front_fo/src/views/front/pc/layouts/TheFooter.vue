<template>
  <footer class="the-footer new_footer dplot_v2">
    <div class="container">
      <div class="container-inner">
        <div class="new_footer_col">
          <p class="footer_title">(주)다다엠앤씨</p>
          <div class="detail-box">
              <p class="detail-p">
                대표이사 : 서승원<br />
                주소 : (04377) 서울특별시 용산구 한강대로 23길 55, 아이파크몰 6층<br />
                통신판매업신고번호 : 2021-서울용산-0161 | 사업자등록번호 : 451-81-02355   <a style="color:#000; text-decoration: underline;" href="http://www.ftc.go.kr/bizCommPop.do?wrkr_no=4518102355">사업자확인 ></a><br />                
                개인정보관리책임자 : 박성진(james.park@cj.net)<br />
                고객센터 : 1666-3642 | 이메일 : cs@dplot.co.kr
              </p>
              <p class="detail-copy atten-new">COPYRIGHT © DADA M&C.  ALL RIGHTS RESERVED.</p>
            </div>
        </div>
        <div class="new_footer_col jcc">
          <ul class="footer_inner">
            <li>
              <a href="https://dadamnc.net/" target="_blank">회사소개</a>
            </li>
            <li>
              <a href="#" @click.prevent="openPersonalModal()">개인정보처리방침</a>
            </li>
            <li>
              <a href="javascript:void(0);"  @click="openTermsModal()">이용약관</a>
            </li>
            <li v-if="!$store.state.isLogin">
              <a href="#" @click.prevent="empSignUp">임직원 회원가입</a>
            </li>
          </ul>
          <ul class="footer_inner">
            <li>
              <router-link to="/cs/main">고객센터</router-link>
            </li>
            <li>
              <router-link to="/service/intro">정품보증</router-link>
            </li>
            <li>
              <router-link to="/cs/store-inquiry">입점/제휴문의</router-link>
            </li>
            <li>
              <router-link to="/service">About <span style="position:relative; top: -3px; margin-left: 5px;">
                <img
                  src="@/assets/common/icon/dplot.png"
                  alt="logo"
                />
              </span></router-link>
            </li>
            <li class="sns-icon-group">
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
            </li>
          </ul>
        </div>
      </div>
    </div>
  </footer>
</template>
<style scoped>
  .the-footer .footer-ul li:nth-last-child(2){margin-bottom:30px;}
</style>

<script>
import NewsLetter from "@views.pc/etc/popup/NewsLetter.vue";
import Terms from "@views/front/common/components/ui/modal/Terms.vue";
import KmcCert from '@views.mobile/member/KmcCert.js'
import Personal from "@views.mobile/etc/popup/Personal.vue";

export default {
  mixins : [KmcCert],
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
    };
  },
  methods: {
    openModal() {
      this.$eventBus.$emit("showModal", NewsLetter, "newsLetterModal");
    },
    redirect(url) {
      window.open(url, "_blank");
    },
    /******************
     * 이용약관 모달 오픈
     *********************/
    openTermsModal() {
      this.$util.debug("openModal ...");
      this.$eventBus.$emit("showModal", Terms, "Terms", {list: ["TRT001", "TRT002", "TRT003", "TRT004"], termstype:"TRT001"}, (param) => {});
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
    /********************************
     * 로그아웃
     ********************************/
    logout() {
      this.$http.post("/member/logout", {}).then((result) => {
        if (result.statusCode == 200) {
          // 리다이렉트 패스 삭제
          this.$storage.removeSessionStorage("redirectPath");
          // 카트삭제
          this.$store.commit("cartList", []);
          // 메인페이지 이동
          this.$router.replace({ name: "shop", params:{init:true}});
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
  },
};
</script>
