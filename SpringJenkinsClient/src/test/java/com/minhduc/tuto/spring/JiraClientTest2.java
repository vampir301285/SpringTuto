package com.minhduc.tuto.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * https://confluence.atlassian.com/jira/connecting-to-ssl-services-117455.html we have to import the ca of Jira Server
 * into keystore.
 * 
 * @author Minh Duc Ngo
 *
 */
public class JiraClientTest2 {
    public static void main(String[] args) {
        try {
            /**
             * follow the link https://confluence.atlassian.com/jira/connecting-to-ssl-services-117455.html we have to import the ca of Jira Server
             * 
             * to import certificate into keystore.
             */
            URL url = new URL("https://jira_server/rest/auth/1/session");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"username\":\"username\",\"password\":\"password\"}";
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK && conn.getResponseCode() != HttpsURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            String body = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                body += output;

            }
            System.out.println(body);
            LoginSessionResponse obj = mapper.readValue(body, LoginSessionResponse.class);
            System.out.println(obj.getSession().get("name") + " - " + obj.getSession().get("value"));

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
