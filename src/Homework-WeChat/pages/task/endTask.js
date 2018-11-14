import Api from '../../utils/api.js';
import { $wuxDialog, $wuxLoading, $wuxToast } from '../../lib/wux-weapp-master/index';
let wxCharts = require('../../lib/wxcharts.js'),
    App = getApp();

Page({
    data: {
        endTask: {},    
    },

    onLoad({ courseId, taskId }) {
        this.getEndTaskInfo(courseId, taskId);
    },

    /* 网络请求 */
    getEndTaskInfo(courseId, taskId) {
        Api.getEndTaskInfo(courseId, taskId)
            .then(res => {
                if(res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({ endTask: res.data });

                    new wxCharts({
                        animation: true,
                        canvasId: 'pieCanvas',
                        type: 'pie',
                        series: [ {
                            name: '已提交',
                            data: parseInt(res.data.receive),
                            color: '#19be6b',
                        },{
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
                if(res) {

                }
            });
    },

    /* 事件处理函数 */
    onHomeworkTap({ detail }) {
        let { userId, fileType} = this.data.endTask.homeworkDtoList[detail],
            { courseId, taskId } = this.data.endTask;
        
        if(fileType === null || fileType === '') return;

        Api.getHomework(courseId, taskId, userId)
            .then(res => {
                if(res.statusCode !== 200) {
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
})