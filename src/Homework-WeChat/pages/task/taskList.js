import Api from '../../utils/api.js';
import { formatDate } from '../../utils/util.js';
import { $wuxDialog } from '../../lib/wux-weapp-master/index';

Page({
    data: {
        taskList: [],        
        isTaskListEmpty: false,
        spinning: false,
    },

    onLoad() {

    },

    onShow() {
        this.getTaskList();
    },

    /* 网络请求 */
    getTaskList() {
        Api.getTaskList()
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({ 
                        taskList: res.data.reverse(),
                        isTaskListEmpty: res.data.length === 0 ? true : false,
                    });
                }
            })
            .catch(res => {
                console.error(`taskList.js getTaskList(): ${res}`);
            });
    },

    /* 事件处理函数 */
    actionButtonTap() {
        console.log('hahahah');
    },

    onAddButtonTap() {
        let setting = wx.getStorageSync('setting'),
            workMail = wx.getStorageSync('workMail');

        console.log(setting);
        if (!setting) {
            this.unSetting();
        } else if (!workMail) {
            this.unSetWorkMail();
        } else {
            wx.navigateTo({
                url: '/pages/task/addTask'
            })
        }

    },

    /* util */
    /* dateFormat(dateString) {
        let date = new Date(dateString),
            year = date.getFullYear(),
            month = date.getMonth() + 1,
            day = date.getDay() + 1,
            hour = date.getHours(),
            minute = date.getMinutes();
        return formatDate([year, month, day, hour, minute]);
    }, */

    unSetting() {
        $wuxDialog().confirm({
            resetOnClose: true,
            closable: true,
            title: '未设置个人信息',
            content: '未设置个人信息不能创建作业，前往设置?',
            onConfirm() {
                wx.navigateTo({
                    url: '/pages/mine/setting'
                });
            },
        });
    },

    unSetWorkMail() {
        $wuxDialog().confirm({
            resetOnClose: true,
            closable: true,
            title: '未设置作业邮箱',
            content: '未设置作业邮箱不能创建课程，前往设置?',
            onConfirm() {
                wx.navigateTo({
                    url: '/pages/mine/setWorkMail'
                });
            },
        });
    }

})