package com.guilherme.bitWatch.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmailIgnoreCase(String username);

    UserDetails findByEmail(String email);

    boolean existsByEmailAndIsActiveUserTrue(String email);

}
