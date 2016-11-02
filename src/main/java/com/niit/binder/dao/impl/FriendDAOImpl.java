package com.niit.binder.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.binder.dao.FriendDAO;
import com.niit.binder.model.Friend;

@EnableTransactionManagement
@Repository(value="friendDAO")
public class FriendDAOImpl implements FriendDAO {
	
	Logger log = Logger.getLogger(FriendDAOImpl.class);
	
	@Autowired	//@Autowired annotation provides more fine-grained control over where and how autowiring should be accomplished..
	private SessionFactory sessionFactory;

	/**
	 *  getter/setter method for sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 *   Constructor of FriendDAOImpl...
	 */
	public FriendDAOImpl() { 		
		
	}	
	public FriendDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 *  Declare all CRUD Operations...
	 */
	
	@Transactional
	public boolean save(Friend friend){
		try {
			log.debug("**********Starting of save() method.");
			sessionFactory.getCurrentSession().save(friend);
			log.debug("**********End of save() method.");
			return true;
		} catch (Exception e) {
			log.error("Error occured : " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean update(Friend friend){
		try {
			log.debug("**********Starting of update() method.");
			sessionFactory.getCurrentSession().update(friend);
			log.debug("**********End of update() method.");
			return true;
		} catch (Exception e) {
			log.error("Error occured : " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public Friend get(String userId, String friendId) {
		log.debug("**********Starting of get() method.");
		String hql = "from Friend where userId = '" + userId + "' and friendId = '" + friendId + "'";
		log.debug("hql : " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<Friend> list = (List<Friend>) query.list();
		
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		log.debug("**********End of get() method.");
		return null;
	}
	
	@Transactional
	public List<Friend> getMyFriends(String userId) {
		log.debug("**********Starting of getMyFriends() method.");
		String hql = "from Friend where userId = '" + userId + "' and status = 'A'";
		log.debug("hql : " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<Friend> list = (List<Friend>) query.list();
		log.debug("**********End of getMyFriends() method.");
		return list;
	}
	
	@Transactional
	public List<Friend> getNewFriendRequests(String friendId) {
		log.debug("**********Starting of getNewFriendRequests() method.");
		String hql = "from Friend where userId = '" + friendId + "' and status = 'N'";
		log.debug("hql : " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<Friend> list = (List<Friend>) query.list();
		log.debug("**********End of getNewFriendRequests() method.");
		return list;
	}
	
	@Transactional
	public void setOnline(String userId) {
		log.debug("**********Starting of setOnline() method.");
		String hql = "update Friend set isOnline = 'Y' where userId = '" + userId + "'";
		log.debug("hql : " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		log.debug("**********End of setOnline() method.");
	}
	
	@Transactional
	public void setOffline(String userId) {
		log.debug("**********Starting of setOffline() method.");
		String hql = "update Friend set isOnline = 'N' where userId = '" + userId + "'";
		log.debug("hql : " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		log.debug("**********End of setOffline() method.");
	}		
}