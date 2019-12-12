package com.lingkj.project.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.commodity.dto.ApiCommodityCommentDto;
import com.lingkj.project.commodity.entity.CommodityComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface CommodityCommentMapper extends BaseMapper<CommodityComment> {
    /**
     * 分页查询商品评论
     *
     * @param pageSize
     * @param limit
     * @param commodityId
     * @return
     */
    List<ApiCommodityCommentDto> queryComment(@Param("pageSize") Integer pageSize, @Param("limit") Integer limit, @Param("commodityId") Long commodityId);

    /**
     * 统计该商品 所有评论
     *
     * @param commodityId
     * @return
     */
    Integer queryCommentCount(Long commodityId);
}
