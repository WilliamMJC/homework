//app.js
import Api from './utils/api.js';
App({
    onLaunch: function () {
        const that = this;
        wx.getSystemInfo({
            success: function (res) {
                that.globalData.systemInfo = { windowWidth: res.windowWidth, windowHeigth: res.windowHeight };
            },
        });

        // let data = {
        //     courseId: 1,
        //     taskName: 'test111',
        //     startTime: '2018-09-08 17:34:00',
        //     endTime: '2018-09-15 17:34:00',
        // }
        // Api.createTask(data)
        //     .then(res => {
        //         console.log(res);
        //     });

    },
    globalData: {
        systemInfo: {},
    },
})