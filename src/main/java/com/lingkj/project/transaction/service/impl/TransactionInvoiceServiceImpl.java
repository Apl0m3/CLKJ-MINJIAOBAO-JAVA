package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.transaction.dto.ApiTransactionInvoiceDto;
import com.lingkj.project.operation.entity.OperateAreas;
import com.lingkj.project.operation.service.OperateAreasService;
import com.lingkj.project.transaction.entity.TransactionInvoice;
import com.lingkj.project.transaction.mapper.TransactionInvoiceMapper;
import com.lingkj.project.transaction.service.TransactionInvoiceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单 用户发票信息
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Service
public class TransactionInvoiceServiceImpl extends ServiceImpl<TransactionInvoiceMapper, TransactionInvoice> implements TransactionInvoiceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionInvoice> page = this.page(
                new Query<TransactionInvoice>(params).getPage(),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public ApiTransactionInvoiceDto selectByRecordId(Long recordId) {
        return this.baseMapper.selectByRecordId(recordId);
    }


}
