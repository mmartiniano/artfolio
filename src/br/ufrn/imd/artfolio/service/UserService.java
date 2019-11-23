package br.ufrn.imd.artfolio.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import br.ufrn.imd.artfolio.exception.UserException;
import br.ufrn.imd.artfolio.model.User;
import br.ufrn.imd.artfolio.repository.UserRepository;

@Stateless
public class UserService {
	
	private final String ACCESS_PATH =  "http://localhost:8000/user/";
	private final String STORAGE_PATH = "C:/storage/user/";
	
	@Inject
	private UserRepository userRepository;
	
	public User authenticateByUsername(User user) throws UserException {
		User userDB = userRepository.getByUsernameAndPassword(user.getUsername(), user.getPassword());
		
		if (userDB == null)
			throw new UserException("Credentials do not match");
		
		return userDB;
	}
	
	public User add(User user) throws UserException {
		if(! userRepository.availableUniqueFields(user))
			throw new UserException("Credentials already taken");
		
		return userRepository.add(user);
		
	}
	
	public User update(User user, Part picture) throws UserException {
		if(! userRepository.availableUniqueFields(user))
			throw new UserException("Credentials already taken");
		
		if(picture != null) {
			
			String rename = Integer.toString(user.getId());
			
			UploadService uploadService = new UploadService(picture);
			
			String uploadPath = uploadService.uploadFile(STORAGE_PATH, rename);
			
			
			if(uploadPath != null) 
				user.setImage(ACCESS_PATH + uploadPath);
		}
		
		userRepository.update(user);
		
		return user;
	}

}
