package com.almundo.callcenter.domain;

public enum Role {
	
	EMPLOYEE(1),
	SUPERVISOR(2),
	DIRECTOR(3),
	ON_HOLD(4);
	
	public Integer priority;
	
	private Role(Integer priority) {
		this.priority = priority;
	}
}
