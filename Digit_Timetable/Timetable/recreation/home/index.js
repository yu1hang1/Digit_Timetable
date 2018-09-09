//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    imgUrls: [
      
      'https://67334961.sutthl.com/images/dice.jpg',
      'https://67334961.sutthl.com/images/gamemore.png',
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    navItems:[
      {
        name:'来撩我',
        url:'../chat/chat'
      },
      {
        name:'摇一摇',
        url:'../dice/dice',
        isSplot:true
      },
      {
        name:'预留',
        url:''
      },
      {
        name:'预留',
        url:''
      },
      {
        name:'预留',
        url:'',
        isSplot:true
      },
      {
        name:'预留',
        url:''
      }
    ]
  },
  onLoad: function () {
    console.log('onLoad')
  }
    
})
