package com.jwtConfig;

import com.repository.UserRepository;
import com.repository.specification.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load username: " + username);

        Optional<com.model.User> opt = repo.findOne(UserSpecifications.byUsername(username));
        com.model.User u = opt.isPresent() ? opt.get() : null;

        if (u != null) {
            List<GrantedAuthority> roles = new ArrayList<>();
            u.getUserAuths().forEach(auth -> {
                roles.add(() -> {
                    return auth.getName();
                });
            });
            return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(),
                    roles);
        } else
            throw new UsernameNotFoundException(username + "Not found");
    }
}
