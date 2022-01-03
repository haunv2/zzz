package com.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "username", nullable = false)
    @ApiModelProperty(value = "username for login", example = "user")
    @NotNull
    String username;
    @Column(name = "password", nullable = false)
    @ApiModelProperty(value = "password for", example = "user")
    @NotNull
    String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(value = "id unique for each user", example = "1")
    private Long id;
    @Column(name = "fullname")
    @ApiModelProperty(value = "name of user", example = "user")
    @NotNull
    private String name;
    @Column(name = "email", nullable = false)
    @ApiModelProperty(value = "email of user", example = "user@gmail.com")
    @NotNull
    private String email;

    @Column(name = "phone", length = 12)
    @ApiModelProperty(value = "phone of user", example = "0965901542")
    private Integer phone;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = {
            @JoinColumn(name = "user_id")
    }, inverseJoinColumns = {@JoinColumn(name = "auth_id")})
    @ApiModelProperty(value = "user roles")
    @NotNull
    private Set<Authority> userAuths;
}