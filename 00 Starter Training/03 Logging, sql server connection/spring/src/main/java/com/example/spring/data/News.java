package com.example.spring.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "News")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NewsID", unique = true , nullable = false)
    private Long newsID;
    @Column(name = "NewsType", length = 20)
    private String newsType;
    @Column(name = "NewsCategory", length = 20)
    private String newsCategory;

}
