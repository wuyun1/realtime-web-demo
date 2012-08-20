package com.realtime.poll;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 线程阻塞版本
 * 
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
@WebServlet("/poll/getNextTime")
public class GetNextTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 线程阻塞
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		out.print(DateFormatUtils.format(System.currentTimeMillis(),
				"yyyy-MM-dd HH:mm:ss SSS"));
		out.flush();

		out.close();
	}
}