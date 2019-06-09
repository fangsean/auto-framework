package com.auto.service.impl;

import com.auto.common.annotation.Af;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.auto.common.annotation.adapters.LogWapper;
import com.auto.entity.User;
import com.auto.mapper.UserMapper;
import com.auto.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User 表数据服务层接口实现类
 */
@Service
@Af(value = "aa"/*, attribute = "bb"*/)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean deleteAll() {
        return retBool(userMapper.deleteAll());
    }

    @Override
    @LogWapper(isSave = true, value = "query user log")
    public List<User> selectListBySQL() {
        return baseMapper.selectListBySQL();
    }

    @Override
    public List<User> selectListByWrapper(Wrapper wrapper) {
        return baseMapper.selectListByWrapper(wrapper);
    }
}