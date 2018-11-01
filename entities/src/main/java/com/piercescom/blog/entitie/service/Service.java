package com.piercescom.blog.entitie.service;

public interface Service<T> {

	public T findById(Object id);
	
	public void save(T entity);

	public T update(T entity);
	
	public void delete(T entity);
	
}
