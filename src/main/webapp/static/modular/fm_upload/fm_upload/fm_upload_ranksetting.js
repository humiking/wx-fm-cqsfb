/**
 * 初始化设置排名对话框
 */
var Fm_uploadRankDlg = {
	Fm_uploadRankData : {}
};

/**
 * 清除数据
 */
Fm_uploadRankDlg.clearData = function() {
    this.Fm_uploadRankData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Fm_uploadRankDlg.set = function(key, val) {
    this.Fm_uploadRankData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Fm_uploadRankDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
Fm_uploadRankDlg.close = function() {
    parent.layer.close(window.parent.Fm_upload.layerIndex);
}

/**
 * 收集数据
 */
Fm_uploadRankDlg.collectData = function() {
    this.set('weight');//收集权重
    if(!window.parent.Fm_upload.selectedId){
   	 Feng.error("添加失败!");
   }
   this.Fm_uploadRankData['fmId'] = window.parent.Fm_upload.selectedId;
}

/**
 * 传参
 */
Fm_uploadRankDlg.queryData = function(){
	var parmData = {};
	
}

/**
 * 提交权重设置
 */
Fm_uploadRankDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    console.log(this.Fm_uploadRankData);
    var paramData = this.Fm_uploadRankData;
    console.log(paramData);
    
    $.ajax({
        url:"/fm_upload/setWeight",
        type:"POST",
        dataType:"json",
        data:{
        	'weight':paramData.weight,
        	'fmId':paramData.fmId
        },
        success:function(res){
            if(res.successful){
            	Feng.success("添加成功!");
                window.parent.Fm_upload.table.refresh();
                window.parent.Fm_upload.selectedId = null;
                Fm_uploadRankDlg.close();
            }else {
            	Feng.error("添加失败!" + data.responseJSON.message + "!");
                window.parent.Fm_upload.selectedId = null;
                Fm_uploadRankDlg.close();
            }

        },
        error:function(msg){
            Feng.error(msg.error)

        }
    })

//    //提交信息
//    var ajax = new $ax(Feng.ctxPath + "/fm_upload/setWeight", function(data){
//        Feng.success("添加成功!");
//        window.parent.Fm_upload.table.refresh();
//        window.parent.Fm_upload.selectedId = null;
//        Fm_uploadRankDlg.close();
//    },function(data){
//        Feng.error("添加失败!" + data.responseJSON.message + "!");
//        window.parent.Fm_upload.selectedId = null;
//        Fm_uploadRankDlg.close();
//    });
//    ajax.set(this.fm_uploadRankData);
//    ajax.start();
}


$(function() {

});
