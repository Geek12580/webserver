<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/style.css" type="text/css" />
<script type="text/javascript" src="js/ajax.js"></script>
<script type="text/javascript" src="js/FileList.js"></script>
<script type="text/javascript" src="js/domOperation.js"></script>
<script type="text/javascript" src="js/AjaxOperation.js"></script>
<script type="text/javascript" src="js/ButtonOperation.js"></script>
<title>文件管理系统</title>
</head>
<body>
	<div class="container">

		<div class="header">
			<h1 class="title">文件管理系统</h1>
		</div>
		<div id="menu">
			<ul id="list">
			</ul>
		</div>

		<div class="content">
			<div class="caption">
				你选中了:<span id="caption">无</span>
			</div>
			<div class="editor">
				<textarea class="text-editor" id="text-editor" name="text"
					readonly="readonly"></textarea>
			</div>
			<div class="btn_area">
				<button id="save" name="save" class="btn btn-sd">保存</button>
				<button id="download" name="download" class="btn btn-sd">下载</button>
			</div>
		</div>

		<div class="footer">鄂公网安备-42010302000385号|鄂ICP备-13008932号版权所有 ©
			2012-现在 武汉东方赛思软件股份有限公司.</div>
	</div>
	<script>
		var list = new FileList();
		list.loadAllModules();
	</script>
</body>
</html>