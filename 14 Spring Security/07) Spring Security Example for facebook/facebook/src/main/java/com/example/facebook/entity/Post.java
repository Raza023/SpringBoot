package com.example.facebook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Posts")
public class Post {

    @Id
    @Column(name = "PostID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "Subject", nullable = false, length = 50)
    private String subject;

    @Column(name = "Description", nullable = false, length = 50)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "PostStatus")
    private PostStatusEnum postStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private User user;

}
