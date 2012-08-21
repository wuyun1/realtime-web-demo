<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Script tag Long Polling With Callback Support</title>
</head>
<body>
<div id="div">Loading ...</div>

<div>&nbsp;</div>
<div id="pre"></div>
</body>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../js/script_communicator.js"></script>
<script type="text/javascript">
		var url = 'scriptTagDemo?callback=doRender&time=' + new Date().getTime();
		var on_success = function(){
			doRequst();
		};
		var on_error = function(){
			alert("Something went wrong!");
		};
		var doRequst = function(){
			ScriptCommunicator.sourceJavaScript(url, on_success, on_error);
		};
		function doRender(content){
			var preTime = $("#div").html();
			if(preTime != "Loading ..."){
				$("#pre").html("Pre Time : " + preTime.replace(/Now Time :/g,''));
			}
			
			$("#div").html("<strong>" + content + "</strong>");
		}
		doRequst();
</script>
</html>