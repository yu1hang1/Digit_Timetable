// index.js
var app = getApp()
var schoolid = require('../../js/city.js');
  var SchoolName=[]
Page({
  data: {
    schoolidText: '',
  },


  //页面显示获取设备屏幕高度，以适配scroll-view组件高度
  onShow: function () {
    wx.getSystemInfo({
      success: (res) => {
        console.log(res)
        this.setData({
          scrollHeight: res.windowHeight - (100 * res.windowWidth / 750) //80为顶部搜索框区域高度 rpx转px 屏幕宽度/750
        });
      }
    })
  },


  onLoad: function () {
     try {
            SchoolName= wx.getStorageSync('SchoolName')
            }catch (e) {}
             console.log(SchoolName)
    var school = [ ]
     for(var i = 0;i < SchoolName.length; i++) {
       school.push({id: SchoolName[i][0], txt: SchoolName[i][1]})
       }

      this.setData({
        display_champions:school
      })
      },
  goTologin: function(e) {
   
      wx.switchTab({  
        url: '../index/index'
        })
      },


  goToDetaile: function(e) {
    var id = e.currentTarget.dataset.gid;
    var name = e.currentTarget.dataset.gname;

    try {
      wx.setStorageSync('schoolid',id)
      wx.setStorageSync('schoolname',name)
      } catch (e) {}
      wx.switchTab({  
        url: '../index/index'
        })
      },


  // 事件处理函数
  onInput: function(e) {
    //console.log(e)
   
    var char = e.detail.value;
   // console.log('--', char);
    char = char && char.trim();
    if (char.length != 0) {
      var school = [ ]
       for(var i = 0;i < SchoolName.length; i++) {
        if(new RegExp(char).test(SchoolName[i][1])){
          console.log('--', SchoolName[i][1]);
          school.push({id: SchoolName[i][0], txt: SchoolName[i][1]})
          }
      }
      this.setData({
        display_champions:school
      })

      if (schoolid.hasOwnProperty(char)) {
      //  console.log(schoolid[char].join(', '))
        this.setData({
          schoolidText: e.detail.value
        });
      }
      else {
        this.setData({
          schoolidText: e.detail.value
        });
      }
    }


    else {
      var school = []
      for (var i = 0; i < SchoolName.length; i++) {
        school.push({ id: SchoolName[i][0], txt: SchoolName[i][1] })
      }

      this.setData({
        schoolidText: '',
         display_champions:school
      });
    }
  },

})
