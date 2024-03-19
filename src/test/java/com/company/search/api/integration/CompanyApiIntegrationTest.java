package com.company.search.api.integration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.company.search.api.model.CompanySearchRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class CompanyApiIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private WireMockApi wireMockApi = new WireMockApi();

    @BeforeEach
    void setUp() throws IOException {
        createExternalApiMocks();
    }

    @Test
    void shouldAssertResponseAsBadRequest() throws Exception {
    	CompanySearchRequest request = new CompanySearchRequest();
		request.setCompanyName("BBC LIMITED");
		request.setCompanyNumber("03054282");
		String reqBody = new ObjectMapper().writeValueAsString(request);
        MockHttpServletRequestBuilder requestBuilder = get("/company").contentType(APPLICATION_JSON).content(reqBody);
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }
    
    @Test
    void shouldAssertResponseIsSameAsPerCompanyResponseModel() throws Exception {
    	CompanySearchRequest request = new CompanySearchRequest();
		request.setCompanyName("BBC LIMITED");
		request.setCompanyNumber("03054282");
		String reqBody = new ObjectMapper().writeValueAsString(request);
        MockHttpServletRequestBuilder requestBuilder = get("/company").contentType(APPLICATION_JSON).header("x-api-key", "xyz").content(reqBody);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @AfterEach
    void tearDown() {
        wireMockApi.stop();
    }


    private void createExternalApiMocks() throws IOException {
        wireMockApi.start();
        wireMockApi.stubCompanyApiResponse();
    }

}
