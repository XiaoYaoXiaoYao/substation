package com.yunan.assignment.mapper;

import com.yunan.assignment.domain.Defect;
import com.yunan.assignment.domain.Project;
import com.yunan.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoyao
 */
public interface DefectMapper {
    /**
     * @param defect 为了条件查询使用
     * @return bug列表
     */
    List<Defect> selectDefectList(@Param("defect") Defect defect);


    /**
     *
     * @param defect  新增的bug
     * @return
     */
    int insertDefect(Defect defect);


    /**
     *
     * @return  查询bug的关联的开发人员的列表
     */
    List<SysUser> selectAssociatedCoderList();


    /**
     *
     * @return 查询所属的项目的列表
     */
    List<Project> selectAssociatedProjectList();

    /**
     *
     * @return  查询创建者的列表
     */
    List<SysUser> createByList();

    int deleteDefectByIds(String[] ids);

    Defect selectDefectById(Integer defectId);

    int updateDefect(Defect defect);

    /**
     * 根据当前登陆的用户查询出自己bug列表
     * @param defect
     * @param userId
     * @return
     */
    List<Defect> selectSelfDefectList(@Param("defect") Defect defect, @Param("userId") Long userId);


    /**
     * 测试人员的bug列表
     * @param defect
     * @param userName
     * @return
     */
    List<Defect> selectCreateBySelfDefectList(@Param("defect")Defect defect,  @Param("userName")String userName);

    List<SysUser> selectAssociatedTesterList();

    List<Defect> selectCreateBySelfAndExecutorDefectList(@Param("defect")Defect defect, @Param("userName")String userName, @Param("userId")Long userId);

    /**
     *
     * @param defect
     * @param userName
     * @return
     */
    List<Defect> selectCreateByTesterSelfDefectList(@Param("defect")Defect defect, @Param("userName")String userName);

    /**
     * 查询出项目下面的(开发经理下面的)所有人员
     * @param projectId
     * @return
     */
    List<SysUser> showAssociatedUser(@Param("projectId") String projectId);

    List<Defect> selectGrouperDefectList(@Param("defect")Defect defect, @Param("userId")Long userId);

    /**
     * 根据点击的项目id查询出项目中的测试人员
     * @param projectId  点击的项目id
     * @return
     */
    List<SysUser> selectAssociatedTesterInProjectList(@Param("projectId")String projectId);


    /**
     * 根据点击的项目id查询出项目中的开发人员
     * @param projectId  点击的项目id
     * @return
     */
    List<SysUser> selectAssociatedCoderInProjectList(@Param("projectId")String projectId);


    /**
     * 根据点击的项目id查询出项目中的测试人员的ids的字符串(127,128)
     * @param projectId  点击的项目id
     * @return
     */
    String  selectAssociatedTesterIdsInProjectList(@Param("projectId")String projectId);

    SysUser selectUserByUserId(@Param("userId")Integer userId);

    /**
     * 根据传来的关联的用户id判断是否要跳转编辑页面
     * @param userId
     * @return
     */
    int isTester(Integer userId);


    /**
     * 判断是不是由实施人员创建的bug
     * @param createBy
     * @return
     */
    Integer selectIfCreateByExecutor(String createBy);

    SysUser selectSysUserByUserId(Long userId);

    List<Defect> selectExecutorDefectList(@Param("defect")Defect defect, @Param("userId")Long userId);

    List<SysUser> createByExecutorTesterList();

    List<SysUser> createByTesterList();

    List<Defect> selectDefectOfTesterList(@Param("defect")Defect defect);



//    /**
//     *
//     * @param projectIds 项目列表id数组
//     * @return 返回的多少条记录
//     */
//    int deleteProjectByids(String[] projectIds);
}
