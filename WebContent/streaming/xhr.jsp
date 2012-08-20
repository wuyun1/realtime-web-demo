<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>XMLHttpRequest Streaming Example</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<meta name="author" content="yongboy@gmail.com" />
<meta name="keywords" content="servlet3, comet, ajax" />
<meta name="description" content="" />
<link type="text/css" rel="stylesheet" href="css/main.css" />
</head>
<body style="margin: 0; overflow: hidden" onload="">
	<div id="showDiv" class="inputStyle"></div>
	<script>
		function showMsg(msg) {
			document.getElementById("showDiv").innerHTML = msg;
		}
		function doXHR() {
			var xhr = null;
			// 在IE8下面，window.XMLHttpRequest = true，因此需要window.XDomainRequest放在第一位置
			if (window.XDomainRequest) {
				xhr = new XDomainRequest();
			} else if (window.XMLHttpRequest) {
				xhr = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				var aVersions = [ "Msxml2.XMLHttp.5.0", "Msxml2.XMLHttp.4.0",
						"Msxml2.XMLHttp.3.0", "Msxml2.XMLHttp",
						"Microsoft.XMLHttp" ];
				for ( var i = 0; i < aVersions.length; i++) {
					try {
						xhr = new ActiveXObject(aVersions[i]);
						break;
					} catch (e) {
					}
				}
			}

			if (xhr == null) {
				showMsg("当前浏览器不支持创建XMLHttpRequest ！");
				return;
			}

			try {
				xhr.onload = function() {
					showMsg(xhr.responseText);
				};
				xhr.onerror = function() {
					showMsg("XMLHttpRequest Fatal Error.");
				};
				xhr.onreadystatechange = function() {
					try {
						if (xhr.readyState > 2) {
							showMsg(xhr.responseText);
						}
					} catch (e) {
						showMsg("XMLHttpRequest Exception: " + e);
					}
				};
				// 经测试：
				// IE8,Safayi完美支持onprogress事件（可以不需要onreadystatechange事件）；
				// Chrome也支持,在后台数据推送到时，会调用其方法，但无法得到responseText值；除非（长连接关闭）
				// Firefox 3.6 也支持，但得到返回值有些BUG
				xhr.onprogress = function() {
					showMsg(xhr.responseText);
				};
				xhr.open("GET", "xhrStreamingDemo?" + Math.random(), true);
				xhr.send(null);
			} catch (e) {
				showMsg("XMLHttpRequest Exception: " + e);
			}
		}
		if (window.addEventListener) {
			window.addEventListener("load", doXHR, false);
		} else if (window.attachEvent) {
			window.attachEvent("onload", doXHR);
		}
	</script>
</body>
</html>