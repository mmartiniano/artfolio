package br.ufrn.imd.artfolio.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import br.ufrn.imd.artfolio.exception.UserException;
import br.ufrn.imd.artfolio.model.User;
import br.ufrn.imd.artfolio.service.UserService;
import br.ufrn.imd.artfolio.view.ErrorMessage;

		
@Named("userMBean")
@SessionScoped
public class UserMBean implements Serializable {

	@Inject
	private UserService userService;

	@Inject
	private PostMBean postMBean;
	
	
	private User user;
	
	private User sessionUser;
	
	private Part picture;
	
	public UserMBean() {
		user = new User();
	}
	
	private void newUser() {
		user = new User();
	}
	
	public String logInForm() {
		newUser();
		return "index.jsf?faces-redirect=true";
	}
	
	public String signUpForm() {
		newUser();
		return "signup.jsf?faces-redirect=true";
	}
	
	public String editForm() {
		user = sessionUser;
		return "edit-profile.jsf?faces-redirect=true";
	}
	
	private boolean requiredFieldsToAuthenticate() {
		return !user.getUsername().isBlank() || !user.getPassword().isBlank();
	}
	
	private boolean requiredFieldsToSave() {
		return !user.getName().isBlank() || !user.getUsername().isBlank() || !user.getEmail().isBlank() || !user.getPassword().isBlank();
	}
	
	public String logIn() {
		
		if(! requiredFieldsToAuthenticate()) {
			new ErrorMessage("Required fields are blank");
			return null;
		}
		
		try {
			sessionUser = userService.authenticateByUsername(user);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionUser", sessionUser);
			postMBean.updateSessionUserPosts();
			
			return "dashboard/index.jsf?faces-redirect=true";
		} catch (UserException e) {
			new ErrorMessage(e.getMessage());
			return null;
		}
		
	}
	
	public String signUp() {
		
		if(! requiredFieldsToSave()) {
			new ErrorMessage("Required fields are blank");
			return null;
		}
		
		try {
			sessionUser = userService.add(user);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionUser", sessionUser);
			postMBean.updateSessionUserPosts();
			newUser();
			
			return "dashboard/index.jsf?faces-redirect=true";
		} catch (UserException e) {
			new ErrorMessage(e.getMessage());
			return null;
		}
	}
	
	public String logOut() {
		sessionUser = null;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionUser", sessionUser);
		return "index?faces-redirect=true";			
	}
	
	public String editSessionUser() {
		
		if(! requiredFieldsToSave()) {
			new ErrorMessage("Required fields are blank");
			return null;
		}		
		
		try {
			sessionUser = userService.update(user, picture);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionUser", sessionUser);
			
			return "dashboard/index.jsf?faces-redirect=true";	
			
		} catch (UserException e) {
			new ErrorMessage(e.getMessage());
			user = sessionUser;
			return null;
		}
		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

	public Part getPicture() {
		return picture;
	}

	public void setPicture(Part picture) {
		this.picture = picture;
	}

}
