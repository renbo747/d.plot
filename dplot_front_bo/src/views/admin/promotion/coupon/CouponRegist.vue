<template>
    <!-- 쿠폰 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>쿠폰 등록</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="bar-title">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>사용여부<i class="essential"></i></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" v-model="info.istrash" name="istrashD" id="istrashDF" value="F" />
                                    <label for="istrashDF">사용함</label>
                                    <input type="radio" v-model="info.istrash" name="istrashD" id="istrashDT" value="T" />
                                    <label for="istrashDT">사용안함</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>쿠폰종류<i class="essential"></i></dt>
                            <dd>
                                <div class="radio_wrap">
                                    <div v-for="item in commonCode.comcpntype" :key="item.cmcode">
                                        <input type="radio" name="comcpntypeD" :id="'comcpntypeD_'+item.cmcode" :value="item.cmcode" v-model="info.comcpntype"/>
                                        <label :for="'comcpntypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>발급종류<i class="essential"></i></dt>
                            <dd>
                                <div class="radio_wrap">
                                    <!-- 배송비쿠폰인 경우 즉시할인 제공안됨 -->
                                    <div v-for="item in commonCode.cpnissuetype" :key="item.cmcode">
                                        <input type="radio" name="cpnissuetypeD" :id="'cpnissuetypeD_'+item.cmcode" :value="item.cmcode" v-model="info.cpnissuetype"/> 
                                            <!--v-if="info.comcpntype != $store.getters['ADMIN'].COM_CPN_DELIV || (info.comcpntype == $store.getters['ADMIN'].COM_CPN_DELIV && item.cmcode != $store.getters['ADMIN'].CPN_ISSUE_NOW_DISCOUNT)"/> -->
                                        <label :for="'cpnissuetypeD_'+item.cmcode">
                                            <!-- v-if="info.comcpntype != $store.getters['ADMIN'].COM_CPN_DELIV || (info.comcpntype == $store.getters['ADMIN'].COM_CPN_DELIV && item.cmcode != $store.getters['ADMIN'].CPN_ISSUE_NOW_DISCOUNT)">-->
                                        {{ item.codename }}</label>
                                    </div>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>쿠폰명<i class="essential"></i></dt>
                            <dd><input type="text" style="width: 100%" placeholder="쿠폰명" v-model="info.cpnname" maxlength="200"/></dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" style="width: 100%" placeholder="설명" v-model="info.cpndesc" maxlength="400"/></dd>
                        </dl>
                        <dl>
                            <dt>소멸안내 알림톡</dt>
                            <dd><input type="checkbox" id="chkSendYn"/> 발송 (소멸 <input type="text" style="width: 50px" v-model="info.issendmsg" maxlength="3" /> 일 전)</dd>
                        </dl>
                    </div>
                </div>
                <div class="bar-title">발급대상회원</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>대상범위<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap">
                                        <input type="radio" name="ismemtypeD" id="ismemtypeDT" v-model="info.ismemtype" value="T">
                                        <label for="ismemtypeDT">특정 유형/등급 대상</label>
                                        <input type="radio" name="ismemtypeD" id="ismemtypeDF" v-model="info.ismemtype" value="F">
                                        <label for="ismemtypeDF">특정 회원 대상</label>
                                    </div>
                                </td>
                            </tr>
                            <!-- 특정 유형/등급 대상 선택시 노출 -->
                            <tr v-if="info.ismemtype == 'T'">
                                <th>대상회원유형<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllMemberD" v-model="info.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
                                        <label for="chkAllMemberD">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mumembertypeD_'+item.cmcode" v-model="info.mumembertypeArr" :true-value="[]" :value="item.cmcode">
                                        <label :for="'mumembertypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.ismemtype == 'T'">
                                <th>대상회원등급<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllMemlvD" v-model="info.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
                                        <label for="chkAllMemlvD">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.memlvtype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mumemlvtypeD_'+item.cmcode" v-model="info.mumemlvtypeArr" :true-value="[]" :value="item.cmcode">
                                        <label :for="'mumemlvtypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <!-- // 특정 유형/등급 대상 선택시 노출 -->
                            <!-- 특정 회원 대상 선택시 노출 -->
                            <tr v-if="info.ismemtype == 'F'">
                                <th>대상회원<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix dpb">
                                        <div class="total-group fl">
                                            <span class="total">발급대상 회원목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <input type="file" ref="userExcelFile" @change="readExcelFile('userExcelFile', $event)" hidden
                                                accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
                                            <button type="button" class="btn black-line" @click="downloadExcelTemplate('MemberTemplate.xlsx')">양식 다운로드</button>
                                            <button type="button" class="btn green-line" @click="fileAttach('userExcelFile')">엑셀파일 올리기</button>
                                            <button type="button" class="btn blue-line" @click="openAddUserPopup">회원추가</button>
                                            <button type="button" class="btn red-line" @click="removeUser">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 400px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                                            <caption>특정회원목록</caption>
                                            <colgroup>
                                                <col width="5%" /><!-- checkbox -->
                                                <col width="5%" /><!-- No -->
                                                <col width="15%" /><!-- 아이디 -->
                                                <col width="15%" /><!-- 이름 -->
                                                <col width="15%" /><!-- 유형 -->
                                                <col width="15%" /><!-- 등급 -->
                                                <col width="" /><!-- 가입일 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" id="chkallgoodsmem" v-model="info.isallchkmem" @change="checkAllMemberList($event.target.checked)" true-value="T" false-value="F" /></th>
                                                    <th>No</th>
                                                    <th>아이디</th>
                                                    <th>이름</th>
                                                    <th>유형
                                                        <button type="button" class="sort" :value="sortData.dadamembertypename"
                                                            :class="[{up : sortData.dadamembertypename=== 'dadamembertypename_asc'}, {down : sortData.dadamembertypename === 'dadamembertypename_desc'}]"
                                                            @click="sortToggle(sortData.dadamembertypename)"></button>
                                                    </th>
                                                    <th>등급
                                                        <button type="button" class="sort" :value="sortData.memlvtypename"
                                                            :class="[{up : sortData.memlvtypename=== 'memlvtypename_asc'}, {down : sortData.memlvtypename === 'memlvtypename_desc'}]"
                                                            @click="sortToggle(sortData.memlvtypename)"></button>
                                                    </th>
                                                    <th>가입일
                                                        <button type="button" class="sort" :value="sortData.regdate"
                                                            :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                                            @click="sortToggle(sortData.regdate)"></button>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="info.issueTargetMemberList.length > 0">
                                                <tr v-for="(item, index) in info.issueTargetMemberList" :key="item.userno">
                                                    <td><input type="checkbox" :id="'member_'+index" v-model="item.ischecked" @change="checkMemberList($event.target.checked)"/></td>
                                                    <td>{{ index+1 }}</td>
                                                    <td>{{ item.userid }}</td>
                                                    <td>{{ item.username }}</td>
                                                    <td>{{ item.dadamembertypename }}</td>
                                                    <td>{{ item.memlvtypename }}</td>
                                                    <td>{{ item.regdate }}</td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="7">조회 결과가 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <!-- // 특정 회원 대상 선택시 노출 -->
                            <tr>
                                <th>수신동의상태제한<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="isrcvlimitD" id="isrcvlimitDF" v-model="info.isrcvlimit" value="F">
                                        <label for="isrcvlimitDF">불필요</label>
                                        <input type="radio" name="isrcvlimitD" id="isrcvlimitDT" v-model="info.isrcvlimit" value="T">
                                        <label for="isrcvlimitDT">필요</label>
                                    </div>
                                    <div class="dpib ml10">
                                        <div class="check-wrap" v-for="item in commonCode.mumemrcvtype" :key="item.cmcode">
                                            <input type="checkbox" :id="'mumemrcvtypeD_'+item.cmcode" v-model="info.mumemrcvtypeArr" :true-value="[]" :value="item.cmcode" :disabled="info.isrcvlimit=='F'">
                                            <label :for="'mumemrcvtypeD_'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="bar-title">쿠폰적용대상 상품</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>대상카테고리범위<i class="essential"></i><br>(쿠폰적용대상)</th>
                                <td>
                                    <div class="radio_wrap dpib">
                                        <input type="radio" name="istotcateD" id="istotcateDT" v-model="info.istotcate" value="T"/>
                                        <label for="istotcateDT">전체</label>
                                        <input type="radio" name="istotcateD" id="istotcateDF" v-model="info.istotcate" value="F"/>
                                        <label for="istotcateDF">특정카테고리</label>
                                    </div>
                                    <div class="dpib" v-if="info.istotcate == 'F'">
                                        <select style="width: 175px;" v-model="info.depth1Category.idx" @change="setCategoryCodeInfo('1', info.depth1Category.idx)">
                                            <option value="">대분류</option>
                                            <option v-for="item in categoryObj.depth1list" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                                        </select>
                                        <select style="width: 175px;" v-model="info.depth2Category.idx" @change="setCategoryCodeInfo('2', info.depth2Category.idx)">
                                            <option value="">중분류</option>
                                            <option v-for="item in categoryObj.depth2list" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                                        </select>
                                        <select style="width: 175px;" v-model="info.depth3Category.idx" @change="setCategoryCodeInfo('3', info.depth3Category.idx)">
                                            <option value="">소분류</option>
                                            <option v-for="item in categoryObj.depth3list" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                                        </select>
                                        <select style="width: 175px;" v-model="info.depth4Category.idx" @change="setCategoryCodeInfo('4', info.depth4Category.idx)">
                                            <option value="">세분류</option>
                                            <option v-for="item in categoryObj.depth4list" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                                        </select>
                                        <button type="button" class="add" @click="addCpnCategory('T')"></button>
                                        <button type="button" class="minus" @click="addCpnCategory('F')"></button>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.istotcate == 'F'">
                                <th>특정 카테고리<i class="essential"></i><br>(쿠폰적용대상)</th>
                                <td>
                                    <div class="category-selected">
                                        <ul>
                                            <li v-for="item in info.cpncateList" :key="item.idx">
                                                <span class="box mr5">{{ item.isadd=='T'? '추가':'제외' }}</span>
                                                <span>{{ item.fullcategoryname }}</span>
                                                <button type="button" class="del" @click="removeCategory(item, 'cpncateList')"></button>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상 상품범위<i class="essential"></i><br>(쿠폰적용대상)</th>
                                <td>
                                    <div class="radio_wrap">
                                        <div v-for="item in commonCode.goodsrangetype" :key="item.cmcode">
                                            <input type="radio" name="goodsrangetypeD" :id="'goodsrangetypeD_'+item.cmcode" :value="item.cmcode" v-model="info.goodsrangetype"
                                                :disabled="info.istotcate=='F' && item.cmcode==$store.getters['ADMIN'].GOODS_RANGE_ALL"/>
                                            <label :for="'goodsrangetypeD_'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="(info.istotcate=='F' || !$util.isNull(info.goodsrangetype)) && info.goodsrangetype != $store.getters['ADMIN'].GOODS_RANGE_ALL">
                                <th>대상 상품<i class="essential"></i><br>(쿠폰적용대상)</th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">적용대상 상품목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" @click="addGoods('cpngoodsList')">상품추가</button>
                                            <button type="button" class="btn red-line" @click="removeGoods('cpngoodsList')">삭제</button>
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
                                                <col width="5.5%" /><!-- 단품코드 -->
                                                <col width="62px" /><!-- 이미지 -->
                                                <col width="" /><!-- 상품명 -->
                                                <col width="12%" /><!-- 옵션명 -->
                                                <col width="7%" /><!-- 판매가 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" id="chkallcpngoods" v-model="info.isallchkcpngoods" @change="checkAllGoodsList($event.target.checked, 'cpngoodsList')" /></th>
                                                    <th>No</th>
                                                    <th>판매구분</th>
                                                    <th>파트너사명</th>
                                                    <th>상품코드</th>
                                                    <th>단품코드</th>
                                                    <th colspan="2">상품명</th>
                                                    <th>옵션명</th>
                                                    <th>판매가</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="info.cpngoodsList.length > 0">
                                                <tr v-for="(item, index) in info.cpngoodsList" :key="item.goodsno+'_'+item.optioncode">
                                                    <td><input type="checkbox" :id="item.goodsno+'_'+item.optioncode" v-model="item.ischecked" @change="checkGoodsList('cpngoodsList')"/></td>
                                                    <td>{{ index+1 }}</td>
                                                    <td>{{ item.ispbgoodsname }}</td>
                                                    <td>{{ item.dealername }}</td>
                                                    <td>{{ item.goodscode }}</td>
                                                    <td>{{ item.optioncode }}</td>
                                                    <td>
                                                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                                            <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                                        </div>
                                                    </td>
                                                    <td class="left no-left">
                                                        <span class="small-txt">{{ item.fullcategoryname }}</span>
                                                        <p class="mg0">{{ item.goodsname }}</p>
                                                    </td>
                                                    <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                                                    <td class="right">{{ $util.maskComma(item.price) }}</td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="10">조회 결과가 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.comcpntype==$store.getters['ADMIN'].COM_CPN_GOODS">
                                <th>쿠폰비용 분담비율<i class="essential"></i></th>
                                <td>
                                    <span>파트너사</span><input type="text" class="ml3 right" style="width: 50px;" v-model="info.partratio" maxlength="5"/><span class="ml3">%</span>
                                    <span>(D.PLOT</span><input type="text" class="ml3 right" style="width: 50px;" v-model="info.dadaratio"  maxlength="5" readonly /><span class="ml3">%)</span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="bar-title" v-show="!$util.isNull(info.comcpntype) && !$util.isNull(info.cpnissuetype)">사용 조건</div>
                <div class="form-area" v-show="!$util.isNull(info.comcpntype) && !$util.isNull(info.cpnissuetype)">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <!-- 정기발급인 경우 -->
                            <tr v-show="info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_REGULAR">
                                <th>정기발급기간<i class="essential"></i></th>
                                <td>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="info.evrcpnusestdate"
                                        :fromHH="info.evrcpnusesthour"
                                        :fromMM="info.evrcpnusestmin"
                                        :toYYYYMMDD="info.evrcpnuseeddate"
                                        :toHH="info.evrcpnuseedhour"
                                        :toMM="info.evrcpnuseedmin"
                                        :useFrom="true"
                                        :useTo="true"
                                        @getDate="pickerEvCpnuseChangeEvent"
                                    />
                                    <div class="radio_wrap dpib ml3 period">
                                        <input type="radio" v-model="info.evrcpnuseperiod" id="periodEV_aday_0"   value="aday_0"  /><label for="periodEV_aday_0"  >오늘</label>
                                        <input type="radio" v-model="info.evrcpnuseperiod" id="periodEV_months_1" value="months_1"/><label for="periodEV_months_1">1개월</label>
                                        <input type="radio" v-model="info.evrcpnuseperiod" id="periodEV_months_3" value="months_3"/><label for="periodEV_months_3">3개월</label>
                                        <input type="radio" v-model="info.evrcpnuseperiod" id="periodEV_months_6" value="months_6"/><label for="periodEV_months_6">6개월</label>
                                        <input type="radio" v-model="info.evrcpnuseperiod" id="periodEV_years_1"  value="years_1" /><label for="periodEV_years_1" >1년</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-show="info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_REGULAR">
                                <th>사용기간<i class="essential"></i></th>
                                <td>
                                    <div class="dpb" v-for="item in commonCode.cpnusetype" :key="item.cmcode">
                                        <input type="radio" name="cpnusetypeEV" class="circle" :id="'cpnusetypeEV_'+item.cmcode" :value="item.cmcode" v-model="info.cpnusetype" v-if="item.cmcode != $store.getters['ADMIN'].CPN_USE_PERIOD">
                                        <label :for="'cpnusetypeEV_'+item.cmcode" v-if="item.cmcode != $store.getters['ADMIN'].CPN_USE_PERIOD">{{ item.codename }}</label>
                                        <input type="text" class="center" style="width: 60px;" v-model="info.issuedaycnt" v-if="item.cmcode == $store.getters['ADMIN'].CPN_USE_DAY_CNT" maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');">
                                        <span class="ml3" v-if="item.cmcode == $store.getters['ADMIN'].CPN_USE_DAY_CNT">일간 사용가능</span>
                                    </div>
                                </td>
                            </tr>
                            <!-- 정기발급이 아닌경우 -->
                            <tr v-show="info.cpnissuetype != $store.getters['ADMIN'].CPN_ISSUE_REGULAR">
                                <th>사용기간<i class="essential"></i></th>
                                <td>
                                    <div class="dpb" v-for="item in commonCode.cpnusetype" :key="item.cmcode">
                                        <input type="radio" name="cpnusetypeD" class="circle" :id="'cpnusetypeD_'+item.cmcode" :value="item.cmcode" v-model="info.cpnusetype">
                                        <label :for="'cpnusetypeD_'+item.cmcode">{{ item.codename }}</label>
                                        <CommonDatePickerFromTo
                                            :fromYYYYMMDD="info.cpnusestdate"
                                            :fromHH="info.cpnusesthour"
                                            :fromMM="info.cpnusestmin"
                                            :toYYYYMMDD="info.cpnuseeddate"
                                            :toHH="info.cpnuseedhour"
                                            :toMM="info.cpnuseedmin"
                                            :useFrom="true"
                                            :useTo="true"
                                            @getDate="pickerCpnuseChangeEvent"
                                            v-show="item.cmcode == $store.getters['ADMIN'].CPN_USE_PERIOD"
                                        />
                                        <div class="radio_wrap dpib ml3 period" v-show="item.cmcode == $store.getters['ADMIN'].CPN_USE_PERIOD">
                                            <input type="radio" v-model="info.cpnuseperiod" id="periodD_aday_0"  value="aday_0" /><label for="periodD_aday_0">오늘</label>
                                            <input type="radio" v-model="info.cpnuseperiod" id="periodD_days_3"  value="days_3" /><label for="periodD_days_3">3일</label>
                                            <input type="radio" v-model="info.cpnuseperiod" id="periodD_days_7"  value="days_7" /><label for="periodD_days_7">7일</label>
                                            <input type="radio" v-model="info.cpnuseperiod" id="periodD_days_30" value="days_30"/><label for="periodD_days_30">30일</label>
                                        </div>
                                        <input type="text" class="center" style="width: 60px;" v-model="info.issuedaycnt" v-if="item.cmcode == $store.getters['ADMIN'].CPN_USE_DAY_CNT" maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');">
                                        <span class="ml3" v-if="item.cmcode == $store.getters['ADMIN'].CPN_USE_DAY_CNT">일간 사용가능</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>사용채널<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllChannelD" v-model="info.isallmuappch" true-value="T" false-value="F" @change="checkAllAppchtype">
                                        <label for="chkAllChannelD">전체적용</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                                        <input type="checkbox" :id="'muappchtypeD_'+item.cmcode" v-model="info.muappchtypeArr" :true-value="[]" :value="item.cmcode"/>
                                        <label :for="'muappchtypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.comcpntype != $store.getters['ADMIN'].COM_CPN_DELIV">
                                <th>사용가능기준금액<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" v-model="info.isorderlimit" name="isorderlimitD" id="isorderlimitDF" value="F"/>
                                        <label for="isorderlimitDF">제한없음</label>
                                        <input type="radio" v-model="info.isorderlimit" name="isorderlimitD" id="isorderlimitDT" value="T"/>
                                        <label for="isorderlimitDT">주문금액기준</label>
                                    </div>
                                    <div class="dpib ml3" v-if="info.isorderlimit == 'T'">
                                        <input type="text" class="right" style="width: 80px;" v-model="info.orlimitamt" maxlength="11" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');">
                                        <span class="ml3">원 이상 구매 시</span>
                                        <span class="txt-orange ml10" v-if="info.comcpntype===$store.getters['ADMIN'].COM_CPN_GOODS"><i class="icon-alert"></i>할인 프로모션(즉시할인)이 적용된 상품금액 기준</span>
                                        <span class="txt-orange ml10" v-else><i class="icon-alert"></i>할인 프로모션(즉시할인)이 적용된 주문금액 기준</span>
                                    </div>
                                </td>
                            </tr>
                            <!-- 배송비 쿠폰인 경우 -->
                            <tr v-else>
                                <th>사용가능기준금액<i class="essential"></i></th>
                                <td>
                                    <span>상품 금액 기준</span>
                                    <input type="text" class="right ml3" style="width: 80px;" v-model="info.orlimitamt" maxlength="11" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');">
                                    <span class="ml3">원 이상 구매 시</span>
                                    <span class="txt-orange ml10"><i class="icon-alert"></i>할인 프로모션(즉시할인)이 적용된 상품금액 기준</span>
                                </td>
                            </tr>
                            <!-- // 배송비 쿠폰인 경우 -->
                            <tr v-if="info.comcpntype != $store.getters['ADMIN'].COM_CPN_DELIV">
                                <th>할인 정액/정률<i class="essential"></i></th>
                                <td>
                                    <div class="dpb">
                                        <input type="radio" name="ispercentD" class="circle" id="ispercentDF" v-model="info.ispercent" value="F">
                                        <label for="ispercentDF">정액</label>
                                        <input type="text" class="right ml3" style="width: 80px;" v-model="info.disprice" maxlength="11" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');">
                                        <span class="ml3">원</span>
                                    </div>
                                    <div class="dpb">
                                        <input type="radio" name="ispercentD" class="circle" id="ispercentDT" v-model="info.ispercent" value="T">
                                        <label for="ispercentDT">정률</label>
                                        <input type="text" class="right ml3" style="width: 80px;" v-model="info.dispercent" maxlength="11">
                                        <span class="ml3">% (원단위 절사/최대할인금액</span>
                                        <input type="text" class="right ml3" style="width: 80px;" v-model="info.maxdisamt" maxlength="11" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');">
                                        <span class="ml3">원)</span>
                                    </div>
                                    <div class="dpb mt10" v-if="info.comcpntype == $store.getters['ADMIN'].COM_CPN_GOODS">
                                        <input type="checkbox" id="isrvmarginD" v-model="info.isrvmargin" true-value="T" false-value="F">
                                        <label for="isrvmarginD">역마진 발생상품 제외</label>
                                    </div>
                                </td>
                            </tr>
                            <!-- 배송비 쿠폰인 경우 -->
                            <tr v-else>
                                <th>배송비 최대할인금액<i class="essential"></i></th>
                                <td>
                                    <span>최대</span>
                                    <input type="text" class="right ml3" style="width: 80px;" v-model="info.maxdisamt" maxlength="11" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');">
                                    <span class="ml3">원 까지</span>
                                </td>
                            </tr>
                            <!-- // 배송비 쿠폰인 경우 -->
                            <!-- 장바구니 쿠폰인 경우 -->
                            <tr v-if="info.comcpntype == $store.getters['ADMIN'].COM_CPN_CART">
                                <th>중복사용범위</th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllCpndupD" v-model="info.isallcpndup" true-value="T" false-value="F" @change="checkAllCpnduptype">
                                        <label for="chkAllCpndupD">전체적용</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.mucpnduptype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mucpnduptypeD_'+item.cmcode" v-model="info.mucpnduptypeArr" :true-value="[]" :value="item.cmcode"/>
                                        <label :for="'mucpnduptypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <!-- // 장바구니 쿠폰인 경우 -->
                        </tbody>
                    </table>
                </div>
                <!-- 구매확정보상인 경우 -->
                <div class="bar-title" v-show="!$util.isNull(info.cpnissuetype) && info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_PURCH_CONFIRM">구매확정 조건</div>
                <div class="form-area" v-show="!$util.isNull(info.cpnissuetype) && info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_PURCH_CONFIRM">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>구분<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="isfirstordD" id="isfirstordDF" v-model="info.isfirstord" value="F" />
                                        <label for="isfirstordDF">재구매 확정</label>
                                        <input type="radio" name="isfirstordD" id="isfirstordDT" v-model="info.isfirstord" value="T" />
                                        <label for="isfirstordDT">첫구매 확정</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.isfirstord=='F'">
                                <th>대상카테고리범위<i class="essential"></i><br>(구매확정대상)</th>
                                <td>
                                    <div class="radio_wrap dpib">
                                        <input type="radio" name="isordtotcateD" id="isordtotcateDT" v-model="info.isordtotcate" value="T"/>
                                        <label for="isordtotcateDT">전체</label>
                                        <input type="radio" name="isordtotcateD" id="isordtotcateDF" v-model="info.isordtotcate" value="F"/>
                                        <label for="isordtotcateDF">특정카테고리</label>
                                    </div>
                                    <div class="dpib" v-if="info.isordtotcate == 'F'">
                                        <select style="width: 175px;" v-model="info.depth1OrdCategory.idx" @change="setCategoryCodeInfo('1', info.depth1OrdCategory.idx, 'ord')">
                                            <option value="">대분류</option>
                                            <option v-for="item in categoryObj.depth1ordlist" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                                        </select>
                                        <select style="width: 175px;" v-model="info.depth2OrdCategory.idx" @change="setCategoryCodeInfo('2', info.depth2OrdCategory.idx, 'ord')">
                                            <option value="">중분류</option>
                                            <option v-for="item in categoryObj.depth2ordlist" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                                        </select>
                                        <select style="width: 175px;" v-model="info.depth3OrdCategory.idx" @change="setCategoryCodeInfo('3', info.depth3OrdCategory.idx, 'ord')">
                                            <option value="">소분류</option>
                                            <option v-for="item in categoryObj.depth3ordlist" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                                        </select>
                                        <select style="width: 175px;" v-model="info.depth4OrdCategory.idx" @change="setCategoryCodeInfo('4', info.depth4OrdCategory.idx, 'ord')">
                                            <option value="">세분류</option>
                                            <option v-for="item in categoryObj.depth4ordlist" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                                        </select>
                                        <button type="button" class="add" @click="addCpnCategory('T', 'ord')"></button>
                                        <button type="button" class="minus" @click="addCpnCategory('F', 'ord')"></button>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.isordtotcate == 'F'">
                                <th>특정 카테고리<i class="essential"></i><br>(구매확정대상)</th>
                                <td>
                                    <div class="category-selected">
                                        <ul>
                                            <li v-for="item in info.cpnordcateList" :key="item.idx">
                                                <span class="box mr5">{{ item.isadd=='T'? '추가':'제외' }}</span>
                                                <span>{{ item.fullcategoryname }}</span>
                                                <button type="button" class="del" @click="removeCategory(item, 'cpnordcateList')"></button>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.isfirstord=='F'">
                                <th>대상 상품범위<i class="essential"></i><br>(구매확정대상)</th>
                                <td>
                                    <div class="radio_wrap">
                                        <div v-for="item in commonCode.goodsrangetype" :key="item.cmcode">
                                            <input type="radio" name="ordgdrangetypeD" :id="'ordgdrangetypeD_'+item.cmcode" :value="item.cmcode" v-model="info.ordgdrangetype"
                                                :disabled="info.isordtotcate=='F' && item.cmcode==$store.getters['ADMIN'].GOODS_RANGE_ALL"/>
                                            <label :for="'ordgdrangetypeD_'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="(info.isordtotcate=='F' || !$util.isNull(info.ordgdrangetype)) && info.ordgdrangetype != $store.getters['ADMIN'].GOODS_RANGE_ALL">
                                <th>대상 상품<i class="essential"></i><br>(구매확정대상)</th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">적용대상 상품목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" @click="addGoods('cpnordgoodsList')">상품추가</button>
                                            <button type="button" class="btn red-line" @click="removeGoods('cpnordgoodsList')">삭제</button>
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
                                                <col width="5.5%" /><!-- 단품코드 -->
                                                <col width="62px" /><!-- 이미지 -->
                                                <col width="" /><!-- 상품명 -->
                                                <col width="12%" /><!-- 옵션명 -->
                                                <col width="7%" /><!-- 판매가 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" id="chkallcpnordgoods" v-model="info.isallchkcpnordgoods" @change="checkAllGoodsList($event.target.checked, 'cpnordgoodsList')" /></th>
                                                    <th>No</th>
                                                    <th>판매구분</th>
                                                    <th>파트너사명</th>
                                                    <th>상품코드</th>
                                                    <th>단품코드</th>
                                                    <th colspan="2">상품명</th>
                                                    <th>옵션명</th>
                                                    <th>판매가</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="info.cpnordgoodsList.length > 0">
                                                <tr v-for="(item, index) in info.cpnordgoodsList" :key="item.goodsno+'_'+item.optioncode">
                                                    <td><input type="checkbox" :id="'ord_'+item.goodsno+'_'+item.optioncode" v-model="item.ischecked" @change="checkGoodsList('cpnordgoodsList')"/></td>
                                                    <td>{{ index+1 }}</td>
                                                    <td>{{ item.ispbgoodsname }}</td>
                                                    <td>{{ item.dealername }}</td>
                                                    <td>{{ item.goodscode }}</td>
                                                    <td>{{ item.optioncode }}</td>
                                                    <td>
                                                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                                            <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                                        </div>
                                                    </td>
                                                    <td class="left no-left">
                                                        <span class="small-txt">{{ item.fullcategoryname }}</span>
                                                        <p class="mg0">{{ item.goodsname }}</p>
                                                    </td>
                                                    <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                                                    <td class="right">{{ $util.maskComma(item.price) }}</td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="10">조회 결과가 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!-- // 구매확정보상인 경우 -->
                <div class="bar-title" v-show="!$util.isNull(info.comcpntype) && !$util.isNull(info.cpnissuetype)">발급 조건</div>
                <div class="form-area" v-show="!$util.isNull(info.comcpntype) && !$util.isNull(info.cpnissuetype)">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <!-- 정기발급인 경우 -->
                            <tr v-show="info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_REGULAR">
                                <th>재발급주기<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="iseverydayD" id="iseverydayDT" v-model="info.iseveryday" value="T">
                                        <label for="iseverydayDT">발급 후</label>
                                        <input type="radio" name="iseverydayD" id="iseverydayDF" v-model="info.iseveryday" value="F">
                                        <label for="iseverydayDF">매월</label>
                                    </div>
                                    <div class="dpib">
                                        <input type="text" class="center" style="width: 60px;" v-if="info.iseveryday=='F'" v-model="info.everymonthloop" maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');">
                                        <input type="text" class="center" style="width: 60px;" v-else v-model="info.everydayloop" maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');">
                                        <span class="ml3">일마다 재발급</span>
                                        <DatePicker v-model="info.everyhh" class="com-time ml10" type="time" format="HH시" value-type="format" style="width: 70px" @change="changeEveryHHMM"/>
                                        <DatePicker v-model="info.everymm" class="com-time ml3" type="time" format="mm분" value-type="format" style="width: 70px" @change="changeEveryHHMM"/>
                                        <span>에 발급</span>
                                    </div>
                                </td>
                            </tr>
                            <!-- // 정기발급인 경우 -->
                            <tr v-show="info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_NOW_DISCOUNT
                                    || (info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_REGULAR && info.iseveryday == 'T')">
                                <th>발급시점<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap dpib">
                                        <input type="radio" name="isnowissueD" id="isnowissueDT" v-model="info.isnowissue" value="T" />
                                        <label for="isnowissueDT">즉시발급</label>
                                        <input type="radio" name="isnowissueD" id="isnowissueDF" v-model="info.isnowissue" value="F" />
                                        <label for="isnowissueDF">지정시점에 발급</label>
                                    </div>
                                    <div class="dpib ml3" v-show="info.isnowissue=='F'">
                                        <CommonDatePickerFromTo
                                            :fromYYYYMMDD="info.cpnissuedate"
                                            :fromHH="info.cpnissuehour"
                                            :fromMM="info.cpnissuemin"
                                            :useFrom="true"
                                            :useTo="false"
                                            @getDate="pickerCpnissueChangeEvent"
                                        />
                                    </div>
                                </td>
                            </tr>
                            <!-- 신규가입인 경우 -->
                            <tr v-show="info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_NEW_JOIN">
                                <th>발급시점<i class="essential"></i></th>
                                <td>
                                    <span>신규 회원가입 완료 후 즉시 발급됩니다.</span>
                                </td>
                            </tr>
                            <!-- // 신규가입인 경우 -->
                            <!-- 생일인 경우 -->
                            <tr v-show="info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_BIRTHDAY">
                                <th>발급시점<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="isbirththedayD" id="isbirththedayDT" v-model="info.isbirththeday" value="T" />
                                        <label for="isbirththedayDT">생일당일</label>
                                        <input type="radio" name="isbirththedayD" id="isbirththedayDF" v-model="info.isbirththeday" value="F" />
                                        <label for="isbirththedayDF">생일</label>
                                    </div>
                                    <div class="dpib ml3" v-if="info.isbirththeday=='F'">
                                        <input type="text" class="right" style="width: 80px;" v-model="info.birthbfdaycnt" maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');">
                                        <span class="ml3">일전</span>
                                    </div>
                                </td>
                            </tr>
                            <!-- // 생일인 경우 -->
                            <!-- 구매확정보상인 경우 -->
                            <tr v-show="info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_PURCH_CONFIRM">
                                <th>발급시점<i class="essential"></i></th>
                                <td>
                                    <span>구매확정조건 만족 시 즉시 발급됩니다.</span>
                                </td>
                            </tr>
                            <!-- // 구매확정보상인 경우 -->
                            <!-- 회원(프로모션)인 경우 -->
                            <tr v-show="info.cpnissuetype == $store.getters['ADMIN'].CPN_ISSUE_MEM_PROMO">
                                <th>발급시점<i class="essential"></i></th>
                                <td>
                                    <span>추천 리워드(쿠폰) 신규 등록 시, 시리얼 프로모션(쿠폰) 등록 시 발급됩니다.</span>
                                </td>
                            </tr>
                            <!-- // 회원(프로모션)인 경우 -->
                            <tr>
                                <th>발급방법<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="isautopayD" id="isautopayDT" v-model="info.isautopay" value="T" />
                                        <label for="isautopayDT">자동적용</label>
                                        <input type="radio" name="isautopayD" id="isautopayDF" v-model="info.isautopay" value="F" />
                                        <label for="isautopayDF">다운로드</label>
                                    </div>
                                </td>
                            </tr>
                            <!-- 상품쿠폰, 즉시할인인 경우 -->
                            <tr v-if="info.comcpntype===$store.getters['ADMIN'].COM_CPN_GOODS && info.cpnissuetype===$store.getters['ADMIN'].CPN_ISSUE_NOW_DISCOUNT && info.isautopay=='T'">
                                <th>쿠폰수량제한<i class="essential"></i></th>
                                <td>
                                    <span>상품 즉시할인 쿠폰의 자동적용은 수량을 제한하지 않습니다.</span>
                                </td>
                            </tr>
                            <!-- // 상품쿠폰, 즉시할인인 경우 -->
                            <tr v-if="info.isautopay=='F'">
                                <th>쿠폰수량제한<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="iscntlimtD" id="iscntlimtDF" v-model="info.iscntlimt" value="F" />
                                        <label for="iscntlimtDF">제한없음</label>
                                        <input type="radio" name="iscntlimtD" id="iscntlimtDT" v-model="info.iscntlimt" value="T" />
                                        <label for="iscntlimtDT">제한</label>
                                    </div>
                                    <div class="dpib ml3" v-if="info.iscntlimt=='T'">
                                        <span>선착순 최대</span>
                                        <input type="text" class="right ml3" style="width: 80px;" v-model="info.cpnlimtcnt" maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');">
                                        <span class="ml3">장</span>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.isautopay=='F'">
                                <th>계정당 발급 제한<i class="essential"></i></th>
                                <td>
                                    <!-- div class="radio_wrap wide dpib" >
                                        <input type="radio" name="isduppersonD" id="isduppersonDF" v-model="info.isdupperson" value="F" />
                                        <label for="isduppersonDF">불가능</label>
                                        <input type="radio" name="isduppersonD" id="isduppersonDT" v-model="info.isdupperson" value="T" />
                                        <label for="isduppersonDT">가능</label>
                                    </div-->
                                    <div class="dpib ml3" v-if="info.isdupperson=='T'">
                                        <input type="text" class="right" style="width: 80px;" v-model="info.dupcnt" maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');">
                                        <span class="ml3">회까지 다운 가능</span>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="save">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /쿠폰 등록 팝업 -->
</template>


<script src="./CouponRegist.js"/>