package com.lingkj.project.commodity.mapper;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.project.api.commodity.dto.ApiCommodityLadderDto;
import com.lingkj.project.commodity.entity.CommodityLadder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品数量阶梯
 *
 * @author chenyongsong
 * @date 2019-09-17 15:13:42
 */
@Mapper
public interface CommodityLadderMapper extends BaseMapper<CommodityLadder> {

    /**
     * 修改商品时，移除已删除的阶梯价
     *
     * @param commodityId
     * @param updateLadders
     */
    void updateByNoId(@Param("commodityId") Long commodityId, @Param("list") List<Long> updateLadders);

    CommodityLadder[] getList(@Param("commodityId") Long commodityId);

    /**
     * 查询商品阶梯价
     *
     * @param commodityId
     * @return
     */
    List<ApiCommodityLadderDto> selectByCommodityIdApi(@Param("commodityId") Long commodityId);

    /**
     * 查询
     * @param id
     * @return
     */
    JSONObject selectJsonById(Long id);
}
