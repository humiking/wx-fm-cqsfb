/**
 * 初始化电台上传详情对话框
 */
var Fm_uploadInfoDlg = {
    fm_uploadInfoData : {}
};

/**
 * 清除数据
 */
Fm_uploadInfoDlg.clearData = function() {
    this.fm_uploadInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Fm_uploadInfoDlg.set = function(key, val) {
    this.fm_uploadInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Fm_uploadInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
Fm_uploadInfoDlg.close = function() {
    parent.layer.close(window.parent.Fm_upload.layerIndex);
}

/**
 * 收集数据
 */
Fm_uploadInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
Fm_uploadInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fm_upload/add", function(data){
        Feng.success("添加成功!");
        window.parent.Fm_upload.table.refresh();
        Fm_uploadInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fm_uploadInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
Fm_uploadInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fm_upload/update", function(data){
        Feng.success("修改成功!");
        window.parent.Fm_upload.table.refresh();
        Fm_uploadInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fm_uploadInfoData);
    ajax.start();
}

$(function() {

});
