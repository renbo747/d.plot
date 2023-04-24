import AddressList from './popup/AddressList.vue'
import Address from './popup/Address.vue'
import Post from "@views.common/components/ui/modal/Post.vue"
import Delivery from './popup/Delivery.vue'
import CouponDownload from './popup/CouponDownload.vue'
import CouponChange from './popup/CouponChange.vue'
import Terms from '@views/front/common/components/ui/modal/Terms.vue'
import Order from '@views.mobile/order/Order.js'

export default {
    mixins:[Order],
    beforeCreate() {
        if(sessionStorage.getItem('refresh') == 'true') {
            sessionStorage.removeItem('refresh');
            window.location.reload();
        }
        this.$store.commit("onSubHeaderOption", {
            title: "주문/결제",
            searchIcon: false,
            cartIcon: false,
            homeIcon: false,
        });
    },
    data() {
      return {
        skey : 0,
        isnonmember : 'F',
        userno : 0,
        isemp : false,
        timer: null, //타이머
        timeout: 0, // 인증번호 입력 남은시간(초)
        hhmm: '', // timeout을 mm:ss로 변환해서 화면에 표시하는 computed 변수
        nonMemberInfo : {
            isAdult : false,
            isAgree : false,
            isAuth : false,
            isSend : false,
            isSame : false,
            authnum : '',
            ordname : '',
            ordemail : '',
            ordtel : '',
        },
        //배송지주소 처리용
        addr : {
            rcvname : '',
            rcvtel1 : '',
            rcvaddr : '',
            rcvaddrdetail : '',
            rcvaddrroad : '',
            rcvaddrdetailroad : '',
            rcvpost : '',
            rcvsigungucode : '',
            rcvbuildingcode : '',
            rcvroadnamecode : '', 
            isolatetype : 'N' // N:일반, J:제주, I:도서산간
        },
        reqList : [],
        rcvreqtype : '',
        directinput : false,
        rcvreqdetail : '',
        //카드사목록
        card : '',
        cardList : [],
        //주문상품 목록 처리용
        orderlist : [],
        items : [],
        dealers : [],
        //dealerItems : [],
        //사은품 처리용
        orgGiftList : [],
        firstGiftList : [],
        giftList : [],
        selectGiftList : [],
        //쿠폰목록
        couponList : [],
        cartCoupon : null,
        cartCouponIdx : null,
        cartCouponList : [],
        delivCoupon : null,
        delivCouponIdx : null,
        goodDelivCouponIdx : null,
        delivCouponList : [],
        downCouponList : [],
        //결제수단
        paywayList : [],
        //회원정보
        memberInfo : {
            userno: 0,
            userid: '',
            name: '',
            memlvtype: '',
            memlvtypenm: '',
            membertype: '',
            respoint: 0,
            epoint: 0,
            emppoint: 0,
        },
        epoint : {},
        //포인트처리용
        chk : {
            respoint : false,
            epoint : false,
            emppoint : false
        },
        //포인트 사용금액
        usepoint : {
            reservetotamt : 0, //적립금 총사용금액
            epointtotamt : 0, //D포인트총사용금액
            empreservetotamt : 0 //임직원적립금총사용금액
        },
        //총합계
        totAmt : {
            ordtotprice : 0,        //주문총금액
            totsalepromoamt : 0,    //총프로모션할인금액
            totgoodscpnamt : 0,     //총상품쿠폰할인금액
            basketcpnamt : 0,       //장바구니쿠폰할인금액
            totcpnamt : 0,          //총할인금액
            dadadelivamt: 0,        //자사 총배송비금액
            ptndelivamt: 0,         //파트너 총배송비금액
            totdelivcpnamt : 0,     //총배송비쿠폰할인금액
            paytotprice : 0,        //결제총금액
            rpaytotprice : 0        //실제결제금액
        },
        reserve : 0, //적립금퍼센트
        reserveAmt : 0, //적립예상금액
        payInfo : {
          paywaytype : 'PWT001', //결재수단
          cardCompany : '',
          cardInstallmentPlan : '0',
          useEscrow : false, //에스크로사용여부
          //cashreceipttype : '미발급', // 현금영수증 발급여부
          //registrationNumber : '' //휴대폰번호,주민등록번호,사업자등록번호,현금영수증
        },
        isfrstorder : 'F',
        orgaddrlist : [],
        cardPlan : [],
        cashReceipt: [
          {
            id: "cashreceipt1",
            label: "소득공제"
          },
          {
            id: "cashreceipt2",
            label: "지출증빙"
          },
          {
            id: "cashreceipt3",
            label: "미발행"
          },
        ],
        cashReceiptType: {
            isDisabled : false,
            selected : '',
            cashreceipttype : '미발행',
            registrationNumber : '',
            type : [
                {value : 'HP', label : '휴대폰번호'},
                {value : 'RRN', label : '현금영수증카드번호'},
                {value : 'BIZNO', label : '사업자번호'}
            ]
        },
        agreeTerm : {
          allagree : false,  
          agree : [false, false, false]
        },
        termList : [],
      };
    },
    mounted() {
        if (this.$util.isEmpty(this.$route.query.items)) {
            this.$eventBus.$emit('alert', '알림', '결재할 상품을 없습니다.', () => {
                this.$router.go(-1);
            });
        } else {
            const order = {
                items : this.$route.query.items,
                islogin : this.$route.query.islogin,
            }
            this.$storage.setSessionStorage('orderinfo', order);

            this.apiTerm();
            this.getOrderList();
        }
    },
    methods: {
        /*********************
         * 이용약관 목록 조회
         *********************/
        apiTerm() {
            this.$util.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            this.$util.debug("이용약관");
            this.$util.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            let param = {
                termslist: ['TRT002', 'TRT004', 'TRT012']
            }
            this.$http.post('/member/signup/term', param).then(result => {
                if (result.statusCode == 200) {
                    var list = result.data.list;
                    for (var i = 0; i < list.length; i++) {
                        list[i].checked = false;
                        list[i].id = "agreeChk" + (i + 1);
                        list[i].label = list[i].prefix + list[i].termstypename;
                    }
                    this.termList = list;
                    this.$util.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    this.$util.debug(this.termList);
                    this.$util.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                }
            });
        },
        getOrderList() {
            const param = {
                items : this.$front.parseOrderStr(this.$route.query.items)
            };
            
            this.$http.post('/order/list', param).then(result => {
                if (result.statusCode == 200) {
                    if((this.$route.query.islogin == 'true' || this.$route.query.islogin == true) && result.data.memberinfo.authtype != 'member'){
                      this.$eventBus.$emit('alert', '알림', '로그아웃되었습니다. 다시 로그인해주세요');
                      this.$router.replace('/member/login');
                      return;
                    }

                    if(result.data.memberinfo.authtype == 'nonMember') {
                        this.isnonmember = 'T';
                    }
                    this.userno = result.data.memberinfo.userno;

                    this.orderlist = result.data.orderinfo.items;

                    //다운로드 쿠폰목록
                    this.getDownCouponList();

                    //현재 선택되어 있는 배송지 정보
                    this.orgaddrlist = result.data.addr;

                    //배송지주소
                    this.selectAddr(result.data.addr);

                    //첫구매여부
                    //this.isfrstorder = result.data.orderpaycnt > 0 ? 'T' : 'F';
                    this.isfrstorder = result.data.isfrstorder;
                    //배송요청사항
                    this.reqList = result.data.reqlist;
                    
                    //카드사목록
                    this.cardList = result.data.cardlist;
                    
                    //할부
                    this.cardPlan.push({value : '0', label : '일시불'});
                    for(let i = 2 ; i <= 12 ; i++) {
                        this.cardPlan.push({value : i + '', label : i+'개월'});
                    }
                    
                    //회원정보
                    this.memberInfo = result.data.memberinfo;
                    if(this.memberInfo.authtype == 'member' && this.memberInfo.membertype != 'DMT001' && this.memberInfo.membertype != 'DMT002') {
                        this.isemp = true;
                    }
                    
                    //네이버페이 검수계정(검수완료 후 삭제 또는 주석처리)
                    /*if(process.env.NODE_ENV == 'prod') {
                        for(let i = result.data.paywaylist.length -1 ; i >= 0  ; i--) {
                            if(result.data.paywaylist[i].cmcode == 'PWT005'){
                                if(this.memberInfo.userid != 'drkim007') {
                                    result.data.paywaylist.splice(i, 1);
                                }
                            }
                        }
                    }*/

                    //결제수단
                    this.paywayList = result.data.paywaylist;
                                        
                    //적립금퍼센트
                    this.reserve = result.data.reserve;

                    //총계산금액
                    this.totAmt = result.data.orderinfo.totamt;

                    //EPOINT
                    result.data.epoint.remainpoint = result.data.epoint.paypoint - result.data.epoint.usedpoint;
                    this.epoint = result.data.epoint;
                    
                    //쿠폰목록
                    this.setCouponList(result.data.couponlist);

                    //상품목록
                    const list = result.data.orderinfo.items;

                    for(let i = 0 ; i < list.length ; i++) {
                        //상품쿠폰초기화
                        list[i].coupon = {
                            cpninfoidx : null,
                            usecnt : 1
                        };
                        
                        // list[i].brandname = null;
                        //상품 옵션명
                        if(list[i].isaddgoods == 'F') {
                          list[i].optionname = '옵션 : ' + list[i].optionname;
                        } else {
                          list[i].optionname = '추가상품 : ' + list[i].optionname;
                        }

                        let optionnames = list[i].optionname.split('|');
                        list[i].opthtml = '';
                        if(!this.$util.isBlank(list[i].optionname) && optionnames.length > 0) {
                          for(let i = 0 ; i < optionnames.length ; i++) {
                            optionnames[i] = '<span>' + optionnames[i] + '</span>';
                          } 
                          list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');  
                        }
                        
                        //판매자
                        let idx = this.$front.containIdx(this.dealers, "dealerno", list[i].dealerno);
                        if(idx == -1) {
                            var dealer = {
                                dealerno : list[i].dealerno,
                                dealernm : list[i].dealernm,
                                delivamt : 0
                            }
                            this.$set(dealer, 'items', []);
                            this.dealers.push(dealer);
                        }
                    }
                    
                    // gtmDataLayer 데이터 작업
                    let ecommerce = new Object();
                    let items =  new Array();
                    let numcnt = 0;
                    for (let i = 0; i < this.orderlist.length; i++) {
                        let item1 = new Object();
                        item1.item_id = this.orderlist[i].goodscode;
                        item1.id = this.orderlist[i].goodscode;
                        item1.item_name = this.orderlist[i].goodsname;
                        item1.price = this.orderlist[i].marketprice;
                        item1.quantity = this.orderlist[i].ordcnt;
                        numcnt += this.orderlist[i].ordcnt;
                        item1.item_brand = this.orderlist[i].brandname;
                        item1.item_category = this.orderlist[i].fullcategoryname;
                        item1.item_variant = this.orderlist[i].fulloptionnm;
                        items.push(item1);
                    }
                    ecommerce.value = this.totAmt;
                    ecommerce.items = items;
                    ecommerce.num_items = this.orderlist.length;
                    ecommerce.num_quantity = numcnt;
                    this.$front.gtmDataLayer('begin_checkout', ecommerce);
                    // gtmDataLayer 데이터 작업 끝

                    //입점사와 입점사상품 세팅
                    for(let i = 0 ; i < this.dealers.length ; i++) {
                        for(let j = 0 ; j < list.length; j++) {
                            if(this.dealers[i].dealerno == list[j].dealerno) {
                                this.dealers[i].items.push(list[j]);
                            }
                        }
                        this._initCoupon(this.dealers[i].items);
                    }

                    this.setFilterCoupon();
                    this.getCalAmt();
                }
            });
        },
        //쿠폰 종류별 세팅
        setCouponList(couponList) {
            this.couponList = [];
            this.cartCouponList = [];
            this.delivCouponList = [];
            for(let i = 0 ; i < couponList.length ; i++) {
                couponList[i].value=couponList[i].cpnmisidx;
                couponList[i].label=couponList[i].cpnname;

                if(couponList[i].comcpntype == 'CCT001') {
                    if(couponList[i].isorderlimit == 'T' && couponList[i].orlimitamt > (couponList[i].saleamt * couponList[i].ordcnt)) continue;
                    couponList[i].value=couponList[i].cpninfoidx;
                    this.couponList.push(couponList[i]);
                } else if(couponList[i].comcpntype == 'CCT002') {
                    let idx = this.$front.containIdx(this.cartCouponList, "cpninfoidx", couponList[i].cpninfoidx);
                    if(idx == -1) {
                        this.cartCouponList.push(couponList[i]);
                    }
                } else {
                    this.delivCouponList.push(couponList[i]);
                }
            }
        },
        //장바구니쿠폰, 배송비쿠폰 수정
        setFilterCoupon() {
            //사용가능기준금액 이하쿠폰 삭제
            for(let i = this.cartCouponList.length - 1 ; i >= 0 ; i--) {
                if(this.cartCouponList[i].isorderlimit == 'T' && this.cartCouponList[i].orlimitamt > (this.totAmt.ordtotprice - this.totAmt.totsalepromoamt)){
                    this.cartCouponList.splice(i, 1);
                }
            }

            if(this.cartCouponList.length == 0) {
                this.cartCouponIdx = 'emptyCoupon';
                this.cartCouponList.push({value : 'emptyCoupon', label : '적용할 수 있는 쿠폰이 없습니다.', cpnmisidx : 'noCoupon'});
            } else if(this.cartCouponList.length < 2) {
                if(this.cartCouponList[0].value != 'emptyCoupon') {
                    this.cartCouponList.unshift({value : 'noCoupon', label : '적용안함', cpnmisidx : 'noCoupon'});
                    if(this.cartCouponList[1].ispercent == 'F') {
                        this.cartCouponList[1].discountamt = this.cartCouponList[1].disprice;
                    } else {
                        this.cartCouponList[1].discountamt = this.totAmt.ordtotprice * (this.cartCouponList[1].dispercent/100);
                        if(this.cartCouponList[1].maxdisamt2 < this.cartCouponList[1].discountamt) {
                            this.cartCouponList[1].discountamt = this.cartCouponList[1].maxdisamt2;
                        }
                    }
                }
                if(this.cartCouponIdx == null) {
                    this.cartCouponIdx = this.cartCouponList[1].cpnmisidx;
                    this.cartCoupon = this.cartCouponList[1];
                } else if(this.cartCouponIdx == 'emptyCoupon') {
                    this.cartCouponIdx = '';
                }
            } else {
                let isExistNoCoupon = false;
                if(this.cartCouponList[0].value == 'emptyCoupon') {
                    this.cartCouponList.splice(0, 1);
                }
                for(let i = 0 ; i < this.cartCouponList.length ; i++) {
                    if(this.cartCouponList[i].value == 'noCoupon') {
                        isExistNoCoupon = true;
                        continue;
                    }
                    if(this.cartCouponList[i].ispercent == 'F') {
                        this.cartCouponList[i].discountamt = this.cartCouponList[i].disprice;
                    } else {
                        this.cartCouponList[i].discountamt = this.totAmt.ordtotprice * (this.cartCouponList[i].dispercent/100);
                        if(this.cartCouponList[i].maxdisamt2 < this.cartCouponList[i].discountamt) {
                            this.cartCouponList[i].discountamt = this.cartCouponList[i].maxdisamt2;
                        }
                    }
                }
                if(!isExistNoCoupon){
                    this.cartCouponList.unshift({value : 'noCoupon', label : '적용안함', cpnmisidx : 'noCoupon'});
                }
                if(this.cartCouponIdx == null) {
                    this.cartCouponIdx = this.cartCouponList[1].cpnmisidx;
                    this.cartCoupon = this.cartCouponList[1];
                } else if(this.cartCouponIdx == 'emptyCoupon') {
                    this.cartCouponIdx = '';
                }
            }
            
            let temp = [];
            for(let i = this.delivCouponList.length - 1 ; i >= 0 ; i--) {
                if((this.delivCouponList[i].saleamt * this.delivCouponList[i].ordcnt) < this.delivCouponList[i].orlimitamt) {
                    this.delivCouponList.splice(i, 1);
                }
            }
            
            for(let i = 0 ; i < this.delivCouponList.length ; i++) {
                //주문상품 배송비 체크(0원일경우 쿠폰제외, )
                let delivamt = 0;
                for(let j = 0 ; j < this.orderlist.length ; j++) {
                    if(this.delivCouponList[i].goodsno == this.orderlist[j].goodsno
                        && this.delivCouponList[i].optioncode == this.orderlist[j].optioncode) {
                        delivamt = this.orderlist[j].delivamt;
                    }
                }
                
                if(delivamt == 0) {
                    continue;
                } else {
                    if(this.delivCouponList[i].maxdisamt2 > delivamt){
                        this.delivCouponList[i].discountamt = delivamt;
                    } else {
                        this.delivCouponList[i].discountamt = this.delivCouponList[i].maxdisamt2;
                    }
                }

                //배송비쿠폰별 상품구조 변경
                let deliv = this.$util.deepClone(this.delivCouponList[i]);
                
                let idx = this.$front.containIdx(temp, 'cpnmisidx', deliv.cpnmisidx);
                
                if(idx == -1) {
                    deliv.detail = [];
                    deliv.detail.push({value : 'noCoupon', label : '적용안함', cpnmisidx : 'noCoupon'});
                    this.delivCouponList[i].value = this.delivCouponList[i].cpnmisidx;
                    this.delivCouponList[i].label = this.delivCouponList[i].goodsname + ' (' + this.delivCouponList[i].optionname + ')';
                    deliv.detail.push(this.$util.deepClone(this.delivCouponList[i]));
                    temp.push(deliv);
                } else {
                    deliv.value = deliv.cpnmisidx;
                    deliv.label = deliv.goodsname + ' (' + deliv.optionname + ')';
                    temp[idx].detail.push(deliv);
                }
            }
            this.delivCouponList = temp;

            if(this.delivCouponList.length == 0) {
                this.delivCouponIdx = 'emptyCoupon';
                this.delivCouponList.push({value : 'emptyCoupon', label : '적용할 수 있는 쿠폰이 없습니다.', cpnmisidx : 'emptyCoupon'});
            } else {
                this.delivCouponList.unshift({value : 'noCoupon', label : '적용안함', cpnmisidx : 'noCoupon'});
                if(this.delivCouponIdx == null) {
                    this.delivCouponIdx = this.delivCouponList[1].cpnmisidx;
                    this.goodDelivCouponIdx = this.delivCouponList[1].cpnmisidx;
                    this.delivCoupon = this.delivCouponList[1];
                } else if(this.delivCouponIdx == 'emptyCoupon') {
                    this.delivCouponIdx = '';
                }
            }
        },
        // 상품별 초기쿠폰설정
        _initCoupon(items) {
            this.$front.setGoodsCouponList(items, this.couponList);
            for(let i = 0 ; i < items.length ; i++) {
                if(items[i].couponList.length > 1){
                    let coupon = this.$util.deepClone(items[i].couponList[1]);
                    if(coupon.isdirectcoupon == 'T') {
                        coupon.usecnt = items[i].ordcnt;
                    } else {
                        if(items[i].ordcnt > coupon.remaincnt) {
                            coupon.usecnt = coupon.remaincnt;
                        } else {
                            coupon.usecnt = items[i].ordcnt;
                        }
                    }
                    items[i].coupon = coupon;
                    //this.$front.setCouponUsed(items[i].coupon, this.couponList);
                }
                this.$front.setGoodsCouponList(items, this.couponList);
            }
        },
        //요청사항 변경이벤트
        reqtypeChange() {
            if(this.rcvreqtype == 'RVT006') {
                this.directinput = true;
            } else {
                this.rcvreqdetail = '';
                this.directinput = false;
            }
        },
        //금액계산처리
        getCalAmt(callback) {
            let param = Object.assign({},this.usepoint);
            param.isolatetype = this.addr.isolatetype;
            param.isfirst = this.isfrstorder;
            param.items = [];
            param.coupon = [];
            param.delivCoupon = this.delivCoupon;
            param.cartCoupon = this.cartCoupon;
            param.isloading = false;
            
            for(let i = 0 ; i < this.dealers.length; i++) {
                let items = this.dealers[i].items;

                for(let j = 0 ; j < items.length ; j++) {
                    let temp = this.$util.deepClone(items[j]);
                    if(this.$util.isBlank(temp.coupon.cpninfoidx) || temp.coupon.cpninfoidx == 'emptyCoupon' || temp.coupon.cpninfoidx == 'noCoupon') {
                        temp.goodscpnidx = null;
                        temp.usecnt = 0;
                    } else {
                        temp.goodscpnidx = temp.coupon.comcpnidx;
                        temp.usecnt = temp.coupon.usecnt;
                        param.coupon.push(temp.coupon);
                    }
                    delete temp['couponList'];
                    delete temp['cntList'];
                    delete temp['coupon'];
                    param.items.push(temp);
                }
            }
            
            this.$http.post('/order/cal', param).then(result => {
                if (result.statusCode == 200) {
                    this.totAmt = result.data.totamt;
                    this.totAmt.totdelivamt = this.totAmt.dadadelivamt + this.totAmt.ptndelivamt;
                    this.usepoint = result.data.usepoint;
                    
                    if(this.usepoint.reservetotamt == 0) {
                        this.chk.respoint = false;
                    }
                    if(this.usepoint.epointtotamt == 0) {
                        this.chk.epoint = false;
                    }
                    if(this.usepoint.empreservetotamt == 0) {
                        this.chk.emppoint = false;
                    }

                    //사은품목록
                    this.orgGiftList = result.data.giftlist;
                    this._setGiftList(this.orgGiftList);

                    this.afterCalAmt(result.data.items);
                    if(callback) {
                        callback();
                    }
                }
            });
        },
        //금액계산후 처리
        afterCalAmt(list) {
            this.orderlist = [];

            this.reserveAmt = 0;
            for(let i = 0 ; i < this.dealers.length ; i++) {
                let items = this.dealers[i].items;
                this.dealers[i].delivamt = 0;
                this.dealers[i].adddelivamt = 0;
                this.dealers[i].totpromoamt = 0;
                this.dealers[i].totcouponamt = 0;
                
                for(let j = 0 ; j < items.length ; j++) {
                    for(let y = 0 ; y < list.length ; y++) {
                        if(items[j].goodsno == list[y].goodsno 
                            && items[j].optioncode == list[y].optioncode) {
                            Object.assign(items[j], list[y]);
                        }
                    }
                    
                    this.dealers[i].delivamt += items[j].delivamt;
                    this.dealers[i].adddelivamt += items[j].adddelivamt;
                    this.dealers[i].totpromoamt += items[j].salepromoamt;
                    this.dealers[i].totcouponamt += items[j].goodscpnamt;

                    //주문목록
                    let temp = this.$util.deepClone(items[j]);
                    temp.delivamt = temp.delivamt + temp.adddelivamt;
                    if(temp.goodscpnidx == 'emptyCoupon' || temp.goodscpnidx == 'noCoupon'){
                        temp.goodscpnidx = 0;
                    }
                    delete temp['couponList'];
                    delete temp['cntList'];

                    if(this.reserve != null) {
                        const tempamt = items[j].realgoodsamt - items[j].reserveamt - items[j].epointamt - items[j].basketdivamt;  
                        this.reserveAmt = this.reserveAmt + Math.round(tempamt * (this.reserve.purcfmamt / 100));
                    }

                    this.orderlist.push(temp);
                }
            }
            //적립금 기간내 n배
            if(this.reserve != null && this.reserve.ismulti == 'T') {
                this.reserveAmt = this.reserveAmt * this.reserve.cfmmulti;
            }

        },
        afterPointChange() {
            //실제결제금액 (결제총금액 - 사용포인트)
            this.totAmt.rpaytotprice = this.totAmt.paytotprice - Number(this.usepoint.reservetotamt)  - Number(this.usepoint.epointtotamt) - Number(this.usepoint.empreservetotamt);
        },
        //사은품 모델변경
        _setGiftList(list) {
            //기존 선택된 사은품
            this.selectGiftList = [];
            for(let i = 0 ; i < this.giftList.length ; i++) {
                for(let j = 0 ; j < this.giftList[i].detail.length ; j++) {
                    for(let y = 0 ; y < this.giftList[i].detail[j].length ; y++) {
                        if(this.giftList[i].detail[j][y].chk) {
                            this.selectGiftList.push(this.giftList[i].detail[j][y]);
                        }
                    }
                }
            }

            this.giftList = [];
            for(let i = 0 ; i < list.length ; i++) {
                list[i].id = list[i].giftpromoidx + '_' + list[i].giftidx;
                let chk = false;
                for(let j = 0 ; j < this.selectGiftList.length ; j++) {
                    if(list[i].giftpromoidx == this.selectGiftList[j].giftpromoidx 
                        && list[i].giftidx == this.selectGiftList[j].giftidx){
                        chk = true;
                    }
                }
                list[i].chk = chk;
                
                //사은품구조 변경
                const temp = this.$util.deepClone(list[i]);
                let idx = this.$front.containIdx(this.giftList, "giftpromoidx", temp.giftpromoidx);

                if(idx == -1) {
                    temp.temp = [];
                    if(list[i].gifttermtype == 'GFT004') {
                        list[i].chk = true;
                    }
                    temp.temp.push(this.$util.deepClone(list[i]));
                    this.giftList.push(temp);
                } else {
                    //첫구매시사은품은 한개만지급
                    if(temp.gifttermtype != 'GFT004') {
                        this.giftList[idx].temp.push(temp);
                    }
                }
            }
            
            for(let i = 0 ; i < this.giftList.length ; i++) {
                if(window.sessionStorage.getItem('platform') == 'MAC001'){
                    this.giftList[i].detail = this.$util.division(this.giftList[i].temp, 3);
                } else {
                    this.giftList[i].detail = this.$util.division(this.giftList[i].temp, 2);
                }
            }
            this.selectGiftList = [];
        },
        // 사은품 체크클릭 이벤트
        changeGiftChk(gift, goods) {
            let cnt = 0;
            for(let i = 0 ; i < gift.detail.length ; i++) {
                for(let j = 0 ; j < gift.detail[i].length ; j++) {
                    if(gift.detail[i][j].chk) {
                        cnt++;
                    }
                }
            }
            
            if(cnt > gift.giftselcnt) {
                this.$nextTick(()=>{
                    goods.chk = false;
                    this.$eventBus.$emit('alert', '알림', "최대 " + gift.giftselcnt + "만 선택가능합니다.");
                });
            }
        },
        //결제수단수정
        changePayWayType() {
            this.payInfo.cardCompany = '';
            this.cashReceiptType.cashreceipttype = '미발행';
            this.cashReceiptType.selected = '';
            this.cashReceiptType.registrationNumber = '';
        },
        //현금영수증 구분변경
        changeCashReceiptType() {
            switch (this.cashReceiptType.cashreceipttype) {
              case '소득공제':
                this.cashReceiptType.type = [
                    {value : 'HP', label : '휴대폰번호'},
                    {value : 'RRN', label : '현금영수증카드번호'}
                ];
                this.cashReceiptType.selected = 'HP';
                this.cashReceiptType.isDisabled = false;
                break;
              case '지출증빙':
                this.cashReceiptType.type = [
                    {value : 'BIZNO', label : '사업자번호'},
                ];
                this.cashReceiptType.selected = 'BIZNO';
                this.cashReceiptType.isDisabled = true;
                break;
              default:
                this.cashReceiptType.selected = '';
                this.cashReceiptType.isDisabled = false;
                break;
            }
            this.cashReceiptType.registrationNumber = '';
        },
        // 주문결제 전체동의
        checkAllAgree() {
            for(let i = 0 ; i < this.agreeTerm.agree.length ; i++) {
                this.agreeTerm.agree[i] = this.agreeTerm.allagree;
            }
        },
        // 동의하기 버튼
        changeAgree() {
            let cnt = 0;
            for(let i = 0 ; i < this.agreeTerm.agree.length ; i++) {
                if(this.agreeTerm.agree[i]) cnt++;
            }
            if(this.agreeTerm.agree.length == cnt) {
                this.agreeTerm.allagree = true;
            } else {
                this.agreeTerm.allagree = false;
            }
        },
        // 약관모달
        handleTermsModal() {
            
        },
        // 결제하기 버튼클릭
        orderPayment() {
          if(this.isnonmember == 'T') {
              if(!this.nonMemberInfo.isAdult) {
                this.$eventBus.$emit('alert', '알림', '만14세 미만인 경우, 구매가 불가합니다.');
                return;      
              } else if (!this.nonMemberInfo.isAgree) {
                this.$eventBus.$emit('alert', '알림', '비회원 개인정보 수집 및 활용동의해주세요.');
                return;
              } else if(this.$util.isBlank(this.nonMemberInfo.ordname)
                        || this.$util.isBlank(this.nonMemberInfo.ordtel)
                        || this.$util.isBlank(this.nonMemberInfo.ordemail)) {
                this.$eventBus.$emit('alert', '알림', '주문자정보를 입력해주세요.');
                return;      
              } else if(!this.$util.isEmail(this.nonMemberInfo.ordemail)) {
                this.$eventBus.$emit('alert', '알림', '이메일 형식이 아닙니다.');
                return;      
              } else if(!this.nonMemberInfo.isAuth) {
                this.$eventBus.$emit('alert', '알림', '비회원 주문인증을 받으세요.');
                return;      
              } else if(!this.nonMemberInfo.isAuth) {
                this.$eventBus.$emit('alert', '알림', '비회원 주문인증을 받으세요.');
                return;      
              }
          }

          if(this.$util.isBlank(this.addr.rcvname)
            ||this.$util.isBlank(this.addr.rcvtel1)
            ||this.$util.isBlank(this.addr.rcvpost)) {
            this.$eventBus.$emit('alert', '알림', '배송지정보를 입력해주세요.');
            return;      
          }

          if(!this.agreeTerm.allagree) {
            this.$eventBus.$emit('alert', '알림', '이용약관 모두 동의해주세요.');
            return;
          }

          if(this.cashReceiptType.cashreceipttype != '미발행' && this.$util.isBlank(this.cashReceiptType.registrationNumber)) {
            this.$eventBus.$emit('alert', '알림', '현금영수증 정보를 입력해주세요.');
            return;
          }

          if(this.giftList.length == 0) {
            this.checkAuth();
          } else {
            this.checkGiftList(0);
          }
        },
        checkGiftList(idx) {
            let cnt = 0;
            let chkCnt = 0;
            for(let j = 0 ; j < this.giftList[idx].detail.length ; j++) {
                for(let y = 0 ; y < this.giftList[idx].detail[j].length ; y++) {
                    cnt++;
                    if(this.giftList[idx].detail[j][y].chk) {
                        chkCnt++;
                    }
                }
            }
            if(chkCnt == 0) {
                this.$eventBus.$emit('confirm', '사은품선택', '사은품을 선택하지 않으셨습니다.<br/>사은품을 받지 않으시겠습니까?',()=>{
                    //this.afterCheckGift(idx);
                    this.checkAuth();
                })
            } else if(cnt > 0 && this.giftList[idx].giftselcnt > chkCnt){
                this.$eventBus.$emit('confirm', '사은품선택', chkCnt + '개의 사은품만 받으시겠어요?<br/>(최대 ' + this.giftList[idx].giftselcnt + ' 개 선택가능)',()=>{
                    //this.afterCheckGift(idx);
                    this.checkAuth();
                })
            } else {
                this.afterCheckGift(idx);
            }
        },
        afterCheckGift(idx) {
            if(idx + 1 == this.giftList.length) {
                this.checkAuth();
            } else {
                idx++;
                this.checkGiftList(idx);
            }
        },
        //주문전 세션체크
        checkAuth() {
            this.$http.post('/member/checkAuth', {isloading : false}).then(result => {
                if(result.statusCode == 200) {
                  this.saveOrder();
                } else {
                  if((this.$route.query.islogin == 'true' || this.$route.query.islogin == true)){
                    this.$eventBus.$emit('alert', '알림', '로그아웃되었습니다. 다시 로그인해주세요');
                    this.$router.replace('/member/login');
                    return;
                  }
                  this.saveOrder();
                }
            });
        },
        //주문저장처리
        saveOrder() {
            // 선택된 사은품 목록
            this.selectGiftList = [];
            for(let i = 0 ; i < this.giftList.length ; i++) {
                for(let j = 0 ; j < this.giftList[i].detail.length ; j++) {
                    for(let y = 0 ; y < this.giftList[i].detail[j].length ; y++) {
                        if(this.giftList[i].detail[j][y].chk) {
                            this.selectGiftList.push(this.giftList[i].detail[j][y]);
                        }
                    }
                }
            }

            this.$http.post('/order/orderno', {}).then(result => {
                if (result.statusCode == 200) {
                    //결제정보 세팅
                    let orderInfo = {
                        ordno : result.data.orderno,
                        rcvreqtype : this.rcvreqtype,
                        rcvreqdetail : this.rcvreqdetail,
                        orderlist : this.orderlist,
                        giftlist : this.selectGiftList,
                        cartCoupon : this.cartCoupon,
                        delivCoupon : this.delivCoupon,
                        isfrstorder : this.isfrstorder
                    }
                    //주소
                    orderInfo = Object.assign({}, orderInfo, this.addr);
                    //주문결재금액
                    orderInfo = Object.assign({}, orderInfo, this.totAmt);
                    //포인트사용
                    orderInfo = Object.assign({}, orderInfo, this.usepoint);
                    //현금영수증
                    if(this.cashReceiptType.cashreceipttype != '미발행') {
                        orderInfo.cashreceipttype = this.cashReceiptType.cashreceipttype;
                        orderInfo.registrationnumber = this.cashReceiptType.registrationNumber;
                    }

                    //비회원일경우 주문자정보
                    if(this.isnonmember == 'T') {
                        orderInfo = Object.assign({}, orderInfo, this.nonMemberInfo);
                    }
                    orderInfo.payinfo = null;

                    if(this.totAmt.rpaytotprice > 0) {
                        orderInfo.paywaytype = this.payInfo.paywaytype;
                        const temp = this.$CryptoJS.AES.encrypt(JSON.stringify(orderInfo), result.data.orderno).toString();
                        window.sessionStorage.setItem('ORDERPAYLIST', temp);
                        this.paymentProc(result.data.orderno);
                    } else {
                        //결제금액이 없을경우
                        
                        if((this.usepoint.empreservetotamt + this.usepoint.reservetotamt) == this.totAmt.paytotprice) {
                            orderInfo.paywaytype = 'PWT009';
                        } else {
                            orderInfo.paywaytype = null;
                        }
                        //rderInfo.paywaytype = null;
                        this.orderProc(orderInfo);
                    }
                }
            });
        },
        //토스결제
        paymentProc(ordno) {
            if(this.payInfo.paywaytype == 'PWT005') {
                if(this.totAmt.rpaytotprice < 100) {
                    this.$eventBus.$emit('alert','알림','최소 100원이상 결제가 가능합니다.');
                    return;
                }

                let productItems = [];
                let ordcnt = 0;
                for(let i = 0 ; i < this.orderlist.length ; i++) {
                    productItems.push({
                        "categoryType": "PRODUCT",
                        "categoryId" : "GENERAL",
                        "uid" : this.orderlist[i].goodscode,
                        "name" : this.orderlist[i].goodsname + this.orderlist[i].optionname,
                        //"payReferrer" : "ETC",
                        "count" : Number(this.orderlist[i].ordcnt)
                    });
                    ordcnt = ordcnt + Number(this.orderlist[i].ordcnt);
                }
                // const naverParam = {
                //     "merchantUserKey": ordno,
                //     "merchantPayKey": this.userno,
                //     "productName": this.orderlist[0].goodsname,
                //     "totalPayAmount": this.totAmt.rpaytotprice,
                //     "taxScopeAmount": this.totAmt.rpaytotprice,
                //     "taxExScopeAmount": 0,
                //     "productCount": ordcnt,
                //     "returnUrl": window.location.origin + "/order-naverpaymentSucc?orderId=" + ordno + "&amount=" + this.totAmt.rpaytotprice,
                //     "productItems": productItems
                // };
                 const naverParam = {
                    //"merchantUserKey": this.userno,
                    "merchantPayKey": ordno,
                    "productName": this.orderlist[0].goodsname,
                    "totalPayAmount": this.totAmt.rpaytotprice,
                    "taxScopeAmount": this.totAmt.rpaytotprice,
                    "taxExScopeAmount": 0,
                    "productCount": ordcnt,
                    "returnUrl": window.location.origin + "/order-naverpaymentSucc?orderId=" + ordno + "&amount=" + this.totAmt.rpaytotprice,
                    "productItems": productItems
                };
                
                if(window.sessionStorage.getItem('platform') != 'MAC001') {
                    window.popupCallback = this.afterProcPayment;
                    window.name = 'NaverWindow';
                    setTimeout(() => {
                        const popupUrl = '/common/naverPayment?payinfo=' + encodeURIComponent(JSON.stringify(naverParam));
                        window.open(popupUrl, "_blank", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
                    });
                } else {
                    if(!Naver.isInitialized) {
                        Naver.Pay.create({
                            "mode" : process.env.VUE_APP_NAVER_PAY_MODE,
                            "clientId" : process.env.VUE_APP_NAVER_PAY_CLIENT_KEY,
                        })
                    }
                    Naver.Pay.open(naverParam);
                }
            } else {
                let payType = '카드';
                      
                let tossInfo = {
                    amount : this.totAmt.rpaytotprice,
                    //amount : 5000, // TEST
                    orderId : ordno,
                    orderName : this.orderlist[0].goodsname,
                    customerName : this.isnonmember == 'T' ? this.nonMemberInfo.ordname : this.memberInfo.name,
                    successUrl: window.location.origin + "/order-tossPaymentSucc",
                    failUrl: window.location.origin + "/order-tossPaymentFail",
                    //successUrl: window.location.origin + "/api/order/payment",
                    //failUrl: window.location.origin + "/etc/error/500",
                    //flowMode : 'DIRECT',
                    //easyPay : '페이코',
                    //cardCompany : 'KOOKMIN'
                }
                if(this.orderlist.length > 1) {
                    tossInfo.orderName = tossInfo.orderName + ' 외 ' + (this.orderlist.length - 1) + '건';
                }
                if(tossInfo.orderName.length > 100) {
                    tossInfo.orderName = tossInfo.orderName.substring(0,99);
                }
                tossInfo.orderName = tossInfo.orderName.replace(/&/g,' ');

                switch (this.payInfo.paywaytype) {
                    case 'PWT001':
                        payType = '카드';
                        if(this.payInfo.cardCompany != '') {
                            tossInfo.cardCompany = this.payInfo.cardCompany;
                            tossInfo.cardInstallmentPlan = this.payInfo.cardInstallmentPlan;
                            tossInfo.flowMode = 'DIRECT';
                        }
                        
                        break;
                    case 'PWT002':
                        payType = '가상계좌';
                        tossInfo.validHours = 72; //가상계좌 유효시간
                        tossInfo.useEscrow = this.payInfo.useEscrow;
                        tossInfo.cashReceipt = {
                            type : '미발행'
                        }
                        if(process.env.NODE_ENV != 'local') {
                            tossInfo.virtualAccountCallbackUrl = process.env.VUE_APP_SERVER_URL + "/api/common/toss/virtualAccount";
                        } 
                        else {
                            tossInfo.virtualAccountCallbackUrl = "http://local.mobilefactory.co.kr/api/common/toss/virtualAccount";
                        }
                        break;
                    case 'PWT003':
                        payType = '계좌이체';
                        tossInfo.useEscrow = this.payInfo.useEscrow;
                        tossInfo.cashReceipt = {
                            type : '미발행'
                        }
                        break;
                    case 'PWT004':
                        payType = '휴대폰';
                        break;
                    case 'PWT006':
                        payType = '토스결제';
                        break;
                    case 'PWT007':
                        payType = "카드";
                        tossInfo.flowMode = 'DIRECT';
                        tossInfo.easyPay = "페이코";
                        break;
                    case 'PWT008':
                        payType = "카드";
                        tossInfo.flowMode = 'DIRECT';
                        tossInfo.easyPay = "카카오페이";
                        break;
                    default:
                        payType = '카드';
                        break;
                }

                this.$util.debug("Toss결제 파라미터 : " + payType + " - " + JSON.stringify(tossInfo));
                if(window.sessionStorage.getItem('platform') != 'MAC001') {
                    window.popupCallback = this.afterProcPayment;
                    window.name = 'TossWindow';
                    setTimeout(() => {
                        const popupUrl = '/common/tossPayment?payType=' + payType + '&tossInfo=' + JSON.stringify(tossInfo);
                        window.open(popupUrl, "_blank", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
                    });
                } else {
                    const tossPayments = TossPayments(process.env.VUE_APP_TOSS_CLIENT_KEY);
                    tossPayments.requestPayment(payType, tossInfo).catch(error => {
                        if(error.code == 'USER_CANCEL') {
                            error = '사용자에 의해 결제가 취소되었습니다.';
                        }

                        this.$eventBus.$emit('alert', '알림', error);
                        
                    });
                }
            }
        },
        //팝업결제 후처리
        afterProcPayment(payInfo) {
            const temp = window.sessionStorage.getItem('ORDERPAYLIST');
            
            const orderStr = this.$CryptoJS.AES.decrypt(temp, payInfo.orderid).toString(this.CryptoJS.enc.Utf8);
            let orderInfo = JSON.parse(orderStr);
            orderInfo.payinfo = payInfo;
            //orderInfo.isloading = false;

            this.$util.debug("주문결제 목록 : " + JSON.stringify(orderInfo));
            this.orderProc(orderInfo);
        },
        //배송지변경 팝업
        changeAddr(){
            this.$eventBus.$emit('showModal', AddressList, 'addressListModal', {}, this.selectAddr);
        },
        //배송지추가 팝업
        addAddr() {
            this.$eventBus.$emit('showModal', Address, 'addressModal', {
                type : 'add',
                idx:0
            }, this.selectAddr);
        },
        //비회원주문 배송지조회 우편번호 팝업
        postAddr() {
            this.$eventBus.$emit('showModal', Post, 'postModal', {}, (address)=>{
                const isolatetype = this.addr.isolatetype;
                this.addr.rcvaddrroad = this.$util.isBlank(address.roadAddress) ? address.autoRoadAddress: address.roadAddress;      //도로명주소
                this.addr.rcvaddr     = this.$util.isBlank(address.jibunAddress) ? address.autoJibunAddress : address.jibunAddress; //지번주소
                this.addr.rcvpost     = address.zonecode;     //우편번호
                this.addr.rcvsigungucode = address.sigunguCode   //시군구코드
                this.addr.rcvbuildingcode = address.buildingCode //빌딩코드
                this.addr.rcvroadnamecode  = address.roadnameCode //도로명코드 
                this.$http.post('common/addr/isolatetype',{post: this.addr.rcvpost}).then(result => {
                    if (result.statusCode == 200) {
                        this.addr.isolatetype = result.data.isolatetype;
                        if(isolatetype != result.data.isolatetype) {
                            this.getCalAmt();
                        }
                    }
                });
            });
        },
        //비회원주문 인증번호 발송
        sendAuth() {
            if (this.$util.isBlank(this.nonMemberInfo.ordtel)) {
                this.$eventBus.$emit('alert', '알림', '연락처를 입력해주세요.');
                return;
            } else if (!this.$util.isPhone(this.nonMemberInfo.ordtel)) {
                this.$eventBus.$emit('alert', '알림', '연락처 형식이 아닙니다.');
                return;
            }

            // //TEST
            // this.nonMemberInfo.isAuth = true;
            // if(this.nonMemberInfo.isAuth) {
            //     return;
            // }

            var param = {
                emailhp: this.nonMemberInfo.ordtel
            }
    
            this.$http.post('/order/sendAuth', param).then(result => {
                // 타이머 돌고 있으면 타이머 클리어
                if (this.timer) {
                    clearInterval(this.timer);
                }
        
                if (result.statusCode == 200) {
                    this.nonMemberInfo.isSend = true;
                    this.nonMemberInfo.authnum = ''; // 입력한 인증번호 초기화
                    this.timeout = result.data.timeout; // 타이머 시간 세팅
        
                    this.countDownTimer();
                    this.$toast.clear();
                    this.$toast.open("인증번호를 발송하였습니다.");
                }
            });
        },
        /************************
         * 카운트 다운
         ************************/
        countDownTimer() {
            this.timer = setInterval(() => {
                this.timeout -= 1;
                if(this.timeout < 0) {
                    reSendAuthNum();
                    return false;
                }
                this.hhmm =  Math.floor(this.timeout / 60) + " : " + this.$util.addZero((this.timeout % 60));
            }, 1000);
        },
        //비회원주문 인증번호확인
        confirmAuth() {
            if (this.$util.isBlank(this.nonMemberInfo.ordtel)) {
                this.$eventBus.$emit('alert', '알림', '연락처를 입력해주세요.');
                return;
            }
    
            var param = {
                emailhp: this.nonMemberInfo.ordtel,
                authnum: this.nonMemberInfo.authnum
            }
            this.$http.post('/order/confirmAuth', param).then(result => {
                clearInterval(this.timer);
                if (result.statusCode == 200) {
                    this.$eventBus.$emit('alert', '알림', '인증되었습니다.');
                    this.nonMemberInfo.isAuth = true;
                } else {
                    this.nonMemberInfo.authnum = '';
                    this.nonMemberInfo.isAuth = false;
                }
            });
        },
        //배송지선택
        selectAddr(addr) {
            if(this.$util.isEmpty(addr)) {
                this.addr.isolatetype = 'N';
                return;
            }

            const isolatetype = this.addr.isolatetype;
            
            this.addr.rcvname = addr.title;
            this.addr.rcvtel1 = addr.mobile;
            this.addr.rcvaddr = addr.addr;
            this.addr.rcvaddrdetail = addr.addrdetail;
            this.addr.rcvaddrroad = addr.addrroad;
            this.addr.rcvaddrdetailroad = addr.addrdetailroad;
            this.addr.rcvpost = addr.post;
            this.addr.isolatetype = addr.isolatetype;
            this.addr.isdefault = addr.isdefault;
            this.addr.rcvsigungucode = addr.sigungucode;
            this.addr.rcvbuildingcode = addr.buildingcode;
            this.addr.rcvroadnamecode = addr.roadnamecode;
            if(this.addr.isolatetype != 'N') {
              this.$eventBus.$emit('alert', '알림', '도서/산간지역은 추가배송료가 발생할 수 있습니다.');
            }
            // if(isolatetype != addr.isolatetype) {
            if(this.orgaddrlist.isolatetype != addr.isolatetype) {
                this.getCalAmt();
                this.orgaddrlist = addr;
            }
        },
        //비회원 주문 주문자동일체크
        sameOrder() {
            if(this.nonMemberInfo.isSame) {
                this.addr.rcvname = this.nonMemberInfo.ordname;
                this.addr.rcvtel1 = this.nonMemberInfo.ordtel;
            } else {
                this.addr.rcvname = '';
                this.addr.rcvtel1 = '';
            }
        },
        //배송비 상세
        delivDetail(){
            this.$eventBus.$emit('showModal', Delivery, 'deliveryModal', {dealers : this.dealers, totAmt : this.totAmt, isolatetype : this.addr.isolatetype});
        },
        //쿠폰변경 팝업
        couponChange(){
            if(this.isnonmember == 'T') {
                this.$eventBus.$emit('alert', '알림', '회원으로 구매 시 쿠폰혜택을 받으실 수 있습니다.');
                return;
            }
            this.$eventBus.$emit('showModal', 
                CouponChange, 
                'couponApplyModal', 
                {
                    isemp : this.isemp,
                    dealers : this.$util.deepClone(this.dealers),
                    couponList : this.couponList,
                    cartCouponList : this.cartCouponList,
                    delivCouponList : this.delivCouponList,
                    cartCouponIdx : this.cartCouponIdx,
                    delivCouponIdx : this.delivCouponIdx,
                    goodDelivCouponIdx : this.goodDelivCouponIdx,
                },
                (dealers, cartCoupon, delivCoupon, cartCouponIdx, delivCouponIdx, goodDelivCouponIdx) => {
                    this.dealers = dealers;
                    this.cartCoupon = cartCoupon;
                    this.cartCouponIdx = cartCouponIdx;
                    this.delivCoupon = delivCoupon;
                    this.delivCouponIdx = delivCouponIdx;
                    this.goodDelivCouponIdx = goodDelivCouponIdx;
                    this.getCalAmt();
                }
            );
        },
        getDownCouponList(callback) {
            let param = {
                isloading : false,
                items : this.orderlist,
                isoption : 'T'
            }
            this.$http.post('/coupon/order/list', param).then(result => {
                this.downCouponList = result.data.list;
                if(callback) {
                    callback();
                }
            });
        },
        //쿠폰다운로드 팝업
        couponDownload(){
            if(this.isnonmember == 'F') {
                // /api/coupon/order/list 를 페이지 로딩 시 호출, "쿠폰 더 받기" 누를때 한번 더 호출해서 부하기 심하므로 페이지 로딩 때 불러온 데이터를 사용하고 한번만 호출하도록 변경(2022-09-23, James)
                //this.getDownCouponList(() => {  
                    if(this.downCouponList.length == 0) {
                      this.$eventBus.$emit('alert', '알림', '다운로드 가능한 쿠폰이 없습니다.');
                      return;
                    }
                    let param = {
                      isloading : false,
                      items : this.orderlist,
                      isoption : 'T',
                      couponList : this.downCouponList
                    }
                    this.$eventBus.$emit('showModal', CouponDownload, 'couponAddModal', param, (couponList)=>{
                        for(let i = 0 ; i < couponList.length ; i++) {
                            couponList[i].value=couponList[i].cpnmisidx;
                            couponList[i].label=couponList[i].cpnname;
                            
                            if(couponList[i].comcpntype == 'CCT001') {
                                if(couponList[i].isorderlimit == 'T' && couponList[i].orlimitamt > (couponList[i].saleamt * couponList[i].ordcnt)) continue;
                                couponList[i].value=couponList[i].cpninfoidx;
    
                                let idx = this.$front.containIdx(this.couponList, 'cpninfoidx', couponList[i].cpninfoidx);
                                if(idx == -1) {
                                    this.couponList.push(couponList[i]);
                                    continue;
                                } else {
                                    this.couponList[idx] = couponList[i];
                                }
                            } else if(couponList[i].comcpntype == 'CCT002') {
                                let idx = this.$front.containIdx(this.cartCouponList, 'cpnmisidx', couponList[i].cpnmisidx);
                                if(idx == -1) {
                                    couponList[i].value=couponList[i].cpnmisidx;
                                    this.cartCouponList.push(couponList[i]);
                                    continue;
                                }
                            } else {
                                let idx = this.$front.containIdx(this.delivCouponList, 'cpnmisidx', couponList[i].cpnmisidx);
                                if(idx == -1) {
                                    this.delivCouponList.push(couponList[i]);
                                    continue;
                                }
                            }
                        }
                        
                        this.setFilterCoupon();
    
                        for(let i = 0 ; i < this.dealers.length ; i++) {
                            this.$front.setGoodsCouponList(this.dealers[i].items, this.couponList);
                            // for(let j = 0 ; j < this.dealers[i].items.length ; j++) {
                            //     let item = this.dealers[i].items[j];
                            //     this.$front.setGoodsCouponList(item, this.couponList);
                            // }
                        }
                    });
                //});
            } else {
                this.$eventBus.$emit('confirm', "쿠폰다운로드 안내", '회원으로 구매 시 쿠폰혜택을 받으실 수 있습니다. 로그인 하시겠습니까?', 
                    ()=>{
                    this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, query : this.$route.query});
                    this.$router.push({name : 'member-login'});
                    }
                );
            }
        },
        //적립금 체크클릭 이벤트
        changeChkPoint(type) {
            if(this.chk[type]) {
                switch(type) {
                    case 'respoint' :
                        if(this.epoint.isepointdup == 'F' && Number(this.usepoint.epointtotamt) > 0) {
                            this.$nextTick(()=>{
                                this.$eventBus.$emit('alert', '알림', '적립금과 D포인트를 동시에 사용할수 없습니다.');
                                this.chk[type] = false;
                                this.usepoint.reservetotamt = 0;
                            });
                            break;
                        }

                        if(Number(this.usepoint.reservetotamt) == 0) {
                            this.usepoint.reservetotamt = Number(this.memberInfo.respoint);
                            if(Number(this.totAmt.paytotprice) < (Number(this.usepoint.reservetotamt) + Number(this.usepoint.epointtotamt) + Number(this.usepoint.empreservetotamt))) {
                                this.usepoint.reservetotamt = Number(this.totAmt.paytotprice) - (Number(this.usepoint.epointtotamt) + Number(this.usepoint.empreservetotamt));
                            }
                            if(Number(this.usepoint.reservetotamt) == 0){
                                this.$nextTick(()=>{
                                    this.chk[type] = false;
                                });
                            }
                        }
                        break;
                    case 'epoint' :
                        if(this.epoint.isepointdup == 'F' && Number(this.usepoint.reservetotamt) > 0) {
                            this.$nextTick(()=>{
                                this.$eventBus.$emit('alert', '알림', '적립금과 D포인트를 동시에 사용할수 없습니다.');
                                this.chk[type] = false;
                                this.usepoint.epointtotamt = 0;
                            });
                            break;
                        }
                        if(Number(this.usepoint.epointtotamt) == 0) {
                            this.usepoint.epointtotamt = Number(this.memberInfo.epoint);
                            if(Number(this.totAmt.paytotprice) < (Number(this.usepoint.reservetotamt) + Number(this.usepoint.epointtotamt) + Number(this.usepoint.empreservetotamt))) {
                                this.usepoint.epointtotamt = Number(this.totAmt.paytotprice) - (Number(this.usepoint.reservetotamt) + Number(this.usepoint.empreservetotamt));
                            }
                            if(Number(this.usepoint.epointtotamt) == 0){
                                this.$nextTick(()=>{
                                    this.chk[type] = false;
                                });
                            }
                        }
                        break;
                    default :
                        if(Number(this.usepoint.empreservetotamt) == 0) {
                            this.usepoint.empreservetotamt = Number(this.memberInfo.emppoint);
                            if(Number(this.totAmt.paytotprice) < (Number(this.usepoint.reservetotamt) + Number(this.usepoint.epointtotamt) + Number(this.usepoint.empreservetotamt))) {
                                this.usepoint.empreservetotamt = Number(this.totAmt.paytotprice) - (Number(this.usepoint.reservetotamt) + Number(this.usepoint.epointtotamt));
                            }
                            if(Number(this.usepoint.empreservetotamt) == 0){
                                this.$nextTick(()=>{
                                    this.chk[type] = false;
                                });
                            }
                        }
                        break;
                }
            } else {
                switch(type) {
                    case 'respoint' :
                        this.usepoint.reservetotamt = 0;
                        break;
                    case 'epoint' :
                        this.usepoint.epointtotamt = 0;
                        break;
                    default :
                        this.usepoint.empreservetotamt = 0;
                        break;
                }
            }
            this.getCalAmt();
        },
        blurPoint(type) {
            switch(type) {
                case 'reservetotamt' :
                    if(Number(this.usepoint.reservetotamt) == 0) {
                        this.chk.respoint = false;
                    } else {
                        if(this.epoint.isepointdup == 'F' && Number(this.usepoint.epointtotamt) > 0) {
                            this.$nextTick(()=>{
                                this.$eventBus.$emit('alert', '알림', '적립금과 D포인트를 동시에 사용할수 없습니다.');
                                this.chk.respoint = false;
                                this.usepoint.reservetotamt = 0;
                            });
                            break;
                        }
                        
                        //포인트 사용합계가 총결제금액보다 클경우
                        if(Number(this.totAmt.paytotprice) < (Number(this.usepoint.reservetotamt) + Number(this.usepoint.epointtotamt) + Number(this.usepoint.empreservetotamt))) {
                            this.usepoint.reservetotamt = Number(this.totAmt.paytotprice) - (Number(this.usepoint.epointtotamt) + Number(this.usepoint.empreservetotamt));
                        }
                        if(Number(this.usepoint.reservetotamt) > Number(this.memberInfo.respoint)) {
                            this.usepoint.reservetotamt = Number(this.memberInfo.respoint);
                        }
                        
                        if(Number(this.usepoint.reservetotamt) > 0) {
                            this.chk.respoint = true;
                        } 
                    }
                    break;
                case 'epointtotamt' :
                    if(Number(this.usepoint.epointtotamt) == 0) {
                        this.chk.epoint = false;
                    } else {
                        if(this.epoint.isepointdup == 'F' && Number(this.usepoint.reservetotamt) > 0) {
                            this.$nextTick(()=>{
                                this.$eventBus.$emit('alert', '알림', '적립금과 D포인트를 동시에 사용할수 없습니다.');
                                this.chk.epoint = false;
                                this.usepoint.epointtotamt = 0;
                            });
                            break;
                        }
                        //포인트 사용합계가 총결제금액보다 클경우
                        if(Number(this.totAmt.paytotprice) < (Number(this.usepoint.reservetotamt) + Number(this.usepoint.epointtotamt) + Number(this.usepoint.empreservetotamt))) {
                            this.usepoint.epointtotamt = Number(this.totAmt.paytotprice) - (Number(this.usepoint.reservetotamt) + Number(this.usepoint.empreservetotamt));
                        }
                        if(Number(this.usepoint.epointtotamt) > Number(this.memberInfo.epoint)) {
                            this.usepoint.epointtotamt = Number(this.memberInfo.epoint);
                        }

                        if(Number(this.usepoint.epointtotamt) > 0) {
                            this.chk.epoint = true;
                        } 
                    }
                    break;
                default :
                    if(Number(this.usepoint.empreservetotamt) == 0) {
                        this.chk.epoint = false;
                    } else {
                        if(Number(this.totAmt.paytotprice) < (Number(this.usepoint.reservetotamt) + Number(this.usepoint.epointtotamt) + Number(this.usepoint.empreservetotamt))) {
                            this.usepoint.empreservetotamt = Number(this.totAmt.paytotprice) - (Number(this.usepoint.reservetotamt) + Number(this.usepoint.epointtotamt));
                        }
                        if(Number(this.usepoint.empreservetotamt) > Number(this.memberInfo.emppoint)) {
                            this.usepoint.empreservetotamt = Number(this.memberInfo.emppoint);
                        }
                        if(Number(this.usepoint.empreservetotamt) > 0) {
                            this.chk.emppoint = true;
                        } 
                    }
                    break;
            }
            this.getCalAmt();
        },
        changeCardCompany(val) {
          this.payInfo.cardCompany = val;
        },
        showModal(modalId, param) {
            let params = Object.assign({}, param);
            params.list = ['TRT002', 'TRT004', 'TRT012'];
            this.$eventBus.$emit('showModal', Terms, modalId, params);
        },
    },
    computed: {
      paywayName() {
        let paywayName = '';
        for(let i = 0 ; i < this.paywayList.length ; i++) {
          if(this.paywayList[i].value == this.payInfo.paywaytype) {
            paywayName = this.paywayList[i].label;
            break;
          }
        }
        return paywayName;
      }
    },
    beforeDestroy(){
        clearInterval(this.timer);
    }
};