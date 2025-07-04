package com.example.hibernate.dao;

import com.example.hibernate.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void savePerson(Person person) {
        entityManager.persist(person);
    }

    @Transactional(readOnly = true)
    public List<Person> getPersons() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        cq.from(Person.class);
        return entityManager.createQuery(cq).getResultList();
    }
}

