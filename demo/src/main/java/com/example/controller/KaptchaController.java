package com.example.controller;

import com.google.code.kaptcha.Producer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class KaptchaController {


    @Autowired
    private Producer kaptcherProducer;//Producer是kaptcha的核心接口，通过它去创建图片以及随机的字符串

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(@RequestParam String key, HttpServletResponse response, HttpSession session){
        //生成验证码
        String text = kaptcherProducer.createText();
        BufferedImage image = kaptcherProducer.createImage(text);

        //将验证码存入session
        //session.setAttribute("kaptcha", text);
        //UUID uuid = UUID.randomUUID();
        redisTemplate.opsForValue().set("kaptcha"+key,text);
        //将图片输出到浏览器
        response.setContentType("image/png");
        try{
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        }catch (IOException e){
            throw new RuntimeException("响应验证码失败：" + e.getMessage());
        }
    }

}

