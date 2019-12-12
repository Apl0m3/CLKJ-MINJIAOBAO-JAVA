package com.lingkj.project.api.commodity.controller;

import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.api.commodity.dto.ApiCommodityCommentAndFileDto;
import com.lingkj.project.commodity.service.CommodityCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ApiCommodityCommentController
 *
 * @author chen yongsong
 * @className ApiCommodityCommentController
 * @date 2019/9/19 10:22
 */
@Api("商品评论模块")
@Slf4j
@RestController
@RequestMapping("/api/commodity/comment")
public class ApiCommodityCommentController {
    @Autowired
    private CommodityCommentService commodityCommentService;

    @ApiOperation("分页查询商品评论")
    @GetMapping("/queryPage")
    public R queryCommentPage(Page page,@RequestParam("commodityId") Long commodityId) {
        PageUtils pageUtils = commodityCommentService.queryPageApi(page, commodityId);
        return R.ok().put("page", pageUtils);
    }
    @ApiOperation("新增评论")
    @PostMapping("/saveComment")
    @Login
    public R  saveComment(@RequestBody ApiCommodityCommentAndFileDto commentAndFileDto,@RequestAttribute("userId")Long userId){
       commodityCommentService.saveComment(commentAndFileDto,userId);
        return R.ok();
    }

}
