package com.lingkj.project.api.user.controller;


import com.lingkj.common.authentication.annotation.Login;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.user.dto.UserCollectionAndCommodityDto;
import com.lingkj.project.user.service.UserCollectionCommodityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Api(value = "用户收藏夹接口")
@RestController
@RequestMapping("/api/user/collection")
public class ApiUserCollectionController {

    @Autowired
    private UserCollectionCommodityService userCollectionCommodityService;


    /**
     * 用户收藏夹类别
     * @param page
     * @param userId
     * @return
     */
    @PostMapping("/userCollection")
    @Login
    public R getCollectionLsit(@RequestBody Page page, @RequestAttribute("userId") Long userId) {
        Integer start = page.getLimit();
        Integer end = page.getPageSize();
        List<UserCollectionAndCommodityDto> userCollectionList = userCollectionCommodityService.getUserCollectionList(userId, start, end);
        Integer userCollectionCount = userCollectionCommodityService.getUserCollectionCount(userId);
        PageUtils pageUtils = new PageUtils(userCollectionList, userCollectionCount, page.getPageSize(), page.getPage());
        return R.ok().put("page", pageUtils);
    }


    /**
     * 批量删除收藏夹
     * @param id
     * @param userId
     * @return
     */
    @PostMapping("/deletesCollection")
    @Login
    public R deletesCollection(@RequestBody Long[] id,@RequestAttribute("userId")Long userId) {
        if (id == null || id.length <0) {
            return R.error();
        }
        List<Long> longs = Arrays.asList(id);
            userCollectionCommodityService.deleteUserCollectionIds(userId,longs);
        return R.ok();
    }

    /**
     * 添加收藏
     * @param commodityId
     * @param userId
     * @return
     */
    @Login
    @PostMapping("/addOrDelFavorites")
    public R addOrDelFavorites(@RequestParam("commodityId") Long commodityId , @RequestAttribute("userId")Long userId, HttpServletRequest request) {

        return userCollectionCommodityService.addOrDelFavorites(userId,commodityId,request);
    }
}
