package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.commodity.entity.CommodityCommentFile;
import com.lingkj.project.commodity.mapper.CommodityCommentFileMapper;
import com.lingkj.project.commodity.service.CommodityCommentFileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("commodityCommentFileService")
public class CommodityCommentFileServiceImpl extends ServiceImpl<CommodityCommentFileMapper, CommodityCommentFile> implements CommodityCommentFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommodityCommentFile> page = this.page(
                new Query<CommodityCommentFile>(params).getPage(),
                new QueryWrapper<CommodityCommentFile>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<String> selectImagesById(Long id) {
        return baseMapper.selectImagesById(id);
    }

}
