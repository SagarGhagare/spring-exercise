package com.company.search.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.company.search.api.model.CompanySearchApiResponse;
import com.company.search.api.model.CompanySearchOfficer;
import com.company.search.api.model.CompanySearchRequest;
import com.company.search.api.model.CompanySearchResponse;
import com.company.search.api.service.CompanyService;

@RestController
@RequestMapping(value = "/company")
public class CompanyApi {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CompanyService companyService;

	private static final Logger logger = LogManager.getLogger(CompanyApi.class);

	@GetMapping
	public ResponseEntity<?> getCompany(@RequestBody CompanySearchRequest body,
			@RequestHeader("x-api-key") String key) {
		try {
			String res = null;
			if (body == null) {
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}

			if (body.getCompanyName() == null || body.getCompanyNumber() == null) {
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}

			if (body.getCompanyName().isEmpty() || body.getCompanyNumber().isEmpty()) {
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}

			CompanySearchResponse comapanies = companyService.getComapanies(body.getCompanyNumber());
			List<CompanySearchOfficer> officers = companyService.getOfficers(body.getCompanyNumber());
			CompanySearchApiResponse response = new CompanySearchApiResponse();
			response.setTotalResults(1);
			List<CompanySearchResponse> CompanySearch = new ArrayList<CompanySearchResponse>();
			comapanies.setOfficers(officers);
			CompanySearch.add(comapanies);
			response.setItems(CompanySearch);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
