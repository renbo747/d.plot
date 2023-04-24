export default {
    name: 'FrontUtil',
    /*****************************
     * 최근검색어 저장
     ******************************/
    setRecentSearch: function (thisObj, item) {
        if (thisObj.$util.isNull(item.content)) {
            return;
        }
        let items = thisObj.$storage.getLocalStorage("RECENT_SEARCH");
        if (thisObj.$util.isNull(items)) {
            items = [];
        }
        for (let i = 0; i < items.length; i++) {
            if (items[i].content == item.content) {
                items.splice(i, 1);
                break;
            }
        }
        const temp = {
            content: item.content,
            regdate: thisObj.$util.getDate()
        }
        items.unshift(temp);
        if (items.length > 10) {
            items.pop();
        }
        thisObj.$storage.setLocalStorage("RECENT_SEARCH", items);
    },
    /*********************************
     * 최근검색어 목록조회
     * @returns 
     ************************************/
    getRecentSearch: function (thisObj) {
        let items = thisObj.$storage.getLocalStorage("RECENT_SEARCH");
        if (thisObj.$util.isNull(items)) {
            items = [];
        }
        return items;
    },
    /**********************************
     * 최근검색어 목록 삭제
     * @returns 
     ********************************/
    delRecentSearch: function (thisObj, obj) {
        let items = thisObj.$storage.getLocalStorage("RECENT_SEARCH");
        let temp = [];
        if (thisObj.$util.isNull(items)) {
            return;
        }
        temp = items.filter((x) => x.content !== obj.content);
        thisObj.$storage.setLocalStorage("RECENT_SEARCH", temp);
    },

    /**
     * 최근본상품 저장
     * @returns 
     */
    setRecentItem: function (thisObj, item) {
        let items = thisObj.$storage.getLocalStorage("RECENT_ITEMS");
        if (thisObj.$util.isNull(items)) {
            items = [];
        }
        for (let i = 0; i < items.length; i++) {
            if (items[i].goodsno == item.goodsno) {
                items.splice(i, 1);
                break;
            }
        }
        const temp = {
            recentidx: item.goodsno + thisObj.$util.getDate(),
            goodsno: item.goodsno,
            brandname: item.brandname,
            goodsname: item.goodsname,
            saleamt: item.saleamt,
            marketprice: item.marketprice,
            disrate: item.disrate,
            fullpath: item.fullpath,
            cateidx: item.cateidx,
            iswished: item.iswished,
            regdate: thisObj.$util.getDate()
        }
        items.unshift(temp);
        if (items.length > 10) {
            items.pop();
        }

        thisObj.$storage.setLocalStorage("RECENT_ITEMS", items);
    },
    /**
     * 최근본상품 목록조회
     * @returns 
     */
    getRecentItems: function (thisObj) {
        let items = thisObj.$storage.getLocalStorage("RECENT_ITEMS");
        if (thisObj.$util.isNull(items)) {
            items = [];
        }
        return items;
    },
    /**
     * 최근본상품 목록 삭제
     * @returns 
     */
    delRecentItems: function (thisObj, obj) {
        let items = thisObj.$storage.getLocalStorage("RECENT_ITEMS");
        let temp = [];
        if (thisObj.$util.isNull(items)) {
            return;
        }
        thisObj.$util.debug(obj.regdate + "::" + obj.goodsno);
        temp = items.filter((x) => x.recentidx !== (obj.goodsno + obj.regdate));
        thisObj.$storage.setLocalStorage("RECENT_ITEMS", temp);
    },
    /**
     * 주문목록 List Object -> 파라미터 String 변환
     * @param {*} orderItems 
     * @returns 
     */
    makeOrderStr: function (orderItems) {
        let items = [];

        for (let i = 0; i < orderItems.length; i++) {
            let item = [];
            item.push(orderItems[i].cartidx);
            item.push(orderItems[i].goodsno);
            item.push(orderItems[i].optioncode);
            item.push(orderItems[i].isaddgoods);
            item.push(orderItems[i].cateidx);
            item.push(orderItems[i].ordcnt);
            item.push(orderItems[i].istrash);
            item.push(orderItems[i].orggoodsno);
            items.push(item.join('#'));
        }

        return items.join('|');
    },
    /**
     * 주문목록 파라미터 String -> List Object
     * @param {*} orderStr 
     * @returns 
     */
    parseOrderStr: function (orderStr) {
        let items = [];
        let tempList = orderStr.split('|');

        for (let i = 0; i < tempList.length; i++) {
            let item = {};
            let temp = tempList[i].split('#');
            item.cartidx = temp[0];
            item.goodsno = temp[1];
            item.optioncode = temp[2];
            item.isaddgoods = temp[3];
            item.cateidx = temp[4];
            item.ordcnt = temp[5];
            item.istrash = temp[6];
            item.orggoodsno = temp[7];

            items.push(item);
        }

        return items;
    },
    /**
     * 클레임신청목록 List Object -> 파라미터 String 변환
     * @param {*} claimItems 
     * @returns 
     */
    makeClaimStr: function (claimItems) {
        let items = [];

        for (let i = 0; i < claimItems.length; i++) {
            let item = [];
            item.push(claimItems[i].orderidx);
            item.push(claimItems[i].ordgdidx);
            item.push(claimItems[i].optioncode);
            item.push(claimItems[i].clmcnt == null || claimItems[i].clmcnt == 'undefined' ? 1 : claimItems[i].clmcnt);
            item.push(claimItems[i].clmidx == null || claimItems[i].clmidx == 'undefined' ? '' : claimItems[i].clmidx);
            items.push(item.join('#'));
        }

        return items.join('|');
    },
    /**
     * 클레임신청목록 파라미터 String -> List Object
     * @param {*} claimStr 
     * @returns 
     */
    parseClaimStr: function (claimStr) {
        let items = [];
        let tempList = claimStr.split('|');

        for (let i = 0; i < tempList.length; i++) {
            let item = {};
            let temp = tempList[i].split('#');
            item.orderidx = temp[0];
            item.ordgdidx = temp[1];
            item.optioncode = temp[2];
            item.clmcnt = Number(temp[3]);
            item.clmidx = Number(temp[4]);

            items.push(item);
        }

        return items;
    },
    /**
     * 리스트에 동일한값 포함여부 체크
     * @param {*} list 
     * @param {*} key 
     * @param {*} value 
     * @returns 
     */
    containIdx(list, key, value) {
        let idx = -1;
        for (let i = 0; i < list.length; i++) {
            if (list[i][key] == value) {
                idx = i;
                break;
            }
        }
        return idx;
    },

    /**
     * 배송비 계산 
     * @param {*}} list 파트너별 상품목록
     * @param {*} type N:일반, J:제주, I:도서산간
     */
    calDelivAmt(list, type) {
        let tempList = []; //배송템플릿 저장
        let delivAmtSum = {
            delivamt: 0,
            adddelivamt: 0
        }
        for (let i = 0; i < list.length; i++) {
            if (list[i].istrash == 'T') continue;
            if (list[i].iscombdeliv == 'F') {
                //개별배송일경우
                this._calItemDelivAmt(list[i], type, list);
            } else {
                //묵음배송일경우
                //동일한 배송템플릿 여부
                const idx = this.containIdx(tempList, 'delividx', list[i].delividx);
                if (idx == -1) {
                    //배송템플릿이 없을경우
                    this._calItemDelivAmt(list[i], type, list);
                    tempList.push(list[i]);
                } else {
                    //배송템플릿이 있을경우
                    list[i].delivamt = 0;
                    list[i].adddelivamt = 0;
                }
            }
            delivAmtSum.delivamt = delivAmtSum.delivamt + list[i].delivamt;
            delivAmtSum.adddelivamt = delivAmtSum.adddelivamt + list[i].adddelivamt;
        }

        return delivAmtSum;
    },
    /**
     * 
     * @param {*} item 
     * @param {*} type 
     */
    _calItemDelivAmt(item, type, list) {
        if (item.delivfaretype == 'DCT001') {
            //무료
            item.delivamt = 0;
        } else if (item.delivfaretype == 'DCT002') {
            //유료
            item.delivamt = item.delivfare;
        } else if (item.delivfaretype == 'DCT005') {
            //구매수량당
            item.delivamt = item.delivfare * item.ordcnt;
        } else {
            //조건부 무료
            //동일템플릿에서 조건이 만족하면 배송비는 전부 0원
            let isOk = false;
            if(item.iscombdeliv == 'T') {
                let sum = 0;
                for (let i = 0; i < list.length; i++) {
                    if (list[i].iscombdeliv == 'T' && item.delividx == list[i].delividx) {
                        sum =  sum + (list[i].saleamt * list[i].ordcnt);
                    }
                }
                if(sum >= item.delivfreefare) {
                    isOk = true;
                }
            } else {
                let sum = 0;
                for (let i = 0; i < list.length; i++) {
                    if (item.goodsno == list[i].goodsno) {
                        sum =  sum + (list[i].saleamt * list[i].ordcnt);
                    }
                }
                if(sum >= item.delivfreefare) {
                    isOk = true;
                }
            }
            
            if (isOk) {
                item.delivamt = 0;
            } else {
                item.delivamt = item.delivfare;
            }
        }
        if (type == 'J') {
            item.adddelivamt = item.chejufare;
        } else if (type == 'I') {
            item.adddelivamt = item.isolfare;
        } else {
            item.adddelivamt = 0;
        }
    },
    /**
     * 리다이렉트 페이지이동
     * @param {*} thisObj 
     */
    redirectPage(thisObj) {
        var redirectPath = thisObj.$storage.getSessionStorage('redirectPath');
        if (!thisObj.$util.isNull(redirectPath)) {
            thisObj.$storage.removeSessionStorage('redirectPath');
            thisObj.$router.replace(redirectPath);
        } else {
            thisObj.$router.replace({ name: 'shop' });
        }
    },
    /**
     * 비밀번호 변경처리
     * @param {*} thisObj 
     * @param {*} callback 
     * @returns 
     */
    changePw(thisObj, callback) {
        // 유효성 3개 다 success 여야함
        if (thisObj.newpw == "") {
            thisObj.$eventBus.$emit('alert', '알림', "새 비밀번호를 입력해주세요.");
            return false;
        }
        if (thisObj.confirmpw == "") {
            thisObj.$eventBus.$emit('alert', '알림', "비밀번호 확인을 입력해주세요.");
            return false;
        }

        if (thisObj.newpw != thisObj.confirmpw) {
            thisObj.$eventBus.$emit('alert', '알림', "비밀번호 확인이 일치하지 않습니다.");
            return false;
        }

        if (!thisObj.$util.isPassword(thisObj.newpw)) {
            thisObj.$eventBus.$emit('alert', '알림', "최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 또는 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합으로 입력해야합니다.");
            return false;
        }
        const param = {
            userno: thisObj.param.userno,
            newpw: thisObj.newpw,
            confirmpw: thisObj.confirmpw,
        };

        thisObj.$http.post("/member/pw/change", param).then((result) => {
            if (result.statusCode == 200) {
                thisObj.$eventBus.$emit('alert', '알림', "비밀번호 변경이 처리되었습니다.", () => {
                    callback();
                });
            }
        });
    },
    /**
     * SNS/BIO Login
     * @param {*} thisObj 
     * @param {*} id 
     * @param {*} snstype 
     */
    otherLogin(thisObj, id, type, otherid, snstype,isadpush, isadmailing, isadsms) {
        const param = {
            id: id,
            mode: type,
            otherid: String(otherid), //snsuserno가 Interger인데 서버 map<string,string>이라 인식못해서 string으로 변경
            snstype: snstype,
            isadpush: isadpush,
            isadmailing:isadmailing,
            isadsms:isadsms
        }
        thisObj.$http.post('/member/login_act', param).then(result => {
            if (result.statusCode == 200) {
                thisObj.$store.commit("isLogin", true);
                thisObj.$storage.setSessionStorage('name', result.data.memberinfo.username);
                //네이티브일경우 Prefrence 설정
                this.sendNativeMessage({
                    type: 'PREF_SAVE',
                    data: {
                        isLogin: true,
                        isAlarm: result.data.memberinfo.ispush,
                        isBio: result.data.memberinfo.isbio,
                        appVerion: '1.0.0',
                        name: result.data.memberinfo.username,
                        userid: result.data.memberinfo.userid,
                    }
                })
                //휴면계정일경우
                if (result.data.issleep) {
                    thisObj.$storage.setLocalStorage('memberSleep', result.data);
                }

                //장바구니 담기
                thisObj.$eventBus.$emit('saveCart', () => {
                    thisObj.$storage.removeLocalStorage("CART_ITEMS");
                    //비밀번호변경일경우
                    //SNS로그인 시라도 기가입채널이 다다픽이라면 비밀번호 변경처리
                    if (!thisObj.$util.isNull(result.data.joinchtype) && result.data.joinchtype == "UCT001") {
                        if (result.data.isneedinitpw) {
                            if (result.platform != 'MAC001') {
                                result.$eventBus.$emit('showModal', PasswordChange, 'password-change', {
                                    param: result.data,
                                    isTempPassword: true
                                });
                            } else {
                                result.$router.replace({ name: 'member-password-change', query: { info: result.data.memberinfo.userno, isTempPassword: true } });
                            }
                            //thisObj.$eventBus.$emit('showModal', PasswordNew, 'password-new', { param: result.data }, thisObj.$front.redirectPage(thisObj));
                        }else if (result.data.isneedchangepw) {
                            if (thisObj.platform != 'MAC001') {
                                thisObj.$eventBus.$emit('showModal', PasswordChange, 'password-change', {  param: result.data, isTempPassword: false  }, thisObj.$front.redirectPage(thisObj));
                            } else {
                                thisObj.$router.replace({ name: 'member-password-change', params: { memberInfo: result.data , isTempPassword: false} });
                            }
                        } else {
                            thisObj.$front.redirectPage(thisObj);
                        }
                    } else {
                        thisObj.$front.redirectPage(thisObj);
                    }

                });
            } else {
                thisObj.$eventBus.$emit('alert', '알림', result.message, () => {
                    thisObj.$router.replace({ name: 'shop' });
                });
            }
        });
    },
    /**
     * 장바구니 저장처리
     * @param {*} thisObj 
     * @param {*} items 
     * @param {*} callback 
     */
    saveCart(thisObj, list, callback) {
        //재고가 0인 제품은 제외
        let items = [];
        for (let i = 0; i < list.length; i++) {
            if (list[i].stockcnt > 0 || list[i].istrash == 'T') {
                items.push(list[i]);
            }
        }
        if (thisObj.$store.state.isLogin) {
            const param = {
                items: items,
                isloading: false,
                iscart: thisObj.iscart
            };
            thisObj.$http.post('/cart/save', param).then(result => {
                if (result.statusCode == 200) {
                    thisObj.$store.commit("cartList", result.data.list);
                    thisObj.$store.commit("cartCount", result.data.list.length);
                    if (callback) {
                        callback(result.data);
                    }
                }
            });
        } else {
            let cartList = thisObj.$storage.getLocalStorage("CART_ITEMS");
            if (thisObj.$util.isNull(cartList)) {
                cartList = [];
            }

            /*
            * 카트에 동일한 상품옵션있을 경우 수량증가
            * 없을경우 상품옵션 장바구니 추가
            */
            for (let i = 0; i < items.length; i++) {
                let isExist = false;
                for (let j = cartList.length - 1; j >= 0; j--) {
                    //장바구니에 상품이 있을경우 수량증가
                    if (items[i].goodsno == cartList[j].goodsno
                        && items[i].optioncode == cartList[j].optioncode) {
                        if (items[i].istrash == 'T') {
                            // 삭제된 상품옵션
                            cartList.splice(j, 1);
                            continue;
                        }
                        cartList[i].istrash = 'F';
                        if (items[i].ordcnt == 0) {
                            // 품정상품
                            cartList[j].ordcnt = 0;
                        } else {
                            if (thisObj.iscart == 'T') {
                                cartList[j].ordcnt = items[i].ordcnt;
                            } else {
                                cartList[j].ordcnt = cartList[j].ordcnt + items[i].ordcnt;
                            }
                        }
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    cartList.push(items[i]);
                }
            }

            const params = {
                items: cartList
            }

            thisObj.$http.post('/cart/list', params).then(result => {
                if (result.statusCode == 200) {
                    for (let i = 0; i < result.data.list.length; i++) {
                        result.data.list[i].disrate = thisObj.$util.saleRate(result.data.list[i].marketprice, result.data.list[i].saleamt);
                    }
                    thisObj.$storage.setLocalStorage("CART_ITEMS", result.data.list);
                    thisObj.$store.commit("cartList", result.data.list);
                    thisObj.$store.commit("cartCount", result.data.list.length);
                    if (callback) {
                        callback(result.data);
                    }
                }
            });
        }
    },
    /**
     * 장바구니 목록 조회
     * @param {*} thisObj 
     * @param {*} callback 
     */
    getCartList(thisObj, callback) {
        let items = thisObj.$storage.getLocalStorage("CART_ITEMS");
        if (thisObj.$util.isNull(items)) {
            items = [];
        }
        const params = {
            items: items,
            isloading: false
        }
        thisObj.$http.post('/cart/list', params).then(result => {
            if (result.statusCode == 200) {
                let ecommerce = new Object();
                let items =  new Array();
                let value = new Object();
                let numcnt = 0;
                for (let i = 0; i < result.data.list.length; i++) {
                    let salepromo = result.data.list[i].salepromo.split('|');
                    result.data.list[i].salepromoidx = salepromo[0]; //프로모션 IDX
                    result.data.list[i].salepromoamt = Number(salepromo[1]); //프로모션 할인가
                    result.data.list[i].saleamt = result.data.list[i].price - result.data.list[i].salepromoamt;
                    result.data.list[i].disrate = thisObj.$util.saleRate(result.data.list[i].marketprice, result.data.list[i].saleamt);

                    let item1 = new Object();
                    item1.item_id = result.data.list[i].goodscode;
                    item1.id = result.data.list[i].goodscode;
                    item1.item_name = result.data.list[i].goodsname;
                    item1.price = result.data.list[i].price;
                    item1.quantity = result.data.list[i].ordcnt;
                    numcnt += result.data.list[i].ordcnt;
                    item1.item_brand = result.data.list[i].brandname;
                    item1.item_category = result.data.list[i].fullcategoryname;
                    item1.item_variant = result.data.list[i].fulloptionnm;
                    items.push(item1);
                }
                
                ecommerce.value = value;
                ecommerce.items = items;
                ecommerce.num_items = result.data.list.length;
                ecommerce.num_quantity = numcnt;
                this.gtmDataLayer('view_cart', ecommerce);
                
                thisObj.$store.commit("cartList", result.data.list);
                if (callback) {
                    callback(result.data);
                }
            }
        });
    },
    /**
     * 찜 변경처리
     * @param {*} thisObj 
     * @param {*} product 
     * @returns 
     */
    goodsChangeWish(thisObj, product, wishcnt) {
        if (!thisObj.$store.state.isLogin) {
            // thisObj.$eventBus.$emit("alert", "알림", "로그인 후 이용해주세요.");
            thisObj.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?', () => {
                thisObj.$storage.setSessionStorage('redirectPath', { path: thisObj.$route.path, query: thisObj.$route.query });
                thisObj.$router.push({ name: 'member-login' });
            });
            return;
        }

        let param = {
            goodsno: product.goodsno,
            cateidx: product.cateidx,
            iswished: product.iswished,
        };
        
        // gtmDataLayer 데이터 작업.
        let ecommerce = new Object();
        let items =  new Array();
        let item1 = new Object();
        item1.item_id = product.goodscode;
        item1.item_name = product.goodsname;
        item1.price = product.saleamt;
        item1.item_brand = product.brandname;
        item1.item_category = product.fullcategoryname;
        items.push(item1);
        ecommerce.items = items;
        this.gtmDataLayer('add_to_wishlist', ecommerce);

        thisObj.$http.post("/goods/wish", param).then((result) => {
            if (result.statusCode == 200) {
                product.iswished = product.iswished == "T" ? "F" : "T";
                if (!thisObj.$util.isNull(wishcnt)) {
                    if (product.iswished == "T") {
                        product.wishcnt = wishcnt + 1;
                    } else {
                        product.wishcnt = wishcnt - 1;
                    }
                }
                return true;
            } else {
                return false;
            }
        });
    },
    checkCartItem(thisObj, items) {
        let cnt = 0; //주문상품
        let soldoutCnt = 0; //품절상품
        let addCnt = 0; //추가주문상품
        let delCnt = 0; //삭제주문상품

        for (let i = 0; i < items.length; i++) {
            if (items[i].istrash == 'T') {
                delCnt++;
            } else {
                if (items[i].isaddgoods == 'T') {
                    addCnt++;
                } else if (items[i].stockcnt == 0) {
                    soldoutCnt++;
                } else {
                    cnt++;
                }
            }
        }

        if (delCnt == items.length) {
            thisObj.$eventBus.$emit('alert', '알림', "옵션을 선택하세요.");
            return false;
        }

        if (cnt == 0 && addCnt > 0) {
            thisObj.$eventBus.$emit('alert', '알림', "추가상품은 단독으로 구매가 불가능합니다.");
            return false;
        }

        if (delCnt + soldoutCnt == items.length) {
            thisObj.$eventBus.$emit('alert', '알림', "품절된 상품입니다.");
            return false;
        }

        return true;
    },
    gradeList() {
        let list = [
            {
                grade: 1,
                type: "MDL001",
                src: "img-bronze-level-40px.png",
                img: "img-bronze-level-64px.png",
                label: "BRONZE",
                color: "#c96f05",
                minamt: 0,
                maxamt: 0,
                ordcnt: 0,
                benefitList: {
                    title: "회원가입시 기본 등급", // 혜택 제목
                    recently: 6, // 산정 기간
                    list: [
                        // 혜택 목록
                        "구매시(구매확정시) 1% 적립 (첫 구매 시 3% 적립)",
                        "1,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "생일기념 2,000원 할인쿠폰 지급 (결제조건없음)",
                    ],
                },
                benefitPcList: {
                    title: "회원가입시 기본 등급", // 혜택 제목
                    recently: 6, // 산정 기간
                    list: ["1% 적립", "1,000원 할인쿠폰", "2,000원 할인쿠폰"],
                },
            },
            {
                grade: 2,
                type: "MDL002",
                src: "img-silver-level-40px.png",
                img: "img-gold-level-64px.png",
                label: "SILVER",
                color: "#6e757c",
                minamt: 1000000,
                maxamt: 2000000,
                ordcnt: 2,
                benefitList: {
                    title: "100만원 이상 200만원 미만<br />구매건수 2건 이상 고객",
                    recently: 6,
                    list: [
                        "결제 시 1.5% 적립",
                        "1,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "2,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "생일기념 2,000원 할인쿠폰 지급 (결제조건없음)",
                    ],
                },
                benefitPcList: {
                    title: "100만원 이상 200만원 미만<br />구매건수 2건 이상 고객",
                    recently: 6,
                    list: [
                        "1.5% 적립",
                        "1,000원 할인쿠폰",
                        "2,000원 할인쿠폰",
                        "2,000원 생일쿠폰",
                    ],
                },
            },
            {
                grade: 3,
                type: "MDL003",
                src: "img-gold-level-40px.png",
                img: "img-gold-level-64px.png",
                label: "GOLD",
                color: "#c59d17",
                minamt: 2000000,
                maxamt: 3000000,
                ordcnt: 5,
                benefitList: {
                    title: "200만원 이상 300만원 미만<br />구매건수 5건 이상 고객",
                    recently: 6,
                    list: [
                        "결제 시 2% 적립",
                        "1,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "2,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "3,000원 할인쿠폰 지급 (70,000원 이상 결제 시)",
                        "배송비 무료 쿠폰",
                        "생일기념 2,000원 할인쿠폰 지급 (결제조건없음)",
                    ],
                },
                benefitPcList: {
                    title: "200만원 이상 300만원 미만<br />구매건수 5건 이상 고객",
                    recently: 6,
                    list: [
                        "2% 적립",
                        "1,000원 할인쿠폰",
                        "2,000원 할인쿠폰",
                        "3,000원 할인쿠폰",
                        "배송비 무료 쿠폰",
                        "2,000원 생일쿠폰",
                    ],
                },
            },
            {
                grade: 4,
                type: "MDL004",
                src: "img-vip-level-40px.png",
                img: "img-vip-level-64px.png",
                label: "VIP",
                color: "#7d23c9",
                minamt: 3000000,
                maxamt: 5000000,
                ordcnt: 7,
                benefitList: {
                    title: "300만원 이상 500만원 미만<br />구매건수 7건 이상 고객",
                    recently: 6,
                    list: [
                        "결제 시 2.5% 적립",
                        "1,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "2,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "3,000원 할인쿠폰 지급 (70,000원 이상 결제 시)",
                        "5,000원 할인쿠폰 지급 (100,000원 이상 결제 시)",
                        "배송비 무료 쿠폰",
                        "생일기념 2,000원 할인쿠폰 지급 (결제조건없음)",
                    ],
                },
                benefitPcList: {
                    title: "300만원 이상 500만원 미만<br />구매건수 7건 이상 고객",
                    recently: 6,
                    list: [
                        "2.5% 적립",
                        "1,000원 할인쿠폰",
                        "2,000원 할인쿠폰",
                        "3,000원 할인쿠폰",
                        "5,000원 할인쿠폰",
                        "배송비 무료 쿠폰",
                        "2,000원 생일쿠폰",
                    ],
                },
            },
            {
                grade: 5,
                type: "MDL005",
                src: "img-vvip-level-40px.png",
                img: "img-vvip-level-64px.png",
                label: "VVIP",
                color: "#d8245d",
                minamt: 5000000,
                maxamt: 0,
                ordcnt: 10,
                benefitList: {
                    title: "500만원 이상<br />구매건수 10건 이상 고객",
                    recently: 6,
                    list: [
                        "결제 시 3% 적립",
                        "1,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "2,000원 할인쿠폰 지급 (50,000원 이상 결제 시)",
                        "3,000원 할인쿠폰 지급 (70,000원 이상 결제 시)",
                        "5,000원 할인쿠폰 지급 (100,000원 이상 결제 시)",
                        "10,000원 할인쿠폰 지급 (200,000원 이상 결제 시)",
                        "배송비 무료 쿠폰",
                        "생일기념 2,000원 할인쿠폰 지급 (결제조건없음)",
                    ],
                },
                benefitPcList: {
                    title: "500만원 이상<br />구매건수 10건 이상 고객",
                    recently: 6,
                    list: [
                        "3% 적립",
                        "1,000원 할인쿠폰",
                        "2,000원 할인쿠폰",
                        "3,000원 할인쿠폰",
                        "5,000원 할인쿠폰",
                        "10,000원 할인쿠폰",
                        "배송비 무료 쿠폰",
                        "2,000원 생일쿠폰",
                    ],
                },
            }
        ]
        return list;
    },
    /**
     * 상품쿠폰 사용 및 잔여수량
     * 사용안함
     * @param {*} coupon 
     * @param {*} couponList 
     */
    setCouponUsed(coupon, couponList) {
        let usecnt = 0;
        //잔여량 초기화 및 사용수량 합계
        for (let i = 0; i < couponList.length; i++) {
            //쿠폰잔량(동일쿠폰)
            if (couponList[i].isdirectcoupon == 'T') { //즉시할인쿠폰
                couponList[i].remaincnt = couponList[i].ordcnt;
            } else {
                couponList[i].remaincnt = couponList[i].dupcouponcnt;
            }

            if (coupon.cpninfoidx == couponList[i].cpninfoidx) {
                usecnt += Number(couponList[i].usecnt);
            }
        }
        //잔여수량 - 사용수량합계
        for (let i = 0; i < couponList.length; i++) {
            if (coupon.cpninfoidx == couponList[i].cpninfoidx) {
                if (couponList[i].isdirectcoupon != 'T') { //즉시발급{
                    couponList[i].remaincnt = couponList[i].remaincnt - usecnt;
                } else {
                    couponList[i].remaincnt = couponList[i].ordcnt;
                }
                // couponList[i].cntList = []
                // for (let y = 1; y <= couponList[i].remaincnt; y++) {
                //     couponList[i].cntList.push({ value: y, label: y });
                // }
            }
        }
    },
    /**
     * 상품별 쿠폰목록 및 사용수량목록
     * @param {*} item 
     * @param {*} couponList 
     */
    setGoodsCouponList(items, couponList) {
        for (let i = 0; i < couponList.length; i++) {
            //쿠폰잔량, 사용수량 초기화
            couponList[i].usecnt = 0;
            if (couponList[i].isdirectcoupon == 'T') { //즉시할인쿠폰
                couponList[i].remaincnt = couponList[i].ordcnt;
            } else {
                couponList[i].remaincnt = couponList[i].dupcouponcnt;
            }

            let usecnt = 0;
            for (let j = 0; j < items.length; j++) {
                if (couponList[i].cpninfoidx == items[j].coupon.cpninfoidx && couponList[i].cpnmisidx == items[j].coupon.cpnmisidx && couponList[i].isdirectcoupon == 'F') {
                    usecnt += Number(items[j].coupon.usecnt);
                }
            }
            
            if (couponList[i].isdirectcoupon == 'F') {
                couponList[i].remaincnt = couponList[i].remaincnt - usecnt;
            }
        }

        for (let i = 0; i < items.length; i++) {
            items[i].couponList = [];
            for (let j = 0; j < couponList.length; j++) {
                if (items[i].goodsno == couponList[j].goodsno
                    && items[i].optioncode == couponList[j].optioncode) {
                    //동일발금쿠폰 제외
                    if (this.containIdx(items[i].couponList, 'cpninfoidx', couponList[j].cpninfoidx) > -1) {
                        continue;
                    }
                    //잔여수량 0 제외
                    if (items[i].coupon.cpninfoidx != couponList[j].cpninfoidx && couponList[j].remaincnt <= 0) {
                        continue;
                    }
                    //쿠폰변경 수량 Select Option 설정
                    couponList[j].cntList = [];
                    let limit = couponList[j].remaincnt;
                    if (items[i].coupon.cpninfoidx == couponList[j].cpninfoidx) {
                        if (couponList[j].isdirectcoupon == 'T') { //즉시발급
                            couponList[j].remaincnt = couponList[j].ordcnt;
                        } else {
                            couponList[j].remaincnt = couponList[j].dupcouponcnt;
                        }
                    }
                    for (let y = 1; y <= limit; y++) {
                        couponList[j].cntList.push({ value: y, label: y });
                    }

                    items[i].couponList.push(couponList[j]);
                }
            }

            if (items[i].couponList.length == 0) {
                items[i].coupon.cpninfoidx = 'emptyCoupon';
                items[i].couponList.push({ value: 'emptyCoupon', label: '적용할 수 있는 쿠폰이 없습니다.', cpninfoidx: 'emptyCoupon' });
            } else {
                items[i].couponList.unshift({ value: 'noCoupon', label: '적용안함', cpninfoidx: 'noCoupon' });
            }
        }
    },
    /*******************
     * 필터 값 가져오기
     ******************/
    getFilterInfo(thisObj) {
        thisObj.$util.debug("getFilterInfo");
        let filterinfo = thisObj.$store.state.filterinfo;
        thisObj.setCateidx = filterinfo.setCateidx;
        thisObj.setBrandList = filterinfo.setBrandList;
        thisObj.setColorList = filterinfo.setColorList;
        thisObj.setBenefitList = filterinfo.setBenefitList;
        thisObj.setPriceValue = filterinfo.setPriceValue;
        thisObj.setRatingList = filterinfo.setRatingList;
        thisObj.setPriceList = filterinfo.setPriceList;
        thisObj.priceList = filterinfo.priceList;
        thisObj.setGiconList = filterinfo.setGiconList;
        thisObj.setRecomList = filterinfo.setRecomList;
        thisObj.setFilterList = filterinfo.setFilterList;

        thisObj.$util.debug("setCateidx::" + thisObj.setCateidx);
        thisObj.$util.debug("setBrandList::" + thisObj.setBrandList);
        thisObj.$util.debug("setColorList::" + thisObj.setColorList);
        thisObj.$util.debug("setBenefitList::" + thisObj.setBenefitList);
        thisObj.$util.debug("setPriceValue::" + thisObj.setPriceValue);
        thisObj.$util.debug("setRatingList::" + thisObj.setRatingList);
        thisObj.$util.debug("setFilterList::" + thisObj.setFilterList);
        thisObj.$util.debug("setPriceList::" + thisObj.setPriceList);
        thisObj.$util.debug("priceList::" + thisObj.priceList);
        thisObj.$util.debug("setGiconList::" + thisObj.setGiconList);
        thisObj.$util.debug("setRecomList::" + thisObj.setRecomList);
    },
    /****************
     * 필터 값 저장
     ****************/
    setFilterInfo(thisObj) {
        thisObj.$util.debug("setFilterInfo");
        let param = {
            setCateidx: thisObj.$util.isNull(thisObj.setCateidx) ? 0 : thisObj.setCateidx,
            setBrandList: thisObj.setBrandList,
            setColorList: thisObj.setColorList,
            setBenefitList: thisObj.setBenefitList,
            setPriceValue: thisObj.setPriceValue,
            setRatingList: thisObj.setRatingList,
            setFilterList: thisObj.setFilterList,
            setPriceList: thisObj.setPriceList,
            setGiconList: thisObj.setGiconList,
            setRecomList: thisObj.setRecomList,
            priceList: thisObj.priceList
        }
        thisObj.$store.commit("filterinfo", param);
    },
    /****************
    * 필터 값 초기화
    ****************/
    getFilterInfoInit(thisObj) {
        thisObj.$util.debug("getFilterInfoInit");
        thisObj.setBrandList = [];
        thisObj.setColorList = [];
        thisObj.setBenefitList = [];
        thisObj.setRatingList = [];
        thisObj.setFilterList = [];
        thisObj.setPriceList = [];
        thisObj.setGiconList = [];
        thisObj.setRecomList = [];
    },

    /**************************
     * 필터 항목 삭제
     *************************/
    delQueryStrFilter(thisObj, item) {
        thisObj.$util.debug("delFilter()...");
        thisObj.$util.debug("item::" + JSON.stringify(item));
        if (item.target == "setBrandList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setBrandList = thisObj.setBrandList.filter((x) => x !== item.id);
        } else if (item.target == "setColorList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setColorList = thisObj.setColorList.filter((x) => x !== item.id);
        } else if (item.target == "setBenefitList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setBenefitList = thisObj.setBenefitList.filter((x) => x !== item.id);
        } else if (item.target == "setGiconList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setGiconList = thisObj.setGiconList.filter((x) => x !== item.id);
        } else if (item.target == "setRecomList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setRecomList = thisObj.setRecomList.filter((x) => x !== item.id);
        } else if (item.target == "setPriceList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setPriceList = [];
            thisObj.setPriceValue = {
                currentValue: [thisObj.initMinPrice, thisObj.initMaxPrice], //현재 금액
                min: thisObj.initMinPrice, //최소 금액
                max: thisObj.initMaxPrice, //최대 금액 
            }

            //TO_DO::필터항목에서 가격삭제시 후처리 방안 나오면 추후개발
        } else if (item.target == "setRatingList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setRatingList = thisObj.setRatingList.filter((x) => x !== item.id);
        } else {
            return;
        }
        thisObj.$util.debug("setBrandList:" + thisObj.setBrandList);
        thisObj.$util.debug("setColorList:" + thisObj.setColorList);
        thisObj.$util.debug("setBenefitList:" + thisObj.setBenefitList);
        thisObj.$util.debug("setFilterList:" + thisObj.setFilterList);
        thisObj.$util.debug("setRatingList:" + thisObj.setRatingList);
        thisObj.$util.debug("setGiconList:" + thisObj.setGiconList);
        thisObj.$util.debug("setRecomList:" + thisObj.setRecomList);
    },
    /********************************
     * 필터 적용 쿼리스트링 생성
     ********************************/
    setQueryStr(thisObj) {
        let param = {
            idx: thisObj.setCateidx,
        }
        if (thisObj.setBrandList.length > 0) {
            param.setBrandList = JSON.stringify(thisObj.setBrandList);
        }
        if (thisObj.setColorList.length > 0) {
            param.setColorList = JSON.stringify(thisObj.setColorList);
        }
        if (thisObj.setBenefitList.length > 0) {
            param.setBenefitList = JSON.stringify(thisObj.setBenefitList);
        }
        if (thisObj.setPriceList.length > 0) {
            param.setPriceList = JSON.stringify(thisObj.setPriceList);
        }
        if (thisObj.setRatingList.length > 0) {
            param.setRatingList = JSON.stringify(thisObj.setRatingList);
        }
        if (thisObj.setGiconList.length > 0) {
            param.setGiconList = JSON.stringify(thisObj.setGiconList);
        }
        if (thisObj.setRecomList.length > 0) {
            param.setRecomList = JSON.stringify(thisObj.setRecomList);
        }
        if (thisObj.setFilterList.length > 0) {
            param.setFilterList = JSON.stringify(thisObj.setFilterList);
            if (thisObj.setFilterList.filter((x) => (x.target == "setPriceList")).length > 0) {
                param.minprice = thisObj.setPriceValue.currentValue[0];
                param.maxprice = thisObj.setPriceValue.currentValue[1];
            }
        }
        return param;
    },
    /********************************
    * 필터 적용 쿼리스트링 목록조회 param으로 변경
    ********************************/
    setQueryStrToParam(thisObj) {
        let param = {};
        //카테고리
        param.idx = thisObj.$route.query.idx;
        //브랜드
        param.fBrandList = thisObj.$util.isEmpty(thisObj.$route.query.setBrandList) ? [] : JSON.parse(thisObj.$route.query.setBrandList);
        //색상
        param.colorlist = thisObj.$util.isEmpty(thisObj.$route.query.setColorList) ? [] : JSON.parse(thisObj.$route.query.setColorList);
        //혜택
        param.benefitList = thisObj.$util.isEmpty(thisObj.$route.query.setBenefitList) ? [] : JSON.parse(thisObj.$route.query.setBenefitList);
        param.giconList = thisObj.$util.isEmpty(thisObj.$route.query.setGiconList) ? [] : JSON.parse(thisObj.$route.query.setGiconList);
        param.recomList = thisObj.$util.isEmpty(thisObj.$route.query.setRecomList) ? [] : JSON.parse(thisObj.$route.query.setRecomList);
        param.isdelivfree = "";
        param.ispccoupon = "";
        for (let i = 0; i < param.benefitList.length; i++) {
            if (param.benefitList[i] == "freeDeli") {
                param.isdelivfree = "T";
            }
            if (param.benefitList[i] == "goodscoupon") {
                param.ispccoupon = "T";
            }
        }
        param.isdelivfree = "";
        param.ispccoupon = "";

        param.isnewbadge = "";
        param.issalebadge = "";
        param.iscouponbadge = "";
        for (let i = 0; i < param.giconList.length; i++) {
            if (param.giconList[i] == "isnewbadge") {
                param.isnewbadge = "T";
            }
            if (param.giconList[i] == "issalebadge") {
                param.issalebadge = "T";
            }
            if (param.giconList[i] == "iscouponbadge") {
                param.iscouponbadge = "T";
            }
        }
        param.isnewbadge = "";
        param.issalebadge = "";
        param.iscouponbadge = "";
        //별점
        param.ratinglist = thisObj.$util.isEmpty(thisObj.$route.query.setRatingList) ? [] : JSON.parse(thisObj.$route.query.setRatingList);
        //필터
        param.filterList = thisObj.$util.isEmpty(thisObj.$route.query.setFilterList) ? [] : JSON.parse(thisObj.$route.query.setFilterList);
        //가격
        param.minprice = null;
        param.maxprice = null;
        if (param.filterList.filter((x) => (x.target == "setPriceList")).length > 0) {
            param.minprice = thisObj.$route.query.minprice;
            param.maxprice = thisObj.$route.query.maxprice;
        }
        //정렬기준
        param.sort = thisObj.sortActive;
        return param;
    },
    /********************************
    * 쿼리스트링 각 리스트에 매핑
    ********************************/
    setQueryStrToList(thisObj) {
        thisObj.setCateidx = thisObj.$route.query.idx;
        thisObj.setBrandList = thisObj.$util.isEmpty(thisObj.$route.query.setBrandList) ? [] : JSON.parse(thisObj.$route.query.setBrandList);
        thisObj.setColorList = thisObj.$util.isEmpty(thisObj.$route.query.setColorList) ? [] : JSON.parse(thisObj.$route.query.setColorList);
        thisObj.setBenefitList = thisObj.$util.isEmpty(thisObj.$route.query.setBenefitList) ? [] : JSON.parse(thisObj.$route.query.setBenefitList);
        let priceObj = {
            currentValue: [300000, 1000000], //현재 금액
            min: 0, //최소 금액
            max: 10000000, //최대 금액
            interval: 100000, //금액 증가량
        }
        thisObj.setPriceValue = thisObj.$util.isEmpty(thisObj.$route.query.setPriceValue) ? priceObj : JSON.parse(thisObj.$route.query.setPriceValue);
        thisObj.setRatingList = thisObj.$util.isEmpty(thisObj.$route.query.setRatingList) ? [] : JSON.parse(thisObj.$route.query.setRatingList);
        thisObj.setPriceList = thisObj.$util.isEmpty(thisObj.$route.query.setPriceList) ? [] : JSON.parse(thisObj.$route.query.setPriceList);
        thisObj.setGiconList = thisObj.$util.isEmpty(thisObj.$route.query.setGiconList) ? [] : JSON.parse(thisObj.$route.query.setGiconList);
        thisObj.setRecomList = thisObj.$util.isEmpty(thisObj.$route.query.setRecomList) ? [] : JSON.parse(thisObj.$route.query.setRecomList);
        thisObj.priceList = thisObj.$util.isEmpty(thisObj.$route.query.priceList) ? [] : JSON.parse(thisObj.$route.query.priceList);
        thisObj.setFilterList = thisObj.$util.isEmpty(thisObj.$route.query.setFilterList) ? [] : JSON.parse(thisObj.$route.query.setFilterList);
    },
    sendNativeMessage(param) {
        if (window.ReactNativeWebView) {
            window.ReactNativeWebView.postMessage(JSON.stringify(param));
            //this.$store.commit("name", param.name);
        }
    },
    invitenoCrykey() {
        let param = "dadamnc!@#"
        return param;
    },
    /*************************************************************************************
     * 상품목록 최신 
     ****************************************************************************************/
     /****************************
    * 선택 옵션 필터 리스트 셋팅 처리
    ****************************/
      setFilter(thisObj, target, value) {
        if (target == "setCateList") { //카테고리
            thisObj.$util.debug("setCateList");
            let name = "";
            thisObj.cateList.forEach((element) => {
                thisObj.$util.debug(element);
                if (element.cateidx == value) {
                    name = element.catename;
                }
            });
            if (thisObj.setFilterList.filter((x) => (x.value == name)).length == 0) {
                thisObj.setFilterList.unshift({ target: target, value: name, id: value });
            } else {
                thisObj.setFilterList = thisObj.setFilterList.filter((x) => (x.value != name));
            }
        } else if (target == "setBrandList") {  //브랜드
            let name = "";
            thisObj.fBrandList.forEach((element) => {
                if (element.idx == value) {
                    name = thisObj.$util.isNull(element.enname)?element.name:element.enname ;
                }
            });
            if (thisObj.setFilterList.filter((x) => (x.value == name)).length == 0) {
                thisObj.setFilterList.unshift({ target: target, value: name, id: value });
            } else {
                thisObj.setFilterList = thisObj.setFilterList.filter((x) => (x.value != name));
            }
        } else if (target == "setColorList") { //색상
            let name = "";
            thisObj.colorList.forEach((element) => {
                if (element.colortype == value) {
                    name = element.colorname;
                }
            });
            if (thisObj.setFilterList.filter((x) => (x.value == name)).length == 0) {
                thisObj.setFilterList.unshift({ target: target, value: name, id: value });
            } else {
                thisObj.setFilterList = thisObj.setFilterList.filter((x) => (x.value != name));
            }
        } else if (target == "setBenefitList") { //혜택
            let name = "";
            thisObj.benefitList.forEach((element) => {
                if (element.id == value) {
                    name = element.name;
                }
            });
            if (thisObj.setFilterList.filter((x) => (x.value == name)).length == 0) {
                thisObj.setFilterList.unshift({ target: target, value: name, id: value });
            } else {
                thisObj.setFilterList = thisObj.setFilterList.filter((x) => (x.value != name));
            }
        } else if (target == "setGiconList") { //상품유형
            let name = "";
            thisObj.giconList.forEach((element) => {
                if (element.id == value) {
                    name = element.name;
                }
            });
            if (thisObj.setFilterList.filter((x) => (x.value == name)).length == 0) {
                thisObj.setFilterList.unshift({ target: target, value: name, id: value });
            } else {
                thisObj.setFilterList = thisObj.setFilterList.filter((x) => (x.value != name));
            }
        } else if (target == "setRecomList") { //추천유형
            let name = "";
            thisObj.recomList.forEach((element) => {
                if (element.id == value) {
                    name = element.name;
                }
            });
            if (thisObj.setFilterList.filter((x) => (x.value == name)).length == 0) {
                thisObj.setFilterList.unshift({ target: target, value: name, id: value });
            } else {
                thisObj.setFilterList = thisObj.setFilterList.filter((x) => (x.value != name));
            }
        } else if (target == "setPriceList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.target != target);
            if (thisObj.setFilterList.filter((x) => (x.value == value)).length == 0) {
                thisObj.setFilterList.unshift({ target: target, value: value, id: value });
            } else {
                thisObj.setFilterList = thisObj.setFilterList.filter((x) => (x.value != value));
            }
        } else if (target == "setRatingList") { //별점
            let name = "";
            thisObj.ratingList.forEach((element) => {
                if (element.rating == value) {
                    name = element.name;
                }
            });
            if (thisObj.setFilterList.filter((x) => (x.value == name)).length == 0) {
                thisObj.setFilterList.unshift({ target: target, value: name, id: value });
            } else {
                thisObj.setFilterList = thisObj.setFilterList.filter((x) => (x.value != name));
            }
        } else {
            return;
        }
        thisObj.$util.debug("최종setFilter값::" + JSON.stringify(thisObj.setFilterList));
    },
     /**************************
     * 필터 항목 삭제
     *************************/
      delFilter(thisObj, item) {
        thisObj.$util.debug("delFilter()...");
        thisObj.$util.debug("item::" + JSON.stringify(item));
        if (item.target == "setBrandList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setBrandList = thisObj.setBrandList.filter((x) => x !== item.id);
        } else if (item.target == "setColorList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setColorList = thisObj.setColorList.filter((x) => x !== item.id);
        } else if (item.target == "setBenefitList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setBenefitList = thisObj.setBenefitList.filter((x) => x !== item.id);
        } else if (item.target == "setGiconList") { 
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setGiconList = thisObj.setGiconList.filter((x) => x !== item.id);
        } else if (item.target == "setRecomList") { 
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setRecomList = thisObj.setRecomList.filter((x) => x !== item.id);
        } else if (item.target == "setPriceList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setPriceList = [];
            //TO_DO::필터항목에서 가격삭제시 후처리 방안 나오면 추후개발
        } else if (item.target == "setRatingList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setRatingList = thisObj.setRatingList.filter((x) => x !== item.id);
        } else if(item.target == "setCateList") {
            thisObj.setFilterList = thisObj.setFilterList.filter((x) => x.value != item.value);
            thisObj.setCateList = thisObj.setCateList.filter((x) => x !== item.id);
        }else {
            return;
        }
        thisObj.$util.debug("setBrandList:" + thisObj.setBrandList);
        thisObj.$util.debug("setColorList:" + thisObj.setColorList);
        thisObj.$util.debug("setBenefitList:" + thisObj.setBenefitList);
        thisObj.$util.debug("setFilterList:" + thisObj.setFilterList);
        thisObj.$util.debug("setRatingList:" + thisObj.setRatingList);
        thisObj.$util.debug("setGiconList:" + thisObj.setGiconList);
        thisObj.$util.debug("setRecomList:" + thisObj.setRecomList);
    },
    /************************************
     * 상품 목록 검색 조건 파라미터 생성
     ************************************/
    setSearchData(thisobj) {
        thisobj.$util.debug("setSearchData()");
        thisobj.$util.debug("setFilterList:" + thisobj.setFilterList);
        thisobj.searchData = {}


        //목록은 idx, 검색은 setCateList
        if (thisobj.$route.name == "shop-list") {
            thisobj.searchData.idx = thisobj.$route.params.idx;
        } else {
            //카테고리(검색용)
            thisobj.$util.debug(thisobj.setCateList);
            if (thisobj.setCateList.length > 0) {
                thisobj.searchData.setCateList = thisobj.setCateList;
            }
        }


        //브랜드
        if (thisobj.setBrandList.length > 0) {
            thisobj.searchData.fBrandList = thisobj.setBrandList;
        }
        //컬러
        if (thisobj.setColorList.length > 0) {
            thisobj.searchData.colorList = thisobj.setColorList;
        }
        //별점
        if (thisobj.setRatingList.length > 0) {
            thisobj.searchData.ratingList = thisobj.setRatingList;
        }
        //필터
        if (thisobj.setFilterList.length > 0) {
            thisobj.searchData.setFilterList = thisobj.setFilterList;
        }
        if (thisobj.setPriceList.length > 0) {
            thisobj.searchData.setPriceList = thisobj.setPriceList;
        }
        if (thisobj.setGiconList.length > 0) {
            thisobj.searchData.giconList = thisobj.setGiconList;
        }
        if (thisobj.setRecomList.length > 0) {
            thisobj.searchData.recomList =  thisobj.setRecomList;
        }
        //가격
        //필터에 가격 없을시 가격지정 조회x
        if (thisobj.setFilterList.filter((x) => (x.target == "setPriceList")).length > 0) {
            thisobj.searchData.minprice = thisobj.setPriceValue.currentValue[0];
            thisobj.searchData.maxprice = thisobj.setPriceValue.currentValue[1];
        } else {
            thisobj.searchData.minprice = null;
            thisobj.searchData.maxprice = null;
        }

        //헤택
        if (thisobj.setBenefitList.length > 0) {
            thisobj.searchData.benefitList = thisobj.setBenefitList;
        }
        if (thisobj.platform == "MAC001") {
            thisobj.searchData.page = thisobj.pagingData.currentPage;
        }
       
        thisobj.searchData.isdelivfree = "";
        thisobj.searchData.ispccoupon = "";
        for (let i = 0; i < thisobj.setBenefitList.length; i++) {
            if (thisobj.setBenefitList[i] == "freeDeli") {
                thisobj.searchData.isdelivfree = "T";
            }
            if (thisobj.setBenefitList[i] == "goodscoupon") {
                thisobj.searchData.ispccoupon = "T";
            }
        }
        
        if (thisobj.setRecomList.length > 0) {
            thisobj.searchData.recomList = thisobj.setRecomList;
        }

        thisobj.searchData.isnewbadge = "";
        thisobj.searchData.issalebadge = "";
        thisobj.searchData.iscouponbadge = "";
        for (let i = 0; i < thisobj.setRecomList.length; i++) {
            if (thisobj.setRecomList[i] == "isnewbadge") {
                thisobj.searchData.isnewbadge = "T";
            }
            if (thisobj.setRecomList[i] == "issalebadge") {
                thisobj.searchData.issalebadge = "T";
            }
            if (thisobj.setRecomList[i] == "iscouponbadge") {
                thisobj.searchData.iscouponbadge = "T";
            }
        }

        thisobj.searchData.sort = thisobj.sortActive;
    },
    /************************************
     * 쿼리스트링 to Param
     ************************************/
    queryStrToParam(thisobj) {
        const searchStr = decodeURIComponent(thisobj.$route.query.search);
        thisobj.searchData = JSON.parse(searchStr);
        thisobj.searchData.idx = thisobj.$route.params.idx;
        if (thisobj.platform == "MAC001") {
            thisobj.pagingData.currentPage = thisobj.$util.isNull(thisobj.searchData.page) ? thisobj.pagingData.currentPage : thisobj.searchData.page;
        }
        thisobj.sortActive = thisobj.$util.isEmpty(thisobj.searchData.sort) ? thisobj.sortActive : thisobj.searchData.sort;
        thisobj.setMaxPrice = thisobj.$util.isNull(thisobj.searchData.maxprice) ? null : thisobj.searchData.maxprice;
        thisobj.setMinPrice = thisobj.$util.isNull(thisobj.searchData.minprice) ? null : thisobj.searchData.minprice;
        thisobj.setBenefitList = thisobj.$util.isEmpty(thisobj.searchData.benefitList) ? [] : thisobj.searchData.benefitList;
        thisobj.setColorList = thisobj.$util.isEmpty(thisobj.searchData.colorList) ? [] : thisobj.searchData.colorList;
        thisobj.setBrandList = thisobj.$util.isEmpty(thisobj.searchData.fBrandList) ? [] : thisobj.searchData.fBrandList;
        thisobj.setRatingList = thisobj.$util.isEmpty(thisobj.searchData.ratingList) ? [] : thisobj.searchData.ratingList;
        thisobj.setFilterList = thisobj.$util.isEmpty(thisobj.searchData.setFilterList) ? [] : thisobj.searchData.setFilterList;
        thisobj.setPriceList = thisobj.$util.isEmpty(thisobj.searchData.setPriceList) ? [] : thisobj.searchData.setPriceList;
        thisobj.setGiconList = thisobj.$util.isEmpty(thisobj.searchData.giconList) ? [] : thisobj.searchData.giconList;
        thisobj.setRecomList = thisobj.$util.isEmpty(thisobj.searchData.recomList) ? [] : thisobj.searchData.recomList;
    },
    /****************
    * 조회 필터 쿼리 생성
    ****************/
    setFilterSearchUrl(thisObj) {
        let str = ""; //전체
        let subStr = ""; //filter용
        /*****************************************************
         * DEFAULT 조건
         * 페이지, 조회 목록수, 회원구분, 회원등급, 적용채널
         *****************************************************/
        str += "query=" + encodeURIComponent(thisObj.$route.query.content); //검색어
        //str += "&page=" + thisObj.pagingData.currentPage; //페이지
        // /str += "&totaldisplay=" + thisObj.pagingData.listCnt; //조회 총 목록수
        str += "&exquery=goods@[mumembertype:contains:" + thisObj.membertype + "]"; //회원구분 다중조건
        str += "[mumemlvtype:contains:" + thisObj.memlvtype + "]"; //회원등급 다중조건
        str += "[muappchtype:contains:" + thisObj.platform + "]"; //적용채널 다중조건
        str += "[goodsselltype:contains:GST002|GST004|GST005]";
        return str;
    },
    /****************
    * 조회 필터 쿼리 생성
    ****************/
    setSearchUrl(thisObj) {
        let str = ""; //전체
        let subStr = ""; //filter용
        /*****************************************************
         * DEFAULT 조건
         * 페이지, 조회 목록수, 회원구분, 회원등급, 적용채널
         *****************************************************/
        str += "query=" + encodeURIComponent(thisObj.$route.query.content); //검색어
        if (thisObj.platform  == "MAC001") {
            str += "&page=" + thisObj.pagingData.currentPage; //페이지
            str += "&totaldisplay=" + thisObj.pagingData.listCnt; //조회 총 목록수
        }else {
            if (thisObj.pagingData.init == "T") {
                str += "&page=1"; //페이지
                str += "&totaldisplay=" + (thisObj.pagingData.listCnt * thisObj.pagingData.currentPage); //조회 총 목록수
            }else {
                str += "&page=" + thisObj.pagingData.currentPage; //페이지
                str += "&totaldisplay=" + thisObj.pagingData.listCnt; //조회 총 목록수
            }
        }
        
        str += "&exquery=goods@[mumembertype:contains:" + thisObj.membertype + "]"; //회원구분 다중조건
        str += "[mumemlvtype:contains:" + thisObj.memlvtype + "]"; //회원등급 다중조건
        str += "[muappchtype:contains:" + thisObj.platform + "]"; //적용채널 다중조건
        str += "[goodsselltype:contains:GST002|GST004|GST005]";
        /*****************************************************
         * 필터 적용 조건 추가
         * filter: 혜택-쿠폰적용, 별점
         * exquery:카테고리, 브랜드, 컬러, 필터-무료배송
         ****************************************************/
        /***************************
         * 1)exquery 관련 추가
         ***************************/
        //필터 카테고리 조회조건 추가
        if (thisObj.setCateidx != 0) {
            str += "[cateidx:contains:" + thisObj.setCateidx + "]";
        }
        //필터 브랜드 조회조건 리스트 추가
        if (thisObj.setBrandList.length > 0) {
            str += "[brandidx:contains:" + thisObj.setBrandList.join('|') + "]";
        }
        // //필터 카테고리 조회조건 추가
        if (thisObj.setCateList.length > 0) {
            str += "[cateidx:contains:" + thisObj.setCateList.join('|') + "]";
        }
        // //필터 컬러 조회조건 추가
        if (thisObj.setColorList.length > 0) {
            str += "[colortype:contains:" + thisObj.setColorList.join('|') + "]";
        }
         // //필터 상품유형 조회조건 추가
         if (thisObj.setGiconList.length > 0) {
            str += "[colortype:contains:" + thisObj.setGiconList.join('|') + "]";
        }
         // //필터 추천유형 조회조건 추가
         if (thisObj.setRecomList.length > 0) {
            str += "[colortype:contains:" + thisObj.setRecomList.join('|') + "]";
        }
        //혜택_무료배송 조회조건 추가
        thisObj.$util.debug(thisObj.setBenefitList);
        thisObj.$util.debug(thisObj.setBenefitList.includes("freeDeli"));

        if (thisObj.setBenefitList.includes("freeDeli")) {
            str += "[isdelivfree:contains:T]";//exquery 계산임
        }

        /***************************
         * 2)filter 관련 추가
         ***************************/
        //혜택_쿠폰 조회조건 추가
        let filterStr = "";
        filterStr += "[isdisplay:match:T]";
        if (thisObj.setBenefitList.includes("goodscoupon")) {
            let target = thisObj.platform == "MAC001" ? "ispccoupon" : thisObj.platform == "MAC002" ? "ismocoupon" : "isappcoupon";
            filterStr += "[" + target + ":match:T]";
        }
        //필터 별점 조회조건 추가
        if (thisObj.setRatingList.length > 0) {
            filterStr += "[reviewavg:in:" + thisObj.setRatingList.join(' ') + "]";
        }

        //필터 가격 조회조건 추가
        if (thisObj.setFilterList.filter((x) => (x.target == "setPriceList")).length > 0) {
            let targetPrice = thisObj.platform == "MAC001" ? "pcsaleamt" : thisObj.platform == "MAC002" ? "mosaleamt" : "appsaleamt";
            filterStr += "[" + targetPrice + ":gte:" + thisObj.setPriceValue.currentValue[0] + "]";
            filterStr += "[" + targetPrice + ":lte:" + thisObj.setPriceValue.currentValue[1] + "]";
        }

        if (!thisObj.$util.isNull(filterStr)) {
            str += "&filter=goods@" + filterStr;
        }
        str += ";content@[istrash:match:F];brand@[istrash:match:F]";
        //goods@[pcsaleamt:gte:10000][pcsaleamt:lte:20000]
        /*****************************************************
         * 정렬 기준 셋팅
        *****************************************************/
        str += "&sort=" + thisObj.setSortStr();
        return str;
    }, 
    getPriceList(thisobj, cnt){
        //let standAmt =  (thisobj.setPriceValue.max - thisobj.setPriceValue.min)/cnt; //기준가
        let standAmt = 0;
        let cutprice = 1000;
        let minprice = thisobj.setPriceValue.min;
        let maxprice = thisobj.setPriceValue.max;
        standAmt =  Math.floor((maxprice- minprice)/cnt);
        thisobj.priceList = [];
        let avgAmt = thisobj.$util.priceCutting(standAmt, "C", cutprice);
        let max = avgAmt;
        //alert(avgAmt);
        //평균금액이 최고금액보다 클경우(최소, 최대금액으로 셋팅)
        if (avgAmt > maxprice) {
            thisobj.priceList.push({id:'price1', startPrice:minprice, endPrice:maxprice});
        }else if(avgAmt < cutprice){
            if(minprice == maxprice) {
                maxprice = 100000;
            }
            thisobj.priceList.push({id:'price1', startPrice:minprice, endPrice:maxprice});
        }else {
            //금액 5분위로
            for (let i = 0; i < cnt; i++) {
                if (max < maxprice) {
                    if (i == 0) {
                        cutprice = 100000;
                        thisobj.priceList.push({id:'price'+(i+1), startPrice:0, endPrice:cutprice});
                    }else if(i == cnt - 1){
                        if(i == 5) {
                            if(maxprice < 3000000) {
                                thisobj.priceList.push({id:'price'+(i+1), startPrice:maxprice, endPrice:maxprice});
                            } else {
                                thisobj.priceList.push({id:'price'+(i+1), startPrice:3000000, endPrice:maxprice});
                            }
                            
                        } else {
                            thisobj.priceList.push({id:'price'+(i+1), startPrice:thisobj.priceList[i-1].endPrice, endPrice:maxprice});
                        }
                    }else {
                        if(i == 1) {
                            cutprice = 100000;
                        } else if(i == 2) {
                            cutprice = 300000;
                        } else if(i == 3) {
                            cutprice = 500000;
                        } else if(i == 4) {
                            cutprice = 1000000;
                        }
                        thisobj.priceList.push({id:'price'+(i+1), startPrice:thisobj.priceList[i-1].endPrice, endPrice:thisobj.priceList[i-1].endPrice + cutprice});
                    }
                    max = thisobj.priceList[i].endPrice;
                }
            }
            
        }
    },
    /**
     * gtm data layer
     * @param {*} event 
     * @param {*} ecommerce 
     */
    gtmDataLayer(event, ecommerce) {
        dataLayer.push({
            'event':  event,
            'ecommerce': ecommerce
        });
    },
    /**
     * 장바구니 상품 수 가져오기
     * @param {*} thisObj 
     */
    getCartGoodsCount(thisObj) {
        let items = thisObj.$storage.getLocalStorage("CART_ITEMS");
        if (thisObj.$util.isNull(items)) {
            items = [];
        }
        
        const params = {
            items: items,
            isloading: false
        }
        thisObj.$http.post('/cart/cnt', params).then(result => {
            if (result.statusCode == 200) {
                //thisObj.$store.commit("cartGoodsCount", result.data.cnt);
                thisObj.$store.commit("cartCount", result.data.cnt);
                //this.cartGoodsCount = result.data.cnt;
                //alert("getCartGoodsCount : " + result.data.cnt);
                //return result.data.cnt;
                
                /**
                  개인정보처리방침은 페이지가 뜨자마자 modal 창으로 열어야 하는데 /common/private/policy 페이지에서는 mounted로 emit 함수가 동작하지 않음
                  해서 모든 페이지에서 호출하는 /cart/cnt 에서 결과 회신되면 해당 페이지 주소를 보고 개인정보처리방침 페이지일 때 modal로 팝업 띄우게 수정(2022-12-01, James)
                 */
                let url = window.location.href;
                
                if(url.indexOf("/common/private/policy") > 0) {
                    thisObj.$eventBus.$emit(
                      "showModal",
                      Personal,
                      "personalModal",
                      {},
                      (param) => {}
                    );
                }
            }
        });
    },
}
import Personal from "@views.mobile/etc/popup/Personal.vue";