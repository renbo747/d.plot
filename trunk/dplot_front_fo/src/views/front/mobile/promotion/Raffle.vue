<template>
	<div class="event">
		<div class="top">
			<img src="http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/top.jpg" alt="">
			<div class="top_title">
				<strong class="atten-new">Members Only</strong>
				<p>매주 화요일마다 시작하는 꽝없는 100% 당첨 래플 이벤트. <br />
				카카오톡 공유 시 당첨 확률 <b class="atten-new">UP UP!</b></p>
				<p class="date atten-new">22.09.15~22.10.11</p>
			</div>
		</div> 
		<div class="raffle_progress">
			<div v-if="raffleend">
				<p class="chk_title">{{rafflecontent[currentraffle].month}}월 <span class="point">{{rafflecontent[currentraffle].week}}</span>주차</p>
				<p class="prog_title">래플 종료</p>
				<div class="count_date">
					<p>래플 이벤트가 끝났습니다.</p>
				</div>
			</div>
			<div v-else>
				<!--p class="chk_title">{{rafflecontent[currentraffle].month}}월 <span class="point">{{rafflecontent[currentraffle].week}}</span>주차</p-->
				<div v-if="rafflecontent[currentraffle].status=='T'">
					<p class="prog_title">래플 진행중</p>
					<count-down-vue :targetDate="rafflecontent[currentraffle].enddate"></count-down-vue>
				</div>
				<div v-else>
					<p class="prog_title">래플 시작 일정</p>
					<div class="count_date">
						<p>{{rafflecontent[currentraffle].startdate[1]}}월 {{rafflecontent[currentraffle].startdate[2]}}일 ({{getKorDay(rafflecontent[currentraffle].startdate)}}) {{rafflecontent[currentraffle].startdate[3]}}:{{rafflecontent[currentraffle].startdate[4]}}</p>
					</div>
				</div>
			</div>
			<div class="prog_prd">
				<div class="prd_img">
					<div class="prd_brand">
						<img :src="rafflecontent[currentraffle].item.brandlogoimg" alt="">
					</div>
					<div class="prd_big_thumb">
						<div class="coming_soon" v-if="raffleend">
							<p class="atten-new">END</p>
						</div>
						<div class="coming_soon" v-if="rafflecontent[currentraffle].status=='F'">
							<p class="atten-new">coming<br />soon</p>
						</div>
						<img :src="rafflecontent[currentraffle].item.itemimg" alt="">
					</div> 
				</div>
				<div class="prd_title">
					<p>{{rafflecontent[currentraffle].item.name}}</p>
					<div class="prd_price">
						<div class="price atten-new">
							<strong>{{$util.maskComma(rafflecontent[currentraffle].item.saleprice)}}<small>원</small></strong>
							<b>{{$util.maskComma(rafflecontent[currentraffle].item.price)}}<small>원</small></b>
						</div>
						<div class="percent atten-new">{{rafflecontent[currentraffle].item.salepercent}}<small>%</small></div>
					</div>
					<p class="date">당첨자 발표 : {{rafflecontent[currentraffle].pubdate[1]}}월 {{rafflecontent[currentraffle].pubdate[2]}}일 ({{getKorDay(rafflecontent[currentraffle].pubdate)}}) {{rafflecontent[currentraffle].pubdate[3]}}:{{rafflecontent[currentraffle].pubdate[4]}}</p>
				</div>
			</div> 
			<div class="prog_btn" v-if="!raffleend">
				<a @click="snsShareModal">공유하기</a>
				<a @click="goSubscription(rafflecontent[currentraffle])">응모하기</a>
			</div>
		</div>
		<div class="schedule">
			<div class="tip">
				<p class="tip_title">릴레이 래플 놓치지 않는 TIP</p>
				<p>*디플롯 앱을 다운로드 하면 래플 오픈 전 <br />푸시 메시지를 받을 수 있습니다.</p>
				<p>*디플롯 마케팅 정보 수신 동의 사용자에 한함</p>
				<a @click="goToDownloadApp" class="tip_btn">앱 다운로드</a>
			</div>
			<div class="sdate">
				<p class="sdate_title">릴레이 래플 일정</p>
				<ul>
					<li v-for="(rafflecontentItem, idx) in rafflecontent" :key="idx">
						<div v-if="rafflecontent[idx].status == 'END'" class="result_view">
							<a @click="winnerList(rafflecontent[idx])">당첨 결과보기</a>
						</div>
						<div class="prd_thumb">
							<span class="start">{{setEngNumber(idx + 1)}} EVENT</span>
							<img :src="rafflecontent[idx].item.itemthumb" alt=""> 
							<!--span class="pers_num">10명</span -->
						</div> 
						<div class="prd_info">
							<p v-if="rafflecontent[idx].status == 'T'" class="ing">진행중</p>
							<div v-else>
								<p>래플 시작일</p>
								<p class="date">{{rafflecontent[idx].startdate[1]}}월 {{rafflecontent[idx].startdate[2]}}일 ({{getKorDay(rafflecontent[idx].startdate)}}) {{rafflecontent[idx].startdate[3]}}:{{rafflecontent[idx].startdate[4]}}</p>
							</div>
							<p class="name">{{rafflecontent[idx].item.name}}</p> 
							<div class="prd_price">
								<div class="price atten-new">
									<strong>{{$util.maskComma(rafflecontent[idx].item.saleprice)}}<small>원</small></strong>
									<b>{{$util.maskComma(rafflecontent[idx].item.price)}}<small>원</small></b>
								</div>
								<div class="percent atten-new">{{rafflecontent[idx].item.salepercent}}<small>%</small></div>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div class="tip">
				<p class="tip_title">래플 이벤트 응모자 전원 100% 당첨</p>
				<p>*이벤트 응모자 중  미당첨자 한하여 <br />장바구니 할인 쿠폰 100% 발급</p> 
				<p><b>지급 일정 : 매주 화요일 14시 이후 일괄 지급</b></p>
			</div>
		</div> 
		<div class="notice">
			<p class="notice_title">이벤트 유의사항</p>
			<p>응모 방법 : 페이지 내 응모하기 버튼 클릭 <br />
			응모 기간 : 매주 화 15:00 ~ 월 24:00 <br />
			이벤트 페이지 하단 당첨자 발표 메뉴를 통해 당첨자 리스트 발표 <br />
			상품 당첨자에게는 알림톡(카카오톡)을 통해 발표일 오후 14시에 개별 연락 드립니다.
			</p>
			<ul>
				<li>디플롯 계정당 한 상품에 1회에 한하여 참여 가능합니다.</li>
				<li>본 이벤트는 무작위 추첨제로 진행됩니다.</li>
				<li>당첨 여부는 이벤트 페이지 하단 당첨자 발표 메뉴를 통해 당첨자 안내 드리며, 알림톡(카카오)를 통해 발표일 오후 14시에 개별 연락드립니다.</li>
				<li>당첨자 분께 문자를 통해 자세한 경품 구매 방법이 안내될 예정입니다.</li>
				<li>반드시 정해진 구매 시간 내에 구매하셔야 하며 미구매 시 당첨이 취소되며 이에 따른 책임은 디플롯과 무관합니다.</li>
				<li>비정상적인 방법을 통해 이벤트를 참여한 것으로 판단될 경우, 당첨 대상에서 제외될 수 있습니다.</li>
				<li>본 이벤트는 별도의 공지없이 당사의 사정에 따라 사전 고지없이 변경 또는 조기 종료 될 수 있습니다.</li>
				<li>본 이벤트 관련 문의는 고객센터 또는 채팅 상담으로 문의 부탁드립니다.</li>
				<li>디플롯 고객센터 1666-3642 (운영시간 : 평일 10:00~16:00)</li>
				<li>해당 이벤트를 참여하신 경우 마케팅 수신 활용에 동의하신 것으로 간주됩니다.</li>
				<li>당첨자의 연락처가 없을 시 앱푸시로 안내가 진행됩니다.</li>
				<li>당첨자에게는 세무 신고를 위해 당첨자의 개인정보를 요청할 예정입니다.</li>
				<li>경품에 대한 제세공과금은 (주)다다엠앤씨에서 부담하며 제세공과금 신고를 위한 개인정보와 신분증 사본을 요구할 수 있으며 정보제출이 어려울 경우 당첨이 취소됩니다.</li>
				<li>경품 특성 상 교환 및 환불이 불가하며, A/S는 해당 브랜드사 정책에 따릅니다.</li>
				<li>개인 정보 기재 오류 등 SMS 미수신 및 연락이 불가능한 경우 당첨 대상에서 제외되며 이로인한 피해는 별도로 보상하지않습니다.</li>
				<li> 이에 동의하지 않을 경우 당첨이 취소됩니다.</li>				
			</ul>
		</div>
	</div>
</template> 

<script>
	import CountDownVue from './raffle_components/CountDown.vue';
	import SnsShareModal from "@views.mobile/shop/popup/SnsShareModal.vue";
	import WinnerListModalVue from '@views.pc/promotion/raffle_components/WinnerListModal.vue';
	import SubscriptionModalVue from '@views.pc/promotion/raffle_components/SubscriptionModal.vue';

	export default {
		components: {
			CountDownVue,
		},
		data(){
			return{
				raffleend : false,
				currentraffle: 0,
				eventidx: 0,				
				rafflecontent: [
					{
						week: 3,
						month: 9,
						startdate: ['2022','09','15','14','00','00'],
						enddate: ['2022','09','20','14','00','00'],
						pubdate: ['2022','09','20','14','00','00'],
						status: 'F', /* T : 진행중 , F : 대기중 , END : 종료 */
						item: {
							brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/brand_logo_01.jpg',
							itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/prd_01.jpg',
							itemthumb: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/prd_01_thumb.jpg',
							name: '팻보이 - 테이블 램프',
							price: '220000',
							saleprice: '10000',
							salepercent: '95',
						}
					},
					{
						week: 4,
						month: 9,
						startdate: ['2022','09','22','14','00','00'],
						enddate: ['2022','09','27','14','00','00'],
						pubdate: ['2022','09','20','14','00','00'],
						status: 'F',
						item: {
							brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/brand_logo_02.jpg',
							itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/prd_02.jpg',
							itemthumb: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/prd_02_thumb.jpg',
							name: '비라인 - 보비 트롤리 22 (화이트)',
							price: '580000',
							saleprice: '30000',
							salepercent: '95',
						}
					},
					{
						week: 1,
						month: 10,
						startdate: ['2022','09','29','14','00','00'],
						enddate: ['2022','10','4','14','00','00'],
						pubdate: ['2022','09','20','14','00','00'],
						status: 'F',
						item: {
							brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/brand_logo_03.jpg',
							itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/prd_03.jpg',
							itemthumb: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/prd_03_thumb.jpg',
							name: '아르테미테 - 티지오 35',
							price: '540000',
							saleprice: '20000',
							salepercent: '96',
						}
					},
					{
						week: 2,
						month: 10,
						startdate: ['2022','10','6','14','00','00'],
						enddate: ['2022','10','11','14','00','00'],
						pubdate: ['2022','09','20','14','00','00'],
						status: 'F',
						item: {
							brandlogoimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/brand_logo_04.jpg',
							itemimg: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/prd_04.jpg',
							itemthumb: 'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/prd_04_thumb.jpg',
							name: '토넷 - S42체어,월넛',
							price: '780000',
							saleprice: '100000',
							salepercent: '87',
						}
					}
				],
				mainTimer: '',
			}
		},
		mounted() {			
			this.mainTimer = setInterval(this.setCurrentRaffle, 1000)
			// init(){
			// 	console.log('a');
			// 	alert("2222");
			// 	this.onSearch();
			// }
		},
		created() {
			// setCurrentRaffle 함수 작동 시 오류로 인해 프론트 작업중 주석처리 (rafflecontent 값이 빈값으로 들어옴)
			this.onSearch();
		},
		unmounted() {
			clearInterval(this.mainTimer);
		},
		methods: {
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
							if(idx !== this.rafflecontent.length - 1){
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
                });			},
			// 상세 검색
			onSearch() {
				let params = {
				}
				this.$http.post("/event/raffleSearch", params)
					.then(result => {
						if (result.statusCode === 200) {
						let data = result.data;

						// 프로모션 상세 정보
						this.rafflecontent = data.rafflecontent;

						}
					})
					.catch(error => {
						this.$util.debug(error);
				});
			},
		},

	}
</script>

<style scoped>
	.event{width:100%; max-width:1000px; margin:0 auto;  color: #000;  letter-spacing:-1px;  font-size: 1rem;font-weight: 400;line-height: 1.4;}
	.event img {max-width:100%;}
	.event .top{position: relative;}
	.event .top .top_title{position: absolute; width:100%; height:100%; left:0; top:0; padding:40% 7% 0; }
	.event .top .top_title strong{background-color: #c0ff00; font-size: 18px; color:#000; display: inline-block; padding:5px 15px; font-weight:600;}
	.event .top .top_title p{font-size: 18px; color:#fff; margin:30px 0 20px}
	.event .top .top_title p b{color: #c0ff00; font-weight:600; font-size:24px;}
	.event .top .top_title p.date{font-weight:600;font-size:20px; margin-top:10px} 
	.event .count_date{font-size:36px; font-weight:900; color: #eb2929; margin:20px 0 40px;}
	.event .raffle_progress{background-color: #c0ff00; padding:70px 0; text-align: center;}
	.event .raffle_progress .chk_title{font-size: 20px; color: #eb2929; font-weight:700;  margin-bottom: 5px;}
	.event .raffle_progress .chk_title .point{position: relative;  }
	.event .raffle_progress .chk_title .point::after{content:''; width: 10px; height: 7.5px; background: url('http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/raffle/mobile/ico_check.png') 0 0 / cover; position: absolute; left: 0; top: -6px;}
	.event .raffle_progress .prog_title{font-size: 30px;  font-weight:700; } 
	.event .raffle_progress .prog_prd{width:80%; margin: 0 auto;}
	.event .raffle_progress .prog_prd .prd_img{position: relative; width:86%; margin:0 auto;}
	.event .raffle_progress .prog_prd .prd_img .prd_brand{text-align:left; width:32%;}
	.event .raffle_progress .prog_prd .prd_img .prd_big_thumb{position:relative}
	.event .raffle_progress .prog_prd .prd_img .prd_big_thumb .coming_soon{width:100%; height:100%; background:rgba(0,0,0,0.2); position:absolute; top:0; left:0; display:flex; justify-content: center; align-items: center;}
	.event .raffle_progress .prog_prd .prd_img .prd_big_thumb .coming_soon p{color:#eb2929; font-weight: 900; font-size:55px; line-height:45px;}
	.event .raffle_progress .prog_prd .prd_title{padding:30px 0;}
	.event .raffle_progress .prog_prd .prd_title p{font-size: 20px;font-weight: 600;}
	.event .raffle_progress .prog_prd .prd_title p.date{font-size: 20px; color:#eb2929; font-weight: 500; font-family: "AppleSDGothicNeoM00"; margin-top:10px} 
	.event .raffle_progress .prog_prd .prd_title .prd_price{ margin:10px 0 0;}
	.event .raffle_progress .prog_prd .prd_title .prd_price > div{  display:inline-block; margin:0 7px; line-height: 1;} 
	.event .raffle_progress .prog_prd .prd_title .prd_price .price{text-align:right; }
	.event .raffle_progress .prog_prd .prd_title .prd_price .price strong{ font-size:30px; color:#eb2929; font-weight:800;   line-height:1.2; display:block;}
	.event .raffle_progress .prog_prd .prd_title .prd_price .price small{font-size:18px; font-weight:600;}
	.event .raffle_progress .prog_prd .prd_title .prd_price .price b{font-size:18px; color:#000; font-weight:400; display:inline-block; position:relative}
	.event .raffle_progress .prog_prd .prd_title .prd_price .price b:after{content:""; width:100%; height:1px; background:#000; position:absolute; left:0; top:50%;}
	.event .raffle_progress .prog_prd .prd_title .prd_price .price b small{font-size:20px; color:#000;  font-weight:400;}
	.event .raffle_progress .prog_prd .prd_title .prd_price .percent {font-size:70px; color:#eb2929; font-weight:800; line-height:60px;} 
	.event .raffle_progress .prog_prd .prd_title .prd_price .percent small{font-size:40px}
	.event .raffle_progress .prog_btn{width:90%; margin:0 auto; display:flex; flex-wrap:wrap; justify-content: space-between; align-items: center;}
	.event .raffle_progress .prog_btn a{display:block; width:49%; height:56px; line-height:56px; text-align:center; border:2px solid #000; font-size:20px; font-weight:700; background-color:#fff;}
	.event .raffle_progress .prog_btn a:last-child{background-color: #000; color:#c0ff00;}
	.event .schedule{background-color: #f1f1f1; padding:30px 0 30px; text-align:center;}
	.event .schedule .tip{width:80%; margin:60px auto 0; padding:20px 0;  border-top:7px solid #000;}
	.event .schedule .tip .tip_title{ font-size:20px; font-weight:700; margin-bottom: 20px;}
	.event .schedule .tip p{margin:10px 0; font-size:15px;  } 
	.event .schedule .tip p b{font-size:16px; color: #eb2929; font-weight:600; margin-top:20px; display:block;}
	.event .schedule .tip_btn{width:100%; height:56px; line-height:56px; background-color: #000; color:#fff; font-weight:700; font-size:20px; display:block; margin:30px auto;}
	.event .schedule .sdate{width:90%; margin:30px auto 0;}
	.event .schedule .sdate .sdate_title{font-size:30px; font-weight:700;}
	.event .schedule .sdate > ul{width: 100%; margin: 20px auto; background-color: #cccccc; padding:7px;} 
	.event .schedule .sdate > ul > li:not(:last-child){margin-bottom:14px;}
	.event .schedule .sdate > ul > li{position: relative; background-color: #fff; display:flex; flex-wrap:wrap; width:100%;} 
	.event .schedule .sdate > ul > li .result_view{width:100%; height:100%; background-color:  rgba(0,0,0,0.4); position: absolute; left:0; top:0; display:flex; align-items: center; justify-content: center;     z-index: 2;}
	.event .schedule .sdate > ul > li .result_view::after{content: ""; background-color:rgba(255,255,255,0.26);  position: absolute; left:0; top:0; width: 100%; height:100%; z-index:1;}
	.event .schedule .sdate > ul > li .result_view a{display:block; font-size:20px; background-color: #000; width:70%; text-align:center; height:46px; line-height:46px; color:#fff; position:relative; z-index:2;}
	.event .schedule .sdate > ul > li .prd_thumb{position: relative; width:40%; padding-top:10px;}
	.event .schedule .sdate > ul > li .prd_thumb .start{background-color: #e4e4e4; font-size:13px; padding:0 10px; height:26px; line-height:26px; position: absolute; left:0; top:0; font-weight: 700; } 
	.event .schedule .sdate > ul > li .prd_thumb .pers_num{ font-size:16px; padding:20px; position: absolute; right:0; bottom:0; font-weight: 500; } 
	.event .schedule .sdate > ul > li .prd_info{width:60%; padding:20px 10px 0 0; text-align:left;}
	.event .schedule .sdate > ul > li .prd_info p{ font-size:14px; font-weight:600;}
	.event .schedule .sdate > ul > li .prd_info p.date{font-size:20px; color: #eb2929; margin-bottom:5px;}
	.event .schedule .sdate > ul > li .prd_info p.name{color:#666666; font-weight:400}
	.event .schedule .sdate > ul > li .prd_info p.ing{width:70%; font-size:16px; height:34px; line-height: 34px; background-color: #c0ff00; text-align: center; margin-bottom: 10px;}
	.event .schedule .sdate > ul > li .prd_price{margin:5px 0 0; }
	.event .schedule .sdate > ul > li .prd_price > div{  display:inline-block; margin-right:10px; line-height: 1; margin-bottom:10px;} 
	.event .schedule .sdate > ul > li .prd_price .price{   }
	.event .schedule .sdate > ul > li .prd_price .price strong{ font-size:20px; color:#eb2929; font-weight:800;   line-height:1.2; display:block;}
	.event .schedule .sdate > ul > li .prd_price .price small{font-size:16px; font-weight:600;}
	.event .schedule .sdate > ul > li .prd_price .price b{font-size:16px; color:#000; font-weight:400; display:inline-block; position:relative}
	.event .schedule .sdate > ul > li .prd_price .price b:after{content:""; width:100%; height:1px; background:#000; position:absolute; left:0; top:50%;}
	.event .schedule .sdate > ul > li .prd_price .price b small{font-size:14px; color:#000;  font-weight:400;}
	.event .schedule .sdate > ul > li .prd_price .percent {font-size:46px; color:#eb2929; font-weight:800; line-height:40px;} 
	.event .schedule .sdate > ul > li .prd_price .percent small{font-size:20px}
	.event .notice{padding:50px 20px;}
	.event .notice .notice_title{font-size: 18px; font-weight:600; margin:0 0 10px }
	.event .notice p{font-size:15px; font-weight:500; margin:0 0 20px;}
	.event .notice > ul {padding-left:10px;}
	.event .notice > ul > li{font-size:14px; margin:3px 0; position:relative; padding-left:10px; list-style:none;} 
	.event .notice > ul > li:after{content:""; width:3px; height:3px; border-radius:50%; position:absolute; left:0; top:7px; background-color: #000;}
</style>