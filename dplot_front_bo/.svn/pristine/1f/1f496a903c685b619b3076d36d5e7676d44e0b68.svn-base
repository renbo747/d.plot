import CommonDatePickerFromTo from '@views.admin/common/CommonDatePickerFromTo.vue';
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';
import CommonAddGiftPopup from '@views.admin/common/popup/CommonAddGiftPopup.vue';
import CommonArraySort from '@views.admin/common/CommonArraySort.vue';

export default {
    name: 'admin.promotion.promotion.promotiondetail',
    props: ['activePromoIdx'],
    components: {
        CommonDatePickerFromTo,
        CommonArraySort
    },
    data() {
        return {
            commonCode: {
                muappchtype: [],    // 다중적용채널
                dadamembertype: [], // 다다픽회원유형
                memlvtype: [],      // 회원등급
                promodivtype: [],   // 프로모션구분
                goodsrangetype: [], // 상품대상범위
                gifttermtype: [],   // 사은품지급조건
            },
            issearch: false,            // 프로모션정보 조회여부
            isdisabled: false,          // disable 여부
            info: {
                reguserid: '',          // 작성자id
                regdate: '',            // 등록일
                moddate: '',            // 수정일
                istrash: '',            // 사용여부
                promoname: '',          // 프로모션명
                promodesc: '',          // 프로모션설명
                promostday: '',         // 프로모션시작일시
                promostdate: '',        // 프로모션시작일자
                promosthour: '',        // 프로모션시작시간
                promostmin: '',         // 프로모션시작분
                promoedday: '',         // 프로모션종료일시
                promoeddate: '',        // 프로모션종료일자
                promoedhour: '',        // 프로모션종료시간
                promoedmin: '',         // 프로모션종료분
                isallmuappch: '',       // 적용채널 전체여부
                muappchtype: '',        // 적용채널
                muappchtypeArr: [],     // 적용채널Array
                isallmember: '',        // 다중대상회원유형전체여부
                mumembertype: '',       // 다중대상회원유형
                mumembertypeArr: [],    // 다중대상회원유형Array
                isallmemlv: '',         // 다중대상회원등급전체여부
                mumemlvtype: '',        // 다중대상회원등급
                mumemlvtypeArr: [],     // 다중대상회원등급Array
                promosttype: '',        // 프로모션상태
                promodivtype: '',       // 프로모션구분
                depth1Category: {category:'대분류', idx: '', value:''},  //대분류 카테고리일련번호
                depth2Category: {category:'중분류', idx: '', value:''},  //중분류 카테고리일련번호
                depth3Category: {category:'소분류', idx: '', value:''},  //소분류 카테고리일련번호
                depth4Category: {category:'세분류', idx: '', value:''},  //세분류 카테고리일련번호
                istotcate: 'T',         // 카테고리전체여부
                goodsrangetype: '',     // 대상상품범위
                ispercent: '',          // 정률여부
                disprice: '',           // 할인액
                dispercent: '',         // 할인율
                partratio: '',          // 파트너부담비율
                dadaratio: '',          // 다다픽부담비율
                gifttermtype: '',       // 사은품 지급조건
                giftgoodscnt: '',       // 구매수량조건
                giftselcnt: '',         // 사은품선택수량
                isorderprice: '',       // 주문금액기준여부
                orderfromprice: '',     // 상품주문금액FROM
                ordertoprice: '',       // 상품주문금액TO
                isreserve: '',          // 적립금여부
                reservepoint: '',       // 적립금
                isepointdup: '',        // E포인트중복사용여부
                isover: ''              // 선착순수량 소진여부
            },
            promocateList: [],          // 특정 카테고리목록
            promogoodsList:[],          // 특정 상품목록
            isallchkpromogoods: false,  // 특정 상품목록 전체여부
            promogiftList: [],          // 대상 사은품목록
            isallchkpromogift: false,   // 대상 사은품목록 전체여부
            categoryObj: {              // 카테고리 depth별 목록
                depth1list: [],
                depth2list: [],
                depth3list: [],
                depth4list: []
            },
            giftMoveData: {                 // 노출순위 데이터
                moveValue: '',          // 움직일 값
                targetIdx: [],          // 대상 위치
                code: 'U',              // 위, 아래 코드
                isSuccess: false        // 저장 성공 여부 (** 중요)
            }
        }
    },
    mounted() {
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 초기화
                    this.onInit();
                } else {
                    alert('페이지 접근 권한이 없습니다.');
                    this.info = this.$options.data().info
                    this.onClose();
                }

                if(!this.isWrite) {
                    this.disableButtons();
                }
            }).catch(error => {
                this.$util.debug(error);
            });
    },
    methods: {
        // 초기 세팅
        onInit: function() {
            // 공통코드 조회
            this.getCommonCodeList();
            // 대분류 카테고리 목록 조회
            let params = { depth: 0, idx: 0, value: ''};
            this.getCategoryCodeList(params);
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['MUAPPCHTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE', 'PROMODIVTYPE', 'GOODSRANGETYPE', 'GIFTTERMTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    this.getPromotionInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 프로모션정보 조회
        getPromotionInfo: function() {
            this.$http.post('/admin/promotion/promotion/detail', { promoidx: this.activePromoIdx })
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    for (const [key] of Object.entries(data.info)) {
                        this.info[key] = data.info[key];
                    }
                    this.info.muappchtypeArr =  this.info.muappchtype.split(',');
                    this.info.mumembertypeArr =  this.info.mumembertype.split(',');
                    this.info.mumemlvtypeArr =  this.info.mumemlvtype.split(',');
                    this.promocateList = data.promocatelist;
                    this.promogoodsList = data.promogoodslist;
                    this.promogiftList = data.promogiftlist;

                    setTimeout(function () {
                        // 진행상태에 따른 버튼 노출여부
                        if(this.isWrite && this.info.promosttype != this.$store.getters['ADMIN'].PROMO_ST_BEFORE) {
                            this.disableButtons();
                            this.isdisabled = true;
                        }
                        
                        for (let i=0; i<this.promogiftList.length; i++){
                            let giftItem = this.promogiftList[i];
                            if (giftItem.fcfscnt == giftItem.usecnt) {
                                this.info.isover = true;
                            }
                        }
                        this.issearch = true;
                    }.bind(this), 50);
                    
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 수정불가하도록 버튼 hide
        disableButtons: function() {
            let buttons = this.$el.getElementsByTagName('button');
            let showButtonsId = [];
            if (this.info.promosttype == this.$store.getters['ADMIN'].PROMO_ST_PROCEEDING) {
                showButtonsId = ['addGift', 'save', 'close', 'cancel'];
            } else if (this.info.promosttype == this.$store.getters['ADMIN'].PROMO_ST_END) {
                showButtonsId = ['close', 'cancel'];
            }
            for(let button of buttons){
                if(showButtonsId.indexOf(button.id) < 0) {
                    button.style.display = 'none';
                    button.disabled = true;
                }
            }
        },
        // datepicker callback
        pickerChangeEvent(data) {
            this.info.promostdate = data.fromYYYYMMDD;
            this.info.promosthour = data.fromHH;
            this.info.promostmin = data.fromMM;
            this.info.promostday = data.fromDate12;

            this.info.promoeddate = data.toYYYYMMDD;
            this.info.promoedhour = data.toHH;
            this.info.promoedmin = data.toMM;
            this.info.promoedday = data.toDate12;
        },
        // 적용채널 전체적용 체크
        checkAllAppchtype: function() {
            let isAllCheck = this.info.isallmuappch;
            this.info.muappchtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.info.muappchtypeArr.push(type.cmcode);
                }
            }
        },
        // 대상회원유형 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.info.isallmember;
            this.info.mumembertypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.dadamembertype){
                    this.info.mumembertypeArr.push(type.cmcode);
                }
            }
        },
        // 대상회원등급 전체체크
        checkAllMemlvtype: function() {
            let isAllCheck = this.info.isallmemlv;
            this.info.mumemlvtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.memlvtype){
                    this.info.mumemlvtypeArr.push(type.cmcode);
                }
            }
        },
        // 카테고리분류 목록 조회
        getCategoryCodeList: function(obj) {
            if (obj.depth < 4) {
                // 하위 카테고리 목록 초기화
                for (let i=parseInt(obj.depth); i<4; i++) {
                    let listName = 'depth'+ (i+1) +'list';
                    this.categoryObj[listName] = [];
                    let categoryName = 'depth'+ (i+1) +'Category';
                    this.info[categoryName].idx = '';
                    this.info[categoryName].value = '';
                }
                // 하위카테고리 목록 조회
                obj.isloading = false;
                this.$http.post('/admin/goods/regist/cate/list', obj)
                    .then(result => {
                        this.$util.debug(result);
                        let categoryList = result.data.list;

                        // 카테고리 목록 세팅
                        let targetDepth = parseInt(obj.depth) +1;
                        let targetListName = 'depth'+ targetDepth +'list';
                        this.categoryObj[targetListName] = categoryList;
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            } else {
                this.info.depth4Category.idx = obj.idx;
            }
        },
        // 카테고리분류 정보 셋팅
        setCategoryCodeInfo: function(index, idx) {
            let targetName = 'depth' + index + 'Category';
            this.info[targetName].idx = idx;
            this.categoryObj['depth' + index + 'list'].forEach(item => {
                if (item.idx == idx) {
                    this.info[targetName].value = item.value;
                }
            });
            
            let params = {depth: index, idx: idx};
            this.getCategoryCodeList(params);
        },
        // 프로모션 카테고리 추가
        addPromoCategory: function(isadd) {
            // 카테고리 선택 필수 체크
            if (this.$util.isNull(this.info.depth1Category.idx)) {
                alert('카테고리를 선택해주세요.');
                return;
            }
            
            let cateidx = '';
            let fullCategoryName = '';
            for (let i=0; i<4; i++) {
                let listName = 'depth' + (i+1) + 'list';
                let categoryName = 'depth' + (i+1) + 'Category';
                let categoryObj = this.info[categoryName];
                
                // 카테고리명 세팅
                if (i == 0) {
                    fullCategoryName = fullCategoryName.concat(categoryObj.value);
                } else {
                    if (this.categoryObj[listName].length > 0 && !this.$util.isNull(categoryObj.idx)) {
                        fullCategoryName = fullCategoryName.concat(' > ', categoryObj.value);
                    }
                }
                // 최종선택 카테고리 세팅
                if (this.categoryObj[listName].length > 0 && !this.$util.isNull(categoryObj.idx)) {
                    cateidx = categoryObj.idx;
                }
            }

            for (let i=0; i<this.promocateList.length; i++) {
                let categoryObj = this.promocateList[i];
                // 카테고리 중복체크
                if (cateidx == categoryObj.cateidx) {
                    alert('이미 추가된 카테고리 입니다.');
                    return;
                }
            }

            // 카테고리 목록에 추가
            let params = {
                isadd: isadd,
                fullcategoryname: fullCategoryName,
                cateidx: cateidx
            }
            this.promocateList.push(params);

            // 카테고리 초기화
            this.info.depth1Category.idx = '';
            this.info.depth2Category.idx = '';
            this.info.depth3Category.idx = '';
            this.info.depth4Category.idx = '';
            this.info.depth1Category.value = '';
            this.info.depth2Category.value = '';
            this.info.depth3Category.value = '';
            this.info.depth4Category.value = '';
            this.categoryObj.depth2list = [];
            this.categoryObj.depth3list = [];
            this.categoryObj.depth4list = [];
        },
        // 프로모션 카테고리 삭제
        removeCategory: function(obj) {
            let findIndex = this.promocateList.indexOf(obj);
            this.promocateList.splice(findIndex, 1);
        },
        // 프로모션 상품 추가
        addGoods: function() {
            if (this.$util.isNull(this.info.goodsrangetype)) {
                alert("대상상품범위를 선택해주세요.");
                return;
            }
            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, {isshowoption: 'T'},
                (result) => {
                    // 팝업에서 가져온 결과 추가상품 목록에 적용(이미 추가되어 있는 상품 제외)
                    let resultList = result.list;
                    
                    let isadd = this.info.goodsrangetype==this.$store.getters['ADMIN'].GOODS_RANGE_INCLUDE? 'T' : 'F';
                    for (let i=0; i<resultList.length; i++) {
                        let existCnt = this.promogoodsList.filter(item => {
                            return item.goodsno+'_'+item.optioncode == resultList[i].goodsno+'_'+resultList[i].optioncode;
                        }).length;
                        if (existCnt == 0) {
                            resultList[i].isadd = isadd;
                            resultList[i].ischecked = false;
                            this.promogoodsList.push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 프로모션 상품 삭제
        removeGoods: function() {
            let delList = this.promogoodsList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (delList.length == 0) {
                alert('삭제할 상품을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            delList.forEach(item => {
                let findIndex = this.promogoodsList.indexOf(item);
                this.promogoodsList.splice(findIndex, 1);
            });
            if (this.promogoodsList.length == 0) {
                this.isallchkpromogoods = false;
            }
        },
        // 상품목록 전체체크
        checkAllGoodsList: function(value) {
            this.promogoodsList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 상품목록 개별체크
        checkGoodsList: function() {
            let checkedList = this.promogoodsList.filter(item => {
                return item.ischecked == true;
            });
            if (this.promogoodsList.length > checkedList.length){
                this.isallchkpromogoods = false;
            } else {
                this.isallchkpromogoods = true;
            }
        },
        // 프로모션 사은품 추가
        addGift: function() {
            this.$eventBus.$emit('modalShow', CommonAddGiftPopup, null,
                (result) => {
                    // 팝업에서 가져온 결과 추가상품 목록에 적용(이미 추가되어 있는 상품 제외)
                    let resultList = result.list;
                    
                    for (let i=0; i<resultList.length; i++) {
                        let existCnt = this.promogiftList.filter(item => {
                            return item.giftidx == resultList[i].giftidx;
                        }).length;
                        if (existCnt == 0) {
                            resultList[i].usecnt = 0;
                            resultList[i].ischecked = false;
                            resultList[i].sortnum = this.promogiftList.length + 1;
                            this.promogiftList.push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 프로모션 사은품 삭제
        removeGift: function() {
            // 선택항목 체크
            if (this.giftMoveData.targetIdx.length == 0) {
                alert('삭제할 사은품을 선택해주세요.');
                return;
            }
            // 목록에서 선택된 항목 삭제
            this.giftMoveData.targetIdx.sort((a, b) => b-a);
            for (let i=0; i<this.giftMoveData.targetIdx.length; i++) {
                this.promogiftList.splice(this.giftMoveData.targetIdx[i], 1);
            }
            // 삭제후 목록이 없는경우 전체체크 해제
            if (this.promogiftList.length == 0) {
                this.isallchkpromogift = false;
            }
            this.giftMoveData.targetIdx = [];
            this.giftMoveData.isSuccess = false;
        },
        // 사은품목록 전체체크
        checkAllGiftList: function(value) {
            this.giftMoveData.targetIdx = [];
            if (value) {
                this.promogiftList.forEach((item, index) => {
                    this.giftMoveData.targetIdx.push(index);
                });
            }
        },
        // 사은품목록 개별체크
        checkGiftList: function() {
            if (this.promogiftList.length > this.giftMoveData.targetIdx.length){
                this.isallchkpromogift = false;
            } else {
                this.isallchkpromogift = true;
            }
        },
        // 입력 validation 체크
        checkValidation: function() {
            let checkResult = true;
            // 필수체크
            let valid = [
                {field: 'info.istrash', type: 'S', name:'[기본정보] 사용여부', required: true},
                {field: 'info.promoname', type: 'I', name:'[기본정보] 프로모션명', required: true},
                {field: 'info.promodesc', type: 'I', name:'[기본정보] 설명', required: true},
                {field: 'info.promostdate', type: 'I', name:'[조건설정] 진행시작일자', required: true},
                {field: 'info.promosthour', type: 'I', name:'[조건설정] 진행시작시간', required: true},
                {field: 'info.promostmin', type: 'I', name:'[조건설정] 진행시작분', required: true},
                {field: 'info.promoeddate', type: 'I', name:'[조건설정] 진행종료일자', required: true},
                {field: 'info.promoedhour', type: 'I', name:'[조건설정] 진행종료시간', required: true},
                {field: 'info.promoedmin', type: 'I', name:'[조건설정] 진행종료분', required: true},
                {field: 'info.muappchtype', type: 'S', name:'[조건설정] 적용채널', required: true},
                {field: 'info.mumembertype', type: 'S', name:'[조건설정] 대상회원유형', required: true},
                {field: 'info.mumemlvtype', type: 'S', name:'[조건설정] 대상회원등급', required: true},
                {field: 'info.promodivtype', type: 'S', name:'[조건설정] 프로모션구분', required: true},
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                checkResult = false;
                alert(msg);
            }
            // 프로모션명 공백체크
            if(checkResult && this.$util.isNull(this.info.promoname.trim())) {
                checkResult = false;
                alert('[기본정보] 프로모션명을 입력해주세요.');
            }
            // 설명 공백체크
            if(checkResult && this.$util.isNull(this.info.promodesc.trim())) {
                checkResult = false;
                alert('[기본정보] 설명을 입력해주세요.');
            }
            // 전시기간 From-To 체크
            if(checkResult && this.info.promostday > this.info.promoedday) {
                checkResult = false;
                alert('[조건설정] 진행종료일시를 진행시작일시 이후로 입력하세요.');
            }

            // 프로모션구분이 사은품 경우 (화면 순서상 앞에서 체크)
            if (checkResult && this.info.promodivtype == this.$store.getters['ADMIN'].PROMO_DIV_GIFT) {
                // 사은품 지급조건
                if (this.$util.isNull(this.info.gifttermtype)) {
                    checkResult = false;
                    alert('[조건설정] 사은품 지급조건을 선택해주세요.');
                }
            }

            // 대상카테고리범위
            if (checkResult && this.$util.isNull(this.info.istotcate)) {
                checkResult = false;
                alert('[조건설정] 대상카테고리범위를 선택하세요.');
            }
            // 특정카테고리목록
            if (checkResult && this.info.istotcate == 'F' && this.promocateList.length == 0) {
                checkResult = false;
                alert('[조건설정] 특정카테고리를 추가해주세요.');
            }
            // 대상상품범위(카테고리대상범위-전체)
            if (checkResult && this.info.istotcate=='T' && this.$util.isNull(this.info.goodsrangetype)) {
                checkResult = false;
                alert('[조건설정] 대상상품범위를 선택하세요.');
            }
            // 특정카테고리목록(카테고리대상범위-전체 ,상품대상범위-추가/제외)
            if (checkResult && this.info.istotcate=='T' && this.info.goodsrangetype != this.$store.getters['ADMIN'].GOODS_RANGE_ALL && this.promogoodsList.length == 0) {
                checkResult = false;
                alert('[조건설정] 대상상품을 추가해주세요.');
            }

            // 프로모션구분이 할인인 경우
            if (checkResult && this.info.promodivtype == this.$store.getters['ADMIN'].PROMO_DIV_DISCOUNT) {
                // 할인율구분
                if (this.$util.isNull(this.info.ispercent)) {
                    checkResult = false;
                    alert('[조건설정] 할인율 구분을 선택해주세요.');
                }
                // 할인율
                if (checkResult && this.info.ispercent == 'T' && this.$util.isNull(this.info.dispercent)) {
                    checkResult = false;
                    alert('[조건설정] 할인율을 입력해주세요.');
                }
                // 할인액
                if (checkResult && this.info.ispercent == 'F' && this.$util.isNull(this.info.disprice)) {
                    checkResult = false;
                    alert('[조건설정] 할인액을 입력해주세요.');
                }
                // 할인비용 분담비율
                if (checkResult && this.$util.isNull(this.info.partratio)) {
                    checkResult = false;
                    alert('[조건설정] 할인비용 분담비율을 입력해주세요.');
                }
            }
            
            // 프로모션구분이 사은품 경우
            if (checkResult && this.info.promodivtype == this.$store.getters['ADMIN'].PROMO_DIV_GIFT) {
                // 사은품 지급조건-특정상품구매시, 구매수량조건
                if (this.info.gifttermtype == this.$store.getters['ADMIN'].GIFT_TERM_PURCH && this.$util.isNull(this.info.giftgoodscnt)) {
                    checkResult = false;
                    alert('[조건설정] 구매수량조건을 입력해주세요.');
                }
                // 사은품 지급조건-특정상품구매시지급이 아닌 경우만, 주문금액기준여부/구매금액FROM
                if (checkResult && this.info.gifttermtype != this.$store.getters['ADMIN'].GIFT_TERM_PURCH) {
                    if(this.$util.isNull(this.info.isorderprice)) {
                        checkResult = false;
                        alert('[조건설정] 구매금액제한 기준을 선택해주세요.');
                    }
                    if(checkResult && this.$util.isNull(this.info.orderfromprice)) {
                        checkResult = false;
                        alert('[조건설정] 구매금액제한 최저가를 입력해주세요.');
                    }
                }
                // 사은품 지급조건-특정구매구간구매시, 구매금액TO
                if (checkResult && this.info.gifttermtype == this.$store.getters['ADMIN'].GIFT_TERM_PRICE_RANGE) {
                    if(this.$util.isNull(this.info.ordertoprice)) {
                        checkResult = false;
                        alert('[조건설정] 구매금액제한 최고가를 입력해주세요.');
                    }
                }
                // 대상사은품 필수체크
                if (checkResult && this.promogiftList.length == 0) {
                    checkResult = false;
                    alert('[조건설정] 대상사은품을 추가해주세요.');
                }
                // 대상사은품 선착순수량, 재고 체크
                if (checkResult) {
                    for(let i=0; i<this.promogiftList.length; i++) {
                        let giftItem = this.promogiftList[i];
                        if (checkResult && (this.$util.isNull(giftItem.fcfscnt) || giftItem.fcfscnt == 0)) {
                            checkResult = false;
                            alert('[조건설정] 대상사은품의 선착순수량을 입력해주세요.');
                        }
                        if (checkResult && parseInt(giftItem.fcfscnt) <= parseInt(giftItem.usecnt)) {
                            checkResult = false;
                            alert('[조건설정] 대상사은품 선착순수량은 사용량 이상만 입력 가능합니다.');
                        }
                    }
                }
                // 사은품 지급조건-첫구매가 아닌경우만
                if (checkResult && this.info.gifttermtype != this.$store.getters['ADMIN'].GIFT_TERM_FRST_PURCH && this.$util.isNull(this.info.giftselcnt)) {
                    checkResult = false;
                    alert('[조건설정] 사은품선택조건을 입력해주세요.');
                }
            }

            // 프로모션구분이 적립금 경우
            if (checkResult && this.info.promodivtype == this.$store.getters['ADMIN'].PROMO_DIV_RESERVE) {
                // 구매수량조건
                if (this.$util.isNull(this.info.giftgoodscnt)) {
                    checkResult = false;
                    alert('[조건설정] 구매수량조건을 입력해주세요.');
                }
                // 적립금 구분
                if (checkResult && this.$util.isNull(this.info.isreserve)) {
                    checkResult = false;
                    alert('[조건설정] 적립금 구분을 선택해주세요.');
                }
                // 적립금
                if (checkResult && this.$util.isNull(this.info.reservepoint)) {
                    checkResult = false;
                    alert('[조건설정] 적립금을 입력해주세요.');
                }
                // 적립금여부-E포인트, E포인트 중복사용여부
                if (checkResult && this.info.isreserve=='F' && this.$util.isNull(this.info.isepointdup)) {
                    checkResult = false;
                    alert('[조건설정] D포인트중복사용여부를 선택해주세요.');
                }
            }

            return checkResult;
        },
        // 저장
        save: function() {
            if (this.checkValidation()) {
                if (this.info.istotcate=='F' && this.info.goodsrangetype != this.$store.getters['ADMIN'].GOODS_RANGE_ALL && this.promogoodsList.length == 0) {
                    this.info.goodsrangetype = '';
                }
                if(confirm('저장하시겠습니까?')) {
                    let params = Object.assign({}, this.info);
                    params.promocatelist = this.promocateList;
                    params.promogoodslist = this.promogoodsList;
                    params.promogiftList = this.promogiftList;
                    this.$http.post('/admin/promotion/promotion/update', params)
                        .then(result => {
                            this.$util.debug(result);
                            if (result.statusCode =='200') {
                                alert('저장이 완료되였습니다.');
                                this.$emit('closePopup', true);
                            }
                        })
                        .catch(error => {
                            this.$util.debug(error);
                        });
                }
            }
        },
        // 팝업 닫기
        onClose: function() {
            this.$emit('closePopup');
        }
    },
    watch: {
        // 적용채널
        'info.muappchtypeArr': function(value) {
            if (value.length < this.commonCode.muappchtype.length) {
                this.info.isallmuappch = 'F';
            } else {
                this.info.isallmuappch = 'T';
            }
            this.info.muappchtype = this.info.muappchtypeArr.join();
        },
        // 대상회원유형
        'info.mumembertypeArr': function(value) {
            if (value.length < this.commonCode.dadamembertype.length) {
                this.info.isallmember = 'F';
            } else {
                this.info.isallmember = 'T';
            }
            this.info.mumembertype = this.info.mumembertypeArr.join();
        },
        // 대상회원등급
        'info.mumemlvtypeArr': function(value) {
            if (value.length < this.commonCode.memlvtype.length) {
                this.info.isallmemlv = 'F';
            } else {
                this.info.isallmemlv = 'T';
            }
            this.info.mumemlvtype = this.info.mumemlvtypeArr.join();
        },
        // 프로모션구분
        'info.promodivtype': function() {
            if (this.issearch) {
                this.promocateList = [];
                this.promogoodsList = [];
                this.promogiftList = [];
                this.isallchkpromogoods = false;
                this.isallchkpromogift = false;
                this.info.istotcate = 'T';
                this.info.goodsrangetype = this.$store.getters['ADMIN'].GOODS_RANGE_ALL;
                this.info.ispercent = '';
                this.info.disprice = '';
                this.info.dispercent = '';
                this.info.partratio = '0';
                this.info.dadaratio = '100';
                this.info.gifttermtype = '';
                this.info.giftgoodscnt = '';
                this.info.giftselcnt = '';
                this.info.isorderprice = '';
                this.info.orderfromprice = '';
                this.info.ordertoprice = '';
                this.info.isreserve = '';
                this.info.reservepoint = '';
                this.info.isepointdup = '';
            }
        },
        // 프로모션구분
        'info.gifttermtype': function() {
            if (this.issearch) {
                this.promocateList = [];
                this.promogoodsList = [];
                this.promogiftList = [];
                this.isallchkpromogoods = false;
                this.isallchkpromogift = false;
                this.info.istotcate = 'T';
                this.info.goodsrangetype = this.$store.getters['ADMIN'].GOODS_RANGE_ALL;
                this.info.giftgoodscnt = '';
                this.info.giftselcnt = '';
                this.info.isorderprice = '';
                this.info.orderfromprice = '';
                this.info.ordertoprice = '';
            }
        },
        // 대상카테고리범위
        'info.istotcate': function(value) {
            if (this.issearch) {
                this.info.depth1Category.idx = '';
                this.info.depth2Category.idx = '';
                this.info.depth3Category.idx = '';
                this.info.depth4Category.idx = '';
                this.info.depth1Category.value = '';
                this.info.depth2Category.value = '';
                this.info.depth3Category.value = '';
                this.info.depth4Category.value = '';
                this.categoryObj.depth2list = [];
                this.categoryObj.depth3list = [];
                this.categoryObj.depth4list = [];
                this.promocateList = [];
                if (value === 'F' && this.info.goodsrangetype === this.$store.getters['ADMIN'].GOODS_RANGE_ALL) {
                    this.info.goodsrangetype = '';
                    this.promogoodsList = [];
                }
            }
        },
        // 대상상품범위
        'info.goodsrangetype': function(value) {
            if(this.issearch && value == this.$store.getters['ADMIN'].GOODS_RANGE_ALL) {
                this.promogoodsList = [];
            }
        },
        // 정률여부
        'info.ispercent': function() {
            if (this.issearch) {
                this.info.disprice = '';
                this.info.dispercent = '';
            }
        },
        // 적립금여부
        'info.isreserve': function() {
            if (this.issearch) {
                this.info.isepointdup = '';
            }
        },
        // 할인액 - 숫자만 입력
        'info.disprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.disprice = value.replace(/(^0[\d]|[^\d])/gi, '');
        },
        // 할인율 - 숫자 (소수 2자리까지)
        'info.dispercent': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            var pattern = /(^\d+$)|(^\d{1,5}.\d{0,2}$)/;

            if (!pattern.test(value)) {
                value = value.substr(0, value.length-1);
            }
            return this.info.dispercent = value;
        },
        // 파트너사 할인부담율 - 숫자 (소수 2자리까지)
        'info.partratio': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            var pattern = /(^\d+$)|(^\d{1,5}.\d{0,2}$)/;

            if (!pattern.test(value)) {
                value = value.substr(0, value.length-1);
            }

            let floatValue = parseFloat(value);
            if(floatValue >= 100) {
                this.info.dadaratio = 0;
            } else {
                this.info.dadaratio = 100 - floatValue;
            }

            return this.info.partratio = value;
        },
        // 구매수량조건 - 숫자만 입력
        'info.giftgoodscnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.giftgoodscnt = value.replace(/(^0[\d]|[^\d])/gi, '');
        },
        // 사은품선택조건 - 숫자만 입력
        'info.giftselcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.giftselcnt = value.replace(/(^0[\d]|[^\d])/gi, '');
        },
        // 구매금액 FROM
        'info.orderfromprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.orderfromprice = value.replace(/(^0[\d]|[^\d])/gi, '');
        },
        // 구매금액 TO
        'info.ordertoprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.ordertoprice = value.replace(/(^0[\d]|[^\d])/gi, '');
        },
        // 적립금
        'info.reservepoint': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.reservepoint = value.replace(/(^0[\d]|[^\d])/gi, '');
        }
    }
}