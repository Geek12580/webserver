/**
 * 负责处理dom相关操作
 */
function domOperation() {

}
/**
 * 处理树的点击事件的函数
 * 包括文件夹的伸缩，发起访问文件的请求
 * 在此函数中判断触发了哪个具体的事件并将请求转发到具体的处理函数
 * @param {} res 传入具体点击的是哪个元素
 * @param {} ajaxOp ajax操作对象
 * @param {} domOp  dom操作对象
 */
domOperation.prototype.handleClick = function(res, ajaxOp, domOp) {
	var module = res;
	// 如果点击的不是li标签，而是它的子元素，则将点击的元素替换成li元素
	while (module.tagName.toLocaleLowerCase() != 'li') {
		module = module.parentNode;
	}
	var node = document.getElementsByClassName("treeClick")[0];
	var div = module.firstChild;
	// 给选中的元素添加样式
	div.className = "treeClick";
	if (node) {
		// 消除上一次选中元素的样式
		node.className = "hoverDiv";
	}
	var array = module.id.split("&");
	var name = array[0];
	var isFolder = array[1];
	// 点击的文件，发起访问文件的请求
	if (isFolder == "false") {
		this.treeFileClick(module, ajaxOp, domOp);
	}
	// 点击了文件夹，进行伸展或收缩操作
	else {
		this.treeFolderClick(module, name);
	}

}

/**
 * 访问文件的处理函数
 * @param {} res 具体点击的元素
 * @param {} ajaxOp ajax操作对象
 * @param {} domOp  dom操作对象
 */
domOperation.prototype.treeFileClick = function(res, ajaxOp, domOp) {
	var module = res;
	// 取出元素保存的文件名
	var requestPath = module.id.split("&")[0];
	/*
	 * 循环遍历父元素，找出父元素的文件名
	 * 并拼接成路径  
	 * 注意：这里得到的路径为逆序的
	 */
	var subModule = module.parentNode;
	while (subModule.id != "list") {
		if (subModule.tagName.toLocaleLowerCase() == 'li' && subModule.id) {
			requestPath = requestPath + "/" + subModule.id.split("&")[0];
		}
		subModule = subModule.parentNode;
	}
	/*
	 * 先逆序文件路径
	 * 然后在调用ajax的相关函数
	 */
	var path = this.reversePath(requestPath);
	// 文件的后缀名为图片，在新页面打开
	var endwith = path.substring(path.lastIndexOf(".") + 1, requestPath.length);
	if (endwith == "jpg" || endwith == "png" || endwith == "gif") {
		ajaxOp.showFile(path);
	}
	else {
		ajaxOp.callFile(path, function(data) {
			    var textArea = document.getElementById("text-editor");
			    domOp.showContent(data, textArea);
		    });
	}
	// 将当前选中的文件名称显示到标题栏的提示中
	var caption = document.getElementById("caption");
	caption.innerHTML = path;
}

/**
 * 反转文件路径
 * @param {} requestPath 待转的文件路径
 */
domOperation.prototype.reversePath = function(requestPath) {
	var array = requestPath.split("/");
	array = array.reverse();
	requestPath = array.join("/");
	return requestPath;
}

/**
 *	处理树的展开折叠函数
 * @param {} res 响应的节点元素
 */
domOperation.prototype.treeFolderClick = function(res) {
	var module = res;
	var par = module.lastChild;
	var span = module.firstChild.firstChild.nextSibling.firstChild;
	// 树展开
	if (par.style.display == 'none') {
		par.style.display = 'block';
		span.className = "switchOpen";
	}
	// 树折叠
	else {
		par.style.display = 'none';
		span.className = "switchclose";
	}
}
/**
 * 在富文本框中显示文本信息
 * @param {} data 传入的数据
 * @param {} textArea 要显示文本的富文本容器
 */
domOperation.prototype.showContent = function(data, textArea) {
	textArea.value = data;
	textArea.readOnly = "";
}