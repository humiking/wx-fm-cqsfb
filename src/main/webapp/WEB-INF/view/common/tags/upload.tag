@/*
    多图上传:
    name : 名称
    id : id
@*/
<div class="form-group">
    <div class="col-sm-12 control-label head-scu-label">${name}</div>
    <div class="col-sm-12">
 	   <div id="uploader${id}" class="wu-example">
          <div class="queueList">
              <div id="dndArea${id}" class="placeholder">
                  <div id="filePicker${id}"></div>
                  <p>或将照片拖到这里</p>
              </div>
          </div>
          <div class="statusBar" style="display:none">
              <div class="progress">
                  <span class="text">0%</span>
                  <span class="percentage"></span>
              </div>    
              <div class="info"></div>
              <div class="btns">
                  <div id="filePicker_c${id}" class="webuploader-btn"></div>
                  <div class="upload-btn uploadBtn"><i class="fa fa-upload"></i>&nbsp;开始上传</div>
              </div>
          </div>
      </div> <!-- uploader end -->
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


