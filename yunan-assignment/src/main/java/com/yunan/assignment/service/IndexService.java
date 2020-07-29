package com.yunan.assignment.service;

import com.yunan.assignment.domain.ManageTask;
import com.yunan.assignment.domain.Project;

import java.util.List;


public interface IndexService {

    public List<Project> selectIndexProjectList(int id);

    public List<ManageTask> selectIndexTaskList(long id);

    public List<Project> selectIndexProjectListAll();

    public List<ManageTask> selectIndexTaskListAll();
}
