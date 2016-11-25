package com.minhduc.tuto.spring.restclient;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.minhduc.tuto.spring.model.User;

public class HelloWorldCRUDRestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/SpringMVCRestCRUD";

    /**
     * GET
     * 
     * to fetch all users
     */
    @SuppressWarnings("unchecked")
    private void listAllUsers() {
        System.out.println("Testing listall users API ------------");
        RestTemplate restTemp = new RestTemplate();
        List<LinkedHashMap<String, Object>> usersMap = restTemp.getForObject(REST_SERVICE_URI + "/user/", List.class);
        if (usersMap != null) {
            for (LinkedHashMap<String, Object> map : usersMap) {
                System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age=" + map.get("age") + ", Salary="
                        + map.get("salary"));
            }
        } else {
            System.out.println("No user exist----------");
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
        User user = null;
        try {
            user = restTemp.getForObject(REST_SERVICE_URI + "/user/" + id, User.class);
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
        try {
            URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user/", user, User.class);
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
        try {
            restTemplate.put(REST_SERVICE_URI + "/user/1", user);
            System.out.println(user);
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    /* DELETE */
    private void deleteUser(long id) {
        System.out.println("Testing delete User API---------- id = " + id);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(REST_SERVICE_URI + "/user/" + id);
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    /* DELETE */
    private void deleteAllUsers() {
        System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/user/");
    }

    public static void main(String[] args) {
        HelloWorldCRUDRestClient restClient = new HelloWorldCRUDRestClient();
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
