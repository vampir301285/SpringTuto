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
            filters.forEach((k, v) -> {
                System.out.println("Key: " + k + ", Value: " + v);
                Expression<String> filterKeyExp = r.get(k).as(String.class);
                String filterValue = "%" + ((String) v).toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(filterKeyExp), filterValue));
            });
            q.where(predicates.toArray(new Predicate[] {}));
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
}