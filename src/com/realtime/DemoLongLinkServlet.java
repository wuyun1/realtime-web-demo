package com.realtime;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
@WebServlet("/demoLongLink")
public class DemoLongLinkServlet extends HttpServlet {
	private static final long serialVersionUID = 4617227991063927036L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Connection", "Keep-Alive");
		response.setHeader("Proxy-Connection", "Keep-Alive");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();
		out.println("<div>Start ...</div>");
		out.flush();

		int num = 0;
		int max = 100;
		while (true) {
			out.println("<div>" + (num++) + "</div>");
			out.flush();

			if (num >= max) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		out.println("<div>Done !</div>");
		out.flush();
		out.close();
	}
}