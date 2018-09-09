// pages/uestcAnnounce/uestcAnnounce.js
Page({
  data: {
    imgUrls: [
      'https://67334961.sutthl.com/images/news1.png',
      'https://67334961.sutthl.com/images/news2.jpg',
      'https://67334961.sutthl.com/images/news3.jpg',

    ],
    caiItems: [],
    loading: true,
    hasMore: false,
    page: 1,
    news_id:0,
    imageimageList: [],
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log("111")
    this.setData({
      page: 1,
      news_id:0,
      caiItems: [],
      imageimageList: []
    })
    that = this;
    this.getDataFromServer(that, this.data.news_id);
    //this.getImageFromServer(that, this.data.page)

  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
    wx.getSystemInfo({
      success: (res) => {
        console.log(res)
        this.setData({
          scrollHeight: res.windowHeight 
        });
      }
    })
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },



  refresh: function () {
    console.log("下拉刷新....")
    this.onLoad()

  },
  loadMore: function () {

    this.setData({ news_id: this.data.news_id + 5 })

    console.log("上拉拉加载更多...." + this.data.news_id)
    //news_id = news_id+1
    this.getDataFromServer(that, this.data.news_id)
  },
  //获取网络数据的方法
  getDataFromServer: function (that, news_id) {
    that.setData({
      loading: false,
      hasMore: true
    })
    //调用网络请求
    wx.request({
      //连到服务器 获取json格式的文档
      url: Constant.host + "schoolnews",
      data: {
        news_id: news_id,
        newsdetail_id: 0
      },
      header: {
      },
      success: function (res) {
        if (res == null) {
      
          console.error(Constant.ERROR_DATA_IS_NULL);
          return;
        }
        // for(var i=0;i<=res.data.htmlBody.length;i++){
        //     _body:res.data.htmlBody
        // }
        else {
          console.log("daozhe" + res.data.session.length)
          if (res.data.session.length<=5)
          {
            news_id = news_id - 5 + res.data.session.length;
            console.log("daozhe")
          }
          var news = []
          console.log("111")
          news.splice(0, news.length);
          console.log(res.data.session)
          for (var i = 0; i < res.data.session.length; i++) {
            news.push({ title: res.data.session[i][0], time: res.data.session[i][1], desc: res.data.session[i][2], id: res.data.session[i][3] })
          }
          that.setData({
            news_id: news_id,
            caiItems: that.data.caiItems.concat(news), loading: true, hasMore: false
          })
        }

      }
    });
  },
  //获取网络数据的方法 专题图片
  getImageFromServer: function (that, page) {
    that.setData({
      loading: false,
      hasMore: true
    })
    //调用网络请求
    wx.request({
      //连到服务器 获取json格式的文档
      url: Constant.SERVER_ADDRESS + "/uestcSpecial",
      header: {
      },
      success: function (res) {
        if (res == null) {
          console.error(Constant.ERROR_DATA_IS_NULL);
          return;
        }
        // for(var i=0;i<=res.data.htmlBody.length;i++){
        //     _body:res.data.htmlBody
        // }
        that.setData({
          imageimageList: res.data.htmlBody
        })
      }
    });
  },
  //点击 跳转到具体页面
  onItemClick: function (event) {
    //   var targetUrl="/pages/uestcAnnounceDetail/uestcAnnounceDetail"
    var targetUrl = "/pages/focusNewsDetail/focusNewsDetail"
    if (event.currentTarget.dataset.id != null)
      var id = event.currentTarget.dataset.id;
    console.log("daozhe" + id)
      wx.navigateTo({
        url: '../schoolnewsdetail/schoolnewsdetail?id=' + id
      });
  },
})

var that;
//获取配置的的 全局常量
var Constant = require('../../config.js');