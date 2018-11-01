package com.piercescom.blog.entitie.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piercescom.blog.entitie.Post;
import com.piercescom.blog.entitie.service.PostService;

@Stateless
public class PostServiceBean extends AbstractService<Post> implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceBean.class);

	public PostServiceBean() {
		super(Post.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> findAll() {
		long t = System.currentTimeMillis();
		try {

			return em.createQuery("SELECT p FROM Post p ORDER by p.id ASC").getResultList();
		
		} finally {
			logger.trace("findAll in {} [ms]", (System.currentTimeMillis() - t));
		}
	}
	
	public int deleteById(Long id) {
		long t = System.currentTimeMillis();
		int result = 0;
		try {
			
			Query q = em.createQuery("DELETE FROM Post WHERE id = :id");
			q.setParameter("id", id);
			
			result = q.executeUpdate();
			return result;
			
		} finally {
			logger.trace("deleteById({}) in {} [ms] with result: {}", id, (System.currentTimeMillis() - t), result);
		}
	}
	
	@Override
	protected Logger getLogger() {
		return logger;
	}
	
}
