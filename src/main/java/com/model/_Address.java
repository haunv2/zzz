package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.security.PermitAll;
import javax.persistence.*;
import java.io.Serializable;

@ApiModel(
        value = "address model",
        description = "address params"
)
@Entity
@Table(name = "address")
@Setter
@Getter
public class _Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(name = "id", value = "uuid", example = "1")
    private Long id;

    @Column(name = "province_id", nullable = false)
    @ApiModelProperty(name = "provinceId", value = "id province", example = "1")
    private Integer provinceId;

    @Column(name = "district_id", nullable = false)
    @ApiModelProperty(name = "districtId", value = "id district", example = "1")
    private Long districtId;

    @Column(name = "ward_id", nullable = false)
    @ApiModelProperty(name = "wardId", value = "id ward", example = "1")
    private Long wardId;

    @Column(name = "detail", nullable = false, length = 500)
    @ApiModelProperty(name = "detail", value = "address detail", example = "31 Laso street")
    private String detail;

    @Column(name = "user_id", nullable = false)
    @ApiModelProperty(name = "user id", value = "id for user", example = "1")
    private Long userId;

}