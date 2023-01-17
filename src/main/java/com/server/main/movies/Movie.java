package com.server.main.movies;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Movie {

	@Id
	private Integer id;
	@NotNull
	@Min(4)
	@Column
	private Integer releaseyear;
	@NotNull
	@Size(min = 5, max = 50)
	@Column
	private String title;
	@NotNull
	@Size(min = 5, max = 50)
	@Column
	private String studios;
	@NotNull
	@Size(min = 5, max = 50)
	@Column
	private String producers;
	@NotNull
	@Column
	private Boolean winner;

	protected Movie() {		
	}

	@Override
	public String toString() {
		return "Movies [year=" + releaseyear + ", title=" + title + ", studios=" + studios + ", producers=" + producers
				+ ", winner=" + winner + "]";
	}

	public Movie(Integer id, Integer releaseyear, String title, String studios, String producers, Boolean winner) {
		super();
		this.id = id;
		this.releaseyear = releaseyear;
		this.title = title;
		this.studios = studios;
		this.producers = producers;
		this.winner = winner;
	}

	public Integer getReleaseyear() {
		return releaseyear;
	}
	public void setReleaseyear(Integer releaseyear) {
		this.releaseyear = releaseyear;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStudios() {
		return studios;
	}
	public void setStudios(String studios) {
		this.studios = studios;
	}
	public String getProducers() {
		return producers;
	}
	public void setProducers(String producers) {
		this.producers = producers;
	}
	public Boolean getWinner() {
		return winner;
	}
	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
