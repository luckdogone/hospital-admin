package com.spring.admin.core.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @date 2023/2/4
 */
@Slf4j
public class BaseServiceImpl<
    M extends BaseMapper<T>,
    T
    > extends ServiceImpl<M, T> implements IService<T> {
}
