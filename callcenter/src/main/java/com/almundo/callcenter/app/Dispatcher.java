package com.almundo.callcenter.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.callcenter.domain.Call;
import com.almundo.callcenter.domain.Role;

public class Dispatcher {
	
	private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);

	public void getByRole(Role role, Long time) {
		switch (role) {
		case EMPLOYEE:
			if (!QueueManager.MAP_QUEUE.get(Role.EMPLOYEE).isEmpty()) {
				Call call = QueueManager.MAP_QUEUE.get(Role.EMPLOYEE).poll();
				LOG.info("Consumer " + role + " tomando la tarea: " + call.getId() + " - Tiempo: " + time+ " - Pendientes: " + QueueManager.MAP_QUEUE.get(Role.EMPLOYEE).size());
				waitCall(time);
			}
			break;
		case SUPERVISOR:
			if (!QueueManager.MAP_QUEUE.get(Role.SUPERVISOR).isEmpty()) {
				Call call = QueueManager.MAP_QUEUE.get(Role.SUPERVISOR).poll();
				LOG.info("Consumer " + role + " tomando la tarea: " + call.getId() + " - Tiempo: " + time+ " - Pendientes: " + QueueManager.MAP_QUEUE.get(Role.SUPERVISOR).size());
				waitCall(time);
			}
			break;
		case DIRECTOR:
			if (!QueueManager.MAP_QUEUE.get(Role.DIRECTOR).isEmpty()) {
				Call call = QueueManager.MAP_QUEUE.get(Role.DIRECTOR).poll();
				LOG.info("Consumer " + role + " tomando la tarea: " + call.getId() + " - Tiempo: " + time+ " - Pendientes: " + QueueManager.MAP_QUEUE.get(Role.DIRECTOR).size());
				waitCall(time);
			}
			break;
		default:
			Call call = QueueManager.MAP_QUEUE.get(Role.ON_HOLD).poll();
			LOG.info("Siguiente llamada en cola ON_HOLD con id {} volviendo a ser encolada",call.getId());
			this.dispatchCall(call);
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
