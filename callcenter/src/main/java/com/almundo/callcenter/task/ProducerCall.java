package com.almundo.callcenter.task;

import com.almundo.callcenter.app.Dispatcher;
import com.almundo.callcenter.domain.Call;

public class ProducerCall implements Runnable{
	
	private Dispatcher dispatcher;
	private Integer idCall;
	
	public ProducerCall(Dispatcher dispatcher,Integer idCall) {
		this.dispatcher = dispatcher;
		this.idCall = idCall;
	}
	public void run() {
		Call call = new Call(this.idCall);
		dispatcher.dispatchCall(call);
	}
}
