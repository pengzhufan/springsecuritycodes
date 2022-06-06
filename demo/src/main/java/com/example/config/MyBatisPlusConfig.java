package com.example.config;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.example.entity.Sequence;
import com.example.mapper.SequenceMapper;
import com.example.service.ISequenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Configuration
public class MyBatisPlusConfig {

    /**
     * 自定义主键
     */
    /*@Component
    public class CustomIdGenerator implements IdentifierGenerator {
        @Resource
        private SequenceMapper sequenceMapper;

        @Override
        public Long nextId(Object entity) {
            //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
            Long id=1L;
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
                id= Long.valueOf(sequence.getNextValue());
                Long nextValue=1L;
                nextValue = Long.valueOf(sequence.getNextValue())+1L;
                sequence.setCurrentValue(sequence.getNextValue());
                sequence.setNextValue(String.valueOf(nextValue));
            }
            //返回生成的id值即可.
            return id;
        }
    }*/

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

}
