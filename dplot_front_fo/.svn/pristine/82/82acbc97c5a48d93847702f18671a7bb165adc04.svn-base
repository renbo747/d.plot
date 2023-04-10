<template>
    <b-modal id="confirmModal" modal-class="dp-modal alarm" centered style="z-index:9999">
        <template #modal-header>
        <h5 class="modal-title">{{title}}</h5>
        </template>

        <p class="modal-text mb-0" v-html="content"></p>

        <template #modal-footer="{}">
        <div class="btn-group">
            <b-button
                variant="outline-gray-800 not-hover btn-h38"
                @click="cancel()"
            >{{button2}}</b-button>
            <b-button
                variant="gray-800 btn-h38"
                @click="ok()"
            >{{button1}}</b-button>
        </div>
        </template>
    </b-modal>
</template>

<script>
export default {
    props: {
        title: { type: String },
        content:  { type: String },
        button1:  { type: String },
        button2:  { type: String },
        fnConfirm : {type : Function},
        fnCancel : {type : Function},
    },
    mounted() {
    },
    methods : {
        ok() {
            this.fnConfirm();
            this.$bvModal.hide('confirmModal');
        },
        cancel(){
            this.fnCancel();
            this.$bvModal.hide('confirmModal');
        }
    }
}
</script>
<style>
.modal-backdrop {
    z-index:2000 !important;
}
.modal {
    z-index:2001 !important;
}
</style>