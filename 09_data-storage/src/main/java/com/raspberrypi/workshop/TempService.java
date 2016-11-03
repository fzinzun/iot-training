package com.raspberrypi.workshop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raspberrypi.workshop.model.TmpValue;

@Component
public class TempService {

	@Autowired
	private TmpRepository tmpRepo;
	
	public void addValue(TmpValue value) {
		tmpRepo.save(value);
	}
	
	public List<TmpValue> getValueList() {
		return (List<TmpValue>) tmpRepo.findAll();
	}
}
