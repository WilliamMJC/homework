import Api from '../../utils/api.js';
import { $wuxDialog, $wuxLoading, $wuxToast } from '../../lib/wux-weapp-master/index';
let wxCharts = require('../../lib/wxcharts.js'),
    App = getApp();

Page({
    data: {
        endTask: {},
        showCanvas: 'visible'
    },

    onLoad({ courseId, taskId }) {
        this.getEndTaskInfo(courseId, taskId);
    },

    /* 网络请求 */
    getEndTaskInfo(courseId, taskId) {
        Api.getEndTaskInfo(courseId, taskId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({ endTask: res.data });

                    new wxCharts({
                        animation: true,
                        canvasId: 'pieCanvas',
                        type: 'pie',
                        series: [{
                            name: '已提交',
                            data: parseInt(res.data.receive),
                            color: '#19be6b',
                        }, {
                            name: '未提交',
                            data: parseInt(res.data.unReceive),
                            color: '#ed3f14',
                        },],
                        width: App.globalData.systemInfo.windowWidth,
                        height: 300,
                        dataLabel: true,
                    });
                }
            })
            .catch(res => {
                console.error(res);
                if (res) {

                }
            });
    },

    /* 事件处理函数 */
    onHomeworkTap({ currentTarget: { dataset: { index } } }) {
        let { userId, fileType } = this.data.endTask.homeworkDtoList[index],
            { courseId, taskId } = this.data.endTask;

        if (fileType === null || fileType === '') return;

        Api.getHomework(courseId, taskId, userId)
            .then(res => {
                if (res.statusCode !== 200) {
                    return Promise.reject(res);
                } else {
                    wx.openDocument({
                        filePath: res.tempFilePath,
                        fileType: fileType,
                        success: function (res) {
                            console.log('打开文档成功')
                        },
                        fail(e) {
                            return Promise.reject(e);
                        },
                    });
                }
            })
            .catch(res => {
                console.log(res);
                $wuxToast().show({
                    type: 'cancel',
                    duration: 1500,
                    color: '#fff',
                    text: '打开文档失败',
                });
            })
    },

    onEvalutionTap() {
        let courseId = this.data.endTask.courseId,
            taskId = this.data.endTask.taskId;
        
        const that = this;

        this.setData({
            showCanvas: 'hidden'
        });
        $wuxLoading().show({
            text: '加载中',
        });
        Api.createEvaluation(courseId, taskId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    $wuxLoading().hide();
                    $wuxDialog().open({
                        resetOnClose: true,
                        title: '发送评价邮件',
                        content: '作业评价Excel已发送到您的私人邮箱，请填写评价后将此文件作为附件以“作业名-评价”为主题发送到您的作业邮箱',
                        maskClosable: false,
                        buttons: [
                            {
                                text: '我已填写评价并发送邮件',
                                type: 'primary',
                                onTap() {
                                    $wuxLoading().show({
                                        text: '加载中',
                                    });
                                    Api.completeEvaluation(courseId, taskId)
                                        .then(res => {
                                            if (res.code !== 0) {
                                                return Promise.reject(res);
                                            } else {
                                                $wuxLoading().hide();
                                                $wuxDialog().alert({
                                                    resetOnClose: true,
                                                    title: '成功',
                                                    content: '作业评价正在处理中，稍后即可完成',
                                                    onConfirm(e) {
                                                        that.setData({
                                                            showCanvas: 'visible'
                                                        });
                                                    },
                                                });
                                            }
                                        })
                                        .catch(res => {
                                            $wuxToast().show({
                                                type: 'cancel',
                                                duration: 1500,
                                                color: '#fff',
                                                text: '获取评价Excel失败',
                                            });
                                            $wuxLoading().hide();
                                            that.setData({
                                                showCanvas: 'visible'
                                            });
                                        });
                                },
                            },
                        ],
                    });
                }
            })
            .catch(res => {
                $wuxToast().show({
                    type: 'cancel',
                    duration: 1500,
                    color: '#fff',
                    text: '创建评价excel失败',
                });
                $wuxLoading().hide();
                that.setData({
                    showCanvas: 'visible'
                });
            });
    }
})