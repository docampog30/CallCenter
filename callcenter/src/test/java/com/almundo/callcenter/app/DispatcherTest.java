package com.almundo.callcenter.app;

import org.junit.Assert;
import org.junit.Test;

import com.almundo.callcenter.domain.Call;
import com.almundo.callcenter.domain.Role;

public class DispatcherTest {
	
	private Dispatcher dispatcher = new Dispatcher();
	
	@Test
	public void debeEnviarMensajeAConsumerYVerificarCorrectaAsignación(){
		
		Assert.assertTrue(Dispatcher.callsNumber.get() == 0);
		dispatcher.dispatchCall(new Call(1));
		Assert.assertTrue(Dispatcher.callsNumber.get() == 1);
	}
	
	@Test
	public void debeVerificarCorrectoConsumoEnLaCola(){
		QueueManager.MAP_QUEUE.get(Role.EMPLOYEE).add(new Call(1));
		Assert.assertTrue(QueueManager.MAP_QUEUE.get(Role.EMPLOYEE).size() == 1);
		dispatcher.takeCall(Role.EMPLOYEE, 1l);
		Assert.assertTrue(QueueManager.MAP_QUEUE.get(Role.EMPLOYEE).size() == 0);
	}	
}
