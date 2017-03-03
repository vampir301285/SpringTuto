package com.minhduc.tuto.spring;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * https://confluence.atlassian.com/jira/connecting-to-ssl-services-117455.html we have to import the ca of Jira Server
 * into keystore.
 * 
 * or using NullHostnameVerifier as in Rest Client
 * 
 * @author UE1PHOT
 *
 */
public class JiraLoginTest {

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0");
        headers.add("Accept-Language", "en-US,en;q=0.5");
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        return headers;
    }

    private static void login() throws JsonParseException, JsonMappingException, IOException {
        /**
         * follow the link https://confluence.atlassian.com/jira/connecting-to-ssl-services-117455.html we have to
         * import the ca of Jira Server
         * 
         * to import certificate into keystore.
         */
        RestTemplate restTemp = new RestTemplate();
        // RestTemplate restTemp = new RestTemplate();
        // Create the request body as a MultiValueMap
        String username = "username";
        String password = "password";
        LinkedHashMap<String, String> body = new LinkedHashMap<String, String>();
        body.put("username", username);
        body.put("password", password);
        HttpEntity<Object> request = new HttpEntity<Object>(body, getHeaders());
        String url = "https://jira_verser/rest/auth/1/session";
        // ResponseEntity<JiraLoginResponse> response = restTemp.exchange(url, HttpMethod.POST, request, JiraLoginResponse.class);
        ResponseEntity<String> response = restTemp.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println(response.getBody());
        
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = response.getBody();
        LoginSessionResponse obj = mapper.readValue(responseBody, LoginSessionResponse.class);
        System.out.println(obj.getSession().get("name") + " - " + obj.getSession().get("value"));
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        JiraLoginTest.login();
    }

}
