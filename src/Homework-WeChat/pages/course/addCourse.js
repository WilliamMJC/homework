import Api from '../../utils/api.js';
import Notify from '../../lib/vant-weapp/notify/index.js';
import { $wuxToast } from '../../lib/wux-weapp-master/index.js';
import Promise from '../../lib/blurbird';
import { $wuxLoading } from '../../lib/wux-weapp-master/index';

Page({
    data: {
        /* 表单数据 */
        courseName: '',
        courseDesc: '',
        isPublic: true,

        btnLoading: false,

    },

    onLoad() {

    },

    /* 事件处理函数 */

    formSubmit({ detail: { value } }) {
        let { courseName, courseDesc, isPublic } = value;

        this.setData({ btnLoading: true });

        if (courseName === null || courseName === '') {
            Notify({
                text: '必填项不能为空',
                duration: 1000,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else {
            this.createCourse(value);
        }
    },

    onSwitchChange({ detail: { value } }) {
        this.setData({ isPublic: value });
    },


    onCourseNameInputChange({ detail }) {
        this.setData({
            courseName: detail,
        });
        if (!!this.data.courseName) {
            this.setData({ isSwitchDisabled: false });
        } else {
            this.setData({ isSwitchDisabled: true });
        }
    },

    onHowTap() {
        wx.navigateTo({
            url: '/pages/help/help3',
        });
    },

    /* 网络请求 */

    createCourse(value) {
        let data = value;

        Api.createCourse(data)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({ btnLoading: false });
                    Notify({
                        text: '课程创建成功',
                        duration: 1000,
                        selector: '#form-notify',
                        backgroundColor: '#19be6b'
                    });
                    wx.redirectTo({
                        url: '/pages/course/course?courseId=' + res.data,
                    })
                }
            })
            .catch(res => {
                this.setData({ btnLoading: false });
                console.error(res);
                if (res.msg) {
                    $wuxToast().show({
                        type: 'cancel',
                        duration: 1500,
                        color: '#fff',
                        text: res.msg
                    });
                }
            });
    },
})