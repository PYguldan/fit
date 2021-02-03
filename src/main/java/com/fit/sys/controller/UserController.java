package com.fit.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fit.sys.entity.Result;
import com.fit.sys.entity.User;
import com.fit.sys.mapper.CourseMapper;
import com.fit.sys.service.IUserService;
import com.fit.sys.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hongwq
 * @since 2021-01-31
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    CourseMapper courseMapper;

    @Value("${file.path}")
    String filePath;

    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public ResponseEntity<Result<Object>> getAll(@PathVariable long id) {
        User queryResult = userService.getById(id);
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

    @ApiOperation("通过名字分页查询")
    @GetMapping("/like")
    public ResponseEntity<Result<Object>> getAll(int page, int size, String username) {
        if (username==null) {
            username = "";
        }
        IPage<User> queryResult = userService.page(new Page<>(page, size), new QueryWrapper<User>().lambda().like(User::getUsername, "%" + username + "%"));
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
    public ResponseEntity<Result<Object>> deleteUser(@PathVariable Long id) {
        Result result = new Result();
        if (userService.removeById(id)) {
            courseMapper.deleteUser(id);
            result.setCode(200);
            result.setMsg("删除成功");
        } else {
            result.setCode(1000);
            result.setMsg("删除失败");
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("更改密码")
    @PutMapping("/password/{id}")
    public ResponseEntity<Result<Object>> updatePassword(@PathVariable Long id,String oldPassword, String newPassword) {
        User user = userService.getById(id);
        Result result = new Result();
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userService.updateById(user);
            result.setCode(200);
            result.setMsg("密码修改成功");
        } else {
            result.setCode(1000);
            result.setMsg("原密码错误");
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("更改")
    @PutMapping
    public ResponseEntity<Result<Object>> updateUser(MultipartFile pic, HttpServletRequest request) {
        Result result = new Result();
        File x = null;
        if (pic != null) {
            FileUtil.checkSize(100, pic.getSize());
            x = FileUtil.upload(pic, filePath + "picture/");
        }
        try{
            Map<String, String[]> map = request.getParameterMap();
            Long id = Long.parseLong(map.get("id")[0]);
            User user = userService.getById(id);
            if (pic != null && StringUtils.hasText(user.getAvatar())) {
                FileUtil.del(filePath + "picture/" + user.getAvatar().replace("file/picture/", ""));
            }
            if (map.get("password") != null) {
                user.setPassword(map.get("password")[0]);
            }
            if (map.get("vip") != null) {
                user.setVip(LocalDateTime.parse(map.get("vip")[0]));
            }
            user.setUsername(map.get("username")[0])
                    .setPhone(map.get("phone")[0])
                    .setHeight(new BigDecimal(map.get("height")[0]))
                    .setWeight(new BigDecimal(map.get("weight")[0]))
                    .setSex("true".equals(map.get("sex")[0]));
            if (x != null) {
                user.setAvatar("file/picture/" + x.getName());
            }
            if (userService.updateById(user)) {
                result.setCode(200);
                result.setMsg("更新成功");
            } else {
                result.setCode(1000);
                result.setMsg("更新失败");
            }
        } catch (Exception e) {
            FileUtil.del(x);
            result.setCode(1000);
            result.setMsg("更新失败");
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("增加")
    @PostMapping
    public ResponseEntity<Result<Object>> addUser(@RequestBody User user) {
        Result result = new Result();
        user.setId(null);
        user.setPassword("123456");
        user.setVip(LocalDateTime.now());
        if (userService.count(new QueryWrapper<User>().lambda().eq(User::getUsername, user.getUsername())) == 0) {
            if (userService.save(user)) {
                result.setCode(200);
                result.setMsg("插入成功");
            } else {
                result.setCode(1000);
                result.setMsg("插入失败");
            }
        } else {
            result.setCode(1000);
            result.setMsg("用户名已存在");
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("登录")
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
            result.setData(queryResult.getId());
        }
        return ResponseEntity.ok(result);
    }
}
