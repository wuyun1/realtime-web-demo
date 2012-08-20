<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String serverUrl = request.getServerName()
			+ (request.getServerPort() == 80 ? "" : ":"
					+ request.getServerPort())
			+ request.getServletContext().getContextPath();
	request.setAttribute("serverUrl", serverUrl);
%>
<!DOCTYPE html>
<html>
<head>
<title>Flash Web Socket Test</title>
<script type="text/javascript" src="../js/swfobject.js"></script>
<script type="text/javascript" src="../js/web_socket.js"></script>
<script type="text/javascript">
	// Set URL of your WebSocketMain.swf here:
	WEB_SOCKET_SWF_LOCATION = "../js/WebSocketMain.swf";
	// Set this to dump debug message from Flash to console.log:
	WEB_SOCKET_DEBUG = true;

	// Everything below is the same as using standard WebSocket.
	var socket;

	function init() {
		socket = new WebSocket("ws://${serverUrl}/websocket/tomcatWebsocket");
		socket.onmessage = function(event) {
			var ta = document.getElementById('responseText');
			ta.value = ta.value + '\n' + event.data
		};
		socket.onopen = function(event) {
			var ta = document.getElementById('responseText');
			ta.value = "Web Socket opened!";
		};
		socket.onclose = function(event) {
			var ta = document.getElementById('responseText');
			ta.value = ta.value + "Web Socket closed";
		};
		socket.onerror = function() {
			var ta = document.getElementById('responseText');
			ta.value = "Web Socket Error!";
		};
	}

	function send(message) {
		if (!window.WebSocket) {
			return;
		}
		if (socket.readyState == WebSocket.OPEN) {
			socket.send(message);
		} else {
			alert("The socket is not open.");
		}
	}
</script>
</head>
<body onload="init();">
	<form>
		<input type="text" name="message" value="Hello, World!" /><input
			type="button" value="Send Web Socket Data"
			onclick="send(this.form.message.value)" />
		<h3>Output</h3>
		<textarea id="responseText" style="width: 500px; height: 300px;"></textarea>
	</form>
</body>
</html>