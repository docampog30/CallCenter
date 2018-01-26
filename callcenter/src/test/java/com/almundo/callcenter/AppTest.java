package com.almundo.callcenter;

import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.almundo.callcenter.app.Dispatcher;
import com.almundo.callcenter.app.QueueManager;
import com.almundo.callcenter.domain.Call;
import com.almundo.callcenter.domain.Role;
import com.almundo.callcenter.task.ProducerCall;

public class AppTest {
	
	private Dispatcher dispatcher = new Dispatcher();
	
	@Test
	public void debeLanzar10LlamadasConcurrentes() throws InterruptedException{
		
		//Arrange
		
		ExecutorService executor = Executors.newFixedThreadPool(12);
		
		executor.execute(new ProducerCall(dispatcher, 1));
		executor.execute(new ProducerCall(dispatcher, 2));
		executor.execute(new ProducerCall(dispatcher, 3));
		executor.execute(new ProducerCall(dispatcher, 4));
		executor.execute(new ProducerCall(dispatcher, 5));
		executor.execute(new ProducerCall(dispatcher, 6));
		executor.execute(new ProducerCall(dispatcher, 7));
		executor.execute(new ProducerCall(dispatcher, 8));
		executor.execute(new ProducerCall(dispatcher, 9));
		executor.execute(new ProducerCall(dispatcher, 10));
		
		//Action
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
		
		//Assert
		
		//Se verifica que no existan mensajes encolados
		for (Entry<Role, Queue<Call>> entry : QueueManager.MAP_QUEUE.entrySet()) {
		    Queue<Call> value = entry.getValue();
		    Assert.assertTrue(value.size() == 0);
		    
		}
		
	}

}
