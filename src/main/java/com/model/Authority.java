package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "authority")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ApiModelProperty(value = "uuid", example = "1")
    private Long id;

    @Column(name = "name", nullable = false)
    @ApiModelProperty(value = "role name", example = "admin")
    private String name;

    @ManyToMany(mappedBy = "userAuths", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ApiModelProperty(value = "list users with current role", example = "null")
    private Set<User> authUsers;
}