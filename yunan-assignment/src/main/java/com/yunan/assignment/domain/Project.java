package com.yunan.assignment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunan.common.annotation.Excel;
import com.yunan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.beans.Transient;
import java.util.List;

/**
 * 项目对象 manage_project
 *
 * @author yunan
 * @date 2020-06-21
 */
public class Project extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目编号，唯一 */
    @Excel(name = "项目编号",prompt = "唯一")
    private String projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目描述 */
    @Excel(name = "项目描述")
    private String projectDescription;

    /** 项目预计开始时间 */
    @Excel(name = "项目预计开始时间")
    private String projectStartTime;

    /** 项目预计结束时间 */
    @Excel(name = "项目预计结束时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private String projectEndTime;

    /** 项目阶段:1需求、2设计、3开发、5测试、6发布 */
    @Excel(name = "项目阶段",readConverterExp = "1=需求,2=设计,3=开发,5=测试,6=发布")
    private Integer projectStage;

    /** 项目类型:1短期 2长期 */
    @Excel(name = "项目类型",readConverterExp = "1=短期,2=长期")
    private Integer projectType;

    /** 项目状态:1未开始、2进行中、3进行中已延期、4已完成、5延期完成、6已关闭 */
    @Excel(name = "项目状态",readConverterExp = "1=未开始,2=进行中,3=进行中已延期,4=已完成,5=延期完成,6=已关闭")
    private Integer projectStatus;

    /** 项目进度,百分比 */
    @Excel(name = "项目进度")
    private Integer projectProcess;

    /** 部门组长 */
    @Excel(name = "部门组长编号")
    private Integer userId;

    /** 项目优先级,数字越大,越靠前 */
    @Excel(name = "项目优先级",prompt = "数字越大,越靠前")
    private Integer projectPriority;

    /** 项目结束时间 */
    @Excel(name = "项目结束时间")
    private String projectRealEndTime;

    /** 项目的版本号 */
    private String softwareVersion;

    /** 项目所在的svn地址 */
    private String svnPath;

    @Excel(name = "测试人员编号")
    private String testerId;
    //    无用字段
    private String test;

    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    public String getProjectId()
    {
        return projectId;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectName()
    {
        return projectName;
    }
    public void setProjectDescription(String projectDescription)
    {
        this.projectDescription = projectDescription;
    }

    public String getProjectDescription()
    {
        return projectDescription;
    }
    public void setProjectStartTime(String projectStartTime)
    {
        this.projectStartTime = projectStartTime;
    }

    public String getProjectStartTime()
    {
        return projectStartTime;
    }
    public void setProjectEndTime(String projectEndTime)
    {
        this.projectEndTime = projectEndTime;
    }

    public String getProjectEndTime()
    {
        return projectEndTime;
    }
    public void setProjectStage(Integer projectStage)
    {
        this.projectStage = projectStage;
    }

    public Integer getProjectStage()
    {
        return projectStage;
    }
    public void setProjectType(Integer projectType)
    {
        this.projectType = projectType;
    }

    public Integer getProjectType()
    {
        return projectType;
    }
    public void setProjectStatus(Integer projectStatus)
    {
        this.projectStatus = projectStatus;
    }

    public Integer getProjectStatus()
    {
        return projectStatus;
    }
    public void setProjectProcess(Integer projectProcess)
    {
        this.projectProcess = projectProcess;
    }

    public Integer getProjectProcess()
    {
        return projectProcess;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectPriority() {
        return projectPriority;
    }

    public void setProjectPriority(Integer projectPriority) {
        this.projectPriority = projectPriority;
    }

    public String getProjectRealEndTime() {
        return projectRealEndTime;
    }

    public void setProjectRealEndTime(String projectRealEndTime) {
        this.projectRealEndTime = projectRealEndTime;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getSvnPath() {
        return svnPath;
    }

    public void setSvnPath(String svnPath) {
        this.svnPath = svnPath;
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }
    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", projectStartTime='" + projectStartTime + '\'' +
                ", projectEndTime='" + projectEndTime + '\'' +
                ", projectStage=" + projectStage +
                ", projectType=" + projectType +
                ", projectStatus=" + projectStatus +
                ", projectProcess=" + projectProcess +
                ", userId=" + userId +
                ", projectPriority=" + projectPriority +
                ", projectRealEndTime='" + projectRealEndTime + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", svnPath='" + svnPath + '\'' +
                ", testId=" + testerId +
                ", test='" + test + '\'' +
                "} " + super.toString();
    }
}
