var util = require("../../utils/util.js");
var page = this;



Page({
  data: {
    imgUrls: [
      'http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/poster-fm/187794407730706565.jpg',
      'http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/poster-fm/683096449993577757.jpg',
      'http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/poster-fm/691883003392395573.jpg'
    ],
    playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    playing:false,
    list: [],
    user: {},
    activePercent:0,
    currentPlay:0
  },
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '珊瑚坝电台'

    })

    // var id=options.id||'177018'
    // var that = this
    // wx.request({
    //   url: 'https://n.sqaiyan.com/userplaylists?id='+id,
    //   success: function (res) {
    //     that.setData({
    //       list: res.data.playlist,
    //       user: res.data.playlist[0].creator
    //     });
    //     wx.setNavigationBarTitle({
    //       title: res.data.playlist[0].creator.nickname
    //     })
    //   }
    // });
  },
  changeIndicatorDots: function (e) {
    this.setData({
      indicatorDots: !this.data.indicatorDots
    })
  },
  changeAutoplay: function (e) {
    this.setData({
      autoplay: !this.data.autoplay
    })
  },
  intervalChange: function (e) {
    this.setData({
      interval: e.detail.value
    })
  },
  durationChange: function (e) {
    this.setData({
      duration: e.detail.value
    })
  },
  isplay:function(e){
    const backgroundAudioManager = wx.getBackgroundAudioManager();
    backgroundAudioManager.onTimeUpdate(function (callback) {

      var duration = backgroundAudioManager.duration;
      var currentTime = backgroundAudioManager.currentTime;
      var percent = util.formPercent(currentTime, duration);
      that.setData({
        activePercent: percent
      })
    })
    backgroundAudioManager.onPause(function (callback) {
      that.setData({
        currentPlay: backgroundAudioManager.currentTime,
        



      })

    })
    backgroundAudioManager.onEnded(function (callback) {
      that.setData({
        currentPlay: backgroundAudioManager.currentTime,
        playImage: "http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
        activePercent:0
        



      })

    })
    backgroundAudioManager.onStop(function (callback) {


    })
    var that = this;
    that.setData({
      playing:that.data.playing?false:true
    });
    var kind = e.currentTarget.id;
    console.log(kind);

    if(that.data.playing){
      that.setData({
          
        playImage:"http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/pause.png"


      });
      if (!that.data.currentPlay){
        wx.playBackgroundAudio({
          dataUrl: 'http://ws.stream.qqmusic.qq.com/M500001VfvsJ21xFqb.mp3?guid=ffffffff82def4af4b12b3cd9337d5e7&uin=346897220&vkey=6292F51E1E384E06DCBDC9AB7C49FD713D632D313AC4858BACB8DDD29067D3C601481D36E62053BF8DFEAF74C0A5CCFADD6471160CAF3E6A&fromtag=46'
        })
      }else{
        wx.seekBackgroundAudio({
          position: that.data.currentPlay
        })
        backgroundAudioManager.play();
        
      }
    }else{
      that.setData({
        playImage:"http://poster-fm-gurui.oss-cn-shanghai.aliyuncs.com/icon-fm/playing.png",
        playing:false

      })
      wx.pauseBackgroundAudio();
    }
    


  },
  changeAllScreen:function(){
    var that=this;
    wx.navigateTo({
      url: '../test1/index?playing=' + that.data.playing + "&current=" + that.data.activePercent + "&currentPlay=" + that.data.currentPlay,
    })

  },
  
});

