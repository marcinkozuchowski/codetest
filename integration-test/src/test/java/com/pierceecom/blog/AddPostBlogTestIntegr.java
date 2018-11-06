package com.pierceecom.blog;

import org.junit.Test;

import com.piercevom.blog.api.dto.PostDto;

import io.restassured.response.Response;

/**
 * Tests various cases of creating a post.
 * 
 * @author marcin.kozuchowski
 *
 */
public class AddPostBlogTestIntegr extends AbstractBlogTest {

	/**
	 * Edit the post with new (correct) title.
	 */
	@Test
	public void without_content() {
		PostDto post = new PostDto();
		post.setTitle("It was awesome saturday NIGHT!");

		sendAddPost(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}

	/**
	 * Edit the post with new (correct) content.
	 */
	@Test
	public void without_title() {
		PostDto post = new PostDto();
		post.setContent("Some terible story...");

		sendAddPost(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}

	@Test
	public void test_success() {

		PostDto post = new PostDto();
		post.setTitle("Melisandre has told");
		post.setContent("The night is dark and full of terror");

		Response response = sendAddPost(post, javax.ws.rs.core.Response.Status.CREATED);
		getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK);
	}

	/**
	 * Edit the post with empty title.
	 */
	@Test
	public void test_success2() {
		PostDto post = new PostDto();
		post.setId("asdadasd");
		post.setTitle("Melisandre has told");
		post.setContent("The night is dark and full of terror");

		Response response = sendAddPost(post, javax.ws.rs.core.Response.Status.CREATED);
		getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK);
	}

	@Test
	public void empty_post() {
		PostDto post = new PostDto();

		sendAddPost(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}
	
	@Test
	public void null_post() {
		sendAddPost(null, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}
	
}
