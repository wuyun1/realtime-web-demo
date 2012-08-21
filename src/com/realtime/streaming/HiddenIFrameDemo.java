package com.realtime.streaming;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
/**
 * 负责客户端的推送,30秒推送一次
 * 
 * @author yongboy
 * @date 2011-1-13
 * @version 1.0
 */
@WebServlet(urlPatterns = { "/streaming/hiddenIFrameDemo" }, asyncSupported = true)
public class HiddenIFrameDemo extends HttpServlet {
	private static final long serialVersionUID = 8546832356595L;
	private static final Log log = LogFactory.getLog(HiddenIFrameDemo.class);
	private final Queue<AsyncContext> asyncContexts = new ConcurrentLinkedQueue<AsyncContext>();
	private final ScheduledExecutorService executors = Executors
			.newScheduledThreadPool(1);
	private static final String TARGET_STRING = "<script type=\"text/javascript\">comet.showMsg(\"%s\");</script>\n";

	@Override
	public void init() throws ServletException {
		log.debug("inti now ...");
		executors.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				log.debug("run now ...");
				String nowTimeStr = DateFormatUtils.format(
						System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss SSS");
				Iterator<AsyncContext> iter = asyncContexts.iterator();
				while (iter.hasNext()) {
					AsyncContext asyncContext = iter.next();
					log.debug("asyncContexts is not empty.");
					HttpServletResponse response = (HttpServletResponse) asyncContext
							.getResponse();
					PrintWriter out = null;
					try {
						out = response.getWriter();
					} catch (IOException e) {
						e.printStackTrace();
						asyncContext.complete();
						continue;
					}

					out.write(String.format(TARGET_STRING, nowTimeStr));
					out.flush();
				}
			}
		}, 10L, 30L, TimeUnit.SECONDS);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Connection", "Keep-Alive");
		response.setHeader("Proxy-Connection", "Keep-Alive");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		final PrintWriter out = response.getWriter();

		// 创建Comet Iframe
		out.println("<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">");
		out.println("<script type=\"text/javascript\">var comet = window.parent.comet;</script>");
		out.flush();

		final AsyncContext asyncContext = request.startAsync();
		asyncContext.setTimeout(10 * 60 * 1000);// 10分钟时间;tomcat7下默认为10000毫秒

		asyncContext.addListener(new AsyncListener() {
			public void onComplete(AsyncEvent event) throws IOException {
				log.info("the event : " + event.toString()
						+ " is complete now !");
				asyncContexts.remove(asyncContext);
			}

			public void onTimeout(AsyncEvent event) throws IOException {
				log.info("the event : " + event.toString()
						+ " is timeout now !");

				// 尝试向客户端发送超时方法调用, 客户端会再次请求, 周而复始
				log.info("try to notify the client the connection is timeout now ...");
				String alertStr = "<script type=\"text/javascript\">comet.timeout();</script>";
				out.println(alertStr);
				out.flush();
				out.close();

				asyncContexts.remove(asyncContext);
			}

			public void onError(AsyncEvent event) throws IOException {
				log.info("the event : " + event.toString() + " is error now !");
				asyncContexts.remove(asyncContext);
			}

			public void onStartAsync(AsyncEvent event) throws IOException {
				log.info("the event : " + event.toString()
						+ " is Start Async now !");
			}
		});

		asyncContexts.add(asyncContext);
	}
}
