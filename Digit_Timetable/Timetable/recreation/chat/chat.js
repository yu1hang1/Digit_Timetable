Page({
  data:{
    message:[],
    inputMsg:"",
    scrollTop:0
  },
  onLoad:function(options){
    var message = wx.getStorageSync('message');
    var top = message.length * 100;
    this.setData({
      message:message || [],
      scrollTop:top
    })
    
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
    wx.getSystemInfo({
      success: (res) => {
        console.log(res)
        this.setData({
          scrollHeight: res.windowHeight - (100 * res.windowWidth / 750 ) //80为顶部搜索框区域高度 rpx转px 屏幕宽度/750
        });
      }
    })
  },
  onUnload:function(){
    wx.setStorageSync('message',this.data.message);
  },
  inputMsg:function(e){
   this.setData({
     inputMsg:e.detail.value
   })
  },
  sendMessage:function(e){
    this.setData({
     inputMsg:e.detail.value.input
   })
    var that = this;
    if(this.data.inputMsg != ""){
      var msg = { type: 0, src: Constant.host + 'images/chat1.png',content:this.data.inputMsg};
      //发送信息
      this.setMessage(msg);
      //回复
      wx.request({
        url: Constant.host + 'chat',
        header:{"Content-type":"application/json"},
        data:{key:"fa7f4d06b0a24b479d29ea0a01672350",info:msg.content},
        success:function(data){
          console.log("cc" + data.data.session)
          var reply = { type: 1, src: Constant.host + 'images/chat2.png', content: data.data.session};
          that.setMessage(reply);
          that.setData({
            scrollTop:that.data.scrollTop+300
          })
        }
      })
    }
  },
  setMessage:function(msg){
    var msgList = this.data.message;
    msgList.push(msg);
    this.setData({
      message:msgList,
      inputMsg:"",
    })
  }
})
var Constant = require('../../config.js');