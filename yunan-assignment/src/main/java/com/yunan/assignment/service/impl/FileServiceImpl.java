package com.yunan.assignment.service.impl;

import com.yunan.assignment.domain.File;
import com.yunan.assignment.domain.Project;
import com.yunan.assignment.mapper.FileMapper;
import com.yunan.assignment.service.FileService;
import com.yunan.common.constant.UserConstants;
import com.yunan.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;
    @Override
    public int insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    @Override
    public int updateFile(File file) {
        return fileMapper.updateFile(file);
    }

    @Override
    public File selectFileByFile(File file) {
        return fileMapper.selectFileByFile(file);
    }

    @Override
    public List<File> selectFileByProjectId(String projectId) {
        return fileMapper.selectFileByProjectId(projectId);
    }

    @Override
    public List<File> selectFileByProjectIdAndFileType(String projectId, Long fileType) {
        return fileMapper.selectFileByProjectIdAndFileType(projectId,fileType);
    }

    @Override
    public void deleteFileById(String projectId) {
        fileMapper.deleteFileById(projectId);
    }

    @Override
    public void deleteFileByIds(String projectIds) {
        fileMapper.deleteFileByIds(Convert.toStrArray(projectIds));
    }

    @Override
    public String checkFileNameUnique(String fileName) {
        File fileInfo = fileMapper.checkFileNameUnique(fileName);
        if(fileInfo != null){
            //不唯一
            return UserConstants.FILE_NAME_NOT_UNIQUE;
        }else{
            return UserConstants.FILE_NAME_UNIQUE;
        }
    }

    @Override
    public List<File> selectFileByProjectIdAndFileTypeAndLevel(String projectId, Long fileType, int file_level) {
        return fileMapper.selectFileByProjectIdAndFileTypeAndLevel(projectId,fileType,file_level);
    }
}
