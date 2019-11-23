package br.ufrn.imd.artfolio.repository;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.artfolio.model.Tag;

@Stateless	
public class TagRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Tag add(Tag tag) {
		if(tag.getId() == 0) entityManager.persist(tag);
		else entityManager.merge(tag);
		
		return tag;
	}
	
	public void remove(Tag tag) {
		tag = entityManager.find(Tag.class, tag.getId());
		entityManager.remove(tag);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tag> list() {
		try {
			Query query = entityManager.createQuery("SELECT t FROM Tag t");
			return (List<Tag>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Tag> listPostTags(int id) {
		try {
			Query query = entityManager.createQuery("SELECT t FROM Tag t WHERE Post_id = :post_id");
			query.setParameter("post_id", id);
			return (List<Tag>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Tag findById(int id) {
		try {
			Query query = entityManager.createQuery("SELECT t FROM Tag t WHERE id = :id");
			query.setParameter("id", id);
			return (Tag) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
