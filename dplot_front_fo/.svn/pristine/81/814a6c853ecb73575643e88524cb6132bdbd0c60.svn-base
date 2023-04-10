<template>
  <main class="mypage-message">
    <div
      class="message-section"
      v-for="(item, index) in messageList"
      :key="index"
    >
      <p
        class="message-date"
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
          <p class="message-title mb-0" :class="{ active: item.isRead }">{{ item.msgtypenm }}</p>
          <i
            class="dp-icon icon-close-black sm01"
            @click="deleteMessage(item.msgidx)"
          ></i>
        </div>
        <div class="message-box__body" @click="goLink(item.linkurl)">
          <p class="message-text mb-0" v-html="item.message"></p>
        </div>
      </div>
    </div>
    <infinite-loading
      :identifier="infiniteId"
      @infinite="getMessageList"
      spinner="circles"
    >
      <div slot="no-more"></div>
      <div slot="no-results">
        <div class="message__empty" v-if="isEnd && messageList.length <= 0">
          <div class="empty__icon">
            <i class="empty-list-icon"></i>
          </div>
          <p class="empty__text">수신된 메시지가 없어요</p>
        </div>
      </div>
    </infinite-loading>
  </main>
</template>

<script src="./Index.js">
</script>
