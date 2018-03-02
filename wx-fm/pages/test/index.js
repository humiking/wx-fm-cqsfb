var util = require("../../utils/util.js");
var hostInfo = require("../../host.js");
var https = hostInfo.online;
var app = getApp();
var backgroundAudioManager = wx.getBackgroundAudioManager();




Page({
  data: {
    //加载刷新
    hidden_loading:false,//显示加载提示语
    hidden_bottom_icon:true,//隐藏加载中
    hidden_top_icon:true,//隐藏刷新
    hasMore:false,//是否还有下划列表
    size:10,//每页的条数
    page:1,//当前页面
    //轮播图
    banner_height:0,//轮播图高度
    //轮播图参数
    imgUrls: [],
    playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    //用户参数
    user: {},
    //播放参数
    playing: false,
    list_sf: [],//播放列表
    activePercent: 0,//进度条播放百分比
    currentPosition: 0,//当前播放时间
    currentTotalDuration: 0,//当前电台的总时长
    currentDuration: 0,//该部分的播放时长
    currentPlayInfo: {},//当前播放电台的信息
    currentPlayUrl: {},//当前播放的url
    index:0//索引
  },
  
  onLoad: function (options) {
    var appData = app.globalData;
    var that = this;
    //改变标题
    wx.setNavigationBarTitle({
      title: '珊瑚坝电台'

    })
    var query = wx.createSelectorQuery().in(this);
    query.select('#swiper').boundingClientRect(function (res) {
      that.setData({
        banner_height: res.height // 这个组件内 #the-id 节点的宽度
      })
    }).exec();
    //播放列表
    var timestamp = Date.parse(new Date());
    wx.request({
      url: https + '/wx/list?time=' + timestamp,
      method: 'GET',
      success: function (res) {
        if (res.data.successful) {
          that.setData({
            hidden_loading:true,
            list_sf: res.data.data.list_sf
          })

          if (that.data.currentPlayInfo.name === undefined){
            that.setData({
            currentPlayInfo: res.data.data.list_sf[0],
            currentPlayUrl: res.data.data.list_sf[0].urlList[0].mp3Url,
            currentTotalDuration: res.data.data.list_sf[0].totalDuration,
            currentDuration: res.data.data.list_sf[0].urlList[0].duration

            })
          appData.currentPlayInfo = res.data.data.list_sf[0];
          appData.curplay = appData.currentPlayInfo.urlList[0].mp3Url;
          appData.currentTotalDuration = appData.currentPlayInfo.totalDuration
          appData.currentDuration = appData.currentPlayInfo.urlList[0].duration

          }

          appData.list_sf = res.data.data.list_sf;
          appData.index_am = appData.list_sf.length;
        }
      }
    })

    //获取请求轮播图
    wx.request({
      url: https + '/wx/banner',
      success: function (res) {
        if (res.data.successful) {
          that.setData({
            imgUrls: res.data.data
          })
        }
      }
    })

  },

  onShow: function () {
    var that = this;
    var data = app.globalData;
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
        index: data.index
      })
      if (data.playing){
        that.setData({
          playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/pause.png"
        })
      }else{
        that.setData({
          playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png"
        })
      }
      
    }


  },

  isplay: function () {
    var that = this;
    //获取后台播放管理器
    //更新进度条
    backgroundAudioManager.onTimeUpdate(function (callback) {
      if (!that.data.hidden_loading){
      that.setData({
        hidden_loading: true
      })
      }
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
        playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
      })
    })
    //设置自然结束监听器
    backgroundAudioManager.onEnded(function (callback) {
      that.setData({
        currentPosition: backgroundAudioManager.currentTime,
        playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
        activePercent: 0,
        currentPosition: 0
      })
      var index = that.data.index;
      ++index;
      that.setData({
        currentPlayInfo: data.list_sf[index],
        index: index
      })
      app.globalData.currentPlayInfo = data.list_sf[index];
      that.isplay();
    })
    //设置手动结束监听器
    backgroundAudioManager.onStop(function (callback) {
      that.setData({
        currentPosition: backgroundAudioManager.currentTime,
        activePercent: 0,
        currentPosition: 0
      })
    })
    //后台播放等候
    backgroundAudioManager.onWaiting(function (callback) {
      that.setData({
        hidden_loading:false
      })
    })
    
    //改变播放的按钮以及状态
    that.setData({
      playing: that.data.playing ? false : true
    });
    app.globalData.playing = that.data.playing;
    if (that.data.playing) {
      that.setData({
        playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/pause.png"
      });
      if (!that.data.currentPosition) {
        var url = that.data.currentPlayInfo.urlList;
        wx.playBackgroundAudio({
          dataUrl: url[0].mp3Url
        })
      } else {
        wx.seekBackgroundAudio({
          position: that.data.currentPosition
        })
        backgroundAudioManager.play();
      }
    } else {
      that.setData({
        playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
      })
      wx.pauseBackgroundAudio();
    }
  },

  //切换页面
  changeAllScreen: function () {
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

    wx.navigateTo({
      url: '../test1/index',
    }) 
  },


  //点击列表内的歌曲进行换歌
  playThisMusic: function (event) {
    //先进行判断是否在播放的歌曲是改歌曲
    var currentTarget = event.currentTarget;
    var id = currentTarget.dataset.id;
    var that = this;
    var data = that.data;
    if (data.currentPlayInfo.id == id) {
      that.isplay();
    } else {
      for (var i in data.list_sf) {
        if (data.list_sf[i].id == id) {
          that.setData({
            currentPlayInfo: data.list_sf[i],
            index:i
          })
          app.globalData.currentPlayInfo = data.list_sf[i];
        }
      }

      //重新播放
      backgroundAudioManager.stop();
      that.setData({
        playing: false,
        currentPosition: 0,
        activePercent: 0
      })
      that.isplay();
    }

  },
onPullDownRefresh:function(){
  var that = this;
  that.setData({
    hidden_top_icon: false
  })
  that.onLoad();
  setInterval(function(){
    that.setData({
      hidden_top_icon: true
    })
  },1000)
  
  wx.stopPullDownRefresh();
  
},
onReachBottom:function(){
  
  
  


}

  

});