package com.lingkj.project.manage.commodity.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.commodity.dto.CommodityDto;
import com.lingkj.project.commodity.dto.IntegralCommodityAddDto;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.service.CommodityService;
import com.lingkj.project.manage.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/commodity")
public class CommodityController extends AbstractController {
    @Autowired
    private CommodityService commodityService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:commodity:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = commodityService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 根据商品名称查询
     */
    @GetMapping("/getCommodityByName")
    public R getCommodityByName(@RequestParam("name") String name) {
        List<Commodity> list = commodityService.getCommodity(name);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:commodity:info")
    public R info(@PathVariable("id") Long id) {
        CommodityDto commodityAddDto = commodityService.selectCommodity(id);

        return R.ok().put("commodityAddDto", commodityAddDto);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:commodity:save")
    public R save(@RequestBody CommodityDto commodityAddDto) {

        commodityService.insertCommodity(commodityAddDto, getSysUserId());

        return R.ok();
    }

    /**
     * 查询积分商品信息
     * @author XXX <XXX@163.com>
     * @date 2019/10/16 16:58
     * @param id
     * @return com.lingkj.common.utils.R
     */
    @GetMapping("/integralInfo/{id}")
    @RequiresPermissions("manage:commodity:info")
    public R integralInfo(@PathVariable("id") Long id) {
        IntegralCommodityAddDto dto = commodityService.selectIntegralCommodity(id);
        return R.ok().put("commodityAddDto", dto);
    }
    /**
     *  保存积分商品
     * @author xxx
     * @date 2019-10-9 9:34
     * @param integralCommodityAddDto
     * @return com.lingkj.common.utils.R
     */
    @PostMapping("/saveIntegralCommodity")
    @RequiresPermissions("manage:commodity:save")
    public R saveIntegralCommodity(@RequestBody IntegralCommodityAddDto integralCommodityAddDto){
        commodityService.insertIntegralCommodity(integralCommodityAddDto);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:commodity:update")
    public R update(@RequestBody CommodityDto commodityAddDto) {

        commodityService.updateCommodity(commodityAddDto, getSysUserId());

        return R.ok();
    }
    /**
     * 修改积分商品
     * @author XXX <XXX@163.com>
     * @date 2019/10/9 9:54
     * @param integralCommodityAddDto
     * @return com.lingkj.common.utils.R
     */
    @PostMapping("/updateIntegralCommodity")
    @RequiresPermissions("manage:commodity:update")
    public R updateIntegralCommodity(@RequestBody IntegralCommodityAddDto integralCommodityAddDto){
        commodityService.updateIntegralCommodity(integralCommodityAddDto);
        return R.ok();
    }
    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:commodity:delete")
    public R delete(@RequestBody Long[] ids) {
        commodityService.updateBatchIds(ids);

        return R.ok();
    }

    /**
     * 上架/下架
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("manage:commodity:updateStatus")
    public R updateStatus(@RequestBody Long[] ids) {
        commodityService.updateBatchStatus(ids);

        return R.ok();
    }


}
