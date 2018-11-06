package com.pierceecom.blog;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;

import com.piercevom.blog.api.dto.PostDto;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class AbstractBlogTest {

	protected static RequestSpecification rspec;

	@BeforeClass
	public static void initAbstractClass() {
		RestAssured.defaultParser = Parser.JSON;

		RequestSpecBuilder rspecBuilder = new RequestSpecBuilder();
		rspecBuilder.setBaseUri(TestConstants.BASE_URI);
		rspecBuilder.setBasePath(TestConstants.BASE_PATH);
		rspecBuilder.addHeader("Content-Type", MediaType.APPLICATION_JSON);
		rspecBuilder.addHeader("Accept", MediaType.APPLICATION_JSON);

		rspec = rspecBuilder.build();

	}
	
	/**
	 * Do HTTP POST to persist post.
	 * @param post - post to persist
	 * @param headers - some http headers (ie. Content-type or Accept)
	 * @return Server response
	 */
	protected Response sendAddPost(PostDto post, javax.ws.rs.core.Response.Status expectedStatus, Header ... headers) {
		RequestSpecification builder = buildSpecWithHeaders(headers);
    	
		if (post != null) {
			builder.body(post);
		}
		
    	Response response = builder.post(TestConstants.POSTS_RESOURCE);
    	response.then().statusCode(expectedStatus.getStatusCode());

    	return response;
	}
	
	/**
	 * Do HTTP PUT to update post.
	 * @param post - post to update
	 * @param headers - some http headers (ie. Content-type or Accept)
	 * @return Server response
	 */
	protected Response sendEdit(PostDto post, javax.ws.rs.core.Response.Status expectedStatus, Header ... headers) {
		RequestSpecification builder = buildSpecWithHeaders(headers);
		
		if (post != null) {
			builder.body(post);
		}
		
    	Response response = builder.put(TestConstants.POSTS_RESOURCE);
    	response.then().statusCode(expectedStatus.getStatusCode());
    	
    	return response;
	}

	/**
	 * Builds RequestSpecification with headers
	 * @param headers to set on request
	 * @return Partially prepared RequestSpecification
	 */
	private RequestSpecification buildSpecWithHeaders(Header... headers) {
		RequestSpecification builder = given().spec(rspec);
    	
    	if (headers != null) {
    		for (Header header : headers) {
				builder.header(header);
			}
    	}
		return builder;
	}
	
	/**
	 * Returns PostDto, method expects that object with given id exists.
	 * @param id - post id
	 * @param headers - some http headers (ie. Content-type or Accept)
	 * @return post
	 */
	protected PostDto getPostById(String id, javax.ws.rs.core.Response.Status expectedStatus, Header ...headers) {
		RequestSpecification builder = buildSpecWithHeaders(headers);
		
		ValidatableResponse validatableResponse = builder.get(TestConstants.POSTS_RESOURCE + "/" + id).then().statusCode(expectedStatus.getStatusCode());
		
		if (expectedStatus.equals(javax.ws.rs.core.Response.Status.OK)) {
			return validatableResponse.extract().body().as(PostDto.class);
		}
		
		return null;
	}
	
	/**
	 * Returns all posts.
	 * 
	 * @param headers - some http headers (ie. Content-type or Accept)
	 * @return
	 */
	protected List<PostDto> getAll(javax.ws.rs.core.Response.Status expectedStatus, Header ... headers) {
		RequestSpecification builder = given().spec(rspec);
		boolean xmlResponse = false;
		
		if (headers != null) {
    		for (Header header : headers) {
				builder.header(header);

				if (HttpHeaders.ACCEPT.equalsIgnoreCase(header.getName()) && MediaType.APPLICATION_XML.equalsIgnoreCase(header.getValue())) {
					xmlResponse = true;
				}
			}
    	}
		
		ValidatableResponse validatableResponse = builder.get(TestConstants.POSTS_RESOURCE).
				then().statusCode(expectedStatus.getStatusCode());
				
		if (expectedStatus.equals(javax.ws.rs.core.Response.Status.OK)) {
			ResponseBodyExtractionOptions reponseBody = validatableResponse.and().extract().body();
			
			if (xmlResponse) {
				return reponseBody.xmlPath().getList("postDtoes.Post", PostDto.class);
			}
			
			return reponseBody.jsonPath().getList("$", PostDto.class);
		} 
		
		return null;
	}
	
	/**
	 * Method invokes GET on address taken from head's 'location' param and validates retrieved PostDto with local one.
	 * @param post - data send to update/add in previous request
	 * @param response - response of that request
	 * @return Fresh instance of PostDto.cass retrieved from server
	 */
	protected PostDto getPostAfterCreate(PostDto post, Response response, javax.ws.rs.core.Response.Status expectedStatus, Header ... headers) {
		// Retrieve previously added/modified POST, and validate its values
		RequestSpecification builder = given().spec(rspec);
		ObjectMapperType mapperType = ObjectMapperType.JACKSON_2;
		
		if (headers != null) {
    		for (Header header : headers) {
				builder.header(header);

				if (HttpHeaders.ACCEPT.equalsIgnoreCase(header.getName()) && MediaType.APPLICATION_XML.equalsIgnoreCase(header.getValue())) {
					mapperType = ObjectMapperType.JAXB;
				}
			}
    	}
    	
    	ValidatableResponse validatableResponse = builder.get(response.getHeader("Location"))
    			.then().statusCode(expectedStatus.getStatusCode());
    	
    	if (expectedStatus.equals(javax.ws.rs.core.Response.Status.OK)) {
    		PostDto persistedPost =  validatableResponse.and()
        			.extract().body().as(PostDto.class, mapperType);
        	
        	assertEquals(post.getTitle(), persistedPost.getTitle());
        	assertEquals(post.getContent(), persistedPost.getContent());
        	
        	post.setId(persistedPost.getId()); //this correct IDs may be used in further tests
        	
        	return persistedPost;	
    	}
    	
    	return null;
	}
}
