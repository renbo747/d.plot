<template>
    <b-modal id="alertModal" modal-class="dp-modal alarm" centered :no-close-on-backdrop="true">
        <template #modal-header>
        <h5 class="modal-title">{{title}}</h5>
        </template>

        <p class="modal-text mb-0" v-html="content"></p>

        <template #modal-footer="{}">
        <div class="btn-group">
            <b-button 
            variant="gray-800 btn-h38" 
            @click="ok()"
            >{{button1}}</b-button
            >
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
        fnConfirm : {type : Function}
    },
    mounted() {
    },
    methods : {
        ok() {
            this.fnConfirm();
            this.$bvModal.hide('alertModal');
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
