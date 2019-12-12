package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.commodity.entity.CommodityUserCollection;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
public interface CommodityUserCollectionService extends IService<CommodityUserCollection> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 商品收藏
     *
     * @param page
     * @param userId
     * @return
     */
    PageUtils queryUserCollectionList(Page page, Long userId);

    PageUtils selectPageByUserId(Page page, Long userId);

    /**
     * 用户收藏商品
     *
     * @param commodityId
     * @param userId
     */
    void save(Long commodityId, Long userId);

    /**
     * 用户购物车 收藏商品
     * @param asList
     * @param userId
     */
    void saveByCart(List<Long> asList, Long userId);

    Integer collectionStatus(Long id, Long userId);
}

