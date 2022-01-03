package com.controller;

import com.model.District;
import com.model.ResponseData;
import com.repository.specification.DistrictSpecifications;
import com.service.DistrictService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/district")
public class DistrictController {
    private final DistrictService service;

    @Autowired
    public DistrictController(DistrictService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(new ResponseData(service.findById(id),
                null, null));
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "province", required = false) Integer province) {
        Specification specs = null;
        if (province != null)
            specs = DistrictSpecifications.byProvince(province);
        return ResponseEntity.ok(new ResponseData(service.findAll(specs, page),
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
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> save(@Valid @RequestBody District obj) {
        obj.setId(null);
        return ResponseEntity.ok(new ResponseData(service.save(obj),
                null, null));
    }

    @PutMapping("/{id}")
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
