package com.yunan.assignment.mapper;

import com.yunan.assignment.domain.ManageTask;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ManageTaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(ManageTask record);

    int insertSelective(ManageTask record);

    ManageTask selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(ManageTask record);

    int updateByPrimaryKey(ManageTask record);

    List<ManageTask> selectTaskList(@Param("task") ManageTask task);

    ManageTask selectTaskByTaskId(Long taskId);

    ManageTask selectProjectAndUserByTaskId(Long tasKId);

    List<ManageTask> selectMyselfTaskList(@Param("task") ManageTask task,@Param("userId") Long userId);

//    List<Long> selectAvgProgressByProjectId();

    List<ManageTask> selectIndexTaskList(long id);

    List<ManageTask> selectIndexTaskListAll();


    List<ManageTask> taskListManage(long id);

    List<ManageTask> selectTaskById(long id);

    List<ManageTask> selectGrouperTaskList(@Param("task") ManageTask task, @Param("userId") Long userId);

    /**
     * 查看项目的进度
     * @return
     */
    Integer selectProjectProcess(String projectId);

    List<ManageTask> selectProjectOfTaskByProjectId(String projectId);

    Integer deleteTaskByUserId(@Param("userId") Integer userId,@Param("projectId") String projectId);

    Integer updateTaskByUserIdAndProjectId(@Param("taskTitle") String taskTitle, @Param("projectId") String projectId, @Param("projectName") String projectName, @Param("taskType") String taskType, @Param("taskStatus") int taskStatus, @Param("userId") long userId, @Param("taskStartTime") Date taskStartTime, @Param("taskEndTime") Date taskEndTime, @Param("createBy") String createBy, @Param("updateBy") String updateBy, @Param("remark") String remark,@Param("progress") int progress);

    ManageTask selectTaskbyUserIdAndProjectId(@Param("userId") Long userId, @Param("projectId") String projectId);

    Integer updateTaskByUserTesterIdAndProjectId(@Param("taskTitle") String taskTitle, @Param("projectId") String projectId, @Param("projectName") String projectName, @Param("taskType") String taskType, @Param("taskStatus") int taskStatus, @Param("userId") long userId, @Param("taskStartTime") Date taskStartTime, @Param("taskEndTime") Date taskEndTime, @Param("createBy") String createBy, @Param("updateBy") String updateBy, @Param("remark") String remark,@Param("progress") int progress, @Param("testerId")  long testerId);
}