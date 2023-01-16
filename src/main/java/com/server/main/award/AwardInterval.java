package com.server.main.award;

public class AwardInterval {
	Award min;
	Award max;
	
	public AwardInterval() {
		
	}
	
	public AwardInterval(Award min, Award max) {
		super();
		this.min = min;
		this.max = max;
	}
	public Award getMin() {
		return min;
	}
	public Award getMax() {
		return max;
	}
}
