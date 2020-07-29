package com.yunan.assignment.domain;

import com.yunan.common.annotation.Excel;
import com.yunan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 manage_file
 * 
 * @author yunan
 * @date 2020-06-23
 */
public class File extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件id */
    private Long fileId;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 1需求,2设计,3开发,4测试 */
    @Excel(name = "1需求,2设计,3开发,4测试")
    private Long fileType;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件级别：1项目级别包括需求文档、详细设计文档、操作文档、安装文档、维护文档；2任务级别，包括测试用例、图片、文字，开发图片 */
    @Excel(name = "文件级别：1项目级别包括需求文档、详细设计文档、操作文档、安装文档、维护文档；2任务级别，包括测试用例、图片、文字，开发图片")
    private String fileLevel;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectId;

    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileType(Long fileType) 
    {
        this.fileType = fileType;
    }

    public Long getFileType() 
    {
        return fileType;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setFileLevel(String fileLevel) 
    {
        this.fileLevel = fileLevel;
    }

    public String getFileLevel() 
    {
        return fileLevel;
    }
    public void setProjectId(String projectId) 
    {
        this.projectId = projectId;
    }

    public String getProjectId() 
    {
        return projectId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("fileName", getFileName())
            .append("fileType", getFileType())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("createTime", getCreateTime())
            .append("filePath", getFilePath())
            .append("fileLevel", getFileLevel())
            .append("projectId", getProjectId())
            .toString();
    }
}
