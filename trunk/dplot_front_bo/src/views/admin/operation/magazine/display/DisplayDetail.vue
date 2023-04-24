<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>전시영역컨텐츠 상세</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix">
                    <div class="fl">
                        <span>작성자 : {{ info.reguserid }}</span>
                    </div>
                    <div class="fr txt-gray">
                        <span>등록일 : {{ info.regdate }}</span>
                        <span class="left-bar">수정일 : {{ info.moddate }}</span>
                    </div>
                </div>
                <div class="bar-title mt10">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>노출상태<i class="essential"></i></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="rgroup00" id="rgroup01" v-model="info.isdisplay" value="T" checked/><label for="rgroup01">노출</label>
                                    <input type="radio" name="rgroup00" id="rgroup02" v-model="info.isdisplay" value="F" /><label for="rgroup02">미노출</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>카피<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.copy" style="width: 100%" placeholder="전시영역 컨텐츠 카피" /></dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" v-model="info.desc" style="width: 100%" placeholder="전시영역 컨텐츠 설명" /></dd>
                        </dl>
                    </div>
                </div>
                <div class="bar-title">조건설정</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>전시기간<i class="essential"></i></th>
                                <td>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="timeInfo.startYYYYMMDD"
                                        :fromHH="timeInfo.startHH"
                                        :fromMM="timeInfo.startMM"
                                        :toYYYYMMDD="timeInfo.toYYYYMMDD"
                                        :toHH="timeInfo.toHH"
                                        :toMM="timeInfo.toMM"
                                        @getDate="getEventTimeDate"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>노출채널<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" v-model="checkObj.isallmuappch" id="isallmuappch" true-value="T" false-value="F" @change="checkAllAppchtype">
                                        <label for="isallmuappch">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                                        <input type="checkbox" v-model="checkObj.muappchtypearr" :id="'muappchtype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'muappchtype_'+item.cmcode">{{item.codename}}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원유형<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" v-model="checkObj.isallmumember" id="isallmumember" true-value="T" false-value="F" @change="checkAllMembertype">
                                        <label for="isallmumember">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.mumembertype" :key="item.cmcode">
                                        <input type="checkbox" v-model="checkObj.mumembertypearr" :id="'mumembertype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'mumembertype_'+item.cmcode">{{item.codename}}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원등급<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" v-model="checkObj.isallmumemlv" id="isallmumemlv" true-value="T" false-value="F" @change="checkAllMemLvtype">
                                        <label for="isallmumemlv">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.mumemlvtype" :key="item.cmcode">
                                        <input type="checkbox" v-model="checkObj.mumemlvtypearr" :id="'mumemlvtype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'mumemlvtype_'+item.cmcode">{{item.codename}}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>설명문구<i class="essential"></i></th>
                                <td>
                                    <input type="text" v-model="info.contents" style="width: calc(100% - 100px);" placeholder="설명문구 텍스트" />
                                </td>
                            </tr>
                            <tr>
                                <th>분류기준<i class="essential"></i></th>
                                <td>
                                    <select v-model="info.mztype" style="width: 450px;">
                                        <option value="">분류기준을 선택하세요.</option>
                                        <option v-for="(row, c) in this.commonCode.mztype" :key="c" :value="row.cmcode">{{ row.codename }}</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>링크(PC)<i class="essential"></i></th>
                                <td>
                                    <input type="text" v-model="info.pclinkurl" style="width: calc(100% - 100px);" placeholder="전시영역 클릭 시 연결되는 PC 화면 주소" />
                                    <input type="checkbox" v-model="info.ispcnwindow" true-value="T" false-value="F" id="group02" class="ml10" /><label for="group21">새창</label>
                                </td>
                            </tr>
                            <tr>
                                <th>링크(모바일)<i class="essential"></i></th>
                                <td>
                                    <input type="text" v-model="info.molinkurl" style="width: calc(100% - 100px);" placeholder="전시영역 클릭 시 연결되는 모바일 화면 주소" />
                                    <input type="checkbox" v-model="info.ismonwindow" true-value="T" false-value="F" id="group02" class="ml10" /><label for="group21">새창</label>
                                </td>
                            </tr>
                            <tr>
                                <th>대표 이미지(PC)<i class="essential"></i></th>
                                <td>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" style="width: 310px; height: 225px;" :class="{'no-image': $util.isNull(files['pcimgfile'])}"><!-- 이미지 없는 경우 no-image 클래스 추가 -->
                                            <img style="width: 310px; height: 225px;" :src="imgPreview['pcimgfile']" alt="대표 이미지(pc)" v-if="!$util.isNull(files['pcimgfile'])">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">파일 올리기</button>
                                        <input type="file" ref="pcimgfile" @change="handleFileUpload('pcimgfile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['pcimgfile'])" @click="removeFile('pcimgfile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 620*450 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대표 이미지(모바일)<i class="essential"></i></th>
                                <td>
                                    <div class="mb10">
                                        <input type="checkbox" v-model="info.copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label for="copy-img">PC 대표 이미지를 복사</label>
                                    </div>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" style="width: 360px; height: 320px;" :class="{'no-image': $util.isNull(files['mobileimgfile'])}">
                                            <img style="width: 360px; height: 320px;" :src="imgPreview['mobileimgfile']" alt="대표 이미지(모바일)" v-if="!$util.isNull(files['mobileimgfile'])">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">파일 올리기</button>
                                        <input type="file" ref="mobileimgfile" @change="handleFileUpload('mobileimgfile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['mobileimgfile'])" @click="removeFile('mobileimgfile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*640 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>컨텐츠 키워드<i class="essential"></i></th>
                                <td>
                                    <div class="mb10">
                                        <input type="text" placeholder="키워드 입력" ref="keyword">
                                        <button type="button" class="btn blue-line ml3" @click="addKeyword">추가</button>
                                    </div>
                                    <div class="keyword-group" v-for="(row,key) in this.keywordlist" :key="key">
                                        <div>{{ row.keyword }}<button type="button" class="file-del" @click="removeKeyword(key)"></button></div>
                                    </div>                                    
                                </td>
                            </tr>
                            <tr>
                                <th>상품1<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">노출대상 상품</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" v-show="$util.isNull(info.goodsno1)" @click="openGoodsAdditionPopup(1)">상품추가</button>
                                            <button type="button" class="btn red-line" v-show="!$util.isNull(info.goodsno1)" @click="removeGoods(1)">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                            <colgroup>
                                                <col width="4%"><!-- No -->
                                                <col width="6%"><!-- 판매구분 -->
                                                <col width="10%"><!-- 파트너사명 -->
                                                <col width="8%"><!-- 상품코드 -->
                                                <col width="62px"><!-- 이미지 -->
                                                <col width=""><!-- 상품명 -->
                                                <col width="7%"><!-- 판매가 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>판매구분</th>
                                                    <th>파트너사명</th>
                                                    <th>상품코드</th>
                                                    <th colspan="2">상품명</th>
                                                    <th>판매가</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="!$util.isNull(info.goodsno1)">
                                                <tr>
                                                    <td>1</td>
                                                    <td>{{ goods1.ispbgoodsname }}</td>
                                                    <td>{{ goods1.dealername }}</td>
                                                    <td>{{ goods1.goodscode }}</td>
                                                    <td>
                                                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(goods1.fullpath)}">
                                                            <img :src="goods1.fullpath" v-if="!$util.isNull(goods1.fullpath)">
                                                        </div>
                                                    </td>
                                                    <td class="left no-left">
                                                        <span class="small-txt">{{ goods1.fullcategoryname }}</span>
                                                        <p class="mg0">{{ goods1.goodsname }}</p>
                                                    </td>
                                                    <td class="right">{{ goods1.price }}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>상품2<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">노출대상 상품</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" v-show="$util.isNull(info.goodsno2)" @click="openGoodsAdditionPopup(2)">상품추가</button>
                                            <button type="button" class="btn red-line" v-show="!$util.isNull(info.goodsno2)" @click="removeGoods(2)">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                            <colgroup>
                                                <col width="4%"><!-- No -->
                                                <col width="6%"><!-- 판매구분 -->
                                                <col width="10%"><!-- 파트너사명 -->
                                                <col width="8%"><!-- 상품코드 -->
                                                <col width="62px"><!-- 이미지 -->
                                                <col width=""><!-- 상품명 -->
                                                <col width="7%"><!-- 판매가 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>판매구분</th>
                                                    <th>파트너사명</th>
                                                    <th>상품코드</th>
                                                    <th colspan="2">상품명</th>
                                                    <th>판매가</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="!$util.isNull(info.goodsno2)">
                                                <tr>
                                                    <td>1</td>
                                                    <td>{{ goods2.ispbgoodsname }}</td>
                                                    <td>{{ goods2.dealername }}</td>
                                                    <td>{{ goods2.goodscode }}</td>
                                                    <td>
                                                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(goods2.fullpath)}">
                                                            <img :src="goods2.fullpath" v-if="!$util.isNull(goods2.fullpath)">
                                                        </div>
                                                    </td>
                                                    <td class="left no-left">
                                                        <span class="small-txt">{{ goods2.fullcategoryname }}</span>
                                                        <p class="mg0">{{ goods2.goodsname }}</p>
                                                    </td>
                                                    <td class="right">{{ goods2.price }}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="goSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
export default {
    props: {
        commonCodeList : Object,
        idx: Number
    },
    name: 'admin.operation.magazine.display.detail',
    components: {
        CommonDatePickerFromTo
    },
    data() {
        return {
            info: {
            },
            keywordlist: [],
            deletekeywordlist:[],
            goods1: {},
            goods2: {},
            timeInfo: {              // 진행기간
                startYYYYMMDD: '',
                startHH: '',
                startMM: '',
                toYYYYMMDD: '',
                toHH: '',
                toMM: ''
            },
            checkObj: {
                muappchtypearr: [],
                isallmuappch: 'F',
                mumembertypearr: [],
                isallmumember: 'F',
                mumemlvtypearr: [],
                isallmumemlv: 'F',
            },
            commonCode: this.commonCodeList,
            files: {},
            addfiles: {
                pcimgfile: '',
                mobileimgfile: '',
            },
            imgPreview: {
                pcimgfile: '',
                mobileimgfile: '',
            },
            deletefile: [],
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {

            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');
      
            if(this.isRead){
                let param = {
                    mzexidx : this.idx
                }

                this.$http.post("/admin/operation/magazine/display/detail", param)
                    .then(result =>{
                        this.info = result.data.info;
                        this.files = result.data.files;
                        this.keywordlist = result.data.keywordlist;
                        this.goods1 = result.data.goods1;
                        this.goods2 = result.data.goods2;
                        this.imgPreview.pcimgfile = this.files.pcimgfile.fullpath;
                        this.imgPreview.mobileimgfile = this.files.mobileimgfile.fullpath;
                        this.info.copyimgcheck = false;
                        this.setDateInit();
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }else{
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
        setDateInit() {
            this.timeInfo.startYYYYMMDD = this.$util.getFormatDate(this.info.exsttime.substring(0,8),'-');
            this.timeInfo.startHH = this.info.exsttime.substring(8,10);
            this.timeInfo.startMM = this.info.exsttime.substring(10,12);
            this.timeInfo.toYYYYMMDD = this.$util.getFormatDate(this.info.exedtime.substring(0,8),'-');
            this.timeInfo.toHH = this.info.exedtime.substring(8,10);
            this.timeInfo.toMM = this.info.exedtime.substring(10,12);

            if(!this.$util.isNull(this.info.muappchtype)) {
                this.checkObj.muappchtypearr = this.info.muappchtype.split(',');
            }
            if(!this.$util.isNull(this.info.mumembertype)) {
                this.checkObj.mumembertypearr = this.info.mumembertype.split(',');
            }
            if(!this.$util.isNull(this.info.mumemlvtype)) {
                this.checkObj.mumemlvtypearr = this.info.mumemlvtype.split(',');
            }
        },
         // 진행기간 DatePicker 콜백 메서드
        getEventTimeDate(date) {
            this.info.exsttime = date.fromDate12;
            this.info.exedtime = date.toDate12;
        },
        goSave() {
            if(this.checkValidation()) {
                let params = this.info;
                let files = [];
                if(!this.$util.isNull(this.addfiles.pcimgfile)){
                    files.push({key: 'pcimgfile', file: this.addfiles.pcimgfile.file});
                }
                if(!this.$util.isNull(this.addfiles.mobileimgfile)){
                    // pc이미지 복사를 한 경우 file key가 없고 복사시 기존에 저장되어있는 pcimgfile을 저장해야하므로 pcimgfile의 idx를 보냄
                    if(Object.prototype.hasOwnProperty.call(this.files.mobileimgfile,'file')){
                        files.push({key: 'mobileimgfile', file: this.addfiles.mobileimgfile.file});
                    } else {
                        params.copycheck = true;
                        params.copyidx = this.files.pcimgfile.idx;
                    }
                }
                params.files = files;
                params.deletefile = new Set(this.deletefile);

                let addkeywordlist = this.keywordlist.filter(a => !Object.prototype.hasOwnProperty.call(a,'mzexwdidx'));
                if(addkeywordlist.length > 0) {
                    params.addkeywordlist = addkeywordlist;
                }
                if(this.deletekeywordlist.length > 0) {
                    params.deletekeywordlist = this.deletekeywordlist;
                }

                if(params.isdisplay === 'F') {
                    let param = {
                        idx : params.mzexidx,
                        isdisplay: params.isdisplay,
                        overmsg: '저장하시겠습니까?',
                        zeromsg: '저장',
                        isloading: false,
                    }

                    this.$http.post("/admin/operation/magazine/display/check", param)
                    .then(result => {
                        if (result.statusCode == 200) {
                            params.msg = result.data.msg;
                            this.update(params);
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    })
                } else {
                    params.msg = "저장하시겠습니까?";
                    this.update(params);
                }
            }
        },
        update(params) {
            if(confirm(params.msg)){
                this.$http.post("/admin/operation/magazine/display/modify", params)
                .then(result => {
                    if(result.statusCode === 200){
                        alert("저장되었습니다.");
                        this.onClose(true);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }  
        },
        checkValidation() {
            let msg = '';
            let valid = [
                {field: 'info.copy', type: 'I', name: '카피', required: true},
                {field: 'info.muappchtype', type: 'S', name: '노출채널', required: true},
                {field: 'info.mumembertype', type: 'S', name: '대상회원유형', required: true},
                {field: 'info.mumemlvtype', type: 'S', name: '대상회원등급', required: true},
                {field: 'info.contents', type: 'I', name: '설명문구', required: true},
                {field: 'info.mztype', type: 'S', name: '분류기준', required: true},
                {field: 'info.pclinkurl', type: 'I', name: '링크(PC)', required: true},
                {field: 'info.molinkurl', type: 'I', name: '링크(모바일)', required: true},
                {field: 'info.goodsno1', type: 'S', name: '상품1', required: true},
                {field: 'info.goodsno2', type: 'S', name: '상품2', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(this.info.exsttime.length < 12) {
                alert("전시시작시간을 선택해주세요.");
                return false;
            }
            if(this.info.exedtime.length < 12) {
                alert("전시종료시간을 선택해주세요.");
                return false;
            }
            if(this.info.exsttime >= this.info.exedtime) {
                alert("전시종료시간을 시작시간 이후로 설정해주세요.");
                return false;
            }

            if(this.files.pcimgfile === ''){
                alert("대표 이미지(PC)를 등록해주세요.");
                return false;
            }
            if(this.files.mobileimgfile === ''){
                alert("대표 이미지(모바일)를 등록해주세요.");
                return false;
            }

            if(this.keywordlist.length === 0){
                alert("컨텐츠 키워드를 등록해주세요.");
                return false;
            }  

            return true;
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
        handleFileUpload: function(fileTypeKey, target) {
            // PC, 모바일 대표이미지
            let file = this.$refs[fileTypeKey];
            if (this.$util.isNull(file)) {
                return;
            }

            if(Object.prototype.hasOwnProperty.call(this.files[fileTypeKey],'idx')){
                if(fileTypeKey !== 'mobileimgfile' || !this.info.copyimgcheck) {
                    this.deletefile.push(this.files[fileTypeKey].idx);
                }
            }

            let fileType = ['image/png','image/jpeg', 'image/png'];
            if(!fileType.includes(file.files[0].type)){
                alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                file.value = null;
                this.files[fileTypeKey] = '';
                return false;
            }
            if(file.files[0].size > 1048576 * 10){
                alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                file.value = null;
                this.files[fileTypeKey] = '';
                return false;
            }
            let fileObj = {
                file: file.files[0],
                iscreated: true
            }
            
            this.files[fileTypeKey] = fileObj;
            this.addfiles[fileTypeKey] = fileObj;
            this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);

            if(fileTypeKey === 'pcimgfile' && this.info.copyimgcheck){
                this.setSameAsPcepreImg();
            }
            // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
            if (fileTypeKey === 'mobileimgfile') {
                this.info.copyimgcheck = false;
            }
        },
        removeFile(fileTypeKey, index) {
            if(confirm('파일을 삭제 하시겠습니까?')){
                if(Object.prototype.hasOwnProperty.call(this.files[fileTypeKey],'idx')){
                    if(fileTypeKey !== 'mobileimgfile' || !this.info.copyimgcheck) {
                        this.deletefile.push(this.files[fileTypeKey].idx);
                    }
                }

                this.files[fileTypeKey] = '';
                this.addfiles[fileTypeKey] = '';
                this.imgPreview[fileTypeKey] = '';
                this.$refs[fileTypeKey].value = null;

                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey === 'mobileimgfile') {
                    this.info.copyimgcheck = false;
                }
            }
        },
        // PC 대표이미지와 동일하게 세팅
        setSameAsPcepreImg: function() {
            if(Object.prototype.hasOwnProperty.call(this.files.mobileimgfile,'idx')){
                if(this.info.copyimgcheck) {
                    if(!this.deletefile.includes(this.files.mobileimgfile.idx)) {
                        this.deletefile.push(this.files.mobileimgfile.idx);
                    }
                }
            }
            if (this.info.copyimgcheck) {
                this.files.mobileimgfile = this.files.pcimgfile;
                this.addfiles.mobileimgfile = this.files.pcimgfile;
                this.imgPreview.mobileimgfile = this.imgPreview.pcimgfile;
            } else {
                this.files.mobileimgfile = '';
                this.addfiles.mobileimgfile = '';
                this.imgPreview.mobileimgfile = '';
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllAppchtype: function() {
            let isAllCheck = this.checkObj.isallmuappch;
            this.checkObj.muappchtypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.checkObj.muappchtypearr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.checkObj.isallmumember;
            this.checkObj.mumembertypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.mumembertype){
                    this.checkObj.mumembertypearr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllMemLvtype: function() {
            let isAllCheck = this.checkObj.isallmumemlv;
            this.checkObj.mumemlvtypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.mumemlvtype){
                    this.checkObj.mumemlvtypearr.push(type.cmcode);
                }
            }
        },
        // 추가상품 팝업 오픈
        openGoodsAdditionPopup(index) {
            let param = {
                issingle: true,
                isdisplay: 'T',
                goodsselltypeArr: [this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST002]
            }
            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, param,
                (result) => {
                    let goodsinfo = result.list[0];
                    if(index === 1) {
                        if(this.info.goodsno2 === goodsinfo.goodsno) {
                            alert("상품2와 동일한 상품을 등록할 수 없습니다.");
                            return;
                        }
                        this.info.goodsno1 = goodsinfo.goodsno;
                        this.goods1 = goodsinfo;
                    } else {
                        if(this.info.goodsno1 === goodsinfo.goodsno) {
                            alert("상품1과 동일한 상품을 등록할 수 없습니다.");
                            return;
                        }
                        this.info.goodsno2 = goodsinfo.goodsno;
                        this.goods2 = goodsinfo;
                    }
                }
            );
        },
        removeGoods(index){
            if(index === 1) {
                this.info.goodsno1 = '';
                this.goods1 = {};
            } else {
                this.info.goodsno2 = '';
                this.goods2 = {};
            }
        },
        addKeyword() {
            let obj = {
                keyword: this.$refs.keyword.value,
            }; 

            if(this.$util.isNull(obj.keyword)) {
                alert("추가할 키워드를 입력해주세요.");
                return;
            }

            this.keywordlist.push(obj);
            this.$refs.keyword.value = '';
        },
        removeKeyword(index) {
            if(Object.prototype.hasOwnProperty.call(this.keywordlist[index],'mzexwdidx')) {
                this.deletekeywordlist.push(this.keywordlist[index].mzexwdidx);
            }
            this.keywordlist.splice(index, 1);
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            this.timeInfo = this.$options.data().timeInfo;
            this.checkObj = this.$options.data().checkObj;
            this.keywordlist = this.$options.data().keywordlist;
            this.deletekeywordlist = this.$options.data().deletekeywordlist;
            this.files = this.$options.data().files;
            this.addfiles = this.$options.data().addfiles;
            this.deletefile = this.$options.data().deletefile;
            this.imgPreview = this.$options.data().imgPreview;
            // 팝업 닫기
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closeDetail', isreload);
            } else {
                this.$emit('closeDetail');
            }
        },
    },
    watch: {
        'checkObj.muappchtypearr': function(value){
            if (value.length < this.commonCode.muappchtype.length) {
                this.checkObj.isallmuappch = 'F';
            } else {
                this.checkObj.isallmuappch = 'T';
            }
            this.info.muappchtype = this.checkObj.muappchtypearr.join();
        },
        'checkObj.mumembertypearr': function(value){
            if (value.length < this.commonCode.mumembertype.length) {
                this.checkObj.isallmumember = 'F';
            } else {
                this.checkObj.isallmumember = 'T';
            }
            this.info.mumembertype = this.checkObj.mumembertypearr.join();
        },
        'checkObj.mumemlvtypearr': function(value){
            if (value.length < this.commonCode.mumemlvtype.length) {
                this.checkObj.isallmumemlv = 'F';
            } else {
                this.checkObj.isallmumemlv = 'T';
            }
            this.info.mumemlvtype = this.checkObj.mumemlvtypearr.join();
        },
    }
}
</script>

<style>

</style>