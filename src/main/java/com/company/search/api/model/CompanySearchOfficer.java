package com.company.search.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CompanySearchOfficer {
	
	@JsonProperty("name")
    private String name;
	@JsonProperty("officer_role")
    private String officerRole;
	@JsonProperty("appointed_on")
    private String appointedOn;
	@JsonProperty("address")
    private Address address;
}