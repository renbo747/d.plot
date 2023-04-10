<template>
    <!-- 사은품 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>사은품 등록</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="bar-title">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>사용여부</dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" v-model="info.istrash" name="group00" id="group01" value="F" checked/><label for="group01">사용함</label>
                                    <input type="radio" v-model="info.istrash" name="group00" id="group02" value="T" /><label for="group02">사용안함</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>ERP오리지널명<i class="essential"></i></dt>
                            <dd>
                                <input type="text" v-model="info.erpgiftstr" style="width: calc(100% - 114px);" placeholder="ERP사은품" value="" disabled />
                                <button type="button" class="btn blue-line" @click="searchErpGift">{{info.erpgiftlist.length > 0 ? '연결('+info.erpgiftlist.length+')' : 'ERP사은품 연결'}}</button>
                            </dd>
                        </dl>
                        <dl>
                            <dt>사은품명<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.giftname" style="width: 100%" placeholder="" /></dd>
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
                                <th>총재고<i class="essential"></i></th>
                                <td>
                                    <input type="text" v-model="info.giftstockcnt" class="center" style="width: 100px;" value="1,000" readonly />
                                    <span class="ml3">개</span>
                                </td>
                            </tr>
                            <tr>
                                <th>대표이미지(PC)<i class="essential"></i></th>
                                <td>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" style="width: 190px; height: 190px;" :class="{'no-image': $util.isNull(files['pcimgfile'])}">
                                            <img style="width: 190px; height: 190px;" :src="imgPreview['pcimgfile']" alt="대표이미지(PC)" v-show="!$util.isNull(files['pcimgfile'])">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                v-show="$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">파일 올리기
                                        </button>
                                        <input type="file" ref="pcimgfile" @change="handleFileUpload('pcimgfile')"
                                                accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                v-show="!$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">변경
                                        </button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                v-show="!$util.isNull(files['pcimgfile'])" @click="removeFile('pcimgfile')">삭제
                                        </button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 190*190 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대표이미지(모바일)<i class="essential"></i></th>
                                <td>
                                    <div class="mb10">
                                        <input type="checkbox" v-model="copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label for="copy-img">PC 대표 이미지를 복사</label>
                                    </div>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" style="width: 270px; height: 270px;"
                                            :class="{'no-image': $util.isNull(files['mobileimgfile'])}">
                                            <img style="width: 270px; height: 270px;" :src="imgPreview['mobileimgfile']" alt="대표이미지(모바일)" v-show="!$util.isNull(files['mobileimgfile'])">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                v-show="$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">파일 올리기
                                        </button>
                                        <input type="file" ref="mobileimgfile" @change="handleFileUpload('mobileimgfile')"
                                                accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                v-show="!$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">변경
                                        </button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                v-show="!$util.isNull(files['mobileimgfile'])" @click="removeFile('mobileimgfile')">삭제
                                        </button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 270*270 / 용량: 10MB 이하 / 파일 : JPG,
                                            JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr> 
                                <th>내용(PC)<i class="essential"></i></th>
                                <td>
                                    <div>
                                        <CommonEditor ref="pcEditor"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>내용(모바일)<i class="essential"></i></th>
                                <td>
                                    <div class="mb10">
                                        <button type="button" class="btn blue-line" @click="copyByContent">PC 내용을 복사</button>
                                    </div>
                                    <div class="mt10">
                                        <CommonEditor ref="mobileEditor"/>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="onSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /사은품 등록 팝업 -->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
import SearchOriginalCodePopup from '@views.admin/goods/popup/SearchOriginalCodePopup.vue';
export default {
    name: 'admin.promotion.promotion.giftregist',
    components: {
        CommonEditor
    },
    data() {
        return {
            info: {
                istrash: 'F',           // 사용여부
                erpgiftlist: [],        // erp오리지널코드
                erpgiftstr: '',
                giftname: '',           // 사은품명
                giftstockcnt: '',       // 총재고
                reguserid: '',          // 작성자
                giftinfo: '',           // 내용
                giftinfomobile: '',     // 모바일 내용
            },
            copyimgcheck: false,        // pc이미지 복사여부
            files: {
                pcimgfile: '',          // pc이미지
                mobileimgfile: '',      // mobile이미지
            },
            imgPreview: {
                pcimgfile: '',          // pc이미지 미리보기
                mobileimgfile: '',      // mobile이미지 미리보기
            },
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                // 등록자id 세팅
                let userInfo = this.$storage.getLocalStorage("ADMIN_USER");
                this.info.nowuserid = userInfo.id;
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
        // 에디터 내용 가져오기
        setEditorText() {
            if (this.$util.isNull(this.$refs.pcEditor.content)) {
                this.info.giftinfo = ''
            } else {
                this.info.giftinfo = this.$refs.pcEditor.content;
            }

            if (this.$util.isNull(this.$refs.mobileEditor.content)) {
                this.info.giftinfomobile = ''
            } else {
                this.info.giftinfomobile = this.$refs.mobileEditor.content;
            }
        },
        // 첨부파일(탐색기 열기)
        fileAttach(fileTypeKey) {
            if (Array.isArray(this.$refs[fileTypeKey])) {
                this.$refs[fileTypeKey][0].click();
            } else {
                this.$refs[fileTypeKey].click();
            }
        },
        // 가져온 파일 세팅
        handleFileUpload(fileTypeKey, target) {
            // PC, 모바일 대표이미지
            let file = this.$refs[fileTypeKey];
            if (this.$util.isNull(file)) {
                return;
            }
            let fileType = ['image/png', 'image/jpeg', 'image/png'];
            if (!fileType.includes(file.files[0].type)) {
                alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                file.value = null;
                this.files[fileTypeKey] = '';
                return false;
            }
            if (file.files[0].size > 10485760) {
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
            this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);

            if (fileTypeKey === 'pcimgfile' && this.copyimgcheck) {
                this.setSameAsPcepreImg();
            }
            // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
            if (fileTypeKey === 'mobileimgfile') {
                this.copyimgcheck = false;
            }
        },
        // 파일 삭제
        removeFile(fileTypeKey, index) {
            if (confirm('파일을 삭제 하시겠습니까?')) {
                this.files[fileTypeKey] = '';
                this.imgPreview[fileTypeKey] = '';
                this.$refs[fileTypeKey].value = null;

                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey === 'mobileimgfile') {
                    this.copyimgcheck = false;
                }
            }
        },
        // PC 대표이미지와 동일하게 세팅
        setSameAsPcepreImg() {
            if (this.copyimgcheck) {
                this.files.mobileimgfile = this.files.pcimgfile;
                this.imgPreview.mobileimgfile = this.imgPreview.pcimgfile;
            } else {
                this.files.mobileimgfile = '';
                this.imgPreview.mobileimgfile = '';
            }
        },
        // PC 내용 복사
        copyByContent(value) {
            this.$refs.mobileEditor.content = this.$refs.pcEditor.content;
        },
        checkValidation() {
            this.setEditorText();

            if(this.info.erpgiftlist.length === 0) {
                alert("ERP사은품을 연결해주세요.");
                return false;
            }

            let msg = '';
            let valid = [
                {field: 'info.giftname', type: 'I', name: '사은품명', required: true},
                {field: 'info.giftinfo', type: 'I', name: '내용(PC)', required: true},
                {field: 'info.giftinfomobile', type: 'I', name: '내용(모바일)', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(this.$util.isNull(this.files.pcimgfile)){
                alert("대표이미지(PC)를 첨부해주세요.");
                return false;
            }
            if(this.$util.isNull(this.files.mobileimgfile)){
                alert("대표이미지(모바일)를 첨부해주세요.");
                return false;
            }

            return true;
        },
        onSave() {
            if(!this.checkValidation()){
                return;
            }

            if(confirm("저장하시겠습니까?")){
                let params = this.info;

                let files = [];

                files.push({key: 'pcimgfile', file: this.files.pcimgfile.file});
                files.push({key: 'mobileimgfile', file: this.files.mobileimgfile.file});
                params.files = files;

                this.$http.post('/admin/promotion/promotion/gift/save', params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("저장이 완료되었습니다.");
                        this.onClose(true);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            this.files = this.$options.data().files;
            this.imgPreview = this.$options.data().imgPreview;
            this.copyimgcheck = false;
            if(typeof(isreload) === 'boolean' && isreload) {
                this.$emit('closeRegist', true);
            } else {
                this.$emit('closeRegist');
            }
        },
        searchErpGift() {
            let param = { 
                linkedList: this.$util.isNull(this.info.erpgiftlist)? []:this.$util.deepClone(this.info.erpgiftlist)
            };
            this.$eventBus.$emit('modalShow', SearchOriginalCodePopup, param,
                (result) => {
                    this.info.erpgiftlist = result.list;
                    this.info.erpgiftstr = this.info.erpgiftlist.map(item => item.erpoptname).join(', ');
                }
            );
        }
    },
}
</script>

<style>

</style>