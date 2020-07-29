package com.yunan.web.controller.manage;

import com.yunan.assignment.domain.Defect;
import com.yunan.assignment.service.DefectService;
import com.yunan.assignment.service.FileService;
import com.yunan.common.annotation.Log;
import com.yunan.common.core.controller.BaseController;
import com.yunan.common.core.domain.AjaxResult;
import com.yunan.common.core.page.TableDataInfo;
import com.yunan.common.enums.BusinessType;
import com.yunan.common.exception.job.TaskException;
import com.yunan.common.utils.poi.ExcelUtil;
import com.yunan.framework.util.ShiroUtils;
import com.yunan.system.domain.SysRole;
import com.yunan.system.domain.SysUser;
import com.yunan.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * bug列表
 *
 * @author xiaoyao
 * @date 2020/06/22
 * 未完成
 */
@Api("实施关联给测试的BUG信息管理")
@Controller
@RequestMapping("/assignment/executor")
public class DefectExecutorController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private String prefix = "assignment/executor";

    @Autowired
    private DefectService defectService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ISysRoleService roleService;



    @RequiresPermissions("assignment:executor:view")
    @GetMapping()
    public String executor() {
        return "assignment/executor/defect_executor";
    }



    /**
     * 查询bug列表
     */
    @ApiOperation("获取BUG列表")
    @RequiresPermissions("assignment:executor:list")
    @ResponseBody
    @PostMapping("/list")
    public TableDataInfo list(Defect defect) {
//        //根据当前登陆的用户的角色进行列表不同显示
        SysUser user = ShiroUtils.getSysUser();
        startPage();
        List<Defect> list = defectService.selectExecutorDefectList(defect,user.getUserId());
        log.info("list----------:" + list);
        return getDataTable(list);

    }





    /**
     * 跳转编辑bug页面
     */
    @GetMapping("/edit/{defectId}")
    public String edit(@PathVariable("defectId") Integer defectId, ModelMap mmap)
    {
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        Defect defect = defectService.selectDefectById(defectId);
        mmap.put("defect", defect);
        mmap.put("roleKey",role.getRoleKey());
        log.info("defect:"+defect.toString());
        return prefix + "/edit";
    }







    /**
     * 修改bug
     */
    @ApiOperation("修改bug")
    @RequiresPermissions("assignment:executor:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Defect defect) throws SchedulerException, TaskException
    {
        return toAjax(defectService.updateDefect(defect));
    }


    /**
     * 导出bug列表
     */
    @RequiresPermissions("assignment:executor:export")
    @Log(title = "【请填写defect功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Defect defect)
    {
        SysUser user = ShiroUtils.getSysUser();
        List<Defect> list = defectService.selectExecutorDefectList(defect,user.getUserId());
        ExcelUtil<Defect> util = new ExcelUtil<Defect>(Defect.class);
        return util.exportExcel(list, "defect");
    }

}
