package com.ideasync.ideasyncbackend.userrole;

import jakarta.persistence.*;
import com.ideasync.ideasyncbackend.user.User;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "userrole")
public class UserRole {

  @Id @GeneratedValue
  @JdbcTypeCode(Types.VARCHAR)
  private UUID id;

  @OneToMany(mappedBy = "userRole", cascade = {CascadeType.MERGE})
  private List<User> users;

  @Column(name = "roleName", nullable = false)
  private String roleName;

  // Constructors, getters, and setters

  public UUID getId() {
    return id;
  }

  public List<User> getUsers() {
    return users;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
}

