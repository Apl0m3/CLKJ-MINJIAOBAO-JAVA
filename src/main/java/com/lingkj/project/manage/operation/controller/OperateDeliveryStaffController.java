package com.lingkj.project.manage.operation.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.operation.entity.OperateDeliveryStaff;
import com.lingkj.project.operation.service.OperateDeliveryStaffService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 配送员信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
@RestController
@RequestMapping("/manage/operate/deliveryStaff")
public class OperateDeliveryStaffController extends AbstractController {
    @Autowired
    private OperateDeliveryStaffService operateDeliveryStaffService;
    @Autowired
    private MessageUtils messageUtils;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("manage:deliverystaff:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = operateDeliveryStaffService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("manage:deliverystaff:info")
    public R info(@PathVariable("id") Long id) {
        OperateDeliveryStaff operateDeliveryStaff = operateDeliveryStaffService.getById(id);

        return R.ok().put("deliveryStaff", operateDeliveryStaff);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:deliverystaff:save")
    public R save(@RequestBody OperateDeliveryStaff operateDeliveryStaff) {
        operateDeliveryStaff.setCreateSysUserId(getSysUserId());
        operateDeliveryStaff.setCreateTime(new Date());
        operateDeliveryStaff.setStatus(0);
        operateDeliveryStaffService.save(operateDeliveryStaff);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:deliverystaff:update")
    public R update(@RequestBody OperateDeliveryStaff operateDeliveryStaff) {
        operateDeliveryStaff.setUpdateSysUserId(getSysUserId());
        operateDeliveryStaff.setUpdateTime(new Date());
        operateDeliveryStaffService.updateById(operateDeliveryStaff);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:deliverystaff:delete")
    public R delete(@RequestBody Long[] ids, HttpServletRequest request) {
        Assert.isNull(ids, messageUtils.getMessage("manage.operate.delivery.staff.delete.error", request));
        operateDeliveryStaffService.updateStatusBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
