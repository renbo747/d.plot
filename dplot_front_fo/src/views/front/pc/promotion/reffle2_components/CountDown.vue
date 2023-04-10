<template>

    <div class="timer_box">
        <div class="time_con">
            <div>  
                <p class="day count_text">{{dateFormatter(nDate[2])}}</p>
                <p class="text">DAY</p>
            </div>
        </div>
        <div  class="time_con">
            <div>
                <p class="hour count_text">{{dateFormatter(nDate[3])}}</p>
                <p class="text">HOUR</p>
            </div>
        </div>
        <div  class="time_con">
            <div>
                <p class="minutes count_text">{{dateFormatter(nDate[4])}}</p>
                <p class="text">MINUTES</p>
            </div>
        </div>
        <div  class="time_con">
            <div>
                <p class="second count_text">{{dateFormatter(nDate[5])}}</p>
                <p class="text">SECOND</p>
            </div>
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
    .section02 .timer_box{ display: flex;  justify-content: space-between;position: relative;margin-left: 20px;}
    .section02 .timer_box::before{content: '';display: block; width: 100%; height: 4px ; position: absolute; top:45px;left:0; background-blend-mode: overlay; background: linear-gradient(to right, #00f4db, #00f0cd); z-index: 10;}
    .section02 .timer_box .time_con{display: flex; width: 25%; justify-content: center; position: relative;}
    .section02 .timer_box .time_con:not(:last-child)::after{ content: ':'; line-height: 1; display: block; font-size: 80px;  font-family: 'Mont'; -webkit-text-stroke: 5px #222;position: absolute; right:0; top: 10px; transform: translate3d(50%,0,0);}
    .section02 .timer_box .time_con:last-child > div{width: 100%; text-align: center;}
    .section02 .timer_box .count_text{font-size: 108px;  line-height: 1;color: #222;font-family: 'Mont'; text-stroke: 5px #222;-webkit-text-stroke: 5px #222;margin-bottom: 0;}
    .section02 .timer_box .text{text-align: center; font-family: AttenNew; font-size: 24px; color: #3a3a3a;}
</style>