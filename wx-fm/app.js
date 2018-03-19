var hostInfo = require("host.js");
var https = hostInfo.online;
var backgroundAudioManager = wx.getBackgroundAudioManager();
//app.js
App({
  globalData: {
    //加载
    hidden_loading: false,//显示加载提示语
    hasLogin: true,
    list_am: [], //播放列表，已经经历过播放模式排序后的
    index_am: 0,
    playtype: 1, //播放类型：1.播放列表播放 
    curplay: {}, //添加播放的url
    shuffle: 1, //播放模式shuffle，1顺序，2单曲，3随机
    //播放参数
    playing: false,
    list_sf: [], //播放列表
    activePercent:0,//进度条播放百分比
    currentPosition: 0,//当前播放位置
    currentTotalDuration:0,//当前电台的总时长
    currentDuration:0,//该部分的播放时长
    currentPlayInfo:{},//当前播放电台歌曲信息
    currentPlayUrl: {},//当前播放的url
    index: 0//索引
   
  },

  onLaunch: function () {
    

  },
  /**
   * OK ，这个只是切换索引，可以播放下一首也可以播放上一首
   */
  nextplay: function (t) { //播放下一首电台
    //播放列表中下一首
    this.preplay(); //停止播放

    var list = this.globalData.list_am; //歌曲列表
    var index = this.globalData.index_am; //歌曲索引
    if (t == 1) { //如果t不为1，就播放前一个电台
      index++;
    } else {
      index--;
    }
    index = index > list.length - 1 ? 0 : (index < 0 ? list.length - 1 : index); //循环播放
    this.globalData.curplay = list[index] || {}; //当前播放
    this.globalData.index_am = index; //索引
    this.seekmusic(1); //搜索歌曲并播放该歌曲
  },
  /**
   * OK,停止播放，停止
   */
  preplay: function () {
    //歌曲切换 停止当前音乐
    this.globalData.playing = false;
    wx.stopBackgroundAudio();
  },

  /**
   * OK,获取电台
   */
  getfm: function () { //获取FM
    var that = this;
    console.log("从新获取fm")
    wx.request({
      url: 'https://n.sqaiyan.com/fm?t=' + (new Date()).getTime(),
      method: 'GET',
      success: function (res) {
        that.globalData.list_fm = res.data.data;
        that.globalData.index_fm = 0;
        that.globalData.curplay = res.data.data[0];
        that.seekmusic(2); //搜索音乐
      }
    })
  },
  /**
   * OK,停止播放
   */
  stopmusic: function (type, cb) { //停止播放音乐
    var that = this;
    wx.pauseBackgroundAudio();
    wx.getBackgroundAudioPlayerState({
      complete: function (res) {
        that.globalData.currentPosition = res.currentPosition || '0',
          that.globalData.playing = false
      }
    })
  },
  /**
   * OK,搜索音乐并播放
   */
  seekmusic: function (type, cb, seek) { //搜索音乐并播放歌曲
    console.log("type:", type) //查询类型 type:1 音乐 
    var that = this;
    var m = this.globalData.curplay;
    this.globalData.playtype = type; //播放类型 type: 1音乐 
    if (type == 1) { //播放音乐
      wx.request({
        url: 'https://n.sqaiyan.com/song?id=' + that.globalData.curplay.id,
        success: function (res) {
          if (!res.data.songs[0].mp3Url) { //如果没有歌曲的话
            that.nextplay(1);
          }
        }
      })
    }
    wx.playBackgroundAudio({ //播放后台音乐
      dataUrl: m.mp3Url,
      title: m.name,
      success: function (res) {
        if (seek != undefined) {
          wx.seekBackgroundAudio({ position: seek })
        };
        that.globalData.playing = true;
        cb && cb();
      },
      fail: function () {
        if (type == 1) {
          that.nextplay(1);
        } else {
          that.nextfm();
        }
      }
    })
  },
  /**
   * OK，播放模式
  */
  shuffleplay: function (shuffle) { //播放模式
    //播放模式shuffle，1顺序，2单曲，3随机
    var that = this;
    that.globalData.shuffle = shuffle;
    if (shuffle == 1) {
      that.globalData.list_am = that.globalData.list_sf;//顺序播放
    } else if (shuffle == 2) {
      that.globalData.list_am = [that.globalData.curplay]//单曲循环
    } else {
      that.globalData.list_am = [].concat(that.globalData.list_sf);//合并成播放列表
      var sort = that.globalData.list_am; //数组
      sort.sort(function () { //排序随机
        return Math.random() - (0.5) ? 1 : -1;
      })

    }
    for (let s in that.globalData.list_am) {//数组列表，s是索引
      if (that.globalData.list_am[s].id == that.globalData.curplay.id) {
        that.globalData.index_am = s; //
      }
    }
  }



})