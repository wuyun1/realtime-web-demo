package com.realtime;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
/**
 * XHR获取最新信息
 * 
 * @author yongboy
 * @date 2011-1-10
 * @version 1.0
 */
@WebServlet(urlPatterns = "/getnew", asyncSupported = true)
public class GetNewBlogPosts extends HttpServlet {
	private static final long serialVersionUID = 5656988888865656L;
	private static final Log log = LogFactory.getLog(GetNewBlogPosts.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Connection", "Keep-Alive");
		response.setHeader("Proxy-Connection", "Keep-Alive");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();

		// 若当前输出内容较少情况下，需要产生大约2KB的垃圾数据，诸如下面产生一些空格
		for (int i = 0; i < 10; i++) {
			writer.print("                                   ");
		}

		writer.println("<div class='logDiv,clear'>waiting for ......</div>");
		writer.flush();

		final AsyncContext ac = request.startAsync();
		// 设置成长久链接
		ac.setTimeout(10 * 60 * 1000);
		ac.addListener(new AsyncListener() {
			public void onComplete(AsyncEvent event) throws IOException {
				NewBlogXHRListener.ASYNC_XHR_QUEUE.remove(ac);
			}

			public void onTimeout(AsyncEvent event) throws IOException {
				NewBlogXHRListener.ASYNC_XHR_QUEUE.remove(ac);
			}

			public void onError(AsyncEvent event) throws IOException {
				NewBlogXHRListener.ASYNC_XHR_QUEUE.remove(ac);
			}

			public void onStartAsync(AsyncEvent event) throws IOException {
				log.info("now add the AsyncContext");
			}
		});

		NewBlogXHRListener.ASYNC_XHR_QUEUE.add(ac);
	}
}