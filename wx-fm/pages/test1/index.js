// pages/test1/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    playing:false,
    currentPlay:0
  
  },
  onLoad:function(options){
    wx.setNavigationBarTitle({
      title: '珊瑚坝电台'

    })
    var f = options;
    var that = this;
    that.setData({
      playing:options.playing,
      currentPlay: parseInt(options.currentPlay)
    })



  },

  playingtoggle:function(){
    var that = this;
    const backgroundAudioManager = wx.getBackgroundAudioManager();
    // backgroundAudioManager.onTimeUpdate(function (callback) {

    //   var duration = backgroundAudioManager.duration;
    //   var currentTime = backgroundAudioManager.currentTime;
    //   var percent = util.formPercent(currentTime, duration);
    //   that.setData({
    //     activePercent: percent
    //   })
    // })
    // backgroundAudioManager.onPause(function (callback) {
    //   that.setData({
    //     currentPlay: backgroundAudioManager.currentTime,




    //   })

    // })
    backgroundAudioManager.onEnded(function (callback) {
      that.setData({
        playing: false
      })

    })
    backgroundAudioManager.onStop(function (callback) {


    })
    backgroundAudioManager.onPause(function(callback){
      playing:false
    })

    that.setData({
      playing: that.data.playing ? false : true
    });

    if (that.data.playing) {
      
      if (!that.data.currentPlay) {
        wx.playBackgroundAudio({
          dataUrl: 'http://ws.stream.qqmusic.qq.com/M500001VfvsJ21xFqb.mp3?guid=ffffffff82def4af4b12b3cd9337d5e7&uin=346897220&vkey=6292F51E1E384E06DCBDC9AB7C49FD713D632D313AC4858BACB8DDD29067D3C601481D36E62053BF8DFEAF74C0A5CCFADD6471160CAF3E6A&fromtag=46'
        })
      } else {
        wx.seekBackgroundAudio({
          position: that.data.currentPlay
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

  }
})