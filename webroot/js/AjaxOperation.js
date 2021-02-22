/**
 * 负责组装相关ajax请求
 */
function AjaxOperation() {

}
/**
 * 访问系统的根路径
 */
AjaxOperation.prototype.callRoot = function(callback) {

	ajax({
		    type : 'GET',
		    url : 'callRoot.do',
		    success : function(response) {
			    callback(JSON.parse(response));
		    }
	    });
}
/**
 * 发起访问具体的文件的ajax请求
 * @param {} root 文件的路径
 * @param {} callback 回调函数
 */
AjaxOperation.prototype.callFile = function(path, callback) {
	ajax({
		    type : 'GET',
		    url : "callFile.do" + path,
		    success : function(response) {
			    callback(response);
		    }
	    });
}
/**
 * 修改文本的内容
 * @param {} path 待修改文本在服务器的相对地址
 * @param {} content 修改后的内容
 * @param {} callback 服务器完成修改后的回调函数
 */
AjaxOperation.prototype.saveFile = function(path, content, callback) {
	ajax({
		    type : 'POST',
		    url : "saveFile.do" + path,
		    data : {
			    path : path,
			    content : content
		    },
		    success : function(response) {
			    callback(response);
		    }
	    });
}

/**
 * 下载文件 该函数直接在当前页面打开下载窗口
 * 无需ajax
 * @param {} path
 * @param {} callback
 */

AjaxOperation.prototype.downloadFile = function(path, callback) {
	window.location.href = 'downloadFile.do' + path;

}
/**
 * 查看图片的方法
 * 在新页面打开图片
 * @param {} path图片的路径
 */
AjaxOperation.prototype.showFile = function(path) {
	window.open('openFile.do' + path, "");
}