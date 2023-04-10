<template>
    <!-- 운영자 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header">
                <h2>운영자 등록</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="bar-title">기본정보</div>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="150px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>사용여부<i class="essential"></i></th>
                            <td>
                                <div class="radio_wrap wide">
                                    <input type="radio" v-model="info.istrash" name="radio01" id="rd01" value="F" checked/><label for="rd01">사용</label>
                                    <input type="radio" v-model="info.istrash" name="radio01" id="rd02" value="T"/><label for="rd02">미사용</label>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>아이디<i class="essential"></i></th>
                            <td>
                                <input type="text" v-model="info.userid" style="width: 177px;" @change="idChange"/>
                                <button type="button" class="btn blue-line ml3" @click="idCheck">중복확인</button>
                                <span class="txt-orange ml3"><i class="icon-alert"></i>영문 소문자 또는 영문 소문자 + 숫자 조합하여 6~12자</span>
                            </td>
                        </tr>
                        <tr>
                            <th>비밀번호<i class="essential"></i></th>
                            <td>
                                <input type="password" v-model="info.userpw"/>
                                <span class="txt-orange ml3"><i class="icon-alert"></i>영문/숫자/특수문자 3가지 이상 조합(최소 8자 이상)</span>
                            </td>
                        </tr>
                        <tr>
                            <th>비밀번호 확인<i class="essential"></i></th>
                            <td>
                                <input type="password" v-model="info.userpw2"/>
                                <span class="txt-orange ml3"><i class="icon-alert"></i>영문/숫자/특수문자 3가지 이상 조합(최소 8자 이상)</span>
                            </td>
                        </tr>
                        <tr>
                            <th>이름<i class="essential"></i></th>
                            <td>
                                <input type="text" v-model="info.name"/>
                            </td>
                        </tr>
                        <tr>
                            <th>이메일<i class="essential"></i></th>
                            <td>
                                <input type="text" v-model="info.email1"/>
                                <span>@</span>
                                <input type="text" v-show="info.email2 === 'INPUT'" v-model="info.emailinput" style="width: 150px;"/>
                                <input type="text" v-show="info.email2 !== 'INPUT'" v-model="info.email2" style="width: 150px;" disabled/>
                                <select v-model="info.email2">
                                    <option v-for="(domain,i) in info.emaildomain" :key="i" :value="domain.cmcode">{{domain.codename}}</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>전화번호</th>
                            <td>
                                <select class="short" v-model="info.tel1">
                                    <option v-for="(num,j) in info.telnum" :key="j" :value="num.cmcode">{{num.codename}}</option>
                                </select>
                                <span>-</span>
                                <input type="text" v-model="info.tel2" style="width: 74px;" />
                                <span>-</span>
                                <input type="text" v-model="info.tel3" style="width: 74px;" />
                            </td>
                        </tr>
                        <tr>
                            <th>휴대폰번호<i class="essential"></i></th>
                            <td>
                                <select class="short" v-model="info.mobile1">
                                    <option v-for="(n,k) in info.mobilenum" :key="k" :value="n.cmcode">{{n.codename}}</option>
                                </select>
                                <span>-</span>
                                <input type="text" v-model="info.mobile2" style="width: 74px;" />
                                <span>-</span>
                                <input type="text" v-model="info.mobile3" style="width: 74px;" />
                            </td>
                        </tr>
                        <tr>
                            <th>기타메모</th>
                            <td>
                                <input type="text" v-model="info.memo" style="width: 100%;" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="clearfix">
                    <div class="bar-title fl mb0">권한 설정</div>
                    <div class="fr" style="line-height: 36px;">
                        <input type="checkbox" id="checkall" v-model="allCheck" @change="onCheckAll($event.target.checked)"/><label for="checkall">전체선택</label>
                    </div>
                </div>
                <table cellpadding="0" cellspacing="0" class="gray-tb line">
                    <colgroup>
                        <col width="150px">
                        <col width="170px">
                        <col width="170px">
                        <col width="">
                    </colgroup>
                    <tbody v-if="this.permissionlist.length > 0">
                        <tr v-for="(row,index) in this.permissionlist" :key="index">
                            <th v-if="row.depth === 0" :rowspan="row.dep1span" class="no-left center">{{row.name}}</th>
                            <td v-if="row.depth < 3" :rowspan="row.dep2span > 0 ? row.dep2span : 1" :colspan="row.dep2span > 0 ? 1 : 2">{{row.depth === 0 ? '전체' : row.name }}</td>
                            <td v-if="row.dep2span > 0 || row.depth === 3">{{row.name}}</td>
                            <td v-if="row.depth === 0" class="left">
                                <input type="checkbox" v-model="row.allread" :id="'promotion-read'+row.depth+'_'+row.code" true-value="T" false-value="F" @change="onCheckAllCode(row.code,'R',$event.target.checked)"><label :for="'promotion-read'+row.depth+'_'+row.code">읽기</label>
                                <input type="checkbox" v-model="row.allwrite" :id="'promotion-write'+row.depth+'_'+row.code" true-value="T" false-value="F" @change="onCheckAllCode(row.code,'W',$event.target.checked)"><label :for="'promotion-write'+row.depth+'_'+row.code">쓰기</label>
                            </td>
                            <td v-if="row.depth !== 0" class="left">
                                <input type="checkbox" v-model="row.isread" :id="'promotion-read'+row.depth+'_'+row.code" true-value="T" false-value="F" @change="onCheckCode(row.code,'R', $event.target.checked)"><label :for="'promotion-read'+row.depth+'_'+row.code">읽기</label>
                                <input type="checkbox" v-model="row.iswrite" :id="'promotion-write'+row.depth+'_'+row.code" true-value="T" false-value="F" @change="onCheckCode(row.code,'W', $event.target.checked)"><label :for="'promotion-write'+row.depth+'_'+row.code">쓰기</label>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="onSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /운영자 등록 팝업 -->
</template>

<script>
export default {
    name: 'admin.configuration.operator.operatorregist',
    props: {
        permilist: Array
    },
    data() {
        return {
            info: {
                istrash: 'F',        // 사용여부
                userid: '',          // 유저id
                userpw: '',          // 비밀번호
                userpw2: '',   // 비밀번호 확인
                name: '',            // 사용자 이름
                email: '',           // email
                tel: '',             // 전화번호
                mobile:'',           // 휴대폰번호
                memo: '',            // 기타메모
                email1 : '',
                email2 : 'cj.net',
                emailinput : '',
                emaildomain : [
                    {cmcode : 'cj.net', codename : 'cj.net'},
                    {cmcode : 'dadapick.com', codename : 'dadapick.com'},
                    {cmcode : 'naver.com', codename : 'naver.com'},
                    {cmcode : 'gmail.com', codename : 'gmail.com'},
                    {cmcode : 'hanmail.net', codename : 'hanmail.net'},
                    {cmcode : 'hotmail.net', codename : 'hotmail.net'},
                    {cmcode : 'nate.com', codename : 'nate.com'},
                    {cmcode : 'INPUT', codename : '직접입력'}
                ],
                tel1: '',               // 전화번호 앞
                tel2: '',               // 전화번호 중간
                tel3: '',               // 전화번호 마지막
                telnum: [
                    {cmcode : '', codename : '선택'},
                    {cmcode : '02', codename : '02'},
                    {cmcode : '031', codename : '031'},
                    {cmcode : '032', codename : '032'},
                    {cmcode : '033', codename : '033'},
                    {cmcode : '041', codename : '041'},
                    {cmcode : '042', codename : '042'},
                    {cmcode : '043', codename : '043'},
                    {cmcode : '044', codename : '044'},
                    {cmcode : '051', codename : '051'},
                    {cmcode : '052', codename : '052'},
                    {cmcode : '053', codename : '053'},
                    {cmcode : '054', codename : '054'},
                    {cmcode : '055', codename : '055'},
                    {cmcode : '061', codename : '061'},
                    {cmcode : '062', codename : '062'},
                    {cmcode : '063', codename : '063'},
                    {cmcode : '064', codename : '064'},
                ],
                mobile1: '',            // 핸드폰번호 앞
                mobile2: '',            // 핸드폰번호 중간
                mobile3: '',            // 핸드폰번호 마지막  
                mobilenum: [
                    {cmcode : '', codename : '선택'},
                    {cmcode : '010', codename : '010'},
                    {cmcode : '011', codename : '011'},
                    {cmcode : '016', codename : '016'},
                    {cmcode : '017', codename : '017'},
                    {cmcode : '018', codename : '018'},
                    {cmcode : '019', codename : '019'},
                ],         
            },
            allCheck: false,        // 전체체크여부
            permissionlist: this.permilist, // 권한설정
            idcheck: false,         // 아이디 유효성체크
            pwcheck: false,         // 비밀번호 유효성체크
            pwconfirmcheck: false,  // 비밀번호 확인
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(!this.isRead) {
                alert("페이지 접근 권한이 없습니다.");
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
        idCheck() {
            if(this.info.userid.trim().length == 0){
                this.idcheck = false;
                alert("아이디를 입력해주세요.");
                return;
            }

            if(!/^[A-Za-z0-9]{6,12}$/.test(this.info.userid) || !this.$util.isExistEng(this.info.userid)){
                alert("아이디는 6~12자의 영문, 숫자만 사용 할 수 있습니다.");
                this.idcheck = false;
                return;
            }

            const param = {
                userid : this.info.userid,
            }
            this.$http.post('/admin/configuration/operator/operator/idcheck', param).then(result => {
                if (result.statusCode === 200) {
                    if(!result.data.check) {
                        alert("사용할 수 있는 아이디입니다.");
                        this.idcheck = true;
                    } else {
                        alert("사용할 수 없는 아이디입니다.");
                        this.idcheck = false;
                    }
                }
            });
        },
        pwCheck() {
            if(this.$util.isPassword(this.info.userpw)) {
                this.pwcheck = true;
            } else {
                this.pwcheck = false;
            }
        },
        pwConfirm() {
            if(this.$util.isNull(this.info.userpw) || this.$util.isNull(this.info.userpw2)){
                this.pwconfirmcheck = false;
            } else {
                if(this.info.userpw == this.info.userpw2) {
                    this.pwconfirmcheck = true;
                } else {
                    this.pwconfirmcheck = false;
                }
            }
        },
        idChange() {
            this.idcheck = false;
        },
        // 전체 체크
        onCheckAll(checked) {
            let check = 'T';
            if(!checked) { 
                check = 'F';
            }

            this.permissionlist.forEach(menu => {
                if(menu.depth === 0) {
                    menu.allread = check;
                    menu.allwrite = check;
                }
                menu.isread = check;
                menu.iswrite = check;
            });
        },
        // 하위 메뉴 전체 체크
        onCheckAllCode(code, type, checked) {
            let check = 'T';
            if(!checked) { 
                check = 'F';
                this.allCheck = false;
            }

            this.permissionlist.forEach(menu => {
                if(menu.code.indexOf(code) > -1) {
                    if(type === 'R') {
                        menu.isread = check;
                    } else {
                        if(checked) {
                            if(menu.depth === 0) {
                                menu.allread = check;
                            }
                            menu.isread = check;
                        }
                        menu.iswrite = check;
                    }
                }
            });
        },
        // 하위 메뉴 체크 변경시 전체 체크 변경
        onCheckCode(code, type, checked) {
            let root = code.substring(0,1);
            
            let allCheck = true;
            this.permissionlist.forEach((menu) => {
                if(menu.code === code && type === 'W' && checked) {
                    menu.isread = 'T';
                }
                if(menu.code.indexOf(root) > -1 && menu.depth === 0) {
                    if(type === 'R') {
                        menu.allread = checked;
                    } else {
                        menu.allwrite = checked;
                    }
                }
                if(menu.isread === 'F' || menu.iswrite === 'F') {
                    allCheck = false;
                } 
            });

            this.allCheck = allCheck;
        },
        onSave() {
            if(this.checkValidation()) {
                this.info.mobile = this.info.mobile1 + this.info.mobile2 + this.info.mobile3;
                this.info.tel = this.info.tel1 + this.info.tel2 + this.info.tel3;
                let params = this.info;
                params.permissionlist = this.permissionlist;

                if(confirm("저장하시겠습니까?")) {
                    this.$http.post('/admin/configuration/operator/operator/save', params)
                    .then(result => {
                        if (result.statusCode === 200) {
                            alert("저장되었습니다.");
                            this.onClose(true);
                        }
                    });
                }
            }
        },
        checkValidation() {
            this.pwCheck();
            this.pwConfirm();

            if(!this.idcheck) {
                alert("아이디 중복확인을 해주세요.");
                return false;
            }

            let msg = '';

            let valid = [
                {field: 'info.userid', type: 'I', name:'[기본정보] 아이디', required: true},
                {field: 'info.userpw', type: 'I', name:'[기본정보] 비밀번호', required: true},
                {field: 'info.userpw2', type: 'I', name:'[기본정보] 비밀번호 확인', required: true},
                {field: 'info.name', type: 'I', name:'[기본정보] 이름', required: true},
                {field: 'info.email1', type: 'I', name:'[기본정보] 이메일', required: true},
                {field: 'info.mobile1', type: 'I', name:'[기본정보] 휴대폰번호', required: true},
                {field: 'info.mobile2', type: 'I', name:'[기본정보] 휴대폰번호', required: true},
                {field: 'info.mobile3', type: 'I', name:'[기본정보] 휴대폰번호', required: true},
            ];

            
            if(this.info.email2 === 'INPUT') {
                valid.push({field: 'info.emailinput', type: 'I', name:'[기본정보] 이메일', required: true});
                this.info.email = this.info.email1 + '@' + this.info.emailinput;
            } else {
                this.info.email = this.info.email1 + '@' + this.info.email2;
            }

            // 전화번호는 필수가 아니지만 입력한 경우 전부 입력하도록
            let telCheck = false;
            if(!this.$util.isNull(this.info.tel1) || !this.$util.isNull(this.info.tel2) || !this.$util.isNull(this.info.tel3)) {
                valid.push({field: 'info.tel1', type: 'I', name:'[기본정보] 전화번호', required: true});
                valid.push({field: 'info.tel2', type: 'I', name:'[기본정보] 전화번호', required: true});
                valid.push({field: 'info.tel3', type: 'I', name:'[기본정보] 전화번호', required: true});
                telCheck = true;
            }

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(!this.pwcheck) {
                alert("비밀번호 형식이 올바르지 않습니다.");
                return false;
            }

            if(!this.pwconfirmcheck) {
                alert("비밀번호확인이 일치하지 않습니다.");
                return false;
            }

            if(!this.$util.isEmail(this.info.email)) {
                alert("이메일 형식에 맞게 입력해주세요.");
                return false;
            }

            let phone = this.info.mobile1 + this.info.mobile2 + this.info.mobile3;
            if(!this.$util.isPhone(phone)) {
                alert("휴대폰번호에 맞게 입력해주세요.");
                return false;
            }

            if(!/([0-9]{3,4})([0-9]{4})$/.test(this.info.tel2 + this.info.tel3) && telCheck) {
                alert("전화번호에 맞게 입력해주세요.");
                return false;
            }

            return true;
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            this.idcheck = false;         
            this.pwcheck = false;         
            this.pwconfirmcheck = false;  
            this.onCheckAll(false);
            if(typeof(isreload) === 'boolean' && isreload) {
                this.$emit('closeRegist', true);
            } else {
                this.$emit('closeRegist');
            }
        }
    },
    watch: {
        'info.tel2' : function(value,oldValue) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            if(!/^[0-9]{0,4}$/.test(value)){
                return this.info.tel2 = oldValue;
            } 
        },
        'info.tel3' : function(value,oldValue) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            if(!/^[0-9]{0,4}$/.test(value)){
                return this.info.tel3 = oldValue;
            } 
        },
        'info.mobile2' : function(value,oldValue) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            if(!/^[0-9]{0,4}$/.test(value)){
                return this.info.mobile2 = oldValue;
            } 
        },
        'info.mobile3' : function(value,oldValue) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            if(!/^[0-9]{0,4}$/.test(value)){
                return this.info.mobile3 = oldValue;
            } 
        },
    }
}
</script>

<style>

</style>