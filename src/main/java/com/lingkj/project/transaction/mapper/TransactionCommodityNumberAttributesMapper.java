package com.lingkj.project.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityNumberAttributeDto;
import com.lingkj.project.transaction.entity.TransactionCommodityNumberAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author chenyongsong
 * @date 2019-10-22 14:55:54
 */
@Mapper
public interface TransactionCommodityNumberAttributesMapper extends BaseMapper<TransactionCommodityNumberAttributes> {
    /**
     * 查询订单商品 数量 服装
     * @param id
     * @return
     */
    List<ApiTransactionCommodityNumberAttributeDto> selectByTransactionCommodityId(@Param("id") Long id);
}
