import Api from '../../utils/api.js';
import { $wuxDialog, $wuxLoading, $wuxToast } from '../../lib/wux-weapp-master/index';

Page({
    data: {
        task: {},
        acceptFileType: '',

        size: 0,
        spinning: false,
    },

    onLoad(option) {
        let { taskId, courseId, size } = option;
        this.setData({
            taskId,
            courseId,
            size,
        });
    },
    onShow() {
        let setting = wx.getStorageSync('setting');
        if (!setting) {
            $wuxDialog().open({
                resetOnClose: true,
                title: '未设置个人信息',
                content: '未设置个人信息不能查看作业信息，前往设置？',
                buttons: [{
                    text: '回首页',
                    type: 'default',
                    onTap() {
                        wx.switchTab({
                            url: '/pages/task/taskList',
                        });
                    }
                },
                {
                    text: '确定',
                    type: 'primary',
                    onTap() {
                        wx.navigateTo({
                            url: '/pages/mine/setting',
                        })
                    },
                },
                ],
            });
        } else {
            this.getTaskInfo(this.data.courseId, this.data.taskId);
        }
    },

    /* 网络请求 */
    getTaskInfo(courseId, taskId) {
        const that = this;
        Api.getTaskInfo(courseId, taskId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({ task: res.data });
                    let arr = res.data.acceptType.toString(2).split('').reverse(),
                        acceptFileType = '';
                    if (arr[0] === '1') acceptFileType += '.doc ';
                    if (arr[1] === '1') acceptFileType += '.docx ';
                    if (arr[2] === '1') acceptFileType += '.pdf ';
                    if (arr[3] === '1') acceptFileType += '超大附件';
                    this.setData({ acceptFileType });
                }
            })
            .catch(res => {
                console.error(`task getTaskInfo(): ${res}`);
                if (res.code === 102) {
                    $wuxDialog().open({
                        resetOnClose: true,
                        title: '加入课程后方可查看',
                        maskClosable: false,
                        content: '你尚未加入此课程，无法查看此作业信息，是否加入？',
                        buttons: [{
                            text: '回首页',
                            type: 'default',
                            onTap() {
                                wx.switchTab({
                                    url: '/pages/task/taskList'
                                });
                            },
                        },
                        {
                            text: '确定',
                            type: 'primary',
                            onTap() {
                                Api.joinCourse(courseId)
                                    .then(res => {
                                        if (res.code !== 0) {
                                            return Promise.reject(res);
                                        } else {
                                            that.getTaskInfo(courseId, taskId);
                                        }
                                    })
                                    .catch(res => {
                                        console.error(`task.js Api.joinCourse(): ${res}`)
                                        if (res) {
                                            $wuxToast().show({
                                                type: 'cancel',
                                                duration: 1000,
                                                color: '#fff',
                                                text: res.msg,
                                            });
                                            setTimeout(() => {
                                                wx.switchTab({
                                                    url: '/pages/task/taskList'
                                                });
                                            }, 1000);
                                        }
                                    });
                            },
                        },
                        ],
                    })
                }
            });
    },

    /* 事件处理函数 */
    onUpdateTap() {
        let wuxLoading = $wuxLoading(),
            { courseId, taskId } = this.data.task;
        wuxLoading.show({
            text: '数据加载中',
        })
        Api.updateMail(courseId, taskId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    let temp = 'task.receive',
                        size = parseInt(this.data.size);
                    if (this.data.isTeacher) {
                        size = res.data.length;
                    } else {
                        if (this.data.task.receive.length === 0) {
                            size += res.data.length;
                        }
                    }
                    wuxLoading.hide();
                    $wuxToast().show({
                        type: 'success',
                        duration: 1500,
                        color: '#fff',
                        text: '作业信息更新成功',
                    });
                    this.setData({
                        [temp]: res.data,
                        size,
                    });
                }
            })
            .catch(res => {
                wuxLoading.hide();
                console.error(`task.js onUpdateTap: ${res}`);
                $wuxToast().show({
                    type: 'cancel',
                    duration: 1500,
                    color: '#fff',
                    text: '邮件更新失败',
                });
            });
    },

    onGetHomeworkTap() {
        let wuxLoading = $wuxLoading(),
            { courseId, taskId } = this.data.task;
        wuxLoading.show({
            text: '数据加载中',
        });
        Api.getConstruction(courseId, taskId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    wuxLoading.hide();
                    $wuxToast().show({
                        type: 'success',
                        duration: 1500,
                        color: '#fff',
                        text: '邮件请求已接收，稍后将会发送到您的私人邮箱',
                    });  
                }
            })
            .catch(res => {
                wuxLoading.hide();
                console.error(`task.js onGetHomeworkTap: ${res}`)
                $wuxToast().show({
                    type: 'cancel',
                    duration: 1500,
                    color: '#fff',
                    text: '作业邮件发送失败',
                });
            })
    },

    onEndTap() {
        let wuxLoading = $wuxLoading(),
            { courseId, taskId } = this.data.task;
        wuxLoading.show({
            text: '数据加载中',
        });
        $wuxDialog().confirm({
            resetOnClose: true,
            closable: true,
            title: '是否结束任务',
            content: '点击确定即可结束此任务',
            onConfirm(e) {
                Api.endTask(courseId, taskId)
                    .then(res => {
                        if (res.code !== 0) {
                            return Promise.reject(res);
                        } else {
                            wuxLoading.hide();
                            wx.redirectTo({
                                url: `/pages/task/endTask?courseId=${courseId}&taskId=${taskId}`,
                            });
                        }
                    })
                    .catch(res => {
                        wuxLoading.hide();
                        console.error(`task onEndTap(): ${res}`);
                        if (res) {
                            $wuxToast().show({
                                type: 'cancel',
                                duration: 1500,
                                color: '#fff',
                                text: res.msg,
                            });
                        }
                    });
            },
            onCancel(e) {
                console.log('谢谢你不吃之恩！')
            },
        });


    },

    onHomeworkTap({ currentTarget: { dataset: { index: detail } } }) {
        let { userId, fileType } = this.data.task.receive[detail],
            { courseId, taskId } = this.data;

        Api.getHomework(courseId, taskId, userId)
            .then(res => {
                if (res.statusCode !== 200) {
                    return Promise.reject(res);
                } else {
                    wx.openDocument({
                        filePath: res.tempFilePath,
                        fileType: fileType,
                        success: function (res) {
                            console.log('打开文档成功');
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
            });
    },

    onShareAppMessage: function (res) {
        if (res.from === 'button') {
            // 来自页面内转发按钮
            console.log(res.target);
        }
        return {
            title: this.data.task.taskName,
            path: `/pages/task/task?courseId=${this.data.task.courseId}&taskId=${this.data.task.taskId}&size=${this.data.size}`,
        }
    },
})