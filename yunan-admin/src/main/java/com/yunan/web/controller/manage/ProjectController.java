package com.yunan.web.controller.manage;

import com.yunan.assignment.domain.File;
import com.yunan.assignment.domain.ManageTask;
import com.yunan.assignment.domain.Project;
import com.yunan.assignment.domain.Software;
import com.yunan.assignment.service.FileService;
import com.yunan.assignment.service.ProjectService;
import com.yunan.assignment.service.SoftwareService;
import com.yunan.assignment.service.TaskService;
import com.yunan.common.annotation.Log;
import com.yunan.common.config.Global;
import com.yunan.common.constant.UserConstants;
import com.yunan.common.core.controller.BaseController;
import com.yunan.common.core.domain.AjaxResult;
import com.yunan.common.core.page.TableDataInfo;
import com.yunan.common.enums.BusinessType;
import com.yunan.common.utils.file.FileUploadUtils;
import com.yunan.common.utils.poi.ExcelUtil;
import com.yunan.framework.util.ShiroUtils;
import com.yunan.system.domain.SysRole;
import com.yunan.system.domain.SysUser;
import com.yunan.system.service.ISysRoleService;
import com.yunan.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 【请填写功能名称】Controller
 *
 * @author yunan
 * @date 2020-06-21
 */
@Controller
@RequestMapping("/assignment/project")
public class ProjectController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private String prefix = "assignment/project";

    @Autowired
    private ProjectService projectService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private FileService fileService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;

    @RequiresPermissions("assignment:project:view")
    @GetMapping()
    public String project()
    {
        return prefix + "/project";
    }

    /**
     * 查询项目列表
     */
    @RequiresPermissions("assignment:project:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Project project)
    {
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        //各个开发经理只能看到旗下的项目
        if(role.getRoleKey().equals("grouper")){
            project.setUserId(ShiroUtils.getUserId().intValue());
            System.out.println(project+"1111111111111111111");
            startPage();
            List<Project> list = projectService.selectProjectList(project);
            return getDataTable(list);
        }else if(role.getRoleKey().equals("tester")){
            //各个测试人员只能看到旗下的项目
            int testerId = ShiroUtils.getUserId().intValue();
            //查出所有的list
            List<Project> list1 = projectService.selectProjectList(project);
            //最终显示的list
            startPage();
            List<Project> list = new ArrayList<Project>();
            for (Project project1:list1) {
                String arr[] = project1.getTesterId().split(",");
                for (int i = 0; i < arr.length ; i++) {
                    if(Integer.parseInt(arr[i])==testerId){
                        list.add(project1);
                    }
                }
            }
            return getDataTable(list);
        }else if(role.getRoleKey().equals("coder")){
//            long id= ShiroUtils.getUserId();
            startPage();
            List<Project> list = projectService.selectMyProjectList(project,ShiroUtils.getUserId());
//            List<Project> list = projectService.selectIndexProjectList((int)id);
            //各个开发人员只能看到旗下的项目
           /* Long coderId = ShiroUtils.getUserId();
            SysUser user = userService.selectDeptIdByUserId(coderId);
            System.out.println("9888888888888"+user);
            //只要user所在的部门和项目中开发经理所在的部门相同 就可以
            //查出所有的list
            List<Project> list1 = projectService.selectProjectList(project);
            //最终显示的list
            startPage();
            List<Project> list = new ArrayList<Project>();
            for (Project project1:list1) {
                System.out.println("id---------"+project1.getUserId().longValue());
                SysUser user1 = userService.selectDeptIdByUserId(project1.getUserId().longValue());
                System.out.println("999999999999"+user1);
                if(user1.getDeptId() == user.getDeptId()){
                    list.add(project1);
                }
            }*/
            return getDataTable(list);

        }else if(role.getRoleKey().equals("admin")){
            //管理员看不到项目
            return getDataTable(null);
        }else {
            startPage();
            List<Project> list = projectService.selectProjectList(project);
            return getDataTable(list);
        }
    }

    /**
     * 查询项目中的任务详细
     */
    @RequiresPermissions("assignment:task:list")
    @GetMapping("/detail/{projectId}")
    public String detail(@PathVariable("projectId") String projectId, ModelMap mmap)
    {
        mmap.put("project",projectService.selectProjectNameById(projectId));
        return "assignment/task/task_info";
    }
    /**
     * 导出项目列表
     */
    @RequiresPermissions("assignment:project:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Project project)
    {
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        List<Project> list = new ArrayList<>();
        //各个开发经理只能看到旗下的项目
        if(role.getRoleKey().equals("grouper")){
            project.setUserId(ShiroUtils.getUserId().intValue());
            list = projectService.selectProjectList(project);
        }else if(role.getRoleKey().equals("tester")){
            //各个测试人员只能看到旗下的项目
            int testerId = ShiroUtils.getUserId().intValue();
            //查出所有的list
            List<Project> list1 = projectService.selectProjectList(project);
            //最终显示的list
            for (Project project1:list1) {
                String arr[] = project1.getTesterId().split(",");
                for (int i = 0; i < arr.length ; i++) {
                    if(Integer.parseInt(arr[i])==testerId){
                        list.add(project1);
                    }
                }
            }
        }else if(role.getRoleKey().equals("coder")){
            long id= ShiroUtils.getUserId();
            list = projectService.selectIndexExportProject((int)id);

        }else if(role.getRoleKey().equals("admin")){
            list=null;
        }else {
            list = projectService.selectProjectList(project);
        }
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        return util.exportExcel(list, "project");
    }

    /**
     * 新增项目
     */
    @GetMapping("/add")
    public String add()
    {
        System.out.println("执行addControlelr-----------");
        return prefix + "/add";
    }

    /**
     * 新增保存项目
     */
    @RequiresPermissions("assignment:project:add")
    @Log(title = "新增项目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Project project, String softwareVersion, String svnPath) throws ParseException {
        SysUser sysUser = userService.selectUserByLoginName(ShiroUtils.getLoginName());
        System.out.println("软件的信息:------------->"+softwareVersion+!("".equals(softwareVersion.trim())));
        //@RequestParam(value="file",required = false) MultipartFile file 一直不行 只能这样了
        //如果softwareVersion和fileName不是null时 给软件版本表project_software添加数据
        if(softwareVersion!=null&&!("".equals(softwareVersion.trim()))){
            Software software = new Software();
            software.setSoftwareVersion(softwareVersion);
            //因为版本号不能为空，所以如果版本号为null 就算svnPath有值也是无法添加的
            if(!("".equals(svnPath.trim()))){
                software.setSvnPath(svnPath);
            }
            software.setProjectId(project.getProjectId());
            software.setCreateBy(sysUser.getUserName());
            software.setUpdateBy(sysUser.getUserName());
            //执行添加软版本的方法
            softwareService.insertSoftware(software);
        }
        //首先判断日期格式是否正确
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(project.getProjectStartTime());
            sdf.parse(project.getProjectEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return error("新增项目'" + project.getProjectName() + "'失败，日期格式不正确");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //如果预计结束时间小于预计开始时间 添加失败
            if(dateFormat.parse(project.getProjectStartTime()).getTime()>dateFormat.parse(project.getProjectEndTime()).getTime()){
                return error("新增项目'" + project.getProjectName() + "'失败，项目预计结束时间不能小于项目预计开始时间");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (UserConstants.PROJECT_CODE_NOT_UNIQUE.equals(projectService.checkProjectCodeUnique(project)))
        {
            return error("新增项目'" + project.getProjectName() + "'失败，项目编号已存在");
        }
        project.setCreateBy(sysUser.getUserName());

        //管理员不能添加项目
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        if(role.getRoleKey().equals("admin")){
            //管理员添加不了项目
            return error("新增项目'" + project.getProjectName() + "'失败，管理员不能添加");
        }

        int result = projectService.insertProject(project);
//        Date start = new SimpleDateFormat("yyyy:mm:dd").parse( project.getProjectStartTime());
//        Date end = new SimpleDateFormat("yyyy:mm:dd").parse( project.getProjectEndTime());
//        List<ManageTask> list = new ArrayList<>();
        ManageTask manageTask = new ManageTask();
        if (result>0){
                String arr[] = project.getTesterId().split(",");
                for (int i = 0; i < arr.length ; i++) {
                    manageTask.setTaskTitle(project.getProjectName());
                    manageTask.setProjectId(project.getProjectId());
                    manageTask.setProjectName(project.getProjectName());
                    manageTask.setTaskType("3");
                    manageTask.setTaskStatus(1);
                    manageTask.setUserId(Long.parseLong(arr[i]));
                    manageTask.setTaskStartTime(sdf.parse(project.getProjectStartTime()));
                    manageTask.setTaskEndTime(sdf.parse(project.getProjectEndTime()));
                    manageTask.setCreateBy(ShiroUtils.getLoginName());
                    manageTask.setUpdateBy(ShiroUtils.getLoginName());
                    manageTask.setRemark(project.getRemark());
                    manageTask.setTaskProgress(0);
                    ShiroUtils.clearCachedAuthorizationInfo();
                    taskService.addTask(manageTask);
                }
            }
        return toAjax(result);
    }

    /**
     * 上传项目
     */
    @GetMapping("/upload/{projectId}")
    public String upload(@PathVariable("projectId") String projectId, ModelMap mmap)
    {
        Project project = projectService.selectProjectById(projectId);
        System.out.println("执行uploadControlelr-----------"+projectId);
//        通过filetype分类获取文件 1需求,2设计,3开发,4测试
//
        List<File> fileList = fileService.selectFileByProjectIdAndFileType(projectId,(long)1);
        List<File> fileList1 = fileService.selectFileByProjectIdAndFileType(projectId,(long)2);
        List<File> fileList2 = fileService.selectFileByProjectIdAndFileType(projectId,(long)3);
        List<File> fileList3 = fileService.selectFileByProjectIdAndFileTypeAndLevel(projectId,(long)4,1);
        SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
        mmap .put("roleKey",role.getRoleKey());
        mmap.put("fileList", fileList);
        mmap.put("fileList1", fileList1);
        mmap.put("fileList2", fileList2);
        mmap.put("fileList3", fileList3);
        mmap.put("Project", project);
        return prefix + "/upload";
    }

    /**
     * 新增上传项目
     */
    @RequiresPermissions("assignment:project:upload")
    @Log(title = "上传项目", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadSave(String fileObj, MultipartFile file)
    {
        SysUser sysUser = userService.selectUserByLoginName(ShiroUtils.getLoginName());
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
            SysRole role =roleService.selectRoleKeyByUserId(ShiroUtils.getUserId());
            File fileInfo = new File();
            if (!file.isEmpty())
            {
                String filePath = FileUploadUtils.upload(Global.getUploadPath(), file);
                String filename = URLDecoder.decode(map.get("fileName"),"UTF-8").trim();
                fileInfo.setProjectId(map.get("projectId"));
                fileInfo.setFileName(filename);
//                fileInfo.setFileLevel(map.get("fileLevel"));
                if (role.getRoleKey().equals("pm")){
                    fileInfo.setFileType((long)1);
                }else if(role.getRoleKey().equals("tester")){
//                    fileInfo.setFileType((long) Integer.parseInt(map.get("fileType")));
                    fileInfo.setFileType((long)4);
                }else{
                    return error("无在项目中上传文件的权限！");
                }
                fileInfo.setFileLevel(map.get("fileLevel"));
                fileInfo.setRemark(map.get("remark"));
                fileInfo.setFilePath(filePath);
                fileInfo.setCreateBy(sysUser.getUserName());
                System.out.println("------整理完的file"+fileInfo);
                if(fileService.checkFileNameUnique(fileInfo.getFileName()).equals(UserConstants.FILE_NAME_NOT_UNIQUE)){
                    return toAjax(fileService.updateFile(fileInfo));
                }
                return toAjax(fileService.insertFile(fileInfo));
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
     * 修改项目
     */
    @GetMapping("/edit/{projectId}")
    public String edit(@PathVariable("projectId") String projectId, ModelMap mmap)
    {
        List<SysUser> users = userService.selectTestRole();
        //获得所有的测试人员之后 将测试人员的id和项目的testerId做比较 如果说一样了 就设置falg为true
        System.out.println("执行updateControlelr-----------"+projectId);
        Project project = projectService.selectProjectById(projectId);
        String arr[] = project.getTesterId().split(",");
        for (SysUser user : users){
            for (int i = 0; i < arr.length; i++) {
                if(user.getUserId()==Integer.parseInt(arr[i])){
                    user.setFlag(true);
                }
            }
            System.out.println("注意user的flag值 注意是不是true-----------"+user.getUserName()+user.isFlag()+user);
        }
        mmap.put("users", users);
        mmap.put("Project", project);
        return prefix + "/edit";
    }

    /**
     * 项目详情
     */
    @GetMapping("/pdetail/{projectId}")
    public String pdetail(@PathVariable("projectId") String projectId, ModelMap mmap)
    {
        List<SysUser> users = userService.selectTestRole();
        //获得所有的测试人员之后 将测试人员的id和项目的testerId做比较 如果说一样了 就设置falg为true
        System.out.println("执行updateControlelr-----------"+projectId);
        Project project = projectService.selectProjectById(projectId);
        String arr[] = project.getTesterId().split(",");
        for (SysUser user : users){
            for (int i = 0; i < arr.length; i++) {
                if(user.getUserId()==Integer.parseInt(arr[i])){
                    user.setFlag(true);
                }
            }
            System.out.println("注意user的flag值 注意是不是true-----------"+user.getUserName()+user.isFlag()+user);
        }
        mmap.put("users", users);
        mmap.put("Project", project);
        return prefix + "/detail";
    }

    /**
     * @author:  kqk
     * @createtime:  2020/7/22 10:01
     * @desc: 比较两个数组，找出不同的元素
     * @version: 1
     **/
    public static <T> List<T> compare(T[] t1, T[] t2) {
        List<T> list1 = Arrays.asList(t1); //将t1数组转成list数组
        List<T> list2 = new ArrayList<T>();//用来存放2个数组中不相同的元素
        for (T t : t2) {
            if (!list1.contains(t)) {
                list2.add(t);
            }
        }
        return list2;
    }

    /**
     * 修改保存项目
     */
    @RequiresPermissions("assignment:project:edit")
    @Log(title = "【修改项目】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Project project, String softwareVersion, String svnPath) throws ParseException {
        int result=0;
        if(null==project.getTesterId()){
            return error("请注意,测试人员不能为空！");
        }
        SysUser sysUser = userService.selectUserByLoginName(ShiroUtils.getLoginName());
        if(softwareVersion!=null&&!("".equals(softwareVersion.trim()))){
            Software software = new Software();
            software.setSoftwareVersion(softwareVersion);
            //因为版本号不能为空，所以如果版本号为null 就算svnPath有值也是无法添加的
            software.setSvnPath(svnPath);
            software.setProjectId(project.getProjectId());
            System.out.println("00000000000000000000000"+sysUser);
            software.setUpdateBy(sysUser.getUserName());
            //执行修改软版本的方法
            softwareService.updateSoftware(software);
        }
        //首先判断日期格式是否正确
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(project.getProjectStartTime());
            sdf.parse(project.getProjectEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return error("修改项目'" + project.getProjectName() + "'失败，日期格式不正确");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //如果预计结束时间小于预计开始时间 添加失败
            if(dateFormat.parse(project.getProjectStartTime()).getTime()>dateFormat.parse(project.getProjectEndTime()).getTime()){
                return error("修改项目'" + project.getProjectName() + "'失败，项目预计结束时间不能小于项目预计开始时间");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        log.info(project.getTesterId()+"testterId");
        project.setUpdateBy(sysUser.getUserName());
        //查询测试是否修改过
//        根据projectid获取到当前的测试人员
        Project project1 = projectService.selectProjectById(project.getProjectId());
//        这个是页面传回的测试人员的id
        String arr[] = project.getTesterId().split(",");
        Integer arr2[] = new Integer[arr.length];
        for(int i=0;i<arr.length;i++){
            arr2[i]=Integer.parseInt(arr[i]);
        }
//        这个是根据当前项目编号拿到的测试人员
        String arr1[] = project1.getTesterId().split(",");
        Integer arr4[] = new Integer[arr1.length];
        for(int i=0;i<arr1.length;i++){
            arr4[i]=Integer.parseInt(arr1[i]);
        }
        log.info("页面传回的测试人员的id"+Arrays.toString(arr));
        log.info("页面传回的测试人员的id2"+Arrays.toString(arr2));
        log.info("数据库中本来的的测试人员的id1"+Arrays.toString(arr1));
        log.info("数据库中本来的的测试人员的id4"+Arrays.toString(arr4));
        //如果以上都不成立则获取新的测试人员增加一条记录
        result=projectService.updateProject(project);
        if (result>0){
//            根据projectId查询testerId，再根据testerId修改userId
//            同时要修改测试中的任务的信息
            if (!Arrays.equals(arr, arr1)){//如果两者不一致
//                todo 如果人员发生变化，则找到对应的人获取到她的任务百分比更新一下
                ManageTask manageTask = new ManageTask();
                ManageTask task;
                if(arr.length==1&&arr1.length==1){
                    task =taskService.selectTaskbyUserIdAndProjectId(Long.parseLong(arr1[0]),project.getProjectId());
                    taskService.updateTaskByUserTesterIdAndProjectId(task.getTaskTitle(),task.getProjectId(),task.getProjectName(),"3",task.getTaskStatus(),Long.parseLong(arr[0]),
                            task.getTaskStartTime(),task.getTaskEndTime(),ShiroUtils.getLoginName(),ShiroUtils.getLoginName(),task.getRemark(),task.getTaskProgress(),task.getUserId());
                }else{
                    if (arr2.length<arr4.length){//页面传过来的比实际的少
                        List<Integer> list = compare(arr2,arr4);
                        for (Integer str : list) {
                            taskService.deleteTaskByUserId(str,project.getProjectId());
                        }
                    }else{
                        List<Integer> list1 = compare(arr4,arr2);
                        for (Integer str : list1) {
                            manageTask.setTaskTitle(project.getProjectName());
                            manageTask.setProjectId(project.getProjectId());
                            manageTask.setProjectName(project.getProjectName());
                            manageTask.setTaskType("3");
                            manageTask.setTaskStatus(1);
                            manageTask.setUserId((long)str);
                            manageTask.setTaskStartTime(sdf.parse(project.getProjectStartTime()));
                            manageTask.setTaskEndTime(sdf.parse(project.getProjectEndTime()));
                            manageTask.setCreateBy(ShiroUtils.getLoginName());
                            manageTask.setUpdateBy(ShiroUtils.getLoginName());
                            manageTask.setRemark(project.getRemark());
                            manageTask.setTaskProgress(0);
                            ShiroUtils.clearCachedAuthorizationInfo();
                            taskService.addTask(manageTask);
                        }
                    }
                }
            }else{//测试人员没变的话修改task中的其他信息
                //先查到任务，根据任务id
                //根据projectId和userid修改任务信息
                for (int i = 0; i < arr.length ; i++){
                    ManageTask mt = taskService.selectTaskbyUserIdAndProjectId(Long.parseLong(arr[i]),project.getProjectId());
                    log.info("mt的值："+mt);
                    taskService.updateTaskByUserIdAndProjectId(project.getProjectName(),project.getProjectId(),project.getProjectName(),"3",mt.getTaskStatus(),Long.parseLong(arr[i]),
                            sdf.parse(project.getProjectStartTime()),sdf.parse(project.getProjectEndTime()),
                            ShiroUtils.getLoginName(),ShiroUtils.getLoginName(),project.getRemark(),mt.getTaskProgress());
                }
            }
            return toAjax(result);
        }else {
            return error("修改失败！");
        }
    }

    /**
     * 删除项目
     */
    @RequiresPermissions("assignment:project:remove")
    @Log(title = "项目", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        //删除项目的同时删除其对应的版本
        softwareService.deleteSoftwareByIds(ids);
        //删除项目的同时删除其对应的附件
        fileService.deleteFileByIds(ids);
//        删除项目下的所有任务
        //1、根据projectid在task中查询到所有的任务（id和进度）
        List<ManageTask> TaskIdlist = taskService.selectProjectOfTaskByProjectId(ids);
        for(ManageTask task :TaskIdlist){
            if (task.getTaskProgress()!=0){
                return error("有任务在进行中，不可删除项目！");
            }else{
                long id =task.getTaskId();
                taskService.deleteTaskByIds((int) id);
            }
        }

        //2、所有的任删除务（如果有任务正在进行中无法删除）
        return toAjax(projectService.deleteProjectByIds(ids));
    }

    /**
     * 校验项目编码
     */
    @PostMapping("/checkProjectCodeUnique")
    @ResponseBody
    public String checkProjectCodeUnique(Project project)
    {
        return projectService.checkProjectCodeUnique(project);
    }

    /**
     * 校验文件名
     * @return
     */
    @PostMapping("/checkFileNameUnique")
    @ResponseBody
    public String checkFileNameUnique(String fileName) {
        if(fileService.checkFileNameUnique(fileName).equals(UserConstants.FILE_NAME_NOT_UNIQUE)){
            return "1";
        }else{
            return "2";
        }
    }
}
