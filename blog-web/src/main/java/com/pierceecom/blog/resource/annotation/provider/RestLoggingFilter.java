package com.pierceecom.blog.resource.annotation.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.pierceecom.blog.resource.annotation.LogRestData;

@LogRestData
@Provider
public class RestLoggingFilter extends AbstractFilter implements ContainerRequestFilter, ContainerResponseFilter {


	private static final Logger LOGGER = Logger.getLogger(RestLoggingFilter.class.getName());

	@Context
	private HttpServletRequest request;
	

	private static final String REQUEST_START_TIME_PARAM_NAME = "REQUEST_RECEIVED_AT";


	@Override
	public void filter(ContainerRequestContext requestContext) {
		try {
			LOGGER.finest(() -> "HTTP Request - Method: " + requestContext.getMethod() + " , Path: " + requestContext.getUriInfo().getPath());
			LOGGER.finest(() -> "HTTP Request - Header: " + requestContext.getHeaders());
			LOGGER.finest(() -> "HTTP Request - Entity: " + getEntityBody(requestContext));
			requestContext.setProperty(REQUEST_START_TIME_PARAM_NAME, System.currentTimeMillis());
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}


	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
		try {
	        LOGGER.finest(() -> "HTTP Response - Header: " + responseContext.getHeaders());
	        LOGGER.finest(() -> "HTTP Response - Status: " + responseContext.getStatus() + " , Entity: " + responseContext.getEntity());
	        Long requestStartTime = (Long) requestContext.getProperty(REQUEST_START_TIME_PARAM_NAME);
	        if (requestStartTime != null) {
	        	LOGGER.fine(() -> "HTTP Response in: " + (System.currentTimeMillis() - requestStartTime) 
	        			+ " [ms], Method: " + requestContext.getMethod() + ", Path: " + requestContext.getUriInfo().getPath());
	        }
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}

}
