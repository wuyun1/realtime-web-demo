package com.realtime.spdy;

import java.util.Arrays;
import java.util.List;

/**
 * @author nieyong
 * @time 2012-8-20
 * @version 1.0
 */
public class SimpleServerProvider implements ServerProvider {
 
	private String selectedProtocol = null;
 
	public void unsupported() {
		//if unsupported, default to http/1.1
		selectedProtocol = "http/1.1";
	}
 
	public List<String> protocols() {
		   return Arrays.asList("spdy/2","http/1.1");
	}
 
	public void protocolSelected(String protocol) {
		selectedProtocol = protocol;
	}
 
	public String getSelectedProtocol() {	
		return selectedProtocol;
	}
}
