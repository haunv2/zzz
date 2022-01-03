package com.service.impl;

import com.model.Authority;
import com.model.User;
import com.repository.AuthorityRepository;
import com.repository.UserRepository;
import com.repository.specification.UserSpecifications;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repo;

    private AuthorityRepository authorityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.repo = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Caching(
            cacheable = @Cacheable(value = "user", key = "#id")
            ,
            put = @CachePut(value = "user", key = "#id")
    )
    public User findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    @CachePut(value =
            "user", key = "#result.id")
    public User save(User obj) {
        Set<Authority> auths = new HashSet<Authority>(); // make new auth list

        if (obj.getUserAuths() != null) { //walk through list
            obj.getUserAuths().forEach(auth -> {
                if (auth.getId() != null) {
                    // here we get reference to authority entity by id
                    auths.add(authorityRepository.findById(auth.getId()).get());
                }
            });
        }
        obj.setUserAuths(auths); // set auth agains
        return repo.saveAndFlush(obj);
    }

    @Override
    @CacheEvict(cacheNames = {"user", "users", "userCount"}, allEntries = true)
    public User deleteById(Long id) {
        User obj = findById(id); // fetch user by id
        obj.setUserAuths(null); // set null for auths, if not set, jpa will clear all 3 tables
        repo.delete(obj); // delete
        return obj;
    }

    @Override
    @Caching(
            cacheable = @Cacheable(value = "users", key = "#page")
            ,
            put = @CachePut(value = "users", key = "#page")
    )
    public List<User> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        System.out.println(repo.count(specs) / 30);
        return repo.count(specs);
    }

    @Override
    public List<User> searchByUser(User u) {
        return repo.findAll(Specification
                .where(UserSpecifications.byUsername(u.getUsername()))
                .or(UserSpecifications.byEmail(u.getEmail()))
                .or(UserSpecifications.byPhone(u.getPhone())));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load username: " + username);

        Optional<User> opt = repo.findOne(UserSpecifications.byUsername(username));
        User u = opt.isPresent() ? opt.get() : null;

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
