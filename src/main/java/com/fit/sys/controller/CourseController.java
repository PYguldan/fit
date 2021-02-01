package com.fit.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fit.sys.entity.Result;
import com.fit.sys.entity.Course;
import com.fit.sys.service.ICourseService;
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
@Api(tags = "课程")
@RestController
@RequestMapping("/sys/class")
public class CourseController {

    @Autowired
    ICourseService courseService;

    @Value("${file.path}")
    String filePath;

    @ApiOperation("分页查询")
    @GetMapping
    public ResponseEntity<Result<Object>> getAll(int page, int size) {
        IPage<Course> queryResult = courseService.page(new Page<>(page, size));
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

    @ApiOperation("按字段分页查询")
    @GetMapping("/like")
    public ResponseEntity<Result<Object>> getAll(int page, int size, String name,
                                                 String body, String machine, String level, boolean vip) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        String q = "--";
        if (name != null) {
            queryWrapper.like("name", name);
        }
        if (!q.equals(body)) {
            queryWrapper.eq("body", body);
        }
        if (!q.equals(machine)) {
            queryWrapper.eq("machine", machine);
        }
        if (!q.equals(level)) {
            queryWrapper.eq("level", Integer.parseInt(level));
        }
        queryWrapper.eq("vip", vip);
        IPage<Course> queryResult = courseService.page(new Page<>(page, size), queryWrapper);
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
    public ResponseEntity<Result<Object>> deleteCourse(@PathVariable Long id) {
        Result result = new Result();
        if (courseService.removeById(id)) {
            result.setCode(200);
            result.setMsg("删除成功");
        } else {
            result.setCode(1000);
            result.setMsg("删除失败");
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("更改")
    @PutMapping
    public ResponseEntity<Result<Object>> updateCourse(@RequestBody Course course) {
        Result result = new Result();
        Course entity = courseService.getById(course.getId());
        entity.setName(course.getName())
                .setBody(course.getBody())
                .setLevel(course.getLevel())
                .setMachine(course.getMachine())
                .setVip(course.getVip());
        if (courseService.updateById(entity)) {
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
    public ResponseEntity<Result<Object>> addCourse(@RequestBody Course course) {
        Result result = new Result();
        if (courseService.save(course)) {
            result.setCode(200);
            result.setMsg("插入成功");
        } else {
            result.setCode(1000);
            result.setMsg("插入失败");
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation("上传视频")
    @PostMapping("/video/{id}")
    public ResponseEntity<Result<Object>> addCourse(@PathVariable Long id, MultipartFile file) {
        FileUtil.checkSize(100, file.getSize());
        File x = FileUtil.upload(file, filePath + "video\\");
        Result result = new Result();
        try {
            Course course = courseService.getById(id);
            if (course.getSrc() != null) {
                FileUtil.del(filePath + "video\\" + course.getSrc().replace("file/video/", ""));
            }
            course.setSrc("file/video/" + x.getName());
            courseService.updateById(course);
            result.setCode(200);
            result.setMsg("插入成功");
            result.setData(course.getSrc());
        } catch (Exception e) {
            FileUtil.del(x);
            result.setCode(1000);
            result.setMsg("插入失败");
        }
        return ResponseEntity.ok(result);
    }

}
