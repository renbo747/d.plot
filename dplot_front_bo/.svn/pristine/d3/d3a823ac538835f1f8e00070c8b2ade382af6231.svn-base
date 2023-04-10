import XLSX from 'xlsx'
import DatePicker from "vue2-datepicker";
import CommonDatePickerFromTo from '@views.admin/common/CommonDatePickerFromTo.vue';
import CommonAddUserPopup from '@views.admin/common/popup/CommonAddUserPopup.vue';
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';

export default {
    name: 'admin.promotion.coupon.couponregist',
    props: ['activeComcpnidx', 'activeCpninfoidx'],
    components: {
        CommonDatePickerFromTo,
        DatePicker
    },
    data() {
        return {
            commonCode: {
                comcpntype: [],         // 쿠폰종류
                cpnissuest: [],         // 발급상태
                cpnissuetype: [],       // 발급종류
                dadamembertype: [],     // 다다픽회원유형
                memlvtype: [],          // 회원등급
                mumemrcvtype: [],       // 다중수신동의
                goodsrangetype: [],     // 상품범위
                cpnusetype: [],         // 사용기간종류
                muappchtype: [],        // 적용채널
                mucpnduptype: [],       // 장바구니쿠폰중복사용범위
            },
            sortData: {
                dadamembertypename: 'dadamembertypename_asc',
                memlvtypename: 'memlvtypename_asc',
                regdate: 'regdate_desc'
            },
            info: {
                /* 기본정보 */
                istrash: '',        // 사용여부
                comcpntype: '',     // 쿠폰종류
                cpnissuetype: '',   // 발급종류
                cpnname: '',        // 쿠폰명
                cpndesc: '',        // 설명
                /* 발급대상회원 */
                ismemtype: '',              // 특정유형등급대상여부
                isallmember: '',            // 다중대상회원유형전체여부
                mumembertype: '',           // 다중대상회원유형
                mumembertypeArr: [],        // 다중대상회원유형Array
                isallmemlv: '',             // 다중대상회원등급전체여부
                mumemlvtype: '',            // 다중대상회원등급
                mumemlvtypeArr: [],         // 다중대상회원등급Array
                issueTargetMemberList: [],  // 발급대상회원(특정회원) 목록 
                isrcvlimit: '',             // 수신동의제한여부
                mumemrcvtype: '',           // 다중수신동의
                mumemrcvtypeArr: [],        // 다중수신동의Array
                /* 쿠폰적용대상 */
                istotcate: '',              // 전체카테고리여부
                depth1Category: {category:'대분류', idx: '', value:''},  //대분류 카테고리일련번호
                depth2Category: {category:'중분류', idx: '', value:''},  //중분류 카테고리일련번호
                depth3Category: {category:'소분류', idx: '', value:''},  //소분류 카테고리일련번호
                depth4Category: {category:'세분류', idx: '', value:''},  //세분류 카테고리일련번호
                cpncateList: [],            // 카테고리 목록
                goodsrangetype: '',         // 대상상품범위
                cpngoodsList: [],           // 상품목록
                isallchkcpngoods: false,    // 상품목록 전체체크 여부
                partratio: '',              // 파트너부담비율
                dadaratio: '',              // 다다픽부담비율
                /* 사용조건 */
                cpnusetype: '',             // 쿠폰사용기간종류
                cpnuseperiod: '',           // 쿠폰사용기간
                cpnusestday: '',            // 쿠폰사용시작일
                cpnusestdate: '',           // 쿠폰사용시작일자
                cpnusesthour: '',           // 쿠폰사용시작시
                cpnusestmin: '',            // 쿠폰사용시작분
                cpnuseedday: '',            // 쿠폰사용종료일
                cpnuseeddate: '',           // 쿠폰사용종료일자
                cpnuseedhour: '',           // 쿠폰사용종료시
                cpnuseedmin: '',            // 쿠폰사용종료분
                issuedaycnt: '',            // 쿠폰발급일기준일
                isallmuappch: '',           // 적용채널 전체여부
                muappchtype: '',            // 적용채널
                muappchtypeArr: [],         // 적용채널Array
                isorderlimit: '',           // 사용가능금액적용여부
                orlimitamt: '',             // 사용가능기준금액
                maxdisamt: '',              // 최대할인금액
                ispercent: '',              // 정률여부
                disprice: '',               // 할인액
                dispercent: '',             // 할인율
                isallcpndup: '',            // 장바구니쿠폰중복사용범위전체여부
                mucpnduptype: '',           // 장바구니쿠폰중복사용범위
                mucpnduptypeArr: [],        // 장바구니쿠폰중복사용범위Array
                isrvmargin: '',             // 역마진체크여부
                evrcpnuseperiod: '',        // 정기발급쿠폰사용기간
                evrcpnusestday: '',         // 정기발급사용시작일
                evrcpnusestdate: '',        // 정기발급사용시작일자
                evrcpnusesthour: '',        // 정기발급사용시작시간
                evrcpnusestmin: '',         // 정기발급사용시작분
                evrcpnuseedday: '',         // 정기발급사용종료일
                evrcpnuseeddate: '',        // 정기발급사용종료일자
                evrcpnuseedhour: '',        // 정기발급사용종료시간
                evrcpnuseedmin: '',         // 정기발급사용종료분
                /* 발급조건 */
                isnowissue: 'T',        // 즉시발급여부
                cpnissueday: '',        // 발급일시
                cpnissuedate: '',       // 발급일자
                cpnissuehour: '',       // 발급시
                cpnissuemin: '',        // 발급분
                isautopay: '',          // 자동발급여부
                iscntlimt: '',          // 쿠폰수량제한여부
                cpnlimtcnt: '',         // 쿠폰수량제한
                isdupperson: '',        // 동일인재발급여부
                dupcnt: '',             // 동일인재발급수량
                isbirththeday: '',      // 생일당일여부
                birthbfdaycnt: '',      // 생일쿠폰생일전발급일수
                iseveryday: '',         // 정기발급발급시점매일발급여부
                everydayloop: '',       // 정기발급반복일수
                everymonthloop: '',     // 정기발급반복월일
                everyhhmm: '',          // 정기발급발급시분
                everyhh: '',            // 정기발급발급시
                everymm: '',            // 정기발급발급분
                /* 구매확정조건 */
                isfirstord: '',         // 구매확정조건첫구매확정여부
                isordtotcate: '',       // 구매확정전체카테고리여부
                depth1OrdCategory: {category:'대분류', idx: '', value:''},  // 구매확정 대분류 카테고리일련번호
                depth2OrdCategory: {category:'중분류', idx: '', value:''},  // 구매확정 중분류 카테고리일련번호
                depth3OrdCategory: {category:'소분류', idx: '', value:''},  // 구매확정 소분류 카테고리일련번호
                depth4OrdCategory: {category:'세분류', idx: '', value:''},  // 구매확정 세분류 카테고리일련번호
                cpnordcateList: [],         // 구매확정 카테고리 목록
                ordgdrangetype: '',      // 구매확정 상품범위
                cpnordgoodsList: [],        // 구매확정 상품목록
                isallchkcpnordgoods: false, // 구매확정 상품목록 전체체크 여부
            },
            categoryObj: {              // 카테고리 depth별 목록
                depth1list: [],
                depth2list: [],
                depth3list: [],
                depth4list: [],
                depth1Ordlist: [],
                depth2Ordlist: [],
                depth3Ordlist: [],
                depth4Ordlist: []
            },
            issearch: false,            // 조회여부
            isRead: false,              // 읽기권한여부
            isWrite: false,             // 쓰기권한여부
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead) {
                // 초기화
                this.onInit();
                // 대분류 카테고리 목록 조회
                let params = { depth: 0, idx: 0, value: ''};
                this.getCategoryCodeList(params);
            } else {
                alert('페이지 접근 권한이 없습니다.');
                this.onClose();
            }

            if(!this.isWrite){
                let buttons = this.$el.getElementsByTagName('button');

                for(let button of buttons){
                    if(button.className !== 'pop-close') {
                        button.style.display = 'none';
                        button.disabled = true;
                    }
                }
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        onInit() {
            // 공통코드 조회
            this.getCommonCodeList();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['COMCPNTYPE', 'CPNISSUEST', 'CPNISSUETYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE'
                            , 'MUMEMRCVTYPE', 'GOODSRANGETYPE', 'CPNUSETYPE', 'MUAPPCHTYPE', 'MUCPNDUPTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 초기데이터 세팅
                    this.initData();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 초기데이터 세팅
        initData: function() {
            // 복사등록인 경우 쿠폰정보 조회
            if (!this.$util.isNull(this.activeComcpnidx)) {
                // 쿠폰상세내역 조회
                this.getCouponDetail();
            } else {
                this.issearch = true;
                this.info.istrash = 'F';            // 사용여부
                this.info.ismemtype = 'T';          // 특정회원여부
                this.info.isallmember = 'T';        // 회원유형전체여부
                this.info.isallmemlv = 'T';         // 회원등급전체여부
                this.info.isrcvlimit = 'F';         // 수신동의제한여부
                this.info.isrcvlimit = 'F';         // 수신동의제한여부
                this.info.istotcate = 'T';          // 전체카테고리여부
                this.info.goodsrangetype = this.$store.getters['ADMIN'].GOODS_RANGE_ALL;    // 대상상품범위
                this.info.isallmuappch = 'T';       // 적용채널 전체여부
                this.checkAllMemlvtype();
                this.checkAllMembertype();
                this.checkAllAppchtype();
            }
        },
        // 쿠폰 상세내역 조회
        getCouponDetail: function() {
            this.$http.post('/admin/promotion/coupon/detail', { comcpnidx: this.activeComcpnidx, cpninfoidx: this.activeCpninfoidx })
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    for (const [key] of Object.entries(data.info)) {
                        this.info[key] = data.info[key];
                    }
                    this.info.mumembertypeArr =  this.$util.isNull(this.info.mumembertype)? [] : this.info.mumembertype.split(',');
                    this.info.mumemlvtypeArr =  this.$util.isNull(this.info.mumemlvtype)? [] : this.info.mumemlvtype.split(',');
                    this.info.muappchtypeArr =  this.$util.isNull(this.info.muappchtype)? [] : this.info.muappchtype.split(',');
                    this.info.mucpnduptypeArr =  this.$util.isNull(this.info.mucpnduptype)? [] : this.info.mucpnduptype.split(',');
                    this.info.mumemrcvtypeArr =  this.$util.isNull(this.info.mumemrcvtype)? [] : this.info.mumemrcvtype.split(',');
                    this.info.issueTargetMemberList = data.issuetargetmemberlist;
                    this.info.cpncateList = data.cpncatelist;
                    this.info.cpngoodsList = data.cpngoodslist;
                    this.info.cpnordcateList = data.cpnordcatelist;
                    this.info.cpnordgoodsList = data.cpnordgoodslist;
                    this.info.couponissueList = data.couponissuelist;
                    this.info.couponmemissueList = data.couponmemissuelist;
                    if(this.info.isdupperson == 'F'){
                        this.info.isdupperson = 'T';
                        this.info.dupcnt = '1';

                    }            
                    // 정기발급발급시분이 있는 경우 세팅
                    if(!this.$util.isNull(this.info.everyhhmm)) {
                        this.info.everyhh = this.info.everyhh + '시';
                        this.info.everymm = this.info.everymm + '분';
                    }
                    
                    setTimeout(function () {
                        this.issearch = true;
                    }.bind(this), 200);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 정기발급 기간 datepicker callback
        pickerEvCpnuseChangeEvent: function(data) {
            this.info.evrcpnusestdate = data.fromYYYYMMDD;
            this.info.evrcpnusesthour = data.fromHH;
            this.info.evrcpnusestmin = data.fromMM;
            this.info.evrcpnusestday = data.fromDate12;

            this.info.evrcpnuseeddate = data.toYYYYMMDD;
            this.info.evrcpnuseedhour = data.toHH;
            this.info.evrcpnuseedmin = data.toMM;
            this.info.evrcpnuseedday = data.toDate12;
        },
        // 사용기간 datepicker callback
        pickerCpnuseChangeEvent: function(data) {
            this.info.cpnusestdate = data.fromYYYYMMDD;
            this.info.cpnusesthour = data.fromHH;
            this.info.cpnusestmin = data.fromMM;
            this.info.cpnusestday = data.fromDate12;

            this.info.cpnuseeddate = data.toYYYYMMDD;
            this.info.cpnuseedhour = data.toHH;
            this.info.cpnuseedmin = data.toMM;
            this.info.cpnuseedday = data.toDate12;
        },
        // 발급일시 datepicker callback
        pickerCpnissueChangeEvent: function(data) {
            this.info.cpnissuedate = data.fromYYYYMMDD;
            this.info.cpnissuehour = data.fromHH;
            this.info.cpnissuemin = data.fromMM;
            this.info.cpnissueday = data.fromDate12;
        },
        // 정기발급시분
        changeEveryHHMM: function() {
            let hh = this.info.everyhh.substring(0, 2);
            let mm = this.info.everymm.substring(0, 2);
            let hhmm = hh.concat(mm);
            this.info.everyhhmm = hhmm;
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
        // 장바구니쿠폰중복사용범위 전체적용 체크
        checkAllCpnduptype: function() {
            let isAllCheck = this.info.isallcpndup;
            this.info.mucpnduptypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.mucpnduptype){
                    this.info.mucpnduptypeArr.push(type.cmcode);
                }
            }
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
        // 특정회원 추가 팝업
        openAddUserPopup: function() {
            this.$eventBus.$emit('modalShow', CommonAddUserPopup, null,
                (result) => {
                    // 팝업에서 가져온 결과 회원목록에 적용(이미 추가되어 있는 회원 제외)
                    let resultList = result.list;
                    for (let i=0; i<resultList.length; i++) {
                        resultList[i].ischecked = false;
                        let existCnt = this.info.issueTargetMemberList.filter(member => {
                            return member.userno == resultList[i].userno;
                        }).length;
                        if (existCnt == 0) {
                            this.info.issueTargetMemberList.push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 특정회원 삭제
        removeUser: function() {
            let targetList = this.info.issueTargetMemberList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (targetList.length == 0) {
                alert('삭제할 특정회원을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            targetList.forEach(item => {
                let findIndex = this.info.issueTargetMemberList.indexOf(item);
                this.info.issueTargetMemberList.splice(findIndex, 1);
            });
            // 삭제후 목록이 없는경우 전체체크 해제
            if (this.info.issueTargetMemberList.length == 0) {
                this.info.isallchkmem = 'F';
            }
        },
        // 특정회원목록 전체체크
        checkAllMemberList: function(value) {
            this.info.issueTargetMemberList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 특정회원목록 개별체크
        checkMemberList: function() {
            let checkedList = this.info.issueTargetMemberList.filter(item => {
                return item.ischecked == true;
            });

            if (this.info.issueTargetMemberList.length > checkedList.length){
                this.info.isallchkmem = 'F';
            } else {
                this.info.isallchkmem = 'T';
            }
        },
        // 첨부파일(탐색기 열기)
        fileAttach: function(fileTypeKey) {
            this.$refs[fileTypeKey].click();
        },
        // 엑셀파일 읽기
        readExcelFile: function(fileTypeKey, event) {
            let file = null;
            let headerInfo = [];
            // 특정회원
            if (fileTypeKey.indexOf('userExcelFile') > -1) {
                file = event.target.files[0];
                headerInfo = ['userid'];
            }
            let reader = new FileReader();
            let tmpResult = {};
            reader.onload = () => {
                let data = reader.result;
                let workbook = XLSX.read(data, {type: 'array'});
                workbook.SheetNames.forEach(sheetName => {
                    const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], {header: headerInfo, defval: ''});
                    if (roa.length) tmpResult = roa ;
                });
                this.readExcelData(fileTypeKey, tmpResult);
            };
            reader.readAsArrayBuffer(file);
        },
        // 엑셀일괄 업로드
        readExcelData(fileTypeKey, excelData) {
            this.$util.debug(excelData);
            if (excelData.length == 0) {
                alert("엑셀파일에 데이터가 존재하지 않습니다.");
                return;
            }
            
            // 특정회원
            if (fileTypeKey.indexOf('userExcelFile') > -1) {
                let useridlist = excelData.splice(1, excelData.length); // 헤더여부에 따라 달라짐
                this.$http.post('/admin/goods/regist/userinfo', { useridlist: useridlist })
                    .then(result => {
                        if (result.statusCode == '200') {
                            let resultList = result.data.list;
                            if ( resultList.length == 0) {
                                alert("회원정보가 존재하지않습니다. 입력한 사용자ID를 확인해주세요.");
                            } else {                                
                                // 회원목록에 적용(이미 추가되어 있는 회원 제외)
                                let resultCnt = 0;
                                for (let i=0; i<resultList.length; i++) {
                                    let existCnt = this.info.issueTargetMemberList.filter(member => {
                                        return member.userno == resultList[i].userno;
                                    }).length;
                                    if (existCnt == 0) {
                                        this.info.issueTargetMemberList.push(resultList[i]);
                                        resultCnt++;
                                    }
                                }
                                alert(resultCnt + '건 추가되었습니다.');
                            }
                        }
                        this.$refs.userExcelFile.value='';
                    })
                    .catch(error => {
                        this.$util.debug(error);
                        this.$refs.userExcelFile.value='';
                    });
            }
        },
        // 정렬
        sortToggle(key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData[sortKey] = sortName;
            this.info.psort = sortName;

            this.info.issueTargetMemberList.sort((a, b) => {
                a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                
                if (a[sortKey] < b[sortKey]) {
                    return sortOrder == 'asc'? -1: 1;
                } else if (a[sortKey] > b[sortKey]) {
                    return sortOrder == 'asc'? 1: -1;
                }
                return 0;
            });
        },
        // 카테고리분류 목록 조회
        // 구매확정인 경우 type: ord, 아닌경우 null
        getCategoryCodeList: function(obj, type) {
            if (obj.depth < 4) {
                // 하위 카테고리 목록 초기화
                for (let i=parseInt(obj.depth); i<4; i++) {
                    let listName = 'depth'+ (i+1) +'list';
                    let categoryName = 'depth'+ (i+1) +'Category';
                    if (!this.$util.isNull(type) && type == 'ord') {
                        listName = 'depth'+ (i+1) +'ordlist';
                        categoryName = 'depth'+ (i+1) +'OrdCategory';
                    }
                    this.categoryObj[listName] = [];
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
                        if (this.$util.isNull(type) && obj.depth == 0) {
                            this.categoryObj.depth1list = categoryList;
                            this.categoryObj.depth1ordlist = categoryList;
                        } else {
                            if (obj.depth > 0 && !this.$util.isNull(obj.idx)) {
                                let targetDepth = parseInt(obj.depth) +1;
                                let targetListName = 'depth'+ targetDepth +'list';
                                if (!this.$util.isNull(type) && type == 'ord') {
                                    targetListName = 'depth'+ targetDepth +'ordlist';
                                }
                                this.categoryObj[targetListName] = categoryList;
                                this.$forceUpdate();
                            }
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            } else {
                this.info.depth4Category.idx = obj.idx;
            }
        },
        // 카테고리분류 정보 셋팅
        // 구매확정인 경우 type: ord, 아닌경우 null
        setCategoryCodeInfo: function(index, idx, type) {
            let targetObjName = '';     // 선택한 값 세팅할 카테고리
            let targetListName = '';    // 선택한 카테고리명 가져오기위한 카테고리목록명
            
            if (!this.$util.isNull(type) && type == 'ord') {
                targetObjName = 'depth' + index + 'OrdCategory';
                targetListName = 'depth' + index + 'ordlist';
            } else {
                targetObjName = 'depth' + index + 'Category';
                targetListName = 'depth' + index + 'list';
            }
            this.info[targetObjName].idx = idx;
            this.categoryObj[targetListName].forEach(item => {
                if (item.idx == idx) {
                    this.info[targetObjName].value = item.value;
                }
            });
            
            let params = {depth: index, idx: idx};
            this.getCategoryCodeList(params, type);
        },
        // 쿠폰 카테고리 추가
        // 구매확정인 경우 type: ord, 아닌경우 null
        addCpnCategory: function(isadd, type) {
            let listName = 'cpncateList';
            if (!this.$util.isNull(type) && type == 'ord') {
                listName = 'cpnordcateList';
            }
            // 카테고리 선택 필수 체크
            if ((listName == 'cpncateList' && this.$util.isNull(this.info.depth1Category.idx))
                || (listName == 'cpnordcateList' && this.$util.isNull(this.info.depth1OrdCategory.idx))) {
                alert('카테고리를 선택해주세요.');
                return;
            }
            
            let cateidx = '';
            let fullCategoryName = '';
            for (let i=0; i<4; i++) {
                let listName = 'depth' + (i+1) + 'list';
                let categoryName = 'depth' + (i+1) + 'Category';
                if (!this.$util.isNull(type) && type == 'ord') {
                    listName = 'depth' + (i+1) + 'ordlist';
                    categoryName = 'depth' + (i+1) + 'OrdCategory';
                }
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

            for (let i=0; i<this.info[listName].length; i++) {
                let categoryObj = this.info[listName][i];
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
            this.info[listName].push(params);

            // 카테고리 초기화
            if (!this.$util.isNull(type) && type == 'ord') {
                this.info.depth1OrdCategory.idx = '';
                this.info.depth2OrdCategory.idx = '';
                this.info.depth3OrdCategory.idx = '';
                this.info.depth4OrdCategory.idx = '';
                this.info.depth1OrdCategory.value = '';
                this.info.depth2OrdCategory.value = '';
                this.info.depth3OrdCategory.value = '';
                this.info.depth4OrdCategory.value = '';
                this.categoryObj.depth2ordlist = [];
                this.categoryObj.depth3ordlist = [];
                this.categoryObj.depth4ordlist = [];
            } else {
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
            }
        },
        // 쿠폰 카테고리 삭제
        removeCategory: function(obj, listName) {
            let findIndex = this.info[listName].indexOf(obj);
            this.info[listName].splice(findIndex, 1);
        },
        // 쿠폰 상품 추가
        addGoods: function(listName) {
            if ((listName == 'cpngoodsList' && this.$util.isNull(this.info.goodsrangetype))
                || (listName == 'cpnordgoodsList' && this.$util.isNull(this.info.ordgdrangetype))) {
                alert("대상상품범위를 선택해주세요.");
                return;
            }
            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, {isshowoption: 'T'},
                (result) => {
                    // 팝업에서 가져온 결과 추가상품 목록에 적용(이미 추가되어 있는 상품 제외)
                    let resultList = result.list;
                    
                    let isadd = this.info.goodsrangetype==this.$store.getters['ADMIN'].GOODS_RANGE_INCLUDE? 'T' : 'F';
                    for (let i=0; i<resultList.length; i++) {
                        let existCnt = this.info[listName].filter(item => {
                            return item.goodsno+'_'+item.optioncode == resultList[i].goodsno+'_'+resultList[i].optioncode;
                        }).length;
                        if (existCnt == 0) {
                            resultList[i].isadd = isadd;
                            resultList[i].ischecked = false;
                            this.info[listName].push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 쿠폰 상품 삭제
        removeGoods: function(listName) {
            let delList = this.info[listName].filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (delList.length == 0) {
                alert('삭제할 상품을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            delList.forEach(item => {
                let findIndex = this.info[listName].indexOf(item);
                this.info[listName].splice(findIndex, 1);
            });
            if (this.info[listName].length == 0) {
                if (listName == 'cpngoodsList') {
                    this.info.isallchkcpngoods = false;
                } else {
                    this.info.isallchkcpnordgoods = false;
                }
            }
        },
        // 상품목록 전체체크
        checkAllGoodsList: function(value, listName) {
            this.info[listName].forEach(item => {
                item.ischecked = value;
            });
        },
        // 상품목록 개별체크
        checkGoodsList: function(listName) {
            let checkedList = this.info[listName].filter(item => {
                return item.ischecked == true;
            });
            if (this.info[listName].length > checkedList.length){
                if (listName == 'cpngoodsList') {
                    this.info.isallchkcpngoods = false;
                } else if (listName == 'cpnordgoodsList') {
                    this.info.isallchkcpnordgoods = false;
                }
            } else {
                if (listName == 'cpngoodsList') {
                    this.info.isallchkcpngoods = true;
                } else if (listName == 'cpnordgoodsList') {
                    this.info.isallchkcpnordgoods = true;
                }
            }
        },
        // 입력 validation 체크
        checkValidation: function() {
            let checkResult = true;
            // 기본정보 필수체크
            let valid = [
                {field: 'info.istrash', type: 'S', name:'[기본정보] 사용여부', required: true},
                {field: 'info.comcpntype', type: 'S', name:'[기본정보] 쿠폰종류', required: true},
                {field: 'info.cpnissuetype', type: 'S', name:'[기본정보] 발급종류', required: true},
                {field: 'info.cpnname', type: 'I', name:'[기본정보] 쿠폰명', required: true}
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                checkResult = false;
                alert(msg);
            }
            // 쿠폰명 공백체크
            if(checkResult && this.$util.isNull(this.info.cpnname.trim())) {
                checkResult = false;
                alert('[기본정보] 쿠폰명을 입력해주세요.');
            }

            // 대상범위
            if (checkResult && this.$util.isNull(this.info.ismemtype)) {
                checkResult = false;
                alert('[발급대상회원] 대상범위를 선택하세요.');
            }
            // 대상회원유형 또는 대상회원등급
            if (checkResult && this.info.ismemtype == 'T') {
                if (this.info.mumembertypeArr.length == 0 || this.info.mumemlvtypeArr.length == 0 ) {
                    checkResult = false;
                    alert("[발급대상회원] 대상회원유형 또는 대상회원등급을 선택해주세요.");
                }
            }
            // 특정회원유형
            if (checkResult && this.info.ismemtype == 'F') {
                if (this.info.issueTargetMemberList.length == 0) {
                    checkResult = false;
                    alert("[발급대상회원] 특정회원을 추가해주세요.");
                }
            }
            // 수신동의상태제한
            if (checkResult && this.$util.isNull(this.info.ismemtype)) {
                checkResult = false;
                alert('[발급대상회원] 수신동의상태제한을 선택하세요.');
            }
            // 다중수신제한
            if (checkResult && this.info.isrcvlimit == 'T') {
                if (this.info.mumemrcvtypeArr.length == 0) {
                    checkResult = false;
                    alert("[발급대상회원] 수신동의상태를 제한할 항목을 선택해주세요.");
                }
            }

            // 대상카테고리범위
            if (checkResult && this.$util.isNull(this.info.istotcate)) {
                checkResult = false;
                alert('[쿠폰적용대상상품] 대상카테고리범위를 선택하세요.');
            }
            // 특정카테고리목록
            if (checkResult && this.info.istotcate == 'F' && this.info.cpncateList.length == 0) {
                checkResult = false;
                alert('[쿠폰적용대상상품] 특정카테고리를 추가해주세요.');
            }
            // 대상상품범위(카테고리대상범위-전체)
            if (checkResult && this.info.istotcate=='T' && this.$util.isNull(this.info.goodsrangetype)) {
                checkResult = false;
                alert('[쿠폰적용대상상품] 대상상품범위를 선택하세요.');
            }
            // 특정카테고리목록(카테고리대상범위-전체 ,상품대상범위-추가/제외)
            if (checkResult && this.info.istotcate=='T' && this.info.goodsrangetype != this.$store.getters['ADMIN'].GOODS_RANGE_ALL && this.info.cpngoodsList.length == 0) {
                checkResult = false;
                alert('[쿠폰적용대상상품] 대상상품을 추가해주세요.');
            }
            // 파트너사분담비율(쿠폰종류-상품쿠폰)
            if(checkResult && this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_GOODS && this.$util.isNull(this.info.partratio)) {
                checkResult = false;
                alert('[쿠폰적용대상상품] 파트너사분담비율을 입력해주세요.');
            }

            // 사용기간종류
            if (checkResult && this.$util.isNull(this.info.cpnusetype)) {
                checkResult = false;
                alert('[사용조건] 사용기간종류를 선택하세요.');
            }
            // 기간설정
            if (checkResult && this.$util.isNull(this.info.cpnusetype)) {
                checkResult = false;
                alert('[사용조건] 사용기간종류를 선택하세요.');
            }
            // 정기발급사용시작일시(사용기간종류-기간설정)
            if (this.info.cpnissuetype == this.$store.getters['ADMIN'].CPN_ISSUE_REGULAR) {
                if (checkResult && (this.$util.isNull(this.info.evrcpnusestdate) || this.$util.isNull(this.info.evrcpnusesthour) || this.$util.isNull(this.info.evrcpnusestmin))) {
                    checkResult = false;
                    alert('[사용조건] 정기발급기간 시작일시를 입력하세요.');
                }
                // 사용종료일시(사용기간종류-기간설정)
                if (checkResult && (this.$util.isNull(this.info.evrcpnuseeddate) || this.$util.isNull(this.info.evrcpnuseedhour) || this.$util.isNull(this.info.evrcpnuseedmin))) {
                    checkResult = false;
                    alert('[사용조건] 정기발급기간 종료일시를 입력하세요.');
                }
                // 사용종료일시가 사용시작일시보다 빠른 경우
                if(checkResult && (this.info.evrcpnuseedday <= this.info.evrcpnusestday)) {
                    checkResult = false;
                    alert('[사용조건] 정기발급기간 종료일시를 시작일시 이후로 입력하세요.');
                }
             }
            // 사용시작일시(사용기간종류-기간설정)
            else {
                if (checkResult && this.info.cpnusetype == this.$store.getters['ADMIN'].CPN_USE_PERIOD
                    && (this.$util.isNull(this.info.cpnusestdate) || this.$util.isNull(this.info.cpnusesthour) || this.$util.isNull(this.info.cpnusestmin))) {
                    checkResult = false;
                    alert('[사용조건] 사용시작일시를 입력하세요.');
                }
                // 사용종료일시(사용기간종류-기간설정)
                if (checkResult && this.info.cpnusetype == this.$store.getters['ADMIN'].CPN_USE_PERIOD
                    && (this.$util.isNull(this.info.cpnuseeddate) || this.$util.isNull(this.info.cpnuseedhour) || this.$util.isNull(this.info.cpnuseedmin))) {
                    checkResult = false;
                    alert('[사용조건] 사용종료일시를 입력하세요.');
                }
                // 사용종료일시가 사용시작일시보다 빠른 경우
                if(checkResult && (this.info.cpnuseedday <= this.info.cpnusestday)) {
                    checkResult = false;
                    alert('[사용조건] 사용종료일시를 사용시작일시 이후로 입력하세요.');
                }
            }
            // 발급일기준쿠폰사용일(사용기간종류-발급일기준)
            if (checkResult && this.info.cpnusetype == this.$store.getters['ADMIN'].CPN_USE_DAY_CNT && this.$util.isNull(this.info.issuedaycnt)) {
                checkResult = false;
                alert('[사용조건] 발급일기준 쿠폰사용일을 입력하세요.');
            }
            // 사용채널
            if (checkResult && this.$util.isNull(this.info.muappchtype)) {
                checkResult = false;
                alert('[사용조건] 사용채널을 선택하세요.');
            }
            // 사용가능금액적용여부(쿠폰종류-상품쿠폰/장바구니쿠폰)
            if (checkResult && this.$util.isNull(this.info.isorderlimit)
                && (this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_GOODS || this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_CART)) {
                checkResult = false;
                alert('[사용조건] 사용가능금액적용여부를 선택하세요.');
            }
            // 사용가능기준금액(쿠폰종류-상품쿠폰/장바구니쿠폰, 사용가능금액적용)
            if (checkResult && this.info.isorderlimit == 'T' && this.$util.isNull(this.info.orlimitamt)
                && (this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_GOODS || this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_CART)) {
                checkResult = false;
                alert('[사용조건] 사용가능기준금액를 입력하세요.');
            }
            // 사용가능기준금액(쿠폰종류-배송비쿠폰)
            if (checkResult && this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_DELIV && this.$util.isNull(this.info.orlimitamt)) {
                checkResult = false;
                alert('[사용조건] 사용가능기준금액를 입력하세요.');
            }
            // 할인내용 체크(쿠폰종류-상품쿠폰/장바구니쿠폰)
            if (this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_GOODS || this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_CART) {
                // 할인율구분
                if (checkResult && this.$util.isNull(this.info.ispercent)) {
                    checkResult = false;
                    alert('[사용조건] 할인율 구분을 선택해주세요.');
                }
                // 할인액
                if (checkResult && this.info.ispercent == 'F' && this.$util.isNull(this.info.disprice)) {
                    checkResult = false;
                    alert('[사용조건] 할인액을 입력해주세요.');
                }
                // 할인율
                if (checkResult && this.info.ispercent == 'T' && this.$util.isNull(this.info.dispercent)) {
                    checkResult = false;
                    alert('[사용조건] 할인율을 입력해주세요.');
                }
                // 최대할인금액
                if (checkResult && this.info.ispercent == 'T' && this.$util.isNull(this.info.maxdisamt)) {
                    checkResult = false;
                    alert('[사용조건] 최대할인금액을 입력해주세요.');
                }
            }
            // 할인내용 체크(쿠폰종류-배송비쿠폰)
            if (checkResult && this.info.comcpntype == this.$store.getters['ADMIN'].COM_CPN_DELIV && this.$util.isNull(this.info.maxdisamt)) {
                checkResult = false;
                alert('[사용조건] 최대할인금액을 입력해주세요.');
            }

            // 구매확정구분(쿠폰발급종류-구매확정보상)
            if (checkResult && this.info.cpnissuetype == this.$store.getters['ADMIN'].CPN_ISSUE_PURCH_CONFIRM && this.$util.isNull(this.info.isfirstord)) {
                checkResult = false;
                alert('[구매확정조건] 구매확정구분을 선택하세요.');
            }
            // 구매확정 대상카테고리, 상품(쿠폰발급종류-구매확정보상, 구매확정구분-재구매확정)
            if (this.info.cpnissuetype == this.$store.getters['ADMIN'].CPN_ISSUE_PURCH_CONFIRM && this.info.isfirstord == 'F') {
                // 대상카테고리범위
                if (checkResult && this.$util.isNull(this.info.isordtotcate)) {
                    checkResult = false;
                    alert('[구매확정조건] 대상카테고리범위를 선택하세요.');
                }
                // 특정카테고리목록
                if (checkResult && this.info.isordtotcate == 'F' && this.info.cpnordcateList.length == 0) {
                    checkResult = false;
                    alert('[구매확정조건] 특정카테고리를 추가해주세요.');
                }
                // 대상상품범위(카테고리대상범위-전체)
                if (checkResult && this.info.isordtotcate=='T' && this.$util.isNull(this.info.ordgdrangetype)) {
                    checkResult = false;
                    alert('[구매확정조건] 대상상품범위를 선택하세요.');
                }
                // 특정카테고리목록(카테고리대상범위-전체 ,상품대상범위-추가/제외)
                if (checkResult && this.info.isordtotcate=='T' && this.info.ordgdrangetype != this.$store.getters['ADMIN'].GOODS_RANGE_ALL && this.info.cpnordgoodsList.length == 0) {
                    checkResult = false;
                    alert('[구매확정조건] 대상상품을 추가해주세요.');
                }
            }
            
            // 발급시점(쿠폰발급종류-즉시할인, 정기발급-매일발급)
            if(this.info.cpnissuetype == this.$store.getters['ADMIN'].CPN_ISSUE_NOW_DISCOUNT || (this.info.cpnissuetype == this.$store.getters['ADMIN'].CPN_ISSUE_REGULAR && this.info.iseveryday == 'T')) {
                if(checkResult && this.$util.isNull(this.info.isnowissue)) {
                    checkResult = false;
                    alert('[발급조건] 발급시점을 선택해주세요.');
                }
                if(checkResult && this.info.isnowissue=='F' && (this.$util.isNull(this.info.cpnissuedate) || this.$util.isNull(this.info.cpnissuehour) || this.$util.isNull(this.info.cpnissuemin))) {
                    checkResult = false;
                    alert('[발급조건] 발급일시를 입력해주세요.');
                }
            }
            // 발급시점(쿠폰발급종류-생일)
            if(this.info.cpnissuetype == this.$store.getters['ADMIN'].CPN_ISSUE_BIRTHDAY) {
                if(checkResult && this.$util.isNull(this.info.isbirththeday)) {
                    checkResult = false;
                    alert('[발급조건] 발급시점을 선택해주세요.');
                }
                if(checkResult && this.info.isbirththeday=='F' && this.$util.isNull(this.info.birthbfdaycnt)) {
                    checkResult = false;
                    alert('[발급조건] 생일전 발급일수를 입력해주세요.');
                }
            }
            // 재발급주기(쿠폰발급종류-정기발급)
            if(this.info.cpnissuetype == this.$store.getters['ADMIN'].CPN_ISSUE_REGULAR) {
                if(checkResult && this.$util.isNull(this.info.iseveryday)) {
                    checkResult = false;
                    alert('[발급조건] 재발급주기를 선택해주세요.');
                }
                if(checkResult && this.info.iseveryday=='F' && this.$util.isNull(this.info.everymonthloop)) {
                    checkResult = false;
                    alert('[발급조건] 재발급주기일수를 입력해주세요.');
                }
                if(checkResult && this.info.iseveryday=='T' && this.$util.isNull(this.info.everydayloop)) {
                    checkResult = false;
                    alert('[발급조건] 재발급주기일수를 입력해주세요.');
                }
                if(checkResult && (this.$util.isNull(this.info.everyhh) || this.$util.isNull(this.info.everymm))) {
                    checkResult = false;
                    alert('[발급조건] 재발급시분을 입력해주세요.');
                }
            }
            // 발급방법
            if(checkResult && this.$util.isNull(this.info.isautopay)) {
                checkResult = false;
                alert('[발급조건] 발급방법을 선택해주세요.');
            }
            // 쿠폰수량제한, 동일인재발급(발급방법-다운로드)
            if(this.info.isautopay=='F') {
                if (checkResult && this.$util.isNull(this.info.iscntlimt)) {
                    checkResult = false;
                    alert('[발급조건] 쿠폰수량제한구분을 선택해주세요.');
                }
                if (checkResult && this.info.iscntlimt=='T' && this.$util.isNull(this.info.cpnlimtcnt)) {
                    checkResult = false;
                    alert('[발급조건] 쿠폰제한수량을 입력해주세요.');
                }
                if (checkResult && this.$util.isNull(this.info.isdupperson)) {
                    checkResult = false;
                    alert('[발급조건] 동일인재발급구분을 선택해주세요.');
                }
                if (checkResult && this.info.isdupperson=='T' && ( this.$util.isNull(this.info.dupcnt) || this.info.dupcnt < 1) ) {
                    checkResult = false;
                    alert('[발급조건] 동일인 재발급수량을 넣어주세요.');
                }
            }

            return checkResult;
        },
        // 저장
        save: function() {
            if (this.checkValidation()) {
                if(confirm('저장하시겠습니까?')) {
                    this.$http.post('/admin/promotion/coupon/save', this.info)
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
        // 엑셀양식다운로드
        downloadExcelTemplate: function(filename) {
            let params = { filename: filename }   // 서버에 저장되어있는 파일명
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/common/excel/download', params, config);
        },
        // 팝업 닫기
        onClose: function() {
            this.$emit('closePopup');
        }
    },
    watch: {
        // 쿠폰사용기간
        'info.cpnuseperiod': function (value) {
            if (this.$util.isNull(value)) return;
            
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]);

            if (type == 'aday') {
                this.info.cpnusestdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.info.cpnuseeddate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.info.cpnusestdate = this.$util.getDate('-');
                this.info.cpnuseeddate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'months') {
                this.info.cpnusestdate = this.$util.getDate('-');
                this.info.cpnuseeddate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
            }
            this.info.cpnusesthour = '00';
            this.info.cpnusestmin = '00';
            this.info.cpnuseedhour = '23';
            this.info.cpnuseedmin = '59';
        },
        // 정기발급쿠폰 발급기간
        'info.evrcpnuseperiod': function (value) {
            if (this.$util.isNull(value)) return;
            
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]);

            if (type == 'aday') {
                this.info.evrcpnusestdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.info.evrcpnuseeddate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.info.evrcpnusestdate = this.$util.getDate('-');
                this.info.evrcpnuseeddate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'months') {
                this.info.evrcpnusestdate = this.$util.getDate('-');
                this.info.evrcpnuseeddate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
            } else if (type == 'years') {
                this.info.evrcpnusestdate = this.$util.getDate('-');
                this.info.evrcpnuseeddate = this.$util.getAddYear(this.$util.getDate(), addValue, '-');
            }
            this.info.evrcpnusesthour = '00';
            this.info.evrcpnusestmin = '00';
            this.info.evrcpnuseedhour = '23';
            this.info.evrcpnuseedmin = '59';
        },
        // 쿠폰종류
        'info.comcpntype': function(value) {
            if (this.issearch) {
                this.info.cpnissuetype = '';
                this.info.issuedaycnt = '';
                this.info.isallmuappch = 'T';
                this.checkAllAppchtype();
                if (value == this.$store.getters['ADMIN'].COM_CPN_DELIV) {
                    this.info.isorderlimit = 'T';
                } else {
                    this.info.isorderlimit = '';
                }
                this.info.orlimitamt = '';
                if (value == this.$store.getters['ADMIN'].COM_CPN_GOODS) {
                    this.info.partratio = 0;
                    this.info.dadaratio = 100;
                } else {
                    this.info.partratio = '';
                    this.info.dadaratio = '';
                }
                if (value == this.$store.getters['ADMIN'].COM_CPN_CART) {
                    this.info.isallcpndup = 'T';
                } else {
                    this.info.isallcpndup = '';
                }
                this.checkAllCpnduptype();
            }
        },
        // 발급종류
        'info.cpnissuetype': function(value) {
            if (this.issearch) {
                if (value == this.$store.getters['ADMIN'].CPN_ISSUE_REGULAR) {
                    this.info.cpnusetype = this.$store.getters['ADMIN'].CPN_USE_DAY_CNT; // 사용기간종류(default: 발급일기준)
                    this.info.evrcpnuseperiod = 'months_3';  // 쿠폰사용기간(default: 3개월)
                    this.info.cpnuseperiod = '';
                    this.info.cpnusestdate = '';
                    this.info.cpnusesthour = '';
                    this.info.cpnusestmin = '';
                    this.info.cpnusestday = '';
                    this.info.cpnuseeddate = '';
                    this.info.cpnuseedhour = '';
                    this.info.cpnuseedmin = '';
                    this.info.cpnuseedday = '';
                } else {
                    this.info.cpnusetype = this.$store.getters['ADMIN'].CPN_USE_PERIOD; // 사용기간종류(default: 기간설정)
                    this.info.cpnuseperiod = 'days_3';  // 쿠폰사용기간(default: 3일)
                    this.info.evrcpnuseperiod = '';
                    this.info.evrcpnusestdate = '';
                    this.info.evrcpnusesthour = '';
                    this.info.evrcpnusestmin = '';
                    this.info.evrcpnusestday = '';
                    this.info.evrcpnuseeddate = '';
                    this.info.evrcpnuseedhour = '';
                    this.info.evrcpnuseedmin = '';
                    this.info.evrcpnuseedday = '';
                }
                this.info.ispercent = '';
                this.info.disprice = '';
                this.info.dispercent = '';
                this.info.maxdisamt = '';
                this.info.isrvmargin = 'F';

                if (value == this.$store.getters['ADMIN'].CPN_ISSUE_NOW_DISCOUNT) {
                    this.info.isnowissue = 'T';
                } else {
                    this.info.isnowissue = '';
                }
                this.info.isautopay = '';
                this.info.iscntlimt = '';
                this.info.isdupperson = '';
                if (value == this.$store.getters['ADMIN'].CPN_ISSUE_BIRTHDAY) {
                    this.info.isbirththeday = 'T';
                    this.info.birthbfdaycnt = '3';
                } else {
                    this.info.isbirththeday = '';
                    this.info.birthbfdaycnt = '';
                }
                this.info.iseveryday = '';
                this.info.everydayloop = '';
                this.info.everymonthloop = '';
                this.info.everyhhmm = '';
                this.info.isfirstord = '';
            }
        },
        // 발급대상회원 범위
        'info.ismemtype': function(value) {
            if (this.issearch) {
                if (value === 'T') {
                    this.info.issueTargetMemberList = [];
                } else if (value === 'F') {
                    this.info.isallmember = 'T';
                    this.info.isallmemlv = 'T';
                    this.checkAllMemlvtype();
                    this.checkAllMembertype();
                } else {
                    this.info.issueTargetMemberList = [];
                    this.info.isallmember = 'T';
                    this.info.isallmemlv = 'T';
                    this.checkAllMemlvtype();
                    this.checkAllMembertype();
                }
            }
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
        // 적용채널
        'info.muappchtypeArr': function(value) {
            if (value.length < this.commonCode.muappchtype.length) {
                this.info.isallmuappch = 'F';
            } else {
                this.info.isallmuappch = 'T';
            }
            this.info.muappchtype = this.info.muappchtypeArr.join();
        },
        // 장바구니쿠폰중복사용범위
        'info.mucpnduptypeArr': function(value) {
            if (value.length < this.commonCode.mucpnduptype.length) {
                this.info.isallcpndup = 'F';
            } else {
                this.info.isallcpndup = 'T';
            }
            this.info.mucpnduptype = this.info.mucpnduptypeArr.join();
        },
        // 수신동의 제한여부
        'info.isrcvlimit': function() {
            if (this.issearch) {
                this.info.mumemrcvtypeArr = [];
            }
        },
        // 다중수신동의
        'info.mumemrcvtypeArr': function() {
            this.info.mumemrcvtype = this.info.mumemrcvtypeArr.join();
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
                this.info.cpncateList = [];
                if (value === 'F' && this.info.goodsrangetype === this.$store.getters['ADMIN'].GOODS_RANGE_ALL) {
                    this.info.goodsrangetype = '';
                    this.info.cpngoodsList = [];
                }
            }
        },
        // 대상상품범위
        'info.goodsrangetype': function(value) {
            if(this.issearch && value == this.$store.getters['ADMIN'].GOODS_RANGE_ALL) {
                this.info.cpngoodsList = [];
            }
        },
        // 재발급주기
        'info.iseveryday': function(value) {
            if (this.issearch) {
                if (value==='T') {
                    this.info.isnowissue = 'T';
                    this.info.everymonthloop = '';
                } else if (value==='F') {
                    this.info.isnowissue = 'F';
                    this.info.everydayloop = '';
                }
            }
        },
        // 발급시점-즉시지급여부
        'info.isnowissue': function() {
            if (this.issearch) {
                this.info.cpnissuedate = '';
                this.info.cpnissuehour = '';
                this.info.cpnissuemin = '';
                this.info.cpnissueday = '';
            }
        },
        // 발급방법
        'info.isautopay': function(value) {
            if (this.issearch) {
                if (value == 'F') { //다운로드
                    this.info.iscntlimt = 'F';
                    this.info.isdupperson = 'T';

                } else {
                    this.info.iscntlimt = '';
                    this.info.isdupperson = '';
                }
            }

        },
        // 쿠폰수량제한여부
        'info.iscntlimt': function() {
            if (this.issearch) {
                this.info.cpnlimtcnt = '';
            }
        },
        // 동일인재발급여부
        'info.isdupperson': function() {
            if (this.issearch) {
                this.info.dupcnt = '1';
            }
        },
        // 구매확정조건첫구매확정여부
        'info.isfirstord': function(value) {
            if (this.issearch) {
                if (value == 'T') {
                    this.info.isordtotcate = '';
                    this.info.ordgdrangetype = '';
                } else {
                    this.info.isordtotcate = 'T';
                    this.info.ordgdrangetype = this.$store.getters['ADMIN'].GOODS_RANGE_ALL;
                }
            }
        },
        // 구매확정 전체카테고리여부
        'info.isordtotcate': function(value) {
            if (this.issearch) {
                this.info.depth1OrdCategory.idx = '';
                this.info.depth2OrdCategory.idx = '';
                this.info.depth3OrdCategory.idx = '';
                this.info.depth4OrdCategory.idx = '';
                this.info.depth1OrdCategory.value = '';
                this.info.depth2OrdCategory.value = '';
                this.info.depth3OrdCategory.value = '';
                this.info.depth4OrdCategory.value = '';
                this.categoryObj.depth2ordlist = [];
                this.categoryObj.depth3ordlist = [];
                this.categoryObj.depth4ordlist = [];
                this.info.cpnordcateList = [];
                if (value === 'F' && this.info.ordgdrangetype === this.$store.getters['ADMIN'].GOODS_RANGE_ALL) {
                    this.info.ordgdrangetype = '';
                    this.info.cpnordgoodsList = [];
                }
            }
        },
        // 구매확정 대상상품범위
        'info.ordgdrangetype': function(value) {
            if(this.issearch && value == this.$store.getters['ADMIN'].GOODS_RANGE_ALL) {
                this.info.cpnordgoodsList = [];
            }
        },
        // 파트너사 할인부담율 - 숫자 (소수 2자리까지)
        'info.partratio': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) {
                this.info.dadaratio = ''; 
                return this.info.partratio = '';
            } else {
                var pattern = /(^\d+$)|(^\d{1,5}.\d{0,2}$)/;
                if (!pattern.test(value)) {
                    value = value.substr(0, value.length-1);
                }
                let floatValue = Math.round(value*100);
                if(floatValue >= 10000) {
                    this.info.dadaratio = 0;
                } else {
                    this.info.dadaratio = (10000 - floatValue)/100;
                }
                return this.info.partratio = value;
            }
        },
        // 발급일기준사용일
        'info.issuedaycnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.issuedaycnt = value.replace(/(^0[\d]|[^\d])/gi, '');
        },
        // 할인율
        'info.dispercent': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            var pattern = /(^\d+$)|(^\d{1,5}.\d{0,2}$)/;
            if (!pattern.test(value)) {
                value = value.substr(0, value.length-1);
            }
            return this.info.dispercent = value;
        },
        // 할인액
        'info.disprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.disprice = value.replace(/(^0|[^0-9])/gi, '');
        },
        // 쿠폰제한수량
        'info.cpnlimtcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.cpnlimtcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        // 동일인재발급수량
        'info.dupcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.dupcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        // 생일쿠폰생일전발급일수
        'info.birthbfdaycnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.birthbfdaycnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        // 정기발급반복일수
        'info.everydayloop': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.everydayloop = value.replace(/(^0|[^0-9])/gi, '');
        },
        // 정기발급반복월일
        'info.everymonthloop': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.everymonthloop = value.replace(/(^0|[^0-9])/gi, '');
        }
    }
}