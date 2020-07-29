package com.yunan.assignment.service;

import com.yunan.assignment.domain.Defect;
import com.yunan.assignment.domain.Project;
import com.yunan.system.domain.SysUser;

import java.util.List;

/**
 * @author  xiaoyao
 * @date 2020/06/22
 */
public interface DefectService {
    /**
     *
     * @param defect
     * @return  bug的list集合
     */
    List<Defect> selectDefectList(Defect defect);

    /**
     *
     * @param defect
     * @return  新增bug
     */
    int insertDefect(Defect defect);

    /**
     *
     * @return  条件搜索框中的<关联的开发人员>的列表
     */
    List<SysUser> selectAssociatedCoderList();

    /**
     *
     * @return 所属的项目列表
     */
    List<Project> associatedProjectList();


    /**
     *
     * @return 创建者的列表(开发经理登陆时)
     */
    List<SysUser> createByList();

    /**
     *
     * @return 创建者的列表(开发人员登陆时)
     */
    List<SysUser> createByTesterList();


     String getPrincipalRoleKey();

    /**
     *
     * @return 创建者(实施给测试的关联的列表)
     */
    List<SysUser> createByExecutorTesterList();

    /**
     *
     * @param ids bug的id
     * @return 删除bug
     */
    int deleteDefect(String ids);

    /**
     *
     * @param defectId  回显的defectId
     * @return  回显的bug数据
     */
    Defect selectDefectById(Integer defectId);


    /**
     *
     * @param defect  修改的bug数据
     * @return
     */
    int updateDefect(Defect defect);


    /**
     * 根据当前的登陆的用户查询出自己的列表
     * @param defect
     * @param userId
     * @return
     */
    List<Defect> selectSelfDefectList(Defect defect, Long userId);


    /**
     * 实施人员的bug列表
     * @param defect
     * @param userName
     * @return
     */
    List<Defect> selectCreateBySelfDefectList(Defect defect,String userName);


    /**
     *
     * @param defect
     * @param userName
     * @return
     */
    List<Defect> selectCreateByTesterSelfDefectList(Defect defect,String userName);


    /**
     *
     * @return 实施人员下的关联用户(测试人员)的列表
     */
    //List<SysUser> selectAssociatedTesterList();

    /**
     *
     * @return 测试人员的bug列表
     */

    //List<Defect> selectCreateBySelfAndExecutorDefectList(Defect defect, String userName, Long userId);


    /**
     * 根据项目id查询项目下的关联的用户
     * 1.实施人员登录的时候,关联的用户是测试人员
     * 2.测试人员登录的时候,关联的用户是开发人员
     *
     * @param projectId
     * @return
     */
    List<SysUser> showAssociatedUser(String projectId);


    /**
     * 查询开发经理登陆时的bug列表
     * @param defect
     * @return
     */
    List<Defect> selectGrouperDefectList(Defect defect,Long userId);

    /**
     * 测试人员想要编辑实施人员的bug时进行跳转编辑页面的判断
     * @return
     * @param userId
     */
    int isTester(Integer userId);


    /**
     * 判断是不是由实施人员创建的bug
     * @param createBy
     * @return
     */
    Integer selectIfCreateByExecutor(String createBy);

    SysUser selectSysUserByUserId(Long userId);


    //查询当前的测试登陆的实施给测试的bug
    List<Defect> selectExecutorDefectList(Defect defect, Long userId);


    //部门经理显示的bug列表(只显示测试创建的bug)
    List<Defect> selectDefectOfTesterList(Defect defect);
}

