<template>
  <div class="dp-promotion promotion-detail pc-top-padding">
    <div class="container">
      <div class="container-inner">
        <header class="promotion__header">
          <h2 class="promotion__title mb-0">PROMOTION</h2>
        </header>
        <!-- 프로모션 타이틀 -->
        <div class="promotion-detail__header">
          <div class="container">
            <div class="promotion-detail__description-title">
              <span class="promotion__title">[ {{Detail.eventname}} ]</span>
              {{ Detail.subject }}
            </div>
            <div
              class="banner__description__bottom d-flex justify-content-between"
            >
              <div class="promotion__date dp-p">
                기간 : {{Detail.startdate }} ~ {{Detail.enddate}} <br/>
                {{!$util.isNull(Detail.pubtime) ? '발표일 : ' + Detail.pubtime : ''}}
              </div>
              <div class="promotion__icon-share" style="cursor: pointer">
                <i class="dp-icon sm02 icon-share"  @click="snsShareModal()"></i>
              </div>
              <!-- // URL Copy Modal -->
            </div>
          </div>
        </div>
        <!-- 프로모션 상세 영역    -->
        <div class="promotion__detail-area">
          <!-- <img
            :src="Detail.fullpath"
            alt="프로모션 상세 영역"
          /> -->
        <div v-html="Detail.evinfo"></div>
        </div>
        <!-- 프로모션 상품 영역    -->
        
        <section class="promotion__product__section">
          <div class="promotion__product-area">
            <div class="container">
              <ul class="list-style-none row">
                <li
                  class="col-3"
                  v-for="(item, index) in goodsList"
                  :key="index"
                >
                  <product :product-info="item" product_type="promotion" :isBedge="true"/>
                </li>
              </ul>
            </div>
          </div>
        </section>

        <!-- 220823 스와이퍼 제거  -->
        <!-- <div class="promotion__product-area">
          <swiper :options="promotionSwiper"> -->
            <!-- <swiper-slide
              v-for="(list, index) in goodsGroup"
              :key="index"
            >
              <template v-for="(item, index2) in list" >
              <product :product-info="item" :key="index2"/>
              </template>
            </swiper-slide> -->
            <!-- <swiper-slide
              v-for="(item, index) in goodsList"
              :key="index"
            >
              <product :product-info="item" />
            </swiper-slide>

            <div class="swiper-pagination" slot="pagination" v-show="goodsList.length > 4"></div>
          </swiper>
        </div> -->
        
        <!-- 프로모션 브랜드 정보   -->
        <div v-if="!$util.isNull(Detail.brandidx)" class="add-info__brand">
          <div class="brand__box d-flex" style="width:100%">
            <figure class="brand__figure">
              <img :src="Detail.brandpath" />
            </figure>

            <p
              class="brand__slogan dp-title02 text-gray-700 font-weight-400 mb-0"
            >
            {{Detail.headcopy}}
            </p>
            <div>
              <b-button
                class="dp-btn dp-btn-icon"
                variant="outline-gray-800 not-hover btn-h48"
                squared
                @click="goBrand(Detail.brandidx)"
              >
                <span class="dp-p text-black">브랜드샵</span>
                <i class="dp-icon icon-more sm02"></i>
              </b-button>
            </div>
          </div>
        </div>
        <div v-else style="margin-bottom: 100px;">
        </div>

        <!-- 프로모션 댓글  -->
        <div v-if="Detail.iscomment === 'T'" class="promotion__comment-area container">
          <div class="comment__title">댓글</div>
          <div class="comment__box">
            <form>
              <base-textarea
                v-model="comment"
                class="comment__box__textarea"
                placeholder="개인정보를 공유 및 요청하거나, 명예훼손, 무단광고, 불법 정보 유포시 모니터링 후 삭제 될 수 있으며, 이에 대한 민형사상 책임은 게시자에게 있습니다."
              ></base-textarea>
            </form>
          </div>
          <div class="comment-btn-area">
            <div>
            </div>
            <div class="comment-btn">
              <b-button
                  @click="regComment()"
                  class="btn dp-btn dp-btn-icon not-hover btn-outline-gray-800 rounded-0"
                  variant="outline-gray-800"
                  squaredicon-review02
                  v-b-modal.productInquiryModal
              >
                <i class="dp-icon icon-review02"></i>
                <span class="dp-p-sm text-black font-weight-400"
                >댓글 등록하기</span
                >
              </b-button>
            </div>

          </div>
          <div class="comment-list__header">총 {{commentListTotal}}건</div>
          <div class="comment-list__area">
            <!--  댓글이 없을 때      -->
            <template v-if="commentList.length === 0">
              <p class="text-center dp-p-sm">댓글이 없습니다.</p>
            </template>
            <!--  댓글이 있을 때     -->
            <template v-else>
              <ul class="comment-list__ul list-style-none">
                <li v-for="(list, index) in commentList" :key="index">
                  <!-- 댓글 -->
                  <div class="comment-list-item">
                    <div class="comment-header d-flex justify-content-between">
                      <div class="comment-header-left">
                        <span class="comment-date">{{
                          list.regdate
                        }}</span>
                        <span>{{ $util.maskUserId(list.reguserid) }}</span><span v-if="list.own == 'true'"> (작성자)</span>
                      </div>
                      <div class="comment-edit" v-if="list.own == 'true'">
                        <span @click="reviseCmt(index)">수정</span>
                        <span class="dp-bar h10"></span>
                        <span @click="deleteCmt(list.commentidx)">삭제</span>
                      </div>
                    </div>
                    <div class="comment-body">
                      <p v-if="!reviseComment[index]" class="comment-text">
                        {{ list.comment }}
                      </p>
                      <!-- 수정 -->
                      <div v-if="reviseComment[index]" :key="skey" class="comment-list-item">
                        <div class="comment-textarea">
                          <form>
                            <base-textarea
                              v-model="reviseCmtList[index]"
                              class="comment-reply__box__textarea"
                              placeholder="댓글을 입력해주세요."
                            ></base-textarea>
                          </form>
                        </div>

                        <div
                          class="comment-reply-btn-area d-flex justify-content-end"
                        >
                          <b-button
                            @click="closeCmt(index)"                          
                            class="dp-btn not-hover reply-cancel-btn"
                            variant="outline-gray-800"
                            squared
                          >
                            <span>취소</span>
                          </b-button>
                          <b-button
                            @click="goReviseComment(index, list.commentidx)"
                            class="dp-btn text-white reply-registration-btn"
                            variant="gray-800"
                            squared
                          >
                            <span>수정</span>
                          </b-button>
                        </div>                                        
                      </div>
                      
                    </div>
                    
                    <div class="comment-footer">
                      <b-button
                        @click="openReply(index)"
                        class="dp-btn btn-h32 not-hover comment-write"
                        variant="outline-gray-800 type02"
                        squared
                        pill
                      >
                        <span>답글쓰기</span>
                      </b-button>
                      <span class="comment-report" @click="repReport(list.commentidx)">신고하기</span>
                    </div>
                  </div>
                  <!-- 댓글 -->

                  <!-- 댓글 답변 달기 -->
                  <div :key="skey">
                    <div v-if="replyWrite[index]" class="comment-reply">
                      <div class="comment-textarea">
                        <form>
                          <base-textarea
                            v-model="reply[index]"
                            class="comment-reply__box__textarea"
                            placeholder="답글을 입력해주세요."
                          ></base-textarea>
                        </form>
                      </div>
                      <div
                        class="comment-reply-btn-area d-flex justify-content-between"
                      >
                        <div>
                        </div>
                        <div class="d-flex">
                          <b-button
                              @click="commentCancle(index)"
                              class="dp-btn not-hover reply-cancel-btn"
                              variant="outline-gray-800"
                              squared
                          >
                            <span>취소</span>
                          </b-button>
                          <b-button
                              @click="saveReply(index,list.commentidx)"
                              class="dp-btn text-white reply-registration-btn"
                              variant="gray-800"
                              squared
                          >
                            <span>등록</span>
                          </b-button>
                        </div>

                      </div>
                    </div>
                  </div>
                  <!-- // 댓글 답변 달기 -->

                  <!-- 대댓글 -->
                  <div v-if="!$util.isEmpty(list.reply)">
                  <div v-for="(item, index2) in list.reply" :key="index2" class="comment-list-item reply">
                    <div class="comment-header d-flex justify-content-between">
                      <div class="dp-caption comment-header-left">
                        <span class="comment-date">{{ item.regdate }}</span>
                        <span>{{ $util.maskUserId(item.reguserid) }}</span><span v-if="item.own == 'true'"> (작성자)</span>
                        <span class="comment-report" @click="repReport(item.commentidx)">신고하기</span>
                      </div>
                      <div
                        class="comment-edit dp-caption"
                        v-if="item.own == 'true'"
                      >
                        <span @click="reviseReply(index,index2)">수정</span>
                        <span class="dp-bar h10"></span>
                        <span @click="deleteReply(item.commentidx)">삭제</span>

                      </div>
                    </div>
                    <div class="comment-body">
                      <p v-if="!ReviseOpen[index2]" class="comment-text dp-p-sm">{{ item.comment }}</p>

                      <div v-if="ReviseOpen[index2]" :key="skey" class="comment-list-item">
                            <div class="comment-textarea">
                              <form>
                                <base-textarea
                                  v-model="reviseRlyList[index2]"
                                  class="comment-reply__box__textarea"
                                  placeholder="댓글을 입력해주세요."
                                ></base-textarea>
                              </form>
                            </div>
                            <div
                              class="comment-reply-btn-area d-flex justify-content-end"
                            >
                              <b-button
                                @click="reviseRlyClose(index,index2)"                              
                                class="dp-btn not-hover reply-cancel-btn"
                                variant="outline-gray-800"
                                squared
                              >
                                <span>취소</span>
                              </b-button>
                              <b-button @click="updateReply(index,index2,item.commentidx)"
                                class="dp-btn text-white reply-registration-btn"
                                variant="gray-800"
                                squared
                              >
                                <span>수정</span>
                              </b-button>
                          </div> 
                        </div>

                    </div>
                  </div>
                  </div>
                  <!-- // 대댓글 -->
                </li>
              </ul>
            </template>
          </div>
        </div>

        <hr class="dp-hr h1" v-if="!$util.isNull(Detail.brandidx) || Detail.iscomment === 'T'"/>

        <div class="d-flex justify-content-center">
            <base-pagination :currentPage="currentPage" :totalPage="totalPage" :listTotal="commentListTotal" :listCnt="perPage" @changePage="changePage"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="@views.pc/shop/promotion/Detail.js"/>

<style scoped>
  .promotion__product__section .promotion__product-area li {margin-bottom: 60px;}
</style>