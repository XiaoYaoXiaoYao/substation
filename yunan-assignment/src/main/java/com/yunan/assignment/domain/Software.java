package com.yunan.assignment.domain;

import com.yunan.common.annotation.Excel;
import com.yunan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 manage_software
 * 
 * @author yunan
 * @date 2020-06-23
 */
public class Software extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 软件id */
    private Integer softwareId;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectId;

    /** 项目版本 */
    @Excel(name = "项目版本")
    private String softwareVersion;

    /** 项目SVN地址 */
    @Excel(name = "项目SVN地址")
    private String svnPath;

    public void setSoftwareId(Integer softwareId) 
    {
        this.softwareId = softwareId;
    }

    public Integer getSoftwareId() 
    {
        return softwareId;
    }
    public void setProjectId(String projectId) 
    {
        this.projectId = projectId;
    }

    public String getProjectId() 
    {
        return projectId;
    }
    public void setSoftwareVersion(String softwareVersion) 
    {
        this.softwareVersion = softwareVersion;
    }

    public String getSoftwareVersion() 
    {
        return softwareVersion;
    }
    public void setSvnPath(String svnPath) 
    {
        this.svnPath = svnPath;
    }

    public String getSvnPath() 
    {
        return svnPath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("softwareId", getSoftwareId())
            .append("projectId", getProjectId())
            .append("softwareVersion", getSoftwareVersion())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("svnPath", getSvnPath())
            .toString();
    }
}
