package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ward")
@Getter
@Setter
public class Ward implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    @ApiModelProperty(name = "id", value = "id unique ward", example = "1")
    private Integer id;

    @Basic
    @Column(name = "_name")
    @ApiModelProperty(name = "name", value = "name of ward", example = "Quận 9")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    @ApiModelProperty(name = "district", value = "null", example = "null")
    private District district;

}
