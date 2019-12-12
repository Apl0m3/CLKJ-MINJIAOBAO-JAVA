package com.lingkj.project.commodity.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.commodity.dto.ApiCommodityExpectDto;
import com.lingkj.project.commodity.entity.CommodityExpect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预计到货时间
 *
 * @author chenyongsong
 * @date 2019-09-26 11:41:34
 */
@Mapper
public interface CommodityExpectMapper extends BaseMapper<CommodityExpect> {
    /**
     * 移除 删除的到货时间
     *
     * @param commodityId
     * @param updateExpectIds
     */
    void updateByNoId(@Param("commodityId") Long commodityId, @Param("list") List<Long> updateExpectIds);

    CommodityExpect[] getList(@Param("id") Long id);

    /**
     * 查询商品预计到达时间
     *
     * @param commodityId
     * @return
     */
    List<ApiCommodityExpectDto> selectByCommodityIdApi(Long commodityId);

    /**
     * 获取 预计到货json
     * @param id
     * @return
     */
    JSONObject selectJsonById(Long id);
}
