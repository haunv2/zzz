package com.controller;

import com.jwtConfig.jwtModel.JwtRequest;
import com.jwtConfig.jwtUtil.JwtTokenUtil;
import com.model.ResponseData;
import com.model.User;
import com.model.Ward;
import com.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false,
            paramType = "header", dataTypeClass = String.class,
            example = "Bearer access_token")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray());
        return ResponseEntity.ok(new ResponseData(service.findById(id),
                null, null));
    }

    @GetMapping("/getAll")
    @ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false,
            paramType = "header", dataTypeClass = String.class,
            example = "Bearer access_token")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(new ResponseData(service.findAll(null, page),
                page,
                page < service.count(null).intValue())
        );
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.PUT}, produces = {"application/json"}, consumes = {"application/json"})
    @ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false,
            paramType = "header", dataTypeClass = String.class,
            example = "Bearer access_token")
    @Transactional
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> save(@Valid @RequestBody User obj) {
        obj.setId(null);
        System.out.println("user == null " + obj);

        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

    @PutMapping(value = "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    @ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false,
            paramType = "header", dataTypeClass = String.class,
            example = "Bearer access_token")
    @Transactional
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> update(@Valid @RequestBody User obj, @PathVariable(value = "id") Long id) {
        obj.setId(id);
        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false,
            paramType = "header", dataTypeClass = String.class,
            example = "Bearer access_token")
    @Transactional
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println("begin login");

        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData("error", "Username or password invalid!"), HttpStatus.FORBIDDEN);
        }

        UserDetails userDetail = null;
        try {
            userDetail = ((UserDetailsService) service).loadUserByUsername(jwtRequest.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseData("error", "Username or password invalid!"), HttpStatus.FORBIDDEN);
        }
        System.out.println("login server->" + service.hashCode());

        if (userDetail == null)
            return new ResponseEntity<>(new ResponseData("error", "Username or password invalid!"), HttpStatus.FORBIDDEN);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities()));
        return ResponseEntity.ok(new ResponseData("Bearer ".concat(jwtTokenUtil.generateToken(userDetail)),
                null, null));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody User obj) {
        obj.setId(null);
        List<User> list = service.searchByUser(obj);

        if (service.searchByUser(obj).size() == 0) {
            return ResponseEntity.ok(new ResponseData(service.save(obj),
                    null, null));
        } else {
            User u = list.get(0);
            Map<String, String> errors = new HashMap<>();
            if (obj.getUsername().equalsIgnoreCase(u.getUsername()))
                errors.put("username", "username has exist!");
            if (obj.getEmail().equalsIgnoreCase(u.getEmail()))
                errors.put("email", "user with email has exist!");
            if (obj.getPhone() == u.getPhone())
                errors.put("phone", "user with phone has exist!");
            return ResponseEntity.ok(new ResponseData("error", errors.toString()));
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
