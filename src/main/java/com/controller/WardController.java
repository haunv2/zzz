package com.controller;

import com.model.Province;
import com.model.ResponseData;
import com.model.Ward;
import com.repository.specification.WardSpecifications;
import com.service.WardService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;


@RestController
@RequestMapping(value = "api/ward")
public class WardController {

    private final WardService service;

    @Autowired
    public WardController(WardService wardService) {
        this.service = wardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(new ResponseData(service.findById(id),
                null, null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "district", required = false) Integer district) {
        Specification specs = null;
        if (district != null)
            specs = WardSpecifications.byDistrict(district);
        return ResponseEntity.ok(new ResponseData(service.findAll(specs, page),
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
    public ResponseEntity<?> save(@Valid @RequestBody Ward obj) {
        obj.setId(null);
        obj.setDistrict(null);
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
    public ResponseEntity<?> update(@Valid @RequestBody Ward obj, @PathVariable(value = "id") Integer id) {
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
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(new ResponseData(service.deleteById(id), null, null));
    }
}
