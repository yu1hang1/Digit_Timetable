//logs.js
var util = require('../../utils/util.js')
Page({
  
  onLoad: function (option) {
   
    
    this.setData({
       kcm:option.kcm,
       js:option.js,
       ls:option.ls,
       time:option.time,
       zs:option.zs,
       sx:option.sx,
      })
  
  },
  yuyueSubmit:function(){
        var self=this;
  
        wx.switchTab({
    
      url: '../index/index'
    })
  }
  
})
