import axios from 'axios'

import vue from '@root/main.js'

import util from '@root/js/util.js'
import storage from '@root/js/storage.js';
import store from '@root/js/store.js'

import event from '@root/js/event.js'

/*----------------------------- axios 설정 ------------------------------*/
axios.defaults.baseURL = '/api/';
axios.defaults.withCredentials = true;
axios.defaults.headers.post['Content-Type'] = 'application/json';

const pendingRequest = new Map();

function generateReqKey(config) {
    return config.url;
}

function addPendingRequest(config) {
    const requestKey = generateReqKey(config);
    if(requestKey.indexOf('common/code') > -1|| requestKey.indexOf('member/captcha') > -1 || requestKey.indexOf('mypage/reward')){
        return;
    }
    config.cancelToken = config.cancelToken || new axios.CancelToken((cancel) => {
        if (!pendingRequest.has(requestKey)) {
            pendingRequest.set(requestKey, cancel);
        }
    });
}

function removePendingRequest(config) {
    const requestKey = generateReqKey(config);
    if (pendingRequest.has(requestKey)) {
       const cancelToken = pendingRequest.get(requestKey);
       cancelToken(requestKey);
       pendingRequest.delete(requestKey);
    }
}

// API 요청 전처리
axios.interceptors.request.use(function(request) {
        util.debug("Request Info : " + JSON.stringify(request));
        
        if(request.url.indexOf('http') === -1) {
            request.headers.platform = window.sessionStorage.getItem('platform');
        }

        if (request.method !== 'post' || request.data == null || (typeof request.data === 'string' || request.data instanceof String))
            return request;

        util.debug("Request Data to FormData");
        let paramData = request.data;
        if (Object.prototype.hasOwnProperty.call(paramData, 'iskeep') && paramData.iskeep) {
            storage.setSessionStorage('param-' + paramData.reqname, paramData);
        }

        let params;
        if (Object.prototype.hasOwnProperty.call(paramData, 'files')) {
            // Object paramData를 FormData로 전환
            // 첨부파일이 있을경우 files : Array[]로 추가
            // 나머지 파라미터는 JSON string으로 전송
            let frmData = new FormData();
            const files = paramData['files'];
            for (let i = 0; i < files.length; i++) {
                const fileKey = files[i].key;
                util.debug(files[i].file);
                frmData.append(fileKey, files[i].file);
            }

            delete paramData['files'];
            frmData.append('params', new Blob([JSON.stringify(paramData)], { type: "application/json" }));
            params = frmData;
        } else {
            params = paramData;

            //중복요청 제외
            removePendingRequest(request);
            addPendingRequest(request);
        }

        if(util.isBlank(paramData.isloading) || paramData.isloading){
            event.$emit('loading', true);
        }

        request.data = params;
        return request
    },
    function(error) {
        event.$emit('loading', false);
        util.error('Request Error : ' + error);
        pendingRequest.clear();
        alert('서버에서 에러가 발생하였습니다.');
        return Promise.reject(error)
    }
)

// API 요청 후처리
axios.interceptors.response.use(function(response) {
        pendingRequest.delete(generateReqKey(response.config));
        if (response.headers['content-type'].indexOf("application/vnd") > -1) {
            if(window.navigator && window.navigator.msSaveOrOpenBlob) {
                const contentDisposition = response.headers['content-disposition'];
    
                let fileName = 'unknown';
                if (contentDisposition) {
                    const [fileNameMatch] = contentDisposition.split(';').filter(str => str.includes('filename'));
                    if (fileNameMatch)
                        [, fileName] = fileNameMatch.split('=');
                }
    
                fileName = decodeURIComponent(fileName);
                window.navigator.msSaveBlob(new Blob([response.data]), fileName);
            } else {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                const contentDisposition = response.headers['content-disposition'];
    
                let fileName = 'unknown';
                if (contentDisposition) {
                    const [fileNameMatch] = contentDisposition.split(';').filter(str => str.includes('filename'));
                    if (fileNameMatch)
                        [, fileName] = fileNameMatch.split('=');
                }
    
                fileName = decodeURIComponent(fileName);
    
                link.href = url;
                link.setAttribute('download', `${fileName}`);
                link.style.cssText = 'display:none';
                document.body.appendChild(link);
                link.click();
                link.remove();
            }
        }

        event.$emit('loading', false);
        util.debug("Response Info : " + JSON.stringify(response));
      
        //util.debug("Response Data : " + JSON.stringify(response.data));
        if(response.config.url.indexOf('logout') > -1){
            if (window.ReactNativeWebView) {
                window.ReactNativeWebView.postMessage(JSON.stringify({
                    type : 'PREF_SAVE',
                    data : {
                        isLogin : false,
                    }
                }));
            }
        }

        if (response.data.statusCode === 200) {
            if (response.data.data.isremember) {
                storage.setSessionStorage('name', response.data.data.memberinfo.name);
            
            const keys = Object.keys(pendingRequest); 
            for(let i = 0 ; i < keys.length ; i++){
               const cancelToken = pendingRequest.get(keys[i]);
                cancelToken(keys[i]);
                pendingRequest.delete(keys[i]);
            }
            
            vue.$router.go();
                
               // return;
             }

        
            if(!util.isEmpty(response.data.data.memberinfo)){
                if (response.data.data.memberinfo.authtype == 'member') {
                    store.commit("isLogin", true);
                    store.commit("membertype", response.data.data.memberinfo.membertype);
                    store.commit("joinchtype", response.data.data.memberinfo.joinchtype);
                    storage.setSessionStorage('name', response.data.data.memberinfo.name);
                }else {
                    store.commit("isLogin", false);
                    store.commit("membertype", "");
                    store.commit("joinchtype", "");
                }
            }
        } else if(response.data.statusCode === 204 && response.data.errorShow === true) {
            event.$emit('alert', '알림', response.data.message);
        } else if(response.data.statusCode === 500 && response.data.errorShow === true) {
            if(response.data.message.indexOf('로그인') > -1) {
                event.$emit('alert', '알림', response.data.message);
            } else {
                event.$emit('alert', '에러', response.data.message);
            }
        } else if(response.data.statusCode === 401) {
            if(response.config.url.indexOf('checkAuth') == -1) {
                event.$emit('alert', '알림', '로그아웃 되었습니다.', ()=>{
                    vue.$router.push({name:'member-login'});
                });
            }
        }

        return response.data;
    },
    function(error) {
        event.$emit('loading', false);
        //util.error("Response Error : " + error);
        pendingRequest.delete(error.message);
        if(error.constructor.name && error.constructor.name != 'Cancel' && error.constructor.name != 'r') {
            // alert(error);
            return Promise.reject(error);
        } else {
            throw error;
        }
    }
)

export default axios