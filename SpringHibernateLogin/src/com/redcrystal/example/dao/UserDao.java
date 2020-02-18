package com.redcrystal.example.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.redcrystal.example.entities.AccessGroup;
import com.redcrystal.example.entities.User;

/**
 * the annotation Scope to enable annotation scan in the Spring config
 * 
 * @author mngo
 * 
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(readOnly = true)
public class UserDao extends AbstractDao {

	/** Logger object */
	private static final Logger LOGGER = Logger.getLogger(AbstractDao.class);

	/**
	 * save user
	 */
	@Transactional(readOnly = false)
	public int saveUser(User user) {
		LOGGER.debug("saveUser(User user)");
		sessionFactory.getCurrentSession().save(user);
		return user.getUserId();
	}

	/**
	 * update user
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateUser(User user) {
		LOGGER.debug("updateUser(User user)");
		sessionFactory.getCurrentSession().update(user);
		return user.getUserId();
	}

	/**
	 * update group
	 * 
	 * @param group
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateAccessGroup(AccessGroup group) {
		sessionFactory.getCurrentSession().update(group);
		return group.getGroupId();
	}

	/**
	 * save group
	 * 
	 * @param group
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveAccessGroup(AccessGroup group) {
		sessionFactory.getCurrentSession().save(group);
		return group.getGroupId();
	}

	/**
	 * finds access group
	 * 
	 * @param name
	 * @return
	 */
	public AccessGroup findAccessGroupByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccessGroup.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.setMaxResults(1);
		return (AccessGroup) criteria.uniqueResult();
	}

	/**
	 * finds user
	 * 
	 * @param username
	 * @return
	 */
	public User findUserByUsername(String username) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.setMaxResults(1);
		return (User) criteria.uniqueResult();
	}

}
