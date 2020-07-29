package com.yunan.assignment.mapper;

import com.yunan.assignment.domain.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件Mapper接口
 * 
 * @author yunan
 * @date 2020-06-21
 */
public interface FileMapper
{
    /**
     * 添加文件上传
     * @param file
     */
    public int insertFile(File file);
    /**
     * 修改文件上传
     * @param file
     */
    public int updateFile(File file);


    File selectFileByFile(File file);
    /**
     * 根据项目的id查找相关的文件
     */
    List<File> selectFileByProjectIdAndFileType(@Param("projectId") String projectId, @Param("fileType") Long fileType);
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
    public void deleteFileByIds(String[] projectIds);

    List<File> selectFileByProjectId(String projectId);

    /**
     * 校验文件名
     *
     * @param fileName 文件信息
     * @return 结果
     */
    public File checkFileNameUnique(String fileName);

    List<File> selectFileByProjectIdAndFileTypeAndLevel(@Param("projectId") String projectId, @Param("fileType") Long fileType, @Param("file_level") int file_level);
}
