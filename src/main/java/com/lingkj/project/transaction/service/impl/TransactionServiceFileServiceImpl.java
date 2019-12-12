package com.lingkj.project.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.project.transaction.entity.TransactionServiceFile;
import com.lingkj.project.transaction.mapper.TransactionServiceFileMapper;
import com.lingkj.project.transaction.service.TransactionServiceFileService;
import org.springframework.stereotype.Service;

import java.util.Map;


import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;


/**
 * 
 *
 * @author chenyongsong
 * @date 2019-10-21 11:47:26
 */
@Service
public class TransactionServiceFileServiceImpl extends ServiceImpl<TransactionServiceFileMapper, TransactionServiceFile> implements TransactionServiceFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TransactionServiceFile> page = this.page(
                new Query<TransactionServiceFile>(params).getPage(),
                new QueryWrapper<TransactionServiceFile>()
        );

        return new PageUtils(page);
    }

}
