var cityData = require('../../js/city.js');
var Constant = require('../../config.js');
var $ = require('../../js/util.js');
var app = getApp();
var schid = 0;
var schname = "请选择你的学校"
Page({

  data: {
    schoolid: null, //学校数据
    schoolname: "请选择你的学校",
    schoolid_index: 0,
    userName: '',
    userm: '',
    yzm: ''
  },

  onReady: function () {
    //初始化数据
    // this.getschoolid();

  },

  pChange1: function (e) {
    wx.redirectTo({
      url: '../logs/logs'
    });

  },

  //用户名、密码有、验证码
  bindChange: function (e) {
    var val = e.detail.value;
    switch (e.target.id) {
      case "userName":
        this.setData({
          userName: val
        });
        break;
      case "userm":
        this.setData({
          userm: val
        });
        break;
      case "yzm":
        this.setData({
          yzm: val
        });
        break;
    }
  },

  alert: function (t) {
    wx.showModal({
      title: "系统提示",
      content: t,
      showCancel: false,
      confirmColor: '#000'
    });
  },

  // 提交登陆信息
  yuyueSubmit: function () {
    var self = this;
    if (!self.data.schoolid_index) {
      self.alert('请选择学校！');
      return;
    }

    if (!self.data.userName) {
      self.alert('请输入您的学号');
      return;
    }
    if (!self.data.userm) {
      self.alert('请输入密码');
      return
    }


    try {
      var schid = wx.getStorageSync('schoolid')
    } catch (e) { }

    wx.showToast({
      title: '提交中...',
      icon: 'loading',
      duration: 10000
    });
    wx.request({
      url: Constant.host + 'logins',
      data: {
        schoolid: schid,
        name: self.data.userName,
        userm: self.data.userm,
        yzm: self.data.yzm,

      },
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        console.log(res.data)
        var data = res.data;
        wx.hideToast();
        if (data.session.id == "no") {
          self.alert("登陆失败！");
          self.setData({
            userName: '',
            userm: '',
            yzm: '',
            yzmview: 'line'
          });
          wx.request({
            url: Constant.host + 'login',
            data: {
              schoolid: schid
            },
            header: {
              'content-type': 'application/json;charset=UTF-8;'
            },
            success: function (res) {
              if (!res.data.session.id)
                var yzmview = "none";
              self.setData({
                yzmview: yzmview,
                face: res.data.session.id,

              });
            }
          })
        }

        //判断登陆成功
        else if (data.session.id) {
          self.setData({
            schoolid_index: 0,
            userName: '',
            userm: '',
            yzm: '',
          });
          self.alert(data.session.id);

          try {
            wx.removeStorageSync('curriculum')
          } catch (e) { }
          wx.request({
            url: Constant.host + 'Results',
            data: {
              schoolid: schid
            },
            header: {
              'content-type': 'application/json;charset=UTF-8;'
            },
            success: function (res) {
              console.log(res.data)
              var data = res.data;
              wx.hideToast();
              var temp = data.session.temp;
              try {
                wx.setStorageSync('curriculum', data.session)
              } catch (e) { }
              wx.switchTab({
                url: '../../pages/index/index'
              })
            }
          })
        }//cichu
        else {
          self.alert("登陆失败！");
          self.setData({
            userName: '',
            userm: '',
            yzm: '',
            yzmview: 'line'
          });
          wx.request({
            url: Constant.host + 'login',
            data: {
              schoolid: schid
            },
            header: {
              'content-type': 'application/json;charset=UTF-8;'
            },
            success: function (res) {
              if (!res.data.session.id)
                var yzmview = "none";
              self.setData({
                yzmview: yzmview,
                face: res.data.session.id,

              });
            }
          })
        }
      }
    })
  },



  /* === 学校选择start === */
  getschoolid: function () {
    this.setData({
      schoolid: cityData.schoolid
    });

  },



  alert: function (t) {
    wx.showModal({
      title: "系统提示",
      content: t,
      showCancel: false,
      confirmColor: '#000'
    });
  },

  //此处链接后获取验证码

  //学校选择
  onShow: function () {



    try {
      schid = wx.getStorageSync('schoolid')
      schname = wx.getStorageSync('schoolname')
    } catch (e) { }
    if (schname == '') {
      schname = "请选择你的学校";
    }

    console.log('--', schid);
    console.log('--', schname);
    this.setData({
      schoolid_index: schid,

      schoolname: "ssss",
      city_index: 0,
      yzmview: 'line'
    });
    var self = this;
    var s;
    wx.request({
      url: Constant.host + 'login',
      data: {
        schoolid: schid,
      },
      header: {
        'content-type': 'application/json;charset=UTF-8;'
      },
      success: function (res) {
        if (!res.data.session.id)
          var yzmview = "none";
        self.setData({
          yzmview: yzmview,
          face: res.data.session.id,

        });
      }
    })


    this.setData({
      schoolid_index: schid,
      schoolname: schname,
      city_index: 0,
      yzmview: 'line'
      //
    });
    var showBusy = text => wx.showToast({
      title: text,
      icon: 'loading',
      duration: 10000
    });
  },
})