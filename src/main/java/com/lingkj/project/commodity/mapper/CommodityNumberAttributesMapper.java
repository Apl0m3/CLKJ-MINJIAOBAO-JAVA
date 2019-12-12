package com.lingkj.project.commodity.mapper;

import com.lingkj.project.commodity.dto.CommodityNumberAttributesDto;
import com.lingkj.project.commodity.entity.CommodityNumberAttributes;
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
public interface CommodityNumberAttributesMapper extends BaseMapper<CommodityNumberAttributes> {

    CommodityNumberAttributesDto[] getList(Long commodityId);

    List<Long> selectNotInIds(@Param("updateAttributesIds") List<Long> updateAttributesIds,@Param("commodityId") Long commodityId);

    void updateStatusInIds(List<Long> deleteIds);
}
