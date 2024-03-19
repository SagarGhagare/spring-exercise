package com.company.search.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Matches {

    @JsonProperty("snippet")
    private String[] snippet;
    @JsonProperty("title")
    private String[] title;
}