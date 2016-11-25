package com.minhduc.tuto.spring.restclient;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.minhduc.tuto.spring.model.User;

public class HelloWorldCRUDRestBaiscAuthenClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/SpringMVCRestAuthentication";

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private HttpHeaders getHeaders() {
        String plainCredentials = "bill:abc123";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * GET
     * 
     * to fetch all users
     */
    @SuppressWarnings("unchecked")
    private void listAllUsers() {
        System.out.println("Testing listall users API ------------");
        RestTemplate restTemp = new RestTemplate();
        try {
            HttpEntity<String> request = new HttpEntity<String>(getHeaders());
            ResponseEntity<List> response = restTemp.exchange(REST_SERVICE_URI + "/user/", HttpMethod.GET, request, List.class);
            List<LinkedHashMap<String, Object>> usersMap = response.getBody();
            if (usersMap != null) {
                for (LinkedHashMap<String, Object> map : usersMap) {
                    System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age=" + map.get("age") + ", Salary="
                            + map.get("salary"));
                }
            } else {
                System.out.println("No user exist----------");
            }
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    /**
     * GET
     * 
     * get single user
     * 
     * @param id
     */
    private void getUser(long id) {
        System.out.println("Testing get user API ------------ id = " + id);
        RestTemplate restTemp = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        User user = null;
        try {
            ResponseEntity<User> response = restTemp.exchange(REST_SERVICE_URI + "/user/" + id, HttpMethod.GET, request, User.class);
            user = response.getBody();
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }

        if (user == null) {
            System.out.println("No user exists.");
        } else {
            System.out.println(user.toString());
        }
    }

    /* POST */
    /**
     * rest client to create new user
     */
    private void createUser() {
        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        
        User user = new User(0, "Minh Duc", 30, 70000);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        try {
            URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user/", request, User.class);
            System.out.println("Location : " + uri.toASCIIString());
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    /* PUT */
    /**
     * rest template to update user
     */
    private void updateUser() {
        System.out.println("Testing update User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(1, "Tomy", 33, 70000);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        try {
            ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI + "/user/1", HttpMethod.PUT, request, User.class);
            System.out.println(response.getBody());
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    /* DELETE */
    private void deleteUser(long id) {
        System.out.println("Testing delete User API---------- id = " + id);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        try {
            restTemplate.exchange(REST_SERVICE_URI + "/user/" + id, HttpMethod.DELETE, request, User.class);
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    /* DELETE */
    private void deleteAllUsers() {
        System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpEntity<String> request = new HttpEntity<String>(getHeaders());
            restTemplate.exchange(REST_SERVICE_URI+"/user/", HttpMethod.DELETE, request, User.class);
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    public static void main(String[] args) {
        HelloWorldCRUDRestBaiscAuthenClient restClient = new HelloWorldCRUDRestBaiscAuthenClient();
        restClient.listAllUsers();
        restClient.getUser(1);
        restClient.getUser(10);
        restClient.createUser();
        restClient.createUser();
        restClient.updateUser();
        restClient.listAllUsers();
        restClient.deleteUser(3);
        restClient.deleteUser(100);
        restClient.listAllUsers();
        restClient.deleteAllUsers();
        restClient.listAllUsers();

    }
}
