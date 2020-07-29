package com.yunan.web.controller.manage;

import com.yunan.assignment.domain.File;
import com.yunan.assignment.domain.ManageTask;
import com.yunan.assignment.domain.Project;
import com.yunan.assignment.service.DefectService;
import com.yunan.assignment.service.FileService;
import com.yunan.assignment.service.ProjectService;
import com.yunan.assignment.service.TaskService;
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
import com.yunan.system.service.ISysUserService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:  kqk
 * @createtime:  2020/6/22 14:51
 * @desc: 我的任务controller
 * @version: 1
 **/
@Controller
@RequestMapping("/assignment/task")
public class MyTaskController extends BaseController {
    private String prefix = "assignment/task";
    private static final Logger log = LoggerFactory.getLogger(MyTaskController.class);
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DefectService defectService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ISysRoleService roleService;

    /**
     * @author:  kqk
     * @createtime:  2020/7/1 10:45
     * @desc: 页面跳转至task列表页面
     * @version: 1
     **/
    @RequiresPermissions("assignment:task:view")
    @GetMapping()
    public String notice()
    {
        return prefix + "/task";
    }

    /**
     * 上传任务附件
     */
    @RequiresPermissions("assignment:task:upload")
    @Log(title = "上传项目", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadSave(String fileObj, MultipartFile file)
    {
        SysUser user = ShiroUtils.getSysUser();
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        //System.out.println("fileObj:-------------"+fileObj);
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
            System.out.println("文件上传路径--------:"+ Global.getUploadPath());
            File fileInfo = new File();
            if (!file.isEmpty())
            {
                String filePath = FileUploadUtils.upload(Global.getUploadPath(), file);
                String filename = URLDecoder.decode(map.get("fileName"),"UTF-8");
                fileInfo.setProjectId(map.get("projectId"));
                fileInfo.setFileName(filename);
                if (role.getRoleKey().equals("pm")){
                    fileInfo.setFileLevel("1");
                    fileInfo.setFileType((long)1);//需求文档
                }
                if (role.getRoleKey().equals("grouper")){
                    fileInfo.setFileLevel("2");
                    fileInfo.setFileType((long)2);//设计文档
                }
                if(role.getRoleKey().equals("coder")){
                    fileInfo.setFileType((long)3);
                }
                if(role.getRoleKey().equals("tester")){
                    fileInfo.setFileType((long)4);
                }
                fileInfo.setFileLevel(map.get("fileLevel"));
                fileInfo.setRemark(map.get("remark"));
                fileInfo.setFilePath(filePath);
                fileInfo.setCreateBy(user.getUserName());
                System.out.println("------整理完的file"+fileInfo);
                int result=fileService.insertFile(fileInfo);

                if (result>0){
                    //文件上传后要在task中更新一下文件的编号
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

    /**
     * 上传附件后修改bug,添加fileId
     */
    @ApiOperation("修改task,添加fileId")
    @RequiresPermissions("assignment:task:editByCondition")
    @PostMapping("/editByCondition")
    @ResponseBody
    public AjaxResult editByCondition(@RequestBody ManageTask task) throws SchedulerException, TaskException
    {
        int result = taskService.updateTask(task);
        return toAjax(result);
    }


    /**
     * 页面跳转到新增任务页面
     * @return add页面
     */
    @GetMapping("/add")
    public String toAdd(ModelMap map)
    {
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        map .put("roleKey",role.getRoleKey());
        map.put("projects",projectService.selectMyProjectById(ShiroUtils.getUserId()));
        //1，根据开发经理的userid查询到开发经理的部门 user表和dept表连接查询
        SysUser user = userService.selectDeptIdByUserId(ShiroUtils.getUserId());
        // 2，再根据部门查询底下的相关人员
        map.put("users",userService.selectUserByDeptId(user.getDeptId()));
        log.info("users是---------"+projectService.selectMyProjectById(ShiroUtils.getUserId()));
        return prefix + "/add";
    }
    /**
     * 上传文件
     */
    @GetMapping("/upload/{taskId}")
    public String upload(@PathVariable("taskId") Long taskId, ModelMap mmap)
    {
        ManageTask task = taskService.selectTaskByTaskId(taskId);
        System.out.println("执行uploadControlelr-----------"+taskId);
        mmap.put("task", task);
        return prefix + "/upload";
    }
    /**
     * 页面跳转到修改任务页面
     * @return edit页面
     */
    @GetMapping("/edit/{taskId}")
    public String toEdit(@PathVariable("taskId") Long taskId, ModelMap mmap)
    {
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        mmap.put("roleKey",role.getRoleKey());
//      根据taskId查询到projectId
        ManageTask manageTask = taskService.selectProjectAndUserByTaskId(taskId);
//      根据userId查询到所有相关的项目信息
        mmap.put("projects",projectService.selectMyProjectById(ShiroUtils.getUserId()));
        log.info("projects--"+projectService.selectMyProjectById(ShiroUtils.getUserId()));
//        根据projectId查询project信息
        mmap.put("curProjects",projectService.selectProjectNameById(manageTask.getProjectId()));
        log.info("curProjects是---------"+projectService.selectProjectNameById(manageTask.getProjectId()));
//        System.out.println("projectname是---------"+projectService.selectProjectById(manageTask.getProjectId()));
//      根据taskId查询开发人员
        mmap.put("curUser",userService.selectUserById(manageTask.getUserId()));
        log.info("curUser是---------"+defectService.selectAssociatedCoderList());
        //1，根据开发经理的userid查询到开发经理的部门 user表和dept表连接查询
        SysUser user = userService.selectDeptIdByUserId(ShiroUtils.getUserId());
        // 2，再根据部门查询底下的相关人员
        mmap.put("users",userService.selectUserByDeptId(user.getDeptId()));
        log.info("users是---------"+userService.selectUserByDeptId(user.getDeptId()));
        mmap.put("task",taskService.selectTaskByTaskId(taskId));
        System.out.println("task---------"+taskService.selectTaskByTaskId(taskId));
        return prefix + "/edit";
    }
    /**
     * 查询任务列表
     */
    @RequiresPermissions("assignment:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ManageTask task)
    {
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        if (role.getRoleKey().equals("pm")){
            //通过角色判别显示对应的列表
            startPage();
            List<ManageTask> list = taskService.selectTaskList(task);
            System.out.println("list----------:"+list);
            return getDataTable(list);
        }else if(role.getRoleKey().equals("grouper")){
            startPage();
            List<ManageTask> list = taskService.selectGrouperTaskList(task,ShiroUtils.getUserId());
            return getDataTable(list);
        }else{
            startPage();
            List<ManageTask> list = taskService.selectMyselfTaskList(task,ShiroUtils.getUserId());
            System.out.println("mylist----------:"+list);
            return getDataTable(list);
        }
    }

    /**
     * 导出任务列表
     */
    @RequiresPermissions("assignment:task:export")
    @Log(title = "【请填写task功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ManageTask task)
    {
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        List<ManageTask> list = new ArrayList<>();
        if (role.getRoleKey().equals("pm")){
            //通过角色判别显示对应的列表
             list = taskService.selectTaskList(task);
        }else if(role.getRoleKey().equals("grouper")){
            list = taskService.selectGrouperTaskList(task,ShiroUtils.getUserId());
        }else{
            list = taskService.selectMyselfTaskList(task,ShiroUtils.getUserId());
        }
        ExcelUtil<ManageTask> util = new ExcelUtil<>(ManageTask.class);
        return util.exportExcel(list, "task");
    }

    /**
     * 任务新增
     * @param task
     * @return
     */
    @RequiresPermissions("assignment:task:add")
    @Log(title = "任务新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated ManageTask task)
    {
        System.out.println(task.getTaskStatus());
        SysUser user = ShiroUtils.getSysUser();
        task.setCreateBy(user.getUserName());
        task.setUpdateBy(user.getUserName());
        if(task.getTaskStartTime().compareTo(task.getTaskEndTime())==1){
            return error("注意！开始日期不得晚于截止日期！");
        }
//      任务进度默认是0
        task.setTaskProgress(0);
        task.setTaskStatus(1);
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(taskService.addTask(task));
    }

    /**
     * 删除任务
     */
    @RequiresPermissions("assignment:task:remove")
    @Log(title = "任务列表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Integer ids)
    {
        //检查角色，如果是项目经理在进度为零的时候才能修改数据
//        1、获取当前用户的编号查询角色
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        //如果是其他人员可修改
        ManageTask mt = taskService.selectTaskByTaskId((long)ids);
//        如果是项目经理，进度不为0，不可修改
        if (role.getRoleKey().equals("grouper") && 0!=mt.getTaskProgress()){
            return error("任务进行中，不可删除！");
        }
//        删除附件
        fileService.deleteFileByIds(String.valueOf(ids));
        return toAjax(taskService.deleteTaskByIds(ids));
    }

    /**
     * 修改保存项目
     */
    @RequiresPermissions("assignment:task:edit")
    @Log(title = "【修改任务】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ManageTask task) throws ParseException {
        SysUser user = ShiroUtils.getSysUser();
        //检查角色，如果是项目经理在进度为零的时候才能修改数据
//        1、获取当前用户的编号查询角色
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        //如果是其他人员可修改
        ManageTask mt = taskService.selectTaskByTaskId(task.getTaskId());
//        如果是项目经理，进度不为0，不可修改
        if (role.getRoleKey().equals("grouper") && 0!=mt.getTaskProgress()){
            return error("任务进行中，不可修改！");
        }
        if(task.getTaskStartTime().compareTo(task.getTaskEndTime())==1){
            return error("注意！开始日期不得晚于截止日期！");
        }
        System.out.println("task==========="+task);
        task.setCreateBy(mt.getCreateBy());
        task.setUpdateBy(user.getUserName());
        taskService.updateTask(task);
        // 在获取列表之前首先查询任务状态
//        查询任务进度
        ManageTask task_progress = taskService.selectTaskByTaskId(task.getTaskId());
//        任务状态：1创建任务,2是开始任务,3提交任务,4任务审核,5任务结束
        if (task_progress.getTaskProgress()<=0){
            task.setTaskStatus(1);
        }
        if (task_progress.getTaskProgress()>0&&task_progress.getTaskProgress()<100){
            task.setTaskStatus(2);
        }
        if (task_progress.getTaskProgress()>=100){
            task.setTaskStatus(5);
        }
        taskService.updateTask(task);

        //注释：刘玉杰
        //根据每一次修改任务进度进行判断
        //首先根据已经修改的进度查看项目的进度
        Integer proprocess = taskService.selectProjectProcess(task.getProjectId());
        System.out.println(task.getProjectId());
        System.out.println(proprocess+"项目进度===========");
        Project project = projectService.selectProjectById(task.getProjectId()) ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowtime = dateFormat.format(new Date());
        //当前时间<=预计结束时间  1、进度不为0 进行中 2、进度100 已完成
        if(dateFormat.parse(nowtime).getTime() <= dateFormat.parse(project.getProjectEndTime()).getTime()){
            if(proprocess==0){
                project.setProjectProcess(proprocess);
                project.setProjectStatus(1);
            } else if(proprocess!=0&&proprocess!=100){
                project.setProjectProcess(proprocess);
                project.setProjectStatus(2);
            }else if(proprocess==100){
                project.setProjectProcess(proprocess);
                project.setProjectStatus(4);
                project.setProjectRealEndTime(nowtime);
            }
        }else{
            //当前时间>预计结束时间  1、进度不为0 进行中已延期 2、进度100 延期完成
            if(proprocess==0){
                project.setProjectProcess(proprocess);
                project.setProjectStatus(1);
            }else if(proprocess!=0&&proprocess!=100){
                project.setProjectProcess(proprocess);
                project.setProjectStatus(3);
            }else if(proprocess==100){
                project.setProjectProcess(proprocess);
                project.setProjectStatus(5);
                project.setProjectRealEndTime(nowtime);
            }
        }
        return toAjax(projectService.updateProject(project));
    }


    @RequestMapping("/listByTaskId")
    @ResponseBody
    public TableDataInfo listByTaskId(int taskId)
    {
        startPage();
        List<ManageTask> list = taskService.selectTaskById(taskId);
        return getDataTable(list);
    }


}
