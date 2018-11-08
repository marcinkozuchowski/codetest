package com.piercescom.blog.entitie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class of post.
 * @author marcin.kozuchowski
 *
 */
@Entity
@Table(schema = "blog")
@SequenceGenerator(name="post_sequence", schema = "blog", sequenceName = "post_id_seq", allocationSize = 1)
public class Post implements EntityIntf {

	/**
	 * Post unique id.
	 */
	@Id
	@GeneratedValue(generator = "post_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	/**
	 * Post title.
	 */
	@Column(nullable = false)
	private String title;
	
	/**
	 * Post content.
	 */
	@Column(nullable = false, columnDefinition="text")
	private String content;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
