<template>
  <div class="image-upload" :class="{ pc: !isMobile }">
    <div class="image-upload__box">
      <label for="image-input" class="image-upload__label">
        <input
          id="image-input"
          class="image-upload__input"
          type="file"
          ref="addFile"
          accept="image/jpeg, image/png, video/mp4"
          @change="handleFileUpload('addFile', $event.target)"
          hidden
        />
        <span class="image-input__number text-gray-700"
          >{{ fileLen }} / 10</span
        >
      </label>
    </div>
    <div class="image-upload__list">
      <swiper ref="swiper" :options="fileSwiperOption">
        <template v-for="(list, index) in filelist" >
        <swiper-slide :key="index"  v-show="list.status !== 'D'">
          <div class="upload__item" v-if="list.status === 'I'">
            <figure :class="{ play: list.type === 'video/mp4' }" v-show="list.type === 'video/mp4'">
              <video :id="'snapshot_' + (index + 1)" payload="metadata" :src="videoUrl" style="display:none"/>
              <canvas :id="'canvas_' + (index + 1)" ></canvas>
            </figure>
            <figure :class="{ play: list.type === 'video/mp4' }" v-show="list.type !== 'video/mp4'">
              <img :src="list.url"/>
            </figure>
            <i class="upload__close-icon" @click="removeFile(list.id)"></i>
          </div>
          <div class="upload__item" v-if="list.status === 'N'">
            <figure :class="{ play: list.filetype === 'FLT002' }">
              <img :src="list.url"/>
            </figure>
            <i class="upload__close-icon" @click="removeFile(list.id)"></i>
          </div>
        </swiper-slide>
        </template>
      </swiper>
    </div>
  </div>
</template>

<script>
import { swiper, swiperSlide } from "vue-awesome-swiper";
import 'video.js/dist/video-js.css'
import 'vue-video-player/src/custom-theme.css'

export default {
  components: {
    swiper,
    swiperSlide,
  },
  props: {
    isMobile: {
      type: Boolean,
      default: true,
    },
    files: {
      type:Array
    }
  },
  data() {
    return {
      fileSwiperOption: {
        slidesPerView: "auto",
        spaceBetween: 10,
        observeParents: true,
        observer: true
      },
      filelist: [],
      videoUrl : "",
      fileLen:0
    }
  },
  methods: {
    handleFileUpload(fileTypeKey, target) {
      if (fileTypeKey.indexOf("addFile") > -1) {
        let files = target.files;
        let fileType = ["image/png", "image/jpeg", "image/png"];
        let movfileType = ["video/mp4", "video/quicktime", "video/mov"];
        let list = this.filelist.filter((x) => x.status !== "D");
        if (this.$util.isNull(files) || files.length <= 0) {
          return false;
        }
        //파일업로드 10개까지만
        if (list.length >= 10) {
          this.$eventBus.$emit("alert", "알림", "10개까지 등록 가능합니다.");
          return false;
        }
        //동영상 파일 1개만 등록 제한
        if (movfileType.includes(files[0].type) ) {
          let movCnt = 0;
          for (let i = 0; i < list.length; i++) {
            if (movfileType.includes(list[i].type) || list[i].filetype == "FLT002") {
              movCnt += 1;
            }
          }
          this.$util.debug('movCnt::'+movCnt);
          if (movCnt > 0) {
            this.$eventBus.$emit("alert", "알림", "동영상 파일은 1개만 등록가능합니다.");
            return false;
          }
        }
        
        if (!(fileType.includes(files[0].type) ||movfileType.includes(files[0].type))) {
          this.$eventBus.$emit("alert", "알림", "jpg, jpeg, png, mp4, mov파일만 첨부 가능합니다.");
          this.$refs.addFile.value = null;
          return false;
        }
        //이미지 파일 크기 제한
        if (fileType.includes(files[0].type) && files[0].size > 10485760) {
          this.$eventBus.$emit("alert", "알림", "파일 최대 크기는 10MB를 초과 할 수 없습니다.");
          this.$refs.addFile.value = null;
          return false;
        }
        //동영상 파일 크기 제한
        if (movfileType.includes(files[0].type) && files[0].size > 52428800) {
          this.$eventBus.$emit("alert", "알림", "파일 최대 크기는 50MB를 초과 할 수 없습니다.");
          this.$refs.addFile.value = null;
          return false;
        }

        let fileObj = {
          file:files[0],
          status: "I",
          id:this.filelist.length
        }

        fileObj.url = URL.createObjectURL(fileObj.file);
        fileObj.type = files[0].type;
        this.filelist.push(fileObj);
        /*******************************
         * 동영상 => 썸네일 CANVAS 그리기
         ******************************/
        if (fileObj.type == "video/mp4") {
            this.$nextTick(()=>{
              const snapshot = document.getElementById('snapshot_' + this.filelist.length);
              snapshot.src = fileObj.url + "#t=15";
              const canvas = document.getElementById('canvas_' + this.filelist.length);
              
              snapshot.addEventListener('loadeddata', () => {
                  let w = 100;
                  let h = 100;
                  canvas.width = 100;
                  canvas.height = 100;
                  if(!this.isMobile) {
                    w = 188;
                    h = 188;
                    canvas.width = 188;
                    canvas.height = 188;
                  }
                  const context = canvas.getContext('2d');
                  context.clearRect(0, 0, canvas.width, canvas.height);
                  //context.fillRect(0, 0, w, h);
                  context.drawImage(snapshot, 0, 0, w, h);
              });
            });
        }         
        //초기화
        this.$refs.addFile.value = '';
      }
    },
    /***********************************
     * 파일삭제 처리
     ***********************************/
    removeFile(id) {
      for (let index = 0; index < this.filelist.length; index++) {
        if (this.filelist[index].id == id) {
          if (this.filelist[index].status == "I") {
            this.filelist.splice(index, 1);
          }else {
            this.filelist[index].status = "D";
          }
        }
      }
      this.fileLen =  this.filelist.filter((x) => (x.status !== "D")).length;
    },
    /**************************
     * 부모에서 전달받은 파일목록 매핑
     ****************************/
    bindFile(files){
      this.$util.debug("bindFile....");
      this.$util.debug(files);

      //저장된 파일리스트에 id값 부여
      for (let index = 0; index < files.length; index++) {
        files[index].id = 'files'+index;
        if (files[index].filetype == "FLT002") {
          let thumbUrl = this.$util.changeFileType(files[index].fullpath,'.jpg');
          files[index].url = thumbUrl;
        }else {
          files[index].url = files[index].fullpath;
        }
      }
      this.filelist = files;
    },
    /*******************************
     * 부모에 파일 목록 전달
     *******************************/
    emitFileList(){
      let files = []; //저장할 파일 목록
      let deletelist = []; //삭제할 파일 목록
      for (let index = 0; index < this.filelist.length; index++) {
        if (this.filelist[index].status !== "D") {
          files.push({key:'filelist'+index, file:this.filelist[index].file})
        }else{
          deletelist.push(this.filelist[index].idx);
        }
      }
      this.$emit("change", files, deletelist);
    }
  },
  mounted(){
    this.$util.debug("imageupload 접근...");
  },
  watch: {
    filelist(val) {
      this.$util.debug("aaaa");
      this.fileLen = val.filter((x) => (x.status !== "D")).length;
    }
  }
};
</script>
