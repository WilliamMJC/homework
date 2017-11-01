// 发送服务器请求
function sync2server(config) {
  // 请求资源：
  // setting 个人设置
  var resource = config.resource;
  var data = config.data;
  console.dir(config)

  return {};
}

function homeworks(key, isSearch) {
  var homeworks = [{
    name: 'UML-1：用例建模',
    course: 'UML与可视化建模',
    deadline: '2017-03-03',
    total: 50,
    submits: 50,
    received: true,
    likes: 22
  }, {
    name: 'Android-1：开发环境',
    course: '移动应用设计与开发',
    deadline: '2017-03-03',
    total: 50,
    submits: 49,
    received: true,
    likes: 222
  }, {
    name: 'UML-2：过程建模',
    course: 'UML与可视化建模',
    deadline: '2017-03-17',
    total: 50,
    submits: 45,
    received: true,
    likes: 221
  }, {
    name: 'Android-2：组件编程',
    course: '移动应用设计与开发',
    deadline: '2017-03-17',
    total: 50,
    submits: 46,
    received: true,
    likes: 220
  }, {
    name: 'UML-3：结构建模',
    course: 'UML与可视化建模',
    deadline: '2017-03-31',
    total: 50,
    submits: 44,
    received: false,
    likes: 33
  }, {
    name: 'Android-3：界面编程',
    course: '移动应用设计与开发',
    deadline: '2017-03-31',
    total: 50,
    submits: 40,
    received: true,
    likes: 33
  }, {
    name: 'UML-4：交互建模',
    course: 'UML与可视化建模',
    deadline: '2017-04-14',
    total: 50,
    submits: 33,
    received: true,
    likes: 33
  }, {
    name: 'Android-4：存储编程',
    course: '移动应用设计与开发',
    deadline: '2017-04-14',
    total: 50,
    submits: 44,
    received: true,
    likes: 33
  }, {
    name: 'UML-5：状态建模',
    course: 'UML与可视化建模',
    deadline: '2017-04-28',
    total: 50,
    submits: 45,
    received: true,
    likes: 33
  }, {
    name: 'Android-5：数据库编程',
    course: '移动应用设计与开发',
    deadline: '2017-04-28',
    total: 50,
    submits: 36,
    received: true
  }, {
    name: 'UML-5：综合实验',
    course: 'UML与可视化建模',
    deadline: '2017-05-12',
    total: 50,
    submits: 46,
    received: false
  }, {
    name: 'Android-6：网络编程',
    course: '移动应用设计与开发',
    deadline: '2017-05-12',
    total: 50,
    submits: 48,
    received: true
  }, {
    name: 'Android-7：设备编程',
    course: '移动应用设计与开发',
    deadline: '2017-05-12',
    total: 50,
    submits: 11,
    received: false
  }, {
    name: 'Android-8：综合实验',
    course: '移动应用设计与开发',
    deadline: '2017-06-26',
    total: 50,
    submits: 22,
    received: false
  }]
  if (key) {
    var lessWorks = [];
    for (var i = 0; i < homeworks.length; i++) {
      var work = homeworks[i]
      if (!isSearch) {
        if (work.name == key) {
          return work;
        }
      } else if (work.name.indexOf(key) > -1) {
        // is search
        lessWorks[lessWorks.length] = work;
      }
    }
    if (isSearch) {
      return lessWorks;
    }
  } else {
    return homeworks;
  }
}

function tags() {
  var tags = [{
    text: '人工智能',
    index: 6301
  }, {
    text: '肿瘤医学',
    index: 5112
  }, {
    text: '深度学习',
    index: 2301
  }, {
    text: '生物制药',
    index: 1200
  }, {
    text: '生物识别',
    index: 500
  }, {
    text: '自动驾驶',
    index: 300
  }, {
    text: '农业信息化',
    index: 112
  }, {
    text: '自动化种植',
    index: 50
  }];
  return tags
}

module.exports = {
  sync2server: sync2server,
  homeworks: homeworks,
  tags: tags
}