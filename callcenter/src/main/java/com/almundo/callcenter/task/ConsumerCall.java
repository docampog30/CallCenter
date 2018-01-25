package com.almundo.callcenter.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.callcenter.app.Dispatcher;
import com.almundo.callcenter.domain.Role;
import com.almundo.callcenter.util.RandomUtil;

public class ConsumerCall implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);

	private Dispatcher dispatcher;
	private Role role;

	public ConsumerCall(Dispatcher dispatcher, Role role) {
		this.dispatcher = dispatcher;
		this.role = role;
	}
	
	public void run() {
		Long time = RandomUtil.asignRandomTimeToTask(); 
		this.dispatcher.getByRole(role, time);
	}
	
	
}
