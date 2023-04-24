<template>
  <div>
    <!-- 엑셀 파일 정보 구성 팝업 -->
    <ExcelFileInfoPopup
        ref="excelPopup"
        v-if="isExcelFileInfoPopupShow"
        :event-idx="eventIdx"
        :path="excelPostPath"
        @close="closeExcelDownloadPopup"
        style="z-index: 1001"
    />

    <!-- 첨부 사진 팝업 -->
    <PictureViewPopup
        ref="picturePopup"
        v-if="isPictureViewPopupShow"
        :img-path="imgPath"
        @close="closeUploadFilePopup"
        style="z-index: 1002"
    />

    <VideoView
        ref="videoPopup"
        v-if="isVideoViewPopupShow"
        :videoInfo="videoInfo"
        @close="closeVideoPopup"
        style="z-index: 1003"
    />

    <!-- 답글 관리 팝업 -->
    <ReplyInfoPopup ref="replyPopup"
                    v-if="isReplyInfoPopupShow"
                    style="z-index: 1004"
                    @close="closeReplyInfoPopup"
                    :reply-obj="replyPopupObj"
    />

    <!-- 신고 정보 팝업 -->
    <ReportInfoPopup ref="reportPopup"
                     v-if="isReportInfoPopupShow"
                     style="z-index: 1005"
                     @close="closeReportInfoPopup"
                     :report-obj="reportPopupObj"
    />

    <!-- 유저 상세 정보 팝업 -->
    <AdminMemberInfo
        v-if="isMemberDetailShow"
        v-bind:activeUserNo="memberDetailUserNo"
        v-on:closeDetail="closeMemberInfoPopup"
        style="z-index: 1006;"
    />
    <!-- /유저 상세 정보 팝업 -->

    <!-- 프로모션 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
      <div class="modal-content" style="width: 1400px;">
        <div class="pop-header">
          <h2>프로모션 상세</h2>
          <button type="button" class="pop-close" @click="$emit('close')"></button>
        </div>
        <div class="pop-body">

          <!-- 프로모션 상세 내역 블럭 -->
          <div>
            <div class="gray-box mg0 clearfix">
              <div class="fl">
                <span>작성자 : {{ boardInfo.reguserid }}</span>
                <span class="left-bar">조회수 : {{ boardInfo.readcnt }}</span>
                <span class="left-bar">직접접근가능경로</span>
                <input type="text" id="urlinput" class="url-input" ref="urlinput"
                       style="position:absolute;top:-9999em;">
                <button type="button" class="btn blue-line ml3" @click="goUrlBtn()">URL 복사</button>
              </div>
              <div class="fr txt-gray">
                <span>등록일 : {{ boardInfo.regdate }}</span>
                <span class="left-bar">수정일 : {{ boardInfo.moddate }}</span>
              </div>
            </div>
            <div class="bar-title mt10">기본정보</div>
            <div class="boxing">
              <div class="form-area">
                <dl>
                  <dt>노출상태</dt>
                  <dd>
                    <div class="radio_wrap wide">
                      <input type="radio" name="group00" id="group01" value="T" v-model="boardInfo.isdisplay"
                             checked/><label
                        for="group01">노출</label>
                      <input type="radio" name="group00" id="group02" value="F" v-model="boardInfo.isdisplay"/><label
                        for="group02">미노출</label>
                    </div>
                  </dd>
                </dl>
                <dl>
                  <dt>제목<i class="essential"></i></dt>
                  <dd><input type="text" style="width: 100%" placeholder="제목" v-model="boardInfo.subject"/></dd>
                </dl>
                <dl>
                  <dt>설명</dt>
                  <dd><input type="text" style="width: 100%" placeholder="프로모션 설명" v-model="boardInfo.evdesc"/></dd>
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
                  <td>
                    <CommonDatePickerFromTo
                        :fromYYYYMMDD="eventTimeInfo.startyyyymmdd"
                        :fromHH="eventTimeInfo.starthh"
                        :fromMM="eventTimeInfo.startmm"
                        :toYYYYMMDD="eventTimeInfo.toyyyymmdd"
                        :toHH="eventTimeInfo.tohh"
                        :toMM="eventTimeInfo.tomm"
                        @getDate="getEventTimeDate"
                    />
                  </td>
                </tr>
                <tr>
                  <th>발표일<i class="essential"></i></th>
                  <td>
                    <CommonDatePickerFromTo
                        :toYYYYMMDD="pubTimeInfo.toyyyymmdd"
                        :toHH="pubTimeInfo.tohh"
                        :toMM="pubTimeInfo.tomm"
                        :use-from="false"
                        :use-to="true"
                        @getDate="getEventPubTimeDate"
                    />
                  </td>
                </tr>
                <tr>
                    <th>카테고리<i class="essential"></i></th>
                    <td>
                        <div class="radio_wrap">
                            <input type="radio" name="group06" v-model="boardInfo.isevent" value="T" id="group61" checked=""><label for="group61">이벤트</label>
                            <input type="radio" name="group06" v-model="boardInfo.isevent" value="F" id="group62"><label for="group62">기획전</label>
                        </div>
                    </td>
                </tr>
                <tr>
                  <th>적용채널<i class="essential"></i></th>
                  <td>
                    <div class="check-wrap">
                      <input type="checkbox" id="all1" v-model="checkObj.isallmuappch" true-value="T" false-value="F"
                             @change="checkAllMuAppch($event.target.checked)">
                      <label for="all1">전체</label>
                    </div>
                    <div class="check-wrap" v-for="(row, i) in pageCode.muappchtype" :key="i">
                      <input type="checkbox" :id="'group1' + i" :value="row.cmcode"
                             v-model="checkObj.muappchTypeChecked">
                      <label :for="'group1' + i">{{ row.codename }}</label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>대상회원유형<i class="essential"></i></th>
                  <td>
                    <div class="check-wrap">
                      <input type="checkbox" id="all2" v-model="checkObj.isallmumember" true-value="T" false-value="F"
                             @change="checkAllMuMember($event.target.checked)">
                      <label for="all2">전체</label>
                    </div>
                    <div class="check-wrap" v-for="(row, i) in pageCode.mumembertype" :key="i">
                      <input type="checkbox" :id="'group2' + i" :value="row.cmcode"
                             v-model="checkObj.mumemberTypeChecked">
                      <label :for="'group2' + i">{{ row.codename }}</label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>대상회원등급<i class="essential"></i></th>
                  <td>
                    <div class="check-wrap">
                      <input type="checkbox" id="all3" v-model="checkObj.isallmumemlv" true-value="T" false-value="F"
                             @change="checkAllMuMemLv($event.target.checked)">
                      <label for="all3">전체</label>
                    </div>
                    <div class="check-wrap" v-for="(row, i) in pageCode.mumemlvtype" :key="i">
                      <input type="checkbox" :id="'group3' + i" :value="row.cmcode"
                             v-model="checkObj.mumemlvTypeChecked">
                      <label :for="'group3' + i">{{ row.codename }}</label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>전시여부<i class="essential"></i></th>
                  <td>
                    <div class="radio_wrap wide3">
                      <div v-for="(row, i) in pageCode.disptype" :key="i">
                        <input type="radio" :id="'group4' + i" :value="row.cmcode" v-model="boardInfo.disptype">
                        <label :for="'group4' + i">{{ row.codename }}</label>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>옵션</th>
                  <td>
                    <div class="dpb">
                      <input type="checkbox" id="Dgroup61" true-value="T" false-value="F"
                             v-model="boardInfo.iscomment"/><label for="Dgroup61">댓글</label>
                      <!-- <span v-show="boardInfo.iscomment === 'T'" class="txt-gray dpib"
                            style="width: 40px; text-align: center;">비밀글</span>
                      <div v-show="boardInfo.iscomment === 'T'" class="radio_wrap dpib ml3">
                        <input type="radio" name="group061" id="group611" value="F"
                               v-model="boardInfo.isseccomment"/><label
                          for="group611">미허용</label>
                        <input type="radio" name="group061" id="group612" value="T"
                               v-model="boardInfo.isseccomment"/><label
                          for="group612">허용</label>
                      </div> -->
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>대표이미지(PC)<i class="essential"></i></th>
                  <td>
                    <div class="img-with-text" style="width: 202px;">
                      <div class="img-thumb" style="width: 305px; height: 150px;" :class="{'no-image': this.$util.isNull(files['pcimgfile'])}">
                        <img style="width: 305px; height: 150px;" :src="imgPreview['pcimgfile']" alt="대표이미지(PC)"
                             v-if="!this.$util.isNull(files['pcimgfile'])">
                      </div>
                      <button type="button" class="btn blue-line mt10" style="width: 100%;"
                              v-if="this.$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">파일 올리기
                      </button>
                      <input type="file" ref="pcimgfile" @change="handleFileUpload('pcimgfile')"
                             accept="image/jpeg, image/png" hidden/>
                      <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                              v-if="!this.$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">변경
                      </button>
                      <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                              v-if="!this.$util.isNull(files['pcimgfile'])" @click="removeFile('pcimgfile')">삭제
                      </button>
                    </div>
                    <div class="img-with-text text">
                      <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                      <p class="txt-orange"><i class="icon-alert"></i>사이즈: 610*300 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG,
                        JPEG, PNG</p>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>대표이미지(모바일)<i class="essential"></i></th>
                  <td>
                    <div class="mb10">
                      <input type="checkbox" v-model="copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label
                        for="copy-img">PC 대표 이미지를 복사</label>
                    </div>
                    <!-- 모바일 이미지-->
                    <div class="img-with-text" style="width: 202px;">
                      <div class="img-thumb" style="width: 360px; height: 200px;"
                           :class="{'no-image': this.$util.isNull(files['mobileimgfile'])}">
                        <img style="width: 360px; height: 200px;" :src="imgPreview['mobileimgfile']" alt="대표이미지(모바일)"
                             v-if="!this.$util.isNull(files['mobileimgfile'])">
                      </div>
                      <button type="button" class="btn blue-line mt10" style="width: 100%;"
                              v-if="this.$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">파일
                        올리기
                      </button>
                      <input type="file" ref="mobileimgfile" @change="handleFileUpload('mobileimgfile')"
                             accept="image/jpeg, image/png" hidden/>
                      <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                              v-if="!this.$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">변경
                      </button>
                      <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                              v-if="!this.$util.isNull(files['mobileimgfile'])" @click="removeFile('mobileimgfile')">삭제
                      </button>
                    </div>
                    <div class="img-with-text text">
                      <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                      <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*400 / 용량: 10MB 이하 / 파일 : JPG,
                        JPEG, PNG</p>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>내용(PC)<i class="essential"></i></th>
                  <td>
                    <div>
                      <CommonEditor ref="pcEditor"/>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>내용(모바일)<i class="essential"></i></th>
                  <td>
                    <div class="mb10">
                      <button type="button" class="btn blue-line" @click="copyByContent">PC 내용을 복사</button>
                    </div>
                    <div class="mt10">
                      <CommonEditor ref="mobileEditor"/>
                    </div>
                  </td>
                </tr>
                <tr>
                <th>브랜드샵</th>
                    <td>
                        <div class="unlinkfile-wrap clearfix">                                    
                            <button type="button" v-if="$util.isNull(boardInfo.brandidx)" class="btn blue-line" @click="openSearchBrandPopup">브랜드추가</button>
                            <div class="dpib" v-if="!$util.isNull(boardInfo.brandidx)">{{'['+brandInfo.brcode+'] '+brandInfo.brandname}}<button type="button" class="file-del" @click="removeBrand"></button></div>
                        </div>
                    </td>
                </tr>
                <tr>
                  <th>연관상품(선택)</th>
                  <td>
                    <div class="caption-group clearfix">
                      <div class="total-group fl">
                        <span class="total">적용대상 상품목록</span>
                      </div>
                      <div class="btn-group fr">
                        <button type="button" class="btn blue-line" @click="openGoodsAdditionPopup" v-if="isWrite">
                          상품추가
                        </button>
                        <button type="button" class="btn red-line" @click="removeGoodsAddition" v-if="isWrite">삭제
                        </button>
                      </div>
                    </div>
                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                      <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                          <col width="3%"/><!-- checkbox -->
                          <col width="4%"/><!-- No -->
                          <col width="6%"/><!-- 판매구분 -->
                          <col width="10%"/><!-- 파트너사명 -->
                          <col width="8%"/><!-- 상품코드 -->
                          <col width="62px"/><!-- 이미지 -->
                          <col width=""/><!-- 상품명 -->
                          <col width="7%"/><!-- 판매가 -->
                        </colgroup>
                        <thead>
                        <tr>
                          <th><input type="checkbox" id="chkall" v-model="isProductAllCheck"
                                     @click="onCheckAllProduct($event.target.checked)"/></th>
                          <th>No</th>
                          <th>판매구분</th>
                          <th>파트너사명</th>
                          <th>상품코드</th>
                          <th colspan="2">상품명</th>
                          <th>판매가</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(row, i) in productList" :key="i">
                          <td><input type="checkbox" id="chk01" :value="i" v-model="moveData.targetIdx" @change="onCheckProduct"/></td>
                          <td>{{ $util.lpad(i+1,2,'0') }}</td>
                          <td>{{ row.ispbgoodsname }}</td>
                          <td>{{ row.dealername }}</td>
                          <td>{{ row.goodscode }}</td>
                          <td>
                            <div class="img-thumb size60" :class="{'no-image': $util.isNull(row.fullpath)}">
                              <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)" alt="사진">
                            </div>
                          </td>
                          <td class="left no-left">
                            <span class="small-txt">{{ row.fullcategoryname }}</span>
                            <p class="mg0">{{ row.goodsname }}</p>
                          </td>
                          <td class="right">{{ row.price }}</td>
                        </tr>
                        </tbody>
                      </table>
                    </div>
                    <div class="bottom-group">
                      <CommonArraySort
                          :move-data="moveData"
                          :list-data="productList"
                          :is-active-save-btn="false"
                          key-name="goodsno"
                      />
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>추첨여부</th>
                    <td>
                      <div class="dpb">
                      <input type="checkbox" id="Rgroup61" true-value="T" false-value="F"
                         v-model="boardInfo.isselect"/><label for="Rgroup61">추첨</label>
                      </div>
                    </td>
                </tr>
                <tr v-if="boardInfo.isselect=='T'">
                  <th>당첨조건</th>
                  <td>
                    <tr>
                    <select v-model="searchData.condcode" @change="getConcodeVal($event)" style="width: 15%">
                      <option v-for="(item,index) in commonCode.lotterycond" :key="index" :value="item.cmcode" >{{ item.codename }}</option>
                    </select>
                    <input type="text" style="width: 10%" v-model="searchData.cond1"/>
                    <select v-model="searchData.sign1"  @change="getConsign1Val($event)" style="width: 6%">
                      <option v-for="(item,index) in commonCode.lotterysign" :key="index" :value="item.cmcode" >{{ item.codename }}</option>
                    </select>
                    <select v-model="searchData.relation" style="width: 8%">
                      <option value=""></option>
                      <option value="AND">AND</option>
                      <option value="OR">OR</option>
                    </select>
                    <input type="text" style="width: 10%" v-model="searchData.cond2"/>
                    <select v-model="searchData.sign2" @change="getConsign2Val($event)" style="width: 6%">
                      <option v-for="(item,index) in commonCode.lotterysign" :key="index" :value="item.cmcode" >{{ item.codename }}</option>
                    </select>
                    &nbsp;확률<input type="text" style="width: 10%" v-model="searchData.condval"/>배
                    &nbsp;&nbsp;<button type="button"  class="btn blue-line" @click="addcond">조건추가</button>
                    </tr>
                    <tr v-for="(item, index) in boardInfo.conddata" v-bind:key="index">
                      <td><button type="button"  class="btn blue-line" @click="removecond(index)">
                        {{item.concodeval}},{{item.cond1}} {{item.sign1val}}
                        {{item.relation}} {{item.cond2}} {{item.sign2val}},{{item.condval}}배  X
                      </button></td>
                    </tr>
                  </td>                  
                </tr>

                <tr>
                  <th>당첨자수</th>
                    <td>
                      <input type="text" style="width: 10%" placeholder="당첨자수" v-model="boardInfo.winnercount"/>
                      &nbsp;
                      <button type="button" class="btn big blue" @click="golottery" v-if="isEndYn && boardInfo.isselect=='T' && boardInfo.isselectcomplete=='F'">추첨하기</button>
                      <button type="button" class="btn big blue" @click="winnerList" v-if="isEndYn && boardInfo.isselect=='T' && boardInfo.isselectcomplete=='T'">당첨자조회</button>
                      <button type="button" class="btn big blue" @click="entryList" v-if="isEndYn && boardInfo.isselect=='T' ">응모자조회</button>
                      <button type="button" class="btn big blue" @click="lotteryinit" v-if="false">당첨초기화</button>
                    </td>

                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <!-- /프로모션 상세 내역 블럭 -->

          <!-- 댓글 블럭 -->
          <div v-if="boardInfo.iscomment === 'T'">
            <div class="bar-title">댓글</div>
            <div class="caption-group clearfix">
              <div class="total-group fl">
                <span class="total">전체 <strong>{{ commentList.length }}</strong>건</span>
              </div>
              <div class="btn-group fr">
                <button type="button" class="btn red-line" @click="onDeleteComment" v-if="isWrite">삭제</button>
                <button type="button" class="btn black-line" @click="onChangeBlind('T')" v-if="isWrite">블라인드 지정</button>
                <button type="button" class="btn blue-line" @click="onChangeBlind('F')" v-if="isWrite">블라인드 해제</button>
                <button type="button" class="btn green-line" @click="goExcelDownloadPopup('comment')">
                  <i class="icon-excel"></i>엑셀다운로드
                </button>
              </div>
            </div>
            <div class="scroll-y" style="max-height: 400px;">
              <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>댓글</caption>
                <colgroup>
                  <col width="3%"/><!-- checkbox -->
                  <col width="4%"/><!-- No -->
                  <col width="5%"/><!-- 블라인드 -->
                  <col width=""/><!-- 댓글 -->
                  <col width="50px"/><!-- 수정버튼 -->
                  <col width="5%"/><!-- 좋아요 -->
                  <col width="5%"/><!-- 첨부 -->
                  <col width="5%"/><!-- 댓글구분 -->
                  <col width="8%"/><!-- 답글 -->
                  <col width="8%"/><!-- 아이디 -->
                  <col width="8%"/><!-- 이름 -->
                  <col width="9%"/><!-- 등록/수정일자 -->
                </colgroup>
                <thead>
                <tr>
                  <th><input type="checkbox" id="comment_check_all" v-model="isCommentAllCheck"
                             @click="onCheckAllComment()"/>
                  </th>
                  <th>No</th>
                  <th>블라인드</th>
                  <th colspan="2">댓글</th>
                  <th>좋아요</th>
                  <th>신고</th>
                  <th>댓글구분</th>
                  <th>답글</th>
                  <th>아이디</th>
                  <th>이름</th>
                  <th>등록/수정일자
                    <button type="button"
                            :value="sortData.regdate"
                            class="sort"
                            :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                            @click="sortToggle(sortData.regdate)"></button>
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(row, i) in commentList" :key="i">
                  <td><input type="checkbox" :id="'chk0' + i" :value="row.commentidx" v-model="commentCheckList"/></td>
                  <td>{{ row.rn }}</td>
                  <td>{{ row.isblind }}</td>
                  <td :colspan="(row.userid === userInfo.id) && (isWrite === true) ? '0' : '2'" class="left">
                    {{ row.comment }}
                  </td>
                  <!-- 수정버튼 없는 경우 td에 colspan="2" -->
                  <td v-if="(row.userid === userInfo.id) && (isWrite === true)" class="no-left">
                    <button type="button" class="btn blue-line"
                            @click="goReplyInfoPopup(row.commentidx, row.depth, row.comment, 'F')">수정
                    </button>
                  </td>
                  <td>{{ row.likecount }}</td>
                  <td v-if="row.reportcount > 0">
                    <a @click="goReportInfoPopup(row.commentidx)" class="link">
                    {{ row.reportcount }}
                    </a>
                  </td>
                  <td v-else>
                    0
                  </td>
                  <td>{{ row.issecret }}</td>
                  <td>{{ row.commentcount }}
                    <button v-if="(row.depth === 0) && (isWrite === true)" type="button" class="btn blue-line ml10"
                            @click="goReplyInfoPopup(row.commentidx, 1, '','T')">추가
                    </button>
                  </td>
                  <td>{{ row.userid }}</td>
                  <td><a @click="goMemberInfoPopup(row.userno)" class="link">{{ row.username }}</a></td>
                  <td>{{ row.regdate }}</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <!-- /댓글 블럭 -->
          <div class="btn-group">
            <button type="button" class="btn big blue" @click="goSave" v-if="isWrite">저장</button>
            <button type="button" class="btn big darkgray" @click="onClose">취소</button>
          </div>

        </div>
      </div>
    </div>
    <!-- /프로모션 상세 팝업 -->
  </div>
</template>

<script>
import _ from 'lodash';
import SearchBrandListPopup from '@views.admin/goods/popup/SearchBrandListPopup.vue';
import ExcelFileInfoPopup from "../popup/ExcelFileInfoPopup";
import PictureViewPopup from "@views.admin/common/popup/CommonImageView";
import ReplyInfoPopup from "../popup/ReplyInfoPopup";
import ReportInfoPopup from "../popup/ReportInfoPopup";
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonEditor from "@views.admin/common/CommonEditor";
import CommonAddGoodsPopup from "@views.admin/common/popup/CommonAddGoodsPopup.vue";
import CommonArraySort from "@views.admin/common/CommonArraySort";
import util from "@js/util";
import AdminMemberInfo from "@views.admin/member/info/AdminMemberInfo";
import VideoView from '@views.admin/common/popup/CommonVideoView';
import WinnerListModalVue from '@views.admin/operation/shopping/promotion/manage/WinnerListModal.vue';
import EntryListModalVue from '@views.admin/operation/shopping/promotion/manage/EntryListModalVue.vue';
export default {
  name: "admin.operation.shopping.promotion.manage.detail",
  components: {
    AdminMemberInfo,
    CommonArraySort,
    CommonEditor,
    CommonDatePickerFromTo,
    ReportInfoPopup,
    ReplyInfoPopup,
    PictureViewPopup,
    ExcelFileInfoPopup,
    VideoView
  },
  props: {
    pageCode: {                 // 페이지 코드
      disptype: {}, // 노출 구분
      muappchtype: {}, // 적용 채널
      mumembertype: {}, // 대상 회원
      mumemlvtype: {}, // 대상 회원 등급
      dupentertype: {}, // 중복 코드
    },
    eventIdx: Number,
  },
  data() {
    return {
      isWrite: false,                     // 권한 읽기
      isRead: false,                      // 권한 쓰기
      copyimgcheck: false,                // 이미지 복사
      isEndYn: false,                     // 프로모션 종료됐는지여부
      checkObj: {
        isallmuappch: 'F',
        muappchTypeChecked: [],          // 적용 채널
        isallmumember: 'F',
        mumemberTypeChecked: [],         // 회원 유형
        isallmumemlv: 'F',
        mumemlvTypeChecked: [],          // 회원 등급
      },
      searchData: {
        condcode: '',
        concodeval: '',
        cond1: '',
        sign1: '',
        sign1val: '',
        relation: '',
        cond2: '',
        sign2: '',
        sign2val: '',
        condval: '',
      },      

      commonCode: {
        lotterycond: [{cmcode:'',codename:''}],   // 조건명
        lotterysign: [{cmcode:'',codename:''}]   // 부등호기호
      },
      boardInfo: {
        isdisplay: 'F',             // 사용여부
        subject: '',                // 제목
        evdesc: '',                 // 프로모션 설명
        evsttime: '',               // 프로모션 시작일
        evedtime: '',               // 프로모션 종료일
        pubtime: '',                // 프로모션 발표일
        muappchtype: '',            // 적용 채널
        mumembertype: '',           // 회원 유형
        mumemlvtype: '',            // 회원 등급
        disptype: '',               // 노출 여부
        iscomment: 'T',             // 댓글 사용 여부
        isseccomment: 'F',          // 비밀글 사용 여부
        content: '',                // 프로모션 pc 내용
        mobilecontent: '',          // 프로모션 모바일 내용
        evinfo: '',                 // 내용
        evinfomobile: '',           // 모바일 내용
        readcnt: '',                // 조횟수
        reguserud: '',              // 등록자
        regdate: '',                // 등록 날짜
        moddate: '',                // 수정 날짜
        isselect: 'F',           	// 추첨여부
        winnercount: '',           	// 당첨자수
        conddata:[]                // 추점조건데이터        
      },
      files: {                      // 첨부 파일
        pcimgfile: '',
        mobileimgfile: '',
      },
      imgPreview: {                 // 이미지용
        pcimgfile: '',
        mobileimgfile: '',
      },
      addfiles: {
        pcimgfile: '',
        mobileimgfile: '',
      },
      deletefile: [],
      eventTimeInfo: {              // 진행기간
        startyyyymmdd: '',
        starthh: '',
        startmm: '',
        toyyyymmdd: '',
        tohh: '',
        tomm: ''
      },
      pubTimeInfo: {                //발표일
        toyyyymmdd: '',
        tohh: '',
        tomm: '',
      },
      moveData: {
        moveValue: '',              // 움직일 값
        targetIdx: [],              // 대상 위치
        code: 'U',                  // 위, 아래 코드
        isSuccess: false            // 저장 성공 여부 (** 중요)
      },
      brandInfo: {},
      userInfo: {
        id: '',
        mdcode: '',
        name: '',
        no: '',
        status: '',
        usertype: ''
      },

      productList: [],              // 연관 상품
      isProductAllCheck: false,     // 연관 상품 전체 체크

      commentList: [],              // 댓글 리스트
      commentCheckList: [],         // 댓글 체크 리스트
      isCommentAllCheck: false,     // 댓글 전체 체크

      isExcelFileInfoPopupShow: false,  // 엑셀 파일 정보 구성 팝업
      excelPostPath: '',                // post path 경로

      isPictureViewPopupShow: false,    // 첨부 사진 팝업
      imgPath: '',                      // 사진 경로

      isVideoViewPopupShow: false,
      videoInfo: null,

      isReplyInfoPopupShow: false,      // 답글 관리 팝업
      replyPopupObj: {                  // 답글 관리 팝업 props 객체
        // eventidx: '',                   // 프로모션 idx
        // commentidx: '',                 // 댓글 idx
        // depth: '',                      // 깊이
        // isinsert: '',                   // 추가인지 수정인지
        // reguserid: '',                  // 댓글 작성자
      },

      isReportInfoPopupShow: false,     // 신고 정보 팝업
      reportPopupObj: {                 // 신고 정보 팝업 props 객체
        commentidx: '',
      },

      isMemberDetailShow: false,        // 유저 상세 팝업
      memberDetailUserNo: '',           // 유저 번호

      sortData: {
        regdate: 'regdate_desc',            // 등록일자
      },
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드////////////////////////////////////////
    // 초기화
    onInit() {
      this.onSearch();
      this.setUserInfo();
      // TODO: url 입력필요
      this.$refs.urlinput.value = process.env.VUE_APP_PC_DOMAIN+"/shop/promotion/detail/" + this.eventIdx;
    },

    // 프로모션 내역 셋팅
    setBoardInfo() {
      // 에디터 내용
      this.$refs.pcEditor.content = this.boardInfo.evinfo;
      this.$refs.mobileEditor.content = this.boardInfo.evinfomobile;

      // 이미지 파일 셋팅
      this.imgPreview.pcimgfile = this.files.pcimgfile.fullpath;
      this.imgPreview.mobileimgfile = this.files.mobileimgfile.fullpath;

      // 적용 채널 체크 상태
      if (this.boardInfo.muappchtype !== "") {
        this.checkObj.muappchTypeChecked = this.boardInfo.muappchtype.split(",");
      }

      // 회원 유형 체크 상태
      if (this.boardInfo.mumembertype !== "") {
        this.checkObj.mumemberTypeChecked = this.boardInfo.mumembertype.split(",");
      }

      // 회원 등급 체크 상태
      if (this.boardInfo.mumemlvtype !== "") {
        this.checkObj.mumemlvTypeChecked = this.boardInfo.mumemlvtype.split(",");
      }
    },

    // 접속자 정보 가져오기
    setUserInfo() {
      this.userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
    },
        // 공통코드 목록 조회
    getCommonCodeList: function() {
        let cmclassArr = ['LOTTERYCOND', 'LOTTERYSIGN'];
        this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
            .then(result =>{
                let data = result.data;
                for (const [key] of Object.entries(data)) {
                    this.commonCode[key] = data[key];
                }

            })
            .catch(error => {
                this.$util.debug(error);
            });
    },
    // 상세 검색
    onSearch() {
      let params = {
        eventidx: this.eventIdx
      }

      this.$http.post("/admin/operation/shopping/promotion/detail", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;

              // 프로모션 상세 정보
              this.boardInfo = data.eventdtl.eventdtlinfo;

              this.boardInfo.conddata = data.eventdtl.conddata;

              // 프로모션 진행기간 정보
              this.eventTimeInfo = data.eventdtl.eventtimeinfo;
              // 프로모션 발표일 정보
              this.pubTimeInfo = data.eventdtl.pubtimeinfo;
              // 브랜드 정보
              this.brandInfo = data.eventdtl.brandinfo;
              // 연관 상품 정보
              this.productList = data.productlist;
              // 댓글 정보
              this.commentList = data.commentlist;

              // 이미지 파일
              this.files.pcimgfile = data.files.pcimgfile;
              this.files.mobileimgfile = data.files.mobileimgfile;

              // 게시판 정보 셋팅
              this.setBoardInfo();
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 적용 채널 전체 체크
    checkAllMuAppch(value) {
      if (value) {
        // 적용 채널
        this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtype);
      } else {
        this.checkObj.muappchTypeChecked = [];
      }
    },

    // 회원 유형 전체 체크
    checkAllMuMember(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertype);
      } else {
        this.checkObj.mumemberTypeChecked = [];
      }
    },

    // 회원 등급 전체 체크
    checkAllMuMemLv(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtype);
      } else {
        this.checkObj.mumemlvTypeChecked = [];
      }
    },

    // 첨부파일(탐색기 열기)
    fileAttach(fileTypeKey) {
      if (Array.isArray(this.$refs[fileTypeKey])) {
        this.$refs[fileTypeKey][0].click();
      } else {
        this.$refs[fileTypeKey].click();
      }
    },

    // 가져온 파일 세팅
    handleFileUpload(fileTypeKey, target) {
      // PC, 모바일 대표이미지
      let file = this.$refs[fileTypeKey];
      if (this.$util.isNull(file)) {
          return;
      }

      if(Object.prototype.hasOwnProperty.call(this.files[fileTypeKey],'idx')){
          if(fileTypeKey !== 'mobileimgfile' || !this.copyimgcheck) {
              this.deletefile.push(this.files[fileTypeKey].idx);
          }
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
      this.addfiles[fileTypeKey] = fileObj;
      this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);

      if(fileTypeKey === 'pcimgfile' && this.copyimgcheck){
          this.setSameAsPcepreImg();
      }
      // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
      if (fileTypeKey === 'mobileimgfile') {
          this.copyimgcheck = false;
      }
    },

    // 파일 삭제
    removeFile(fileTypeKey, index) {
      if(confirm('파일을 삭제 하시겠습니까?')){
        if(Object.prototype.hasOwnProperty.call(this.files[fileTypeKey],'idx')){
            if(fileTypeKey !== 'mobileimgfile' || !this.copyimgcheck) {
                this.deletefile.push(this.files[fileTypeKey].idx);
            }
        }

        this.files[fileTypeKey] = '';
        this.addfiles[fileTypeKey] = '';
        this.imgPreview[fileTypeKey] = '';
        this.$refs[fileTypeKey].value = null;

        // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
        if (fileTypeKey == 'mobileimgfile') {
            this.copyimgcheck = false;
        }
      }
    },

    // PC 대표이미지와 동일하게 세팅
    setSameAsPcepreImg() {
      if(Object.prototype.hasOwnProperty.call(this.files.mobileimgfile,'idx')){
        if(this.copyimgcheck) {
          if(!this.deletefile.includes(this.files.mobileimgfile.idx)) {
            this.deletefile.push(this.files.mobileimgfile.idx);
          }
        }
      }
      if (this.copyimgcheck) {
          this.files.mobileimgfile = this.files.pcimgfile;
          this.addfiles.mobileimgfile = this.files.pcimgfile;
          this.imgPreview.mobileimgfile = this.imgPreview.pcimgfile;
      } else {
          this.files.mobileimgfile = '';
          this.addfiles.mobileimgfile = '';
          this.imgPreview.mobileimgfile = '';
      }
    },

    // codename array get
    getCmCode(codeArr) {
      return codeArr.map(obj => obj.cmcode);
    },

    // PC 내용 복사
    copyByContent() {
      this.$refs.mobileEditor.content = this.$refs.pcEditor.content;
    },

    // 에디터 내용 가져오기
    setEditorText() {
      if (this.$util.isNull(this.$refs.pcEditor.content)) {
        this.boardInfo.evinfo = ''
      } else {
        this.boardInfo.evinfo = this.$refs.pcEditor.content;
      }

      if (this.$util.isNull(this.$refs.mobileEditor.content)) {
        this.boardInfo.evinfomobile = ''
      } else {
        this.boardInfo.evinfomobile = this.$refs.mobileEditor.content;
      }
    },

    // 유효성 체크
    checkValidation() {
      this.setEditorText();

      let msg = '';
      let checkResult = true;
      let valid = [
        {field: 'boardInfo.subject', type: 'I', name: '제목', required: true},
        {field: 'boardInfo.muappchtype', type: 'S', name: '노출채널', required: true},
        {field: 'boardInfo.mumembertype', type: 'S', name: '대상회원유형', required: true},
        {field: 'boardInfo.mumemlvtype', type: 'S', name: '대상회원등급', required: true},
        {field: 'boardInfo.isevent', type: 'S', name: '카테고리', required: true},
        {field: 'boardInfo.disptype', type: 'S', name: '전시여부', required: true},
        {field: 'boardInfo.evinfo', type: 'I', name: '내용(PC)', required: true},
        {field: 'boardInfo.evinfomobile', type: 'I', name: '내용(모바일)', required: true},
        // {field: 'boardInfo.brandidx', type: 'S', name: '브랜드샵', required: true},
      ]

      // (1) 기본 정보 검사
      msg = this.$util.validMsg(this.$data, valid);
      if (!this.$util.isNull(msg)) {
        checkResult = false;
        alert(msg);
        return;
      }

      if(this.boardInfo.evsttime.length < 12) {
          alert("진행시작시간을 선택해주세요.");
          return false;
      }
      if(this.boardInfo.evedtime.length < 12) {
          alert("진행종료시간을 선택해주세요.");
          return false;
      }
      if(this.boardInfo.evsttime >= this.boardInfo.evedtime) {
          alert("진행종료시간을 시작시간 이후로 설정해주세요.");
          return false;
      }
      
      if(!this.$util.isNull(this.boardInfo.pubtime)) {
        if(this.boardInfo.pubtime.length < 12 && this.boardInfo.pubtime.length > 0) {
            alert("발표일을 선택해주세요.");
            return false;
        }
        if(this.boardInfo.evedtime >= this.boardInfo.pubtime) {
            alert("발표일을 진행종료시간 이후로 설정해주세요.");
            return false;
        }
      }

      // (2) 첨부 파일 검사
      if (util.isNull(this.files.pcimgfile)) {
        alert("대표이미지(PC)를 첨부해주세요.");
        checkResult = false;
        return;
      }
      if (util.isNull(this.files.mobileimgfile)) {
        alert("대표이미지(모바일)을 첨부해주세요.");
        checkResult = false;
        return;
      }

      return checkResult;
    },
    // 저장 버튼
    goSave() {
      if (!this.checkValidation()) {
        return;
      }
      
      let params = this.boardInfo;
      if(params.isdisplay === 'F') {
        let checkParam = {
          idx: this.eventIdx,
          isdisplay: 'F',
          overmsg: '저장 하시겠습니까?',
          zeromsg: '수정',
          isloading: false,
        }

        this.$http.post("/admin/operation/shopping/promotion/check", checkParam)
        .then(result => {
            if (result.statusCode == 200) {
                params.msg = result.data.msg;
                this.update(params);
            }
        })
        .catch(error => {
            this.$util.debug(error);
        })
      }else {
        params.msg = "저장 하시겠습니까?";
        this.update(params);
      }
    },
    getConcodeVal(event){
      //var optionValue = event.target.value;
      //var optionText = event.target.options[event.target.options.selectedIndex].text;
      this.searchData.concodeval = event.target.options[event.target.options.selectedIndex].text;
    },
    getConsign1Val(event){
      this.searchData.sign1val = event.target.options[event.target.options.selectedIndex].text;
    },    
    getConsign2Val(event){
      this.searchData.sign2val = event.target.options[event.target.options.selectedIndex].text;
    },    
    addcond() {
      if(!this.$util.isNull(this.searchData.condcode)
      && !this.$util.isNull(this.searchData.cond1)
      && !this.$util.isNull(this.searchData.sign1)
      && !this.$util.isNull(this.searchData.condval)
      ) {
         if(this.$util.isNull(this.searchData.relation)
         || this.$util.isNull(this.searchData.cond2)
         || this.$util.isNull(this.searchData.sign2)
         ){

          this.searchData.relation = '';
          this.searchData.cond2 = '';
          this.searchData.sign2 = '';
         }
        this.boardInfo.conddata.push(_.cloneDeep(this.searchData));
      }

      this.searchData.condcode = '';
      this.searchData.cond1 = '';
      this.searchData.sign1 = '';
      this.searchData.relation = '';
      this.searchData.cond2 = '';
      this.searchData.sign2 = '';
      this.searchData.condval = ''; 
    },
    removecond(event){
      this.boardInfo.conddata.splice(event,1);
      this.$forceUpdate();
    },     
    golottery(){
      let checkParam = {
        eventidx: this.eventIdx,
        winnercount: Number(this.boardInfo.winnercount),
          overmsg: '추첨 하시겠습니까?',
          zeromsg: '추첨',
          isloading: false,
        }
        if (confirm("추첨 하시겠습니까?")) {
          this.$http.post("/admin/operation/shopping/promotion/lottery", checkParam)
        .then(result => {
          if (result.statusCode == 200) {
                // params.msg = result.data.msg;
                // this.update(params);
                alert("추첨하였습니다.")
                this.boardInfo.isselectcomplete = 'T';

            }
            else if (result.statusCode == 201) {
                alert("이미 추첨되었습니다.");
            }
        })
        .catch(error => {
            this.$util.debug(error);
        })
        }

    },
    lotteryinit(){
      let checkParam = {
        eventidx: this.eventIdx,
        winnercount: Number(this.boardInfo.winnercount),
          overmsg: '초기화 하시겠습니까?',
          zeromsg: '추첨',
          isloading: false,
        }
        if (confirm("추첨 하시겠습니까?")) {
          this.$http.post("/admin/operation/shopping/promotion/lotteryinit", checkParam)
        .then(result => {
          if (result.statusCode == 200) {
                // params.msg = result.data.msg;
                // this.update(params);
                alert("초기화하였습니다.")
                this.boardInfo.isselectcomplete = 'F';

            }
        })
        .catch(error => {
            this.$util.debug(error);
        })
        }

    },    
    entryList(){
      let params = {
          eventidx: this.eventIdx,
        }
        this.$eventBus.$emit('modalShow', EntryListModalVue, params,
          (result) => {
          }
      );
    },    
    winnerList(data) {
				let params = {
          eventidx: this.eventIdx,
        }
        this.$eventBus.$emit('modalShow', WinnerListModalVue, params,
          (result) => {
          }
      );
		},
    update(params) {
      if (confirm(params.msg)) {
        params.idxlist = [this.eventIdx];

        // 이미지 데이터
        let files = [];
        if(!this.$util.isNull(this.addfiles.pcimgfile)){
            files.push({key: 'pcimgfile', file: this.addfiles.pcimgfile.file});
        }
        if(!this.$util.isNull(this.addfiles.spcimgfile)){
            files.push({key: 'spcimgfile', file: this.addfiles.spcimgfile.file});
        }
        if(!this.$util.isNull(this.addfiles.mobileimgfile)){
            // pc이미지 복사를 한 경우 file key가 없고 복사시 기존에 저장되어있는 pcimgfile을 저장해야하므로 pcimgfile의 idx를 보냄
            if(Object.prototype.hasOwnProperty.call(this.files.mobileimgfile,'file')){
                files.push({key: 'mobileimgfile', file: this.addfiles.mobileimgfile.file});
            } else {
                params.copycheck = true;
                params.copyidx = this.files.pcimgfile.idx;
            }
        }
        params.files = files;
        params.deletefile = new Set(this.deletefile);

        if(this.productList.length > 0) {
          params.productlist = this.productList;
        }

        this.$http.post("/admin/operation/shopping/promotion/update/detail", params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.onClose(true);
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    // 취소 버튼
    onClose(isreload) {
      // 팝업 닫기
      if (typeof(isreload)==='boolean' && isreload) {
          this.$emit('close', isreload);
      } else {
          this.$emit('close');
      }
    },

    // 추가상품 팝업 오픈
    openGoodsAdditionPopup() {
      this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, null,
          (result) => {
            this.addGoodsAddition(result.list);
          }
      );
    },

    // 상품 추가
    addGoodsAddition(list) {
      list.forEach(obj => {
        // 중복 제거
        let isExist = this.productList.find(element => element.goodsno === obj.goodsno);
        if (typeof isExist === 'undefined') {
          this.productList.push(obj);
        }

        // sortnum 새로 발번
        for (let i = 0; i < this.productList.length; i++) {
          this.productList[i].sortnum = i;
        }
      });
    },

    // 상품 삭제
    removeGoodsAddition() {
      if(this.moveData.targetIdx.length === 0) {
          alert("선택된 상품이 없습니다.");
          return ;
      }

      this.moveData.targetIdx.sort((a,b) => {
          if (a < b) {
              return 1;
          } else { 
              return -1;
          }
      })

      this.moveData.targetIdx.forEach(n => {
          this.productList.splice(n, 1);
      });
      this.moveData.targetIdx = [];

      if(this.productList.length === 0) {
          this.isProductAllCheck = false;
      }
    },

    // 블라인드 지정 버튼
    onChangeBlind(isBlind) {
      if (this.commentCheckList.length === 0) {
        alert("댓글을 선택해주세요.");
        return;
      }

      if (confirm("상태를 전환 하시겠습니까?")) {
        let params = {
          commentidxlist: this.commentCheckList,
          isblind: isBlind
        }

        this.$http.post("/admin/operation/shopping/promotion/update/comment/blind", params)
            .then(result => {
              if (result.statusCode === 200) {
                this.onSearch();
                this.commentCheckList = [];
                this.isCommentAllCheck = false;
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    // 상품 전체 선택
    onCheckAllProduct(check) {
      if (check) {
        this.moveData.targetIdx = [];

        let size = this.productList.length;
        for (let i = 0; i < size; i++) {
          this.moveData.targetIdx.push(i);
        }
      } else {
        this.moveData.targetIdx = [];
      }
    },

    onCheckProduct() {
      if(this.productList.length > this.moveData.targetIdxlength) {
        this.isProductAllCheck = false;
      } else {
        this.isProductAllCheck = true;
      }
    },

    // 댓글 전체 선택
    onCheckAllComment() {
      this.commentCheckList = [];
      if (!this.isCommentAllCheck) {
        this.commentCheckList = this.commentList.map(obj => obj.commentidx);
      }
    },

    // 댓글 삭제
    onDeleteComment() {
      if (this.commentCheckList.length === 0) {
        alert("삭제할 댓글을 선택해주세요.");
        return;
      }

      if (confirm("삭제 하시겠습니까?")) {
        let params = {
          commentidxlist: this.commentCheckList,
        }
        this.$http.post("/admin/operation/shopping/promotion/update/comment/delete", params)
            .then(result => {
              if (result.statusCode === 200) {
                this.onSearch();
                this.commentCheckList = [];
                this.isCommentAllCheck = false;
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    // 테이블 소트
    sortToggle(key) {
      let arr = key.split("_");
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData = this.$options.data().sortData;

      this.sortData[sortKey] = sortName;

      this.onSearchComment();
    },

    // 댓글 재조회
    onSearchComment() {
      let params = {
        eventidx: this.eventIdx,
        sort: this.sortData.regdate,
        isloading: false,
      }

      this.$http.post("/admin/operation/shopping/promotion/comment", params)
          .then(result => {
            if (result.statusCode === 200) {
              this.commentList = result.data.list;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    
    // 브랜드조회 팝업
    openSearchBrandPopup: function() {
        let param = { };
        this.$eventBus.$emit('modalShow', SearchBrandListPopup, param,
            (result) => {
                this.boardInfo.brandidx = result.idx;
                this.brandInfo = result;
            }
        );
    },
    removeBrand() {
      this.boardInfo.brandidx = null;
      this.brandInfo = {};
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드///////////////////////////////////////
    // 진행기간 DatePicker 콜백 메서드
    getEventTimeDate(date) {
      this.boardInfo.evsttime = date.fromDate12;
      this.boardInfo.evedtime = date.toDate12;
    },

    // 발표기간 DatePicker 콜백 메서드
    getEventPubTimeDate(date) {
      this.boardInfo.pubtime = date.toDate12;
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////팝업 메서드////////////////////////////////////////////
    // 직접 접근 URL 버튼
    goUrlBtn() {
      this.$refs.urlinput.select();
      let isCopy = document.execCommand('copy');
      if (isCopy) {
        alert("복사되었습니다.");
      }
    },

    // 첨부 사진 팝업
    goUploadFilePopup(imgPath) {
      this.isPictureViewPopupShow = true;
      this.imgPath = imgPath;
    },

    // 첨부 사진 팝업 닫기
    closeUploadFilePopup() {
      this.isPictureViewPopupShow = false;
      this.imgPath = '';
    },

    goVideoPopup(obj) {
      this.videoInfo = obj;
      this.isVideoViewPopupShow = true;
    },
    closeVideoPopup() {
      this.isVideoViewPopupShow = false;
    },

    // 답글 관리 팝업 열기
    goReplyInfoPopup(commentIdx, depth, comment, isAdd) {
      this.isReplyInfoPopupShow = true;
      if (isAdd === 'T') { // 추가
        this.replyPopupObj.depth = depth;
        this.replyPopupObj.isinsert = isAdd;
        this.replyPopupObj.reguserid = this.userInfo.id;
        this.replyPopupObj.upcommentidx = commentIdx;
        this.replyPopupObj.eventidx = this.eventIdx;
        this.replyPopupObj.issecret = 'F';
      } else { // 수정
        this.replyPopupObj.comment = comment;
        this.replyPopupObj.commentidxlist = [commentIdx];
      }
    },

    // 답글 관리 팝업 닫기
    closeReplyInfoPopup(isClose) {
      this.replyPopupObj = {};
      this.isReplyInfoPopupShow = false;

      // 저장을 눌렀을 때만 새로 고침
      if (isClose === 'F') {
        this.onSearch();
      }
    },

    // 신고 정보 팝업
    goReportInfoPopup(commentIdx) {
      this.isReportInfoPopupShow = true;
      this.reportPopupObj.commentidx = commentIdx;
    },

    // 신고 정보 팝업 닫기
    closeReportInfoPopup() {
      this.isReportInfoPopupShow = false;
      this.reportPopupObj = {}
    },

    // 유저 상세 팝업
    goMemberInfoPopup(userNo) {
      // alert("사용자 상세 팝업");
      this.isMemberDetailShow = true;
      this.memberDetailUserNo = userNo;
    },

    closeMemberInfoPopup() {
      this.isMemberDetailShow = false;
      this.memberDetailUserNo = '';
    },

    // 댓글 엑셀 다운로드
    goExcelDownloadPopup(isKind) {
      this.isExcelFileInfoPopupShow = true;
      if (isKind === 'comment') {
        this.excelPostPath = '/admin/operation/shopping/promotion/excel/comment';
      }
    },

    // 댓글 엑셀 다운로드 삭제
    closeExcelDownloadPopup() {
      this.isExcelFileInfoPopupShow = false;
      this.excelPostPath = '';
    },
    ///////////////////////////////////////////////////////////////////////////////////////////
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      if (this.isRead) {
        this.onInit();
        this.getCommonCodeList();
      } else {
        alert("페이지 접근 권한이 없습니다.");
        this.$emit('close');
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
     if(this.boardInfo.evedtime < (this.$util.getDate('') + this.$util.getTime('').substr(0,4)) ){
      this.isEndYn = true;
     }
     else{
      this.isEndYn = true;
     }
  

  },
  watch: {
    // 적용 채널 체크 상태 검사
    'checkObj.muappchTypeChecked'(value) {
      if (value.length < this.pageCode.muappchtype.length) {
        this.checkObj.isallmuappch = 'F';
      } else {
        this.checkObj.isallmuappch = 'T';
      }
      this.boardInfo.muappchtype = this.checkObj.muappchTypeChecked.join();
    },

    // 회원 유형 체크 상태 검사
    'checkObj.mumemberTypeChecked'(value) {
      if (value.length < this.pageCode.mumembertype.length) {
        this.checkObj.isallmumember = 'F';
      } else {
        this.checkObj.isallmumember = 'T';
      }
      this.boardInfo.mumembertype = this.checkObj.mumemberTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.mumemlvTypeChecked'(value) {
      if (value.length < this.pageCode.mumemlvtype.length) {
        this.checkObj.isallmumemlv = 'F';
      } else {
        this.checkObj.isallmumemlv = 'T';
      }
      this.boardInfo.mumemlvtype = this.checkObj.mumemlvTypeChecked.join();
    },

    // 댓글 상태
    'boardInfo.iscomment'(value) {
      if (value === 'F') {
        this.boardInfo.isseccomment = 'F'
      }
    },
  }
}
</script>
