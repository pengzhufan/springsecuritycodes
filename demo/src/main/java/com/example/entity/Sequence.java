package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sequence implements Serializable {

    /**
     * 表命
     */
    @TableId(type = IdType.INPUT)
    private String name;

    /**
     * 当前值
     */
    private String currentValue;

    /**
     * 下一个值
     */
    private String nextValue;
}
