package com.pierceecom.blog;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.piercevom.blog.api.dto.PostDto;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

/**
 * TODO, Consider it part of the test to replace HttpURLConnection with better
 * APIs (for example Jersey-client, JSON-P etc-) to call REST-service
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogTestIntegr {

    private static final String POST_1 = "{\"id\":\"1\",\"title\":\"First title\",\"content\":\"First content\"}";
    private static final String POST_2 = "{\"id\":\"2\",\"title\":\"Second title\",\"content\":\"Second content\"}";
    private static final String POSTS_URI = "http://localhost:8080/blog-web/posts/";
    
    private static final String BASE_URI = "http://localhost:8080";
    private static final String BASE_PATH = "blog-web";
    private static final String POSTS = "posts";
    
	private static RequestSpecification rspec;

    
    public BlogTestIntegr() {
    }

    @BeforeClass
    public static void init() {
    	RestAssured.defaultParser = Parser.JSON;

		RequestSpecBuilder rspecBuilder = new RequestSpecBuilder();
			rspecBuilder.setBaseUri (BASE_URI);
			rspecBuilder.setBasePath (BASE_PATH);
			rspecBuilder.addHeader("Content-Type", MediaType.APPLICATION_JSON);
			rspecBuilder.addHeader("Accept", MediaType.APPLICATION_JSON);

		rspec = rspecBuilder.build();
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
	    	.get(POSTS).
	    		then().statusCode(200).
	    		and().extract().body().as(outputList.getClass());
    	
    	assertEquals(1, outputList.size());
    }

    @Test
    public void test_2_AddPosts() {
        String location = POST(POSTS_URI, POST_1);
        assertEquals(POSTS_URI + "1", location);

        location = POST(POSTS_URI, POST_2);
        assertEquals(POSTS_URI + "2", location);
    }

    @Test
    public void test_3_GetPost() {
        String postJson = GET(POSTS_URI + "1", 200);
        assertEquals(POST_1, postJson);

        postJson = GET(POSTS_URI + "2", 200);
        assertEquals(POST_2, postJson);
    }

    @Test
    public void test_4_GetAllPosts() {
        String output = GET(POSTS_URI, 200);
        assertEquals("[" + POST_1 + "," + POST_2 + "]", output);
    }
    
    @Test
    public void test_5_DeletePosts() {
        DELETE(POSTS_URI + "1", 200);        
        // Should now be gone
        GET(POSTS_URI + "1", 204);

        DELETE(POSTS_URI + "2", 200);        
        // Should now be gone
        GET(POSTS_URI + "2", 204);      

    }

    @Test
    public void test_6_GetAllPostsShouldNowBeEmpty() {
        String output = GET(POSTS_URI, 200);
        assertEquals("[]", output);
    }

    /* Helper methods */
    private String GET(String uri, int expectedResponseCode) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            assertEquals(expectedResponseCode, conn.getResponseCode());
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

    private String POST(String uri, String json) {
        String location = "";
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            assertEquals(201, conn.getResponseCode());

            location = conn.getHeaderField("Location");
            conn.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return location;
    }

    private void DELETE(String uri, int expectedResponseCode) {
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");
            assertEquals(expectedResponseCode, conn.getResponseCode());
        } catch (MalformedURLException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
