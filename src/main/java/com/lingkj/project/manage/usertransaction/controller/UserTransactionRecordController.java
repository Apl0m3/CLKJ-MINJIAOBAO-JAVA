package com.lingkj.project.manage.usertransaction.controller;

import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.RedisUtils;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.operation.service.OperateDeliveryStaffService;
import com.lingkj.project.transaction.dto.*;
import com.lingkj.project.transaction.entity.TransactionRecord;
import com.lingkj.project.transaction.service.TransactionRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;


/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
@RestController
@RequestMapping("/manage/usertransactionrecord")
public class UserTransactionRecordController extends AbstractController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private TransactionRecordService transactionRecordService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:usertransactionrecord:list")
    public R list(TransactionReqDto reqDto, Page page) {
        R r = new R();
        PageUtils pageUtils = transactionRecordService.queryPage(reqDto, page);
        return r.put("page", pageUtils);

    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:usertransactionrecord:info")
    public R info(@PathVariable("id") Long id) {
        TransactionDetailRespDto map = transactionRecordService.getInfo(id);
        return R.ok().put("map", map);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:usertransactionrecord:save")
    public R save(@RequestBody TransactionRecord userTransactionRecord) {
        transactionRecordService.save(userTransactionRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:usertransactionrecord:update")
    public R update(@RequestBody TransactionRecord userTransactionRecord) {
        transactionRecordService.updateById(userTransactionRecord);

        return R.ok();
    }

    /**
     * 确认收款
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("manage:usertransactionrecord:updateStatus")
    public R updateStatus(@RequestParam("transactionId") String transactionId, @RequestParam("type") Integer type, HttpServletRequest request) {
        String redisKey = "receipt_" + transactionId + "_" + getSysUserId();
        if (redisUtils.exists(redisKey)) {
            return R.error(getMessage("common.repeat_click", request));
        } else {
            redisUtils.set(redisKey, transactionId, 60);
        }
        transactionRecordService.updateStatus(transactionId, getSysUserId(), type, request);
        redisUtils.delete(redisKey);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:usertransactionrecord:delete")
    public R delete(@RequestBody Long[] ids) {
        transactionRecordService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 查询设计师或供应商
     *
     * @param page
     * @param reqDto
     * @return
     */
    @GetMapping("/queryDelivery")
    @RequiresPermissions("manage:usertransactionrecord:queryDelivery")
    public R queryDeliveryList(Page page, TransactionDeliveryListReqDto reqDto) {
        PageUtils pageUtils = transactionRecordService.queryDeliveryList(page, reqDto);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 分配订单给设计师或供应商
     */
    @PostMapping("/delivery")
    @RequiresPermissions("manage:usertransactionrecord:delivery")
    public R delivery(@RequestBody TransactionDeliveryReqDto reqDto, HttpServletRequest request) {
        transactionRecordService.delivery(reqDto, getSysUserId(), request);
        return R.ok();
    }


    @GetMapping("/queryManuscriptInfo")
    @RequiresPermissions("manage:usertransactionrecord:queryManuscriptInfo")
    public R queryManuscriptInfo(Long transactionCommodityId, HttpServletRequest request) {
        Map<String, Object> map = transactionRecordService.queryManuscriptInfo(transactionCommodityId, request);
        return R.ok().put("info", map);
    }

    @PostMapping("/reviewManuscript")
    @RequiresPermissions("manage:usertransactionrecord:reviewManuscript")
    public R reviewManuscript(@RequestBody ReviewManuscriptReqDto reviewManuscriptReqDto, HttpServletRequest request) {
        transactionRecordService.reviewManuscript(reviewManuscriptReqDto, getSysUserId(), request);
        return R.ok();
    }


    @GetMapping("/monthlySales")
    @RequiresPermissions("manage:usertransactionrecord:monthlySales")
    public R monthlySales() {
        BigDecimal amount = transactionRecordService.monthlySales();
        return R.ok().put("amount", amount);
    }


}
