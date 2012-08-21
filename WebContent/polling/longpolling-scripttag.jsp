<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Script tag Long Polling</title>
</head>
<body>
<div id="div">Loading ...</div>
</body>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../js/script_communicator.js"></script>
<script type="text/javascript">
		var url = 'scriptTagDemo?time=' + new Date().getTime();
		var on_success = function(){
			doRequst();
		};
		var on_error = function(){
			alert("Something went wrong!");
		};
		var doRequst = function(){
			ScriptCommunicator.sourceJavaScript(url, on_success, on_error);
		};
		doRequst();
</script>
</html>