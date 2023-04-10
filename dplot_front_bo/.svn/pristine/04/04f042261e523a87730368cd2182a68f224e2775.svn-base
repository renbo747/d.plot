<template>
    <!-- ERP 오리지널코드 조회 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" :style="params.isread? 'width: 600px;' : 'width: 1200px;'">
            <div class="pop-header">
                <h2 class="half">ERP 오리지널코드 조회</h2>
                <h2 class="half" v-if="!params.isread">연결상품</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="col2" style="min-height: 414px;">
                    <div style="width: 550px;">
                        <div class="boxing search-area pd0">
                            <dl>
                                <dt style="width: 100px;">직접검색</dt>
                                <dd>
                                    <select style="width: 115px;" v-model="searchData.skey">
                                        <!-- <option value="">전체</option> -->
                                        <option value="orgcode">오리지널코드</option>
                                        <option value="orgname">단품명</option>
                                    </select>
                                    <input type="text" style="width: 250px;" @keyup.enter="searchErpList" ref="sword" v-model="searchData.sword"/>
                                    <button type="button" class="btn blue" @click="searchErpList">검색</button>
                                </dd>
                            </dl>
                        </div>
                        <div class="caption-group clearfix mt10">
                            <div class="total-group fl">
                                <span class="total">전체 <strong>{{ erpCodeList.length }}</strong>건</span>
                            </div>
                            <div class="btn-group fr">
                                <button type="button" class="btn blue-line" @click="addLinked" v-if="!params.isread">연결상품 추가 ></button>
                            </div>
                        </div>
                        <div class="scroll-y" style="max-height: 300px;">
                            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                <caption>검색결과</caption>
                                <colgroup>
                                    <col width="6%" v-if="!params.isread"/><!-- checkbox -->
                                    <col width="8%" /><!-- No -->
                                    <col width="24%" /><!-- 오리지널코드 -->
                                    <col width="" /><!-- 단품명 -->
                                    <!-- <col width="8%" /> --><!-- 재고 -->
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th v-if="!params.isread"><input type="checkbox" id="chkallerp" v-model="isallchkerp" @change="checkAllErpCodeList($event.target.checked)"/></th>
                                        <!-- <th>선택</th> -->
                                        <th>No</th>
                                        <th>오리지널코드
                                            <button type="button" :value="sortData.org_item_code" class="sort"
                                                :class="[{up : sortData.org_item_code === 'org_item_code-asc'}, {down : sortData.org_item_code === 'org_item_code-desc'}]"
                                                @click="sortToggle('erp', sortData.org_item_code)"></button>
                                        </th>
                                        <th>단품명</th>
                                        <!-- <th>재고</th> -->
                                    </tr>
                                </thead>
                                <tbody v-if="erpCodeList.length > 0">
                                    <tr v-for="(item, index) in erpCodeList" :key="item.erpoptcode">
                                        <td v-if="!params.isread"><input type="checkbox" :id="'erp_'+index" v-model="item.ischecked" @change="checkErpCodeList($event.target.checked)"/></td>
                                        <!-- <td>
                                            <input type="radio" name="erpoptcode" class="circle" :id="'erpoptcode'+index"  :checked="item.ischecked" @change="setIsCheckedErp(item)">
                                        </td> -->
                                        <td>{{ index+1 }}</td>
                                        <td>{{ item.org_item_code }}</td>
                                        <td>{{ item.org_item_name }}</td>
                                        <!-- <td>{{ item.stockcnt }}</td> -->
                                    </tr>
                                </tbody>
                                <tbody v-else>
                                    <tr><td :colspan="params.isread? 4:5">조회 결과가 존재하지 않습니다.</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <template v-if="!params.isread">
                        <div class="arrow-middle">
                            <img src="@assets.admin/img/arrow-middle.png">
                        </div>
                        <div style="width: 550px;">
                            <div class="gray-box mg0">
                                상품명 : {{ searchData.goodsname }}
                            </div>
                            <div class="caption-group clearfix mt10">
                                <div class="total-group fl">
                                    <span class="total">전체 <strong>{{ linkedList.length }}</strong>건</span>
                                </div>
                                <div class="btn-group fr">
                                    <button type="button" class="btn red-line" @click="removeLinked">삭제</button>
                                </div>
                            </div>
                            <div class="scroll-y" style="max-height: 300px;">
                                <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                    <caption>검색결과</caption>
                                    <colgroup>
                                        <col width="6%" /><!-- checkbox -->
                                        <col width="8%" /><!-- No -->
                                        <col width="24%" /><!-- 오리지널코드 -->
                                        <col width="" /><!-- 단품명 -->
                                        <col width="12%" /><!-- 재고 -->
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th><input type="checkbox" id="chkalllink" v-model="isallchklink" @change="checkAllLinkedList($event.target.checked)"/></th>
                                            <!-- <th>선택</th> -->
                                            <th>No</th>
                                            <th>오리지널코드
                                                <button type="button" :value="sortData.erpoptcode" class="sort"
                                                    :class="[{up : sortData.erpoptcode === 'erpoptcode-asc'}, {down : sortData.erpoptcode === 'erpoptcode-desc'}]"
                                                    @click="sortToggle('linked', sortData.erpoptcode)"></button>
                                            </th>
                                            <th>단품명</th>
                                            <th>수량</th>
                                        </tr>
                                    </thead>
                                    <tbody v-if="linkedList.length > 0">
                                        <tr v-for="(item, index) in linkedList" :key="'linked'+item.erpoptcode">
                                            <td><input type="checkbox" :id="'link_'+index" v-model="item.ischecked" @change="checkLinkedList($event.target.checked)"/></td>
                                            <!-- <td>
                                                <input type="radio" name="linkedcode" class="circle" :id="'linkedcode'+index" :checked="item.ischecked" @change="setIsCheckedLink(item)">
                                            </td> -->
                                            <td>{{ index+1 }}</td>
                                            <td>{{ item.erpoptcode }}</td>
                                            <td>{{ item.erpoptname }}</td>
                                            <td><input type="text" value="" style="width: 100%;" v-model="item.cnt" :maxlength="11" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');"></td>
                                        </tr>
                                    </tbody>
                                    <tbody v-else>
                                        <tr><td colspan="6">조회 결과가 존재하지 않습니다.</td></tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </template>
                </div>
                <hr class="solid" v-if="!params.isread"/>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="sendAllList" v-if="!params.isread">전체 적용</button>
                    <button type="button" class="btn big blue" @click="sendSelectedList" v-if="!params.isread">선택 대상 적용</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /ERP 오리지널코드  조회 팝업 -->
</template>

<script src="./SearchOriginalCodePopup.js"></script>