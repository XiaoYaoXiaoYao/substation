package com.yunan.web.controller.index;

import com.yunan.assignment.domain.ManageTask;
import com.yunan.assignment.domain.Project;
import com.yunan.assignment.service.ProjectService;
import com.yunan.assignment.service.TaskService;
import com.yunan.common.core.controller.BaseController;
import com.yunan.common.core.page.TableDataInfo;
import com.yunan.framework.util.ShiroUtils;
import com.yunan.system.domain.SysRole;
import com.yunan.system.domain.SysUser;
import com.yunan.system.service.ISysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private ISysRoleService iSysRoleService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/main")
    /*@RequiresPermissions("index:index:view")*/
    public String toIndex(Model model){
        SysUser user = ShiroUtils.getSysUser();
        SysRole role=iSysRoleService.findRoleById(user.getUserId());
        long roleId=role.getRoleId();
        model.addAttribute("id" ,user.getUserId());
        if(roleId==100){
            return "index/indexAll";
        }else if(roleId==99) {
            return "index/indexDeptManager";
        }else if(roleId==103) {
            return "index/indexTester";
        }
        else{
            return "index/index";
            }

    }

    @RequestMapping("/projectList")
    /*@RequiresPermissions("index:project:view")*/
    @ResponseBody
    public TableDataInfo projectList(int id ){
        startPage();
        List<Project> list = projectService.selectIndexProjectList(id);
        return getDataTable(list);
    }


    @RequestMapping("/taskList")
    @ResponseBody
    public TableDataInfo taskList(long id ){
        startPage();
        List<ManageTask> list = taskService.selectIndexTaskList(id);
        return getDataTable(list);
    }




    //项目经理查询所有项目列表
    @RequestMapping("/projectListAll")
    @ResponseBody
    public TableDataInfo projectList(){
        startPage();
        List<Project> list = projectService.selectIndexProjectListAll();
        return getDataTable(list);
    }



    //项目经理查询所有任务列表
    @RequestMapping("/taskListAll")
    @ResponseBody
    public TableDataInfo taskList(){
        startPage();
        List<ManageTask> list = taskService.selectIndexTaskListAll();
        return getDataTable(list);
    }

    //开发经理查询小组的项目列表
    @RequestMapping("/projectListManage")
    @ResponseBody
    public TableDataInfo projectListManage(int id){
        startPage();
        List<Project> list = projectService.projectListManage(id);
        return getDataTable(list);
    }


    //开发经理查询小组任务列表
    @RequestMapping("/taskListManage")
    @ResponseBody
    public TableDataInfo taskListManage(long id){
        startPage();
        List<ManageTask> list = taskService.taskListManage(id);
        return getDataTable(list);
    }



    //测试人员查询项目列表
    @RequestMapping("/projectListTest")
    @ResponseBody
    public TableDataInfo projectListTest(int id){
        startPage();
        List<Project> list = projectService.projectListTest(id);
        return getDataTable(list);
    }

    //点击项目名称跳转详情
    @GetMapping("/detail/{projectId}")
    public String detail(@PathVariable("projectId") String projectId, ModelMap mmap)
    {
        mmap.put("project",projectService.selectProjectNameById(projectId));
        return "assignment/task/task_info";
    }

    //点击任务名称跳转详情
    @GetMapping("/detailTask/{taskId}")
    public String detailTask(@PathVariable("taskId") long taskId, ModelMap mmap){
        mmap.put("project",taskService.selectTaskById(taskId));
        mmap.put("taskId",taskId);
        return "assignment/task/task_info1";
    }
}
