package com.lingkj.project.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.transaction.entity.TransactionCommodityExpect;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单商品 预计到货时间
 * 
 * @author chenyongsong
 * @date 2019-10-22 14:17:44
 */
@Mapper
public interface TransactionCommodityExpectMapper extends BaseMapper<TransactionCommodityExpect> {
	
}
