package com.ideasync.ideasyncbackend.userrole;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    UserRole findUserRoleByRoleName(String roleName);
}
