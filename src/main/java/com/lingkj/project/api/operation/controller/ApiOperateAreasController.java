package com.lingkj.project.api.operation.controller;

import com.lingkj.common.utils.R;
import com.lingkj.project.api.operation.dto.AreasDto;
import com.lingkj.project.operation.service.OperateAreasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/operate/areas")
public class ApiOperateAreasController {
    @Autowired
    private OperateAreasService operateAreasService;

    @ApiOperation(value = "根据邮政编码获取省市信息")
    @GetMapping("/queryAreasByCode")
    public R queryByPostalCode(String postalCode, HttpServletRequest request) {
        List<AreasDto> areasDto = operateAreasService.queryByPostalCode(postalCode, request);
        return R.ok().put("areas", areasDto);
    }


}
