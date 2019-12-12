package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.commodity.entity.CommodityCommentFile;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-07-04 09:50:39
 */
public interface CommodityCommentFileService extends IService<CommodityCommentFile> {

    PageUtils queryPage(Map<String, Object> params);

    List<String> selectImagesById(Long id);
}

