package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.operation.entity.ReturnReasons;
import com.lingkj.project.operation.mapper.ReturnReasonsMapper;
import com.lingkj.project.operation.service.ReturnReasonsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



/**
 * 退款原因
 *
 * @author chenyongsong
 * @date 2019-09-19 16:11:50
 */
@Service
public class ReturnReasonsServiceImpl extends ServiceImpl<ReturnReasonsMapper, ReturnReasons> implements ReturnReasonsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReturnReasons> page = this.page(
                new Query<ReturnReasons>(params).getPage(),
                new QueryWrapper<ReturnReasons>().orderByDesc("create_time")
        );

        return new PageUtils(page);
    }


}
