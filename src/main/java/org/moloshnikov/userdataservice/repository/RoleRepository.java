package org.moloshnikov.userdataservice.repository;

import org.moloshnikov.userdataservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
