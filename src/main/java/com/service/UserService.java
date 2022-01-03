package com.service;

import com.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface UserService {
    User findById(Long id);

    User save(User obj);

    User deleteById(Long id);

    List<User> findAll(Specification specs, int page);

    Long count(Specification specs);

    List<User> searchByUser(User u);

}
