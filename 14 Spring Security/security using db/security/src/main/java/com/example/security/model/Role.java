package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// Lombok's @Data adds equals() and hashCode() based on all fields,
// including the users set may cause StackOverflowError in bidirectional relationships.
@EqualsAndHashCode(exclude = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @Column(name = "RoleID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name = "Role", unique = true, nullable = false)   //unique because I don't want duplicate columns in this table.
    private String role;

    @JsonIgnore  //Prevent infinite recursion with @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
