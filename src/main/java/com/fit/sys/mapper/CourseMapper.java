package com.fit.sys.mapper;

import com.fit.sys.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hongwq
 * @since 2021-01-31
 */
public interface CourseMapper extends BaseMapper<Course> {
    @Insert("insert into user_course(course_id, user_id) values(#{courseId}, #{userId})")
    Boolean insertRelation(@Param("courseId") Long courseId,
                           @Param("userId") Long userId);

    @Delete("delete from user_course where course_id = #{courseId} and user_id = #{userId}")
    Boolean deleteRelation(@Param("courseId") Long courseId,
                           @Param("userId") Long userId);

    @Select("select count(0) from user_course where course_id = #{courseId} and user_id = #{userId}")
    Integer count(@Param("courseId") Long courseId,
                  @Param("userId") Long userId);

    @Delete("delete from user_course where course_id = #{courseId}")
    Boolean deleteCourse(@Param("courseId") Long courseId);

    @Delete("delete from user_course where user_id = #{userId}")
    Boolean deleteUser(@Param("userId") Long userId);

    @Select("select course_id from user_course where user_id = #{userId}")
    List<Long> getCourseIds(@Param("userId") Long userId);

}
