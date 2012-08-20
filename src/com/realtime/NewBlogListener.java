package com.realtime;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

/**
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
/**
 * 监听器单独线程推送到客户端
 * 
 * @author yongboy
 * @date 2011-1-13
 * @version 1.0
 */
//@WebListener
public class NewBlogListener implements ServletContextListener {
	private static final Log log = LogFactory.getLog(NewBlogListener.class);
	public static final BlockingQueue<MicBlog> BLOG_QUEUE = new LinkedBlockingDeque<MicBlog>();
	public static final Queue<AsyncContext> ASYNC_AJAX_QUEUE = new ConcurrentLinkedQueue<AsyncContext>();
	private static final String TARGET_STRING = "<script type=\"text/javascript\">comet.showMsg(%s);</script>";

	private String getFormatContent(MicBlog blog) {
		return String.format(TARGET_STRING, buildJsonString(blog));
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("context is destroyed!");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("context is initialized!");
		// 启动一个线程处理线程队列
		new Thread(runnable).start();
	}

	private Runnable runnable = new Runnable() {
		public void run() {
			boolean isDone = true;

			while (isDone) {
				if (!BLOG_QUEUE.isEmpty()) {
					try {
						log.info("ASYNC_AJAX_QUEUE size : "
								+ ASYNC_AJAX_QUEUE.size());
						MicBlog blog = BLOG_QUEUE.take();

						if (ASYNC_AJAX_QUEUE.isEmpty()) {
							continue;
						}

						String targetJSON = getFormatContent(blog);

						for (AsyncContext context : ASYNC_AJAX_QUEUE) {
							if (context == null) {
								log.info("the current ASYNC_AJAX_QUEUE is null now !");
								continue;
							}
							log.info(context.toString());
							PrintWriter out = context.getResponse().getWriter();

							if (out == null) {
								log.info("the current ASYNC_AJAX_QUEUE's PrintWriter is null !");
								continue;
							}

							out.println(targetJSON);
							out.flush();
						}
					} catch (Exception e) {
						e.printStackTrace();
						isDone = false;
					}
				}
			}
		}
	};

	private static String buildJsonString(MicBlog blog) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("content", blog.getContent());
		info.put("date",
				DateFormatUtils.format(blog.getPubDate(), "HH:mm:ss SSS"));

		JSONObject jsonObject = JSONObject.fromObject(info);

		return jsonObject.toString();
	}
}