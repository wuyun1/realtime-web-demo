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
 * XHR获取最新信息
 * 
 * @author yongboy
 * @date 2011-1-10
 * @version 1.0
 */
@WebServlet(urlPatterns = "/streaming/xhrStreamingDemo", asyncSupported = true)
public class XMLHttpRequestStreamingDemo extends HttpServlet {
	private static final long serialVersionUID = 5656988888865656L;
	private static final Log log = LogFactory
			.getLog(XMLHttpRequestStreamingDemo.class);
	private final Queue<AsyncContext> asyncContexts = new ConcurrentLinkedQueue<AsyncContext>();
	private final ScheduledExecutorService executors = Executors
			.newScheduledThreadPool(1);
	private static final String TARGET_STRING = "<div class='logDiv,clear'>%s</div>\n";

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
		response.setHeader("Connection", "Keep-Alive");
		response.setHeader("Proxy-Connection", "Keep-Alive");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		// 若当前输出内容较少情况下，需要产生大约2KB的垃圾数据，诸如下面产生一些空格
		for (int i = 0; i < 10; i++) {
			out.print("                                   ");
		}

		out.println(String.format(TARGET_STRING, "waiting for ..."));
		out.flush();

		final AsyncContext ac = request.startAsync();
		// 设置成长久链接
		ac.setTimeout(10 * 60 * 1000);
		ac.addListener(new AsyncListener() {
			public void onComplete(AsyncEvent event) throws IOException {
				asyncContexts.remove(ac);
			}

			public void onTimeout(AsyncEvent event) throws IOException {
				asyncContexts.remove(ac);
			}

			public void onError(AsyncEvent event) throws IOException {
				asyncContexts.remove(ac);
			}

			public void onStartAsync(AsyncEvent event) throws IOException {
				log.info("now add the AsyncContext");
			}
		});

		asyncContexts.add(ac);
	}
}