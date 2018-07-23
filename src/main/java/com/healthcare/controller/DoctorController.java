package com.healthcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.util.ApplicationProperties;

@RestController
public class DoctorController {
    
    ApplicationProperties properties;
    
    public DoctorController(ApplicationProperties properties) {
        this.properties = properties;
    }
    
    @GetMapping("/doctors/{uid}")
    public ResponseEntity<?> getDoctor(@PathVariable("uid") String uid) {
        
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(properties.doctorApiUrl+"/doctors/"+uid);
        uriComponentsBuilder.queryParam("user_key", properties.doctorApiKey);
        ResponseEntity<String> response
          = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), String.class);
        
        if (response.getStatusCode()==HttpStatus.OK) {
            
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode data = root.path("data");
                
                return new ResponseEntity(data, response.getStatusCode());
                
            } catch (Exception ex) {
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        return new ResponseEntity<Void>(response.getStatusCode());
        
    }
    
    @GetMapping("/doctors")
    public ResponseEntity<?> searchDoctors(
            @RequestParam(name="query", required=false, defaultValue="") String query,
            @RequestParam(name="location", required=false, defaultValue="") String location,
            @RequestParam(name="user_location", required=false, defaultValue="") String userLocation,
            @RequestParam(name="skip", required=false, defaultValue="0") int skip,
            @RequestParam(name="limit", required=false, defaultValue="5") int limit) {
        
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(properties.doctorApiUrl+"/doctors");
        uriComponentsBuilder.queryParam("query", query);
        uriComponentsBuilder.queryParam("location", location);
        uriComponentsBuilder.queryParam("userLocation", userLocation);
        uriComponentsBuilder.queryParam("skip", skip);
        uriComponentsBuilder.queryParam("limit", limit);
        uriComponentsBuilder.queryParam("user_key", properties.doctorApiKey);
        
        ResponseEntity<String> response
          = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), String.class);
        
        if (response.getStatusCode()==HttpStatus.OK) {
            
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                
                return new ResponseEntity(root, response.getStatusCode());
                
            } catch (Exception ex) {
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        return new ResponseEntity<Void>(response.getStatusCode());
        
    }

}
