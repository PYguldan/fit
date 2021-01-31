package com.fit.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fit.sys.entity.Result;
import com.fit.sys.entity.Course;
import com.fit.sys.service.ICourseService;
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
@RequestMapping("/sys/class")
public class CourseController {

    @Autowired
    ICourseService courseService;

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

    @PutMapping
    public ResponseEntity<Result<Object>> updateCourse(@RequestBody Course course) {
        Result result = new Result();
        if (courseService.updateById(course)) {
            result.setCode(200);
            result.setMsg("更新成功");
        } else {
            result.setCode(1000);
            result.setMsg("更新失败");
        }
        return ResponseEntity.ok(result);
    }

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

}
