var app = getApp();
var util = require("../../utils/util.js");

// pages/test1/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //播放参数
    playing: false,
    list_sf: [],//播放列表
    activePercent: 0,//进度条播放百分比
    currentPosition: 0,//当前播放时间
    currentTotalDuration: 0,//当前电台的总时长
    currentDuration: 0,//该部分的播放时长
    currentPlayInfo: {},//当前播放电台的信息
    currentPlayUrl: {},//当前播放的url
    index: 0,//索引
    //时间显示扩展
    currentDurationTime: "00:00",//当前总时间
    currentPlayTime:"00:00",//当前播放的时间
  
  },
  onLoad: function (options){
    //获取当前播放的状态以及当前歌曲信息
    var that = this;
    var data = app.globalData;
    that.setData({
      playing: data.playing,
      list_sf:data.list_sf,
      activePercent: data.activePercent,
      currentPosition: data.currentPosition,
      currentTotalDuration: data.currentTotalDuration,
      currentDuration: data.currentDuration,
      currentPlayInfo: data.currentPlayInfo,
      currentPlayUrl: data.currentPlayUrl,
      index:data.index,
      currentDurationTime: util.formatduration(data.currentTotalDuration),
      currentPlayTime: util.formatduration(data.currentPosition)

    })

    //更换标题
    wx.setNavigationBarTitle({
      title: that.data.currentPlayInfo.name
    })
    

  },
  onShow:function(){
    var that = this;
    var data = app.globalData;
    //获取当前播放的状态以及当前歌曲信息
    if (app.globalData.list_sf.length > 0) {
      that.setData({
        playing: data.playing,
        list_sf: data.list_sf,
        activePercent: data.activePercent,
        currentPosition: data.currentPosition,
        currentTotalDuration: data.currentTotalDuration,
        currentDuration: data.currentDuration,
        currentPlayInfo: data.currentPlayInfo,
        currentPlayUrl: data.currentPlayUrl,
        index: data.index,
        currentDurationTime: util.formatduration(data.currentTotalDuration),
        currentPlayTime: util.formatduration(data.currentPosition)

      })
      

    }



  },

  playingtoggle:function(){
    var that = this;
    var backgroundAudioManager = wx.getBackgroundAudioManager();
    backgroundAudioManager.onEnded(function (callback) {
      that.setData({
        playing: false
      })

    })

    
    backgroundAudioManager.onStop(function (callback) {
      that.setData({
        playing: false
      })


    })
    backgroundAudioManager.onPause(function(callback){
      playing:false
    })

    that.setData({
      playing: that.data.playing ? false : true
    });
    app.globalData.playing = that.data.playing ? false : true;

    if (that.data.playing) {
      
      if (!that.data.currentPosition) {
        wx.playBackgroundAudio({
          dataUrl: that.data.currentPlayInfo.urlList[0].mp3Url
        })
        
      } else {
        wx.seekBackgroundAudio({
          position: Number.parseFloat(that.data.currentPosition)
        })
        backgroundAudioManager.play();

      }
    } else {
      that.setData({
        playing: false

      })
      wx.pauseBackgroundAudio();
    }
  },

  

  allscreen:function(){
    wx.navigateBack({
      delta:1
    })
    var that = this;
    var data = app.globalData;

    app.globalData.playing = that.data.playing;
    app.globalData.list_sf = that.data.list_sf;
    app.globalData.activePercent = that.data.activePercent;
    app.globalData.currentPosition = that.data.currentPosition;
    app.globalData.currentTotalDuration = that.data.currentTotalDuration;
    app.globalData.currentDuration = that.data.currentDuration;
    app.globalData.currentPlayInfo = that.data.currentPlayInfo;
    app.globalData.currentPlayUrl = that.data.currentPlayUrl;
    app.globalData.index = that.data.index;

  },
  playother:function(event){
    // var offset = event.getc 

  }
})