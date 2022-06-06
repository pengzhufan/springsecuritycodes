package com.example.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public final String POST = "POST";

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //只能post
        if (!POST.equals(request.getMethod())){
            throw new AuthenticationServiceException("认证请求不支持" + request.getMethod());
        }
        //json
        if (!request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            throw new AuthenticationServiceException("认证请求不支持"+request.getContentType());
        }
        try {
            Map<String, String> userInfo = new ObjectMapper()
                    .readValue(request.getInputStream(), Map.class);
            //获取用户名及密码
            String username = userInfo.get(getUsernameParameter());
            String password = userInfo.get(getPasswordParameter());
            String kaptcha = userInfo.get("kaptcha");
            String key = userInfo.get("key");

            String kaptchaSession= (String) redisTemplate.opsForValue().get("kaptcha"+key);

            //String kaptchaSession= (String) request.getSession().getAttribute("kaptcha");
            if (!kaptcha.equalsIgnoreCase(kaptchaSession)){
                throw new AuthenticationServiceException("验证码不正确");
            }
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.attemptAuthentication(request, response);
    }
}
