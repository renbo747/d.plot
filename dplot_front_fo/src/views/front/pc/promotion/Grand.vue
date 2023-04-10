<template>
    <main>
        <img
            :src=" $store.state.platform == 'MAC001' ? `http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/grand_open_main_visual.png` : `http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/grand_open_main_visual_mo.png`"
            alt=""
        />
        <content-tab :menus="menus" :maxWidth="968"></content-tab>
        <section id="prom01">
            <section-title :data="{title:'스크래치 이벤트', desc:'발견하는 즐거움, 이미지를 터치하면 숨겨진 혜택이 나타나요.<br>(하루에 5번까지 참여 가능)', idx:'1'}" />
            <div class="img_box">
                <img @click="goScratch" src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/event01.png" alt="">
            </div>
        </section>
        <section id="prom02">
            <router-link :to = "{name:'raffle'}">
                <section-title :data="{title:'릴레이 래플 이벤트', desc:'꽝 없는 100% 당첨!<br>갖고 싶었던 아이코닉한 상품에 응모하세요.', idx:'2', descType:'leftAlign', gotoBtn: true}" />
                <div class="img_box">
                    <img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/event02.png" alt="">
                </div>
            </router-link>
        </section>
        <section id="prom03">
            <router-link :to = "{name:'welcome'}">
                <section-title :data="{title:'9월의 신규 혜택.ZIP', desc:'브랜드 셀렉트숍, 디플롯 가입하고<br>웰컴쿠폰팩과 추가 혜택까지 받아보세요.', idx:'3', descType:'leftAlign', gotoBtn: true}" />
                <div class="img_box">
                    <img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/event03.png" alt="">
                </div>
            </router-link>
        </section>
        <section id="prom04">
            <section-title :data="{title:'쇼핑 기획전', desc:'디플롯이 선정한 해외 디자인 브랜드와<br>국내 라이징 브랜드를 특별한 가격으로 만나보세요', idx:'4', descType:'leftAlign'}" />
            <div class="grand_brand_box">
                <router-link :to="{name:'magazine-brand-detail' ,params:{bid:19, init:true}}">
                    <div class="img_box">
                        <img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/grand_brand01.png" alt="artemide" />
                    </div>
                </router-link>
                <router-link :to="{name:'magazine-brand-detail' ,params:{bid:48, init:true}}">
                    <div class="img_box">
                        <img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/grand_brand02.png" alt="alessi" />
                    </div>
                </router-link>
                <router-link :to="{name:'magazine-brand-detail' ,params:{bid:78, init:true}}">
                    <div class="img_box">
                        <img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/grand_brand03.png" alt="fatboy" />
                    </div>
                </router-link>
                <router-link :to="{name:'magazine-brand-detail' ,params:{bid:107, init:true}}">
                    <div class="img_box">
                        <img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/grand/pc/grand_brand04.png" alt="mujagi" />
                    </div>
                </router-link>
            </div>
        </section>
        <notice-section :data="notice" />
    </main>
</template>

<script>
    import InnerContentTabVue from './common/components/InnerContentTab.vue';
    import NoticeSectionVue from './common/components/NoticeSection.vue';
    import SectionTitleVue from './common/components/SectionTitle.vue';
    import ScratchVue from './grand_components/Scratch.vue';

    export default {
        components: {
            'notice-section' : NoticeSectionVue,
            'content-tab': InnerContentTabVue,
            'section-title' : SectionTitleVue,
        },
        data() {
            return {
                menus: ['스크래치 이벤트', '래플이벤트', '신규 회원 혜택', '쇼핑 기획전'],
                notice: {
                    title: '이벤트 유의사항',
                    top_desc: `
                        응모 방법 : 스크래치 이벤트 이미지 터치 후 응모하기 버튼 클릭<br>
                        응모 기간 : 2022.09.15~09.31<br>
                        응모 횟수 : 계정당 일 5회까지 참여 가능 / 매일 오전 11시에 응모 횟수 리셋<br>
                    `,
                    list: [
                        '혜택 당첨자에게는 마이페이지를 통해 자동으로 혜택이 지급됩니다.',
                        '본 스크래치 이벤트는 무작위 추첨제로 진행됩니다.',
                        '비정상적인 방법을 통해 이벤트에 참여한 것으로 판단될 경우, 당첨 대상에서 제외될 수 있습니다.',
                        '본 이벤트 관련 문의는 고객센터 또는 채팅 상담으로 문의 부탁드립니다.',
                        '해당 이벤트를 참여하신 경우 마케팅 수신 활용에 동의하신 것으로 간주됩니다.',
                    ]
                },
            }
        },
        methods : {
            goScratch() {
                alert("이벤트가 종료되었습니다. 감사합니다.");
				/*if (!this.$store.state.isLogin) {
					this.$eventBus.$emit('confirm', '로그인', '로그인이 필요합니다. 로그인 하시겠습니까?',()=>{
						this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, query : this.$route.query});
						this.$router.push({name : 'member-login'});
					});
				} else {
					this.$eventBus.$emit('showModal', ScratchVue, "scratchModal");
				}*/
			},
        }
    }
</script>

<style scoped>
    main {max-width:1280px; margin:0 auto;}
    main img {max-width:100%;}
    section {min-height:70vh; padding-bottom: 270px; }
    .img_box {display: flex; justify-content: center; align-items: center;}
    #prom01 {background:#ffccff;}
    #prom02 {background:#fff;}
    #prom03 {background:#fff;}
    #prom04 {background:#fff;}
    #prom01 .img_box,
    #prom02 .img_box,
    #prom03 .img_box {width: 100%; display: flex; justify-content: center; align-items: flex-start;}
    #prom01 .img_box img,
    #prom02 .img_box img,
    #prom03 .img_box img {cursor:pointer;}
    #prom01 .img_box img {min-height:980px;}
    #prom02 .img_box img,
    #prom03 .img_box img {min-height:810px;}
    .grand_brand_box {max-width: 720px; display: flex; flex-wrap: wrap; justify-content: space-between; margin:0 auto;}
    .grand_brand_box .img_box {width: 355px; height: 400px; margin-bottom: 10px;}
    .grand_brand_box .img_box img {max-width:100%; width: 100%;}

    @media (max-width: 500px){
        main {max-width:100%; margin:0 auto;}
        main img {max-width:100%;}
        section {min-height:100vh; padding-bottom: 135px; }
        .img_box {display: flex; justify-content: center; align-items: center;}
        #prom01 {background:#ffccff;}
        #prom02 {background:#fff;}
        #prom03 {background:#fff;}
        #prom04 {background:#fff;}
        #prom01 .img_box,
        #prom02 .img_box,
        #prom03 .img_box {width: 90%; display: flex; justify-content: center; align-items: flex-start; margin:0 auto;}
        #prom01 .img_box img,
        #prom02 .img_box img,
        #prom03 .img_box img {cursor:pointer;}
        #prom01 .img_box img {min-height:0;}
        #prom02 .img_box img,
        #prom03 .img_box img {min-height:0;}
        .grand_brand_box {max-width: 90%; display: flex; flex-wrap: wrap; justify-content: space-between; margin:0 auto;}
        .grand_brand_box > a {display:inline-block; width: 48%;}
        .grand_brand_box .img_box {width: 100%; margin-bottom: 10px; height:auto;}
        .grand_brand_box .img_box img {max-width:100%; width: 100%;}
    }
</style>