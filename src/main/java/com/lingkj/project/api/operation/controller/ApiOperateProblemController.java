package com.lingkj.project.api.operation.controller;

import com.lingkj.common.utils.R;
import com.lingkj.project.api.operation.dto.CommonProblemDto;
import com.lingkj.project.operation.service.CommonProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/operate/commonProblem")
public class ApiOperateProblemController {
    @Autowired
    CommonProblemService commonProblemService;

    @ApiOperation(value = "查询常见问题列表", httpMethod = "GET")
    @GetMapping("/list")
    public R queryCommonProblemList() {
        List<CommonProblemDto> commonProblemDtoLists = commonProblemService.getCommonProblemDtoList();
        return R.ok().put("list", commonProblemDtoLists);
    }
}
