package com.yunan.web.controller.manage;

import com.yunan.assignment.domain.Defect;
import com.yunan.assignment.domain.File;
import com.yunan.assignment.domain.Project;
import com.yunan.assignment.service.DefectService;
import com.yunan.assignment.service.FileService;
import com.yunan.common.annotation.Log;
import com.yunan.common.config.Global;
import com.yunan.common.core.controller.BaseController;
import com.yunan.common.core.domain.AjaxResult;
import com.yunan.common.core.page.TableDataInfo;
import com.yunan.common.enums.BusinessType;
import com.yunan.common.exception.job.TaskException;
import com.yunan.common.utils.file.FileUploadUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bug列表
 *
 * @author xiaoyao
 * @date 2020/06/22
 * 未完成
 */
@Api("BUG信息管理")
@Controller
@RequestMapping("/assignment/defect")
public class ManageDefectController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private String prefix = "assignment/defect";

    @Autowired
    private DefectService defectService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ISysRoleService roleService;

    @RequiresPermissions("assignment:defect:view")
    @GetMapping()
    public String notice() {
        return prefix + "/defect";
    }
    /**
     * 查询bug列表
     */
    @ApiOperation("获取BUG列表")
    @RequiresPermissions("assignment:defect:list")
    @ResponseBody
    @PostMapping("/list")
    public TableDataInfo list(Defect defect) {
        //根据当前登陆的用户的角色进行列表不同显示
        SysUser user = ShiroUtils.getSysUser();
        SysRole role = roleService.selectRoleKeyByUserId(user.getUserId());
        //开发经理
        if (role.getRoleKey().equals("grouper")) {
            startPage();
            List<Defect> list = defectService.selectGrouperDefectList(defect,user.getUserId());
            log.info("list----------:" + list);
            return getDataTable(list);

            //开发人员
        }else if(role.getRoleKey().equals("coder")){

            startPage();
            List<Defect> list = defectService.selectSelfDefectList(defect,user.getUserId());
            log.info("list----------:" + list);
            return getDataTable(list);


            //部门经理
        }else if(role.getRoleKey().equals("pm")) {

            startPage();
            List<Defect> list = defectService.selectDefectOfTesterList(defect);
            log.info("list----------:" + list);
            return getDataTable(list);


            //实施人员
        }else if(role.getRoleKey().equals("executor")){

            startPage();
            List<Defect> list = defectService.selectCreateBySelfDefectList(defect,user.getUserName());
            log.info("list----------:" + list);
            return getDataTable(list);

            //管理员
        }
//        else if(role.getRoleKey().equals("admin")){
//            startPage();
//            List<Defect> list = defectService.selectDefectList(defect);
//            log.info("list----------:" + list);
//            return getDataTable(list);
//
//            //测试人员
//        }
        else{
            startPage();
            List<Defect> list = defectService.selectCreateByTesterSelfDefectList(defect,user.getUserName());
            log.info("list----------:" + list);
            return getDataTable(list);
        }

    }




    /**
     * 跳转新增bug页面
     */
    @GetMapping("/add")
    public String add(Defect defect)
    {
        return prefix + "/add";
    }




    @ApiOperation("新增bug")
    @RequiresPermissions("assignment:defect:add")
    @ResponseBody
    @PostMapping("/add")
    public AjaxResult addSave(@Validated Defect defect) throws SchedulerException, TaskException
    {
        int count = defectService.insertDefect(defect);
        //System.out.println(count);
        if (count==2323) {

            return   AjaxResult.error("所选的截止日期不能是当前日期之前!");
        }
        return AjaxResult.success(count);
    }

    @ApiOperation("新增bug通过实施人员修改的")
    @RequiresPermissions("assignment:defect:addSaveByExecutor")
    @ResponseBody
    @PostMapping("/addSaveByExecutor")
    public AjaxResult addSaveByExecutor(@RequestBody Defect defect) throws SchedulerException, TaskException
    {

        Long userId = ShiroUtils.getUserId();
        SysUser sysUser=defectService.selectSysUserByUserId(userId);
        defect.setCreateBy(sysUser.getUserName());
        int count = defectService.insertDefect(defect);
        //System.out.println(count);
        if (count==2323) {

            return   AjaxResult.error("所选的截止日期不能是当前日期之前!");
        }
        return AjaxResult.success(count);
    }

    @ApiOperation("关联的人员列表")
    @RequiresPermissions("assignment:defect:associatedCoderList")
    @ResponseBody
    @GetMapping("/associatedCoderList")
    public AjaxResult associatedCoderList() {


            return AjaxResult.success(defectService.selectAssociatedCoderList());


    }


    @ApiOperation("下拉框所属项目列表")
    @RequiresPermissions("assignment:defect:associatedProjectList")
    @ResponseBody
    @GetMapping("/associatedProjectList")
    public AjaxResult associatedProjectList()
    {

        List<Project> associatedProjectList = defectService.associatedProjectList();
        return AjaxResult.success(associatedProjectList);
    }

    @ApiOperation("下拉框创建者列表")
    @RequiresPermissions("assignment:defect:createByList")
    @ResponseBody
    @GetMapping("/createByList")
    public AjaxResult createByList()
    {

        List<SysUser> createByList = defectService.createByList();
        return AjaxResult.success(createByList);
    }


    @ApiOperation("删除bug")
    @RequiresPermissions("assignment:defect:remove")
    @ResponseBody
    @PostMapping("/remove")
    public AjaxResult remove(String ids)
    {

        return AjaxResult.success(defectService.deleteDefect(ids));
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
//        //对于测试人员列表下的实施人员创建的bug是不能够修改(所属项目和关联的用户)的
//        if (role.getRoleKey().equals("tester")) {
//            //拿到回显的bug数据,取出关联用户userId,查询这个userId是否是测试的角色
//            //是测试的角色就不能进行编辑,不是可以编辑
//            Integer userId = defect.getUserId();
//            int flag = defectService.isTester(userId);
//            if (flag==1) {
//                return prefix + "/defect";
//            }
//        }
        log.info("defect:"+defect.toString());
        return prefix + "/edit";
    }


    //判断是不是测试人员
    @ApiOperation("是否有编辑权限")
    @RequiresPermissions("assignment:defect:ifTester")
    @ResponseBody
    @PostMapping("/ifTester")
    public AjaxResult ifTester()
    {

        long id = ShiroUtils.getUserId();
        int  id_= (int)id;
        Integer  count = defectService.isTester(id_);
        if (count==1) {
            //count是1,就是当前登录的是测试人员

                return AjaxResult.success(count);

        }
        //count是0,就是当前登录的  不是测试人员
        return AjaxResult.success("0");
    }


    //根据createBy来判断是不是实施人员
    @ApiOperation("判断是不是实施人员")
    @RequiresPermissions("assignment:defect:ifCreateByExecutor")
    @ResponseBody
    @PostMapping("/ifCreateByExecutor/{createBy}")
    public AjaxResult ifCreateByExecutor(@PathVariable String createBy)
    {

        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        Integer  count=defectService.selectIfCreateByExecutor(createBy);
        //对于测试人员列表下的实施人员创建的bug是不能够修改(所属项目和关联的用户)的
        if (count==1) {
            //是实施人员创建的bug

            return AjaxResult.success(Integer.toString(count));

        }
        return AjaxResult.success("0");
    }






    /**
     * 导出bug列表
                */
        @RequiresPermissions("assignment:defect:export")
        @Log(title = "【请填写defect功能名称】", businessType = BusinessType.EXPORT)
        @PostMapping("/export")
        @ResponseBody
        public AjaxResult export(Defect defect)
        {
        //根据当前登陆的用户的角色进行列表不同显示
        SysUser user = ShiroUtils.getSysUser();
        SysRole role = roleService.selectRoleKeyByUserId(user.getUserId());
        List<Defect> list = new ArrayList<>();
        //开发经理
        if (role.getRoleKey().equals("grouper")) {
             list = defectService.selectGrouperDefectList(defect,user.getUserId());
            log.info("list----------:" + list);

            //开发人员
        }else if(role.getRoleKey().equals("coder")){

             list = defectService.selectSelfDefectList(defect,user.getUserId());
            log.info("list----------:" + list);


            //部门经理
        }else if(role.getRoleKey().equals("pm")) {

             list = defectService.selectDefectOfTesterList(defect);
            log.info("list----------:" + list);


            //实施人员
        }else if(role.getRoleKey().equals("executor")){

             list = defectService.selectCreateBySelfDefectList(defect,user.getUserName());
            log.info("list----------:" + list);

        }
        else{
             list = defectService.selectCreateByTesterSelfDefectList(defect,user.getUserName());
            log.info("list----------:" + list);
        }
        ExcelUtil<Defect> util = new ExcelUtil<Defect>(Defect.class);
        return util.exportExcel(list, "defect");
    }

    /**
     * 所属的项目和关联的用户联级操作
     * 根据项目id查询相关的用户列表
     * 1.实施人员登录下是项目相关的测试人员的列表
     * 2.测试人员登录下是项目相关的开发人员的列表
     */
    @ApiOperation("修改bug")
    @RequiresPermissions("assignment:defect:showAssociatedUser")
    @ResponseBody
    @PostMapping("/showAssociatedUser/{projectId}")
    public AjaxResult showAssociatedUser(@RequestBody Project project)
    {

        //log.info(project.getProjectId()+"==========================");
        List<SysUser> associatedUser= defectService.showAssociatedUser(project.getProjectId());
        log.info(associatedUser.toString());
        return  AjaxResult.success(associatedUser);
    }


    /**
     * 修改bug
     */
    @ApiOperation("修改bug")
    @RequiresPermissions("assignment:defect:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Defect defect) throws SchedulerException, TaskException
    {
        //更新时候的截止日期在现在的日期之前
        if (defectService.updateDefect(defect)==2323) {
            return AjaxResult.error("所选的截止日期不能是当前日期之前!");

        }
        return toAjax(defectService.updateDefect(defect));
    }

    /**
     * 上传附件后修改bug,添加fileId
     */
    @ApiOperation("修改bug,添加fileId")
    @RequiresPermissions("assignment:defect:editByCondition")
    @PostMapping("/editByCondition")
    @ResponseBody
    public AjaxResult editByCondition(@RequestBody Defect defect) throws SchedulerException, TaskException
    {

        return toAjax(defectService.updateDefect(defect));
    }

    /**
     * 上传附件
     */
    @GetMapping("/upload/{defectId}")
    public String upload(@PathVariable("defectId") Integer defectId, ModelMap mmap)
    {
        Defect defect = defectService.selectDefectById(defectId);
        log.info("执行uploadControlelr-----------"+defectId);
        mmap.put("defect", defect);
        return prefix + "/upload";
    }

    /**
     * 新增上传附件
     */
    @RequiresPermissions("assignment:defect:upload")
    @Log(title = "上传附件", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadSave(String fileObj, MultipartFile file)
    {
        //log.info("fileObj:-------------"+fileObj);
        //将fileObj进行拆分
        String arr[] = fileObj.split("&");
        //将分别对应放置map中
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i <arr.length ; i++) {
            //防止remark没有输入时 会导致下标越界 将result的长度设置为2即可解决这一个问题
            String result[] = arr[i].split("=");
            if(result[0].equals("remark")){
                if(result.length==1){
                    result = new String[2];
                    result[0] = "remark";
                    result[1] = null;
                }
            }
            map.put(result[0],result[1]);
        }
        try
        {
            log.info("文件上传路径--------:"+ Global.getUploadPath());
            File fileInfo = new File();
            if (!file.isEmpty())
            {
                String filename = URLDecoder.decode(map.get("fileName"),"UTF-8");
                String filePath = FileUploadUtils.upload(Global.getUploadPath(), file);
                fileInfo.setProjectId(map.get("projectId"));
                fileInfo.setFileName(filename);
                fileInfo.setFileLevel(map.get("fileLevel"));
                fileInfo.setFileType((long) Integer.parseInt(map.get("fileType")));
                fileInfo.setRemark(map.get("remark"));
                fileInfo.setFilePath(filePath);
                fileInfo.setCreateBy(ShiroUtils.getLoginName());
                log.info("------整理完的file"+fileInfo);
                int i = fileService.insertFile(fileInfo);
                if (i==1) {
                    File selectFileByFile = fileService.selectFileByFile(fileInfo);
                    return AjaxResult.success(selectFileByFile);

                }

            }
            return error();
        }
        catch (Exception e)
        {
            log.error("上传文件路径失败！", e);
            return error(e.getMessage());
        }
    }






}
