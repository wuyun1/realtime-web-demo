package com.realtime.polling;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */

@WebServlet("/polling/getServerTime")
public class GetServerTime extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String nowTime = DateFormatUtils.format(System.currentTimeMillis(),
				"yyyy-MM-dd HH:mm:ss SSS");

		out.write(nowTime);
		out.flush();
		out.close();
	}
}