package com.pierceecom.blog.resource.annotation.provider;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import com.pierceecom.blog.resource.annotation.EmptyBodyNotAllowed;

/**
 * Filter that checks if request body is empty, if true then error code 405 is return to the client.
 * @author marcin.kozuchowski
 *
 */
@EmptyBodyNotAllowed
@Provider
@Priority(value = Priorities.ENTITY_CODER)
public class EmptyBodyNotAllowedFilter extends AbstractFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if(StringUtils.isBlank(getEntityBody(requestContext))) {
			
			requestContext.abortWith(Response.status(Response.Status.METHOD_NOT_ALLOWED)
		              .entity("Empty body not allowed.")
		              .build());
		}
		
	}

}
