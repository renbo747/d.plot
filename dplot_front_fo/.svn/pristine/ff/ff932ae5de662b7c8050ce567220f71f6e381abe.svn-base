<template>
  <div class="aside-article mypage-message">
    <header class="aside-article__header">
      <div class="header-title">Message</div>
    </header>
    <div v-if="messageList.length > 0">
      <div class="mypage-message__container">
        <div
          class="message-section"
          v-for="(item, index) in messageList"
          :key="index"
        >
          <p
            class="message-date"
            :class="{ active: item.isRead }"
            v-if="
              index == 0
                ? true
                : messageList[index].regdate != messageList[index - 1].regdate
            "
          >
            {{ item.regdate }}
          </p>
          <div class="message-box">
            <div class="message-box__header">
              <p class="message-title mb-0">{{ item.msgtypenm }}</p>
              <i
                class="dp-icon icon-close-black md"
                @click="deleteMessage(item.msgidx)"
              ></i>
            </div>
            <div class="message-box__body" @click="goLink(item.linkurl)" style="cursor:pointer;">
              <p class="message-text mb-0" v-html="$util.toEscape(item.message)"></p>
            </div>
          </div>
        </div>
      </div>
      <base-pagination
        class="justify-content-center dp-mt-40"
        :currentPage="pagingData.currentPage"
        :listTotal="pagingData.listTotal"
        :listCnt="pagingData.listCnt"
        @changePage="changePage"
      />
    </div>
    <div v-if="isEnd && messageList.length <= 0">
      <div class="message__empty">
        <div class="empty__icon">
          <i class="empty-list-icon"></i>
        </div>
        <p class="empty__text">수신된 메시지가 없어요</p>
      </div>
    </div>
  </div>
</template>

<script src="./Index.js"></script>
