package com.lingkj.project.manage.operation.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.operation.entity.OperatePaymentMethod;
import com.lingkj.project.operation.service.PaymentMethodService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;


/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/paymentmethod")
public class PaymentMethodController extends AbstractController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:paymentmethod:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = paymentMethodService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:paymentmethod:info")
    public R info(@PathVariable("id") Long id) {
        OperatePaymentMethod operatePaymentMethod = paymentMethodService.queryInfo(id);

        return R.ok().put("paymentMethod", operatePaymentMethod);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:paymentmethod:save")
    public R save(OperatePaymentMethod operatePaymentMethod, HttpServletRequest request) {
        paymentMethodService.saveOrUpdate(operatePaymentMethod, getSysUserId(),request);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:paymentmethod:update")
    public R update(@RequestBody OperatePaymentMethod operatePaymentMethod) {
        paymentMethodService.updateInfo(operatePaymentMethod);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("manage:paymentmethod:delete")
    public R delete(@RequestBody Long[] ids) {
        paymentMethodService.updateStatusByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/disableEnable")
    @RequiresPermissions("manage:paymentmethod:disableEnable")
    public R disableEnable(@RequestParam("id") Long id) {
        paymentMethodService.disableEnable(id);

        return R.ok();
    }

}
