package com.yunan.assignment.service.impl;

import com.yunan.assignment.domain.Defect;
import com.yunan.assignment.domain.Project;
import com.yunan.assignment.mapper.DefectMapper;
import com.yunan.assignment.service.DefectService;
import com.yunan.assignment.utils.DateUtil;
import com.yunan.common.core.text.Convert;
import com.yunan.framework.util.ShiroUtils;
import com.yunan.system.domain.SysRole;
import com.yunan.system.domain.SysUser;
import com.yunan.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoyao
 * @date 2020/06/22
 */
@Service("defect")
public class DefectServiceImpl implements DefectService {

    @Resource
    private DefectMapper  defectMapper;

    @Autowired
    private ISysRoleService roleService;


    @Override
    public List<Defect> selectDefectList(Defect defect) {
        return defectMapper.selectDefectList(defect);
    }

    @Override
    public int insertDefect(Defect defect) {
        Date date = DateUtil.strToDate(defect.getDefectEndTime());
        System.out.println(new Date());
        if (date.compareTo(new Date())==-1) {

            return   2323;

        }

        return defectMapper.insertDefect(defect);
    }

    @Override
    public List<SysUser> selectAssociatedCoderList() {
        //根据当前登陆的用户的角色进行列表不同显示
        //实施关联的是测试,测试关联的是开发
        SysRole role = roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        //实施人员
        if (role.getRoleKey().equals("executor")) {
            return defectMapper.selectAssociatedTesterList();

        //测试人员
        }else{
            return defectMapper.selectAssociatedCoderList();
        }

    }

    @Override
    public List<Project> associatedProjectList() {
        return defectMapper.selectAssociatedProjectList();
    }

    @Override
    public List<SysUser> createByList() {
        return defectMapper.createByList();
    }

    @Override
    public List<SysUser> createByTesterList() {
        return defectMapper.createByTesterList();
    }

    @Override
    public String getPrincipalRoleKey() {
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        String roleKey=role.getRoleKey();

        if (roleKey.equals("grouper")) {

            return  roleKey;

        } else if (roleKey.equals("coder")) {

            return  roleKey;

        }
        return  roleKey;
    }

    @Override
    public List<SysUser> createByExecutorTesterList() {
        return defectMapper.createByExecutorTesterList();
    }

    @Override
    public int deleteDefect(String ids) {
        return defectMapper.deleteDefectByIds(Convert.toStrArray(ids));
    }

    @Override
    public Defect selectDefectById(Integer defectId) {
        return defectMapper.selectDefectById(defectId);
    }

    @Override
    public int updateDefect(Defect defect) {

        //当开发人员进行编辑操作的时候进行判断时间是否为空
        //开发人员进入编辑  传过来的时间为空
        if (defect.getDefectEndTime()==null) {
            return defectMapper.updateDefect(defect);

        }
        //对于修改的截止时间不能在当前日期之间
        //首先判断这个传过来的日期是不是被修改过,如果被修改过就要判断是不是在当前的日期之前
        //没有被修改过就直接保存
        //传过来的日期
        Date date = DateUtil.strToDate(defect.getDefectEndTime());
        Defect defectById = defectMapper.selectDefectById(defect.getDefectId());
        String defectEndTimeById = defectById.getDefectEndTime();
        //没有被修改过就直接保存
        if (defectEndTimeById.equals(defect.getDefectEndTime())) {
            return defectMapper.updateDefect(defect);
        }

        System.out.println(new Date());
        if (date.compareTo(new Date())==-1) {

            return   2323;

        }
        defect.getUserId();



        return defectMapper.updateDefect(defect);
    }

    @Override
    public List<Defect> selectSelfDefectList(Defect defect, Long userId) {
        return defectMapper.selectSelfDefectList(defect,userId);
    }

    @Override
    public List<Defect> selectCreateBySelfDefectList(Defect defect,String userName) {
        return defectMapper.selectCreateBySelfDefectList(defect,userName);
    }

    @Override
    public List<Defect> selectCreateByTesterSelfDefectList(Defect defect, String userName) {
        return defectMapper.selectCreateByTesterSelfDefectList(defect,userName);
    }

//    @Override
//    public List<SysUser> selectAssociatedTesterList() {
//        return defectMapper.selectAssociatedTesterList();
//    }
//
//    @Override
//    public List<Defect> selectCreateBySelfAndExecutorDefectList(Defect defect, String userName, Long userId) {
//
//        return defectMapper.selectCreateBySelfAndExecutorDefectList(defect,userName,userId);
//
//    }

    @Override
    public List<SysUser> showAssociatedUser(String projectId) {
        //根据当前登陆的用户的角色进行联级操作的关联用户的不同显示
        //实施人员登录,点击项目关联用户的是此项目下的测试人员,测试人员登录是此项目下关联的开发人员
        SysRole role = roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        //实施人员
        if (role.getRoleKey().equals("executor")) {

            List<SysUser> sysUserList = new ArrayList<>();
            List<Integer> list = new ArrayList<>();

            //先查询出 tester_id,在进行分割
            String  testerId= defectMapper.selectAssociatedTesterIdsInProjectList(projectId);
            //(127,128)
            String _string = testerId;
            String substring = _string.substring(0, _string.length());
            //以逗号分割，得出的数据存到 result 里面
            String[] result = substring.split(",");
            for (String r : result) {
                //System.out.println("分割结果是: " + r);
                list.add(Integer.parseInt(r));
            }
            for (Integer userId : list) {
                SysUser sysUser=defectMapper.selectUserByUserId(userId);
                sysUserList.add(sysUser);
            }
            return sysUserList;

        //测试人员
        }else{
            return defectMapper.selectAssociatedCoderInProjectList(projectId);
        }



        //return defectMapper.showAssociatedUser(projectId);
    }



    @Override
    public List<Defect> selectGrouperDefectList(Defect defect,Long userId) {
        return defectMapper.selectGrouperDefectList(defect,userId);
    }

    @Override
    public int isTester(Integer userId) {
        return defectMapper.isTester(userId);
    }

    @Override
    public Integer selectIfCreateByExecutor(String createBy) {
        return defectMapper.selectIfCreateByExecutor(createBy);

    }

    @Override
    public SysUser selectSysUserByUserId(Long userId) {
        return defectMapper.selectSysUserByUserId(userId);
    }

    @Override
    public List<Defect> selectExecutorDefectList(Defect defect, Long userId) {
        return defectMapper.selectExecutorDefectList(defect,userId);
    }

    @Override
    public List<Defect> selectDefectOfTesterList(Defect defect) {
        return defectMapper.selectDefectOfTesterList(defect);
    }


}
