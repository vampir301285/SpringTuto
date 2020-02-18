package com.redcrystal.example.ws.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redcrystal.example.dao.UserDao;
import com.redcrystal.example.entities.User;
import com.redcrystal.example.ws.WebServicePort;
import com.redcrystal.example.ws.domain.WSUser;

@Component
@WebService(serviceName = WebServicePort.SERVICE_NAME, endpointInterface = "com.redcrystal.example.ws.WebServicePort")
public class WebServiceImpl implements WebServicePort {

	/** Logger object */
	private static final Logger LOGGER = Logger.getLogger(WebServicePort.class);

	/** The web service context */
	@Resource
	private WebServiceContext wsContext;

	/**
	 * to authenticate user
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean validateUser() {
		// gets detail from request header
		MessageContext msgContext = wsContext.getMessageContext();
		Map httpHeaders = (Map) msgContext.get(MessageContext.HTTP_REQUEST_HEADERS);
		List userList = (List) httpHeaders.get("Username");
		List passList = (List) httpHeaders.get("Password");
		String username = "";
		String password = "";
		if (userList != null) {
			username = userList.get(0).toString();
		}
		if (passList != null) {
			password = passList.get(0).toString();
		}

		// validate again data base
		LOGGER.debug("checking credentials for the user: " + username);
		User user = userDao.findUserByUsername(username);
		if (user != null && user.getHashedPassword().equals(password)) {
			LOGGER.debug("valid credentials");
			return true;
		}
		LOGGER.error("invalid credentials");
		return false;
	}

	@Autowired
	private UserDao userDao;

	@Override
	public String getHelloWorldAsString() {
		return "Hello world!";
	}

	@Override
	public WSUser getUser(int id) {
		LOGGER.info("getUser with id: " + id);
		WSUser wsuser = new WSUser();
		User user = userDao.find(User.class, id);
		if (user != null) {
			wsuser.setId(id);
			wsuser.setUsername(user.getUsername());
			LOGGER.info(wsuser.toString());
			return wsuser;
		}
		return wsuser;
	}

	/**
	 * This service requires the user authentication.
	 */
	@Override
	public String getEchoAsSecureService(String input) {
		if (!validateUser()) {
			throw new WebServiceException("invalid user name or password");
		}
		return input;
	}
}
