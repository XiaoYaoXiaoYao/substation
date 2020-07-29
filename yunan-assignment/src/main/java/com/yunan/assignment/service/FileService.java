package com.yunan.assignment.service;

import com.yunan.assignment.domain.File;
import com.yunan.assignment.domain.Project;

import java.util.List;

/**
 * 文件Service接口
 * 
 * @author yunan
 * @date 2020-06-21
 */
public interface FileService
{

    /**
     * 新增文件上传
     * 
     * @param file
     * @return 结果
     */
    public int insertFile(File file);
    /**
     * 修改文件上传
     * @param file
     */
    public int updateFile(File file);

    /**
     * 根据条件查询File
     * @param file
     * @return
     */
    File selectFileByFile(File file);
    /**
     * 根据项目的id查找相关的文件
     */
    List<File> selectFileByProjectId(String projectId);

    /**
     * @author:  kqk
     * @createtime:  2020/7/6 17:27
     * @desc: 根据项目名和filetype查找
     * @version: 1
     **/
    List<File> selectFileByProjectIdAndFileType(String projectId,Long fileType);
    /**
     * 删除附件
     *
     * @param projectId 项目ID
     * @return 结果
     */
    public void deleteFileById(String projectId);

    /**
     * 批量删除附件
     *
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
    public void deleteFileByIds(String projectIds);
    /**
     * 校验文件名
     *
     * @param fileName 文件信息
     * @return 结果
     */
    public String checkFileNameUnique(String fileName);

    List<File> selectFileByProjectIdAndFileTypeAndLevel(String projectId, Long fileType, int file_level);
}
