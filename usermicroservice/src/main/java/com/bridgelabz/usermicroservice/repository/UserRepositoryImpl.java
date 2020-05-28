package com.bridgelabz.usermicroservice.repository;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.usermicroservice.dto.UserLoginInformation;
import com.bridgelabz.usermicroservice.dto.UserUpdatePassword;
import com.bridgelabz.usermicroservice.entity.User;
@Repository
public class UserRepositoryImpl implements UserRepository
{

	@PersistenceContext
	private EntityManager entityManager;
			
	@Override	
	@Transactional
	@Modifying
	public User registrationSave(User information) 
	{
            Session session = entityManager.unwrap(Session.class);
            ((Session) session).saveOrUpdate(information);
             return information;
	}
	@Override
	public Optional<User> loginValidate(UserLoginInformation information) 
	{
		String email = information.getEmail();
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM User where email =:email").setParameter("email", email).uniqueResultOptional();

	}



	@Override
	public boolean verify(Long id) 
	{
		Session session = entityManager.unwrap(Session.class);
    	TypedQuery<User> q = session.createQuery("update User set is_verified =:verify where user_id=:id");
		q.setParameter("verify", true);
		q.setParameter("id", id);
		int status = q.executeUpdate();
		if (status > 0)
		{
			return true;

		} else 
		{
			return false;
		}

	}

	@Override
	public boolean updatePassword(UserUpdatePassword updatePassword, Long id) 
	{
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("update User set password =:p" + " " + " " + "where id=:i");

		q.setParameter("p", updatePassword.getNewPassword());
		q.setParameter("i", id);
	    //q.setParameter("pass", updatePassword.getOldPassword());
		int status = q.executeUpdate();
		if (status > 0) 
		{
			return true;

		} 
		else
		{
			return false;
		}
	}

	@Override
	public Optional<User> getUser(String email)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM User where email =:email").setParameter("email", email).uniqueResultOptional();
	}

	@Override
	public boolean updateRandomPassword(String password, long id) 
	{
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("update User set password =:p" + " " + " " + "where id=:i");
		q.setParameter("p", password);
		q.setParameter("i", id);
		int status = q.executeUpdate();
		if (status > 0) 
		{
			return true;

		} 
		else 
		{
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<User> getUserById(long id) {
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM User where userId =:id").setParameter("id", id).uniqueResultOptional();
	}
	
}
