package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(value = "id unique dítrict", example = "1")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    @ApiModelProperty(value = "product name", required = true, example = "Kahav")
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    @ApiModelProperty(value = "product detail", example = "Product with mmmm!")
    private String description;

    @Column(name = "price", nullable = false)
    @ApiModelProperty(value = "product price", required = true, example = "141")
    @Min(value = 0)
    @NotNull
    private Integer price;

    @Column(name = "image", nullable = false)
    @NotNull
    @ApiModelProperty(value = "product image('path')", required = true, example = "https://asd.a/ava.png")
    private String image;

    @Column(name = "year", nullable = false)
    @NotNull
    @ApiModelProperty(value = "year release", example = "2021")
    private Integer year;

    @Column(name = "season", nullable = false)
    @NotNull
    @ApiModelProperty(value = "season release", required = true, example = "SUMMER")
    private String season;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category product_Cat;
}