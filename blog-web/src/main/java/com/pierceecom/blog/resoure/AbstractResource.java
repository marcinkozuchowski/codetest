package com.pierceecom.blog.resoure;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Some abstract stuff goes hire.
 * 
 * @author marcin.kozuchowski
 *
 */
public abstract class AbstractResource {

	@Context
	protected HttpServletRequest request;

	@Context
	protected HttpServletResponse response;

	@Context
	protected UriInfo uriInfo;
	
	
}
