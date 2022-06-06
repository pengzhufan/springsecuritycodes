package com.example.controller;

import com.google.code.kaptcha.Producer;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class KaptchaController {

    public final String KAPCHA="KAPCHA";

    @Autowired
    private Producer producer;//Producer是kaptcha的核心接口，通过它去创建图片以及随机的字符串

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)
    public Map<String,String> getKaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        //1.生成验证码
        String text = producer.createText();
        //2.放入 session redis 实现
        //session.setAttribute("kaptcha", text);
        //3.生成图片
        String uuid = UUID.randomUUID().toString();
        BufferedImage bi = producer.createImage(text);
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(bi, "jpg", fos);
        //4.返回 base64
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("captcha",Base64.encodeBase64String(fos.toByteArray()));
        redisTemplate.opsForValue().set(KAPCHA+uuid,text,1, TimeUnit.MINUTES);
        hashMap.put("UUID",uuid);
        System.out.println(text);
        return hashMap;
    }

}

