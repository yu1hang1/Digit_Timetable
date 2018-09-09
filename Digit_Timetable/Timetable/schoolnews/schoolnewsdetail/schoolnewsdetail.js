//在使用的View中引入WxParse模块


Page({
  data:{
    title: "",
    time: "",
    desc: ""

  },
  
  onLoad: function (option){

  

    this.setData({
      title:"",
      time: "",
      desc: ""
    })
    var title;
    var time
    var desc
    // 页面初始化 options为页面跳转所带来的参数
    // 页面初始化 options为页面跳转所带来的参数

    var self = this;

    wx.request({
      url: Constant.host + "schoolnews",
      data: {
        news_id: 0,
        newsdetail_id: option.id
      },
      header: {
        'content-type': 'application/json;charset=UTF-8;'
      },
      success: function (res) {
        title = res.data.session[0][0],
          time = res.data.session[0][1],
          desc = res.data.session[0][2]
        self.setData({
          title: title,
          time: time,
          desc: desc
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
  }
})
var Constant = require('../../config.js');