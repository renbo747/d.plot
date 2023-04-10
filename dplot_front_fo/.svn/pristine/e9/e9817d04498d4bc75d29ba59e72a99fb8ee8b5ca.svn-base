<template>
  <main class="cs cs-main">
    <div class="container">
      <!-- 검색 영역 -->
      <div class="search-area">
        <base-search v-model="searchKeyword" style="cursor: pointer" @isEnter="isEnter(searchKeyword)" @input="handleSearch" placeholder="궁금한 점을 검색해보세요" @search="searchClick" />
      </div>

      <!-- 자주 하는 질문 -->
      <section class="dp-panel">
        <header class="dp-panel__header">
          <h2 class="dp-panel__title">자주하는 질문</h2>
          <a style="cursor:pointer" class="cs-link" @click="goFaqPage()">
            <span>더보기</span>
            <i class="dp-icon icon-arrow-right gray"></i>
          </a>
        </header>

        <ul class="dp-accordion list-style-none">
          <li v-for="(list, index) in faqData" :key="index">
            <div class="accordion-item">
              <div class="accordion__header" v-b-toggle="`accordion-0${index}`" @click="faqHit(list.idx,index)">
                <span class="header__text" style="word-break: break-all;">[{{list.faqtypename}}] {{ list.subject }}</span>
                <i
                  class="dp-icon icon-arrow-down md"
                ></i>
              </div>
              <b-collapse
                :id="`accordion-0${index}`"
                accordion="gnb-accordion"
                tag="div"
              >
                <div class="accordion__body">
                  <div v-html="list.content"></div>
                </div>
              </b-collapse>
            </div>
          </li>
        </ul>
      </section>

      <!-- 고객센터 -->
      <section class="dp-panel cs-info">
        <div class="cs-info-box">
          <div class="info-box">
            <p class="info-title">고객센터</p>
            <a class="info-number atten-new" href="tel: 1666-3642">1666-3642</a>
          </div>

          <p class="info-operation">
            <span>운영시간 </span>월~금 10:00 ~ 16:00
          </p>
          <p class="info-operation">
            <span>점심시간 </span>12:00 ~ 13:00 <br/>※휴뮤 - 토,일 및 공휴일
          </p>
        </div>

        <div class="cs-info__btn-group">
          <b-button class="dp-btn" variant="outline-gray-800" squared @click="goToInquiry()">
            <span>나의 상담내역</span>
          </b-button>

          <b-button class="dp-btn" variant="gray-800" squared @click="goToWrite()">
            <span>1:1 문의하기</span>
          </b-button>
        </div>
      </section>

      <!-- 프리미엄 서비스
      <section class="dp-panel cs-main__premium">
        <header class="dp-panel__header">
          <h2 class="dp-panel__title">D.PLOT의 프리미엄 서비스</h2>
        </header>
      
        <div class="contents__service">
          <ul class="service__ul list-style-none">
            <li>
              <div class="service__item" v-b-modal.serviceIntroModal>
                <div class="service__circle">
                  <img
                    src="@/assets/common/icon/icon-document-black-34px.svg"
                    alt="아이콘"
                  />
                </div>
                <p class="service__caption">소개</p>
              </div>

              <b-modal
                id="serviceIntroModal"
                modal-class="dp-modal pop-over"
                hide-footer
                centered
              >
                <template #modal-header="{ cancel }">
                  <h5 class="modal-title">서비스 소개</h5>
                  <i class="modal-close" @click="cancel()">
                    <img
                      src="@/assets/common/icon/icon-close-modal-30px.svg"
                      alt="닫기"
                    />
                  </i>
                </template>

                <div class="dp-mb-0">
                  <p class="modal-text02 mb-0 word-break-all">
                    디플롯은 특별한 혹은 소소한, 진중한 때론 재미난
                    브랜드와 제품 이야기가 있는 프리미엄 라이프스타일 쇼핑
                    플랫폼입니다.<br />
                    이야기 즉 플롯은 브랜드와 디자이너의 철학, 제품의
                    쓸모와 아름다움, 나아가 사용자의 삶 속에서 함께
                    만들어가는 이야기까지 아우르죠.<br />
                    모든 사람이 제품 이면의 이야기를 궁금해하는 것은
                    아니겠지만 디플롯은 단지 숫자로만 제품의 가치를 논하고
                    싶지 않았어요.<br />
                    이 제품이 왜 좋은 지, 누구를 위한 것인지, 어떻게
                    사용해야 하는 지에 대한 구체적이고 친절한 정보를
                    전달해야겠다고 생각했어요.<br />
                    다양한 제품의 플롯을 탐색하며 자신의 취향을 발견하고,
                    쇼핑을 통해 그 개성을 실현시키길 바라요.<br />
                    디플롯이 들려주는 형형색색 갖가지 플롯으로 지식과
                    감성을, 그리고 나만의 시간과 공간을 채워가는 즐거움을
                    경험해보세요
                  </p>
                </div>
              </b-modal>

            </li>
            <li>
              <div class="service__item" v-b-modal.asModal>
                <div class="service__circle">
                  <img
                    src="@/assets/common/icon/icon-setting-black-34px.svg"
                    alt="아이콘"
                  />
                </div>
                <p class="service__caption">A/S</p>
              </div>

              <b-modal
                id="asModal"
                modal-class="dp-modal pop-over"
                hide-footer
                centered
              >
                <template #modal-header="{ cancel }">
                  <h5 class="modal-title">A/S신청</h5>
                  <i class="modal-close" @click="cancel()">
                    <img
                      src="@/assets/common/icon/icon-close-modal-30px.svg"
                      alt="닫기"
                    />
                  </i>
                </template>

                <div>
                  <p class="modal-text02 mb-0">
                    D.PLOT에서 구매한 상품에 문제가 생겼나요?<br />
                    D.PLOT에서는 해당 상품의 AS 가능 여부를 확인하고 빠르고
                    정확한 AS 처리를 돕는 'AS전용 문의 서비스'를 운영합니다.<br />
                    <br />
                    마이페이지에 위치한 'AS전용 문의 서비스'를 통해 관련 내용을
                    접수하면 담당자 검토 후 신속하게 답변을 해드리겠습니다.
                  </p>
                </div>
              </b-modal>

            </li>
            <li>
              <div class="service__item" v-b-modal.managerServiceModal>
                <div class="service__circle">
                  <img
                    src="@/assets/common/icon/icon-card-black-34px.svg"
                    alt="아이콘"
                  />
                </div>
                <p class="service__caption">정품보증</p>
              </div>

              <b-modal
                id="managerServiceModal"
                modal-class="dp-modal pop-over manager-service"
                hide-footer
                centered
              >
                <template #modal-header="{ cancel }">
                  <h5 class="modal-title">전담매니저</h5>
                  <i class="modal-close" @click="cancel()">
                    <img
                      src="@/assets/common/icon/icon-close-modal-30px.svg"
                      alt="닫기"
                    />
                  </i>
                </template>

                <div class="dp-mb-20">
                  <p class="modal-text02 mb-0">
                    D.PLOT에서 구매한 상품에 문제가 생겼나요?<br />
                    D.PLOT에서는 해당 상품의 AS 가능 여부를 확인하고 빠르고
                    정확한 AS 처리를 돕는 'AS전용 문의 서비스'를 운영합니다.<br />
                    <br />
                    마이페이지에 위치한 'AS전용 문의 서비스'를 통해 관련 내용을
                    접수하면 담당자 검토 후 신속하게 답변을 해드리겠습니다.
                  </p>
                </div>
              </b-modal>
            </li>
          </ul>
        </div>
      </section>
      -->

      <!-- 공지사항 -->
      <section class="dp-panel">
        <header class="dp-panel__header">
          <h2 class="dp-panel__title">공지사항</h2>

          <router-link class="cs-link" to="/cs/notice">
            <span>더보기</span>
            <i class="dp-icon icon-arrow-right gray"></i>
          </router-link>
        </header>
        <ul class="cs-notice__ul list-style-none">
          <li v-for="(list, index) in noticeData" :key="index" @click="goDetail(list.idx)">
            <a style="cursor:pointer" class="notice-link">
              <span v-if="list.isread === 'true'" class="notice-title new">{{list.subject}}</span>
              <span v-else class="notice-title" v-html="list.subject"></span>
              <span class="notice-date" v-html="list.poststtime"></span>
            </a>
          </li>
        </ul>
      </section>
    </div>
  </main>
</template>

<script src="@views.mobile/cs/Index.js"/>
