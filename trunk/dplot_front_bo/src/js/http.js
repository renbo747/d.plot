import axios from 'axios'

import util from '@root/js/util.js'
import storage from '@root/js/storage.js';
import store from '@root/js/store.js'

import event from '@root/js/event.js'
import adminRouter from "@router/admin";
import partnersRouter from "@router/partners";

/*----------------------------- axios 설정 ------------------------------*/
axios.defaults.baseURL = '/api/';
axios.defaults.withCredentials = true;
axios.defaults.headers.post['Content-Type'] = 'application/json';

// API 요청 전처리
axios.interceptors.request.use(function(request) {
        let system = window.sessionStorage.getItem("system");
        if(request.url.indexOf('http') === -1) {
            request.headers.platform = window.sessionStorage.getItem('platform');
        }

        //관리자/파트너사 컴포넌트를 같이 쓰기위해서 URL 맵핑을 변경해줌
        const session = storage.getLocalStorage(store.getters.CONSTANTS.MANAGER_SESSION);
        const admin = storage.getLocalStorage(store.getters.CONSTANTS.ADMIN_USER);
        const partner = storage.getLocalStorage(store.getters.CONSTANTS.PARTNER_USER);

        if( (!request.url.startsWith("/partnership/")) && !request.url.startsWith("/common/") && request.url !== '/admin/login_act' && request.url !== '/partners/login_act') {

            if (typeof session !== 'undefined' && (system === 'ADMIN' || system === 'PARTNER')) {

                if (system === 'ADMIN') {
                    if (JSON.stringify(session) !== JSON.stringify(admin)) {
                        storage.removeLocalStorage(store.getters.CONSTANTS.ADMIN_USER);
                        adminRouter.push("/login?error=401").then(r => this.$util.debug(r));
                        return new Promise(function(resolve, reject) {});
                    }
                } else if (system === 'PARTNER') {
                    if (JSON.stringify(session) !== JSON.stringify(partner)) {
                        storage.removeLocalStorage(store.getters.CONSTANTS.PARTNER_USER);
                        partnersRouter.push("/login?error=401").then(r => this.$util.debug(r));
                        return new Promise(function(resolve, reject) {});
                    }
                }
            }
        }

        if(typeof session !== 'undefined'&& session.usertype === store.getters.CONSTANTS.ADMIN.USER_TYPE_PARTNER && request.url.startsWith("/admin/") && request.url !== '/admin/login_act'){
            request.url = request.url.replace("/admin/", "/partners/");
        }

        if (request.method !== 'post' || request.data == null || (typeof request.data === 'string' || request.data instanceof String))
            return request;

        util.debug("Request Data to FormData");
        let paramData = request.data;

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
        alert('서버에서 에러가 발생하였습니다.');
        return Promise.reject(error)
    }
)

// API 요청 후처리
axios.interceptors.response.use(function(response) {
        let system = window.sessionStorage.getItem("system");

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
        util.debug("Response Data : " + JSON.stringify(response.data));

        //서버세션이 만료되었을 경우 처리
        if(response.data.statusCode === 401 && response.data.errorShow === true) {
            if(system === "ADMIN") {
                storage.removeLocalStorage(store.getters.CONSTANTS.MANAGER_SESSION);
                storage.removeLocalStorage(store.getters.CONSTANTS.ADMIN_USER);
                adminRouter.push("/login?error=401").then(r => this.$util.debug(r));
            } else if(system === "PARTNER"){
                storage.removeLocalStorage(store.getters.CONSTANTS.MANAGER_SESSION);
                storage.removeLocalStorage(store.getters.CONSTANTS.PARTNER_USER);
                partnersRouter.push("/login?error=401").then(r => this.$util.debug(r));
            }
        }

        if(response.data.statusCode === 500 && response.data.errorShow === true) {
            alert(response.data.message);
        }

        return response.data;
    },
    function(error) {
        event.$emit('loading', false);
        util.error("Response Error : " + error);
        alert(error);
        return Promise.reject(error);
    }
)

export default axios