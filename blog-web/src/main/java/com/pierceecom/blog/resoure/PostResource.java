package com.pierceecom.blog.resoure;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces({ MediaType.APPLICATION_JSON })
public interface PostResource {

	@GET
	Response getAllPosts();

}
