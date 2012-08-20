package com.realtime.eventsource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * EventsourceDemo Demo
 */
@WebServlet("/eventsourceDemo")
public class EventsourceDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String NEW_MESSAGE = "id: %s\ndata: server time: %s";
	private final List<String> messages = new ArrayList<String>();
	
	{
		// 设置每次连接断开事件为10秒之后重新连接
		messages.add("retry: 10000\ndata: My message\n\n");
		messages.add("data: first line\ndata: second line\n\n");

		messages.add("data: {\"msg\": \"First message\"}\n\n"
				+ "event: userlogon\n"
				+ "data: {\"username\": \"John123\"}\n\n"
				+ "event: update\n"
				+ "data: {\"username\": \"John123\", \"emotion\": \"happy\"}\n\n");
	}

	/**
	 * 这里不用考虑线程安全问题
	 */
	private int num = 0;
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/event-stream");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Access-Control-Allow-Origin", "*");

		if (num < messages.size()) {
			print(response, messages.get(num));
			num = num + 1;
		} else {
			long time = System.currentTimeMillis();
			print(response, String.format(NEW_MESSAGE, time,
					DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss SSS")));
		}
	}

	private void print(HttpServletResponse response, String message) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		out.write(message);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}