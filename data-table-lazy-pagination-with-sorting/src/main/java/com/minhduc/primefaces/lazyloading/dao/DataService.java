package com.minhduc.primefaces.lazyloading.dao;

import org.fluttercode.datafactory.impl.DataFactory;
import org.primefaces.model.SortOrder;

import com.minhduc.primefaces.lazyloading.enity.Employee;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum DataService {

    INSTANCE; // singleton of Enum

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("employee-unit");

    private final int MAX_ROWS = 10000;

    DataService() {
        // persisting some data in database
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        DataFactory dataFactory = new DataFactory();
        for (int i = 1; i < MAX_ROWS; i++) {
            Employee employee = new Employee();
            employee.setName(dataFactory.getName());
            employee.setPhoneNumber(String.format("%s-%s-%s", dataFactory.getNumberText(3), dataFactory.getNumberText(3), dataFactory.getNumberText(4)));
            employee.setAddress(dataFactory.getAddress() + "," + dataFactory.getCity());
            employee.setEmail(dataFactory.getEmailAddress());
            em.persist(employee);
        }

        em.getTransaction().commit();
        em.close();
    }

    public List<Employee> getEmployeeList(int start, int size, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> q = cb.createQuery(Employee.class);
        Root<Employee> r = q.from(Employee.class);
        CriteriaQuery<Employee> select = q.select(r);
        if (sortField != null) {
            q.orderBy(sortOrder == SortOrder.DESCENDING ? cb.asc(r.get(sortField)) : cb.desc(r.get(sortField)));
        }
        if (filters != null && !filters.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            filters.forEach((key, value) -> {
                if (value == null) {
                    return; // only skips this iteration.
                }
                System.out.println("Key: " + key + ", Value: " + value);
                Expression<String> filterKeyExp = r.get(key).as(String.class);
                String filterValue = "%" + ((String) value).toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(filterKeyExp), filterValue));
            });
            if (predicates.size() > 0) {
                q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }

        TypedQuery<Employee> query = em.createQuery(select);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Employee> list = query.getResultList();
        return list;
    }

    public int getEmployeeTotalCount() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("Select count(e.id) From Employee e");
        return ((Long) query.getSingleResult()).intValue();
    }

    public int getFilteredRowCount(Map<String, Object> filters) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }

                Expression<String> expr = root.get(field).as(String.class);
                Predicate p = cb.like(cb.lower(expr), "%" + value.toString().toLowerCase() + "%");
                predicates.add(p);
            }
            if (predicates.size() > 0) {
                criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        Long count = em.createQuery(select).getSingleResult();
        return count.intValue();
    }
}