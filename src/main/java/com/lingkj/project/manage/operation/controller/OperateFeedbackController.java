package com.lingkj.project.manage.operation.controller;

import java.util.Arrays;
import java.util.Map;

import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.operation.dto.OperateFeedBackReqDto;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lingkj.project.operation.entity.OperateFeedback;
import com.lingkj.project.operation.service.OperateFeedbackService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 意见反馈
 *
 * @author chenyongsong
 * @date 2019-07-24 10:58:16
 */
@RestController
@RequestMapping("manage/operatefeedback")
public class OperateFeedbackController extends AbstractController {
    @Autowired
    private OperateFeedbackService operateFeedbackService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("manage:operatefeedback:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = operateFeedbackService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("manage:operatefeedback:info")
    public R info(@PathVariable("id") Long id){
			OperateFeedback operateFeedback = operateFeedbackService.getById(id);

        return R.ok().put("operateFeedback", operateFeedback);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("manage:operatefeedback:save")
    public R save(@RequestBody OperateFeedback operateFeedback){
			operateFeedbackService.save(operateFeedback);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("manage:operatefeedback:update")
    public R update(@RequestBody OperateFeedback operateFeedback){
			operateFeedbackService.updateById(operateFeedback);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("manage:operatefeedback:delete")
    public R delete(@RequestBody Long[] ids){
			operateFeedbackService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 回复
     */
    @PostMapping("/reply")
    @RequiresPermissions("manage:operatefeedback:reply")
    public R reply(@RequestBody OperateFeedBackReqDto operateFeedBackReqDto, HttpServletRequest request){
        operateFeedbackService.reply(operateFeedBackReqDto,getSysUserId(),request);
        return R.ok();
    }

}
