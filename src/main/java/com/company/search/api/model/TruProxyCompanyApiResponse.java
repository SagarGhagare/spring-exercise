package com.company.search.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TruProxyCompanyApiResponse {
    @JsonProperty("page_number")
    private Boolean pageNumber;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("items")
    private List<Company> items;
}