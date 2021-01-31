package com.fit.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fit.sys.entity.Result;
import com.fit.sys.entity.Food;
import com.fit.sys.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hongwq
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/sys/food")
public class FoodController {

    @Autowired
    IFoodService foodService;

    @GetMapping("/like")
    public ResponseEntity<Result<Object>> getAll(int page, int size, String like) {
        IPage<Food> queryResult = foodService.page(new Page<>(page, size), new QueryWrapper<Food>().lambda().like(Food::getName, "%" + like + "%"));
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
    public ResponseEntity<Result<Object>> updateFood(@RequestBody Food food) {
        Result result = new Result();
        if (foodService.updateById(food)) {
            result.setCode(200);
            result.setMsg("更新成功");
        } else {
            result.setCode(1000);
            result.setMsg("更新失败");
        }
        return ResponseEntity.ok(result);
    }

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
}
