import XLSX from 'xlsx'
import vClickOutside from 'v-click-outside';
import CommonNavigator from '@views.admin/common/CommonNavigator.vue'
import CommonEditor from '@views.admin/common/CommonEditor.vue';
import CommonDatePickerFromTo from '@views.admin/common/CommonDatePickerFromTo.vue';
import SearchBrandListPopup from '@views.admin/goods/popup/SearchBrandListPopup.vue';
import OptionItemManagePopup from '@views.admin/goods/popup/OptionItemManagePopup.vue';
import SearchOriginalCodePopup from '@views.admin/goods/popup/SearchOriginalCodePopup.vue';
import DelivTempListPopup from '@views.admin/goods/popup/DelivTempListPopup.vue';
import CommonAddUserPopup from '@views.admin/common/popup/CommonAddUserPopup.vue';
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';

export default {
    name: 'admin.goods.regist.goodsRegist',
    components: {
        CommonNavigator,
        CommonEditor,
        CommonDatePickerFromTo
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
            isPartner: false,
            user: {},
            // Anchor 탭
            tabObject: {
                tab1: {href: '#tab1', tabName: '기본정보', isActive: false, isOpen: true},
                tab2: {href: '#tab2', tabName: '판매정보', isActive: false, isOpen: true},
                tab3: {href: '#tab3', tabName: '옵션정보', isActive: false, isOpen: true},
                tab4: {href: '#tab4', tabName: '이미지정보', isActive: false, isOpen: true},
                tab5: {href: '#tab5', tabName: '배송정보', isActive: false, isOpen: true},
                tab6: {href: '#tab6', tabName: '상품정보고시', isActive: false, isOpen: true},
                tab7: {href: '#tab7', tabName: '추가정보', isActive: false, isOpen: true}
            },
            commonCode: {
                muappchtype: [],    // 적용채널
                goodsselltype: [],  // 상품판매상태
                goodsdivtype: [],   // 상품구분상태
                dadamembertype: [], // 다다픽회원유형
                memlvtype: [],      // 회원등급
                kcdivtype: [],      // KC대상구분
                colortype: [],      // 컬러코드값
                gicontype: []       // 아이콘설정
            },
            sortData: {
                chargemd: {
                    psort: 'mdsabun_asc',
                    mdsabun: 'mdsabun_asc',
                    empname: 'empname_asc',
                    mdcode: 'mdcode_asc',
                    mdname: 'mdname_asc'
                },
                goodsMember: {
                    psort: 'regdate_desc',
                    dadamembertypename: 'dadamembertypename_asc',
                    memlvtypename: 'memlvtypename_asc',
                    regdate: 'regdate_desc'
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
            deletefilelist: [],
            optionExcelFile: '',
            files: {
                pcrepreimgfile: null,
                morepreimgfile: null,
                addimgfilelist: [],
                kcfilelist: []
            },
            imgPreview: {
                pcrepreimgfile: '',
                morepreimgfile: '',
                addimgfilelist: [],
                kcfilelist: []
            },
            // 조회여부
            issearch: false,
            // 기본정보
            basicInfo: {
                goodsapprtype: '',  //상품승인상태
                orggoodsapprtype: '',  //기존상품승인상태
                contents: '',       //승인요청시 사유
                isdeal: 'F',        //딜여부
                istempsave: '',     //임시저장여부
                orgistempsave: '',  //기존임시저장여부
                ispbgoods: 'T',     //판매구분
                dealerno: null,     //입점업체번호
                goodsno: '',        //상품번호
                goodsname: '',      //상품명
                categoryname: '',   //카테고리명
                categoryidx: '',    //카테고리일련번호
                summary: '',        //상품요약설명
                keyword: '',        //검색키워드
                brandidx: '',       //브랜드일련번호
                brandname: '',      //브랜드명
                isnobrand: 'F',     //브랜드없음여부
                mdsabun: '',        //MD사번
                empname: '',        //사원이름
                mdifidx: '',        //MD정보IDX
                mdsword: '',        //MD검색어
                isallchannel:'T',   //전체채널여부
                delivinfo: '',      //배송안내
                muappchtype: '',    //다중적용채널
                muappchtypeArr: [], //다중적용채널Array
                depth1Category: {category:'대분류', idx: '', text:''},  //대분류 카테고리일련번호
                depth2Category: {category:'중분류', idx: '', text:''},  //중분류 카테고리일련번호
                depth3Category: {category:'소분류', idx: '', text:''},  //소분류 카테고리일련번호
                depth4Category: {category:'세분류', idx: '', text:''},  //세분류 카테고리일련번호
                // DB조회 파라미터
                partnerList: [],
                categoryObj: {      //카테고리 depth별 목록
                    depth1list: [],
                    depth2list: [],
                    depth3list: [],
                    depth4list: []
                },
                goodsCategoryList: [],
                categorynameList:[],
                categoryTemplateList:[],
                chargemdList: []
            },
            // 판매정보
            sellInfo: {
                istaxfree: 'F',     //면세여부
                goodsselltype: '',  //상품판매상태
                disstday: '',       //전시시작일
                disstdate: '',      //전시시작일자
                disedday: '',       //전시종료일
                diseddate: '',      //전시종료일자
                dissthour: '',      //전시시작시간
                disstmin: '',       //전시시작분
                disedhour: '',      //전시종료시간
                disedmin: '',       //전시종료분
                disperiod: '',      //전시기간
                marketprice: '',    //정상가
                price: '',          //판매가
                minordcnt: 1,       //최소주문수량
                maxordcnt: 999,     //최대주문수량
                daymaxordcnt: 999,  //일최대주문수량
                permaxordcnt: 999,  //1인당최대주문수량
                isdisplay: 'T',     //전시여부
                isfrstsale: 'T',    //첫구매할일적용여부
                iscncappr: 'F',     //취소승인필요여부
                goodsdivtype: '',   //상품구분상태
                selltarget: 'ALL',  //판매대상 (defualt: 전체회원)
                isallmemlv: 'T',    //회원등급전체여부
                mumemlvtype: '',    //다중대상회원등급
                mumemlvtypeArr: [], //다중대상회원등급Array
                goodsMemberList: [],//상품별 회원목록
                isallchkmem: 'F'    //상품별회원목록 전체선택여부
            },
            // 옵션정보
            optionInfo: {
                isallchkopt: 'F',       //옵션전체체크여부
                totcolortype: '',       //상단검색컬러타입
                optionItemList: [],     //옵션항목목록
                delOptionnoList: [],    //삭제한옵션번호목록
                optionList: [],         //옵션목록
                delOptioncodeList:[],   //삭제한옵션코드목록
                commrate: 0             //수수료율
            },
            // 이미지정보
            imageInfo: {
                issamepcimg: false,
                sameimgidx: '',
                issamepccont: false,
                isusenotice: 'F',
                isuseintro: 'F',
                noticecontent: '',
                introcontent: '',
                pccontent: '',
                mobilecontent: '',
                isallgicontype:'',      //전체아이콘여부
                mugicontype: '',        //다중아이콘
                mugicontypeArr: [],     //다중아이콘Array
            },
            // 배송정보
            deliveryInfo: {
                iscombdeliv: 'F', // 묶음배송여부
                delividx: '',   // 배송템플릿일련번호
                delivTempList: [],    // 배송템플릿목록
                delivTemp: {}   // 선택한 배송템플릿 정보
            },
            // 상품정보고시
            notifyInfo: {
                notifytplidx: '',       // 정보고시템플릿일련번호
                notifytpltitle: '',     // 정보고시템플릿명칭
                notifyTempList: [],     // 상품정보고시템플릿 목록
                notifyTempItemList: [], // 상품정보고시템플릿항목 목록
                kcdivtype: '',  // KC대상구분
                kccertlist: [], // KC인증목록
                chkRef: false           //상품상세참고 체크
            },
            // 추가정보
            etcInfo: {
                isepif: 'T',            //ep연동여부
                aswarmonth: '',         //as보증기간
                asnotice: '',           //as안내문구
                isallmember: 'T',       //회원유형전체여부
                mumembertype: '',       //다중대상회원유형
                mumembertypeArr: [],    //다중대상회원유형Array
                isopenreview: 'T',      //리뷰공개여부
                isallchkgoods: 'F',     //추가상품목록 전체체크여부
                goodsAdditionList: [],  //추가상품목록
                isallchkgoodsgrp: 'F',  //리뷰묶어보기 전체체크여부
                goodsGrpList: [],       //리뷰묶어보기 상품목록
                isshowmemo: false,      //메모리스트 show여부
                inputmemo: '',          //입력메모
                goodsMemoList: []       //관리자메모 목록
            },
            // 변경정보
            chgInfo: {
                chgCate: '',
                chgSpUser: '',
                chgOptItem: '',
                chgOpt: '',
                chgErpOpt: '',
                chgNotify: '',
                chgKccert: '',
                chgGoodsGrp: '',
                chgGoodsAdd: '',
                chgMemo: '',
                chgPcImg: '',
                chgMoImg: '',
                chgAddImg: '',
                chgKcAttach: '',
                fstTempSave: '',
                fstSave: ''
            }
        };
    },
    methods: {
        // 초기데이터 세팅
        onInit:function() {
            this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
            this.sellInfo.goodsselltype = this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST001;  //상품판매상태 (defualt: 판매대기)
            this.sellInfo.goodsdivtype = this.$store.getters['ADMIN'].GOODS_DIV_TYPE_GDT001;     //상품구분상태 (defualt: 새상품)
            this.notifyInfo.kcdivtype = this.$store.getters['ADMIN'].KC_DIV_TYPE_KDT001;        //KC대상구분 (defualt: 미대상)

            // 유저정보 세팅
            this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
            if (this.isPartner) {
                this.basicInfo.ispbgoods = 'F';
                this.basicInfo.dealerno = this.user.no;
                this.sellInfo.isfrstsale = 'T';
                this.sellInfo.iscncappr = 'F';
                this.sellInfo.selltarget = 'ALL';
                this.etcInfo.isopenreview = 'T';
                this.etcInfo.isepif = 'T';
                this.getPartnerList('F');
            }

            // 상품정보조회
            if (!this.$util.isNull(this.basicInfo.goodsno)) {
                this.issearch = true;
                this.getGoodsInfo();
            } else {
                let params = { 
                    status: 'I',
                    sort: 1,
                    optionname: '옵션'
                };
                this.optionInfo.optionItemList.splice(0, 0, params);
            }
            // 공통코드 조회
            this.getCommonCodeList();
            // 대분류 카테고리 목록 조회
            let params = { depth: 0, idx: 0, value: ''};
            this.getCategoryCodeList(params);
            // 상품정보고시템플릿 목록 조회
            this.getNotifyTempList();
            // 배송템플릿 조회
            this.getDelivTempList();
            // 전시기간 세팅 (defualt: 상시)
            this.sellInfo.disperiod = 'all_0';
        },
        // 공통코드 세팅
        getCommonCodeList: function() {
            let cmclassArr = ['MUAPPCHTYPE', 'GOODSSELLTYPE', 'GOODSDIVTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE', 'KCDIVTYPE', 'COLORTYPE', 'GICONTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 적용채널 초기값 전체선택
                    if (!this.issearch) {
                        this.checkAllChannel();
                        this.checkAllMembertype();
                        this.checkAllMemlvtype();
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 상품정보 조회
        getGoodsInfo: function() {
            let param = { goodsno: this.basicInfo.goodsno };
            this.$http.post('/admin/goods/regist/goods/detail', param)
                .then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == '200') {
                        let data = result.data;
                        // 상품별 목록조회
                        this.basicInfo.partnerList = data.partnerlist;
                        this.basicInfo.goodsCategoryList = data.goodscategorylist;
                        this.sellInfo.goodsMemberList = data.goodsmemberlist;
                        this.optionInfo.optionItemList = data.optionitemlist;
                        this.optionInfo.optionList = data.optionlist;
                        setTimeout(function () {
                            this.optionInfo.optionItemList.forEach((item, itemIdx) => {
                                this.optionInfo.optionList.forEach((option, optIdx) => {
                                    this.$refs['optionnm'+ item.sort + '_' + optIdx][0].value = option['optionnm'+(itemIdx+1)];
                                    this.$refs['optionno'+ item.sort + '_' + optIdx][0].value = option['optionno'+(itemIdx+1)];
                                });
                            });
                        }.bind(this), 50);
                        this.deliveryInfo.delivTempList = data.delivtemplist;
                        this.notifyInfo.kccertlist = data.kccertlist;
                        // 첫행 추가
                        if (this.notifyInfo.kccertlist.length == 0) {
                            let param = { iscert: '' };
                            this.notifyInfo.kccertlist.splice(0, 0, param);
                        }
                        this.notifyInfo.notifyTempList = data.notifytemplist;
                        this.notifyInfo.notifyTempItemList = data.notifytempitemlist;
                        if (this.notifyInfo.notifyTempItemList.length > 0) {
                            this.notifyInfo.notifytplidx = this.notifyInfo.notifyTempItemList[0].notifytplidx;
                            this.notifyInfo.notifytpltitle = this.notifyInfo.notifyTempItemList[0].notifytpltitle;
                        }
                        this.etcInfo.goodsGrpList = data.goodsgrplist;
                        this.etcInfo.goodsAdditionList = data.goodsadditionlist;
                        this.etcInfo.goodsMemoList = data.goodsmemolist;
                        if (this.etcInfo.goodsMemoList.length > 0) {
                            this.etcInfo.isshowmemo = true;
                        }
                        this.files = data.files;
                        
                        if (!this.$util.isNull(data.goodscontent)) {
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
                        }
                        
                        // 상품정보 세팅
                        for(const [key] of Object.entries(data.basicinfo)) {
                            this.basicInfo[key] = data.basicinfo[key];
                            if (key == 'muappchtype') {
                                this.basicInfo.muappchtypeArr =  this.basicInfo[key].split(',');
                            }
                        }
                        this.basicInfo.orgistempsave = this.basicInfo.istempsave;
                        this.basicInfo.orggoodsapprtype = this.basicInfo.goodsapprtype;
                        for(const [key] of Object.entries(data.sellinfo)) {
                            this.sellInfo[key] = data.sellinfo[key];
                            if (key == 'mumemlvtype') {
                                this.sellInfo.mumemlvtypeArr =  this.sellInfo[key].split(',');
                            }
                        }
                        for(const [key] of Object.entries(data.delivinfo)) {
                            this.deliveryInfo[key] = data.delivinfo[key];
                        }
                        for(const [key] of Object.entries(data.notifyinfo)) {
                            this.notifyInfo[key] = data.notifyinfo[key];
                        }
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

        // [기본정보] ////////////////////////////////////////////////////////////////////
        // 파트너사 목록 조회
        getPartnerList: function(ispbgoods) {
            // 위탁인 경우만 조회
            if (ispbgoods === 'F') {
                let param = this.basicInfo;
                this.$http.post('/admin/goods/regist/partner/list', param)
                    .then(result => {
                        this.$util.debug(result);
                        this.basicInfo.partnerList = result.data.list;
                        if (this.isPartner) {
                            // 파트너사 수수료율 초기화
                            this.basicInfo.partnerList.forEach(item => {
                                if (item.no === this.basicInfo.dealerno) {
                                    this.optionInfo.commrate = item.commrate===''? null:item.commrate;
                                    this.optionInfo.optionList.forEach(option => {
                                        option.commrate = item.commrate;
                                    });
                                }
                            });
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            } else {
                this.basicInfo.partnerList = [];
                this.basicInfo.dealerno = null;
            }
        },
        // 카테고리분류 목록 조회
        getCategoryCodeList: function(obj) {
            if (obj.depth < 4) {
                // 하위 카테고리 목록 초기화
                for (let i=parseInt(obj.depth); i<4; i++) {
                    let listName = 'depth'+ (i+1) +'list';
                    this.basicInfo.categoryObj[listName] = [];
                    let categoryName = 'depth'+ (i+1) +'Category';
                    this.basicInfo[categoryName].idx = '';
                    this.basicInfo[categoryName].value = '';
                }
                // 하위카테고리 목록 조회
                obj.isloading = false;
                this.$http.post('/admin/goods/regist/cate/list', obj)
                    .then(result => {
                        this.$util.debug(result);
                        let categoryList = result.data.list;

                        // 카테고리 목록 세팅
                        if (obj.depth == 0) {
                            this.basicInfo.categoryObj.depth1list = categoryList;
                        } else if (obj.depth > 0 && !this.$util.isNull(obj.idx)) {
                            let targetDepth = parseInt(obj.depth) +1;
                            let targetListName = 'depth'+ targetDepth +'list';
                            this.basicInfo.categoryObj[targetListName] = categoryList;
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            } else {
                this.basicInfo.depth4Category.idx = obj.idx;
            }
        },
        // 카테고리분류 정보 셋팅
        setCategoryCodeInfo: function(index, idx) {
            let targetName = 'depth' + index + 'Category';
            this.basicInfo[targetName].idx = idx;
            this.basicInfo.categoryObj['depth' + index + 'list'].forEach(item => {
                if (item.idx == idx) {
                    this.basicInfo[targetName].value = item.value;
                }
            });
            
            let params = {depth: index, idx: idx};
            this.getCategoryCodeList(params);
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
                let listName = 'depth' + (i+1) + 'list';
                let categoryName = 'depth' + (i+1) + 'Category';
                let categoryObj = this.basicInfo[categoryName];

                // 하위 카테고리까지 선택여부 체크
                if (this.basicInfo.categoryObj[listName].length > 0 && this.$util.isNull(categoryObj.idx)) {
                    alert(categoryObj.category + ' 카테고리를 선택해주세요.');
                    return;
                }
                // 카테고리명 세팅
                if (i == 0) {
                    fullCategoryName = fullCategoryName.concat(categoryObj.value);
                } else {
                    if (this.basicInfo.categoryObj[listName].length > 0 && !this.$util.isNull(categoryObj.idx)) {
                        fullCategoryName = fullCategoryName.concat(' > ', categoryObj.value);
                    }
                }
                // 최종선택 카테고리 세팅
                if (this.basicInfo.categoryObj[listName].length > 0 && !this.$util.isNull(categoryObj.idx)) {
                    cateidx = categoryObj.idx;
                }
            }

            let isrepreExist = false;
            for (let i=0; i<this.basicInfo.goodsCategoryList.length; i++) {
                let categoryObj = this.basicInfo.goodsCategoryList[i];
                // 대표여부 확인
                if (categoryObj.isrepre) {
                    isrepreExist = true;
                }
                // 카테고리 중복체크
                if (cateidx == categoryObj.cateidx) {
                    alert('이미 추가된 카테고리 입니다.');
                    return;
                }
            }

            // 카테고리 목록에 추가
            let params = {
                isselected: this.basicInfo.goodsCategoryList.length===0? 'T':'F',
                isrepre: isrepreExist? 'F' : 'T',
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
            let isrepreCnt = this.basicInfo.goodsCategoryList.filter(item => {
                return item.isrepre == 'T';
            }).length;
            if (isrepreCnt == 0 && this.basicInfo.goodsCategoryList.length > 0) {
                this.basicInfo.goodsCategoryList[0].isrepre = 'T';
            }
            this.chgInfo.chgCate = 'T';
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
                let goodsCateCnt = this.basicInfo.goodsCategoryList.length;
                if (catetemp.ischecked ) {
                    let existCnt = this.basicInfo.goodsCategoryList.filter(goodscate => {
                        return goodscate.cateidx == catetemp.cateidx;
                    }).length;
                    if (existCnt == 0) {
                        params = {
                            isstar: 'T',
                            isselected: goodsCateCnt==0? 'T' : 'F',
                            isrepre: goodsCateCnt==0? 'T' : 'F',
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
                // vue 관련 오류로 초기화 클릭 후 가끔 초기화가 안되는 오류로 인해 수정
                // 22.06.07 이강원
                this.$set(item, 'ischecked', false);
            });
        },
        // 카테고리 정보 셋팅
        setCategoryInfo: function(obj) {
            this.closeCatenameLayer();
            this.basicInfo.categoryname = obj.fullcategoryname;
            this.basicInfo.categoryidx = obj.cateidx;

            this.basicInfo.depth1Category.idx = obj.depth1idx;
            this.basicInfo.depth1Category.value = obj.depth1name;
            this.setCategoryCodeInfo('1', obj.depth1idx);
            this.basicInfo.depth2Category.idx = obj.depth2idx;
            this.basicInfo.depth2Category.value = obj.depth2name;
            this.setCategoryCodeInfo('2', obj.depth2idx);
            this.basicInfo.depth3Category.idx = obj.depth3idx;
            this.basicInfo.depth3Category.value = obj.depth3name;
            this.setCategoryCodeInfo('3', obj.depth3idx);
            this.basicInfo.depth4Category.idx = obj.depth4idx;
            this.basicInfo.depth4Category.value = obj.depth4name;
            this.setCategoryCodeInfo('4', obj.depth4idx);
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
        // 대표카테고리 선택
        setIsrepreCategory: function(obj) {
            this.basicInfo.goodsCategoryList.forEach(item => {
                item.isrepre = 'F';
            });
            obj.isrepre = 'T';
        },
        // 선택카테고리 표시
        selectCategory: function(obj) {
            this.basicInfo.goodsCategoryList.forEach(item => {
                item.isselected = 'F';
            })
            obj.isselected = 'T';
        },
        // 담당MD 조회 show/hide
        onChargemdShow: function() {
            if (this.isPartner) {
                this.getChargemdList();
            } else {
                if (!this.showInfo.ismdshow) {
                    this.getChargemdList();
                } else {
                    this.basicInfo.chargemdList = [];
                    this.showInfo.ismdshow = false;
                    this.sortData.chargemd = {
                        psort: 'mdsabun_asc',
                        mdsabun: 'mdsabun_asc',
                        empname: 'empname_asc',
                        mdcode: 'mdcode_asc',
                        mdname: 'mdname_asc'
                    };
                }
            }
        },
        // 담당MD 목록 조회
        getChargemdList: function() {
            let param = { psort: this.sortData.chargemd.psort };
            param.mdsword = this.basicInfo.mdsword;
            param.isdp = this.basicInfo.ispbgoods == 'T'? '':'F';
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
            this.basicInfo.mdifidx = obj.mdifidx;
            this.basicInfo.mdsabun = obj.mdsabun;
            this.basicInfo.empname = obj.empname;
            this.basicInfo.mdsword = '';
            setTimeout(function () {
                this.basicInfo.chargemdList = [];
                this.showInfo.ismdshow = false;
            }.bind(this), 50);
        },
        // 브랜드조회 팝업
        openSearchBrandPopup: function() {
            this.basicInfo.isnobrand = 'F';
            let param = { brandname: this.basicInfo.brandname };
            this.$eventBus.$emit('modalShow', SearchBrandListPopup, param,
                (result) => {
                    this.basicInfo.brandidx = result.idx;
                    this.basicInfo.brandname = result.brandname;
                }
            );
        },
        // 브랜드정보 초기화
        initBrandInfo: function() {
            this.basicInfo.brandidx = '';
            this.basicInfo.brandname = '';
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

        // [판매정보] ////////////////////////////////////////////////////////////////////
        // datepicker callback
        pickerChangeEvent(data) {
            this.sellInfo.disstdate = data.fromYYYYMMDD;
            this.sellInfo.dissthour = data.fromHH;
            this.sellInfo.disstmin = data.fromMM;
            this.sellInfo.disstday = data.fromDate12;

            this.sellInfo.diseddate = data.toYYYYMMDD;
            this.sellInfo.disedhour = data.toHH;
            this.sellInfo.disedmin = data.toMM;
            this.sellInfo.disedday = data.toDate12;
        },
        // 판매대상-등급별 전체체크
        checkAllMemlvtype: function() {
            let isAllCheck = this.sellInfo.isallmemlv;
            this.sellInfo.mumemlvtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.memlvtype){
                    this.sellInfo.mumemlvtypeArr.push(type.cmcode);
                }
            }
        },
        // 특정회원 추가 팝업
        openAddUserPopup: function() {
            this.$eventBus.$emit('modalShow', CommonAddUserPopup, null,
                (result) => {
                    // 팝업에서 가져온 결과 상품별 회원목록에 적용(이미 추가되어 있는 회원 제외)
                    let resultList = result.list;
                    for (let i=0; i<resultList.length; i++) {
                        resultList[i].ischecked = false;
                        let existCnt = this.sellInfo.goodsMemberList.filter(member => {
                            return member.userno == resultList[i].userno;
                        }).length;
                        if (existCnt == 0) {
                            this.sellInfo.goodsMemberList.push(resultList[i]);
                        }
                    }
                    this.chgInfo.chgSpUser = 'T';
                }
            );
        },
        // 특정회원 삭제
        removeUser: function() {
            let targetList = this.sellInfo.goodsMemberList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (targetList.length == 0) {
                alert('삭제할 특정회원을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            targetList.forEach(item => {
                let findIndex = this.sellInfo.goodsMemberList.indexOf(item);
                this.sellInfo.goodsMemberList.splice(findIndex, 1);
            });
            // 삭제후 목록이 없는경우 전체체크 해제
            if (this.sellInfo.goodsMemberList.length == 0) {
                this.sellInfo.isallchkmem = 'F';
            }
            this.chgInfo.chgSpUser = 'T';
        },
        // 특정회원목록 전체체크
        checkAllMemberList: function(value) {
            this.sellInfo.goodsMemberList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 특정회원목록 개별체크
        checkMemberList: function() {
            let checkedList = this.sellInfo.goodsMemberList.filter(item => {
                return item.ischecked == true;
            });

            if (this.sellInfo.goodsMemberList.length > checkedList.length){
                this.sellInfo.isallchkmem = 'F';
            } else {
                this.sellInfo.isallchkmem = 'T';
            }
        },

        // [옵션정보] ////////////////////////////////////////////////////////////////////
        // 엑셀양식다운로드
        downloadExcelTemplate: function(filename) {
            let params = { filename: filename }   // 서버에 저장되어있는 파일명
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/common/excel/download', params, config);
        },
        // 옵션전시여부 세팅
        setOptDisplay: function(value) {
            let checkedList = this.optionInfo.optionList.filter(item => {
                return item.ischecked == true;
            });
            if (checkedList.length == 0) {
                alert('옵션상태를 변경할 항목을 선택해주세요.');
                return;
            }
            this.optionInfo.optionList.forEach(item => {
                if (item.ischecked) {
                    item.isoptdisplay = value;
                }
            });
            this.chgInfo.chgOpt = 'T';
        },
        // 옵션 개별 전시여부 변경
        changeOptDisplay: function(obj) {
            if (obj.isoptdisplay == 'F') {
                obj.isoptdisplay = 'T';
            } else {
                obj.isoptdisplay = 'F';
            }
            this.chgInfo.chgOpt = 'T';
        },
        // 옵션 검색컬러 일괄지정
        setAllColorType: function() {
            if (this.$util.isNull(this.optionInfo.totcolortype)) {
                alert('검색컬러를 선택해주세요.');
                return;
            }
            if (this.optionInfo.optionList.length === 0) {
                alert('옵션추가 후 진행해주세요.');
                return;
            }
            let checkedCnt = this.optionInfo.optionList.filter(item => {
                return item.ischecked == true;
            }).length;
            if (checkedCnt === 0) {
                alert('적용할 옵션을 선택해주세요.');
                return;
            }
            this.optionInfo.optionList.forEach(item=> {
                if (item.ischecked) {
                    item.colortype = this.optionInfo.totcolortype;
                }
            });
            this.chgInfo.chgOpt = 'T';
        },
        // 옵션목록 전체체크
        checkAllOptionList: function(value) {
            this.optionInfo.optionList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 옵션목록 개별체크
        checkOptionList: function() {
            let checkedList = this.optionInfo.optionList.filter(item => {
                return item.ischecked == true;
            });
            if (this.optionInfo.optionList.length > checkedList.length){
                this.optionInfo.isallchkopt = 'F';
            } else {
                this.optionInfo.isallchkopt = 'T';
            }
        },
        // 옵션행추가
        addOption: function() {
            let ispbgoods = this.basicInfo.ispbgoods;
            let maingoodsCnt = this.optionInfo.optionList.filter(item => {
                return item.ismaingoods === 'T';
            }).length;
            let addItem = {
                status: 'I',
                ischecked: false,
                ismaingoods: maingoodsCnt>0 ? 'F':'T',
                isoptdisplay: 'T',
                linkedcnt: 0,
                colortype: null, // this.optionInfo.totcolortype,
                safestockcnt: ispbgoods=='T'? 10: 0,
                commrate: this.$util.isNull(this.basicInfo.dealerno)? null : this.optionInfo.commrate
            };
            this.optionInfo.optionList.splice(0, 0, addItem);

            // 입력한 옵션항목 세팅
            setTimeout(function () {
                this.optionInfo.optionItemList.forEach(item => {
                    for (let i=this.optionInfo.optionList.length; i>1; i--){
                        let postkey = item.sort + '_' + (i-2);
                        let nextkey = item.sort + '_' + (i-1);
                        this.$refs['optionnm'+nextkey][0].value = this.$refs['optionnm'+postkey][0].value;
                        this.$refs['optionno'+nextkey][0].value = this.$refs['optionno'+postkey][0].value;
                    }
                    let key = item.sort + '_0';
                    this.$refs['optionnm'+key][0].value = '';
                    this.$refs['optionno'+key][0].value = '';
                });
            }.bind(this), 50); 
            this.chgInfo.chgOpt = 'T';
        },
        // 옵션행삭제
        removeOption: function() {
            let targetList = this.optionInfo.optionList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (targetList.length == 0) {
                alert('삭제할 옵션을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            for (let i=0; i<targetList.length; i++) {
                let findIndex = this.optionInfo.optionList.indexOf(targetList[i]);
                this.optionInfo.optionItemList.forEach(item => {
                    if (findIndex == this.optionInfo.optionList.length-1) {
                        let key = item.sort + '_' + findIndex;
                        this.$refs['optionnm'+key][0].value = '';
                        this.$refs['optionno'+key][0].value = '';
                    } else {
                        for (let j=findIndex; j<this.optionInfo.optionList.length-1; j++){
                            let postkey = item.sort + '_' + j;
                            let nextkey = item.sort + '_' + (j+1);
                            this.$refs['optionnm'+postkey][0].value = this.$refs['optionnm'+nextkey][0].value;
                            this.$refs['optionno'+postkey][0].value = this.$refs['optionno'+nextkey][0].value;
                        }
                    }
                });
                if (this.optionInfo.optionList[findIndex].status === 'N') {
                    this.optionInfo.delOptioncodeList.push(this.optionInfo.optionList[findIndex].optioncode);
                }
                
                this.optionInfo.optionList.splice(findIndex, 1);
            }
            // 삭제후 목록이 없는경우 전체체크 해제
            if (this.optionInfo.optionList.length == 0) {
                this.optionInfo.isallchkopt = 'F';
                this.sellInfo.marketprice = null;
                this.sellInfo.price = null;
            } else {
                // 삭제후 대표상품이 없는경우 첫행 세팅
                let mainGoodsCnt = this.optionInfo.optionList.filter(item => {
                    return item.ismaingoods === 'T';
                }).length;
                if (mainGoodsCnt === 0) {
                    this.setIsMainGoods(this.optionInfo.optionList[0]);
                }
            }
            this.chgInfo.chgOpt = 'T';
        },
        // 대표상품 셋팅
        setIsMainGoods: function(obj) {
            this.optionInfo.optionList.forEach(item => {
                item.ismaingoods = 'F';
            });
            obj.ismaingoods = 'T';
            this.sellInfo.marketprice = obj.marketprice;
            this.sellInfo.price = obj.price;
            this.chgInfo.chgOpt = 'T';
        },
        // 정상가 변경
        changeMarketprice: function(obj) {
            let marketprice = obj.marketprice+'';
            obj.marketprice = marketprice.replace(/(^0|[^0-9])/gi, '');
            if (obj.ismaingoods == 'T') {
                this.sellInfo.marketprice = obj.marketprice;
            }
            this.chgInfo.chgOpt = 'T';
        },
        // 판매가 변경
        changePrice: function(obj) {
            let price = obj.price+'';
            obj.price = price.replace(/(^0|[^0-9])/gi, '');
            if (obj.ismaingoods == 'T') {
                this.sellInfo.price = obj.price;
            }
            if (this.basicInfo.ispbgoods === 'T') {
                this.calulateMarginrate(obj);
            }
            this.chgInfo.chgOpt = 'T';
        },
        // 마진율 계산
        calulateMarginrate: function(obj) {
            let originalprice = parseInt(obj.originalprice);
            let price = parseInt(obj.price);
            let marginrate = 0;
            if (this.$util.isNumber(originalprice) && this.$util.isNumber(price) 
                && !this.$util.isNull(originalprice) && !this.$util.isNull(price)){
                if (price > 0) {
                    marginrate = (price - (originalprice * 1.1))/price * 100;
                }
            }
            obj.marginrate = Math.round(marginrate*10) / 10;
            return obj.marginrate;
        },
        // 항목관리팝업 오픈
        openOtionItemPopup: function() {
            let param = {
                goodsno: this.basicInfo.goodsno,
                optionItemList: this.$util.deepClone(this.optionInfo.optionItemList)
            };
            this.$eventBus.$emit('modalShow', OptionItemManagePopup, param,
                (result) => {
                    if (result.list.length === 1 && result.list[0].status==='I' && this.$util.isNull(result.list[0].optionname)) {
                        result.list.splice(0, 1);
                    }
                    this.optionInfo.optionItemList = result.list;
                    this.optionInfo.delOptionnoList = result.dellist;
                    // 항목 리스트에 매핑
                    this.optionInfo.optionItemList.forEach((item, itemIdx) => {
                        this.optionInfo.optionList.forEach((option, optIdx) => {
                            option.optionnm1 = '';
                            option.optionno1 = '';
                            option.optionnm2 = '';
                            option.optionno2 = '';
                            option.optionnm3 = '';
                            option.optionno3 = '';
                            option.optionnm4 = '';
                            option.optionno4 = '';
                            if (item.status != 'I') {
                                option['optionnm'+(itemIdx+1)] = this.$refs['optionnm'+item.sort+'_'+optIdx][0].value;
                                option['optionno'+(itemIdx+1)] = this.$refs['optionno'+item.sort+'_'+optIdx][0].value;
                            }
                        });
                    });
                    this.chgInfo.chgOptItem = 'T';
                }
            );
        },
        // ERP 오리지널코드조회 팝업
        openSearchOrgCodePopup: function(obj) {
            let param = { 
                goodsno: this.basicInfo.goodsno, 
                goodsname: this.basicInfo.goodsname
                     + (this.$util.isNull(obj.optionnm1)? '': ' '+ obj.optionnm1)
                     + (this.$util.isNull(obj.optionnm2)? '': '|'+ obj.optionnm2)
                     + (this.$util.isNull(obj.optionnm3)? '': '|'+ obj.optionnm3)
                     + (this.$util.isNull(obj.optionnm4)? '': '|'+ obj.optionnm4),
                optioncode: obj.optioncode,
                // 객체를 그대로 전달하는 경우 erp팝업에서 삭제하고 저장이 아닌 닫기를 눌러도
                // 부모의 상품연결도 삭제되는 경우때문에 깊은복사로 객체전달
                // 22.06.07 이강원
                linkedList: this.$util.isNull(obj.linkedlist)? []: this.$util.deepClone(obj.linkedlist)
            };
            this.$eventBus.$emit('modalShow', SearchOriginalCodePopup, param,
                (result) => {
                    obj.linkedcnt = result.list.length;
                    obj.linkedlist = result.list;
                    
                    let originalprice = 0;
                    obj.linkedlist.forEach(item => {
                        originalprice = originalprice + Number(item.originalprice);
                    });
                    obj.originalprice = originalprice;

                    this.calulateMarginrate(obj);
                    this.chgInfo.chgErpOpt = 'T';
                }
            );
        },

        // [이미지정보] //////////////////////////////////////////////////////////////////
        // PC 대표이미지와 동일하게 세팅
        setSameAsPcepreImg: function() {
            if (this.imageInfo.issamepcimg) {
                if (!this.$util.isNull(this.files.morepreimgfile) && this.files.morepreimgfile.status=='N') {
                    this.deletefilelist.push(this.files.morepreimgfile);
                }
                if (!this.$util.isNull(this.files.pcrepreimgfile)) {
                    this.files.morepreimgfile = Object.assign({}, this.files.pcrepreimgfile);
                    if (this.files.morepreimgfile.status=='N') {
                        this.imageInfo.sameimgidx = this.files.morepreimgfile.idx;
                    } else {
                        this.imgPreview.morepreimgfile = this.imgPreview.pcrepreimgfile;
                    }
                }
            } else {
                this.imageInfo.sameimgidx = '';
                this.files.morepreimgfile = null;
                this.imgPreview.morepreimgfile = null;
            }
        },
        // PC 상품상세설명과 동일하게 세팅
        setSameAsPcDetailContrent: function() {
            // if (this.imageInfo.issamepccont) {
                this.$refs.mobileeditor.content = this.$refs.pceditor.content;
            // }
        },
        // 아이콘설정 전체적용 체크
        checkAllGicontype: function() {
            let isAllCheck = this.imageInfo.isallgicontype;
            this.imageInfo.mugicontypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.gicontype){
                    this.imageInfo.mugicontypeArr.push(type.cmcode);
                }
            }
        },

        // [배송정보] ////////////////////////////////////////////////////////////////////
        // 배송템플릿 목록 조회
        getDelivTempList: function() {
            let param = { istrash: 'F', dealerno: this.basicInfo.dealerno, isloading: false };
            this.$http.post('/admin/goods/regist/delivtemp/list', param)
                .then(result => {
                    this.$util.debug(result);
                    this.deliveryInfo.delivTempList = result.data.list;
                    this.deliveryInfo.delivTemp = {};
                    this.deliveryInfo.delivTempList.forEach(item => {
                        if (item.delividx == this.deliveryInfo.delividx) {
                            this.deliveryInfo.delivTemp = item;
                            return;
                        }
                    });
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 배송템플릿 팝업 오픈
        openDelivTempListPopup: function() {
            let param = {dealerno: this.basicInfo.dealerno, ispbgoods: this.basicInfo.ispbgoods};
            this.$eventBus.$emit('modalShow', DelivTempListPopup, param,
                () => {
                    this.getDelivTempList();
                }
            );
        },

        // [상품정보고시] ////////////////////////////////////////////////////////////////
        // 정보고시템플릿목록 조회
        getNotifyTempList: function() {
            this.$http.post('/admin/goods/regist/notifytpl/list', { isloading: false })
                .then(result => {
                    this.$util.debug(result);
                    this.notifyInfo.notifyTempList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 정보고시템플릿항목 조회
        getNotifyTempItemList: function() {
            let params = { notifytplidx: this.notifyInfo.notifytplidx };
            this.$http.post('/admin/goods/regist/notifytplitem/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.notifyInfo.notifyTempItemList = result.data.list;
                    if (this.notifyInfo.notifyTempItemList.length > 0) {
                        this.notifyInfo.notifytplidx = this.notifyInfo.notifyTempItemList[0].notifytplidx;
                        this.notifyInfo.notifytpltitle = this.notifyInfo.notifyTempItemList[0].notifytpltitle;
                    }
                    this.chgInfo.chgNotify = 'T';
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 전체 '상품상세설명 참고'로 표기
        setAllRefDetail: function(value, isreset) {
            isreset = this.$util.isNull(isreset)? true : isreset;
            if (value) {
                this.notifyInfo.notifyTempItemList.forEach(item => {
                    item.notifydata = '상품상세설명 참고';
                });
                this.chgInfo.chgNotify = 'T';
            } else {
                if (isreset) {
                    this.notifyInfo.notifyTempItemList.forEach(item => {
                        item.notifydata = '';
                    });
                }
            }
            this.notifyInfo.chkRef = value;
        },
        // 상품정보고시항목 추가
        addNotifyItem: function(index) {
            this.notifyInfo.notifyTempItemList.splice(index+1, 0, {});
        },
        // 상품정보고시항목 삭제
        removeNotifyItem: function(index) {
            this.notifyInfo.notifyTempItemList.splice(index, 1);
        },
        // KC 인증확인
        kcCertification: function(obj) {
            if (this.$util.isNull(obj.kccertno)) {
                alert('인증번호를 입력해주세요.');
                return;
            }
            this.$http.post('/admin/goods/regist/kc/certification', obj)
                .then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == '200') {
                        if (result.data.code == 'success') {
                            obj.iscert = 'T';
                        } else {
                            obj.iscert = 'F';
                        }
                    }
                    this.chgInfo.chgKccert = 'T';
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // KC 인증목록 추가
        addKccertItem: function(index) {
            let params = { certno: '', iscert: '' };
            this.notifyInfo.kccertlist.splice(index+1, 0, params);
            this.chgInfo.chgKccert = 'T';
        },
        // KC 인증목록 삭제
        removeKccertItem: function(index) {
            this.notifyInfo.kccertlist.splice(index, 1);
            this.chgInfo.chgKccert = 'T';
        },

        // [추가정보] ////////////////////////////////////////////////////////////////////
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
        // 추가상품 팝업 오픈
        openGoodsAdditionPopup: function(type) {
            let targetList = [];
            if (type == 'review') {
                targetList = this.etcInfo.goodsGrpList;
                this.chgInfo.chgGoodsGrp = 'T';
            } else if (type == 'addgoods') {
                targetList = this.etcInfo.goodsAdditionList;
                this.chgInfo.chgGoodsAdd = 'T';
            }
            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, null,
                (result) => {
                    // 팝업에서 가져온 결과 추가상품 목록에 적용(이미 추가되어 있는 상품 제외)
                    let resultList = result.list;
                    for (let i=0; i<resultList.length; i++) {
                        let existCnt = targetList.filter(item => {
                            return item.goodsno == resultList[i].goodsno;
                        }).length;
                        if (existCnt == 0) {
                            resultList[i].ischecked = false;
                            targetList.push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 상품 삭제
        removeGoodsAddition: function(type) {
            let targetList = [];
            let allchkname = '';
            if (type == 'review') {
                targetList = this.etcInfo.goodsGrpList;
                allchkname = 'isallchkgoodsgrp';
                this.chgInfo.chgGoodsGrp = 'T';
            } else if (type == 'addgoods') {
                targetList = this.etcInfo.goodsAdditionList;
                allchkname = 'isallchkgoods';
                this.chgInfo.chgGoodsAdd = 'T';
            }
            let delList = targetList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (delList.length == 0) {
                alert('삭제할 상품을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            delList.forEach(item => {
                let findIndex = targetList.indexOf(item);
                targetList.splice(findIndex, 1);
            });
            if (targetList.length == 0) {
                this.etcInfo[allchkname] = 'F';
            }
        },
        // 추가상품목록 전체체크
        checkAllGoodsAddList: function(value) {
            this.etcInfo.goodsAdditionList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 추가상품목록 개별체크
        checkGoodsAddList: function() {
            let checkedList = this.etcInfo.goodsAdditionList.filter(item => {
                return item.ischecked == true;
            });
            if (this.etcInfo.goodsAdditionList.length > checkedList.length){
                this.etcInfo.isallchkgoods = 'F';
            } else {
                this.etcInfo.isallchkgoods = 'T';
            }
        },
        // 리뷰묶어보기 상품목록 전체체크
        checkAllGoodsGrpList: function(value) {
            this.etcInfo.goodsGrpList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 리뷰묶어보기 상품목록 개별체크
        checkGoodsGrpList: function() {
            let checkedList = this.etcInfo.goodsGrpList.filter(item => {
                return item.ischecked == true;
            });
            if (this.etcInfo.goodsGrpList.length > checkedList.length){
                this.etcInfo.isallchkgoodsgrp = 'F';
            } else {
                this.etcInfo.isallchkgoodsgrp = 'T';
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
            this.reloadRowno(this.etcInfo.goodsMemoList);
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
            this.reloadRowno(this.etcInfo.goodsMemoList);
            this.chgInfo.chgMemo = 'T';
        },
        // RowNo 재조회
        reloadRowno: function(targetList) {
            let rowno = 1;
            for (let i=0; i<targetList.length; i++) {
                let target = targetList[i];
                if (target.istrash == 'F') {
                    target.no = rowno;
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
        viewKcFile: function(obj, index) {
            let url = '';
            if (obj.isimage) {
                if (obj.status=='N') {
                    url = obj.fullpath;
                } else {
                    url = this.imgPreview.kcfilelist[index];
                }
                this.viewFile(url);
            }
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
            if (fileTypeKey.indexOf('kcFile') > -1) {
                // KC 첨부파일
                let files = target.files;
                if (this.files.kcfilelist.length >= 5) {
                    alert('KC인증 파일은 5개까지 첨부 가능합니다.');
                    return;
                }
                let fileObj = {
                    file: files[0],
                    status: 'I',
                    isimage: false
                }
                let fileType = ['image/png','image/jpeg', 'image/png'];
                if(fileType.includes(fileObj.file.type)){
                    fileObj.isimage = true;
                }
                this.imgPreview.kcfilelist[this.files.kcfilelist.length] = URL.createObjectURL(fileObj.file);
                this.files.kcfilelist.push(fileObj);
                // 초기화
                this.$refs.kcFile.value = '';
                this.chgInfo.chgKcAttach = 'T';
            } else if (fileTypeKey.indexOf('changeaddimgfile') > -1) {
                // 추가이미지 변경
                let files = target.files;
                if (this.$util.isNull(files[0])) {
                    return;
                }
                // let fileType = ['image/png','image/jpeg', 'image/png'];
                let extension = ['jpg','jpeg', 'png'];
                let filename = files[0].name;
                let fileExe = this.$util.isNull(filename)? '' : filename.split(".")[1];
                if(!extension.includes(fileExe)){
                    alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                if(files[0].size > 2097152){
                    alert('파일 최대 크기는 2MB를 초과 할 수 없습니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                let fileObj = {
                    file: files[0],
                    status: 'I'
                }
                this.deletefilelist.push(this.files.addimgfilelist[index]);
                this.$set(this.imgPreview.addimgfilelist, index, URL.createObjectURL(fileObj.file));
                this.$set(this.files.addimgfilelist, index, fileObj);
                // 초기화
                this.$refs['changeaddimgfile'+index].value = '';
                this.chgInfo.chgAddImg = 'T';
            } else if (fileTypeKey.indexOf('addimg') > -1) {
                // 추가이미지
                let files = target.files;
                if (this.$util.isNull(files[0])) {
                    return;
                }
                // let fileType = ['image/png','image/jpeg', 'image/png'];
                let extension = ['jpg','jpeg', 'png'];
                let filename = files[0].name;
                let fileExe = this.$util.isNull(filename)? '' : filename.split(".")[1];
                if(!extension.includes(fileExe)){
                    alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                if(files[0].size > 2097152){
                    alert('파일 최대 크기는 2MB를 초과 할 수 없습니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                if (this.files.addimgfilelist.length >= 7) {
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
                // let fileType = ['image/png','image/jpeg', 'image/png'];
                let extension = ['jpg','jpeg', 'png'];
                let filename = file.files[0].name;
                let fileExe = this.$util.isNull(filename)? '' : filename.split(".")[1];
                if(!extension.includes(fileExe)){
                    alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                    file.value = null;
                    return false;
                }
                if(file.files[0].size > 2097152){
                    alert('파일 최대 크기는 2MB를 초과 할 수 없습니다.');
                    file.value = null;
                    return false;
                }
                if (!this.$util.isNull(this.files[fileTypeKey]) && this.files[fileTypeKey].status === 'N' && this.$util.isNull(this.imageInfo.sameimgidx)) {
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
            } else if (fileTypeKey.indexOf('optionExcelFile') > -1) {
                // 엑셀올리기
                let file = this.$refs.optionExcelFile;
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
                this.optionExcelFile = file.files[0];
                this.$refs.optionExcelFileName.value = file.files[0].name;
                this.chgInfo.chgOpt = 'T';
            }
        },
        // 파일삭제
        removeFile(fileTypeKey, index) {
            if (fileTypeKey.indexOf('kcFile') > -1) {
                if (this.files.kcfilelist[index].status == 'N') {
                    this.deletefilelist.push(this.files.kcfilelist[index]);
                }
                this.files.kcfilelist.splice(index, 1);
                this.$refs.kcFile.value = '';
                this.chgInfo.chgKcAttach = 'T';
            } else if (fileTypeKey.indexOf('addimg') > -1) {
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
        // event param -> 특정회원 업로드
        readExcelFile: function(fileTypeKey, event) {
            let file = null;
            let headerInfo = [];
            // 옵션정보 - 직매입
            if (fileTypeKey.indexOf('pbOptionExcelFile') > -1) {
                if (this.$util.isNull(this.optionExcelFile)) {
                    alert('파일을 선택해주세요.');
                    return;
                }
                file = this.optionExcelFile;
                headerInfo = ['marketprice', 'price', 'safestockcnt', 'optionnm1', 'optionnm2', 'optionnm3', 'optionnm4', 'others'];
            }
            // 옵션정보 - 위탁
            else if (fileTypeKey.indexOf('optionExcelFile') > -1) {
                if (this.$util.isNull(this.optionExcelFile)) {
                    alert('파일을 선택해주세요.');
                    return;
                }
                file = this.optionExcelFile;
                headerInfo = ['marketprice', 'price', 'commrate', 'stockcnt', 'optionnm1', 'optionnm2', 'optionnm3', 'optionnm4', 'others'];
            }
            // 판매정보 특정회원
            else if (fileTypeKey.indexOf('spUserExcelFile') > -1) {
                file = event.target.files[0];
                headerInfo = ['userid'];
            }
            let reader = new FileReader();
            let tmpResult = {};
            reader.onload = () => {
                let data = reader.result;
                let workbook = XLSX.read(data, {type: 'array'});
                workbook.SheetNames.forEach((sheetName, index) => {
                    if (index === 0) {
                        const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], {header: headerInfo, defval: ''});
                        if (roa.length) tmpResult = roa ;
                    }
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
        
            // 직매입옵션 일괄등록
            if (fileTypeKey.indexOf('pbOptionExcelFile') > -1) {
                let requiredHeader = ['marketprice', 'price', 'safestockcnt'];
                let requiredHeaderName = ['정상가', '판매가', '안전재고'];
                let addIndex = this.optionInfo.optionList.length;
                let targetList = [];
                
                // 유효성 체크
                let isValid = true;
                if (this.$util.isNull(excelData[0].optionnm1)) {
                    alert('한개 이상의 옵션항목은 필수입니다.');
                    isValid = false;
                    return;
                }
                if (!this.$util.isNull(excelData[0].others)) {
                    alert('항목은 4개까지 입력 가능합니다. 확인 후 진행해주세요.');
                    isValid = false;
                    return;
                } else {
                    // 기존 항목 삭제
                    let headerKeys = Object.keys(excelData[0]);
                    let optionItems = headerKeys.filter(headerKey => {
                        return headerKey.indexOf('optionnm') > -1;
                    });
                    if (optionItems.length > 0) {
                        this.optionInfo.optionItemList.forEach(item => {
                            if(item.status === 'N') {
                                this.optionInfo.delOptionnoList.push(item.optionno);
                            }
                        });
                        this.optionInfo.optionItemList = [];
                    }
                    // 항목 추가
                    optionItems.forEach((item, index) => {
                        let params = {
                            status: 'I',
                            sort: index + 1,
                            optionname: excelData[0][item]
                        }
                        this.optionInfo.optionItemList.push(params);
                    });
                }

                for (let i=1; i<excelData.length; i++) {
                    let item = excelData[i];
                    let keyset = Object.keys(item);
                    // 필수입력항목 체크
                    for (let j=0; j<keyset.length; j++) {
                        let key = keyset[j];
                        if (requiredHeader.indexOf(key)>-1 && this.$util.isNull(item[key])) {
                            alert("필수입력항목(" + requiredHeaderName[j] + ") 입력되지 않았습니다. 확인 후 진행해주세요.");
                            isValid = false;
                            break;
                        }
                        if (key==='optionnm1' && this.$util.isNull(item[key])) {
                            alert("필수입력항목(" + excelData[0].optionnm1 + " 옵션내용) 입력되지 않았습니다. 확인 후 진행해주세요.");
                            isValid = false;
                            break;
                        }
                    }
                    // 정상가는 판매가 이하로 입력 가능
                    let marketprice = parseInt(item.marketprice);
                    let price = parseInt(item.price);
                    if (marketprice < price) {
                        alert("정상가보다 판매가가 높은 데이터가 존재합니다. 확인 후 진행해주세요.");
                        isValid = false;
                        break;
                    }
                    // 상품명 중복체크
                    // for (let j=i+1; j<excelData.length; j++) {
                    //     if (item.goodsname.replace(/ /g,"") == excelData[j].goodsname.replace(/ /g,"")) {
                    //         alert("동일한 상품명이 존재합니다. 확인 후 진행해주세요.");
                    //         isValid = false;
                    //         this.$refs.optionExcelFile.value = '';
                    //         this.$refs.optionExcelFileName.value = '';
                    //         this.optionExcelFile = null;
                    //         break;
                    //     }
                    // }

                    let listcnt = targetList.length;
                    // 목록 추가
                    let additem = {
                        ischecked: false,
                        ismaingoods: listcnt>0 ? 'F':'T',
                        isoptdisplay: 'T',
                        marketprice: item.marketprice,
                        price: item.price,
                        safestockcnt: item.safestockcnt,
                        colortype: null, //item.colortype,
                        optionnm1: item.optionnm1,
                        optionnm2: item.optionnm2,
                        optionnm3: item.optionnm3,
                        optionnm4: item.optionnm4
                    }
                    targetList.push(additem);
                }
                // 옵션항목 내용 세팅
                if (isValid) {
                    this.chgInfo.chgOpt = 'T';
                    targetList.forEach(obj => {
                        this.optionInfo.optionList.push(obj);
                        if (obj.ismaingoods === 'T') {
                            this.setIsMainGoods(obj);
                        }
                    });
                } else {
                    this.$refs.optionExcelFile.value = '';
                    this.$refs.optionExcelFileName.value = '';
                    this.optionExcelFile = null;
                }
                setTimeout(function () {
                    this.optionInfo.optionItemList.forEach((item, itemIdx) => {
                        for (let i=0; i<targetList.length; i++) {
                            let optionnm = targetList[i]['optionnm'+ (itemIdx+1)];
                            let rows = addIndex+i;
                            this.$refs['optionnm'+ item.sort + '_' + rows][0].value = this.$util.isNull(optionnm)? '':optionnm;
                            this.$refs['optionno'+ item.sort + '_' + rows][0].value = item.optionno;
                        }
                    });
                }.bind(this), 50);
                
                this.$refs.optionExcelFile.value = '';
                this.$refs.optionExcelFileName.value = '';
                this.optionExcelFile = null;
            }
            // 위탁옵션 일괄등록
            else if (fileTypeKey.indexOf('optionExcelFile') > -1) {
                let requiredHeader = ['marketprice', 'price', 'commrate', 'stockcnt'];
                let requiredHeaderName = ['정상가', '판매가', '수수료율', '재고'];
                let addIndex = this.optionInfo.optionList.length;
                let targetList = [];
                
                // 유효성 체크
                let isValid = true;
                if (this.$util.isNull(excelData[0].optionnm1)) {
                    alert('한개 이상의 옵션항목은 필수입니다.');
                    isValid = false;
                    return;
                }
                if (!this.$util.isNull(excelData[0].others)) {
                    alert('항목은 4개까지 입력 가능합니다. 확인 후 진행해주세요.');
                    isValid = false;
                    return;
                } else {
                    // 기존 항목 삭제
                    let headerKeys = Object.keys(excelData[0]);
                    let optionItems = headerKeys.filter(headerKey => {
                        return headerKey.indexOf('optionnm') > -1;
                    });
                    if (optionItems.length > 0) {
                        this.optionInfo.optionItemList.forEach(item => {
                            if(item.status === 'N') {
                                this.optionInfo.delOptionnoList.push(item.optionno);
                            }
                        });
                        this.optionInfo.optionItemList = [];
                    }
                    // 항목 추가
                    optionItems.forEach((item, index) => {
                        let params = {
                            status: 'I',
                            sort: index + 1,
                            optionname: excelData[0][item]
                        }
                        this.optionInfo.optionItemList.push(params);
                    });
                }
                
                for (let i=1; i<excelData.length; i++) {
                    let item = excelData[i];
                    let keyset = Object.keys(item);
                    // 필수입력항목 체크
                    for (let j=0; j<keyset.length; j++) {
                        let key = keyset[j];
                        if (requiredHeader.indexOf(key)>-1 && this.$util.isNull(item[key])) {
                            if (key !== 'commrate') {
                                alert("필수입력항목(" + requiredHeaderName[requiredHeader.indexOf(key)] + ") 입력되지 않았습니다. 확인 후 진행해주세요.");
                                isValid = false;
                                break;
                            }
                        }
                        if (key==='optionnm1' && this.$util.isNull(item[key])) {
                            alert("필수입력항목(" + excelData[0].optionnm1 + " 옵션내용) 입력되지 않았습니다. 확인 후 진행해주세요.");
                            isValid = false;
                            break;
                        }
                    }
                    // 정상가는 판매가 이하로 입력 가능
                    let marketprice = parseInt(item.marketprice);
                    let price = parseInt(item.price);
                    if (marketprice < price) {
                        alert("정상가보다 판매가가 높은 데이터가 존재합니다. 확인 후 진행해주세요.");
                        isValid = false;
                        break;
                    }
                    // 상품명 중복체크
                    // for (let j=i+1; j<excelData.length; j++) {
                    //     if (item.goodsname.replace(/ /g,"") == excelData[j].goodsname.replace(/ /g,"")) {
                    //         alert("동일한 상품명이 존재합니다. 확인 후 진행해주세요.");
                    //         isValid = false;
                    //         this.$refs.optionExcelFile.value = '';
                    //         this.$refs.optionExcelFileName.value = '';
                    //         this.optionExcelFile = null;
                    //         break;
                    //     }
                    // }
                    // 재고는 0 이상 입력 가능
                    let stockcnt = parseInt(item.stockcnt);
                    if (stockcnt == 0) {
                        alert("재고가 0인 데이터가 존재합니다. 확인 후 진행해주세요.");
                        isValid = false;
                        break;
                    }

                    let listcnt = targetList.length;
                    // 목록 추가
                    let additem = {
                        ischecked: false,
                        ismaingoods: listcnt>0 ? 'F':'T',
                        isoptdisplay: 'T',
                        marketprice: item.marketprice,
                        price: item.price,
                        commrate: this.isPartner? this.optionInfo.commrate : (this.$util.isNull(item.commrate)? this.optionInfo.commrate : item.commrate),
                        stockcnt: item.stockcnt,
                        colortype: null, //item.colortype,
                        optionnm1: item.optionnm1,
                        optionnm2: item.optionnm2,
                        optionnm3: item.optionnm3,
                        optionnm4: item.optionnm4
                    }
                    targetList.push(additem);
                }
                // 옵션항목 내용 세팅
                if (isValid) {
                    this.chgInfo.chgOpt = 'T';
                    targetList.forEach(obj => {
                        this.optionInfo.optionList.push(obj);
                        if (obj.ismaingoods === 'T') {
                            this.setIsMainGoods(obj);
                        }
                    });
                } else {
                    this.$refs.optionExcelFile.value = '';
                    this.$refs.optionExcelFileName.value = '';
                    this.optionExcelFile = null;
                }
                setTimeout(function () {
                    this.optionInfo.optionItemList.forEach((item, itemIdx) => {
                        for (let i=0; i<targetList.length; i++) {
                            let optionnm = targetList[i]['optionnm'+ (itemIdx+1)];
                            let rows = addIndex+i;
                            this.$refs['optionnm'+ item.sort + '_' + rows][0].value = this.$util.isNull(optionnm)? '':optionnm;
                            this.$refs['optionno'+ item.sort + '_' + rows][0].value = item.optionno;
                        }
                    });
                }.bind(this), 50);

                this.$refs.optionExcelFile.value = '';
                this.$refs.optionExcelFileName.value = '';
                this.optionExcelFile = null;
            }
            // 특정회원
            else if (fileTypeKey.indexOf('spUserExcelFile') > -1) {
                let useridlist = excelData.splice(1, excelData.length); // 헤더여부에 따라 달라짐
                this.$http.post('/admin/goods/regist/userinfo', { useridlist: useridlist })
                    .then(result => {
                        if (result.statusCode == '200') {
                            let resultList = result.data.list;
                            if ( resultList.length == 0) {
                                alert("회원정보가 존재하지않습니다. 입력한 사용자ID를 확인해주세요.");
                            } else {                                
                                // 상품별 회원목록에 적용(이미 추가되어 있는 회원 제외)
                                let resultCnt = 0;
                                for (let i=0; i<resultList.length; i++) {
                                    let existCnt = this.sellInfo.goodsMemberList.filter(member => {
                                        return member.userno == resultList[i].userno;
                                    }).length;
                                    if (existCnt == 0) {
                                        this.sellInfo.goodsMemberList.push(resultList[i]);
                                        resultCnt++;
                                    }
                                }
                                this.chgInfo.chgSpUser = 'T';
                                alert(resultCnt + '건 추가되었습니다.');
                            }
                        }
                        this.$refs.spUserExcelFile.value='';
                    })
                    .catch(error => {
                        this.$util.debug(error);
                        this.$refs.spUserExcelFile.value='';
                    });
            }
        },
        // 정렬
        sortToggle: function(target, key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.sortData = this.$options.data().sortData;

            this.sortData[target][sortKey] = sortName;
            this.sortData[target].psort = sortName;

            // 담당MD 조회
            if (target == 'chargemd') {
                this.basicInfo.chargemdList.sort((a, b) => {
                    a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                    b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                    if (a[sortKey] < b[sortKey]) {
                        return sortOrder == 'asc'? -1: 1;
                    } else if (a[sortKey] > b[sortKey]) {
                        return sortOrder == 'asc'? 1: -1;
                    }
                    return 0;
                });
                // this.getChargemdList();
            }
            // 특정회원 조회
            else if (target == 'goodsMember') {
                this.sellInfo.goodsMemberList.sort((a, b) => {
                    a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                    b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                    if (a[sortKey] < b[sortKey]) {
                        return sortOrder == 'asc'? -1: 1;
                    } else if (a[sortKey] > b[sortKey]) {
                        return sortOrder == 'asc'? 1: -1;
                    }
                    return 0;
                });
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
            if (this.sellInfo.isallmemlv == 'T') {
                this.checkAllMemlvtype();
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

            // 옵션항목 리스트에 매핑
            this.optionInfo.optionItemList.forEach((item, itemIdx) => {
                this.optionInfo.optionList.forEach((option, optIdx) => {
                    option['optionnm'+(itemIdx+1)] = this.$refs['optionnm'+item.sort+'_'+optIdx][0].value;
                    option['optionno'+(itemIdx+1)] = this.$refs['optionno'+item.sort+'_'+optIdx][0].value;
                });
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
            // - 상품명 필수체크
            if (checkResult) {
                valid = [
                    {field: 'basicInfo.goodsname', type: 'I', name:'[기본정보] 상품명', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            // - 브랜드없음 체크 안하면 브랜드입력 필수체크
            if (checkResult && this.basicInfo.isnobrand=='F') {
                valid = [
                    {field: 'basicInfo.brandname', type: 'I', name:'[기본정보] 브랜드', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }

            // (2) 판매정보
            // - 입력 필수체크
            if (checkResult) {
                valid = [
                    {field: 'sellInfo.istaxfree'        , type: 'S', name:'[판매정보] 과세여부', required: true},
                    {field: 'sellInfo.goodsselltype'    , type: 'S', name:'[판매정보] 상품판매상태', required: true},
                    {field: 'sellInfo.disstdate'        , type: 'I', name:'[판매정보] 전시기간 시작일자', required: true},
                    {field: 'sellInfo.dissthour'        , type: 'I', name:'[판매정보] 전시기간 시작시간', required: true},
                    {field: 'sellInfo.disstmin'         , type: 'I', name:'[판매정보] 전시기간 시작분', required: true},
                    {field: 'sellInfo.diseddate'        , type: 'I', name:'[판매정보] 전시기간 종료일자', required: true},
                    {field: 'sellInfo.disedhour'        , type: 'I', name:'[판매정보] 전시기간 종료시간', required: true},
                    {field: 'sellInfo.disedmin'         , type: 'I', name:'[판매정보] 전시기간 종료분', required: true},
                    {field: 'sellInfo.marketprice'      , type: 'I', name:'[판매정보] 정상가', required: true},
                    {field: 'sellInfo.price'            , type: 'I', name:'[판매정보] 판매가', required: true},
                    {field: 'sellInfo.minordcnt'        , type: 'I', name:'[판매정보] 최소주문수량', required: true},
                    {field: 'sellInfo.maxordcnt'        , type: 'I', name:'[판매정보] 최대주문수량', required: true},
                    {field: 'sellInfo.daymaxordcnt'     , type: 'I', name:'[판매정보] 1일최대주문수량', required: true},
                    {field: 'sellInfo.isdisplay'        , type: 'S', name:'[판매정보] 전시여부', required: true},
                    {field: 'sellInfo.isfrstsale'       , type: 'S', name:'[판매정보] 첫구매시할인혜택여부', required: true},
                    {field: 'sellInfo.iscncappr'        , type: 'S', name:'[판매정보] 취소시판매자승인여부', required: true},
                    {field: 'sellInfo.goodsdivtype'     , type: 'S', name:'[판매정보] 상품상태', required: true}
                ];
                if (this.isPartner) {
                    valid[14].required = false;
                    valid[15].required = false;
                }
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            // - 전시기간 체크
            if(checkResult && parseInt(this.sellInfo.disstday) > parseInt(this.sellInfo.disedday)) {
                checkResult = false;
                alert('[판매정보] 전시종료일시를 전시시작일시 이후로 입력하세요.');
            }
            // - 최대수량 최소수량 체크
            if (checkResult) {
                if (Number(this.sellInfo.minordcnt) > Number(this.sellInfo.maxordcnt)) {
                    checkResult = false;
                    alert('[판매정보] 최대주문수량은 최소주문수량 이상이어야 합니다.');
                }
                if (checkResult && Number(this.sellInfo.minordcnt) > Number(this.sellInfo.daymaxordcnt)) {
                    checkResult = false;
                    alert('[판매정보] 1일최대주문수량은 최소주문수량 이상이어야 합니다.');
                }
                if (checkResult && Number(this.sellInfo.minordcnt) > Number(this.sellInfo.permaxordcnt)) {
                    checkResult = false;
                    alert('[판매정보] 1인당최대구매수량은 최소주문수량 이상이어야 합니다.');
                }
            }

            // (3) 배송정보
            // if (checkResult) {
            //     valid = [
            //         {field: 'deliveryInfo.iscombdeliv'  , type: 'S', name:'[배송정보] 배송유형', required: true}
            //     ];
            //     msg = this.$util.validMsg(this.$data, valid);
            //     if(!this.$util.isNull(msg)){
            //         checkResult = false;
            //         alert(msg);
            //     }
            // }

            // (4)추가정보
            // - 리뷰공개여부
            if (checkResult && !this.isPartner) {
                valid = [
                    {field: 'etcInfo.isopenreview'  , type: 'S', name:'[추가정보] 리뷰공개여부', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }

            return checkResult;
        },
        // 저장 유효성체크
        checkSaveVaildation: function() {
            // 1. 저장 데이터 전처리
            this.setPreSave();
            
            let checkResult = true;
            let valid = [];
            let msg = '';

            // (1) 기본정보
            // - 카테고리목록 존재시 대표카테고리 선택여부 체크
            if (checkResult) {
                if (this.basicInfo.goodsCategoryList.length > 0) {
                    let repreCnt = this.basicInfo.goodsCategoryList.filter(item => {
                        return item.isrepre === 'T';
                    }).length;
                    if (repreCnt == 0) {
                        checkResult = false;
                        alert('[기본정보] 대표카테고리를 선택해주세요.');
                    }
                } else {
                    checkResult = false;
                    alert('[기본정보] 카테고리를 한 개 이상 추가해주세요.');
                }
            }
            if (checkResult) {
                // - 상품명 필수체크
                valid = [
                    {field: 'basicInfo.goodsname', type: 'I', name:'[기본정보] 상품명', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            // - 브랜드없음 체크 안하면 브랜드입력 필수체크
            if (checkResult && this.basicInfo.isnobrand=='F') {
                valid = [
                    {field: 'basicInfo.brandname', type: 'I', name:'[기본정보] 브랜드', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            // - 담당MD 필수체크
            if (checkResult) {
                valid = [
                    {field: 'basicInfo.mdifidx', type: 'I', name:'[기본정보] 담당MD', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }

            // (2) 판매정보
            // - 입력 필수체크
            if (checkResult) {
                valid = [
                    {field: 'sellInfo.istaxfree'        , type: 'S', name:'[판매정보] 과세여부', required: true},
                    {field: 'sellInfo.goodsselltype'    , type: 'S', name:'[판매정보] 상품판매상태', required: true},
                    {field: 'sellInfo.disstdate'        , type: 'I', name:'[판매정보] 전시기간 시작일자', required: true},
                    {field: 'sellInfo.dissthour'        , type: 'I', name:'[판매정보] 전시기간 시작시간', required: true},
                    {field: 'sellInfo.disstmin'         , type: 'I', name:'[판매정보] 전시기간 시작분', required: true},
                    {field: 'sellInfo.diseddate'        , type: 'I', name:'[판매정보] 전시기간 종료일자', required: true},
                    {field: 'sellInfo.disedhour'        , type: 'I', name:'[판매정보] 전시기간 종료시간', required: true},
                    {field: 'sellInfo.disedmin'         , type: 'I', name:'[판매정보] 전시기간 종료분', required: true},
                    {field: 'sellInfo.marketprice'      , type: 'I', name:'[판매정보] 정상가', required: true},
                    {field: 'sellInfo.price'            , type: 'I', name:'[판매정보] 판매가', required: true},
                    {field: 'sellInfo.minordcnt'        , type: 'I', name:'[판매정보] 최소주문수량', required: true},
                    {field: 'sellInfo.maxordcnt'        , type: 'I', name:'[판매정보] 최대주문수량', required: true},
                    {field: 'sellInfo.daymaxordcnt'     , type: 'I', name:'[판매정보] 1일최대주문수량', required: true},
                    {field: 'sellInfo.permaxordcnt'     , type: 'I', name:'[판매정보] 1인당최대구매수량', required: true},
                    {field: 'sellInfo.isdisplay'        , type: 'S', name:'[판매정보] 전시여부', required: true},
                    {field: 'sellInfo.isfrstsale'       , type: 'S', name:'[판매정보] 첫구매시할인혜택여부', required: true},
                    {field: 'sellInfo.iscncappr'        , type: 'S', name:'[판매정보] 취소시판매자승인여부', required: true},
                    {field: 'sellInfo.goodsdivtype'     , type: 'S', name:'[판매정보] 상품상태', required: true}
                ];
                if (this.isPartner) {
                    valid[15].required = false;
                    valid[16].required = false;
                }
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            // - 전시기간 체크
            if(checkResult && parseInt(this.sellInfo.disstday) > parseInt(this.sellInfo.disedday)) {
                checkResult = false;
                alert('[판매정보] 전시종료일시를 전시시작일시 이후로 입력하세요.');
            }
            // - 최대수량 최소수량 체크
            if (checkResult && Number(this.sellInfo.minordcnt) > Number(this.sellInfo.maxordcnt)) {
                checkResult = false;
                alert('[판매정보] 최대주문수량은 최소주문수량 이상이어야 합니다.');
            }
            if (checkResult && Number(this.sellInfo.minordcnt) > Number(this.sellInfo.daymaxordcnt)) {
                checkResult = false;
                alert('[판매정보] 1일최대주문수량은 최소주문수량 이상이어야 합니다.');
            }
            if (checkResult && Number(this.sellInfo.minordcnt) > Number(this.sellInfo.permaxordcnt)) {
                checkResult = false;
                alert('[판매정보] 1인당최대구매수량은 최소주문수량 이상이어야 합니다.');
            }
            // - 판매대상 등급별, 특정회원별 선택시 필수체크
            if (checkResult) {
                if (this.sellInfo.selltarget == 'TYPE') {
                    if (this.sellInfo.mumemlvtypeArr.length == 0) {
                        checkResult = false;
                        alert('[판매정보] 판매대상 등급을 체크해주세요.');
                    }
                } else if (this.sellInfo.selltarget == 'SP') {
                    if (this.sellInfo.goodsMemberList.length == 0) {
                        checkResult = false;
                        alert('[판매정보] 특정회원을 추가해주세요.');
                    }
                }
            }

            // (3) 옵션정보
            // - 최소 1개 옵션항목 필수
            if (checkResult && this.optionInfo.optionItemList.length === 0) {
                checkResult = false;
                alert('[옵션정보] 한개 이상의 옵션항목은 필수입니다.');
            }
            // - 최소 1개 옵션 필수
            if (checkResult && this.optionInfo.optionList.length === 0) {
                checkResult = false;
                alert('[옵션정보] 한개 이상의 옵션은 필수입니다.');
            }
            // - 직매입
            if (checkResult && this.basicInfo.ispbgoods === 'T') {
                for (let i=0; i<this.optionInfo.optionList.length; i++) {
                    let item = this.optionInfo.optionList[i];
                    if (this.$util.isNull(item.marketprice)) {
                        checkResult = false;
                        alert('[옵션정보] 정상가는 필수입력 항목입니다.');
                    }
                    if (checkResult && this.$util.isNull(item.price)) {
                        checkResult = false;
                        alert('[옵션정보] 판매가는 필수입력 항목입니다.');
                    }
                    if (checkResult && Number(item.marketprice) < Number(item.price)) {
                        checkResult = false;
                        alert('[옵션정보] 판매가는 정상가보다 낮게 입력해야 합니다.');
                    }
                    // if (checkResult && this.$util.isNull(item.goodsname)) {
                    //     checkResult = false;
                    //     alert('[옵션정보] 상품명은 필수입력 항목입니다.');
                    // }
                    if (checkResult && this.$util.isNull(item.safestockcnt)) {
                        checkResult = false;
                        alert('[옵션정보] 안전재고는 필수입력 항목입니다.');
                    }
                    for (let j=0; j<this.optionInfo.optionItemList.length; j++) {
                        let optionNm = eval("item.optionnm" + (j+1));
                        if (checkResult && this.$util.isNull(optionNm)) {
                            checkResult = false;
                            alert('[옵션정보] \'' + this.optionInfo.optionItemList[j].optionname + '\'의 내용은 필수입력 입니다.');
                        }
                    }
                    if (checkResult && item.ismaingoods==='T' && item.isoptdisplay==='F') {
                        checkResult = false;
                        alert('[옵션정보] 대표상품은 옵션상태가 \'노출\'인 상품만 가능합니다.');
                    }
                    // 옵션 연결 체크
                    if (checkResult && (this.$util.isNull(item.linkedlist) || item.linkedcnt === 0)) {
                        checkResult = false;
                        alert('[옵션정보] EPR상품 연결은 필수입니다.');
                    }
                }
            }
            // - 위탁
            else if (checkResult && this.basicInfo.ispbgoods === 'F') {
                for (let i=0; i<this.optionInfo.optionList.length; i++) {
                    let item = this.optionInfo.optionList[i];
                    if (this.$util.isNull(item.marketprice)) {
                        checkResult = false;
                        alert('[옵션정보] 정상가는 필수입력 항목입니다.');
                    }
                    if (checkResult && this.$util.isNull(item.price)) {
                        checkResult = false;
                        alert('[옵션정보] 판매가는 필수입력 항목입니다.');
                    }
                    if (checkResult && Number(item.marketprice) < Number(item.price)) {
                        checkResult = false;
                        alert('[옵션정보] 판매가는 정상가보다 낮게 입력해야 합니다.');
                    }
                    if (checkResult && this.$util.isNull(item.commrate)) {
                        checkResult = false;
                        alert('[옵션정보] 수수료율 필수입력 항목입니다.');
                    }
                    if (checkResult && this.$util.isNull(item.stockcnt)) {
                        checkResult = false;
                        alert('[옵션정보] 재고는 필수입력 항목입니다.');
                    }
                    
                    for (let j=0; j<this.optionInfo.optionItemList.length; j++) {
                        let optionNm = eval("item.optionnm" + (j+1));
                        if (checkResult && this.$util.isNull(optionNm)) {
                            checkResult = false;
                            alert('[옵션정보] \'' + this.optionInfo.optionItemList[j].optionname + '\'의 내용은 필수입력 입니다.');
                        }
                    }
                    // if (checkResult && this.$util.isNull(item.goodsname)) {
                    //     checkResult = false;
                    //     alert('[옵션정보] 상품명은 필수입력 항목입니다.');
                    // }
                    if (checkResult && item.ismaingoods==='T' && item.isoptdisplay==='F') {
                        checkResult = false;
                        alert('[옵션정보] 대표상품은 옵션상태가 \'노출\'인 상품만 가능합니다.');
                    }
                    
                }
            }

            // (4) 이미지정보
            if (checkResult && this.$util.isNull(this.files.pcrepreimgfile)) {
                checkResult = false;
                alert('[이미지정보] 대표이미지(PC)는 필수입력 항목입니다.');
            }
            if (checkResult && this.$util.isNull(this.files.morepreimgfile)) {
                checkResult = false;
                alert('[이미지정보] 대표이미지(모바일)는 필수입력 항목입니다.');
            }
            if (checkResult && this.imageInfo.isusenotice === 'T' && this.$util.isNull(this.imageInfo.noticecontent)) {
                checkResult = false;
                alert('[이미지정보] 공지이미지를 입력해주세요.');
            }
            if (checkResult && this.imageInfo.isuseintro === 'T' && this.$util.isNull(this.imageInfo.introcontent)) {
                checkResult = false;
                alert('[이미지정보] 인트로이미지를 입력해주세요.');
            }
            if (checkResult && this.$util.isNull(this.imageInfo.pccontent)) {
                checkResult = false;
                alert('[이미지정보] PC용 상품상세설명은 필수입력 항목입니다.');
            }
            if (checkResult && this.$util.isNull(this.imageInfo.mobilecontent)) {
                checkResult = false;
                alert('[이미지정보] 모바일용 상품상세설명은 필수입력 항목입니다.');
            }

            // (5) 배송정보
            if (checkResult && this.$util.isNull(this.deliveryInfo.delividx)) {
                checkResult = false;
                alert('[배송정보] 배송템플릿을 선택해주세요.');
            }
            if (checkResult) {
                valid = [
                    {field: 'deliveryInfo.iscombdeliv'  , type: 'S', name:'[배송정보] 배송유형', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            /*if (checkResult && this.deliveryInfo.delivTemp.delivschtype != 'DSC001' && this.$util.isNull(this.basicInfo.delivinfo)) {
                checkResult = false;
                alert('[배송안내] 배송안내를 입력해주세요.');
            }
            배송안내 필수 입력값 제외(김찬익님 요청, 2022-10-17, James)
            */
            // (6) 상품정보고시
            if (checkResult) {
                valid = [
                    {field: 'notifyInfo.notifytplidx'  , type: 'S', name:'[상품정보고시] 상품군', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
                }
            }
            // - KC인증 대상인경우 필수체크, 적합여부 체크
            if (checkResult) {
                if (this.notifyInfo.kcdivtype == this.$store.getters['ADMIN'].KC_DIV_TYPE_KDT002) {
                    if (this.notifyInfo.kccertlist.length == 0) {
                        checkResult = false;
                        alert('[상품정보고시] KC인증을 추가해주세요.');
                    } else {
                        let chkCnt = this.notifyInfo.kccertlist.filter(item => {
                            return item.iscert != 'T';
                        }).length;
                        if (chkCnt > 0) {
                            checkResult = false;
                            alert('[상품정보고시] KC 인증확인을 진행해주세요.');
                        }
                    }
                }
            }
            
            // (7)추가정보
            if (checkResult) {
                valid = [
                    {field: 'etcInfo.aswarmonth'    , type: 'I', name:'[추가정보] A/S 보증기간', required: true},
                    {field: 'etcInfo.isopenreview'  , type: 'S', name:'[추가정보] 리뷰공개여부', required: true}
                ];
                if (this.isPartner) {
                    valid[1].required = false;
                }
                msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    checkResult = false;
                    alert(msg);
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
                    this.sellInfo.goodsselltype = this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST001   // 상품판매상태(임시저장시 판매대기)
                    if (this.chgInfo.fstTempSave==='T') {
                        this.chgInfo.fstTempSave = '';
                    } else {
                        this.chgInfo.fstTempSave = 'T';
                    }
                    this.save();
                }
            }
        },
        // 저장 (마스터)
        goSave: function() {
            // 저장 유효성체크
            if (this.checkSaveVaildation()) {
                if (confirm('저장 하시겠습니까?')) {
                    this.basicInfo.istempsave = 'F';
                    if (this.$util.isNull(this.basicInfo.goodsno) || (this.basicInfo.orgistempsave === 'T' && this.basicInfo.istempsave === 'F')) {
                        this.chgInfo.fstTempSave = 'F';
                        this.chgInfo.fstSave = 'T';
                    }
                    this.basicInfo.goodsapprtype = this.$store.getters['ADMIN'].GOODS_STATUS_APPROVAL // 상품승인상태(관리자: 승인완료)
                    this.save();
                }
            }
        },
        // 승인요청 (파트너사)
        goReqApprv: function() {
            // 저장 유효성체크
            if (this.checkSaveVaildation()) {
                // 상품 저장
                if (confirm('승인요청 하시겠습니까?')) {
                    if (this.$util.isNull(this.basicInfo.goodsno) || this.basicInfo.istempsave === 'T') {
                        this.chgInfo.fstTempSave = 'F';
                        this.chgInfo.fstSave = 'T';
                    }
                    this.basicInfo.istempsave = 'F';
                    this.basicInfo.goodsapprtype = this.$store.getters['ADMIN'].GOODS_STATUS_REQ;        // 상품승인상태(파트너사: 승인요청)
                    this.basicInfo.contents = '승인요청합니다.';                                          // 파트너사 승인요청시 사유
                    this.sellInfo.goodsselltype = this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST001;   // 상품판매상태(판매대기)
                    this.save();
                }
            }
        },
        // 저장로직
        save: function() {
            // 저장 파라미터 merge
            let params = Object.assign({}, this.basicInfo, this.sellInfo, this.optionInfo, this.imageInfo, this.deliveryInfo, this.notifyInfo, this.etcInfo, this.chgInfo);
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
            for(let i=0; i<this.files.kcfilelist.length; i++) {
                files.push({key: constants['IMG_TYPE_GOODS_KC_CERT']+(i+1), file: this.files.kcfilelist[i].file});
            }
            params.files = files;
            params.deletefilelist = new Set(this.deletefilelist);

            let msg = '저장이 완료되었습니다.';
            if (this.basicInfo.istempsave === 'T') {
                msg = '임시저장이 완료되었습니다.';
            } else if (this.basicInfo.goodsapprtype ===  this.$store.getters['ADMIN'].GOODS_STATUS_REQ) {
                msg = '승인요청이 완료되었습니다.';
            }
            this.$http.post('/admin/goods/regist/goods/save', params)
                .then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == '200') {
                        let data = result.data;
                        this.basicInfo.goodsno = data.goodsno;
                        if (this.basicInfo.istempsave === 'T') {
                            alert(msg);
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
        },
        checkInputRate: function(obj, colName, value) {
            value = value+'';
            if (this.$util.isNull(value)) {
                obj[colName] = value;
            } else {
                var pattern = /^([0-9]*)[/.]?([0-9]{0,1})?$/;
                if (!pattern.test(value)) {
                    value = value.substr(0, value.length-1);
                }
                obj[colName] = value;
            }
        }
    },
    watch: {
        // 판매구분
        'basicInfo.ispbgoods': function(value) {
            if (!this.issearch) {
                // 입점업체정보 초기화
                if (value == 'T') {
                    this.basicInfo.dealerno = null;
                } else {
                    if (!this.isPartner) {
                        this.basicInfo.dealerno = '';
                    }
                }
                // MD 정보 초기화
                this.basicInfo.mdifidx = '';
                this.basicInfo.mdsabun = '';
                this.basicInfo.empname = '';
                // 판매가/정상가 초기화(옵션 대표상품의 가격 표시)
                this.sellInfo.marketprice = '';
                this.sellInfo.price = '';
                // 옵션리스트 초기화
                this.optionInfo.optionList.forEach(item => {
                    if (item.status === 'N') {
                        this.optionInfo.delOptioncodeList.push(item);
                    }
                });
                this.optionInfo.optionList = [];
                // 배송템플릿목록 초기화
                this.deliveryInfo.delividx = '';
                this.getDelivTempList();
            }
        },
        // 파트너사 선택
        'basicInfo.dealerno': function(value) {
            if (!this.issearch) {
                // 배송템플릿목록 초기화
                this.deliveryInfo.delividx = '';
                this.getDelivTempList();

                // 파트너사 수수료율 초기화
                this.basicInfo.partnerList.forEach(item => {
                    if (item.no === value) {
                        this.optionInfo.commrate = item.commrate===''? null:item.commrate;
                        this.optionInfo.optionList.forEach(option => {
                            option.commrate = item.commrate;
                        });
                    }
                });
            }
        },
        // 적용채널
        'basicInfo.muappchtypeArr': function(value) {
            if (value.length < this.commonCode.muappchtype.length) {
                this.basicInfo.isallchannel = 'F';
            } else {
                this.basicInfo.isallchannel = 'T';
            }
            this.basicInfo.muappchtype = this.basicInfo.muappchtypeArr.join();
        },
        // 판매대상
        'sellInfo.selltarget': function(value) {
            if (!this.issearch) {
                // 특정회원이 아닌 경우
                if (value !== 'SP') {
                    this.sellInfo.isallmemlv = 'T';
                    this.checkAllMemlvtype();
                } else {
                    this.sellInfo.mumemlvtypeArr = [];
                }
            }
        },
        // 판매대상-등급별
        'sellInfo.mumemlvtypeArr': function(value) {
            if (value.length < this.commonCode.memlvtype.length) {
                this.sellInfo.isallmemlv = 'F';
            } else {
                this.sellInfo.isallmemlv = 'T';
            }
            this.sellInfo.mumemlvtype = this.sellInfo.mumemlvtypeArr.join();
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
        // 전시기간
        'sellInfo.disperiod': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]);

            // 상시
            if (type == 'all') {
                this.sellInfo.disstdate = this.$util.getDate('-');
                this.sellInfo.diseddate = this.$util.getFormatDate('20991231', '-');
            } else if (type == 'day') {
                this.sellInfo.diseddate = this.$util.getAddDate(this.sellInfo.disstdate, addValue, '-');
            } else if (type == 'month') {
                this.sellInfo.diseddate = this.$util.getAddMonth(this.sellInfo.disstdate, addValue, '-');
            }
            this.sellInfo.dissthour = '00';
            this.sellInfo.disedhour = '11';
            this.sellInfo.disstmin = '00';
            this.sellInfo.disedmin = '59';
        },
        // 아이콘설정
        'imageInfo.mugicontypeArr': function(value) {
            if (value.length < this.commonCode.gicontype.length) {
                this.imageInfo.isallgicontype = 'F';
            } else {
                this.imageInfo.isallgicontype = 'T';
            }
            this.imageInfo.mugicontype = this.imageInfo.mugicontypeArr.join();
        },
        // 상품정보고시
        'notifyInfo.notifytplidx': function() {
            this.setAllRefDetail(false, false);
        },
        // 배송템플릿 선택
        'deliveryInfo.delividx': function(value) {
            // if (!this.issearch) {
            //     this.deliveryInfo.iscombdeliv = 'F';
            // }
            if (value === null) {
                this.deliveryInfo.delividx = '';
            }
            this.deliveryInfo.delivTemp = {};
            this.deliveryInfo.delivTempList.forEach(item => {
                if (item.delividx == value) {
                    this.deliveryInfo.delivTemp = item;
                    this.deliveryInfo.iscombdeliv = item.iscombdeliv;
                    if(this.deliveryInfo.delivTemp.delivschtype == 'DSC001') {
                        this.basicInfo.delivinfo = '주문확인 후 최대 1~4일 내 배송완료';
                    } else {
                        this.basicInfo.delivinfo = '';
                    }
                    return;
                }
            });
        },
        // KC인증대상구분
        'notifyInfo.kcdivtype': function(value) {
            if (!this.issearch) {
                if(value != this.$store.getters['ADMIN'].KC_DIV_TYPE_KDT002) {
                    this.files.kcfilelist.forEach(item => {
                        this.deletefilelist.push(item);
                    });
                    this.notifyInfo.kccertlist = [];
                    this.files.kcfilelist = [];
                    this.imgPreview.kcfilelist = [];
                }
                if (this.notifyInfo.kccertlist.length == 0) {
                    let param = { iscert: '' };
                    this.notifyInfo.kccertlist.splice(0, 0, param);
                }
            }
        },
        // 숫자만입력
        'sellInfo.minordcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.sellInfo.minordcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        'sellInfo.maxordcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.sellInfo.maxordcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        'sellInfo.daymaxordcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.sellInfo.daymaxordcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        'sellInfo.permaxordcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.sellInfo.permaxordcnt = value.replace(/(^0[0-9]|[^0-9])/gi, '');
        },
        'etcInfo.aswarmonth': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.etcInfo.aswarmonth = value.replace(/(^0[0-9]|[^0-9])/gi, '');
        }
    }
}