package org.moloshnikov.userdataservice.repository;

import org.moloshnikov.userdataservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Role r WHERE r.id=:id")
    int delete(@Param("id") int id);
}
