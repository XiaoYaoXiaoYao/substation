package com.yunan.assignment.mapper;

import com.yunan.assignment.domain.Software;

/**
 * 项目Mapper接口
 * 
 * @author yunan
 * @date 2020-06-21
 */
public interface SoftwareMapper
{
    /**
     * 添加软件版本
     * @param software
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
    public void deleteSoftwareByIds(String[] projectIds);


}
