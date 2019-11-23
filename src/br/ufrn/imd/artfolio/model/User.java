package br.ufrn.imd.artfolio.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;
import com.google.gson.JsonObject;

@Entity
@Table(name = "ArtUser")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_USER")
	@SequenceGenerator(name="SEQ_USER", sequenceName="id_seq_user", allocationSize=1)
	private int id;
	
	@Column(nullable = false, length = 255)
	private String username;
	
	@Column(nullable = false, length = 255)
	private String name;
	
	@Column(nullable = false, length = 255)
	private String email;
	
	@Column(nullable = false, length = 8)
	private String password;
	
	@Column(length = 255)
	private String image;
	
	@Column(length = 50)
	private String alias;
	
	@Column(length = 200)
	private String slogan;
	
	private String bio;
	
	public User() {}
	
	public User(String username, String name, String email, String password, String image, String alias, String slogan, String bio) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = image;
		this.alias = alias;
		this.slogan = slogan;
		this.bio = bio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		JsonObject object = new JsonObject();
		
		object.addProperty("id", getId());
		object.addProperty("username", getUsername());
		object.addProperty("name", getName());
		object.addProperty("email", getEmail());
		object.addProperty("password", getPassword());
		object.addProperty("image", getImage());
		object.addProperty("alias", getAlias());
		object.addProperty("slogan", getSlogan());
		object.addProperty("bio", getBio());
		
		return object.toString();
	}
	
	
	
	
}
