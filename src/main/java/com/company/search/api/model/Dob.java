package com.company.search.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Dob {

	@JsonProperty("month")
	private int month;
	@JsonProperty("year")
	private int year;

}