package com.lingkj.project.commodity.mapper;

import com.lingkj.project.commodity.entity.CommodityNumberAttributesValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-10-11 16:30:01
 */
@Mapper
public interface CommodityNumberAttributesValueMapper extends BaseMapper<CommodityNumberAttributesValue> {

    CommodityNumberAttributesValue[] queryByCommodityNumberAttributesValueId(Long commodityNumberAttributesId);

    void updateStatusInIds(List<Long> deleteIds);

    void updateStatusNotInIds(@Param("updateValueIds") List<Long> updateValueIds,@Param("commodityNumberAttributesId") Long commodityNumberAttributesId);
}
