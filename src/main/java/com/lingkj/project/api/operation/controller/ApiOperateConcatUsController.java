package com.lingkj.project.api.operation.controller;


import com.lingkj.common.utils.R;
import com.lingkj.project.api.operation.dto.ConcatUsDto;
import com.lingkj.project.operation.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ApiOperateConcatUsController
 *
 * @author chen yongsong
 * @className ApiOperateAreasController
 * @date 2019/9/23 10:53
 */
@Api
@Slf4j
@RestController
@RequestMapping("/api/operate/concatUs/")
public class ApiOperateConcatUsController {
    @Autowired
    ProjectService projectService;

    @ApiOperation(value = "关于我们接口 ")
    @GetMapping("/list")
    public R queryConcatUsList() {
        List<ConcatUsDto> concatUsDtoLists = projectService.queryList();
        return R.ok().put("list", concatUsDtoLists);
    }
}
