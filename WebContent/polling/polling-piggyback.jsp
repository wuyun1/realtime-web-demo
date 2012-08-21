<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Piggyback Polling Example</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">
        $(function() {
            function processEvents(events) {
                if (events.length) {
                    $('#logs').append('<span style="color: blue;">[client] ' + events.length + ' events</span><br/>');
                } else {
                    $('#logs').append('<span style="color: red;">[client] no event</span><br/>');
                }
                for (var i in events) {
                    $('#logs').append('<span>[event] ' + events[i] + '</span><br/>');
                }
            }

            $('#submit').click(function() {
                $('#logs').append('<span>[client] checking for events...</span><br/>');
                $.post('piggybackDemo', function(data) {
                    $('#logs').append('<span>[server] form valid ? ' + data.formValid + ' </span><br/>');
                    processEvents(data.events);
                });
            });

        });
    </script>
</head>
<body>
<button id="submit">Submit form</button>
<div id="logs" style="font-family: monospace;">
</div>
</body>
</html>
