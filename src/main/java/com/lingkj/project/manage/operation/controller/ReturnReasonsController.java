package com.lingkj.project.manage.operation.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.lingkj.project.operation.entity.ReturnReasons;
import com.lingkj.project.operation.service.ReturnReasonsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;


/**
 * 退款原因
 *
 * @author chenyongsong
 * @date 2019-09-19 16:11:50
 */
@RestController
@RequestMapping("manage/operate/returnreasons")
public class ReturnReasonsController {
    @Autowired
    private ReturnReasonsService returnReasonsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("returnreasons:returnreasons:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = returnReasonsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("returnreasons:returnreasons:info")
    public R info(@PathVariable("id") Long id){
			ReturnReasons returnReasons = returnReasonsService.getById(id);

        return R.ok().put("returnReasons", returnReasons);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("returnreasons:returnreasons:save")
    public R save(@RequestBody ReturnReasons returnReasons){
        returnReasons.setCreateBy(getSysUserId());
        returnReasons.setCreateTime(new Date());
        returnReasonsService.save(returnReasons);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("returnreasons:returnreasons:update")
    public R update(@RequestBody ReturnReasons returnReasons){
        returnReasons.setUpdateTime(new Date());
        returnReasons.setUpdateBy(getSysUserId());
        returnReasonsService.updateById(returnReasons);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("returnreasons:returnreasons:delete")
    public R delete(@RequestBody Long[] ids){
        returnReasonsService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
