package com.company.search.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanySearchApiResponse {
  
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("items")
    private List<CompanySearchResponse> items;
}