package com.example.scheduler.service;

import com.example.scheduler.dao.UserDao;
import com.example.scheduler.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    //Schedule a job to add data to db. (every 5 seconds.)
    @Scheduled(fixedRate = 5000)
    private void addToDBJob() {
        User user = new User();
        user.setName("User"+new Random().nextInt(12342));
        dao.save(user);
        System.out.println("1 user added to db: ");
        System.out.println(user);
    }

    //Schedule a job to fetch data from db. (every 15 seconds.)
    @Scheduled(fixedRate = 15000)
    private void fetchFromDBJob() {
        List<User> users = dao.findAll();
        System.out.println("We have "+users.size()+" users in db.");
    }

}
