package com.realtime.spdy;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author nieyong
 * @time 2012-8-20
 * @version 1.0
 */
public class SPDYServer {

	public static void main(String[] args) {
		// bootstrap is used to configure and setup the server
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		bootstrap.setPipelineFactory(new SPDYPipelineFactory());
		bootstrap.bind(new InetSocketAddress(8443));
	}
}
