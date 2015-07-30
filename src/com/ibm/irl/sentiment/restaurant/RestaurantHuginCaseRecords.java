package com.ibm.irl.sentiment.restaurant;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestaurantHuginCaseRecords {

	private Map<String, RestaurantHuginCaseRecord> recordsMap;

	public RestaurantHuginCaseRecords() {
		recordsMap = new HashMap<String, RestaurantHuginCaseRecord>();
	}

	public void addRecord(RestaurantHuginCaseRecord record) {
		if (recordsMap.get(record.getRestaurantID()) == null)
			recordsMap.put(record.getRestaurantID(), record);
	}

	public RestaurantHuginCaseRecord getRecord(String id) {
		return recordsMap.get(id);
	}

	public Collection<RestaurantHuginCaseRecord> getRecords() {
		return recordsMap.values();
	}
}
