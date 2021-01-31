package com.fit.sys.service.impl;

import com.fit.sys.entity.Food;
import com.fit.sys.mapper.FoodMapper;
import com.fit.sys.service.IFoodService;
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
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements IFoodService {

}
