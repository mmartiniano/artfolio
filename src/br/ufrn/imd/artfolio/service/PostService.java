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
	
	@Inject
	private PostRepository postRepository;
	
	public Post add(Post post, Part image) throws PostException {
		if(image == null)
			throw new PostException("Image not found");
		
		String tempName = "artfolio_image";		
		post.setImage(tempName);
		
		post = postRepository.add(post);
		
		String path = "C:/storage/post/";
		String rename = Integer.toString(post.getId());
		
		UploadService uploadService = new UploadService(image);
		
		String uploadPath = uploadService.uploadFile(path, rename);
		
		if(uploadPath == null) 
			throw new PostException("Could not save image");
		
		String accessPath = "http://localhost:8000/post/";
		post.setImage(accessPath + uploadPath);
		
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
