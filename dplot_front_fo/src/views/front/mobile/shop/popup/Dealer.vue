<template>
  <!-- 판매자 정보 Modal -->
    <b-modal
        id="sellerInfoModal"
        modal-class="dp-modal pop-over"
        hide-footer
        centered
    >
        <template #modal-header="{ cancel }">
        <h5 class="modal-title">판매자 정보</h5>
        <i class="modal-close" @click="cancel()">
            <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
            />
        </i>
        </template>

        <div>
        <table class="dp-table">
            <colgroup>
            <col width="120" />
            <col width="*" />
            </colgroup>
            <tbody>
            <tr>
                <th>상호명</th>
                <td>{{param.compname}}</td>
            </tr>
            <tr>
                <th>대표자</th>
                <td>{{param.reprename}}</td>
            </tr>
            <tr>
                <th>사업자등록번호</th>
                <td>{{param.bizno}}</td>
            </tr>
            <tr>
                <th>연락처</th>
                <td>{{param.tel}}</td>
            </tr>
            <tr>
                <th>E-mail</th>
                <td>{{param.email}}</td>
            </tr>
            <!-- <tr>
                <th>Fax</th>
                <td>{{param.fax}}</td>
            </tr> -->
            </tbody>
        </table>
        </div>
    </b-modal>
    <!-- // 판매자 정보 Modal -->
</template>

<script>
export default {
    props :{
        param : Object
    }
}
</script>

<style>

</style>