<template>
    <!-- A/S문의 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1100px;">
            <div class="pop-header">
                <h2>A/S 문의 상세</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="as-area">
                    <div class="title orange"><i class="icon-q"></i>질문</div>
                    <div class="bd-wrap">
                        <table cellpadding="0" cellspacing="0">
                            <caption>파트너사 문의 - 질문</caption>
                            <colgroup>
                                <col width="10%">
                                <col width="14%">
                                <col width="10%">
                                <col width="15%">
                                <col width="10%">
                                <col width="15%">
                                <col width="10%">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>주문번호</th>
                                    <td>{{ info.ordno }}</td>
                                    <th>송장번호</th>
                                    <td>{{ info.invoiceno }}</td>
                                    <th>전송여부</th>
                                    <td>{{ info.ispassname }}</td>
                                    <th>접수일시</th>
                                    <td>{{ info.regdate }}</td>
                                </tr>
                                <tr v-if="isAdmin">
                                    <th>고객명</th>
                                    <td>{{ info.username }}</td>
                                    <th>아이디</th>
                                    <td>{{ info.userid }}</td>
                                    <th>회원유형</th>
                                    <td>{{ info.membertypename }}</td>
                                    <th>회원등급</th>
                                    <td>{{ info.memlvtypename }}</td>
                                </tr>
                                <tr v-if="isAdmin">
                                    <th>접수자</th>
                                    <td><input type="text" style="width: 100%;" v-model="info.writer"/></td>
                                    <th>연락처</th>
                                    <td><input type="text" style="width: 100%;" v-model="info.tel"/></td>
                                    <th>주소</th>
                                    <td colspan="3">
                                        <button type="button" class="btn blue-line" @click="searchAddress">주소검색</button>
                                        <input type="text" class="ml3" style="width: 55px;" readonly v-model="info.post">
                                        <input type="text" class="ml3" style="width: 200px;" readonly v-model="info.addrroad">
                                        <input type="text" class="ml3" style="width: calc(100% - 330px);" v-model="info.addrdetail">
                                    </td>
                                </tr>
                                <tr>
                                    <th>상품명</th>
                                    <td colspan="7">{{ info.goodsname }}<br>옵션 : {{ info.optionconts }}</td>
                                </tr>
                                <tr class="contents">
                                    <th>A/S접수내용</th>
                                    <td colspan="7"><div v-html="info.content"/></td>
                                </tr>
                                <tr>
                                    <th>첨부이미지</th>
                                    <td colspan="3">
                                        <div v-for="(item, index) in imgFileList" :key="index">
                                            <a href="javascript:void(0);" class="file-link" @click="imageView(item.fullpath)">{{ item.imgforiname }}</a>
                                        </div>
                                    </td>
                                    <th>첨부동영상</th>
                                    <td colspan="3">
                                        <div v-for="(item, index) in videoFileList" :key="index">
                                            <a href="javascript:void(0);" class="file-link" @click="showVideo(index)">{{ item.imgforiname }}</a>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="as-area">
                    <div class="title blue"><i class="icon-a"></i>답변</div>
                    <div class="bd-wrap">
                        <table cellpadding="0" cellspacing="0">
                            <caption>파트너사 문의 - 답변</caption>
                            <colgroup>
                                <col width="10%">
                                <col width="23%">
                                <col width="10%">
                                <col width="23%">
                                <col width="10%">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>판매구분</th>
                                    <td>{{ info.ispbgoodsname }}</td>
                                    <th>파트너사</th>
                                    <td>{{ info.dealername }}</td>
                                    <th>상태</th>
                                    <td>{{ info.asstatusname }}</td>
                                </tr>
                                <tr>
                                    <th>답변자</th>
                                    <td>{{ info.repname }}</td>
                                    <th>아이디</th>
                                    <td>{{ info.repid }}</td>
                                    <th>답변일시</th>
                                    <td>{{ info.repregdate }}</td>
                                </tr>
                                <tr>
                                    <th>답변템플릿</th>
                                    <td colspan="5">
                                        <select style="width: 800px;" v-model="info.tplidx" @change="changeRepTemplate">
                                            <option value="">자주 쓰는 답변 선택</option>
                                            <option v-for="item in repTemplateList" :key="item.tplidx" :value="item.tplidx">{{ item.subject }}</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr class="contents">
                                    <th>답변내용</th>
                                    <td colspan="5">
                                        <CommonEditor ref="aseditor"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="save" v-if="info.asstatus === $store.getters['ADMIN'].AS_RECEIPT">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
        <CommonVideoView ref="videoPopup" v-if="isShowVideoPopup" :videoInfo="videoInfo" @close="closeVideoPopup" />
    </div>
    <!-- / A/S문의 상세 팝업-->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
import CommonVideoView from '@/views/admin/common/popup/CommonVideoView';

export default {
    name: 'admin.cs.as.detail',
    components: {
        CommonEditor,
        CommonVideoView
    },
    props: ['activeAsIdx'],
    mounted() {
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 초기화
                    this.onInit();
                }
            }).catch(error => {
                this.$util.debug(error);
            });
    },
    data() {
        return {
            isAdmin: this.$util.isAuthorized('ADMIN_USER'),
            info: {
                asidx: '',
                tplidx: ''
            },
            imgFileList: [],
            videoFileList: [],
            repTemplateList: [],
            user: {},
            isRead : false,
            isWrite : false,
            videoInfo: {},
            isShowVideoPopup: false
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            this.info.asidx = this.activeAsIdx;
            this.user = this.isAdmin? this.$util.getUser('ADMIN_USER') : this.$util.getUser('PARTNER_USER');
            // A/S문의 상세내용 조회
            this.getAsInquiryInfo();
        },
        // A/S문의 상세내용 조회
        getAsInquiryInfo: function() {
            this.$http.post('/admin/cs/as/detail', this.info)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.info = data.info;
                    this.repTemplateList = data.reptemplatelist;
                    this.imgFileList = data.imgfilelist;
                    this.videoFileList = data.videofilelist;
                    this.$refs.aseditor.content = this.info.repcontent;
                    this.info.ispass = '';

                    // 답변자 정보 없으면 로그인한 사용자 정보 세팅
                    if (this.$util.isNull(this.info.repid)) {
                        this.info.repid = this.user.id;
                        this.info.repname = this.user.name;
                        this.info.repuserno = this.user.no;
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 답변템플릿 변경
        changeRepTemplate: function() {
            let tplidx = this.info.tplidx;
            this.repTemplateList.forEach(item => {
                if(item.tplidx === tplidx) {
                    this.$refs.aseditor.content = item.content;
                }
            });
        },
        // 주소검색
        searchAddress: function() {
            new window.daum.Postcode({
                oncomplete: (data) => {
                    this.$set(this.info, 'post', data.zonecode);
                    this.$set(this.info, 'addrroad', this.$util.isNull(data.roadAddress)? data.autoRoadAddress : data.roadAddress);
                    this.$set(this.info, 'addr', this.$util.isNull(data.jibunAddress)? data.autoJibunAddress : data.jibunAddress);
                    this.$set(this.info, 'sigungucode', data.sigunguCode);
                    this.$set(this.info, 'buildingcode', data.buildingCode);
                    this.$set(this.info, 'roadnamecode', data.roadnameCode);
                    this.$set(this.info, 'addrdetail', '');
                }
            }).open();
        },
        // 이미지 미리 보기
        imageView(url) {
            this.$viewerApi({
                images: [url]
            })
        },
        // 동영상뷰어 팝업 열기
        showVideo(index) {
            this.videoInfo = this.videoFileList[index];
            this.isShowVideoPopup = true;
        },
        // 동영상뷰어 팝업 닫기
        closeVideoPopup() {
            this.isShowVideoPopup = false;
        },
        // 저장
        save: function() {
            // 에디터 답변내용 세팅
            this.info.repcontent = this.$refs.aseditor.content;

            // 필수입력항목 체크
            let valid = [
                {field: 'info.writer'       , type: 'I', name:'접수자', required: true},
                {field: 'info.tel'          , type: 'I', name:'연락처', required: true},
                {field: 'info.post'         , type: 'I', name:'주소', required: true},
                {field: 'info.addrroad'     , type: 'I', name:'주소', required: true},
                {field: 'info.repcontent'   , type: 'I', name:'답변내용', required: true}
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return;
            }
            if(this.$util.isNull(this.info.repcontent.trim())) {
                alert('답변내용은 필수입력 항목입니다.');
                return;
            }
            
            if (confirm("저장 하시겠습니까?")) {
                let params = Object.assign(this.info, this.user);
                this.$http.post('/admin/cs/as/save', params)
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
        // 팝업 닫기
        onClose: function(isreload) {
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closeDetail', isreload);
            } else {
                this.$emit('closeDetail');
            }
        }
    }
}
</script>
