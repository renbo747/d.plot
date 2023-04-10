<template>
    <div class="relay2_event">
        <div class="banner">
            <div class="img_box">
                <img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/mo_banner.jpg" alt="매주 화요일에 시작하는 6주간 릴레이 래플 이벤트 - 갖고 싶었던 아이코닉한 상품에 응모하세요">

            </div>
            <div class="text_box">
                <span class="tit">MEMBERS ONLY</span>
                <span class="period">22.11.15~22.12.26</span>
            </div>
        </div>

        <div class="section02">
            <div class="wrap_734">
                <div class="con_title"  v-if="raffleend">
                    <p>래플 이벤트가 끝났습니다</p>
                </div>
                
                <!-- {{rafflecontent[currentraffle].status}} -->

                
                <div v-if="rafflecontent[currentraffle].status=='T'">
                    <div class="con_title" >
                        <!--p>{{rafflecontent[currentraffle].month}}월 {{rafflecontent[currentraffle].week}}주차 래플 진행중</p-->
                        <p>{{numberToKorSeq(rafflecontent[currentraffle].rownum)}} 래플 진행중</p>
                    </div>
                    <CountDownVue v-bind:targetDate="rafflecontent[currentraffle].enddate"/>
                </div>

                <div v-if="rafflecontent[currentraffle].status=='F'">
                    <p class="con_title">래플 시작 일정</p>
                    <div class="count_date">
                        <p style="white-space: nowrap; display:flex; font-size:1.3rem; align-items: center; flex-wrap: nowrap; justify-content: center; line-height: 1;">
                            <span style="font-family: 'Mont'; font-size:2rem; text-stroke: 2px">{{rafflecontent[currentraffle].startdate[0]}}</span >년 
                            <span style="font-family: 'Mont'; font-size:2rem; text-stroke: 2px; margin-left:20px;">{{rafflecontent[currentraffle].startdate[1]}}</span >월 
                            <span style="font-family: 'Mont'; font-size:2rem; text-stroke: 2px; margin-left:20px;">{{rafflecontent[currentraffle].startdate[2]}}</span >일 
                            <span style="font-family: 'Mont'; font-size:2rem; text-stroke: 2px; margin-left:20px;">{{rafflecontent[currentraffle].startdate[3]}}</span >시
                        </p>
                        <!-- <p>{{rafflecontent[currentraffle].startdate[1]}}월 {{rafflecontent[currentraffle].startdate[2]}}일 ({{getKorDay(rafflecontent[currentraffle].startdate)}}) {{rafflecontent[currentraffle].startdate[3]}}:{{rafflecontent[currentraffle].startdate[4]}}</p> -->
                        <!-- <div class="timer_box">
                            <div class="time_con">
                                <div>  
                                    <p class="day count_text">{{rafflecontent[currentraffle].startdate[2]}}</p>
                                    <p class="text">DAY</p>
                                </div>
                            </div>
                            <div  class="time_con">
                                <div>
                                    <p class="hour count_text">{{rafflecontent[currentraffle].startdate[3]}}</p>
                                    <p class="text">HOUR</p>
                                </div>
                            </div>
                            <div  class="time_con">
                                <div>
                                    <p class="minutes count_text">{{rafflecontent[currentraffle].startdate[4]}}</p>
                                    <p class="text">MINUTES</p>
                                </div>
                            </div>
                            <div  class="time_con">
                                <div>
                                    <p class="second count_text">{{rafflecontent[currentraffle].startdate[5]}}</p>
                                    <p class="text">SECOND</p>
                                </div>
                            </div>
                        </div> -->
                    </div>
                </div>
                
                <div class="proceeding">
                    <span class="logo"><img :src="rafflecontent[currentraffle].item.brandlogoimg" :alt="rafflecontent[currentraffle].item.name"></span>
                    <div class="product_item" style="overflow: hidden;">
                        
                        <div :class="'img img' + rafflecontent[currentraffle].week ">
                            <img :src="rafflecontent[currentraffle].item.itemimg" :alt="rafflecontent[currentraffle].item">
                        </div>
                        <span class="person">{{rafflecontent[currentraffle].item.person}}명</span>
                        <div class="contents">
                            <p class="title">{{rafflecontent[currentraffle].item.name}}</p>
                            <div class="price_box">
                                <div class="original_price">
                                    <span class="price">{{$util.maskComma(rafflecontent[currentraffle].item.price)}}</span><span class="one">원</span>
                                </div>
                                <div class="discount">
                                    <span class="price">{{$util.maskComma(rafflecontent[currentraffle].item.saleprice)}}</span><span class="one">원</span>
                                </div>
                            </div>
                        </div>
                        <div
                            class="dimmed"
                            v-if="rafflecontent[currentraffle].status=='F'"
                            style="
                                position:absolute;
                                display:flex;
                                justify-content: center;
                                align-items: center;
                                top: 0;
                                left: 0;
                                width:100%;
                                height:100%;
                                font-family: 'Mont';
                                font-weight: bold;
                                font-stretch: normal;
                                letter-spacing: -2.09px;
                                color: rgba(255, 255, 255, 0.8);
                                font-size: 2rem;
                                line-height: 1.04;
                                background: rgba(0,0,0,0.4);
                                font-family: 'Mont';
                            "
                        >
                            <p class="coming_box position_text">COMING SOON</p>
                        </div>
                    </div>
                </div>
                
                <a @click="goSubscription(rafflecontent[currentraffle])" class="btn btn_color1"  v-if="rafflecontent[currentraffle].status=='T'">응모하기</a>

    
                <ul class="dot_list">
                    <li>
                        당첨자 발표 : 
                        {{rafflecontent[currentraffle].pubdate[1]}}월 
                        {{rafflecontent[currentraffle].pubdate[2]}}일 
                        {{rafflecontent[currentraffle].pubdate[3]}}:{{rafflecontent[currentraffle].pubdate[4]}}
                    </li>
                    <li>당첨자에 한하여 개별 카카오 알림톡(MMS) 발송</li>
                </ul>
            </div>

            <div class="product_list">
                <p>RAFFLE CALENDAR</p>
                    <swiper
                    ref="raffle_calendar_swiper"
                    :slides-per-view="2"
                    :options="swiperDetailOption"
                    :pagination="{ clickable: true }"
                    >
                        <swiper-slide 
                            v-for="(rafflecontentItem, idx) in rafflecontent" :key="idx" 
                            :class="rafflecontent[idx].status == 'END' ? 'list_item finish' : 'ist_item'" 
                        >
                            <div :class="rafflecontent[idx].status === 'T' ? 'item clean' : 'item'">
                                <div class="img no_act">
                                    <img :src="rafflecontent[idx].item.itemimg" :alt="rafflecontent[idx].item.name">
                                </div>
                                <div class="act">
                                    <div class="list_date " v-if="rafflecontent[idx].status !== 'END'">
                                        <span class="date_text01">{{rafflecontent[idx].startdate[1]}}<span>/</span>{{rafflecontent[idx].startdate[2]}}</span>
                                        <span class="date_text02">{{rafflecontent[idx].startdate[3]}}:{{rafflecontent[idx].startdate[4]}}</span>
                                        <span class="date_text03">OPEN</span>
                                    </div>
                                <div class="img" v-if="rafflecontent[idx].status !== 'END'">
                                    <img :src="rafflecontent[idx].item.itemimg" :alt="rafflecontent[idx].item.name">
                                </div>
                                    <div class="text_box" v-if="rafflecontent[idx].status !== 'END'">
                                        <p class="title">{{rafflecontent[idx].item.name}}</p>
                                        <span class="person">{{rafflecontent[idx].item.person}}명</span>
                                    </div>
                                    <div class="price_box" v-if="rafflecontent[idx].status !== 'END'">
                                        <div class="original_price">
                                            <span class="price">{{$util.maskComma(rafflecontent[idx].item.price)}}</span><span class="one">원</span>
                                        </div>
                                        <div class="discount">
                                            <span class="price">{{$util.maskComma(rafflecontent[idx].item.saleprice)}}</span><span class="one">원</span>
                                        </div>
                                    </div>
                                    <!-- <p class="finish_box position_text">{{rafflecontent[idx].status}}</p> -->
                                    <p class="finish_box position_text" v-if="rafflecontent[idx].status == 'END'">FINISH</p>
                                    <p class="coming_box position_text" v-if="rafflecontent[idx].status !== 'END'  && rafflecontent[idx].status=='F'">COMING SOON</p>
                                    <a @click="winnerList(rafflecontent[idx])" v-if="rafflecontent[idx].status == 'END'"  class="btn btn_color1 finish_btn">당첨자 확인하기</a>
                                </div>
                            </div>
                        </swiper-slide>
                        
                    </swiper>
                    <div class="swiper_control">
                        <div class="swiper-button-next swiper-button"></div>
                        <div class="swiper-button-prev swiper-button"></div>
                        <div class="swiper-pagination"><span></span></div>
                    </div>
            </div> 
        </div>

        <div class="section03">
            <div class="con_title">
                <p>래플 당첨 확률 2배 높이기</p>
            </div>
            <div class="promise_event">
                <div class="promise_item promise_item01">
                    <p class="title">장바구니에 상품 담기</p>
                    <p class="sub_text">
                        디플롯에서 원하는 상품 2개를 골라 <br/>장바구니에 넣으면 
                    <mark>당첨 확률 2배 UP!!</mark>
                    </p>
                    <div class="img"><img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/basket.png" alt="장바구니"></div>
                    <a href="/" class="btn btn_color2 promise_btn01">상품 둘러보기</a>
                    <ul class="dot_list">
                        <li>동일 상품 2개는 제외</li>
                        <li>{{rafflecontent[currentraffle].startdate[1]}}월 {{rafflecontent[currentraffle].startdate[2]}}일(14:00시 이후) 장바구니 추가 상품만 반영</li>
                    </ul>
                </div>

                <div class="promise_item promise_item02">
                    <p class="title">인스타그램 공유하기</p>
                    <ul class="num_list">
                        <li>
                            <span class="number">1</span>
                            <span class="text"> 디플롯 인스타그램 계정(@dplot.official)을 팔로우하세요.</span>
                        </li>
                        <li>
                            <span class="number">2</span>
                            <span class="text"> 아래 이미지를 두번 터치한 후 열린 새창에서 이미지를 1초간 눌러 다운로드 하세요.</span>
                        </li>
                    </ul>
                    <div class="img">
                        <a href="https://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img-pc.png" download="insta.png">
                            <img src="https://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img-pc.png" alt="인스타">
                        </a>
                    </div>
                    <ul class="num_list">
                        <li>
                            <span class="number">3</span>
                            <span class="text"> 인스타그램에 <b>#디플롯래플</b> 해시태그와 함께 업로드 하세요.</span>
                        </li>
                        <li>
                            <span class="number">4</span>
                            <span class="text"> 인스타그램 DM으로 디플롯 아이디 또는 성함/연락처 보내주세요.</span>
                        </li>
                    </ul>
                    <a href="https://www.instagram.com/dplot.official/" target="_blank" class="btn btn_color2 promise_btn02">D.PLOT 인스타그램 바로가기</a>
                </div>
            </div>
        </div>


        <div class="notice">
            <p class="notice_title">이벤트 유의사항 </p>
            <ul class="dot_list notice_list01">
                <li> 응모 방법 : 페이지 내 응모하기 버튼 클릭 </li>
                <li> 응모 기간 : 매주 화요일 14:00 ~ 매주 월요일 24: 00</li>
                <li> 상품 당첨 확인은 래플 캘린더에서 당첨자 결과보기에서 확인 부탁드립니다. </li>
                <li> 상품 당첨자에게는 알림톡(카카오톡)을 통해 발표일 14시에 개별 연락 드립니다.</li>
                <li> 래플 이벤트 당첨 확률을 높이기 위한 장바구니 상품 추가는 11월 15일 14시 이후에 담긴 상품만 카운트 됩니다.</li>
                <li> 
                    래플 이벤트 당첨 확률을 높이기 위한 SNS 공유하기는 완료 후 디플롯 아이디 또는 성함/연락처 기재 후
                    디플롯 공식 계정으로 DM 부탁드립니다. DM 미 전달 시 SNS 공유하기는 카운트 되지 않습니다. (@dplot.official)
                </li>
            </ul>
            <ul class="dot_list notice_list02">
                <li> 디플롯 계정당 한 상품에 1회 한하여 참여 가능합니다. </li>
                <li> 본 이벤트는 무작위 추첨제로 진행됩니다.</li>
                <li> 당첨자 분께 문자를 통해 자세한 경품 구매 방법이 안내될 예정입니다. </li>
                <li> 반드시 정해진 구매 시간 내에 구매하셔야 하며, 미구매 시 당첨이 취소되며 이에 따른 책임은 디플롯과 무관합니다. </li>
                <li> 비정상적인 방법을 통해 이벤트를 참여한 것으로 판단될 경우, 당첨 대상에서 제외될 수 있습니다. </li>
                <li> 본 이벤트는 별도의 공지없이 당사의 사정에 따라 사전 고지없이 변경 또는 조기 종료 될 수 있습니다. </li>
                <li> 
                    본 이벤트 관련 문의는 고객센터 또는 채팅상담으로 문의 부탁드립니다. 
                    디플롯 고객센터 1666-3642(운영시간 : 평일 10:00-16:00)
                </li>
                <li> 해당 이벤트에 참여하신 경우 마케팅 수신 활동에 동의하신 것으로 간주됩니다. </li>
                <li> 당첨자의 연락처가 없을 시 앱푸시로 안내가 진행됩니다. </li>
                <li> 당첨자에게는 세무 신고를 위해 당첨자의 개인정보를 요청할 예정입니다. </li>
                <li> 
                    경품에 대한 제세공과금은 (주)다다엠앤씨에서 부담하며 제세공과금 신고를 위한 개인정보와 <br/>
                    신분증 사본을 요구할 수 있으며 정보 제출이 어려울 경우 당첨이 취소됩니다.
                </li>
                <li> 당첨 상품 발송은 제세공과금을 위한 정보 수집 후 순차적으로 발송됩니다. </li>
                <li> 경품 특성 상 교환 및 환불이 불가하며, A/S는 해당 브랜드사 정책에 따릅니다.</li>
            </ul>
        </div>
    </div>

</template>


<script>
    import CountDownVue from './reffle2_components/CountDown.vue';
    import WinnerListModalVue from './reffle2_components/WinnerListModal.vue';
    import SubscriptionModalVue from './reffle2_components/SubscriptionModal.vue';
    import RaffleModalVue from '../../pc/promotion/reffle2_components/RaffleModal.vue';
    import { swiper, swiperSlide } from "vue-awesome-swiper";

    export default {
        components: {
            CountDownVue,
            swiper, swiperSlide
        },
        data(){
            return{
                raffleend : false,
                currentraffle: 0,
                eventidx: 0,
                rafflecontent: [
                    // {
                    //     week: 1,
                    //     month: 11,
                    //     startdate:  ['2022','11','14','18','00','00'],
                    //     enddate:    ['2022','11','14','18','50','00'],
                    //     pubdate:    ['2022','11','22','14','00','00'],
                    //     status: 'F', // T : 진행중 , F : 대기중 , END : 종료
                    //     item: {
                    //         brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img06_logo.png',
                    //         itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img06.png',
                    //         name: '네시노 테이블램프 오렌지',
                    //         price: '290000',
                    //         saleprice: '10,000',
                    //         salepercent: '97',
                    //         person: 3 // 추가
                    //     }
                    // },
                    // {
                    //     week:2,
                    //     month: 11,
                    //     startdate:  ['2022','11','14','19','10','00'],
                    //     enddate:    ['2022','11','14','19','20','00'],
                    //     pubdate:    ['2022','11','22','14','00','00'],
                    //     status: 'F',
                    //     item: {
                    //         brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img05_logo.png',
                    //         itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img05.png',
                    //         name: '발뮤다 더 팟 전기포트_화이트',
                    //         price: '159000',
                    //         saleprice: '10,000',
                    //         salepercent: '94',
                    //         person: 5
                    //     }
                    // },
                    // {
                    //     week: 3,
                    //     month: 12,
                    //     startdate:  ['2022','11','15','19','00','00'],
                    //     enddate:    ['2022','11','15','22','10','00'],
                    //     pubdate:    ['2022','11','29','14','00','00'],
                    //     status: 'F',
                    //     item: {
                    //         brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img04_logo.png',
                    //         itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img04.png',
                    //         name: '일광 스노우맨 테이블램프 버터',
                    //         price: '230,000',
                    //         saleprice: '10,000',
                    //         salepercent: '96',
                    //         person: 3
                    //     }
                    // },
                    // {
                    //     week:4,
                    //     month: 12,
                    //     startdate:  ['2022','11','18','18','00','00'],
                    //     enddate:    ['2022','11','18','18','10','00'],
                    //     pubdate:    ['2022','12','13','14','00','00'],
                    //     status: 'F',
                    //     item: {
                    //         brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img03_logo.png',
                    //         itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img03.png',
                    //         name: '발뮤다 스피커',
                    //         price: '449,000',
                    //         saleprice: '20,000',
                    //         salepercent: '96',
                    //         person: 1
                    //     }
                    // },
                    // {
                    //     week: 5,
                    //     month: 12,
                    //     startdate:  ['2022','11','19','18','02','17'],
                    //     enddate:    ['2022','11','19','18','10','00'],
                    //     pubdate:    ['2022','12','20','14','00','00'],
                    //     status: 'F',
                    //     item: {
                    //         brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img02_logo.png',
                    //         itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img02.png',
                    //         name: '팻보이 테이블램프 트랜스롯체, 투명',
                    //         price: '220,000',
                    //         saleprice: '10,000',
                    //         salepercent: '95',
                    //         person: 3
                    //     }
                    // },
                    // {
                    //     week: 6,
                    //     month: 12,
                    //     startdate:  ['2022','11','20','18','00','00'],
                    //     enddate:    ['2022','11','20','18','10','00'],
                    //     pubdate:    ['2022','12','27','14','00','00'],
                    //     status: 'F',
                    //     item: {
                    //         brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img01_logo.png',
                    //         itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle2/img01.png',
                    //         name: '허먼밀러 세일 체어, 블랙',
                    //         price: '1,210,000',
                    //         saleprice: '50,000',
                    //         salepercent: '96',
                    //         person: 1
                    //     }
                    // }
                ],
                mainTimer: '',
                swiperDetailOption: {
                    slidesPerView:2,
                    centeredSlides: true,
                    slidesOffsetBefore: 0,
                    grabCursor: true,
                    effect: 'slide',
                    loop: true,
                    navigation: {
                        nextEl: ".product_list .swiper-button-next",
                        prevEl: ".product_list .swiper-button-prev",
                    },
                    pagination: {
                        el: ".swiper_control .swiper-pagination"
                    },
                    observeParents: true,
                    observer: true,
                },
            }
        },
        mounted() {
            this.setCurrentRaffle();
			this.$refs.raffle_calendar_swiper.swiper.slideToLoop(this.currentraffle);

            this.mainTimer = setInterval(this.setCurrentRaffle, 1000)
            // init(){
            // 	console.log('a');
            // 	alert("2222");
            // 	this.onSearch();
            // }
        },
        created() {
            // setCurrentRaffle 함수 작동 시 오류로 인해 프론트 작업중 주석처리 (raffleContent 값이 빈값으로 들어옴)
            this.onSearch();
        },
        unmounted() {
            clearInterval(this.mainTimer);
        },
        methods: {
            callRaffleResultModal(){
                this.$eventBus.$emit('showModal', RaffleModalVue, "raffle-modal");
            },
            // 영어 넘버
            setEngNumber(data){
                var rntVal = "th";
                if(data ===1){ rntVal="st"}
                else if(data ===2){ rntVal="nd"}
                else if(data ===3){ rntVal="rd"}
                rntVal = data + rntVal;

                return rntVal;
            },
            //현재 주차 Raffle 계산
            setCurrentRaffle() {
                this.rafflecontent.forEach((el,idx)=>{
                    const raffleStart = new Date (`${el.startdate[0]}/${el.startdate[1]}/${el.startdate[2]} ${el.startdate[3]}:${el.startdate[4]}:${el.startdate[5]}`)
                    const raffleend = new Date (`${el.enddate[0]}/${el.enddate[1]}/${el.enddate[2]} ${el.enddate[3]}:${el.enddate[4]}:${el.enddate[5]}`)
                    const nowDate = new Date();
                    if(raffleStart <= nowDate){
                        this.currentraffle = idx
                        if(raffleend <= nowDate){
                            el.status = "END"
                            if(idx !== (this.rafflecontent.length - 1)){
                                this.currentraffle = idx + 1
                            }else{
                                this.raffleend = true
                            }
                        }else{
                            el.status = "T"
                        }
                    }else{
                        el.status = "F"
                    }
                })
            },
            //한국어 요일 처리
            getKorDay(arr) {
                const startdate = new Date(`${arr[0]}/${arr[1]}/${arr[2]} ${arr[3]}:${arr[4]}:${arr[5]}`)
                const korDay = ['일','월','화','수','목','금','토']
                return korDay[startdate.getDay()]
            },
            /**********************
            * SNS 공유하기 모달
            ********************/
            snsShareModal() {
            let description = "";
                console.log(this)
            let param = {
                kakao: {
                objectType: "feed",
                content: {
                    title: "[D.PLOT] 래플 이벤트",
                    description: description,
                    imageUrl: '',
                    link: {
                    mobileWebUrl: window.location.href,
                    webUrl: window.location.href
                    },
                },
                },
                meta: {
                title: "D.PLOT",
                summary: "상세내용",
                img: ''
                }
            }
            this.$eventBus.$emit('showModal', SnsShareModal, "snsShareModal", param);
            },
            //앱 다운로드
            goToDownloadApp() {
                let osName = this.$util.isAndroid();
                if(osName=="android"){
                    // 안드로이드 앱 다운로드 url
                    window.location.href = 'https://play.google.com/store/apps/details?id=kr.co.dplot';
                }
                else if(osName=="ios"){
                    window.location.href = 'https://apps.apple.com/kr/app/%EB%94%94%ED%94%8C%EB%A1%AF/id1624417666';
                }
                else{
                    alert("모바일 사용자가 아닙니다.");
                }
                
            },
            //응모하기
            goSubscription(data) {
                if (!this.$store.state.isLogin) {
                    this.$eventBus.$emit('confirm', '로그인', '로그인이 필요합니다. 로그인 하시겠습니까?',()=>{
                        this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, query : this.$route.query});
                        this.$router.push({name : 'member-login'});
                    });
                    return;
                } else{
                    if(data.status!='T'){
                        alert("이벤트 진행 전 입니다.");
                        return;
                    }
                    let param = data
                    
                    this.$eventBus.$emit('showModal', SubscriptionModalVue, "raffleSubscription", param);
                }
            },
            //당첨 결과 확인
            winnerList(data) {
                let params = {
                    eventidx : data.eventidx,
                }
                this.$http.post("/event/raffleWinList", params)
                    .then(result => {
                        if (result.statusCode === 200) {
                            let param = data
                            this.$eventBus.$emit('showModal', WinnerListModalVue, "raffleWinner", param);
                        }
                        else if (result.statusCode === 201) {
                            alert("당첨자 추첨 전 입니다.");
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                });
            },
            // 상세 검색
            onSearch() {
                let params = {
                }
                this.$http.post("/event/raffle2Search", params)
                    .then(result => {
                        if (result.statusCode === 200) {
                        let data = result.data;

                        // 프로모션 상세 정보
                        this.rafflecontent = data.rafflecontent;
                        this.setCurrentRaffle();
                        this.$refs.raffle_calendar_swiper.swiper.slideToLoop(this.currentraffle);
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                });
            },
            numberToKorSeq(idx){
                const korDay = ['첫번째','두번째','세번째','네번째','다섯번째','여섯번째','일곱번째']
                return korDay[idx]
            },
        },
        

    }
</script>

<style>
    @import "./Reffle2.css";
</style>