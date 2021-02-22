/**
 * 负责处理文件的相关操作
 */
function FileList() {

}
/**
 * 创建函数调用过程中所需要用到的对象
 * 初始化各组件
 */
FileList.prototype.loadAllModules = function() {
	this.domOp = new domOperation();
	this.ajaxOp = new AjaxOperation();
	var ajaxOpe = this.ajaxOp;
	var btnOp = new ButtonOperation();
	// 初始化button的绑定
	btnOp.bindingBtn(btnOp, ajaxOpe);
	this.loadList();
}
/**
 * 加载文件列表
 */
FileList.prototype.loadList = function() {
	var list = this;
	/*
	 * @data 通过ajax异步请求返回的
	 * 文件列表的json格式的数据
	 */
	list.ajaxOp.callRoot(function(data) {
		    var par = document.getElementById("list");
		    // 给整个文件列表添加点击的监听事件
		    par.addEventListener("click", function(event) {
			        var res = event.target || event.srcElement;
			        list.domOp.handleClick(res, list.ajaxOp, list.domOp);
		        });
		    list.showTree(data, par);
	    });
}

/**
 * 显示文件树
 * @param {} json 传过来的json数据
 * @param {} par 显示树在par所代表的容器中
 */
FileList.prototype.showTree = function(json, par) {
	for (var attr = 0; attr < json.length; attr++) {
		// 文件列表的一项基本单元
		var ele = document.createElement('li');
		// 嵌套在基本单元中的div图层
		var div = document.createElement("div");
		// 嵌套在div中的图标
		var img = document.createElement("img");
		div.className = "hoverDiv";
		img.className = "fileImg"
		ele.appendChild(div);
		div.appendChild(img);
		// 如果是文件，直接给li元素中添加div容器并组装成一个完整的li
		if (json[attr].folder == false) {
			img.src = "img/file.png";
			div.innerHTML += json[attr].name;
			ele.id = json[attr].name + "&" + json[attr].folder;
		}
		// 如果是文件夹，组装好li之外还要递归调用该方法，访问当前目录下子文件列表
		else {
			img.src = "img/folder.png";
			div.innerHTML += '<span><span class="switchClose""></span>' + json[attr].name + '</span>';
			var nextpar = document.createElement('ul');
			ele.appendChild(nextpar);
			nextpar.style.display = 'none';
			ele.id = json[attr].name + "&" + json[attr].folder;
			this.showTree(json[attr + 1], nextpar);
			attr++;
		}
		par.appendChild(ele);
	}
}
