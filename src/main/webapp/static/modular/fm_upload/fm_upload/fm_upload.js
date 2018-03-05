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
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

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

/**
 * 查询电台上传列表
 */
Fm_upload.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Fm_upload.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Fm_upload.initColumn();
    var table = new BSTable(Fm_upload.id, "/fm_upload/list", defaultColunms);
    table.setPaginationType("client");
    Fm_upload.table = table.init();
});
