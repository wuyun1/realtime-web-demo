package com.realtime.spdy;

/**
 * @author nieyong
 * @time 2012-8-20
 * @version 1.0
 */
import static org.jboss.netty.channel.Channels.pipeline;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.ssl.SslHandler;
 
public class SPDYPipelineFactory implements ChannelPipelineFactory {
 
	private SSLContext context;
 
	public SPDYPipelineFactory() {
		try {
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(new FileInputStream("src/main/resources/server.jks"),
					"secret".toCharArray());
 
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keystore, "secret".toCharArray());
 
			context = SSLContext.getInstance("TLS");
			context.init(kmf.getKeyManagers(), null, null);
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = pipeline();
 
		// Uncomment the following line if you want HTTPS
		SSLEngine engine = context.createSSLEngine();
		engine.setUseClientMode(false);
 
		NextProtoNego.put(engine, new SimpleServerProvider());
		NextProtoNego.debug = true;
 
		pipeline.addLast("ssl", new SslHandler(engine));
		pipeline.addLast("pipeLineSelector", new HttpOrSpdyHandler());
 
		return pipeline;
	}
}