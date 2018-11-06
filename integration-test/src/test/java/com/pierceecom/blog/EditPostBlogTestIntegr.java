package com.pierceecom.blog;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import com.piercevom.blog.api.dto.PostDto;

import io.restassured.response.Response;

/**
 * Tests various cases of post edit.
 * 
 * @author marcin.kozuchowski
 *
 */
public class EditPostBlogTestIntegr extends AbstractBlogTest {

	private PostDto post;

	@Before
	public void prepare() {
		post = new PostDto(null, "It was awesom saturday", "Some incredible story....");
		Response response = sendAddPost(post, javax.ws.rs.core.Response.Status.CREATED);
		post = getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK);
	}

	/**
	 * Edit the post with new (correct) title.
	 */
	@Test
	public void update_title() {
		String newTitle = "It was awesome saturday NIGHT!";
		post.setTitle(newTitle);

		Response response = sendEdit(post, javax.ws.rs.core.Response.Status.CREATED);
		getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK);
	}

	/**
	 * Edit the post with new (correct) content.
	 */
	@Test
	public void update_content() {
		String newContent = "Some terible story...";
		post.setContent(newContent);

		Response response = sendEdit(post, javax.ws.rs.core.Response.Status.CREATED);
		getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK);
	}

	/**
	 * Edit the post with new (correct) content and title.
	 */
	@Test
	public void update_post() {
		String newTitle = "Melisandre has told";
		String newContent = "The night is dark and full of terror";

		post.setTitle(newTitle);
		post.setContent(newContent);

		Response response = sendEdit(post, javax.ws.rs.core.Response.Status.CREATED);
		getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK);
	}

	/**
	 * Edit the post with empty title.
	 */
	@Test
	public void update_title_empty() {
		post.setTitle("");

		sendEdit(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}

	/**
	 * Edit the post with NULL title.
	 */
	@Test
	public void update_title_null() {
		post.setTitle(null);

		sendEdit(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}

	/**
	 * Edit the post with too long title.
	 */
	@Test
	public void update_title_too_long() {
		post.setTitle(RandomStringUtils.randomAlphabetic(300));

		sendEdit(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}

	/**
	 * Edit the post with empty content.
	 */
	@Test
	public void update_content_empty() {
		post.setContent("");

		sendEdit(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}

	/**
	 * Edit the post with NULL content.
	 */
	@Test
	public void update_content_null() {
		post.setContent(null);

		sendEdit(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Send to edit post without id.
	 */
	@Test
	public void send_to_edit_post_without_id() {
		post.setId(null);

		sendEdit(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Send to edit post with invliad id.
	 */
	@Test
	public void send_to_edit_post_with_invalid_id() {
		post.setId("qweqwe");

		sendEdit(post, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Edit the post without id.
	 */
	@Test
	public void send_to_edit_empty_post() {
		sendEdit(null, javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED);
	}

	/**
	 * Edit the post with very long content.
	 */
	@Test
	public void update_content_very_long() {
		post.setContent(RandomStringUtils.randomAlphabetic(10000));

		Response response = sendEdit(post, javax.ws.rs.core.Response.Status.CREATED);
		getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK);
	}
	
}
