package com.realtime.poll;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * 异步请求，每60秒统一输出当前服务器时间
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
@WebServlet(urlPatterns = "/poll/getNextTimeAsync", asyncSupported = true)
public class GetNextTimeServletAsync extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(GetNextTimeServletAsync.class);
	private final Queue<AsyncContext> asyncContexts = new ConcurrentLinkedQueue<AsyncContext>();
	private final ScheduledExecutorService executors = Executors
			.newScheduledThreadPool(1);

	@Override
	public void init() throws ServletException {
		log.debug("inti now ...");
		executors.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				log.debug("run now ...");
				String nowTimeStr = DateFormatUtils.format(
						System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss SSS");
				while (!asyncContexts.isEmpty()) {
					log.debug("asyncContexts is not empty.");
					AsyncContext asyncContext = asyncContexts.poll();
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

					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("text/plain");
					out.write(nowTimeStr);
					out.flush();
					asyncContext.complete();
				}
			}
		}, 10L, 60L, TimeUnit.SECONDS);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Connection", "Keep-Alive");
		response.setHeader("Proxy-Connection", "Keep-Alive");
		response.setContentType("text/plain;charset=UTF-8");

		if (!request.isAsyncSupported()) {
			log.error("the servlet is not supported Async");
			return;
		}

		AsyncContext asyncContext = request.startAsync(request, response);
		asyncContext.setTimeout(60L * 1000L);
		asyncContexts.offer(asyncContext);
	}
}