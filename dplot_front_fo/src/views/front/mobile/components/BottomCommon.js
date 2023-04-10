import RestockAlarm from "@views.pc/cart/popup/RestockAlarm.vue"

export default {
    data(){
        return {
            product : {},
            isBtnOpen: false,
            openBottomSheet: false,
            itemOption : [],
            addItem : [],
            addItemOption : [],
            selectedOption : {         //선택된옵션명(옵션명3까지만 조회,옵션4는 BaseDropdown초기화를 위한변수)
                optionnm1 : '',
                optionnm2 : '',
                optionnm3 : '',
                optionnm4 : ''
            },
            selectedAddItem : '',
            selectedItem : [],
            selectedMainItem : false,
        }
    },
    methods: {
        initSelectedOption() {
            this.selectedOption = {
                optionnm1 : '',
                optionnm2 : '',
                optionnm3 : '',
                optionnm4 : ''
            }
        },
        //옵션 조회
        getOptionList(goodsno, isaddgoods) {
            const param = {
                goodsno : goodsno,
                isloading : false
            }
            this.$http.post('/goods/option', param).then(result => {
                if (result.statusCode == 200) {
                    if(result.data.list.length == 0) {
                        //옵션항목이 없을경우
                        const temp = {
                            goodsno : goodsno,
                            optionname : '옵션선택',
                            detail : [],
                            selected : ''
                        }
                        if(isaddgoods == 'T') {
                            this.addItemOption = [temp];
                        } else {
                            this.itemOption = [temp];
                        }
                    } else {
                        for(let i = 0 ; i < result.data.list.length ; i++){
                            result.data.list[i].detail = [];
                            result.data.list[i].selected = '';
                        }
                        if(isaddgoods == 'T') {
                            this.addItemOption = result.data.list;
                        } else {
                            this.itemOption = result.data.list;
                        }
                    }

                    if(isaddgoods == 'T') {
                        this.getOptionDetail(this.addItemOption[0], 0, this.addItemOption, isaddgoods);
                    } else {
                        this.getOptionDetail(this.itemOption[0], 0, this.itemOption, isaddgoods);
                    }
                } 
            });
        },
        //상세옵션 조회
        getOptionDetail(item, index, list, isaddgoods) {
            var param = this.selectedOption;
            param.isloading = false;
            param.index = index;
            param.goodsno = item.goodsno;
            param.islast = list.length - 1 == index ? 'T' : 'F'; //마지막 옵션여부

            this.$http.post('/goods/optionDetail', param).then(result => {
                if (result.statusCode == 200) {
                    for(let i = 0 ; i < result.data.list.length ; i++) {
                        result.data.list[i].label = result.data.list[i].optionnm;
                        result.data.list[i].value = result.data.list[i].optionnm + '|' + result.data.list[i].optioncode;
                        if(param.islast == 'T'){
                            result.data.list[i].saleamt = result.data.list[i].saleamt - result.data.list[i].goodscpnamt;
                            let diffsaleamt = result.data.list[i].saleamt - this.product.saleamt;
                            let diffstr = diffsaleamt > 0 ? '+' + this.$util.maskComma(diffsaleamt) : '' + this.$util.maskComma(diffsaleamt);
                            if(isaddgoods=='T'){
                                if(result.data.list[i].saleamt != 0){
                                    result.data.list[i].label = result.data.list[i].optionnm + ' (' + this.$util.maskComma(result.data.list[i].saleamt) + '원)';
                                }
                            }
                            else{
                                if(diffsaleamt != 0) {
                                    result.data.list[i].label = result.data.list[i].optionnm + ' (' + diffstr + '원)';
                                }
                            }
                            
                            if(result.data.list[i].stockcnt == 0) {
                                result.data.list[i].label = '[품절]' + result.data.list[i].label;
                            }
                        }
                    }
                    item.detail = result.data.list;
                    item.islast = param.islast;
                } 
            });
        },
        //추가상품 조회
        getAddItemsList() {
            const param = {
                goodsno : this.product.goodsno,
                isloading : false
            }
            this.$http.post('/goods/addGoods', param).then(result => {
                if (result.statusCode == 200) {
                    for(let i = 0 ; i < result.data.list.length ; i++) {
                        result.data.list[i].value = result.data.list[i].goodsno;
                        result.data.list[i].label = result.data.list[i].goodsname;
                    }
                    this.addItem = result.data.list;
                } 
            });
        },
        // 추가상품 선택
        selectAddItem() {
            this.getOptionList(this.selectedAddItem, 'T');
        },
        // 추가상품 선택
        // BaseDropdown 컴퍼넌트용
        selectAddItem2(value) {
            this.selectedAddItem = value + '';
            this.getOptionList(this.selectedAddItem, 'T');
        },
        // 옵션 선택2
        // BaseDropdown 컴퍼넌트용
        selectOption2(item, index, isaddgoods, args) {
            item.selected = args;
            this.selectOption(item, index, isaddgoods);
        },
        // 옵션 선택
        selectOption(item, index, isaddgoods) {
            let innerItem = null;
            //선택된 옵션
            for(let i = 0 ; i < item.detail.length ; i++) {
                if(item.detail[i].value == item.selected) {
                    innerItem = this.$util.deepClone(item.detail[i]);
                    break;
                }
            }
            
            this.selectedOption["optionnm" + (index + 1)] = item.selected.split('|')[0];

            var list = [];
            if(isaddgoods == 'T') {
                list = this.addItemOption;
            } else {
                list = this.itemOption;
            }
            //마지막옵션선택
            if(list.length - 1 == index) {
                //품절여부 체크
                // if (innerItem.stockcnt == 0) {
                //     item.selected = '';
                //     this.selectedOption["optionnm" + (index + 1)] = '';
                //     this.$eventBus.$emit('alert', '알림', "품절된 옵션입니다.");
                //     return false;
                // }

                //옵션선택 중복여부 체크
                for(let i = 0 ; i < this.selectedItem.length; i++) {
                    if(innerItem.goodsno == this.selectedItem[i].goodsno &&
                        innerItem.optioncode == this.selectedItem[i].optioncode) {
                        if(this.selectedItem[i].istrash == 'T') {
                            this.selectedItem[i].istrash = 'F';
                            this.selectedItem[i].ordcnt = 1;
                            this.checkMainItemSelected();
                            return;
                        } else {
                            this.$eventBus.$emit('alert', '알림', "이미선택된 옵션입니다.");
                            item.selected = '';
                            this.selectedOption["optionnm" + (index + 1)] = '';
                            return;
                        }
                    }
                }

                //옵션명 HTML변환
                this.$nextTick(()=>{
                    //선택한 옵션 List세팅
                    //추가상품있을경우 상품명 추가
                    let optnames = [];
                    let optionname = [];
                    if(this.addItem.length > 0){
                        if(isaddgoods == 'T') {
                            for(let i = 0 ; i < this.addItem.length ; i++) {
                                if(this.selectedAddItem == this.addItem[i].goodsno) {
                                    optnames.push('<span>' + this.addItem[i].goodsname +'</span>');
                                    //optnames.push('추가상품:');
                                    break;
                                }
                            }
                        } else {
                            //optnames.push('<span>' + this.product.goodsname +'</span>');
                        }
                    }

                    optnames.push('<span>' + list[0].selected.split('|')[0] + '</span>');
                    optionname.push(list[0].selected.split('|')[0]);
                    list[0].selected = '';
                    for(let i = 1 ; i < list.length ; i++) {
                        optnames.push('<span>' + list[i].selected.split('|')[0] + '</span>');
                        optionname.push(list[i].selected.split('|')[0]);
                        list[i].selected = '';
                        list[i].detail = [];
                    }
                    
                    //옵션선택 초기화
                    this.initSelectedOption();
                    this.selectedAddItem = '';
                    this.addItemOption = [];

                    //선택된 옵션 담기
                    innerItem.cartidx = 0;
                    innerItem.orggoodsno = this.product.goodsno;
                    innerItem.istrash = 'F';
                    innerItem.ordcnt = innerItem.stockcnt == 0 ? 0 : innerItem.minordcnt;
                    //innerItem.optnames = optnames;
                    innerItem.optionname = optionname.join(',');
                    innerItem.opthtml = optnames.join('<span class="dp-bar h10"></span>');
                    innerItem.isaddgoods = isaddgoods;
                    this.selectedItem.push(innerItem);
                    this.checkMainItemSelected();
                });
                
            } else {
                for(let i = index + 1 ; i < list.length ; i++) {
                    list[i].detail = [];
                    list[i].selected = '';
                    this.selectedOption["optionnm" + (i + 1)] = '';
                }
                this.getOptionDetail(list[index + 1], index + 1, list); 
            }
        },
        bottomSheetClose() {
            this.$emit("close");
        },
        handleRemove(item) {
            item.istrash = 'T';
            this.checkMainItemSelected();
        },
        checkMainItemSelected(){
            let cnt = 0; //주문상품
            let soldoutCnt = 0; //품절상품
            let addCnt = 0; //추가주문상품
            let delCnt = 0; //삭제주문상품

            for (let i = 0; i < this.selectedItem.length; i++) {
                if (this.selectedItem[i].istrash == 'T') {
                    delCnt++;
                } else {
                    if (this.selectedItem[i].isaddgoods == 'T') {
                        addCnt++;
                    } else if (this.selectedItem[i].stockcnt == 0) {
                        soldoutCnt++;
                    } else {
                        cnt++;
                    }
                }
            }

            if(cnt>0){
                this.selectedMainItem = true;
            }
            else{
                this.selectedMainItem = false;
            }
        },
        cart(){
            // gtmDataLayer 데이터 작업.
            let ecommerce = new Object();
            let items =  new Array();
            for (let i = 0; i < this.selectedItem.length; i++) {
                let item1 = new Object();
                item1.item_id = this.selectedItem[i].goodscode;
                item1.item_name = this.selectedItem[i].goodsname;
                item1.price = this.selectedItem[i].price;
                item1.quantity = this.selectedItem[i].ordcnt;
                item1.item_brand = this.selectedItem[i].brandname;
                item1.item_category = this.selectedItem[i].fullcategoryname;
                item1.item_variant = this.selectedItem[i].fulloptionnm;
                items.push(item1);
            }
            ecommerce.items = items;
            this.$front.gtmDataLayer('add_to_cart', ecommerce);

            this.$emit('cart');
        },
        buy(){
            this.$emit('buy');
        },
        //재입고 모달 열기
        openRestockModal(item){
            if(this.$store.state.isLogin) {
                this.$eventBus.$emit('showModal', RestockAlarm, 'restockAlarmModal', item);
            } else {
                this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
                    this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, params : this.$route.params});
                    this.$router.push({name : 'member-login'});
                });
            }
        }
    },
    computed: {
        // 배송비 계산
        getDelivAmt() {
            const delivAmtSum = this.$front.calDelivAmt(this.selectedItem, 'N');
            return delivAmtSum.delivamt;
        },
        // 총 주문금액 계산
        getTotalOptionPrice() {
            let sum = 0;
            for (var i = 0; i < this.selectedItem.length; ++i) {
                if(this.selectedItem[i].istrash == 'F') {
                    sum += (this.selectedItem[i].ordcnt * this.selectedItem[i].saleamt);
                }
            }
            return sum;
        },
        // 총 할인금액
        getTotalDiscountPrice() {
            let marketprice = 0;
            let sum = 0;
            for (var i = 0; i < this.selectedItem.length; ++i) {
                if(this.selectedItem[i].istrash == 'F') {
                    marketprice += (this.selectedItem[i].ordcnt * this.selectedItem[i].marketprice);
                    sum += (this.selectedItem[i].ordcnt * this.selectedItem[i].saleamt);
                }
            }
            return marketprice - sum;
        },
    },
  };