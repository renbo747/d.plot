<template>
    <!-- 프로모션 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>프로모션 상세</h2>
                <button type="button" id="close" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix" ref="bodytop">
                    <div class="fl txt-gray">
                        <span>작성자 {{ info.reguserid }}</span>
                    </div>
                    <div class="fr txt-gray">
                        <span>등록일 : {{ info.regdate }}</span>
                        <span class="left-bar">수정일 : {{ info.moddate }}</span>
                    </div>
                </div>
                <div class="bar-title mt10">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>사용여부</dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" v-model="info.istrash" name="istrashD" id="istrashDF" value="F" :disabled="info.promosttype == $store.getters['ADMIN'].PROMO_ST_END"/>
                                    <label for="istrashDF">사용함</label>
                                    <input type="radio" v-model="info.istrash" name="istrashD" id="istrashDT" value="T" :disabled="info.promosttype == $store.getters['ADMIN'].PROMO_ST_END"/>
                                    <label for="istrashDT">사용안함</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>프로모션명<i class="essential"></i></dt>
                            <dd><input type="text" style="width: 100%" placeholder="프로모션명" v-model="info.promoname" maxlength="200" :disabled="isdisabled"/></dd>
                        </dl>
                        <dl>
                            <dt>설명<i class="essential"></i></dt>
                            <dd><input type="text" style="width: 100%" placeholder="설명" v-model="info.promodesc" maxlength="400" :disabled="isdisabled"/></dd>
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
                            <!-- 공통조건설정 -->
                            <tr>
                                <th>진행기간<i class="essential"></i></th>
                                <td>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="info.promostdate"
                                        :fromHH="info.promosthour"
                                        :fromMM="info.promostmin"
                                        :toYYYYMMDD="info.promoeddate"
                                        :toHH="info.promoedhour"
                                        :toMM="info.promoedmin"
                                        :useFrom="true"
                                        :useTo="true"
                                        :fromDisable="isdisabled" 
                                        :toDisable="isdisabled"
                                        @getDate="pickerChangeEvent"
                                    />
                                </td>
                            </tr>
                            <tr>
                                <th>적용채널</th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllChannelD" v-model="info.isallmuappch" true-value="T" false-value="F" @change="checkAllAppchtype" :disabled="isdisabled">
                                        <label for="chkAllChannelD">전체적용</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                                        <input type="checkbox" :id="'muappchtypeD_'+item.cmcode" v-model="info.muappchtypeArr" :true-value="[]" :value="item.cmcode" :disabled="isdisabled"/>
                                        <label :for="'muappchtypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원유형</th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllMemberD" v-model="info.isallmember" true-value="T" false-value="F" @change="checkAllMembertype" :disabled="isdisabled">
                                        <label for="chkAllMemberD">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mumembertypeD_'+item.cmcode" v-model="info.mumembertypeArr" :true-value="[]" :value="item.cmcode" :disabled="isdisabled">
                                        <label :for="'mumembertypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원등급</th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllMemlvD" v-model="info.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype" :disabled="isdisabled">
                                        <label for="chkAllMemlvD">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.memlvtype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mumemlvtypeD_'+item.cmcode" v-model="info.mumemlvtypeArr" :true-value="[]" :value="item.cmcode" :disabled="isdisabled">
                                        <label :for="'mumemlvtypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>프로모션구분<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap">
                                        <div v-for="item in commonCode.promodivtype" :key="item.cmcode">
                                            <input type="radio" name="promodivtypeD" :id="'promodivtypeD_'+item.cmcode" :value="item.cmcode" v-model="info.promodivtype" :disabled="isdisabled"/>
                                            <label :for="'promodivtypeD_'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <!-- // 공통 조건설정 -->
                            
                            <!-- 프로모션구분-사은품 -->
                            <tr v-if="info.promodivtype==$store.getters['ADMIN'].PROMO_DIV_GIFT">
                                <th>사은품 지급조건<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap">
                                        <div v-for="item in commonCode.gifttermtype" :key="item.cmcode">
                                            <input type="radio" name="gifttermtypeD" :id="'gifttermtypeD_'+item.cmcode" :value="item.cmcode" v-model="info.gifttermtype" :disabled="isdisabled"/>
                                            <label :for="'gifttermtypeD_'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <!-- 프로모션구분 선택시 -->
                            <tr v-if=" (info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_DISCOUNT)
                                    || (info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_GIFT && !$util.isNull(info.gifttermtype))
                                    || (info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_RESERVE)">
                                <th>대상카테고리범위</th>
                                <td>
                                    <div class="radio_wrap dpib">
                                        <input type="radio" name="istotcateD" id="istotcateDT" v-model="info.istotcate" value="T" :disabled="isdisabled"/>
                                        <label for="istotcateDT">전체</label>
                                        <input type="radio" name="istotcateD" id="istotcateDF" v-model="info.istotcate" value="F" :disabled="isdisabled"/>
                                        <label for="istotcateDF">특정카테고리</label>
                                    </div>
                                    <div class="dpib" v-if="info.istotcate == 'F' && !isdisabled">
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
                                        <button type="button" class="add" @click="addPromoCategory('T')"></button>
                                        <button type="button" class="minus" @click="addPromoCategory('F')"></button>
                                    </div>
                                </td>
                            </tr>
                            <!-- 프로모션구분 선택시 -->
                            <tr v-if="!$util.isNull(info.promodivtype) && info.istotcate == 'F'">
                                <th>특정 카테고리<i class="essential"></i></th>
                                <td>
                                    <div class="category-selected">
                                        <ul>
                                            <li v-for="item in promocateList" :key="item.idx">
                                                <span class="box mr5">{{ item.isadd=='T'? '추가':'제외' }}</span>
                                                <span>{{ item.fullcategoryname }}</span>
                                                <button type="button" class="del" @click="removeCategory(item)"></button>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <!-- 프로모션구분 선택시 -->
                            <tr v-if=" (info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_DISCOUNT)
                                    || (info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_GIFT && !$util.isNull(info.gifttermtype))
                                    || (info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_RESERVE)">
                                <th>대상 상품범위</th>
                                <td>
                                    <div class="radio_wrap">
                                        <div v-for="item in commonCode.goodsrangetype" :key="item.cmcode">
                                            <input type="radio" name="goodsrangetypeD" :id="'goodsrangetypeD'+item.cmcode" :value="item.cmcode" v-model="info.goodsrangetype"
                                                :disabled="(info.istotcate=='F' && item.cmcode==$store.getters['ADMIN'].GOODS_RANGE_ALL) || isdisabled"/>
                                            <label :for="'goodsrangetypeD'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <!-- 프로모션구분 선택시 -->
                            <tr v-if="!$util.isNull(info.promodivtype) && (info.istotcate=='F' || !$util.isNull(info.goodsrangetype)) && info.goodsrangetype != $store.getters['ADMIN'].GOODS_RANGE_ALL">
                                <th>대상 상품<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">적용대상 상품목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" @click="addGoods">상품추가</button>
                                            <button type="button" class="btn red-line" @click="removeGoods">삭제</button>
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
                                                    <th><input type="checkbox" id="chkallpromogoods" v-model="isallchkpromogoods" @change="checkAllGoodsList($event.target.checked)" /></th>
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
                                            <tbody v-if="!$util.isNull(promogoodsList) && promogoodsList.length > 0">
                                                <tr v-for="(item, index) in promogoodsList" :key="item.goodsno+'_'+item.optioncode">
                                                    <td><input type="checkbox" :id="item.goodsno+'_'+item.optioncode" v-model="item.ischecked" @change="checkGoodsList($event.target.checked)"/></td>
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
                                                    <td class="right">{{ item.price }}</td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="9">조회 결과가 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <!-- 프로모션구분-할인 -->
                            <tr v-if="info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_DISCOUNT">
                                <th>할인율<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="ispercentD" id="ispercentDF" v-model="info.ispercent" value="F" :disabled="isdisabled"/>
                                        <label for="ispercentDF">정액</label>
                                        <input type="radio" name="ispercentD" id="ispercentDT" v-model="info.ispercent" value="T" :disabled="isdisabled"/>
                                        <label for="ispercentDT">정률</label>
                                    </div>
                                    <div v-if="!$util.isNull(info.ispercent)"
                                        :class="{'dpib': info.ispercent == 'F', 'dpn': info.ispercent == 'T'}">
                                        <input type="text" class="right" style="width: 80px;" v-model="info.disprice" maxlength="11" :disabled="isdisabled"/>
                                        <span class="ml3">원</span>
                                    </div>
                                    <div v-if="!$util.isNull(info.ispercent)"
                                        :class="{'dpib': info.ispercent == 'T', 'dpn': info.ispercent == 'F'}">
                                        <input type="text" class="right" style="width: 80px;" v-model="info.dispercent" maxlength="8" :disabled="isdisabled"/>
                                        <span class="ml3">%</span>
                                    </div>
                                </td>
                            </tr>
                            <!-- 프로모션구분-할인 -->
                            <tr v-if="info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_DISCOUNT">
                                <th>분담비율</th>
                                <td>
                                    <span>파트너사</span><input type="text" class="ml3 right" style="width: 50px;" v-model="info.partratio" maxlength="5" :disabled="isdisabled"/><span class="ml3">%</span>
                                    <span>(D.PLOT</span><input type="text" class="ml3 right" style="width: 50px;" v-model="info.dadaratio"  maxlength="5" readonly /><span class="ml3">%)</span>
                                </td>
                            </tr>
                            <!-- 프로모션구분-사은품/사은품지급조건-특정상품구매시지급, 프로모션구분-적립금 -->
                            <tr v-if="(info.promodivtype==$store.getters['ADMIN'].PROMO_DIV_GIFT && info.gifttermtype==$store.getters['ADMIN'].GIFT_TERM_PURCH)
                                   || (info.promodivtype==$store.getters['ADMIN'].PROMO_DIV_RESERVE)">
                                <th>구매수량조건<i class="essential"></i></th>
                                <td>
                                    <input type="text" class="right" style="width: 50px;" v-model="info.giftgoodscnt" maxlength="5" :disabled="isdisabled"/>
                                    <span class="ml3" v-if="info.promodivtype==$store.getters['ADMIN'].PROMO_DIV_GIFT">가지 이상 상품 구매 시 지급</span>
                                    <span class="ml3" v-else>개 이상 상품 구매 시 지급</span>
                                    <span class="txt-orange ml10" v-if="info.promodivtype==$store.getters['ADMIN'].PROMO_DIV_GIFT">
                                        <i class="icon-alert"></i>선택한 대상상품은 최대주문 수량을 1로 설정하셔야합니다. (상품관리)
                                    </span>
                                </td>
                            </tr>
                            <!-- 프로모션구분-사은품, 사은품지급조건 선택(특정상품구매시 제외) -->
                            <tr v-if="info.promodivtype==$store.getters['ADMIN'].PROMO_DIV_GIFT && !$util.isNull(info.gifttermtype) && info.gifttermtype!=$store.getters['ADMIN'].GIFT_TERM_PURCH">
                                <th>구매금액제한<i class="essential"></i></th>
                                <td>
                                    <select v-model="info.isorderprice" :disabled="isdisabled">
                                        <option value="">기준금액 선택</option>
                                        <option value="T">주문금액</option>
                                        <option value="F">실결제금액</option>
                                    </select>
                                    <span class="ml3">기준</span>
                                    <input type="text" class="right" style="width: 80px;" v-model="info.orderfromprice" :disabled="isdisabled"/>
                                    <span class="ml3" v-if="info.gifttermtype!=$store.getters['ADMIN'].GIFT_TERM_PRICE_RANGE">원 이상 구매 시</span>
                                    <span class="ml3" v-if="info.gifttermtype==$store.getters['ADMIN'].GIFT_TERM_PRICE_RANGE">원 이상 ~</span>
                                    <input type="text" class="right" style="width: 80px;" v-model="info.ordertoprice" v-if="info.gifttermtype==$store.getters['ADMIN'].GIFT_TERM_PRICE_RANGE" :disabled="isdisabled"/>
                                    <span class="ml3" v-if="info.gifttermtype==$store.getters['ADMIN'].GIFT_TERM_PRICE_RANGE">원 이하 구매 시</span>
                                </td>
                            </tr>
                            <!-- 프로모션구분-사은품, 사은품지급조건 선택 -->
                            <tr v-if="info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_GIFT && !$util.isNull(info.gifttermtype)">
                                <th>대상 사은품<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">적용대상 사은품목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <strong class="txt-red mr5" v-if="info.isover"><i class="icon-alert2"></i>선착순 수량이 모두 소진된 사은품이 있습니다. 수량을 변경하거나 삭제해 주세요.</strong>
                                            <button type="button" id="addGift" class="btn blue-line" @click="addGift">사은품추가</button>
                                            <button type="button" id="removeGift" class="btn red-line" @click="removeGift">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                            <colgroup>
                                                <col width="3%" /><!-- checkbox -->
                                                <col width="4%" /><!-- No -->
                                                <col width="10%" /><!-- 사은품코드 -->
                                                <col width="62px" /><!-- 이미지 -->
                                                <col width="" /><!-- 사은품명 -->
                                                <col width="10%" /><!-- 선착순 -->
                                                <col width="6%" /><!-- 사용 -->
                                                <col width="6%" /><!-- 재고 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" id="chkallpromogift" v-model="isallchkpromogift" @change="checkAllGiftList($event.target.checked)" /></th>
                                                    <th>No</th>
                                                    <th>사은품코드</th>
                                                    <th colspan="2">사은품명</th>
                                                    <th>선착순</th>
                                                    <th>사용</th>
                                                    <th>재고</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="!$util.isNull(promogiftList) && promogiftList.length > 0">
                                                <tr v-for="(item, index) in promogiftList" :key="item.giftidx">
                                                    <td><input type="checkbox" :id="'chkgoods_'+index" v-model="giftMoveData.targetIdx" :value="index" @change="checkGiftList"/></td>
                                                    <td>{{ index+1 }}</td>
                                                    <td>{{ item.giftcode }}</td>
                                                    <td>
                                                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                                            <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                                        </div>
                                                    </td>
                                                    <td class="left no-left">
                                                        <p class="mg0">{{ item.giftname }}</p>
                                                    </td>
                                                    <td><input type="text" class="right" style="width: 60px;" v-model="item.fcfscnt" maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');"> 개</td>
                                                    <td>{{ item.usecnt }}</td>
                                                    <td>{{ item.giftstockcnt }}</td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="8">조회 결과가 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="bottom-group" v-if="info.gifttermtype==$store.getters['ADMIN'].GIFT_TERM_FRST_PURCH && info.promosttype && info.promosttype == $store.getters['ADMIN'].PROMO_ST_BEFORE">
                                        <CommonArraySort :list-data="promogiftList"
                                                        :move-data="giftMoveData"
                                                        :is-active-save-btn="false"
                                                        key-name="giftidx"
                                        />
                                    </div>
                                </td>
                            </tr>
                            <!-- 프로모션구분-사은품, 사은품지급조건 선택(첫구매시지급 제외) -->
                            <tr v-if="info.promodivtype==$store.getters['ADMIN'].PROMO_DIV_GIFT && !$util.isNull(info.gifttermtype) && info.gifttermtype!=$store.getters['ADMIN'].GIFT_TERM_FRST_PURCH">
                                <th>사은품선택조건<i class="essential"></i></th>
                                <td>
                                    <span>위 사은품 중</span>
                                    <input type="text" class="right ml3" style="width: 50px;" v-model="info.giftselcnt" maxlength="5" :disabled="isdisabled"/>
                                    <span class="ml3">개(종류) 선택가능</span>
                                </td>
                            </tr>
                            <!-- 프로모션구분-적립금 -->
                            <tr v-if="info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_RESERVE">
                                <th>적립금<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="isreserveD" id="isreserveDT" v-model="info.isreserve" value="T" :disabled="isdisabled"/>
                                        <label for="isreserveDT">적립금</label>
                                        <input type="radio" name="isreserveD" id="isreserveDF" v-model="info.isreserve" value="F" :disabled="isdisabled"/>
                                        <label for="isreserveDF">D포인트</label>
                                    </div>
                                    <input type="text" class="right ml3" style="width: 80px;" v-model="info.reservepoint" maxlength="11" :disabled="isdisabled"/>
                                    <span class="ml3">p</span>
                                </td>
                            </tr>
                            <!-- 프로모션구분-적립금, 적립금여부-E포인트 -->
                            <tr v-if="info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_RESERVE && info.isreserve=='F'">
                                <th>D포인트 중복사용여부<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="isepointdupD" id="isepointdupDT" v-model="info.isepointdup" value="T" :disabled="isdisabled"/>
                                        <label for="isepointdupDT">허용</label>
                                        <input type="radio" name="isepointdupD" id="isepointdupDF" v-model="info.isepointdup" value="F" :disabled="isdisabled"/>
                                        <label for="isepointdupDF">미허용</label>
                                    </div>
                                    <span class="txt-orange ml10"><i class="icon-alert"></i>적립금과 함께 사용 가능한지의 여부</span>
                                </td>
                            </tr>
                            <!-- 프로모션구분-적립금, 적립금여부-E포인트 -->
                            <tr v-if="info.promodivtype == $store.getters['ADMIN'].PROMO_DIV_RESERVE && info.isreserve=='F'">
                                <th>D포인트 유효기간</th>
                                <td>D포인트 적립금 프로모션을 통해 지급되는 D포인트의 유효기간은 진행기간과 동일하게 자동 설정됩니다.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group" v-show="!$util.isNull(info.promosttype)">
                    <!-- 저장버튼 진행전, 진행중 상태에 노출 -->
                    <button type="button" id="save" class="btn big blue" @click="save">저장</button>
                    <button type="button" id="cancel" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /프로모션 상세 팝업 -->
</template>

<script src="./PromotionDetail.js"/>