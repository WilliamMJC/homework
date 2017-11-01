// homework.js
// pages/conference/conference.js
var serverUtil = require('../../utils/server.js')
var base64 = require("../example/images/base64");
var app = getApp()
Page({
  data: {
    key: '',
    likes: 0,
    isLike: 0,
    favourites: 0,
    isFavour: 0,
    copies: 0,
    animationCopy: {},
    iconCopy: 'copy24.png',
    iconLike: 'like64.png',
    iconFavour: 'star64.png',
    icon: base64.icon20
  },
  onLoad: function (options) {
    var key = options.key;
    var work = serverUtil.homeworks(key);
    this.setData({
      key: key,
      likes: work.likes,
      favourites: work.favourites,
      work: work
    })
    var animation = wx.createAnimation({
      duration: 500,
      timingFunction: 'ease',
    })
    this.animation = animation
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },
  onShareAppMessage: function (res) {
    var that = this;
    //console.dir(res)
    if (res && res.from === 'button') {
      // 来自页面内转发按钮
      console.log(res.target)
    }
    return {
      title: that.data.work.cnName,
      path: '/pages/index/homework?key=' + that.data.key,
      success: function (res) {
        // 转发成功
        console.dir(res)
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },

  onCopyUrl: function (event) {
    var that = this;
    // 复制到剪贴板
    var work = that.data.work
    var copies = that.data.copies
    that.animation.scale(2, 2).step()
    that.animation.scale(1, 1).step()
    wx.setClipboardData({
      data: work.website,
      success: function (res) {
        wx.showToast({
          title: '网址已复制',
          icon: 'success',
          duration: 2000
        })
        that.setData({
          isCopy: 1,
          copies: copies + 1,
          iconCopy: 'copy24_on.png',
          animationCopy: that.animation.export()
        })
      }
    })
  },

  onFavourTap: function (event) {
    var that = this;
    var favourites = that.data.favourites
    if (that.data.isFavour) {
      // 删除收藏
      this.setData({
        isFavour: 0,
        iconFavour: 'star64.png',
        favourites: favourites - 1
      })
    } else {
      // 保存收藏
      // 修改收藏数
      this.setData({
        isFavour: 1,
        iconFavour: 'star64_on.png',
        favourites: favourites + 1
      })
    }
  },

  onLikeTap: function (event) {
    var that = this;
    var likes = that.data.likes ? that.data.likes : 0;
    console.dir(likes);
    that.animation.scale(2, 2).step();
    that.animation.scale(1, 1).step();
    if (that.data.isLike) {
      this.setData({
        isLike: 0,
        iconLike: 'like64.png',
        likes: likes - 1,
        animationLike: that.animation.export()
      })
    } else {
      this.setData({
        isLike: 1,
        iconLike: 'like64_on.png',
        likes: likes + 1,
        animationLike: that.animation.export()
      })
    }
  }
})