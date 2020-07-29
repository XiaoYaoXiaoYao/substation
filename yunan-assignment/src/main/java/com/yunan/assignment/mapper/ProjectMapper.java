package com.yunan.assignment.mapper;

import com.yunan.assignment.domain.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目Mapper接口
 *
 * @author yunan
 * @date 2020-06-21
 */
public interface ProjectMapper
{
    /**
     * 查询项目
     *
     * @param projectId 项目ID
     * @return 项目
     */
    public Project selectProjectById(String projectId);

    /**
     * 查询项目列表
     *
     * @param project 项目
     * @return 项目集合
     */
    public List<Project> selectProjectList(Project project);

    /**
     * 新增项目
     *
     * @param project
     * @return 结果
     */
    public int insertProject(Project project);

    /**
     * 修改项目
     *
     * @param project 项目
     * @return 结果
     */
    public int updateProject(Project project);

    /**
     * 删除项目
     *
     * @param projectId 项目ID
     * @return 结果
     */
    public int deleteProjectById(String projectId);

    /**
     * 批量删除项目
     *
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProjectByIds(String[] projectIds);
    /**
     * 校验项目编码
     *
     * @param projectId 项目编码
     * @return 结果
     */
    public Project checkProjectIdUnique(String projectId);

    Project selectProjectNameById(String projectId);


    /**
     * 根据用户id列表
     *
     * @param id 用户id
     * @return 结果
     */
    List<Project> selectIndexProjectList(int id);


    List<Project> selectIndexExportProject(int id);

    /**
     * 查询所有
     * @return 结果
     */
    List<Project> selectIndexProjectListAll();

    List<Project> projectListManage(int id);

    List<Project> projectListTest(int id);

    List<Project> selectMyProjectById(Long userId);

    List<Project> selectMyProjectList(@Param("project") Project project, @Param("userId") Long userId);
}
