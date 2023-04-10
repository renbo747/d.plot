<template>
  <div>
    <div class="contents-review__board-list">
      <!-- <p class="dp-title02 text-black font-weight-500 mb-0">
        총 {{ pagingData.listTotal }}건
      </p> -->
      <div class="board-list__header" v-if="searchData.isfile !== 'reviewall' || (searchData.isfile === 'reviewall' && reviewList.length > 0)">
        <ul class="dp-sort__ul list-style-none no-scrollbar">
          <li v-for="(list, index) in reviewType" :key="index">
            <label :for="list.id" class="dp-sort__label">
              <input
                type="radio"
                :id="list.id"
                name="review"
                v-model="searchData.isfile"
                :value="list.id"
                :ref="list.id"
                hidden
              />
              <span class="dp-sort__badge dp-p font-weight-400">{{
                list.label
              }}</span>
            </label>
          </li>
        </ul>

        <div class="list-select-box mr-0">
          <base-dropdown
            class="type02"
            :placeholder="nowLabel"
            :options="sampleSelectOptions"
            v-model="searchData.order"
          />
        </div>
      </div>

      <div class="board-list__body">
        <template v-if="reviewList.length === 0">
          <p class="text-center dp-p-sm">첫 리뷰를 작성해주세요.</p>
        </template>
        <template v-else>
          <ul class="board-list__ul list-style-none no-scrollbar">
            <li v-for="(list, index) in reviewList" :key="index">
              <div class="review-list-item">
                <div class="review-header">
                  <div class="d-flex justify-content-between align-items-start">
                    <div>
                      <p class="review-content-date dp-p text-gray-700 mb-0">
                        {{ list.regdate }}
                        <span
                          class="
                            review-content-nickname
                            dp-p
                            text-gray-700
                            dp-ml-10
                          "
                          >{{ $util.maskUserId(list.reguserid) }}</span
                        >
                      </p>
                      <p
                        class="
                          review-content-title
                          mb-0
                          dp-title02
                          font-weight-400
                          text-gray-700
                        "
                      >
                        {{ list.goodsname }} {{ list.optionnm }}
                      </p>
                      <div
                        class="
                          review-content-grade
                          d-flex
                          align-items-center
                          grade
                        "
                      >
                        <i class="star-icon"></i>
                        <p
                          class="
                            mb-0
                            text-align-center
                            atten-new
                            dp-title02
                            text-black
                            font-weight-700
                          "
                        >
                          {{ list.totpoint }}
                        </p>
                      </div>
                    </div>
                    <div
                      class="text-right d-flex"
                      v-if="list.ismyreview == 'T'"
                    >
                      <i v-if="list.grade" class="dp-icon md badge-icon"></i>
                      <div class="review-edit">
                        <span
                          class="dp-p text-gray-800"
                          @click="goToReviewWrite(list)"
                          >수정</span
                        >
                        <span class="dp-bar"></span>
                        <span
                          class="dp-p text-gray-800"
                          @click="deleteReview(list)"
                          >삭제</span
                        >
                      </div>
                    </div>
                  </div>
                </div>
                <div class="review-body d-flex text-gray-700 dp-mb-10">
                  <p
                    class="review-content mb-0"
                    :class="{ 'with-photo': list.files.length > 0 }"
                    v-html="list.content"
                    style="width: calc(100% - 295px)"
                  ></p>
                  <div
                    v-if="list.files.length > 0"
                    class="review-img-content"
                    @click="
                      openModal('photoReviewModal', {
                        photoList: reviewFileTopList,
                        reviewidx: list.reviewidx,
                      })
                    "
                  >
                    <!-- <img
                      :src="
                        list.files[0].filetype == 'FLT001'
                          ? list.files[0].fullpath
                          : $util.changeFileType(list.files[0].fullpath, '.jpg')
                      "
                      width="190"
                      height="190"
                      @click="
                        openModal('photoReviewModal', {
                          photoList: reviewFileTopList,
                          reviewidx: list.reviewidx,
                        })
                      "
                    />
                    <i class="dp-icon icon-video-play xl" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" v-if="list.files[0].filetype == 'FLT002'"></i> -->
                    <review-thumbnail
                      :file="list.files[0]"
                      :imgCount="list.files.length"
                      :size="170"
                    />
                  </div>
                </div>
                <div
                  class="
                    review-footer
                    d-flex
                    align-items-center
                    justify-content-end
                  "
                >
                  <base-like
                    :is-active="list.ismyhelp == 'T' ? true : false"
                    :value="list.goodcnt"
                    :reviewidx="list.reviewidx"
                  />
                  <p
                    class="mb-0 text-gray-800 review-report cursor"
                    @click="
                      openModal('postReportModal', {
                        reviewidx: list.reviewidx,
                        ismyreview: list.ismyreview,
                      })
                    "
                    style="cursor: pointer"
                  >
                    신고하기
                  </p>
                </div>
              </div>
            </li>
          </ul>
        </template>
      </div>
    </div>
    <base-pagination
      class="detail-pagination"
      :currentPage="pagingData.currentPage"
      :listTotal="pagingData.listTotal"
      :listCnt="pagingData.listCnt"
      @changePage="changePage"
    />
  </div>
</template>

<script>
import PostReport from "@views.mobile/shop/popup/PostReport.vue";
import PhotoReview from "@views.mobile/shop/popup/PhotoReview.vue";
export default {
  props: {
    reviewFileTopList: {
      type: Array,
    },
  },
  data() {
    return {
      reviewType: [
        {
          id: "reviewall",
          label: "전체",
        },
        {
          id: "T",
          label: "포토 & 동영상",
        },
        {
          id: "F",
          label: "텍스트",
        },
      ],
      sampleSelectOptions: [
        {
          label: "좋아요순",
          value: "goodcnt",
        },
        {
          label: "최신순",
          value: "new",
        },
        {
          label: "평점높은순",
          value: "pointtop",
        },
        {
          label: "평점낮은순",
          value: "pointlow",
        },
      ],
      nowLabel: "좋아요순",
      reviewData: [
        {
          id: "01",
          date: "2021.11.04",
          nickname: "abc***",
          grade: true,
          title: " 베오사운드 A1 CLOT_옵션1",
          rate: "4.3",
          text: "이런 제품은 정말 좋아요. 처음이야 완전 마음에 들어요. 이럴 수 없네요. 집안분위기가 확 살아요. 이런 제품은 정말 좋아요. 처음이야 완전 마음에 이럴 수 없네요. 집안분위기가 확 살아요. 이런 제품은 정말 좋아요. 처음이야 완전 마음에",
          img: "",
          like: 87,
          isLike: false,
        },
        {
          id: "02",
          date: "2021.11.04",
          nickname: "abc***",
          grade: false,
          title: " 베오사운드 A1 CLOT_옵션1",
          rate: "4.3",
          text: "이런 제품은 정말 좋아요. 처음이야 완전 마음에 들어요. 이럴 수 없네요. 집안분위기가 확 살아요. 이런 제품은 정말 좋아요. 처음이야 완전 마음에 이럴 수 없네요. 집안분위기가 확 살아요. 이런 제품은 정말 좋아요. 처음이야 완전 마음에",
          img: "https://fakeimg.pl/100/",
          like: 100,
          isLike: true,
        },
      ],
      reviewavg: 0,
      totalcnt: 0,
      reviewList: [],
      reviewFileList: [],
      searchData: {
        issearch: "T",
        isfile: "reviewall",
        order: "goodcnt",
        goodscode: this.$route.params.pid,
      },
      pagingData: {
        currentPage: 1, // 현재 페이지
        listTotal: 0, // 조회목록 전체 수
        listCnt: 2, // 한페이지에 노출할 목록수
      },
    };
  },
  methods: {
    /*********************
     * 페이징 데이터 초기화
     * *******************/
    initPagingData() {
      this.pagingData = {
        currentPage: 1, // 현재 페이지
        listTotal: 0, // 조회목록 전체 수
        listCnt: 10, // 한페이지에 노출할 목록수
      };
      this.reviewList = [];
      this.getReviewList();
    },
    /*********************
     * 리뷰 목록 조회
     *********************/
    getReviewList() {
      let param = Object.assign(this.pagingData, this.searchData);
      this.$http.post("/review/list", param).then((result) => {
        if (result.statusCode == 200) {
          this.$util.debug("리뷰 목록 조회");
          this.reviewList = result.data.reviewlist;
          this.pagingData.listTotal = this.$util.isNull(result.data.listtotal)
            ? 0
            : result.data.listtotal;
        }
      });
    },
    /****************************
     * 모달 오픈
     ***************************/
    openModal(modalId, param) {
      this.$util.debug("모달");
      if (modalId == "postReportModal") {
        if (!this.$store.state.isLogin) {
          this.$eventBus.$emit(
            "confirm",
            "로그인",
            "로그인 하시겠습니까?",
            () => {
              this.$storage.setSessionStorage("redirectPath", {
                path: this.$route.path,
                query: this.$route.query,
              });
              this.$router.push({ name: "member-login" });
            }
          );
          return;
        }
        this.$eventBus.$emit("showModal", PostReport, modalId, param);
      } else if (modalId == "photoReviewModal") {
        this.$eventBus.$emit("showModal", PhotoReview, modalId, param);
      }
    },
    /*****************************
     * 자식으로 부터
     ****************************/
    changePage(page) {
      this.pagingData.currentPage = page;
      this.getReviewList();
    },
    /****************************
     * 리뷰 삭제
     ***************************/
    deleteReview(list) {
      if (list.isdeadline == "T") {
        this.$eventBus.$emit(
          "alert",
          "알림",
          "리뷰 삭제는 작성후 3일이내에 가능합니다."
        );
        return;
      }
      if (list.isbest == "T") {
        this.$eventBus.$emit(
          "alert",
          "알림",
          "베스트에 선정된 리뷰는 삭제할수 없습니다."
        );
        return;
      }

      let param = {
        reviewidx: list.reviewidx,
        regdate: list.regdatefull,
      };

      this.$eventBus.$emit( "confirm", "확인",  "리뷰를 삭제하시겠습니까?", () => {
          this.$http.post("/review/delete", param).then((result) => {
            if (result.statusCode == 200) {
              this.$eventBus.$emit(
                "alert",
                "알림",
                "리뷰를 삭제하였습니다.",
                () => {
                  this.initPagingData();
                }
              );
            } else {
              this.$eventBus.$emit("alert", "알림", result.message);
            }
          });
        }
      );
    },
    goToReviewWrite(list) {
      this.$util.debug(JSON.stringify(list));
      this.$router.push({
        name: "mypage-my-review-write",
        query: { orderidx: list.orderidx, ordgdidx: list.ordgdidx },
      });
    },
  },
  mounted() {
    this.$util.debug("reviewListPC");
    this.initPagingData();
  },
  watch: {
    "searchData.isfile"(value) {
      this.$util.debug("searcData 값 변경 감지isfile...");
      this.initPagingData();
    },
    "searchData.order"(value) {
      this.$util.debug("searcData 값 변경 감지order...");
      let item = this.sampleSelectOptions.filter(
        (item) => item.value === value
      );
      this.nowLabel = item[0].label;
      this.initPagingData();
    },
  },
};
</script>
