//index.js
var serverUtil = require('../../utils/server.js')
var base64 = require("../example/images/base64");
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: '学术会议与学术活动',
    userInfo: {},
    windowHeight: 0,
    windowWidth: 0,
    inputShowed: false,
    inputVal: "",
    showMore: false,
    isLower: false,
    isEnd: false,
    homeworks: [],

    role: '',
    isStudent: false,
    isTeacher: false
  },
  onLoad: function (options) {
    //console.log(options.type)
    var that = this
    this.setData({
      icon20: base64.icon20,
      icon60: base64.icon60
    });
    // 调用应用实例的方法获取全局数据
    app.getUserInfo(function (userInfo) {
      // 更新数据
      that.setData({
        userInfo: userInfo
      })
      // 设置窗口大小
      wx.getSystemInfo({
        success: (res) => {
          that.setData({
            windowHeight: res.windowHeight - 5,
            windowWidth: res.windowWidth
          })
          //console.dir(that.data.windowHeight)
        }
      })
    });
  },
  onShow: function() {
    var that = this;
    // 从缓存取回身份设置
    wx.getStorage({
      key: 'role',
      success: function (res) {
        //console.log(res.data)
        var role = res.data;
        that.setData({
          role: role,
          isStudent: role === 'student',
          isTeacher: role === 'teacher'
        });
        // 加载数据
        that.loadHomeworks()
      }
    });
  },
  showInput: function () {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function () {
    this.setData({
      inputVal: "",
      inputShowed: false
    });
  },
  clearInput: function () {
    this.setData({
      inputVal: "",
      searchResults: []
    });
  },
  inputTyping: function (e) {
    var homeworks = serverUtil.homeworks(e.detail.value, 1)
    this.setData({
      inputVal: e.detail.value,
      searchResults: homeworks
    });
  },

  onUpper: function () { },
  onLower: function () {
    var that = this
    that.setData({
      isLower: true
    });
    // load more data
    that.loadMoreHomeworks()
  },
  onScroll: function () { },

  loadHomeworks: function () {
    var that = this;
    var homeworks = serverUtil.homeworks();
    that.setData({
      homeworks: homeworks
    });
    console.dir(homeworks);
  },

  currentPage: 1, // current page
  loadMoreHomeworks: function () {
    var that = this
    var page = that.currentPage++
    var moreHomeworks = serverUtil.homeworks('', page)
    that.setData({
      isLower: false,
      isEnd: moreHomeworks.length == 0,
      homeworks: that.data.homeworks.concat(moreHomeworks)
    })
    //console.dir(page)
  }
})
