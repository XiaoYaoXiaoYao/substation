package com.yunan.assignment.service.impl;

import com.yunan.assignment.domain.ManageTask;
import com.yunan.assignment.domain.Project;
import com.yunan.assignment.mapper.IndexMapper;
import com.yunan.assignment.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public List<Project> selectIndexProjectList(int id) {
        return indexMapper.selectIndexProjectList(id);
    }

    @Override
    public List<ManageTask> selectIndexTaskList(long id) {
        return indexMapper.selectIndexTaskList(id);
    }

    @Override
    public List<Project> selectIndexProjectListAll() {
        return indexMapper.selectIndexProjectListAll();
    }

    @Override
    public List<ManageTask> selectIndexTaskListAll() {
        return indexMapper.selectIndexTaskListAll();
    }
}
