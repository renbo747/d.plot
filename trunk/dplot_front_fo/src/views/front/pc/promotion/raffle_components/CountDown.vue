<template>
    <div class="count_down"> 
        <div>
            <p class="num">{{dateFormatter(nDate[2])}}</p>
            <p class="atten-new">DAY</p>
        </div>
        <div>
            <p class="num">{{dateFormatter(nDate[3])}}</p>
            <p class="atten-new">HOUR</p>
        </div>
        <div>
            <p class="num">{{dateFormatter(nDate[4])}}</p>
            <p class="atten-new">MINUTE</p>
        </div>
        <div>
            <p class="num">{{dateFormatter(nDate[5])}}</p>
            <p class="atten-new">SECOND</p>
        </div>
    </div>
</template>

<script>
    export default {
        props: ['targetDate'],
        data() {
            return {
                nDate: [0,0,0,0,0,0],
                tDate: this.targetDate,
                calc_second : 1000,
                calc_minute : 1000 * 60,
                calc_hour : 1000 * 60  * 60,
                calc_day : 1000 * 60 * 60 * 24,
                timer : ''
            }
        },
        mounted (){
            this.timer = setInterval(this.showRemaining,1000)
        },
        methods: {
            dateFormatter(t){return t < 10 ? `0${t}` : t},
            showRemaining(){
                this.nDate = [];
                let currentDate = new Date();
                let dDate = new Date(`${this.tDate[0]}/${this.tDate[1]}/${this.tDate[2]} ${this.tDate[3]}:${this.tDate[4]}:${this.tDate[5]}`);
                let distDt = dDate - currentDate;
                if(distDt < 0){
                    clearInterval(this.timer);
                    // 타이 머 종료 콜백
                    return;
                }
                let days = Math.floor(distDt / this.calc_day);
                let hours = Math.floor((distDt % this.calc_day) / this.calc_hour);
                let minutes = Math.floor((distDt % this.calc_hour) / this.calc_minute);
                let seconds = Math.floor((distDt % this.calc_minute) / this.calc_second);
    
                this.nDate.push(currentDate.getFullYear());
                this.nDate.push(currentDate.getMonth());
                this.nDate.push(days);
                this.nDate.push(hours);
                this.nDate.push(minutes);
                this.nDate.push(seconds);
            }
        }
    }
</script>

<style>
    .event .count_down{margin: 20px 0 60px; }
	.event .count_down p{display:block; font-size:24px; font-weight:600; }
	.event .count_down p.num {font-weight:500; font-size:100px; position:relative; font-family: 'Mont';  line-height: 1.2;}
	.event .count_down p.num:before{content: ''; width:100%; height: 4px; background-color: #c0ff00; position: absolute; top: 50%; left: 0; margin-top:-2px;}
	.event .count_down > div{display:inline-block; width:240px;  }
	.event .count_down > div:not(:last-child) .num:after{content:':'; position:absolute; right:-10px; top:0;  line-height: 1;}
</style>