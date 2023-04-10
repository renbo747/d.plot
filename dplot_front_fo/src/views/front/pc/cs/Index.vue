<template>
  <aside-layout :asideTitle="asideTitle" :asideData="asideData"/>
</template>

<script>
import AsideLayout from "@views.pc/layouts/AsideLayout";
export default {
  components: { AsideLayout },
  mounted() {
    if((this.$route.path).indexOf('mypage') == -1){
      this.asideData[0].subMenu = [];
      //공통코드 목록 불러오기
      let cmclassArr = ['FAQTYPE'];
      this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
          .then(result =>{
              for(var i=0; i<result.data.faqtype.length; i++){
                  var temp = {};
                  temp.title = result.data.faqtype[i].codename;
                  temp.to = '/cs/cs-faq/' + (i+1);
                  temp.isView = true;
                  this.asideData[0].subMenu.push(temp);
              }
          })
    }

    if((this.$route.path).indexOf('mypage') != -1){
      this.asideTitle = "마이페이지";
      this.asideData = [
        {
          id: 1,
          menu: "LIKE",
          to: "/mypage/like",
          subMenu: [
          ],
        },
        {
          id: 2,
          menu: "나의활동",
          to: "/mypage",
          subMenu: [
            {
              title: "회원정보",
              to: "/mypage/info-confirm",
              isView: true,
            },
            {
              title: "나의 문의",
              to: "/mypage/inquiry/index/inquiry",
              isView: true,
            },
            {
              title: "나의 리뷰",
              to: "/mypage/my-review/before",
              isView: true,
            },
            {
              title: "Message",
              to: "/mypage/message",
              isView: true,
            },
            {
              title: "재입고알림",
              to: "/mypage/restock",
              isView: true,
            },
          ],
        },
        {
          id: 3,
          menu: "쇼핑",
          to: {name:'mypage-order',params:{init:true}},
          subMenu: [
            {
              title: "주문/배송",
              to: {name:'mypage-order',params:{init:true}},
              isView: true,
            },
            {
              title: "취소/교환/반품",
              to: {name: 'mypage-claim', params: {init:true}},
              isView: true,
            },
            {
              title: "A/S전용 문의",
              to: {name:'mypage-as-apply-list',params:{init:true}},
              isView: true,
            },
          ],
        },
        {
          id: 4,
          menu: "혜택",
          to: "/mypage/coupon",
          subMenu: [
            {
              title: "쿠폰",
              to: "/mypage/coupon",
              isView: true,
            },
            {
              title: "D포인트",
              to: "/mypage/epoint",
              isView: true,
            },
            {
              title: "적립금",
              to: "/mypage/reward",
              isView: true,
            },
            {
              title: "임직원 적립금",
              to: "/mypage/employee-reward",
              isView: true,
            },
            {
              title: "친구초대",
              to: "/mypage/invitation",
              isView: true,
            },
          ],
        },
      ]
    }

    if((this.$route.path).indexOf('mypage') != -1){
      if (this.$store.state.membertype == "DMT003" || this.$store.state.membertype == "DMT004") {
        this.myPageAsideData[3].subMenu[3].isView = true;
      }else {
        this.myPageAsideData[3].subMenu[3].isView = false;
      }
    }        
  },    
  data() {
    return {
    asideTitle: "고객센터",
    asideData: [
      {
        id: 1,
        menu: "자주하는 질문",
        to: "/cs/cs-faq/1",
        isView: true,
        subMenu: [
          {
            title : "회원/멤버십",
            to: "/cs/cs-faq/1",
            isView: true,
          },
        ],
      },
      {
        id: 2,
        menu: "나의 문의내역",
        to: "/cs/inquiry/index",
        isView: true,
        subMenu: [
          {
            title: "1:1 문의",
            to: "/cs/inquiry/index/inquiry",
            isView: true,

          },
          {
            title: "상품 Q&A",
            to: "/cs/inquiry/index/qna",
            isView: true,
          },
          {
            title: "AS전용 문의",
            to: "/mypage/as/apply/list",
            isView: true,
          },
        ],
      },
      {
        id: 3,
        menu: "공지사항",
        to: {name:'cs-notice',params:{init:true}},
        isView: true,
        subMenu: [],
      },
      {
        id: 4,
        menu: "입점/제휴문의",
        to: "/cs/store-inquiry",
        isView: true,
        subMenu: [],
      },
    ],
    };
  },
  methods: {
   
  },
};
</script>
