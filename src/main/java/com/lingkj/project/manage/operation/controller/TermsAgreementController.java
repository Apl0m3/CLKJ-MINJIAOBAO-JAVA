package com.lingkj.project.manage.operation.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.operation.entity.OperateTermsAgreement;
import com.lingkj.project.operation.service.OperateTermsAgreementService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;


/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@RestController
@RequestMapping("/manage/termsagreement")
public class TermsAgreementController extends AbstractController {
    @Autowired
    private OperateTermsAgreementService operateTermsAgreementService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:termsagreement:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = operateTermsAgreementService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:termsagreement:info")
    public R info(@PathVariable("id") Long id) {
        OperateTermsAgreement termsAgreement = operateTermsAgreementService.getById(id) ;

        return R.ok().put("termsAgreement", termsAgreement);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:termsagreement:save")
    public R save(@RequestBody  OperateTermsAgreement termsAgreement, HttpServletRequest request) {
        operateTermsAgreementService.saveOrUpdateTermsAgreement(termsAgreement,getSysUserId(),request);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:termsagreement:update")
    public R update(@RequestBody OperateTermsAgreement termsAgreement, HttpServletRequest request) {
        operateTermsAgreementService.saveOrUpdateTermsAgreement(termsAgreement,getSysUserId(),request);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:termsagreement:delete")
    public R delete(@RequestBody Long[] ids) {
        operateTermsAgreementService.updateStatusByIds(Arrays.asList(ids));

        return R.ok();
    }

}
