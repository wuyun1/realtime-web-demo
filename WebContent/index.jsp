<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Realtime Web Examples</title>
<style>
div{}
</style>
</head>
<body>
<h1>Realtime Web Examples</h1>
<div>
	<h3>Polling Examples</h3>
	<ol>
		<li><a href="polling/polling.jsp" target="_blank">Polling Example</a></li>
		<li><a href="polling/polling-piggyback.jsp" target="_blank">Piggyback Polling Example</a></li>
		<li><a href="polling/longpolling-xhr.jsp" target="_blank">Long Polling With XHR Example</a></li>
		<li><a href="polling/longpolling-xhr-async.jsp" target="_blank">Long Polling With XHR Async Support Example</a></li>
		<li><a href="polling/longpolling-scripttag.jsp" target="_blank">Script tag Long Polling</a></li>
		<li><a href="polling/longpolling-scripttagwithcallback.jsp" target="_blank">Script tag Long Polling With Callback Support</a></li>
		<!--
			<li><a href="" target="_blank"></a></li>
		-->
	</ol>
	
	<h3>Streaming Examples</h3>
	<ol>
		<li><a href="streaming/hidden-iframe.jsp" target="_blank">Hidden iFrame Example</a></li>
		<li><a href="streaming/xhr.jsp" target="_blank">XMLHttpRequest Streaming Example</a></li>
	</ol>
	
	<h3>Websocket Examples</h3>
	<ol>
		<li><a href="websocket/tomcat-7.0.29.jsp" target="_blank">Tomcat-7.0.29 Websocket Example</a></li>
		<li><a href="websocket/flash-websocket.jsp" target="_blank">Flash Websocket Example</a></li>
	</ol>
	
	<h3>Server-Sent Events(EventSource) Example</h3>
	<ol>
		<li><a href="eventsource/eventsource.jsp" target="_blank">EventSource Example</a></li>
		<!--li><a href="eventsource/eventsource-ie.jsp" target="_blank">EventSource With Support IE Example</a></li -->
	</ol>
	
	<h3>Async Filter Example</h3>
	<ol>
		<li><a href="demoAsyncLink" target="_blank">Async Servlet with Async Filter</a></li>
		<li><a href="demoAsyncLink2" target="_blank">Async Servlet Without Async Filter</a></li>
	</ol>
	
	
	<h3>Socket.io Example(with socketio-netty server)</h3>
	<ol>
		<li><a href="socket.io/chat.html" target="_blank">Chat Example</a></li>
		<li><a href="socket.io/whiteboard.html" target="_blank">Whiteboard Example</a></li>
	</ol>
	
	<h3>Google PubSubHubbub Subscriber Callback Example</h3>
	<ol>
		<li>See java source : src/com/realtime/pubsubhubbub/SubscriberCallback.java</li>
	</ol>
	
	<h3>Java Observer Pattern Example</h3>
	<ol>
		<li>See java source : src/com/realtime/observer/Publisher.java</li>
	</ol>
</div>
</body>
</html>