package org.moloshnikov.userdataservice.repository;

import org.moloshnikov.userdataservice.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = {"roles"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.login=:login")
    User getByLogin(@Param("login") String login);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.login=:login")
    int deleteByLogin(@Param("login") String login);
}
