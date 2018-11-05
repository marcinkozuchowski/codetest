package com.pierceecom.blog.helper;

import java.util.ArrayList;
import java.util.List;

import com.piercescom.blog.entitie.Post;
import com.piercevom.blog.api.dto.PostDto;

public class EntityToDtoConverter {

	private EntityToDtoConverter () {
		
	}
	
	public static PostDto convertEntity(Post post) {
		PostDto postDto = null;
		if (post != null) {
			postDto = new PostDto(
						(post.getId() != null ? post.getId().toString() : null), 
						post.getTitle(), 
						post.getContent()
					); 
		}
		
		return postDto;
	}
	
	public static java.util.List<PostDto> convert(List<Post> posts) {
		List<PostDto> result = new ArrayList<>();
		for (Post post : posts) {
			result.add(convertEntity(post));
		}
		
		return result;
	}

	public static Post convertDto(PostDto postDto) {
		Post post = null;
		if (postDto != null) {
			post = new Post(); 
			post.setId(postDto.getId() != null ? Long.valueOf(postDto.getId()) : null);
			post.setTitle(postDto.getTitle());
			post.setContent(postDto.getContent());
		}
		
		return post;
	}
	
}
