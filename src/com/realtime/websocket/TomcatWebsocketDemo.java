package com.realtime.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 * Servlet implementation class TomcatWebsocket
 */
@WebServlet("/tomcatWebsocket")
public class TomcatWebsocketDemo extends WebSocketServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Tomcat 7.0.29 多了一个HttpServletRequest request参数
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String arg,
			HttpServletRequest request) {
		return new TheWebSocket();
	}

	private static class TheWebSocket extends MessageInbound {
		@Override
		public void onOpen(WsOutbound outbound) {
		}

		@Override
		public void onTextMessage(CharBuffer buffer) throws IOException {
			String message = buffer.toString();

			getWsOutbound().writeTextMessage(
					CharBuffer.wrap(message.toUpperCase()));
		}

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		}
	}
}