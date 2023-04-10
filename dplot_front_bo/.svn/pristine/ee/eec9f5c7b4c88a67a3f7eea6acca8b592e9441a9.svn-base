import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import store from "@js/store.js";

export default {
    name: 'AddConstGoodsListPopup',
    components: {
        CommonDatePicker
    },
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        // 초기화
        this.onInit();
    },
    data :function(){
        return {
            searchData: {
                skey: '',           //검색구분
                sword: '',          //검색어
                sdate: 'start',     //기간구분
                startDate: '',      //등록시작일자 
                endDate: '',        //등록종료일자
                period: 'months_3', //기간
                ispbgoods: '',      //직매입여부
                dealerno: '',       //입점업체번호
                isallselltype: 'T', //상품판매상태 전체체크여부
                goodsselltypeArr: ''//상품판매상태Array
            },
            commonCode: {
                goodsselltype: []   //상품판매상태
            },
            partnersList: [],       //파트너사 목록
            isallchk: 'F',          //구성상품목록 전체체크여부
            constGoodsList: [],     //구성상품목록
            constants: store.getters['ADMIN']
        }
    },
    methods : {
        onInit: function() {
            this.getCommonCodeList();
            this.getPartnerList();
        },
        // 파트너사 목록 조회
        getPartnerList: function() {
            this.$http.post('/admin/goods/regist/partner/list', this.searchData)
                .then(result => {
                    this.$util.debug(result);
                    this.partnersList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.sdate = 'start';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.period = 'months_3';
            this.searchData.ispbgoods = '';
            this.searchData.dealerno = '';
            this.searchData.isallselltype = 'T';
            this.checkAllSellType();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['GOODSSELLTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }

                    // 검색조건 초기화
                    this.initSearchData();
                    
                    // 딜 구성상품목록 조회
                    this.searchList();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 상품목록 조회
        searchList: function() {
            this.$http.post('/admin/goods/regist/constgoods/list', this.searchData)
                .then(result => {
                    this.$util.debug(result);
                    this.constGoodsList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 시작날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startDate = value;
        },
        // 종료날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.endDate = value;
        },
        // 상품구분상태 전체체크
        checkAllSellType: function() {
            let isAllCheck = this.searchData.isallselltype;
            this.searchData.goodsselltypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.goodsselltype){
                    this.searchData.goodsselltypeArr.push(type.cmcode);
                }
            }
        },
        // 상품목록 전체체크
        checkAllGoodsList: function(value) {
            this.constGoodsList.forEach(function (item) {
                item.ischecked = value;
            });
        },
        // 상품목록 개별체크
        checkGoodsList: function() {
            let checkedList = this.constGoodsList.filter(item => {
                return item.ischecked == true;
            });

            if (this.constGoodsList.length > checkedList.length){
                this.isallchk = 'F';
            } else {
                this.isallchk = 'T';
            }
        },
        // 전체적용
        sendAllList: function() {
            if(this.constGoodsList.length == 0) {
                alert("적용할 내역이 존재하지 않습니다.");
                return;
            }
            if(confirm("전체적용 하시겠습니까?")) {
                // 팝업닫기 - 파라미터 전달
                this.closePopup(this.constGoodsList);
            }
        },
        // 선택적용
        sendSelectedList: function() {
            let targetList = this.constGoodsList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (targetList.length == 0) {
                alert("적용할 항목을 선택해 주세요.");
                return;
            }

            if(confirm("선택대상을 적용하시겠습니까?")) {
                // 팝업닫기 - 파라미터 전달
                this.closePopup(targetList);
            }
        },
        // 팝업닫기
        closePopup: function(targetList) {
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn({list: targetList});
            }
            this.$modal.hide('commonModal');
        }
    },
    watch: {
        // 상품구분상태
        'searchData.goodsselltypeArr': function(value) {
            if (value.length < this.commonCode.goodsselltype.length) {
                this.searchData.isallselltype = 'F';
            } else {
                this.searchData.isallselltype = 'T';
            }
        },
        // 조회기간
        'searchData.period': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]) * -1;

            if (type == 'aday') {
                this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getDate('-');
            } else if (type == 'months') {
                this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getDate('-');
            }
        }
    }
}