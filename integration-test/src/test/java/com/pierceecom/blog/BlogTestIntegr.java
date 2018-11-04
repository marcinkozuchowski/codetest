package com.pierceecom.blog;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.piercevom.blog.api.dto.PostDto;

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
		post1 = new PostDto("1", "First title", "First content");
		post2 = new PostDto("2", "Second title", "Second content");
    }
    
    
    @Test
    public void test_0_letsMakeSureThatRestAssureWorks() {
    	given()
    	.spec(rspec)
    	.get("hello-pierce").
    		then().
    			statusCode(200).
    		and().
    			body(equalTo("{\"message\":\"Hello Pierce\"}"));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void test_1_BlogWithoutPosts() {
    	List<PostDto> outputList = new ArrayList<>();
    	
    	outputList = given()
	    	.spec(rspec)
	    	.get(TestConstants.POSTS).
	    		then().statusCode(200).
	    		and().extract().body().as(outputList.getClass());
    	
    	assertEquals(0, outputList.size());
    }

    @Test
    public void test_2_AddPosts() {
    	
    	sendAddAndValidateResponse(post1);
    	sendAddAndValidateResponse(post2);
    	
    }

    @Test
    public void test_4_GetAllPosts() {
    	PostDto persistedPost1 = given().spec(rspec).get(TestConstants.POSTS + "/" + post1.getId()).then().statusCode(200).and().extract().body().as(PostDto.class);
    	assertEquals(post1, persistedPost1);
    }
    
    @Test
    public void test_5_DeletePosts() {
    	given().spec(rspec).delete(TestConstants.POSTS + "/" + post1.getId()).then().statusCode(200);
        // Should now be gone
        given().spec(rspec).get(TestConstants.POSTS + "/" + post1.getId()).then().statusCode(204);

        given().spec(rspec).delete(TestConstants.POSTS + "/" + post2.getId()).then().statusCode(200);
        // Should now be gone
        given().spec(rspec).get(TestConstants.POSTS + "/" + post2.getId()).then().statusCode(204);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_6_GetAllPostsShouldNowBeEmpty() {
    	List<PostDto> outputList = new ArrayList<>();
    	
    	outputList = given()
	    	.spec(rspec)
	    	.get(TestConstants.POSTS).
	    		then().statusCode(200).
	    		and().extract().body().as(outputList.getClass());
    	
    	assertEquals(0, outputList.size());
    }

}
