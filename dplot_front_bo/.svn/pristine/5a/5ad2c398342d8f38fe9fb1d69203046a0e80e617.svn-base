<template>
    <!-- 브랜드 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1200px;">
            <div class="pop-header">
                <h2>브랜드 등록</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="180px">
                            <col width="">
                            <col width="180px">
                            <col width="400px">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>브랜드 코드</th>
                                <td><input type="text" value="" disabled></td>
                                <th>등록일</th>
                                <td>{{info.regdate}}</td>
                            </tr>
                            <tr>
                                <th>카테고리</th>
                                <td colspan="3">
                                    <select v-model="info.cateidx" style="width: 250px;">
                                        <option value="">대분류</option>
                                        <option v-for="(row, i) in this.cateList" :key="i" :value="row.idx">{{row.value}}</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>사용여부</th>
                                <td colspan="3">
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="istrash1" id="rd111" v-model="info.istrash" value='F' checked/><label for="rd111">사용</label>
                                        <input type="radio" name="istrash1" id="rd122" v-model="info.istrash" value='T'/><label for="rd122">미사용</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>브랜드명(한글)<i class="essential"></i></th>
                                <td colspan="3"><input type="text" style="width: 100%;" v-model="info.name"></td>
                            </tr>
                            <tr>
                                <th>브랜드명(영문)</th>
                                <td colspan="3"><input type="text" style="width: 100%;" v-model="info.enname"></td>
                            </tr>
                            <tr>
                                <th>헤드카피</th>
                                <td colspan="3"><input type="text" style="width: 100%;" v-model="info.headcopy"></td>
                            </tr>
                            <tr>
                                <th>브랜드 상세설명</th>
                                <td colspan="3"><input type="text" style="width: 100%;" v-model="info.detail"></td>
                            </tr>
                            <tr>
                                <th>로고이미지</th>
                                <td colspan="3">
                                    <div class="img-with-text" style="width: 202px;">
                                    <div class="img-thumb" style="width: 320px; height: 320px;" :class="{'no-image': $util.isNull(files['brandimgfile'])}"><!-- 이미지 없는 경우 no-image 클래스 추가 -->
                                        <img style="width: 320px; height: 320px;" :src="imgPreview['brandimgfile']" alt="로고이미지" v-if="!$util.isNull(files['brandimgfile'])">
                                    </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['brandimgfile'])" @click="fileAttach('brandimgfile')">파일 올리기</button>
                                        <input type="file" ref="brandimgfile" @change="handleFileUpload('brandimgfile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['brandimgfile'])" @click="fileAttach('brandimgfile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['brandimgfile'])" @click="removeFile('brandimgfile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text" style="vertical-align: top;">
                                        <p class="txt-orange"><i class="icon-alert"></i>로고이미지는 아래 규격에 맞는 이미지로 올려주세요!</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 최대: 206*68 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>상세 콘텐츠(PC)</th>
                                <td colspan="3">
                                    <div class="img-with-text" style="width: 202px;">
                                    <div class="img-thumb" style="width: 620px; height: 240px;" :class="{'no-image': $util.isNull(files['pcimgfile'])}">
                                        <img style="width: 620px; height: 240px;" :src="imgPreview['pcimgfile']" alt="브랜드관 이미지(PC)" v-if="!$util.isNull(files['pcimgfile'])">
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
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 1240*480 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>상세 콘텐츠(모바일)</th>
                                <td colspan="3">
                                    <div class="mb10">
                                    <input type="checkbox" v-model="info.copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label for="copy-img">PC 대표 이미지를 복사</label>
                                </div>
                                <!-- 모바일 이미지-->
                                <div class="img-with-text" style="width: 202px;">
                                    <div class="img-thumb" style="width: 360px; height: 480px;" :class="{'no-image': $util.isNull(files['mobileimgfile'])}">
                                        <img style="width: 360px; height: 480px;" :src="imgPreview['mobileimgfile']" alt="브랜드관 이미지(모바일)" v-if="!$util.isNull(files['mobileimgfile'])">
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
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*960 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>연관컨텐츠</th>
                                <td colspan="3">
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">컨텐츠 목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" @click="addContent">추가</button>
                                            <button type="button" class="btn red-line" @click="removeContent">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c row-3div">
                                            <colgroup>
                                                <col width="3%"><!-- checkbox -->
                                                <col width="4%"><!-- No -->
                                                <col width=""><!-- 컨텐츠 및 링크 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th rowspan="3"><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                                                    <th rowspan="3">No</th>
                                                    <th>컨텐츠 및 링크</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="this.contentslist.length > 0">
                                                <template v-for="(row, n) in this.contentslist">
                                                <tr :key="'a'+n">
                                                    <td rowspan="3"><input type="checkbox" v-model="moveData.targetIdx" :id="row.reviewidx" :value="n" @change="checkList($event.target.checked)"/></td>
                                                    <td rowspan="3">{{n +1}}</td>
                                                    <td class="left">
                                                        <span class="dpib"  style="width: 100px;">제목</span>
                                                        <input type="text" v-model="contentslist[n].title" style="width: calc(100% - 250px);" placeholder="컨텐츠 제목(최대 30바이트)">
                                                    </td>
                                                </tr>
                                                <tr :key="'b'+n">
                                                    <td class="left">
                                                        <span class="dpib"  style="width: 100px;">링크(PC)</span>
                                                        <input type="text" v-model="contentslist[n].pclinkurl" style="width: calc(100% - 250px);" placeholder="컨텐츠 클릭 시 연결되는 PC 화면 주소">
                                                        <input type="checkbox" v-model="contentslist[n].ispcnwindow" true-value="T" false-value="F" :id="'groupb'+n" class="ml10"><label :for="'groupb'+n">새창</label>
                                                    </td>
                                                </tr>
                                                <tr :key="'c'+n">
                                                    <td class="left">
                                                        <span class="dpib"  style="width: 100px;">링크(모바일)</span>
                                                        <input type="text" v-model="contentslist[n].molinkurl" style="width: calc(100% - 250px);" placeholder="컨텐츠 클릭 시 연결되는 모바일 화면 주소">
                                                        <input type="checkbox" v-model="contentslist[n].ismonwindow" true-value="T" false-value="F"  :id="'groupc'+n" class="ml10"><label :for="'groupc'+n">새창</label>
                                                    </td>
                                                </tr>
                                                </template>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="bottom-group">
                                        <CommonArraySort :list-data="contentslist"
                                            :move-data="moveData"
                                            :is-active-save-btn="false"
                                            v-if="isWrite"
                                        />
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" v-if="isWrite" @click="goSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /브랜드 등록 팝업 -->
</template>

<script>
import CommonArraySort from "@views.admin/common/CommonArraySort";
export default {
    name : 'admin.goods.brand.brandRegist',
    props: ['cateList'],
    components: {
        CommonArraySort
    },
    data() {
        return {
            info : {
                regdate: '',    // 등록일
                cateidx: '',    // 카테고리idx
                istrash: 'F',   // 사용여부(T: 미사용, F: 사용)
                name: '',       // 브랜드명(한글)
                frstname: '',    // 한글 초성
                enname: '',    // 브랜드명(영문)
                frstename: '',    // 영어 초성
                headcopy: '',   // 헤드카피
                detail: '',     // 브랜드 상세설명 
                copyimgcheck: false,
            },
            contentslist: [],
            files: {
                brandimgfile: '',
                pcimgfile: '',
                mobileimgfile: '',
            },
            imgPreview: {
                brandimgfile: '',
                pcimgfile: '',
                mobileimgfile: '',
            },
            moveData: {                       // 노출순위 데이터
                moveValue: '',                  // 움직일 값
                targetIdx: [],                  // 대상 위치
                code: 'U',                      // 위, 아래 코드
                isSuccess: false,               // 저장 성공 여부 (** 중요)
            },
            isallchk: false,
            categoryList: this.cateList,
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {

            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');
      
            if(this.isRead){
                this.info.regdate = this.$util.getDate('-');
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
        onClose(isreload) {
            this.info = this.$options.data().info;
            this.files = this.$options.data().files;
            this.imgPreview = this.$options.data().imgPreview;
            // 팝업 닫기
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closeRegist', isreload);
            } else {
                this.$emit('closeRegist');
            }
        },
        goSave() {
            if(!this.checkValidation()) {
                return;
            } else {
                this.info.name = this.info.name.replace(/^\s*/, "");
                this.info.enname = this.info.enname.replace(/^\s*/, "");
                this.chosung();
                let params = this.info;
                let files = [];
                if(!this.$util.isNull(this.files.brandimgfile)) {
                    files.push({key: 'brandimgfile', file: this.files.brandimgfile.file});
                }
                if(!this.$util.isNull(this.files.pcimgfile)) {
                    files.push({key: 'pcimgfile', file: this.files.pcimgfile.file});
                }
                if(!this.$util.isNull(this.files.mobileimgfile)) {
                    files.push({key: 'mobileimgfile', file: this.files.mobileimgfile.file});
                }
                
                params.files= files;

                if(this.contentslist.length > 0){
                    params.contentslist = this.contentslist;
                }

                if(confirm("저장하시겠습니까?")){
                    this.$http.post("/admin/goods/brand/regist", params)
                    .then(result => {
                        if(result.statusCode === 200){
                            alert("저장되었습니다.");
                            this.onClose(true);
                        }else{
                            alert("저장에 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }
            }
        },
        checkValidation() {
            let valid = [];
            let msg = '';

            valid = [
                // {field: 'info.cateidx'      , type: 'S', name:'카테고리', required: true},
                {field: 'info.istrash'      , type: 'S', name:'사용여부', required: true},
                {field: 'info.name'         , type: 'I', name:'브랜드명(한글)', required: true},
                // {field: 'info.enname'      , type: 'I', name:'브랜드명(영문)', required: true},
                // {field: 'info.headcopy'     , type: 'I', name:'헤드카피', required: true},
                // {field: 'info.detail'       , type: 'I', name:'브랜드 상세설명', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            // if(this.$util.isNull(this.files.brandimgfile)) {
            //     alert("로고 이미지를 선택해주세요.");
            //     return false;
            // }
            // if(this.$util.isNull(this.files.pcimgfile)) {
            //     alert("상세 콘텐츠(PC)를 선택해주세요.");
            //     return false;
            // }
            // if(this.$util.isNull(this.files.mobileimgfile)) {
            //     alert("상세 콘텐츠(모바일)을 선택해주세요.");
            //     return false;
            // }

            return true;
        },
        chosung(){
            let cho = ["ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"];
            let resultHan = "";
            let resultEng = "";

            for(let i=0;i<this.info.name.length;i++) {
                let code = this.info.name.charCodeAt(i)-44032;
                if(code>-1 && code<11172) resultHan += cho[Math.floor(code/588)];
                else resultHan += this.info.name.charAt(i);
            }

            for(let i=0;i<this.info.enname.length;i++) {
                let code = this.info.enname.charCodeAt(i)-44032;
                if(code>-1 && code<11172) resultEng += cho[Math.floor(code/588)];
                else resultEng += this.info.enname.charAt(i);
            }

            this.info.frstname = resultHan.charAt(0);
            this.info.frstename = resultEng.charAt(0);
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
        handleFileUpload: function(fileTypeKey, target) {
            // PC, 모바일 대표이미지
            let file = this.$refs[fileTypeKey];
            if (this.$util.isNull(file)) {
                return;
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
                this.files[fileTypeKey] = '';
                this.imgPreview[fileTypeKey] = '';
                this.$refs[fileTypeKey].value = null;

                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey == 'mobileimgfile') {
                    this.info.copyimgcheck = false;
                }
            }
        },
        // PC 대표이미지와 동일하게 세팅
        setSameAsPcepreImg: function() {
            if (this.info.copyimgcheck) {
                this.files.mobileimgfile = this.files.pcimgfile;
                this.imgPreview.mobileimgfile = this.imgPreview.pcimgfile;
            } else {
                this.files.mobileimgfile = '';
                this.imgPreview.mobileimgfile = '';
            }
        },
        addContent() {
            let obj = {
                title: '',
                pclinkurl: '',
                ispcnwindow: 'F',
                molinkurl: '',
                ismonwindow: 'F',
                sortnum : this.contentslist.length
            }; 
            this.contentslist.push(obj);
        },
        removeContent() {
            if(this.moveData.targetIdx.length === 0) {
                alert("선택된 컨텐츠가 없습니다.");
                return ;
            }

            this.moveData.targetIdx.sort((a,b) => {
                if (a < b) {
                    return 1;
                } else { 
                    return -1;
                }
            })

            this.moveData.targetIdx.forEach(n => {
                this.contentslist.splice(n, 1);
            });
            this.moveData.targetIdx = [];
            if(this.contentslist.length === 0) {
                this.isallchk = false;
            }
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.moveData.targetIdx = [];
            if (value) {
                for (let i in this.contentslist) {
                    this.moveData.targetIdx[i] = i;
                }
            }
        },
         // 목록 개별체크
        checkList: function() {
            if (this.contentslist.length > this.moveData.targetIdx.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
    }
}
</script>