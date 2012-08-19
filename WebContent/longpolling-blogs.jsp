<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Long Polling JSONP Blogs Example</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<meta name="author" content="yongboy@gmail.com" />
<meta name="keywords" content="servlet3, comet, ajax" />
<meta name="description" content="" />
<link type="text/css" rel="stylesheet" href="css/main.css" />
<script type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.5.min.js"></script>
<script type="text/javascript">
	String.prototype.template = function() {
		var args = arguments;
		return this.replace(/\{(\d )\}/g, function(m, i) {
			return args[i];
		});
	}
	var html = '<div class="logDiv">'
	'<div class="contentDiv">{0}</div>'
	'<div class="tipDiv">last date : {1}</div>'
	'<div class="clear">&nbsp;</div>'
	'</div>';

	function showContent(json) {
		$("#showDiv").prepend(html.template(json.content, json.date));
	}
	
	/*修改成非localhost地址形式以模拟跨域*/	
	var url = 'getNewBlogs?callback=?';
	function initJsonp() {
		$.getJSON(url)
				.success(function(json) {
					if (json.state == 1) {
						showContent(json);
					} else {
						initJsonp();
						return;
					}
				}).done(initJsonp).error(function() {
					alert("error!");
				});
	}
	
	$(function() {
		initJsonp();
	});
</script>
</head>
<body style="margin: 0; overflow: hidden">
	<div id="showDiv" class="inputStyle">loading ...</div>
</body>
</html>