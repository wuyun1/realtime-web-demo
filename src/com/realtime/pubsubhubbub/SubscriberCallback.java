package com.realtime.pubsubhubbub;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * 0. 教程地址 http://www.kangye.org/getting-started-with-pubsubhubbub/ <br/>
 * 1. 到此地址注册为订阅者 http://pubsubhubbub.appspot.com/subscribe <br/>
 * 2. 部署此应用到公网，具有一个可以访问地址,那么回调地址则是 http://youdomain.com/callback <br/>
 * 3. http://youdomain.com/callback get请求提供应答，post 请求则为接收数据 <br/>
 * 
 * @author nieyong
 * @time 2012-8-22
 * @version 1.0
 */
@WebServlet(urlPatterns = "/callback")
public class SubscriberCallback extends HttpServlet {
	private static final long serialVersionUID = 734511555662532982L;
	private static final Log log = LogFactory.getLog(SubscriberCallback.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("time : "
				+ DateFormatUtils.format(System.currentTimeMillis(),
						"yyyy-MM-dd HH:mm:ss"));

		String hubchallenge = request.getParameter("hub.challenge");
		response.setStatus(200);
		PrintWriter out = response.getWriter();

		if (hubchallenge != null && !hubchallenge.equals("")) {
			out.print(hubchallenge);
		} else {
			out.print("none");
		}

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n接受POST数据 time : \n"
				+ DateFormatUtils.format(System.currentTimeMillis(),
						"yyyy-MM-dd HH:mm:ss"));

		InputStream inputStream = null;

		String contentEncoding = request.getHeader("Content-Encoding");
		if (contentEncoding != null
				&& StringUtils.equalsIgnoreCase(contentEncoding, "gzip")) {
			inputStream = new GZIPInputStream(request.getInputStream());
		} else {
			inputStream = request.getInputStream();
		}

		List<BlogEntity> blogs = analyseList(inputStream);

		// your code here ...

		for (BlogEntity blog : blogs) {
			log.debug("blog info : " + ToStringBuilder.reflectionToString(blog));
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private List<BlogEntity> analyseList(InputStream inputStream) {
		List<BlogEntity> blogList = new ArrayList<BlogEntity>();

		XmlReader reader = null;
		try {
			reader = new XmlReader(inputStream);
			// log.info("Rss源的编码格式为：" + reader.getEncoding());
			SyndFeedInput input = new SyndFeedInput();
			// 得到SyndFeed对象，即得到Rss源里的所有信息
			SyndFeed feed = input.build(reader);
			// 得到Rss新闻中子项列表
			List entries = feed.getEntries();
			// 循环得到每个子项信息
			BlogEntity blogEntry = null;
			SyndEntry entry = null;
			for (int i = 0; i < entries.size(); i++) {
				blogEntry = new BlogEntity();
				entry = (SyndEntry) entries.get(i);

				SyndFeed linkSynd = entry.getSource();
				SyndContent description = entry.getDescription();
				blogEntry.setUrl(entry.getLink());
				blogEntry.setTitle(entry.getTitle());
				if (description != null) {
					blogEntry.setDescription(description.getValue());
				} else {
					if (entry.getContents() == null
							|| entry.getContents().size() == 0) {
					} else {
						SyndContent syndContent = (SyndContent) entry
								.getContents().get(0);
						blogEntry.setDescription(syndContent.getValue());
					}
				}

				blogEntry.setInDate(new Date());
				try {
					if (linkSynd != null) {
						blogEntry.setAuthor(linkSynd.getTitle());
					} else if (entry != null) {
						blogEntry.setAuthor(entry.getAuthor());
					} else {
						log.info("暂无作者和主页信息:\n" + entry.toString());
						blogEntry.setAuthor("未知");
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
					log.error("error entry:\n" + entry.toString());
				} catch (Exception e) {
					e.printStackTrace();
					log.error("error entry:\n" + entry.toString());
				}

				// 设置发布时间为当前时间
				blogEntry.setPubDate(entry.getPublishedDate());

				List categoryList = entry.getCategories();
				if (categoryList != null) {
					for (int m = 0; m < categoryList.size(); m++) {
						SyndCategory category = (SyndCategory) categoryList
								.get(m);
						blogEntry.setDescription(category.getName());
						// log.error("此标题所属的范畴：" + category.getName());
					}
				}

				// System.out.println("********************************************");
				// System.out.println("title:" + stockBlog.getTitle());
				// System.out.println("link:" + stockBlog.getUrl());
				// System.out.println("homepage:" + stockBlog.getHomepage());
				// System.out.println("author:" + stockBlog.getAuthor());
				// System.out.println("comment:" + stockBlog.getComment());
				// System.out.println("description:" +
				// stockBlog.getDescription());
				// System.out.println("********************************************");

				blogList.add(blogEntry);
			}
			entry = null;
			blogEntry = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				log.error("ERROR!\n" + e.toString());
				e.printStackTrace();
			}
		}

		return blogList;
	}
}