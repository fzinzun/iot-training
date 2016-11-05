package com.raspberrypi.workshop.dto;

public class TemperatureDto {
	
	private String sensorId;
	
	private double [] point;
	
	public TemperatureDto() {
	}
	
	public TemperatureDto(String sensorId, double[] point) {
		super();
		this.sensorId = sensorId;
		this.point = point;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public double[] getPoint() {
		return point;
	}

	public void setPoint(double[] point) {
		this.point = point;
	}

}
