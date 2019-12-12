package com.lingkj.project.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityLadderDto;
import com.lingkj.project.transaction.entity.TransactionCommodityLadder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单商品数量阶梯价
 * 
 * @author chenyongsong
 * @date 2019-10-22 14:17:43
 */
@Mapper
public interface TransactionCommodityLadderMapper extends BaseMapper<TransactionCommodityLadder> {
    /**
     *  订单商品数量阶梯价
     * @param id
     * @return
     */
    ApiTransactionCommodityLadderDto selectByCommodityId(Long id);

}
