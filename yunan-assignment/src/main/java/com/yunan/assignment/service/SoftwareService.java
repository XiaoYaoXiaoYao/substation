package com.yunan.assignment.service;

import com.yunan.assignment.domain.Software;

/**
 * 软件版本Service接口
 * 
 * @author yunan
 * @date 2020-06-21
 */
public interface SoftwareService
{

    /**
     * 新增软件版本
     * 
     * @param software
     * @return 结果
     */
    public int insertSoftware(Software software);

    /**
     * 修改软件版本
     * @param software
     * @return
     */
    public int updateSoftware(Software software);
    /**
     * 删除软件版本
     *
     * @param projectId 项目ID
     * @return 结果
     */
    public void deleteSoftwareById(String projectId);

    /**
     * 批量删除软件版本
     *
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
    public void deleteSoftwareByIds(String projectIds);

}
