package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityFileDto;
import com.lingkj.project.commodity.entity.CommodityFile;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
public interface CommodityFileService extends IService<CommodityFile> {

    PageUtils queryPage(Map<String, Object> params);

    List<ApiCommodityFileDto> selectByCommodityId(Long id);

    CommodityFile[] selectEntityByCommodityId(Long commodityId);

    void deleteNotInBatch(List<CommodityFile> fileUpdList, Long commodityId);
}

