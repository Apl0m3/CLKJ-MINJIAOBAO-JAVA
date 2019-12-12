package com.lingkj.project.manage.operation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.service.CommodityService;
import com.lingkj.project.operation.entity.OperateAdvertisement;
import com.lingkj.project.operation.service.OperateAdvertisementService;
import com.lingkj.project.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/advertisement")
public class OperateAdvertisementController {
    @Autowired
    private OperateAdvertisementService operateAdvertisementService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private MessageUtils messageUtils;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:advertisement:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = operateAdvertisementService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:advertisement:info")
    public R info(@PathVariable("id") Long id) {
        OperateAdvertisement advertisement = operateAdvertisementService.getById(id);
        if (advertisement.getJumpMode() == 3 && advertisement.getModule() != null && advertisement.getModule() == 3) {
            String url = advertisement.getUrl();
            QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
            wrapper.eq(StringUtils.isNotEmpty(url),"id",Integer.valueOf(url));
            Commodity commodity = commodityService.getOne(wrapper);
            advertisement.setCommodityName(commodity.getName());
        }
        return R.ok().put("advertisement", advertisement);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:advertisement:save")
    public R save(@RequestBody OperateAdvertisement advertisement,HttpServletRequest request) {
        SysUserEntity syaUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        advertisement.setCreateTime(new Date());
        advertisement.setCreateBy(syaUser.getUserId());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(true,"status",0);
        wrapper.eq(advertisement.getType() != null,"type",2);
        int count = operateAdvertisementService.count(wrapper);
        int flag = 3;
        int typeFlag = 2;
        if(count >= flag ){
            if(advertisement.getType()== typeFlag){
                return R.error().put("msg",messageUtils.getMessage("manage.operate.advertisement.error", request));
            }
        }
        operateAdvertisementService.save(advertisement);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:advertisement:update")
    public R update(@RequestBody OperateAdvertisement advertisement) {
        SysUserEntity syaUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        if (advertisement.getJumpMode() == 3) {
            if (advertisement.getModule() == 3) {
                if (!StringUtils.isNumeric(advertisement.getUrl())) {
                    advertisement.setUrl(null);
                }
            }
        }
        advertisement.setUpdateTime(new Date());
        advertisement.setUpdateBy(syaUser.getUserId());
//        advertisement.setUpdateSysUserId(syaUser.getUserId());
        operateAdvertisementService.updateById(advertisement);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:advertisement:delete")
    public R delete(@RequestBody Long[] ids) {
        operateAdvertisementService.updateStatusByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * commissionDto
     */
    @RequestMapping("/getTypeList")
//    @RequiresPermissions("user:userdesignerapplication:delete")
    public R getTypeList(){
        List<Map<String,Object>> typeList  = operateAdvertisementService.getTypeList();
        return R.ok().put("typeList", typeList);
    }
}
