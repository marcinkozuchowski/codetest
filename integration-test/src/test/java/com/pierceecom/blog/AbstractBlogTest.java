package com.pierceecom.blog;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;

import com.piercevom.blog.api.dto.PostDto;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AbstractBlogTest {

	protected static RequestSpecification rspec;

	@BeforeClass
	public static void init() {
		RestAssured.defaultParser = Parser.JSON;

		RequestSpecBuilder rspecBuilder = new RequestSpecBuilder();
		rspecBuilder.setBaseUri(TestConstants.BASE_URI);
		rspecBuilder.setBasePath(TestConstants.BASE_PATH);
		rspecBuilder.addHeader("Content-Type", MediaType.APPLICATION_JSON);
		rspecBuilder.addHeader("Accept", MediaType.APPLICATION_JSON);

		rspec = rspecBuilder.build();

	}
	
	protected PostDto sendAddAndValidateResponse(PostDto post) {
		// Add POST
    	Response response = given().spec(rspec).body(post).post(TestConstants.POSTS);
    	response.then().statusCode(201);

    	return validatePostOnAddOrEdit(post, response);
	}

	protected PostDto sendEditAndValidateResponse(PostDto post) {
		// EDIT POST
    	Response response = given().spec(rspec).body(post).put(TestConstants.POSTS);
    	response.then().statusCode(201);

    	return validatePostOnAddOrEdit(post, response);
	}
	
	/**
	 * Method invokes GET on address taken from head's 'location' param.
	 * @param post - data send to update/add in previous request
	 * @param response - response of that request
	 * @return Fresh instance of PostDto.cass retrieved from server
	 */
	private PostDto validatePostOnAddOrEdit(PostDto post, Response response) {
		// Retrieve previously added/modified POST, and validate its values
    	PostDto persistedPost = given().spec(rspec).get(response.getHeader("Location")).then().statusCode(200).and().extract().body().as(PostDto.class);
    	assertEquals(post.getTitle(), persistedPost.getTitle());
    	assertEquals(post.getContent(), persistedPost.getContent());
    	
    	post.setId(persistedPost.getId()); //this correct IDs may be used in further tests
    	
    	return persistedPost;
	}
}
