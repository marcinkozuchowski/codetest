package com.piercevom.blog.api.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TestPostDto {

	@Test
	public void test_PostDto_equality() {
		PostDto post1 = new PostDto("1", "Title", "Content");
		PostDto post2 = new PostDto("1", "Title", "Content");
		
		assertEquals(post1, post2);
	}
	
	@Test
	public void test_PostDto_equality_2() {
		PostDto post1 = new PostDto(null, "Title", "Content");
		PostDto post2 = new PostDto(null, "Title", "Content");
		
		assertEquals(post1, post2);
	}
	
	@Test
	public void test_PostDto_equality_3() {
		PostDto post1 = new PostDto("1", null, "Content");
		PostDto post2 = new PostDto("1", null, "Content");
		
		assertEquals(post1, post2);
	}
	
	@Test
	public void test_PostDto_equality_4() {
		PostDto post1 = new PostDto("1", "Title", "Content2");
		PostDto post2 = new PostDto("1", "Title", "Content");
		
		assertNotEquals(post1, post2);
	}
	
	@Test
	public void test_PostDto_equality_5() {
		PostDto post = new PostDto("1", "Title", "Content");
		
		assertEquals(post, post);
	}
	
	@Test
	public void test_PostDto_equality_6() {
		PostDto post = new PostDto("1", "Title", "Content");
		
		assertNotEquals(post, new StringBuilder());
	}
	
	@Test
	public void test_PostDto_equality_7() {
		PostDto post = new PostDto("1", "Title", "Content");
		
		assertNotEquals(post, null);
	}
	
	/**
	 * No error is a success
	 */
	@Test
	public void test_PostDto_toString() {
		PostDto post1 = new PostDto();
		post1.toString();
	}
}
