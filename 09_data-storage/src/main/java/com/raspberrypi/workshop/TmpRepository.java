package com.raspberrypi.workshop;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raspberrypi.workshop.model.TmpValue;

public interface TmpRepository extends CrudRepository<TmpValue, Long>{

	List<TmpValue> findById(int age);
	
}
