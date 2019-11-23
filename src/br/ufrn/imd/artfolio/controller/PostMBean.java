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
	private ArrayList<Tag> tagList = new ArrayList<Tag>();
	private List<Post> sessionUserPosts;
	
	public PostMBean() {
		post = new Post();
	}
	
	private void newPost() {
		post = new Post();
		tags = "";
		tagList = new ArrayList<Tag>();
	}
	
	public void updateSessionUserPosts() {
		sessionUserPosts = postService.getUserPosts(userMBean.getSessionUser());
	}
	
	private boolean requiredFieldsToSave() {
		return image != null;
	}
	
	public String createForm() {
		newPost();
		return "index.jsf?faces-redirect=true";		
	}
	
	public String editForm(Post p) {
		post = p;
		tagList = (ArrayList<Tag>) tagService.getPostTags(p);
		tags = tagListToString(tagList);
		
		return "edit-post.jsf?faces-redirect=true";
	}
	
	public void create() {	
		
		if(! requiredFieldsToSave()) {
			new ErrorMessage("Required fields are blank");

		} else {
			post.setUser(userMBean.getSessionUser());
	
			try {
				post = postService.add(post, image);
				
				if(! tags.isBlank()) {
					tagList = (ArrayList<Tag>) stringToTagList(tags);
					persistTags();
				}
				
				updateSessionUserPosts();
				newPost();
				
			} catch (PostException e) {
				new ErrorMessage(e.getMessage());
			}
						
		}
		
	}
	
	public String edit() {
		if(! requiredFieldsToSave()) {
			new ErrorMessage("Required fields are blank");
			return null;

		} else {
	
			try {
				post = postService.update(post, image);
				
				if(tags != tagListToString(tagList)) {
					for(Tag tag : tagList) {
						tagService.remove(tag);
					}
					
					tagList = (ArrayList<Tag>) stringToTagList(tags);
					persistTags();
				}
				
				updateSessionUserPosts();
				newPost();
				return "index.jsf?faces-redirect=true";
				
			} catch (PostException e) {
				new ErrorMessage(e.getMessage());
				return null;
			}
						
		}
	}
	
	public void delete(Post p) {
		postService.remove(p);
		updateSessionUserPosts();
	}
	
	private List<Tag> stringToTagList(String str) {
		if(str.isBlank()) return null;
		
		String[] split = str.split(", ");
		ArrayList<Tag> list = new ArrayList<Tag>();
		
		for(String token : split) {
			Tag tag = new Tag();
			tag.setName(token);
			tag.setPost(post);
			
			list.add(tag);
		}
		
		return list;
	}
	
	private String tagListToString(ArrayList<Tag> list) {
		if(list.isEmpty()) return null;
		
		String str = "";
		
		for(Tag tag: list) {
			str += tag.getName() + ", ";
		}
		
		// Remove extra ", " at the end of string
		str = str.substring(0, str.length() - 2);
		
		return str;
	}
	
	private void persistTags() {
		try {
			for(Tag tag : tagList) {
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

	public ArrayList<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(ArrayList<Tag> tagsList) {
		this.tagList = tagsList;
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
