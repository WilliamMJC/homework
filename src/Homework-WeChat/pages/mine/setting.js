// pages/mine/setting.js
Page({
  data:{
    role: '',
    isStudent: false,
    isTeacher: false
  },
  onLoad: function (options) {
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
      }
    }) 
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  },

  inputSchool:function(e) {
    this.saveInput('school',e.detail.value);
  },
  inputStudentId: function (e) {
    this.saveInput('studentId', e.detail.value);
  },
  inputEmail: function (e) {
    this.saveInput('email', e.detail.value);
  },
  inputIntroduction: function (e) {
    this.saveInput('introduction', e.detail.value);
  },
  saveInput : function(name, value) {
    var that = this;
    that.data[name]=value;
  },
  saveSetting:function(e) {
    var that = this;
    //console.dir(that.data)
    // 保存到缓存中
    var setting = {
      school: that.data.school,
      email: that.data.email,
      studentId: that.data.studentId,
      introduction: that.data.introduction
    };
    wx.setStorage({
      key: "setting",
      data: setting,
      success: function () {
        // TODO 发送到服务器
        wx.showToast({
          title: '保存成功',
          icon: 'success',
          duration: 2000
        })
      }
    });
  }
})