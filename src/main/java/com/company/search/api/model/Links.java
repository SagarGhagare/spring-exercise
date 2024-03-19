package com.company.search.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Links {

    @JsonProperty("self")
    private String self;
    @JsonProperty("officer")
    private LinksOfficer officer;

}