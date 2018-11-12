import Api from '../../utils/api.js';
import Notify from '../../lib/vant-weapp/notify/index.js';
import { $wuxToast } from '../../lib/wux-weapp-master/index.js';
import Promise from '../../lib/blurbird';

Page({
    data: {
        /* 表单内容 */
        username: '',
        personalId: '',
        personalMail: '',
        school: '惠州学院',

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
                        username: res.data.username,
                        personalId: res.data.personalId,
                        personalMail: res.data.personalMail,
                    });

                    if(res.data.school !== null && res.data.school !== '') {
                        this.setData({ school: res.data.school });
                    }
                    // /* spin组件bug 需要设置延时才能正常消失 */
                    // setTimeout(() => {
                    //     this.setData({ spinning: false });
                    // }, 100);
                }
            })
            .catch(res => {
                this.setData({ spinning: false });
                console.error(res.msg);
                $wuxToast().show({
                    type: 'cancel',
                    duration: 1000,
                    color: '#fff',
                    text: res.msg
                })
            })
    },

    /* 事件处理函数 */

    /**
     * 表单提交
     *
     * @param {*} { detail: { value } }
     */
    formSubmit({ detail: { value } }) {
        let username = value.username,
            personalId = value.personalId,
            personalMail = value.personalMail,
            school = value.school;

        this.setData({ btnLoading: true });

        /* 输入校验 */
        if (username === '' || personalId === '' || personalMail === '') {
            Notify({
                text: '必填项不能为空',
                duration: 1000,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else if (!personalId.match(/\d+/i)) {
            Notify({
                text: '学号/工号须为数字',
                duration: 1000,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else if (!personalMail.match(/\w@qq|163|126.com/i)) {
            Notify({
                text: '只支持QQ/网易邮箱',
                duration: 1000,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else {
            /* 发起请求 */
            Api.updateUserInfo(value)
                .then(res => {
                    if (res.code !== 0) {
                        return Promise.reject(res);
                    } else {
                        this.setData({
                            username: res.data.username,
                            personalId: res.data.personalId,
                            personalMail: res.data.personalMail,
                            school: res.data.school,
                        })

                        this.setData({ btnLoading: false });
                        Notify({
                            text: '信息更新成功',
                            duration: 1000,
                            selector: '#form-notify',
                            backgroundColor: '#19be6b'
                        });

                        /* 将成功设置个人信息写入缓存 */
                        wx.setStorageSync('setting', true);
                    }
                })
                .catch(res => {
                    console.error(res);
                    if (res.msg) {
                        $wuxToast().show({
                            type: 'cancel',
                            duration: 1000,
                            color: '#fff',
                            text: res.msg
                        });
                    }
                });
        }
    }
})