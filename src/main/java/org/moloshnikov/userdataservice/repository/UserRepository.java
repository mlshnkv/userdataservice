package org.moloshnikov.userdataservice.repository;

import org.moloshnikov.userdataservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
