<template>
    <!-- 송장일괄등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 720px;">
            <div class="pop-header">
                <h2>송장일괄등록</h2>
                <button type="button" class="pop-close" @click="closePopup"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">
                    <p class="mg0">택배사 송장번호를 일괄적으로 변경 적용할 수 있습니다.</p>
                    <button type="button" class="btn big green mt20" @click="downloadExcelTemplate">엑셀양식 다운로드</button>
                    <div class="mt10">
                        <input type="file" ref="excelFile" @change="handleFileUpload" hidden
                            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
                        <input type="text" ref="excelFileName" readonly>
                        <button type="button" class="btn blue-line ml3" @click="fileAttach">파일찾기</button>
                        <button type="button" class="btn blue-line" @click="readExcelFile">일괄등록</button>
                    </div>
                </div>
                <ul class="dot-list mt20">
                    <li>파일 등록은 반드시 “xlsx”로 저장을 하셔야 합니다.</li>
                    <li>업로드 할 엑셀파일에는 주문번호, 상품코드, 단품코드, 상품순번, 택배사명, 운송장번호 항목은 필수로 있어야 합니다.</li>
                    <li>이미 등록된 송장번호가 있어도 새로 등록한 택배사/송장번호로 업데이트 됩니다.</li>
                    <li>택배배송의 경우 송장번호는 하이픈(-)없이 입력해 주세요.</li>
                    <li>한번에 등록 가능한 개수는 1,000개 이며 최대 2MB까지 등록 가능합니다.</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- /송장일괄등록 팝업 -->
</template>

<script>
import XLSX from 'xlsx';

export default {
    name: 'AddAllInvoicePopup',
    props: ['activeOrdgdidx'],
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
    },
    data() {
        return {
            isPartner: false,
            user: {},
            excelFile: '',  // 파일
        }
    },
    methods: {
        // 엑셀양식다운로드
        downloadExcelTemplate: function() {
            let params = { filename: 'InvoiceTemplate.xlsx' }   // 서버에 저장되어있는 파일명
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/common/excel/download', params, config);
        },
        // 첨부파일(탐색기 열기)
        fileAttach: function() {
            this.$refs.excelFile.click();
        },
        // 가져온 파일 세팅
        handleFileUpload: function() {
            // 엑셀올리기
            let file = this.$refs.excelFile;
            if (this.$util.isNull(file.files[0])) {
                return;
            }

            let fileType = ['application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'];
            if(!fileType.includes(file.files[0].type)){
                alert('엑셀 파일만 첨부 가능합니다.');
                file.value = null;
                return false;
            }
            
            if(file.files[0].size > 2097152){
                alert('파일 최대 크기는 2MB를 초과 할 수 없습니다.');
                file.value = null;
                return false;
            }
            this.excelFile = file.files[0];
            this.$refs.excelFileName.value = file.files[0].name;
        },
        // 엑셀파일 읽기
        readExcelFile: function() {
            let file = this.excelFile;
            if (this.$util.isNull(file)) {
                alert('파일을 선택해주세요');
                return;
            }

            let headerInfo = ['ordno', 'goodscode', 'optioncode', 'goodsturn', 'logisname', 'logistype', 'invoiceno', 'delivcnt'];
            let reader = new FileReader();
            let tmpResult = {};
            reader.onload = () => {
                let data = reader.result;
                let workbook = XLSX.read(data, {type: 'array'});
                workbook.SheetNames.forEach(sheetName => {
                    if (sheetName === 'Sheet1') {
                        const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], {header: headerInfo, defval: ''});
                        if (roa.length) tmpResult = roa ;
                    }
                });
                this.readExcelData(tmpResult);
            };
            reader.readAsArrayBuffer(file);
        },
        // 엑셀일괄 업로드
        readExcelData(excelData) {
            this.$util.debug(excelData);
            if (excelData.length == 0) {
                alert('엑셀파일에 데이터가 존재하지 않습니다.');
                return;
            }
            if (excelData.length > 1001) {
                alert('한 번에 1,000개 까지만 등록 가능합니다.');
                return;
            }
            let requiredHeader = ['ordno', 'goodscode', 'optioncode', 'goodsturn', 'logisname', 'logistype', 'invoiceno', 'delivcnt'];
            let requiredHeaderName = ['주문번호', '상품코드', '단품코드', '상품순번', '택배사명', '택배사코드', '운송장번호', '배송수량'];
            let orderdelivList = [];

            // 유효성 체크
            for (let i=1; i<excelData.length; i++) {
                let item = excelData[i];
                let keyset = Object.keys(item);
                // 필수입력항목 체크
                for (let j=0; j<keyset.length; j++) {
                    let key = keyset[j];
                    if (requiredHeader.indexOf(key)>-1 && this.$util.isNull(item[key])) {
                        alert("필수입력항목(" + requiredHeaderName[j] + ")이 입력되지 않았습니다. 확인 후 진행해주세요.");
                        this.$refs.excelFile.value = '';
                        this.$refs.excelFileName.value = '';
                        this.excelFile = null;
                        return;
                    }
                }
                // 배송수량 필수입력 체크
                if (item.delivcnt === 0) {
                    alert('배송수량은 0이상 입력해야 합니다.');
                    return;
                }
                // 운송장번호 하이픈 체크
                if (item.invoiceno.indexOf('-') > -1) {
                    alert('운송장번호는 하이픈(-)없이 입력해 주세요.');
                    return;
                }

                orderdelivList.push(item);
            }

            if (orderdelivList.length === 0) {
                alert("엑셀파일에 입력된 정보가 양식과 일치하지 않습니다. 확인후 진행해주세요.");
                return;
            }
            if (confirm('일괄등록 하시겠습니까?')) {
                let params = { orderdelivList: orderdelivList };
                if (this.isPartner) {
                    params.dealerno = this.user.no;
                }
                this.$http.post('/admin/order/manage/invoice/saveall', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == 200) {
                            alert('일괄등록이 완료되었습니다.');
                            this.$emit('closePopup', true);
                        } else {
                            this.$refs.excelFile.value = '';
                            this.$refs.excelFileName.value = '';
                            this.excelFile = null;
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 팝업닫기
        closePopup: function() {
            this.$emit('closePopup');
        },
    }
}
</script>