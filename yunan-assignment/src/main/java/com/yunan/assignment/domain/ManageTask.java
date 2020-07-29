package com.yunan.assignment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunan.common.annotation.Excel;
import com.yunan.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * @author:  kqk
 * @createtime:  2020/6/22 15:43
 * @desc: 任务实体类
 * @version: 1
 **/
public class ManageTask extends BaseEntity {
//  任务编号
    @Excel(name = "任务编号",prompt = "唯一")
    private Long taskId;
//  任务标题
    @Excel(name = "任务标题")
    private String taskTitle;
//  任务状态：1创建任务,2是开始任务,3提交任务,4任务审核,5任务结束
    @Excel(name = "任务状态",readConverterExp = "1=创建任务,2=开始任务,3=提交任务,4=任务审核,5=任务结束")
    private Integer taskStatus;
//  任务所属哪个项目
    @Excel(name = "所属项目编号")
    private String projectId;
    //  项目名称
    @Excel(name = "项目名称")
    private String projectName;
//  任务关联的人员id
    @Excel(name = "开发人员编号")
    private Long userId;
    //  用户名
    @Excel(name = "开发人员")
    private String userName;
//  完成进度百分比
    @Excel(name = "完成进度百分比")
    private Integer taskProgress;
//  任务开始时间
    @Excel(name = "任务开始时间",dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date taskStartTime;
    //  任务结束时间
    @Excel(name = "任务结束时间",dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date taskEndTime;
//  任务类型 1设计、2开发、3测试、4发布
    @Excel(name="任务类型",readConverterExp = "1=设计,2=开发,3=测试,4=发布")
    private String taskType;
//  任务创建者
//    private String createBy;
//  任务更新者
//    private String updateBy;
//  更新时间
    @Excel(name = "更新时间",dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
//  备注
//    private String remark;
//  文件编号
    @Excel(name = "文件编号")
    private Integer fileId;
//  任务总数
    private String total;
//    文件名称
    @Excel(name="文件名称")
    private String fileName;
//  文件路径
    private String filePath;

    public ManageTask() {
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(Integer taskProgress) {
        this.taskProgress = taskProgress;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ManageTask{" +
                "taskId=" + taskId +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskStatus=" + taskStatus +
                ", projectId='" + projectId + '\'' +
                ", userId=" + userId +
                ", taskProgress=" + taskProgress +
                ", taskStartTime=" + taskStartTime +
                ", taskType='" + taskType + '\'' +
                ", taskEndTime=" + taskEndTime +
                ", updateTime=" + updateTime +
                ", fileId=" + fileId +
                ", total='" + total + '\'' +
                ", projectName='" + projectName + '\'' +
                ", userName='" + userName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
