package com.realtime;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author nieyong
 * @time 2012-8-17
 * @version 1.0
 */
/**
 * HttpServletResponse简单包装器，逻辑简单
 * 
 * @author yongboy
 * @date 2011-1-14
 * @version 1.0
 */
public class MarkWapperedResponse extends HttpServletResponseWrapper {
	private PrintWriter writer = null;
	private static final String MARKED_STRING = "<!--marked filter--->";

	public MarkWapperedResponse(HttpServletResponse resp) throws IOException {
		super(resp);

		writer = new MarkPrintWriter(super.getOutputStream());
	}

	@Override
	public PrintWriter getWriter() throws UnsupportedEncodingException {
		return writer;
	}

	private static class MarkPrintWriter extends PrintWriter {

		public MarkPrintWriter(OutputStream out) {
			super(out);
		}

		@Override
		public void flush() {
			super.flush();

			super.println(MARKED_STRING);
		}
	}
}