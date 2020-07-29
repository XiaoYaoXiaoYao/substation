package com.yunan.assignment.service.impl;

import com.yunan.assignment.domain.Software;
import com.yunan.assignment.mapper.SoftwareMapper;
import com.yunan.assignment.service.SoftwareService;
import com.yunan.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoftwareServiceImpl implements SoftwareService {
    @Autowired
    private SoftwareMapper softwareMapper;

    @Override
    public int insertSoftware(Software software) {
        return softwareMapper.insertSoftware(software);
    }

    @Override
    public int updateSoftware(Software software) {
        return softwareMapper.updateSoftware(software);
    }

    @Override
    public void deleteSoftwareById(String projectId) {
        softwareMapper.deleteSoftwareById(projectId);
    }

    @Override
    public void deleteSoftwareByIds(String projectIds) {
        softwareMapper.deleteSoftwareByIds(Convert.toStrArray(projectIds));
    }
}
