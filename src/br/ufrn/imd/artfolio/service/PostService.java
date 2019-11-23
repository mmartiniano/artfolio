package br.ufrn.imd.artfolio.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import br.ufrn.imd.artfolio.exception.PostException;
import br.ufrn.imd.artfolio.model.Post;
import br.ufrn.imd.artfolio.model.User;
import br.ufrn.imd.artfolio.repository.PostRepository;

@Stateless
public class PostService {
	
	private final String ACCESS_PATH =  "http://localhost:8000/post/";
	private final String STORAGE_PATH = "C:/storage/post/";
	
	@Inject
	private PostRepository postRepository;
	
	public Post add(Post post, Part image) throws PostException {
		if(image == null)
			throw new PostException("Image not found");
		
		String tempName = "artfolio_image";		
		post.setImage(tempName);
		
		post = postRepository.add(post);
		
		String rename = Integer.toString(post.getId());
		
		UploadService uploadService = new UploadService(image);
		
		String uploadPath = uploadService.uploadFile(STORAGE_PATH, rename);
		
		if(uploadPath == null) 
			throw new PostException("Could not save image");
		
		post.setImage(ACCESS_PATH + uploadPath);
		
		postRepository.update(post);
		
		return post;
	}
	
	public Post update(Post post, Part image) throws PostException {
		if(image == null)
			throw new PostException("Image not found");
		
		String rename = Integer.toString(post.getId());
		
		UploadService uploadService = new UploadService(image);
		
		String uploadPath = uploadService.uploadFile(STORAGE_PATH, rename);
		
		if(uploadPath == null) 
			throw new PostException("Could not save image");
		
		post.setImage(ACCESS_PATH + uploadPath);
		
		postRepository.update(post);
		
		return post;
	}	
	
	public void remove(Post post) {
		postRepository.remove(post);
	}
	
	public List<Post> getUserPosts(User user) {
		return postRepository.listUserPosts(user.getId());
	}
	
	

}
