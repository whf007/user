package com.whf.user.dao.mapper;

import com.whf.user.dao.entity.member.TmCourse;
import java.util.List;

public interface TmCourseMapper {
    int deleteByPrimaryKey(String courseId);

    int insert(TmCourse record);

    TmCourse selectByPrimaryKey(String courseId);

    List<TmCourse> selectAll();

    int updateByPrimaryKey(TmCourse record);
}