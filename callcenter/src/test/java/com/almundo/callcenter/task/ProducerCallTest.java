package com.almundo.callcenter.task;

import org.junit.Test;
import org.mockito.Mockito;

import com.almundo.callcenter.app.Dispatcher;
import com.almundo.callcenter.domain.Call;

public class ProducerCallTest {

	@Test
	public void debeDelegarTareaAlDispatcher(){
		//Arrange
		Dispatcher dispatcher =  Mockito.spy(new Dispatcher());
		ProducerCall producerCall = new ProducerCall(dispatcher, 1);
		//Action
		producerCall.run();
		//Assert
		Mockito.verify(dispatcher).dispatchCall(Mockito.any(Call.class));
	}
}
