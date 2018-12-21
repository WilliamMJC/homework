import Api from '../../utils/api.js';
import { $wuxDialog, $wuxToast, $wuxLoading } from '../../lib/wux-weapp-master/index';

Page({
    data: {
        currentTab: 0,

        spinning: false,

        /* floatButton 配置 */
        actionVisible: false,
        actions: [
            // {
            //     label: '创建签到',
            //     icon: '/images/task.png'
            // },
            {
                label: '发布作业',
                icon: '/images/add.png'
            },
        ],
        floatButtonHidden: true,

        /* prompt配置 */
        memberPromptButtons: [
            {
                text: '转发',
                openType: 'share',
            }
        ],
        resourcePromptButtons: [
            {
                text: '查看帮助'
            }
        ],

        flag: false,
        loadmore: true,
        stuListEnd: true,

        courseId: -1,
        courseName: '',
        courseDesc: '',
        isCourseEnd: false,
        isPublic: false,
        stuIdList: [],
        taskList: [],
        isTaskListEmpty: false,
        resourceList: [],
        teacher: [],
        stuList: [],
        stuLength: 0,

        isTeacher: false,
        isStudent: false,

        btnLoading: false,
    },

    onLoad(option) {
        let { courseId } = option;
        this.setData({ courseId });
    },

    onShow() {
        let setting = wx.getStorageSync('setting');
        if (!setting) {
            $wuxDialog().open({
                resetOnClose: true,
                title: '未设置个人信息',
                content: '未设置个人信息不能查看课程信息，前往设置？',
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
            this.getCourseInfo(this.data.courseId);
        }
    },

    /* 网络请求 */
    getCourseInfo(courseId) {
        const that = this;
        Api.getCourseInfo(courseId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    this.setData({
                        courseId: res.data.courseId,
                        courseName: res.data.courseName,
                        courseDesc: res.data.courseDesc,
                        isPublic: res.data.isPublic,
                        isCourseEnd: res.data.isCourseEnd,
                        taskList: res.data.taskList.reverse(),
                        isTaskListEmpty: res.data.taskList.length === 0 ? true : false,
                        resourceList: res.data.resourceList,
                        stuIdList: res.data.stuIdList,
                        teacher: res.data.teacher,
                        stuList: res.data.stuList,
                        isTeacher: res.data.isTeacher,
                        isStudent: res.data.isStudent,
                        stuLength: res.data.stuLength,
                        flag: false,
                    });
                    wx.setNavigationBarTitle({
                        title: res.data.courseName,
                    });
                }
            })
            .catch(res => {
                console.error("course, getCourseInfo:" + res);
                if (res.code === 102) {
                    $wuxDialog().open({
                        resetOnClose: true,
                        title: '加入课程后方可查看',
                        maskClosable: false,
                        content: '你尚未加入此课程，无法查看此课程信息，是否加入？',
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
                                            that.getCourseInfo(courseId);
                                        }
                                    })
                                    .catch(res => {
                                        console.error(`course.js Api.joinCourse(): ${res}`)
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
            })
            .then(() => {
                if (!this.data.isPublic && (this.data.stuIdList.length === 0) && this.data.isTeacher) {
                    const hideDialog = $wuxDialog().open({
                        resetOnClose: true,
                        title: '配置学号列表',
                        content: '请发送学号配置邮件到你的作业邮箱，否则你的学生将无法加入此课程！',
                        verticalButtons: !0,
                        buttons: [{
                            text: '我已发送',
                            bold: !0,
                            onTap() {
                                that.initSc(that.data.courseName, that.data.courseId);
                            },
                        },
                        {
                            text: '如何配置',
                            bold: !0,
                            onTap() {
                                wx.navigateTo({
                                    url: '/pages/help/help3',
                                });
                            }
                        },
                        {
                            text: '取消',
                            bold: !0,
                            onTab() {
                                hideDialog();
                            },
                        },
                        ],
                    });
                }
            });

    },

    initSc(courseName, courseId) {
        let wuxLoading = $wuxLoading()
        wuxLoading.show({
            text: '数据加载中',
        })
        Api.getInitSc(courseName, courseId)
            .then(res => {
                if (res.code !== 0) {
                    return Promise.reject(res);
                } else {
                    wuxLoading.hide();
                    $wuxToast().show({
                        type: 'success',
                        duration: 1500,
                        color: '#fff',
                        text: '已完成',
                    });
                    this.setData({
                        stuIdList: res.data,
                        currentTab: 0,
                    })
                }
            })
            .catch(res => {
                wuxLoading.hide();
                console.error(res);
                if (res) {
                    $wuxToast().show({
                        type: 'cancel',
                        duration: 1000,
                        color: '#fff',
                        text: res.msg,
                    });
                }
            });
    },

    /* 事件处理函数 */

    onTabChange({ detail }) {
        if (detail === 0) {
            this.setData({
                currentTab: detail,
                floatButtonHidden: true,
            })
        } else {
            this.setData({
                currentTab: detail,
                floatButtonHidden: false
            });
        }
    },

    actionButtonTap({ detail: { index } }) {
        if (index === 0) {
            wx.navigateTo({
                url: `/pages/task/addTask?courseId=${this.data.courseId}&courseName=${this.data.courseName}`,
            })
        }
    },

    onEndCourseTap() {
        const that = this;
        $wuxDialog().confirm({
            resetOnClose: true,
            closable: true,
            title: '结束此课程？',
            content: '课程结束后将无法再对此课程进行操作，是否继续？',
            onConfirm(e) {
                that.setData({ btnLoading: true });
                Api.endCourse(that.data.courseId)
                    .then(res => {
                        if (res.code !== 0) {
                            return Promise.reject(res);
                        } else {
                            that.setData({
                                isCourseEnd: true,
                                btnLoading: false,
                            })
                        }
                    })
                    .catch(res => {
                        console.error(`course.js onEndCourseTap(): ${res}`);
                        that.setData({ btnLoading: false });
                    });
            },
            onCancel(e) {

            },
        });
    },

    onDeleteCourseTap() {
        const that = this;
        $wuxDialog().confirm({
            resetOnClose: true,
            closable: true,
            title: '删除此课程？',
            content: '删除此课程意味着此课程所有相关信息都将被删除，是否继续？',
            onConfirm() {
                that.setData({ btnLoading: true });
                Api.deleteCourse(that.data.courseId)
                    .then(res => {
                        if (res.code !== 0) {
                            return Promise.reject(res);
                        } else {
                            wx.switchTab({
                                url: '/pages/course/courseList',
                            });
                        }
                    })
                    .catch(res => {
                        console.error(`course onDeleteCourseTap(): ${res}`);
                        this.setData({ btnLoading: false });
                        if (res) {
                            $wuxToast().show({
                                type: 'cancel',
                                duration: 1000,
                                color: '#fff',
                                text: res.msg,
                            });
                        }
                    });
            },
        });
    },

    onQuitCourseTap() {
        const that = this;
        $wuxDialog().confirm({
            resetOnClose: true,
            closable: true,
            title: '退出此课程？',
            content: '退出此课程意味着你在此课程所有相关信息都将被删除，是否继续？',
            onConfirm() {
                Api.quitCourse(that.data.courseId)
                    .then(res => {
                        if (res.code !== 0) {
                            return Promise.reject(res);
                        } else {
                            wx.switchTab({
                                url: '/pages/course/courseList',
                            });
                        }
                    })
                    .catch(res => {
                        console.error(`course.js onQuitCourseTap(): ${res}`);
                        this.setData({ btnLoading: false });
                        if (res) {
                            $wuxToast().show({
                                type: 'cancel',
                                duration: 1000,
                                color: '#fff',
                                text: res.msg,
                            });
                        }
                    });
            },
        });
    },

    onCourseCodeCellClick() {
        wx.setClipboardData({
            data: this.data.courseId.toString(),
            success: function (res) {

            }
        })
    },

    onStuIdCellTap({ currentTarget: { dataset: { index } } }) {
        const that = this;
        let oldStuId = this.data.stuIdList[index];
        $wuxDialog().prompt({
            resetOnClose: true,
            title: '修改学号',
            fieldtype: 'number',
            password: false,
            defaultText: oldStuId,
            placeholder: '请输入新的学号',
            maxlength: -1,
            onConfirm(e, response) {
                let wuxLoading = $wuxLoading()
                wuxLoading.show({
                    text: '努力加载中',
                });
                Api.updateSC(that.data.courseId, oldStuId, response)
                    .then(res => {
                        if (res.code !== 0) {
                            return Promise.reject(res);
                        } else {
                            wuxLoading.hide();
                            $wuxToast().show({
                                type: 'success',
                                duration: 1000,
                                color: '#fff',
                                text: res.msg,
                            });
                            let temp = `stuIdList[${index}]`;
                            that.setData({ [temp]: response });
                        }
                    })
                    .catch(res => {
                        wuxLoading.hide();
                        console.error(res);
                        if (res) {
                            $wuxToast().show({
                                type: 'cancel',
                                duration: 1000,
                                color: '#fff',
                                text: res.msg,
                            });
                        }
                    });
            },
        });
    },

    onReachBottom() {
        let flag = this.data.flag,
        currentTab = this.data.currentTab;
        if (flag || currentTab !== 2) { return; }
        else { this.setData({ flag: true }) };
        let size = this.data.stuList.length;
        if (size % 20 !== 0) {
            if (size > 20) {
                this.setData({ stuListEnd: false });
                setTimeout(() => {
                    this.setData({ stuListEnd: true });
                }, 2000);
            }
        } else {
            let courseId = this.data.courseId,
                currPage = size / 20;

            this.setData({ loadmore: false });
            Api.queryCMByPage(courseId, currPage, 20)
                .then(res => {
                    if (res.code !== 0) {
                        return Promise.reject(res);
                    } else {
                        if (res.data.length > 0) {
                            let arr = [].concat(this.data.stuList, res.data);
                            this.setData({
                                flag: false,
                                stuList: arr,
                            });
                        } else {
                            this.setData({
                                stuListEnd: false,
                                loadmore: true,
                            });
                            setTimeout(() => {
                                this.setData({ stuListEnd: true });
                            }, 2000);
                        }
                    }
                })
                .catch(res => {
                    console.error(res);
                    if (res) {
                        $wuxToast().show({
                            type: 'cancel',
                            duration: 1000,
                            color: '#fff',
                            text: res.msg,
                        });
                    }
                });
        }
    },

    onShareAppMessage: function (res) {
        if (res.from === 'button') {
            // 来自页面内转发按钮
            console.log(res.target)
        }
        return {
            title: this.data.courseName,
            path: `/pages/course/course?courseId=${this.data.courseId}&total=${this.data.total}` ,
        }
    },

    onCheckTotalHomeworkTap:function(){
      console.log(this.data.courseId)
      wx.navigateTo({
        //需传courseID和taskID
        url: '/pages/task/totalTask?courseId='+this.data.courseId+"&courseName="+this.data.courseName,
      })
    }
})