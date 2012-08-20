<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Hidden iFrame Streaming Example</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<meta name="author" content="yongboy@gmail.com" />
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	var html = '<div class="logDiv"><div class="contentDiv">{0}</div>'
			+ '<div class="clear">&nbsp;</div></div>';

	function showContent(dateStr) {
		$("#showDiv").prepend(html.template(dateStr));
	}
	var server = 'hiddenIFrameDemo';
	$(function(){
		$("#showDiv").append("loading ...");
	});
</script>
<script type="text/javascript" src="../js/comet.js"></script>
</head>
<body>
	<div id="showDiv" class="inputStyle"></div>
</body>
</html>