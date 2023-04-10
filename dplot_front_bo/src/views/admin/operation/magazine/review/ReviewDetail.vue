<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <PictureViewPopup
            ref="picturePopup"
            v-if="isPictureViewPopupShow"
            :img-path="imgPath"
            @close="closeUploadFilePopup"
        />
        <VideoView
            ref="videoPopup"
            v-if="isVideoViewPopupShow"
            :videoInfo="videoInfo"
            @close="closeVideoPopup"
        />
        <div class="modal-content" :style="detailShow || isGoodsDetailShow?'width: 1600px;' :'width: 1300px;'">
            <div class="pop-header">
                <h2>리뷰 상세</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix">
                    <div class="fl">
                        <span v-if="info.isbest === 'T'">베스트</span>
                        <span v-if="info.isblind === 'T'" :class="info.isbest === 'T'? 'left-bar' : ''">블라인드</span>
                    </div>
                    <div class="fr txt-gray">
                        <span>등록일 : {{info.regdate}}</span>
                        <span class="left-bar">수정일 : {{info.moddate}}</span>
                    </div>
                </div>
                <div class="bar-title mt10">작성자 정보</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="150px">
                            <col width="477px">
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>아이디</th>
                                <td>{{info.reguserid}}</td>
                                <th>이름</th>
                                <td><a class="link" @click="goDetail(info.userno)">{{info.regusername}}</a></td>
                            </tr>
                            <tr>
                                <th>회원유형</th>
                                <td>{{info.dadamembertype}}</td>
                                <th>회원등급</th>
                                <td>{{info.memlvtype}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="bar-title">상품 정보</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="150px">
                            <col width="477px">
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>판매구분</th>
                                <td colspan="3">{{goodsInfo.dealername === ''? goodsInfo.ispbgoodsname : goodsInfo.ispbgoodsname+'('+goodsInfo.dealername+')'}}</td>
                            </tr>
                            <tr>
                                <th>카테고리</th>
                                <td>{{goodsInfo.fullcategoryname}}</td>
                                <th>브랜드</th>
                                <td>{{goodsInfo.brandname}}</td>
                            </tr>
                            <tr>
                                <th>상품코드/상품명</th>
                                <td colspan="3"><a class="link" @click="goGoodsDetail(goodsInfo.goodsno)">{{goodsInfo.goodscode}}</a> / {{goodsInfo.goodsname}}</td>
                            </tr>
                            <tr>
                                <th>대표이미지</th>
                                <td colspan="3">
                                    <div class="img-thumb size200">
                                        <img :src="imgPreview['goodsimgfile']" alt="대표이미지">
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>상품요약설명</th>
                                <td colspan="3">{{goodsInfo.summary}}</td>
                            </tr>
                            <tr>
                                <th>적용채널</th>
                                <td>{{goodsInfo.muappchtype}}</td>
                                <th>담당MD</th>
                                <td>[{{goodsInfo.mdcode}}] {{goodsInfo.mdname}}</td>
                            </tr>
                            <tr>
                                <th>판매상태</th>
                                <td>{{goodsInfo.goodsselltypename}}</td>
                                <th>상품상태</th>
                                <td>{{goodsInfo.goodsdivtypename}}</td>
                            </tr>
                            <tr>
                                <th>전시기간</th>
                                <td colspan="3">{{goodsInfo.disdate}} ({{goodsInfo.disdaterange}})</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="bar-title">주문 정보</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="150px">
                            <col width="477px">
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>주문번호</th>
                                <td><a class="link" @click="goOrderDetail(orderInfo.ordno)">{{orderInfo.ordno}}</a></td>
                                <th>구매일자</th>
                                <td>{{orderInfo.orderdate}}</td>
                            </tr>
                            <tr>
                                <th>결제금액</th>
                                <td>{{orderInfo.totprice}}원</td>
                                <th>결제수량</th>
                                <td>{{orderInfo.ordcnt}}</td>
                            </tr>
                            <tr>
                                <th>옵션</th>
                                <td colspan="3">(구성) LG 드럼 세탁기 19L</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="bar-title">리뷰 정보</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="150px">
                            <col width="477px">
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>리뷰구분</th>
                                <td>{{files.reviewimgfile.length > 0 || !$util.isNull(files.reviewvideofile) ? '사진&동영상 리뷰' : '텍스트 리뷰'}}</td>
                                <th>좋아요 수</th>
                                <td>{{info.goodcnt}}</td>
                            </tr>
                            <tr>
                                <th>평점</th>
                                <td colspan="3">총상품평점 {{info.totpoint}} ( 디자인 {{info.designpoint}} | 배송 {{info.delivpoint}} | 가격 {{info.pricepoint}} | 편리성 {{info.convpoint}} )</td>
                                </tr>
                            <tr>
                                <th>내용</th>
                                <td colspan="3">{{info.content}}</td>
                            </tr>
                            <tr>
                                <th>사진</th>
                                <td colspan="3">
                                    <div>
                                        <div class="img-with-text" style="width: 202px;" v-if="files.reviewimgfile.length === 0">
                                            <div class="img-thumb size200 no-image"></div>
                                        </div>
                                        <div class="img-with-text" style="width: 202px;" v-for="(row,n) in files.reviewimgfile.length > 4 ? 4 : files.reviewimgfile.length" :key="row">
                                            <div class="img-thumb size200 no-image" v-if="$util.isNull(files['reviewimgfile'][n])"></div>
                                            <div class="img-thumb size200" v-else>
                                                <img :src="imgPreview['reviewimgfile'][n]" :alt="'리뷰이미지'+n" v-if="!$util.isNull(files['reviewimgfile'][n])" @click="showPicture(n)">
                                            </div>
                                            <a :href="imgPreview['reviewimgfile'][n]" download>
                                                <button type="button" class="btn blue-line mt10" style="width: 100%;" v-if="!$util.isNull(files['reviewimgfile'][n])">다운로드</button>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="mt20">
                                        <div class="img-with-text" style="width: 202px;" v-for="(row,n) in files.reviewimgfile.length < 8 ? files.reviewimgfile.length >= 4 ? files.reviewimgfile.length % 4 : 0 : 4" :key="row+4">
                                            <div class="img-thumb size200 no-image" v-if="$util.isNull(files['reviewimgfile'][n+4])"></div>
                                            <div class="img-thumb size200" v-else>
                                                <img :src="imgPreview['reviewimgfile'][n+4]" :alt="'리뷰이미지'+n" v-if="!$util.isNull(files['reviewimgfile'][n+4])" @click="showPicture(n+4)">
                                            </div>
                                            <a :href="imgPreview['reviewimgfile'][n+4]" download>
                                                <button type="button" class="btn blue-line mt10" style="width: 100%;" v-if="!$util.isNull(files['reviewimgfile'][n+4])">다운로드</button>
                                            </a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>동영상</th>
                                <td colspan="3">
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb size200 no-image" v-if="$util.isNull(files.videoimgfile)"></div>
                                        <div class="img-thumb size200" v-else>
                                            <img :src="imgPreview.videoimgfile" :alt="'리뷰동영상'" v-if="!$util.isNull(files.videoimgfile)" @click="showVideo">
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>신고여부</th>
                                <td>{{info.isnoti}}</td>
                                <th>블라인드</th>
                                <td>
                                    <div class="radio_wrap">
                                        <input type="radio" name="group01blind" id="group11blind" v-model="info.isblind" value="T"><label for="group11blind">지정</label>
                                        <input type="radio" name="group01blind" id="group12blind" v-model="info.isblind" value="F"><label for="group12blind">해지</label>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="bar-title">베스트 설정</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="150px">
                            <col width="477px">
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>베스트</th>
                                <td colspan="3">
                                    <div class="radio_wrap">
                                        <input type="radio" name="group00best" id="group01best" v-model="info.isbest" value="T"><label for="group01best">지정</label>
                                        <input type="radio" name="group00best" id="group02best" v-model="info.isbest" value="F"><label for="group02best">해지</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-show="info.isbest === 'T'">
                                <th>전시기간</th>
                                <td colspan="3">
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="checkObj.startYYYYMMDD"
                                        :fromHH="checkObj.startHH"
                                        :fromMM="checkObj.startMi"
                                        :toYYYYMMDD="checkObj.endYYYYMMDD"
                                        :toHH="checkObj.endHH"
                                        :toMM="checkObj.endMi"
                                        @getDate="changeStartDate"
                                    />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="bar-title">신고 정보</div>
                <div class="scroll-y" style="max-height: 300px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="5%" /><!-- No -->
                            <col width="10%" /><!-- 아이디 -->
                            <col width="10%" /><!-- 이름 -->
                            <col width="8%" /><!-- 유형 -->
                            <col width="8%" /><!-- 등급 -->
                            <col width="12%" /><!-- 신고일시 -->
                            <col width="20%" /><!-- 사유구분 -->
                            <col width="" /><!-- 기타사유 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>아이디</th>
                                <th>이름</th>
                                <th>유형
                                    <button type="button" v-if="isRead" 
                                    :value="sortData.hits"
                                    class="sort"
                                    :class="[{up : sortData.dadamembertype === 'dadamembertype_asc'}, {down : sortData.dadamembertype === 'dadamembertype_desc'}]"
                                    @click="sortToggle(sortData.dadamembertype)">
                                </button>    
                                </th>
                                <th>등급
                                    <button type="button" v-if="isRead" 
                                    :value="sortData.memlvtype"
                                    class="sort"
                                    :class="[{up : sortData.memlvtype === 'memlvtype_asc'}, {down : sortData.memlvtype === 'memlvtype_desc'}]"
                                    @click="sortToggle(sortData.memlvtype)">
                                </button>    
                                </th>
                                <th>신고일시</th>
                                <th>사유구분</th>
                                <th>기타사유</th>
                            </tr>
                        </thead>
                        <tbody v-if="this.info.notilist.length > 0">
                            <tr v-for="(row, index) in this.info.notilist" :key="index">
                                <td>{{row.no}}</td>
                                <td>{{row.userid}}</td>
                                <td>{{row.name}}</td>
                                <td>{{row.dadamembertype}}</td>
                                <td>{{row.memlvtype}}</td>
                                <td>{{row.regdate}}</td>
                                <td>{{row.notitype}}</td>
                                <td class="left">{{row.etcreason === '' ? '-' : row.etcreason}}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="8">신고 목록이 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
                <AdminMemberInfo v-if="detailShow" v-bind:activeUserNo="activeUserNo" v-on:closeDetail="closeDetail"></AdminMemberInfo>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="onSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import AdminMemberInfo from "@views.admin/member/info/AdminMemberInfo.vue";
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import VideoView from './popup/VideoView.vue';
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import PictureViewPopup from "@views.admin/promotion/event/popup/PictureViewPopup.vue";
export default {
    name: 'admin.operation.magazine.review.detail',
    props: ['reviewidx'],
    components: {
        AdminMemberInfo,
        GoodsDetail,
        CommonDatePickerFromTo,
        PictureViewPopup,
        VideoView
    },
    data() {
        return {
            info: {
                userno: '',
                reguserid: '',
                regusername: '',
                dadamembertype: '',
                memlvtype: '',
                ordno: '',
                orderdate: '',
                paytotprice: '',
                ordergoodscnt: '',
                goodsoption: '',
                reviewtype: '',
                goodcnt: '',
                isbest: '',
                designpoint: '',
                delivpoint: '',
                pricepoint: '',
                convpoint: '',
                totpoint: '',
                content: '',
                isnoti: '',
                isblind: '',
                notilist: [],
                regdate: '',
                moddate: '',
            },
            goodsInfo: {
                ispbgoodsname: '',
                dealername: '',
                fullcategoryname: '',
                brandname: '',
                goodscode: '',
                goodsname: '',
                summary: '',
                muappchtype: '',
                mdcode: '',
                mdname: '',
                goodsselltypename: '',
                goodsdivtypename: '',
                disdate: '',
                disdaterange: '',
            },
            orderInfo: {
                ordno: '',
                orderdate: '',
                ordcnt: '',
                totprice: '',
                option: '',
            },
            files: {
                goodsimgfile: '',
                reviewimgfile: [],
                reviewvideofile: '',
                videoimgfile: '',
            },
            imgPreview : {
                goodsimgfile: '',
                reviewimgfile: [],
                reviewvideofile: '',
                videoimgfile: '',
            },
            sortData: {
                dadamembertype: 'dadamembertype_desc',   
                memlvtype: 'memlvtype_desc',   
            },
            checkObj: {
                startYYYYMMDD: '',
                startHH: '',
                startMi: '',
                endYYYYMMDD: '',
                endHH: '',
                endMi: '',
            },
            isRead: false,
            isWrite: false,
            isPictureViewPopupShow: false,
            isVideoViewPopupShow: false,
            imgPath: '',
            videoInfo: null,
            activeUserNo: null,
            activeGoodsNo: null,
            detailShow: false,
            isGoodsDetailShow: false,   //상품상세팝업여부
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.onSearch();
            }else {
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
        onSearch() {
            let param = {
                reviewidx : this.reviewidx
            }

            this.$http.post('/admin/operation/review/detail', param)
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.info = data.info;
                    this.goodsInfo = data.goodsinfo;
                    this.orderInfo = data.orderinfo;
                    this.files = data.files;
                    this.setInit();
                }
            })
            .catch(error => {
                this.$util.debug(error);
            })
        },
        setInit() {
            // 컬럼 생성되면 값 변경필요
            if(this.info.isbest === 'T') {
                this.checkObj.startYYYYMMDD = this.$util.getFormatDate(this.info.poststtime.substring(0,8),'-');
                this.checkObj.startHH = this.info.poststtime.substring(8,10);
                this.checkObj.startMi = this.info.poststtime.substring(10,12);
                this.checkObj.endYYYYMMDD = this.$util.getFormatDate(this.info.poststtime.substring(0,8),'-');
                this.checkObj.endHH = this.info.poststtime.substring(8,10);
                this.checkObj.endMi = this.info.poststtime.substring(10,12);
            } else {
                this.checkObj.startYYYYMMDD = '';
                this.checkObj.startHH = '';
                this.checkObj.startMi = '';
                this.checkObj.endYYYYMMDD = '';
                this.checkObj.endHH = '';
                this.checkObj.endMi = '';
            }

            this.imgPreview.goodsimgfile = this.goodsInfo.fullpath;
            if(!this.$util.isNull(this.files['reviewvideofile'])){
                this.imgPreview.reviewvideofile = this.files.reviewvideofile.fullpath;
            }
            
            this.files.reviewimgfile.forEach(file => {
                this.imgPreview.reviewimgfile.push(file.fullpath);
            });

            if(!this.$util.isNull(this.files.videoimgfile)){
                this.imgPreview.videoimgfile = this.files.videoimgfile.fullpath;
            }
        },
        searchList() {
            let param = {
                reviewidx : this.reviewidx
            }

            this.$http.post('/admin/operation/review/notilist', param)
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.info.notilist = data.list;
                }
            })
            .catch(error => {
                this.$util.debug(error);
            })
        },
        onSave() {
            let params = {
                reviewidx : this.reviewidx,
                isbest : this.info.isbest,
                isblind: this.info.isblind
            }

            if(this.info.isbest === 'T'){ 
                this.info.poststtime = this.checkObj.startYYYYMMDD.replaceAll('-','') + this.checkObj.startHH + this.checkObj.startMi;
                if(this.info.poststtime.length < 12) {
                    alert("전시시작기간을 선택해주세요.");
                    return false;
                }

                this.info.postedtime = this.checkObj.endYYYYMMDD.replaceAll('-','') + this.checkObj.endHH + this.checkObj.endMi;
                if(this.info.postedtime.length < 12) {
                    alert("전시종료기간을 선택해주세요.");
                    return false;
                }
                if(this.info.poststtime >= this.info.postedtime) {
                    alert("전시종료기간을 전시시작기간 이후로 선택해주세요.");
                    return false;
                }

                params.poststtime = this.info.poststtime;
                params.postedtime = this.info.postedtime;
            }
            
            if(confirm("저장하시겠습니까?")){
                this.$http.post('/admin/operation/review/modify', params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("저장이 완료되었습니다.");
                        this.onClose(true);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                })
            }
        },
        // 정렬조건으로 검색
        sortToggle (key) {
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        showVideo() {
            this.videoInfo = this.files.reviewvideofile;
            this.isVideoViewPopupShow = true;
        },
        showPicture(n) {
            this.imgPath = this.files.reviewimgfile[n].fullpath;
            this.isPictureViewPopupShow = true;
        },
        closeUploadFilePopup() {
            this.isPictureViewPopupShow = false;
        },
        closeVideoPopup() {
            this.isVideoViewPopupShow = false;
        },
        // datepicker callback
        changeStartDate(data) {
          this.checkObj.startYYYYMMDD = data.fromYYYYMMDD;
          this.checkObj.startHH = data.fromHH;
          this.checkObj.startMi = data.fromMM;
          this.checkObj.endYYYYMMDD = data.toYYYYMMDD;
          this.checkObj.endHH = data.toHH;
          this.checkObj.endMi = data.toMM;
        },
        goDetail(userNo){
            this.activeUserNo = userNo;
            this.detailShow = true;
        },
        // 상품 상세정보
        goGoodsDetail: function(value) {
            this.activeGoodsNo = value;
            this.isGoodsDetailShow = true;
        },
        closeGoodsDetail: function () {
            this.isGoodsDetailShow = false;
            this.searchList();
        },
        closeDetail: function () {
            this.detailShow = false;
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            this.goodsInfo = this.$options.data().goodsInfo;
            this.files = this.$options.data().files;
            this.imgPreview = this.$options.data().imgPreview;
            // 팝업 닫기
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closeDetail', isreload);
            } else {
                this.$emit('closeDetail');
            }
        }
    },
}
</script>

<style>

</style>