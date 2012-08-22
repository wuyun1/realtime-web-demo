package com.realtime.observer;

/**
 * @author nieyong
 * @time 2012-8-22
 * @version 1.0
 */
public class Publisher extends Observable {
	/**
	 * 业务方法，一旦执行某个操作，则通知观察者
	 */
	public void doBusiness() {
		System.out.println("准备发表日志啦...");
		notifyObservers("发表第一篇日志啦!");
	}

	public static void main(String[] args) {
		Publisher publisher = new Publisher();

		Subscriber mailSubscriber = new MailSubscriber();
		Subscriber logSubscriber = new LogSubscriber();
		SMSSubscriber smsSubscriber = new SMSSubscriber();

		publisher.register(mailSubscriber);
		publisher.register(logSubscriber);
		publisher.register(smsSubscriber);

		publisher.doBusiness();
	}
}