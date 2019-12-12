package com.lingkj.project.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.commodity.dto.ApiCommodityTypeDto;
import com.lingkj.project.commodity.entity.CommodityType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface CommodityTypeMapper extends BaseMapper<CommodityType> {

    /**
     * 逻辑删除
     * @param ids ids
     */
    void updateBatchIds(Long[] ids);


    List<CommodityType> selectListByParent();
    Integer getFirstType();

    List<ApiCommodityTypeDto> queryListApi();

    ApiCommodityTypeDto selectTypeDtoById(Long commodityTypeId);
}
