<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">자동지급설정</div>
            </div>
            <div class="bar-title small">기초설정</div>
            <div class="form-area">
                <table cellpadding="0" cellspacing="0" class="gray-tb align-c line">
                    <colgroup>
                        <col width="8%">
                        <col width="17%">
                        <col width="15%" v-for="item in commonCode.memlvtype" :key="item.cmcode">
                        <!-- <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%"> -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th class="no-left" colspan="2">구분</th>
                            <th v-for="item in commonCode.memlvtype" :key="item.cmcode">{{ item.codename }}</th>
                            <!-- <th>BRONZE</th>
                            <th>SILVER</th>
                            <th>GOLD</th>
                            <th>VIP</th>
                            <th>VVIP</th> -->
                        </tr>
                    </thead>
                    <tbody v-for="membertype in commonCode.dadamembertype" :key="membertype.cmcode">
                        <tr v-for="type in respaytype" :key="type.sort">
                            <th class="no-left bold" :rowspan="respaytype.length" v-if="type.sort==1">{{ membertype.codename }}</th>
                            <th>{{ type.name }}</th>
                            <td v-for="item in commonCode.memlvtype" :key="item.cmcode">
                                <input type="text" class="right" style="width: 70%;" :ref="type.code+'_'+membertype.cmcode+'_'+item.cmcode"
                                       oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');" maxlength="8" />
                                <span class="ml3">{{ type.unit }}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="bar-title small">추가설정</div>
            <div class="form-area">
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="180px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>기간 내 구매확정<i class="essential"></i></th>
                            <td>
                                <CommonDatePickerFromTo
                                    :fromYYYYMMDD="config.cfmstdate"
                                    :fromHH="config.cfmsthour"
                                    :fromMM="config.cfmstmin"
                                    :toYYYYMMDD="config.cfmeddate"
                                    :toHH="config.cfmedhour"
                                    :toMM="config.cfmedmin"
                                    :useFrom="true"
                                    :useTo="true"
                                    @getDate="pickerChangeEvent"
                                />
                                <span>동안 구매 확정 시 기초 설정의</span>
                                <input type="text" class="ml3 center" style="width: 50px;" v-model="config.cfmmulti" maxlength="8" /> 배 지급
                            </td>
                        </tr>
                        <tr>
                            <th>첫 리뷰 작성<i class="essential"></i></th>
                            <td>
                                <span>첫 리뷰 작성 시</span>
                                <input type="text" class="ml3 center" style="width: 60px;" v-model="config.frstreviewamt" maxlength="8" />
                                <span>포인트 추가 지급</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="btn-group">
                <button type="button" v-if="isWrite" class="btn big blue" @click="save">저장</button>
            </div>
        </div>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue'
import CommonDatePickerFromTo from '@views.admin/common/CommonDatePickerFromTo.vue'

export default {
    name: 'admin.promotion.reserve.autopayment',
    components: {
        CommonNavigator,
        CommonDatePickerFromTo
    },
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
            user: {},
            isWrite: false,
            isRead: false,
            commonCode: {
                dadamembertype: [],  // 다다픽회원유형
                memlvtype: []        // 회원등급
            },
            respaytype: [
                {sort: 1, code: 'purcfmamt', name: '구매확정(첫 구매확정 이후)', unit: '%', unitname: '퍼센트'},
                {sort: 2, code: 'textreviewamt', name: '리뷰 작성(텍스트)', unit: 'p', unitname: '포인트'},
                {sort: 3, code: 'photoreviewamt', name: '리뷰 작성(포토)', unit: 'p', unitname: '포인트'},
                {sort: 4, code: 'movreviewamt', name: '리뷰 작성(동영상)', unit: 'p', unitname: '포인트'},
                {sort: 5, code: 'birthdayamt', name: '생일', unit: 'p', unitname: '포인트'},
                {sort: 6, code: 'frstpurcfmamt', name: '첫 구매확정', unit: '%', unitname: '퍼센트'},
            ],
            config: {
                cfmstday: '',        // 구매확정시작일시
                cfmstdate: '',       // 구매확정시작일자
                cfmsthour: '',       // 구매확정시작시간
                cfmstmin: '',        // 구매확정시작분
                cfmedday: '',        // 구매확정종료일시
                cfmeddate: '',       // 구매확정종료일자
                cfmedhour: '',       // 구매확정종료시간
                cfmedmin: '',        // 구매확정종료분
                cfmmulti: '1',       // 구매확정기간 내 추가지급배율
                frstreviewamt: '',   // 첫리뷰작성시 추가지급포인트
                configlist: []       // 설정 목록
            }
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            // 공통코드 조회
            this.getCommonCodeList();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['RESPAYTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 적립금 자동지급 설정 조회
                    this.getReserveConfig();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 적립금 자동지급 설정 조회
        getReserveConfig: function() {
            this.$http.post('/admin/promotion/reserve/config')
                .then(result => {
                    this.$util.debug(result);
                    for (const [key] of Object.entries(result.data)) {
                        this.config[key] = result.data[key];
                    }
                    this.config.configlist.forEach(item => {
                        item.amt = this.$util.isNull(item.amt)? 0: item.amt;
                        let refname = item.colname+'_'+item.membertype+'_'+item.memlvtype;
                        this.$refs[refname][0].value = item.amt;
                    });
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // datepicker callback
        pickerChangeEvent(data) {
            this.config.cfmstdate = data.fromYYYYMMDD;
            this.config.cfmsthour = data.fromHH;
            this.config.cfmstmin = data.fromMM;
            this.config.cfmstday = data.fromDate12;

            this.config.cfmeddate = data.toYYYYMMDD;
            this.config.cfmedhour = data.toHH;
            this.config.cfmedmin = data.toMM;
            this.config.cfmedday = data.toDate12;
        },
        // 유효성체크
        checkValidation: function() {
            // 필수체크
            let valid = [
                {field: 'config.cfmstdate'     , type: 'I', name:'[추가설정] 구매확정기간 시작일자', required: true},
                {field: 'config.cfmsthour'     , type: 'I', name:'[추가설정] 구매확정기간 시작시간', required: true},
                {field: 'config.cfmstmin'       , type: 'I', name:'[추가설정] 구매확정기간 시작분', required: true},
                {field: 'config.cfmeddate'     , type: 'I', name:'[추가설정] 구매확정기간 종료일자', required: true},
                {field: 'config.cfmedhour'     , type: 'I', name:'[추가설정] 구매확정기간 종료시간', required: true},
                {field: 'config.cfmedmin'       , type: 'I', name:'[추가설정] 구매확정기간 종료분', required: true},
                {field: 'config.cfmmulti'      , type: 'I', name:'[추가설정] 구매확정기간 내 추가지급배율', required: true},
                {field: 'config.frstreviewamt' , type: 'I', name:'[추가설정] 첫리뷰작성시 추가지급포인트', required: true}
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if (!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(this.config.cfmeddate <= this.config.cfmstdate) {
                alert("[추가설정] 구매확정기간 종료일자를 시작일자 이후로 선택해주세요.");
                return false;
            }

            return true;
        },
        // 저장
        save: function() {
            if (!this.checkValidation()) return;

            // 회원유형별 등급별 자동지급설정 세팅, 필수입력 체크
            this.config.configlist = [];
            for (let i=0; i<this.commonCode.dadamembertype.length; i++) {
                let membertype = this.commonCode.dadamembertype[i];
                for (let j=0; j<this.commonCode.memlvtype.length; j++) {
                    let memlvtype = this.commonCode.memlvtype[j];
                    let item = {
                        membertype: membertype.cmcode,
                        memlvtype: memlvtype.cmcode
                    }
                    for(let k=0; k<this.respaytype.length; k++) {
                        let respaytype = this.respaytype[k];
                        let refname = respaytype.code+'_'+membertype.cmcode+'_'+memlvtype.cmcode;
                        let refvalue = this.$refs[refname][0].value;
                        if (this.$util.isNull(refvalue)) {
                            alert('[기초설정] '+membertype.codename+' '+memlvtype.codename+'등급 '+respaytype.name+' '+respaytype.unitname+'를 입력해주세요.');
                            return;
                        }
                        item[respaytype.code] = this.$refs[refname][0].value;
                    }
                    this.config.configlist.push(item);
                }
            }

            if (confirm('저장하시겠습니까?')) {
                // 저장 호출
                this.$http.post('/admin/promotion/reserve/config/save', this.config)
                    .then(result =>{
                        this.$util.debug(result);
                        if (result.statusCode == '200') {
                            alert('저장이 완료되였습니다.');
                            // 적립금 자동지급 설정 조회
                            this.getReserveConfig();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        }
    },
    watch: {
        // 숫자만입력
        'config.cfmmulti': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.config.cfmmulti = value.replace(/(^0[0-9]|[^0-9])/gi, '');
        },
        'config.frstreviewamt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.config.frstreviewamt = value.replace(/(^0[0-9]|[^0-9])/gi, '');
        }
    }
}
</script>
