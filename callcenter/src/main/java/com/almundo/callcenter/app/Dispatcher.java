package com.almundo.callcenter.app;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.callcenter.domain.Call;
import com.almundo.callcenter.domain.Role;

public class Dispatcher {
	
	private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);
	public static AtomicInteger callsNumber = new AtomicInteger(0);

	/**
	 * Este metodo permite decidir por que procedimiento seguir segun el parametro role y si es de la cola ON_HOLD se vuelve a delegar para que sea asignado nuevamente
	 * @param role
	 * @param time
	 */
	public void takeCall(Role role, Long time) {
		
		if(Role.ON_HOLD == role) {
			Call call = QueueManager.MAP_QUEUE.get(Role.ON_HOLD).poll();
			LOG.info("Siguiente llamada en cola ON_HOLD con id {} volviendo a ser encolada",call.getId());
			this.dispatchCall(call);
		}else {
			callsNumber.incrementAndGet();
			processCall(role, time);
		}
	}


	/**
	 * Toma el mensaje de la cola y simula el tiempo de atencion de llamada
	 * @param role
	 * @param time
	 */
	private void processCall(Role role, Long time) {
		if (!QueueManager.MAP_QUEUE.get(role).isEmpty()) {
			Call call = QueueManager.MAP_QUEUE.get(role).poll();
			LOG.info("Consumer " + role + " tomando la tarea: " + call.getId() + " - Tiempo: " + time+ " - Pendientes: " + QueueManager.MAP_QUEUE.get(role).size());
			waitCall(time);
		}
	}


	private void waitCall(Long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			LOG.error(ex.getMessage());
		}
	}
	
	public void dispatchCall(Call call){
		QueueManager.addTaskToQueue(call);
	}


}
