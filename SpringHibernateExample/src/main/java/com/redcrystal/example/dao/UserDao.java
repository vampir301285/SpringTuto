package com.redcrystal.example.dao;



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

	/**
	 * save user
	 */
	@Transactional(readOnly = false)
	public int save(User user) {
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
	public int update(User user) {
		System.out.println("Update user");
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
	public int update(AccessGroup group) {
		System.out.println("Update Group");
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
	public int save(AccessGroup group) {
		sessionFactory.getCurrentSession().save(group);
		return group.getGroupId();
	}

	/**
	 * find access group
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
	 * find user
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
