package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "district")
@Getter
@Setter
public class District implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "id", value = "id unique dítrict", example = "1")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "_name", length = 100)
    @ApiModelProperty(name = "name", value = "name of dítrict", example = "Bắc Cạn")
    private String name;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    @ApiModelProperty(name = "wards", value = "líst ward of district")
    private Set<Ward> wards;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(name = "district", value = "null", example = "null")
    private Province province;

}