package com.piercevom.blog.api.dto;

/**
 * Class representing a simple (blog's) post 
 */
public class Post {

	/** Unique identifier. */
	private Integer id;

	/** Title. */
	private String title;

	/** Some awesome content. */
	private String content;
	

	public Post(int id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	@Override
	public String toString() {
		return "Post ["
					+ "id=" + id 
					+ ", title=" + title 
					+ ", content=" + content 
				+ "]";
	}

}
