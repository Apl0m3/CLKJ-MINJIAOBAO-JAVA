package com.lingkj.project.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.transaction.entity.TransactionCommodityAttributes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单 商品属性
 * 
 * @author chenyongsong
 * @date 2019-10-22 14:17:44
 */
@Mapper
public interface TransactionCommodityAttributesMapper extends BaseMapper<TransactionCommodityAttributes> {


    List<TransactionCommodityAttributes> selectByCommodityId(Long trCommodityId);
}
