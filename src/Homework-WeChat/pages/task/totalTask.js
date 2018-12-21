import Api from '../../utils/api.js';
import { $wuxDialog, $wuxLoading, $wuxToast } from '../../lib/wux-weapp-master/index';
let wxCharts = require('../../lib/wxcharts.js'),
  App = getApp();

Page({
  data: {
    TotalTask: {},
    courseName: null,
  },

  onLoad({ courseId, courseName }) {
    this.getTotalTaskInfo(courseId);
    this.setData({
      courseName: courseName
    })
  },

  /* 网络请求 */
  getTotalTaskInfo(courseId) {
    Api.getTotalTaskInfo(courseId)
      .then(res => {
        if (res.code !== 0) {
          return Promise.reject(res);
        } else {
          this.setData({ TotalTask: res.data });

          new wxCharts({
            animation: true,
            canvasId: 'pieCanvas',
            type: 'pie',
            series: [{
              name: '已提交' + res.data.receive + "份",
              data: parseInt(res.data.receive),
              color: '#19be6b',
            }, {
              name: '未提交' + (res.data.total - res.data.receive) + "份",
              data: parseInt(res.data.total - res.data.receive),
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

})