<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EventSource Example</title>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
</head>
<body>
	<div id="trace"></div>
	<script type="text/javascript">
		if (window.EventSource) {
			var source = new EventSource('../eventsourceDemo');
			source.addEventListener('message', function(e) {
				trace(e.data);
			}, false);

			source.addEventListener('open', function(e) {
				// trace("Connection was opened.");
			}, false);

			source.addEventListener('error', function(e) {
				if (e.readyState == EventSource.CLOSED) {
					trace("Connection was closed.");
				}
			}, false);

			// 用户自定义事件(User Customer Event)
			source.addEventListener('userlogon', function(e) {
				var data = JSON.parse(e.data);
				trace('User login:' + data.username);
			}, false);

			source.addEventListener('update', function(e) {
				var data = JSON.parse(e.data);
				trace(data.username + ' is now ' + data.emotion);
			}, false);
		} else {
			trace("Your browser DOES NOT Support EventSoruce!");
		}
		function trace(msg) {
			$("#trace").prepend(msg + "<br/>");
		}
	</script>
</body>
</html>