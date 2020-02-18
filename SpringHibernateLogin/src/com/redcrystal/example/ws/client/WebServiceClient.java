package com.redcrystal.example.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import com.redcrystal.example.util.Utilities;
import com.redcrystal.example.ws.WebServicePort;

public class WebServiceClient {

	/** Logger object */
	private static final Logger LOGGER = Logger.getLogger(WebServiceClient.class);

	/** The end point url. */
	private final String END_PONT_URL = "http://10.216.51.98/SpringHibernateLogin/ws?wsdl";

	/** The target name space */
	private final String TARGET_NAME_SPACE = "http://impl.ws.example.redcrystal.com/";

	/** The web service port (Web service definition). */
	private WebServicePort servicePort;

	/** The constructor */
	private WebServiceClient() {
		try {
			URL url = new URL(END_PONT_URL);
			QName qname = new QName(TARGET_NAME_SPACE, WebServicePort.SERVICE_NAME);
			Service service = Service.create(url, qname);
			servicePort = service.getPort(WebServicePort.class);
		} catch (MalformedURLException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Constructor with user name and password to authenticate against server
	 * 
	 * @param username
	 *            the user name
	 * @param sha256Password
	 *            the sha256 password
	 */
	public WebServiceClient(String username, String sha256Password) {
		this();
		// set user name and password
		Map<String, Object> reqContext = ((BindingProvider) servicePort).getRequestContext();
		reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, END_PONT_URL);
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("Username", Collections.singletonList(username));
		headers.put("Password", Collections.singletonList(sha256Password));
		reqContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	}

	/**
	 * @return the servicePort
	 */
	public WebServicePort getServicePort() {
		return servicePort;
	}

	/**
	 * Testing method
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		WebServiceClient client = new WebServiceClient("mngo", Utilities.sha256("minhduc85"));
		WebServicePort servicePort = client.getServicePort();
		/** secure client */
		System.out.println(servicePort.getHelloWorldAsString());
		System.out.println(servicePort.getUser(10));
		System.out.println(servicePort
				.getEchoAsSecureService("That is an secure web service and requires authentication. I am eligible to use this service."));
	}
}
