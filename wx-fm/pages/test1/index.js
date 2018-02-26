var app = getApp();
// pages/test1/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    playing:false,
    currentPosition:0,
    currentPlayInfo:{}
  
  },
  onLoad: function (options){
    //获取当前播放的状态以及当前歌曲信息
    var that = this;
    console.log(options);
    that.setData({
      playing: app.globalData.playing,
      currentPlayInfo: app.globalData.list_sf[0],
      currentPosition: options.currentPostion
    })
    //更换标题
    wx.setNavigationBarTitle({
      title: that.data.currentPlayInfo.name
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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})