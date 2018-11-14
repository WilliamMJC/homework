import Api from '../../utils/api.js';
import Notify from '../../lib/vant-weapp/notify/index.js';
import { $wuxToast } from '../../lib/wux-weapp-master/index.js';
import Promise from '../../lib/blurbird';

Page({
    data: {
        /* 作业邮箱信息 */
        workMail: '',
        workMailPwd: '',

        btnLoading: false,
        spinning: false,
    },

    onLoad() {
        this.getUserInfo();
    },

    /**
     * 获取用户信息
     *
     */
    getUserInfo() {
        Api.getUserInfo()
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({
                        workMail: res.data.workMail,
                        workMailPwd: res.data.workMailPwd,
                    });
                    
                    /* spin组件bug 需要设置延时才能正常消失 */
                    setTimeout(() => {
                        this.setData({ spinning: false });
                    }, 100);
                }
            })
            .catch(res => {
                this.setData({ spinning: false })
                if(res.msg) {
                    $wuxToast().show({
                        type: 'cancel',
                        duration: 1000,
                        color: '#fff',
                        text: res.msg
                    })
                }
            })
    },


    /* 事件处理函数 */

    /**
     * 表单提交
     *
     * @param {*} { detail: { value } }
     */
    formSubmit({ detail: { value } }) {
        let workMail = value.workMail,
            workMailPwd = value.workMailPwd;

        this.setData({ btnLoading: true });

        /* 输入校验 */
        if (workMail === '' || workMailPwd === '') {
            Notify({
                text: '必填项不能为空',
                duration: 1000,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else if (!workMail.match(/\w@qq|163|126.com/i)) {
            Notify({
                text: '只支持QQ/网易邮箱',
                duration: 1000,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else {
            /* 发去请求 */
            Api.updateUserInfo(value)
                .then(res => {
                    if (res.code !== 0) {
                        return Promise.reject(res);
                    } else {
                        this.setData({
                            workMail: res.data.workMail,
                            workMailPwd: res.data.workMailPwd
                        })

                        this.setData({ btnLoading: false });
                        Notify({
                            text: '作业邮箱设置成功',
                            duration: 1000,
                            selector: '#form-notify',
                            backgroundColor: '#19be6b'
                        });

                        /* 将成功设置作业邮箱的信息写入缓存 */
                        wx.setStorageSync('workMail', true);
                    }
                })
                .catch(res => {
                    if (res.code === 600) {
                        this.setData({ btnLoading: false });
                        Notify({
                            text: '连接邮箱失败，请检查是否填写正确信息',
                            duration: 1000,
                            selector: '#form-notify',
                            backgroundColor: '#ed3f14'
                        });
                    } else {
                        return Promise.reject(res);
                    }
                })
                .catch(res => {
                    Notify({
                        text: res.msg,
                        duration: 1000,
                        selector: '#form-notify',
                        backgroundColor: '#ed3f14'
                    });
                });
        }
    },
})