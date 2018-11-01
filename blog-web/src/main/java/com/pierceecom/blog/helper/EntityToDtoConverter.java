package com.pierceecom.blog.helper;

import java.util.ArrayList;
import java.util.List;

import com.piercescom.blog.entitie.Post;
import com.piercevom.blog.api.dto.PostDto;

public class EntityToDtoConverter {

	public static PostDto convert(Post post) {
		PostDto postDto = null;
		if (post != null) {
			postDto = new PostDto(post.getId().toString(), post.getTitle(), post.getContent()); 
		}
		
		return postDto;
	}
	
	public static java.util.List<PostDto> convert(List<Post> posts) {
		List<PostDto> result = new ArrayList<>();
		for (Post post : posts) {
			result.add(convert(post));
		}
		
		return result;
	}
	
}
