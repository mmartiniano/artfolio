package br.ufrn.imd.artfolio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import com.google.gson.JsonObject;

@Entity
public class Tag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TAG")
	@SequenceGenerator(name="SEQ_TAG", sequenceName="id_seq_tag", allocationSize=1)
	private int id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "Post_id")
	private Post post;
	
	public Tag() {}
	
	public Tag(String name, Post post) {
		this.name = name;
		this.post = post;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		JsonObject object = new JsonObject();
		
		object.addProperty("id", getId());
		object.addProperty("name", getName());
		object.addProperty("post", getPost().toString());
		
		return object.toString();
	}
	
	

}
