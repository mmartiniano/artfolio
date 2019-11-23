package br.ufrn.imd.artfolio.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import br.ufrn.imd.artfolio.exception.UserException;
import br.ufrn.imd.artfolio.model.User;
import br.ufrn.imd.artfolio.repository.UserRepository;

@Stateless
public class UserService {
	
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
			String path = "C:/storage/user/";
			String rename = Integer.toString(user.getId());
			
			UploadService uploadService = new UploadService(picture);
			
			String uploadPath = uploadService.uploadFile(path, rename);
			
			
			if(uploadPath != null) {
				String accessPath = "http://localhost:8000/user/";
				user.setImage(accessPath + uploadPath);
			}
		}
		
		userRepository.update(user);
		
		return user;
	}

}
