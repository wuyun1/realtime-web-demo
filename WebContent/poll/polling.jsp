<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Poll Example</title>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		var stop = true;
		var interval = null;
		var polling = function() {
			$.get("getServerTime", function(data) {
				$("#result").html("<strong>" + data + "</strong>");
				if (stop){
					clearInterval(interval);
				}
			}, "text");
		}	

		$("#btn").click(function(){
			stop = !stop;			
			if(!stop){
				$(this).val("Stop");
				interval = setInterval(polling, 1000);
			}else{
				$(this).val("Start");
			}
		});
	});
</script>
</head>
<body>
	<h3>Get Server Time</h3>
	<div>
		<div>
			<input type="button" id="btn" value="Start">
		</div>
		<div id="result"></div>
	</div>
</body>
</html>