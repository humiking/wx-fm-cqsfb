var util = require("../../utils/util.js");
const app = getApp();
const backgroundAudioManager = wx.getBackgroundAudioManager();
var local = 'wxlocal.cqsfb.top';
var online = 'cqsfb.top';
var https = online;

Page({
  data: {
    imgUrls: [],
    playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    playing:false,
    list: [],
    user: {},
    activePercent:0,
    currentPosition:0,
    currentPlayInfo:{},
    currentPlayUrl:{}
  },
  onLoad: function (options) {
    var that = this;
    //改变标题
    wx.setNavigationBarTitle({
      title: '珊瑚坝电台'

    })
    //播放列表
    var timestamp = Date.parse(new Date());
      wx.request({
        url: 'https://' + https + '/wx/list?time=' + timestamp,
        method: 'GET',
        success: function (res) {
          if (res.data.successful) {
            that.setData({
              list: res.data.data.list_sf,
              currentPlayInfo: res.data.data.list_sf[0],
              currentPlayUrl: res.data.data.list_sf[0].urlList[0].mp3Url
            })
            
            var appData = app.globalData;
            appData.currentPlayInfo = res.data.data.list_sf[0];
            appData.list_sf = res.data.data.list_sf;
            appData.curplay = appData.currentPlayInfo.urlList[0].mp3Url;
            appData.index_am = appData.list_sf.length;
            appData.currentTotalDuration = appData.currentPlayInfo.totalDuration
            appData.currentDuration = appData.currentPlayInfo.urlList[0].duration
          }
        }
      })
    
    //获取请求轮播图
    wx.request({
      url: 'https://' + https + '/wx/banner',
      success: function (res) {
        if (res.data.successful) {
          that.setData({
            imgUrls: res.data.data
          })
        }
      }
    })

  },
  onShow:function(){
    var that = this;
    if (app.globalData.list_sf.length > 0){
      var list = app.globalData.list_sf;
      console.log(list);
      var currentPlayInfo = app.globalData.list_sf[0];
      var urlList = currentPlayInfo.urlList[0];
      that.setData({
        list: app.globalData.list_sf,
        currentPlayInfo: app.globalData.list_sf[0],
        currentPlayUrl: urlList.mp3Url,
        playing: app.globalData.playing
      })

    }
    

  },
  isplay:function(e){
    //获取后台播放管理器
    
    //更新进度条
    backgroundAudioManager.onTimeUpdate(function (callback) {
      var duration = backgroundAudioManager.duration;
      var currentTime = backgroundAudioManager.currentTime;
      var percent = util.formPercent(currentTime, duration);
      that.setData({
        activePercent: percent
      })
    })
    //设置暂停监听器
    backgroundAudioManager.onPause(function (callback) {
      that.setData({
        currentPosition: backgroundAudioManager.currentTime,
      })
    })
    //设置自然结束监听器
    backgroundAudioManager.onEnded(function (callback) {
      that.setData({
        currentPosition: backgroundAudioManager.currentTime,
        playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
        activePercent:0
      })
    })
    //设置手动结束监听器
    backgroundAudioManager.onStop(function (callback) {


    })
    //改变播放的按钮以及状态
    var that = this;
    that.setData({
      playing:that.data.playing?false:true
    });
    app.globalData.playing = that.data.playing;
    var kind = e.currentTarget.id;

    if(that.data.playing){
      that.setData({    playImage:"http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/pause.png"
      });
      if (!that.data.currentPosition){
        var url = app.globalData.currentPlayInfo.urlList;
        console.log(url[0].mp3Url);
        wx.playBackgroundAudio({
          dataUrl: url[0].mp3Url
        })
      }else{
        wx.seekBackgroundAudio({
          position: that.data.currentPosition
        })
        backgroundAudioManager.play();
        
      }
    }else{
      that.setData({    playImage:"http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
      })
      wx.pauseBackgroundAudio();
    }
  },
  //切换页面
  changeAllScreen:function(){
    var that=this;

    wx.navigateTo({
      url: '../test1/index?currentPostion=' + backgroundAudioManager.currentTime,
    })

  },
  
});

