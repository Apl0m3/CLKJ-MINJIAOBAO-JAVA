package com.lingkj.project.api.operation.controller;

import com.lingkj.common.utils.R;
import com.lingkj.project.api.operation.dto.AdvertisementDto;
import com.lingkj.project.operation.service.OperateAdvertisementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * ApiOperateAreasController
 *
 * @author chen yongsong
 * @className ApiOperateAreasController
 * @date 2019/9/23 10:53
 */
@Api
@Slf4j
@RestController
@RequestMapping("/api/operate/advertisement/")
public class ApiOperateAdevertisementController {

    @Autowired
    OperateAdvertisementService operateAdvertisementService;

    @ApiOperation(value = "查询广告列表", httpMethod = "GET")
    @GetMapping("/list")
    public R queryList(@RequestParam("position") Integer position, @RequestParam("type")Integer type) {
        List<AdvertisementDto> advertisementRespDtos = operateAdvertisementService.queryAdvertisementRespDtoList(position,type);
        return R.ok().put("list", advertisementRespDtos);
    }
}
