package com.pierceecom.blog.resoure;

import java.util.Arrays;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.piercevom.blog.api.dto.Post;

@Path("/posts")
public class PostResourceImpl implements PostResource {

	@Override
	public Response getAllPosts() {
		return Response
				.ok(Arrays.asList(new Post(1, "title", "content")))
				.build();
	}

}
