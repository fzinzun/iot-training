package com.raspberrypi.workshop.dto;

public class EncoderDto {
	
	private String sensorId;
	
	private double value;

	private boolean push;
	
	public EncoderDto() {
		// TODO Auto-generated constructor stub
	}


	public EncoderDto(String sensorId, double value, boolean push) {
		super();
		this.sensorId = sensorId;
		this.value = value;
		this.push = push;
	}



	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isPush() {
		return push;
	}

	public void setPush(boolean push) {
		this.push = push;
	}
	
	
}
