package com.realtime.polling;

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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
@WebServlet(urlPatterns = "/polling/scriptTagDemo", asyncSupported = true)
public class ScriptTagAsyncDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(ScriptTagAsyncDemo.class);
	private final Queue<AsyncContext> asyncContexts = new ConcurrentLinkedQueue<AsyncContext>();
	private final ScheduledExecutorService executors = Executors
			.newScheduledThreadPool(1);

	@Override
	public void init() throws ServletException {
		log.debug("init now ...");
		executors.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				log.debug("run now ...");
				String nowTimeStr = DateFormatUtils.format(
						System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss SSS");
				while (!asyncContexts.isEmpty()) {
					log.debug("asyncContexts is not empty.");
					AsyncContext asyncContext = asyncContexts.poll();
					HttpServletRequest request = (HttpServletRequest) asyncContext
							.getRequest();

					String callback = request.getParameter("callback");

					if (StringUtils.isBlank(callback)) {
						callback = "$('#div').html";
					}

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

					response.setHeader("Cache-Control", "private");
					response.setHeader("Pragma", "no-cache");
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("text/javascript");
					
					out.write(String
							.format("ScriptCommunicator.callback_called = true;%s('Now Time : %s');\n", callback, nowTimeStr));
					out.flush();
					asyncContext.complete();
				}
			}
		}, 10L, 20L, TimeUnit.SECONDS);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!request.isAsyncSupported()) {
			log.error("the servlet is not supported Async");
			return;
		}

		AsyncContext asyncContext = request.startAsync(request, response);
		asyncContext.setTimeout(30L * 1000L);
		asyncContexts.offer(asyncContext);
	}
}