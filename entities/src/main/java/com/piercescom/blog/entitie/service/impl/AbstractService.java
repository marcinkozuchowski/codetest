package com.piercescom.blog.entitie.service.impl;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.piercescom.blog.entitie.EntityIntf;
import com.piercescom.blog.entitie.service.Service;

/**
 * Generic abstract class with generic methods providing common entity operations for subclasses.
 * 
 * @author marcin.kozuchowski
 *
 * @param <T> - entity class
 */
public abstract class AbstractService<T extends EntityIntf> implements Service<T> {

	@PersistenceContext(unitName="pierce")
	protected EntityManager em;
	
	/**
	 * Entity class managed by subclass of this abstract.
	 */
	protected Class<T> clazz;
	
	protected  AbstractService(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findById(Object id) {
		long t = System.currentTimeMillis();
		try {
			return em.find(this.clazz, id);
		} finally {
			getLogger().finest(() -> "findById(" + id + ") in " + (System.currentTimeMillis() - t) + " [ms]");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(T entity) {
		long t = System.currentTimeMillis();
		try {
			em.persist(entity);
		} finally {
			getLogger().finest(() -> "save in " + (System.currentTimeMillis() - t) + " [ms]");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T update(T entity) {
		long t = System.currentTimeMillis();
		try {
			return em.merge(entity);
		} finally {
			getLogger().finest(() -> "update in " + (System.currentTimeMillis() - t) + " [ms]");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(T entity) {
		long t = System.currentTimeMillis();
		try {
			em.remove(entity);
		} finally {
			getLogger().finest(() -> "delete in " + (System.currentTimeMillis() - t) + " [ms]");
		}
	}

	protected abstract Logger getLogger();
	
}
