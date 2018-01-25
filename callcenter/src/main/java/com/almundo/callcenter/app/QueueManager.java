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

	public static void addTaskToQueue(Call call) {
		
		LOG.info("Asignando llamada con id : {}",call.getId());
		
		for (Entry<Role, Queue<Call>> entry : MAP_QUEUE.entrySet()) {
			Role key = entry.getKey();
		    Queue<Call> value = entry.getValue();
		    try {
		    	call.setInCharge(key);
		    	value.add(call);
		    	LOG.info("Llamada con id : {} asignada a  {}  ",call.getId(),key.name());
		    	assignConsumer(key);
		    	break;
		    	
			} catch (IllegalStateException ex) {
				call.setInCharge(null);
				LOG.info("Todos los  {} ocupados para atender la llamada {} ",key.name(),call.getId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void assignConsumer(Role role) throws InterruptedException {
		final ExecutorService exService = Executors.newSingleThreadExecutor();
		exService.submit(new ConsumerCall(new Dispatcher(),role));
		exService.awaitTermination(30, TimeUnit.SECONDS);
		exService.shutdown();
	}
}
