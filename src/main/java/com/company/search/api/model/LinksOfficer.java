package com.company.search.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LinksOfficer {

    @JsonProperty("appointments")
    private String appointments;


}