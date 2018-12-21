import Promise from '../lib/blurbird';
import WXP from '../lib/wxp';
import login from './login.js';

class request {
    constructor() {
        this._hasCheckd = false;                        // 生命周期内是否检查过session
        this._token = wx.getStorageSync('token');         // 获取缓存中的token
        this._header = {
            'content-type': 'application/json',
            'Authorization': 'Bearer ' + this._token
        };
    }

    /**
     * 检查token是否存在
     *
     * @returns
     * @memberof request
     */
    checkToken() {
        if (this._token) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 设置统一的异常处理
     *
     * @param {*} handler
     * @memberof request
     */
    errorHandler(res) {
        switch (true) {
            case res.errMsg == "request:fail ":
                console.error('网络连接已断开');
                break;

        }
    }

    /**
     * GET类型的网络请求
     *
     * @param {*} url
     * @param {*} data
     * @param {*} [header=this._header]
     * @returns
     * @memberof request
     */
    getRequest(url, data = {}, header = this._header) {
        return this.requestAll(url, data, header, 'GET');
    }

    /**
     * DELETE类型的网络请求
     *
     * @param {*} url
     * @param {*} data
     * @param {*} [header=this._header]
     * @returns
     * @memberof request
     */
    deleteRequest(url, data = {}, header = this._header) {
        return this.requestAll(url, data, header, 'DELETE');
    }

    /**
     * PUT类型的网络请求
     *
     * @param {*} url
     * @param {*} data
     * @param {*} [header=this._header]
     * @returns
     * @memberof request
     */
    putRequest(url, data = {}, header = this._header) {
        return this.requestAll(url, data, header, 'PUT');
    }

    /**
     * POST类型的网络请求
     *
     * @param {*} url
     * @param {*} data
     * @param {*} [header=this._header]
     * @returns
     * @memberof request
     */
    postRequest(url, data = {}, header = this._header) {
        return this.requestAll(url, data, header, 'POST');
    }

    /**
     * 文件类型请求
     *
     * @param {*} url
     * @param {*} [header=this._header]
     * @returns
     * @memberof request
     */
    fileRequest(url, header = this._header) {
        return WXP.downloadFile({
            url: url,
            header: header,
        });
    }


    /**
     * 网络请求
     *
     * @param {*} url
     * @param {*} data
     * @param {*} header
     * @param {*} method
     * @returns
     * @memberof request
     */
    requestAll(url, data, header, method) {
        const that = this;

        return new Promise((resolve, reject) => {
            if (that.checkToken()) {
                // 当前生命周期内未进行过token检查
                if (!that._hasCheckd) {
                    let checkThridSessionPromise = login.checkThridSession(that._header);
                    let checkWxSessionPromise = login.checkWxSession();
                    Promise.all([checkThridSessionPromise, checkWxSessionPromise])
                        .then(() => {
                            console.log('checksession检查成功');
                            that._hasCheckd = true;
                            resolve();
                        })
                        .catch(() => {
                            console.log('checkSession检查失败');
                            that._hasCheckd = true;
                            login.doLogin()
                                .then(res => {
                                    console.log('doLogin成功');
                                    that._header['Authorization'] = 'Bearer ' + res.data.data.accessToken;
                                    that._token = res.data.data.accessToken;
                                    resolve();
                                })
                                .catch(res => {
                                    console.log("dologin失败")
                                    that.errorHandler(res);
                                    reject();
                                });
                        });
                } else {
                    console.log('token未过期');
                    resolve();
                }
            } else {
                console.log('没有token,直接登陆');
                login.doLogin()
                    .then(res => {
                        that._hasCheckd = true;
                        console.log('doLogin成功');
                        that._header['Authorization'] = 'Bearer ' + res.data.data.accessToken;
                        that._token = res.data.data.accessToken;
                        resolve();
                    })
                    .catch(res => {
                        that.errorHandler(res);
                        reject();
                    })
            }
        }).then(() => {
            return WXP.request({
                url: url,
                data: data,
                header: header,
                method: method
            }).then(res => {
                if (res.statusCode >= 200 && res.statusCode < 300 && res.data.code !== 101) {
                    return Promise.resolve(res.data);
                } else if (res.data.code === 101) {
                    that._hasCheckd = false;
                    return that.requestAll(url, data, header, method);
                } else {
                    return Promise.reject(res);
                }
            }).catch(res => {
                that.errorHandler(res);
                return Promise.reject(res.data);
            });
        });
    }
}

export default request;