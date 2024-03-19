package com.company.search.api;

import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.company.search.api.controller.CompanyApi;
import com.company.search.api.model.CompanySearchOfficer;
import com.company.search.api.model.CompanySearchRequest;
import com.company.search.api.model.CompanySearchResponse;
import com.company.search.api.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyApi.class)
class CompanySearchApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CompanyService companyService;

	@Mock
	private CompanySearchResponse companies;

	@Mock
	private List<CompanySearchOfficer> officers;
	
	@Test
	public void testCompnayApiReturnsBadRequestWhenNoXApiKeyIsPresentInHeader() throws Exception {
		CompanySearchRequest request = new CompanySearchRequest();
		request.setCompanyName("BBC LIMITED");
		request.setCompanyNumber("03054282");
		String reqBody = new ObjectMapper().writeValueAsString(request);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/company").content(reqBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		Mockito.verify(this.companyService, Mockito.times(0)).getComapanies(Mockito.any(String.class));
		Mockito.verify(this.companyService, Mockito.times(0)).getOfficers(Mockito.any(String.class));
	}
	
	@Test
	public void testCompnayApiReturnsBadRequestWhenNullOrEmptyRequestBody() throws Exception {
		CompanySearchRequest request = new CompanySearchRequest();
		request.setCompanyName(null);
		request.setCompanyNumber("");
		String reqBody = new ObjectMapper().writeValueAsString(request);
		mockMvc.perform(MockMvcRequestBuilders.get("/company").header("x-api-key", "xyz").content(reqBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		Mockito.verify(this.companyService, Mockito.times(0)).getComapanies(Mockito.any(String.class));
		Mockito.verify(this.companyService, Mockito.times(0)).getOfficers(Mockito.any(String.class));
	}
	
	@Test
	public void testCompnayApiReturnsBadRequestWhenNoRequestBody() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/company").header("x-api-key", "xyz"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		Mockito.verify(this.companyService, Mockito.times(0)).getComapanies(Mockito.any(String.class));
		Mockito.verify(this.companyService, Mockito.times(0)).getOfficers(Mockito.any(String.class));
	}

	@Test
	public void testCompnayApiReturnsSuccess() throws Exception {
		CompanySearchRequest request = new CompanySearchRequest();
		request.setCompanyName("BBC LIMITED");
		request.setCompanyNumber("03054282");
		String reqBody = new ObjectMapper().writeValueAsString(request);
		
		Mockito.when(this.companyService.getComapanies(Mockito.any(String.class))).thenReturn(companies);
		Mockito.when(this.companyService.getOfficers(Mockito.any(String.class))).thenReturn(officers);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/company").header("x-api-key", "xyz")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding(StandardCharsets.UTF_8).content(reqBody))
				.andExpect(MockMvcResultMatchers.status().isOk());

		Mockito.verify(this.companyService, Mockito.times(1)).getComapanies(Mockito.any(String.class));
		Mockito.verify(this.companyService, Mockito.times(1)).getOfficers(Mockito.any(String.class));
	}
	
	@Test
	public void testCompnayApiReturnsInternalServerError500() throws Exception {
		CompanySearchRequest request = new CompanySearchRequest();
		request.setCompanyName("BBC LIMITED");
		request.setCompanyNumber("03054282");
		String reqBody = new ObjectMapper().writeValueAsString(request);
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/company").header("x-api-key", "xyz")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding(StandardCharsets.UTF_8).content(reqBody))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError());
	}
}
