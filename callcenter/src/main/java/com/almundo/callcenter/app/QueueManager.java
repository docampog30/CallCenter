package com.almundo.callcenter.app;

import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.callcenter.domain.Call;
import com.almundo.callcenter.domain.Role;
import com.almundo.callcenter.task.ConsumerCall;
import com.almundo.callcenter.util.Constants;

public class QueueManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(QueueManager.class);
	
	private static Queue<Call> commonQueue = new ConcurrentLinkedQueue<>();
	
	private static Queue<Call> employeeQueue = new LinkedBlockingQueue<>(Constants.EMPLOYEE_NUMBER);
	private static Queue<Call> directorQueue  = new LinkedBlockingQueue<>(Constants.DIRECTOR_NUMBER);
	private static Queue<Call> supervisorQueue  = new LinkedBlockingQueue<>(Constants.SUPERVISOR_NUMBER);
	
	public static final ConcurrentSkipListMap<Role,Queue<Call>> MAP_QUEUE = new ConcurrentSkipListMap<>();
	
	static {
		MAP_QUEUE.put(Role.EMPLOYEE, employeeQueue);
		MAP_QUEUE.put(Role.DIRECTOR, directorQueue);
		MAP_QUEUE.put(Role.SUPERVISOR, supervisorQueue);
		MAP_QUEUE.put(Role.ON_HOLD, commonQueue);
	}
	
	/**
	 * Metodo que asigna la llamada a la cola disponible segun las reglas de negocio e invoca otro hilo para que consuma el mensaje. 
	 * @param call
	 */

	public static void addTaskToQueue(Call call) {
		
		LOG.info("Asignando llamada con id : {}",call.getId());
		
		for (Entry<Role, Queue<Call>> entryQueue : MAP_QUEUE.entrySet()) {
			Role key = entryQueue.getKey();
		    Queue<Call> value = entryQueue.getValue();
		    try {
		    	call.setInCharge(key);
		    	value.add(call);
		    	LOG.info("Llamada con id : {} asignada a  {}  ",call.getId(),key.name());
		    	assignConsumer(key);
		    	break;
			} catch (IllegalStateException ex) {
				call.setInCharge(null);
				LOG.info("Todos los  {} ocupados para atender la llamada {} ",key.name(),call.getId());
			} 
		}
	}

	private static void assignConsumer(Role role){
		final ExecutorService exService = Executors.newSingleThreadExecutor();
		exService.submit(new ConsumerCall(new Dispatcher(),role));
		exService.shutdown();
		try {
			exService.awaitTermination(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOG.error("Error lanzando hilo consumidor ",e);
		}
		
	}
}
