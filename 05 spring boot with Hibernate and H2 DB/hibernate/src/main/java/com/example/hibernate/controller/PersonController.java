package com.example.hibernate.controller;

import com.example.hibernate.dao.PersonDao;
import com.example.hibernate.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orm")
public class PersonController {

    @Autowired
    private PersonDao dao;

    @PostMapping("/savePerson")
    public String save(@RequestBody Person person) {
        dao.savePerson(person);
        return "success";
    }

    @GetMapping("/getAll")
    public List<Person> getALlPersons() {
        return dao.getPersons();
    }

}
