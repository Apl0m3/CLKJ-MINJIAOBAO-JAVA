package com.lingkj.project.manage.operation. controller ;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.operation.entity.OperateProject;
import com.lingkj.project.operation.service.ProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;



/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@RestController
@RequestMapping("/manage/project")
public class ProjectController extends AbstractController {
    @Autowired
    private ProjectService projectService;

    /**
     * 列表
     */
    @GetMapping("/list")
   @RequiresPermissions("manage:project:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = projectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
   @RequiresPermissions("manage:project:info")
    public R info(@PathVariable("id") Long id){
			OperateProject project = projectService.getById(id) ;

        return R.ok().put("project", project);
    }

    /**
     * 保存
     */
     @PostMapping("/save")
   @RequiresPermissions("manage:project:save")
    public R save(OperateProject project){
			projectService.saveOrUpdate(project,getSysUserId());

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
   @RequiresPermissions("manage:project:update")
    public R update(OperateProject project){
        project.setUpdateSysUserId(getSysUserId());
        project.setUpdateTime(new Date());
        projectService.updateById(project);
        return R.ok();
    }


}
