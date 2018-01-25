package com.almundo.callcenter.util;

import java.util.Random;

public class RandomUtil {

	public static int TIME_MIN = 5000;
	public static int TIME_MAX = 10000;
	
	public static Long asignRandomTimeToTask(){
		Random randomGenerator = new Random();
		int value = randomGenerator.nextInt(TIME_MAX - TIME_MIN + 1) + TIME_MIN;
		
		return new Long(value);
	}

}
