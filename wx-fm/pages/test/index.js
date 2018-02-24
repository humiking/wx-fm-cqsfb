var app =getApp();

Page({
  data: {
    imgUrls: [],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    playing:false,
    list: [],
    user: {},
    currentPlayInfo:{},
  },
  onLoad: function (options) {
    var that = this;
    wx.setNavigationBarTitle({
      title: '珊瑚坝电台'
    })

    wx.request({
      url: 'https://wxlocal.cqsfb.top/wx/banner',
      success:function(res){
        if(res.data.successful){
          that.setData({
            imgUrls: res.data.data
          })
        } 
      }
    })

    var timestamp = Date.parse(new Date());
    wx.request({
      url: 'https://wxlocal.cqsfb.top/wx/list?time=' + timestamp,
      method: 'GET',
      success: function (res) {
        if(res.data.successful){
          app.globalData.list_sf = res.data.data.list_sf;
          that.setData({
            list:res.data.data.list_sf,
            currentPlayInfo: res.data.data.list_sf[0]
          })
        }
        app.globalData.curplay = that.data.currentPlayInfo.urlList[0].mp3Url;
        console.log(app.globalData.curplay);
      }
    })
  },
  isplay:function(){
    var that = this;
    that.setData({
      playing:that.data.playing?false:true
    })
    if(that.data.playing){
      wx.playBackgroundAudio({
        dataUrl: app.globalData.curplay
      })
    }else{
      wx.stopBackgroundAudio();
    }


  },
  changeAllScreen:function(){
    var that=this;
    wx.navigateTo({
      url: '../test1/index?playing='+that.data.playing,
    })

  }
});
