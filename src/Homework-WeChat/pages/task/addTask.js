import { $wuxSelect } from '../../lib/wux-weapp-master/index';
import Notify from '../../lib/vant-weapp/notify/index.js';
import Api from '../../utils/api.js';
import { $wuxToast } from '../../lib/wux-weapp-master/index.js';
import { formatDate } from '../../utils/util.js';
import { $wuxDialog } from '../../lib/wux-weapp-master/index';

Page({
    data: {
        courses: [],
        currentCourse: {},

        taskName: '',
        taskDesc: '',
        startTime: '',
        endTime: '',
        acceptFileType: ['1', '2',],

        btnLoading: false,

    },

    onLoad(option) {
        let startTime = new Date().getTime(),
            endTime = startTime + 604800000;         // 默认间隔是一周

        this.setData({
            startTime,
            endTime,
        });

        if (option.courseId && option.courseName) {
            let param = {
                courseId: option.courseId,
                courseName: option.courseName
            };
            this.setData({
                currentCourse: param,
            });
        } else {
            this.getTeaCourseList();
        }
    },

    /* 网络请求 */
    createTask(data) {
        Api.createTask(data)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({ btnLoading: false })
                    wx.navigateTo({
                        url: `/pages/task/task?courseId=${this.data.currentCourse.courseId}&taskId=${res.data.taskId}&courseId=${this.data.currentCourse.courseId}&size=0`,
                    });
                }
            })
            .catch(res => {
                this.setData({ btnLoading: false });
                console.log(`addTaks.js createTask: ${res}`);
                if (res.msg) {
                    $wuxToast().show({
                        type: 'cancel',
                        duration: 1000,
                        color: '#fff',
                        text: res.msg
                    })
                }
            });
    },

    getTeaCourseList() {
        Api.getTeaCourseList()
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    if (res.data.length === 0) {
                        return Promise.reject(res);
                    } else {
                        this.setData({
                            courses: res.data,
                            currentCourse: res.data[0],
                        });
                    }
                }
            })
            .catch(res => {
                if (res.data.length === 0) {
                    $wuxDialog().open({
                        resetOnClose: true,
                        title: '课程列表为空',
                        content: '去创建一个课程？',
                        maskClosable: false,
                        buttons: [{
                            text: '取消',
                            onTap() {
                                wx.navigateBack({
                                    delta: 1,
                                });
                            },
                        }, {
                            text: '确定',
                            type: 'primary',
                            onTap() {
                                wx.redirectTo({
                                    url: '/pages/course/addCourse'
                                });
                            },
                        }],
                    })
                } else {
                    console.error(`addTask getTeaCourseList(): ${res}`);
                }
            });
    },

    /* 事件处理函数 */

    formSubmit({ detail: { value } }) {
        let taskName = value.taskName,
            taskDesc = value.taskDesc,
            startTime = this.data.startTime,
            endTime = this.data.endTime,
            courseId = this.data.currentCourse.courseId,
            acceptFileType = this.data.acceptFileType,
            acceptType = 0,
            data = { courseId, taskName, taskDesc, startTime, endTime };

        for(let item of acceptFileType) {
            if(item === '') continue;
            acceptType += parseInt(item);
        }
        data.acceptType = acceptType;

        this.setData({ btnLoading: true });

        if (taskName === null || taskName === '') {
            Notify({
                text: '作业名不能为空',
                duration: 1500,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else if (startTime === null || startTime === '') {
            Notify({
                text: '请设置作业开始时间',
                duration: 1500,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else if (endTime === null || endTime === '') {
            Notify({
                text: '请设置作业结束时间',
                duration: 1500,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else if (new Date(endTime).getTime() - new Date(startTime).getTime() < 7200000) {
            Notify({
                text: '作业时间间隔需大于两个小时',
                duration: 1500,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else if(acceptType === 0) {
            Notify({
                text: '请至少选择一种支持的文件类型',
                duration: 1500,
                selector: '#form-notify',
                backgroundColor: '#ed3f14'
            });
            this.setData({ btnLoading: false });
        } else {
            this.createTask(data);
        }
    },

    onCourseSelect() {
        if (this.data.courses.length === 0) return;

        let courses = this.data.courses,
            option = [];

        for (let i = 0, len = courses.length; i < len; i++) {
            option[i] = {};
            option[i].title = courses[i].courseName;
            option[i].value = courses[i].courseId;
            option[i].color = 'positive';
        }

        $wuxSelect('#wux-select').open({
            value: this.data.currentCourse.courseName,
            options: option,
            onConfirm: (value, index, options) => {
                let param = {
                    courseId: value,
                    courseName: options[index].title,
                };
                this.setData({
                    currentCourse: param,
                });
            },
        })
    },

    onStartTimeChange({ detail: { value } }) {
        let startTime = formatDate(value);
        this.setData({ startTime });
    },

    onEndTimeChange({ detail: { value } }) {
        let endTime = formatDate(value);
        this.setData({ endTime });
    },


    onTypeChange({ detail: { checked, index, value } }) {
        let acceptFileType = this.data.acceptFileType;
        acceptFileType[index] = checked ? value : '';
        this.setData({ acceptFileType });
    },

    // /* util */
    // formatDate([year, month, day, hour, minue]) {
    //     let dateString = year + '-' + this.formatNum(month) + '-' + this.formatNum(day) + ' ' + this.formatNum(hour) + ':' + this.formatNum(minue);
    //     return dateString;
    // },

    // formatNum(num) {
    //     return num < 10 ? '0' + num : num;
    // },
})