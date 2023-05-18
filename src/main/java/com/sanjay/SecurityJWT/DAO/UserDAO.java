package com.sanjay.SecurityJWT.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanjay.SecurityJWT.Entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String Username);
}
