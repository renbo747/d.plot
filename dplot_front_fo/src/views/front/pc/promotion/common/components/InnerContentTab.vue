<template>
    <div class="tab_wrap" id="prom_tab_menu">
        <div class="tab_container">
            <ul :style="{maxWidth: maxWidth+'px'}">
                <li v-for="(menu, idx) in menus" :class="menuclassList[idx]" :key="idx"><a @click="scrollToTargetAdjusted(`#prom0${idx+1}`)">{{menu}}</a></li>
            </ul>
        </div>
    </div>
</template>

<script>
	import util from '../../../../../../js/util';

	let isMobile = util.isMobile()

    export default {
        props : ['menus', 'maxWidth'],
        data(){
            return {
                menuclassList : ['prom01_tab','prom02_tab','prom03_tab','prom04_tab'],
            }
        },
        methods : {
            scrollToTargetAdjusted(element){
				element = document.querySelector(`${element}`)
				let headerOffset = 0;
				let elementPosition = element.getBoundingClientRect().top;
				let offsetPosition = elementPosition + window.pageYOffset - headerOffset - (isMobile ? 93 : 185);
			
				window.scrollTo({
					top: offsetPosition,
					behavior: "smooth"
				});
			},
			getAbsoluteTop(element) {
				return element.getBoundingClientRect().top;
			}
        },
        mounted (){
			const promTab = this.$scrollmagic.scene({
				triggerElement: '#prom01',
				duration: $('#prom01').outerHeight(true)+$('#prom02').outerHeight(true)+$('#prom03').outerHeight(true)+$('#prom04').outerHeight(true),
				triggerHook: 0,
                offset: isMobile ? -94 : -185,
			})        
			.setClassToggle('#prom_tab_menu','active')

			const prom01 = this.$scrollmagic.scene({
				triggerElement: '#prom01',
				duration:$('#prom01').outerHeight(true),
				triggerHook: 0,
                offset: isMobile ? -94 : -185,
			})
			// .addIndicators()
			.setClassToggle('.prom01_tab','active')

			const prom02 = this.$scrollmagic.scene({
				triggerElement: '#prom02',
				duration:$('#prom02').outerHeight(true),
				triggerHook: 0,
                offset: isMobile ? -94 : -185,
			})
			// .addIndicators()
			.setClassToggle('.prom02_tab','active')
			const prom03 = this.$scrollmagic.scene({
				triggerElement: '#prom03',
				duration:$('#prom03').outerHeight(true),
				triggerHook: 0,
                offset: isMobile ? -94 : -185,
			})        
			.setClassToggle('.prom03_tab','active')
			const prom04 = this.$scrollmagic.scene({
				triggerElement: '#prom04',
				duration:$('#prom04').outerHeight(true),
				triggerHook: 0,
                offset: isMobile ? -94 : -185,
			})
			.setClassToggle('.prom04_tab','active')

			this.$scrollmagic.addScene(promTab)
			this.$scrollmagic.addScene(prom01)
			this.$scrollmagic.addScene(prom02)
			this.$scrollmagic.addScene(prom03)
			this.$scrollmagic.addScene(prom04)
		},
    }
</script>

<style>
    .sub-page {padding-bottom: 0 !important;}
</style>
<style scoped>
    .tab_wrap { height: 0; position: relative; top: -86px;}
	.tab_wrap.active { z-index: 930; top:100px; position:fixed; width: 100%; max-width:1280px; margin-top: 0; padding-top: 0; }
    .tab_container {width: 100%; display: flex; justify-content: center; align-items: center; position: relative; z-index: 1;}
    .tab_container::after{content:''; width: 100%; height: 2px; position: absolute; bottom: 0; background:#000; z-index: 0;}
	.tab_wrap ul { display: flex; justify-content: space-between; padding: 0; margin: 0; position: relative; width: 100%; z-index: 1;}
	.tab_wrap ul li { border: 1px solid #000; width: 100%; padding: 0; margin: 0; list-style: none; border-bottom: 2px solid #000; text-align: center; border-radius: 20px 20px 0 0; background: #333; color:#fff;}
	.tab_wrap ul li:first-child.active { background: #ffccff; border-bottom: 0; }
	.tab_wrap ul li.active { background: #fff; border-bottom: 0; }
	.tab_wrap ul li a { font-size: 30px; font-weight: 700; cursor:pointer; color: #000; display: block; width: 100%; height: 100%; padding: 25px 0 15px; letter-spacing: -3px; color:#fff;}
    .tab_wrap ul li.active a {color:#000;}

	@media (max-width : 500px){
		.tab_wrap { height: 0; position: relative; top: -43px;}
		.tab_wrap.active { z-index: 930; top:50px; position:fixed; width: 100%; max-width:100%; margin-top: 0; padding-top: 0; }
		.tab_container {width: 100%; display: flex; justify-content: center; align-items: center; position: relative; z-index: 1;}
		.tab_container::after{content:''; width: 100%; height: 2px; position: absolute; bottom: 0; background:#000; z-index: 0;}
		.tab_wrap ul { display: flex; justify-content: space-between; padding: 0; margin: 0; position: relative; width: 100%; z-index: 1;}
		.tab_wrap ul li { border: 1px solid #000; width: 100%; padding: 0; margin: 0; list-style: none; border-bottom: 2px solid #000; text-align: center; border-radius: 10px 10px 0 0; background: #333; color:#fff;}
		.tab_wrap ul li:first-child.active { background: #ffccff; border-bottom: 0; }
		.tab_wrap ul li.active { background: #fff; border-bottom: 0; }
		.tab_wrap ul li a { font-size: 14px; font-weight: 700; cursor:pointer; color: #000; display: block; width: 100%; height: 100%; padding: 10px 0; letter-spacing: -1px; color:#fff; word-break: keep-all;}
		.tab_wrap ul li.active a {color:#000;}
	}
</style>