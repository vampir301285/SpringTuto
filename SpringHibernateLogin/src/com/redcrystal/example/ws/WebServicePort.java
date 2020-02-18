package com.redcrystal.example.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.redcrystal.example.ws.domain.WSUser;

/**
 * The web service port is the interface to define web service methods.
 * 
 * @author mngo
 * 
 */
@WebService
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public interface WebServicePort {
    /** The service name*/
	public static String SERVICE_NAME = "SPRING-JAX-WS";
	
	@WebMethod
	public String getHelloWorldAsString();

	@WebMethod
	public WSUser getUser(int id);
	
	@WebMethod
	public String getEchoAsSecureService(String input);
}
