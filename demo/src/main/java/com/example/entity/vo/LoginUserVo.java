package com.example.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "登陆信息")
public class LoginUserVo {

    @ApiModelProperty(value = "登陆用户名")
    @NotBlank(message = "登陆用户名不能为空")
    private String username;

    @ApiModelProperty(value = "登陆密码")
    @NotBlank(message = "登陆密码不能为空")
    private String password;
}
