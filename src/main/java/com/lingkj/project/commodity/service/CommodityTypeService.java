package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityTypeDto;
import com.lingkj.project.commodity.entity.CommodityType;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
public interface CommodityTypeService extends IService<CommodityType> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 逻辑删除
     * @param ids ids
     */
    void updateBatchIds(Long[] ids);


    /**
     * 查询所有分类
     * @return
     */
    List<CommodityType> queryList();
    /**
     * 根据id查询相对应的分类
     * @return
     */
    List<CommodityType> selectBatchIds(List<String> asList);

    List<ApiCommodityTypeDto> queryListApi();

    ApiCommodityTypeDto selectTypeDtoById(Long commodityTypeId);

}

