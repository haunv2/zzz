package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(value = "uuid", example = "1")
    private Long id;

    @Column(name = "name", nullable = false)
    @ApiModelProperty(value = "category name", example = "hiensacs")
    private String name;

    @OneToMany
    @JsonBackReference
    private Set<Product> car_Products;
}