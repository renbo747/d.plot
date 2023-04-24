<template>
  <!-- 기획전 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>기획전 등록</h2>
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
                            <dt>제목<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.subject" style="width: 100%" placeholder="기획전 제목" ref="subject"/></dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" v-model="info.expdesc" style="width: 100%" placeholder="기획전 설명" /></dd>
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
                                <th>진행기간<i class="essential"></i></th>
                              <CommonDatePickerFromTo
                                  :fromYYYYMMDD="info.startDate"
                                  :fromHH="info.startHour"
                                  :fromMM="info.startMi"
                                  :toYYYYMMDD="info.endDate"
                                  :toHH="info.endHour"
                                  :toMM="info.endMi"
                                  :useFrom="true"
                                  :useTo="true"
                                  @getDate="pickerEventBus"
                              />
<!--                                <td>-->
<!--                                    <CommonDatePicker :value="info.startDate" @change="onChangeStartDate"/>-->
<!--                                    <select style="width: 60px;" v-model="info.startHour">-->
<!--                                        <option v-for="(n, index) in 24" :key="index" :value="($util.lpad(index+'',2, 0))">{{ ($util.lpad(index+"", 2, 0))+"시" }}</option>-->
<!--                                    </select>-->
<!--                                    <select style="width: 60px;" v-model="info.startMi">-->
<!--                                        <option value="">입력</option>-->
<!--                                        <option v-for="(n,index) in 6" :key="index" :value="$util.lpad(index*10+'',2, 0)">{{$util.lpad(index*10+'',2, 0)}}</option>-->
<!--                                    </select>-->
<!--                                    <input type="text" v-model="info.startMi2" :disabled="info.startMi !== ''" class="ml3" style="width: 60px;" placeholder="MM">분-->
<!--                                    <span>~</span>-->
<!--                                    <CommonDatePicker :value="info.endDate" @change="onChangeEndDate"/>-->
<!--                                    <select style="width: 60px;" v-model="info.endHour">-->
<!--                                        <option v-for="(n, index) in 24" :key="index" :value="($util.lpad(index+'',2, 0))">{{ ($util.lpad(index+"", 2, 0))+"시" }}</option>-->
<!--                                    </select>-->
<!--                                    <select style="width: 60px;" v-model="info.endMi">-->
<!--                                        <option value="">입력</option>-->
<!--                                        <option v-for="(n, index) in 6" :key="index" :value="$util.lpad(index*10+'',2, 0)">{{$util.lpad(index*10+'',2, 0)}}</option>-->
<!--                                    </select>-->
<!--                                    <input type="text" v-model="info.endMi2" :disabled="info.endMi !== ''" class="ml3" style="width: 60px;" placeholder="MM">분-->
<!--                                </td>-->
                            </tr>
                            <tr>
                                <th>적용채널</th>
                                <td>
                                    <span>
                                            <input type="checkbox" v-model="info.isallmuappch" id="group11" true-value="T" false-value="F" @change="checkAllMuAppch" checked>
                                            <label for="group11">전체</label>
                                    </span>
                                    <span class="check-for" v-for="item in commonCode.MUAPPCHTYPE" :key="item.cmcode">
                                        <input type="checkbox" v-model="info.muappchtypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원유형</th>
                                <td>
                                    <span>
                                            <input type="checkbox" v-model="info.isallmumember" id="group21" true-value="T" false-value="F" @change="checkAllMuMemer" checked>
                                            <label for="group21">전체</label>
                                    </span>
                                    <span class="check-for" v-for="item in commonCode.MUMEMBERTYPE" :key="item.cmcode">
                                        <input type="checkbox" v-model="info.mumembertypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원등급</th>
                                <td>
                                    <span>
                                            <input type="checkbox" v-model="info.isallmumemlv" id="group31" true-value="T" false-value="F" @change="checkAllMuMemLv" checked>
                                            <label for="group31">전체</label>
                                    </span>
                                    <span class="check-for" v-for="item in commonCode.MUMEMLVTYPE" :key="item.cmcode">
                                        <input type="checkbox" v-model="info.muMemLvtypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                                    </span>    
                                </td>
                            </tr>
                            <tr>
                                <th>부제목<i class="essential"></i></th>
                                <td><input type="text" v-model="info.subtitle" style="width: 100%;" placeholder="기획전 부제목" ref="subtitle"></td>
                            </tr>
                            <tr>
                                <th>메인노출여부<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide">
                                        <input type="radio" v-model="info.ismaindisp" name="group04" id="group41" value="T" checked/><label for="group41">노출</label>
                                        <input type="radio" v-model="info.ismaindisp" name="group04" id="group42" value="F"/><label for="group42">비노출</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대표이미지(PC)<i class="essential"></i></th>
                                <td>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['pcimgFile'])}">
                                            <img :src="imgPreview['pcimgFile']" alt="대표이미지(PC)" v-if="!$util.isNull(files['pcimgFile'])">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['pcimgFile'])" @click="fileAttach('pcimgFile')">파일 올리기</button>
                                        <input type="file" ref="pcimgFile" @change="handleFileUpload('pcimgFile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['pcimgFile'])" @click="fileAttach('pcimgFile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['pcimgFile'])" @click="removeFile('pcimgFile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대표이미지(모바일)<i class="essential"></i></th>
                                <td>
                                    <div class="mb10">
                                        <input type="checkbox" v-model="info.copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label for="copy-img">PC 대표 이미지를 복사</label>
                                    </div>
                                    <!-- 모바일 이미지-->
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" :class="[[info.copyimgcheck ? 'size200':'size460wide'],{'no-image': $util.isNull(files['mobileimgFile'])}]">
                                            <img :src="imgPreview['mobileimgFile']" alt="대표이미지(모바일)" v-if="!$util.isNull(files['mobileimgFile'])">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['mobileimgFile'])" @click="fileAttach('mobileimgFile')">파일 올리기</button>
                                        <input type="file" ref="mobileimgFile" @change="handleFileUpload('mobileimgFile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['mobileimgFile'])" @click="fileAttach('mobileimgFile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['mobileimgFile'])" @click="removeFile('mobileimgFile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text" v-show="!info.copyimgcheck">
                                        <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                    <div class="img-with-text text" v-show="info.copyimgcheck">
                                        <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>테마사용여부</th>
                                <td>
                                    <div class="radio_wrap wide">
                                        <input type="radio" v-model="info.isusetheme" name="group05" id="group51" value="T" checked/><label for="group51">사용함</label>
                                        <input type="radio" v-model="info.isusetheme" name="group05" id="group52" value="F" /><label for="group52">사용안함</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-for="(item, index) in info.themeList" :key="index">
                                <th v-show="index === 0 || info.isusetheme === 'T'">{{info.isusetheme === 'T' ? '테마 및' : ''}}대상상품<i class="essential"></i></th>
                                <td v-show="index === 0 || info.isusetheme === 'T'">
                                    <div class="dpb" style="margin-bottom: 10px;" v-if="info.isusetheme === 'T'">
                                        <input type="text" v-model="info.themeList[index].exhname" style="width: 462px;" placeholder="테마 명칭" />
                                        <button type="button" class="add" @click="addTheme"></button>
                                        <button type="button" v-if="info.themeList.length > 1" class="minus" @click="removeTheme(index)"></button>
                                    </div>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">적용대상 상품목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" @click="openGoodsAdditionPopup(index)">상품추가</button>
                                            <button type="button" class="btn red-line" @click="removeGoodsAddition(index)">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                            <colgroup>
                                                <col width="3%" /><!-- checkbox -->
                                                <col width="4%" /><!-- No -->
                                                <col width="6%" /><!-- 판매구분 -->
                                                <col width="10%" /><!-- 파트너사명 -->
                                                <col width="8%" /><!-- 상품코드 -->
                                                <col width="62px" /><!-- 이미지 -->
                                                <col width="" /><!-- 상품명 -->
                                                <col width="7%" /><!-- 판매가 -->
                                                <col width="10%" /><!-- 메인노출(5개) 지정 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" :id="'chkall'+index" v-model="info.themeList[index].isallchkgoods" @change="checkAllGoodsAddList($event.target.checked,index)" true-value="T" false-value="F" /></th>
                                                    <th>No</th>
                                                    <th>판매구분</th>
                                                    <th>파트너사명</th>
                                                    <th>상품코드</th>
                                                    <th colspan="2">상품명</th>
                                                    <th>판매가</th>
                                                    <th>메인노출(5개) 지정</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="info.themeList[index].goodsList.length > 0">
                                                <tr v-for="(row, i) in info.themeList[index].goodsList" :key="row.addidx">
                                                    <td><input type="checkbox" :id="'chk_'+index+'_'+i" v-model="row.ischecked" @change="checkGoodsAddList(index)"/></td>
                                                    <td>{{ i+1 }}</td>
                                                    <td>{{ row.ispbgoodsname}}</td>
                                                    <td>{{ row.dealername }}</td>
                                                    <td>{{ row.goodsno }}</td>
                                                    <td>
                                                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(row.fullpath)}">
                                                            <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                                                        </div>
                                                    </td>
                                                    <td class="left no-left">
                                                        <span class="small-txt">{{ row.fullcategoryname }}</span>
                                                        <p class="mg0">{{ row.goodsname }}</p>
                                                    </td>
                                                    <td class="right">{{ row.price }}</td>
                                                    <td><input type="checkbox" :id="'chkmain_'+index+'_'+i" v-model="row.ismain" @change="checkGoodsMainDisp(index, i, $event)"></td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="8">대상 상품이 없습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="bottom-group">
                                        <div class="btn-group left">
                                            <button type="button" class="btn black-line square" @click="moveRow('up',index)"><i class="arr-up"></i></button>
                                            <button type="button" class="btn black-line square" @click="moveRow('down',index)"><i class="arr-down"></i></button>
                                            <input type="text" v-model="info.themeList[index].num" class="ml3" style="width: 40px;">
                                            <span>행</span>
                                            <select v-model="info.themeList[index].dir" class="short">
                                                <option value="up">위로</option>
                                                <option value="down">아래로</option>
                                            </select>
                                            <button type="button" class="btn blue-line ml3"  @click="moveRow('',index)">이동</button>
                                            <span class="txt-orange ml10"><i class="icon-alert"></i>한 번에 한 개의 행만 이동할 수 있습니다.</span>
                                        </div>
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
    <!-- /기획전 등록 팝업 -->
</template>

<script>
// import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";

export default {
    name: 'admin.operation.display.exhregist',
    props: {
        commonCode : Object
    },
    components: {
      CommonDatePickerFromTo,
        // CommonDatePicker
    },
    data() {
        return {
            info: {
                reguserid: '',
                startDate: '',
                startHour: '',
                startMi: '',
                endDate: '',
                endHour: '',
                endMi: '',
                istrash: 'F',
                subject: '',
                expdesc: '',
                subtitle: '',
                ismaindisp: 'T',     // 메인노출여부
                isallmuappch: 'T',   // 적용채널 전체여부
                muappchtype: '',    // 적용채널
                muappchtypeArr: [], // 적용채널Array
                isallmumember: 'T',  // 대상회원유형 전체여부
                mumembertype: '',   // 대상회원유형
                mumembertypeArr: [],// 대상회원유형Array
                isallmumemlv: 'T',    // 대상회원등급전체여부
                muMemLvtype: '',    // 대상회원등급
                muMemLvtypeArr: [],    // 대상회원등급Array
                copyimgcheck: false,
                isusetheme: 'T',
                themeList: [
                    {
                        exhname:'',
                        isallchkgoods: false,
                        goodsList: [],
                        num: '1',
                        dir: 'up',
                    }
                ],
            },
            files: {
                pcimgFile: '',
                mobileimgFile: '',
            },
            imgPreview: {
                pcimgFile: '',
                mobileimgFile: '',
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
                let userInfo = this.$storage.getLocalStorage("ADMIN_USER");
                this.info.reguserid = userInfo.id;

                this.info.startDate = this.$util.getDate('-');
                this.info.startHour = '00'
                this.info.startMi = '00'
                this.info.endDate = this.$util.getDate('-');
                this.info.endHour = '23';
                this.info.endMi = '59';

                this.checkAllMuAppch();
                this.checkAllMuMemer();
                this.checkAllMuMemLv();
            }
                else {
                alert('페이지 접근 권한이 없습니다.');
                this.info = this.$options.data().info
                this.$emit('closeRegist');
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
        })
    },
    methods: {
        // datepicker callback
        pickerEventBus(data) {
          this.info.startDate = data.fromYYYYMMDD;
          this.info.startHour = data.fromHH;
          this.info.startMi = data.fromMM;
          this.info.expsttime = data.fromDate12;

          this.info.endDate = data.toYYYYMMDD;
          this.info.endHour = data.toHH;
          this.info.endMi = data.toMM;
          this.info.expedtime = data.toDate12;
        },
        onClose(){
            this.info = this.$options.data().info;
            this.files = this.$options.data().files;
            this.imgPreview = this.$options.data().imgPreview;
            this.$emit('closeRegist');
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(val) {
            this.info.startDate = val;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(val) {
            this.info.endDate = val;
        },
        checkAllMuAppch() {
            let isAllCheck = this.info.isallmuappch;
            this.info.muappchtypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUAPPCHTYPE){
                    this.info.muappchtypeArr.push(type.cmcode);
                }
            }
        },
        checkAllMuMemer() {
            let isAllCheck = this.info.isallmumember;
            this.info.mumembertypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUMEMBERTYPE){
                    this.info.mumembertypeArr.push(type.cmcode);
                }
            }
        },
        checkAllMuMemLv() {
            let isAllCheck = this.info.isallmumemlv;
            this.info.muMemLvtypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUMEMLVTYPE){
                    this.info.muMemLvtypeArr.push(type.cmcode);
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
            
            if(fileTypeKey === 'pcimgFile' && this.info.copyimgcheck){
                this.setSameAsPcepreImg();
            }
            // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
            if (fileTypeKey === 'mobileimgFile') {
                this.info.copyimgcheck = false;
            }
        },
        removeFile(fileTypeKey, index) {
            if(confirm('파일을 삭제 하시겠습니까?')){
                this.files[fileTypeKey] = '';
                this.imgPreview[fileTypeKey] = '';
                this.$refs[fileTypeKey].value = null;
                
                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey == 'mobileimgFile') {
                    this.info.copyimgcheck = false;
                }
            }
        },
        // PC 대표이미지와 동일하게 세팅
        setSameAsPcepreImg: function() {
            if (this.info.copyimgcheck) {
                this.files.mobileimgFile = this.files.pcimgFile;
                this.imgPreview.mobileimgFile = this.imgPreview.pcimgFile;
            } else {
                this.files.mobileimgFile = '';
                this.imgPreview.mobileimgFile = '';
            }
        },
        // 추가상품 팝업 오픈
        openGoodsAdditionPopup(index) {
            let targetList = [];
            targetList = this.info.themeList[index].goodsList;

            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, null,
                (result) => {
                    // 팝업에서 가져온 결과 추가상품 목록에 적용(이미 추가되어 있는 상품 제외)
                    let resultList = result.list;
                    for (let i=0; i<resultList.length; i++) {
                        let existCnt = targetList.filter(item => {
                            return item.goodsno === resultList[i].goodsno;
                        }).length;
                        if (existCnt === 0) {
                            resultList[i].ischecked = false;
                            resultList[i].sort = targetList.length;
                            resultList[i].ismaindisp = 'F';
                            resultList[i].ismain = false;
                            targetList.push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 상품 삭제
        removeGoodsAddition(index) {
            let targetList = [];
            
            targetList = this.info.themeList[index].goodsList;

            let delList = targetList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (delList.length === 0) {
                alert('삭제할 상품을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            delList.forEach(item => {
                let findIndex = targetList.indexOf(item);
                targetList.splice(findIndex, 1);
            });
            if (targetList.length === 0) {
                this.info.themeList[index].isallchkgoods = 'F'; 
            }

            for(let i=0;i<this.info.themeList[index].goodsList.length; i++){
                this.info.themeList[index].goodsList[i].sort = i;
            }
            
        },
        // 추가상품목록 전체체크
        checkAllGoodsAddList(value,index) {
            this.info.themeList[index].goodsList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 추가상품목록 개별체크
        checkGoodsAddList(index) {
            let checkedList = this.info.themeList[index].goodsList.filter(item => {
                return item.ischecked == true;
            });
            if (this.info.themeList[index].goodsList.length > checkedList.length){
                this.info.themeList[index].isallchkgoods = 'F';
            } else {
                this.info.themeList[index].isallchkgoods = 'T';
            }
        },
        addTheme(){
            let theme = {
                exhname: '',
                isallchkgoods: false,
                goodsList: [],
                num: '1',
                dir: 'up',
            }

            this.info.themeList.push(theme);
        },
        removeTheme(index){
            this.info.themeList.splice(index,1);
        },
        onSave(){
            // 저장 유효성 체크
            if(this.checkSaveValidation()){
                let params = {
                    // expsttime : this.getExhitDate('start'),
                    expsttime : this.info.expsttime,
                    // expedtime : this.getExhitDate('end'),
                    expedtime : this.info.expedtime,
                }
                if(this.info.istrash === 'F' && this.info.ismaindisp === 'T'){
                    this.$http.post('/admin/operation/display/exhibit/check', params)
                    .then(result => {
                        if(result.statusCode === 200){
                            if(result.data.check){
                                this.save();
                            }else{
                                alert("메인노출은 동일 전시기간내\n1건만 지정 할 수 있습니다(<사용> 상태에 한함)");
                                return;
                            }
                        }else {
                            alert("유효성 확인에 실패했습니다.");
                            return;
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }else{
                    this.save();
                }
            }
        },
        save(){
            let params = this.info;
            let files = [];
            if(!this.$util.isNull(this.files.pcimgFile)){
                files.push({key: 'pcimgFile', file: this.files.pcimgFile.file});
            }
            if(!this.$util.isNull(this.files.mobileimgFile)){
                files.push({key: 'mobileimgFile', file: this.files.mobileimgFile.file});
            }
            params.files = files;
            // params.expsttime = this.getExhitDate('start');
            // params.expedtime = this.getExhitDate('end');
            if(this.info.isusetheme === 'F'){
                let themelist = [];
                themelist.push(this.info.themeList[0]);
                themelist.exhname = '';
                this.info.themeList = themelist;
            }

            if(confirm("저장 하시겠습니까?")){
                this.$http.post('/admin/operation/display/exhibit/save', params)
                .then(result => {
                    if(result.statusCode === 200){
                        alert("저장이 완료되었습니다.");
                        this.onClose();
                    }else {
                        alert("저장에 실패했습니다.");
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        checkSaveValidation(){
            let checkResult = true;

            let valid = [
                {field: 'info.subject', type: 'I', name: '[기본정보] 제목', required: true},
                {field: 'info.subtitle', type: 'I', name: '[조건설정] 부제목', required: true},
                {field: 'info.subject', type: 'I', name: '[기본정보] 제목', required: true}
            ];
            let msg = '';

            msg = this.$util.validMsg(this.$data,valid);
            if(!this.$util.isNull(msg)){
                checkResult = false;
                alert(msg);
            }

            if(this.info.expsttime > this.info.expedtime){
                checkResult = false;
                alert("진행시작일자를 종료일자 이전으로 설정해주세요.");
            }

            if(this.files.pcimgFile === ''){
                checkResult = false;
                alert("[조건설정] 대표이미지(PC)를 등록해주세요.");
            }else if(this.files.mobileimgFile === ''){
                checkResult = false;
                alert("[조건설정] 대표이미지(모바일)을 등록해주세요.");
            }

            return checkResult;
        },
        getExhitDate(type){
            let time = '';
            if(type === 'start'){
                if(this.info.startMi === ''){
                    time = this.info.startDate.replaceAll('-','') + this.info.startHour + this.info.startMi;
                }else{
                    time = this.info.startDate.replaceAll('-','') + this.info.startHour + this.info.startMi; 
                }
            }else{
                if(this.info.endMi === ''){
                    time = this.info.endDate.replaceAll('-','') + this.info.endHour + this.info.endMi;
                }else{
                    time = this.info.endDate.replaceAll('-','') + this.info.endHour + this.info.endMi; 
                }
            }
            return time;
        },
        moveRow(dir, index){
            let num = 1;
            if(dir === ''){
                dir = this.info.themeList[index].dir;
                num = this.info.themeList[index].num;
            }

            let checkedList = [];
            checkedList = this.info.themeList[index].goodsList.filter(item => {
                return item.ischecked == true;
            });

            if(checkedList.length === 0){
                alert("행을 이동할 상품을 선택해주세요.");
                return;
            }else if(checkedList.length > 1){
                alert("하나의 상품만 선택해주세요.");
                return;
            }

            let oldIndex = checkedList[0].sort;
            if(dir === 'up'){
                if(oldIndex === 0){
                    return;
                }
                let newIndex = 0;
                if(oldIndex > num){
                    newIndex = oldIndex - num;
                }

                const tempList = JSON.parse(JSON.stringify(this.info.themeList[index].goodsList));
                const target = tempList.splice(oldIndex, 1)[0];
                tempList.splice(newIndex, 0, target);
                this.info.themeList[index].goodsList = tempList;
            }else if(dir === 'down'){
                if(oldIndex === this.info.themeList[index].goodsList.length -1){
                    return;
                }
                let newIndex = this.info.themeList[index].goodsList.length -1;
                if(oldIndex + Number(num) < newIndex){
                    newIndex = oldIndex + Number(num);
                }
                const tempList = JSON.parse(JSON.stringify(this.info.themeList[index].goodsList));
                const target = tempList.splice(oldIndex, 1)[0];
                tempList.splice(newIndex, 0, target);
                this.info.themeList[index].goodsList = tempList;
            }

            for(let i=0;i<this.info.themeList[index].goodsList.length; i++){
                this.info.themeList[index].goodsList[i].sort = i;
            }
        },
        checkGoodsMainDisp(index, row, event){
            let checkedList = [];
            let cnt = 0;
            let target;
            target = this.info.themeList[index].goodsList[row];
            this.info.themeList.forEach(info => cnt += info.goodsList.filter(item => {
                return item.ismain == true;
            }).length);
            if(cnt > 5){
                alert("메인노출은 테마 구분없이 5개의 상품만 지정할 수 있습니다");
                event.target.checked = false;
                target.ismain = false;
                target.ismaindisp = 'F';
                return;
            }
            
            target.ismaindisp = event.target.checked ? 'T' : 'F';
        }
    },
    watch: {
        // 'info.startMi': function(value) {
        //     if (this.$util.isNull(value)) return;
        //     return this.info.startMi2 = value;
        // },
        // 'info.endMi': function(value) {
        //     if (this.$util.isNull(value)) return;
        //     return this.info.endMi2 = value;
        // },
        // 'info.startMi2': function(value) {
        //     if (this.$util.isNull(value)) return;
        //     return this.info.startMi2 = value.replace(/(^[6-9]|[^0-9])/gi, '');
        // },
        // 'info.endMi2': function(value) {
        //     if (this.$util.isNull(value)) return;
        //     return this.info.endMi2 = value.replace(/(^[6-9]|[^0-9])/gi, '');
        // },
        'info.muappchtypeArr': function(value){
            if (value.length < this.commonCode.MUAPPCHTYPE.length) {
                this.info.isallmuappch = 'F';
            } else {
                this.info.isallmuappch = 'T';
            }
            this.info.muappchtype = this.info.muappchtypeArr.join();
        },
        'info.mumembertypeArr': function(value){
            if (value.length < this.commonCode.MUMEMBERTYPE.length) {
                this.info.isallmumember = 'F';
            } else {
                this.info.isallmumember = 'T';
            }
            this.info.mumembertype = this.info.mumembertypeArr.join();
        },
        'info.muMemLvtypeArr': function(value){
            if (value.length < this.commonCode.MUMEMLVTYPE.length) {
                this.info.isallmumemlv = 'F';
            } else {
                this.info.isallmumemlv = 'T';
            }
            this.info.muMemLvtype = this.info.muMemLvtypeArr.join();
        },
        'info.themeList': {
                deep: true,
                handler(value) {
                value.forEach(item => {
                    item.num = item.num.replace(/([^0-9])/gi, '');
                    if(item.num === ''){
                        item.num = '1';
                    }
                });
            },
        },
    }
   
}
</script>

<style>

</style>