package com.company.search.api.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.company.search.api.model.Company;
import com.company.search.api.model.CompanySearchOfficer;
import com.company.search.api.model.CompanySearchResponse;
import com.company.search.api.model.Officer;
import com.company.search.api.model.TruProxyCompanyApiResponse;
import com.company.search.api.model.TruProxyOfficersApiResponse;
import com.company.search.api.util.HttpRequest;
import com.company.search.api.util.HttpRequest.HttpRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CompanyService {

	private static final Logger logger = LogManager.getLogger(CompanyService.class);
	private static final String COMPANIES_API_URL = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query=company";
	private static final String OFFICERS_API_URL = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=";
	private static final String X_API_KEY = "PwewCEztSW7XlaAKqkg4IaOsPelGynw6SN9WsbNf";

	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public CompanySearchResponse getComapanies(String companyNumber)
			throws JsonMappingException, JsonProcessingException, HttpRequestException {
		HttpRequest request = HttpRequest.get(COMPANIES_API_URL).header("x-api-key", X_API_KEY).connectTimeout(12000);
		TruProxyCompanyApiResponse apiResponse = MAPPER.readValue(request.body(), TruProxyCompanyApiResponse.class);
		List<Company> items = apiResponse.getItems();
		CompanySearchResponse response = new CompanySearchResponse();
		for (Company company : items) {
			if (company.getCompanyNumber().equals(companyNumber) && company.getCompanyStatus().equals("active")) {
				response.setAddress(company.getAddress());
				response.setCompanyNumber(company.getCompanyNumber());
				response.setCompanyStatus(company.getCompanyStatus());
				response.setCompanyType(company.getCompanyType());
				response.setDateOfCreation(company.getDateOfCreation());
				response.setTitle(company.getTitle());
			}
		}
		return response;
	}

	public List<CompanySearchOfficer> getOfficers(String companyNumber)
			throws JsonMappingException, JsonProcessingException, HttpRequestException {
		HttpRequest request = HttpRequest.get(OFFICERS_API_URL + companyNumber).header("x-api-key", X_API_KEY)
				.connectTimeout(12000);
		
		TruProxyOfficersApiResponse response = MAPPER.readValue(request.body(), TruProxyOfficersApiResponse.class);
		List<Officer> items = response.getItems();
		List<CompanySearchOfficer> officers = new ArrayList<CompanySearchOfficer>();
		for (Officer officer : items) {
			if (officer.getResignedOn() != null) {
				CompanySearchOfficer companySearchOfficer = new CompanySearchOfficer();
				companySearchOfficer.setAddress(officer.getAddress());
				companySearchOfficer.setAppointedOn(officer.getAppointedOn());
				companySearchOfficer.setName(officer.getName());
				companySearchOfficer.setOfficerRole(officer.getOfficerRole());
				officers.add(companySearchOfficer);
			}
		}
		return officers;
	}
}