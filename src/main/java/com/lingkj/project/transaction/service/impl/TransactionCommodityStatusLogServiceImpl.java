package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.transaction.entity.TransactionCommodityStatusLog;
import com.lingkj.project.transaction.mapper.TransactionCommodityStatusLogMapper;
import com.lingkj.project.transaction.service.TransactionCommodityStatusLogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 订单status 改变记录表
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Service
public class TransactionCommodityStatusLogServiceImpl extends ServiceImpl<TransactionCommodityStatusLogMapper, TransactionCommodityStatusLog> implements TransactionCommodityStatusLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionCommodityStatusLog> page = this.page(
                new Query<TransactionCommodityStatusLog>(params).getPage(),
                new QueryWrapper<TransactionCommodityStatusLog>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveStatusLog(Long userId, Long transactionCommodityId,Long recordId,Integer type, Integer status, Integer substate) {
        TransactionCommodityStatusLog commodityStatusLog=new TransactionCommodityStatusLog();

        commodityStatusLog.setUserId(userId);
        commodityStatusLog.setTransactionCommodityId(transactionCommodityId);
        commodityStatusLog.setRecordId(recordId);
        commodityStatusLog.setCreateTime(new Date());
        commodityStatusLog.setType(type);
        if(status!=null){
            commodityStatusLog.setStatus(status);
        }
        if(substate==null){
            commodityStatusLog.setSubstate(0);
        }else {
            commodityStatusLog.setSubstate(substate);
        }

        this.save(commodityStatusLog);
    }

}
