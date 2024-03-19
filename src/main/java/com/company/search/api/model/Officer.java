package com.company.search.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Officer {
	
	@JsonProperty("address")
    private Address address;
	
    @JsonProperty("name")
    private String name;
    @JsonProperty("appointed_on")
    private String appointedOn;
    @JsonProperty("officer_role")
    private String officerRole;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("country_of_residence")
    private String countryOfResidence;
    
    @JsonProperty("links")
    private Links links;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("resigned_on")
    private String resignedOn;
    
    @JsonProperty("date_of_birth")
    private Dob dateOfBirth;

}