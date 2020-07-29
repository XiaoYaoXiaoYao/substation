package com.yunan.assignment.service.impl;

import com.yunan.assignment.domain.Project;
import com.yunan.assignment.mapper.ProjectMapper;
import com.yunan.assignment.service.ProjectService;
import com.yunan.common.constant.UserConstants;
import com.yunan.common.core.text.Convert;
import com.yunan.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author yunan
 * @date 2020-06-21
 */
@Service
public class ProjectServiceImpl implements ProjectService
{
    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 查询项目
     *
     * @param projectId 项目ID
     * @return 项目
     */
    @Override
    public Project selectProjectById(String projectId)
    {
        return projectMapper.selectProjectById(projectId);
    }

    @Override
    public List<Project> selectMyProjectById(Long userId) {
        return projectMapper.selectMyProjectById(userId);
    }

    /**
     * 查询项目列表
     *
     * @param project 项目
     * @return
     */
    @Override
    public List<Project> selectProjectList(Project project)
    {
        System.out.println("执行到service中的方法1");
        return projectMapper.selectProjectList(project);
    }

    @Override
    public List<Project> selectMyProjectList(Project project, Long userId) {
        return projectMapper.selectMyProjectList(project,userId);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param project 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertProject(Project project)
    {
        return projectMapper.insertProject(project);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param project 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateProject(Project project)
    {
        project.setUpdateTime(DateUtils.getNowDate());
        return projectMapper.updateProject(project);
    }

    /**
     * 删除项目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProjectByIds(String ids)
    {
        return projectMapper.deleteProjectByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除项目信息
     *
     * @param projectId 项目ID
     * @return 结果
     */
    @Override
    public int deleteProjectById(String projectId)
    {
        return projectMapper.deleteProjectById(projectId);
    }

    @Override
    public String checkProjectCodeUnique(Project project) {
        if(project.getProjectId().trim()==null||"".equals(project.getProjectId().trim())){
            //不唯一
            return UserConstants.PROJECT_CODE_NOT_UNIQUE;
        }else{
            Project info = projectMapper.checkProjectIdUnique(project.getProjectId());
            if(info != null){
                //不唯一
                return UserConstants.PROJECT_CODE_NOT_UNIQUE;
            }else{
                return UserConstants.PROJECT_CODE_UNIQUE;
            }
        }
    }

    @Override
    public Project selectProjectNameById(String projectId) {
        return projectMapper.selectProjectNameById(projectId);
    }

    @Override
    public List<Project> selectIndexProjectList(int id) {
        return projectMapper.selectIndexProjectList(id);
    }

    @Override
    public List<Project> selectIndexProjectListAll() {
        return projectMapper.selectIndexProjectListAll();
    }


    public List<Project> projectListManage(int id){
        return projectMapper.projectListManage(id);
    }

    @Override
    public List<Project> projectListTest(int id) {
        return projectMapper.projectListTest(id);
    }

    @Override
    public List<Project> selectIndexExportProject(int id) {
        return projectMapper.selectIndexExportProject(id);
    }
}
