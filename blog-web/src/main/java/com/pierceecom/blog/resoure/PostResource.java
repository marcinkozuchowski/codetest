package com.pierceecom.blog.resoure;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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


import com.pierceecom.blog.helper.EntityToDtoConverter;
import com.pierceecom.blog.resource.annotation.EmptyBodyNotAllowed;
import com.pierceecom.blog.resource.annotation.LogRestData;
import com.piercescom.blog.entitie.Post;
import com.piercescom.blog.entitie.service.PostService;
import com.piercevom.blog.api.dto.PostDto;
import com.piercevom.blog.api.validation.groups.PostAction;
import com.piercevom.blog.api.validation.groups.PutAction;

/**
 * Class providing operations to do on Post through REST Service  
 * @author marcin.kozuchowski
 *
 */
@Stateless
@LogRestData
@Path("/posts")
public class PostResource extends AbstractResource {

	private static final Logger LOGGER = Logger.getLogger(PostResource.class.getName());
	
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
	@EmptyBodyNotAllowed
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response publishPost(PostDto post) {
		if (!validatePost(post, PostAction.class)) {
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		}
		post.setId(null);// ID should be assigned by ORM/DB

		Post postEntity = EntityToDtoConverter.convertDto(post);
		postService.save(postEntity);

		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(postEntity.getId()));

		return Response.created(builder.build()).build();
	}

	@PUT
	@EmptyBodyNotAllowed
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePost(PostDto postDto) {
		if (!validatePost(postDto, PutAction.class)) {
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		}
		
		Post post = postService.findById(Long.parseLong(postDto.getId()));
		if (post == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		postService.update(post);
		
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(post.getId()));
		
		return Response.created(builder.build()).build();
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

	@SuppressWarnings("rawtypes")
	public boolean validatePost(PostDto post, Class ... constraitGroup) {
		Set<ConstraintViolation<PostDto>> constraintViolations;
		if ((constraintViolations = validator.validate(post, constraitGroup)).isEmpty()) {
			return true;
		} 
		
		// TODO: Same error class with validation message could be returned to REST CLIENT but provided RAML does not requires that.
		constraintViolations.forEach(c -> LOGGER.warning(() -> c.getPropertyPath() + " " + c.getInvalidValue() + " - " + c.getMessage()));

		return false;
	}
	
}
