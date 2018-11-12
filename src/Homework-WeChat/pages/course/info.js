import Api from '../../utils/api.js';
import { $wuxToast } from '../../lib/wux-weapp-master/index';

Page({
    data: {
        userInfo: {},
        size: 0,
        total: 0,
    },

    onLoad(option) {
        let { isTeacher, courseId, userId, total } = option;
        this.getUserInfo(courseId, userId);
        this.setData({
            courseId,
            total,
            isTeacher: isTeacher === 'true',
        });
    },

    /* 网络请求 */
    getUserInfo(courseId, userId) {
        Api.getUserinfoInCourse(courseId, userId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({ userInfo: res.data });
                }
            })
            .catch(res => {
                console.error(`info.js getUserInfo(): ${res}`);
            })
            .finally(() => {

            });
    },

    /* 事件处理函数 */
    onHomeworkTap({ detail }) {
        if(this.data.userInfo.homeworkDtoList[detail] === null) return;
        let { userId, fileType } = this.data.userInfo.homeworkDtoList[detail],
            courseId = this.data.courseId,
            taskId = detail + 1;


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
                            console.log(e);
                            $wuxToast().show({
                                type: 'cancel',
                                duration: 1500,
                                color: '#fff',
                                text: '打开文档失败',
                            });
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