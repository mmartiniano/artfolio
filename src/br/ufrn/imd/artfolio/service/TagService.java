package br.ufrn.imd.artfolio.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.artfolio.exception.PostException;
import br.ufrn.imd.artfolio.model.Tag;
import br.ufrn.imd.artfolio.model.Post;
import br.ufrn.imd.artfolio.repository.TagRepository;

@Stateless
public class TagService {
	
	@Inject
	private TagRepository tagRepository;
	
	public Tag add(Tag tag) throws PostException {
		tag = tagRepository.add(tag);
		
		if(tag == null)
			throw new PostException("Failed to save tag");
		
		return tag;
			
	}
	
	public void remove(Tag tag) {
		tagRepository.remove(tag);
	}
	
	public List<Tag> getPostTags(Post post) {
		return tagRepository.listPostTags(post.getId());
	}
	
	

}
