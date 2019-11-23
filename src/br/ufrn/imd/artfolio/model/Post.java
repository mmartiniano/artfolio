package br.ufrn.imd.artfolio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import com.google.gson.JsonObject;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_POST")
	@SequenceGenerator(name="SEQ_POSTr", sequenceName="id_seq_post", allocationSize=1)
	private int id;
	
	@Column(nullable = false)
	private String image;
	
	private String caption;
	
	@ManyToOne
	@JoinColumn(name = "User_id")
	private User user;
	
	public Post() {}

	public Post(String image, String caption, User user) {
		this.image = image;
		this.caption = caption;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		Post other = (Post) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		JsonObject object = new JsonObject();
		
		object.addProperty("id", getId());
		object.addProperty("image", getImage());
		object.addProperty("caption", getCaption());
		object.addProperty("user", getUser().toString());
		
		return object.toString();
	}
	
	
	
}
