package com.realtime.polling;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
@WebServlet("/polling/getNextTimeJsonp")
public class GetNextTimeServletJsonp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String callback = request.getParameter("callback");

		if (StringUtils.isBlank(callback)) {
			callback = "showResult";
		}

		PrintWriter out = response.getWriter();

		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder
				.append(callback)
				.append("('")
				.append(DateFormatUtils.format(System.currentTimeMillis(),
						"yyyy-MM-dd HH:mm:ss SSS")).append("');");

		out.print(resultBuilder);
		out.flush();

		out.close();
	}
}
