package com.almundo.callcenter.domain;

public class Call {
	private Integer id;
	private Role inCharge;
	
	public Call(Integer idCall) {
		this.id = idCall;
	}
	
	public void setInCharge(Role inCharge) {
		this.inCharge = inCharge;
	}
	
	public Integer getId() {
		return id;
	}
}
