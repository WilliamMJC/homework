import WXP from '../lib/wxp';
import Promise from '../lib/blurbird';

const LOGIN_URL = 'http://localhost:8080/homework/auth';
const CHECK_URL = 'http://localhost:8080/homework/authStatus'
// const LOGIN_URL = 'https://homework.infoaas.com/homework/auth';
// const CHECK_URL = 'https://homework.infoaas.com/homework/authStatus'
    
let checkWxSessionPromise,
    checkThridSessionPromise,
    loginPromise;

const login = {

    checkThridSession(header) {
        if (checkThridSessionPromise) {
            return Promise.race([checkThridSessionPromise]);
        }
        checkThridSessionPromise = new Promise((resolve, reject) => {
            WXP.request({
                url: CHECK_URL,
                header: header
            }).then(res => {
                // console.log(res);
                if (res.data.code !== 0) {
                    checkThridSessionPromise = null;
                    reject(false);
                } else {
                    checkThridSessionPromise = null;
                    resolve(true);
                }
            }).catch(() => {
                checkThridSessionPromise = null;
                console.error('未知错误');
                reject(false);
            });
        });
        return checkThridSessionPromise;
    },

    checkWxSession() {
        if (checkWxSessionPromise) {
            return Promise.race([checkWxSessionPromise]);
        }
        checkWxSessionPromise = new Promise((resolve, reject) => {
            WXP.checkSession()
                .then(() => { 
                    checkWxSessionPromise = null;
                    resolve(true); 
                })
                .catch(() => {
                    checkWxSessionPromise = null;
                    reject(false);
                });
        });
        return checkWxSessionPromise;
    },

    doLogin() {
        if (loginPromise) {
            return Promise.race([loginPromise]);
        }
        loginPromise = new Promise((resolve, reject) => {
            WXP.login()
                .then(res => {
                    if (res.code) {
                        WXP.request({
                            url: LOGIN_URL,
                            data: {
                                code: res.code
                            },
                            method: 'POST'
                        }).then(res => {
                            if (res.data.code === 0) {
                                wx.setStorageSync('token', res.data.data.accessToken);
                                wx.setStorageSync('setting', res.data.data.setting);
                                wx.setStorageSync('workMail', res.data.data.workMail);
                                loginPromise = null;
                                resolve(res);
                            } else {
                                reject(res);
                            }
                        }).catch(res => {
                            loginPromise = null;
                            reject(res);
                        })
                    }
                })
                .catch(res => {
                    loginPromise = null;
                    console.error('doLogin: ' + res.msg);
                    reject(res);
                });
        })
        return loginPromise;
    }
}

export default login;