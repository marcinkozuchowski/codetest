package com.pierceecom.blog.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.piercescom.blog.entitie.Post;
import com.piercevom.blog.api.dto.PostDto;

public class TestEntityToDtoConverter {

	@Test
	public void test_1() {
		assertNull(EntityToDtoConverter.convertEntity(null));
	}
	
	@Test
	public void test_2() {
		Post post = new Post();
		PostDto postDto = EntityToDtoConverter.convertEntity(post);
		
		assertNotNull(postDto);
		areInternalyEquals(postDto, post);	
	}
	
	@Test
	public void test_3() {
		Post post = new Post();
		post.setId(2l);
		PostDto postDto = EntityToDtoConverter.convertEntity(post);
		
		assertNotNull(postDto);
		areInternalyEquals(postDto, post);	
	}
	
	@Test
	public void test_4() {
		Post post = new Post();
		post.setId(2l);
		post.setTitle("Title");
		PostDto postDto = EntityToDtoConverter.convertEntity(post);
		
		assertNotNull(postDto);
		areInternalyEquals(postDto, post);	
	}
	
	@Test
	public void test_5() {
		Post post = new Post();
		post.setId(2l);
		post.setTitle("Title");
		post.setContent("Content");
		PostDto postDto = EntityToDtoConverter.convertEntity(post);
		
		assertNotNull(postDto);
		areInternalyEquals(postDto, post);	
	}
	
	@Test
	public void test_6() {
		assertNull(EntityToDtoConverter.convertDto(null));
	}
	
	@Test
	public void test_7() {
		PostDto postDto = new PostDto();
		Post post = EntityToDtoConverter.convertDto(postDto);
		
		assertNotNull(post);
		areInternalyEquals(postDto, post);	
	}
	
	@Test
	public void test_8() {
		PostDto postDto = new PostDto("1", null, null);
		Post post = EntityToDtoConverter.convertDto(postDto);
		
		assertNotNull(post);
		areInternalyEquals(postDto, post);	
	}
	
	@Test
	public void test_9() {
		PostDto postDto = new PostDto("1", "Title", null);
		Post post = EntityToDtoConverter.convertDto(postDto);
		
		assertNotNull(post);
		areInternalyEquals(postDto, post);	
	}
	
	@Test
	public void test_10() {
		PostDto postDto = new PostDto("1", "Title", "Content");
		Post post = EntityToDtoConverter.convertDto(postDto);
		
		assertNotNull(post);
		areInternalyEquals(postDto, post);	
	}
	
	protected void areInternalyEquals(PostDto postDto, Post post) {
		assertEquals(post.getId(), (postDto.getId() != null ? Long.parseLong(postDto.getId()) : null));
		
		assertEquals(post.getTitle(), postDto.getTitle());
		assertEquals(post.getContent(), postDto.getContent());
	}
}
