package com.fit.sys.service.impl;

import com.fit.sys.entity.Admin;
import com.fit.sys.mapper.AdminMapper;
import com.fit.sys.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
