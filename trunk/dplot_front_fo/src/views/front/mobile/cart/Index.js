import OptionChange from "@views.pc/cart/popup/OptionChange.vue"
import RestockAlarm from "@views.pc/cart/popup/RestockAlarm.vue"

import { swiper, swiperSlide } from "vue-awesome-swiper";

export default {
  components: {
    swiper,
    swiperSlide,
  },
  beforeCreate() {
    this.$store.commit('onSubHeaderOption', {
      title: '장바구니',
      searchIcon: false,
      cartIcon: false,
      homeIcon: false,
    })
  },
  data() {
    return {
      lodingSucc : 'F',
      chkCnt : 0,
      iscart : 'T',
      dealers : [],
      orderlist : [],
      checkAll : true,
      recopick : process.env.VUE_APP_RECOPICK_CART,
      isCartBuyBottomSheet: false,
      isBuyBottomSheet : false,
      currentItem : {},
      selectedItem : [],
      checkItem : null,
      totprice : 0,
      totcpnamt : 0,
      totdelivamt: 0,
      skey : 0,
      recentItem : [],
      bestItem : [],
      //카트 슬라이드 데이터 (스와이퍼)
      swiperCartOption: {
        slidesPerView: "auto",
        spaceBetween: 20,
        slidesPerGroup: 4,
        //loop: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
      },
    }
  },
  mounted() {
    this.getCartList();
    this.getRecentList();
  },
  methods: {
    //최근본상품
   /***********************
         * 최근본 상품 목록 조회
         ***********************/
    getRecentList() {
      this.recentList = [];
      this.$util.debug("최근본목록:::");
      let list = this.$front.getRecentItems(this);
      if(this.$util.isEmpty(list) || list.length == 0) {
        this.$http.post('/goods/bestList', {}).then(result => {
          if (result.statusCode == 200) {
            for(let i = 0 ; i < result.data.bestlist.length ; i++){
              result.data.bestlist[i].saleamt = result.data.bestlist[i].saleamt - result.data.bestlist[i].goodscpnamt;
            }
            this.recentItem = result.data.bestlist;
          }
        })
      } else {
        let param = {
          goodsnolist: list
        }
        this.$http.post('/goods/recentList', param).then(result => {
            if (result.statusCode == 200) {
              for(let i = 0 ; i < result.data.recentlist.length ; i++){
                result.data.recentlist[i].saleamt = result.data.recentlist[i].saleamt - result.data.recentlist[i].goodscpnamt;
              }
              this.recentItem = result.data.recentlist;
            }
        })
      }
    },
    // 카트목록 조회
    getCartList() {
      this.$front.getCartList(this, (data) =>{
         this.makeCartGoods(data);
      });
    },
    // 카트목록 화면구조로 변경
    makeCartGoods(data) {
      let items = [];
      this.dealers.splice(0);
      
      //Child Compnent Rerendering용
      this.skey++;

      for(let i = 0 ; i < data.list.length ; i++) {
          //프로모션 할인 세팅
          let salepromo = data.list[i].salepromo.split('|');
          data.list[i].salepromoidx = salepromo[0]; //프로모션 IDX
          data.list[i].salepromoamt = Number(salepromo[1]); //프로모션 할인가
          
          //상품 옵션
          let optionnames = data.list[i].optionname.split(',');
          data.list[i].opthtml = '';
          if(optionnames.length > 0) {
            if(data.list[i].isaddgoods == 'T') {
              optionnames[0] = '추가상품:' + optionnames[0]
            }
            for(let j = 0 ; j < optionnames.length ; j++) {
              optionnames[j] = '<span>' + optionnames[j] + '</span>';
            } 
            data.list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');  
          }
          
          // 상품체크상태
          data.list[i].chk = true;
          // if(this.checkItem != null) {
          //   let isCheck = false;
          //   for(let j = 0 ; j < this.checkItem.length ; j++) {
          //     if(data.list[i].goodsno == this.checkItem[j].goodsno
          //       && data.list[i].optioncode == this.checkItem[j].optioncode) {
          //         isCheck = true;
          //       }
          //   }
          //   data.list[i].chk = isCheck;
          // }
          
          //상품구조 변경
          const temp = this.$util.deepClone(data.list[i]);

          let idx = this.$front.containIdx(this.dealers, "dealerno", temp.dealerno);
          if(idx == -1 && temp.isaddgoods == 'F') {
              var dealer = {
                  dealerno : temp.dealerno,
                  dealernm : temp.dealernm,
                  delivamt : 0
              }
              this.$set(dealer, 'items', []);
              this.dealers.push(dealer);
          }

          idx = this.$front.containIdx(items, "goodsno", temp.orggoodsno);
          if(idx == -1) {
              temp.sumsaleamt = 0;
              temp.saleamt = temp.mainsaleamt - temp.maingoodscpnamt;
              this.$set(temp, 'detail', []);
              temp.detail.push(this.$util.deepClone(data.list[i]));
              items.push(temp);
          } else {
              items[idx].detail.push(temp);
          }
      }
      
      //레코픽로그
      this.$nextTick(() => {
        for(let i = 0 ; i < this.orderlist.length ; i++){
          const cart = {
            id : this.orderlist[i].goodscode + '', 
            count : this.orderlist[i].ordcnt
          }
          recoPick('sendLog','basket', cart);
        }
      });

      //입점사와 입점사상품 세팅
      for(let i = 0 ; i < this.dealers.length ; i++) {
          for(let j = 0 ; j < items.length; j++) {
              if(this.dealers[i].dealerno == items[j].dealerno) {
                  this.dealers[i].items.push(items[j]);
              }
          }
      }
      this.itemChange();
      this.lodingSucc = 'T';
    },
    // CartGoods Componet 데이터 변경이벤트(사용여부 체크)
    itemChange() {
        this.checkItem = [];
        this.orderlist = [];
        let checkAll = true;
        let temp = [];

        this.totprice = 0;
        this.totcpnamt = 0;
        this.totdelivamt = 0;
        this.chkCnt = 0;

        for(let i = 0 ; i < this.dealers.length ; i++) {
          this.dealers[i].delivamt = 0;
          this.dealers[i].delivamt2 = 0;
          temp = []; //입점사 배송비 계산용
          //판매사별 배송비
          for(let j = 0 ; j < this.dealers[i].items.length ; j++) {
              let items = this.dealers[i].items[j];
              items.sumsaleamt = 0;
              items.sumdiscount = 0;

              // if(!items.chk)  checkAll = false;
              // if (items.chk) {
              //   this.chkCnt += 1;  
              // }
              
              for(let y = 0 ; y < items.detail.length ; y++){
                let item = items.detail[y];
                if (items.chk) {
                  this.chkCnt += 1;  
                }

                item.delivamt = 0;
                
                //상품할인후 판매가(판매가 - 할인프로모션 - 쿠폰할인가)
                item.saleamt = item.price - item.salepromoamt - item.goodscpnamt;
                //할인가
                item.discount = item.salepromoamt + item.goodscpnamt;
                
                //상품별 합계/할인 급액
                items.sumsaleamt = items.sumsaleamt + (item.saleamt * item.ordcnt);
                items.sumdiscount = items.sumdiscount + (item.discount * item.ordcnt);

                //체크되어 있고 삭제된 상품이 아닐경우 주문목록에 추가
                if(items.chk && item.istrash == 'F') {
                  //배송비계산을 위해임시변수
                  temp.push(item);
                  //주문목록
                  this.orderlist.push(item);
                  this.checkItem.push(item);
                  //총상품금액
                  this.totprice = this.totprice + (item.price * item.ordcnt);
                  //총할인금액
                  this.totcpnamt = this.totcpnamt + (item.discount * item.ordcnt);

                  //판매사별 배송비
                  this.dealers[i].delivamt += item.delivamt;
                }
                if(item.istrash == 'F') {
                  this.dealers[i].delivamt2 += item.delivamt;
                }
              }
          }
          
          //총배송비합계
          //this.totdelivamt = this.totdelivamt + this.dealers[i].delivamt;
        }
        const delivAmt = this.$front.calDelivAmt(this.orderlist, 'N');
        this.totdelivamt = delivAmt.delivamt;
        this.checkAll = checkAll;
    },
    // 전체 체크 이벤트
    checkAllChange(){
      for(let i = 0 ; i < this.dealers.length ; i++) {
        for(let j = 0 ; j < this.dealers[i].items.length ; j ++) {
          this.dealers[i].items[j].chk = this.checkAll;
        }
      }
      this.itemChange();
    },
    //전체삭제
    checkDelete() {
      let deleteItems = [];
      let isCheck = false;
      for(let i = 0 ; i < this.dealers.length ; i++) {
        for(let j = 0 ; j < this.dealers[i].items.length ; j++) {
          if(this.dealers[i].items[j].chk) {
            isCheck = true;
            deleteItems = deleteItems.concat(this.dealers[i].items[j].detail);
          }
        }
      }
      if(!isCheck) {
        this.$eventBus.$emit('alert','알림','상품을 선택해주세요');
        return;
      }
      
      this.$eventBus.$emit('confirm', '확인' ,'선택하신 상품을 삭제하시겠어요?' , ()=>{
        this.itemsDelete(deleteItems);
      });
    },
    //상품옵션삭제
    itemDelete(items, item) {
      item.istrash = 'T';
      
      if(!this.$front.checkCartItem(this, items.detail)){
        item.istrash = 'F';
        return;
      }

      this.$front.saveCart(this, items.detail, (data)=>{
        this.makeCartGoods(data);
        this.$toast.clear();
        this.$toast.open('상품이 삭제되었습니다.');
      });
    },
    //상품삭제
    itemsDelete(items) {
      for(let i = 0 ; i < items.length ; i++) {
        items[i].istrash = 'T';
      }

      this.$front.saveCart(this, items, (data)=>{
        this.makeCartGoods(data);
        this.$toast.clear();
        this.$toast.open('상품이 삭제되었습니다.');
      });
    },
    //옵션변경 클릭
    changeOption(item){
      this.currentItem = item;
      var temp = this.$util.deepClone(item.detail);
      this.selectedItem = temp;
      this.isBuyBottomSheet = true;
    },
    //옵션변경 클릭 모달
    changeOptionModal(item) {
      this.currentItem = item;
      var temp = this.$util.deepClone(item.detail);
      this.selectedItem = temp;

      this.$eventBus.$emit('showModal', OptionChange, 'optionSelectModal', 
        {
          productInfo : this.currentItem,
          items : this.selectedItem
        },
        ()=>{
          this.handleCart();
        }
      );
    },
    //바로구매
    quickOrder(item) {
      if(!this.$front.checkCartItem(this, item.detail)){
        return;
      }
      if(this.$store.state.isLogin) {
        this.$router.push({name:'order', query : {items :  this.$front.makeOrderStr(item.detail), islogin : this.$store.state.isLogin}});
      } else {
        this.$eventBus.$emit('confirm', "비회원구매 안내", '비회원으로 구매 시 추가할인 및 쿠폰, 포인트 혜택을 받으실 수 없습니다.로그인 후 구매하시겠습니까?', 
          ()=>{
            this.$router.push({name:'order', query : {items :  this.$front.makeOrderStr(item.detail), islogin : this.$store.state.isLogin}});
          },
          ()=>{
            this.$router.replace({name:'member-login'});
          }, '비회원구매','로그인'
        );
      }
    },
    //옵션변경처리
    handleCart() {
      if(!this.$front.checkCartItem(this, this.selectedItem)){
        return;
      }
      
      this.$front.saveCart(this, this.selectedItem, (data)=>{
        //this.currentItem.detail = this.selectedItem;
        this.makeCartGoods(data);
        this.isBuyBottomSheet = false;
        this.$toast.clear();
        this.$toast.open('옵션이 변경되었습니다.');
      });
    },
    //주문하기
    goOrder(){
      if(this.orderlist.length === 0) {
        this.$eventBus.$emit('alert', '알림', '상품을 선택해주세요.');
      } else {
        if(this.$store.state.isLogin) {
          this.$router.push({name:'order', query : {items :  this.$front.makeOrderStr(this.orderlist), islogin : this.$store.state.isLogin}});
        } else {
          this.isBuyBottomSheet = false;
          this.$eventBus.$emit('confirm', "비회원구매 안내", '비회원으로 구매 시 추가할인 및 쿠폰, 포인트 혜택을 받으실 수 없습니다.로그인 후 구매하시겠습니까?', 
              ()=>{
                this.$router.push({name:'order', query : {items :  this.$front.makeOrderStr(this.orderlist), islogin : this.$store.state.isLogin}});
              },
              ()=>{
                this.$storage.setSessionStorage('redirectPath', {name : 'cart'});
                this.$router.replace({name:'member-login'});
              }, '비회원구매','로그인'
          );
        }
      }
    },
    //재입고 모달 열기
    openRestockModal(item){
      if(this.$store.state.isLogin) {
        this.$eventBus.$emit('showModal', RestockAlarm, 'restockAlarmModal', item);
      } else {
        this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
          this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, query : this.$route.query});
          this.$router.push({name : 'member-login'});
        });
      }
    }
  },
}
