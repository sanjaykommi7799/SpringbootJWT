package com.sanjay.SecurityJWT.Service;

import java.util.Optional;

import com.sanjay.SecurityJWT.Entity.User;

public interface UserService {
 public Integer save(User user);
 Optional<User> findByUsername(String username);
}
