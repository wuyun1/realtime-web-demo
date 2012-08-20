package com.realtime;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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
 * @date 2011-2-17
 * @version 1.0
 */
//@WebListener
public class NewBlogJsonpListener implements ServletContextListener {
	private static final Log log = LogFactory.getLog(NewBlogJsonpListener.class);
	public static final BlockingQueue BLOG_QUEUE = new LinkedBlockingQueue();
	public static final Queue ASYNC_AJAX_QUEUE = new ConcurrentLinkedQueue();

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

						String targetJSON = buildJsonString(blog);

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

							out.println(context.getRequest().getParameter(
									"callback")
									+ "(" + targetJSON + ");");
							out.flush();

							// 通知，执行完成函数
							context.complete();
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
		Map info = new HashMap();
		info.put("state", 1);
		info.put("content", blog.getContent());
		info.put("date",
				DateFormatUtils.format(blog.getPubDate(), "HH:mm:ss SSS"));

		JSONObject jsonObject = JSONObject.fromObject(info);

		return jsonObject.toString();
	}
}