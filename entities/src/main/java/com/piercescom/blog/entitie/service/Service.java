package com.piercescom.blog.entitie.service;

import com.piercescom.blog.entitie.EntityIntf;

public interface Service<T extends EntityIntf> {

	/**
	 * Returns entity by id.
	 * 
	 * @param id - entity primary key
	 * @return Entity object
	 */
	public T findById(Object id);
	
	/**
	 * Persists entity.
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * Merges entity.
	 * @param entity
	 * @return
	 */
	public T update(T entity);
	
	/**
	 * Removes entity.
	 * @param entity
	 */
	public void delete(T entity);
	
}
