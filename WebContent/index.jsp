<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Realtime Web Demos</title>
</head>
<body>
<h2>Realtime Web Demos</h2>
<div>
	<div>Polling Demo</div>
	<ol>
		<li><a href="polling/polling.jsp" target="_blank">Polling Example</a></li>
		<li><a href="polling/longpolling-xhr.jsp" target="_blank">Long Polling With XHR Example</a></li>
		<li><a href="polling/longpolling-xhr-async.jsp" target="_blank">Long Polling With XHR Async Support Example</a></li>
		<li><a href="polling/longpolling-scripttag.jsp" target="_blank">Script tag Long Polling</a></li>
		<li><a href="polling/longpolling-scripttagwithcallback.jsp" target="_blank">Script tag Long Polling With Callback Support</a></li>
		<li><a href="polling/polling-piggyback.jsp" target="_blank">Piggyback Polling Example</a></li>
		<!--
			<li><a href="" target="_blank"></a></li>
		-->
	</ol>
	
	<div>Streaming Demo</div>
	<ol>
		<li><a href="streaming/hidden-iframe.jsp" target="_blank">Hidden iFrame Example</a></li>
		<li><a href="streaming/xhr.jsp" target="_blank">XMLHttpRequest Streaming Example</a></li>
	</ol>
	
	<div>Websocket Demo</div>
	<ol>
		<li><a href="websocket/tomcat-7.0.29.jsp" target="_blank">Tomcat-7.0.29 Websocket Example</a></li>
		<li><a href="websocket/flash-websocket.jsp" target="_blank">Flash Websocket Example</a></li>
	</ol>
	
	<div>Server-Sent Events(EventSource) Example</div>
	<ol>
		<li><a href="eventsource/eventsource.jsp" target="_blank">EventSource Example</a></li>
		<!--li><a href="eventsource/eventsource-ie.jsp" target="_blank">EventSource With Support IE Example</a></li -->
	</ol>
</div>
</body>
</html>