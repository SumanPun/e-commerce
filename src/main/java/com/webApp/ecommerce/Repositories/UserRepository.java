package com.webApp.ecommerce.Repositories;

import com.webApp.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("select u.userId, u.firstName, u.lastName, u.email, r.roleName From User u inner join UserRole ur on u.userId = ur.id inner join Role r on ur.id = r.roleId")
    List<Object[]> getUserAndRole();
}
