package com.repository.specification.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductFilter {
    @ApiModelProperty(value = "year of product", example = "2021")
    private Integer year;

    @Pattern(regexp = "(^\\bspring\\b$)|(^\\bfall\\b$)|(^\\bsummer\\b$)|^\\bwinter\\b$", message = "season invalid")
    @Size(min = 4, max = 6)
    @ApiModelProperty(value = "season of product", example = "summer")
    private String season;

    @ApiModelProperty(value = "min price", example = "0")
    private Integer minPrice = 0;

    @ApiModelProperty(value = "max price", example = "12000000")
    private Integer maxPrice = 12000000;

    @Override
    public String toString() {
        return "ProductFilter{" +
                "year=" + year +
                ", season='" + season + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
