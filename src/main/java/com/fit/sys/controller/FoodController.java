package com.fit.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fit.sys.entity.Course;
import com.fit.sys.entity.Result;
import com.fit.sys.entity.Food;
import com.fit.sys.service.IFoodService;
import com.fit.sys.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hongwq
 * @since 2021-01-31
 */
@Api(tags = "食物")
@RestController
@RequestMapping("/sys/food")
public class FoodController {

    @Autowired
    IFoodService foodService;

    @Value("${file.path}")
    String filePath;

    @ApiOperation("按字段分页查询")
    @GetMapping("/like")
    public ResponseEntity<Result<Object>> getAll(int page, int size, String name, String type) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        String q = "--";
        if (name != null) {
            queryWrapper.like("name", name);
        }
        if (!q.equals(type)) {
            queryWrapper.eq("type", type);
        }
        IPage<Food> queryResult = foodService.page(new Page<>(page, size), queryWrapper);
        Result result = new Result();
        if (queryResult == null) {
            result.setCode(1000);
            result.setMsg("查询失败");
        } else {
            result.setCode(200);
            result.setMsg("查询成功");
            result.setData(queryResult);
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("分页查询")
    @GetMapping
    public ResponseEntity<Result<Object>> getAll(int page, int size) {
        IPage<Food> queryResult = foodService.page(new Page<>(page, size));
        Result result = new Result();
        if (queryResult == null) {
            result.setCode(1000);
            result.setMsg("查询失败");
        } else {
            result.setCode(200);
            result.setMsg("查询成功");
            result.setData(queryResult);
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Object>> deleteFood(@PathVariable Long id) {
        Result result = new Result();
        if (foodService.removeById(id)) {
            result.setCode(200);
            result.setMsg("删除成功");
        } else {
            result.setCode(1000);
            result.setMsg("删除失败");
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping
    @ApiOperation("更改")
    public ResponseEntity<Result<Object>> updateFood(@RequestBody Food food) {
        Food entity = foodService.getById(food.getId());
        entity.setHot(food.getHot())
                .setName(food.getName())
                .setType(food.getType());
        Result result = new Result();
        if (foodService.updateById(entity)) {
            result.setCode(200);
            result.setMsg("更新成功");
        } else {
            result.setCode(1000);
            result.setMsg("更新失败");
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("增加")
    @PostMapping
    public ResponseEntity<Result<Object>> addFood(@RequestBody Food food) {
        Result result = new Result();
        if (foodService.save(food)) {
            result.setCode(200);
            result.setMsg("插入成功");
        } else {
            result.setCode(1000);
            result.setMsg("插入失败");
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("上传图片")
    @PostMapping("/picture/{id}")
    public ResponseEntity<Result<Object>> addCourse(@PathVariable Long id, MultipartFile file) {
        FileUtil.checkSize(100, file.getSize());
        File x = FileUtil.upload(file, filePath + "picture\\");
        Result result = new Result();
        try {
            Food food = foodService.getById(id);
            if (food.getSrc() != null) {
                FileUtil.del(filePath + "picture\\" + food.getSrc().replace("file/picture/", ""));
            }
            food.setSrc("file/picture/" + x.getName());
            foodService.updateById(food);
            result.setCode(200);
            result.setMsg("插入成功");
            result.setData(food.getSrc());
        } catch (Exception e) {
            FileUtil.del(x);
            result.setCode(1000);
            result.setMsg("插入失败");
        }
        return ResponseEntity.ok(result);
    }
}
