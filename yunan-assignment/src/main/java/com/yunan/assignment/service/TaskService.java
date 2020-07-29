package com.yunan.assignment.service;

import com.yunan.assignment.domain.ManageTask;

import java.util.Date;
import java.util.List;

/**
 * @author:  kqk
 * @createtime:  2020/6/22 15:11
 * @desc: 任务service接口
 * @version: 1
 **/
public interface TaskService {

    /**
     * 查询任务列表
     * @return list
     */
     List<ManageTask> selectTaskList(ManageTask task);

     /**
      * @author:  kqk
      * @createtime:  2020/7/1 10:46
      * @desc: 通过用户编号拿到对应的task
      * @version: 1
      **/
     List<ManageTask> selectMyselfTaskList(ManageTask task,Long userId);

    /**
     *
     * @param ids 删除任务列表的id
     * @return 受影响的记录条数
     */
     Integer deleteTaskByIds(Integer ids);

    /**
     * 添加任务
     * @param task
     * @return
     */
    Integer addTask(ManageTask task);

    /**
     * 通过任务编号查询任务信息
     * @param tasKId
     * @return
     */
    ManageTask selectTaskByTaskId(Long tasKId);

    /**
     * 通过任务编号查询项目信息
     * @param tasKId
     * @return
     */
    ManageTask selectProjectAndUserByTaskId(Long tasKId);

    /**
     * 修改任务信息
     * @param task
     * @return
     */
    Integer updateTask(ManageTask task);

    /**
     * 通过获取到的fileID以及taskId更新task表
     * @return
     */
    Integer updateFileId();

//    List<Long> selectAvgProgressByProjectId();

    /**
     * 根据用户id查询任务列表
     * @return
     */
    List<ManageTask> selectIndexTaskList(long id); //开发

    /**
     * 查询所有
     * @return
     */
    List<ManageTask> selectIndexTaskListAll(); //项目经理


    List<ManageTask> taskListManage(long id); //部门经理


    List<ManageTask> selectTaskById(long id);

    /**
     * @author:  kqk
     * @createtime:  2020/7/8 11:44
     * @desc: 分类查询task列表
     * @version: 1
     **/
    List<ManageTask> selectGrouperTaskList(ManageTask task,Long userId);

    /**
     * 查看项目的进度
     * @return
     */
    Integer selectProjectProcess(String projectId);

    /**
     * @author:  kqk
     * @createtime:  2020/7/8 11:43
     * @desc: 根据项目id查询到所有的任务
     * @version: 1
     **/
    List<ManageTask> selectProjectOfTaskByProjectId(String projectId);
    /**
     * @author:  kqk
     * @createtime:  2020/7/22 9:56
     * @desc: 根据用户id和项目id删除任务
     * @version: 1
     **/
    Integer deleteTaskByUserId(Integer userId,String projectId);
    /**
     * @author:  kqk
     * @createtime:  2020/7/22 9:57
     * @desc: 在项目中修改项目时更新task
     * @version: 1
     **/
    Integer updateTaskByUserIdAndProjectId(String taskTitle, String projectId, String projectName, String taskType, int taskStatus, long userId, Date taskStartTime, Date taskEndTime, String createBy, String updateBy, String remark,int progress);
    /**
     * @author:  kqk
     * @createtime:  2020/7/22 9:57
     * @desc: 根据用户id和项目id查询任务
     * @version: 1
     **/
    ManageTask selectTaskbyUserIdAndProjectId(Long userId,String projectId);
    /**
     * @author:  kqk
     * @createtime:  2020/7/22 9:58
     * @desc: project中更新测试人员
     * @version: 1
     **/
    Integer updateTaskByUserTesterIdAndProjectId(String taskTitle, String projectId, String projectName, String taskType, int taskStatus, long userId, Date taskStartTime, Date taskEndTime, String createBy, String updateBy, String remark,int progress, long testerId);
}

