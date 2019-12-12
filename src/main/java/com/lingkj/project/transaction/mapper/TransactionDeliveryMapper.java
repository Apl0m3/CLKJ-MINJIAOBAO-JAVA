package com.lingkj.project.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.transaction.entity.TransactionDelivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 交易平台配送信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
@Mapper
public interface TransactionDeliveryMapper extends BaseMapper<TransactionDelivery> {

    Map<String, Object> selectByTransactionId(String transactionId);
}
