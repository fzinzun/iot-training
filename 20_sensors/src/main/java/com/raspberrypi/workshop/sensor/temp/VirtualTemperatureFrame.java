package com.raspberrypi.workshop.sensor.temp;

import java.beans.PropertyChangeSupport;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VirtualTemperatureFrame extends JFrame {
	private JLabel lblValue;
	private JLabel label;
	private JSlider slider;
	
	//private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private double value = 0.0;
	public VirtualTemperatureFrame() {
		setTitle("Temperature");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450,113);
		
		lblValue = new JLabel("Value: ");
		
		label = new JLabel("0.0");
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateValue();
			}
		});
		slider.setMinorTickSpacing(1);
		slider.setMinimum(-100);
		slider.setValue(0);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(slider, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblValue)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblValue)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(slider, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
	protected void updateValue() {
		value = slider.getValue();
		label.setText(String.valueOf(value));
		this.firePropertyChange("value", 0.0, value);
	}

}
