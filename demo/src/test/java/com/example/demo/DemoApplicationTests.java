package com.example.demo;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.entity.Sequence;
import com.example.entity.User;
import com.example.mapper.SequenceMapper;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Objects;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    private UserMapper userMapper;
    @Resource
    private SequenceMapper sequenceMapper;
    @Test
    void contextLoads() {
        User loginUser = new User();
        loginUser.setId(Integer.valueOf(genPK(loginUser)));
        loginUser.setUsername("cwh");
        loginUser.setPassword("{noop}cwh");
        userMapper.insert(loginUser);
        User loginUser1 = new User();
        loginUser.setId(Integer.valueOf(genPK(loginUser1)));
        loginUser1.setPassword("{noop}pzf");
        loginUser1.setUsername("pzf");
        userMapper.insert(loginUser1);
    }

    public String genPK(Object entity){
        String value1="1";
        String className = entity.getClass().getName();
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("获取"+className+"对象的反射对象失败");
        }
        //根据bizKey调用分布式ID生成
        String value = aClass.getAnnotation(TableName.class).value();
        if (StringUtils.isEmpty(value)){
            throw new RuntimeException("该"+className+"为添加@TableName注解");
        }
        Sequence sequence = sequenceMapper.selectOne(new LambdaQueryWrapper<Sequence>().eq(Sequence::getName, value));
        if (Objects.isNull(sequence)){
            Sequence seq = new Sequence();
            seq.setName(value);
            seq.setCurrentValue("1");
            seq.setNextValue("2");
            sequenceMapper.insert(seq);
        }else {
            value1 = sequence.getNextValue();
            Long nextValue=1L;
            nextValue = Long.valueOf(sequence.getNextValue())+1L;
            sequence.setCurrentValue(sequence.getNextValue());
            sequence.setNextValue(String.valueOf(nextValue));
        }
        return value1;
    }

}
