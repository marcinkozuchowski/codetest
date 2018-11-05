package com.pierceecom.blog;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.piercevom.blog.api.dto.PostDto;

import io.restassured.http.Header;
import io.restassured.response.Response;

/**
 * Class that tests if all PostResource methods works for both, json and xml content type.
 * @author marcin.kozuchowski
 *
 */
@RunWith(Parameterized.class)
public class MediaTypeTest extends AbstractBlogTest {

	@Parameters
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][]{
				
			{ MediaType.APPLICATION_JSON },
			{ MediaType.APPLICATION_XML }
			
		});
	}
	
	@Parameter(0)
	public String mediaType;
	
	/**
	 * Purge all posts.
	 */
	@BeforeClass
	public static void init() {
		PostDto[] outputList = given()
	    	.spec(rspec)
	    	.get(TestConstants.POSTS_RESOURCE).
	    		then().statusCode(200).
	    		and().extract().body().as(PostDto[].class);
    	
    	for (PostDto postDto : outputList) {
    		given().spec(rspec).delete(TestConstants.POSTS_RESOURCE + "/" + postDto.getId()).then().statusCode(200);
		}
	}
	
	/**
	 * Full happy flow:
	 * - create post
	 * - get all to check if posts numbers match
	 * - edit post
	 * - get all posts and count them again
	 * - delete post
	 * - get all once again to check if none exists
	 */
	@Test
	public void testFullHappyFlow() {
		PostDto post = new PostDto("1", "Post title", "Post content");
		Header contentTypeHeader = new Header(HttpHeaders.CONTENT_TYPE, mediaType);
		Header acceptTypeHeader = new Header(HttpHeaders.ACCEPT, mediaType);
		
		Response response = sendAddPost(post, javax.ws.rs.core.Response.Status.CREATED, contentTypeHeader);
		post = getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK, acceptTypeHeader);
		
    	
		List<PostDto> outputList = getAll(javax.ws.rs.core.Response.Status.OK, acceptTypeHeader);
    	assertEquals(1, outputList.size());
    	
    	post.setTitle("A new title");
    	response = sendEdit(post, javax.ws.rs.core.Response.Status.CREATED, contentTypeHeader);
    	post = getPostAfterCreate(post, response, javax.ws.rs.core.Response.Status.OK, acceptTypeHeader);
    	
    	outputList = getAll(javax.ws.rs.core.Response.Status.OK, acceptTypeHeader);
    	assertEquals(1, outputList.size());
    	
    	given().spec(rspec).delete(TestConstants.POSTS_RESOURCE + "/" + post.getId()).then().statusCode(200);
    	
    	outputList = getAll(javax.ws.rs.core.Response.Status.OK, acceptTypeHeader);
    	assertEquals(0, outputList.size());
	}
	
}
