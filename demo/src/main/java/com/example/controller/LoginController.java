package com.example.controller;

import com.example.common.Result;
import com.example.entity.vo.LoginUserVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

   /* @PostMapping(value = "/login")
    public Result login(@RequestBody @Validated LoginUserVo loginUserVo){

        return Result.ok();
    }*/
}
