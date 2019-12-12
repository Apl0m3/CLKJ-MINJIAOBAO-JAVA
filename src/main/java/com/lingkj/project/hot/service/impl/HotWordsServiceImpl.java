package com.lingkj.project.hot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.project.hot.entity.HotWords;
import com.lingkj.project.hot.mapper.HotWordsMapper;
import com.lingkj.project.hot.service.HotWordsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;



/**
 * 
 *
 * @author chenyongsong
 * @date 2019-11-11 09:52:12
 */
@Service
public class HotWordsServiceImpl extends ServiceImpl<HotWordsMapper, HotWords> implements HotWordsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<HotWords> wrapper = new QueryWrapper<>();
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        String name = (String) params.get("name");
        if (StringUtils.isNotBlank(name)){
            wrapper.like("name",name);
        }
        wrapper.orderByDesc("modify_time");
        IPage<HotWords> page = this.page(
                new Query<HotWords>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public HotWords selectName(String name) {
        QueryWrapper<HotWords> wordsQuery=new QueryWrapper<HotWords>();
        wordsQuery.eq("name",name);
        HotWords one = this.getOne(wordsQuery);
        return one;
    }

}
