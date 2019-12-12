package com.lingkj.project.transaction.mapper;

import com.lingkj.project.transaction.entity.TransactionCommodityStatusLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单status 改变记录表
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Mapper
public interface TransactionCommodityStatusLogMapper extends BaseMapper<TransactionCommodityStatusLog> {

}
