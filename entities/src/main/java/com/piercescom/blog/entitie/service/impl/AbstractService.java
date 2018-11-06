package com.piercescom.blog.entitie.service.impl;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.piercescom.blog.entitie.service.Service;

public abstract class AbstractService<T> implements Service<T> {

	@PersistenceContext(unitName="pierce")
	protected EntityManager em;
	
	protected Class<T> clazz;
	
	protected AbstractService(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public T findById(Object id) {
		long t = System.currentTimeMillis();
		try {
			return em.find(this.clazz, id);
		} finally {
			getLogger().finest(() -> "findById(" + id + ") in " + (System.currentTimeMillis() - t) + " [ms]");
		}
	}
	
	@Override
	public void save(T entity) {
		long t = System.currentTimeMillis();
		try {
			em.persist(entity);
		} finally {
			getLogger().finest(() -> "save in " + (System.currentTimeMillis() - t) + " [ms]");
		}
	}

	@Override
	public T update(T entity) {
		long t = System.currentTimeMillis();
		try {
			return em.merge(entity);
		} finally {
			getLogger().finest(() -> "update in " + (System.currentTimeMillis() - t) + " [ms]");
		}
	}

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
