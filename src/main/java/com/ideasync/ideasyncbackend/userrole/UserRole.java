package com.ideasync.ideasyncbackend.userrole;

import jakarta.persistence.*;
import com.ideasync.ideasyncbackend.user.User;

import java.util.List;

@Entity
@Table(name = "userrole")
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "userRole", cascade = {CascadeType.MERGE})
  private List<User> users;

  @Column(name = "roleName", nullable = false)
  private String roleName;

  // Constructors, getters, and setters

  public Long getId() {
    return id;
  }

  public List<User> getUsers() {
    return users;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
}

