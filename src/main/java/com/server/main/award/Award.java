package com.server.main.award;

import java.util.Objects;

public class Award implements Comparable<Award> {
	String producer;
	Integer interval;
	Integer previousWin;
	Integer followingWin;

	public Award(String producer, Integer interval, Integer previousWin, Integer followingWin) {
		super();
		this.producer = producer;
		this.interval = interval;
		this.previousWin = previousWin;
		this.followingWin = followingWin;
	}

	public String getProducer() {
		return producer;
	}
	public Integer getInterval() {
		return interval;
	}
	public Integer getPreviousWin() {
		return previousWin;
	}
	public Integer getFollowingWin() {
		return followingWin;
	}

	@Override
	public int compareTo(Award o) {
			if( this.getInterval().intValue() == o.getInterval().intValue()) {
				return 0;
			}
			if(this.getInterval().intValue() < o.getInterval().intValue()) {
				return -1;
			}
		return 1;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		Award award = (Award) object;

		return Objects.equals(producer, award.producer) 
				&& Objects.equals(interval, award.interval) 
				&& Objects.equals(previousWin, award.previousWin) 
				&& Objects.equals(followingWin, award.followingWin);

	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + interval;
		hash = 31 * hash + (producer == null ? 0 : producer.hashCode());
		hash = 31 * hash + (previousWin == null ? 0 : previousWin.hashCode());
		hash = 31 * hash + (followingWin == null ? 0 : followingWin.hashCode());
		return hash;
	}

}
