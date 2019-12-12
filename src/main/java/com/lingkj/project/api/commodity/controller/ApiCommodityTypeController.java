package com.lingkj.project.api.commodity.controller;

import com.lingkj.common.utils.R;
import com.lingkj.project.api.commodity.dto.ApiCommodityTypeDto;
import com.lingkj.project.commodity.service.CommodityTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ApiCommodityTypeController
 *
 * @author chen yongsong
 * @className ApiCommodityTypeController
 * @date 2019/9/24 10:15
 */
@Api("商品分类模块")
@RestController
@RequestMapping("/api/commodity/type")
public class ApiCommodityTypeController {
    @Autowired
    private CommodityTypeService commodityTypeService;

    @RequestMapping("/queryList")
    public R queryCommodityType() {
        List<ApiCommodityTypeDto> list = this.commodityTypeService.queryListApi();
        return R.ok().put("list", list);
    }

}
