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

    @GetMapping("/getAll")
    public List<Person> getALlPersons() {
        return dao.getPersons();
    }

    @GetMapping("/findById/{id}")
    public Person findById(@PathVariable Long id) {
        return dao.findById(id);
    }

    @PostMapping("/savePerson")
    public String save(@RequestBody Person person) {
        dao.savePerson(person);
        return "person successfully saved.";
    }

    @PutMapping("/updatePerson")
    public String updatePerson(@RequestBody Person person) {
        boolean updated = dao.updatePerson(person);
        if (updated) {
            return "person updated successfully.";
        } else {
            return "person not found.";
        }
    }

    @DeleteMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable Long id) {
        boolean deleted = dao.deletePerson(id);
        if (deleted) {
            return "person deleted successfully.";
        } else {
            return "person not found.";
        }
    }

}
