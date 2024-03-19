package com.company.search.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TruProxyOfficersApiResponse {
	@JsonProperty("etag")
    private String etag;
	@JsonProperty("inactive_count")
    private int inactiveCount;
	@JsonProperty("links")
    private Links links;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("items_per_page")
    private int itemsPerPage;
    @JsonProperty("items")
    private List<Officer> items;
    @JsonProperty("active_count")
    private int activeCount;
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("resigned_count")
    private int resignedCount;
}