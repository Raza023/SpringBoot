package com.example.logging.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "LogEntity")
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "LoggingTitle")
    private String loggingTitle;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", loggingTitle='" + loggingTitle + '\'' +
                '}';
    }
}
