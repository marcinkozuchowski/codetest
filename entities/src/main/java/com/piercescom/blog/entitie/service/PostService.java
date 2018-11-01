package com.piercescom.blog.entitie.service;

import java.util.List;

import javax.ejb.Local;

import com.piercescom.blog.entitie.Post;

@Local
public interface PostService extends Service<Post> {

	public List<Post> findAll();
	
	public int deleteById(Long id);
	
}
