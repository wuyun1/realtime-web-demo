package com.realtime.observer;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author nieyong
 * @time 2012-8-22
 * @version 1.0
 */
public class Observable {
	private Set<Subscriber> observers;

	public Observable() {
		observers = new CopyOnWriteArraySet<Subscriber>();
	}

	/**
	 * 注册观察者
	 */
	public void register(Subscriber o) {
		if (o == null)
			throw new NullPointerException();
		observers.add(o);
	}

	/**
	 * 取笑观察者
	 */
	public void unregister(Subscriber o) {
		observers.remove(o);
	}

	/**
	 * 通知操作，即被观察者发生变化，通知对应的观察者进行事先设定的操作，不传参数的通知方法
	 */
	public void notifyObservers() {
		notifyObservers(null);
	}

	/**
	 * 与上面的那个通知方法不同的是，这个方法接受一个参数，这个参数一直传到观察者里，以供观察者使用
	 */
	public void notifyObservers(Object arg) {
		for (Subscriber observer : observers) {
			observer.update(arg);
		}
	}
}