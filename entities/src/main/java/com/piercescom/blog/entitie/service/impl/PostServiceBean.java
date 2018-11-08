package com.piercescom.blog.entitie.service.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.piercescom.blog.entitie.Post;
import com.piercescom.blog.entitie.service.PostService;

@Stateless
public class PostServiceBean extends AbstractService<Post> implements PostService {

	private static final Logger LOGGER = Logger.getLogger(PostServiceBean.class.getName());

	public PostServiceBean() {
		super(Post.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> findAll() {
		long t = System.currentTimeMillis();
		try {

			return em.createQuery("SELECT p FROM Post p ORDER by p.id ASC").getResultList();
		
		} finally {
			LOGGER.finest(() -> "findAll in " + (System.currentTimeMillis() - t) + " [ms]");
		}
	}
	
	public int deleteById(Long id) {
		long t = System.currentTimeMillis();
		try {
			
			Query q = em.createQuery("DELETE FROM Post WHERE id = :id");
			q.setParameter("id", id);
			
			return q.executeUpdate();
		} finally {
			LOGGER.finest(() -> "deleteById(" + id + ") in " + (System.currentTimeMillis() - t) + " [ms] ");
		}
	}
	
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
}
