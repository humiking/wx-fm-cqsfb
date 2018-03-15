/**
 * 电台上传管理初始化
 */
var Fm_upload = {
    id: "Fm_uploadTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Fm_upload.initColumn = function () {
	var columns = 
     [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '游客ID', field: 'userId', visible: true, align: 'center', valign: 'middle'},
        {title: '歌曲名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '歌曲演唱者', field: 'artistorName', visible: true, align: 'center', valign: 'middle'},        
        {title: '海报', field: 'poster', visible: true, align: 'center', valign: 'middle',formatter: this.imgFormatter},
        {title: '发布状态', field: 'publishStatus', visible: true, align: 'center', valign: 'middle',formatter:this.publishStatusFormatter},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',formatter:this.statusFormatter},
        {
        	title:'操作',
        	field:'operator',
        	align:'center',
        	events:this.operateEvents,
        	formatter:this.operateFormatter
        }
    ];
	return columns;
};

/**
 * 商品图片预览显示
 */
Fm_upload.imgFormatter = function(value){
	//console.log(value);
    return '<img src="' + value + '" style="width:80px; height:auto;" />';
}

/**
 * 发布状态转换
 */
Fm_upload.publishStatusFormatter = function(value) {
	if(value == 0){
		return '<span>未发布</span>';
	}
	if(value == 5){
		return '<span>已发布</span>';
	}
	if(value == 10){
		return '<span>已下架</span>';
	}
	
}

/**
 * 状态转换
 */
Fm_upload.statusFormatter = function(value) {
	if(value == 0){
		return '<span>正常</span>';
	}
	if(value == -1){
		return '<span>删除</span>'
	}
}

/**
 * 检查是否选中
 */
Fm_upload.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Fm_upload.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加电台上传
 */
Fm_upload.openAddFm_upload = function () {
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
Fm_upload.openFm_uploadDetail = function () {
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
Fm_upload.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/fm_upload/delete", function (data) {
            Feng.success("删除成功!");
            Fm_upload.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("fm_uploadId",this.seItem.id);
        ajax.start();
    }
};

Fm_upload.formParams = function(){
    var queryData = {};

    queryData['FmName'] = $("#FmName").val();
    console.log($("#FmName").val());
    
    return queryData;
}

/**
 * 查询电台上传列表
 */
Fm_upload.search = function () {

    
    Fm_upload.table.refresh({query: Fm_upload.formParams});
};

$(function () {
    var defaultColunms = Fm_upload.initColumn();
    var table = new BSTable(Fm_upload.id, "/fm_upload/list", defaultColunms);
//    table.setPaginationType("client");
//    table.setQueryParams(Fm_upload.formParams);
    Fm_upload.table = table.init();
});
