package com.realtime.flash;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = "/init", loadOnStartup = 1)
public class StartFlashPolicyServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		new FlashSecurityServer(843).start();
		System.out.println("flash server 843 start ...");
	}
}
