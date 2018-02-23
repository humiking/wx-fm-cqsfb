/**
 * 接口查询管理初始化
 */
var Apisearch = {
    id: "ApisearchTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Apisearch.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: 'url', field: 'url', visible: true, align: 'left', valign: 'middle', sortable: false},
        {title: 'pv', field: 'pv', visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: 'err', field: 'err', visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: 'avg', field: 'avg', visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: 'max', field: 'max', visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: 'min', field: 'min', visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: 'ratio', field: 'ratio', visible: true, align: 'center', valign: 'middle', sortable: true},
    ];
};

/**
 * 检查是否选中
 */
Apisearch.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Apidetail.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加api详情
 */
Apisearch.openDetail = function () {

};

Apisearch.formParams = function () {

    var queryData = {};

    queryData['url'] = $("#url").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    
    return queryData;
};

/**
 * 查询接口查询列表
 */
Apisearch.search = function () {

    Apisearch.table.refresh({query: Apisearch.formParams()});
};

$(function () {
	
    
    var defaultColunms = Apisearch.initColumn();
    var table = new BSTable(Apisearch.id, "/apisearch/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Apisearch.formParams());
    Apisearch.table = table.init();
});
