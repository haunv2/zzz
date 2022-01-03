package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "result (error || success)", example = "error")
    private String result;

    @ApiModelProperty(value = "data for     request success")
    private Object data;

    @ApiModelProperty(value = "message if request is error", example = "Invalid argument")
    private String message;

    private Integer currentPage;
    private Boolean hasNextPage;

    public ResponseData(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseData(Object data, Integer currentPage, Boolean hasNextPage) {
        this.data = data;
        this.result = "success";
        this.currentPage = currentPage;
        this.hasNextPage = hasNextPage;
    }

    public JSONObject toJson() {
        return new JSONObject(this);
    }
}
