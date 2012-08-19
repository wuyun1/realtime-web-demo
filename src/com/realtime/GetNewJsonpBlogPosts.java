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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
@WebServlet(urlPatterns = "/getjsonpnew", asyncSupported = true)
public class GetNewJsonpBlogPosts extends HttpServlet {
	private static final long serialVersionUID = 565698895565656L;
	private static final Log log = LogFactory
			.getLog(GetNewJsonpBlogPosts.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Connection", "Keep-Alive");
		response.setHeader("Proxy-Connection", "Keep-Alive");
		response.setContentType("text/javascript;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String timeoutStr = request.getParameter("timeout");

		long timeout;

		if (StringUtils.isNumeric(timeoutStr)) {
			timeout = Long.parseLong(timeoutStr);
		} else {
			// 设置1分钟
			timeout = 1L * 60L * 1000L;
		}

		log.info("new request ...");
		final HttpServletResponse finalResponse = response;
		final HttpServletRequest finalRequest = request;
		final AsyncContext ac = request.startAsync(request, finalResponse);
		// 设置成长久链接
		ac.setTimeout(timeout);
		ac.addListener(new AsyncListener() {
			public void onComplete(AsyncEvent event) throws IOException {
				log.info("onComplete Event!");
				NewBlogJsonpListener.ASYNC_AJAX_QUEUE.remove(ac);
			}

			public void onTimeout(AsyncEvent event) throws IOException {
				// 尝试向客户端发送超时方法调用，客户端会再次请求/blogpush，周而复始
				log.info("onTimeout Event!");

				// 通知客户端再次进行重连
				final PrintWriter writer = finalResponse.getWriter();

				String outString = finalRequest.getParameter("callback")
						+ "({state:0,error:'timeout is now'});";
				writer.println(outString);
				writer.flush();
				writer.close();

				NewBlogJsonpListener.ASYNC_AJAX_QUEUE.remove(ac);
			}

			public void onError(AsyncEvent event) throws IOException {
				log.info("onError Event!");
				NewBlogJsonpListener.ASYNC_AJAX_QUEUE.remove(ac);
			}

			public void onStartAsync(AsyncEvent event) throws IOException {
				log.info("onStartAsync Event!");
			}
		});

		NewBlogJsonpListener.ASYNC_AJAX_QUEUE.add(ac);
	}
}