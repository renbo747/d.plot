<template>
  <div class="content m-leftmenu">
    <common-navigator></common-navigator>
    <div class="inner">
      <div class="category-layout">
        <div class="left">
          <div class="clearfix">
            <div class="bar-title fl">전시 카테고리 설정</div>
            <div class="btn-group fr">
              <button type="button" class="btn big blue-line" @click="collapseAll()" v-if="isRead">전체펼침</button>
              <button type="button" class="btn big blue-line" @click="expandAll()" v-if="isRead">전체닫힘</button>
            </div>
          </div>
          <div class="scroll-y">
            <v-jstree :data="data" :draggable="true" @item-drag-start="onDragStart" @item-drop="onDrop"
                      @item-drop-before="onDropBefore" @item-drag-end="onDragEnd" @item-click="onClick"
                      @item-drop-mouse-up="onDropMouseUp"
                      ref="tree"></v-jstree>
          </div>
          <div class="btn-group fr">
            <button type="button" class="btn big blue" @click="onTreeSave()" v-if="isWrite">이동저장</button>
          </div>
        </div>
        <div class="right">
          <div class="clearfix">
            <div class="bar-title fl">카테고리 정보</div>
          </div>
          <table cellpadding="0" cellspacing="0" class="gray-tb mt10">
            <colgroup>
              <col width="170px;">
              <col width="">
            </colgroup>
            <tbody>
            <tr>
              <th>현재위치</th>
              <td>{{ detailData.path }}</td>
            </tr>
            <tr>
              <th>카테고리 명(한글)</th>
              <td><input type="text" style="width: 100%;" v-model="detailData.name" ref="name"></td>
            </tr>
            <tr>
              <th>카테고리 명(영문)</th>
              <td><input type="text" style="width: 100%;" v-model="detailData.engname" ref="name"></td>
            </tr>
            <tr>
              <th>카테고리 코드</th>
              <td><input type="text" readonly v-model="detailData.idx"></td>
            </tr>
            <tr>
              <th>노출여부</th>
              <td>
                <div class="radio_wrap wide">
                  <input type="radio" name="hidden" id="rd01" v-model="detailData.hidden" value='0' checked/><label
                    for="rd01">노출</label>
                  <input type="radio" name="hidden" id="rd02" v-model="detailData.hidden" value='1'/><label for="rd02">숨김</label>
                </div>
              </td>
            </tr>
            <tr>
              <th>전시상품수</th>
              <td>
                <strong class="txt-black">{{ detailData.goodsno + ' 개' }}</strong>
                <span class="txt-orange ml10">(하위 카테고리 상품 포함)</span>
              </td>
            </tr>
            <tr>
              <th>URL</th>
              <td><a :href="detailData.linkurl" class="link" target="_blank">{{ detailData.linkurl }}</a></td>
            </tr>
            <tr>
              <th>정렬순서<i class="essential"></i></th>
              <td><input type="number" style="width: 10%;" v-model="detailData.sort" min=1></td>
            </tr>
            <tr>
              <th>EP카테고리명</th>
              <td><input type="text" v-model="detailData.epname" size="200"></td>
            </tr>
            <tr v-show="detailData.depth > 0">
              <th>대표이미지(PC)<i class="essential"></i></th>
              <td>
                  <div class="img-with-text" style="width: 202px;">
                      <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['pcimgfile'])}">
                          <img :src="imgPreview['pcimgfile']" alt="대표이미지(PC)" v-show="!$util.isNull(files['pcimgfile'])">
                      </div>
                      <button type="button" class="btn blue-line mt10" style="width: 100%;"
                              v-show="$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">파일 올리기
                      </button>
                      <input type="file" ref="pcimgfile" @change="handleFileUpload('pcimgfile')"
                              accept="image/jpeg, image/png" hidden/>
                      <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                              v-show="!$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">변경
                      </button>
                      <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                              v-show="!$util.isNull(files['pcimgfile'])" @click="removeFile('pcimgfile')">삭제
                      </button>
                  </div>
                  <div class="img-with-text text">
                      <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                      <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                  </div>
              </td>
            </tr>
            <tr v-show="detailData.depth > 0">
                <th>대표이미지(모바일)<i class="essential"></i></th>
                <td>
                    <div class="mb10">
                        <input type="checkbox" v-model="copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label for="copy-img">PC 대표 이미지를 복사</label>
                    </div>
                    <div class="img-with-text" style="width: 202px;">
                        <div class="img-thumb"
                            :class="[[copyimgcheck ? 'size200':'size460wide'],{'no-image': $util.isNull(files['mobileimgfile'])}]">
                            <img :src="imgPreview['mobileimgfile']" alt="대표이미지(모바일)" v-show="!$util.isNull(files['mobileimgfile'])">
                        </div>
                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                v-show="$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">파일 올리기
                        </button>
                        <input type="file" ref="mobileimgfile" @change="handleFileUpload('mobileimgfile')"
                                accept="image/jpeg, image/png" hidden/>
                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                v-show="!$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">변경
                        </button>
                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                v-show="!$util.isNull(files['mobileimgfile'])" @click="removeFile('mobileimgfile')">삭제
                        </button>
                    </div>
                    <div class="img-with-text text" v-show="!copyimgcheck">
                        <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG,
                            JPEG, PNG</p>
                    </div>
                    <div class="img-with-text text" v-show="copyimgcheck">
                        <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG,
                            JPEG, PNG</p>
                    </div>
                </td>
            </tr>
            </tbody>
          </table>
          <div class="btn-group fr">
            <button type="button" class="btn big blue" @click="onSave" v-if="isWrite">저장</button>
            <button type="button" class="btn big red" @click="onDelete" v-if="isWrite">삭제</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./CategoryList.js"/>
