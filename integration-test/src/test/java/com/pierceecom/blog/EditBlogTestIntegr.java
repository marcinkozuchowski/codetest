package com.pierceecom.blog;

import static io.restassured.RestAssured.given;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import com.piercevom.blog.api.dto.PostDto;

import io.restassured.response.Response;

/**
 * 
 * @author marcin.kozuchowski
 *
 */
public class EditBlogTestIntegr extends AbstractBlogTest {

	private PostDto post;

	@Before
	public void prepare() {
		post = new PostDto(null, "It was awesom saturday", "Some incredible story....");
		sendAddAndValidateResponse(post);
	}

	/**
	 * Edit the post with new (correct) title.
	 */
	@Test
	public void update_title() {
		String newTitle = "It was awesome saturday NIGHT!";
		post.setTitle(newTitle);

		sendEditAndValidateResponse(post);
	}

	/**
	 * Edit the post with new (correct) content.
	 */
	@Test
	public void update_content() {
		String newContent = "Some terible story...";
		post.setContent(newContent);

		sendEditAndValidateResponse(post);
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

		sendEditAndValidateResponse(post);
	}

	/**
	 * Edit the post with empty title.
	 */
	@Test
	public void update_title_empty() {
		post.setTitle("");

		Response response = given().spec(rspec).body(post).put(TestConstants.POSTS);
		response.then().statusCode(405);
	}

	/**
	 * Edit the post with NULL title.
	 */
	@Test
	public void update_title_null() {
		post.setTitle(null);

		Response response = given().spec(rspec).body(post).put(TestConstants.POSTS);
		response.then().statusCode(405);
	}

	/**
	 * Edit the post with too long title.
	 */
	@Test
	public void update_title_too_long() {
		post.setTitle(RandomStringUtils.randomAlphabetic(300));

		Response response = given().spec(rspec).body(post).put(TestConstants.POSTS);
		response.then().statusCode(405);
	}

	/**
	 * Edit the post with empty content.
	 */
	@Test
	public void update_content_empty() {
		post.setContent("");

		Response response = given().spec(rspec).body(post).put(TestConstants.POSTS);
		response.then().statusCode(405);
	}

	/**
	 * Edit the post with NULL content.
	 */
	@Test
	public void update_content_null() {
		post.setContent(null);

		Response response = given().spec(rspec).body(post).put(TestConstants.POSTS);
		response.then().statusCode(405);
	}

	/**
	 * Edit the post with very long content.
	 */
	@Test
	public void update_content_very_long() {
		post.setContent(RandomStringUtils.randomAlphabetic(10000));

		sendEditAndValidateResponse(post);
	}
	
}
