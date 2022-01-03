package com.controller;

import com.model.Category;
import com.model.Product;
import com.model.ResponseData;
import com.repository.specification.ProductSpecifications;
import com.repository.specification.model.ProductFilter;
import com.service.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping(value = "api/product")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
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
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> save(@Valid @RequestBody Product obj) {
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
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> update(@Valid @RequestBody Product obj, @PathVariable(value = "id") Long id) {
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
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@Valid @RequestBody ProductFilter filter, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        System.out.println("filter->" + filter);

        Specification specs = ProductSpecifications.filter(filter);
        return ResponseEntity.ok(new ResponseData(service.filter(specs, page), page, page < service.count(specs)));
    }

    @GetMapping("search")
    public ResponseEntity<?> filter(@RequestParam("q") String search, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        System.out.println("search->" + search);
        Specification specs = ProductSpecifications.byName(search);
        return ResponseEntity.ok(new ResponseData(service.filter(specs, page), page, page < service.count(specs)));
    }
}