//index.js
//获取应用实例
var Constant = require('../../config.js');
var app = getApp()
Page({








  onShow: function() {
        //初始化数据
         wx.getSystemInfo({
      success: (res) => {
        console.log(res)
        this.setData({
          scrollHeight: res.windowHeight - (100 * res.windowWidth / 750+13) //80为顶部搜索框区域高度 rpx转px 屏幕宽度/750
        });
      }
    })

 try {
             var date = wx.getStorageSync('Date')
             var week = wx.getStorageSync('Week')
             var weekly= wx.getStorageSync('weekly')
                        } catch (e) {}


var d = new Date();
var nowyears = d.getFullYear();
 var nowmonth = d.getMonth()+1;
 var nowdays = d.getDate();
 var nowda=nowyears+"-"+nowmonth+"-"+nowdays;
 
 var index=parseInt((GetDateDiff(date,nowda)+week-1)/7)+weekly;
 
if(index=="NaN")
index=0;
 this.setData({
      index: index
    })
 var temp=index;
     wx.request({
       url: Constant.host+'schoolname', 
            data: {
                
                },
            header: {
                'content-type': 'application/json;charset=UTF-8;'
                },
            success: function(res) {
               console.log(res.data)
                wx.setStorageSync('SchoolName',res.data.session)
                }
                })



        

    var bgcolor='#007fff';
    var textcolor='#ffffff';
var self=this;
        var curriculum=[]
       // self.getFilter();
        try {
            curriculum= wx.getStorageSync('curriculum')
            }catch (e) {}
      //  console.log('aaa', curriculum[1][8]);         
        var Mondaycurriculum = []
        var Tuesdaycurriculum= []
        var Wednesdaycurriculum= []
        var Thursdaycurriculum= []
        var Fridaycurriculum= []
        var Saturdaycurriculum= []
        var Sundaycurriculum= []
        for(var i = 0;i < curriculum.length; i++) {
            if(curriculum[i][0]=="1"){
              var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Mondaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="2"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Tuesdaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="3"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
            Wednesdaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="4"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Thursdaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="5"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Fridaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="6"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Saturdaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="7"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Sundaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }



            }
        this.setData({
            display_Mondaycurriculum:Mondaycurriculum,
            display_Tuesdaycurriculum:Tuesdaycurriculum,
            display_Wednesdaycurriculum:Wednesdaycurriculum,
            display_Thursdaycurriculum:Thursdaycurriculum,
            display_Fridaycurriculum:Fridaycurriculum,
            display_Saturdaycurriculum:Saturdaycurriculum,
            display_Sundaycurriculum:Sundaycurriculum
            })
        
        },
goToMore:function(e){
       //title="我是navigate"
       var id = e.currentTarget.dataset.gid;
       var kcm = e.currentTarget.dataset.gkcm;
       var js = e.currentTarget.dataset.gjs;
       var ls = e.currentTarget.dataset.gls;
       var time = e.currentTarget.dataset.gtime;
       var zs = e.currentTarget.dataset.gzs;
       var sx = e.currentTarget.dataset.gsx;
        console.log('--', zs);
        wx.navigateTo({
          url: '../logs/logs?kcm='+kcm+'&js='+js+'&ls='+ls+'&time='+time+'&sx='+sx+'&zs='+zs      
        });
    },
    
    data: { 
        bgcolor:'#007fff',
        textcolor:'#ffffff',
          array: ['第1周', '第2周', '第3周', '第4周', '第5周',
    '第6周', '第7周', '第8周', '第9周','第10周',
    '第11周', '第12周', '第13周', '第14周', '第15周',
    '第16周', '第17周', '第18周', '第19周', '第20周','第21周','第22周','第23周','第24周' ],
    index: 0,
        },
      pChange: function(e) {


          console.log("e is " + e.detail.value);
    
    //console.log(getWeek("1.3.5-7.12-18",e.detail.value + 1));
    var temp = Number(e.detail.value);
    
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })



var d = new Date();
var years = d.getFullYear();
 var month = d.getMonth()+1;
 var days = d.getDate();
 var date=years+"-"+month+"-"+days;
 var week=d.getDay();
 if(week=="0")
 week=7;
 try {
              wx.setStorageSync('Date',date)
              wx.setStorageSync('Week',week)
              wx.setStorageSync('weekly',temp)
                        } catch (e) {}

 
 console.log(week)
 


    var bgcolor='#007fff';
    var textcolor='#ffffff';
var self=this;
        var curriculum=[]
       // self.getFilter();
        try {
            curriculum= wx.getStorageSync('curriculum')
            }catch (e) {}
      //  console.log('aaa', curriculum[1][8]);         
        var Mondaycurriculum = []
        var Tuesdaycurriculum= []
        var Wednesdaycurriculum= []
        var Thursdaycurriculum= []
        var Fridaycurriculum= []
        var Saturdaycurriculum= []
        var Sundaycurriculum= []
        for(var i = 0;i < curriculum.length; i++) {
            if(curriculum[i][0]=="1"){
              var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Mondaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="2"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Tuesdaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="3"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
            Wednesdaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="4"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Thursdaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="5"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Fridaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="6"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Saturdaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }
            if(curriculum[i][0]=="7"){
               var strjs=curriculum[i][4].replace(/\n/g, ".").replace(/\s/g, "");
              if(getWeek(strjs,temp)){
                var bgcolor='#007fff';
                var textcolor='#ffffff';
              }
              else{
                 var bgcolor='#ffffff';
                 var textcolor='#000000';
              }
              Sundaycurriculum.push({id: i,kch:curriculum[i][1].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),kcm:curriculum[i][2].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),ls:curriculum[i][3].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),zs:curriculum[i][4].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),time:curriculum[i][9].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),js:curriculum[i][6].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),sx:curriculum[i][5].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),top:curriculum[i][7].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),height:curriculum[i][8].replace(/\n/g, "temps").replace(/\s/g, "").replace(/temps/g, "\n"),bgcolor:bgcolor,textcolor:textcolor})
              }



            }
        this.setData({
            display_Mondaycurriculum:Mondaycurriculum,
            display_Tuesdaycurriculum:Tuesdaycurriculum,
            display_Wednesdaycurriculum:Wednesdaycurriculum,
            display_Thursdaycurriculum:Thursdaycurriculum,
            display_Fridaycurriculum:Fridaycurriculum,
            display_Saturdaycurriculum:Saturdaycurriculum,
            display_Sundaycurriculum:Sundaycurriculum
            })
  },
})


function getWeek(str,request){
request++;
  //var str = "1.3.5-7.12-18";

var week = new Array(22);
for(var i = 0;i < 22;i++){
  week[i] = false;
}
//console.log("string is " + str);
//console.log("The array is " + week);

var arr = str.split(".");
//console.log(arr);
for(var i = 0;i < arr.length;i++){
  var num = arr[i].split("-");
  //console.log(num);
  if(num.length === 2){
    var start = num[0];
    var end = num[1];
    for(var j = start;j <= end;j++){
      week[j] = true;
    }
  }else if(num.length === 1){
    week[num[0]] = true;
  }
}
//console.log("The result is " + week);
if(request > 0 && request <= 21){
  console.log("request is " + request);
  console.log(week[request]);
  return week[request];
}
//console.log("The result is " + week);
  
}

function GetDateDiff(startDate,endDate)  
{  
    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();     
    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
    return  dates;    
}