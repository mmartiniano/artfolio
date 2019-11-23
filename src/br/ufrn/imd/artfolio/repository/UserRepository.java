package br.ufrn.imd.artfolio.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.artfolio.model.User;

@Stateless	
public class UserRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public User add(User user) {
		if(user.getId() == 0) entityManager.persist(user);
		else entityManager.merge(user);
		return user;
	}
	
	public void remove(User user) {
		user = entityManager.find(User.class, user.getId());
		entityManager.remove(user);
	}
	
	public User update(User user) {
		String jpql = "UPDATE User u SET username = :username, name = :name, email = :email, password = :password, image = :image, alias = :alias, slogan = :slogan, bio = :bio WHERE id = :id";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("username", user.getUsername());
		query.setParameter("name", user.getName());
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		query.setParameter("image", user.getImage());
		query.setParameter("alias", user.getAlias());
		query.setParameter("slogan", user.getSlogan());
		query.setParameter("bio", user.getBio());
		query.setParameter("id", user.getId());
		int rowsAffected = query.executeUpdate();
		
		if(rowsAffected > 0) 
			return findById(user.getId());
		else
			return user;
	}

	
	public boolean availableUniqueFields(User user) {
		try {
			Query query = entityManager.createQuery("SELECT u FROM User u WHERE (username = :username OR email = :email) AND id <> :id");
			query.setParameter("username", user.getUsername());
			query.setParameter("email", user.getEmail());
			query.setParameter("id", user.getId());
			User userDB = (User) query.getSingleResult();
			
			return userDB == null;
		} catch (NoResultException e) {
			return true;
		}
	}
	
	
	public User getByUsernameAndPassword(String username, String password) {
		try {
			Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public User findById(int id) {
		try {
			Query query = entityManager.createQuery("SELECT u FROM User u WHERE id = :id");
			query.setParameter("id", id);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	

}
