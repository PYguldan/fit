package com.fit.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fit.sys.entity.Result;
import com.fit.sys.entity.User;
import com.fit.sys.service.IUserService;
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
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping
    public ResponseEntity<Result<Object>> getAll(int page, int size) {
        IPage<User> queryResult = userService.page(new Page<>(page, size));
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
    public ResponseEntity<Result<Object>> deleteUser(@PathVariable Long id) {
        Result result = new Result();
        if (userService.removeById(id)) {
            result.setCode(200);
            result.setMsg("删除成功");
        } else {
            result.setCode(1000);
            result.setMsg("删除失败");
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Result<Object>> updateUser(@RequestBody User user) {
        Result result = new Result();
        if (userService.updateById(user)) {
            result.setCode(200);
            result.setMsg("更新成功");
        } else {
            result.setCode(1000);
            result.setMsg("更新失败");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Result<Object>> addUser(@RequestBody User user) {
        Result result = new Result();
        if (userService.save(user)) {
            result.setCode(200);
            result.setMsg("插入成功");
        } else {
            result.setCode(1000);
            result.setMsg("插入失败");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Result<Object>> login(@RequestBody User user) {
        Result result = new Result();
        User queryResult = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if (queryResult == null) {
            result.setCode(1000);
            result.setMsg("登录失败");
        } else {
            result.setCode(200);
            result.setMsg("登录成功");
        }
        return ResponseEntity.ok(result);
    }
}
