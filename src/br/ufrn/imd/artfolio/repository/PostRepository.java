package br.ufrn.imd.artfolio.repository;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.artfolio.model.Post;

@Stateless	
public class PostRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Post add(Post post) {
		if(post.getId() == 0) entityManager.persist(post);
		else entityManager.merge(post);
		
		return post;
	}
	
	public void remove(Post post) {
		post = entityManager.find(Post.class, post.getId());
		entityManager.remove(post);
	}
	
	public void update(Post post) {
		String jpql = "UPDATE Post SET image = :image, caption = :caption WHERE id = :id";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("image", post.getImage());
		query.setParameter("caption", post.getCaption());
		query.setParameter("id", post.getId());
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> list() {
		try {
			Query query = entityManager.createQuery("SELECT p FROM Post p");
			return (List<Post>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> listUserPosts(int id) {
		try {
			Query query = entityManager.createQuery("SELECT p FROM Post p WHERE User_id = :user_id ORDER BY id DESC");
			query.setParameter("user_id", id);
			return (List<Post>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Post findById(int id) {
		try {
			Query query = entityManager.createQuery("SELECT p FROM Post p WHERE id = :id");
			query.setParameter("id", id);
			return (Post) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	

}
