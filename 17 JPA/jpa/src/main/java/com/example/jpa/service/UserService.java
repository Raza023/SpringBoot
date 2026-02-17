package com.example.jpa.service;

import com.example.jpa.entity.User;
import com.example.jpa.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @PostConstruct
    public void initDB() {
        List<User> users = new ArrayList<>();
        users.add(new User(111, "x", "IT", 23));
        users.add(new User(675, "y", "IT", 24));
        users.add(new User(432, "z", "CIVIL", 26));
        users.add(new User(88, "p", "IT", 23));
        users.add(new User(765, "q", "GOVT", 20));
        repository.saveAllAndFlush(users);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public List<User> getUserByProfession(String profession) {
        return repository.findByProfession(profession);
    }

    public long getCounts(int age) {
        return repository.countByAge(age);
    }

    public List<User> deleteUser(String name) {
        return repository.deleteByName(name);
    }

    public List<User> findByMultiCondition(String profession, int age) {
        return repository.findByProfessionAndAge(profession, age);
    }

    public List<User> getUsersIgnoreCase(String profession) {
        return repository.findByProfessionIgnoreCase(profession);
    }

    // sort
    public List<User> getUserSort(String field) {
        return repository.findAll(Sort.by(field));
    }

    // pagination
    public Page<User> getPaginatedUser() {
        // Page numbers are 0-indexed; size is 3 records per page
        return repository.findAll(PageRequest.of(0, 3, Sort.by("FieldName")));
    }

    // custom Query
    public List<User> getUsersCustomQuery() {
        return repository.getUsersCustomQuery();
    }

}