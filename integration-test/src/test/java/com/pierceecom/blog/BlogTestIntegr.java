package com.pierceecom.blog;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.piercevom.blog.api.dto.PostDto;

import io.restassured.response.Response;

/**
 * TODO, Consider it part of the test to replace HttpURLConnection with better
 * APIs (for example Jersey-client, JSON-P etc-) to call REST-service
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogTestIntegr extends AbstractBlogTest {

    
    private static PostDto post1;
    private static PostDto post2;


    public BlogTestIntegr() {
    	
    }

    @BeforeClass
    public static void init() {
		post1 = new PostDto(null, "First title", "First content");
		post2 = new PostDto(null, "Second title", "Second content");
		
		PostDto[] outputList = given()
	    	.spec(rspec)
	    	.get(TestConstants.POSTS_RESOURCE).
	    		then().statusCode(200).
	    		and().extract().body().as(PostDto[].class);
    	
    	for (PostDto postDto : outputList) {
    		given().spec(rspec).delete(TestConstants.POSTS_RESOURCE + "/" + postDto.getId()).then().statusCode(200);
		}
    }
    
    
    @Test
    public void test_0_letsMakeSureThat_RestAssure_Works() {
    	given()
    	.spec(rspec)
    	.get("hello-pierce").
    		then().
    			statusCode(javax.ws.rs.core.Response.Status.OK.getStatusCode()).
    		and().
    			body(equalTo("{\"message\":\"Hello Pierce\"}"));
    }
    
	@Test
    public void test_1_BlogWithoutPosts() {

		List<PostDto> outputList = getAll(javax.ws.rs.core.Response.Status.OK);

		assertEquals(0, outputList.size());
    }

    @Test
    public void test_2_AddPosts() {
    	
    	Response response = sendAddPost(post1, javax.ws.rs.core.Response.Status.CREATED);
    	post1 = getPostAfterCreate(post1, response, javax.ws.rs.core.Response.Status.OK);
    	
    	response = sendAddPost(post2, javax.ws.rs.core.Response.Status.CREATED);    	
    	post2 = getPostAfterCreate(post2, response, javax.ws.rs.core.Response.Status.OK);
    	
    }
    
    @Test
    public void test_3_GetPost() {
    	PostDto post = getPostById(post1.getId(), javax.ws.rs.core.Response.Status.OK);
        assertEquals(post1, post);

        post = getPostById(post2.getId(), javax.ws.rs.core.Response.Status.OK);
        assertEquals(post2, post);
    }

    @Test
    public void test_4_GetAllPosts() {
    	List<PostDto> posts = getAll(javax.ws.rs.core.Response.Status.OK);
    	assertEquals(new ArrayList<>(Arrays.asList(post1, post2)), posts);
    }
    
    @Test
    public void test_5_DeletePosts() {
    	given().spec(rspec).delete(TestConstants.POSTS_RESOURCE + "/" + post1.getId()).then().statusCode(javax.ws.rs.core.Response.Status.OK.getStatusCode());
        // Should now be gone
        given().spec(rspec).get(TestConstants.POSTS_RESOURCE + "/" + post1.getId()).then().statusCode(javax.ws.rs.core.Response.Status.NO_CONTENT.getStatusCode());

        given().spec(rspec).delete(TestConstants.POSTS_RESOURCE + "/" + post2.getId()).then().statusCode(javax.ws.rs.core.Response.Status.OK.getStatusCode());
        // Should now be gone
        given().spec(rspec).get(TestConstants.POSTS_RESOURCE + "/" + post2.getId()).then().statusCode(javax.ws.rs.core.Response.Status.NO_CONTENT.getStatusCode());
        
        given().spec(rspec).delete(TestConstants.POSTS_RESOURCE + "/" + post2.getId()).then().statusCode(javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_6_GetAllPostsShouldNowBeEmpty() {
    	List<PostDto> outputList = new ArrayList<>();
    	
    	outputList = given()
	    	.spec(rspec)
	    	.get(TestConstants.POSTS_RESOURCE).
	    		then().statusCode(javax.ws.rs.core.Response.Status.OK.getStatusCode()).
	    		and().extract().body().as(outputList.getClass());
    	
    	assertEquals(0, outputList.size());
    }

}
