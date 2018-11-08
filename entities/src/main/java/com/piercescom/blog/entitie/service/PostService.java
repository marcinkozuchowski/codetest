package com.piercescom.blog.entitie.service;

import java.util.List;

import javax.ejb.Local;

import com.piercescom.blog.entitie.Post;

@Local
public interface PostService extends Service<Post> {

	/**
	 * Returns all posts found in database.
	 * @return
	 */
	public List<Post> findAll();
	
	/**
	 * Removes from database post by primary key.
	 * @param id - primary key
	 * @return number of modified elements (0 or 1)
	 */
	public int deleteById(Long id);
	
}
