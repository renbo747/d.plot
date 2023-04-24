<!-- By. Jaeho -->
<!-- !! 필수 값 !!

    넘겨준 listData 안에 sortnum(노출순서) 라는 이름으로 되있어야함

-->
<!-- Option

    keyName - 키 값 이름을 정할 수 있음 (위치 이동시 사용될 고유한 키값)
    isActiveSaveBtn - 저장 버튼 우뮤를 정할 수 있음 - 저장 버튼이 없다면(false), 이동할 때 마다 이벤트 버스로 콜백함(getModifySortNumArray)
    v-on:getModifySortNumArray - 변동된 리스트 array를 리턴함

-->
<!--
    부모에 goChangeSortNum 메서드 안에서 저장 성공시

    this.moveData.targetIdx = '';               // ** 중요
    this.moveData.isSuccess = false;            // ** 중요

    위 값을 초기화 해줘야함
-->
<template>
  <!-- 순서노출 -->
  <div class="btn-group left">
    <button type="button" class="btn black-line square"
            @click="onChangeArrayOrder(childrenMoveData.targetIdx, -1, 'A')"><i class="arr-up"></i></button>
    <button type="button" class="btn black-line square"
            @click="onChangeArrayOrder(childrenMoveData.targetIdx, 1, 'A')"><i class="arr-down"></i></button>
    <input type="number" class="ml3"
           oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
           style="width: 40px;" v-model="childrenMoveData.moveValue">
    <span>행</span>
    <select v-model="childrenMoveData.code">
      <option value="U">위로</option>
      <option value="D">아래로</option>
    </select>
    <button type="button" class="btn blue-line ml3"
            @click="onChangeArrayOrder(childrenMoveData.targetIdx, childrenMoveData.moveValue, 'B')">이동
    </button>
    <button type="button" class="btn blue-line ml3" @click="onChangeArraySave" v-if="isActiveSaveBtn">순서저장</button>
    <span class="txt-orange ml10"><i class="icon-alert"></i>한 번에 한 개의 행만 이동할 수 있습니다.</span>
  </div>
  <!-- /순서노출 -->
</template>


<script>
export default {
  name: "CommonArraySort",
  props: {
    listData: Array,
    moveData: Object,
    keyName: {type: String, default: 'idx'},                // 고유한 키값 이름
    isActiveSaveBtn: {type: Boolean, default: true}         // 저장 버튼 유무
  },
  data() {
    return {
      childrenMoveData: this.moveData,                        // 움직임 정보 데이터
      originList: [],                                         // 맨 처음 초기화된 리스트
    }
  },
  computed: {
    tableData() {                                             // 실제 내부 리스트 변경시 저장될 데이터
      return this.listData;
    },
  },
  methods: {

    /**
     * 노출순위 로직
     *
     * @param list 테이블 리스트
     * @param targetIdx 체크된 데이터 순번
     * @param moveValue 이동할 값
     * @param flag 화살표 이벤트 : A, 이동버튼 이벤트 : B
     */
    onChangeArrayOrder(targetIdx, moveValue, flag) {
      let tarIdx = targetIdx;
      // moveData의 targetIdx가 배열 && 크기가 1개 초과이면 return
      if (Array.isArray(this.moveData.targetIdx) && targetIdx.length > 1) {
        alert("한 번에 한 개의 행만 이동할 수 있습니다");
        return;
      } else if (Array.isArray(this.moveData.targetIdx)) {
        tarIdx = targetIdx[0];
      }

      // originList 초기화
      if (!this.childrenMoveData.isSuccess || // 저장 완료시
          (this.originList.length !== this.listData.length) // 서로 길이가 다를 경우 (삭제로 판단)
      ) {
        this.originList = JSON.parse(JSON.stringify(this.listData));
        this.childrenMoveData.isSuccess = true;
      }

      if (this.tableData.length < 0) return;               // 배열값이 없는 경우
      if (moveValue === '' || moveValue == null) return;   // 이동 값이 없을 경우
      if (tarIdx === '' || tarIdx == null) return;   // 체크된 값이 없을 경우

      // 이동버튼 이벤트 일 경우
      if (flag === 'B') {
        if (this.childrenMoveData.code === 'U') moveValue *= -1;
      }
      moveValue *= 1;
      let newPosition = tarIdx + moveValue;

      // 이동할 값이 리스트 길이 보다 작을 경우
      if (newPosition < 0) {
        newPosition = 0;
      } else if (newPosition >= this.tableData.length) { // 이동할 값이 리스트 길이 보다 작을 경우
        newPosition = this.tableData.length - 1;
      }

      // 옮길 대상
      let target = this.tableData[tarIdx];

      // 교체될 대상
      let move = this.tableData[newPosition];

      if(this.$util.isNull(target.sortnum) || this.$util.isNull(move.sortnum)) {
        alert("순서가 존재하는 행들만 순서를 변경할 수 있습니다.");
        return ;
      }

      // 노출 순서 교체
      let tempNum = target.sortnum;
      target.sortnum = move.sortnum;
      move.sortnum = tempNum;

      // 새로운 위치에 옮길 대상 추가
      this.tableData[tarIdx] = move;
      this.tableData[newPosition] = target;
      this.tableData.splice(); // 리스트 변동사항을 vue에 알려줌

      // 리스트 업데이트 (체크박스 위치, 리스트 )
      if (Array.isArray(this.moveData.targetIdx)) {
        this.childrenMoveData.targetIdx[0] = newPosition;
      } else {
        this.childrenMoveData.targetIdx = newPosition;
      }

      // 순서 저장 버튼이 없을 경우
      if (!this.isActiveSaveBtn) {
        this.onChangeArraySave();
      }
    },

    /**
     * 순서 저장 버튼
     * - 이벤트 버스로 변경된 정렬 순서 정보를 반환함
     */
    onChangeArraySave() {
      // originList 초기화
      if (!this.childrenMoveData.isSuccess) {
        this.originList = JSON.parse(JSON.stringify(this.listData));
        this.childrenMoveData.isSuccess = true;
      }

      let changeNumArray = [];

      // 수정된 리스트 찾기
      for (let i = 0; i < this.originList.length; i++) {
        let originIdx = this.originList[i][this.keyName];
        let changeIdx = this.tableData[i][this.keyName];

        // 순서가 같지 않다면
        if (originIdx !== changeIdx) {
          let temp = {
            idx: changeIdx,
            sortnum: this.tableData[i].sortnum,
            orgsortnum: this.originList[i].orgsortnum
          }
          changeNumArray.push(temp);
        }
      }

      this.$emit("getModifySortNumArray", changeNumArray);
    },
  },
}
</script>
