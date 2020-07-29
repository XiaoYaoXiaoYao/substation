package com.yunan.assignment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunan.common.annotation.Excel;
import com.yunan.common.core.domain.BaseEntity;


/**
 * <br/>
 * Created by xiaoyao on 2020/06/22
 */
public class Defect extends BaseEntity  {

    /**
     * 缺陷_id
     */
    @Excel(name = "bug编号",prompt = "唯一")
    private Integer defectId;

    /**
     * 缺陷名称
     */
    @Excel(name = "bug名称")
    private String defectTitle;

    /**
     * 所关联的用户,哪个用户所产生的bug
     */
    @Excel(name = "开发人员的编号")
    private Integer userId;

    /**
     * 项目编号
     */
    @Excel(name = "项目编号")
    private String projectId;

    /**
     * 缺陷截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "bug截止日期")
    private String defectEndTime;

    /**
     * 1代码错误、2界面优化、3设计缺陷、4性能问题、5安全相关、6其他；
     */
    @Excel(name = "bug类型",readConverterExp = "1=代码错误,2=界面优化,3=设计缺陷,4=性能问题,5=安全相关,6=其他")
    private Integer defectType;

    /**
     * 0待处理、1已解决 2 全部
     */
    @Excel(name = "bug状态",readConverterExp = "0=待处理,1=已解决, 2=全部")
    private Integer defectStatus;

    /**
     * 严重程度1、2、3、4，数字越大越严重；
     */
    @Excel(name = "bug严重程度",readConverterExp = "1=轻微,2=尚可,3=严重,4=危险")
    private Integer defectSeverity;

    /**
     * 重新步骤
     */
    @Excel(name = "重新步骤")
    private String defectReproSteps;

    /**
     * 优先级,数字越大越靠前
     */
    @Excel(name = "bug优先级",readConverterExp = "1=不急,2=普通,3=紧急")
    private Integer defectPriority;

    /**
     * 文件Id
     */
    @Excel(name = "附件编号")
    private Integer fileId;


    /** 用户名称 */
    private String userName;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 文件名称
     */
    @Excel(name = "附件名称")
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;



    public Integer getDefectId() {
        return defectId;
    }

    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    public String getDefectTitle() {
        return defectTitle;
    }

    public void setDefectTitle(String defectTitle) {
        this.defectTitle = defectTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDefectEndTime() {
        return defectEndTime;
    }

    public void setDefectEndTime(String defectEndTime) {
        this.defectEndTime = defectEndTime;
    }

    public Integer getDefectType() {
        return defectType;
    }

    public void setDefectType(Integer defectType) {
        this.defectType = defectType;
    }

    public Integer getDefectStatus() {
        return defectStatus;
    }

    public void setDefectStatus(Integer defectStatus) {
        this.defectStatus = defectStatus;
    }

    public Integer getDefectSeverity() {
        return defectSeverity;
    }

    public void setDefectSeverity(Integer defectSeverity) {
        this.defectSeverity = defectSeverity;
    }

    public String getDefectReproSteps() {
        return defectReproSteps;
    }

    public void setDefectReproSteps(String defectReproSteps) {
        this.defectReproSteps = defectReproSteps;
    }

    public Integer getDefectPriority() {
        return defectPriority;
    }

    public void setDefectPriority(Integer defectPriority) {
        this.defectPriority = defectPriority;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Defect{" +
                "defectId=" + defectId +
                ", defectTitle='" + defectTitle + '\'' +
                ", userId=" + userId +
                ", projectId='" + projectId + '\'' +
                ", defectEndTime=" + defectEndTime +
                ", defectType=" + defectType +
                ", defectStatus=" + defectStatus +
                ", defectSeverity=" + defectSeverity +
                ", defectReproSteps='" + defectReproSteps + '\'' +
                ", defectPriority=" + defectPriority +
                ", fileId=" + fileId +
                ", userName='" + userName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
