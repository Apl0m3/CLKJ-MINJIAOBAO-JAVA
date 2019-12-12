package com.lingkj.project.manage.commodity.controller;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.commodity.entity.CommodityComment;
import com.lingkj.project.commodity.service.CommodityCommentFileService;
import com.lingkj.project.commodity.service.CommodityCommentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/commoditycomment")
public class CommodityCommentController {
    @Autowired
    private CommodityCommentService commodityCommentService;
    @Autowired
    private CommodityCommentFileService commodityCommentFileService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:commoditycomment:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = commodityCommentService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("manage:commoditycomment:info")
    public R info(@PathVariable("id") Long id) {
        CommodityComment commodityComment = commodityCommentService.getById(id);

        return R.ok().put("commodityComment", commodityComment);
    }

    /**
     * 保存
     */
     @PostMapping("/save")
    @RequiresPermissions("manage:commoditycomment:save")
    public R save(@RequestBody CommodityComment commodityComment) {
        commodityCommentService.save(commodityComment);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("manage:commoditycomment:update")
    public R update(@RequestBody CommodityComment commodityComment) {
        commodityCommentService.updateById(commodityComment);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("manage:commoditycomment:delete")
    public R delete(@RequestBody Long[] ids) {
        commodityCommentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 获取评论图片详情
     */
    @GetMapping("/getFile/{id}")
    public R getFile(@PathVariable("id") Long id){
        //修改评论为已读状态
        CommodityComment comment = new CommodityComment();
        comment.setId(id);
        comment.setReadStatus(1);
        commodityCommentService.updateById(comment);

        List<String> imgs=commodityCommentFileService.selectImagesById(id);

        return R.ok().put("imgs",imgs);
    }

}
