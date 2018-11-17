package com.ovarelag.uoc.subastas.gob.scrapper.model.skeleton;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class SkeletonEntity {
	

	@CsvBindByName(column = "MIN_ID")
	@CsvBindByPosition(position = 0)
	protected Integer id;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}
