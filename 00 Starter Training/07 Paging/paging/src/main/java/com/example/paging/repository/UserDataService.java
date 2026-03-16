package com.example.paging.repository;

import com.example.paging.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataService extends JpaRepository<User, Long> {

    Page<User> findByOrderByIdDesc(Pageable pageable);

    Page<User> findByNameLikeIgnoreCaseOrderByIdAsc(Pageable pageable, String name);
}
