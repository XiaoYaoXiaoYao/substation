package com.yunan.assignment.service.impl;

import com.yunan.assignment.domain.ManageTask;
import com.yunan.assignment.mapper.ManageTaskMapper;
import com.yunan.assignment.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author:  kqk
 * @createtime:  2020/6/23 9:50
 * @desc: 任务service
 * @version: 1
 **/
@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private ManageTaskMapper manageTaskMapper;


    /**
     * 我的任务列别
     * @param task
     * @return
     */
    @Override
    public List<ManageTask> selectTaskList(ManageTask task) {
        return manageTaskMapper.selectTaskList(task);
    }

    @Override
    public List<ManageTask> selectMyselfTaskList(ManageTask task,Long userId) {
        return manageTaskMapper.selectMyselfTaskList(task,userId);
    }

    /**
     * 删除任务
     * @param ids 删除任务列表的id
     * @return
     */
    @Override
    public Integer deleteTaskByIds(Integer ids) {
        return manageTaskMapper.deleteByPrimaryKey(ids);
    }

    /**
     * 添加任务
     * @param task
     * @return
     */
    @Override
    public Integer addTask(ManageTask task) {
        return manageTaskMapper.insertSelective(task);
    }

    /**
     * 通过任务边编号查询任务信息
     * @param tasKId
     * @return
     */
    @Override
    public ManageTask selectTaskByTaskId(Long tasKId) {
        return manageTaskMapper.selectTaskByTaskId(tasKId);
    }

    /**
     * 通过任务编号查询项目和用户信息
     * @param tasKId
     * @return
     */
    @Override
    public ManageTask selectProjectAndUserByTaskId(Long tasKId) {
        return manageTaskMapper.selectProjectAndUserByTaskId(tasKId);
    }

    /**
     * @author:  kqk
     * @createtime:  2020/7/1 10:47
     * @desc: 更新task
     * @version: 1
     **/
    @Override
    public Integer updateTask(ManageTask task) {
        return manageTaskMapper.updateByPrimaryKey(task);
    }
    /**
     * @author:  kqk
     * @createtime:  2020/7/1 10:47
     * @desc: 对上传文件后的file回显
     * @version: 1
     **/
    @Override
    public Integer updateFileId() {
        return null;
    }

    @Override
    public List<ManageTask> selectIndexTaskList(long id) {
        return manageTaskMapper.selectIndexTaskList(id);
    }

    @Override
    public List<ManageTask> selectIndexTaskListAll() {
        return manageTaskMapper.selectIndexTaskListAll();
    }

    @Override
    public List<ManageTask> taskListManage(long id) {
        return manageTaskMapper.taskListManage(id);
    }

    @Override
    public List<ManageTask> selectTaskById(long id) {
        return manageTaskMapper.selectTaskById(id);
    }

    @Override
    public List<ManageTask> selectGrouperTaskList(ManageTask task, Long userId) {
        return manageTaskMapper.selectGrouperTaskList(task,userId);
    }

    @Override
    public Integer selectProjectProcess(String projectId) {
        return manageTaskMapper.selectProjectProcess(projectId);
    }

    @Override
    public List<ManageTask> selectProjectOfTaskByProjectId(String projectId) {
        return manageTaskMapper.selectProjectOfTaskByProjectId(projectId);
    }

    @Override
    public Integer deleteTaskByUserId(Integer userId,String projectId) {
        return manageTaskMapper.deleteTaskByUserId(userId,projectId);
    }

    @Override
    public Integer updateTaskByUserIdAndProjectId(String taskTitle, String projectId, String projectName, String taskType, int taskStatus, long userId, Date taskStartTime, Date taskEndTime, String createBy, String updateBy, String remark,int progress) {
        return manageTaskMapper.updateTaskByUserIdAndProjectId(taskTitle,projectId,projectName,taskType,taskStatus,userId,taskStartTime,taskEndTime,createBy,updateBy,remark,progress);
    }

    @Override
    public ManageTask selectTaskbyUserIdAndProjectId(Long userId,String projectId) {
        return manageTaskMapper.selectTaskbyUserIdAndProjectId(userId,projectId);
    }

    @Override
    public Integer updateTaskByUserTesterIdAndProjectId(String taskTitle, String projectId, String projectName, String taskType, int taskStatus, long userId, Date taskStartTime, Date taskEndTime, String createBy, String updateBy, String remark,int progress, long testerId) {
        return manageTaskMapper.updateTaskByUserTesterIdAndProjectId(taskTitle,projectId,projectName,taskType,taskStatus,userId,taskStartTime,taskEndTime,createBy,updateBy,remark,progress,testerId);
    }

}
