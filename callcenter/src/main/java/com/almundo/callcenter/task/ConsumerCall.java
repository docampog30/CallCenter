package com.almundo.callcenter.task;

import com.almundo.callcenter.app.Dispatcher;
import com.almundo.callcenter.domain.Role;
import com.almundo.callcenter.util.RandomUtil;

public class ConsumerCall implements Runnable {

	private Dispatcher dispatcher;
	private Role role;

	public ConsumerCall(Dispatcher dispatcher, Role role) {
		this.dispatcher = dispatcher;
		this.role = role;
	}
	
	/**
	 * Toma un numero aleatorio y delega al dispatcher el proceso de atencion de la llamada.
	 */
	@Override
	public void run() {
		Long time = RandomUtil.asignRandomTimeToTask(); 
		this.dispatcher.takeCall(role, time);
	}
}
