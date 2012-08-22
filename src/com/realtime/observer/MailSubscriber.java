package com.realtime.observer;

/**
 * 日志记录器
 * 
 * @author nieyong
 * @time 2012-8-22
 * @version 1.0
 */
public class MailSubscriber implements Subscriber {

	public void update(Object arg) {
		System.out.println("发送邮件中  : " + arg);
	}
}