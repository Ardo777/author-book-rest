package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.CountryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountriesEndpoint {
    private final String X_RAPIDAPI_KEY = "X-RapidAPI-Key";
    private final String X_RAPIDAPI_HOST = "X-RapidAPI-Host";
    private final String COUNTRIES_URL = "https://geography4.p.rapidapi.com/apis/geography/v1/country";

    @Value("${rapid.api.key}")
    private String rapidApiKey;

    @Value("${rapid.api.host}")
    private String rapidApiHost;

    private final RestTemplate restTemplate;


    @GetMapping
    public ResponseEntity<List<CountryInfo>> getAllCountries() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(X_RAPIDAPI_KEY, rapidApiKey);
        httpHeaders.add(X_RAPIDAPI_HOST, rapidApiHost);

        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);

        ResponseEntity<CountryInfo[]> exchange = restTemplate.exchange(COUNTRIES_URL, HttpMethod.GET, httpEntity, CountryInfo[].class);

        List<CountryInfo> result = List.of(exchange.getBody());
        return ResponseEntity.ok(result);
    }


}
