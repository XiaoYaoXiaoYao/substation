package com.yunan.assignment.service;

import com.yunan.assignment.domain.Project;

import java.util.List;

/**
 * 【项目】Service接口
 *
 * @author yunan
 * @date 2020-06-21
 */
public interface ProjectService
{
    /**
     * 查询【项目】
     *
     * @param projectId 【项目】ID
     * @return 【项目】
     */
    public Project selectProjectById(String projectId);

    /**
     * @author:  kqk
     * @createtime:  2020/7/1 12:53
     * @desc: 在新增任务时查询到和自己相关的项目名
     * @version: 1
     **/
    List<Project> selectMyProjectById(Long userId);

    /**
     * 查询【项目】列表
     *
     * @param project 【项目】
     * @return 【请填写功能名称】集合
     */
    public List<Project> selectProjectList(Project project);

    public List<Project> selectMyProjectList(Project project,Long userId);

    /**
     * 新增项目
     *
     * @param project 【项目】
     * @return 结果
     */
    public int insertProject(Project project);

    /**
     * 修改【项目】
     *
     * @param project 【项目】
     * @return 结果
     */
    public int updateProject(Project project);

    /**
     * 批量删除【项目】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProjectByIds(String ids);

    /**
     * 删除【项目】信息
     *
     * @param projectId 【项目】ID
     * @return 结果
     */
    public int deleteProjectById(String projectId);

    /**
     * 校验项目编码
     *
     * @param project 项目信息
     * @return 结果
     */
    public String checkProjectCodeUnique(Project project);

    /**
     * 根据项目编号查询项目名称
     * @param projectId
     * @return
     */
    Project selectProjectNameById(String projectId);

    /**
     * 根据用户id查询项目列表
     * @param id
     * @return
     */
    public List<Project> selectIndexProjectList(int id);

    /**
     * 查询所有
     * @return
     */
    public List<Project> selectIndexProjectListAll();


    public List<Project> projectListManage(int id);

    public List<Project> projectListTest(int id);

    List<Project> selectIndexExportProject(int id);
}
