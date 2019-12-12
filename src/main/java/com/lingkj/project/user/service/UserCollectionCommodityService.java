package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.user.dto.UserCollectionAndCommodityDto;
import com.lingkj.project.user.entity.UserCollectionCommodity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-20 15:40:52
 */
public interface UserCollectionCommodityService extends IService<UserCollectionCommodity> {

    PageUtils queryPage(Map<String, Object> params);
    List<UserCollectionAndCommodityDto> getUserCollectionList( Long userId, Integer start,  Integer end);
    Integer getUserCollectionCount(Long userId);

    void deleteUserCollectionIds( Long userId,List<Long> ids);
    Integer getUserCollectionIdsCount(Long userId);

    /**
     * 用户收藏商品
     * @param userId
     * @param commodityId
     */
    R addOrDelFavorites(Long userId, Long commodityId, HttpServletRequest request);

    /**
     *
     * @param userId
     * @param id
     * @return
     */
    Integer selectRepeat(Long userId, Long id);

    PageUtils selectPageByUserId(Page page1, Long userId);
}

