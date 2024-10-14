package com.example.hibernate.dao;

import com.example.hibernate.model.Person;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Repository
@Transactional
public class PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        if (ObjectUtils.isEmpty(sessionFactory)) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public void savePerson(Person person) {
        getSession().save(person);
    }

    @SuppressWarnings("unchecked")
    public List<Person> getPersons() {
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        return getSession().createQuery(cb.createQuery(Person.class).select(cq.from(Person.class))).getResultList();
    }

}
