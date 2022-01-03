package com.jwtConfig.jwtModel;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Setter
@Getter
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    @ApiModelProperty(value = "username for login", example = "user")
    private String username;

    @ApiModelProperty(value = "password for", example = "123432")
    @Min(value = 6)
    private String password;

    @Override
    public String toString() {
        return "JwtRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
