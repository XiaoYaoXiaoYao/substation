package com.yunan.assignment.mapper;

import com.yunan.assignment.domain.ManageTask;
import com.yunan.assignment.domain.Project;

import java.util.List;

/**
 * @author shengbaom
 */
public interface IndexMapper {
    /**
     *
     * @param project 为了条件查询使用
     * @return 项目列表
     */
    public List<Project> selectIndexProjectList(int id);

    public List<ManageTask> selectIndexTaskList(long id);

    public List<Project> selectIndexProjectListAll();

    public List<ManageTask> selectIndexTaskListAll();
}
