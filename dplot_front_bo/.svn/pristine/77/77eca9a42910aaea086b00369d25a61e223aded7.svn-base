<template>
    <!-- 배송정보 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1000px;">
            <div class="pop-header">
                <h2>배송정보 상세</h2>
                <button type="button" class="pop-close" @click="$modal.hide('delivTempDetail');"></button>
            </div>
            <div class="pop-body">
                <div class="bar-title small" ref="bodytop">기본정보</div>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="170px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>사용여부</th>
                            <td>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="istrash" id="istrashF" v-model="data.istrash" value="F"/>
                                    <label for="istrashF">사용</label>
                                    <input type="radio" name="istrash" id="istrashT" v-model="data.istrash" value="T" />
                                    <label for="istrashT">미사용</label>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>배송유형</th>
                            <td>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="iscombdeliv" id="iscombdelivF" v-model="data.iscombdeliv" value="F"/>
                                    <label for="iscombdelivF">개별배송</label>
                                    <input type="radio" name="iscombdeliv" id="iscombdelivT" v-model="data.iscombdeliv" value="T" />
                                    <label for="iscombdelivT">묶음배송</label>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>배송정보명<i class="essential"></i></th>
                            <td>
                                <input type="text" style="width: 100%;" v-model="data.delivbname" ref="delivbname" maxlength="100"/>
                            </td>
                        </tr>
                        <tr>
                            <th>배송방법<i class="essential"></i></th>
                            <td>
                                <select v-model="data.delivtype" ref="delivtype">
                                    <option v-for="item in commonCode.delivtype" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                </select>
                                <select v-model="data.logistype" v-if="data.delivtype == constants.DELIV_TYPE_DLT001" ref="logistype">
                                    <option value="">택배사 선택</option>
                                    <option v-for="item in commonCode.logistype" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>배송일정</th>
                            <td>
                                <select v-model="data.delivschtype">
                                    <option v-for="item in commonCode.delivschtype" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                </select>
                                <span class="txt-orange ml3" v-if="data.delivschtype == constants.DELIV_SCH_TYPE_DSC001"><i class="icon-alert"></i>배송안내 : 주문확인 후 최대 1~4일 내 배송완료</span>
                                <span class="txt-orange ml3" v-if="data.delivschtype == constants.DELIV_SCH_TYPE_DSC002"><i class="icon-alert"></i>배송안내 : 배송템플릿 선택 후 직접 입력</span>
                                <span class="txt-orange ml3" v-if="data.delivschtype == constants.DELIV_SCH_TYPE_DSC003"><i class="icon-alert"></i>배송안내 : 배송템플릿 선택 후 직접 입력</span>
                                <span class="txt-orange ml3" v-if="data.delivschtype == constants.DELIV_SCH_TYPE_DSC004"><i class="icon-alert"></i>배송안내 : 배송템플릿 선택 후 직접 입력</span>
                                <span class="txt-orange ml3" v-if="data.delivschtype == constants.DELIV_SCH_TYPE_DSC005"><i class="icon-alert"></i>배송안내 : 주문확인 후 최대 7~14일 내 배송완료</span>
                            </td>
                        </tr>
                        <tr>
                            <th>배송비</th>
                            <td>
                                <!-- <div class="radio_wrap dpib">
                                    <div v-for="(item, index) in commonCode.delivfaretype" :key="item.cmcode">
                                        <input type="radio" name="delivschtype" :id="'delivschtype_'+index" v-model="data.delivfaretype" :value="item.cmcode" @change="initDelivfare(item)"/>
                                        <label :for="'delivschtype_'+index">{{ item.codename }}</label>
                                    </div>
                                </div> -->
                                <div class="dpib">
                                    <select v-model="data.delivfaretype" ref="delivfaretype">
                                        <option v-for="item in commonCode.delivfaretype" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                    </select>
                                </div>
                                <!-- 유료 선택 시 노출 class="dpib" -->
                                <div class="dpib" v-if="data.delivfaretype == constants.DELIV_FARE_DCT002">
                                    <input type="text" class="short right" v-model="data.delivfare" ref="delivfare" maxlength="11"/> 원
                                </div>
                                <!-- 조건부 무료 선택 시 노출 class="dpib" -->
                                <div class="dpib" v-if="data.delivfaretype == constants.DELIV_FARE_DCT003">
                                    <input type="text" class="short right" v-model="data.delivfare" ref="delivfare" maxlength="11"/>
                                    <span class="ml3">원 (</span>
                                    <input type="text" class="short right ml3" v-model="data.delivfreefare" ref="delivfreefare" maxlength="11"/>
                                    <span class="ml3">원 이상 구매 시 무료)</span>
                                </div>
                                <!-- 구매수량비례 선택 시 노출 class="dpib" -->
                                <div class="dpib" v-if="data.delivfaretype == constants.DELIV_FARE_DCT005">
                                    <span class="ml3">구매 수량에 따라 배송비 </span>
                                    <input type="text" class="short right" v-model="data.delivfare" ref="delivfare" maxlength="11"/>
                                    <span class="ml3">원 씩 부과</span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>배송가능지역</th>
                            <td>
                                <div class="radio_wrap dpib">
                                    <input type="radio" name="isnationdeliv" id="isnationdelivT" v-model="data.isnationdeliv" value="T"/>
                                    <label for="isnationdelivT">전국</label>
                                    <input type="radio" name="isnationdeliv" id="isnationdelivF" v-model="data.isnationdeliv" value="F"/>
                                    <label for="isnationdelivF">도서산간지역제외</label>
                                </div>
                            </td>
                        </tr>
                        <tr v-if="data.isnationdeliv==='T'">
                            <th>도서산간추가배송비(편도)</th>
                            <td>
                                <div class="dpb">
                                    <span>제주 <input type="text" class="short right" v-model="data.chejufare" maxlength="11"/> 원</span>
                                    <span class="left-bar">도서산간 <input type="text" class="short right" v-model="data.isolfare" maxlength="11"/> 원</span>
                                    <span class="left-bar"><button type="button" class="btn blue-line" @click="openDelivIsolListPopup">도서산간 지역조회</button></span>
                                </div>
                                <span class="txt-orange dpb"><i class="icon-alert"></i>기본 배송비 외 제주/도서산간 지역 배송지에 한해 추가로 부과하는 배송비입니다.</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="bar-title small">교환/반품 정보</div>
                <span v-if="data.isPartner && data.rtndelivtype == constants.DELIV_TYPE_DLT001" class="txt-orange dpb"><i class="icon-alert"></i>교환/반품 택배 자동수거지시 서비스는 굿스플로와 연동되어 있는 기본택배사만 이용이 가능합니다. (기본택배사 : CJ대한통운, 우체국택배, 한진택배, 로젠택배, 롯데택배)</span>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="170px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>교환/반품택배사<i class="essential"></i></th>
                            <td>
                                <select v-model="data.rtndelivtype" ref="rtndelivtype">
                                    <option v-for="item in commonCode.rtndelivtype" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                </select>
                                <select v-model="data.rtnlogistype" v-if="data.rtndelivtype == constants.DELIV_TYPE_DLT001" ref="rtnlogistype">
                                    <option value="">택배사 선택</option>
                                    <option v-for="item in rtnLogisList" :key="item.rtnlogistype" :value="item.rtnlogistype">
                                    {{ item.logistypename }}
                                    </option>
                                </select>
                                <select v-model="data.rtnlogistype" v-if="data.rtndelivtype == constants.DELIV_TYPE_DLT004" ref="rtnlogistype">
                                    <option value="">택배사 선택</option>
                                    <option v-for="item in rtnManlogisList" :key="item.rtnlogistype" :value="item.rtnlogistype">
                                    {{ item.logistypename }}
                                    </option>
                                </select>
                                <span class="left-bar" v-if="data.isPartner && data.rtndelivtype == constants.DELIV_TYPE_DLT001"><button type="button" class="btn blue-line" @click="applyDeliveryService">기본택배사 이용 신청</button></span>
                                <span class="left-bar" v-if="data.isPartner && data.rtndelivtype == constants.DELIV_TYPE_DLT001"><button type="button" class="btn blue-line" @click="applyDeliveryServiceRefresh">기본택배사 이용 신청 갱신</button></span>
                            </td>
                        </tr>
                        <tr>
                            <th>교환 배송비<i class="essential"></i></th>
                            <td>
                                <span>편도 <input type="text" class="short right" v-model="data.exowfare" maxlength="11">원</span>
                                <span class="left-bar">왕복 <input type="text" class="short right" v-model="data.exrtnfare" maxlength="11">원</span>
                            </td>
                        </tr>
                        <tr>
                            <th>반품 배송비<i class="essential"></i></th>
                            <td>
                                <span>편도 <input type="text" class="short right" v-model="data.rfowfare" maxlength="11">원</span>
                                <span class="left-bar">왕복 <input type="text" class="short right" v-model="data.rfrtnfare" maxlength="11">원</span>
                            </td>
                        </tr>
                        <tr>
                            <th>출고지 주소<i class="essential"></i></th>
                            <td>
                                <div class="dpb">
                                    <input type="text" class="short" v-model="data.relpost" readonly>
                                    <button type="button" class="btn blue-line ml3" @click="searchAddress('rel')">주소검색</button>
                                    <input type="checkbox" id="chkbizaddr" class="ml10" v-model="data.isSameAsBizaddr" @change="setSameAsBizAddr()">
                                    <label for="chkbizaddr">사업자주소 동일적용</label>
                                </div>
                                <input type="text" class="dpb" style="width: 100%;" v-model="data.reladdr" ref="reladdr" readonly>
                                <input type="text" class="dpb" style="width: 100%;" v-model="data.reladdrdetail">
                                <span class="small-txt">[{{ data.reladdr==''? ' ':'도로명' }}] {{ data.reladdr + " " + data.reladdrdetail }}</span>
                            </td>
                        </tr>
                        <tr>
                            <th>교환/반품 주소<i class="essential"></i></th>
                            <td>
                                <div class="dpb">
                                    <input type="text" class="short" v-model="data.rfpost" readonly>
                                    <button type="button" class="btn blue-line ml3" @click="searchAddress('rf')">주소검색</button>
                                    <input type="checkbox" id="chkreladdr" class="ml10" v-model="data.isSameAsReladdr" @change="setSameAsRelAddr()">
                                    <label for="chkreladdr">출고지주소 동일적용</label>
                                    <span class="txt-orange ml10"><i class="icon-alert"></i>대한통운의 경우 택배 계약시 등록한 교환/반품 주소와 반드시 동일하게 입력해주세요.</span>
                                </div>
                                <input type="text" class="dpb" style="width: 100%;" v-model="data.rfaddr" ref="rfaddr" readonly>
                                <input type="text" class="dpb" style="width: 100%;" v-model="data.rfaddrdetail">
                                <span class="small-txt">[{{ data.rfaddr==''? ' ':'도로명' }}] {{ data.rfaddr + " " + data.rfaddrdetail }}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="saveDelivTemp">저장</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('delivTempDetail');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /배송정보 상세 팝업 -->
</template>

<script src="./DelivTempDetailPopup.js"></script>