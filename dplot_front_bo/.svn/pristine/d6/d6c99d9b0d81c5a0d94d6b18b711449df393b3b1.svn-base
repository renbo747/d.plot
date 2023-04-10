<template>
    <!-- 이미지정보 일괄변경 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>이미지정보 일괄변경</h2>
                <button type="button" class="pop-close" @click="$emit('closePopup');"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">총 {{ goodsnoList.length }}개 상품의 이미지정보를 일괄 변경합니다.</div>
                <div class="clearfix mt10">
                    <div class="bar-title fl">이미지정보
                        <span class="txt-orange ml10"><i class="icon-alert"></i>수정을 원하는 항목을 체크하신 후 일괄변경 하시기 바랍니다.</span>
                    </div>
                </div>
                <div class="form-area mt10" style="display: block;">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="175px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th><input type="checkbox" id="ischkpcrepre" v-model="chkObject.pcrepre.ischecked" true-value="T" false-value="F"><label for="ischkpcrepre">대표이미지(PC)</label></th>
                                <td ref="repreimgtd">
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['pcrepreimgfile'])}">
                                            <img :src="imgPreview['pcrepreimgfile']" alt="대표이미지(PC)"
                                                v-if="!$util.isNull(files['pcrepreimgfile']) && files['pcrepreimgfile'].status != 'N'" >
                                            <img :src="files['pcrepreimgfile'].fullpath" alt="대표이미지(PC)" 
                                                v-if="!$util.isNull(files['pcrepreimgfile']) && files['pcrepreimgfile'].status == 'N'" >
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['pcrepreimgfile'])" @click="fileAttach('pcrepreimgfile')">파일 올리기</button>
                                        <input type="file" ref="pcrepreimgfile" @change="handleFileUpload('pcrepreimgfile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['pcrepreimgfile'])" @click="fileAttach('pcrepreimgfile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['pcrepreimgfile'])" @click="removeFile('pcrepreimgfile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*720 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkadd" v-model="chkObject.add.ischecked" true-value="T" false-value="F"><label for="ischkadd">추가이미지</label></th>
                                <td>
                                    <input type="file" id="addimgFile" ref="addimgFile" @change="handleFileUpload('addimgFile', $event.target)" 
                                        accept="image/jpeg, image/png" hidden multiple/>
                                    <div>
                                        <div class="img-with-text" style="width: 202px;" v-for="(index, n) in 5" :key="index">
                                            <div class="img-thumb size200 no-image" v-if="$util.isNull(files['addimgfilelist'][n])"></div>
                                            <div class="img-thumb size200" v-else>
                                                <img :src="imgPreview['addimgfilelist'][n]" :alt="'추가이미지'+n">
                                            </div>
                                            <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                v-if="$util.isNull(files['addimgfilelist'][n])" @click="fileAttach('addimgFile')">파일 올리기</button>
                                            <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                v-if="!$util.isNull(files['addimgfilelist'][n])" @click="fileAttach('changeaddimgfile'+n)">변경</button>
                                            <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                v-if="!$util.isNull(files['addimgfilelist'][n])" @click="removeFile('addimgFile', n)">삭제</button>
                                            <input type="file" :id="'changeaddimgfile'+n" :ref="'changeaddimgfile'+n" @change="handleFileUpload('changeaddimgfile', $event.target, n)" 
                                                accept="image/jpeg, image/png" hidden/>
                                        </div>
                                    </div>
                                    <div class="mt20">
                                        <div class="img-with-text" style="width: 202px;" v-for="(index, n) in 2" :key="index+5">
                                            <div class="img-thumb size200 no-image" v-if="$util.isNull(files['addimgfilelist'][n+5])"></div>
                                            <div class="img-thumb size200" v-else>
                                                <img :src="imgPreview['addimgfilelist'][n+5]" :alt="'추가이미지'+(n+5)">
                                            </div>
                                            <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                v-if="$util.isNull(files['addimgfilelist'][n+5])" @click="fileAttach('addimgFile')">파일 올리기</button>
                                            <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                v-if="!$util.isNull(files['addimgfilelist'][n+5])" @click="fileAttach('changeaddimgfile'+(n+5))">변경</button>
                                            <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                v-if="!$util.isNull(files['addimgfilelist'][n+5])" @click="removeFile('addimgFile', n+5)">삭제</button>
                                            <input type="file" :id="'changeaddimgfile'+(n+5)" :ref="'changeaddimgfile'+(n+5)" @change="handleFileUpload('changeaddimgfile', $event.target, n+5)" 
                                                accept="image/jpeg, image/png" hidden/>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkmorepre" v-model="chkObject.morepre.ischecked" true-value="T" false-value="F"><label for="ischkmorepre">대표이미지(모바일)</label></th>
                                <td>
                                    <div class="mb10">
                                        <input type="checkbox" id="typical-same" @change="setSameAsPcepreImg" v-model="info.issamepcimg"><label for="typical-same">PC 대표 이미지와 동일</label>
                                    </div>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['morepreimgfile'])}">
                                            <img :src="imgPreview['morepreimgfile']" alt="대표이미지(모바일)" class="fit"
                                                v-if="!$util.isNull(files['morepreimgfile']) && files['morepreimgfile'].status != 'N'">
                                            <img :src="files['morepreimgfile'].fullpath" alt="대표이미지(모바일)" class="fit"
                                                v-if="!$util.isNull(files['morepreimgfile']) && files['morepreimgfile'].status == 'N'">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['morepreimgfile'])" @click="fileAttach('morepreimgfile')">파일 올리기</button>
                                        <input type="file" ref="morepreimgfile" @change="handleFileUpload('morepreimgfile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['morepreimgfile'])" @click="fileAttach('morepreimgfile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['morepreimgfile'])" @click="removeFile('morepreimgfile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*720 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischknotice" v-model="chkObject.notice.ischecked" true-value="T" false-value="F"><label for="ischknotice">공지이미지</label></th>
                                <td>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="isusenoticeD" id="isusenoticeDF" v-model="info.isusenotice" value="F">
                                        <label for="isusenoticeDF">미사용</label>
                                        <input type="radio" name="isusenoticeD" id="isusenoticeDT" v-model="info.isusenotice" value="T"/>
                                        <label for="isusenoticeDT">사용</label>
                                    </div>
                                    <div class="mt10" v-show="info.isusenotice=='T'">
                                        <common-editor ref="noticeeditor" :style-object="styleObject"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkintro" v-model="chkObject.intro.ischecked" true-value="T" false-value="F"><label for="ischkintro">인트로이미지</label></th>
                                <td>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="isuseintroD" id="isuseintroDT" v-model="info.isuseintro" value="F"/>
                                        <label for="isuseintroDT">미사용</label>
                                        <input type="radio" name="isuseintroD" id="isuseintroDF" v-model="info.isuseintro" value="T"/>
                                        <label for="isuseintroDF">사용</label>
                                    </div>
                                    <div class="mt10" v-show="info.isuseintro=='T'">
                                        <common-editor ref="introeditor" :style-object="styleObject"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkpcdetail" v-model="chkObject.pcdetail.ischecked" true-value="T" false-value="F"><label for="ischkpcdetail">PC용 상품상세설명</label></th>
                                <td>
                                    <div>
                                        <common-editor ref="pceditor" :style-object="styleObject"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkmodetail" v-model="chkObject.modetail.ischecked" true-value="T" false-value="F"><label for="ischkmodetail">모바일용 상품상세설명</label></th>
                                <td>
                                    <div class="mb10">
                                        <!-- <input type="checkbox" id="pc-same" @change="setSameAsPcDetailContrent" v-model="info.issamepccont"><label for="pc-same">PC용 상품상세설명과 동일</label> -->
                                        <button type="button" class="btn blue-line" @click="setSameAsPcDetailContrent">PC용 상품상세설명 복사</button>
                                    </div>
                                    <div class="mt10">
                                        <common-editor ref="mobileeditor" :style-object="styleObject"/>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="openConfirmPopup">일괄변경</button>
                    <button type="button" class="btn big darkgray" @click="$emit('closePopup');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /이미지정보 일괄변경 팝업 -->
</template>

<script>
import CommonEditor from '@views.admin/common/CommonEditor.vue';
import GoodsChangeConfirmPopup from '@views.admin/goods/popup/GoodsChangeConfirmPopup.vue';

export default {
    name: 'admin.goods.manage.goodsChangeImageInfo',
    props : ['checkedList', 'ckey'],
    components: {
        CommonEditor
    },
    mounted() {
        // 초기화
        this.onInit();
    },
    data() {
        return {
            goodsnoList: [],    // 상품번호 목록
            chkObject: {        // 체크항목 목록
                pcrepre: { key: 'pcrepre', name:'대표이미지', ischecked:'F' },
                add: { key: 'add', name:'추가이미지', ischecked:'F' },
                morepre: { key: 'morepre', name:'모바일이미지', ischecked:'F' },
                notice: { key: 'notice', name:'공지이미지', ischecked:'F' },
                intro: { key: 'intro', name:'인트로이미지', ischecked:'F' },
                pcdetail: { key: 'pcdetail', name:'PC용 상품상세설명', ischecked:'F' },
                modetail: { key: 'modetail', name:'모바일용 상품상세설명', ischecked:'F' }
            },
            files: {        // 파일
                pcrepreimgfile: null,   // PC대표이미지
                morepreimgfile: null,   // 모바일대표이미지
                addimgfilelist: []      // 추가이미지
            },
            imgPreview: {   // 미리보기 URL
                pcrepreimgfile: '',     // PC대표이미지
                morepreimgfile: '',     // PC대표이미지
                addimgfilelist: []      // 추가이미지
            },
            info: {
                issamepcimg: false,     // PC이미지와 동일 여부
                issamepccont: false,    // PC용 상세설명과 동일 여부
                isusenotice: 'F',       // 공지이미지 사용여부
                isuseintro: 'F',        // 인트로이미지 사용여부
                noticecontent: '',      // 공지이미지 content
                introcontent: '',       // 인트로이미지 content
                pccontent: '',          // PC용상세설명 content
                mobilecontent: ''       // 모바일용상세설명 content
            },
            // 에디터 스타일 지정
            styleObject: {
                height: '200px'
            }
        }
    },
    methods: {
        // 화면초기화
        onInit: function() {
            // 넘어온 파라미터 세팅
            this.goodsnoList = this.checkedList;
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
                        if (result.isconfirm === 'T') {
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
            if (this.chkObject.pcrepre.ischecked === 'T' && !this.$util.isNull(this.files.pcrepreimgfile)) {
                files.push({key: constants.IMG_TYPE_GOODS_IMG_PC_B, file: this.files.pcrepreimgfile.file});
            }
            if (this.chkObject.morepre.ischecked === 'T' && !this.$util.isNull(this.files.morepreimgfile)) {
                files.push({key: constants.IMG_TYPE_GOODS_IMG_MO_B, file: this.files.morepreimgfile.file});
            }
            if (this.chkObject.add.ischecked === 'T') {
                for(let i=0; i<this.files.addimgfilelist.length; i++) {
                    files.push({key: constants['IMG_TYPE_GOODS_IMG_ADD_B'+(i+1)], file: this.files.addimgfilelist[i].file});
                }
            }
            
            // 에디터 텍스트 데이터 저장
            if (this.chkObject.notice.ischecked === 'T' && this.info.isusenotice=='T') {
                this.info.noticecontent = this.$refs.noticeeditor.content;
            } else {
                this.info.noticecontent = '';
            }
            if (this.chkObject.intro.ischecked === 'T' && this.info.isuseintro=='T') {
                this.info.introcontent = this.$refs.introeditor.content;
            } else {
                this.info.introcontent = '';
            }
            if (this.chkObject.pcdetail.ischecked === 'T') {
                this.info.pccontent = this.$refs.pceditor.content;
            }
            if (this.chkObject.modetail.ischecked === 'T') {
                this.info.mobilecontent = this.$refs.mobileeditor.content;
            }

            // 변경파라미터 세팅
            let params = {
                ckey: this.ckey,
                ispcreprechange: this.chkObject.pcrepre.ischecked,
                isaddchange: this.chkObject.add.ischecked,
                ismoreprechange: this.chkObject.morepre.ischecked,
                isnoticechange: this.chkObject.notice.ischecked,
                isintrochange: this.chkObject.intro.ischecked,
                ispcdetailchange: this.chkObject.pcdetail.ischecked,
                ismodetailchange: this.chkObject.modetail.ischecked,
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

            // 대표이미지
            if (this.chkObject.pcrepre.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.files.pcrepreimgfile)) {
                    alert('대표이미지를 올려주세요.');
                    checkResult = false;
                }
            }
            // // 추가이미지
            // if (checkResult && this.chkObject.add.ischecked === 'T') {
            //     // 필수체크
            //     if (this.files.addimgfilelist.length == 0) {
            //         alert('추가이미지를 올려주세요.');
            //         checkResult = false;
            //     }
            // }
            // 모바일이미지
            if (checkResult && this.chkObject.morepre.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.files.morepreimgfile)) {
                    alert('모바일이미지를 올려주세요.');
                    checkResult = false;
                }
            }
            // // 공지이미지
            // if (checkResult && this.chkObject.notice.ischecked === 'T') {
            //     // 필수체크
            //     if (this.info.isusenotice == 'T' && this.$util.isNull(this.$refs.noticeeditor.content)) {
            //         alert('공지이미지를 입력해주세요.');
            //         checkResult = false;
            //     }
            // }
            // // 인트로이미지
            // if (checkResult && this.chkObject.intro.ischecked === 'T') {
            //     // 필수체크
            //     if (this.info.isuseintro == 'T' && this.$util.isNull(this.$refs.introeditor.content)) {
            //         alert('인트로이미지를 입력해주세요.');
            //         checkResult = false;
            //     }
            // }
            // PC용 상품상세설명
            if (checkResult && this.chkObject.pcdetail.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.$refs.pceditor.content)) {
                    alert('PC용 상품상세설명을 입력해주세요.');
                    checkResult = false;
                }
            }
            // 모바일용 상품상세설명
            if (checkResult && this.chkObject.modetail.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.$refs.mobileeditor.content)) {
                    alert('모바일용 상품상세설명을 입력해주세요.');
                    checkResult = false;
                }
            }

            return checkResult;
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
                let extension = ['jpg','jpeg', 'png'];
                let filename = files[0].name;
                let fileExe = this.$util.isNull(filename)? '' : filename.split(".")[1];
                if(!extension.includes(fileExe)){
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

                this.$set(this.imgPreview.addimgfilelist, index, URL.createObjectURL(fileObj.file));
                this.files.addimgfilelist[index] = fileObj;
                //  초기화
                this.$refs['changeaddimgfile'+index].value = '';
            } else if (fileTypeKey.indexOf('addimg') > -1) {
                // 추가이미지
                let files = target.files;
                if (this.$util.isNull(files[0])) {
                    return;
                }
                let extension = ['jpg','jpeg', 'png'];
                let filename = files[0].name;
                let fileExe = this.$util.isNull(filename)? '' : filename.split(".")[1];
                if(!extension.includes(fileExe)){
                    alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                    this.$refs.addimgFile.value = null;
                    return false;
                }
                if(files[0].size > 10485760){
                    alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
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
            } else if (fileTypeKey.indexOf('repreimg') > -1) {
                // PC, 모바일 대표이미지
                let file = this.$refs[fileTypeKey];
                if (this.$util.isNull(file.files[0])) {
                    return;
                }
                let extension = ['jpg','jpeg', 'png'];
                let filename = file.files[0].name;
                let fileExe = this.$util.isNull(filename)? '' : filename.split(".")[1];
                if(!extension.includes(fileExe)){
                    alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                    file.value = null;
                    return false;
                }
                if(file.files[0].size > 10485760){
                    alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                    file.value = null;
                    return false;
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
                    this.info.issamepcimg = false;
                }
            }
        },
        // 파일삭제
        removeFile(fileTypeKey, index) {
            if (fileTypeKey.indexOf('addimg') > -1) {
                this.imgPreview.addimgfilelist.splice(index, 1);
                this.files.addimgfilelist.splice(index, 1);
                this.$refs.addimgFile.value = '';
            } else {
                this.imgPreview[fileTypeKey] = '';
                this.files[fileTypeKey] = '';
                this.$refs[fileTypeKey].value = '';
                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey == 'morepreimgfile') {
                    this.info.issamepcimg = false;
                }
            }
        },
        // PC 대표이미지와 동일하게 세팅
        setSameAsPcepreImg: function() {
            if (this.info.issamepcimg) {
                if (!this.$util.isNull(this.files.pcrepreimgfile)) {
                    this.files.morepreimgfile = Object.assign({}, this.files.pcrepreimgfile);
                    this.imgPreview.morepreimgfile = this.imgPreview.pcrepreimgfile;
                }
            } else {
                this.files.morepreimgfile = null;
                this.imgPreview.morepreimgfile = null;
            }
        },
        // PC 상품상세설명과 동일하게 세팅
        setSameAsPcDetailContrent: function() {
            // if (this.info.issamepccont) {
                this.$refs.mobileeditor.content = this.$refs.pceditor.content;
            // }
        },

    },
    watch: {
        
    }
}
</script>
