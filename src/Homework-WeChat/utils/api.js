import request from './request.js';

const api = (function () {
    // const _baseUrl = 'https://homework.infoaas.com/homework';
    const _baseUrl = 'http://localhost:8080/homework';
    const _request = new request;

    return {

        /* 测试相关api */

        /**
         * 测试服务器连接
         *
         * @returns
         * @memberof homework
         */
        test(data) {
            return _request.getRequest(_baseUrl + '/test', undefined, { 'content-type': 'application/json' });
            // return _request.postRequest(_baseUrl + '/test', data, {'content-type': 'application/x-www-form-urlencoded' });
        },

        /**
         * 测试授权
         *
         * @returns
         * @memberof homework
         */
        testAuth(data) {
            return _request.getRequest(_baseUrl + '/testAuth');
        },


        /* 用户相关api */

        /**
         * 用户更新自己的信息
         *
         * @param {*} data
         * @returns
         */
        updateUserInfo(data) {
            return _request.putRequest(_baseUrl + '/user', data);
        },

        /**
         * 获取用户信息
         *
         * @returns
         */
        getUserInfo() {
            return _request.getRequest(_baseUrl + '/user');
        },

        /**
         * 获取用户在此课程的信息
         *
         * @param {*} courseId
         * @param {*} userId
         * @returns
         */
        getUserinfoInCourse(courseId, userId) {
            return _request.getRequest(_baseUrl + '/user/' + courseId + '/' + userId);
        },

        /* 课程相关api */

        /**
         * 创建课程
         *
         * @param {*} data
         * @returns
         */
        createCourse(data) {
            return _request.putRequest(_baseUrl + '/course', data);
        },

        /**
         * 结束课程
         *
         * @param {*} courseId
         * @returns
         */
        endCourse(courseId) {
            return _request.putRequest(_baseUrl + '/course/' + courseId);
        },

        /**
         * 获取用户教授的课程列表
         *
         * @returns
         */
        getTeaCourseList() {
            return _request.getRequest(_baseUrl + '/course/tea');
        },

        /**
         * 获取用户加入的课程列表
         *
         * @returns
         */
        getStuCourseList() {
            return _request.getRequest(_baseUrl + '/course/stu');
        },

        // /**
        //  * 用户加入课程
        //  *
        //  * @param {*} courseCode
        //  * @returns
        //  */
        // joinCourse(courseCode) {
        //     return _request.postRequest(_baseUrl + '/course/' + courseCode);
        // },

        /**
         * 获取课程信息
         *
         * @param {*} courseId
         * @returns
         */
        getCourseInfo(courseId) {
            return _request.getRequest(_baseUrl + '/course/' + courseId);
        },

        /**
         * 删除课程
         *
         * @param {*} courseId
         * @returns
         */
        deleteCourse(courseId) {
            return _request.deleteRequest(_baseUrl + '/course/' + courseId);
        },

        /* task相关 */

        /**
         * 创建task
         *
         * @param {*} data
         * @returns
         */
        createTask(data) {
            return _request.putRequest(_baseUrl + '/task', data);
        },


        /**
         * 查询任务信息
         *
         * @param {*} taskId
         * @param {*} courseId
         * @returns
         */
        getTaskInfo(courseId, taskId) {
            return _request.getRequest(_baseUrl + '/task/' + courseId + '/' + taskId);
        },

        /**
         * 获取用户的任务列表
         *
         * @returns
         */
        getTaskList() {
            return _request.getRequest(_baseUrl + '/task');
        },

        /**
         * 结束任务
         *
         * @param {*} courseId
         * @param {*} taskId
         * @returns
         */
        endTask(courseId, taskId) {
            return _request.putRequest(_baseUrl + '/task/' + courseId + '/' + taskId);
        },

        /**
         * 获取结束任务的详细信息
         *
         * @param {*} courseId
         * @param {*} taskId
         * @returns
         */
        getEndTaskInfo(courseId, taskId) {
            return _request.getRequest(_baseUrl + '/task/' + courseId + '/' + taskId + '/end');
        },

        /* 邮件相关 */

        /**
         * 更新邮件信息
         *
         * @param {*} courseId
         * @param {*} taskId
         * @returns
         */
        updateMail(courseId, taskId) {
            return _request.getRequest(_baseUrl + '/mail/' + courseId + '/' + taskId)
        },

        /* 附件相关 */

        /**
         * 转发附件到教师邮箱
         *
         * @param {*} courseId
         * @param {*} taskId
         * @returns
         */
        getConstruction(courseId, taskId) {
            return _request.getRequest(_baseUrl + '/construction/' + courseId + '/' + taskId);
        },

        /* sc相关 */

        /**
         * 初始化sc
         *
         * @param {*} courseName
         * @param {*} courseId
         * @returns
         */
        getInitSc(courseName, courseId) {
            return _request.getRequest(_baseUrl + '/sc', { courseName, courseId });
        },

        /**
         * 更新sc
         *
         * @param {*} courseId
         * @param {*} oldStuId
         * @param {*} newStuId
         * @returns
         */
        updateSC(courseId, oldStuId, newStuId) {
            return _request.putRequest(_baseUrl + '/sc/' + courseId + '/' + oldStuId, newStuId);
        },

        /* 课程成员相关 */

        /**
         * 学生退出此课程
         *
         * @param {*} courseId
         * @returns
         */
        quitCourse(courseId) {
            return _request.deleteRequest(_baseUrl + '/courseMember/' + courseId);
        },

        /**
         * 用户通过courseId加入课程
         *
         * @param {*} courseId
         */
        joinCourse(courseId) {
            return _request.putRequest(_baseUrl + '/courseMember/' + courseId);
        },

        /**
         * 分页查询课程成员
         *
         * @param {*} courseId
         * @param {*} currPage
         * @param {*} pageSize
         * @returns
         */
        queryCMByPage(courseId, currPage, pageSize) {
            return _request.getRequest(_baseUrl + '/courseMember/' + courseId + '/' + currPage + '/' + pageSize);
        },

        /* homework相关 */

        /**
         * 获取作业文件
         *
         * @param {*} courseId
         * @param {*} taskId
         * @param {*} userId
         * @returns
         */
        getHomework(courseId, taskId, userId) {
            return _request.fileRequest(_baseUrl + '/homework/' + courseId + '/' + taskId + '/' + userId);
        },
    };



})();

export default api;