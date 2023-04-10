<template>
  <div>
    <div class="visual">
      <h1>배/송/정/보/등/록</h1>
      <p>성공적인 비즈니스 파트너! D.PLOT과 함께하세요!</p>
    </div>
    <!-- 컨텐츠 영역 -->
    <div class="join-content">
      <div class="caption-group clearfix">
        <div class="total-group fl">
          <h2 class="title">배송 템플릿</h2>
          <div>배송템플릿은 1건 이상 반드 시 등록해야 프로그램 사용이 가능합니다.</div>
        </div>
        <div class="btn-group fr">
          <button type="button" class="btn blue-line" @click="openNewDelivTempPopup">배송템플릿 신규등록</button>
        </div>
      </div>
      <table cellpadding="0" cellspacing="0" class="data-tb align-c">
        <caption>배송템플릿</caption>
        <colgroup>
          <col width="4%" /><!-- No -->
          <col width="8%" /><!-- 관리코드 -->
          <col width="%" /><!-- 배송정보명 -->
          <col width="7%" /><!-- 배송방법 -->
          <col width="9%" /><!-- 택배사 -->
          <col width="15%" /><!-- 배송비 -->
          <col width="8%" /><!-- 배송가능지역 -->
          <col width="12%" /><!-- 교환 배송비 -->
          <col width="12%" /><!-- 반품 배송비 -->
          <col width="6%" /><!-- 사용여부 -->
        </colgroup>
        <thead>
        <tr>
          <th>No</th>
          <th>관리코드</th>
          <th>배송정보명</th>
          <th>배송방법</th>
          <th>택배사</th>
          <th>배송비</th>
          <th>배송가능지역</th>
          <th>교환 배송비</th>
          <th>반품 배송비</th>
          <th>사용여부</th>
        </tr>
        </thead>
        <tbody v-if="delivTempList.length > 0">
        <tr v-for="(row, index) in delivTempList" v-bind:key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ row.delividx }}</td>
          <td class="left"><a class="link" @click="openDelivTempDetailPopup(row)">{{ row.delivbname }}</a></td>
          <td>{{ row.delivtypename }}</td>
          <td>{{ row.logistypename }}</td>
          <td class="left">
            {{ row.delivfaretypename }}
            {{ row.delivfaretype==$store.getters['ADMIN'].DELIV_FARE_DCT002 ? row.delivfare + '원' : '' }}
            {{ row.delivfaretype==$store.getters['ADMIN'].DELIV_FARE_DCT003 ?
              row.delivfare + '원 ('+ row.delivfreefare + '원 이상 무료)' : '' }}
          </td>
          <td>{{ row.nationdelivconts }}</td>
          <td>{{ row.exowfare }}원 (편도)</td>
          <td>{{ row.rfowfare }}원 (편도)</td>
          <td>{{ row.useyn }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="10">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <div class="bottom-group">
        <div class="btn-group">
          <button type="button" class="btn blue" @click="onConfirm">확인</button>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->
  </div>
</template>

<script>
import DelivTempDetailPopup from "@views.admin/goods/popup/DelivTempDetailPopup.vue";

export default {
  name: "PartnershipDelivTemplate",
  data() {
    return {
      session : null,
      delivTempList : []
    }
  },
  mounted() {
    this.session = this.$route.params;
    this.searchDelivTempList();
  },
  methods : {
    onConfirm(){
      if(this.delivTempList.length > 0){
        this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.PARTNER_USER, this.session);
        this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION, this.session);
        this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.ADMIN_USER);
        this.$router.push({name : "partners.main"});
      } else {
        this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.PARTNER_USER);
        this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION);
        this.$router.push({name : "partners.login"});
      }
    },
    searchDelivTempList(){
      let params = { dealerno: this.session.no };
      this.$http.post('/admin/goods/regist/delivtemp/list', params).then(result => {
        this.delivTempList = result.data.list;
      }).catch(error => {
        this.$util.debug(error);
      });
    },
    // 모달 팝업 SHOW
    openDelivTempPopup: function(params) {
      this.$modal.show(
          DelivTempDetailPopup, {
            'params': params,
            'callbackFn': () => {
              this.searchDelivTempList();
            }
          }, {
            name: "delivTempDetail"
          })
    },
    // 배송템플릿 신규등록 팝업 오픈
    openNewDelivTempPopup: function() {
      this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.PARTNER_USER, this.session);
      this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION, this.session);

      let params = {
        type: 'NEW',
        delividx: '',
        dealerno: this.session.no
      }
      this.openDelivTempPopup(params);
    },
    openDelivTempDetailPopup: function(obj) {
      let params = {
        type: 'DETAIL',
        delividx: obj.delividx,
        dealerno: this.session.no
      }
      this.openDelivTempPopup(params);
    }
  }
}
</script>

<style scoped src="@assets.admin/css/partnership.css"></style>