package com.example.offwork.domain.entities;

import com.example.offwork.domain.extras.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class UserEnt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "requestedBy", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("requestedBy")
    private Set<ApplicationEnt> applications = new HashSet<>();

    public UserEnt() {
    }

    public UserEnt(String name, String surname, String username, String email, String password, Role role, Set<ApplicationEnt> applications) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.applications = applications;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<ApplicationEnt> getApplications() {
        return applications;
    }

    public void setApplications(Set<ApplicationEnt> applications) {
        this.applications = applications;
    }
}
