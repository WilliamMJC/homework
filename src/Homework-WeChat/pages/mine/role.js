// role.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    role: '',
    isDisabled: false,
    radioItems: [{
      value: 'teacher',
      name: '教师',
      checked: false
    }, {
      value: 'student',
      name: '学生',
      checked: false
    }]  
  },

  /**
   * 生命周期函数--监听页面加载
   */ 
  onLoad: function (options) {
    var that = this;
    // 从缓存取回身份设置
    wx.getStorage({
      key: 'role',
      success: function (res) {
        //console.log(res.data)
        var role = res.data;
        var radioItems = that.data.radioItems;
        for (var i = 0, len = radioItems.length; i < len; ++i) {
          if (radioItems[i].value==role) {
            radioItems[i].checked=true;
            break;
          }
        }
        that.setData({
          role: role,
          isDisabled: !!role,
          radioItems: radioItems
        });
      }
    })  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  radioChange: function (e) {
    //console.log('radio发生change事件，携带value值为：', e.detail.value);

    var radioItems = this.data.radioItems;
    for (var i = 0, len = radioItems.length; i < len; ++i) {
      radioItems[i].checked = radioItems[i].value == e.detail.value;
    }

    this.setData({
      radioItems: radioItems
    });
  },

  saveRole : function(e) {
    var that = this;
    // 取回表单设置
    var role = '';
    var radioItems = that.data.radioItems;
    for (var i = 0, len = radioItems.length; i < len; ++i) {
      if (radioItems[i].checked) {
        role = radioItems[i].value;
        break;
      }
    }
    if (role) { // 已选择
      var roleName = role==='teacher'?'教师':'学生';
      wx.showModal({
        title: '确认',
        content: '确定要设置身份为【' + roleName + '】吗？',
        success: function (res) {
          if (res.confirm) {
            //console.log('用户点击确定')
            that.saveToStorage(role);
          } else if (res.cancel) {
            //console.log('用户点击取消')
          }
        }
      });
    } else { // 未选择
      wx.showToast({
        title: '请选择一种身份！',
        icon: 'error',
        duration: 2000
      })
    }
  },

  saveToStorage: function (role) {
    var that = this;
    // 保存在缓存中
    wx.setStorage({
      key: "role",
      data: role,
      success: function () {
        that.setData({
          role: role,
          isDisabled: true
        });
        wx.showToast({
          title: '保存成功',
          icon: 'success',
          duration: 2000
        })
      }
    });
  }
})