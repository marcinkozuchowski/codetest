package com.pierceecom.blog.resoure;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;

public abstract class AbstractResource {

	@Context
	protected HttpServletRequest request;

	@Context
	protected HttpServletResponse response;

	@Context
	protected UriInfo uriInfo;
	
	
	protected abstract Logger getLogger();
	
}
