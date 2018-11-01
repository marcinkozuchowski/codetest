package com.piercescom.blog.entitie.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

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
			getLogger().trace("findById({}) in {} [ms]", id, (System.currentTimeMillis() - t));
		}
	}
	
	@Override
	public void save(T entity) {
		long t = System.currentTimeMillis();
		try {
			em.persist(entity);
		} finally {
			getLogger().trace("save in {} [ms]", (System.currentTimeMillis() - t));
		}
	}

	@Override
	public T update(T entity) {
		long t = System.currentTimeMillis();
		try {
			return em.merge(entity);
		} finally {
			getLogger().trace("update in {} [ms]", (System.currentTimeMillis() - t));
		}
	}

	@Override
	public void delete(T entity) {
		long t = System.currentTimeMillis();
		try {
			em.remove(entity);
		} finally {
			getLogger().trace("delete in {} [ms]", (System.currentTimeMillis() - t));
		}
	}

	protected abstract Logger getLogger();
	
}
