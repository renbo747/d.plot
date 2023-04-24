import XLSX from 'xlsx'
import vClickOutside from 'v-click-outside';
import CommonNavigator from '@views.admin/common/CommonNavigator.vue'
import CommonEditor from '@views.admin/common/CommonEditor.vue';
import CommonDatePickerFromTo from '@views.admin/common/CommonDatePickerFromTo.vue';
import CommonArraySort from "@views.admin/common/CommonArraySort.vue";
import AddConstGoodsListPopup from '@views.admin/goods/popup/AddConstGoodsListPopup.vue';
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';


export default {
    name: 'admin.goods.regist.dealRegist',
    components: {
        CommonNavigator,
        CommonEditor,
        CommonDatePickerFromTo,
        CommonArraySort,
        GoodsDetail
    },
    directives: {
        clickOutside: vClickOutside.directive
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
            this.isWrite = (result.data.iswrite === 'T');
            // 초기데이터 세팅
            this.onInit();

            if(!this.isWrite){
                let buttons = this.$el.getElementsByTagName('button');
                setTimeout(function () {
                    for(let button of buttons){
                        if(button.className !== 'btn-search') {
                            button.style.visibility = 'hidden';
                            button.disabled = true;
                        }
                    }
                }.bind(this), 200);
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    data() {
        return {
            isWrite: false,             // 쓰기권한여부
            user: {},
            // Anchor 탭
            tabObject: {
                tab1: {href: '#tab1', tabName: '기본정보', isActive: false, isOpen: true},
                tab2: {href: '#tab2', tabName: '구성상품', isActive: false, isOpen: true},
                tab3: {href: '#tab3', tabName: '이미지', isActive: false, isOpen: true},
                tab4: {href: '#tab4', tabName: '추가정보', isActive: false, isOpen: true}
            },
            commonCode: {
                goodsselltype: [],  // 상품판매상태
                muappchtype: [],    // 적용채널
                dadamembertype: []  // 다다픽회원유형
            },
            sortData: {
                chargemd: {
                    psort: 'mdcode_asc',
                    mdcode: 'mdcode_asc',
                    mdname: 'mdname_desc',
                    mdtel: 'mdtel_desc'
                },
                memo: {
                    psort: 'regdate_desc',
                    regdate: 'regdate_desc'
                }
            },
            // 내부 레이어컴포넌트 노출정보
            showInfo: {
                ismdshow: false,    //담당MD조회노출여부
                iscateshow: false,  //카테고리명조회 노출여부
                istempshow: false   //카테고리템플릿조회 노출여부
            },
            // 노출순위 데이터
            moveData: {
                moveValue: '',                  // 움직일 값
                targetIdx: [],                  // 대상 위치
                code: 'U',                      // 위, 아래 코드
                isSuccess: false                // 저장 성공 여부 (** 중요)
            },
            deletefilelist: [],
            excelFile: '',
            files: {
                pcrepreimgfile: null,
                morepreimgfile: null,
                addimgfilelist: []
            },
            imgPreview: {
                pcrepreimgfile: '',
                morepreimgfile: '',
                addimgfilelist: []
            },
            // 조회여부
            issearch: false,
            // default 데이터(딜상품에 입력하지 않음, N-N 컬럼)
            defaultInfo: {
                istaxfree: 'F',     //면세여부
                marketprice: 0,     //정상가
                price: 0,           //판매가
                minordcnt: 0,       //최소주문수량
                maxordcnt: 0,       //최대주문수량
                daymaxordcnt: 0,    //일최대주문수량
                isfrstsale: 'F',    //첫구매할일적용여부
                goodsdivtype: this.$store.getters['ADMIN'].GOODS_DIV_TYPE_GDT001, //상품구분상태(새상품)
                iscombdeliv: 'F'   //묶음배송여부
            },
            // 기본정보
            basicInfo: {
                goodsapprtype: '',      //상품승인상태
                orggoodsapprtype: '',   //기존상품승인상태
                isdeal: 'T',        //딜여부
                istempsave: '',     //임시저장여부
                categoryname: '',   //카테고리명
                categoryidx: '',    //카테고리일련번호
                depth1Category: {category:'대분류', idx: '', text:''},  //대분류 카테고리일련번호
                depth2Category: {category:'중분류', idx: '', text:''},  //중분류 카테고리일련번호
                depth3Category: {category:'소분류', idx: '', text:''},  //소분류 카테고리일련번호
                depth4Category: {category:'세분류', idx: '', text:''},  //세분류 카테고리일련번호
                categorynameList:[],        //카테고리명목록
                categoryList: {             //카테고리코드목록(대분류, 중분류, 소분류, 세분류)
                    depth1List: [],
                    depth2List: [],
                    depth3List: [],
                    depth4List: []
                },
                goodsCategoryList: [],      //상품별카테고리목록
                categoryTemplateList:[],    //카테고리템플릿목록
                dealno: '',         //딜상품번호
                goodsname: '',      //상품명
                keyword: '',        //검색키워드
                disstday: '',       //전시시작일
                disstdate: '',      //전시시작일자
                disedday: '',       //전시종료일
                diseddate: '',      //전시종료일자
                dissthour: '',      //전시시작시간
                disstmin: '',       //전시시작분
                disedhour: '',      //전시종료시간
                disedmin: '',       //전시종료분
                disperiod: '',      //전시기간
                isdisplay: 'T',     //전시여부
                goodsselltype: this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST001, //상품판매상태 (defualt: 판매대기)
                mdcode: '',         //MD코드
                mdname: '',         //MD명
                chargemdList: [],   //담당MD목록
                isallchannel:'T',   //전체채널여부
                muappchtype: '',    //다중적용채널
                muappchtypeArr: [], //다중적용채널Array
                iscncappr: ''       //취소승인필요여부
            },
            constInfo: {
                constGoodsList: [], //구성상품목록
                isallchkgoods: 'F'  //구성상품전체체크여부
            },
            // 이미지정보
            imageInfo: {
                ismaingoodsimage: '',
                sameimgidx: '',
                issamepccont: false,
                isusenotice: 'F',
                isuseintro: 'F',
                noticecontent: '',
                introcontent: '',
                pccontent: '',
                mobilecontent: ''
            },
            // 추가정보
            etcInfo: {
                isallmember: 'T',       //회원유형전체여부
                mumembertype: '',       //다중대상회원유형
                mumembertypeArr: [],    //다중대상회원유형Array
                isopenreview: 'T',      //리뷰공개여부
                isshowmemo: false,      //메모리스트 show여부
                inputmemo: '',          //입력메모
                goodsMemoList: []       //관리자메모 목록
            },
            // 에디터 스타일 지정
            styleObject: {
                height: '200px'
            },
            // 변경정보
            chgInfo: {
                chgCate: '',
                chgMemo: '',
                chgConstGoods: '',
                chgPcImg: '',
                chgMoImg: '',
                chgAddImg: '',
                fstTempSave: '',
                fstSave: ''
            },
            activeGoodsNo: null,
            isGoodsDetailShow: false   //상품상세팝업여부
        }
    },
    methods: {
        // 초기데이터 세팅
        onInit:function() {
            // 유저정보 세팅
            this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
            this.basicInfo.mdcode = this.user.mdcode;
            this.basicInfo.mdname = this.user.mdname;

            // 딜상품정보조회
            if (!this.$util.isNull(this.basicInfo.dealno)) {
                this.issearch = true;
                this.getGoodsInfo();
            }
            // 공통코드 조회
            this.getCommonCodeList();
            // 대분류 카테고리 목록 조회
            let params = { depth: 0, idx: 0, name: ''};
            this.getCategoryCodeList(params);
            // 전시기간 세팅 (defualt: 상시)
            this.basicInfo.disperiod = 'all_0';
        },
        // 공통코드 세팅
        getCommonCodeList: function() {
            let cmclassArr = ['MUAPPCHTYPE', 'GOODSSELLTYPE', 'DADAMEMBERTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                        
                        // 적용채널 초기값 전체선택
                        if (!this.issearch && key == 'muappchtype') {
                            this.checkAllChannel();
                        }
                        // 회원유형 별 노출 초기값 전체선택
                        if (!this.issearch && key == 'dadamembertype') {
                            this.checkAllMembertype();
                        }
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 딜상품정보 조회
        getGoodsInfo: function() {
            let param = { dealno: this.basicInfo.dealno };
            this.$http.post('/admin/goods/regist/dealgoods/detail', param)
                .then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == '200') {
                        let data = result.data;
                        // 상품별 목록조회
                        this.basicInfo.goodsCategoryList = data.goodscategorylist;
                        this.constInfo.constGoodsList = data.constgoodslist;
                        this.constInfo.constGoodsList.forEach(item=> {
                            if (item.ismaingoods == 'T') {
                                this.imageInfo.sameimgidx = item.fileidx;
                            }
                        })
                        this.etcInfo.goodsMemoList = data.goodsmemolist;
                        if (this.etcInfo.goodsMemoList.length > 0) {
                            this.etcInfo.isshowmemo = true;
                        }
                        this.files = data.files;
                        this.$refs.pceditor.content = data.goodscontent.pccontent;
                        this.$refs.mobileeditor.content = data.goodscontent.mobilecontent;
                        if (!this.$util.isNull(data.goodscontent.noticecontent)) {
                            this.imageInfo.isusenotice = 'T';
                            this.$refs.noticeeditor.content = data.goodscontent.noticecontent;
                        }
                        if (!this.$util.isNull(data.goodscontent.introcontent)) {
                            this.imageInfo.isuseintro = 'T';
                            this.$refs.introeditor.content = data.goodscontent.introcontent;
                        }
                        
                        // 상품정보 세팅
                        for(const [key] of Object.entries(data.basicinfo)) {
                            this.basicInfo[key] = data.basicinfo[key];
                            if (key == 'muappchtype') {
                                this.basicInfo.muappchtypeArr =  this.basicInfo[key].split(',');
                            }
                        }
                        this.basicInfo.orggoodsapprtype = this.basicInfo.goodsapprtype;
                        for(const [key] of Object.entries(data.etcinfo)) {
                            this.etcInfo[key] = data.etcinfo[key];
                            if (key == 'mumembertype') {
                                this.etcInfo.mumembertypeArr =  this.etcInfo[key].split(',');
                            }
                        }
                    } else {
                        alert(result.message);
                    }
                    setTimeout(function () {
                        this.issearch = false;
                    }.bind(this), 200);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 카테고리분류 목록 조회
        getCategoryCodeList: function(obj) {
            // 세분류가 아닌경우만 하위 카테고리 조회
            if (obj.depth < 4) {
                // 하위 카테고리 목록 초기화
                for (let i=obj.depth; i<=4; i++) {
                    let listName = 'depth'+ (i+1) +'List';
                    this.basicInfo.categoryList[listName] = [];
                }
                // 하위카테고리 목록 조회
                this.$http.post('/admin/goods/regist/cate/list', obj)
                    .then(result => {
                        this.$util.debug(result);
                        let categoryList = result.data.list;

                        // 카테고리 목록 세팅
                        let targetDepth = obj.depth +1;
                        let targetListName = 'depth'+ targetDepth +'List';
                        this.basicInfo.categoryList[targetListName] = categoryList;
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            } else {
                this.basicInfo.depth4CategoryIdx = obj.idx;
            }
        },
        // 카테고리분류 정보 셋팅
        setCategoryCodeInfo: function(obj) {
            let targetName = 'depth' + obj.depth + 'Category';
            this.basicInfo[targetName].idx = obj.idx;
            this.basicInfo[targetName].value = obj.value;
        },
        // 카테고리명 조회 show/hide
        onCategorynameShow: function() {
            if (!this.showInfo.iscateshow) {
                this.getCategorynameList();
            } else {
                this.basicInfo.categorynameList = [];
                this.showInfo.iscateshow = false;
            }
        },
        // 카테고리명 목록 조회
        getCategorynameList: function() {
            let param = {categoryname: this.basicInfo.categoryname};
            this.$http.post('/admin/goods/regist/catename/list', param)
                .then(result => {
                    this.$util.debug(result);
                    this.basicInfo.categorynameList = result.data.list;
                    this.showInfo.iscateshow = true;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 카테고리 정보 셋팅
        setCategoryInfo: function(obj) {
            this.closeCatenameLayer();
            this.basicInfo.categoryname = obj.fullcategoryname;
            this.basicInfo.categoryidx = obj.cateidx;
            this.basicInfo.depth1Category.idx = obj.depth1idx;
            this.basicInfo.depth1Category.value = obj.depth1name;
            this.basicInfo.depth2Category.idx = obj.depth2idx;
            this.basicInfo.depth2Category.value = obj.depth2name;
            this.basicInfo.depth3Category.idx = obj.depth3idx;
            this.basicInfo.depth3Category.value = obj.depth3name;
            this.basicInfo.depth4Category.idx = obj.depth4idx;
            this.basicInfo.depth4Category.value = obj.depth4name;
        },
        // 상품별 카테고리 추가
        addCategory: function() {
            // 카테고리 선택 필수 체크
            if (this.$util.isNull(this.basicInfo.depth1Category.idx)) {
                alert('추가할 카테고리를 선택해주세요.');
                return;
            }
            
            let cateidx = '';
            let fullCategoryName = '';
            for (let i=0; i<4; i++) {
                let listName = 'depth' + (i+1) + 'List';
                let categoryName = 'depth' + (i+1) + 'Category';
                let categoryObj = this.basicInfo[categoryName];
                
                // 하위 카테고리까지 선택여부 체크
                if (this.basicInfo.categoryList[listName].length > 0 && this.$util.isNull(categoryObj.idx)) {
                    alert(categoryObj.category + ' 카테고리를 선택해주세요.');
                    return;
                }
                // 카테고리명 세팅
                if (i == 0) {
                    fullCategoryName = fullCategoryName.concat(categoryObj.value);
                } else {
                    if (this.basicInfo.categoryList[listName].length > 0 && !this.$util.isNull(categoryObj.idx)) {
                        fullCategoryName = fullCategoryName.concat(' > ', categoryObj.value);
                    }
                }
                // 최종선택 카테고리 세팅
                if (this.basicInfo.categoryList[listName].length > 0 && !this.$util.isNull(categoryObj.idx)) {
                    cateidx = categoryObj.idx;
                }
            }

            for (let i=0; i<this.basicInfo.goodsCategoryList.length; i++) {
                let categoryObj = this.basicInfo.goodsCategoryList[i];
                // 카테고리 중복체크
                if (cateidx == categoryObj.cateidx) {
                    alert('이미 추가된 카테고리 입니다.');
                    return;
                }
                // 선택카테고리 여부 초기화
                categoryObj.isselected = false;
            }

            // 카테고리 목록에 추가
            let params = {
                isselected: 'T',
                isrepre: 'F',
                fullcategoryname: fullCategoryName,
                cateidx: cateidx,
                isstar: 'F'
            }
            this.basicInfo.goodsCategoryList.push(params);
            this.chgInfo.chgCate = 'T';
        },
        // 상품별 카테고리 삭제
        removeGoodsCategory: function(obj) {
            let findIndex = this.basicInfo.goodsCategoryList.indexOf(obj);
            this.basicInfo.goodsCategoryList.splice(findIndex, 1);
            this.chgInfo.chgCate = 'T';
        },
        // 선택카테고리 표시
        selectCategory: function(obj) {
            this.basicInfo.goodsCategoryList.forEach(item => {
                item.isselected = 'F';
            })
            obj.isselected = 'T';
        },
        // 카테고리템플릿 조회 show/hide
        onCateTemplateShow: function() {
            if (!this.showInfo.istempshow) {
                this.getCateTemplateList();
            } else {
                this.basicInfo.categoryTemplateList = [];
                this.showInfo.istempshow = false;
            }
        },
        // 카테고리템플릿목록 조회
        getCateTemplateList: function() {
            this.$http.post('/admin/goods/regist/catetemp/list')
                .then(result => {
                    this.$util.debug(result);
                    this.basicInfo.categoryTemplateList = result.data.list;
                    this.showInfo.istempshow = true;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 카테고리템플릿 삭제
        deleteCateTemplate: function(obj) {
            obj.isstar = 'T';
            this.saveCategoryTemp(obj);
        },
        // 카테고리 템플릿 저장
        saveCategoryTemp: function(obj) {
            this.$http.post('/admin/goods/regist/catetemp/save', obj)
                .then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == 200) {
                        if (obj.isstar == 'T') {
                            let findIndex = this.basicInfo.categoryTemplateList.indexOf(obj)
                            this.basicInfo.categoryTemplateList.splice(findIndex, 1);
                        }
                    } else {
                        obj.isstar = 'T';
                        alert(result.message);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 카테고리템플릿 적용
        setGoodsCategory: function() {
            // 체크여부 확인
            let checkCnt = this.basicInfo.categoryTemplateList.filter(item => {
                return item.ischecked == true;
            }).length;
            if (checkCnt == 0) {
                alert('적용할 카테고리를 선택해주세요.');
                return;
            }

            // 상품별 카테고리에 적용 (이미 추가되있는거 제외)
            this.basicInfo.categoryTemplateList.forEach(catetemp => {
                let params = {};
                if (catetemp.ischecked ) {
                    let existCnt = this.basicInfo.goodsCategoryList.filter(goodscate => {
                        return goodscate.cateidx == catetemp.cateidx;
                    }).length;
                    if (existCnt == 0) {
                        params = {
                            isstar: 'T',
                            isselected: 'F',
                            isrepre: 'F',
                            fullcategoryname: catetemp.fullcategoryname,
                            cateidx: catetemp.cateidx
                        }
                        this.basicInfo.goodsCategoryList.push(params);
                    }
                }
            });

            // 카테고리템플릿 레이어 닫기
            this.closeCatetempLayer();
        },
        // 카테고리템플릿 초기화
        initCategoryTemplate: function() {
            this.basicInfo.categoryTemplateList.forEach(item => {
                item.ischecked = false;
            });
        },
        // datepicker callback
        pickerChangeEvent(data) {
            this.basicInfo.disstdate = data.fromYYYYMMDD;
            this.basicInfo.dissthour = data.fromHH;
            this.basicInfo.disstmin = data.fromMM;
            this.basicInfo.disstday = data.fromDate12;

            this.basicInfo.diseddate = data.toYYYYMMDD;
            this.basicInfo.disedhour = data.toHH;
            this.basicInfo.disedmin = data.toMM;
            this.basicInfo.disedday = data.toDate12;
        },
        // 담당MD 조회 show/hide
        onChargemdShow: function() {
            if (!this.showInfo.ismdshow) {
                this.getChargemdList();
            } else {
                this.basicInfo.chargemdList = [];
                this.showInfo.ismdshow = false;
                this.sortData.chargemd = {
                    psort: 'mdcode_asc',
                    mdcode: 'mdcode_asc',
                    mdname: 'mdname_asc',
                };
            }
        },
        // 담당MD 목록 조회
        getChargemdList: function() {
            let param = this.sortData.chargemd;
            this.$http.post('/admin/goods/regist/chargemd/list', param)
                .then(result => {
                    this.$util.debug(result);
                    this.basicInfo.chargemdList = result.data.list;
                    this.showInfo.ismdshow = true;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 담당MD 세팅
        setChargemdInfo: function(obj) {
            this.basicInfo.mdcode = obj.mdcode;
            this.basicInfo.mdname = obj.mdname;
            setTimeout(function () {
                this.basicInfo.chargemdList = [];
                this.showInfo.ismdshow = false;
            }.bind(this), 50);
        },
        // 적용채널 전체적용 체크
        checkAllChannel: function() {
            let isAllCheck = this.basicInfo.isallchannel;
            this.basicInfo.muappchtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.basicInfo.muappchtypeArr.push(type.cmcode);
                }
            }
        },
        // 구성상품삭제
        removeConstGoods: function() {
            // 선택항목 체크
            if (this.moveData.targetIdx.length == 0) {
                alert('삭제할 상품을 선택해주세요.');
                return;
            }
            // 목록에서 선택된 항목 삭제
            for (let i=this.moveData.targetIdx.length-1; i>=0; i--) {
                this.constInfo.constGoodsList.splice(this.moveData.targetIdx[i], 1);
            }
            // 삭제후 목록이 없는경우 전체체크 해제
            if (this.constInfo.constGoodsList.length == 0) {
                this.constInfo.isallchkgoods = 'F';
            }
            this.moveData.targetIdx = [];
            this.moveData.isSuccess = false;
            // RowNo 재조회
            this.reloadRowno(this.constInfo.constGoodsList, 'sortnum');
        },
        // 구성상품목록 전체체크
        checkAllConstGoodsList: function(value) {
            this.moveData.targetIdx = [];
            if (value) {
                this.constInfo.constGoodsList.forEach((item, index) => {
                    this.moveData.targetIdx.push(index);
                });
            }
        },
        // 구성상품목록 개별체크
        checkConstGoodsList: function() {
            if (this.constInfo.constGoodsList.length > this.moveData.targetIdx.length){
                this.constInfo.isallchkgoods = 'F';
            } else {
                this.constInfo.isallchkgoods = 'T';
            }
        },
        // 구성상품 팝업 오픈
        openConstGoodsPopup: function() {
            this.$eventBus.$emit('modalShow', AddConstGoodsListPopup, null,
                (result) => {
                    // 팝업에서 가져온 결과 구성상품 목록에 적용(이미 추가되어 있는 상품 제외)
                    let resultList = result.list;
                    for (let i=0; i<resultList.length; i++) {
                        resultList[i].ismaingoods = 'F';
                        resultList[i].isdisplay = 'T';
                        let existCnt = this.constInfo.constGoodsList.filter(item => {
                            return item.goodsno == resultList[i].goodsno;
                        }).length;
                        if (existCnt == 0) {
                            this.constInfo.constGoodsList.push(resultList[i]);
                        }
                    }
                    // 대표상품 없는경우 세팅
                    let mainGoodsCnt = this.constInfo.constGoodsList.filter(item => {
                        return item.ismaingoods == 'T';
                    }).length;
                    if (this.constInfo.constGoodsList.length > 0 && mainGoodsCnt == 0) {
                        this.constInfo.constGoodsList[0].ismaingoods = 'T';
                    }
                    // RowNo 재조회
                    this.reloadRowno(this.constInfo.constGoodsList, 'sortnum');
                }
            );
        },
        // 대표상품 셋팅
        setIsMainGoods: function(obj) {
            this.constInfo.constGoodsList.forEach(item => {
                item.ismaingoods = 'F';
            });
            obj.ismaingoods = 'T';
            this.imageInfo.sameimgidx = obj.fileidx;
        },
        // 구성상품 개별 전시여부 변경
        changeDisplay: function(obj) {
            if (obj.isdisplay == 'F') {
                obj.isdisplay = 'T';
            } else {
                obj.isdisplay = 'F';
            }
        },
        // 엑셀양식다운로드
        downloadExcelTemplate: function() {
            let params = { filename: 'DealGoodsTemplate.xlsx' }   // 서버에 저장되어있는 파일명
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/common/excel/download', params, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 엑셀다운로드
        downloadExcel: function() {
            if (this.$util.isNull(this.basicInfo.dealno)) {
                alert("임시저장 또는 저장 이후 다운로드가 가능합니다.");
                return;
            }
            if (this.constInfo.constGoodsList.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let params = {
                dealno: this.basicInfo.dealno,
                time: this.$util.getDate()+this.$util.getTime()
            };
            let config = { responseType: 'arraybuffer' };
            this.$http.post("/admin/goods/regist/constgoods/exceldown", params, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 상품상세(수정) 팝업 오픈
        goGoodsDetail: function(value) {
            this.activeGoodsNo = value;
            this.isGoodsDetailShow = true;
        },
        closeGoodsDetail: function () {
            this.isGoodsDetailShow = false;
        },
        // PC 상품상세설명과 동일하게 세팅
        setSameAsPcDetailContrent: function() {
            // if (this.imageInfo.issamepccont) {
                this.$refs.mobileeditor.content = this.$refs.pceditor.content;
            // }
        },
        // 대표상품이미지 세팅
        setPcrepreImg: function(value) {
            if (value == 'T') {
                if (!this.$util.isNull(this.files.pcrepreimgfile)) {
                    this.deletefilelist.push(this.files.pcrepreimgfile);
                }
                this.files.pcrepreimgfile = null;
            }
        },
        // 판매대상-유형별 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.etcInfo.isallmember;
            this.etcInfo.mumembertypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.dadamembertype){
                    this.etcInfo.mumembertypeArr.push(type.cmcode);
                }
            }
        },
        // 메모 추가
        addMeno: function() {
            if (this.$util.isNull(this.etcInfo.inputmemo)){
                alert('메모 내용을 입력해주세요.');
                return;
            }
            let params = {
                iscreated: 'T',
                memo: this.etcInfo.inputmemo,
                regdate: this.$util.getDate('-') + ' ' + this.$util.getTime(':'),
                reguserid: this.user.id,
                regusername: this.user.name,
                istrash: 'F'
            }
            this.etcInfo.goodsMemoList.splice(0, 0, params);
            this.etcInfo.inputmemo = '';
            this.etcInfo.isshowmemo = true;
            // RowNo 재조회
            this.reloadRowno(this.etcInfo.goodsMemoList, 'no');
            this.chgInfo.chgMemo = 'T';
        },
        // 메모 삭제
        removeMeno: function(obj) {
            if (obj.iscreated == 'T') {
                let findIndex = this.etcInfo.goodsMemoList.indexOf(obj);
                this.etcInfo.goodsMemoList.splice(findIndex, 1);
            } else {
                obj.istrash = 'T';
            }
            let showListCnt = this.etcInfo.goodsMemoList.filter(item => {
                return item.istrash == 'F';
            }).length;
            if (showListCnt == 0) {
                this.etcInfo.isshowmemo = false;
            }
            // RowNo 재조회
            this.reloadRowno(this.etcInfo.goodsMemoList, 'no');
            this.chgInfo.chgMemo = 'T';
        },
        // RowNo 재조회
        reloadRowno: function(targetList, col) {
            let rowno = 1;
            for (let i=0; i<targetList.length; i++) {
                let target = targetList[i];
                if (targetList.indexOf('goodsMemoList')>-1) {
                    if (target.istrash == 'F') {
                        target[col] = rowno;
                        rowno ++;
                    }
                } else {
                    target[col] = rowno;
                    rowno ++;
                }
            }
        },
        // 파일보기
        viewFile: function(url) {
            this.$viewerApi({
                images : [url]
            });
        },
        // 첨부파일(탐색기 열기)
        fileAttach: function(fileTypeKey) {
            if (Array.isArray(this.$refs[fileTypeKey])) {
                this.$refs[fileTypeKey][0].click();
            } else {
                this.$refs[fileTypeKey].click();
            }
        },
        // 가져온 파일 세팅
        handleFileUpload: function(fileTypeKey, target, index) {
            if (fileTypeKey.indexOf('changeaddimgfile') > -1) {
                // 추가이미지 변경
                let files = target.files;
                if (this.$util.isNull(files[0])) {
                    return;
                }
                let fileType = ['image/png','image/jpeg', 'image/png'];
                if(!fileType.includes(files[0].type)){
                    alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                if(files[0].size > 10485760){
                    alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                let fileObj = {
                    file: files[0],
                    status: 'I'
                }
                this.deletefilelist.push(this.files.addimgfilelist[index]);
                this.$set(this.imgPreview.addimgfilelist, index, URL.createObjectURL(fileObj.file));
                this.files.addimgfilelist[index] = fileObj;
                // 초기화
                this.$refs['changeaddimgfile'+index].value = '';
                this.chgInfo.chgAddImg = 'T';
            } else if (fileTypeKey.indexOf('addimg') > -1) {
                // 추가이미지
                let files = target.files;
                if (this.$util.isNull(files[0])) {
                    return;
                }
                let fileType = ['image/png','image/jpeg', 'image/png'];
                if(!fileType.includes(files[0].type)){
                    alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                if(files[0].size > 10485760){
                    alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                if (this.files.addimgfilelist.length >= 10) {
                    return;
                }
                let fileObj = {
                    file: files[0],
                    status: 'I'
                }
                this.imgPreview.addimgfilelist[this.files.addimgfilelist.length] = URL.createObjectURL(fileObj.file);
                this.files.addimgfilelist.push(fileObj);
                // 초기화
                this.$refs.addimgFile.value = '';
                this.chgInfo.chgAddImg = 'T';
            } else if (fileTypeKey.indexOf('repreimg') > -1) {
                // PC, 모바일 대표이미지
                let file = this.$refs[fileTypeKey];
                if (this.$util.isNull(file.files[0])) {
                    return;
                }
                let fileType = ['image/png','image/jpeg', 'image/png'];
                if(!fileType.includes(file.files[0].type)){
                    alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                    file.value = null;
                    return false;
                }
                if(file.files[0].size > 10485760){
                    alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                    file.value = null;
                    return false;
                }
                if (!this.$util.isNull(this.files[fileTypeKey]) && this.files[fileTypeKey].status != 'N') {
                    this.deletefilelist.push(this.files[fileTypeKey]);
                }
                let fileObj = {
                    file: file.files[0],
                    status : 'I'
                }
                this.$refs.repreimgtd.click();
                this.files[fileTypeKey] = fileObj;
                this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);
                
                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey == 'morepreimgfile') {
                    this.imageInfo.issamepcimg = false;
                    this.chgInfo.chgMoImg = 'T';
                } else {
                    this.chgInfo.chgPcImg = 'T';
                }
            } else if (fileTypeKey.indexOf('excelFile') > -1) {
                // 엑셀올리기
                let file = this.$refs.excelFile;
                if (this.$util.isNull(file.files[0])) {
                    return;
                }
                let fileType = ['application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'];
                if(!fileType.includes(file.files[0].type)){
                    alert('엑셀 파일만 첨부 가능합니다.');
                    file.value = null;
                    return false;
                }
                
                if(file.files[0].size > 10485760){
                    alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                    file.value = null;
                    return false;
                }
                this.excelFile = file.files[0];
                this.$refs.excelFileName.value = file.files[0].name;
            }
        },
        // 파일삭제
        removeFile(fileTypeKey, index) {
            if (fileTypeKey.indexOf('addimg') > -1) {
                if (this.files.addimgfilelist[index].status == 'N') {
                    this.deletefilelist.push(this.files.addimgfilelist[index]);
                }
                this.files.addimgfilelist.splice(index, 1);
                this.imgPreview.addimgfilelist.splice(index, 1);
                this.$refs.addimgFile.value = '';
                this.chgInfo.chgAddImg = 'T';
            } else {
                if (this.files[fileTypeKey].status == 'N') {
                    this.deletefilelist.push(this.files[fileTypeKey]);
                } else {
                    this.imgPreview[fileTypeKey] = '';
                }
                this.files[fileTypeKey] = '';
                this.$refs[fileTypeKey].value = '';
                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey == 'morepreimgfile') {
                    this.imageInfo.issamepcimg = false;
                    this.chgInfo.chgMoImg = 'T';
                } else {
                    this.chgInfo.chgPcImg = 'T';
                }
            }
        },
        // 엑셀파일 읽기
        readExcelFile: function(fileTypeKey) {
            let file = null;
            let headerInfo = [];
            // 구성상품
            if (fileTypeKey.indexOf('excelFile') > -1) {
                if (this.$util.isNull(this.excelFile)) {
                    alert('파일을 선택해주세요');
                    return;
                }
                file = this.excelFile;
                headerInfo = ['goodscode'];
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
        
            // 구성상품
            if (fileTypeKey.indexOf('excelFile') > -1) {
                // 유효성 체크
                for (let i=1; i<excelData.length; i++) {
                    let item = excelData[i];
                    // 상품코드 중복체크
                    for (let j=i+1; j<excelData.length; j++) {
                        if (item.goodscode == excelData[j].goodscode) {
                            alert("동일한 상품코드가 존재합니다. 확인 후 진행해주세요.");
                            this.$refs.excelFile.value = '';
                            this.$refs.excelFileName.value = '';
                            this.excelFile = null;
                            return;
                        }
                    }
                    // 상품코드 숫자여부 체크
                    if (!this.$util.isNumber(item.goodscode)) {
                        alert("숫자가 아닌 상품코드가 존재합니다. 확인 후 진행해주세요.");
                        this.$refs.excelFile.value = '';
                        this.$refs.excelFileName.value = '';
                        this.excelFile = null;
                        return;
                    }
                    // 상품코드 자릿수 체크
                    let goodscode = item.goodscode + '';
                    if (goodscode.length != 10) {
                        alert("10자리가 아닌 상품코드가 존재합니다. 확인 후 진행해주세요.");
                        this.$refs.excelFile.value = '';
                        this.$refs.excelFileName.value = '';
                        this.excelFile = null;
                        return;
                    }
                }
                this.$refs.excelFile.value = '';
                this.$refs.excelFileName.value = '';
                this.excelFile = null;

                // 상품정보 조회
                this.$http.post('/admin/goods/regist/constgoods/list', { goodscodelist: excelData })
                    .then(result => {
                        if (result.statusCode == '200') {
                            let resultList = result.data.list;
                            if ( resultList.length == 0) {
                                alert("상품정보가 존재하지않습니다. 입력한 상품코드를 확인해주세요.");
                            } else {                                
                                // 구성상품 목록에 적용(이미 추가되어 있는 상품 제외)
                                let resultCnt = 0;
                                for (let i=0; i<resultList.length; i++) {
                                    resultList[i].ismaingoods = 'F';
                                    resultList[i].isdisplay = 'T';
                                    let existCnt = this.constInfo.constGoodsList.filter(item => {
                                        return item.goodscode == resultList[i].goodscode;
                                    }).length;
                                    if (existCnt == 0) {
                                        this.constInfo.constGoodsList.push(resultList[i]);
                                        resultCnt++;
                                    }
                                }
                                // 대표상품 없는경우 세팅
                                let mainGoodsCnt = this.constInfo.constGoodsList.filter(item => {
                                    return item.ismaingoods == 'T';
                                }).length;
                                if (this.constInfo.constGoodsList.length > 0 && mainGoodsCnt == 0) {
                                    this.constInfo.constGoodsList[0].ismaingoods = 'T';
                                }
                                // RowNo 재조회
                                this.reloadRowno(this.constInfo.constGoodsList, 'sortnum');
                                alert(resultCnt + '건 추가되었습니다.');
                            }
                        } else {
                            alert(result.message);
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 정렬
        sortToggle: function(target, key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData[target][sortKey] = sortName;
            this.sortData[target].psort = sortName;

            // 담당MD 조회
            if (target == 'chargemd') {
                this.getChargemdList();
            }
            // 관리자메모 조회
            else if (target == 'memo') {
                this.etcInfo.goodsMemoList.sort((a, b) => {
                    a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                    b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                    if (a[sortKey] < b[sortKey]) {
                        return sortOrder == 'asc'? -1: 1;
                    } else if (a[sortKey] > b[sortKey]) {
                        return sortOrder == 'asc'? 1: -1;
                    }
                    return 0;
                });
                // RowNo 재조회
                this.reloadRowno(this.etcInfo.goodsMemoList);
            }
        },
        // 카테고리명조회 레이어 닫기
        closeCatenameLayer: function() {
            this.showInfo.iscateshow = false;
            this.basicInfo.categorynameList = [];
        },
        // 카테고리템플릿 레이어 닫기
        closeCatetempLayer: function() {
            this.showInfo.istempshow = false;
            this.basicInfo.categoryTemplateList = [];
        },
        // 담당MD 조회 레이어 닫기
        closeChargemdLayer: function() {
            this.showInfo.ismdshow = false;
            this.basicInfo.chargemdList = [];
        },
        // Anchor 탭 클릭 이벤트
        onActive: function(obj) {
            obj.isOpen = true;
        },
        // 탭 title 클릭 이벤트
        onToggle: function(obj) {
            obj.isOpen = !obj.isOpen;
        },
        // 미리보기
        goPriview: function() {
            if (this.$util.isNull(this.basicInfo.goodscode)) {
                alert('임시저장 또는 저장 이후 미리보기가 가능합니다.');
                return;
            }
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + this.basicInfo.goodscode, "_blank");
        },
        setPreSave: function() {
            // 다중항목 체크박스 전체선택인 경우 arr에 세팅
            if (this.basicInfo.isallchannel == 'T') {
                this.checkAllChannel();
            }
            if (this.etcInfo.isallmember == 'T') {
                this.checkAllMembertype();
            }
            
            // 에디터 텍스트 데이터 저장
            if (this.imageInfo.isusenotice=='T') {
                this.imageInfo.noticecontent = this.$refs.noticeeditor.content;
            } else {
                this.imageInfo.noticecontent = '';
            }
            if (this.imageInfo.isuseintro=='T') {
                this.imageInfo.introcontent = this.$refs.introeditor.content;
            } else {
                this.imageInfo.introcontent = '';
            }
            this.imageInfo.pccontent = this.$refs.pceditor.content;
            this.imageInfo.mobilecontent = this.$refs.mobileeditor.content;
            
            // 구성상품중 대표상품의 취소승인필요여부 세팅
            this.constInfo.constGoodsList.forEach(item=> {
                if (item.isrepre === 'T') {
                    this.basicInfo.iscncappr = item.iscncappr;
                }
            });
        },
        // 임시저장 유효성체크
        checkTempSaveVaildation: function() {
            // 1. 저장 데이터 전처리
            this.setPreSave();

            // 2. 유효성체크
            let checkResult = true;
            let valid = [];
            let msg = '';

            // (1) 기본정보
            if (checkResult) {
                valid = [
                    {field: 'basicInfo.goodsname', type: 'I', name:'[기본정보] 상품명', required: true},
                    {field: 'basicInfo.disstdate', type: 'I', name:'[기본정보] 전시시작일자', required: true},
                    {field: 'basicInfo.dissthour', type: 'I', name:'[기본정보] 전시시작시간', required: true},
                    {field: 'basicInfo.disstmin', type: 'I' , name:'[기본정보] 전시시작분', required: true},
                    {field: 'basicInfo.diseddate', type: 'I', name:'[기본정보] 전시종료일자', required: true},
                    {field: 'basicInfo.disedhour', type: 'I', name:'[기본정보] 전시종료시간', required: true},
                    {field: 'basicInfo.disedmin', type: 'I' , name:'[기본정보] 전시종료분', required: true},
                    {field: 'basicInfo.mdcode', type: 'I'   , name:'[기본정보] 담당MD', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            // (2) 구성정보
            if (checkResult) {
                if (this.constInfo.constGoodsList.length > 0) {
                    let maingoodsCnt = this.constInfo.constGoodsList.filter(item => {
                        return item.ismaingoods === 'T';
                    }).length;
                    if (maingoodsCnt == 0) {
                        checkResult = false;
                        alert('[구성정보] 대표상품을 선택해주세요.');
                    }
                }
            }
            
            return checkResult;
        },
        // 저장 유효성체크
        checkSaveVaildation: function() {
            // 1. 저장 데이터 전처리
            this.setPreSave();

            // 2. 유효성체크
            let checkResult = true;
            let valid = [];
            let msg = '';

            // (1) 기본정보
            if (checkResult) {
                valid = [
                    {field: 'basicInfo.goodsname', type: 'I', name:'[기본정보] 상품명', required: true},
                    {field: 'basicInfo.disstdate', type: 'I', name:'[기본정보] 전시시작일자', required: true},
                    {field: 'basicInfo.dissthour', type: 'I', name:'[기본정보] 전시시작시간', required: true},
                    {field: 'basicInfo.disstmin', type: 'I' , name:'[기본정보] 전시시작분', required: true},
                    {field: 'basicInfo.diseddate', type: 'I', name:'[기본정보] 전시종료일자', required: true},
                    {field: 'basicInfo.disedhour', type: 'I', name:'[기본정보] 전시종료시간', required: true},
                    {field: 'basicInfo.disedmin', type: 'I' , name:'[기본정보] 전시종료분', required: true},
                    {field: 'basicInfo.mdcode', type: 'I'   , name:'[기본정보] 담당MD', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            // (2) 구성정보
            if (checkResult) {
                if (this.constInfo.constGoodsList.length > 0) {
                    let maingoodsCnt = this.constInfo.constGoodsList.filter(item => {
                        return item.ismaingoods === 'T';
                    }).length;
                    if (maingoodsCnt == 0) {
                        checkResult = false;
                        alert('[구성정보] 대표상품을 선택해주세요.');
                    }
                }
            }
            
            return checkResult;
        },
        // 임시저장
        goTemporarySave: function() {
            // 임시저장 유효성체크
            if (this.checkTempSaveVaildation()) {
                // 상품 저장
                if (confirm('임시저장 하시겠습니까?')) {
                    this.basicInfo.istempsave = 'T';
                    this.basicInfo.goodsapprtype = this.$store.getters['ADMIN'].GOODS_STATUS_TEMP       // 상품승인상태(임시저장)
                    this.basicInfo.goodsselltype = this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST001  // 상품판매상태(임시저장시 판매대기)
                    if (this.chgInfo.fstTempSave==='T') {
                        this.chgInfo.fstTempSave = '';
                    } else {
                        this.chgInfo.fstTempSave = 'T';
                    }
                    this.save();
                }
            }
        },
        // 저장
        goSave: function() {
            // 저장 유효성체크
            if (this.checkSaveVaildation()) {
                // 상품 저장
                if (confirm('저장 하시겠습니까?')) {
                    if (this.$util.isNull(this.basicInfo.goodsno) || this.basicInfo.istempsave === 'T') {
                        this.chgInfo.fstTempSave = 'F';
                        this.chgInfo.fstSave = 'T';
                    }
                    this.basicInfo.istempsave = 'F';
                    this.basicInfo.goodsapprtype = this.$store.getters['ADMIN'].GOODS_STATUS_APPROVAL // 상품승인상태(관리자: 승인완료)
                    this.save();
                }
            }
        },
        // 저장로직
        save: function() {
            // 저장 파라미터 merge
            let params = Object.assign({}, this.defaultInfo, this.basicInfo, this.constInfo, this.imageInfo, this.etcInfo);
            // 파일세팅
            let files = [];
            const constants = this.$store.getters['ADMIN'];
            if (!this.$util.isNull(this.files.pcrepreimgfile)) {
                files.push({key: constants.IMG_TYPE_GOODS_IMG_PC_B, file: this.files.pcrepreimgfile.file});
            }
            if (!this.$util.isNull(this.files.morepreimgfile)) {
                files.push({key: constants.IMG_TYPE_GOODS_IMG_MO_B, file: this.files.morepreimgfile.file});
            }
            for(let i=0; i<this.files.addimgfilelist.length; i++) {
                files.push({key: constants['IMG_TYPE_GOODS_IMG_ADD_B'+(i+1)], file: this.files.addimgfilelist[i].file});
            }
            params.files = files;
            params.deletefilelist = this.deletefilelist;

            let msg = '저장이 완료되었습니다.';
            if (this.basicInfo.istempsave == 'T') {
                msg = '임시저장이 완료되었습니다.';
            }
            this.$http.post('/admin/goods/regist/deal/save', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.basicInfo.dealno = data.goodsno;
                    if (result.statusCode == '200') {
                        if (this.basicInfo.istempsave === 'T') {
                            alert(msg);
                            // 구성상품 순서노출 초기화
                            this.moveData.targetIdx = [];
                            this.moveData.isSuccess = false;
                            this.getGoodsInfo();
                        } else {
                            if(!alert(msg)) {
                                this.getGoodsInfo();
                            }
                        }
                    }
                }).catch(error => {
                    this.$util.debug(error);
                })
        }
    },
    watch: {
        // 카테고리 선택
        'basicInfo.depth1Category.idx': function(value) {
            let params = {depth: 1, idx: value};
            this.getCategoryCodeList(params);
        },
        'basicInfo.depth2Category.idx': function(value) {
            let params = {depth: 2, idx: value};
            this.getCategoryCodeList(params);
        },
        'basicInfo.depth3Category.idx': function(value) {
            let params = {depth: 3, idx: value};
            this.getCategoryCodeList(params);
        },
        'basicInfo.depth4Category.idx': function(value) {
            let params = {depth: 4, idx: value};
            this.getCategoryCodeList(params);
        },
        // 전시기간
        'basicInfo.disperiod': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]);

            // 상시
            if (type == 'all') {
                this.basicInfo.disstdate = this.$util.getDate('-');
                this.basicInfo.diseddate = this.$util.getFormatDate('20991231', '-');
            } else if (type == 'day') {
                this.basicInfo.diseddate = this.$util.getAddDate(this.basicInfo.disstdate, addValue, '-');
            } else if (type == 'month') {
                this.basicInfo.diseddate = this.$util.getAddMonth(this.basicInfo.disstdate, addValue, '-');
            }
            this.basicInfo.dissthour = '00';
            this.basicInfo.disedhour = '11';
            this.basicInfo.disstmin = '00';
            this.basicInfo.disedmin = '59';
        },
        // 기본정보-적용채널
        'basicInfo.muappchtypeArr': function(value) {
            if (value.length < this.commonCode.muappchtype.length) {
                this.basicInfo.isallchannel = 'F';
            } else {
                this.basicInfo.isallchannel = 'T';
            }
            this.basicInfo.muappchtype = this.basicInfo.muappchtypeArr.join();
        },
        // 추가정보-회원유형별
        'etcInfo.mumembertypeArr': function(value) {
            if (value.length < this.commonCode.dadamembertype.length) {
                this.etcInfo.isallmember = 'F';
            } else {
                this.etcInfo.isallmember = 'T';
            }
            this.etcInfo.mumembertype = this.etcInfo.mumembertypeArr.join();
        },
    }
}