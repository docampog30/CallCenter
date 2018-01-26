package com.almundo.callcenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.callcenter.app.Dispatcher;
import com.almundo.callcenter.task.ProducerCall;



/**
 * @author David
 *
 */
public class App 
{
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	
    public static void main( String[] args ) throws InterruptedException
    {
    	LOG.info("Inicio Main");

		Dispatcher dispatcher = new Dispatcher();
		
		ExecutorService executor = Executors.newFixedThreadPool(16);
				
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
		executor.execute(new ProducerCall(dispatcher, 11));
		executor.execute(new ProducerCall(dispatcher, 12));
		executor.execute(new ProducerCall(dispatcher, 13));
		executor.execute(new ProducerCall(dispatcher, 14));
		executor.execute(new ProducerCall(dispatcher, 15));
		executor.execute(new ProducerCall(dispatcher, 16));
		
		executor.shutdown();
		executor.awaitTermination(120, TimeUnit.SECONDS);
    }
}
