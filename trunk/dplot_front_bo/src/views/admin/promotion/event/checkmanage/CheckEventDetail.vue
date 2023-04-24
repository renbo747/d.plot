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

    <!-- 답글 관리 팝업 -->
    <ReplyInfoPopup
        ref="replyPopup"
        v-if="isReplyInfoPopupShow"
        style="z-index: 1003"
        @close="closeReplyInfoPopup"
        :reply-obj="replyPopupObj"
    />

    <!-- 신고 정보 팝업 -->
    <ReportInfoPopup
        ref="reportPopup"
        v-if="isReportInfoPopupShow"
        style="z-index: 1004"
        @close="closeReportInfoPopup"
        :report-obj="reportPopupObj"
    />

    <!-- 출석인정일/출석일 팝업 -->
    <CheckEventAttendPopup
        ref="attendPopup"
        v-if="isAttendPopupShow"
        style="z-index: 1003"
        @close="closeAttendInfoPopup"
        :attend-popup-obj="attendPopupObj"
    />

    <!-- 유저 상세 정보 팝업 -->
    <AdminMemberInfo
        v-if="isMemberDetailShow"
        v-bind:activeUserNo="memberDetailUserNo"
        v-on:closeDetail="closeMemberInfoPopup"
        style="z-index: 1005;"
    />
    <!-- /유저 상세 정보 팝업 -->

    <!-- 출석체크이벤트 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
      <div class="modal-content" style="width: 1400px;">
        <div class="pop-header">
          <h2>출석체크이벤트 상세</h2>
          <button type="button" class="pop-close" @click="$emit('close')"></button>
        </div>
        <div class="pop-body">

          <!-- 출첵 이벤트 상세 내역 -->
          <div>
            <div class="gray-box mg0 clearfix">
              <div class="fl">
                <span>작성자 : {{ boardInfo.reguserid }}</span>
                <span class="left-bar">조회수 : {{ boardInfo.readcnt }}</span>
                <span class="left-bar">직접접근가능경로</span>
                <button type="button" class="btn blue-line ml3" @click="goUrlBtn">URL 복사</button>
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
                  <dt>사용여부</dt>
                  <dd>
                    <div class="radio_wrap wide">
                      <input type="radio" name="group00" id="group01" value="F" v-model="boardInfo.istrash"
                             checked/><label
                        for="group01">사용함</label>
                      <input type="radio" name="group00" id="group02" value="T" v-model="boardInfo.istrash"/><label
                        for="group02">사용안함</label>
                    </div>
                  </dd>
                </dl>
                <dl>
                  <dt>제목<i class="essential"></i></dt>
                  <dd><input type="text" style="width: 100%" placeholder="제목" v-model="boardInfo.subject"/></dd>
                </dl>
                <dl>
                  <dt>설명</dt>
                  <dd><input type="text" style="width: 100%" placeholder="설명" v-model="boardInfo.evdesc"/></dd>
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
                  <th>적용채널</th>
                  <td>
                    <div class="check-wrap">
                      <input type="checkbox" id="all1" v-model="checkObj.isallmuappch" true-value="T" false-value="F"
                             @change="checkAllMuAppch($event.target.checked)">
                      <label for="all1">전체</label>
                    </div>
                    <div class="check-wrap" v-for="(row, i) in pageCode.muappchtypecode" :key="i">
                      <input type="checkbox" :id="'group1' + i" :value="row.cmcode"
                             v-model="checkObj.muappchTypeChecked">
                      <label :for="'group1' + i">{{ row.codename }}</label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>대상회원유형</th>
                  <td>
                    <div class="check-wrap">
                      <input type="checkbox" id="all2" v-model="checkObj.isallmumember" true-value="T" false-value="F"
                             @change="checkAllMuMemer($event.target.checked)">
                      <label for="all2">전체</label>
                    </div>
                    <div class="check-wrap" v-for="(row, i) in pageCode.mumembertypecode" :key="i">
                      <input type="checkbox" :id="'group2' + i" :value="row.cmcode"
                             v-model="checkObj.mumemberTypeChecked">
                      <label :for="'group2' + i">{{ row.codename }}</label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>대상회원등급</th>
                  <td>
                    <div class="check-wrap">
                      <input type="checkbox" id="all3" v-model="checkObj.isallmumemlv" true-value="T" false-value="F"
                             @change="checkAllMuMemLv($event.target.checked)">
                      <label for="all3">전체</label>
                    </div>
                    <div class="check-wrap" v-for="(row, i) in pageCode.mumemlvtypecode" :key="i">
                      <input type="checkbox" :id="'group3' + i" :value="row.cmcode"
                             v-model="checkObj.mumemlvTypeChecked">
                      <label :for="'group3' + i">{{ row.codename }}</label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>노출여부<i class="essential"></i></th>
                  <td>
                    <div class="radio_wrap wide3">
                      <div v-for="(row, i) in pageCode.disptypecode" :key="i">
                        <input type="radio" :id="'group4' + i" :value="row.cmcode" v-model="boardInfo.disptype">
                        <label :for="'group4' + i">{{ row.codename }}</label>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>옵션<i class="essential"></i></th>
                  <td>
                    <div class="dpb">
                      <input type="checkbox" id="group61" true-value="T" false-value="F"
                             v-model="boardInfo.iscomment"/><label for="group61">댓글</label>
                      <span v-show="boardInfo.iscomment === 'T'" class="txt-gray dpib"
                            style="width: 40px; text-align: center;">비밀글</span>
                      <div v-show="boardInfo.iscomment === 'T'" class="radio_wrap dpib ml3">
                        <input type="radio" name="group061" id="group611" value="F"
                               v-model="boardInfo.isseccomment"/><label
                          for="group611">미허용</label>
                        <input type="radio" name="group061" id="group612" value="T"
                               v-model="boardInfo.isseccomment"/><label
                          for="group612">허용</label>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>개인정보동의<i class="essential"></i></th>
                  <td>
                    <div class="radio_wrap wide dpib">
                      <input type="radio" name="group07" id="group71" value='0' v-model="boardInfo.isinfo"/><label
                        for="group71">불필요</label>
                      <input type="radio" name="group07" id="group72" value="1" v-model="boardInfo.isinfo"/><label
                        for="group72">필요</label>
                    </div>
                    <div class="dpib ml3" v-if="boardInfo.isinfo === '1'">
                      <input type="checkbox" id="group81" true-value="T" false-value="F"
                             v-model="boardInfo.isperagree"/><label for="group81">개인정보 수집 및 이용
                      동의</label>
                      <input type="checkbox" id="group82" true-value="T" false-value="F"
                             v-model="boardInfo.ismktagree"/><label for="group82">마케팅 정보 활용
                      동의</label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>출석인정기준<i class="essential"></i></th>
                  <td>
                    <div class="radio_wrap wide">
                      <input type="radio" name="group09" id="group91" value="F" v-model="boardInfo.iscontattend"/><label
                        for="group91">미연속출석</label>
                      <input type="radio" name="group09" id="group92" value="T" v-model="boardInfo.iscontattend"/><label
                        for="group92">연속출석</label>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>출석수<i class="essential"></i></th>
                  <td>
                    <input type="number" class="center" style="width: 50px;" v-model="boardInfo.winattendcnt"/>
                    <span class="ml3">일 출석 시 혜택 지급 / 총 출석 가능일 수 : </span>
                    <input type="text" class="center" style="width: 50px;" :value="getEventTimeDateCount" readonly/>
                    <!--                <input type="text" class="center" style="width: 50px;"  readonly/>-->
                    <span class="ml3">일</span>
                  </td>
                </tr>
                <tr>
                  <th>혜택지급반복</th>
                  <td>
                    <div class="radio_wrap dpib">
                      <input type="radio" name="group10" id="group101" value="T" v-model="boardInfo.iswagestop"/><label
                        for="group101">혜택지급 제한(1회)</label>
                      <input type="radio" name="group10" id="group102" value="F" v-model="boardInfo.iswagestop"/><label
                        for="group102">혜택지급 반복</label>
                    </div>
                    <span class="ml3">(출석수 충족 시 초기화) / 총 혜택지급 가능 횟수</span>
                    <input type="text" class="center" style="width: 50px;" :value="getBenefitCount" readonly/>
                    <!--                <input type="text" class="center" style="width: 50px;" readonly/>-->
                    <span class="ml3">회</span>
                  </td>
                </tr>
                <tr>
                  <th>대표이미지(PC)<i class="essential"></i></th>
                  <td>
                    <div class="img-with-text" style="width: 202px;">
                      <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['pcimgFile'])}">
                        <img :src="imgPreview['pcimgFile']" alt="대표이미지(PC)" v-if="!$util.isNull(files['pcimgFile'])">
                      </div>
                      <button type="button" class="btn blue-line mt10" style="width: 100%;"
                              v-if="$util.isNull(files['pcimgFile'])" @click="fileAttach('pcimgFile')">파일 올리기
                      </button>
                      <input type="file" ref="pcimgFile" @change="handleFileUpload('pcimgFile')"
                             accept="image/jpeg, image/png" hidden/>
                      <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                              v-if="!$util.isNull(files['pcimgFile'])" @click="fileAttach('pcimgFile')">변경
                      </button>
                      <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                              v-if="!$util.isNull(files['pcimgFile'])" @click="removeFile('pcimgFile')">삭제
                      </button>
                    </div>
                    <div class="img-with-text text">
                      <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                      <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 :
                        JPG,
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
                      <div class="img-thumb"
                           :class="[[copyimgcheck ? 'size200':'size460wide'],{'no-image': $util.isNull(files['mobileimgFile'])}]">
                        <img :src="imgPreview['mobileimgFile']" alt="대표이미지(모바일)"
                             v-if="!$util.isNull(files['mobileimgFile'])">
                      </div>
                      <button type="button" class="btn blue-line mt10" style="width: 100%;"
                              v-if="$util.isNull(files['mobileimgFile'])" @click="fileAttach('mobileimgFile')">파일 올리기
                      </button>
                      <input type="file" ref="mobileimgFile" @change="handleFileUpload('mobileimgFile')"
                             accept="image/jpeg, image/png" hidden/>
                      <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                              v-if="!$util.isNull(files['mobileimgFile'])" @click="fileAttach('mobileimgFile')">변경
                      </button>
                      <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                              v-if="!$util.isNull(files['mobileimgFile'])" @click="removeFile('mobileimgFile')">삭제
                      </button>
                    </div>
                    <div class="img-with-text text" v-show="!copyimgcheck">
                      <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                      <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 :
                        JPG,
                        JPEG, PNG</p>
                    </div>
                    <div class="img-with-text text" v-show="copyimgcheck">
                      <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                      <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 :
                        JPG,
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
                      <input type="checkbox" id="copy-text"><label for="copy-text">PC 내용을 복사</label>
                    </div>
                    <div class="mt10">
                      <CommonEditor ref="mobileEditor"/>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th>혜택 구분<i class="essential"></i></th>
                  <td>
                    <div class="radio_wrap wide3">
                      <div v-for="(row, i) in pageCode.bettypecode" :key="i">
                        <input type="radio" :id="'group134' + i" :value="row.cmcode" v-model="boardInfo.benefittype">
                        <label :for="'group134' + i">{{ row.codename }}</label>
                      </div>
                    </div>
                  </td>
                </tr>
                <!-- 적립금 선택 시 노출 -->
                <tr v-if="boardInfo.benefittype === pageCode.bettypecode[0].cmcode">
                  <th>적립금 지급<i class="essential"></i></th>
                  <td>
                    <span>혜택지급 조건을 만족한 회원에게 적립금</span>
                    <input type="number" class="center" style="width: 50px;" v-model="boardInfo.paypoint"/>
                    <span class="ml3">포인트를 지급</span>
                  </td>
                </tr>
                <!-- /적립금 선택 시 노출 -->
                <!-- e포인트 선택 시 노출 -->
                <tr v-if="boardInfo.benefittype === pageCode.bettypecode[1].cmcode">
                  <th>D포인트 지급<i class="essential"></i></th>
                  <td>
                    <span>혜택지급 조건을 만족한 회원에게 D포인트</span>
                    <input type="number" class="right ml3" style="width: 60px;" v-model="boardInfo.paypoint"/>
                    <span class="ml3">포인트를 지급</span>
                  </td>
                </tr>
                <tr v-if="boardInfo.benefittype === pageCode.bettypecode[1].cmcode">
                  <th>D포인트 중복사용여부<i class="essential"></i></th>
                  <td>
                    <div class="radio_wrap wide dpib">
                      <input type="radio" name="group04" id="group413" value="T" v-model="boardInfo.isepointdup"/><label
                        for="group413">허용</label>
                      <input type="radio" name="group04" id="group424" value="F" v-model="boardInfo.isepointdup"/><label
                        for="group424">미허용</label>
                    </div>
                    <span class="txt-orange ml10"><i class="icon-alert"></i>적립금과 함께 사용 가능한지의 여부</span>
                  </td>
                </tr>
                <tr v-if="boardInfo.benefittype === pageCode.bettypecode[1].cmcode">
                  <th>D포인트 유효기간<i class="essential"></i></th>
                  <td>
                    <span>시리얼프로모션을 통해 지급되는 D포인트의 유휴기간은 진행기간과 동일하게 자동 설정됩니다.</span>
                  </td>
                </tr>
                <!-- /e포인트 선택 시 노출 -->
                <!-- 쿠폰 선택 시 노출-->
                <tr v-if="boardInfo.benefittype === pageCode.bettypecode[2].cmcode">
                  <th>쿠폰 지급<i class="essential"></i></th>
                  <td>
                    <span>혜택지급 조건을 만족한 회원에게</span>
                    <select style="width: 450px;" v-model="boardInfo.comcpnidx">
                      <option v-for="(row, i) in couponList" :key="i" :value="row.idx">{{ row.fulltitle }}</option>
                    </select>
                    <span class="ml3">쿠폰을 지급</span>
                  </td>
                </tr>
                <!-- /쿠폰 선택 시 노출-->
                </tbody>
              </table>
            </div>
          </div>
          <!-- /출첵 이벤트 상세 내역 -->

          <!-- 댓글 -->
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
                <button type="button" class="btn green-line" @click="goExcelDownloadPopup('comment')"><i class="icon-excel"></i>엑셀다운로드</button>
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
                  <col width="4%"/><!-- 신고 -->
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
                  <th>첨부</th>
                  <th>댓글구분</th>
                  <th>답글</th>
                  <th>아이디</th>
                  <th>이름</th>
                  <th>신고</th>
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
                  <td v-if="row.depth === 0">
                    <a @click="goUploadFilePopup(row.imgpath)">
                      <i :class="row.icon"></i>
                    </a>
                  </td>
                  <td v-else>
                    -
                  </td>
                  <td>{{ row.issecret }}</td>
                  <td>{{ row.commentcount }}
                    <button v-if="(row.depth === 0) && (isWrite === true)" type="button" class="btn blue-line ml10"
                            @click="goReplyInfoPopup(row.commentidx, 1, '','T')">추가
                    </button>
                  </td>
                  <td>{{ row.userid }}</td>
                  <td><a @click="goMemberInfoPopup(row.userno)" class="link">{{ row.username }}</a></td>
                  <td><a @click="goReportInfoPopup(row.commentidx)" class="link">{{ row.reportcount }}</a></td>
                  <td>{{ row.regdate }}</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <!-- /댓글 -->

          <!-- 출석체크 -->
          <div>
            <div class="bar-title">출석체크</div>
            <div class="caption-group clearfix">
              <div class="total-group fl">
                <span class="total">전체 <strong>{{ attendList.length }}</strong>건</span>
              </div>
              <div class="btn-group fr">
                <button type="button" class="btn green-line" @click="goExcelDownloadPopup('attend')"><i class="icon-excel"></i>엑셀다운로드</button>
              </div>
            </div>
            <div class="scroll-y" style="max-height: 400px;">
              <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>출석체크</caption>
                <colgroup>
                  <col width="3%"/><!-- checkbox -->
                  <col width="5%"/><!-- No -->
                  <col width="26"/><!-- 아이디 -->
                  <col width="26"/><!-- 이름 -->
                  <col width="20%"/><!-- 출석인정일/출석일 -->
                  <col width="20%"/><!-- 혜택지급 -->
                </colgroup>
                <thead>
                <tr>
                  <th><input type="checkbox" id="chkall" v-model="isAttendAllCheck" @click="onCheckAllAttend"/></th>
                  <th>No</th>
                  <th>아이디</th>
                  <th>이름</th>
                  <th>출석인정일/출석일</th>
                  <th>혜택지급
                    <button type="button" class="sort down"></button>
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(row, i) in attendList" :key="i">
                  <td><input type="checkbox" :id="'chk0' + i"/></td>
                  <td>{{ row.num }}</td>
                  <td>{{ row.memberid }}</td>
                  <td><a @click="goMemberInfoPopup(row.userno)" class="link">{{ row.name }}</a></td>
                  <td><a @click="goAttendInfoPopup(row.userno)" class="link">{{ row.totalcount }}</a></td>
                  <td>{{ row.benefitpaycount }}</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <!-- /출석체크 -->

          <div class="btn-group">
            <button type="button" class="btn big blue" v-if="isWrite" @click="goSave">저장</button>
            <button type="button" class="btn big darkgray" @click="$emit('close')">취소</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /이벤트 상세 팝업 -->
  </div>
</template>

<script>
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonEditor from "@views.admin/common/CommonEditor";
import ExcelFileInfoPopup from "@views.admin/promotion/event/popup/ExcelFileInfoPopup";
import PictureViewPopup from "@views.admin/promotion/event/popup/PictureViewPopup";
import ReplyInfoPopup from "@views.admin/promotion/event/popup/ReplyInfoPopup";
import ReportInfoPopup from "@views.admin/promotion/event/popup/ReportInfoPopup";
import CheckEventAttendPopup from "@views.admin/promotion/event/popup/CheckEventAttendPopup";
import AdminMemberInfo from "@views.admin/member/info/AdminMemberInfo";

export default {
  name: "admin.promotion.checkevent.detail",
  components: {
    AdminMemberInfo,
    CheckEventAttendPopup,
    ReportInfoPopup,
    ReplyInfoPopup, PictureViewPopup, ExcelFileInfoPopup, CommonEditor, CommonDatePickerFromTo
  },
  props: {
    pageCode: {                 // 페이지 코드
      disptypecode: {}, // 노출 구분
      muappchtypecode: {}, // 적용 채널
      mumembertypecode: {}, // 대상 회원
      mumemlvtypecode: {}, // 대상 회원 등급
      completetypecode: {}, // 진행 상태
      bettypecode: {},      // 혜택 구분
    },
    eventIdx: Number,
  },
  data() {
    return {
      isRead: false,
      isWrite: false,
      copyimgcheck: false,
      boardInfo: {
        eventidx: '',               // 이벤트 IDX
        isinfo: '',                 // 체크용(개인정보 동의 상태)
        isdupenter: '',             // N.N 중복응모 사용 여부
        isenter: 'F',               // N.N 응모 사용 여부
        dupentertype: '',           // N.N 중복응모허용타입
        dupentercnt: 0,             // N.N 중복응모횟수
        istrash: 'F',               // 사용여부
        subject: '',                // 제목
        evdesc: '',                 // 이벤트 설명
        evsttime: '',               // 이벤트 시작일
        evedtime: '',               // 이벤트 종료일
        muappchtype: '',            // 적용 채널
        mumembertype: '',           // 회원 유형
        mumemlvtype: '',            // 회원 등급
        disptype: '',               // 노출 여부
        iscomment: 'T',             // 댓글 사용 여부
        isseccomment: 'F',          // 비밀글 사용 여부
        isperagree: 'F',            // 개인정보동의여부
        ismktagree: 'F',            // 마케팅 정보 동의 여부
        userno: '',                 // 작성자 번호
        reguserid: '',              // 작성자 아이디
        writer: '',                 // 작성자 이름
        iscontattend: 'F',          // 연속출석인정여부
        winattendcnt: 0,            // 당첨출석수
        iswagestop: 'T',            // 혜택지급반복
        benefittype: '',            // 혜택구분
        paypoint: 0,               // 지급액
        isepointdup: 'F',           // 중복사용여부
        comcpnidx: '',              // 쿠폰 idx
        evinfo: '',                 // 내용
        evinfomobile: '',           // 모바일 내용
        readcnt: 0,                 // 조횟수
        goodstitle: '',              // 연관상품목록타이틀
        moddate: '',                // 수정 날짜
        regdate: '',                // 생성 날짜
        eventtype: '',               // 이벤트 타입
      },
      count: 0,
      files: {                      // 첨부 파일
        pcimgFile: '',
        mobileimgFile: '',
      },
      imgPreview: {                 // 이미지용
        pcimgFile: '',
        mobileimgFile: '',
      },
      eventTimeInfo: {              // 진행기간
        startyyyymmdd: '',
        starthh: '',
        startmm: '',
        toyyyymmdd: '',
        tohh: '',
        tomm: ''
      },
      checkObj: {
        isallmuappch: 'T',
        muappchTypeChecked: [],          // 적용 채널
        isallmumember: 'T',
        mumemberTypeChecked: [],         // 회원 유형
        isallmumemlv: 'T',
        mumemlvTypeChecked: [],          // 회원 등급
      },
      userInfo: {
        id: '',
        mdcode: '',
        name: '',
        no: '',
        status: '',
        usertype: ''
      },
      couponList: [],                     // 쿠폰 리스트

      commentList: [],              // 댓글 리스트
      commentCheckList: [],         // 댓글 체크 리스트
      isCommentAllCheck: false,     // 댓글 전체 체크

      attendList: [],               // 출첵 현황 리스트
      attendCheckList: [],          // 출첵 현황 체크 리스트
      isAttendAllCheck: false,      // 출첵 현황 전체 체크

      isAttendPopupShow: false,         // 출석여부 팝업
      attendPopupObj: {
        eventidx: '',
        userno: '',
        startdate: '',
        enddate: '',
        iscontattend: ''
      },

      isExcelFileInfoPopupShow: false,  // 엑셀 파일 정보 구성 팝업
      excelPostPath: '',                // post path 경로

      isPictureViewPopupShow: false,    // 첨부 사진 팝업
      imgPath: '',                      // 사진 경로

      isReplyInfoPopupShow: false,      // 답글 관리 팝업
      replyPopupObj: {                  // 답글 관리 팝업 props 객체
        // eventidx: '',                   // 이벤트 idx
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
      }
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드///////////////////////////////////////////
    // 저장
    goSave() {

      // 유효성 체크
      if (!this.checkValidation()) {
        return;
      }

      if (confirm("저장 하시겠습니까?")) {
        let params = JSON.parse(JSON.stringify(this.boardInfo));

        // 이미지 파일 셋팅
        let files = [];
        files.push({key: 'pcimgFile', file: this.files.pcimgFile.file});
        files.push({key: 'mobileimgFile', file: this.files.mobileimgFile.file});
        params.files = files;

        // 타입에 따른 데이터 셋팅
        switch (params.benefittype) {
          case this.pageCode.bettypecode[0].cmcode: // 적립금
            params.isepointdup = ''; // e포인트 중복사용여부
            params.comcpnidx = ''; // 쿠폰
            break;
          case this.pageCode.bettypecode[1].cmcode: // E포인트
            params.comcpnidx = ''; // 쿠폰
            break;
          case this.pageCode.bettypecode[2].cmcode: // 쿠폰
            params.isepointdup = ''; // e포인트 중복사용여부
            params.paypoint = 0; // 적립액
            break;
        }

        this.$http.post("/admin/promotion/checkevent/update", params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.$emit('close');
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    onInit() {
      this.onSearch(); // 세부 정보 조회
      this.setUserInfo(); // 현재 접속 유저 정보 조회
      this.getCouponList(); // 쿠폰 리스트 조회
    },

    // 유효성 체크
    checkValidation() {
      this.setEditorText();

      let msg = '';
      let checkResult = true;
      let valid = [
        {field: 'boardInfo.subject', type: 'I', name: '제목', required: true},
        {field: 'boardInfo.evinfo', type: 'I', name: '내용(PC)', required: true},
        {field: 'boardInfo.evinfomobile', type: 'I', name: '내용(모바일)', required: true},
        {field: 'boardInfo.winattendcnt', type: 'I', name: '출석수', required: true},
      ]

      // 혜택 구분 - 적립금
      if (this.boardInfo.benefittype === this.pageCode.bettypecode[0].cmcode) {
        valid.push({field: 'boardInfo.paypoint', type: 'I', name: '적립금', required: true});
      }

      // 혜택 구분 - E포인트
      if (this.boardInfo.benefittype === this.pageCode.bettypecode[1].cmcode) {
        valid.push({field: 'boardInfo.paypoint', type: 'I', name: '적립금', required: true});
      }

      // 혜택 구분 - 쿠폰폰
      if (this.boardInfo.benefittype === this.pageCode.bettypecode[2].cmcode) {
        valid.push({field: 'boardInfo.comcpnidx', type: 'I', name: '쿠폰', required: true});
      }

      // (1) 기본정보 검사
      msg = this.$util.validMsg(this.$data, valid);
      if (!this.$util.isNull(msg)) {
        checkResult = false;
        alert(msg);
        return;
      }

      // (2) 첨부 파일 검사
      if (this.$util.isNull(this.files.pcimgFile)) {
        alert("대표이미지(PC)를 첨부해주세요.");
        checkResult = false;
        return;
      }
      if (this.$util.isNull(this.files.mobileimgFile)) {
        alert("대표이미지(모바일)을 첨부해주세요.");
        checkResult = false;
        return;
      }

      return checkResult;
    },

    // codename array get
    getCmCode(codeArr) {
      return codeArr.map(obj => obj.cmcode);
    },

    // 출첵 이벤트 상세 조회
    onSearch() {
      let params = {
        eventidx: this.eventIdx
      }

      this.$http.post("/admin/promotion/checkevent/detail", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;

              // 출첵 이벤트 상세 정보
              this.boardInfo = data.checkeventdtl.eventdtlinfo;
              // 출첵 이벤트 진행기간 정보
              this.eventTimeInfo = data.checkeventdtl.eventtimeinfo;
              // 댓글 리스트
              this.commentList = data.commentlist;
              // 출첵 현황 리스트
              this.attendList = data.attendlist;
              // 이미지 파일
              this.files.pcimgFile = data.files.pcimgfile;
              this.files.mobileimgFile = data.files.mobileimgfile;

              // 정보 셋팅
              this.setBoardInfo();
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
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

    // 접속자 정보 가져오기
    setUserInfo() {
      this.userInfo = this.$util.getUser('ADMIN_USER');
    },

    // 상세 정보 셋팅
    setBoardInfo() {
      // 에디터 내용
      this.$refs.pcEditor.content = this.boardInfo.evinfo;
      this.$refs.mobileEditor.content = this.boardInfo.evinfomobile;

      // 이미지 파일 셋팅
      this.imgPreview.pcimgFile = this.files.pcimgFile.fullpath;
      this.imgPreview.mobileimgFile = this.files.mobileimgFile.fullpath;

      // 파일 이름이 같다면 PC 대표 이미지 복사 기능 사용으로 판별
      this.copyimgcheck = this.files.pcimgFile.imgforiname === this.files.mobileimgFile.imgforiname;

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

    // 쿠폰 리스트
    getCouponList() {
      this.$http.post("/admin/promotion/checkevent/coupon", {})
          .then(result => {
            if (result.statusCode === 200) {
              this.couponList = result.data.list;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
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
      let fileType = ['image/png', 'image/jpeg', 'image/png'];
      if (!fileType.includes(file.files[0].type)) {
        alert('jpg, jpeg, png파일만 첨부 가능합니다.');
        file.value = null;
        this.files[fileTypeKey] = '';
        return false;
      }
      if (file.files[0].size > 10485760) {
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
      this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);

      if (fileTypeKey === 'pcimgFile' && this.copyimgcheck) {
        this.setSameAsPcepreImg();
      }
      // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
      if (fileTypeKey === 'mobileimgFile') {
        this.copyimgcheck = false;
      }
    },

    // 파일 삭제
    removeFile(fileTypeKey, index) {
      if (confirm('파일을 삭제 하시겠습니까?')) {
        this.files[fileTypeKey] = '';
        this.imgPreview[fileTypeKey] = '';
        this.$refs[fileTypeKey].value = null;

        // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
        if (fileTypeKey === 'mobileimgFile') {
          this.copyimgcheck = false;
        }
      }
    },

    // PC 대표이미지와 동일하게 세팅
    setSameAsPcepreImg() {
      if (this.copyimgcheck) {
        this.files.mobileimgFile = this.files.pcimgFile;
        this.imgPreview.mobileimgFile = this.imgPreview.pcimgFile;
      } else {
        this.files.mobileimgFile = '';
        this.imgPreview.mobileimgFile = '';
      }
    },

    // 댓글 전체 선택
    onCheckAllComment() {
      this.commentCheckList = [];
      if (!this.isCommentAllCheck) {
        this.commentCheckList = this.commentList.map(obj => obj.commentidx);
      }
    },

    // 출첵 현황 전체 선택
    onCheckAllAttend() {

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
          moduserid: this.userInfo.id
        }
        this.$http.post("/admin/promotion/event/update/comment/delete", params)
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

        this.$http.post("/admin/promotion/event/update/comment/blind", params)
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

    // 적용 채널 전체 체크
    checkAllMuAppch(value) {
      if (value) {
        // 적용 채널
        this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtypecode);
      } else {
        this.checkObj.muappchTypeChecked = [];
      }
    },

    // 회원 유형 전체 체크
    checkAllMuMemer(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertypecode);
      } else {
        this.checkObj.mumemberTypeChecked = [];
      }
    },

    // 회원 등급 전체 체크
    checkAllMuMemLv(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtypecode);
      } else {
        this.checkObj.mumemlvTypeChecked = [];
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

      this.$http.post("/admin/promotion/event/comment", params)
          .then(result => {
            if (result.statusCode === 200) {
              this.commentList = result.data.list;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드//////////////////////////////////////////
    // 진행기간 DatePicker 콜백 메서드
    getEventTimeDate(date) {
      this.boardInfo.evsttime = date.fromDate12;
      this.boardInfo.evedtime = date.toDate12;
    },
    ///////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////팝업 메서드//////////////////////////////////////////////
    // 직접 접근 URL 버튼
    goUrlBtn() {
      alert("직접 접근 URL 버튼");
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

    // 출첵 현황 팝업
    goAttendInfoPopup(userNo) {
      this.isAttendPopupShow = true;
      this.attendPopupObj.eventidx = this.boardInfo.eventidx;
      this.attendPopupObj.userno = userNo;
      this.attendPopupObj.startdate = this.eventTimeInfo.startyyyymmdd;
      this.attendPopupObj.enddate = this.eventTimeInfo.toyyyymmdd;
      this.attendPopupObj.iscontattend = this.boardInfo.iscontattend;
    },

    // 출첵 현황 팝업 닫기
    closeAttendInfoPopup() {
      this.isAttendPopupShow = false;
      this.attendPopupObj.eventidx = '';
      this.attendPopupObj.userno = '';
    },

    // 댓글 엑셀 다운로드
    goExcelDownloadPopup(isKind) {
      this.isExcelFileInfoPopupShow = true;
      if (isKind === 'comment') {
        this.excelPostPath = '/admin/promotion/event/excel/comment';
      } else {
        this.excelPostPath = '/admin/promotion/checkevent/excel/atten';
      }
    },

    // 댓글 엑셀 다운로드 삭제
    closeExcelDownloadPopup() {
      this.isExcelFileInfoPopupShow = false;
      this.excelPostPath = '';
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
    ///////////////////////////////////////////////////////////////////////////////////////////
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      if (this.isRead) {
        this.onInit();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  watch: {
    // 적용 채널 체크 상태 검사
    'checkObj.muappchTypeChecked'(value) {
      if (value.length < this.pageCode.muappchtypecode.length) {
        this.checkObj.isallmuappch = 'F';
      } else {
        this.checkObj.isallmuappch = 'T';
      }
      this.boardInfo.muappchtype = this.checkObj.muappchTypeChecked.join();
    },

    // 회원 유형 체크 상태 검사
    'checkObj.mumemberTypeChecked'(value) {
      if (value.length < this.pageCode.mumembertypecode.length) {
        this.checkObj.isallmumember = 'F';
      } else {
        this.checkObj.isallmumember = 'T';
      }
      this.boardInfo.mumembertype = this.checkObj.mumemberTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.mumemlvTypeChecked'(value) {
      if (value.length < this.pageCode.mumemlvtypecode.length) {
        this.checkObj.isallmumemlv = 'F';
      } else {
        this.checkObj.isallmumemlv = 'T';
      }
      this.boardInfo.mumemlvtype = this.checkObj.mumemlvTypeChecked.join();
    },

    // 댓글이 없을 경우
    'boardInfo.iscomment'(value) {
      if (value === 'F') {
        this.boardInfo.isseccomment = 'F'
      }
    },

    // 개인정보동의 상태
    'boardInfo.isinfo'(value) {
      if (value === "0") {
        this.boardInfo.ismktagree = "F";
        this.boardInfo.isperagree = "F";
      }
    },
  },
  computed: {
    // 총 출석 가능일 수
    getEventTimeDateCount() {
      return this.$util.getDateDiff(this.boardInfo.evsttime, this.boardInfo.evedtime);
    },

    // 총 혜택지급 가능 횟수
    getBenefitCount() {
      if (this.boardInfo.iswagestop === 'T') {
        return 1;
      }

      let count = this.boardInfo.winattendcnt * 1;

      // 공백이거나 0이면 리턴
      if (count === 0 || this.$util.isNull(count)) {
        return 0;
      }
      let day = this.$util.getDateDiff(this.boardInfo.evsttime, this.boardInfo.evedtime)
      return isNaN(day / count) ? 0 : Math.floor(day / count);
    }
  }
}
</script>
