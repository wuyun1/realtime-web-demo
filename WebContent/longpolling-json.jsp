<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Long Polling with JSONP Example</title>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		function log(resp) {
			$("#tip").html("<b>" + resp + "</b>");
		}

		log("loading");

		/*此地址可以修改为非 localhost 以模拟跨域*/
		var url = 'getNextTime2?callback=?';
		function jsonp() {
			$.getJSON(url).success(function(resp) {
				log("now time : " + resp);
			}).done(jsonp);

			// 以下方式也是合法的
			/*
			$.getJSON('http://192.168.0.99:8080/servlet3/getNextTime2?callback=?',function(date){
			 log(date);
			}).done(jsonp);
			 */
		}
		jsonp();
	});
</script>
</head>
<body>
	<div id="tip"></div>
</body>
</html>