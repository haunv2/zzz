package com.controller;

import com.model.*;
import com.repository.specification.AuthoritySpecifications;
import com.service.AuthorityService;
import com.service.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/authority")
public class AuthorityController {
    private final AuthorityService service;

    @Autowired
    public AuthorityController(AuthorityService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    @ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false,
            paramType = "header", dataTypeClass = String.class,
            example = "Bearer access_token")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(new ResponseData(service.findAll(null, page),
                page,
                page < service.count(null).intValue())
        );
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    @PreAuthorize("hasAnyAuthority('admin')")
//    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
//        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
//    }
}
