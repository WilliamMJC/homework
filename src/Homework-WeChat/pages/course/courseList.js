import { $wuxDialog } from '../../lib/wux-weapp-master/index';
import Promise from '../../lib/blurbird';
import Api from '../../utils/api.js';
import { $wuxToast } from '../../lib/wux-weapp-master/index.js';
import { $wuxLoading } from '../../lib/wux-weapp-master/index';
import Notify from '../../lib/vant-weapp/notify/index.js';

Page({
    data: {
        currentTab: 'tab1',    // 当前激活的tab页面

        /* 空列表展示prommpt */
        isCourseListEmpty: false,

        /* 浮动按钮配置 */
        actionVisible: false,
        actions: [
            {
                label: '加入课程',
                icon: '/images/join.png'
            },
            {
                label: '创建课程',
                icon: '/images/add.png'
            },
        ],

        spinning: false,

        // stuCourseList: [],
        // teaCourseList: [],
        courseList: [],
    },

    onLoad() {

    },

    onShow() {
        if(this.data.courseList.length !== 0) {
            this.setData({ courseList: [] });
        }
        Promise.all([this.getTeaCourseList(), this.getStuCourseList()])
            .then(() => {
                if (this.data.courseList.length === 0) {
                    this.setData({ isCourseListEmpty: true });
                }
            });
    },

    /* 网络请求 */
    getStuCourseList() {
        return Api.getStuCourseList()
            .then(res => {
                if (res.code != 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({
                        courseList: this.data.courseList.concat(res.data),
                    });
                }
            })
            .catch(res => {
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

    getTeaCourseList() {
        return Api.getTeaCourseList()
            .then(res => {
                if (res.code != 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({
                        courseList: res.data.concat(this.data.courseList),
                    });
                }
            })
            .catch(res => {
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

    joinCourse(courseId) {
        let loading = $wuxLoading()
        loading.show({
            text: '数据加载中',
        })
        Api.joinCourse(courseId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    loading.hide();
                    wx.navigateTo({
                        url: `/pages/course/course?courseId=${courseId}`,
                    });
                }
            })
            .catch(res => {
                loading.hide();
                console.log(res);
                if (res.code === 404) {
                    $wuxToast().show({
                        type: 'cancel',
                        duration: 1500,
                        color: '#fff',
                        text: '课程不存在'
                    });
                } else if (res.code === 103) {
                    $wuxToast().show({
                        type: 'cancel',
                        duration: 1500,
                        color: '#fff',
                        text: '你已加入此课程'
                    });
                } else if (res.code === 102) {
                    // todo 动画组件bug
                    setTimeout(() => {
                        $wuxDialog().alert({
                            resetOnClose: true,
                            title: '无法加入此课程',
                            content: '你没有加入此课程的权限，请联系老师!',
                            onConfirm(e) {

                            },
                        })
                    }, 1000);
                }
            })
            .finally(() => {

            });
    },

    /* 事件处理函数 */

    // onTabChange({ detail: { key } }) {
    //     this.setData({ currentTab: key });
    // },

    toggleButtons() {
        this.setData({
            actionVisible: !this.data.actionVisible
        })
    },

    actionButtonTap({ detail: { index } }) {
        let setting = wx.getStorageSync('setting'),
            workMail = wx.getStorageSync('workMail');

        if (setting === false) {
            this.unSetting();
        } else if (index === 0) {
            this.joinCoursePrompt();
        } else if (workMail === false && index === 1) {
            this.unSetWorkMail();
        } else if (index === 1) {
            wx.navigateTo({
                url: '/pages/course/addCourse',
            });
        }
    },

    /* 工具函数 */

    unSetting() {
        $wuxDialog().confirm({
            resetOnClose: true,
            closable: true,
            title: '未设置个人信息',
            content: '未设置个人信息不能加入/创建课程，前往设置?',
            onConfirm() {
                wx.redirectTo({
                    url: '/pages/mine/setting'
                });
            },
        });
    },

    joinCoursePrompt() {
        const that = this;
        $wuxDialog().prompt({
            resetOnClose: true,
            title: '加入课程',
            content: '请输入课程号',
            fieldtype: 'text',
            placeholder: '请输入课程号',
            maxlength: 10,
            onConfirm(e, response) {
                that.joinCourse(response);
            },
        })
    },

    unSetWorkMail() {
        $wuxDialog().confirm({
            resetOnClose: true,
            closable: true,
            title: '未设置作业邮箱',
            content: '未设置作业邮箱不能创建课程，前往设置?',
            onConfirm() {
                wx.redirectTo({
                    url: '/pages/mine/setWorkMail'
                });
            },
        });
    }
})