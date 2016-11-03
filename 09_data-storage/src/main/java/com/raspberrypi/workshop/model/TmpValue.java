package com.raspberrypi.workshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TmpValue {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private Double value;
	
	
	public TmpValue() {
	}
	
	public TmpValue(Double value) {
		super();
		this.value = value;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	

}
