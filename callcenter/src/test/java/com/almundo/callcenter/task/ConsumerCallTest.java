package com.almundo.callcenter.task;

import org.junit.Test;
import org.mockito.Mockito;

import com.almundo.callcenter.app.Dispatcher;
import com.almundo.callcenter.domain.Role;

public class ConsumerCallTest {
	
	@Test
	public void debeDelegarAlConsumidorElProcesoDeLaTarea(){
		
		//Arrange
		Dispatcher dispatcher =  Mockito.spy(new Dispatcher());
		ConsumerCall consumerCall = new ConsumerCall(dispatcher, Role.EMPLOYEE);

		//Action
		consumerCall.run();
		
		//Assert
		Mockito.verify(dispatcher).takeCall(Mockito.any(), Mockito.anyLong());
	}
	
	
}