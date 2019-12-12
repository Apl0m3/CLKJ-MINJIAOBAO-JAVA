package com.lingkj.project.api.commodity.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.commodity.dto.ApiCommodityAttributeDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityListDto;
import com.lingkj.project.api.commodity.dto.ApiDetailIntegralCommodityDto;
import com.lingkj.project.commodity.dto.CommodityNumberAttributesDto;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.service.CommodityNumberAttributesService;
import com.lingkj.project.commodity.service.CommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ApiCommodityController
 *
 * @author chen yongsong
 * @className ApiCommodityController
 * @date 2019/9/19 10:18
 */
@Api("商品模块")
@RestController
@Slf4j
@RequestMapping("/api/commodity/commodity")
public class ApiCommodityController {

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CommodityNumberAttributesService commodityNumberAttributesService;

    @ApiOperation(value = "商品列表查询")
    @Login(required = false)
    @GetMapping("/queryPage")
    public R queryCommodityPage(Page page, Integer type, Long typeId, @RequestAttribute(value = "userId", required = false) Long userId) {
        PageUtils pageUtils = commodityService.queryPageApi(page, type, userId, typeId);
        return R.ok().put("page", pageUtils);
    }

    @ApiOperation(value = "商品详情查询")
    @GetMapping("/info/{id}")
    @Login(required = false)
    public R info(@PathVariable("id") Long id, @RequestAttribute(value = "userId", required = false) Long userId, HttpServletRequest request) {
        R ok = R.ok();
        ApiCommodityDto commodityDto = commodityService.info(id, userId, request);
        if (commodityDto.getNumberSelectType() != null && commodityDto.getNumberSelectType().equals(3)) {
            CommodityNumberAttributesDto[] commodityNumberAttributes = commodityNumberAttributesService.getList(id);
            ok.put("commodityNumberAttributes", commodityNumberAttributes);
        }
        ok.put("commodity", commodityDto);
        return ok;
    }

    @ApiOperation(value = "商品属性")
    @GetMapping("/queryAttribute/{id}")
    public R queryAttribute(@PathVariable("id") Long commodityId) {
        List<ApiCommodityAttributeDto> list = commodityService.queryAttributeApi(commodityId);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "商品到货时间")
    @GetMapping("/queryExpectedDelivery/{id}")
    public R queryExpectedDelivery(@PathVariable("id") Long commodityId) {
        Map<String, Object> map = commodityService.queryExpectedDeliveryApi(commodityId);
        return R.ok().put("map", map);
    }

    @ApiOperation(value = "积分商品分页记录查询")
    @PostMapping("/queryIntegralPage")
    public R queryIntegralPage(Page page) {
        PageUtils pageUtils = commodityService.queryIntegralPageApi(page);

        return R.ok().put("page", pageUtils);
    }

    @ApiOperation(value = "积分商品详情查询")
    @PostMapping("/queryIntegralDetail")
    public R queryIntegralDetail(@RequestParam Map<String, Object> params) {
        ApiDetailIntegralCommodityDto apiDetailIntegralCommodityDto = commodityService.queryIntegralDetailApi(params);
        return R.ok().put("object", apiDetailIntegralCommodityDto);
    }

    @ApiOperation(value = "查询最新添加的20个商品,id表示不需要查询的商品")
    @PostMapping("/queryLast20Commodity")
    @Login(required = false)
    public R queryLastCommodityList(Long id, @RequestAttribute(value = "userId", required = false) Long userId) {
        List<ApiCommodityListDto> commodities = commodityService.queryLastCommodityList(id, userId);
        return R.ok().put("list", commodities);
    }

}
