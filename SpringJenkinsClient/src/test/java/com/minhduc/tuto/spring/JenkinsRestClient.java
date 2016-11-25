package com.minhduc.tuto.spring;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class JenkinsRestClient {
    public static final String JENKINS_BASE_URL = "https://jenkinsserver.com";
    
    public static final String REST_API_JOB_URI = JENKINS_BASE_URL + "/job/jobid/api/json";

    public static final String REST_API_BUILDS__URI = JENKINS_BASE_URL + "/job/jobid/api/json?tree=builds[id,number,result,url]";

    public static final String LAST_SUCESSFUL_BUILD_URI = JENKINS_BASE_URL + "/job/jobid/lastSuccessfulBuild/api/json";

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private HttpHeaders getHeaders() {
        String plainCredentials = "username:password";
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
    private void fetchJob() {
        System.out.println("Fetching job ------------");
        NullHostnameVerifier verifier = new NullHostnameVerifier();
        MySimpleClientHttpRequestFactory requestFactory = new MySimpleClientHttpRequestFactory(verifier, "");
        RestTemplate restTemp = new RestTemplate(requestFactory);
        try {
            HttpEntity<String> request = new HttpEntity<String>(getHeaders());
            ResponseEntity<JenkinsJob> response = restTemp.exchange(REST_API_JOB_URI, HttpMethod.GET, request, JenkinsJob.class);
            JenkinsJob job = response.getBody();
            System.out.println("Name: " + job.getName());
            System.out.println("Url: " + job.getUrl());
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    /**
     * GET
     * 
     * to fetch all users
     */
    private void listBuilds() {
        System.out.println("Testing listall users API ------------");
        NullHostnameVerifier verifier = new NullHostnameVerifier();
        MySimpleClientHttpRequestFactory requestFactory = new MySimpleClientHttpRequestFactory(verifier, "");
        RestTemplate restTemp = new RestTemplate(requestFactory);
        try {
            HttpEntity<String> request = new HttpEntity<String>(getHeaders());
            ResponseEntity<JenkinsJob> response = restTemp.exchange(REST_API_BUILDS__URI, HttpMethod.GET, request, JenkinsJob.class);
            JenkinsJob job = response.getBody();
            System.out.println("Name: " + job.getName());
            System.out.println("Url: " + job.getUrl());
            if (job.getBuilds() != null) {
                for (JenkinsBuild build : job.getBuilds()) {
                    System.out.println(build.toString());
                }

            }
        } catch (HttpStatusCodeException e) {
            System.err.println(e.getStatusCode() + " -- " + e.getStatusText());
        }
    }

    public static void main(String[] args) {
        JenkinsRestClient client = new JenkinsRestClient();
        client.fetchJob();
        client.listBuilds();
        
    }

    public class MySimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

        private final HostnameVerifier verifier;
        private final String cookie;

        public MySimpleClientHttpRequestFactory(HostnameVerifier verifier, String cookie) {
            this.verifier = verifier;
            this.cookie = cookie;
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection).setHostnameVerifier(verifier);
                ((HttpsURLConnection) connection).setSSLSocketFactory(trustSelfSignedSSL().getSocketFactory());
                ((HttpsURLConnection) connection).setAllowUserInteraction(true);
                String rememberMeCookie = cookie == null ? "" : cookie;
                ((HttpsURLConnection) connection).setRequestProperty("Cookie", rememberMeCookie);
            }
            super.prepareConnection(connection, httpMethod);
        }

        public SSLContext trustSelfSignedSSL() {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {

                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                ctx.init(null, new TrustManager[] { tm }, null);
                SSLContext.setDefault(ctx);
                return ctx;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

    public class NullHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
