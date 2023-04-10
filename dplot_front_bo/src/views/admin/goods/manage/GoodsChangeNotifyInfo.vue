<template>
    <!-- 상품정보고시 일괄변경 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>상품정보고시 일괄변경</h2>
                <button type="button" class="pop-close" @click="$emit('closePopup');"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">총 {{ goodsnoList.length }}개 상품의 상품정보고시를 일괄 변경합니다.</div>
                <div class="clearfix mt10">
                    <div class="bar-title fl">상품정보고시
                        <span class="txt-orange ml10"><i class="icon-alert"></i>수정을 원하는 항목을 체크하신 후 일괄변경 하시기 바랍니다.</span>
                    </div>
                </div>
                <div class="boxing mt10">
                    <div class="form-area" style="display: block;">
                        <div style="margin: 20px;">
                            <table cellpadding="0" cellspacing="0" class="gray-tb">
                                <colgroup>
                                    <col width="170px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th><input type="checkbox" id="ischknotify" v-model="chkObject.notify.ischecked" true-value="T" false-value="F"><label for="ischknotify">상품정보고시</label></th>
                                        <td>
                                            <select style="width: 250px;" v-model="info.notifytplidx" @change="getNotifyTempItemList">
                                                <option value="">상품군 선택</option>
                                                <option v-for="item in info.notifyTempList" :key="item.idx" :value="item.idx">{{ item.title }}</option>
                                            </select>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <dl v-if="info.notifyTempItemList.length > 0">
                                <dt>{{ info.notifytpltitle }}</dt>
                                <dd>
                                    <input type="checkbox" id="alltxt" @click="setAllRefDetail($event.target.checked)"><label for="alltxt">전체 "상품상세설명 참고"로 표기</label>
                                </dd>
                            </dl>
                            <table cellpadding="0" cellspacing="0" class="gray-tb" v-if="info.notifyTempItemList.length > 0">
                                <colgroup>
                                    <col width="170px">
                                    <col width="">
                                    <col width="70px">
                                </colgroup>
                                <tbody>
                                    <tr v-for="(item) in info.notifyTempItemList" :key="item.notifyitemidx">
                                        <th>{{ item.notifyname }}</th>
                                        <td>
                                            <textarea placeholder="세부사항을 입력하세요!" v-model="item.notifydata" maxlength="1000"></textarea>
                                            <div style="text-align: right;">({{ $util.isNull(item.notifydata)? '0':item.notifydata.length }}/1000)</div>
                                        </td>
                                        <!-- <td>
                                            <button type="button" class="add mg0" @click="addNotifyItem(index)"></button>
                                            <button type="button" class="del" @click="removeNotifyItem(index)" v-if="index > 0"></button>
                                        </td> -->
                                    </tr>
                                </tbody>
                            </table>
                            <div class="clearfix">
                                <div class="bar-title fl">KC인증</div>
                                <div class="fr"><p class="txt-orange"><i class="icon-alert"></i>사실과 다른 인증정보를 입력하실 경우, 관련법에 의하여 처벌받으실 수 있으니, 정확한 정보만 입력해주시기 바랍니다.</p></div>
                            </div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb">
                                <colgroup>
                                    <col width="170px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th><input type="checkbox" id="ischkkcdivtype" v-model="chkObject.kcdivtype.ischecked" @click="checkKcChecked($event.target.checked)" true-value="T" false-value="F"><label for="ischkkcdivtype">대상여부</label></th>
                                        <td>
                                            <div class="radio_wrap">
                                                <div v-for="item in commonCode.kcdivtype" :key="item.cmcode">
                                                    <input type="radio" name="kcdivtypeD" :id="'kcdivtypeD_'+item.cmcode" :value="item.cmcode" v-model="info.kcdivtype"/>
                                                    <label :for="'kcdivtypeD_'+item.cmcode">{{ item.codename }}</label>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr v-if="info.kcdivtype == $store.getters['ADMIN'].KC_DIV_TYPE_KDT002" >
                                        <th><input type="checkbox" id="ischkkccert" v-model="chkObject.kccert.ischecked" true-value="T" false-value="F" disabled><label for="ischkkccert">인증선택</label></th>
                                        <td>
                                            <div class="dpb" v-for="(item, index) in info.kccertlist" :key="item.idx">
                                                <input type="text" class="ml3" placeholder="인증번호입력(-포함)" v-model="item.kccertno" maxlength="30" @input="item.iscert=''">
                                                <button type="button" class="btn blue-line ml3" @click="kcCertification(item)">인증확인</button>
                                                <button type="button" class="add" @click="addKccertItem(index)"></button>
                                                <button type="button" class="del" @click="removeKccertItem(index)" v-if="info.kccertlist.length>1"></button>
                                                <span class="left-bar txt-blue" v-show="item.iscert=='T'">적합</span>
                                                <span class="left-bar txt-red" v-show="item.iscert=='F'">부 적합</span>
                                                <span class="left-bar txt-orange" v-show="$util.isNull(item.iscert)">미 인증</span>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr v-if="info.kcdivtype == $store.getters['ADMIN'].KC_DIV_TYPE_KDT002" >
                                        <th><input type="checkbox" id="ischkkcfile" v-model="chkObject.kcfile.ischecked" true-value="T" false-value="F" disabled><label for="ischkkcfile">파일첨부</label></th>
                                        <td>
                                            <button type="button" class="btn blue-line" @click="fileAttach('kcFile')">파일첨부</button>
                                            <div class="dpib" v-for="(item, index) in files.kcfilelist" :key="'kcFile'+index">
                                                <a href="javascript:void(0);" class="file-link"
                                                    :style="item.isimage? '':'color: #666 !important; text-decoration: none !important'" @click="viewKcFile(item, index)">
                                                    {{ item.file.name }}
                                                </a>
                                                <button type="button" class="file-del" @click="removeFile('kcFile', index)"></button>
                                            </div>
                                            <input type="file" id="input-file" accept="image/jpeg, image/png, .pdf"
                                                ref="kcFile" @change="handleFileUpload('kcFile', $event.target)" hidden multiple />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="openConfirmPopup">일괄변경</button>
                    <button type="button" class="btn big darkgray" @click="$emit('closePopup');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /상품정보고시 일괄변경 팝업 -->
</template>

<script>
import GoodsChangeConfirmPopup from '@views.admin/goods/popup/GoodsChangeConfirmPopup.vue';

export default {
    name: 'admin.goods.manage.goodsChangeNotifyInfo',
    props : ['checkedList', 'ckey'],
    mounted() {
        // 초기화
        this.onInit();
    },
    data() {
        return {
            goodsnoList: [],    // 상품번호 목록
            chkObject: {        // 체크항목 목록
                notify: { key: 'notify', name:'상품정보고시', ischecked:'F' },
                kcdivtype: { key: 'kcdivtype', name:'KC대상여부', ischecked:'F' },
                kccert: { key: 'kccert', name:'KC인증목록', ischecked:'F' },
                kcfile: { key: 'kcfile', name:'KC첨부파일', ischecked:'F' }
            },
            commonCode: {
                kcdivtype: []       // KC대상구분
            },
            files: {        // 파일
                kcfilelist: []  // kc 첨부파일 목록
            },
            imgPreview: {   // 미리보기 URL
                kcfilelist: []  // kc 첨부파일 목록
            },
            info: {
                notifytplidx: '',       // 정보고시템플릿일련번호
                notifytpltitle: '',     // 정보고시템플릿명칭
                notifyTempList: [],     // 상품정보고시템플릿 목록
                notifyTempItemList: [], // 상품정보고시템플릿항목 목록
                kcdivtype: '',          // KC대상여부
                kccertlist: [],         // KC인증목록
            }
        }
    },
    methods: {
        // 화면초기화
        onInit: function() {
            // 넘어온 파라미터 세팅
            this.goodsnoList = this.checkedList;
            // 공통코드 조회
            this.getCommonCodeList();
            // 상품정보고시템플릿 목록 조회
            this.getNotifyTempList();
        },
        // 일괄변경 확인팝업 오픈
        openConfirmPopup: function() {
            // 파라미터 세팅
            let checkItemList = [];
            for (const [key, value] of Object.entries(this.chkObject)) {
                if (this.chkObject[key].ischecked === 'T') {
                    checkItemList.push(value);
                }
            }
            // 선택한 항목 체크
            if (checkItemList.length == 0) {
                alert("변경할 항목을 선택해주세요.");
                return;
            }

            // 일괄변경 확인팝업 오픈
            if (this.checkValidation()) {
                this.$eventBus.$emit('modalShow', GoodsChangeConfirmPopup, { checkItemList: checkItemList},
                    (result) => {
                        if (result.isconfirm == 'T') {
                            this.changeAll();
                        }
                    }
                );
            }
        },
        // 일괄변경
        changeAll: function() {
            // 파일세팅
            let files = [];
            const constants = this.$store.getters['ADMIN'];
            for(let i=0; i<this.files.kcfilelist.length; i++) {
                files.push({key: constants['IMG_TYPE_GOODS_KC_CERT']+(i+1), file: this.files.kcfilelist[i].file});
            }

            let params = {
                ckey: this.ckey,
                isnotifychange: this.chkObject.notify.ischecked,
                iskcdivtypechange: this.chkObject.kcdivtype.ischecked,
                iskccertchange: this.chkObject.kccert.ischecked,
                iskcfilechange: this.chkObject.kcfile.ischecked,
                goodsnoList: this.goodsnoList
            }
            params = Object.assign({}, params, this.info);
            
            let requestUrl = '/admin/goods/manage/update';
            if (files != null && files.length > 0) {
                params.files = files;
                requestUrl = '/admin/goods/manage/update/withfile';
            }
            this.$http.post(requestUrl, params)
                .then(result => {
                    if (result.statusCode === 200) {
                        alert("일괄변경이 완료되었습니다.");
                        this.$emit('closePopup', 'T');
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 입력 validation 체크
        checkValidation: function() {
            let checkResult = true;

            // 상품정보고시
            if (this.chkObject.notify.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.notifytplidx)) {
                    alert('상품정보고시 상품군을 선택해주세요.');
                    checkResult = false;
                }
                if (checkResult && this.info.notifyTempItemList.length == 0) {
                    alert("상품정보고시 항목이 존재하지 않습니다.");
                    checkResult = false;
                }
            }
            // KC대상여부
            if (checkResult && this.chkObject.kcdivtype.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.kcdivtype)) {
                    alert('KC대상여부를 선택해주세요.');
                    checkResult = false;
                }
                // KC대상인경우 인증목록 필수체크, 적합여부 체크
                if (checkResult && this.info.kcdivtype === this.$store.getters['ADMIN'].KC_DIV_TYPE_KDT002 && this.info.kccertlist.length == 0) {
                    alert('KC인증을 추가해주세요.');
                    checkResult = false;
                }
                let chkCnt = this.info.kccertlist.filter(item => {
                    return item.iscert != 'T';
                }).length;
                if (checkResult && this.info.kcdivtype === this.$store.getters['ADMIN'].KC_DIV_TYPE_KDT002 && chkCnt > 0) {
                    alert('적합하지 않은 KC인증이 존재합니다.');
                    checkResult = false;
                }
            }
            // // KC인증목록
            // if (checkResult && this.chkObject.kccert.ischecked === 'T') {
            //     // 필수체크
            //     if (this.info.kccertlist.length == 0) {
            //         alert('KC인증을 추가해주세요.');
            //         checkResult = false;
            //     }
            //     // 부적합, 미인증 항목 확인
            //     let chkCnt = this.info.kccertlist.filter(item => {
            //         return item.iscert != 'T';
            //     }).length;
            //     if (checkResult && chkCnt > 0) {
            //         alert('적합하지 않은 KC인증이 존재합니다.');
            //         checkResult = false;
            //     }
            // }
            // // KC첨부파일
            // if (checkResult && this.chkObject.kcfile.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.files.kcfilelist.length == 0)) {
            //         alert('첨부파일을 추가해주세요.');
            //         checkResult = false;
            //     }
            // }

            return checkResult;
        },
        // 공통코드 세팅
        getCommonCodeList: function() {
            let cmclassArr = ['KCDIVTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 정보고시템플릿목록 조회
        getNotifyTempList: function() {
            this.$http.post('/admin/goods/regist/notifytpl/list', { isloading: false })
                .then(result => {
                    this.$util.debug(result);
                    this.info.notifyTempList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 정보고시템플릿항목 조회
        getNotifyTempItemList: function() {
            let params = { notifytplidx: this.info.notifytplidx, isloading: false };
            this.$http.post('/admin/goods/regist/notifytplitem/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.info.notifyTempItemList = result.data.list;
                    if (this.info.notifyTempItemList.length > 0) {
                        this.info.notifytplidx = this.info.notifyTempItemList[0].notifytplidx;
                        this.info.notifytpltitle = this.info.notifyTempItemList[0].notifytpltitle;
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 전체 '상품상세설명 참고'로 표기
        setAllRefDetail: function(value) {
            if (value) {
                this.info.notifyTempItemList.forEach(item => {
                    item.notifydata = '상품상세설명 참고';
                });
            }
        },
        // 대상 변경 체크시 kc인증목록, 첨부파일 모두 체크
        checkKcChecked: function(value) {
            let ischecked = value? 'T':'F';
            if (this.info.kcdivtype == this.$store.getters['ADMIN'].KC_DIV_TYPE_KDT002) {
                this.chkObject.kccert.ischecked = ischecked;
                this.chkObject.kcfile.ischecked = ischecked;
            }
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
                    } else {
                        alert(result.message);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // KC 인증목록 추가
        addKccertItem: function(index) {
            let params = { certno: '', iscert: '' };
            this.info.kccertlist.splice(index+1, 0, params);
        },
        // KC 인증목록 삭제
        removeKccertItem: function(index) {
            this.info.kccertlist.splice(index, 1);
        },
        // 첨부파일(탐색기 열기)
        fileAttach: function(fileTypeKey) {
            this.$refs[fileTypeKey].click();
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
            }
        },
        // 파일삭제
        removeFile(fileTypeKey, index) {
            if (fileTypeKey.indexOf('kcFile') > -1) {
                this.files.kcfilelist.splice(index, 1);
                this.$refs.kcFile.value = '';
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
                url = this.imgPreview.kcfilelist[index];
                this.viewFile(url);
            }
        }
    },
    watch: {
        // KC인증대상구분
        'info.kcdivtype': function(value) {
            if(value != this.$store.getters['ADMIN'].KC_DIV_TYPE_KDT002) {
                this.info.kccertlist = [];
                this.files.kcfilelist = [];
                this.imgPreview.kcfilelist = [];
                this.chkObject.kccert.ischecked = 'F';
                this.chkObject.kcfile.ischecked = 'F';
            } else {
                this.chkObject.kccert.ischecked = 'T';
                this.chkObject.kcfile.ischecked = 'T';
            }
            if (this.info.kccertlist.length == 0) {
                let param = { iscert: '' };
                this.info.kccertlist.splice(0, 0, param);
            }
        },
    }
}
</script>