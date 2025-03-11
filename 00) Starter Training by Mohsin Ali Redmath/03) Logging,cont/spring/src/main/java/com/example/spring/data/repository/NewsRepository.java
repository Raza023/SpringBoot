package com.example.spring.data.repository;

import com.example.spring.data.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAll();

    Optional<News> findById(Long id);


}
