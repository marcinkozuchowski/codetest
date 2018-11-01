package com.pierceecom.blog.resoure;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pierceecom.blog.helper.EntityToDtoConverter;
import com.piercescom.blog.entitie.service.PostService;
import com.piercevom.blog.api.dto.PostDto;


@Path("/posts")
@Produces({ MediaType.APPLICATION_JSON })
public class PostResourceImpl {

	@EJB
	private PostService postService;
	
	@GET
	public Response getAllPosts() {
		List<PostDto> posts = EntityToDtoConverter.convert(
				postService.findAll()
				);
		
		return Response.ok(posts).build();
	}

//	@Override
//	public Response publishPost(PostDto post) {
//		Post postEntity = new Post();
//		postEntity.setContent(post.getContent());
//		postEntity.setTitle(post.getTitle());
//		
//		postService.save(postEntity);
//		
//		post.setId(postEntity.getId().toString());
//		post.setContent(postEntity.getContent());
//		post.setTitle(postEntity.getTitle());
//		
//		
//		return Response.ok(post).build();
//	}

}
