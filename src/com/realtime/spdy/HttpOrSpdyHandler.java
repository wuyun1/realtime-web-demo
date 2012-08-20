package com.realtime.spdy;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.codec.spdy.SpdyFrameDecoder;
import org.jboss.netty.handler.codec.spdy.SpdyFrameEncoder;
import org.jboss.netty.handler.codec.spdy.SpdyHttpDecoder;
import org.jboss.netty.handler.codec.spdy.SpdyHttpEncoder;
import org.jboss.netty.handler.codec.spdy.SpdySessionHandler;
import org.jboss.netty.handler.ssl.SslHandler;

/**
 * @author nieyong
 * @time 2012-8-20
 * @version 1.0
 */


public class HttpOrSpdyHandler implements  ChannelUpstreamHandler {
 
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
 
		// determine protocol type
		SslHandler handler = ctx.getPipeline().get(SslHandler.class);
		SimpleServerProvider provider = (SimpleServerProvider) NextProtoNego.get(handler.getEngine());
 
		if ("spdy/2".equals(provider.getSelectedProtocol())) {
			ChannelPipeline pipeline = ctx.getPipeline();
 
	        pipeline.addLast("decoder", new SpdyFrameDecoder());
	        pipeline.addLast("spdy_encoder", new SpdyFrameEncoder());
	        pipeline.addLast("spdy_session_handler", new SpdySessionHandler(true));
	        pipeline.addLast("spdy_http_encoder", new SpdyHttpEncoder());
 
               // Max size of SPDY messages set to 1MB
	        pipeline.addLast("spdy_http_decoder", new SpdyHttpDecoder(1024*1024)); 
	        pipeline.addLast("handler", new HttpRequestHandler());
 
	        // remove this handler, and process the requests as spdy
	        pipeline.remove(this);
			ctx.sendUpstream(e);
		}  else if ("http/1.1".equals(provider.getSelectedProtocol())) {
			ChannelPipeline pipeline = ctx.getPipeline();
			pipeline.addLast("decoder", new HttpRequestDecoder());
			pipeline.addLast("http_encoder", new HttpResponseEncoder());
			pipeline.addLast("handler", new HttpRequestHandler());
 
			// remove this handler, and process the requests as http
			pipeline.remove(this);
			ctx.sendUpstream(e);
		} else {
			// we're still in protocol negotiation, no need for any handlers
			// at this point.
		}
	}
}