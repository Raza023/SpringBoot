package com.example.hibernate.dao;

import com.example.hibernate.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// This annotation indicates that this class is a DAO component. And it is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
// It is a specialization of the @Component annotation, allowing for implementation classes to be autodetected through classpath scanning.
// The @Repository annotation is used to indicate that the class is a Data Access Object (DAO) and is responsible for encapsulating the logic required to access data sources.
// It also provides additional benefits such as exception translation, which converts database-related exceptions into Spring's DataAccessException hierarchy, making it easier to handle exceptions in a consistent manner across the application.
// This helps in separating the data access logic from the business logic, promoting a cleaner architecture and better maintainability of the codebase.     
@Repository    
public class PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Person> getPersons() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        cq.from(Person.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional(readOnly = true)
    public Person findById(Long id) {
        return entityManager.find(Person.class, id);
    }

    @Transactional
    public void savePerson(Person person) {
        entityManager.persist(person);
    }

    @Transactional
    public boolean updatePerson(Person person) {
        // If id is null, treat as new record (GenerationType.AUTO works with null)
        Person existing = null;
        if (person.getId() != null) {
            existing = entityManager.find(Person.class, person.getId());
        }
        if (existing != null) {
            entityManager.merge(person);
            return true;
        } else {
            person.setId(null); // Let DB auto-generate id if using GenerationType.AUTO
            entityManager.persist(person);
            return false;
        }
    }

    @Transactional
    public boolean deletePerson(Long id) {
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
            entityManager.remove(person);
            return true;
        }
        return false;
    }

}
