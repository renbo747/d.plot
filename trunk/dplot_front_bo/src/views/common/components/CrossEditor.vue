<template>
  <div>
    <textarea :id="id" v-model="content"></textarea>
  </div>
</template>

<script>
let ___uuid = new Date().getTime();

export default {
  name: "crosseditor",
  props : {
    id: {
      type: String,
      'default': function() {
        return 'namose-'+ (++___uuid);
      }
    },
    value : {
      type : String,
    },
  },
  data() {
    return {
      content : this.value,
    };
  },
  mounted: function() {
    this.create();
  },
  methods: {
    create: function() {
      let CE = this.CrossEditor = new NamoSE(this.id);
      let me = this;
      CE.params = {
        Width: this.width,
        Height: this.height,
        UserLang: "auto",
        IconColor: "default",
        FullScreen: false,
        Menu : false,
        ImageSrcBase64 : true,
        NoUseToolBarPanelHTML : false,
        PreventDragAndDrop : true,
        DeleteCommand : ['iconmenu', 'fullscreen', 'backgroundimage', 'insertfile', 'emoticon', 'bookmark', 'autosave', 'privacy', 'validation', 'templatelist', 'insertdatetime', 'help', 'information', 'ce_imageeditor'],
        ParentEditor: this.$el,
        EditorBaseURL: 'http://local.mobilefactory.co.kr:82/namo/crosseditor/',
        event: {
          OnInitCompleted: function () {
            me.update()
          },
          CE_OnKeyActive() {
            me.$emit('getContent', CE.GetValue("XHTML"));
          },
          CBInsertedImage(img, type) {
            console.log("::::::::::::::::::::::::::::::::::::::::");
            console.log("::::::::::::::::::::::::::::::::::::::::");
            console.log(img);
            console.log(type);
            console.log("::::::::::::::::::::::::::::::::::::::::");
            console.log("::::::::::::::::::::::::::::::::::::::::");
          }
        }
      };

      CE.EditorStart();
    },
    update: function(val) {
      val = val || this.innerValue;
      this.CrossEditor.SetBodyValue(val);
    },
    getValue: function() {
      return this.CrossEditor.GetBodyValue();
    }
  }
}
</script>

<style scoped />