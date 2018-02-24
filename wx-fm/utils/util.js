//已看
function formatTime(date, type) {
  type = type || 1;
  //type 1,完成输出年月日时分秒，2对比当前时间输出日期，或时分;
  var d = new Date(date)
  var year = d.getFullYear()
  var month = d.getMonth() + 1
  var day = d.getDate()

  var hour = d.getHours()
  var minute = d.getMinutes()
  var second = d.getSeconds();
  if (type == 1) {
    return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':');
  } else {
    var current = new Date();
    var curtimes = current.getTime();
    if ((curtimes - date) < 24 * 3600000) {
      if (curtimes - date < 3600000) {
        return (new Date(curtimes - date)).getSeconds() + "分钟前";
      }
      else {
        return [hour, minute].map(formatNumber).join(':');
      }

    } else if ((curtimes - date) < 48 * 3600000) {
      return "昨天：" + [hour, minute].map(formatNumber).join(':');

    } else if (year != current.getFullYear()) {
      return year + "年" + month + "月" + day + "日"
    }
    else {
      return month + "月" + day + "日"
    }
  }
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}
function formatduration(duration) {
  duration = new Date(duration);
  let mint = duration.getMinutes();
  let sec = duration.getSeconds();
  return formatNumber(mint) + ":" + formatNumber(sec);//格式化时间
}
//用于歌词播放的时间，更换
function parse_lrc(lrc_content) {
  let now_lrc = [];
  let lrc_row = lrc_content.split("\n");
  let scroll = true;
  for (let i in lrc_row) {
    if ((lrc_row[i].indexOf(']') == -1) && lrc_row[i]) {
      now_lrc.push({ lrc: lrc_row[i] });
    } else if (lrc_row[i] != "") {
      var tmp = lrc_row[i].split("]");
      for (let j in tmp) {
        scroll = false
        let tmp2 = tmp[j].substr(1, 8);
        tmp2 = tmp2.split(":");
        let lrc_sec = parseInt(tmp2[0] * 60 + tmp2[1] * 1);
        if (lrc_sec && (lrc_sec > 0)) {
          let count = tmp.length;
          let lrc = trimStr(tmp[count - 1]);
          if (lrc != "") {
            now_lrc.push({ lrc_sec: lrc_sec, lrc: lrc });
          }

        }
      }
    }
  }
  if (!scroll) {
    now_lrc.sort(function (a, b) {
      return a.lrc_sec - b.lrc_sec;
    });
  }
  return {
    now_lrc: now_lrc,
    scroll: scroll
  };
}
//两端清除空白符
function trimStr(str) { return str.replace(/(^\s*)|(\s*$)/g, ""); }

/**音乐播放监听*/
function playAlrc(that, app) {
  if (app.globalData.globalStop) {
    that.setData({
      playtime: '00:00',
      duration: '00:00',
      percent: 0.1,
      playing: false
    });
    return;
  }
  if (that.data.music.id != app.globalData.curplay.id) {
    that.setData({//设置ID
      music: app.globalData.curplay,
      lrc: [],
      showlrc: false,
      lrcindex: 0,
      duration: formatduration(app.globalData.curplay.duration)//时长
    });
    wx.setNavigationBarTitle({ title: app.globalData.curplay.name + "-" + app.globalData.curplay.artists[0].name });//当前播放歌曲名称和歌手名称
    //加载评论的id
    loadrec(0, 0, that.data.music.commentThreadId, function (res) {
      that.setData({
        commentscount: res.total//获取并设置在评论的数量中
      })
    })
  }
  wx.getBackgroundAudioPlayerState({//获取音频播放的状态
    complete: function (res) {//回执都是
      var time = 0, lrcindex = that.data.lrcindex, playing = false, playtime = 0;
      //res.status 播放状态（2：没有音乐在播放，1：播放中，0：暂停中）
      if (res.status != 2) {//如果有音乐在播放
        time = res.currentPosition / res.duration * 100;//设置播放的百分比
        playtime = res.currentPosition;//播放的位置
        if (that.data.showlrc && !that.data.lrc.scroll) {//如果有歌词展示，而且没有滚动
          for (let i in that.data.lrc.lrc) {//遍历歌词
            var se = that.data.lrc.lrc[i];//获取歌词的秒数
            if (se.lrc_sec <= res.currentPosition) {
              lrcindex = i//遍历到最近的一个歌词
            }
          }
        };

      } if (res.status == 1) {//如果播放中
        playing = true;
      }
      that.setData({
        playtime: formatduration(playtime * 1000),//播放时间
        percent: time,
        playing: playing,
        lrcindex: lrcindex
      })
    }
  });
};
/**加载推荐*/
function loadrec(offset, limit, id, cb) {
  wx.request({
    url: 'https://n.sqaiyan.com/recommend',
    data:{
      id:id,
      limit:limit,
      offset:offset
    },
    method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    success: function (res) {
      var data = res.data;//
      for (let i in data.hotComments) {
        data.hotComments[i].time = formatTime(data.hotComments[i].time, 2);
      }
      for (let i in data.comments) {
        data.comments[i].time = formatTime(data.comments[i].time, 2);
      };
      cb && cb(data)
    }
  })
}
/**加载歌词 */
function loadlrc(that) {
  if (that.data.showlrc) {//如果有歌词就关闭
    that.setData({
      showlrc: false
    })
    return;
  } else {//否则打开显示歌词
    that.setData({
      showlrc: true
    })
  }
  if (!that.data.lrc.code) {//如果歌词不存在
    var lrcid = that.data.music.id;//获取歌曲的ID
    var that = that;//获取对象
    wx.request({
      url: 'https://n.sqaiyan.com/lrc?id=' + lrcid,//获取歌词
      success: function (res) {
        var lrc = parse_lrc(res.data.lrc && res.data.lrc.lyric ? res.data.lrc.lyric : '');
        res.data.lrc = lrc.now_lrc;
        res.data.scroll = lrc.scroll ? 1 : 0//滚动
        that.setData({
          lrc: res.data
        });
      }
    })
  }
}
module.exports = {
  formatTime: formatTime,//格式化时间
  formatduration: formatduration,//格式化时长
  parse_lrc: parse_lrc,//解析歌词
  playAlrc: playAlrc,//播放所有推荐
  loadlrc: loadlrc,//加载歌词
  loadrec: loadrec//加载推荐
}
