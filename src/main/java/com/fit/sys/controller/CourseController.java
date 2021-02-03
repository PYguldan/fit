package com.fit.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fit.sys.entity.Result;
import com.fit.sys.entity.Course;
import com.fit.sys.mapper.CourseMapper;
import com.fit.sys.service.ICourseService;
import com.fit.sys.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Autowired
    CourseMapper courseMapper;

    @Value("${file.path}")
    String filePath;

    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public ResponseEntity<Result<Object>> getById(@PathVariable long id) {
        Course queryResult = courseService.getById(id);
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

    @ApiOperation("根据userId查询关注的课程")
    @GetMapping("user/{id}")
    public ResponseEntity<Result<Object>> getCoursesByUserId(@PathVariable long id) {
        List<Long> ids = courseMapper.getCourseIds(id);
        Collection<Course> queryResult;
        if (ids == null || ids.size() == 0) {
            queryResult = new ArrayList<>();
        } else {
            queryResult = courseService.listByIds(ids);
        }
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

    @ApiOperation("取消关注课程")
    @DeleteMapping("user/{id}")
    public ResponseEntity<Result<Object>> deleteRelation(@PathVariable long id, long courseId) {
        Result result = new Result();
        courseMapper.deleteRelation(courseId, id);
        result.setCode(200);
        result.setMsg("取消关注成功");
        return ResponseEntity.ok(result);
    }

    @ApiOperation("关注课程")
    @PostMapping("user/{id}")
    public ResponseEntity<Result<Object>> addRelation(@PathVariable long id, long courseId) {
        Result result = new Result();
        if (0 == courseMapper.count(courseId, id)) {
            if (courseMapper.insertRelation(courseId, id)) {
                result.setCode(200);
                result.setMsg("关注成功");
            } else {
                result.setCode(1000);
                result.setMsg("查询失败");
            }
        } else {
            result.setCode(1000);
            result.setMsg("已关注，无需重复关注");
        }
        return ResponseEntity.ok(result);
    }

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
            courseMapper.deleteCourse(id);
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
    public ResponseEntity<Result<Object>> addCourse(@PathVariable Long id, MultipartFile file, MultipartFile pic) {
        boolean flag1 = file == null, flag2 = pic == null;
        File x = null,y = null;
        if (!flag1) {
            FileUtil.checkSize(100, file.getSize());
            x = FileUtil.upload(file, filePath + "video/");
        }
        if (!flag2) {
            FileUtil.checkSize(100, pic.getSize());
            y = FileUtil.upload(pic, filePath + "picture/");
        }
        Result result = new Result();
        try {
            Course course = courseService.getById(id);
            if (!flag1) {
                if (StringUtils.hasText(course.getSrc())) {
                    FileUtil.del(filePath + "video/" + course.getSrc().replace("file/video/", ""));
                }
                course.setSrc("file/video/" + x.getName());
            }
            if (!flag2) {
                if (StringUtils.hasText(course.getPic())) {
                    FileUtil.del(filePath + "picture/" + course.getPic().replace("file/picture/", ""));
                }
                course.setPic("file/picture/" + y.getName());
            }
            courseService.updateById(course);
            result.setCode(200);
            result.setMsg("插入成功");
            result.setData(course);
        } catch (Exception e) {
            FileUtil.del(x);
            result.setCode(1000);
            result.setMsg("插入失败");
        }
        return ResponseEntity.ok(result);
    }

}
