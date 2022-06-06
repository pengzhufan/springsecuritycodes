package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Sequence;
import com.example.mapper.SequenceMapper;
import com.example.service.ISequenceService;
import org.springframework.stereotype.Service;

@Service
public class SequenceService extends ServiceImpl<SequenceMapper, Sequence> implements ISequenceService {

}
