package br.ufrn.imd.artfolio.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import br.ufrn.imd.artfolio.exception.PostException;
import br.ufrn.imd.artfolio.model.Post;
import br.ufrn.imd.artfolio.model.Tag;
import br.ufrn.imd.artfolio.service.PostService;
import br.ufrn.imd.artfolio.service.TagService;
import br.ufrn.imd.artfolio.view.ErrorMessage;

		
@Named("postMBean")
@SessionScoped
public class PostMBean implements Serializable {
	
	@Inject
	private PostService postService;
	
	@Inject
	private TagService tagService;
	
	@Inject
	private UserMBean userMBean;
	
	private Post post;
	
	private Part image;
	
	private String tags;
	private ArrayList<Tag> tagsList = new ArrayList<Tag>();
	private List<Post> sessionUserPosts;
	
	public PostMBean() {
		post = new Post();
	}
	
	private void newPost() {
		post = new Post();
		tags = "";
		tagsList = new ArrayList<Tag>();
	}
	
	public void updateSessionUserPosts() {
		sessionUserPosts = postService.getUserPosts(userMBean.getSessionUser());
	}
	
	private boolean requiredFieldsToCreate() {
		return image != null;
	}
	
	public void create() {	
		
		if(! requiredFieldsToCreate()) {
			new ErrorMessage("Required fields are blank");

		} else {
			post.setUser(userMBean.getSessionUser());
	
			try {
				post = postService.add(post, image);
				
				if(! tags.isBlank()) {
					convertTags();
					persistTags();
				}
				
				updateSessionUserPosts();
				newPost();
				
			} catch (PostException e) {
				new ErrorMessage(e.getMessage());
			}
						
		}
		
	}
	
	public void delete(Post p) {
		postService.remove(p);
		updateSessionUserPosts();
	}
	
	private void convertTags() {
		String[] tagSplit = tags.split(",");
		
		for(String t : tagSplit) {
			Tag tag = new Tag();
			tag.setName(t);
			tag.setPost(post);
			
			tagsList.add(tag);
		}
	}
	
	private void persistTags() {
		try {
			for(Tag tag : tagsList) {
				tagService.add(tag);
			}
			
		} catch (PostException e) {
			new ErrorMessage(e.getMessage());
		}
		
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public ArrayList<Tag> getTagsList() {
		return tagsList;
	}

	public void setTagsList(ArrayList<Tag> tagsList) {
		this.tagsList = tagsList;
	}

	public Part getImage() {
		return image;
	}

	public void setImage(Part image) {
		this.image = image;
	}

	public List<Post> getSessionUserPosts() {
		return sessionUserPosts;
	}

	public void setSessionUserPosts(List<Post> sessionUserPosts) {
		this.sessionUserPosts = sessionUserPosts;
	}


	
	

}
