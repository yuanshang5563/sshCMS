package org.ys.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ys.dao.UserDAO;
import org.ys.model.User;

@Component("userDAO")
public class UserDAOImpl implements UserDAO {
//	private HibernateTemplate hibernateTemplate;
//
//	@Autowired
//	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
//		this.hibernateTemplate = hibernateTemplate;
//	}

	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession() throws Exception{
		Session session = sessionFactory.getCurrentSession();
		//Session session = null;
		if(null == session){
			session = sessionFactory.openSession();
		}
		return session;
	}

	@Override
	public void addUser(User user) throws Exception {
		Session session = getSession();
		session.save(user);
	}

	@Override
	public void deleteUserById(String userId) throws Exception {
		String hql = "delete from User where userId=?";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setString(0, userId);
		query.executeUpdate();
	}

	@Override
	public void updateUser(User user) throws Exception {
		Session session = getSession();
		session.update(user);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> pageUserList(String hql,int startNum,int pageSize) throws Exception {
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(startNum);
		query.setMaxResults(pageSize);
		List<User> users = query.list();
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public long pageUserListCount(String hql) throws Exception {
		Session session = getSession();
		Query query = session.createQuery(hql);
		List<Object> userCount = query.list();
		return (Long) userCount.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserById(String userId) throws Exception {
		Session session = getSession();
		Query query = session.createQuery("from User where userId='"+userId+"'");
		List<User> users = query.list();
		if(null == users || users.size() == 0){
			return null;
		}else{
			return users.get(0);
		}
	}
}
