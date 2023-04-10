<template>
    <section class="notice_section">
        <div class="text_wrap">
            <div class="notice_title">
                <p>{{data.title}}</p>
            </div>
            <p v-html="data.top_desc"></p>
            <ul class="notice_list">
                <li v-for="(listItem,idx) in data.list" :key="idx">{{listItem}}</li>
            </ul>
        </div>
    </section>
</template>

<script>
export default {
    props: {
        data: Object
    }    
}
</script>

<style scoped>
    .notice_section { background: #f1f1f1; padding: 110px 0 110px; }
	.notice_section .text_wrap { padding: 0 6%; }
    .notice_section p{font-size:26px; font-weight:500; margin:0 0 20px;}
	.notice_title p { display: inline-block; border-bottom: 2px solid #3c3c3c; font-size: 28px; font-weight: 700; letter-spacing: -3px; padding: 0; margin-bottom: 0;  margin-bottom: 30px;}
	.notice_list { line-height: 1.8; font-size: 20px; font-weight: 400; padding: 0; margin: 0; margin-top: 25px; }
	.notice_list li { list-style: none; padding-left: 10px; position: relative; word-break: keep-all; }
	.notice_list li::before { content: '-'; position: absolute; left: 0;}

    @media (max-width: 500px){
        .notice_section { background: f1f1f1; padding: 55px 0 55px; min-height: 50vh;}
        .notice_section .text_wrap { padding: 0 5%; }
        .notice_section p{font-size:11px; font-weight:500; margin:0 0 20px;}
        .notice_title p { display: inline-block; border-bottom: 2px solid #3c3c3c; font-size: 15px; font-weight: 700; letter-spacing: -1px; padding: 0; margin-bottom: 10px; }
        .notice_list { line-height: 1.8; font-size: 12px; font-weight: 400; padding: 0; margin: 0; margin-top: 25px; }
        .notice_list li { list-style: none; padding-left: 10px; position: relative; word-break: keep-all; }
        .notice_list li::before { content: '-'; position: absolute; left: 0; }
    }
</style>