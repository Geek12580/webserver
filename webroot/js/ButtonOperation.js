/**
 * 负责按钮的相关操作
 */
function ButtonOperation() {

}
/**
 * 页面初始化绑定button函数
 * @param {} btnOp button操作对象
 * @param {} ajaxOp ajax操作对象
 */
ButtonOperation.prototype.bindingBtn = function(btnOp, ajaxOp) {
	var saveBtn = document.getElementById("save");
	var downloadBtn = document.getElementById("download");
	// 保存按钮添加监听事件
	saveBtn.addEventListener("click", function(event) {
		    btnOp.save(btnOp, ajaxOp);
	    });
	// 下载按钮保存监听事件
	downloadBtn.addEventListener("click", function(event) {
		    btnOp.download(btnOp, ajaxOp);
	    });
}
/**
 * 保存按钮触发函数
 * @param {} btnOp button操作对象
 * @param {} ajaxOp ajax操作对象
 */
ButtonOperation.prototype.save = function(btnOp, ajaxOp) {
	var caption = document.getElementById("caption");
	// 取出选中元素的path
	var path = caption.innerHTML;
	var endwith = path.substring(path.lastIndexOf(".") + 1, path.length);
	// 组件save按钮的名字为默认值，则表示没有选中任何元素，给予提示信息
	if (path == "无") {
		alert("请您选中需要保存的文件！");
	}
	else if (endwith == "jpg" || endwith == "png" || endwith == "gif") {
		alert("目前只支持修改文本文件!");
	}
	else {
		var textArea = document.getElementById("text-editor");
		// 将富文本框中的值读取出来发送至服务器
		var content = textArea.value;
		ajaxOp.saveFile(path, content, function(data) {
			    alert(data);
		    })
	}
	btnOp.resetModules();
}

/**
 * 按钮事件响应完成后，重置提示文字
 * 重置文本框为readonly
 * @param {} data
 */
ButtonOperation.prototype.resetModules = function() {
	var textArea = document.getElementById("text-editor");
	var caption = document.getElementById("caption");
	caption.innerHTML = "无";
	textArea.readOnly = "readOnly";
}
/**
 * 下载文件触发函数
 * @param {} btnOp button操作对象
 * @param {} ajaxOp ajax操作对象
 */
ButtonOperation.prototype.download = function(btnOp, ajaxOp) {
	var caption = document.getElementById("caption");
	// 取出选中元素的path
	var path = caption.innerHTML;
	// 组件save按钮的名字为默认值，则表示没有选中任何元素，给予提示信息
	if (path == "无") {
		alert("请您选中需要下载的文件!");
	}
	else {
		// 调用ajax对象文件下载函数
		ajaxOp.downloadFile(path, function(data) {
			    btnOp.resetModules(data);
		    })
	}
}