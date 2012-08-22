package com.realtime.observer;

/**
 * 日志记录器
 * 
 * @author nieyong
 * @time 2012-8-22
 * @version 1.0
 */
public class LogSubscriber implements Subscriber {

	public void update(Object arg) {
		System.out.println("记录到日志文件中  : " + arg);
	}
}