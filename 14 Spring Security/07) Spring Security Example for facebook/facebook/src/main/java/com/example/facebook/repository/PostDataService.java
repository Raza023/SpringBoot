package com.example.facebook.repository;

import com.example.facebook.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDataService extends JpaRepository<Post, Integer> {



}
