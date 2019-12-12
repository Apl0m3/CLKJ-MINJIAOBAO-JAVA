package com.lingkj.project.manage.operation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.operation.entity.OperatePartner;
import com.lingkj.project.operation.service.OperatePartnerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;


/**
 * 合作伙伴
 *
 * @author chenyongsong
 * @date 2019-09-20 14:44:16
 */
@RestController
@RequestMapping("manage/operate/partner")
public class OperatePartnerController {
    @Autowired
    private OperatePartnerService operatePartnerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("partner:operatepartner:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = operatePartnerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("partner:operatepartner:info")
    public R info(@PathVariable("id") Long id){
			OperatePartner operatePartner = operatePartnerService.getById(id);

        return R.ok().put("operatePartner", operatePartner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("partner:operatepartner:save")
    public R save(@RequestBody OperatePartner operatePartner){
        operatePartner.setCreateTime(new Date());
        operatePartner.setCreateBy(getSysUserId());

        operatePartnerService.saveOrUpdate(operatePartner);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("partner:operatepartner:update")
    public R update(@RequestBody OperatePartner operatePartner){
        operatePartner.setUpdateTime(new Date());
        operatePartner.setUpdateBy(getSysUserId());
			operatePartnerService.saveOrUpdate(operatePartner);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("partner:operatepartner:delete")
    public R delete(@RequestBody Long[] ids){
			operatePartnerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
