package com.fit.sys.service.impl;

import com.fit.sys.entity.User;
import com.fit.sys.mapper.UserMapper;
import com.fit.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hongwq
 * @since 2021-01-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
