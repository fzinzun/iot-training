package com.raspberrypi.workshop.sensor;

import java.beans.PropertyChangeListener;

public interface Sensor {
	
	public void addListener(PropertyChangeListener sensorListener);
}
