package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "province")
@Getter
@Setter
public class Province implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(value = "id unique province", example = "1")
    private Integer id;

    @Column(name = "_name", length = 50)
    @ApiModelProperty(value = "name of province", example = "HCM")
    private String name;

    @OneToMany(mappedBy = "province")
    @JsonIgnore
    @ApiModelProperty(value = "líst ward of distruct")
    private Set<District> districts;

}