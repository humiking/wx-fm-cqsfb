/**
 * 电台上传管理初始化
 */
var Fm_upload = {
    id: "Fm_uploadTable", //表格id
    seItem: null, //选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Fm_upload.initColumn = function() {
    var columns = [
        { field: 'selectItem', radio: true },
        { title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle' },
        { title: '歌曲名称', field: 'name', visible: true, align: 'center', valign: 'middle' },
        { title: '歌曲演唱者', field: 'artistorName', visible: true, align: 'center', valign: 'middle' },
        { title: '海报', field: 'poster', visible: true, align: 'center', valign: 'middle', formatter: this.imgFormatter },
        { title: '发布状态', field: 'publishStatus', visible: true, align: 'center', valign: 'middle', formatter: this.publishStatusFormatter },
        { title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle', formatter: this.statusFormatter },
        { title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle' },
        { title: '发布时间', field: 'publishTime', visible: true, align: 'center', valign: 'middle' },
        { title: '权重', field: 'weight', visible: true, align: 'center', valign: 'middle' },
        {
            title: '操作',
            field: 'operator',
            align: 'center',
            events: this.operateEvents,
            formatter: this.operateFormatter
        }
    ];
    return columns;
};

/**
 * 按钮操作事件
 */
Fm_upload.operateEvents = {
    //删除电台播放列表
    'click .delete': function(e, value, row, index) {
        Fm_upload.deleteFm(row.id, row.name);
    },

    //编辑电台播放列表
    'click .edit': function(e, value, row, index) {
        Fm_upload.editFm(row.id);
    },
    //发布电台播放列表
    'click .publish': function(e, value, row, index) {
        Fm_upload.publishFm(row.id,row.name);
    },
    //下架电台
    'click .undercarriage': function(e, value, row, index) {
        Fm_upload.undercarriageFm(row.id,row.name);
    },
    //设置排名
    'click .rankBtn': function(e, value, row, index) {
        Fm_upload.rankBtnSetting(row.id);
    }


}

/**
 * 设置排名
 */
// Fm_upload.rankBtnSetting = function(fmId){
//     var promptText = '';
//     promptText = '该已经在小程序上发布的电台歌曲：'+name+',确认要下架吗?';
//     Feng.confirm(promptText, function() {
//         $.ajax({
//             url:"/fm_upload/updatePublishStatus",
//             type:"GET",
//             dataType:"json",
//             data:{
//                 publishStatus:10,
//                 fmId:fmId
//             },
//             success:function(res){
//                 if(res.successful){
//                     Feng.success('下架成功');
//                     Fm_upload.search();
//                 }else {
//                     Feng.error(res.error);
//                 }

//             },
//             error:function(msg){
//                 Feng.error(msg.error)

//             }
//         })

//     });


// }


/**
 * 下架电台
 */
Fm_upload.undercarriageFm = function(fmId,name){
    var promptText = '';
    promptText = '该已经在小程序上发布的电台歌曲：'+name+',确认要下架吗?';
    Feng.confirm(promptText, function() {
        $.ajax({
            url:"/fm_upload/updatePublishStatus",
            type:"GET",
            dataType:"json",
            data:{
                publishStatus:10,
                fmId:fmId
            },
            success:function(res){
                if(res.successful){
                    Feng.success('下架成功');
                    Fm_upload.search();
                }else {
                    Feng.error(res.error);
                }

            },
            error:function(msg){
                Feng.error(msg.error)

            }
        })

    });


}


/**
 * 发布电台
 */
Fm_upload.publishFm = function(fmId,name){
    var promptText = '';
    promptText = '该电台歌曲：'+name+',确认要发布到小程序播放列表上吗?';
    Feng.confirm(promptText, function() {
        $.ajax({
            url:"/fm_upload/updatePublishStatus",
            type:"GET",
            dataType:"json",
            data:{
                publishStatus:5,
                fmId:fmId
            },
            success:function(res){
                if(res.successful){
                    Feng.success('发布成功');
                    Fm_upload.search();
                }else {
                    Feng.error(res.error);
                }

            },
            error:function(msg){
                Feng.error(msg.error)

            }
        })

    });


}

/**
 * 删除电台
 */
Fm_upload.deleteFm = function(fmId, name) {
    var promptText = '';
    promptText = '确定要删除ID为' + fmId + ',歌曲名称为：' + name + '的歌曲吗?';
    Feng.confirm(promptText, function() {
        var ajax = new $ax(Feng.ctxPath + "/fm_upload/delete", function(data) {
            if (data.successful) {
                Feng.success('删除成功！');
                Fm_upload.search();
            } else {
                Feng.error(data.error);
            }
        }, function(data) {
            //Feng.error(data.error);

        });
        ajax.set("fmId", fmId);
        ajax.start();

    });

}

/**
 * 按钮显示
 */
Fm_upload.operateFormatter = function(value, row, index) {
    var status = row.status;
    var publishStatus = row.publishStatus;
    var deleteBtn = '<button class="btn delete btn-primary button-margin" type="button" id="delete"><i class="fa btn-primary"></i>删除</button>',
        editBtn = '<button class="btn edit btn-info button-margin" type="button" id="edit"><i class="fa fa-edit"></i>编辑</button>',
        publishBtn = '<button class="btn publish btn-primary button-margin" type="button" id="publish"><i class="fa btn-primary"></i>发布</button>',
        undercarriageBtn = '<button class="btn undercarriage btn-primary button-margin" type="button" id="undercarriage"><i class="fa btn-primary"></i>下架</button>',
        rankBtn = '<button class="btn rankBtn  button-margin" type="button" ' +
        'style="background-color: #FFFFFF;  border-color: #000;  color: #000;" id="rankBtn"><i class="fa rankBtn"></i>设置排名</button>';
    if (status == 0) {
        if (publishStatus == 0) {
            return [deleteBtn, editBtn, publishBtn, rankBtn].join("");
        } else if (publishStatus == 5) {
            return [undercarriageBtn, rankBtn].join("");
        } else if (publishStatus == 10) {
            return [deleteBtn, editBtn, publishBtn, rankBtn].join("");
        }


    }

}



/**
 * 商品图片预览显示
 */
Fm_upload.imgFormatter = function(value, row, index) {
    var poster = row.poster;
    return '<img src="' + poster + '" style="width:80px; height:auto;" />';
}


/**
 * 发布状态转换
 */
Fm_upload.publishStatusFormatter = function(value, row, index) {
    var publishStatus = row.publishStatus;
    if (publishStatus == 0) {
        return '<span>未发布</span>';
    }
    if (publishStatus == 5) {
        return '<span>已发布</span>';
    }
    if (publishStatus == 10) {
        return '<span>已下架</span>';
    }

}

/**
 * 状态转换
 */
Fm_upload.statusFormatter = function(value, row, index) {
    var status = row.status;
    if (status == 0) {
        return '<span>正常</span>';
    }
    if (status == -1) {
        return '<span>删除</span>';
    }
}



/**
 * 检查是否选中
 */
Fm_upload.check = function() {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Fm_upload.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加电台上传
 */
Fm_upload.openAddFm_upload = function() {
    var index = layer.open({
        type: 2,
        title: '添加电台上传',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/fm_upload/fm_upload_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看电台上传详情
 */
Fm_upload.openFm_uploadDetail = function() {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '电台上传详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/fm_upload/fm_upload_update/' + Fm_upload.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除电台上传
 */
Fm_upload.delete = function() {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/fm_upload/delete", function(data) {
            Feng.success("删除成功!");
            Fm_upload.table.refresh();
        }, function(data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("fm_uploadId", this.seItem.id);
        ajax.start();
    }
};



/**
 * 查询电台上传列表
 */
Fm_upload.search = function() {
    var queryData = {};
    queryData['sortType'] = $("#sort").val();
    queryData['minCreateTime'] = $("#createBeginTime").val();
    queryData['maxCreateTime'] = $("#createEndTime").val();
    queryData['minPublishTime'] = $("#publishBeginTime").val();
    queryData['maxPublishTime'] = $("#publishEndTime").val();
    queryData['publishStatus'] = $("#state").val();
    queryData['FmName'] = $("#FmName").val();
    console.log($("#FmName").val());
    console.log(queryData);

    Fm_upload.table.refresh({ query: queryData });
};



$(function() {
    var defaultColunms = Fm_upload.initColumn();
    var table = new BSTable(Fm_upload.id, "/fm_upload/list", defaultColunms);
    //    table.setPaginationType("client");
    //    table.setQueryParams(Fm_upload.formParams);
    Fm_upload.table = table.init();
});