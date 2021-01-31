package com.fit.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fit.sys.entity.Admin;
import com.fit.sys.entity.Result;
import com.fit.sys.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hongwq
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/sys/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @PostMapping
    public ResponseEntity<Result<Object>> addAdmin(@RequestBody Admin admin) {
        Result result = new Result();
        if (adminService.save(admin)) {
            result.setCode(200);
            result.setMsg("插入成功");
        } else {
            result.setCode(1000);
            result.setMsg("插入失败");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Result<Object>> login(@RequestBody Admin admin) {
        Result result = new Result();
        Admin queryResult = adminService.getOne(new QueryWrapper<Admin>().lambda().eq(Admin::getUsername, admin.getUsername()).eq(Admin::getPassword, admin.getPassword()));
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
