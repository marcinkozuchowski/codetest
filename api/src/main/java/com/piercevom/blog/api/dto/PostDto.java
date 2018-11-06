package com.piercevom.blog.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.piercevom.blog.api.validation.groups.PostAction;
import com.piercevom.blog.api.validation.groups.PutAction;

/**
 * Class representing a simple (blog's) post 
 */
@XmlRootElement(name = "Post")
@JsonPropertyOrder({"id", "title", "content"})
public class PostDto {

	/** Unique identifier. */
	@NotBlank(groups = PutAction.class)
	@Pattern(regexp = "[0-9]+", groups = PutAction.class)
	private String id;

	/** Title. */
	@NotBlank(groups = {PutAction.class, PostAction.class})
	@Size(max=255, groups = {PutAction.class, PostAction.class})
	private String title;

	/** Some awesome content. */
	@NotBlank(groups = {PutAction.class, PostAction.class})
	private String content;
	
	public PostDto() {
	}
	
	public PostDto(String id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	public boolean equals(Object obj) {
		if (this == obj) {
	        return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
	        return false;
		}
		
		PostDto post = (PostDto) obj;

		return Objects.equals(id, post.id)
				&& Objects.equals(title, post.title)
	            && Objects.equals(content, post.content);
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
