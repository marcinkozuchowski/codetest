package com.pierceecom.blog.resoure;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pierceecom.blog.helper.EntityToDtoConverter;
import com.piercescom.blog.entitie.Post;
import com.piercescom.blog.entitie.service.PostService;
import com.piercevom.blog.api.dto.PostDto;


@Stateless
@Path("/posts")
public class PostResource extends AbstractResource {

	private static final Logger logger = LoggerFactory.getLogger(PostResource.class);
	
	
	@EJB
	private PostService postService;

	@Inject
	private Validator validator;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllPosts() {
		List<PostDto> posts = EntityToDtoConverter.convert(postService.findAll());

		GenericEntity<List<PostDto>> entity = new GenericEntity<List<PostDto>>(posts){};
		return Response.ok(entity).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response publishPost(PostDto post) {
		Post postEntity = EntityToDtoConverter.convertDto(post);
		postEntity.setId(null); // ID should be assigned by ORM/DB

		postService.save(postEntity);

		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(postEntity.getId()));

		return Response.created(builder.build()).build();
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePost(PostDto postDto) {
		
		Post post = postService.findById(Long.parseLong(postDto.getId()));

		if (post == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		if (!validatePost(postDto)) {
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		}
		

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		postService.update(post);
		
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(post.getId()));
		
		return Response.created(builder.build()).build();
	}
	
	public boolean validatePost(PostDto post) {
		Set<ConstraintViolation<PostDto>> constraintViolations;
		if ((constraintViolations = validator.validate(post)).isEmpty()) {
			return true;
		} 
		
		constraintViolations.forEach(c -> logger.info("{} {} - {}", c.getPropertyPath(), c.getInvalidValue(), c.getMessage()));

		return false;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("{id}")
	public Response getPost(@PathParam("id") String id) {
		Post post = postService.findById(Long.valueOf(id));

		if (post == null) {
			return Response.noContent().build();
		}

		return Response.ok(EntityToDtoConverter.convertEntity(post)).build();
	}

	@DELETE
	@Path("{id}")
	public Response deletePost(@PathParam("id") String id) {

		Post post = postService.findById(Long.valueOf(id));
		if (post == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		postService.deleteById(post.getId());

		return Response.ok().build();
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
