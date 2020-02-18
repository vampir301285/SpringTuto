package com.redcrystal.example.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(readOnly = true)
public class AbstractDao {

	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * Hibernate template. The introduction came early in Hibernate's
	 * development and prior to Hibernate 3.
	 */
	protected HibernateTemplate hibernateTemplate;

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * Find an entity in the DB.
	 * 
	 * @param <T>
	 *            the entity type
	 * @param type
	 *            the entity
	 * @param id
	 *            the id
	 * @return the Entity
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> type, Serializable id) {
		return (T) sessionFactory.getCurrentSession().get(type, id);
	}

	/**
	 * Find all entities of the specified type in the DB.
	 * 
	 * @param <T>
	 *            Entity class corresponding to the table.
	 * @param type
	 *            The Entity class we're trying to sort
	 * @param orderby
	 *            the SimpleAttribute to sort by
	 * @param order
	 *            sort order direction
	 * @return A sorted result list.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> type, String orderby, String order) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
		if (order.equalsIgnoreCase("ASC")) {
			criteria.addOrder(Order.asc(orderby));
		} else {
			criteria.addOrder(Order.desc(orderby));
		}
		return criteria.list();
	}

}
