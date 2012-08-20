<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Long Polling With XHR Example</title>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		function log(resp) {
			$("#tip").html("<b>" + resp + "</b>");
		}

		log("loading");

		$.ajaxSetup({
			cache : false
		});

		function initGet() {
			$.get("getNextTime").success(function(resp) {
				log(resp);
			}).error(function() {
				log("ERROR!");
			}).done(initGet);
		}

		initGet();
	});
</script>
</head>
<body>
	<div id="tip"></div>

</body>
</html>