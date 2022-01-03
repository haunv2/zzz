package com.controller;

import com.model.Authority;
import com.model.Category;
import com.model.ResponseData;
import com.model._Address;
import com.service.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/category")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.findById(id),
                null, null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(new ResponseData(service.findAll(null, page),
                page,
                page < service.count(null).intValue())
        );
    }

    @PostMapping(value = "/", produces = {"application/json"}, consumes = {"application/json"})
    @ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false,
            paramType = "header", dataTypeClass = String.class,
            example = "Bearer access_token")
    @Transactional
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> save(@Valid @RequestBody Category obj) {
        obj.setId(null);
        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

    @PutMapping(value = "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    @ApiImplicitParam(name = "Authorization", value = "Access Token",
            required = true, allowEmptyValue = false,
            paramType = "header", dataTypeClass = String.class,
            example = "Bearer access_token")
    @Transactional
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    public ResponseEntity<?> update(@Valid @RequestBody Category obj, @PathVariable(value = "id") Long id) {
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
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
    }

}
