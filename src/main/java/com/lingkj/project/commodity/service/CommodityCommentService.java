package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityCommentAndFileDto;
import com.lingkj.project.commodity.entity.CommodityComment;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
public interface CommodityCommentService extends IService<CommodityComment> {

    PageUtils queryPage(Map<String, Object> params);

    /*********************api*****************************/
    /**
     * 分页查询商品评论
     *
     * @param page
     * @param commodityId
     * @return
     */
    PageUtils queryPageApi(Page page, Long commodityId);

    void  saveComment (ApiCommodityCommentAndFileDto commentAndFileDto,Long userId);
}

